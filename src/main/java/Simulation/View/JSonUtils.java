package Simulation.View;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSonUtils {


		@SuppressWarnings("unchecked")
		public void parse(JSONObject jsonObject) throws ParseException {
			//JSON parser object to parse read file
			JSONParser jsonParser = new JSONParser();


				//Read JSON file
				Object obj = jsonParser.parse(String.valueOf(jsonObject));

				JSONArray employeeList = (JSONArray) obj;
				System.out.println(employeeList);

				//Iterate over employee array
				employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

		}

		private static void parseEmployeeObject(JSONObject employee)
		{
			//Get employee object within list
			JSONObject employeeObject = (JSONObject) employee.get("employee");

			//Get employee first name
			String firstName = (String) employeeObject.get("firstName");
			System.out.println(firstName);

			//Get employee last name
			String lastName = (String) employeeObject.get("lastName");
			System.out.println(lastName);

			//Get employee website name
			String website = (String) employeeObject.get("website");
			System.out.println(website);
		}
	}

