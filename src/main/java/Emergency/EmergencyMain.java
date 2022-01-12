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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EmergencyMain { ;

	public static void main(String[] args) throws IOException, MqttException {
		SessionFactory sessionFactory = getSessionFactory();
		FireController fireController = new FireController();


		final String serverUrl   = "tcp://164.4.3.201:1883";     /* ssl://mqtt.cumulocity.com:8883 for a secure connection */
		final String clientId    = "my_mqtt_java_client";
		final String device_name = "My Java MQTT device";
		final String tenant      = "<<tenant_ID>>";
		final String username    = "<<username>>";
		final String password    = "<<password>>";

		// MQTT connection options
		final MqttConnectOptions options = new MqttConnectOptions();
		options.setUserName(tenant + "/" + username);
		options.setPassword(password.toCharArray());

		// connect the client to Cumulocity IoT
		final MqttClient client = new MqttClient(serverUrl, clientId, null);
		client.connect();

		// listen for operations
		List<Capteur> capteurs = new ArrayList<>();
		client.subscribe("python/capteur_data", new IMqttMessageListener() {
			public void messageArrived (final String topic, final MqttMessage message) throws Exception {
				final String payload = new String(message.getPayload());

				System.out.println("Received capteur " + payload);
				List<String> id = List.of(payload.split(" "));
				List<String> intensity = List.of(id.get(1).split("="));
				Capteur capteur = new Capteur(new Integer(id.get(0)), new Integer(intensity.get(1)));
				capteurs.add(capteur);
				if(capteurs.size()==4){

				}
			}
		});

		//Server
		HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
		server.start();
		System.out.println("tata");
		try (Session session = sessionFactory.openSession()) {

			List<Capteur> capteurInDataBase = loadAllData(Capteur.class,session);

			fireController.setSensors(capteurInDataBase);

			//Generate Fires
			List<Fire> fires = new ArrayList<>();
			AlertGenerator alertGenerator = new AlertGenerator();
			int i = 0;
			while(i < 5) {
				fires.add(alertGenerator.generate());
				i++;
			}

			ViewController viewController = new ViewController(fires, capteurInDataBase);
			fireController.setViewController(viewController);
			fireController.setAlertGenerator(alertGenerator);
			fireController.setFires(fires);

			//Server requests
			getCamion getCamion = new getCamion(null);
			getFire getFire = new getFire(null);

			List<Camion> camions = new ArrayList<>(fireController.getCamions());
			for(Fire fire : fires) {

				//Building JSON
				JSONObject jsonObject = JSonUtils.buildJSonCamions(camions);
				JSONObject jsonObjectFire = JSonUtils.buildJSonFire(fire);



				//Server requests update
				getCamion.setSensorsList(jsonObject.toString());
				getFire.setFire(jsonObjectFire.toString());

				//server.createContext("/getFeux", getFire);
				//server.createContext("/getCapteurs", getSensors);

				//Pause for 20s between each fire
				Thread.sleep(20000);
			}

			//session.save(capteurInDataBase);
			sessionFactory.close();


		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//Hibernate configuration
	private static SessionFactory getSessionFactory() {
		Configuration config = new Configuration();
		config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:30306/Simulation");
		config.setProperty("hibernate.connection.username", "root");
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
