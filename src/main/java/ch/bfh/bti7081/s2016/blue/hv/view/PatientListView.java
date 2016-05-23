package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.test.EntityService;

/**
 * Created by uck1 on 22.05.2016.
 */
public class PatientListView extends Panel implements View {

    private static final String NAME = "Patients"; // variable for HealthVisUI

    private Button butMenu = new Button("Menu");
    private Button butCall = new Button("Call");
    private Button butGestern = new Button("Gestern");
    private Button butHeute = new Button("Heute");
    private Button butMorgen = new Button("Morgen");
    private Button butTermine = new Button("Termine");
    private Button butRoute = new Button("Route");
    private Button butKalender = new Button("Kalender");

    public PatientListView() {
	setSizeFull();
	buildLayout();
    }

    private void buildLayout() {
	EntityService es = new EntityService();
	es.create();

	Panel panel = new Panel("Patients");
	panel.setSizeFull();
	VerticalLayout vertLay = new VerticalLayout();

	// 1st row
	HorizontalLayout firstLay = new HorizontalLayout(butMenu, butCall);
	vertLay.addComponent(firstLay);

	// 2nd row
	HorizontalLayout horizontalTage = new HorizontalLayout(butGestern, butHeute, butMorgen);
	vertLay.addComponent(horizontalTage);

	// table row
	JPAContainer<Patient> patients = JPAContainerFactory.make(Patient.class, "healthVisApp");

	Table personTable = new Table();
	personTable.setContainerDataSource(patients);
	personTable.setSelectable(true);
	personTable.setImmediate(true);

	final TextField name = new TextField();
	name.setCaption("Type your name here:");

	Button button = new Button("Click Me");
	button.addClickListener(e -> {
	    Container.Filter filter = new Compare.Equal("firstname", name.getValue());
	    patients.addContainerFilter(filter);
	    patients.addNestedContainerProperty("drugs.*");
	    vertLay.addComponent(new Table("The patients drugs", patients));
	});

	vertLay.addComponents(name, button, personTable);
	vertLay.setMargin(true);
	vertLay.setSpacing(true);

	// last row
	HorizontalLayout horizontalPages = new HorizontalLayout(butTermine, butRoute, butKalender);
	vertLay.addComponent(horizontalPages);

	// binding to the panel
	panel.setContent(vertLay);
	// setCompositionRoot(panel);
	setContent(panel);
    }

    // method for the HealthVisUI
    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
