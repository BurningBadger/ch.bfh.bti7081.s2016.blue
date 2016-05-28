package ch.bfh.bti7081.s2016.blue.hv.components;

import ch.bfh.bti7081.s2016.blue.hv.entities.Drug;
import ch.bfh.bti7081.s2016.blue.hv.entities.Prescription;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.model.PrescriptionsModel;
import ch.bfh.bti7081.s2016.blue.hv.model.VisitsModel;
import com.vaadin.data.Item;
import com.vaadin.server.SystemError;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kerberos on 28/05/16.
 */
public class PrescriptionsComponent extends VerticalLayout {
    private static final String COLUMN_NAME_TIME = "Time";
    private static final String COLUMN_NAME_DOSE = "Dose";
    private static final String COLUMN_NAME_DRUG = "Drug";
    private static final String BUTTON_ADD_MEDICATION = "Add medication";

    private final Table table;
    private final VisitsModel visitsModel;
    private final PrescriptionsModel prescriptionsModel = new PrescriptionsModel();

    @SuppressWarnings("unchecked")
    public PrescriptionsComponent(Visit visit, VisitsModel model, boolean enabled) {
        this.visitsModel = model;

        setSpacing(true);

        Label title = new Label("Prescriptions");
        title.setStyleName("h2");
        addComponent(title);

        table = new Table();

        table.setWidth("100%");
        table.setHeight("300px");
        table.setEditable(enabled);
        //table.setTableFieldFactory(new GivenMedicationFieldFactory());

        table.addContainerProperty(COLUMN_NAME_TIME, Date.class, new Date());
        table.addContainerProperty(COLUMN_NAME_DOSE, String.class, "");
        table.addContainerProperty(COLUMN_NAME_DRUG, Drug.class, null);

        table.addGeneratedColumn("", (source, itemId, columnId) -> {
            Button button = new Button("Delete");
            button.addClickListener(event -> source.getContainerDataSource().removeItem(itemId));
            return button;
        });

        // Get data from db
        for (Prescription g : visit.getPrescriptions()) {
            Item item = table.addItem(g.getId());
            item.getItemProperty(COLUMN_NAME_TIME).setValue(g.getPrescriptionDate());
            item.getItemProperty(COLUMN_NAME_DOSE).setValue(g.getDose());
            item.getItemProperty(COLUMN_NAME_DRUG).setValue(g.getDrug());
        }

        addComponent(table);

        // Add buttons if enabled
        if (enabled) {
            HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.setSpacing(true);

            Button newItem = new Button(BUTTON_ADD_MEDICATION);
            newItem.addClickListener(event -> table.addItem());
            buttonLayout.addComponent(newItem);

            Button save = new Button("Save");
            save.addClickListener(event -> {
                ArrayList<Prescription> data = fetchUserData();
                save.setComponentError(null);
                if (!validate(data)) {
                    save.setComponentError(new UserError("An error occurred while validating the data."));
                    return;
                }
                if (save(data)) {
                    return;
                }
                save.setComponentError(new SystemError("An error occurred while saving the data."));
            });

            buttonLayout.addComponent(save);

            addComponent(buttonLayout);
        }
    }
    protected ArrayList<Prescription> fetchUserData() {
        ArrayList<Prescription> data = new ArrayList<>();

        for (Object id : table.getItemIds()) {
            Prescription p = new Prescription();
            Item item = table.getItem(id);

            for (Object propId : item.getItemPropertyIds()) {
                if (propId.equals(COLUMN_NAME_TIME)) {
                    p.setPrescriptionDate((Date) item.getItemProperty(propId).getValue());
                    continue;
                }
                if (propId.equals(COLUMN_NAME_DOSE)) {
                    p.setDose((String) item.getItemProperty(propId).getValue());
                    continue;
                }
                if (propId.equals(COLUMN_NAME_DRUG)) {
                    p.setDrug((Drug) item.getItemProperty(propId).getValue());
                    continue;
                }
            }

            data.add(p);
        }

        return data;
    }

    protected boolean save(ArrayList<Prescription> data) {
        return prescriptionsModel.save(data);
    }

    // implement validation!!!!
    private boolean validate(ArrayList<Prescription> data) {
        return true; //prescriptionsModel.validateGivenMedication(data)
    }
}
