package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.hv.entities.Drug;
import ch.bfh.bti7081.s2016.blue.hv.model.DrugsModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;

public class DrugsView extends VerticalLayout implements View {

    private static final long serialVersionUID = 5462829240356734337L;
    private static final String NAME = "Drugs";

    public DrugsView(){
        DrugsModel model = new DrugsModel();

        this.setSizeFull();
        this.setMargin(true);

        final Table table = new Table();
        table.addStyleName("components-inside");

        // define the columns
        table.addContainerProperty("Drug Name", Label.class, null);
        table.addContainerProperty("Description", Label.class, null);
        table.addContainerProperty("", Button.class, null);

        for (Drug d : model.findAll()) {

            Label drugName = new Label(d.getName());
            Label drugDescription = new Label(d.getDescription());

            Button editButton = new Button("edit");
            editButton.setData(d);
            editButton.addClickListener(event -> {
                // show the detail of the selected element
                Drug dr = (Drug) event.getButton().getData();
                showEditWindow(dr);
            });
            editButton.addStyleName("link");

            // Create the table row.
            table.addItem(
                    new Object[] { drugName, drugDescription },
                    d.getId());
        }

        this.addComponent(table);

        // button to add visit
        Button addNewDrugBtn = new Button("add new drug");
        addNewDrugBtn.addClickListener(event -> {
            showEditWindow(null);
        });
        this.addComponent(addNewDrugBtn);
    }

    @Override
    public void enter(ViewChangeEvent event) {
	// TODO Auto-generated method stub
    }

    public static String getName() {
	return NAME;
    }

    private void showEditWindow(Drug drug) {

        final FormLayout formLayout = new FormLayout();
        String windowTitle = null;

        // add new
        if (drug == null) {
            windowTitle = "Add new drug";
        }
        // show details
        else {
            windowTitle = drug.getName();
        }

        final Window window = new Window(windowTitle);
        window.setWidth(800.0f, Unit.PIXELS);
        window.center();
        window.setContent(formLayout);
        UI.getCurrent().addWindow(window);
    }

}
