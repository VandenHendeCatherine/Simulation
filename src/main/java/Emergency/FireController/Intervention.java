package Emergency.FireController;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "intervention")
public class Intervention {
	@Id
	@Column(name = "idIntervention",
	        nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "idFeu")
	private Fire idFeu;

	@Column(name = "heureArrivee")
	private Instant heureArrivee;

	@Column(name = "heureFin")
	private Instant heureFin;

	@Column(name = "dateFin")
	private LocalDate dateFin;

	public Intervention(Integer id, Fire idFeu, Instant heureArrivee) {
		this.id = id;
		this.idFeu = idFeu;
		this.heureArrivee = heureArrivee;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public Instant getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(Instant heureFin) {
		this.heureFin = heureFin;
	}

	public Instant getHeureArrivee() {
		return heureArrivee;
	}

	public void setHeureArrivee(Instant heureArrivee) {
		this.heureArrivee = heureArrivee;
	}

	public Fire getIdFeu() {
		return idFeu;
	}

	public void setIdFeu(Fire idFeu) {
		this.idFeu = idFeu;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Intervention{" + "id=" + id + ", idFeu=" + idFeu + ", heureArrivee=" + heureArrivee + ", heureFin=" + heureFin + ", dateFin=" + dateFin + '}';
	}
}