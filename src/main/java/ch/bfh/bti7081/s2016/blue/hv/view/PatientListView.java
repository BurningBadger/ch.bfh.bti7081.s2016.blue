package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.components.PhoneComponent;
import ch.bfh.bti7081.s2016.blue.hv.entities.Contact;
import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.model.ContactModel;
import ch.bfh.bti7081.s2016.blue.hv.model.HealthVisitorsModel;
import ch.bfh.bti7081.s2016.blue.hv.model.PatientModel;
import ch.bfh.bti7081.s2016.blue.hv.model.VisitsModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;

import java.util.Set;

/**
 * The PatientListView class specify all the patients treated by the logged-in Health visitor.
 * The main part of the class is the database table. The user can manipulate these entries directly
 * by selecting each one of them. By removing the patients are these not directly deleted from the
 * database, so it's possible, that the same patient can be treated from different Health visitors.
 * It's only removed from the Health visitors list.
 *
 * For more details of the specific patient have the user a possibility to edit these entries via
 * <<Edit>>- and <<show details>>- buttons. With the implemented <<Call>>-function is the phone
 * number of the selected patient ready to be copied. The connection to the calendar and the
 * appointment list of the Health visitor can be achieved also with the corresponding buttons.
 */

public class PatientListView extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 8701473681064614045L;
	
	private static final String NAME = "Patients"; // variable for HealthVisUI
    private HealthVisitor visitor = ((HealthVisUI) UI.getCurrent()).getCurrentUser();
    private Set<Visit> visits = visitor.getVisits();

    /**
     * these variables have to be declared in the global scope to be reached from
     * the outside the table method
     */
    Button callBtn = new Button("Call");
    Button editPatientXtraBtn = new Button("Edit");
    Button updateBtn = new Button("Update");
    Button deletePatientBtn = new Button("Remove");

    public PatientListView() {

	this.setSizeFull();
	this.setMargin(true);

	// show the vertical layout parts
	showFirstRow();
	showTable();
	showTableButtons();
    }

    /**
     * vertical layout: 1st row buttons
     */
    private void showFirstRow() {

	// button: home
	HorizontalLayout firstLay = new HorizontalLayout();
	Button homeBtn = new Button("Home");
	homeBtn.addClickListener(event -> {
	    getUI().getNavigator().navigateTo(LandingView.getName());
	});
	firstLay.addComponent(homeBtn);
	firstLay.setComponentAlignment(homeBtn, Alignment.MIDDLE_LEFT);

	//button: call
	firstLay.addComponent(callBtn);
	firstLay.setComponentAlignment(callBtn, Alignment.MIDDLE_RIGHT);
	firstLay.setWidth("100%");
	firstLay.setHeight("100%");
	this.addComponent(firstLay);
	this.setExpandRatio(firstLay, 0.1f);                //10% of the mainframe
    }

    /**
     * vertical layout: table row with patients
     */
    private void showTable() {

	final Table table = new Table();
	table.setWidth("100%");
	table.setHeight("100%");
	table.addStyleName("components-inside");
	table.setSelectable(true);        // mark the wanted row of the table
	table.setImmediate(true);        // show the table changes directly

	// define the columns
	table.addContainerProperty("first name", Label.class, null);
	table.addContainerProperty("last name", Label.class, null);
	table.addContainerProperty("birthday", Label.class, null);
	table.addContainerProperty("is active?", CheckBox.class, null);
	table.addContainerProperty("", Button.class, null);

	// connect with the DB and add data into the table
	PatientModel patientModel = new PatientModel();

	// show visitor's patients
	for (Visit visit : visits) {

	    Patient patient = visit.getPatient();
	    Label firstName = new Label(patient.getFirstname());
	    Label lastName = new Label(patient.getLastname());
	    Label birthday = new Label(String.valueOf(patient.getBirthday()));
	    CheckBox isActive = new CheckBox();
	    isActive.setValue(patient.isActive());

	    // change the value of the Checkbox
	    isActive.addValueChangeListener(event -> {
		Patient p = patientModel.findById(patient.getId());
		p.setActive(isActive.getValue());	// reference on Checkbox
		patientModel.saveOrUpdate(p);		// update patient
	    });

	    // show details of the selected patient
	    Button detailsBtn = new Button("show details");
	    detailsBtn.setData(patient);
	    detailsBtn.addClickListener(event -> {
		Patient pat = (Patient) event.getButton().getData();
		showDetailsWindow(pat);
	    });
	    detailsBtn.addStyleName("link");

	    // Create a person as a new object and insert into the table row
    	    if (patient.isActive()) {
		    table.addItem(new Object[] { firstName, lastName, birthday, isActive, detailsBtn }, patient.getId());
	    }
	}
	this.addComponent(table);
	this.setExpandRatio(table, 0.8f);        // vertical layout ratio

	/**
	 * bind the selected person to the call functionality
	 *
	 * cast the object to handle as patient entity!
	 */
	callBtn.addClickListener(event -> {
	    Object selected = table.getValue();                //necessary to pick the exact one
	    showPatientNumber(patientModel.findById((Long) selected));
	});
	editPatientXtraBtn.addClickListener(event -> {
	    Object selected = table.getValue();
	    showDetailsWindow(patientModel.findById((Long) selected));
	});
	deletePatientBtn.addClickListener(event -> {
	    Object selected = table.getValue();        //table entry is already an object

	    if (selected == null) {
		Notification.show("Please select an item!");
	    }
	    else {
		Notification.show("Person : " + selected + "removed!");
		patientModel.delete(patientModel.findById((Long) selected));
		table.removeItem(selected);
	    }
	});
    }

    /**
     * vertical layout: table handling buttons
     */
    private void showTableButtons() {

	// buttons to add/ed/del patient
	HorizontalLayout adDel = new HorizontalLayout();

	// button: add new person
	Button addNewPatientBtn = new Button("add patient");
	addNewPatientBtn.addClickListener(event -> {
	    entryDetailsWindow(); //help method
	});
	adDel.addComponent(addNewPatientBtn);
	adDel.setComponentAlignment(addNewPatientBtn, Alignment.MIDDLE_LEFT);

	// button: edit patient
	adDel.addComponent(editPatientXtraBtn);
	adDel.setComponentAlignment(editPatientXtraBtn, Alignment.MIDDLE_CENTER);

	/*
	 * button: delete person
	 *
	 * Actually, the patient is not deleted from the DB as such, only
	 * from the visitors view.
	 */
	adDel.addComponent(deletePatientBtn);
	adDel.setComponentAlignment(deletePatientBtn, Alignment.MIDDLE_RIGHT);
	adDel.setWidth("100%");
	adDel.setHeight("100%");
	this.addComponent(adDel);
	this.setExpandRatio(adDel, 0.1f);
    }

    /**
     * help method to bind patient to person in general and show the phone number
     *
     * @param patient
     */
    private void showPatientNumber(Patient patient) {

	PhoneComponent component = new PhoneComponent(patient);
	component.getClass();
    }

    /**
     * help method to connect to the PatientView class
     *
     * @param patient
     */
    private void showDetailsWindow(Patient patient) {

	HealthVisUI.setMainView(new PatientView(patient));
    }

    /**
     * help method to entry a new patient data
     */
    private void entryDetailsWindow() {

	final FormLayout formLayout = new FormLayout();

	// add person details
	TextField firstName = new TextField("first name");
	formLayout.addComponent(firstName);
	TextField lastName = new TextField("last name");
	formLayout.addComponent(lastName);
	PopupDateField birthday = new PopupDateField("birthday");
	birthday.setDateFormat("yyyy-MM-dd");
	formLayout.addComponent(birthday);
	TextField street = new TextField("street");
	formLayout.addComponent(street);
	TextField zip = new TextField("zip");
	formLayout.addComponent(zip);
	TextField city = new TextField("city");
	formLayout.addComponent(city);
	TextField phoneNumber = new TextField("phone number");
	formLayout.addComponent(phoneNumber);

	// button: save
	Button saveButn = new Button("save");
	saveButn.addClickListener(event -> {
	    Patient patient = new Patient();
	    PatientModel patientModel = new PatientModel();
	    patient.setFirstname(firstName.getValue());
	    patient.setLastname(lastName.getValue());
	    patient.setBirthday(birthday.getValue());
	    patient.setActive(true);	// set new to true by default

	    // Contact as Entity: create object needed (for other attributes)
	    Contact contact = new Contact();
	    ContactModel contactModel = new ContactModel();
	    contact.setStreet(street.getValue());
	    contact.setZip(zip.getValue());
	    contact.setCity(city.getValue());
	    contact.setPhoneNumber(phoneNumber.getValue());

	    // update all
	    patient.setContact(contact);
	    contactModel.saveOrUpdate(contact);	// save in DB and give ID
	    patientModel.saveOrUpdate(patient); // get's ID from contact

	    updateVisitor(patient);

	    Notification notif = new Notification("..saved!", Notification.Type.WARNING_MESSAGE);
	    notif.setDelayMsec(2000);
	    getUI().setPollInterval(1000);	// poll the vaadin server for any changes
	    notif.setPosition(Position.MIDDLE_CENTER);
	    notif.show(Page.getCurrent());
	});
	formLayout.setMargin(true);
	formLayout.addComponent(saveButn);

	// pop-up window for the new patient's entries
	final Window window = new Window();
	window.setWidth(1000.0f, Unit.PIXELS);
	window.center();
	formLayout.setMargin(true);
	window.setContent(formLayout);
	UI.getCurrent().addWindow(window);
    }

    /**
     * help method save und update visitor
     *
     * @param patient
     */
    private void updateVisitor(Patient patient) {

	HealthVisitorsModel visitorsModel = new HealthVisitorsModel();
	VisitsModel visitsModel = new VisitsModel();

	// bind the patient to the visitor
	Visit newVisit = new Visit();
	newVisit.setPatient(patient);
	newVisit.setVisitor(visitor);
	visits.add(newVisit);

	// both visitor models need to be updated
	visitsModel.saveOrUpdate(newVisit);
	visitorsModel.saveOrUpdate(visitor);
    }

    /**
     * method for the HealthVisUI
     *
     * @return
     */
    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
