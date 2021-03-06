package ch.bfh.bti7081.s2016.blue.hv.view;

import java.util.Date;

import ch.bfh.bti7081.s2016.blue.hv.components.CalendarComponent;
import ch.bfh.bti7081.s2016.blue.hv.components.MeetingsComponent;
import ch.bfh.bti7081.s2016.blue.hv.util.Helper;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.model.HealthVisitorsModel;

public class LandingView extends VerticalLayout implements View {

    private HealthVisitorsModel healthVisitorsModel = new HealthVisitorsModel();
    private HealthVisitor currentVisitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
    private static final long serialVersionUID = 8807692569903926065L;
    private static final String NAME = "Home";

    private MeetingsComponent meetingList = new MeetingsComponent();
    private CalendarComponent calendar = new CalendarComponent(meetingList);

    public LandingView() {
    	setMargin(true);
    	//setSizeFull();
    	setWidth("100%");
    	setSizeUndefined();
    	buildUI();
    }

    @SuppressWarnings("deprecation")
	private void buildUI() {

        meetingList.createEventsList(Helper.findVisitEventsByDay(currentVisitor, new Date()));

        final VerticalLayout layout = new VerticalLayout();
        final HorizontalLayout visitorData = new HorizontalLayout();

        visitorData.addComponent(new Label(currentVisitor.getFirstname() + " " + currentVisitor.getLastname()));

        layout.addComponent(visitorData);
        layout.addComponent(calendar);
        layout.addComponent(meetingList);

        layout.setMargin(true);
        layout.setSpacing(true);

        //setContent(layout);
        addComponent(layout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    public static String getName() {
	return NAME;
    }

}
