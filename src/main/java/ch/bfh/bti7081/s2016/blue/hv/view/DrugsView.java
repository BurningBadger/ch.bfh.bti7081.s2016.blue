package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class DrugsView extends VerticalLayout implements View {

    private static final long serialVersionUID = 5462829240356734337L;
    private static final String NAME = "Drugs";

    @Override
    public void enter(ViewChangeEvent event) {
	// TODO Auto-generated method stub

    }

    public static String getName() {
	return NAME;
    }

}
