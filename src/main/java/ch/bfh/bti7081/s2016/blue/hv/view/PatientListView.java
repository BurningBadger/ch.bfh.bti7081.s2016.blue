package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.hv.entities.Contact;
import ch.bfh.bti7081.s2016.blue.hv.model.ContactModel;
import ch.bfh.bti7081.s2016.blue.hv.model.PatientModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;


public class PatientListView extends VerticalLayout implements View {

    private static final String NAME = "Patients"; // variable for HealthVisUI

    public PatientListView() {

	this.setSizeFull();
	this.setMargin(true);

	// vertical layout: 1st row
	HorizontalLayout firstLay = new HorizontalLayout();
	Button butMenu = new Button("Menu");
	firstLay.addComponent(butMenu);
	firstLay.setComponentAlignment(butMenu, Alignment.MIDDLE_LEFT);
	Button butCall = new Button("Call");
	firstLay.addComponent(butCall);
	firstLay.setComponentAlignment(butCall, Alignment.MIDDLE_RIGHT);
	firstLay.setWidth("100%");
	firstLay.setHeight("100%");
	this.addComponent(firstLay);

	// vertical layout: 2nd row
	HorizontalLayout horizontalTage = new HorizontalLayout();
	Button butGestern = new Button("Gestern");
	horizontalTage.addComponent(butGestern);
	horizontalTage.setComponentAlignment(butGestern, Alignment.MIDDLE_LEFT);
	Button butHeute = new Button("Heute");
	horizontalTage.addComponent(butHeute);
	horizontalTage.setComponentAlignment(butHeute, Alignment.MIDDLE_CENTER);
	Button butMorgen = new Button("Morgen");
	horizontalTage.addComponent(butMorgen);
	horizontalTage.setComponentAlignment(butMorgen, Alignment.MIDDLE_RIGHT);
	horizontalTage.setWidth("100%");
	horizontalTage.setHeight("100%");
	this.addComponent(horizontalTage);

	// vertical layout: table row
	final Table table = new Table();
	table.setWidth("100%");
	table.setHeight("100%");
	table.addStyleName("components-inside");
	table.setSelectable(true);
	table.setImmediate(true);

	// define the columns
	table.addContainerProperty("first name", Label.class, null);
	table.addContainerProperty("last name", Label.class,  null);
	table.addContainerProperty("birthday", Label.class, null);
	table.addContainerProperty("", Button.class, null);

	// connect with the DB and add data into the table
	PatientModel patientModel = new PatientModel();

	// show all elements
	for (Patient patient : patientModel.findAll()) {

	    Label firstName = new Label(patient.getFirstname());
	    Label lastName = new Label(patient.getLastname());
	    Label birthday = new Label(String.valueOf(patient.getBirthday()));

	    Button detailsBtn = new Button("show details");
	    detailsBtn.setData(patient);
	    detailsBtn.addClickListener(event -> {
		Patient pat = (Patient) event.getButton().getData();
		showDetailsWindow(pat);
	    });
	    detailsBtn.addStyleName("link");

	    // Create the table row
	    table.addItem(new Object[] {firstName, lastName, birthday, detailsBtn},
			    patient.getId());
	}
	this.addComponent(table);

	// buttons to add/del patient
	HorizontalLayout adDel = new HorizontalLayout();
	Button addNewPatientBtn = new Button("add new patient");
	addNewPatientBtn.addClickListener(event -> {
	    entryDetailsWindow();
	});
	adDel.addComponent(addNewPatientBtn);
	adDel.setComponentAlignment(addNewPatientBtn, Alignment.TOP_LEFT);
	Button deletePatientBtn = new Button("delete patient");
	deletePatientBtn.addClickListener(event -> {
	    Object selected = table.getValue();	//table entry is already an object

	    if (selected == null) {
		Notification.show("Please select an item!");
	    }
	    else {
		Notification.show("Person : " + selected + "removed!");
		patientModel.delete(patientModel.findById((Long)selected));
		table.removeItem(selected);
	    }
	});
	adDel.addComponent(deletePatientBtn);
	adDel.setComponentAlignment(deletePatientBtn, Alignment.TOP_RIGHT);
	adDel.setWidth("100%");
	adDel.setHeight("100%");
	this.addComponent(adDel);

	// last row
	HorizontalLayout horizontalPages = new HorizontalLayout();
	Button butTermine = new Button("Termine");
	horizontalPages.addComponent(butTermine);
	horizontalPages.setComponentAlignment(butTermine, Alignment.MIDDLE_LEFT);

	Button butRoute = new Button("Route");
	horizontalPages.addComponent(butRoute);
	horizontalPages.setComponentAlignment(butRoute, Alignment.MIDDLE_CENTER);

	Button butKalender = new Button("Kalender");
	horizontalPages.addComponent(butKalender);
	horizontalPages.setComponentAlignment(butKalender, Alignment.MIDDLE_RIGHT);
	horizontalPages.setWidth("100%");
	horizontalPages.setHeight("100%");
	this.addComponent(horizontalPages);
	this.setExpandRatio(firstLay, 0.1f);		//10% of the mainframe
	this.setExpandRatio(horizontalTage, 0.1f);
	this.setExpandRatio(table, 0.65f);
	this.setExpandRatio(adDel, 0.05f);
	this.setExpandRatio(horizontalPages, 0.1f);
    }

    // help method to connect to the PatientView class
    private void showDetailsWindow(Patient patient) {
	PatientView showPatient = new PatientView(patient);
	final Window window = new Window();
	window.setSizeFull();
	window.setContent(showPatient);
	UI.getCurrent().addWindow(window);
//	getUI().getNavigator().navigateTo(PatientView.NAME);
    }

    // help method to entry a new patient data
    private void entryDetailsWindow() {
	final FormLayout formLayout = new FormLayout();

	TextField firstName = new TextField("first name");
	//	firstName.setRequired(true);
	//	firstName.addValidator(new NullValidator("Must enter the first name!", false));
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

	Button saveButn = new Button("save");
	saveButn.addClickListener(event -> {
	    Patient patient = new Patient();
	    PatientModel patMod = new PatientModel();
	    patient.setFirstname(firstName.getValue());
	    patient.setLastname(lastName.getValue());
	    patient.setBirthday(birthday.getValue());

	    // Contact as Entity: create object needed
	    Contact pContact = new Contact();
	    ContactModel contactModel = new ContactModel();
	    pContact.setStreet(street.getValue());
	    pContact.setZip(zip.getValue());
	    pContact.setCity(city.getValue());
	    pContact.setPhoneNumber(phoneNumber.getValue());

	    patient.setContact(pContact);
	    contactModel.saveOrUpdate(pContact);
	    patMod.saveOrUpdate(patient);

	    Notification notif = new Notification("..saved!", Notification.Type.WARNING_MESSAGE);
	    notif.setDelayMsec(2000);
	    notif.setPosition(Position.MIDDLE_CENTER);
	    notif.show(Page.getCurrent());
	});
	formLayout.setMargin(true);
	formLayout.addComponent(saveButn);

	final Window window = new Window();
	window.setWidth(1000.0f, Unit.PIXELS);
	window.center();
	formLayout.setMargin(true);
	window.setContent(formLayout);
	UI.getCurrent().addWindow(window);
    }

    // method for the HealthVisUI
    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
