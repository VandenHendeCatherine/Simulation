package Emergency.FireController;

import javax.persistence.*;

@Entity
public class Camion {
	@Id
	@Column(name = "idCamion",
	        nullable = false)
	private Integer id;

	@Column(name = "typeCamion",
	        length = 50)
	private String typeCamion;

	@Column(name = "capaciteCamion")
	private Integer capaciteCamion;

	@Column(name = "positionXCamion")
	private Double positionXCamion;

	@Column(name = "positionYCamion")
	private Double positionYCamion;

	@Column(name = "nbPompier")
	private Integer nbPompier;

	@Column(name = "qttEauCamion")
	private Double qttEauCamion;

	@Column(name = "pleinCamion")
	private Double pleinCamion;


	public Double getPleinCamion() {
		return pleinCamion;
	}

	public void setPleinCamion(Double pleinCamion) {
		this.pleinCamion = pleinCamion;
	}

	public Double getQttEauCamion() {
		return qttEauCamion;
	}

	public void setQttEauCamion(Double qttEauCamion) {
		this.qttEauCamion = qttEauCamion;
	}

	public Integer getNbPompier() {
		return nbPompier;
	}

	public void setNbPompier(Integer nbPompier) {
		this.nbPompier = nbPompier;
	}

	public Double getPositionYCamion() {
		return positionYCamion;
	}

	public void setPositionYCamion(Double positionYCamion) {
		this.positionYCamion = positionYCamion;
	}

	public Double getPositionXCamion() {
		return positionXCamion;
	}

	public void setPositionXCamion(Double positionXCamion) {
		this.positionXCamion = positionXCamion;
	}

	public Integer getCapaciteCamion() {
		return capaciteCamion;
	}

	public void setCapaciteCamion(Integer capaciteCamion) {
		this.capaciteCamion = capaciteCamion;
	}

	public String getTypeCamion() {
		return typeCamion;
	}

	public void setTypeCamion(String typeCamion) {
		this.typeCamion = typeCamion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Camion{" + "id=" + id + ", typeCamion='" + typeCamion + '\'' + ", capaciteCamion=" + capaciteCamion + ", positionXCamion=" + positionXCamion + ", positionYCamion=" + positionYCamion  + '}';
	}
}