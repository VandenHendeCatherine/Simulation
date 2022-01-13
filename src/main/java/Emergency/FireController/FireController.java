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
	private List<InterventionTampon> interventions;
	private JSONObject jsonObject;

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

	public Fire processFire(List<Capteur> capteurs, List<CamionCaserne> camionCasernes){
		List<Capteur> sensorsList = attributeNewIntensity(capteurs);
		Fire fire = calculatePositionFire(sensorsList);
		addFire(fire);
		Intervention intervention =  new Intervention(interventions.size()+1, fire,new Date().toInstant());
		CamionCaserne camionCaserneNeuf = null;
		int i =0;
		for(CamionCaserne camionCaserne : camionCasernes) {
			Map<Camion, Boolean> map = camionCaserne.getMapCamionBool();
			for(Camion camion : map.keySet()) {
				if(!map.get(camion)) {
					camionCaserneNeuf = camionCaserne;
					map.replace(camion, false, true);
					i = 1;
					break;
				}
			}
			if(i!=0){
				break;
			}
		}
		camions.clear();
		for(CamionCaserne camionCaserne : camionCasernes) {
			Map<Camion, Boolean> map = camionCaserne.getMapCamionBool();
			camions.addAll(map.keySet());
		}

		InterventionTampon interventionTampon = new InterventionTampon(camionCaserneNeuf,intervention);
		interventions.add(interventionTampon);
		System.out.println("Intervention :" + interventions + "\n");
		return  fire;

	}

	public JSONObject camionManagement() {
			for(InterventionTampon intervention: interventions){
				deplaceCamion(intervention, intervention.getCamionCaserne().getCamions());
			}
			//Building JSON
			return JSonUtils.buildJSonCamions(camions);

	}

	public void deplaceCamion(InterventionTampon interventionTampon, List<Camion> camions){
		for(Camion camion: camions){
			this.camions.remove(camion);
			this.camions.add(trajectoire(interventionTampon.getCamionCaserne().getCaserne(), interventionTampon.getIntervention().getIdFeu(), camion));
		}

	}

	public Camion trajectoire(Caserne caserne, Fire fire, Camion camion){
		double X = 0;
		double Y = 0;

		double distanceX = (fire.getPositionYFeu() - caserne.getPositionXCaserne())/5;
		double distanceY = (fire.getPositionXFeu() - caserne.getPositionYCaserne())/5;
		if(camion.getPositionXCamion() == null){
			 X = caserne.getPositionXCaserne() + distanceX;
			 Y = caserne.getPositionYCaserne() + distanceY;
		}else
		 if(!Objects.equals(camion.getPositionXCamion(), fire.getPositionYFeu()) || !Objects.equals(camion.getPositionXCamion(), caserne.getPositionXCaserne()) ){
			 Y = camion.getPositionYCamion() + distanceY;
			 X = camion.getPositionXCamion() + distanceX;
		}else{
			 Y = 0;
			 X = 0;
		 }
		camion.setPositionXCamion(X);
		camion.setPositionYCamion(Y);
		System.out.println("Camion " + camion);
		return  camion;
	}
	public void gatherCapteurToCreateFire(FireController fireController, getFire getFire, List<Capteur> capteurs, Fire[] fire, List<CamionCaserne> camionCasernes) {
		if(capteurs.size()==4) {
			fire[0] = fireController.processFire(capteurs, camionCasernes);

			JSONObject fireString = JSonUtils.buildJSonFire(fire[0]);
			System.out.println("Fiiiire " + fireString);
			getFire.setFire(fireString.toString());

			capteurs.clear();
		}
	}
}
