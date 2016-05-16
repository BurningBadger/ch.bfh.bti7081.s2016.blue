package ch.bfh.bti7081.s2016.blue.hv.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "drugs")
public class Drug extends BaseEntity {

	private static final long serialVersionUID = -838966460652903682L;

	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@ManyToOne
	private Customer customer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
