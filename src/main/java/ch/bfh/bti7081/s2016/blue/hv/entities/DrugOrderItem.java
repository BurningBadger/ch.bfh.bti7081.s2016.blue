package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Created by kerberos on 06/06/16.
 */
@Entity
public class DrugOrderItem extends BaseEntity{

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
    private boolean isPrescribed;

    public Drug getDrug() { return drug; }

    public void setDrug(Drug drug) { this.drug = drug; }

    public DrugOrder getDrugOrder() { return drugOrder; }

    public void setDrugOrder(DrugOrder drugOrder) { this.drugOrder = drugOrder; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public boolean isPrescribed() { return this.drug.isPrescribed(this.drugOrder.getPatient()); }

    public String getName(){ return this.getDrug().getName(); }

}
