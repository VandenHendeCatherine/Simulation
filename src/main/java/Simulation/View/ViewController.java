package Simulation.View;

import Simulation.FireController.Fire;
import Simulation.FireController.Sensor;
import java.util.*;
import java.util.stream.Collectors;

public class ViewController {

	private List<Fire> fire;
	private List<Sensor> sensors;
	private int[] sensorsIntensity;

	public ViewController(){

	}
	public ViewController(List<Fire> fire, List<Sensor> sensors){
		this.fire = fire;
		this.sensors = sensors;

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

}

