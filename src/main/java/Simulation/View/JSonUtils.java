package Simulation.View;

import Simulation.FireController.Fire;
import Simulation.FireController.Sensor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class JSonUtils {


		@SuppressWarnings("unchecked")
		public static Sensor readJSonSensor(String jsonObject1) throws ParseException {
			JSONObject jsonObject = new JSONObject(jsonObject1);
			JSONArray jsonArray = (JSONArray) jsonObject.get("capteurs");
			JSONObject sensor = (JSONObject) jsonArray.get(0);
			// getting id and intensity
			Integer id = (Integer) sensor.get("id");
			Integer intensity = (Integer) sensor.get("intensity");

			Sensor newSensor = new Sensor();
			newSensor.setId(id);
			newSensor.setIntensity(intensity);
			System.out.println(id);
			System.out.println(intensity);
			return newSensor;
		}

	public static Sensor readJSonFire(JSONObject jsonObject){

		JSONArray jsonArray = (JSONArray) jsonObject.get("capteurs");
		JSONObject sensor = (JSONObject) jsonArray.get(0);
		// getting id and intensity
		Integer id = (Integer) sensor.get("id");
		Integer intensity = (Integer) sensor.get("intensity");

		Sensor newSensor = new Sensor();
		newSensor.setId(id);
		newSensor.setIntensity(intensity);
		System.out.println(id);
		System.out.println(intensity);
		return newSensor;
	}


	public static JSONObject buildJSonSensors(List<Sensor> sensors)  {
		JSONObject jsonObject = new JSONObject();
		for(Sensor sensor: sensors){
			JSONObject sensorJSon = new JSONObject();
			sensorJSon.put("intensity", sensor.getIntensity());
			sensorJSon.put("id", sensor.getId());
			jsonObject.append("capteurs", sensorJSon);
		}
		//JSonUtils.readJSonSensor(jsonObject);
		return jsonObject ;
	}
	public static JSONObject buildJSonFire(Fire fire)  {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("intensity", fire.getIntensityMax());
		jsonObject.put("positionX", fire.getPositionX());
		jsonObject.put("positionY", fire.getPositionY());
		jsonObject.put("id", fire.getId());
		return jsonObject ;
	}
	}

