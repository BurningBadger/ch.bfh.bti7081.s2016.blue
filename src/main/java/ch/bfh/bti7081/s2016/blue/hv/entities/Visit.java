package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;

@Entity(name = "Visit")
@Table(name = "visits")
public class Visit extends BaseEntity {

    private static final long serialVersionUID = -2691308440282309881L;

    @ManyToOne
    private HealthVisitor visitor;
    
    @ManyToMany(mappedBy = "visit", fetch = FetchType.EAGER)
    private Set<HealthVisitor> visitors;

    @ManyToMany(mappedBy = "visit", fetch = FetchType.EAGER)
    private Patient patients;

    @OneToMany(mappedBy = "visit")
    private Set<VisitEvent> visitEvents;
    
    public Set<HealthVisitor> getVisitors() {
	return visitors;
    }

    public void setVisitors(Set<HealthVisitor> visitors) {
	this.visitors = visitors;
    }

    public HealthVisitor getVisitor() {
	return visitor;
    }

    public void setVisitor(HealthVisitor visitor) {
	this.visitor = visitor;
    }

    public Patient getPatient() {
	return patients;
    }

    public void setPatient(Patient patient) {
	this.patients = patient;
    }

    public Set<VisitEvent> getVisitEvents() {
	return visitEvents;
    }

    public void setVisitEvents(Set<VisitEvent> visitEvents) {
	this.visitEvents = visitEvents;
    }
}
