package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

/**
 * Created by uck1 on 14.05.2016.
 */
public class PatientView extends Panel implements View {

    private static final String NAME = "Patient";  //variable for HealthVisUI

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

        Panel panel = new Panel("Patient");
//        panel.setSizeFull();
        VerticalLayout vertLay = new VerticalLayout();

        // 1st row
        HorizontalLayout firstLay = new HorizontalLayout();
        firstLay.addComponent(butMenu);
        firstLay.setComponentAlignment(butMenu, Alignment.MIDDLE_LEFT);
        firstLay.addComponent(butCall);
        firstLay.setComponentAlignment(butCall, Alignment.MIDDLE_RIGHT);
        vertLay.addComponent(firstLay);

        // 2nd row horizontal splitted
        HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        hsplit.setHeight(10, Unit.CM);
        hsplit.setSplitPosition(40, Unit.PERCENTAGE);
        // left side
        AbsoluteLayout left = new AbsoluteLayout();
        left.addComponent(picture, "left: 30px; top: 20px;");
//        left.setMargin(true);
        hsplit.setFirstComponent(left);
        // right side
        AbsoluteLayout right = new AbsoluteLayout();
        right.addComponent(firstName, "left: 10px; top: 20px;");
        right.addComponent(lastName, "left: 10px; top: 50px;");
        right.addComponent(age, "left: 10px; top: 80px;");
        hsplit.setSecondComponent(right);
        vertLay.addComponent(hsplit);   //add both to the row of the vertical layout

        // panel with buttons
        HorizontalSplitPanel buttonLay = new HorizontalSplitPanel();
        buttonLay.setSplitPosition(50, Unit.PERCENTAGE);
        // left side
        AbsoluteLayout buttonLeftLay = new AbsoluteLayout();
        buttonLeftLay.addComponent(new Image(null, new ThemeResource("icons/thumbs-up-circle-120x120.png")),
                "left:10; top: 50px;");
        butBesuche.addClickListener(e -> {});
        buttonLeftLay.addComponent(new Image(null, new ThemeResource("icons/thumbs-down-circle-120x120.png")),
                "right:10; top: 50px;");
        butAnmerkungen.addClickListener(e -> {});
        buttonLay.setFirstComponent(buttonLeftLay);
        // right side
        AbsoluteLayout buttonRightLay = new AbsoluteLayout();
        buttonRightLay.addComponent(new Image(null, new ThemeResource("icons/thumbs-up-circle-120x120.png")),
                "left:10; top: 80px;");
        butThree.addClickListener(e -> {});
        buttonRightLay.addComponent(new Image(null, new ThemeResource("icons/thumbs-down-circle-120x120.png")),
                "right:10; top: 80px;");
        butFour.addClickListener(e -> {});
        buttonLay.setSecondComponent(buttonRightLay);
        vertLay.addComponent(buttonLay);

        // final add to the panel
        panel.setContent(vertLay);
        setContent(panel);
    }

    // method for the HealthVisUI
    public static String getName() {
        return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}