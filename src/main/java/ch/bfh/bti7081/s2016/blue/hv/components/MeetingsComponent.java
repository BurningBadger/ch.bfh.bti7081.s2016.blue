package ch.bfh.bti7081.s2016.blue.hv.components;

import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;
import ch.bfh.bti7081.s2016.blue.hv.util.Helper;
import ch.bfh.bti7081.s2016.blue.hv.view.state.StateContext;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class MeetingsComponent extends VerticalLayout{

    private final Table table = new Table();
    private Embedded routeContainer;
    private final String routeUrl = "http://healthvisitorroutservice.azurewebsites.net/api/route/";
    //private final String routeUrl = "http://localhost:50588/api/route/";

    public MeetingsComponent() {
	table.setWidth("100%");
	table.setHeight("400px");
	table.addStyleName("components-inside");

	// define the columns
	table.addContainerProperty("Patient Name", Label.class, null);
	table.addContainerProperty("Time", Label.class, null);
	table.addContainerProperty("", Button.class, null);
	table.addContainerProperty("Route", Button.class, null);

	this.addComponent(table);

	createRoute("");
    }

    public void createEventsList(Set<VisitEvent> visitEvents){
	for (VisitEvent vEvent : visitEvents) {
	    if (vEvent.getVisit() == null) {
		continue;
	    }
	    Label patientName = new Label(vEvent.getVisit().getPatient().getFirstname() + " " +
			    vEvent.getVisit().getPatient().getLastname());

	    Label appointmentTime = new Label();
	    appointmentTime.setValue(getDateAsString(vEvent)+" "+vEvent.getDateFrom().getHours() + ":" + vEvent.getDateFrom().getMinutes());

	    Button detailsBtn = new Button("Select Meeting");
	    detailsBtn.setData(vEvent);
	    detailsBtn.addClickListener(event -> {
		VisitEvent ve = (VisitEvent) event.getButton().getData();
		//Meeting Selected
		StateContext sContext = new StateContext();
		//Modal Window
		Window subwindow = new Window();
		sContext.next(subwindow, ve);
		UI.getCurrent().addWindow(subwindow);
	    });
	    detailsBtn.addStyleName("link");

	    Button createRoute = new Button("Show Route");
	    createRoute.setData(vEvent);
	    createRoute.addClickListener(e ->{
		Patient patient = vEvent.getVisit().getPatient();
		String url = patient.getContact().getCity() + "/" +
			     patient.getContact().getStreet() + "/" +
			     patient.getContact().getZip()+ "/" +
			     Helper.cutName(patient);
		this.reloadRoute(url);
	    });
	    // Create the table row.
	    table.addItem(new Object[] { patientName, appointmentTime, detailsBtn, createRoute }, vEvent.getId());
	}
    }

    /**
     * method create a instance of Embedded and load roadMap
     * @param url
     *
     */
    public void createRoute(String url){
	routeContainer = new Embedded("",new ExternalResource(routeUrl+url));
	routeContainer.setType(Embedded.TYPE_BROWSER);
	routeContainer.setWidth("100%");
	routeContainer.setHeight("500px");
	this.addComponent(routeContainer);
    }

    /**
     * method remove old routeMap and create new one
     * @param url
     */
    public void reloadRoute(String url){
	this.removeComponent(routeContainer);
	createRoute(url);
    }

    /**
     *
     * @param visit
     * @return date in format 'MM/dd/yyyy'
     */
    private String getDateAsString(VisitEvent visit){
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	String reportDate = df.format(visit.getDateFrom());
	return reportDate;
    }
}
