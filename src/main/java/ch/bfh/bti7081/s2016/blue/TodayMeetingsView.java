package ch.bfh.bti7081.s2016.blue;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;

public class TodayMeetingsView extends HorizontalLayout implements View {
	private static final String NAME = "TodayMeetings";

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	public static String getName() {
		return NAME;
	}

}
