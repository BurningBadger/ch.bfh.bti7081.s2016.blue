package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.Cart;
import ch.bfh.bti7081.s2016.blue.hv.entities.DrugOrder;
import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;

import java.util.logging.Logger;

/**
 * Created by kerberos on 04/06/16.
 */
public class DrugOrderModel extends BaseModel<DrugOrder, Long> {
    private static final long serialVersionUID = -1857636223717022166L;

    private final static Logger LOGGER = Logger.getLogger(DrugOrderModel.class.getName());

    public DrugOrderModel() { super(DrugOrder.class);
    }

    public boolean placeOrder(Cart cart){
        boolean orderSuccessful = false;
        try {

            if (cart.getNumberOfItems() != 0){

                // Send Order via Mail

                // Persist DrugOrder
                if (saveOrUpdate(cart.getDrugOrder(new Patient()))){
                    orderSuccessful = true;
                }
            }

        } catch (Exception e){
            //implement
        }
        return orderSuccessful;
    }



}
