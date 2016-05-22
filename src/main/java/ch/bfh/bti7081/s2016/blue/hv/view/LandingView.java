package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.hv.model.Calendar;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import ch.bfh.bti7081.s2016.blue.hv.model.*;
import ch.bfh.bti7081.s2016.blue.hv.test.*;

public class LandingView extends Panel implements View {
	
	private static final long serialVersionUID = 8807692569903926065L;
	private static final String NAME = "Home";

	public LandingView(){
		setSizeFull();
		buildUI();
	}

	private void buildUI(){
		EntityService es = new EntityService();
		es.create();

		JPAContainer<Patient> patients = JPAContainerFactory.make(Patient.class, "healthVisApp");
		JPAContainer<HealthVisitor> visitors = JPAContainerFactory.make(HealthVisitor.class, "healthVisApp");
		JPAContainer<Visit> visits = JPAContainerFactory.make(Visit.class, "healthVisApp");
		JPAContainer<VisitEvent> visitEvents = JPAContainerFactory.make(VisitEvent.class, "healthVisApp");
		JPAContainer<Calendar> calendars = JPAContainerFactory.make(Calendar.class, "healthVisApp");

		Table personTable = new Table();
		personTable.setContainerDataSource(patients);
		personTable.setSelectable(true);
		personTable.setImmediate(true);

		Table vTable = new Table();
		vTable.setContainerDataSource(visitors);
		vTable.setSelectable(true);
		vTable.setImmediate(true);

		Table visitTable = new Table();
		visitTable.setContainerDataSource(visits);
		visitTable.setSelectable(true);
		visitTable.setImmediate(true);

		Table visitEventTable = new Table();
		visitEventTable.setContainerDataSource(visitEvents);
		visitEventTable.setSelectable(true);
		visitEventTable.setImmediate(true);

		Table calendarTable = new Table();
		calendarTable.setContainerDataSource(calendars);
		calendarTable.setSelectable(true);
		calendarTable.setImmediate(true);

		final VerticalLayout layout = new VerticalLayout();

		final TextField name = new TextField();
		name.setCaption("Type your name here:");

		Button button = new Button("Click Me");
		button.addClickListener( e -> {
//            Container.Filter filter = new Compare.Equal("firstname", name.getValue());
//            visitors.addContainerFilter(filter);
//            layout.addComponent(new Table("The Persistent Visitors", visitors));
//
//            visitors.addNestedContainerProperty("patients.*");
//            layout.addComponent(new Table("The Persistent Patients", patients));

			Container.Filter filter = new Compare.Equal("firstname", name.getValue());
			patients.addContainerFilter(filter);
			patients.addNestedContainerProperty("drugs.*");
			layout.addComponent(new Table("The patients drugs", patients));
		});

		layout.addComponents(name, button, personTable);
		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	public static String getName() {
		return NAME;
	}

}
