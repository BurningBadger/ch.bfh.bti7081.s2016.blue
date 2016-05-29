package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.hv.entities.Contact;
import ch.bfh.bti7081.s2016.blue.hv.model.ContactModel;
import ch.bfh.bti7081.s2016.blue.hv.model.PatientModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;

public class PatientListView extends VerticalLayout implements View {

    private static final String NAME = "Patients"; // variable for HealthVisUI

    public PatientListView() {

	// define background panel
	Panel panel = new Panel();
	panel.setSizeFull();

	// vertical layout: 1st row
	HorizontalLayout firstLay = new HorizontalLayout();
	Button butMenu = new Button("Menu");
	firstLay.addComponent(butMenu);
	firstLay.setComponentAlignment(butMenu, Alignment.MIDDLE_LEFT);
	Button butCall = new Button("Call");
	firstLay.addComponent(butCall);
	firstLay.setComponentAlignment(butCall, Alignment.MIDDLE_RIGHT);
	firstLay.setMargin(true);
//	butMenu.addStyleName("btntestclass");
//	butCall.addStyleName("btntestclass");
	firstLay.setWidth("100%");
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
	horizontalTage.setMargin(true);
//	butGestern.addStyleName("btntestclass");
//	butHeute.addStyleName("btntestclass");
//	butMorgen.addStyleName("btntestclass");
	horizontalTage.setWidth("100%");
	this.addComponent(horizontalTage);

	// vertical layout: table row
	final Table table = new Table();
	table.setWidth("100%");
	table.addStyleName("components-inside");

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
	this.setMargin(true);

	// button to add patient
	Button addNewPatientBtn = new Button("add new patient");
	addNewPatientBtn.addClickListener(event -> {
	    entryDetailsWindow();
	});
	this.addComponent(addNewPatientBtn);

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
	horizontalPages.setMargin(true);
//	butTermine.addStyleName("btntestclass");
//	butRoute.addStyleName("btntestclass");
//	butKalender.addStyleName("btntestclass");
	horizontalPages.setWidth("100%");
	this.addComponent(horizontalPages);
	panel.setContent(this);
    }

    // help method to connect to the PatientView class
    private void showDetailsWindow(Patient patient) {
	PatientView showPatient = new PatientView(patient);
	final Window window = new Window();
	window.setSizeFull();
//	window.center();
	window.setContent(showPatient);
	UI.getCurrent().addWindow(window);
    }

    // help method to entry a new patient data
    public void entryDetailsWindow() {
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

	    Contact pContact = new Contact();
	    ContactModel contactModel = new ContactModel();
	    pContact.setStreet(street.getValue());
	    pContact.setZip(zip.getValue());
	    pContact.setCity(city.getValue());
	    pContact.setPhoneNumber(phoneNumber.getValue());

	    patient.setContact(pContact);
	    contactModel.saveOrUpdate(pContact);

	    patMod.saveOrUpdate(patient);
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
