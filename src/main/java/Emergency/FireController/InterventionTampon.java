package Emergency.FireController;

public class InterventionTampon {

	private CamionCaserne camionCaserne;
	private Intervention intervention;

	public CamionCaserne getCamionCaserne() {
		return camionCaserne;
	}

	public void setCamionCaserne(CamionCaserne camionCaserne) {
		this.camionCaserne = camionCaserne;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public InterventionTampon(CamionCaserne camionCaserne, Intervention intervention) {
		this.camionCaserne = camionCaserne;
		this.intervention = intervention;
	}

	@Override
	public String toString() {
		return "InterventionTampon{" + "camionCaserne=" + camionCaserne + ", intervention=" + intervention + '}';
	}
}
