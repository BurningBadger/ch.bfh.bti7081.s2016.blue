package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.hv.components.PrescriptionsComponent;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.model.VisitsModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;

public class TodayMeetingsView extends HorizontalLayout implements View {
	private static final String NAME = "TodayMeetings";

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public TodayMeetingsView() {
		setSizeFull();
		//this.addComponent(new PrescriptionsComponent(new Visit(), new VisitsModel(new VisitsView()), true));
	}
	
	public static String getName() {
		return NAME;
	}

}
