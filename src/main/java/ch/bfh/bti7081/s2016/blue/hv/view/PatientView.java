package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.model.PatientModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.io.*;


public class PatientView extends VerticalLayout implements View {

    public static final String NAME = "Patient"; // variable for HealthVisUI

    public PatientView(Patient patient) {

	this.setSizeFull();
	this.setMargin(true);

	// show layout parts
	showFirstRow();
	showSplittedRow(patient);
	showBottomButtons(patient);
    }

    // vertical layout: 1st row buttons
    private void showFirstRow() {

	HorizontalLayout firstLay = new HorizontalLayout();
	Button butMenu = new Button("Menu");
	butMenu.addClickListener(event -> {
	    getUI().getNavigator().navigateTo(PatientListView.getName());
	});
	firstLay.addComponent(butMenu);
	firstLay.setComponentAlignment(butMenu, Alignment.MIDDLE_LEFT);
	Button butCall = new Button("Call");
	firstLay.addComponent(butCall);
	firstLay.setComponentAlignment(butCall, Alignment.MIDDLE_RIGHT);
	firstLay.setMargin(true);
	firstLay.setWidth("100%");
	firstLay.setHeight("100%");
	this.addComponent(firstLay);
	this.setExpandRatio(firstLay, 0.1f);	//take 10% of the frame
    }

    // vertical layout: 2nd row horizontal splitted
    private void showSplittedRow(Patient patient) {

	HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
	hsplit.addStyleName("invisiblesplitter");
	hsplit.setSplitPosition(40, Unit.PERCENTAGE);

	/*
	 * left split side: image
	 */
	VerticalLayout left = new VerticalLayout();
//	left.setSizeFull();
	left.setMargin(true);

	// image handling
	Upload upload = new Upload("Upload here", null);
	upload.setButtonCaption("Upload");
	final Image picture = new Image();

	// handle picture: get the picture from DB
	byte[] bas = patient.getPicture();
	if (bas != null) {
	    try {
		picture.setSource(new StreamResource((StreamResource.StreamSource) () -> new ByteArrayInputStream(bas), ""));
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
	    picture.setSource(new StreamResource((StreamResource.StreamSource) () -> new ByteArrayInputStream(bytes), ""));
	    patient.setPicture(bytes);
	});

	picture.setWidth("90%");	// resize the image
	left.addComponent(picture);
	left.setExpandRatio(picture, 0.8f);
	left.addComponent(upload);
	left.setExpandRatio(upload, 0.2f);
	hsplit.setFirstComponent(left);

	/*
	 * right split side: Details editing
	 */
	VerticalLayout right = new VerticalLayout();
	right.setSizeFull();
	right.setMargin(true);

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

	// save button
	Button savButn = new Button("save");
	savButn.addClickListener(event -> {
	    PatientModel patMod = new PatientModel();
	    patient.setFirstname(firstName.getValue());
	    patient.setLastname(lastName.getValue());
	    patient.setBirthday(birthday.getValue());
	    patient.getContact().setStreet(street.getValue());
	    patient.getContact().setZip(zip.getValue());
	    patient.getContact().setCity(city.getValue());
	    patient.getContact().setPhoneNumber(phoneNumber.getValue());
	    patMod.saveOrUpdate(patient);
	    Notification notif = new Notification("..saved!", Notification.Type.WARNING_MESSAGE);
	    notif.setDelayMsec(2000);
	    notif.setPosition(Position.MIDDLE_CENTER);
	    notif.show(Page.getCurrent());
	});
	bottomButtons.addComponent(savButn);

	// return button
	Button back = new Button("cancel");
	back.addClickListener(event -> {
	    this.replaceComponent(this, new PatientListView());
//	    getUI().getNavigator().navigateTo(PatientListView.getName());
	});
	bottomButtons.addComponent(back);
	bottomButtons.setWidth("40%");
	right.addComponent(bottomButtons);

	hsplit.setSecondComponent(right);
	hsplit.setWidth("100%");
	hsplit.setHeight("100%");

	this.addComponent(hsplit); // add both to the row of the vertical layout
	this.setExpandRatio(hsplit, 0.5f);
    }

    // vertical layout: bottom buttons
    private void showBottomButtons(Patient patient) {

	GridLayout grid = new GridLayout(2, 2);
	grid.setSizeFull();
	grid.setWidth("100%");
	grid.setMargin(true);

	/*
	 * left side
	 */
	Image butBesuche = new Image();
	butBesuche.setSource(new ThemeResource("icons/double-cutted-circle-120x120.png"));
	butBesuche.setDescription("Patient Visits");
	butBesuche.addClickListener(e -> {
	    // TODO
	    showPatientVisits(patient);
	});
	grid.addComponent(butBesuche, 0, 0);
	grid.setComponentAlignment(butBesuche, Alignment.TOP_LEFT);

	Image butAnmerkungen = new Image();
	butAnmerkungen.setSource(new ThemeResource("icons/double-cutted-circle-120x120.png"));
	butAnmerkungen.setDescription("Notes");
	butAnmerkungen.addClickListener(e -> {
	    // TODO
	});
	grid.addComponent(butAnmerkungen, 0, 1);
	grid.setComponentAlignment(butAnmerkungen, Alignment.BOTTOM_LEFT);
	/*
	 * right side
	 */
	Image butOrderDrugs = new Image();
	butOrderDrugs.setSource(new ThemeResource("icons/double-cutted-circle-120x120.png"));
	butOrderDrugs.setDescription("Order drugs");
	butOrderDrugs.addClickListener(e -> {});
	grid.addComponent(butOrderDrugs, 1, 0);
	grid.setComponentAlignment(butOrderDrugs, Alignment.TOP_RIGHT);

	Image butEmergencyContact = new Image();
	butEmergencyContact.setSource(new ThemeResource("icons/double-cutted-circle-120x120.png"));
	butEmergencyContact.setDescription("Order drugs");
	butEmergencyContact.addClickListener(e -> {});
	grid.addComponent(butEmergencyContact, 1, 1);
	grid.setComponentAlignment(butEmergencyContact, Alignment.BOTTOM_RIGHT);

	this.addComponent(grid);
	this.setExpandRatio(grid, 0.4f);
    }

    // help class to connect with the Patient Visits
    private void showPatientVisits(Patient patient) {
	PatientVisitHistoryListView patientVisitHistory = new PatientVisitHistoryListView(patient.getId());
	final Window window = new Window();
	window.setSizeFull();
	window.setContent(patientVisitHistory);
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