package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * Created by uck1 on 14.05.2016.
 */
public class HealthVisitorView extends CustomComponent {

    private Image picture = new Image(null, new ThemeResource("icons/Guy.png"));
    private Label name = new Label("Name: ");
    private Label age = new Label("Age: ");
    // TextField address = new TextField("Address: ");
    // TextField city = new TextField("City: ");
    // Component birthday = new PopupDateField("Birthday");
    private CheckBox checkbox = new CheckBox("Free");
    private Button butOne = new Button("Choice 1");
    private Button butTwo = new Button("Choice 2");
    private Button butThree = new Button("Choice 3");
    private Button butFour = new Button("Choice 4");
    private Button ok = new Button("OK");
    private Button nok = new Button("NO");

    public HealthVisitorView() {

	configureComponents();
	buildLayout();
    }

    private void configureComponents() {

    }

    private void buildLayout() {

	Panel panel = new Panel("Health Visitor");
	panel.setSizeFull();
	HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
	hsplit.setHeight(24, Unit.CM);
	hsplit.setSplitPosition(45, Unit.PERCENTAGE);

	AbsoluteLayout left = new AbsoluteLayout();
	left.addComponent(picture, "left: 30px; top: 50px;");
	// left.setMargin(true);

	left.addComponent(name, "left: 80px; top: 390px;");
	left.addComponent(age, "left: 80px; top: 440px;");
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
	ok.addClickListener(e -> {
	});
	right.addComponent(new Image(null, new ThemeResource("icons/thumbs-down-circle-120x120.png")),
		"right:100; top: 700px;");
	nok.addClickListener((e -> {
	}));

	hsplit.setSecondComponent(right);

	panel.setContent(hsplit);
	setCompositionRoot(panel);
    }

}