package Emergency.FireController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CamionCaserne {

	private Caserne caserne;
	private Map<Camion, Boolean> camions;

	public Map<Camion, Boolean> getMapCamionBool(){
		return camions;
	}
	public void setCamions(Map<Camion, Boolean> camions) {
		this.camions = camions;
	}

	public List<Camion> getCamions() {
		return new ArrayList<>(camions.keySet());
	}

	public void setCamions(List<Camion> camions) {
		for(Camion camion: camions){
			this.camions.put(camion, false);
		}
	}

	public Caserne getCaserne() {
		return caserne;
	}

	public void setCaserne(Caserne caserne) {
		this.caserne = caserne;
	}


	public CamionCaserne(Caserne caserne, List<Camion> camion) {
		this.caserne = caserne;
		camions = new HashMap<>();
		setCamions(camion);
	}

	@Override
	public String toString() {
		return "CamionCaserne{" + "caserne=" + caserne + ", camions=" + camions + '}';
	}
}
