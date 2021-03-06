
package Emergency.View;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class getFire implements HttpHandler {
	private String fire;

	public String getFire() {
		return fire;
	}

	public void setFire(String fire) {
		this.fire = fire;
	}

	public getFire(String fire){
		this.fire = fire;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		//send fire
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		System.out.println("getFeux : " + fire);
		exchange.sendResponseHeaders(200, fire.length());
		OutputStream os = exchange.getResponseBody();
		os.write(fire.getBytes());
		os.close();
	}

}
