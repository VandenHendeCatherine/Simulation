package Simulation.FireController;

import javax.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Feu")
public class Fire {

	@GeneratedValue
	@Column(name = "idFeu")
	@Id
	private int id;

	@Column(name = "dateFeu")
	private java.sql.Date day;

	@Column(name="heureFeu")
	private java.sql.Time hour;

	private Date date;
	@Column(name="positionXFeu")
	private double positionX;

	@Column(name="positionYFeu")
	private double positionY;

	@Column(name="intensiteMax")
	private int intensityMax;

	public Fire() {
	}

	public Fire(Date date, double positionX, double positionY, int intensityMax){
		this.date = date;
		this.day = new java.sql.Date(date.getTime());
		this.hour = new Time(date.getTime());
		this.positionX =positionX;
		this.positionY = positionY;
		this.intensityMax = intensityMax;
	}

	public Integer getId(){
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getHour() {
		return hour;
	}

	public void setHour(Time hour) {
		this.hour = hour;
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public int getIntensityMax() {
		return intensityMax;
	}

	public void setIntensityMax(int intensityMax) {
		this.intensityMax = intensityMax;
	}

	@Override
	public String toString() {
		return  positionX + "-" + positionY + "-" + intensityMax;
	}
}
