package Simulation.FireController;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "feu")
public class Fire {
	@Id
	@Column(name = "idFeu",
	        nullable = false)
	private Integer id;

	@Column(name = "heureAlerte")
	private Instant heureAlerte;

	@Column(name = "dateAlerte")
	private LocalDate dateAlerte;

	@Column(name = "positionXFeu")
	private Double positionXFeu;

	@Column(name = "positionYFeu")
	private Double positionYFeu;

	@Column(name = "intensiteMax")
	private Integer intensiteMax;

	public Fire() {
	}

	public Fire(Date date, double positionX, double positionY, int intensityMax){
		//this.dateAlerte = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
		this.heureAlerte = date.toInstant();
		this.positionXFeu =positionX;
		this.positionYFeu = positionY;
		this.intensiteMax = intensityMax;
	}


	public Integer getIntensiteMax() {
		return intensiteMax;
	}

	public void setIntensiteMax(Integer intensiteMax) {
		this.intensiteMax = intensiteMax;
	}

	public Double getPositionYFeu() {
		return positionYFeu;
	}

	public void setPositionYFeu(Double positionYFeu) {
		this.positionYFeu = positionYFeu;
	}

	public Double getPositionXFeu() {
		return positionXFeu;
	}

	public void setPositionXFeu(Double positionXFeu) {
		this.positionXFeu = positionXFeu;
	}

	public LocalDate getDateAlerte() {
		return dateAlerte;
	}

	public void setDateAlerte(LocalDate dateAlerte) {
		this.dateAlerte = dateAlerte;
	}

	public Instant getHeureAlerte() {
		return heureAlerte;
	}

	public void setHeureAlerte(Instant heureAlerte) {
		this.heureAlerte = heureAlerte;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return  positionXFeu + "-" + positionYFeu + "-" + intensiteMax;
	}
}