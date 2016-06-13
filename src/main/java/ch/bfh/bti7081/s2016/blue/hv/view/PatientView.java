package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.components.PhoneComponent;
import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.model.PatientModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.io.*;

/**
 * The PatientView class specify the already selected patient treated by the logged-in Health visitor.
 * The main part of the class are the details of the patient which are shown in the Textfields. The user
 * can manipulate these entries directly by selecting each one of them. Additionally can he upload an
 * image for better profiling. All changed information is directly saved into the database.
 *
 * Beside the <<back>>- and the <<call>>-button which the user can return to the previous view, resp. copy
 * the phone number of the patient with, there are four main buttons at the bottom of the screen. These
 * connect the patient entity with his most necessary tasks which are crucial for the Health visitor.
 */

public class PatientView extends VerticalLayout implements View {

    public static final String NAME = "Patient"; // variable for HealthVisUI

    public PatientView(Patient patient) {

	this.setSizeFull();
	this.setMargin(true);

	// show the vertical layout parts
	showFirstRow(patient);
	showSplittedRow(patient);
	showBottomButtons(patient);
    }

    /**
     * vertical layout: 1st row buttons
     *
     * @param patient
     */
    private void showFirstRow(Patient patient) {

	HorizontalLayout firstLay = new HorizontalLayout();

	// button: home
	Button butMenu = new Button("Home");
	butMenu.addClickListener(event -> {
	    this.detach();
	    getUI().getNavigator().navigateTo(LandingView.getName());
	    this.removeAllComponents();
	});
	firstLay.addComponent(butMenu);
	firstLay.setComponentAlignment(butMenu, Alignment.MIDDLE_LEFT);

	//button: call
	Button butCall = new Button("Call");
	butCall.addClickListener(event -> {
	    showPatientsNumber(patient);
	});
	firstLay.addComponent(butCall);
	firstLay.setComponentAlignment(butCall, Alignment.MIDDLE_RIGHT);
	firstLay.setMargin(true);
	firstLay.setWidth("100%");
	firstLay.setHeight("100%");
	this.addComponent(firstLay);
	this.setExpandRatio(firstLay, 0.1f);	//take 10% of the frame
    }

    /**
     * vertical layout: 2nd row horizontal splitted
     *
     * can't use methods as components of the horizontal split! Because of this the code
     * structure can't be divided.
     *
     * @param patient
     */
    private void showSplittedRow(Patient patient) {

	// split the horizontal layout
	HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
	hsplit.addStyleName("invisiblesplitter");	// use CSS
	hsplit.setSplitPosition(40, Unit.PERCENTAGE);

	/*
	 * left split side: image
	 */
	VerticalLayout left = new VerticalLayout();
	left.setMargin(true);

	// image handling: Upload component
	Upload upload = new Upload("Upload here", null);
	upload.setButtonCaption("Upload");
	final Image picture = new Image();

	/*
	 * handle picture: get the picture as byteArray from the DB
	 *
	 * without try/catch the image will not be shown properly
	 */
	byte[] bas = patient.getPicture();
	if (bas != null) {
	    try {
		picture.setSource(new StreamResource((StreamResource.StreamSource) () ->
				new ByteArrayInputStream(bas), ""));
		picture.setVisible(true);
	    } catch (Exception e) {
		picture.setVisible(false);
	    }
	}

	// create upload stream
	final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// first implement the Upload.Receiver interface
	upload.setReceiver((Upload.Receiver) (filename, mimeType) -> {
	    return baos;        //return the output stream
	});
	upload.addSucceededListener((Upload.SucceededListener) succeededEvent -> {
	    final byte[] bytes = baos.toByteArray();
	    picture.setVisible(true);
	    picture.setSource(new StreamResource((StreamResource.StreamSource) () ->
			    new ByteArrayInputStream(bytes), ""));
	    patient.setPicture(bytes);
	});

	picture.setWidth("90%");	// resize the image
	left.addComponent(picture);
	left.setExpandRatio(picture, 0.8f);	// set the ratio to 80%
	left.addComponent(upload);
	left.setExpandRatio(upload, 0.2f);
	hsplit.setFirstComponent(left);

	/*
	 * right split side: Details editing
	 */
	VerticalLayout right = new VerticalLayout();
	right.setSizeFull();
	right.setMargin(true);

	// didn't want to declare these in the global scope
	TextField firstName = new TextField();
	firstName.setWidth(10, Unit.CM);
	firstName.setConvertedValue(patient.getFirstname());
	right.addComponent(firstName);
	TextField lastName = new TextField();
	lastName.setWidth(10, Unit.CM);
	lastName.setConvertedValue(patient.getLastname());
	right.addComponent(lastName);
	PopupDateField birthday = new PopupDateField();
	birthday.setWidth(10, Unit.CM);
	birthday.setDateFormat("yyyy-MM-dd");
	birthday.setValue(patient.getBirthday());
	right.addComponent(birthday);
	TextField street = new TextField();
	street.setWidth(10, Unit.CM);
	street.setConvertedValue(patient.getContact().getStreet());
	right.addComponent(street);
	TextField zip = new TextField();
	zip.setWidth(10, Unit.CM);
	zip.setConvertedValue(patient.getContact().getZip());
	right.addComponent(zip);
	TextField city = new TextField();
	city.setWidth(10, Unit.CM);
	city.setConvertedValue(patient.getContact().getCity());
	right.addComponent(city);
	TextField phoneNumber = new TextField();
	phoneNumber.setWidth(10, Unit.CM);
	phoneNumber.setConvertedValue(patient.getContact().getPhoneNumber());
	right.setWidth("100%");
	right.addComponent(phoneNumber);

	// right split side: bottom buttons
	HorizontalLayout bottomButtons = new HorizontalLayout();
	bottomButtons.setSpacing(true);

	/*
	 * because the patient's attributes are not declared in the global scope
	 * is the lambda expression a bit longer
	 */
	// save button
	Button updateBtn = new Button("update");
	updateBtn.addClickListener(event -> {
	    PatientModel patMod = new PatientModel();
	    patient.setFirstname(firstName.getValue());	// the patient is already in DB, so no need to
	    patient.setLastname(lastName.getValue());	// build a new contact object
	    patient.setBirthday(birthday.getValue());
	    patient.getContact().setStreet(street.getValue());
	    patient.getContact().setZip(zip.getValue());
	    patient.getContact().setCity(city.getValue());
	    patient.getContact().setPhoneNumber(phoneNumber.getValue());
	    patMod.saveOrUpdate(patient);
	    Notification notif = new Notification("..updated!", Notification.Type.WARNING_MESSAGE);
	    notif.setDelayMsec(2000);
//	    getUI().setPollInterval(1000);
	    notif.setPosition(Position.MIDDLE_CENTER);
	    notif.show(Page.getCurrent());
	});
	bottomButtons.addComponent(updateBtn);

	// cancel button
	Button back = new Button("cancel");
	back.addClickListener(event -> {
	    this.detach();
	    getUI().getNavigator().navigateTo(PatientListView.getName());
	});
	bottomButtons.addComponent(back);
	bottomButtons.setWidth("40%");
	right.addComponent(bottomButtons);

	hsplit.setSecondComponent(right);
	hsplit.setWidth("100%");
	hsplit.setHeight("100%");

	this.addComponent(hsplit); // add both splits as the hor. row to the vertical layout
	this.setExpandRatio(hsplit, 0.5f);
    }

    /**
     * vertical layout: bottom buttons
     *
     * @param patient
     */
    private void showBottomButtons(Patient patient) {

	GridLayout grid = new GridLayout(2, 2);
	grid.setSizeFull();
	grid.setWidth("100%");
	grid.setMargin(true);

	/*
	 * left side
	 */
	// image as button: show all patient's visits
	Image butBesuche = new Image();
	butBesuche.setSource(new ThemeResource("icons/double-cutted-circle-120x120.png"));
	butBesuche.setDescription("Patient Visits");
	butBesuche.addClickListener(event -> {
	    showPatientVisits(patient);
	});
	grid.addComponent(butBesuche, 0, 0);
	grid.setComponentAlignment(butBesuche, Alignment.TOP_LEFT);

	// image as button: show notes
	Image butAnmerkungen = new Image();
	butAnmerkungen.setSource(new ThemeResource("icons/double-cutted-circle-120x120.png"));
	butAnmerkungen.setDescription("Notes");
	butAnmerkungen.addClickListener(event -> {
	    // TODO
	});
	grid.addComponent(butAnmerkungen, 0, 1);
	grid.setComponentAlignment(butAnmerkungen, Alignment.BOTTOM_LEFT);

	/*
	 * right side
	 */
	// image as button: show patient's emergency contact
	Image butEmergencyContact = new Image();
	butEmergencyContact.setSource(new ThemeResource("icons/double-cutted-circle-120x120.png"));
	butEmergencyContact.setDescription("Emergency contact");
	butEmergencyContact.addClickListener(event -> {
	    showEmergencyContact(patient);
	});
	grid.addComponent(butEmergencyContact, 1, 0);
	grid.setComponentAlignment(butEmergencyContact, Alignment.TOP_RIGHT);

	// image as button: show patient's drugs
	Image butOrderDrugs = new Image();
	butOrderDrugs.setSource(new ThemeResource("icons/double-cutted-circle-120x120.png"));
	butOrderDrugs.setDescription("Order drugs");
	butOrderDrugs.addClickListener(event -> {
	    showPatientsDrugs();
	});
	grid.addComponent(butOrderDrugs, 1, 1);
	grid.setComponentAlignment(butOrderDrugs, Alignment.BOTTOM_RIGHT);

	this.addComponent(grid);
	this.setExpandRatio(grid, 0.4f);
    }

    /**
     * help method: bind patient to person in general and show the phone number
     *
     * @param patient
     */
    private void showPatientsNumber(Patient patient) {

	PhoneComponent component = new PhoneComponent(patient);
	component.getClass();
    }

    /**
     * help method to connect with the Patient Visits
     *
     * @param patient
     */
    private void showPatientVisits(Patient patient) {

	this.detach();
	HealthVisUI.setMainView(new PatientVisitHistoryListView(patient.getId(),"Patients"));
    }

    /**
     * help method to show patient's emergency contact
     *
     * @param patient
     */
    private void showEmergencyContact(Patient patient) {

	this.detach();
	HealthVisUI.setMainView(new EmergencyContactView(patient.getId(),"Patients"));
    }

    /**
     * help method to show patient's drugs
     */
    private void showPatientsDrugs() {

	this.detach();
	HealthVisUI.setMainView(new DrugsOrderView());
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