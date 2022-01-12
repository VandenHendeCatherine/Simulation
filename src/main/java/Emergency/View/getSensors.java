package Emergency.View;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class getSensors implements HttpHandler {
	private String sensorsList;

	public void setSensorsList(String sensorsList) {
		this.sensorsList = sensorsList;
	}

	public getSensors(String sensorsList){
		this.sensorsList = sensorsList;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		//send sensors
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		System.out.println("getCapteur : " + sensorsList);
		exchange.sendResponseHeaders(200, sensorsList.length());
		OutputStream os = exchange.getResponseBody();
		os.write(sensorsList.getBytes());
		os.close();
	}

}
