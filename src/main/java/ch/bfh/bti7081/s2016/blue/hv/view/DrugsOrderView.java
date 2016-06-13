package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.components.DrugCart;
import ch.bfh.bti7081.s2016.blue.hv.entities.*;
import ch.bfh.bti7081.s2016.blue.hv.model.DrugOrderModel;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;

import java.util.Date;
import java.util.Set;

/**
 * View for DrugOrder
 *
 * @author Michel Hosmann
 */
public class DrugsOrderView extends HorizontalLayout implements View {

    private static final long serialVersionUID = 2371941312011678653L;

    private static final String NAME = "Orders";
    private Table drugOrderTable;
    private Table orderDetailsTable;
    private ComboBox patientSelect;
    private TextArea remarksArea;
    private Label sentAt;
    private VerticalLayout orderDetailsContainer;

    /**
     * Generates an instance of DrugsOrderView
     */
    public DrugsOrderView(){
        /**
         * Initialization of class variables, models and the current user
         */
        this.orderDetailsContainer = new VerticalLayout();
        this.drugOrderTable = new Table();
        this.orderDetailsTable = new Table();
        this.remarksArea = new TextArea();
        this.sentAt = new Label();
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        this.patientSelect = new ComboBox("Filter table by patient", container);

        HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
        DrugOrderModel drugOrderModel = new DrugOrderModel();

	/**
	 * Configure and populate the DrugOrder table
         */
        drugOrderTable.setSelectable(true);
        drugOrderTable.addStyleName("components-inside");
        drugOrderTable.addContainerProperty("Date", Date.class, null);
        drugOrderTable.addContainerProperty("Patient firstname", Label.class, null);
        drugOrderTable.addContainerProperty("Patient lastname", Label.class, null);
        drugOrderTable.addContainerProperty("Amount of items", Label.class, null);
        drugOrderTable.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event){
                if(drugOrderTable.getValue()!=null){
                    if (!orderDetailsContainer.isVisible()) {
                        orderDetailsContainer.setVisible(true);
                    }
                    orderDetailsTable.removeAllItems();
                    DrugOrder drugOrder = drugOrderModel.findById((long) drugOrderTable.getValue());
                    orderDetailsTable.setData(drugOrder);
                    for (DrugOrderItem item : drugOrder.getDrugOrderItems()) {
                        Label drugName = new Label(item.getName());
                        Label drugAmount = new Label(Integer.toString(item.getQuantity()));

                        //add to table
                        orderDetailsTable.addItem(new Object[] { drugName, drugAmount }, item.getId());
                    }
                    remarksArea.setValue(drugOrder.getRemarks());
                    sentAt.setValue(drugOrder.getCreatedAt().toString());
                }
            }
        });

        /**
         * Configure and populate the DrugOrder details table
         */
        orderDetailsTable.addStyleName("components-inside");
        orderDetailsTable.setPageLength(6);
        orderDetailsTable.setWidth("100%");
        orderDetailsTable.addContainerProperty("Drug name", Label.class, null);
        orderDetailsTable.addContainerProperty("Amount", Label.class, null);

	/**
	 * Configure and populate the Patient selection ComboBox
         */
        patientSelect.setFilteringMode(FilteringMode.CONTAINS);
        patientSelect.setInvalidAllowed(false);
        patientSelect.setNullSelectionAllowed(true);
        for(Patient patient : visitor.getPatients()){
            String name = patient.getFirstname() + " " + patient.getLastname();
            patientSelect.addItem(patient);
            patientSelect.setItemCaption(patient, name);
        }
        // add a listener so that the order details are shown on row selection
        patientSelect.addValueChangeListener(e -> {
            fillDrugOrderTable(drugOrderModel,(Patient)e.getProperty().getValue());
        });

	/**
	 * Configure the button to create a new DrugOrder
         * Opens a window containing a DrugCart component
         */
        Button addNewOrderBtn = new Button("New order");
        addNewOrderBtn.addClickListener(event -> {
            if(patientSelect.getValue()==null){
                showOrderWindow(null, null);
            } else {
                showOrderWindow((Patient)patientSelect.getValue(), null);
            }
        });

        /**
         * Configure the button to create a copy of the selected DrugOrder
         * Opens a window containing a DrugCart component where the cart contains all items from the selected order
         */
        Button copyOrderBtn = new Button("Copy this order");
        copyOrderBtn.addClickListener(event -> {
            if(patientSelect.getValue()==null){
                showOrderWindow(null, (DrugOrder) orderDetailsTable.getData());
            } else {
                showOrderWindow((Patient) patientSelect.getValue(), (DrugOrder) orderDetailsTable.getData());
            }
        });

	/**
	 * Configure remarks TextArea
         */
        remarksArea.setEnabled(false);
        remarksArea.setWidth("100%");

	/**
	 * Configure static Labels
         */
        final Label orderDetails = new Label("Order details");
        final Label remarksTitle = new Label("Remarks for this order:");
        final Label sentAtTitle = new Label("This order was sent on:");

        /**
         * Adding layout components, configuring and positioning View parts
         */
        // left part containing the DrugOrder table
        GridLayout ordersGrid = new GridLayout(2,2);
        ordersGrid.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        ordersGrid.setWidth("100%");
        ordersGrid.setMargin(true);
        ordersGrid.setSpacing(true);
        ordersGrid.addComponent(patientSelect, 0, 0);
        ordersGrid.addComponent(addNewOrderBtn, 1, 0);
        ordersGrid.addComponent(drugOrderTable,0,1,1,1);

        // Container for DrugOrder details
        orderDetailsContainer.setVisible(false);
        orderDetailsContainer.setImmediate(true);
        orderDetailsContainer.setWidth("500px");
        orderDetailsContainer.setMargin(true);
        orderDetailsContainer.setSpacing(true);
        orderDetailsContainer.addComponent(orderDetails);
        orderDetailsContainer.addComponent(orderDetailsTable);
        orderDetailsContainer.addComponent(remarksTitle);
        orderDetailsContainer.addComponent(remarksArea);
        orderDetailsContainer.addComponent(sentAtTitle);
        orderDetailsContainer.addComponent(sentAt);
        orderDetailsContainer.addComponent(copyOrderBtn);

        // add components to the main panel
        this.addComponent(ordersGrid);
        this.addComponent(orderDetailsContainer);

	/**
	 * Populate the DrugOrder table
         */
        fillDrugOrderTable(drugOrderModel, null);

    }

    /**
     * Mandatory method inherited from vaadin View
     * @param event the enter event
     */
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    /**
     * Gets the name of this View
     * @return the name of the View
     */
    public static String getName() {
        return NAME;
    }

    /**
     * Generates a window containing a DrugCart component
     * @param patient a Patient to preset in the DrugOrder (optional)
     * @param drugOrder a DrugOrder from which all items will be added to the cart
     */
    private void showOrderWindow(Patient patient, DrugOrder drugOrder){
        final Window window = new Window();
        window.setSizeFull();
        window.setContent(new DrugCart(patient, drugOrder));
        UI.getCurrent().addWindow(window);

    }

    /**
     * Fills the DrugOrder table with DrugOrders depending on the visitor and the @param patient parameter
     * @param model the DrugOrderModel (containing the visitor)
     * @param patient the Patient for filtering
     */
    private void fillDrugOrderTable(DrugOrderModel model, Patient patient){
        drugOrderTable.removeAllItems();
        for (DrugOrder order : model.findAll()){
            if (isInPatients(order)){
                if(patient==null || order.getPatient().getId()==patient.getId()) {
                    Label patientFirstname = new Label(order.getPatient().getFirstname());
                    Label patientLastname = new Label(order.getPatient().getLastname());
                    Label amount = new Label(Integer.toString(order.getTotalItemsAmount()));
                    drugOrderTable.addItem(
                                    new Object[] { order.getCreatedAt(), patientFirstname, patientLastname, amount },
                                    order.getId());
                }
            }
        }
    }

    /**
     * Checks if a DrugOrder belongs to a Patient of the current HealthVisitor
     * @param order DrugOrder to be checked
     * @return does this DrugOrder belong to one of my Patients?
     */
    private boolean isInPatients(DrugOrder order){
        boolean isIn = false;
        HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
        for (Patient p : visitor.getPatients()) {
            if (p.getId().compareTo(order.getPatient().getId()) == 0){
                isIn = true;
                break;
            }
        }
        return isIn;
    }
}
