package Emergency.FireController;

import Emergency.View.ViewController;
import com.sun.xml.fastinfoset.tools.FI_SAX_Or_XML_SAX_SAXEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FireController {

	private List<Fire> fires;
	private List<Capteur> sensors;
	private Fire currentFire;
	private ViewController viewController;
	private int maxIntensity = 9;
	private List<Emergency.FireController.Camion> camions;
	private List<Caserne> casernes;
	private List<Intervention> interventions;

	public List<Caserne> getCasernes() {
		return casernes;
	}

	public void setCasernes(List<Caserne> casernes) {
		this.casernes = casernes;
	}

	public List<Camion> getCamion() {
		return camions;
	}

	public void setCamion(List<Camion> camionInDataBase) {
		this.camions = camionInDataBase;
	}

	public List<Camion> getCamions() {
		return camions;
	}

	public void setCamions(List<Emergency.FireController.Camion> camions) {
		this.camions = camions;
	}

	public FireController(){

		interventions = new ArrayList<>();
		camions = new ArrayList<>();
		casernes = new ArrayList<>();
		fires =new ArrayList<>();

	}
	public FireController(List<Capteur> sensors, ViewController viewController){
		this.sensors = sensors;
		this.viewController = viewController;
		interventions = new ArrayList<>();
		camions = new ArrayList<>();
		casernes = new ArrayList<>();
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

		double x =0;
		double y = 0;
		int randomIntensity = 0;
		for(Capteur sensor : sensors){
			x += sensor.getX();
			y += sensor.getY();
			randomIntensity += sensor.getIntensity();
		}
		x = Math.abs((sensors.get(0).getX()-(x/sensors.size()*2)));
		y = Math.abs((sensors.get(0).getY()-(y/sensors.size()*2)));

		randomIntensity = randomIntensity/sensors.size();
		Date date = new Date();
		Fire fire= new Fire(date, x, y, randomIntensity);
		System.out.println("Capteurs : " + sensors);
		System.out.println("Fire created :" + fire +"\n");
		return fire;


	}

	public Fire addFire(Fire fire){

		fire.setId(fires.size()+1);
		fires.add(fire);
		return fire;
	}

	/**
	 * attribute the new intensity of each sensor from the view
	 */
	public List<Capteur> attributeNewIntensity(List<Capteur> sensors) {
		for(int i = 0; i < this.sensors.size(); i++){
			Capteur sensor = this.sensors.get(i);
			for(Capteur sensor1 : sensors){
				if(sensor.getId() == sensor1.getId()){
					sensor1.setX(sensor.getX());
					sensor1.setY(sensor.getY());
					this.sensors.get(i).setIntensity(sensor1.getIntensity());
				}
			}

		}
		return sensors;
	}

	public Fire processFire(List<Capteur> capteurs){
		List<Capteur> sensorsList = attributeNewIntensity(capteurs);
		Fire fire = calculatePositionFire(sensorsList);
		addFire(fire);
		Intervention intervention =  new Intervention(interventions.size()+1, fire,new Date().toInstant());
		interventions.add(intervention);
		System.out.println("Intervention :" + interventions + "\n");
		return  fire;

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
