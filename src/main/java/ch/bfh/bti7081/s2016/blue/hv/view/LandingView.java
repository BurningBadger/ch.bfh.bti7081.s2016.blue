package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.model.HealthVisitorsModel;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import ch.bfh.bti7081.s2016.blue.hv.entities.Calendar;
import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Table;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class LandingView extends Panel implements View {

    private static final long serialVersionUID = 8807692569903926065L;
    private static final String NAME = "Home";

    private HealthVisitorsModel healthVisitorsModel = new HealthVisitorsModel();

    public LandingView() {
	setSizeFull();
	buildUI();
    }

    private void buildUI() {


        HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();

      //  Set<Patient> patients = visitor.getPatients();

        Table patientsTable = new Table("Patients");
        patientsTable.setSizeFull();

        patientsTable.addContainerProperty("First Name", String.class, null);
        patientsTable.addContainerProperty("Last Name", String.class, null);
        patientsTable.addContainerProperty("Birthday", String.class, null);
        patientsTable.addContainerProperty("City", String.class, null);

//        for(Patient p : patients){
//            patientsTable.addItem(
//                new Object[] { p.getFirstname(), p.getLastname(), p.getBirthday().toString(), p.getContact().getCity() },
//                null
//            );
//        }

        patientsTable.setSelectable(true);
        patientsTable.setImmediate(true);


        Embedded e = new Embedded("Routing", new ExternalResource("http://localhost:50588/"));
        e.setType(Embedded.TYPE_BROWSER);
        e.setWidth("100%");
        e.setHeight("400px");


        final VerticalLayout layout = new VerticalLayout();
        layout.addComponent(e);
        layout.addComponent(new Label(visitor.getFirstname()));
        layout.addComponent(new Label(visitor.getLastname()));
        layout.addComponent(new Label(visitor.getBirthday().toString()));
        layout.addComponent(patientsTable);

        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }
//	EntityService es = new EntityService();
//	es.create();
//
//	JPAContainer<Patient> patients = JPAContainerFactory.make(Patient.class, "healthVisApp");
//	JPAContainer<HealthVisitor> visitors = JPAContainerFactory.make(HealthVisitor.class, "healthVisApp");
//	JPAContainer<Visit> visits = JPAContainerFactory.make(Visit.class, "healthVisApp");
//	JPAContainer<VisitEvent> visitEvents = JPAContainerFactory.make(VisitEvent.class, "healthVisApp");
//	JPAContainer<Calendar> calendars = JPAContainerFactory.make(Calendar.class, "healthVisApp");
//
//	Table personTable = new Table();
//	personTable.setContainerDataSource(patients);
//	personTable.setSelectable(true);
//	personTable.setImmediate(true);
//
//	Table vTable = new Table();
//	vTable.setContainerDataSource(visitors);
//	vTable.setSelectable(true);
//	vTable.setImmediate(true);
//
//	Table visitTable = new Table();
//	visitTable.setContainerDataSource(visits);
//	visitTable.setSelectable(true);
//	visitTable.setImmediate(true);
//
//	Table visitEventTable = new Table();
//	visitEventTable.setContainerDataSource(visitEvents);
//	visitEventTable.setSelectable(true);
//	visitEventTable.setImmediate(true);
//
//	Table calendarTable = new Table();
//	calendarTable.setContainerDataSource(calendars);
//	calendarTable.setSelectable(true);
//	calendarTable.setImmediate(true);
//
//	final VerticalLayout layout = new VerticalLayout();
//
//	final TextField name = new TextField();
//	name.setCaption("Type your name here:");
//
//	Button button = new Button("Click Me");
//	button.addClickListener(e -> {
//	    // Container.Filter filter = new Compare.Equal("firstname",
//	    // name.getValue());
//	    // visitors.addContainerFilter(filter);
//	    // layout.addComponent(new Table("The Persistent Visitors",
//	    // visitors));
//	    //
//	    // visitors.addNestedContainerProperty("patients.*");
//	    // layout.addComponent(new Table("The Persistent Patients",
//	    // patients));
//
//	    Container.Filter filter = new Compare.Equal("firstname", name.getValue());
//	    patients.addContainerFilter(filter);
//	    patients.addNestedContainerProperty("drugs.*");
//	    layout.addComponent(new Table("The patients drugs", patients));
//	});
//
//	layout.addComponents(name, button, personTable);
//	layout.setMargin(true);
//	layout.setSpacing(true);
//
//	setContent(layout);
//    }

    @Override
    public void enter(ViewChangeEvent event) {
	// TODO Auto-generated method stub

    }

    public static String getName() {
	return NAME;
    }

}
