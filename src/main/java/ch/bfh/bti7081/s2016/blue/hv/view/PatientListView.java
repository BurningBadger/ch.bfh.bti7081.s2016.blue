package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * Created by uck1 on 22.05.2016.
 */
public class PatientListView extends CustomComponent implements View {

    private static final String NAME = "Patients";

    private Button menuBut = new Button();
    private Button callBut = new Button();
    private Button butOne = new Button("Gestern");
    private Button butTwo = new Button("Heute");
    private Button butThree = new Button("Morgen");

    public PatientListView() {

        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
    }

    private void buildLayout() {
        Panel panel = new Panel();
        panel.setSizeFull();
        VerticalLayout vertLay = new VerticalLayout();

        // 1st row
        HorizontalLayout firstLay = new HorizontalLayout(menuBut, callBut);
        vertLay.addComponent(firstLay);

        HorizontalLayout horLay = new HorizontalLayout(butOne, butTwo, butThree);
        vertLay.addComponent(horLay);
        panel.setContent(vertLay);
        setCompositionRoot(panel);
    }

    public static String getName() {
        return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
