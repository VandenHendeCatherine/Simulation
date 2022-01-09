package Simulation.View;

import Simulation.FireController.Fire;
import Simulation.FireController.FireController;
import Simulation.FireController.Sensor;
import com.sun.net.httpserver.*;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class postSensors implements HttpHandler {
	//private List<Sensor> sensorsList;
	private FireController fireController;

	public postSensors(FireController fireController){
		this.fireController = fireController;

	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(isr);

		// From now on, the right way of moving from bytes to utf-8 characters:
		int b;
		StringBuilder buf = new StringBuilder(512);
		while ((b = br.read()) != -1) {
			buf.append((char) b);
		}
		System.out.println(buf);
		Sensor sensor = new Sensor();
		try {
			sensor = JSonUtils.readJSonSensor(buf.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Sensor> sensorList =new ArrayList<>();
		sensorList.add(sensor);
		System.out.println(sensor);
		Fire fire  = fireController.calculatePositionFire(sensorList);
		System.out.println("Feu : " + fire);
		JSONObject jsonObject = JSonUtils.buildJSonFire(fire);
		String response = jsonObject.toString();
		System.out.println(response);
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
		exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
		exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

		//String response = "{\"tata\" : 41}";
		exchange.sendResponseHeaders(200, response.length());
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
		br.close();
		isr.close();
		System.out.println("Query : " + buf);


	}

}
