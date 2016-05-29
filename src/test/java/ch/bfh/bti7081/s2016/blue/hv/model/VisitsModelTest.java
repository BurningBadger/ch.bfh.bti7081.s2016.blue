package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;


public class VisitsModelTest {
    private VisitsModel visitsModel = new VisitsModel();

    @Ignore
    @Test
    public void getAllVisits(){
	List<Visit> visits = visitsModel.findAll();
    }

    @Ignore
    @Test
    public void getVisitEventsByVisit(){
	List<Visit> visits = visitsModel.findAll();

	List<VisitEvent> visitEvents = (List<VisitEvent>) visits.get(0).getVisitEvents();

	int count = visitEvents.size();
    }


}
