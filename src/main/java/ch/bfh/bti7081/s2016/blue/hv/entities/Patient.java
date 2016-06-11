package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;

@Entity(name = "Patient")
@Table(name = "Patients")
public class Patient extends Person {

	private static final long serialVersionUID = 8023922743116129612L;

	@OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private Set<Visit> visits;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private Set<Drug> drugs;
    
    @Valid
    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST)
    private Set<Prescription> prescriptions;

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

    public Set<Visit> getVisits() {
	return visits;
    }

    public void setVisits(Set<Visit> visits) {
	this.visits = visits;
    }
    
    public Set<Prescription> getPrescriptions() { 
    	return prescriptions; 
    }

    public void setPrescriptions(Set<Prescription> prescriptions) { 
    	this.prescriptions = prescriptions; 
    }
    
}