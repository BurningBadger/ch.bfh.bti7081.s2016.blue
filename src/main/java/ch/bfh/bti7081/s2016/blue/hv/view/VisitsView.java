package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.blue.hv.model.Visit;
import ch.bfh.bti7081.s2016.blue.hv.util.Constants;

public class VisitsView extends Panel implements View {

    private static final String NAME = "Visits";

    private JPAContainer<Visit> visits;

    public VisitsView() {
	visits = JPAContainerFactory.make(Visit.class, Constants.PERSISTENCE_UNIT);

	setSizeFull();
	buildLayout();
    }

    private void buildLayout() {
	Panel panel = new Panel("Visits");

	Table visitTable = new Table();
	visitTable.setContainerDataSource(visits);
	visitTable.setSelectable(true);
	visitTable.setImmediate(true);

	final VerticalLayout layout = new VerticalLayout();
	layout.addComponents(panel, visitTable);
	setContent(layout);
    }

    // method for the HealthVisUI
    public static String getName() {
	return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}