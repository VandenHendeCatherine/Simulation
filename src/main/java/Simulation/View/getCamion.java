package Simulation.View;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class getCamion implements HttpHandler {
	private String camionsList;

	public void setSensorsList(String sensorsList) {
		this.camionsList = sensorsList;
	}

	public getCamion(String sensorsList){
		this.camionsList = sensorsList;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		//send sensors
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		System.out.println("getCamion : " + camionsList);
		exchange.sendResponseHeaders(200, camionsList.length());
		OutputStream os = exchange.getResponseBody();
		os.write(camionsList.getBytes());
		os.close();
	}

}
