package ch.bfh.bti7081.s2016.blue.hv.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "health_visitors")
public class HealthVisitor extends BaseEntity {

	private static final long serialVersionUID = -4737237051107455291L;

	@Column(name = "first_name", length = 255, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 255, nullable = false)
	private String lastName;

	@Column(name = "user_name", length = 255, nullable = false)
	private String userName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
