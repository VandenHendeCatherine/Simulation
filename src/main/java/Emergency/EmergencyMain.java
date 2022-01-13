package Emergency;

import Emergency.FireController.*;
import Emergency.View.*;
import com.sun.net.httpserver.HttpServer;
import org.eclipse.paho.client.mqttv3.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EmergencyMain {

	public static void main(String[] args) throws IOException, MqttException {
		SessionFactory sessionFactory = getSessionFactory();
		FireController fireController = new FireController();

		try (Session session = sessionFactory.openSession()) {
			List<Capteur> capteurInDataBase = loadAllData(Capteur.class,session);
			List<Caserne> caserneInDataBase = loadAllData(Caserne.class,session);
			List<Camion> camionInDataBase = loadAllData(Camion.class,session);
			/*for(Camion camion: camionInDataBase){
				int randomCaserne = new Random().nextInt(5) +1;
				camion.setCaserne(caserneInDataBase.get(randomCaserne));
			}
*/
			fireController.setSensors(capteurInDataBase);
			ViewController viewController = new ViewController(null, capteurInDataBase);
			fireController.setViewController(viewController);
			fireController.setCamions(camionInDataBase);
			fireController.setCasernes(caserneInDataBase);

			//MQTT Broker connection
			final String serverUrl   = "tcp://164.4.3.201:1883";
			final String clientId    = "my_mqtt_java_client";

			// connect the client to Cumulocity IoT
			final MqttClient client = new MqttClient(serverUrl, clientId, null);
			client.connect();

			//Server
			HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
			server.start();

			//Server requests
			getCamion getCamion = new getCamion(null);
			getFire getFire = new getFire(null);

			// listen for operations

			List<Capteur> capteurs = new ArrayList<>();
			Fire[] fire = { new Fire() };
			List<Camion> camions = new ArrayList<>(fireController.getCamions());

			Thread thread = new Thread(){
				public void run(){
					System.out.println("Thread Interventions Running");
					fireController.camionManagement();
				}
			};
			thread.start();

			client.subscribe("python/capteur_data", new IMqttMessageListener() {
				public void messageArrived (final String topic, final MqttMessage message) throws Exception {
					final String payload = new String(message.getPayload());
					System.out.println("Received capteur " + payload);
					List<String> id = List.of(payload.split(" "));
					List<String> intensity = List.of(id.get(1).split("="));
					intensity = List.of(intensity.get(1).split("\r"));
					System.out.println("id : "+id);
					Capteur capteur = new Capteur(Integer.valueOf(id.get(0)), Integer.valueOf(intensity.get(0)));
					System.out.println("Capteur : "+ capteur);
					capteurs.add(capteur);
					fireController.gatherCapteurToCreateFire(fireController, getCamion, getFire, capteurs, fire, camions);
				}
			});

			server.createContext("/getFeux", getFire);
			server.createContext("/getCamions", getCamion);
			}

			sessionFactory.close();

	}



	//Hibernate configuration
	private static SessionFactory getSessionFactory() {
		Configuration config = new Configuration();
		config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		config.setProperty("hibernate.connection.url", "jdbc:mysql://164.4.3.175:3306/EmergencyLogic");
		config.setProperty("hibernate.connection.username", "Emergency");
		config.setProperty("hibernate.connection.password", "CYFBcpe2021");
		config.setProperty("dialect", "net.sf.hibernate.dialect.MySQLDialect");
		config.addAnnotatedClass(Capteur.class);
		config.addAnnotatedClass(Fire.class);
		config.addAnnotatedClass(Intervention.class);
		config.addAnnotatedClass(Caserne.class);
		config.addAnnotatedClass(Camion.class);
		return config.buildSessionFactory();
	}

	//Hibernate data
	private static <T> List<T> loadAllData(Class<T> type, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		criteria.from(type);
		return session.createQuery(criteria).getResultList();
	}
}
