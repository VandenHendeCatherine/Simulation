package Simulation.View;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.*;

public class CustomHandler implements HttpHandler {
	private String json;

	public CustomHandler(String json){
		this.json = json;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String requestMethod = exchange.getRequestMethod();

		String query = exchange.getRequestURI().getQuery();
		System.out.println(query);

		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		//String response = json;
		exchange.sendResponseHeaders(200, json.length());
		System.out.println(exchange.getRequestHeaders());
		OutputStream os = exchange.getResponseBody();
		os.write(json.getBytes());
		os.close();
	}

}
