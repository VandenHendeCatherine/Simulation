package Simulation.View;

import Simulation.FireController.Sensor;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSonUtils {


		@SuppressWarnings("unchecked")
		public static Sensor readJSonSensor(JSONObject jsonObject){

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

	}

