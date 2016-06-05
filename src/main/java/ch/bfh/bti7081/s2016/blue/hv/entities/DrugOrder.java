package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.ManyToOne;

/**
 * Created by kerberos on 04/06/16.
 */
public class DrugOrder extends BaseEntity {

    @ManyToOne
    private Patient patient;
}
