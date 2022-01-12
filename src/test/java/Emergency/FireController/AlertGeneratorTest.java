package Emergency.FireController;



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
		assertTrue(fire.getPositionX()<=maxPositionX);
		assertTrue(fire.getPositionX()>=minPositionX);
		assertTrue(fire.getPositionY()<=maxPositionY);
		assertTrue(fire.getPositionY()>=minPositionY);
		assertTrue(fire.getIntensityMax()<=maxIntensity);
		assertTrue(fire.getIntensityMax()>=0);
	}
}