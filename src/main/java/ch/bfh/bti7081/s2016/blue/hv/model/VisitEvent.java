package ch.bfh.bti7081.s2016.blue.hv.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "visit_events")
public class VisitEvent extends BaseEntity {
	
	private static final long serialVersionUID = -3616824349353093546L;
	
	@ManyToOne
	private Visit visit;
	
	public Visit getVisit() {
		return visit;
	}

	public void setEvents(Visit visit) {
		this.visit = visit;
	}

}
