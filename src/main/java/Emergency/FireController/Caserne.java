package Emergency.FireController;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Caserne {
	@Id
	@Column(name = "idCaserne",
	        nullable = false)
	private Integer id;

	@Column(name = "nomCaserne",
	        length = 50)
	private String nomCaserne;

	@Column(name = "positionXCaserne")
	private Double positionXCaserne;

	@Column(name = "positionYCaserne")
	private Double positionYCaserne;

	@Column(name = "qttEauCaserne")
	private Double qttEauCaserne;

	@Column(name = "pleinCaserne")
	private Double pleinCaserne;

	public Double getPleinCaserne() {
		return pleinCaserne;
	}

	public void setPleinCaserne(Double pleinCaserne) {
		this.pleinCaserne = pleinCaserne;
	}

	public Double getQttEauCaserne() {
		return qttEauCaserne;
	}

	public void setQttEauCaserne(Double qttEauCaserne) {
		this.qttEauCaserne = qttEauCaserne;
	}

	public Double getPositionYCaserne() {
		return positionYCaserne;
	}

	public void setPositionYCaserne(Double positionYCaserne) {
		this.positionYCaserne = positionYCaserne;
	}

	public Double getPositionXCaserne() {
		return positionXCaserne;
	}

	public void setPositionXCaserne(Double positionXCaserne) {
		this.positionXCaserne = positionXCaserne;
	}

	public String getNomCaserne() {
		return nomCaserne;
	}

	public void setNomCaserne(String nomCaserne) {
		this.nomCaserne = nomCaserne;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}