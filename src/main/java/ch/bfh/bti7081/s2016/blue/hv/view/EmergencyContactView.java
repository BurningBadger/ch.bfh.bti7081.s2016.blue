package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;

import ch.bfh.bti7081.s2016.blue.hv.entities.EmergencyContact;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.model.EmergencyContactModel;
import ch.bfh.bti7081.s2016.blue.hv.model.VisitsModel;

public class EmergencyContactView extends VerticalLayout implements View {

    private static final long serialVersionUID = -3931584070955988897L;
    

    // Buttons headLine
    private static final String NAME = "EmergencyContactView";
    private long patientID;
    private String lastPage;
    private Visit visit;
    private EmergencyContactModel emergencyContactModel = new EmergencyContactModel();
    
    private VerticalLayout main;
    private HorizontalLayout header;
    private HorizontalLayout footerView;
    private HorizontalLayout footerEdit;
  

    private Button homeBut = new Button("Home");
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
    
    public EmergencyContactView() {
		
		this.setSizeFull();
		
		visit = new VisitsModel().findById(patientID);
		EmergencyContact emergencyContact = emergencyContactModel.findById(visit.getPatient().getEmergencyContact().getId());
	
		fieldGrpTF = new FieldGroup();
	
		lastNameDBe = new TextField();
		fieldGrpTF.bind(lastNameDBe, "lastNameDB");
		lastNameDBe.setValue(emergencyContact.getLastname());
	
		firstNameDBe = new TextField();
		fieldGrpTF.bind(firstNameDBe, "firstNameDB");
		firstNameDBe.setValue(emergencyContact.getFirstname());
	
		addressDBe = new TextField();
		fieldGrpTF.bind(addressDBe, "addressDB");
		addressDBe.setValue(emergencyContact.getStreet());
	
		zipDBe = new TextField();
		fieldGrpTF.bind(zipDBe, "zipDB");
		zipDBe.setValue(emergencyContact.getZip());
	
		cityDBe = new TextField();
		fieldGrpTF.bind(cityDBe, "cityDB");
		cityDBe.setValue(emergencyContact.getCity());
	
		phoneDBe = new TextField();
		fieldGrpTF.bind(phoneDBe, "phoneDB");
		phoneDBe.setValue(emergencyContact.getPhoneNumber());
	
		fieldGrpTF.setReadOnly(true);
		configureComponents();
		buildLayout();

    }

    private void configureComponents() {

    }

    private void buildLayout() {
	main = new VerticalLayout();

	// Header
	header = new HorizontalLayout();
	header.addComponent(backBut);
	backBut.addClickListener(event -> {
	    this.detach();
	    getUI().getNavigator().navigateTo(lastPage);    
	    this.removeAllComponents();
	});
	header.setComponentAlignment(backBut, Alignment.MIDDLE_LEFT);
	
	header.addComponent(callBut);
	callBut.addClickListener(event -> {
	    final FormLayout formLayout = new FormLayout();

	    String windowTitle = "Phone: " + visit.getPatient().getEmergencyContact().getPhoneNumber();

	    final Window window = new Window(windowTitle);
	    window.setWidth(400.0f, Unit.PIXELS);
	    window.center();
	    window.setContent(formLayout);
	    UI.getCurrent().addWindow(window);
	});
	header.setComponentAlignment(callBut, Alignment.MIDDLE_CENTER);
	
	header.addComponent(homeBut);
	homeBut.addClickListener(event -> {
	    this.detach();
	    getUI().getNavigator().navigateTo(LandingView.getName());
	    this.removeAllComponents();
	});
	header.setComponentAlignment(homeBut, Alignment.MIDDLE_RIGHT);
	header.setHeight(37, Unit.PIXELS);
	header.setHeight("100%");
	header.setWidth("100%");
	header.setMargin(true);

	// Middle/Center
	HorizontalLayout middle = new HorizontalLayout();
	GridLayout grid = new GridLayout(4, 4);
	
	grid.setCaption("Emergency Contact of " + visit.getPatient().getFirstname() + " "
		    + visit.getPatient().getLastname());
	
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
	
	grid.setRowExpandRatio(getComponentCount(), getComponentCount());
	grid.setSpacing(true);
	
	middle.addComponent(grid);
	middle.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
	middle.setWidth("100%");
	middle.setHeight("100%");
	middle.setMargin(true);

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
	    saveChange(visit.getPatient().getEmergencyContact().getId());
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

	main.addComponent(header);
	main.addComponent(middle);
	main.addComponent(footerView);
	this.addComponent(main);
    }

    private void switchToView() {
	fieldGrpTF.setReadOnly(true);
	main.replaceComponent(footerEdit, footerView);
	return;
    }

    private void switchToEdit() {
	fieldGrpTF.setReadOnly(false);
	main.replaceComponent(footerView, footerEdit);
	return;
    }
    
    private void saveChange(long id){
	EmergencyContact newEmergencyContact = new EmergencyContact();
	newEmergencyContact.setId(id);
	newEmergencyContact.setLastname(lastNameDBe.getValue());
	newEmergencyContact.setFirstname(firstNameDBe.getValue());
	newEmergencyContact.setStreet(addressDBe.getValue());
	newEmergencyContact.setZip(zipDBe.getValue());
	newEmergencyContact.setCity(cityDBe.getValue());
	newEmergencyContact.setPhoneNumber(phoneDBe.getValue());
	
	emergencyContactModel.saveOrUpdate(newEmergencyContact);
    }

    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	String[] params = event.getParameters().split("/");
    	this.patientID = Long.parseLong(params[0]);
		this.lastPage = params[1];
    }

}
