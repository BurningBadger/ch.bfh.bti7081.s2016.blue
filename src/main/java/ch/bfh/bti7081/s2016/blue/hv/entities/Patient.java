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

    @ManyToMany
    @JoinTable(name="PATIENT_DRUG", joinColumns=@JoinColumn(name="PAT_ID", referencedColumnName="ID"),
		    inverseJoinColumns=@JoinColumn(name="DRUG_ID", referencedColumnName="ID")
    )
    private Set<Drug> drugs;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<DrugOrder> drugOrders;

    @OneToOne(optional = false)
    @JoinColumn(name = "emergency_contact_id", unique = true)
    private EmergencyContact emergencyContact;

    @Column(name = "is_active")
    private boolean isActive;

    public EmergencyContact getEmergencyContact() {
	return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
	this.emergencyContact = emergencyContact;
    }

    public Set<Drug> getDrugs() { return drugs; }

    public void setDrugs(Set<Drug> drug) { this.drugs = drug; }

    public Visit getVisit() {
	return visit;
    }

    public void setVisit(Visit visit) {
	this.visit = visit;
    }

    public Set<DrugOrder> getDrugOrders() { return drugOrders; }

    public void setDrugOrders(Set<DrugOrder> drugOrders) { this.drugOrders = drugOrders; }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

}