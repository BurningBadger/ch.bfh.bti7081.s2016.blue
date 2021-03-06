package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ch.bfh.bti7081.s2016.blue.hv.entities.Report;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;
import ch.bfh.bti7081.s2016.blue.hv.model.VisitsModel;
import ch.bfh.bti7081.s2016.blue.hv.util.DateUtils;

public class PatientVisitHistoryListView extends VerticalLayout implements View {
	private static final long serialVersionUID = -5383397578535592333L;
	private static final String NAME = "PatientVisitHistoryListView";
    private long patientID;
    private String lastPage;
    private Button backBut = new Button("Back");
    private Button callBut = new Button("CALL");
    private Button homeBut = new Button("Home");
    private Table table;
    private Visit visit;

    public PatientVisitHistoryListView(long patientID, String lastPage) {
		this.patientID = patientID;
		this.lastPage = lastPage;
		this.setSizeFull();
		
		configureComponents();
		buildLayout();
    }
    
    /*public PatientVisitHistoryListView() {
    	this.setSizeFull();
		
		configureComponents();
		buildLayout();
    }*/

    private void configureComponents() {

    }

    @SuppressWarnings("deprecation")
	private void buildLayout() {
		VerticalLayout main = new VerticalLayout();
	
		// Header
		HorizontalLayout header = new HorizontalLayout();
		header.addComponent(backBut);
		backBut.addClickListener(event -> {
		    this.detach();
		    getUI().getNavigator().navigateTo(lastPage);
		    // getUI().getNavigator().navigateTo(VisitsView.getName());
		    this.removeAllComponents();
		});
		header.setComponentAlignment(backBut, Alignment.MIDDLE_LEFT);
	
		header.addComponent(callBut);
		callBut.addClickListener(event -> {
		    final FormLayout formLayout = new FormLayout();
	
		    String windowTitle = "Phone: " + visit.getPatient().getContact().getPhoneNumber().toString();
	
		    final Window window = new Window(windowTitle);
		    window.setWidth(400.0f, Unit.PIXELS);
		    window.center();
		    window.setContent(formLayout);
		    UI.getCurrent().addWindow(window);
		});
		header.setComponentAlignment(callBut, Alignment.MIDDLE_CENTER);
	
		header.addComponent(homeBut);
		homeBut.addClickListener(event -> {
		    this.detach();
		    getUI().getNavigator().navigateTo(LandingView.getName());
		    this.removeAllComponents();
		});
		header.setComponentAlignment(homeBut, Alignment.MIDDLE_RIGHT);
		header.setWidth("100%");
		header.setHeight(37, Unit.PIXELS);
		header.setMargin(true);
	
		// Middle
		HorizontalLayout middle = new HorizontalLayout();
		table = new Table();
		table.addStyleName("components-inside");
	
		table.addContainerProperty("Date", Label.class, null);
		table.addContainerProperty("Visitor", Label.class, null);
		table.addContainerProperty("Reports", Button.class, null);
	
		try {
	
		    visit = new VisitsModel().findById(patientID);
		    table.setCaption(
			    "Visit History of " + visit.getPatient().getFirstname() + " " + visit.getPatient().getLastname());
	
		    for (VisitEvent visitEvent : visit.getVisitEvents()) {
	
			Label date = new Label(DateUtils.formatDate(visitEvent.getDateFrom()));
			Label visitor = new Label(visitEvent.getVisit().getVisitor().getUserName());
			Button detailsBtn = new Button("View Report");
			detailsBtn.setData(visitEvent);
			detailsBtn.addClickListener(event -> {
			    VisitEvent v = (VisitEvent) event.getButton().getData();
			    showDetailsWindow(v);
			});
	
			detailsBtn.addStyleName("link");
	
			// Create the table row.
			table.addItem(new Object[] { date, visitor, detailsBtn }, visitEvent.getId());
		    }
	
		    table.setPageLength(10);
		}
		catch (Exception dbconnectionerror) {
		    new Notification("WARNING", "<br/>unable to get requested data", Notification.TYPE_ERROR_MESSAGE);
		}
	
		table.setWidth("100%");
		table.setHeight("100%");
	
		middle.addComponent(table);
		middle.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
		middle.setWidth("100%");
		middle.setHeight("100%");
		middle.setMargin(true);
	
		main.addComponent(header);
		main.addComponent(middle);
		this.addComponent(main);
    }

    private void showDetailsWindow(VisitEvent visitEvent) {

		Window window = new Window("Reports of " + visitEvent.getVisit().getPatient().getLastname()
			+ visitEvent.getVisit().getPatient().getFirstname() + " from "
			+ DateUtils.formatDate(visitEvent.getDateFrom()));
	
		VerticalLayout tableLayout = new VerticalLayout();
	
		Table table = new Table();
		table.addStyleName("components-inside");
	
		// define the columns
		table.addContainerProperty("Title", Label.class, null);
		table.addContainerProperty("Details", Label.class, null);
	
		for (Report report : visitEvent.getVisitReports()) {
		    table.addItem(new Object[] { new Label(report.getTitle()), new Label(report.getReportText()) },
			    report.getId());
		}
	
		table.setWidth("100%");
		table.setHeight("100%");
		table.setSizeFull();
		table.setImmediate(true);
	
		tableLayout.addComponent(table);
		tableLayout.setExpandRatio(table, 1f);
		tableLayout.setSizeFull();
		tableLayout.setImmediate(true);
	
		window.setContent(tableLayout);
		window.setWidth(800.0f, Unit.PIXELS);
		window.setHeight(800.0f, Unit.PIXELS);
		window.center();
		window.setImmediate(true);
		UI.getCurrent().addWindow(window);
    }

    public static String getName() {
    	return NAME;
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	/*String[] params = event.getParameters().split("/");
    	this.patientID = Long.parseLong(params[0]);
		this.lastPage = params[1];*/
    }

}
