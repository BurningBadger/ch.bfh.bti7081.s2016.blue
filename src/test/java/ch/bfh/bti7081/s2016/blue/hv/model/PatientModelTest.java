package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.Contact;
import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * JUnit test for the PatientModel class
 */

public class PatientModelTest {

    PatientModel patientModel = new PatientModel();

    /**
     * Test: save patient
     */
    @Ignore
    @Test
    public void testSave() {

	// given
	Patient patient = new Patient();
	Contact contact = new Contact();
	contact.setCity("x");
	contact.setStreet("xx");
	contact.setZip("3000");
	contact.setPhoneNumber("123456789");

	patient.setId(Long.valueOf(1500));
	patient.setContact(contact);
	patient.setFirstname("Test");
	patient.setLastname("Test2");
	patient.setBirthday(java.sql.Date.valueOf(LocalDate.of(2000, Month.APRIL, 7)));

	// when
	patientModel.saveOrUpdate(patient);

	// then
	Assert.assertEquals("Test", patientModel.findById(patient.getId()).getFirstname());
	Assert.assertEquals("Test2", patientModel.findById(patient.getId()).getLastname());
	Assert.assertEquals(java.sql.Date.valueOf(LocalDate.of(2000, Month.APRIL, 7)),
			patientModel.findById(patient.getId()).getBirthday());
    }

    /**
     * Test: get the patient data
     */
    @Ignore
    @Test
    public void getPatient() {

	// given
	testSave();	//use the testSave() method
	Long l = 1500l;

	// when
	Patient p = patientModel.findById(l);

	// then
	Assert.assertEquals("Test", p.getFirstname());
	Assert.assertEquals("Test2", p.getLastname());
    }

    /**
     * Test: get all patients
     */
    @Ignore
    @Test
    public void showAllPatients() {

	List<Patient> patients = patientModel.findAll();

	for (Patient p : patients) {
	    System.out.println(p.getFirstname() + " " + p.getLastname() +
			    " " + p.getId());
	}
    }
}
