package Simulation.FireController;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "capteur")
public class Capteur {
	@Id
	@Column(name = "id",
	        nullable = false)
	private Integer id;

	@Column(name = "x",
	        nullable = false,
	        precision = 4,
	        scale = 2)
	private BigDecimal x;

	@Column(name = "y",
	        nullable = false,
	        precision = 5,
	        scale = 2)
	private BigDecimal y;

	@Column(name = "intensity",
	        nullable = false)
	private Integer intensity;

	public Capteur(){

	}
	public Capteur(Integer id, Integer intensity){
		this.id= id;
		this.intensity =intensity;
	}

	public Integer getIntensity() {
		return intensity;
	}

	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}

	public Double getY() {
		return y.doubleValue();
	}

	public void setY(double y) {
		this.y =new BigDecimal(Float.toString((float) y));
	}

	public Double getX() {
		return x.doubleValue();
	}

	public void setX(double x) {
		this.x = new BigDecimal(Float.toString((float) x));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toString(){
		return id*4.5 + "-" + intensity*3;
	}
}