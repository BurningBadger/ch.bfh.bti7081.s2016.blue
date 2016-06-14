package ch.bfh.bti7081.s2016.blue.hv.components;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;
import ch.bfh.bti7081.s2016.blue.hv.util.Helper;
import com.vaadin.ui.*;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarComponentEvents;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

import java.util.*;

public class CalendarComponent extends HorizontalLayout {

    private Calendar calendar = new Calendar();
    private static final HealthVisitor currentVisitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();


    public CalendarComponent(MeetingsComponent meetingsComponent){
	this.setSizeFull();
	calendar.setWidth("100%");  // Undefined by default
	calendar.setHeight("500px"); // Undefined by default
	calendar.setLocale(new Locale("en", "GB"));
	calendar.setHandler(new BasicDateClickHandler() {
	    public void dateClick(CalendarComponentEvents.DateClickEvent event) {
		Set<VisitEvent> visitEvents = Helper.findVisitEventsByDay(currentVisitor, event.getDate());
		meetingsComponent.createEventsList(visitEvents);
	    }
	});

	// Set start date to first date in this month
	GregorianCalendar calStart = new GregorianCalendar();
	calendar.setStartDate(calStart.getTime());

	// Set end date to last day of this month
	GregorianCalendar calEnd = new GregorianCalendar();
	calEnd.set(java.util.Calendar.DATE, 1);
	calEnd.roll(java.util.Calendar.DATE, -1);
	calendar.setEndDate(calEnd.getTime());

	addVisitsToCalendar(currentVisitor.getVisits());

	this.addComponent(calendar);
	this.setExpandRatio(calendar, 1.2f);
    }

    /**
     *
     * @param visits
     */
    private void addVisitsToCalendar(Set<Visit> visits){
	for(Visit visit : visits){
	    Set<VisitEvent> visitEvents = visit.getVisitEvents();
	    for (VisitEvent visitEvent : visitEvents) {
		addVisitEventToDay(visitEvent, visit.getPatient());
	    }
	}
    }

    /**
     *
     * @param visitEvent
     *
     * @param patient
     */
    private void addVisitEventToDay(VisitEvent visitEvent, Patient patient){
	calendar.addEvent(new BasicEvent(Helper.cutName(patient),
			patient.getContact().getStreet(),
			visitEvent.getDateFrom(), visitEvent.getDateTo()));
    }
}
