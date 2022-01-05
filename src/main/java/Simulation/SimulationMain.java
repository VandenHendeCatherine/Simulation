package Simulation;

import Simulation.FireController.AlertGenerator;
import Simulation.FireController.Fire;
import Simulation.FireController.Sensor;
import Simulation.MicroBit.SerialPortCommunication;
import Simulation.View.ViewController;

import java.util.ArrayList;
import java.util.List;

public class SimulationMain { ;

	public static void main(String[] args) throws InterruptedException {

		List<Fire> fires = new ArrayList<>();
		AlertGenerator alertGenerator = new AlertGenerator();
		int i = 0;
		while( i < 10 ){
			fires.add(alertGenerator.generate());
			i++;
		}
		ViewController viewController = new ViewController();
		viewController.setFire(fires);
		System.out.println(fires);

		//test iot
		List<Sensor> sensors = new ArrayList<>();

			sensors.add(new Sensor(1, 1));
			sensors.add(new Sensor(2, 1));
			sensors.add(new Sensor(3, 1));
			sensors.add(new Sensor(4, 1));

			System.out.println(sensors);
			SerialPortCommunication serialPortCommunication = new SerialPortCommunication();
			serialPortCommunication.sendSensorIntensityToComm(sensors);
			serialPortCommunication.closeCommunication();
			sensors.clear();

	}
}
