package Simulation;

import Simulation.FireController.*;
import Simulation.MicroBit.SerialPortCommunication;
import Simulation.View.*;
import com.sun.net.httpserver.HttpServer;
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

public class SimulationMain { ;

	public static void main(String[] args) throws  IOException {
		SessionFactory sessionFactory = getSessionFactory();
		FireController fireController = new FireController();

		//Server
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.start();

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
			getSensors getSensors = new getSensors(null);
			getFire getFire = new getFire(null);

			server.createContext("/postCapteurs",  new postSensors(fireController));

			for(Fire fire : fires) {
				List<Capteur> capteurs = new ArrayList<>(fireController.calculatePositionSensor(fire));

				//Building JSON
				JSONObject jsonObject = JSonUtils.buildJSonSensors(capteurs);
				JSONObject jsonObjectFire = JSonUtils.buildJSonFire(fire);

				//micro-bit
				SerialPortCommunication serialPortCommunication = new SerialPortCommunication();
				serialPortCommunication.sendSensorIntensityToComm(capteurs);
				serialPortCommunication.closeCommunication();
				capteurs.clear();

				//Server requests update
				getSensors.setSensorsList(jsonObject.toString());
				getFire.setFire(jsonObjectFire.toString());

				server.createContext("/getFeux", getFire);
				server.createContext("/getCapteurs", getSensors);

				//Pause for 20s between each fire
				Thread.sleep(20000);
			}

			for(Capteur capteur: capteurInDataBase){

				session.save(capteur);
			}
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
