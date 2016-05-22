package ch.bfh.bti7081.s2016.blue.hv.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name = "Patients")
public class Patient extends Person {

    @ManyToMany(mappedBy = "patients")
    private Set<HealthVisitor> visitors;

    @OneToMany(mappedBy = "patient")
    private Set<Visit> visits;

    @OneToMany(mappedBy = "patient")
    private Set<Drug> drugs;

    public Set<Drug> getDrugs() {
	return drugs;
    }

    public void setDrugs(Set<Drug> drugs) {
	this.drugs = drugs;
    }

    public Set<HealthVisitor> getVisitors() {
	return visitors;
    }

    public void setVisitors(Set<HealthVisitor> visitors) {
	this.visitors = visitors;
    }

    public Set<Visit> getVisits() {
	return visits;
    }

    public void setVisits(Set<Visit> visits) {
	this.visits = visits;
    }
}