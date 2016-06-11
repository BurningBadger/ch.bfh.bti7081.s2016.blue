package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;

@Entity(name = "Patient")
@Table(name = "Patients")
public class Patient extends Person {

    private static final long serialVersionUID = 8023922743116129612L;

    @OneToOne(mappedBy = "patient")
    private Visit visit;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private Set<Drug> drugs;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<DrugOrder> drugOrders;

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

    public Visit getVisit() {
	return visit;
    }

    public void setVisit(Visit visit) {
	this.visit = visit;
    }

    public Set<DrugOrder> getDrugOrders() { return drugOrders; }

    public void setDrugOrders(Set<DrugOrder> drugOrders) { this.drugOrders = drugOrders; }
}