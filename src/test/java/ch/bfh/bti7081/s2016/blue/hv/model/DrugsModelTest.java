package ch.bfh.bti7081.s2016.blue.hv.model;

import org.junit.Assert;
import org.junit.Test;

import ch.bfh.bti7081.s2016.blue.hv.entities.Drug;

public class DrugsModelTest {

    private DrugsModel drugsModel = new DrugsModel();

    @Test
    public void testSave() {

	// given
	Drug drug = new Drug();
	drug.setName("Test Name");

	// when
	drugsModel.save(drug);

	// then
	Assert.assertEquals(1, drugsModel.findAll().size());
	Assert.assertEquals("Test Name", drugsModel.findAll().get(0).getName());
    }

    @Test
    public void testFindById() {

	// given
	Drug drug1 = new Drug();
	drug1.setName("Test Name 1");
	drugsModel.save(drug1);

	// when
	Drug d = drugsModel.findById(Long.valueOf(1));

	// then
	Assert.assertEquals("Test Name 1", d.getName());
    }
}
