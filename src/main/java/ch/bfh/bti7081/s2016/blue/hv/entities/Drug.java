package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "Drug")
@Table(name = "drugs")
public class Drug extends BaseEntity {

    private static final long serialVersionUID = -838966460652903682L;

    @Column(name = "name", length = 255, nullable = false)
    @Size(min=1, max=100)
    private String name;

    @Column(unique = true)

    @Size(min=1, max = 14)
    private int gtin; // GTIN: Global Trade Item Number

    @Column(nullable = false, length = 800)
    @Size(min = 0, max = 800)
    private String description;
    
    @ManyToMany(mappedBy = "drugs", fetch = FetchType.EAGER)
    private Set<Patient> patients;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Set<Patient> getPatients() { return patients; }

    public void setPatients(Set<Patient> patients) { this.patients = patients; }

    public int getGtin() { return gtin; }

    public void setGtin(int gtin) { this.gtin = gtin; }

    public boolean isPrescribed(Patient patient){
        boolean prescribed = false;
        for(Patient p : patients){
            if (patient.getId()==p.getId()){
                prescribed = true;
                break;
            }
        }
        return prescribed;
    }
}
