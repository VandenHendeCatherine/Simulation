package Simulation.FireController;

import java.util.Date;
import java.util.Random;

public class AlertGenerator {
	private double minPositionX = 45.71;
	private double maxPositionX = 45.8;
	private double minPositionY = 4.8;
	private double maxPositionY = 4.9;
	private int maxIntensity = 9;
	public AlertGenerator(){

	}

	public Fire generate(){
		double randomX = minPositionX + new Random().nextDouble() * (maxPositionX - minPositionX);
		double randomY = minPositionY + new Random().nextDouble() * (maxPositionY - minPositionY);
		int randomIntensity = new Random().nextInt(maxIntensity + 1);
		Date date = new Date();

		return new Fire(date, randomX, randomY, randomIntensity);
	}
}
