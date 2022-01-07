package Simulation;

import Simulation.FireController.AlertGenerator;
import Simulation.FireController.Fire;
import Simulation.FireController.Sensor;
import Simulation.MicroBit.SerialPortCommunication;
import Simulation.View.CustomHandler;
import Simulation.View.GreetClient;
import Simulation.View.ViewController;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SimulationMain { ;

	public static void main(String[] args) throws InterruptedException, IOException, ParseException {

		List<Fire> fires = new ArrayList<>();
		AlertGenerator alertGenerator = new AlertGenerator();
		int i = 0;
		while( i < 10 ){
			fires.add(alertGenerator.generate());
			i++;
		}

		//test iot
		List<Sensor> sensors = new ArrayList<>();

			sensors.add(new Sensor(18, 6));
			sensors.add(new Sensor(2, 1));
			sensors.add(new Sensor(3, 1));
			sensors.add(new Sensor(4, 1));


		ViewController viewController = new ViewController(fires, sensors);
		viewController.setFire(fires);
		System.out.println(fires);
		JSONObject jsonObject = viewController.buildJSonSensors(sensors);
		System.out.println(jsonObject);

			SerialPortCommunication serialPortCommunication = new SerialPortCommunication();
			serialPortCommunication.sendSensorIntensityToComm(sensors);
			serialPortCommunication.closeCommunication();
			sensors.clear();

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/test", new CustomHandler(jsonObject.toString()));
		server.start();
		/*PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
		File file = new File(path);
		if (!file.exists()) {
			out.write("HTTP 404"); // the file does not exists
		}
		FileReader fr = new FileReader(file);
		BufferedReader bfr = new BufferedReader(fr);
		String line;
		while ((line = bfr.readLine()) != null) {
			out.write(line);
		}

		bfr.close();
		br.close();
		out.close();
		*/

	}
}
