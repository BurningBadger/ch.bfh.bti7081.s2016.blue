package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.model.PatientModel;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.awt.image.BufferedImage;
import java.io.*;


public class PatientView extends VerticalLayout implements View {

    public static final String NAME = "Patient"; // variable for HealthVisUI

    public PatientView(Patient patient) {

	this.setSizeFull();
	this.setMargin(true);

	// 1st row
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
	//	butMenu.addStyleName("btntestclass");
	//	butCall.addStyleName("btntestclass");
	firstLay.setWidth("100%");
	firstLay.setHeight("100%");
	this.addComponent(firstLay);

	// 2nd row horizontal splitted
	HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
	hsplit.addStyleName("invisiblesplitter");
	hsplit.setSplitPosition(40, Unit.PERCENTAGE);
	/*
	 left side
	  */
	AbsoluteLayout left = new AbsoluteLayout();
//	Image picture = new Image(null, new ThemeResource("icons/Guy.png"));  // for testing
	/**
	 * image handling
	 */
	Upload upload = new Upload("Upload here", null);
	upload.setButtonCaption("Upload");
	final Image picture = new Image("Uploaded image");
	picture.setVisible(false);

	// create upload stream
	final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// first implement the Upload.Receiver interface
	upload.setReceiver(new Upload.Receiver() {
	    @Override
	    public OutputStream receiveUpload(String filename, String mimeType) {
		return baos;	//return the output stream
	    }
	});
	upload.addSucceededListener(new Upload.SucceededListener() {
	    @Override
	    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
		final byte[] bytes = baos.toByteArray();
		picture.setVisible(true);
		picture.setSource(new StreamResource(new StreamResource.StreamSource() {
		    @Override
		    public InputStream getStream() {
			return new ByteArrayInputStream(bytes);
		    }
		},""));
	    }
	});

	// resize the image
	picture.setWidth("60%");
	left.addComponent(picture, "left: 30px; top: 30px;");
	left.addComponent(upload, "left: 30px; top: 400px;");

	hsplit.setFirstComponent(left);
	/*
	 right side
	  */
	AbsoluteLayout right = new AbsoluteLayout();

	TextField firstName = new TextField();
	firstName.setConvertedValue(patient.getFirstname());
	right.addComponent(firstName, "left: 10px; top: 20px;");
	TextField lastName = new TextField();
	lastName.setConvertedValue(patient.getLastname());
	right.addComponent(lastName, "left: 10px; top: 70px;");
	PopupDateField birthday = new PopupDateField();
	birthday.setDateFormat("yyyy-MM-dd");
	birthday.setValue(patient.getBirthday());
	right.addComponent(birthday, "left: 10px; top: 120px;");
	TextField street = new TextField();
	street.setConvertedValue(patient.getContact().getStreet());
	right.addComponent(street, "left: 10px; top: 170px;");
	TextField zip = new TextField();
	zip.setConvertedValue(patient.getContact().getZip());
	right.addComponent(zip, "left: 10px; top: 220px;");
	TextField city = new TextField();
	city.setConvertedValue(patient.getContact().getCity());
	right.addComponent(city, "left: 10px; top: 270px;");
	TextField phoneNumber = new TextField();
	phoneNumber.setConvertedValue(patient.getContact().getPhoneNumber());
	right.addComponent(phoneNumber, "left: 10px; top: 320px;");

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
	right.addComponent(savButn, "left: 10px; top: 390px;");

	Button back = new Button("back");
	back.addClickListener(event -> {
	    getUI().getNavigator().navigateTo(PatientListView.getName());
	});
	right.addComponent(back, "left: 120; top: 390px");

	hsplit.setSecondComponent(right);
	hsplit.setWidth("100%");
	hsplit.setHeight("100%");
	this.addComponent(hsplit); // add both to the row of the vertical layout

	// panel with buttons
	HorizontalSplitPanel buttonLay = new HorizontalSplitPanel();
	buttonLay.setSplitPosition(50, Unit.PERCENTAGE);
	/*
	 left side
	  */
	AbsoluteLayout buttonLeftLay = new AbsoluteLayout();
	Button butBesuche = new Button();
	buttonLeftLay.addComponent(new Image(null, new ThemeResource("icons/double-cutted-circle-120x120.png")),
		"left:180; top: 50px;");
	butBesuche.setDescription("Patient Visits");
	butBesuche.addClickListener(e -> {});

	Button butAnmerkungen = new Button("Anmerkungen");
	buttonLeftLay.addComponent(new Image(null, new ThemeResource("icons/double-cutted-circle-120x120.png")),
		"left:180; top: 250px;");
	butAnmerkungen.setDescription("Notes");
	butAnmerkungen.addClickListener(e -> {});
	buttonLay.setFirstComponent(buttonLeftLay);
	/*
	 right side
	  */
	AbsoluteLayout buttonRightLay = new AbsoluteLayout();
	Button butThree = new Button();
	buttonRightLay.addComponent(new Image(null, new ThemeResource("icons/double-cutted-circle-120x120.png")),
		"right:180; top: 50px;");
	butThree.setDescription("Choice 3");
	butThree.addClickListener(e -> {});

	Button butFour = new Button();
	buttonRightLay.addComponent(new Image(null, new ThemeResource("icons/double-cutted-circle-120x120.png")),
		"right:180; top: 250px;");
	butFour.setDescription("Emergency Contact");
	butFour.addClickListener(e -> {
//	    EmergencyContactView emergency = new EmergencyContactView();
//	    emergency
	});
	buttonLay.setWidth("100%");
	buttonLay.setHeight("100%");
	buttonLay.setSecondComponent(buttonRightLay);
	this.addComponent(buttonLay);
	this.setExpandRatio(firstLay, 0.1f);	//take 10% of the frame
	this.setExpandRatio(hsplit, 0.5f);
	this.setExpandRatio(buttonLay, 0.4f);
    }

    // method for the HealthVisUI
    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}