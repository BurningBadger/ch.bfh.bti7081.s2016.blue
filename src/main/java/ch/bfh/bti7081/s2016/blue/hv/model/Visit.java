package ch.bfh.bti7081.s2016.blue.hv.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "visits")
public class Visit extends BaseEntity {

	private static final long serialVersionUID = -2691308440282309881L;
	
	@OneToOne(mappedBy = "visit")
	private Customer cust;
	
	@OneToMany(mappedBy = "visit")
	private Set<VisitEvent> events;
	
	public Set<VisitEvent> getEvents() {
		return events;
	}

	public void setEvents(Set<VisitEvent> events) {
		this.events = events;
	}

}
