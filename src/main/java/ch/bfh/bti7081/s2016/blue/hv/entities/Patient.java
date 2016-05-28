package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.util.Set;

import javax.persistence.*;

@Entity(name = "Patients")
public class Patient extends Person {

    @ManyToMany(mappedBy = "patients", fetch = FetchType.EAGER)
    private Set<HealthVisitor> visitors;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private Set<Visit> visits;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private Set<Drug> drugs;

    @OneToOne(optional = false)
    @JoinColumn(name = "emergency_contact_id", unique = true)
    private EmergencyContact emergencyContact;

    public EmergencyContact getEmergencyContact() {
	return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
	this.emergencyContact = emergencyContact;
    }

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