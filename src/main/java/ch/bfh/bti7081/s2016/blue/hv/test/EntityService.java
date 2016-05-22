package ch.bfh.bti7081.s2016.blue.hv.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import ch.bfh.bti7081.s2016.blue.hv.model.*;

/**
 * Created by Denis on 5/21/2016.
 */
public class EntityService {


    final static String[] firstNames = {  "Peter", "Alice", "Joshua", "Mike",
            "Olivia", "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik",
            "Rene", "Lisa", "Marge", "Marshall" };

    final static String[] lastNames = { "Smith", "Gordon", "Simpson", "Brown",
            "Clavel", "Simons", "Verne", "Scott", "Allison", "Gates",
            "Rowling", "Barks", "Ross", "Schneider", "Tate" };

    final static String cities[] = { "Amsterdam", "Berlin", "Helsinki",
            "Hong Kong", "London", "Luxemburg", "New York", "Oslo", "Paris",
            "Rome", "Stockholm", "Tokyo", "Turku", "Basel", "Geneve" };

    final static String streets[] = { "4215 Blandit Av.", "452-8121 Sem Ave",
            "279-4475 Tellus Road", "4062 Libero. Av.", "7081 Pede. Ave",
            "6800 Aliquet St.", "P.O. Box 298, 9401 Mauris St.",
            "161-7279 Augue Ave", "P.O. Box 496, 1390 Sagittis. Rd.",
            "448-8295 Mi Avenue", "6419 Non Av.", "659-2538 Elementum Street",
            "2205 Quis St.", "252-5213 Tincidunt St.",
            "P.O. Box 175, 4049 Adipiscing Rd.", "3217 Nam Ave",
            "P.O. Box 859, 7661 Auctor St.", "2873 Nonummy Av.",
            "7342 Mi, Avenue", "539-3914 Dignissim. Rd.",
            "539-3675 Magna Avenue", "Ap #357-5640 Pharetra Avenue",
            "416-2983 Posuere Rd.", "141-1287 Adipiscing Avenue",
            "Ap #781-3145 Gravida St.", "6897 Suscipit Rd.",
            "8336 Purus Avenue", "2603 Bibendum. Av.", "2870 Vestibulum St.",
            "Ap #722 Aenean Avenue", "446-968 Augue Ave",
            "1141 Ultricies Street", "Ap #992-5769 Nunc Street",
            "6690 Porttitor Avenue", "Ap #105-1700 Risus Street",
            "P.O. Box 532, 3225 Lacus. Avenue", "736 Metus Street",
            "414-1417 Fringilla Street", "Ap #183-928 Scelerisque Road",
            "561-9262 Iaculis Avenue" };

    final static String drugs[] = {"Analgin", "Ibuprofen", "Ketamin", "Pervitin", "Cocaine", "Heroine"};

    public void create(){

        EntityManager em = Persistence.createEntityManagerFactory("healthVisApp").createEntityManager();

        Random r = new Random();
        Contact vContact = new Contact();
        HealthVisitor visitor = new HealthVisitor();

        vContact.setCity(cities[r.nextInt(14) + 1]);
        vContact.setPhoneNumber(10000 + r.nextInt(20000) + 1000 + r.nextInt(1000) + "");
        vContact.setStreet(streets[r.nextInt(40) + 1]);
        vContact.setZip(10000 + r.nextInt(20000)+"");

        visitor.setFirstname(firstNames[r.nextInt(14) + 1]);
        visitor.setLastname(lastNames[r.nextInt(14) + 1]);
        visitor.setBirthday(new Date(1990, 5, 1));
        visitor.setUserName("testUser");

        em.getTransaction().begin();

        Set<Patient> vPatients = new HashSet<Patient>();

        int amount = r.nextInt(5) + 1;
        for(int i = 0; i < amount; i++){
            Contact pContact = new Contact();
            Patient patient = new Patient();
            Visit visit = new Visit();

            visit.setPatient(patient);
            visit.setVisitor(visitor);

            Set<VisitEvent> visitEvents = new HashSet<VisitEvent>();

            int visitsAmount = r.nextInt(5) + 1;
            for(int j = 0; j < visitsAmount; j++){
                VisitEvent visitEvent = new VisitEvent();
                Calendar calendar = new Calendar();

                int day = r.nextInt(31) + 1;
                int hour = r.nextInt(12) + 1;
                Date dateFrom = new Date(2015, 5, day, hour, 0);
                Date dateTo = new Date(2015, 5, day, hour+2, 0);
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
            for(int j = 0; j < drugsAmount; j++) {
                Drug drug = new Drug();

                drug.setName(drugs[r.nextInt(4) + 1]);
                drug.setPatient(patient);

                pDrugs.add(drug);
                em.persist(drug);
            }
            patient.setDrugs(pDrugs);
            patient.setBirthday(new Date(1990, 5, 1));


            Set<HealthVisitor> visitors = new HashSet<HealthVisitor>();
            visitors.add(visitor);

            pContact.setCity(cities[r.nextInt(14) + 1]);
            pContact.setPhoneNumber(10000 + r.nextInt(20000) + 1000 + r.nextInt(1000) + "");
            pContact.setStreet(streets[r.nextInt(40) + 1]);
            pContact.setZip(10000 + r.nextInt(20000)+"");

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
    }
}
