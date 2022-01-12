package Emergency.View;

import Emergency.FireController.Capteur;
import Emergency.FireController.Fire;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;

public class JSonUtils {


		@SuppressWarnings("unchecked")
		public static Capteur readJSonSensor(String jsonObject1) throws ParseException {
			JSONObject jsonObject = new JSONObject(jsonObject1);
			JSONArray jsonArray = (JSONArray) jsonObject.get("capteurs");
			JSONObject sensor = (JSONObject) jsonArray.get(0);
			// getting id and intensity
			Integer id = (Integer) sensor.get("id");
			Integer intensity = (Integer) sensor.get("intensity");

			Capteur newSensor = new Capteur();
			newSensor.setId(id);
			newSensor.setIntensity(intensity);
			System.out.println(id);
			System.out.println(intensity);
			return newSensor;
		}

	public static Capteur readJSonFire(JSONObject jsonObject){

		JSONArray jsonArray = (JSONArray) jsonObject.get("capteurs");
		JSONObject sensor = (JSONObject) jsonArray.get(0);
		// getting id and intensity
		Integer id = (Integer) sensor.get("id");
		Integer intensity = (Integer) sensor.get("intensity");

		Capteur newSensor = new Capteur();
		newSensor.setId(id);
		newSensor.setIntensity(intensity);
		System.out.println(id);
		System.out.println(intensity);
		return newSensor;
	}


	public static JSONObject buildJSonSensors(List<Capteur> sensors)  {
		JSONObject jsonObject = new JSONObject();
		for(Capteur sensor: sensors){
			JSONObject sensorJSon = new JSONObject();
			sensorJSon.put("intensity", sensor.getIntensity());
			sensorJSon.put("id", sensor.getId());
			jsonObject.append("capteurs", sensorJSon);
		}
		return jsonObject ;
	}
	public static JSONObject buildJSonFire(Fire fire)  {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", fire.getId());
		jsonObject.put("intensity", fire.getIntensiteMax());
		jsonObject.put("positionX", fire.getPositionXFeu());
		jsonObject.put("positionY", fire.getPositionYFeu());
		JSONObject jsonObjectFire = new JSONObject();
		jsonObjectFire.append("feux", jsonObject);
		return jsonObjectFire ;
	}
	}

