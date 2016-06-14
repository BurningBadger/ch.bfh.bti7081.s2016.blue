package ch.bfh.bti7081.s2016.blue.hv.util;

import ch.bfh.bti7081.s2016.blue.hv.entities.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public final class Helper {

    /**
     *
     * @param visitor
     * @return all patients of this visitor
     *
     */
    //TODO: move to PatientsModel
    public static Set<Patient> getPatients(HealthVisitor visitor){
	Set<Visit> visits = visitor.getVisits();
	Set<Patient> patients = new HashSet<Patient>();
	for(Visit visit : visits){
	    patients.add(visit.getPatient());
	}
	return patients;
    }

    /**
     *
     * @param visitor
     * @return all visitEvents of this visitor
     */
    //TODO: move to VisitEventModel
    public static Set<VisitEvent> getVisitEvents(HealthVisitor visitor){
	Set<VisitEvent> visitsList = new HashSet<>();

	for(Visit visit : visitor.getVisits()){
	    for (VisitEvent visitEvent : visit.getVisitEvents()){
		visitsList.add(visitEvent);
	    }
	}
	return visitsList;
    }

    /**
     *
     * @param visitor
     * @param date
     * @return all visitEvents of this visitor filtered by date
     */
    //TODO: move to VisitEventModel
    public static Set<VisitEvent> findVisitEventsByDay(HealthVisitor visitor, Date date){
	Set<VisitEvent> visitEvents = getVisitEvents(visitor);
	Set<VisitEvent> resultList = new HashSet<>();

	for (VisitEvent visitEvent : visitEvents){
	    if(visitEvent.getDateFrom().getDate() == date.getDate())
		resultList.add(visitEvent);
	}

	return resultList;
    }

    /**
     *
     * @param person
     * @return cut person firstname
     */
    public static String cutName(Person person){
	return person.getFirstname().substring(0,1) + "." + person.getLastname();
    }
}
