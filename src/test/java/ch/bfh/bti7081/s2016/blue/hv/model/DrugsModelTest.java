package ch.bfh.bti7081.s2016.blue.hv.model;

import java.util.List;

import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import ch.bfh.bti7081.s2016.blue.hv.entities.Drug;

/**
 * Test class for testing the {@link DrugsModel}.
 */
public class DrugsModelTest {

    private DrugsModel drugsModel = new DrugsModel();

    @Ignore
    @Test
    public void cleanUpAfterEveryTest() {
	// cleanup everything to have a fresh start for every test.
	List<Drug> list = drugsModel.findAll();
	for (Drug d : list) {
	    drugsModel.delete(d);
	}
    }

    @Ignore
    @Test
    public void testSave() {

	// given
	Drug drug = new Drug();
	drug.setName("Test Name");
	drug.setDescription("Test Description");

	// when
	drugsModel.saveOrUpdate(drug);

	// then
	Assert.assertEquals(3, drugsModel.findAll().size());
	Assert.assertEquals("Test Name", drugsModel.findAll().get(0).getName());
    }

    @Ignore
    @Test
    public void testFindById() {
	// given
	Drug drug1 = new Drug();
	drug1.setId(Long.valueOf(999));
	drug1.setName("Test Name 1");
	drugsModel.saveOrUpdate(drug1);

	// when
	Drug d = drugsModel.findById(Long.valueOf(999));

	// then
	Assert.assertEquals("Test Name 1", d.getName());
    }

    @Ignore
    @Test
    public void getPatient(){
	Drug drug1 = new Drug();
	drug1.setId(Long.valueOf(999));
	drug1.setName("Test Name 1");
	drug1.setDescription("Test Descr 1");
	drugsModel.saveOrUpdate(drug1);

	Drug d = drugsModel.findById(Long.valueOf(999));
	//Patient patients = d.getPatient();
    }

    @Ignore
    @Test
    public void findAllDrugs(){
	List<Drug> drugs = drugsModel.findAll();

	for(Drug d : drugs){
	    System.out.println(d.getName() + " " + d.getId());
	}
    }
}
