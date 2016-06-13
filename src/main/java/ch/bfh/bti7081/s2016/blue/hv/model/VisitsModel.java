package ch.bfh.bti7081.s2016.blue.hv.model;

import java.util.Date;
import java.util.logging.Logger;

import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;

public class VisitsModel extends BaseModel<Visit, Long> {

    private static final long serialVersionUID = -5573378810271266249L;

    private final static Logger LOGGER = Logger.getLogger(VisitsModel.class.getName());

    private VisitEventModel visitEventModel;

    public VisitsModel() {
	super(Visit.class);
	visitEventModel = new VisitEventModel();
    }

    /**
     * Save a new Visit.
     * 
     * @param selectedPatient
     *            the id of the selected patient.
     * @param dateFrom
     *            the date from
     * @param dateTo
     *            the date to.
     */
    public boolean saveNewVisit(Patient selectedPatient, Date dateFrom, Date dateTo, HealthVisitor healthVisitor) {

	Visit visit = new Visit();
	visit.setPatient(selectedPatient);

	// save the VisitEvent
	VisitEvent visitEvent = new VisitEvent();
	visitEvent.setDateFrom(dateFrom);
	visitEvent.setDateTo(dateTo);
	visitEventModel.saveOrUpdate(visitEvent);

	visit.getVisitEvents().add(visitEvent);

	// save the visit
	this.saveOrUpdate(visit);
	return true;
    }

}
