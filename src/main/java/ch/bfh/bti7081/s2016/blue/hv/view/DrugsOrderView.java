package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.entities.*;
import ch.bfh.bti7081.s2016.blue.hv.model.DrugOrderModel;
import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.Date;

/**
 * Created by kerberos on 06/06/16.
 */
public class DrugsOrderView extends HorizontalLayout implements View {

    private static final long serialVersionUID = 2371941312011678653L;

    private static final String NAME = "Orders";

    public DrugsOrderView(){
        HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
        DrugOrderModel drugOrderModel = new DrugOrderModel();


        VerticalLayout leftPanel = new VerticalLayout();
        final Table drugOrderTable = new Table();
        drugOrderTable.setSelectable(true);
        drugOrderTable.addStyleName("components-inside");

        drugOrderTable.addContainerProperty("Date", Date.class, null);
        drugOrderTable.addContainerProperty("Patient firstname", Label.class, null);
        drugOrderTable.addContainerProperty("Patient lastname", Label.class, null);
        drugOrderTable.addContainerProperty("Amount of items", Label.class, null);
        drugOrderTable.addContainerProperty("Order details", Button.class, null);

        VerticalLayout rightPanel = new VerticalLayout();
        final Table orderDetailsTable = new Table();
        orderDetailsTable.addStyleName("components-inside");
        rightPanel.setVisible(false);
        rightPanel.setImmediate(true);

        orderDetailsTable.addContainerProperty("Drug name", Label.class, null);
        orderDetailsTable.addContainerProperty("Amount", Label.class, null);

        for (DrugOrder order : drugOrderModel.findAll()){
            if (order.isInPatients(visitor.getPatients())){ // ToDo: maybe generic method in BaseEntity?
                Label patientFirstname = new Label(order.getPatient().getFirstname());
                Label patientLastname = new Label(order.getPatient().getLastname());
                Label amount = new Label(Integer.toString(order.getTotalItemsAmount()));
                Button detailsBtn = new Button("Details");
                detailsBtn.setData(order);
                detailsBtn.addClickListener(event -> {
                    if(!rightPanel.isVisible()){
                        rightPanel.setVisible(true);
                    }
                    orderDetailsTable.removeAllItems();
                    DrugOrder drugOrder = (DrugOrder)event.getButton().getData();
                    for(DrugOrderItem item : drugOrder.getDrugOrderItems()){
                        Label drugName = new Label(item.getName());
                        Label drugAmount = new Label(Integer.toString(item.getQuantity()));

                        //add to table
                        orderDetailsTable.addItem(new Object[] { drugName, drugAmount}, item.getId());
                    }
                });
                //add to table
                drugOrderTable.addItem(new Object[] { order.getCreatedAt(), patientFirstname, patientLastname, amount, detailsBtn}, order.getId());
            }
        }

        leftPanel.addComponent(drugOrderTable);

        //button for new order
        Button addNewOrderBtn = new Button("new order");
        addNewOrderBtn.addClickListener(event -> {
            showOrderWindow(null);
        });
        leftPanel.addComponent(addNewOrderBtn);

        this.addComponent(leftPanel);

        rightPanel.addComponent(orderDetailsTable);

        this.addComponent(rightPanel);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    public static String getName() {
        return NAME;
    }

    private void showOrderWindow(DrugOrder drugOrder){

    }

    private void showConfirmationWindow() {

        final FormLayout formLayout = new FormLayout();
        final Window window = new Window("Bestätigung");
        window.setWidth(800.0f, Unit.PIXELS);
        window.center();
        window.setContent(formLayout);
        UI.getCurrent().addWindow(window);
    }


}
