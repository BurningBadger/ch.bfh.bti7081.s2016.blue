package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.blue.hv.controller.VisitsController;

public class VisitsView extends Panel implements View {

    private static final long serialVersionUID = 7626840964598390162L;

    private static final String NAME = "Visits";
    private Container visits;

    public VisitsView() {
	new VisitsController(this);

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

    public void setVisits(Container visits) {
	this.visits = visits;
    }

    public Container getVisits() {
	return this.visits;
    }

}