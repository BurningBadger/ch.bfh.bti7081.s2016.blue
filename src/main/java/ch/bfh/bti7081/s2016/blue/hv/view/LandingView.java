package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class LandingView extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 8807692569903926065L;
	private static final String NAME = "Home";
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	public static String getName() {
		return NAME;
	}

}
