package Simulation.FireController;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AlertGeneratorTest {
	private double minPositionX = 45.71;
	private double maxPositionX = 45.8;
	private double minPositionY = 4.8;
	private double maxPositionY = 4.9;
	private int maxIntensity = 9;

	@Test
	void generate() {
		AlertGenerator alertGenerator = new AlertGenerator();
		Fire fire = alertGenerator.generate();
		assertTrue(fire.getPositionXFeu()<=maxPositionX);
		assertTrue(fire.getPositionXFeu()>=minPositionX);
		assertTrue(fire.getPositionYFeu()<=maxPositionY);
		assertTrue(fire.getPositionYFeu()>=minPositionY);
		assertTrue(fire.getIntensiteMax()<=maxIntensity);
		assertTrue(fire.getIntensiteMax()>=0);
	}
}