package Simulation.View;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class getSensors implements HttpHandler {
	private String sensorsList;

	public getSensors(String sensorsList){
		this.sensorsList = sensorsList;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//send sensors
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		exchange.sendResponseHeaders(200, sensorsList.length());
		System.out.println(exchange.getRequestHeaders());
		OutputStream os = exchange.getResponseBody();
		os.write(sensorsList.getBytes());
		os.close();
	}

}
