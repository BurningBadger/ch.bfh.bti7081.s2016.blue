package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.entities.DrugOrder;
import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import com.vaadin.ui.UI;

import java.util.logging.Logger;

/**
 * Model for DrugOrders
 *
 * @author Michel Hosmann
 */
public class DrugOrderModel extends BaseModel<DrugOrder, Long> {
    private static final long serialVersionUID = -1857636223717022166L;

    private final static Logger LOGGER = Logger.getLogger(DrugOrderModel.class.getName());
    private HealthVisitor visitor;

    /**
     * Creates an instance of the DrugOrderModel
     */
    public DrugOrderModel() {
        super(DrugOrder.class);
        this.visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
    }

    /**
     * Sends an Email containing the @param drugOrder and persists the order
     * @param drugOrder the DrugOrder to be sent and persisted
     * @return is true if the mail was sent and the DrugOrder was persisted
     * @throws Exception if something went wrong
     */
    public boolean placeOrder(DrugOrder drugOrder) throws Exception{
        boolean orderSuccessful = false;
        try {

            if (drugOrder != null && drugOrder.getTotalItemsAmount() > 0){

                // Send Order via Mail
                MailModel.send(visitor.getUserName(), "New Drug Order", "TEXT", "receiver@healthvis.bfh.com");

                // Persist DrugOrder
                if (saveOrUpdate(drugOrder)){
                    orderSuccessful = true;
                }
            } else {
                throw new Exception("something went wrong.");
            }

        } catch (Exception e){
            throw new Exception(e); //ToDo implement
        }
        return orderSuccessful;
    }



}
