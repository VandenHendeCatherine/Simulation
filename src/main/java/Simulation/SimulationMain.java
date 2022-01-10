package Simulation;

import Simulation.FireController.*;
import Simulation.MicroBit.SerialPortCommunication;
import Simulation.View.getSensors;
import Simulation.View.JSonUtils;
import Simulation.View.ViewController;
import Simulation.View.postSensors;
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
		Configuration config = new Configuration();
		config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:30306/Simulation");
		config.setProperty("hibernate.connection.username", "root");
		config.setProperty("hibernate.connection.password", "CYFBcpe2021");
		config.setProperty("dialect", "net.sf.hibernate.dialect.MySQLDialect");
		config.addAnnotatedClass(Capteur.class);
		config.addAnnotatedClass(Fire.class);
		config.addAnnotatedClass(Intervention.class);
		SessionFactory sessionFactory = config.buildSessionFactory();


		FireController fireController = new FireController();

		JSONObject jsonObject = new JSONObject();
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		server.start();
		try (Session session = sessionFactory.openSession()) {

			List<Capteur> capteurInDataBase = loadAllData(Capteur.class,session);

			fireController.setSensors(capteurInDataBase);
			List<Fire> fires = new ArrayList<>();
			AlertGenerator alertGenerator = new AlertGenerator();
			int i = 0;
			while(i < 10) {
				fires.add(alertGenerator.generate());
				i++;
			}
			ViewController viewController = new ViewController(fires, capteurInDataBase);
			fireController.setViewController(viewController);
			viewController.setFire(fires);

			server.createContext("/postCapteurs", new postSensors(fireController));

			for(Fire fire : fires) {
				List<Capteur> capteurs = new ArrayList<>();
				capteurs.addAll(fireController.calculatePositionSensor(fire));

				System.out.println("Capteurs : " + capteurs);
				System.out.println("Feu : " + fire);
				jsonObject = JSonUtils.buildJSonSensors(capteurs);
				System.out.println("Json : " + jsonObject);
				SerialPortCommunication serialPortCommunication = new SerialPortCommunication();
				serialPortCommunication.sendSensorIntensityToComm(capteurs);
				serialPortCommunication.closeCommunication();
				capteurs.clear();
				Thread.sleep(15000);
				server.createContext("/getCapteurs", new getSensors(jsonObject.toString()));

			}
			sessionFactory.close();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static <T> List<T> loadAllData(Class<T> type, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		criteria.from(type);
		List<T> data = session.createQuery(criteria).getResultList();
		return data;
	}
}
