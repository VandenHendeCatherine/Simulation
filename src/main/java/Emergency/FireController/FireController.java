package Emergency.FireController;

import Emergency.View.JSonUtils;
import Emergency.View.ViewController;
import Emergency.View.getCamion;
import Emergency.View.getFire;
import com.sun.xml.fastinfoset.tools.FI_SAX_Or_XML_SAX_SAXEvent;
import org.json.JSONObject;

import java.time.Instant;
import java.util.*;

public class FireController {

	private List<Fire> fires;
	private List<Capteur> sensors;
	private Fire currentFire;
	private ViewController viewController;
	private int maxIntensity = 9;
	private List<Camion> camions;
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

	public void setCamions(List<Camion> camions) {
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

		double x = 0;
		double y = 0;
		int randomIntensity = 0;
		for(Capteur sensor : sensors){
			x += (10.0 - sensor.getIntensity())*sensor.getX();
			y += (10.0 - sensor.getIntensity())*sensor.getY();
			randomIntensity += sensor.getIntensity();
		}
		x = Math.abs(x/(40.0-randomIntensity));
		y = Math.abs(y/(40.0-randomIntensity));
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
				if(Objects.equals(sensor.getId(), sensor1.getId())){
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

	public void camionManagement(){
		while(true){
			for(Intervention intervention: interventions){
			//	deplaceCamion(intervention.getCamion(), intervention.getIdFeu());
			}
		}

	}

	public void deplaceCamion(List<Camion> camions, Fire fire){
		for(Camion camion: camions){
			//trajectoire(camion.getCaserne(),fire,camion);
		}
	}

	public void trajectoire(Caserne caserne, Fire fire, Camion camion){
		double X = camion.getPositionXCamion() +(Math.sqrt(Math.pow((fire.getPositionXFeu() - caserne.getPositionXCaserne()), 2)/Math.pow((fire.getPositionYFeu() - caserne.getPositionYCaserne()), 2)))/10;
		double Y = X*(fire.getPositionXFeu() -caserne.getPositionXCaserne())/(fire.getPositionYFeu() - caserne.getPositionYCaserne())+fire.getPositionXFeu();
		camion.setPositionXCamion(X);
		camion.setPositionYCamion(Y);
	}
	public void gatherCapteurToCreateFire(FireController fireController, getCamion getCamion, getFire getFire, List<Capteur> capteurs, Fire[] fire, List<Camion> camions) {
		if(capteurs.size()==4) {
			fire[0] = fireController.processFire(capteurs);

			JSONObject fireString = JSonUtils.buildJSonFire(fire[0]);
			System.out.println("Fiiiire " + fireString);
			getFire.setFire(fireString.toString());


			camions.get(1).setPositionXCamion(fire[0].getPositionXFeu()+0.02);
			camions.get(1).setPositionYCamion(fire[0].getPositionYFeu()+0.05);

			//Building JSON
			JSONObject jsonObject = JSonUtils.buildJSonCamions(camions);

			//Server requests update
			getCamion.setSensorsList(jsonObject.toString());
			capteurs.clear();
		}
	}
}
