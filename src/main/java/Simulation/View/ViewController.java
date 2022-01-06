package Simulation.View;

import Simulation.FireController.Fire;
import Simulation.FireController.Sensor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ViewController {

	private List<Fire> fire;
	private List<Sensor> sensors;
	private int[] sensorsIntensity;

	public ViewController(){

	}
	public ViewController(List<Fire> fire, List<Sensor> sensors) throws IOException {
		this.fire = fire;
		this.sensors = sensors;


		//connexion View
		URL url = new URL("http://localhost:4200/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("GET");
		Map<String, String> parameters = new HashMap<>();
		parameters.put("param1", this.fire.toString());

		con.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
		out.flush();
		out.close();
		String contentType = con.getHeaderField("Content-Type");

		System.out.println(contentType);
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		int status = con.getResponseCode();
		/*BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		System.out.println(content);
		in.close();*/
		con.disconnect();

	}

	public int[] getSensorsIntensity() {
		return sensorsIntensity;
	}

	/**
	 * send the position and intensity of the fires currently burning to the view
	 * @return
	 */
	public Optional<String> sendFireToView(){
		if(!fire.isEmpty()){
			List<String> firesList = fire.stream()
			                             .map(object -> Objects.toString(object, null))
			                             .collect(Collectors.toList());
			return Optional.of(String.join(", ", firesList));
		}
		return Optional.empty();
	}

	/**
	 * get the sensor intensity from the view in an array
	 * @param sensorsIntensity
	 */
	public void getSensorsFromView(int[] sensorsIntensity){
		this.sensorsIntensity = sensorsIntensity;
	}

	public void setFire(List<Fire> fire) {
		this.fire = fire;
	}

	@Override
	public String toString() {
		return "ViewController :" + "fire=" + fire + ", sensors=" + sensors + '}';
	}

	public JSONObject buildJSonSensors(List<Sensor> sensors){
		JSONObject jsonObject = new JSONObject();
		for(Sensor sensor: sensors){
			JSONObject sensorJSon = new JSONObject();
			sensorJSon.put("intensity", sensor.getIntensity());
			sensorJSon.put("id", sensor.getId());
			jsonObject.append("capteurs", sensorJSon);
		}
		return jsonObject ;
	}
}

