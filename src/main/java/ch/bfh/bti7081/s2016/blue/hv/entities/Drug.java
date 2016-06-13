package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * The Drug class is a representation of a medication. It contains the Drug name, its GTIN (Global Trade Item Number)
 * and a description.
 * Each Drug can be assigned to several Patients (prescription).
 *
 * @author Michel Hosmann
 */
@Entity(name = "Drug")
@Table(name = "drugs")
public class Drug extends BaseEntity {

    /**
     * Class variables
     */
    private static final long serialVersionUID = -838966460652903682L;

    @Column(name = "name", length = 255, nullable = false)
    @Size(min=1, max=100)
    private String name;

    @Column(unique = true)

    @Size(min=1, max = 14)
    private int gtin; //

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
}
