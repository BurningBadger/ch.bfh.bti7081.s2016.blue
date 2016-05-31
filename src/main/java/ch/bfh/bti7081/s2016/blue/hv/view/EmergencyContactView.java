package ch.bfh.bti7081.s2016.blue.hv.view;

import java.util.List;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import ch.bfh.bti7081.s2016.blue.hv.entities.EmergencyContact;
import ch.bfh.bti7081.s2016.blue.hv.model.EmergencyContactModel;

/**
 * Created by suttc1 on 22.05.2016.
 */

public class EmergencyContactView extends VerticalLayout implements View {

    private static final long serialVersionUID = -3931584070955988897L;

    // Buttons headLine
    private static final String NAME = "EmergencyContactView";

    private long emergencyContactID;

    // header

    private HorizontalLayout header;

    // middle

    // footers
    private HorizontalLayout footerView;
    private HorizontalLayout footerEdit;

    private Button menuBut = new Button("Menu");
    private Button backBut = new Button("Back");
    private Button callBut = new Button("CALL");
    private Button editBut = new Button("Edit");
    private Button cancelBut = new Button("Cancel");
    private Button saveBut = new Button("Save");

    // Labels mainContent
    private Label lastNameLabel = new Label("Name:");
    private Label firstNameLabel = new Label("Vorname:");
    private Label addressLabel = new Label("Address:");
    private Label zipLabel = new Label("ZIP-Code:");
    private Label cityLabel = new Label("City:");
    private Label phoneLabel = new Label("Phone:");

    FieldGroup fieldGrpTF;

    private TextField lastNameDBe;
    private TextField firstNameDBe;
    private TextField addressDBe;
    private TextField zipDBe;
    private TextField cityDBe;
    private TextField phoneDBe;

    public EmergencyContactView(long emergencyContactID) {

	this.emergencyContactID = emergencyContactID;

	EmergencyContactModel contactModel = new EmergencyContactModel();
	EmergencyContact contact = contactModel.findById(emergencyContactID);

	fieldGrpTF = new FieldGroup();

	lastNameDBe = new TextField();
	fieldGrpTF.bind(lastNameDBe, "lastNameDB");
	lastNameDBe.setValue(contact.getLastname());

	firstNameDBe = new TextField();
	fieldGrpTF.bind(firstNameDBe, "firstNameDB");
	firstNameDBe.setValue(contact.getFirstname());

	addressDBe = new TextField();
	fieldGrpTF.bind(addressDBe, "addressDB");
	addressDBe.setValue(contact.getStreet());

	zipDBe = new TextField();
	fieldGrpTF.bind(zipDBe, "zipDB");
	zipDBe.setValue(contact.getZip());

	cityDBe = new TextField();
	fieldGrpTF.bind(cityDBe, "cityDB");
	cityDBe.setValue(contact.getCity());

	phoneDBe = new TextField();
	fieldGrpTF.bind(phoneDBe, "phoneDB");
	phoneDBe.setValue(contact.getPhoneNumber());

	fieldGrpTF.setReadOnly(true);
	configureComponents();
	setSizeFull();
	buildLayout();

    }

    private void configureComponents() {

    }

    private void buildLayout() {

	// Header
	header = new HorizontalLayout();
	header.addComponent(backBut);
	header.setComponentAlignment(backBut, Alignment.MIDDLE_LEFT);
	header.addComponent(callBut);
	header.setComponentAlignment(callBut, Alignment.MIDDLE_CENTER);
	header.addComponent(menuBut);
	header.setComponentAlignment(menuBut, Alignment.MIDDLE_RIGHT);
	header.setHeight(37, Unit.PIXELS);
	header.setWidth("100%");

	// Middle/Center
	HorizontalLayout middle = new HorizontalLayout();
	GridLayout grid = new GridLayout(4, 4);

	grid.setCaption("Emergency Contact");
	grid.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

	grid.addComponent(lastNameLabel, 0, 0);
	grid.setComponentAlignment(lastNameLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(lastNameDBe, 1, 0);
	grid.setComponentAlignment(lastNameDBe, Alignment.MIDDLE_LEFT);

	grid.addComponent(firstNameLabel, 2, 0);
	grid.setComponentAlignment(firstNameLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(firstNameDBe, 3, 0);
	grid.setComponentAlignment(firstNameDBe, Alignment.MIDDLE_LEFT);

	grid.addComponent(addressLabel, 0, 1);
	grid.setComponentAlignment(addressLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(addressDBe, 1, 1);
	grid.setComponentAlignment(addressDBe, Alignment.MIDDLE_LEFT);

	grid.addComponent(zipLabel, 0, 2);
	grid.setComponentAlignment(zipLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(zipDBe, 1, 2);
	grid.setComponentAlignment(zipDBe, Alignment.MIDDLE_LEFT);

	grid.addComponent(cityLabel, 2, 2);
	grid.setComponentAlignment(cityLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(cityDBe, 3, 2);
	grid.setComponentAlignment(cityDBe, Alignment.MIDDLE_LEFT);

	grid.addComponent(phoneLabel, 0, 3);
	grid.setComponentAlignment(phoneLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(phoneDBe, 1, 3);
	grid.setComponentAlignment(phoneDBe, Alignment.MIDDLE_LEFT);

	middle.addComponent(grid);
	grid.setRowExpandRatio(getComponentCount(), getComponentCount());
	grid.setSpacing(true);
	grid.setMargin(true);

	middle.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
	middle.setWidth("100%");
	middle.setHeight("100%");

	// View footer
	footerView = new HorizontalLayout();
	footerView.addComponent(editBut);
	editBut.addClickListener(event -> {
	    switchToEdit();
	});
	footerView.setComponentAlignment(editBut, Alignment.MIDDLE_CENTER);

	footerView.setMargin(true);
	footerView.setSpacing(true);
	footerView.setWidth("100%");
	footerView.setHeight(37, Unit.PIXELS);

	// Edit footer
	footerEdit = new HorizontalLayout();
	footerEdit.addComponent(saveBut);
	saveBut.addClickListener(event -> {
	    // Store Changes in DB
	    switchToView();
	});
	footerEdit.setComponentAlignment(saveBut, Alignment.MIDDLE_CENTER);

	footerEdit.addComponent(cancelBut);
	cancelBut.addClickListener(event -> {
	    switchToView();
	});
	footerEdit.setComponentAlignment(cancelBut, Alignment.MIDDLE_CENTER);

	footerEdit.setMargin(true);
	footerEdit.setSpacing(true);
	footerEdit.setWidth("100%");
	footerEdit.setHeight(37, Unit.PIXELS);

	this.addComponent(header);
	this.addComponent(middle);

	this.addComponent(footerView);
    }

    private void switchToView() {
	fieldGrpTF.setReadOnly(true);
	this.replaceComponent(footerEdit, footerView);
	return;
    }

    private void switchToEdit() {
	fieldGrpTF.setReadOnly(false);
	this.replaceComponent(footerView, footerEdit);
	return;
    }

    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

}
