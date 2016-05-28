package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.model.PatientModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;


public class PatientView extends VerticalLayout implements View {

    private static final String NAME = "Patient"; // variable for HealthVisUI

    public PatientView(Patient patient) {

	setSizeFull();

	// 1st row
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
	firstLay.setHeight("30%");
	this.addComponent(firstLay);

	// 2nd row horizontal splitted
	HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
	hsplit.addStyleName("invisibleSplitter");
//	hsplit.setHeight(5, Unit.CM);
	hsplit.setSplitPosition(40, Unit.PERCENTAGE);
	/*
	 left side
	  */
	AbsoluteLayout left = new AbsoluteLayout();
	Image picture = new Image(null, new ThemeResource("icons/Guy.png"));  // for testing
	left.addComponent(picture, "left: 30px; top: 20px;");
	// left.setMargin(true);
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
	});
	right.addComponent(savButn, "left: 10px; top: 390px;");

	hsplit.setSecondComponent(right);
	this.addComponent(hsplit); // add both to the row of the vertical layout

	// panel with buttons
	HorizontalSplitPanel buttonLay = new HorizontalSplitPanel();
	buttonLay.setSplitPosition(50, Unit.PERCENTAGE);

	/*
	 left side
	  */
	AbsoluteLayout buttonLeftLay = new AbsoluteLayout();
	Button butBesuche = new Button("Besuche");
	buttonLeftLay.addComponent(new Image(null, new ThemeResource("icons/double-cutted-circle-120x120.png")),
		"left:60; top: 50px;");
	butBesuche.addClickListener(e -> {});

	Button butAnmerkungen = new Button("Anmerkungen");
	buttonLeftLay.addComponent(new Image(null, new ThemeResource("icons/double-cutted-circle-120x120.png")),
		"left:60; top: 190px;");
	butAnmerkungen.addClickListener(e -> {});
	buttonLay.setFirstComponent(buttonLeftLay);

	/*
	 right side
	  */
	AbsoluteLayout buttonRightLay = new AbsoluteLayout();
	Button butThree = new Button("Choice 3");
	buttonRightLay.addComponent(new Image(null, new ThemeResource("icons/double-cutted-circle-120x120.png")),
		"right:60; top: 50px;");
	butThree.addClickListener(e -> {});

	Button butFour = new Button("Choice 4");
	buttonRightLay.addComponent(new Image(null, new ThemeResource("icons/double-cutted-circle-120x120.png")),
		"right:60; top: 190px;");
	butFour.addClickListener(e -> {});
	buttonLay.setSecondComponent(buttonRightLay);
	this.addComponent(buttonLay);
    }

    // method for the HealthVisUI
    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}