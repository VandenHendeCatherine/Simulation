package Simulation.FireController;
import javax.persistence.*;

@Entity
@Table(name = "Capteur")
public class Sensor {

	@GeneratedValue
	@Column(name = "idSensor")
	@Id
	private int id;

	@Column(name="positionXCapteur")
	private double positionX;

	@Column(name="positionYCapteur")
	private double positionY;
	private int intensity;

	public Sensor() {
	}

	public Sensor(int id,  int intensity) {
		this.id = id;
		this.intensity = intensity;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	@Override
	/*public String toString() {
		return "Sensor : " + "id=" + id + ", positionX=" + positionX + ", positionY=" + positionY + '}';
	}*/

	public String toString(){
		return id*4.5 + "-" + intensity*3;
	}
}
