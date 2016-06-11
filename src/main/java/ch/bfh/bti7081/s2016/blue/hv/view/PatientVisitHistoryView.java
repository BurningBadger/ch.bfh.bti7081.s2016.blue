package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.model.VisitsModel;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * Created by suttc1 on 22.05.2016.
 */

public class PatientVisitHistoryView extends VerticalLayout implements View {

    private static final String NAME = "PatientVisitsHistoryView";
    private Button menuBut = new Button("Menu");
    private Button backBut = new Button("Back");
    private Label dateLabel = new Label("01.01.1999");
    private Label historyLabel = new Label("History");
    private Label historyTitle = new Label("Title");
    private Label patientName = new Label("Name:");
    private Label patientNameLabel = new Label("HardC0de");
    private Label patientSurname = new Label("Vorname:");
    private Label patientSurnameLabel = new Label("Muster");
    private Label history = new Label("Lorem ipsum");

    public PatientVisitHistoryView(long patientID, int date) {
	
	final Table table = new Table();
	table.addStyleName("components-inside");

	// define the columns
	table.addContainerProperty("Patient firstname", Label.class, null);
	table.addContainerProperty("Patient lastname", Label.class, null);
	table.addContainerProperty("Phone number", Label.class, null);
	table.addContainerProperty("Street", Label.class, null);
	table.addContainerProperty("Zip", Label.class, null);
	table.addContainerProperty("City", Label.class, null);
	table.addContainerProperty("", Button.class, null);

	VisitsModel visitsModel = new VisitsModel();

	    Label patientFirstname = new Label(visitsModel.findById(patientID).getPatient().getFirstname());
	    Label patientLastname = new Label(visitsModel.findById(patientID).getPatient().getLastname());
	    Label phoneNumber = new Label(visitsModel.findById(patientID).getPatient().getContact().getPhoneNumber());
	    Label street = new Label(visitsModel.findById(patientID).getPatient().getContact().getStreet());
	    Label zip = new Label(visitsModel.findById(patientID).getPatient().getContact().getZip());
	    Label city = new Label(visitsModel.findById(patientID).getPatient().getContact().getCity());
	    
	configureComponents();
	buildLayout();
    }

    private void configureComponents() {

    }

    private void buildLayout() {
	// 1st row
	HorizontalLayout headline = new HorizontalLayout(menuBut, backBut);

	// menuBut.addClickListener(new Button.ClickListener() {
	// @Override
	// public void buttonClick(Button.ClickEvent event) {
	// setCompositionRoot(LandingView);
	// }
	// });
	// historyTitle.setValue("History of "+db.getDate(patientID, date));
	this.addComponent(headline);

	HorizontalLayout infobar = new HorizontalLayout(historyLabel);
	infobar.addComponent(dateLabel);
	infobar.addComponent(historyLabel);
	infobar.addComponent(historyTitle);
	infobar.addComponent(patientNameLabel);
	infobar.addComponent(patientName);
	infobar.addComponent(patientSurnameLabel);
	infobar.addComponent(patientSurname);
	this.addComponent(infobar);

	HorizontalLayout content = new HorizontalLayout();
	content.addComponent(history);
	this.addComponent(content);
    }

    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
