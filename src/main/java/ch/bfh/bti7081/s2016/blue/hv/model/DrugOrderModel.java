package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.entities.DrugOrder;
import ch.bfh.bti7081.s2016.blue.hv.entities.DrugOrderItem;
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

    /**
     * Creates an instance of the DrugOrderModel
     */
    public DrugOrderModel() {
        super(DrugOrder.class);
    }

    /**
     * Sends an Email containing the @param drugOrder and persists the order
     * @param drugOrder the DrugOrder to be sent and persisted
     * @return is true if the mail was sent and the DrugOrder was persisted
     * @throws Exception if something went wrong
     */
    public boolean placeOrder(DrugOrder drugOrder) throws Exception{
        boolean orderSuccessful = false;
        HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
        try {

            if (drugOrder != null && drugOrder.getTotalItemsAmount() > 0){

                // Send Order via Mail
                MailModel.send(visitor.getUserName(), "New Drug Order", generateMailText(drugOrder), "receiver@healthvis.bfh.com");

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

    /**
     * Generates the text body for the order email from the DrugOrder content.
     * @param order the source for the text generation
     * @return the full text body for the email
     */
    private String generateMailText(DrugOrder order){
        HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
        StringBuilder mailText = new StringBuilder();

        mailText.append("Dear medication provider \n \n");
        mailText.append("I would like to place a new order for " + order.getPatient().getFirstname() + " " + order.getPatient().getLastname() + "\n \n");
        mailText.append("Please send the following items to his address: \n \n");
        mailText.append("Amount\tGTIN\tDrug Name\n");

        for(DrugOrderItem item : order.getDrugOrderItems()){
            mailText.append(item.getQuantity() + "\t" + item.getDrug().getGtin() + "\t" + item.getName() + "\n");
        }

        mailText.append("\nThe address is as follows:\n\n");

        mailText.append(order.getPatient().getFirstname() + " " + order.getPatient().getLastname() + "\n");
        mailText.append(order.getPatient().getContact().getStreet() + "\n");
        mailText.append(order.getPatient().getContact().getPhoneNumber() + " " + order.getPatient().getContact().getCity() + "\n\n");

        if(order.getRemarks()!=null){
            mailText.append("Please consider these remarks:\n");
            mailText.append(order.getRemarks() + "\n\n");
        }

        mailText.append("Yours sincerely\n\n");
        mailText.append("\t" +visitor.getFirstname() + " " + visitor.getLastname());

        return mailText.toString();
    }
}
