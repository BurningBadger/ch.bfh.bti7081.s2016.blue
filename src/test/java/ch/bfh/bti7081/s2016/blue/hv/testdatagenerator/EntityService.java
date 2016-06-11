package test.java.ch.bfh.bti7081.s2016.blue.hv.testdatagenerator;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import ch.bfh.bti7081.s2016.blue.hv.entities.*;
import ch.bfh.bti7081.s2016.blue.hv.entities.Calendar;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * Created by Denis on 5/21/2016.
 */
public class EntityService {

    final static String[] userNames = {"user1@test.com", "user2@test.com", "user3@test.com", "user4@test.com", "user5@test.com"};

    final static String[] passwords = {"user1test", "user2test", "user3test", "user4test", "user5test"};

    final static String[] firstNames = { "Peter", "Alice", "Joshua", "Mike", "Olivia", "Nina", "Alex", "Rita", "Dan",
	    "Umberto", "Henrik", "Rene", "Lisa", "Marge", "Marshall" };

    final static String[] lastNames = { "Smith", "Gordon", "Simpson", "Brown", "Clavel", "Simons", "Verne", "Scott",
	    "Allison", "Gates", "Rowling", "Barks", "Ross", "Schneider", "Tate" };

    final static String cities[] = { "Amsterdam", "Berlin", "Helsinki", "Hong Kong", "London", "Luxemburg", "New York",
	    "Oslo", "Paris", "Rome", "Stockholm", "Tokyo", "Turku", "Basel", "Geneve" };

    final static String streets[] = { "4215 Blandit Av.", "452-8121 Sem Ave", "279-4475 Tellus Road",
	    "4062 Libero. Av.", "7081 Pede. Ave", "6800 Aliquet St.", "P.O. Box 298, 9401 Mauris St.",
	    "161-7279 Augue Ave", "P.O. Box 496, 1390 Sagittis. Rd.", "448-8295 Mi Avenue", "6419 Non Av.",
	    "659-2538 Elementum Street", "2205 Quis St.", "252-5213 Tincidunt St.", "P.O. Box 175, 4049 Adipiscing Rd.",
	    "3217 Nam Ave", "P.O. Box 859, 7661 Auctor St.", "2873 Nonummy Av.", "7342 Mi, Avenue",
	    "539-3914 Dignissim. Rd.", "539-3675 Magna Avenue", "Ap #357-5640 Pharetra Avenue", "416-2983 Posuere Rd.",
	    "141-1287 Adipiscing Avenue", "Ap #781-3145 Gravida St.", "6897 Suscipit Rd.", "8336 Purus Avenue",
	    "2603 Bibendum. Av.", "2870 Vestibulum St.", "Ap #722 Aenean Avenue", "446-968 Augue Ave",
	    "1141 Ultricies Street", "Ap #992-5769 Nunc Street", "6690 Porttitor Avenue", "Ap #105-1700 Risus Street",
	    "P.O. Box 532, 3225 Lacus. Avenue", "736 Metus Street", "414-1417 Fringilla Street",
	    "Ap #183-928 Scelerisque Road", "561-9262 Iaculis Avenue" };

    final static String drugNames[] = { "Analgin", "Ibuprofen", "Ketamin", "Pervitin", "Cocaine", "Heroine",
			"Viagra", "Prozac", "Tamiflu", "Opium", "LSD", "Valium", "Cannabis", "Aspirin", "Crestor",
			"Synthroid", "Ventolin HFA", "Nexium", "Advair Diskus", "Vyvanse", "Lyrica", "Ponstan"};

	final static String drugDescriptions[] = {
			"That thing your mom takes to get \"in the mood\"",
			"Headache, cancer, Aids, whatever - just take this.",
			"When your ass burns like fire, this won't help - but it makes you feel good.",
			"One of these in her drink and you're good to go.",
			"Try not to take too many of these - but, well, I'm not your mom, so do what you want.",
			"Don't mix with alcohol. Or do, how should I know? I'm just a description."};

	final static String remarks[] = { "Urgent!", "Please deliver as soon as possible.", "The Patient will get it at your store." };

    static Random r = new Random();

    private Drug createDrug(){
		Drug drug = new Drug();

		drug.setName(drugNames[r.nextInt(drugNames.length)]);
		drug.setDescription(drugDescriptions[r.nextInt(drugDescriptions.length)]);
		drug.setGtin(r.nextInt(999999999));

		return drug;
	}

    private DrugOrder createDrugOrder(Patient patient, Set<DrugOrderItem> items){
	    DrugOrder drugOrder = new DrugOrder();

	    drugOrder.setPatient(patient);
	    drugOrder.setDrugs(items);
	    drugOrder.setRemarks(remarks[r.nextInt(remarks.length)]);

	    return drugOrder;
    }

    private DrugOrderItem createDrugOrderItem(Drug drug, DrugOrder drugOrder){
	    DrugOrderItem di = new DrugOrderItem();

	    di.setDrug(drug);
	    di.setQuantity(r.nextInt(20));
	    di.setDrugOrder(drugOrder);

	    return di;
    }

    private Contact createContact(){
	Contact contact = new Contact();

	contact.setCity(cities[r.nextInt(cities.length)]);
	contact.setPhoneNumber(10000 + r.nextInt(20000) + 1000 + r.nextInt(1000) + "");
	contact.setStreet(streets[r.nextInt(streets.length)]);
	contact.setZip(10000 + r.nextInt(20000) + "");

	return contact;
    };

    private EmergencyContact createEmergencyContact(){
	EmergencyContact contact = new EmergencyContact();

	contact.setFirstname(firstNames[r.nextInt(firstNames.length)]);
	contact.setLastname(lastNames[r.nextInt(lastNames.length)]);

	contact.setCity(cities[r.nextInt(cities.length)]);
	contact.setPhoneNumber(10000 + r.nextInt(20000) + 1000 + r.nextInt(1000) + "");
	contact.setStreet(streets[r.nextInt(streets.length)]);
	contact.setZip(10000 + r.nextInt(20000) + "");

	return contact;
    };

    private HealthVisitor createVisitor(int i, Contact contact){
	HealthVisitor visitor = new HealthVisitor();

	visitor.setFirstname(firstNames[r.nextInt(firstNames.length)]);
	visitor.setLastname(lastNames[r.nextInt(lastNames.length)]);
	visitor.setBirthday(new Date(1990, 5, 1));
	visitor.setUserName(userNames[i]);
	visitor.setPassword(passwords[i]);
	visitor.setContact(contact);

	return visitor;
    };

    private Patient createPatient(Contact contact, EmergencyContact emergencyContact){
	Patient patient = new Patient();

	patient.setFirstname(firstNames[r.nextInt(firstNames.length)]);
	patient.setLastname(lastNames[r.nextInt(lastNames.length)]);
	patient.setBirthday(new Date(1990, 5, 1));
	patient.setContact(contact);
	patient.setEmergencyContact(emergencyContact);

	return patient;
    };

    private Visit createVisit(HealthVisitor visitor, Patient patient){
	Visit visit = new Visit();

	visit.setVisitor(visitor);
	visit.setPatient(patient);

	return visit;
    };

    private VisitEvent createVisitEvent(Calendar calendar, Visit visit){
	VisitEvent visitEvent = new VisitEvent();

	visitEvent.setCalendar(calendar);
	visitEvent.setVisit(visit);

	return visitEvent;
    };

    private Calendar createCalendar(){
	Calendar calendar = new Calendar();

	int day = r.nextInt(15) + 1;
	int hour = r.nextInt(12) + 1;

	Date dateFrom = new Date(2016, 6, day, hour, 0);
	Date dateTo = new Date(2016, 6, day, hour + 2, 0);
	calendar.setDateFrom(dateFrom);
	calendar.setDateTo(dateTo);

	return calendar;
    };

    @Test
    public void create() {

	EntityManager em = Persistence.createEntityManagerFactory("healthVisApp").createEntityManager();

	em.getTransaction().begin();

	ArrayList<Drug> drugs = new ArrayList<Drug>();

	for ( int i=0; i < 20; i++){
		Drug drug = createDrug();
		em.persist(drug);
		drugs.add(drug);
	}

	for(int hv=0; hv < userNames.length; hv++){
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
		    Calendar calendar = createCalendar();
		    VisitEvent visitEvent = createVisitEvent(calendar, visit);
		    visitEvents.add(visitEvent);

		    em.persist(calendar);
		    em.persist(visitEvent);
		}

		Set<Drug> drugSet = new HashSet<Drug>();
		for (int j=0;j<8;j++){
			Drug d = drugs.get(r.nextInt(drugs.size()));
			if(!drugSet.contains(d)){
				drugSet.add(d);
			}
		}

		Set<DrugOrder> drugOrderSet = new HashSet<DrugOrder>();
		for (int j=0;j<10;j++){
		    DrugOrder d = new DrugOrder();

		    Set<DrugOrderItem> items = new HashSet<DrugOrderItem>();
		    int orderItemsAmount = r.nextInt(20)+1;
		    for (int k=0;k<orderItemsAmount;k++){
			DrugOrderItem di = createDrugOrderItem((Drug)drugSet.toArray()[r.nextInt(drugSet.size())], d);
			boolean isInSet = false;
			for (DrugOrderItem x : items){
			    if(x.getName().equals(di.getName())){
				isInSet = true;
				break;
			    }
			}
			if(!isInSet){
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

/*
	em.getTransaction().begin();

	Set<Patient> vPatients = new HashSet<Patient>();

	int amount = r.nextInt(5) + 1;
	for (int i = 0; i < amount; i++) {
	    Contact pContact = new Contact();
	    Patient patient = new Patient();
	    Visit visit = new Visit();

	    visit.setPatient(patient);
	    visit.setVisitor(visitor);

	    Set<VisitEvent> visitEvents = new HashSet<VisitEvent>();

	    int visitsAmount = r.nextInt(5) + 1;
	    for (int j = 0; j < visitsAmount; j++) {
		VisitEvent visitEvent = new VisitEvent();
		Calendar calendar = new Calendar();

		int day = r.nextInt(31) + 1;
		int hour = r.nextInt(12) + 1;
		Date dateFrom = new Date(2015, 5, day, hour, 0);
		Date dateTo = new Date(2015, 5, day, hour + 2, 0);
		calendar.setDateFrom(dateFrom);
		calendar.setDateTo(dateTo);
		visitEvent.setCalendar(calendar);

		Set<Note> notes = new HashSet<Note>();
		Note note = new Note();
		note.setNoteText("Dummy note text");
		notes.add(note);

		Set<Report> reports = new HashSet<Report>();
		Report report = new Report();
		report.setTitle("Dummy report title");
		report.setReportText("Dummy report text");
		reports.add(report);

		em.persist(calendar);
		em.persist(note);
		em.persist(report);

		visitEvents.add(visitEvent);

		em.persist(visitEvent);
	    }
	    visit.setVisitEvents(visitEvents);
	    em.persist(visit);

	    Set<Drug> pDrugs = new HashSet<Drug>();

	    int drugsAmount = r.nextInt(5) + 1;
	    for (int j = 0; j < drugsAmount; j++) {
            Drug drug = new Drug();

            drug.setName(drugs[r.nextInt(4) + 1]);
            drug.setPatient(patient);
            drug.setDescription("Test");

            pDrugs.add(drug);
            em.persist(drug);
	    }
	    patient.setDrugs(pDrugs);
	    patient.setBirthday(new Date(1990, 5, 1));

	    Set<HealthVisitor> visitors = new HashSet<HealthVisitor>();
	    visitors.add(visitor);

	    pContact.setCity(cities[r.nextInt(14) + 1]);
	    pContact.setPhoneNumber(10000 + r.nextInt(20000) + 1000 + r.nextInt(1000) + "");
	    pContact.setStreet(streets[streets.length -1]);
	    pContact.setZip(10000 + r.nextInt(20000) + "");

	    patient.setFirstname(firstNames[r.nextInt(14) + 1]);
	    patient.setLastname(lastNames[r.nextInt(14) + 1]);
	    patient.setVisitors(visitors);
	    patient.setContact(pContact);

	    vPatients.add(patient);

	    em.persist(pContact);
	    em.persist(patient);
	}

	visitor.setContact(vContact);
	visitor.setPatients(vPatients);

	em.persist(vContact);
	em.persist(visitor);

	em.getTransaction().commit();
*/
    }
}
