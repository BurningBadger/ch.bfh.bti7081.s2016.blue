package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.model.VisitsModel;
import elemental.html.ShadowElement;

public class VisitsView extends HorizontalLayout implements View {

    private static final long serialVersionUID = -4194821923203100613L;

    private static final String NAME = "Visits";

    public VisitsView() {
	this.setSizeFull();

	VerticalLayout visitsView = new VerticalLayout();

	HorizontalLayout tableView = new HorizontalLayout();
	Table table = new Table();
	table.addStyleName("components-inside");

	// define the columns
	table.addContainerProperty("Patient firstname", Label.class, null);
	table.addContainerProperty("Patient lastname", Label.class, null);
	table.addContainerProperty("Phone number", Label.class, null);
	table.addContainerProperty("Street", Label.class, null);
	table.addContainerProperty("Zip", Label.class, null);
	table.addContainerProperty("City", Label.class, null);
	table.addContainerProperty("", Button.class, null);

	VisitsModel visitsModel = new VisitsModel();
	for (Visit visit : visitsModel.findAll()) {

	    Label patientFirstname = new Label(visit.getPatient().getFirstname());
	    Label patientLastname = new Label(visit.getPatient().getLastname());
	    Label phoneNumber = new Label(visit.getPatient().getContact().getPhoneNumber());
	    Label street = new Label(visit.getPatient().getContact().getStreet());
	    Label zip = new Label(visit.getPatient().getContact().getZip());
	    Label city = new Label(visit.getPatient().getContact().getCity());

	    Button detailsBtn = new Button("show details");
	    detailsBtn.setData(visit);
	    detailsBtn.addClickListener(event -> {
		    Visit v = (Visit) event.getButton().getData();
		    HealthVisUI.setMainView(new PatientVisitHistoryListView(v.getId()));
//		    HealthVisUI.setMainView(new EmergencyContactView(v.getId()));
		});
	    detailsBtn.addStyleName("link");

	    // Create the table row.
	    table.addItem(
		    new Object[] { patientFirstname, patientLastname, phoneNumber, street, zip, city, detailsBtn },
		    visit.getId());
	}

	table.setWidth("100%");
	table.setHeight("100%");

	tableView.addComponent(table);
	tableView.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
	tableView.setHeight("100%");
	tableView.setWidth("100%");
	tableView.setMargin(true);

	HorizontalLayout footer = new HorizontalLayout();

	// button to add visit
	Button addNewVisitBtn = new Button("add new visit");
	addNewVisitBtn.addClickListener(event -> {
	    showDetailsWindow(null);
	});

	footer.addComponent(addNewVisitBtn);
	footer.setComponentAlignment(addNewVisitBtn, Alignment.BOTTOM_CENTER);
	footer.setWidth("100%");
	footer.setHeight(37, Unit.PIXELS);
	footer.setMargin(true);

	visitsView.addComponent(tableView);
	visitsView.addComponent(footer);
	this.addComponent(visitsView);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

    public static String getName() {
	return NAME;
    }

    private void showDetailsWindow(Visit visit) {

	final FormLayout formLayout = new FormLayout();
	String windowTitle = null;

	// add newy
	if (visit == null) {
	    windowTitle = "Add new visit";
	}
	// show details
	else {
	    windowTitle = visit.getPatient().getFirstname() + " " + visit.getPatient().getLastname();
	}

	final Window window = new Window(windowTitle);
	window.setWidth(800.0f, Unit.PIXELS);
	window.center();
	window.setContent(formLayout);
	UI.getCurrent().addWindow(window);
    }

}
