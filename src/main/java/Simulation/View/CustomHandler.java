package Simulation.View;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CustomHandler implements HttpHandler {
	private String sensorsList;

	public CustomHandler(String sensorsList){
		this.sensorsList = sensorsList;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		//receive sensors
		InputStream inputStream = exchange.getRequestBody();
		byte[] bytes = inputStream.readAllBytes();
		String string =new String(bytes, StandardCharsets.UTF_8);
		System.out.println(string);

		String query = exchange.getRequestURI().getQuery();
		System.out.println(query);

		//send sensors
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		exchange.sendResponseHeaders(200, sensorsList.length());
		System.out.println(exchange.getRequestHeaders());
		OutputStream os = exchange.getResponseBody();
		os.write(sensorsList.getBytes());
		os.close();
	}

}
