package Simulation;

import Simulation.FireController.AlertGenerator;
import Simulation.FireController.Fire;
import Simulation.FireController.Sensor;
import Simulation.MicroBit.SerialPortCommunication;
import Simulation.View.CustomHandler;
import Simulation.View.JSonUtils;
import Simulation.View.ViewController;
import com.sun.net.httpserver.HttpServer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class SimulationMain { ;

	public static void main(String[] args) throws  IOException{
		Configuration config = new Configuration();
		config.addClass(Sensor.class);
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			Sensor personne = session.load(Sensor.class, 40);
			System.out.println("id = " + personne.getId());
		} finally {
			session.close();
		}

		sessionFactory.close();

		List<Fire> fires = new ArrayList<>();
		AlertGenerator alertGenerator = new AlertGenerator();
		int i = 0;
		while( i < 10 ){
			fires.add(alertGenerator.generate());
			i++;
		}

		//test iot
		List<Sensor> sensors = new ArrayList<>();

			sensors.add(new Sensor(40, 6));
			sensors.add(new Sensor(5, 4));
			sensors.add(new Sensor(56, 8));
			sensors.add(new Sensor(37, 3));


		ViewController viewController = new ViewController(fires, sensors);
		viewController.setFire(fires);
		System.out.println(fires);
		JSONObject jsonObject = JSonUtils.buildJSonSensors(sensors);
		System.out.println(jsonObject);

			SerialPortCommunication serialPortCommunication = new SerialPortCommunication();
			serialPortCommunication.sendSensorIntensityToComm(sensors);
			serialPortCommunication.closeCommunication();
			sensors.clear();

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/getCapteurs", new CustomHandler(jsonObject.toString()));
		server.start();

	}
}
