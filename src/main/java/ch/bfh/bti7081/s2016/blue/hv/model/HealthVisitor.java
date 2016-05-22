package ch.bfh.bti7081.s2016.blue.hv.model;

import javax.persistence.*;
import javax.persistence.Column;
import java.util.Set;

@Entity(name = "health_visitors")
public class HealthVisitor extends Person {

	private static final long serialVersionUID = -4737237051107455291L;

	@Column(name = "user_name", length = 255, nullable = false)
	private String userName;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="Visitors_Patients",
			joinColumns =
			@JoinColumn(name="health_visitor_id"),
			inverseJoinColumns =
			@JoinColumn(name = "patient_id"))
	private Set<Patient> patients;

	@OneToMany(mappedBy = "visitor")
	private Set<Visit> visits;

	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	public Set<Visit> getVisits() {
		return visits;
	}

	public void setVisits(Set<Visit> visits) {
		this.visits = visits;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	// TODO add visits if needed in another iteration.
	// private Set<Visit> visits;

}
