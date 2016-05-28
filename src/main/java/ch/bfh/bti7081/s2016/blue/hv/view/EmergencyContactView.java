package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * Created by suttc1 on 22.05.2016.
 */

public class EmergencyContactView extends VerticalLayout implements View {

    private static final String NAME = "EmergencyContactView";
    private Button menuBut = new Button("Menu");
    private Button backBut = new Button("Back");

    private Label nameLabel = new Label("Name:");
    private Label nameDB = new Label("Muster");
    private Label surnameLabel = new Label("Vorname:");
    private Label surnameDB = new Label("Muster");
    private Label addressLabel = new Label("Address:");
    private Label addressDB = new Label("Langstrasse 1337");
    private Label areaLabel = new Label("City Code:");
    private Label areaDB = new Label("3000");
    private Label cityLabel = new Label("City:");
    private Label cityDB = new Label("Bern");
    private Label phoneLabel = new Label("Phone:");
    private Label phoneDB = new Label("079 999 99 99");
    private Label birthDayLabel = new Label("Date of Birth:");
    private Label birthDayDB = new Label("01.01.1999");

    public EmergencyContactView() {
    
	// try {
	// //GET DB INFO FOR USER ID WITH DATE & TITLE
	// } catch (Exception dbconnectionerror) {
	// Notification notif = new Notification(
	// "WARNING",
	// "<br/>unable to get requested data",
	// Notification.TYPE_ERROR_MESSAGE);
	// }
	
	configureComponents();
	setSizeFull();
	buildLayout();

    }

    private void configureComponents() {
	
    }

    private void buildLayout() {
	// 1st row
	HorizontalLayout headline = new HorizontalLayout();
	headline.addComponent(backBut);
	headline.setComponentAlignment(backBut, Alignment.MIDDLE_LEFT);
	headline.addComponent(menuBut);
	headline.setComponentAlignment(menuBut, Alignment.MIDDLE_CENTER);
	headline.setMargin(true);
	headline.setWidth("100%");
	headline.setHeight("10%");
	this.addComponent(headline);

	HorizontalLayout container = new HorizontalLayout();

	GridLayout grid = new GridLayout(4, 4);
	grid.addComponent(nameLabel, 0, 0);
	grid.setComponentAlignment(nameLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(nameDB, 1, 0);
	grid.setComponentAlignment(nameDB, Alignment.MIDDLE_LEFT);

	grid.addComponent(surnameLabel, 2, 0);
	grid.setComponentAlignment(surnameLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(surnameDB, 3, 0);
	grid.setComponentAlignment(surnameDB, Alignment.MIDDLE_LEFT);

	grid.addComponent(addressLabel, 0, 1);
	grid.setComponentAlignment(addressLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(addressDB, 1, 1);
	grid.setComponentAlignment(addressDB, Alignment.MIDDLE_LEFT);

	grid.addComponent(areaLabel, 0, 2);
	grid.setComponentAlignment(areaLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(areaDB, 1, 2);
	grid.setComponentAlignment(areaDB, Alignment.MIDDLE_LEFT);

	grid.addComponent(cityLabel, 2, 2);
	grid.setComponentAlignment(cityLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(cityDB, 3, 2);
	grid.setComponentAlignment(cityDB, Alignment.MIDDLE_LEFT);

	grid.addComponent(phoneLabel, 0, 3);
	grid.setComponentAlignment(phoneLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(phoneDB, 1, 3);
	grid.setComponentAlignment(phoneDB, Alignment.MIDDLE_LEFT);

	grid.addComponent(birthDayLabel, 2, 3);
	grid.setComponentAlignment(birthDayLabel, Alignment.MIDDLE_LEFT);

	grid.addComponent(birthDayDB, 3, 3);
	grid.setComponentAlignment(birthDayDB, Alignment.MIDDLE_LEFT);
	
	container.setSpacing(true);
	container.setMargin(true);
	container.setWidth("100%");
	container.setHeight("100%");
	container.addComponent(grid);
	this.addComponent(container);
    }

    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

}
