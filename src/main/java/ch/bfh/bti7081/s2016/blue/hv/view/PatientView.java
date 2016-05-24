package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by uck1 on 14.05.2016.
 */
public class PatientView extends VerticalLayout implements View {

    private static final String NAME = "Patient"; // variable for HealthVisUI

    private Button butMenu = new Button("Menu");
    private Button butCall = new Button("Call");

    private Image picture = new Image(null, new ThemeResource("icons/Guy.png"));
    private Label firstName = new Label("First Name: ");
    private Label lastName = new Label("Last Name: ");
    private Label age = new Label("Age: ");

    private Button butBesuche = new Button("Besuche");
    private Button butAnmerkungen = new Button("Anmerkungen");
    private Button butThree = new Button("Choice 3");
    private Button butFour = new Button("Choice 4");

    public PatientView() {
	setSizeFull();
	buildLayout();
    }

    private void buildLayout() {

	// 1st row
	HorizontalLayout firstLay = new HorizontalLayout();
	firstLay.addComponent(butMenu);
	firstLay.setComponentAlignment(butMenu, Alignment.MIDDLE_LEFT);
	firstLay.addComponent(butCall);
	firstLay.setComponentAlignment(butCall, Alignment.MIDDLE_RIGHT);
	firstLay.setMargin(true);
	butMenu.addStyleName("btntestclass");
	butCall.addStyleName("btntestclass");
	firstLay.setWidth("100%");
	this.addComponent(firstLay);

	// 2nd row horizontal splitted
	HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
//	hsplit.setHeight(5, Unit.CM);
	hsplit.setSplitPosition(40, Unit.PERCENTAGE);
	// left side
	AbsoluteLayout left = new AbsoluteLayout();
	left.addComponent(picture, "left: 30px; top: 20px;");
	// left.setMargin(true);
	hsplit.setFirstComponent(left);
	// right side
	AbsoluteLayout right = new AbsoluteLayout();
	right.addComponent(firstName, "left: 10px; top: 20px;");
	right.addComponent(lastName, "left: 10px; top: 50px;");
	right.addComponent(age, "left: 10px; top: 80px;");
	hsplit.setSecondComponent(right);
	this.addComponent(hsplit); // add both to the row of the vertical layout

	// panel with buttons
	HorizontalSplitPanel buttonLay = new HorizontalSplitPanel();
	buttonLay.setSplitPosition(50, Unit.PERCENTAGE);

	// left side
	AbsoluteLayout buttonLeftLay = new AbsoluteLayout();
	buttonLeftLay.addComponent(new Image(null, new ThemeResource("icons/thumbs-up-circle-120x120.png")),
		"left:60; top: 50px;");
	butBesuche.addClickListener(e -> {});
	buttonLeftLay.addComponent(new Image(null, new ThemeResource("icons/thumbs-down-circle-120x120.png")),
		"left:60; top: 190px;");
	butAnmerkungen.addClickListener(e -> {});
	buttonLay.setFirstComponent(buttonLeftLay);
	// right side
	AbsoluteLayout buttonRightLay = new AbsoluteLayout();
	buttonRightLay.addComponent(new Image(null, new ThemeResource("icons/thumbs-up-circle-120x120.png")),
		"right:60; top: 50px;");
	butThree.addClickListener(e -> {});
	buttonRightLay.addComponent(new Image(null, new ThemeResource("icons/thumbs-down-circle-120x120.png")),
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