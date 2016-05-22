package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

/**
 * Created by uck1 on 14.05.2016.
 */
public class PatientView extends CustomComponent implements View {

    private Image picture = new Image(null, new ThemeResource("icons/Guy.png"));
    private Label name = new Label("Name: ");
    private Label age = new Label("Age: ");
//    TextField address = new TextField("Address: ");
//    TextField city = new TextField("City: ");
//    Component birthday = new PopupDateField("Birthday");
    private CheckBox checkbox = new CheckBox("Free");
    private Button butOne = new Button("Choice 1");
    private Button butTwo = new Button("Choice 2");
    private Button butThree = new Button("Choice 3");
    private Button butFour = new Button("Choice 4");
    private Button ok = new Button("OK");
    private Button nok = new Button("NO");

    public PatientView() {

        configureComponents();
        buildLayout();
    }

    private void configureComponents() {

    }

    private void buildLayout() {

        Panel panel = new Panel("Patient");
        panel.setSizeFull();
        HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        hsplit.setHeight(24, Unit.CM);
        hsplit.setSplitPosition(45, Unit.PERCENTAGE);

        AbsoluteLayout left = new AbsoluteLayout();
        left.addComponent(picture, "left: 30px; top: 50px;");
//        left.setMargin(true);
        
//        GridLayout grid = new GridLayout(4, 4);
//        grid.addComponent(nameLabel, 0, 0);
//        grid.setComponentAlignment(nameLabel, Alignment.MIDDLE_LEFT);
//        
////        //DB output
////        grid.addComponent(nameDB, 1, 0);
////        grid.setComponentAlignment(nameDB, Alignment.MIDDLE_LEFT);
//        
//        grid.addComponent(surnameLabel, 2, 0);
//        grid.setComponentAlignment(surnameLabel, Alignment.MIDDLE_LEFT);
//        
////      //DB output
////      grid.addComponent(surnameDB, 3, 0);
////      grid.setComponentAlignment(surnameDB, Alignment.MIDDLE_LEFT);
//        
//        grid.addComponent(addressLabel, 0, 1);
//        grid.setComponentAlignment(addressLabel, Alignment.MIDDLE_LEFT);
//        
////        grid.addComponent(addressDB, 1, 1);
////        grid.setComponentAlignment(addressDB, Alignment.MIDDLE_LEFT);
//        
//        grid.addComponent(areaLabel, 0, 2);
//        grid.setComponentAlignment(areaLabel, Alignment.MIDDLE_LEFT);
//        
////        grid.addComponent(areaDB, 1, 2);
////        grid.setComponentAlignment(areaDB, Alignment.MIDDLE_LEFT);
//        
//        grid.addComponent(cityLabel, 2, 2);
//        grid.setComponentAlignment(cityLabel, Alignment.MIDDLE_LEFT);
//        
////        grid.addComponent(cityDB, 3, 2);
////        grid.setComponentAlignment(cityDB, Alignment.MIDDLE_LEFT);
//        
//        grid.addComponent(phoneLabel, 0, 3);
//        grid.setComponentAlignment(phoneLabel, Alignment.MIDDLE_LEFT);
//        
////        grid.addComponent(phoneDB, 0, 3);
////        grid.setComponentAlignment(phoneDB, Alignment.MIDDLE_LEFT);
        
        left.addComponent(name, "left: 80px; top: 390px;");
        left.addComponent(age, "left: 80px; top: 440px;");
//        left.addComponent(grid);
        hsplit.setFirstComponent(left);

        AbsoluteLayout right = new AbsoluteLayout();
        right.addComponent(butOne, "left: 40px; top: 90px;");
        butOne.addStyleName("large");
        right.addComponent(butTwo, "left: 40px; top: 180px;");
        butTwo.addStyleName("large");
        right.addComponent(butThree, "left: 40px; top: 270px;");
        butThree.addStyleName("large");
        right.addComponent(butFour, "left: 40px; top: 360px;");
        butFour.addStyleName("large");
        right.addComponent(checkbox, "left: 40px; top: 450px;");

        right.addComponent(new Image(null, new ThemeResource("icons/thumbs-up-circle-120x120.png")),
                "left:100; top: 700px;");
        ok.addClickListener(e -> {});
        right.addComponent(new Image(null, new ThemeResource("icons/thumbs-down-circle-120x120.png")),
                "right:100; top: 700px;");
        nok.addClickListener((e -> {}));

        hsplit.setSecondComponent(right);

        panel.setContent(hsplit);
        setCompositionRoot(panel);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}