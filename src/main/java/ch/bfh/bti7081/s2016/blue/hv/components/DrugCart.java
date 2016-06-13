package ch.bfh.bti7081.s2016.blue.hv.components;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.entities.*;
import ch.bfh.bti7081.s2016.blue.hv.model.DrugOrderModel;
import ch.bfh.bti7081.s2016.blue.hv.model.DrugsModel;
import com.vaadin.data.Validator;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Component implementing a cart for Drug entities to generate a DrugOrder entity.
 * The component extends the vaadin HorizontalLayout component, so it can be used
 * as part in any other vaadin container.
 *
 * @author Michel Hosmann
 */
public class DrugCart extends HorizontalLayout {

    private static final long serialVersionUID = 6414800929293891007L;
    private Table drugTable;
    private ComboBox patientSelect;
    private List<DrugOrderItem> items;
    private TextArea remarks;
    private Table cartTable;
    private int cartSize;
    private static final int BASE_AMOUNT = 1;

    /**
     * Generates a DrugCart instance.
     * @param patient optional parameter to set the patient to receive the order
     * @param order optional parameter to add items and remarks from an existing order to the cart
     */
    public DrugCart(Patient patient, DrugOrder order){ //use null as second arg to generate an empty cart
	/**
	 * Initialization of class variables, models and the current user
         */
        this.drugTable = new Table();
        this.cartTable = new Table();
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        this.patientSelect = new ComboBox("Select Patient", container);
        this.remarks = new TextArea();
        this.items = new ArrayList<DrugOrderItem>();
        this.cartSize = 0;

        HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
        DrugOrderModel drugOrderModel = new DrugOrderModel();
        DrugsModel drugsModel = new DrugsModel();

	/**
	 * Configure and populate the ComboBox for selecting the patient
         */
        patientSelect.setFilteringMode(FilteringMode.CONTAINS);
        patientSelect.setInvalidAllowed(false);
        patientSelect.setNullSelectionAllowed(false);
        for(Patient p : visitor.getPatients()){
            String name = p.getFirstname() + " " + p.getLastname();
            patientSelect.addItem(p);
            patientSelect.setItemCaption(p, name);
        }
        if(patient != null){
            patientSelect.setValue(patient);
        }

	/**
	 * Configure the button which sends the contents of the cart and the remarks
         */
        Button sendOrderBtn = new Button("send order");
        sendOrderBtn.addClickListener(e ->{
            try {
                DrugOrder drugOrder = generateDrugOrder();
                drugOrderModel.placeOrder(drugOrder);
                Notification.show("Order sent successfully.");
                emptyCart();
            } catch (Exception exception){
                Notification.show("Order could not be sent: " + exception.getMessage());
            }

        });

	/**
	 * Configure and populate the table containing the drugs to choose from and add to the cart
         */
        drugTable.addStyleName("components-inside");
        drugTable.setWidth("100%");
        drugTable.setPageLength(12);
        drugTable.addContainerProperty("Drug Name", Label.class, null);
        drugTable.addContainerProperty("GTIN", Label.class, null);
        drugTable.addContainerProperty("Description", Label.class, null);
        drugTable.addContainerProperty("Amount", TextField.class, null);
        drugTable.addContainerProperty("", Button.class, null);
        for(Drug d : drugsModel.findAll()){
            // Labels
            Label drugName = new Label(d.getName());
            Label gtin = new Label(Integer.toString(d.getGtin()));
            Label drugDescription = new Label(d.getDescription());
            // TextField with property
            ObjectProperty<Integer> amountProperty = new ObjectProperty<Integer>(BASE_AMOUNT);
            TextField drugAmount = new TextField(amountProperty);
            drugAmount.setImmediate(true);
            drugAmount.setWidth("50px");
            // Button to add the item in the row to the cart (with a wrapped item bound to it)
            Button addBtn = new Button();
            addBtn.setIcon(FontAwesome.CART_PLUS);
            DrugItemWrapper diw = new DrugItemWrapper(d,drugAmount);
            addBtn.setData(diw);
            addBtn.addClickListener(event -> {
                DrugItemWrapper drugItemWrapper = (DrugItemWrapper)event.getButton().getData();
                DrugOrderItem drugItem = drugItemWrapper.getItem();
                if(drugItem!=null) {
                    addItem(drugItem);
                }
            });
            // Create the table rows
            drugTable.addItem(
                    new Object[] { drugName, gtin, drugDescription, drugAmount, addBtn }, d.getId());
        }

	/**
	 * Configuring the table containing the actual cart
         */
        cartTable.addStyleName("components-inside");
        cartTable.setPageLength(6);
        cartTable.setWidth("300px");
        cartTable.addContainerProperty("Drug name", Label.class, null);
        cartTable.addContainerProperty("Amount", TextField.class, null);
        cartTable.addContainerProperty("", Button.class, null);

	/**
	 * Configuring remarks TextArea
         */
        remarks.setWidth("300px");

        /**
         * Configure static Labels
         */
        final Label drugsLabel = new Label("Available drugs:");
        final Label cartLabel = new Label("Order cart:");

        /**
	 * Adding layout components and positioning cart parts
	 */
        GridLayout grid = new GridLayout(2,5);
        grid.setMargin(true);
        grid.setSpacing(true);
        grid.setImmediate(true);
        grid.setWidth("100%");
        grid.addComponent(drugsLabel, 0, 0);
        grid.addComponent(drugTable, 0, 1, 0, 4);
        grid.addComponent(cartLabel, 1, 0);
        grid.addComponent(cartTable, 1, 1);
        grid.addComponent(patientSelect, 1, 2);
        grid.addComponent(remarks, 1, 3);
        grid.addComponent(sendOrderBtn, 1, 4);

        // add grid to the main panel
        this.addComponent(grid);

	/**
	 * If an order is used as constructing parameter, fill the items into the cart
         */
        if(order != null){
            populate(order);
        }
    }

    /**
     * Adds an item to the cart. If the item is already present in the cart, its quantity will be
     * increased by the corresponding amount
     * @param drugOrderItem the item to be added to the cart
     */
    public synchronized void addItem(DrugOrderItem drugOrderItem){
        boolean newItem = true;

        for (DrugOrderItem i : items) {
            if (i.getDrug().getGtin() == drugOrderItem.getDrug().getGtin()) {
                newItem = false;
                i.setQuantity(i.getQuantity() + drugOrderItem.getQuantity());
                ((TextField)cartTable.getItem(drugOrderItem.getDrug().getGtin()).getItemProperty("Amount").getValue()).setValue(Integer.toString(i.getQuantity()));
            }
        }
        if (newItem) { //ToDo: implement
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

    /**
     * Get a set of items that are currently in the cart.
     * @return items in the cart
     */
    public synchronized List<DrugOrderItem> getItems(){
        return items;
    }

    /**
     * Calculate the total amount of items in the cart (not just the number of different types)
     * @return number of items in cart
     */
    public synchronized int getNumberOfItems(){
        cartSize = 0;
        for (DrugOrderItem i : items){
            cartSize += i.getQuantity();
        }
        return cartSize;
    }

    /**
     * Clear all items that are currently in the cart
     */
    public synchronized void emptyCart(){
        items.clear();
        cartSize = 0;
        cartTable.removeAllItems();
    }

    /**
     * Generate a DrugOrder entity object from the items in the cart
     * @return DrugOrder object generated from cart content. Returns null if the DrugOrder cannot
     * be generated with the current cart content (no items or no patient selected)
     */
    public DrugOrder generateDrugOrder() {
        if (getNumberOfItems() > 0 && patientSelect.getValue()!=null) {
            DrugOrder drugOrder = new DrugOrder();
            drugOrder.setPatient((Patient) patientSelect.getValue());
            drugOrder.setDrugs(new HashSet<DrugOrderItem>(getItems()));
            drugOrder.setRemarks(remarks.getValue());
            return drugOrder;
        } else {
            return null;
        }
    }

    /**
     * Adds all items (in their respective quantity) from a DrugOrder object to the cart
     * @param order DrugOrder to be added to the cart
     */
    private void populate(DrugOrder order){
        for(DrugOrderItem  item : order.getDrugOrderItems()){
            addItem(item);
        }
    }

    /**
     * Nested wrapper class for the Drug table
     */
    private class DrugItemWrapper{
        private Drug drug;
        private TextField textField;

	/**
         * Generate a DrugItem wrapper object
         * @param d Drug for this wrapper
         * @param t TextField containing the amount for this wrapper
         */
        public DrugItemWrapper(Drug d, TextField t){
            drug = d;
            textField = t;
            textField.addValidator(new IntegerRangeValidator(
                            "The value must be integer between 0-120 (was {0})",
                            0, 999));
        }

	/**
         * Get the DrugOrderItem from this wrapper
         * @return the DrugOrderItem contained in this wrapper
         */
        public DrugOrderItem getItem(){
            try {
                textField.validate();
                DrugOrderItem item = new DrugOrderItem();
                item.setDrug(drug);
                item.setQuantity(Integer.parseInt(textField.getValue()));
                return item;
            } catch (Validator.InvalidValueException e)  {
                Notification.show(e.getMessage());
                return null;
            }

        }
    }

    /**
     * Helper to test if a String can be converted to an Integer
     * @param value String to be tested
     * @return can the String be parsed?
     */
    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
