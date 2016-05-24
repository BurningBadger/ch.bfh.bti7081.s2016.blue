package ch.bfh.bti7081.s2016.blue.hv.view;

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

    public PatientVisitHistoryView(int patientID, int date) {
	// try {
	// //GET DB INFO FOR USER ID WITH DATE & TITLE
	// } catch (Exception dbconnectionerror) {
	// Notification notif = new Notification(
	// "WARNING",
	// "<br/>unable to get requested data",
	// Notification.TYPE_ERROR_MESSAGE);
	// }

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
