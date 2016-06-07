package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.entities.Cart;
import ch.bfh.bti7081.s2016.blue.hv.entities.Drug;
import ch.bfh.bti7081.s2016.blue.hv.entities.DrugOrder;
import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.model.DrugOrderModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * Created by kerberos on 06/06/16.
 */
public class DrugsOrderView extends HorizontalLayout implements View {

    private static final long serialVersionUID = 2371941312011678653L;

    private static final String NAME = "Orders";

    public DrugsOrderView(){
        HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
        DrugOrderModel drugOrderModel = new DrugOrderModel();

        //left panel (drug order table)
        VerticalLayout leftPanel = new VerticalLayout();
        final Table drugOrderTable = new Table();
        drugOrderTable.setSelectable(true);
        //drugOrderTable.addItemClickListener();
        drugOrderTable.addStyleName("components-inside");

        //define columns
        drugOrderTable.addContainerProperty("Date", Label.class, null);
        drugOrderTable.addContainerProperty("Patient firstname", Label.class, null);
        drugOrderTable.addContainerProperty("Patient lastname", Label.class, null);
        drugOrderTable.addContainerProperty("Amount of items", Label.class, null);

        for (DrugOrder order : drugOrderModel.findAll()){
            if (visitor.getPatients().contains(order.getPatient())){
                Label orderDate = new Label(order.getCreatedAt().toString());
                Label patientFirstname = new Label(order.getPatient().getFirstname());
                Label patientLastname = new Label(order.getPatient().getLastname());
                Label amount = new Label(Integer.toString(order.getTotalItemsAmount()));

                //add to table
                drugOrderTable.addItem(new Object[] { orderDate, patientFirstname, patientLastname, amount}, order.getId());
            }
        }
        leftPanel.addComponent(drugOrderTable);

        //button for new order
        Button addNewOrderBtn = new Button("new order");
        addNewOrderBtn.addClickListener(event -> {
            showOrderWindow(null);
        });
        this.addComponent(addNewOrderBtn);

        this.addComponent(leftPanel);

        // *************

        // right panel (order details)
        VerticalLayout rightPanel = new VerticalLayout();
        final Table orderDetailsTable = new Table();
        orderDetailsTable.addStyleName("components-inside");

        //define columns
        orderDetailsTable.addContainerProperty("Drug name", Label.class, null);
        orderDetailsTable.addContainerProperty("Amount", Label.class, null);

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
