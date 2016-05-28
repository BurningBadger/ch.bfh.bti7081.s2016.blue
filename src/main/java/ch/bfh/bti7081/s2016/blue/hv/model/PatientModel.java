package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.ui.Table;

import java.util.List;
import java.util.Set;

/**
 * Created by uck1 on 28.05.2016.
 */
public class PatientModel extends BaseModel<Patient, Long> {

    private static final long serialVersionUID = -3477963697182614276L;

    public PatientModel() {
	super(Patient.class);
    }

    boolean validate(Patient patient) {
	return false;
    }

    public List<Patient> getAllPatients() {

	List<Patient> patients = this.findAll();
	return patients;
    }
}
