package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * The DrugOrder class stores a list of DrugOrderItems and a remarks String. It is bound to a Patient.
 *
 * @author Michel Hosmann
 */
@Entity(name = "DrugOrder")
@Table(name = "DrugOrders")
public class DrugOrder extends BaseEntity {

    /**
     * Class variables
     */
    private static final long serialVersionUID = -1197336589699356951L;

    @ManyToOne
    private Patient patient;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<DrugOrderItem> drugOrderItems;

    @Column
    private String remarks;

    public Patient getPatient() { return patient; }

    public void setPatient(Patient patient) { this.patient = patient; }

    public Set<DrugOrderItem> getDrugOrderItems() { return drugOrderItems; }

    public void setDrugs(Set<DrugOrderItem> drugOrderItems) { this.drugOrderItems = drugOrderItems; }

    public String getRemarks() { return remarks; }

    public void setRemarks(String remarks) { this.remarks = remarks; }

    /**
     * Iterates through all DrugOrderItems and creates a sum of all item quantities.
     * @return total amount of items in this DrugOrder
     */
    public int getTotalItemsAmount(){
        int orderSize = 0;
        for (DrugOrderItem i : drugOrderItems){
            orderSize += i.getQuantity();
        }
        return orderSize;
    }
}
