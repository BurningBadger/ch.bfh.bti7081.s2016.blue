package ch.bfh.bti7081.s2016.blue.hv.testdatagenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.Test;

import ch.bfh.bti7081.s2016.blue.hv.entities.Contact;
import ch.bfh.bti7081.s2016.blue.hv.entities.Drug;
import ch.bfh.bti7081.s2016.blue.hv.entities.DrugOrder;
import ch.bfh.bti7081.s2016.blue.hv.entities.DrugOrderItem;
import ch.bfh.bti7081.s2016.blue.hv.entities.EmergencyContact;
import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;

/**
 * Created by Denis on 5/21/2016.
 */
public class EntityService {

    final static String[] userNames = { "user1@test.com", "user2@test.com", "user3@test.com", "user4@test.com",
	    "user5@test.com" };

    final static String[] passwords = { "user1test", "user2test", "user3test", "user4test", "user5test" };

    final static String[] firstNames = { "Peter", "Alice", "Joshua", "Mike", "Olivia", "Nina", "Alex", "Rita", "Dan",
	    "Umberto", "Henrik", "Rene", "Lisa", "Marge", "Marshall" };

    final static String[] lastNames = { "Smith", "Gordon", "Simpson", "Brown", "Clavel", "Simons", "Verne", "Scott",
	    "Allison", "Gates", "Rowling", "Barks", "Ross", "Schneider", "Tate" };

    final static String cities[] = { "Bern" };

    final static String streets[] = { "Erlenweg 23", "Neuengasse 43", "Viktoriastrasse 21", "Sempachstrasse 22",
	    "Laupenstrasse 19", "Südbahnhofstrasse 14", "Riedbachstrasse 100", "Wankdorfallee 4" };

    final static String zips[] = { "3008", "3011", "3030", "3014", "3008", "3007", "3027", "3030" };

    final static String drugNames[] = { "Analgin", "Ibuprofen", "Ketamin", "Pervitin", "Cocaine", "Heroine", "Viagra",
	    "Prozac", "Tamiflu", "Opium", "LSD", "Valium", "Cannabis", "Aspirin", "Crestor", "Synthroid",
	    "Ventolin HFA", "Nexium", "Advair Diskus", "Vyvanse", "Lyrica", "Ponstan" };

    final static String drugDescriptions[] = { "That thing your mom takes to get \"in the mood\"",
	    "Headache, cancer, Aids, whatever - just take this.",
	    "When your ass burns like fire, this won't help - but it makes you feel good.",
	    "One of these in her drink and you're good to go.",
	    "Try not to take too many of these - but, well, I'm not your mom, so do what you want.",
	    "Don't mix with alcohol. Or do, how should I know? I'm just a description." };

    final static String remarks[] = { "Urgent!", "Please deliver as soon as possible.",
	    "The Patient will get it at your store." };

    static Random r = new Random();

    private Drug createDrug() {
	Drug drug = new Drug();

	drug.setName(drugNames[r.nextInt(drugNames.length)]);
	drug.setDescription(drugDescriptions[r.nextInt(drugDescriptions.length)]);
	drug.setGtin(r.nextInt(999999999));

	return drug;
    }

    private DrugOrder createDrugOrder(Patient patient, Set<DrugOrderItem> items) {
	DrugOrder drugOrder = new DrugOrder();

	drugOrder.setPatient(patient);
	drugOrder.setDrugs(items);
	drugOrder.setRemarks(remarks[r.nextInt(remarks.length)]);

	return drugOrder;
    }

    private DrugOrderItem createDrugOrderItem(Drug drug, DrugOrder drugOrder) {
	DrugOrderItem di = new DrugOrderItem();

	di.setDrug(drug);
	di.setQuantity(r.nextInt(20));
	di.setDrugOrder(drugOrder);

	return di;
    }

    private Contact createContact() {
	Contact contact = new Contact();

	contact.setCity(cities[r.nextInt(cities.length)]);
	contact.setPhoneNumber(10000 + r.nextInt(20000) + 1000 + r.nextInt(1000) + "");

	int randStreetIndex = r.nextInt(streets.length);

	contact.setStreet(streets[randStreetIndex]);
	contact.setZip(zips[randStreetIndex]);

	return contact;
    };

    private EmergencyContact createEmergencyContact() {
	EmergencyContact contact = new EmergencyContact();

	contact.setFirstname(firstNames[r.nextInt(firstNames.length)]);
	contact.setLastname(lastNames[r.nextInt(lastNames.length)]);

	contact.setCity(cities[r.nextInt(cities.length)]);
	contact.setPhoneNumber(10000 + r.nextInt(20000) + 1000 + r.nextInt(1000) + "");
	contact.setStreet(streets[r.nextInt(streets.length)]);
	contact.setZip(10000 + r.nextInt(20000) + "");

	return contact;
    };

    private HealthVisitor createVisitor(int i, Contact contact) {
	HealthVisitor visitor = new HealthVisitor();

	visitor.setFirstname(firstNames[r.nextInt(firstNames.length)]);
	visitor.setLastname(lastNames[r.nextInt(lastNames.length)]);
	visitor.setBirthday(new Date(1990, 5, 1));
	visitor.setUserName(userNames[i]);
	visitor.setPassword(passwords[i]);
	visitor.setContact(contact);

	return visitor;
    };

    private Patient createPatient(Contact contact, EmergencyContact emergencyContact) {
	Patient patient = new Patient();

	patient.setFirstname(firstNames[r.nextInt(firstNames.length)]);
	patient.setLastname(lastNames[r.nextInt(lastNames.length)]);
	patient.setBirthday(new Date(1990, 5, 1));
	patient.setActive(true);
	patient.setContact(contact);
	patient.setEmergencyContact(emergencyContact);

	return patient;
    };

    private Visit createVisit(HealthVisitor visitor, Patient patient) {
	Visit visit = new Visit();

	visit.setVisitor(visitor);
	visit.setPatient(patient);

	return visit;
    };

    private VisitEvent createVisitEvent(Visit visit) {
	VisitEvent visitEvent = new VisitEvent();
	addDates(visitEvent);
	visitEvent.setVisit(visit);
	return visitEvent;
    };

    private void addDates(VisitEvent visitEvent) {

	int day = r.nextInt(15) + 1;
	int hourFrom = r.nextInt(12) + 1;
	int hourTo = hourFrom + 2;

	java.util.Calendar dateCalendar = java.util.Calendar.getInstance();
	dateCalendar.set(java.util.Calendar.YEAR, 2016);
	dateCalendar.set(java.util.Calendar.MONTH, java.util.Calendar.JUNE);
	dateCalendar.set(java.util.Calendar.DAY_OF_MONTH, day);

	java.util.Calendar timeFrom = java.util.Calendar.getInstance();
	timeFrom.set(java.util.Calendar.HOUR, hourFrom);
	timeFrom.set(java.util.Calendar.MINUTE, 30);

	java.util.Calendar timeTo = java.util.Calendar.getInstance();
	timeTo.set(java.util.Calendar.HOUR, hourTo);
	timeTo.set(java.util.Calendar.MINUTE, 30);

	visitEvent.setDateFrom(timeFrom.getTime());
	visitEvent.setDateTo(timeTo.getTime());
    }

    @Test
    public void create() {

	EntityManager em = Persistence.createEntityManagerFactory("healthVisApp").createEntityManager();

	em.getTransaction().begin();

	ArrayList<Drug> drugs = new ArrayList<Drug>();

	for (int i = 0; i < 20; i++) {
	    Drug drug = createDrug();
	    em.persist(drug);
	    drugs.add(drug);
	}

	for (int hv = 0; hv < userNames.length; hv++) {
	    Contact vContact = createContact();
	    HealthVisitor visitor = createVisitor(hv, vContact);
	    Set<Visit> visits = new HashSet<Visit>();

	    int amount = r.nextInt(5) + 1;
	    for (int i = 0; i < amount; i++) {
		Contact pContact = createContact();
		EmergencyContact emContact = createEmergencyContact();
		Patient patient = createPatient(pContact, emContact);
		Visit visit = createVisit(visitor, patient);

		Set<VisitEvent> visitEvents = new HashSet<VisitEvent>();

		int visitsAmount = r.nextInt(5) + 1;
		for (int j = 0; j < visitsAmount; j++) {
		    VisitEvent visitEvent = createVisitEvent(visit);
		    visitEvents.add(visitEvent);

		    em.persist(visitEvent);
		}

		Set<Drug> drugSet = new HashSet<Drug>();
		for (int j = 0; j < 8; j++) {
		    Drug d = drugs.get(r.nextInt(drugs.size()));
		    if (!drugSet.contains(d)) {
			drugSet.add(d);
		    }
		}

		Set<DrugOrder> drugOrderSet = new HashSet<DrugOrder>();
		for (int j = 0; j < 10; j++) {
		    DrugOrder d = new DrugOrder();

		    Set<DrugOrderItem> items = new HashSet<DrugOrderItem>();
		    int orderItemsAmount = r.nextInt(20) + 1;
		    for (int k = 0; k < orderItemsAmount; k++) {
			DrugOrderItem di = createDrugOrderItem((Drug) drugSet.toArray()[r.nextInt(drugSet.size())], d);
			boolean isInSet = false;
			for (DrugOrderItem x : items) {
			    if (x.getName().equals(di.getName())) {
				isInSet = true;
				break;
			    }
			}
			if (!isInSet) {
			    items.add(di);
			}
		    }

		    Date now = new Date();

		    d.setCreatedAt(now);
		    d.setUpdatedAt(now);
		    d.setDrugs(items);
		    d.setPatient(patient);
		    d.setRemarks(remarks[r.nextInt(remarks.length)]);
		    em.persist(d);
		}

		visit.setVisitEvents(visitEvents);
		patient.setVisit(visit);
		patient.setDrugs(drugSet);
		patient.setDrugOrders(drugOrderSet);

		em.persist(visit);
		em.persist(emContact);
		em.persist(pContact);
		em.persist(patient);
	    }

	    visitor.setVisits(visits);

	    em.persist(vContact);
	    em.persist(visitor);
	}

	em.getTransaction().commit();
    }
}
