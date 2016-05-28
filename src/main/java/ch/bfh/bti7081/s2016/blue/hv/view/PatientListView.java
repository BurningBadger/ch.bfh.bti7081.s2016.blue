package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.test.EntityService;

/**
 * Created by uck1 on 22.05.2016.
 */
public class PatientListView extends VerticalLayout implements View {

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
//	setSizeFull();
	buildLayout();
    }

    private void buildLayout() {
	EntityService es = new EntityService();
	es.create();

	Panel panel = new Panel();
	panel.setSizeFull();

	// 1st row
	HorizontalLayout firstLay = new HorizontalLayout();
	firstLay.addComponent(butMenu);
	firstLay.setComponentAlignment(butMenu, Alignment.MIDDLE_LEFT);
	firstLay.addComponent(butCall);
	firstLay.setComponentAlignment(butCall, Alignment.MIDDLE_RIGHT);
	firstLay.setMargin(true);
	butMenu.addStyleName("btntestclass");
	butCall.addStyleName("btntestclass");
	firstLay.setWidth("100%");
	this.addComponent(firstLay);

	// 2nd row
	HorizontalLayout horizontalTage = new HorizontalLayout();
	horizontalTage.addComponent(butGestern);
	horizontalTage.setComponentAlignment(butGestern, Alignment.MIDDLE_LEFT);
	horizontalTage.addComponent(butHeute);
	horizontalTage.setComponentAlignment(butHeute, Alignment.MIDDLE_CENTER);
	horizontalTage.addComponent(butMorgen);
	horizontalTage.setComponentAlignment(butMorgen, Alignment.MIDDLE_RIGHT);
	horizontalTage.setMargin(true);
	butGestern.addStyleName("btntestclass");
	butHeute.addStyleName("btntestclass");
	butMorgen.addStyleName("btntestclass");
	horizontalTage.setWidth("100%");
	this.addComponent(horizontalTage);

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
	    this.addComponent(new Table("The patients drugs", patients));
	});

	this.addComponents(name, button, personTable);
	this.setMargin(true);
	this.setSpacing(true);

	// last row
	HorizontalLayout horizontalPages = new HorizontalLayout();
	horizontalPages.addComponent(butTermine);
	horizontalPages.setComponentAlignment(butTermine, Alignment.MIDDLE_LEFT);
	horizontalPages.addComponent(butRoute);
	horizontalPages.setComponentAlignment(butRoute, Alignment.MIDDLE_CENTER);
	horizontalPages.addComponent(butKalender);
	horizontalPages.setComponentAlignment(butKalender, Alignment.MIDDLE_RIGHT);
	horizontalPages.setMargin(true);
	butTermine.addStyleName("btntestclass");
	butRoute.addStyleName("btntestclass");
	butKalender.addStyleName("btntestclass");
	horizontalPages.setWidth("100%");
	this.addComponent(horizontalPages);
	panel.setContent(this);
    }

    // method for the HealthVisUI
    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
