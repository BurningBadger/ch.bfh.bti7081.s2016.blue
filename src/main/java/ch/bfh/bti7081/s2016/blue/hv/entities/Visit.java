package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "visits")
public class Visit extends BaseEntity {

    private static final long serialVersionUID = -2691308440282309881L;

    @ManyToOne
    private HealthVisitor visitor;

    @ManyToOne
    private Patient patient;

    @OneToMany(mappedBy = "visit")
    private Set<VisitEvent> visitEvents;

    public HealthVisitor getVisitor() {
	return visitor;
    }

    public void setVisitor(HealthVisitor visitor) {
	this.visitor = visitor;
    }

    public Patient getPatient() {
	return patient;
    }

    public void setPatient(Patient patient) {
	this.patient = patient;
    }

    public Set<VisitEvent> getVisitEvents() {
	return visitEvents;
    }

    public void setVisitEvents(Set<VisitEvent> visitEvents) {
	this.visitEvents = visitEvents;
    }

}
