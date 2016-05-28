package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import java.util.List;

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
