package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.Drug;
import ch.bfh.bti7081.s2016.blue.hv.entities.DrugOrder;
import ch.bfh.bti7081.s2016.blue.hv.entities.DrugOrderItem;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Test class for {@link DrugOrderModel}
 */
public class DrugOrderModelTest {
    @BeforeClass
    public static void setUp(){
    }

    private DrugOrderModel drugOrderModel = new DrugOrderModel();
    private  DrugsModel drugsModel = new DrugsModel();

    private Drug drug;
    private DrugOrderItem item;
    private DrugOrder order;



    private void fillVariables(){
        Random r = new Random();
        this.drug = new Drug();
        drug.setGtin(r.nextInt(99999999));
        drug.setDescription("Description");
        drug.setName("Drug");
        drug.setId((long)12345);

        this.item = new DrugOrderItem();
        item.setDrug(drug);
        item.setQuantity(1);

        this.order = new DrugOrder();
        Set<DrugOrderItem> items = new HashSet<DrugOrderItem>();
        items.add(item);
        order.setDrugs(items);
        order.setRemarks("Remarks");
        order.setId((long)54321);
    }

    @Test
    public void testDrugPersistence(){
        fillVariables();

        int drugsAmount = drugsModel.findAll().size();

        drugsModel.saveOrUpdate(drug);

        Assert.assertEquals(drugsAmount + 1, drugsModel.findAll().size());

        Drug testDrug = drugsModel.findById((long)12345);

        Assert.assertEquals("Drug", testDrug.getName());
        Assert.assertEquals("Description", testDrug.getDescription());
        Assert.assertEquals(drug.getGtin(), testDrug.getGtin());

//        drugsModel.delete(drug);
//        Assert.assertEquals(drugsAmount, drugsModel.findAll().size());
    }

    @Test
    public void testDrugOrderPersistence(){
        fillVariables();

        int drugOrderAmount = drugOrderModel.findAll().size();

        drugOrderModel.saveOrUpdate(order);

        Assert.assertEquals(drugOrderAmount + 1, drugOrderModel.findAll().size());

        DrugOrder testDrugOrder = drugOrderModel.findById((long)54321);

        Assert.assertEquals("Remarks", testDrugOrder.getRemarks());
        Assert.assertEquals(1, testDrugOrder.getDrugOrderItems().size());
        Assert.assertEquals("Drug", testDrugOrder.getDrugOrderItems().iterator().next().getName());

//        drugOrderModel.delete(order);
//        Assert.assertEquals(drugOrderAmount, drugOrderModel.findAll().size());
    }



}
