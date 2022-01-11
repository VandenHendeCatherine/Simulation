package Simulation.FireController;

import java.util.Date;
import java.util.Random;

public class AlertGenerator {
	private double minPositionY = 45.71;
	private double maxPositionY = 45.8;
	private double minPositionX = 4.8;
	private double maxPositionX = 4.9;
	private int maxIntensity = 9;
	private int id;

	public AlertGenerator(){
		id = 0;

	}

	public void refreshIdFire(int id){
		this.id =id;
	}
	public Fire generate(){
		double randomX = minPositionX + new Random().nextDouble() * (maxPositionX - minPositionX);
		double randomY = minPositionY + new Random().nextDouble() * (maxPositionY - minPositionY);
		int randomIntensity = new Random().nextInt(maxIntensity + 1);
		Date date = new Date();
		Fire fire =new Fire(date, randomX, randomY, randomIntensity);
		id ++;
		fire.setId(id);
		return fire;
	}

}
