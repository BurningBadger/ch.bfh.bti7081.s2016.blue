package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.components.DrugCart;
import ch.bfh.bti7081.s2016.blue.hv.entities.*;
import ch.bfh.bti7081.s2016.blue.hv.model.DrugOrderModel;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;

import java.util.Date;

/**
 * Created by kerberos on 06/06/16.
 */
public class DrugsOrderView extends HorizontalLayout implements View {

    private static final long serialVersionUID = 2371941312011678653L;

    private static final String NAME = "Orders";
    private Table drugOrderTable = new Table();

    public DrugsOrderView(){
        HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
        DrugOrderModel drugOrderModel = new DrugOrderModel();


        VerticalLayout leftPanel = new VerticalLayout();

        drugOrderTable.setSelectable(true);
        drugOrderTable.addStyleName("components-inside");

        drugOrderTable.addContainerProperty("Date", Date.class, null);
        drugOrderTable.addContainerProperty("Patient firstname", Label.class, null);
        drugOrderTable.addContainerProperty("Patient lastname", Label.class, null);
        drugOrderTable.addContainerProperty("Amount of items", Label.class, null);

        VerticalLayout rightPanel = new VerticalLayout();
        final Table orderDetailsTable = new Table();
        orderDetailsTable.addStyleName("components-inside");
        orderDetailsTable.setPageLength(6);
        rightPanel.setVisible(false);
        rightPanel.setImmediate(true);

        orderDetailsTable.addContainerProperty("Drug name", Label.class, null);
        orderDetailsTable.addContainerProperty("Amount", Label.class, null);

        leftPanel.addComponent(drugOrderTable);

        // Container for dropdown and button:
        HorizontalLayout leftBottom = new HorizontalLayout();

        // Dropdown
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);

        ComboBox patientSelect = new ComboBox("Select Patient", container);
        patientSelect.setFilteringMode(FilteringMode.CONTAINS);
        patientSelect.setInvalidAllowed(false);
        patientSelect.setNullSelectionAllowed(true);

        for(Patient patient : visitor.getPatients()){
            String name = patient.getFirstname() + " " + patient.getLastname();
            patientSelect.addItem(patient);
            patientSelect.setItemCaption(patient, name);
        }

        patientSelect.addValueChangeListener(e -> {
            fillDrugOrderTable(drugOrderModel,(Patient)e.getProperty().getValue());
        });


        //button for new order
        Button addNewOrderBtn = new Button("new order");
        addNewOrderBtn.addClickListener(event -> {
            if(patientSelect.getValue()==null){
                showOrderWindow(null, null);
            } else {
                showOrderWindow((Patient)patientSelect.getValue(), null);
            }
        });
        leftBottom.addComponent(patientSelect);
        leftBottom.addComponent(addNewOrderBtn);
        leftPanel.addComponent(leftBottom);

        this.addComponent(leftPanel);

        rightPanel.addComponent(orderDetailsTable);

        Label remarksTitle = new Label("Remarks for this order:");
        rightPanel.addComponent(remarksTitle);

        TextArea remarksArea = new TextArea();
        remarksArea.setEnabled(false);
        rightPanel.addComponent(remarksArea);

        Label sentAtTitle = new Label("This order was sent on:");
        Label sentAt = new Label();
        rightPanel.addComponent(sentAtTitle);
        rightPanel.addComponent(sentAt);

        Button copyOrderBtn = new Button("Copy this order");
        copyOrderBtn.addClickListener(event -> {
            if(patientSelect.getValue()==null){
                showOrderWindow(null, (DrugOrder) orderDetailsTable.getData());
            } else {
                showOrderWindow((Patient) patientSelect.getValue(), (DrugOrder) orderDetailsTable.getData());
            }
        });

        rightPanel.addComponent(copyOrderBtn);
        this.addComponent(rightPanel);

        drugOrderTable.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event){
                if(drugOrderTable.getValue()!=null){
                    if (!rightPanel.isVisible()) {
                        rightPanel.setVisible(true);
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

        fillDrugOrderTable(drugOrderModel, null);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    public static String getName() {
        return NAME;
    }

    private void showOrderWindow(Patient patient, DrugOrder drugOrder){
        final Window window = new Window();
        window.setSizeFull();
        window.setContent(new DrugCart(patient, drugOrder));
        UI.getCurrent().addWindow(window);

    }

    private void showConfirmationWindow() {

        final FormLayout formLayout = new FormLayout();
        final Window window = new Window("Bestätigung");
        window.setWidth(800.0f, Unit.PIXELS);
        window.center();
        window.setContent(formLayout);
        UI.getCurrent().addWindow(window);
    }

    private void fillDrugOrderTable(DrugOrderModel model, Patient patient){
        drugOrderTable.removeAllItems();
        for (DrugOrder order : model.findAll()){
            HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
            if (order.isInPatients(visitor.getPatients())){
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

}
