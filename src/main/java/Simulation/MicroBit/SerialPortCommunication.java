package Simulation.MicroBit;

import Simulation.FireController.Sensor;
import com.fazecast.jSerialComm.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @class SerialPortCommunication
 *
 */
public class SerialPortCommunication {

	private SerialPort serialPort;
	private final Integer bauderate = 115200;
	private final String portName = "COM5";

	public SerialPortCommunication(){
		serialPort = SerialPort.getCommPort(portName);
		serialPort.setBaudRate(bauderate);
		serialPort.openPort();
	}

	/**
	 * close the port used to communicate
	 */
	public void closeCommunication(){
		serialPort.closePort();
	}

	/**
	 * prepare and send the message with the sensor id and their intensity via SerialPort
	 * @param sensors
	 */
	public void sendSensorIntensityToComm(List<Sensor> sensors) {
		//Array to String
		List<String> sensorsToString = sensors.stream()
		                                   .map(object -> Objects.toString(object, null))
		                                   .collect(Collectors.toList());
		String command = String.join(", ", sensorsToString);

		//secure data

		byte[] encodedData = Base64.getEncoder().encode(command.getBytes());
		byte[] endOfLine = "\r\n".getBytes();
		byte[] message = new byte[endOfLine.length + encodedData.length ];
		System.arraycopy(encodedData, 0,message,0, encodedData.length);
		System.arraycopy(endOfLine, 0,message, encodedData.length , endOfLine.length);

		//send data
		serialPort.writeBytes(message, message.length);
	}

}
