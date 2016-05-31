package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;
import ch.bfh.bti7081.s2016.blue.hv.model.VisitEventModel;
import ch.bfh.bti7081.s2016.blue.hv.view.state.StateContext;
import ch.bfh.bti7081.s2016.blue.hv.view.state.StateSwitch;

public class TodayMeetingsView extends HorizontalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = -772153662477057466L;
	private static final String NAME = "TodayMeetings";

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	public TodayMeetingsView() {
		setSizeFull();
		prepareUI();
	}
	
    public void prepareUI() {
    	final Table table = new Table();
    	table.setSizeFull();
    	table.addStyleName("components-inside");

    	// define the columns
    	table.addContainerProperty("Patient Name", Label.class, null);
    	table.addContainerProperty("Address", Label.class, null);
    	table.addContainerProperty("Time", Label.class, null);
    	table.addContainerProperty("", Button.class, null);

    	VisitEventModel veModel = new VisitEventModel();
    	for (VisitEvent vEvent : veModel.findAll()) {
	    if (vEvent.getVisit() == null) {
		continue;
	    }
    		//if(vEvent.getVisit().getVisitor().getUserName() != "user1@test.com") {
    			//continue;
    		//}
    		
    	    Label patientName = new Label(vEvent.getVisit().getPatient().getFirstname() + " " +
    	    		vEvent.getVisit().getPatient().getLastname());
    	    Label patientStreet = new Label(vEvent.getVisit().getPatient().getContact().getStreet() + " " +
    	    		vEvent.getVisit().getPatient().getContact().getZip() + " / " + 
    	    		vEvent.getVisit().getPatient().getContact().getCity());
    	    @SuppressWarnings("deprecation")
			Label appointmentTime = new Label(vEvent.getCalendar().getDateFrom().toLocaleString() + " - " +
    	    		vEvent.getCalendar().getDateTo().toLocaleString());

    	    Button detailsBtn = new Button("Select Meeting");
    	    detailsBtn.setData(vEvent);
    	    detailsBtn.addClickListener(event -> {
    	    	VisitEvent ve = (VisitEvent) event.getButton().getData();
    	    	//Meeting Selected
    	    	StateContext sContext = new StateContext();
    	    	//Modal Window
    	    	Window subwindow = new Window();
    			
    			/*while((sContext.getStateSwitch() != StateSwitch.E_CANCEL || 
    					sContext.getStateSwitch() != StateSwitch.E_FINISH) && 
    					!subwindow.isVisible()) {*/
    				
    			sContext.next(subwindow, ve);
    				
    			//}
    	    	
    	    	UI.getCurrent().addWindow(subwindow);
    	    });
    	    detailsBtn.addStyleName("link");

    	    // Create the table row.
    	    table.addItem(
    		    new Object[] { patientName, patientStreet, appointmentTime, detailsBtn },
    		    vEvent.getId());
    	}
    	this.addComponent(table);
    }
    
    public static String getName() {
		return NAME;
	}
	
}
