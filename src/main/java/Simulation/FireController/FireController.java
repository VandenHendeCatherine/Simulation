package Simulation.FireController;

import Simulation.View.ViewController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FireController {

	private AlertGenerator alertGenerator;
	private List<Fire> fires;
	private List<Sensor> sensors;
	private Fire currentFire;
	private ViewController viewController;
	private int maxIntensity = 9;

	public FireController(List<Sensor> sensors, ViewController viewController){
		this.sensors = sensors;
		this.viewController = viewController;
	}
	/**
	 *
	 * @return Fire created
	 */
	public Fire calculatePositionFire(List<Sensor> sensors){
		//attributeNewIntensity(sensors);
		//getHigherSensors(sensors);

		double randomX = sensors.get(0).getPositionX();
		double randomY = sensors.get(0).getPositionY();
		int randomIntensity = new Random().nextInt(maxIntensity + 1);
		Date date = new Date();

		return new Fire(date, randomX, randomY, randomIntensity);


	}

	/**
	 * attribute the new intensity of each sensor from the view
	 */
	private void attributeNewIntensity(List<Sensor> sensors) {
		List<Sensor> newSensorList = new ArrayList<>();
		for(int i = 0; i < this.sensors.size(); i++){
			Sensor sensor = this.sensors.get(i);
			for(Sensor sensor1 : sensors){
				if(sensor.getId() == sensor1.getId()){

					sensor.setIntensity(sensor1.getIntensity());
				}
			}

			newSensorList.add(sensor);
		}
		sensors = newSensorList;
	}

	/**
	 * Return the 3 Sensors with the highest Intensity
	 * @param sensorList
	 */
	private void getHigherSensors(List<Sensor> sensorList) {
		for(int j = 0; j<3; j++){
			Sensor maxSensor = sensorList.get(0);
			for(int i = 1; i< sensorList.size(); i++){
				if(maxSensor.getIntensity() < sensorList.get(i).getIntensity()){
					maxSensor = sensorList.get(i);
				}
			}
			sensors.add(maxSensor);
			sensorList.remove(maxSensor);
		}
	}

	/**
	 *
	 * @param fire
	 * @return
	 */
	public List<Sensor> calculatePositionSensor(Fire fire){
		currentFire=fire;
		List<Sensor> sensorList = new ArrayList<>();
		for(Sensor sensor : sensors){
			if((Math.abs(currentFire.getPositionX() - sensor.getPositionX()) <= 0.1) && (Math.abs(currentFire.getPositionY() - sensor.getPositionY()) <= 0.2)){
				sensorList.add(sensor);
			}
		}
		for(Sensor sensor : sensorList){
			int randomIntensity = new Random().nextInt(maxIntensity + 1);
			sensor.setIntensity(randomIntensity);
		}
		return sensorList;
	}

}
