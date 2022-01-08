package Simulation;

import Simulation.FireController.AlertGenerator;
import Simulation.FireController.Fire;
import Simulation.FireController.Sensor;
import Simulation.MicroBit.SerialPortCommunication;
import Simulation.View.CustomHandler;
import Simulation.View.JSonUtils;
import Simulation.View.ViewController;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class SimulationMain { ;

	public static void main(String[] args) throws  IOException{

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
