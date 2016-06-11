package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.hv.entities.*;
import ch.bfh.bti7081.s2016.blue.hv.view.*;
import ch.bfh.bti7081.s2016.blue.hv.model.*;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.*;

/**
 * Created by suttc1 on 22.05.2016.
 */

public class PatientVisitHistoryListView extends VerticalLayout implements View {

    private static final String NAME = "PatientVisitHistoryListView";
    private long patientID;
    private Button backBut = new Button("Back");
    private Button callBut = new Button("CALL");
    private Button homeBut = new Button("Home");
    private HorizontalLayout header;
    private HorizontalLayout middle;
    private Table table;
    private Visit visit;

    ListSelect visits = new ListSelect("Last Visits");

    @SuppressWarnings("deprecation")
    public PatientVisitHistoryListView(long patientID) {
	this.patientID = patientID;
	this.setSizeFull();
	this.setMargin(true);

	configureComponents();
	buildLayout();
    }

    private void configureComponents() {

    }

    private void buildLayout() {

	// Header
	header = new HorizontalLayout();
	header.addComponent(backBut);
	backBut.addClickListener(event -> {
	    this.detach();
	    getUI().getNavigator().navigateTo(VisitsView.getName());
	    this.removeAllComponents();
	});
	header.setComponentAlignment(backBut, Alignment.MIDDLE_LEFT);
	
	header.addComponent(callBut);
	header.setComponentAlignment(callBut, Alignment.MIDDLE_CENTER);
	header.addComponent(homeBut);
	header.setComponentAlignment(homeBut, Alignment.MIDDLE_RIGHT);
	header.setHeight(37, Unit.PIXELS);
	header.setHeight("100%");
	header.setWidth("100%");
	
	// Middle
	middle = new HorizontalLayout();
	table = new Table();
	table.setSizeFull();
	
	table.addStyleName("components-inside");
	
	table.addContainerProperty("Date", Label.class, null);
	table.addContainerProperty("Visitor", Label.class, null);
	table.addContainerProperty("Content", Button.class, null);

	try {
	    
	    visit = new VisitsModel().findById(patientID);
	    table.setCaption("Visit History of " + visit.getPatient().getLastname() + " " + visit.getPatient().getFirstname());

	    for (VisitEvent visitEvent : visit.getVisitEvents()) {

		Label date = new Label(visitEvent.getCalendar().getFormattedMeetingDate());
		Label visitor = new Label(visitEvent.getVisit().getVisitor().getUserName());
		Label content = new Label(visitEvent.getVisitReports().toString());

		Button detailsBtn = new Button("show reports");
		detailsBtn.setData(visitEvent);
		detailsBtn.addClickListener(event -> {
		     VisitEvent v = (VisitEvent) event.getButton().getData();
			 showDetailsWindow(v);
		    });
		
		detailsBtn.addStyleName("link");
		
		// Create the table row.
		    table.addItem(
			    new Object[] { date, visitor, detailsBtn },
			    visitEvent.getId());
	    }

	    table.setPageLength(10);
	}
	catch (Exception dbconnectionerror) {
	    new Notification("WARNING", "<br/>unable to get requested data", Notification.TYPE_ERROR_MESSAGE);
	}
	
	table.setWidth("100%");
	middle.addComponent(table);
	middle.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
	middle.setWidth("100%");
	middle.setHeight("100%");
	
	this.addComponent(header);
	this.addComponent(middle);
    }

    private void showDetailsWindow(VisitEvent visitEvent) {

	final FormLayout formLayout = new FormLayout();
	
	String windowTitle = "Reports of "+visitEvent.getVisit().getPatient().getLastname()+visitEvent.getVisit().getPatient().getFirstname()+" from "+visitEvent.getCalendar().getFormattedMeetingDate();
	
	final Table table = new Table();
	table.addStyleName("components-inside");
	
	// define the columns
	table.addContainerProperty("Title", Label.class, null);
	table.addContainerProperty("Details", Label.class, null);
	
	for(Report report : visitEvent.getVisitReports()){
	    table.addItem(
		    new Object[] { new Label(report.getTitle()), new Label(report.getReportText()) },
		    report.getId());
	}
	
	formLayout.addComponent(table);
	
	final Window window = new Window(windowTitle);
	window.setWidth(800.0f, Unit.PIXELS);
	window.center();
	table.setSizeFull();
	window.setContent(formLayout);
	UI.getCurrent().addWindow(window);
    }

    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

}
