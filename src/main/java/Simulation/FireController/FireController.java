package Simulation.FireController;

import Simulation.View.ViewController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FireController {

	private AlertGenerator alertGenerator;
	private List<Fire> fires;
	private List<Capteur> sensors;
	private Fire currentFire;
	private ViewController viewController;
	private int maxIntensity = 9;

	public FireController(){

	}
	public FireController(List<Capteur> sensors, ViewController viewController){
		this.sensors = sensors;
		this.viewController = viewController;
	}

	public List<Fire> getFires() {
		return fires;
	}

	public void setFires(List<Fire> fires) {
		this.fires = fires;
	}

	public List<Capteur> getSensors() {
		return sensors;
	}

	public void setSensors(List<Capteur> sensors) {
		this.sensors = sensors;
	}

	public Fire getCurrentFire() {
		return currentFire;
	}

	public void setCurrentFire(Fire currentFire) {
		this.currentFire = currentFire;
	}

	public ViewController getViewController() {
		return viewController;
	}

	public void setViewController(ViewController viewController) {
		this.viewController = viewController;
	}

	/**
	 *
	 * @return Fire created
	 */
	public Fire calculatePositionFire(List<Capteur> sensors){
		//attributeNewIntensity(sensors);
		//getHigherSensors(sensors);

		double randomX = sensors.get(0).getX();
		double randomY = sensors.get(0).getY();
		int randomIntensity = new Random().nextInt(maxIntensity + 1);
		Date date = new Date();

		return new Fire(date, randomX, randomY, randomIntensity);


	}

	/**
	 * attribute the new intensity of each sensor from the view
	 */
	public List<Capteur> attributeNewIntensity(List<Capteur> sensors) {
		List<Capteur> newSensorList = new ArrayList<>();
		for(int i = 0; i < this.sensors.size(); i++){
			Capteur sensor = this.sensors.get(i);
			for(Capteur sensor1 : sensors){
				if(sensor.getId() == sensor1.getId()){
					sensor1.setX(sensor.getX());
					sensor1.setY(sensor.getY());
					this.sensors.get(i).setIntensity(sensor1.getIntensity());
				}
			}

			newSensorList.add(sensor);
		}
		return newSensorList;
	}

	/**
	 * Return the 3 Sensors with the highest Intensity
	 * @param sensorList
	 */
	private void getHigherSensors(List<Capteur> sensorList) {
		for(int j = 0; j<3; j++){
			Capteur maxSensor = sensorList.get(0);
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
	public List<Capteur> calculatePositionSensor(Fire fire){
		currentFire=fire;
		List<Capteur> sensorList = new ArrayList<>();
		for(Capteur sensor : sensors){
			double x = Math.abs(currentFire.getPositionXFeu() - sensor.getX());
			double y = Math.abs(currentFire.getPositionYFeu() - sensor.getY());
			if((x <= 0.02 ) && ( y <= 0.01)){
				sensorList.add(sensor);
			}
		}
		for(Capteur sensor : sensorList){
			int randomIntensity = new Random().nextInt(maxIntensity + 1);
			sensor.setIntensity(randomIntensity);
		}
		return sensorList;
	}

}
