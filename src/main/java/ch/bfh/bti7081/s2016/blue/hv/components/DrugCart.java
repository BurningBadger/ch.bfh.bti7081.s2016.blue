package ch.bfh.bti7081.s2016.blue.hv.components;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.entities.*;
import ch.bfh.bti7081.s2016.blue.hv.model.DrugOrderModel;
import ch.bfh.bti7081.s2016.blue.hv.model.DrugsModel;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by kerberos on 10/06/16.
 */
public class DrugCart extends HorizontalLayout {

    private static final long serialVersionUID = 6414800929293891007L;
    private Patient patient;
    private List<DrugOrderItem> items;
    private String remarks;
    private Table cartTable = new Table();
    private int cartSize;
    private static final int BASE_AMOUNT = 1;


    public DrugCart(Patient patient, DrugOrder order){ //use null as second arg to generate an empty cart
        this.patient = patient;
        items = new ArrayList<DrugOrderItem>();
        cartSize = 0;

        HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
        DrugOrderModel drugOrderModel = new DrugOrderModel();
        DrugsModel drugsModel = new DrugsModel();

        VerticalLayout leftPanel = new VerticalLayout();
        final Table drugTable = new Table();
        drugTable.addStyleName("components-inside");

        drugTable.addContainerProperty("Drug Name", Label.class, null);
        drugTable.addContainerProperty("GTIN", Label.class, null);
        drugTable.addContainerProperty("Description", Label.class, null);
        drugTable.addContainerProperty("Amount", TextField.class, null);
        drugTable.addContainerProperty("", Button.class, null);

        VerticalLayout rightPanel = new VerticalLayout();
        cartTable.addStyleName("components-inside");
        rightPanel.setImmediate(true);

        cartTable.addContainerProperty("Drug name", Label.class, null);
        cartTable.addContainerProperty("Amount", TextField.class, null);
        cartTable.addContainerProperty("", Button.class, null);

        for(Drug d : drugsModel.findAll()){
            Label drugName = new Label(d.getName());
            Label gtin = new Label(Integer.toString(d.getGtin()));
            Label drugDescription = new Label(d.getDescription());
            ObjectProperty<Integer> amountProperty = new ObjectProperty<Integer>(BASE_AMOUNT);
            TextField drugAmount = new TextField(amountProperty);
            drugAmount.setImmediate(true);
            drugAmount.setWidth("50px");
            Button addBtn = new Button();
            addBtn.setIcon(FontAwesome.CART_PLUS);

            DrugItemWrapper diw = new DrugItemWrapper(d,drugAmount);
            //ToDo: foreach DrugOrderItem set DrugOrder
            addBtn.setData(diw);
            addBtn.addClickListener(event -> {
                DrugItemWrapper drugItemWrapper = (DrugItemWrapper)event.getButton().getData();
                DrugOrderItem drugItem = drugItemWrapper.getItem();
                addItem(drugItem);
            });

            // Create the table rows
            drugTable.addItem(
                    new Object[] { drugName, gtin, drugDescription, drugAmount, addBtn }, d.getId());
        }


        leftPanel.addComponent(drugTable);

        this.addComponent(leftPanel);

        rightPanel.addComponent(cartTable);
        this.addComponent(rightPanel);

        if(order != null){
            populate(order);
        }
    }

    public synchronized void addItem(DrugOrderItem drugOrderItem){
        boolean newItem = true;

        for (DrugOrderItem i : items) {
            if (i.getDrug().getGtin() == drugOrderItem.getDrug().getGtin()) {
                newItem = false;
                i.setQuantity(i.getQuantity() + drugOrderItem.getQuantity());
                ((TextField)cartTable.getItem(drugOrderItem.getDrug().getGtin()).getItemProperty("Amount").getValue()).setValue(Integer.toString(i.getQuantity()));
            }
        }
        if (newItem) { //ToDo: fix Entityservice so that patients have drugs
//            if(!drugOrderItem.getDrug().isPrescribed(patient)){
//                Notification notif = new Notification("This drug is not prescribes to this patient.", Notification.Type.WARNING_MESSAGE);
//                notif.setDelayMsec(2000);
//                notif.setPosition(Position.MIDDLE_CENTER);
//                notif.show(Page.getCurrent());
//                return;
//            }
            items.add(drugOrderItem);
            Label drugName = new Label(drugOrderItem.getName());
            ObjectProperty<Integer> amountProperty = new ObjectProperty<Integer>(drugOrderItem.getQuantity());
            TextField drugAmount = new TextField(amountProperty);
            drugAmount.setImmediate(true);
            drugAmount.setWidth("50px");
            drugAmount.setData(drugOrderItem.getDrug().getGtin());
            drugAmount.addTextChangeListener(e -> {
                    String value = e.getText();
                    if (tryParseInt(value)) {
                        for (DrugOrderItem d : items) {
                            if (d.getDrug().getGtin() == (int)((TextField)e.getSource()).getData() ) {
                                d.setQuantity(Integer.parseInt(e.getText()));
                                break;
                            }
                        }
                    }

            });
            Button rmvBtn = new Button();
            rmvBtn.setIcon(FontAwesome.REMOVE);
            rmvBtn.setData(drugOrderItem.getDrug().getGtin());
            rmvBtn.addClickListener(event -> {
                for(DrugOrderItem d : items) {
                    if(d.getDrug().getGtin() == (int)event.getButton().getData()) {
                        items.remove(d);
                        break;
                    }
                }
                cartTable.removeItem(event.getButton().getData());
            });
            cartTable.addItem(new Object[] { drugName, drugAmount, rmvBtn }, drugOrderItem.getDrug().getGtin());
        }
    }


    public synchronized List<DrugOrderItem> getItems(){
        return items;
    }

    public synchronized int getNumberOfItems(){
        cartSize = 0;
        for (DrugOrderItem i : items){
            cartSize += i.getQuantity();
        }
        return cartSize;
    }

    public synchronized void emptyCart(){
        items.clear();
        cartSize = 0;
        cartTable.removeAllItems();
    }

    public DrugOrder generateDrugOrder() {
        if (getNumberOfItems() > 0) {
            DrugOrder drugOrder = new DrugOrder();
            drugOrder.setPatient(patient);
            drugOrder.setDrugs(new HashSet<DrugOrderItem>(getItems()));
            drugOrder.setRemarks(remarks);
            return drugOrder;
        } else {
            return null;
        }
    }

    private void populate(DrugOrder order){
        for(DrugOrderItem  item : order.getDrugOrderItems()){
            addItem(item);
        }
        remarks = order.getRemarks();
    }

    private class DrugItemWrapper{
        private Drug drug;
        private TextField textField;
        public DrugItemWrapper(Drug d, TextField t){
            drug = d;
            textField = t;
        }
        public DrugOrderItem getItem(){
            DrugOrderItem item = new DrugOrderItem();
            item.setDrug(drug);
            item.setQuantity(Integer.parseInt(textField.getValue()));
            return item;
        }
    }

    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
