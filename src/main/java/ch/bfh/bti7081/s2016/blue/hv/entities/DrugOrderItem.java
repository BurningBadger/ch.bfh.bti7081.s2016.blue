package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * The DrugOrderItem is a wrapper class for Drug objects to be added to a DrugOrder.
 * It inherits the basic fields and methods from the BaseEntity class.
 * DrugOrderItems are persisted with their corresponding DrugOrder (cascade all).
 *
 * @author Michel Hosmann
 */
@Entity
public class DrugOrderItem extends BaseEntity{

    /**
     * Class variables
     */
    private static final long serialVersionUID = 4880752889983169522L;

    @Valid
    @OneToOne(optional = false)
    private Drug drug;

    @Valid
    @ManyToOne
    private DrugOrder drugOrder;

    @Column(length = 40)
    @Size(min = 1,max = 4)
    private int quantity;

    @Column
    private boolean isPrescribed; //not implemented

    public Drug getDrug() { return drug; }

    public void setDrug(Drug drug) { this.drug = drug; }

    public DrugOrder getDrugOrder() { return drugOrder; }

    public void setDrugOrder(DrugOrder drugOrder) { this.drugOrder = drugOrder; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * Get the name of the Drug wrapped in this object.
     * @return the Drug name
     */
    public String getName(){ return this.getDrug().getName(); }

}
