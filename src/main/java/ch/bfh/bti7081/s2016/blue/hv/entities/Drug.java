package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "drugs")
public class Drug extends BaseEntity {

    private static final long serialVersionUID = -838966460652903682L;

    @Column(name = "name", length = 255, nullable = false)
    @Size(min=1, max=100)
    private String name;

    @Column(nullable = false, length = 800)
    @Size(min = 0, max = 800)
    private String description;

    @ManyToOne
    private Patient patient;

    @OneToMany(mappedBy = "drug")
    private Set<Prescription> prescriptions;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Patient getPatient() {
	return patient;
    }

    public void setPatient(Patient patient) {
	this.patient = patient;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
