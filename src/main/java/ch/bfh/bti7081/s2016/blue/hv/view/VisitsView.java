package ch.bfh.bti7081.s2016.blue.hv.view;

import java.util.Set;

import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.entities.Patient;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;
import ch.bfh.bti7081.s2016.blue.hv.model.HealthVisitorsModel;
import ch.bfh.bti7081.s2016.blue.hv.model.PatientModel;
import ch.bfh.bti7081.s2016.blue.hv.model.VisitsModel;
import ch.bfh.bti7081.s2016.blue.hv.util.DateUtils;

public class VisitsView extends HorizontalLayout implements View {

    private static final long serialVersionUID = -4194821923203100613L;

    private static final String NAME = "Visits";

    private VerticalLayout visitsView;

    private VisitsModel visitsModel;

    private PatientModel patientModel;

    private HealthVisitorsModel healthVisitorsModel;

    public VisitsView() {
	this.setSizeFull();

	visitsView = new VerticalLayout();
	visitsModel = new VisitsModel();
	patientModel = new PatientModel();
	healthVisitorsModel = new HealthVisitorsModel();

	HorizontalLayout tableView = new HorizontalLayout();
	Table table = new Table();
	table.addStyleName("components-inside");
	
	this.addStyleName("v-scrollable");
        this.setHeight("100%");

	// define the columns
	table.addContainerProperty("Patient firstname", Label.class, null);
	table.addContainerProperty("Patient lastname", Label.class, null);
	table.addContainerProperty("Phone number", Label.class, null);
	table.addContainerProperty("Street", Label.class, null);
	table.addContainerProperty("Zip", Label.class, null);
	table.addContainerProperty("City", Label.class, null);
	table.addContainerProperty("Info", Button.class, null);
	table.addContainerProperty("History", Button.class, null);

	for (Visit visit : visitsModel.findAll()) {

	    Label patientFirstname = new Label(visit.getPatient().getFirstname());
	    Label patientLastname = new Label(visit.getPatient().getLastname());
	    Label phoneNumber = new Label(visit.getPatient().getContact().getPhoneNumber());
	    Label street = new Label(visit.getPatient().getContact().getStreet());
	    Label zip = new Label(visit.getPatient().getContact().getZip());
	    Label city = new Label(visit.getPatient().getContact().getCity());

	    // history button
	    Button historyBtn = new Button();
	    historyBtn.setIcon(FontAwesome.HISTORY);
	    historyBtn.setDescription("Show history");
	    historyBtn.setData(visit);
	    historyBtn.addClickListener(event -> {
		Visit v = (Visit) event.getButton().getData();
		HealthVisUI.setMainView(new PatientVisitHistoryListView(v.getId(), getName()));
		// HealthVisUI.setMainView(new EmergencyContactView(v.getId(),
		// getName()));
	    });
	    // historyBtn.addStyleName("link");

	    // record detail button
	    Button detailsBtn = new Button();
	    detailsBtn.setIcon(FontAwesome.INFO_CIRCLE);
	    detailsBtn.setDescription("Show details");
	    detailsBtn.setData(visit);
	    detailsBtn.addClickListener(event -> {
		Visit v = (Visit) event.getButton().getData();
		showOrAddDetailsWindow(v);
	    });
	    // detailsBtn.addStyleName("link");

	    // Create the table row.
	    table.addItem(new Object[] { patientFirstname, patientLastname, phoneNumber, street, zip, city, detailsBtn,
		    historyBtn }, visit.getId());
	}

	table.setWidth("100%");
	table.setHeight("100%");

	tableView.addComponent(table);
	tableView.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
	tableView.setHeight("100%");
	tableView.setWidth("100%");
	tableView.setMargin(true);

	HorizontalLayout footer = new HorizontalLayout();

	// button to add visit
	Button addNewVisitBtn = new Button();
	addNewVisitBtn.setDescription("add new visit");
	addNewVisitBtn.setIcon(FontAwesome.PLUS_SQUARE_O);
	addNewVisitBtn.addClickListener(event -> {
	    showOrAddDetailsWindow(null);
	});

	footer.addComponent(addNewVisitBtn);
	footer.setComponentAlignment(addNewVisitBtn, Alignment.BOTTOM_CENTER);
	footer.setWidth("100%");
	footer.setHeight(37, Unit.PIXELS);
	footer.setMargin(true);

	visitsView.addComponent(tableView);
	visitsView.addComponent(footer);
	this.addComponent(visitsView);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

    public static String getName() {
	return NAME;
    }

    /**
     * Show the window with the visits details (if visit != null), otherwise
     * show the form to add a new visit.
     */
    private void showOrAddDetailsWindow(Visit visit) {

	final Window window = new Window();
	window.setWidth(500.0f, Unit.PIXELS);
	window.center();
	window.setModal(true);

	// add newy
	if (visit == null) {
	    window.setCaption("Add new visit");

	    VerticalLayout layout = new VerticalLayout();
	    FormLayout form = new FormLayout();

	    // Patient selection
	    ComboBox patientSelect = new ComboBox("Patient");
	    patientSelect.setFilteringMode(FilteringMode.CONTAINS);
	    patientSelect.setInvalidAllowed(false);
	    patientSelect.setNullSelectionAllowed(true);
	    for (Patient patient : patientModel.findAll()) {
		String name = patient.getFirstname() + " " + patient.getLastname();
		patientSelect.addItem(patient);
		patientSelect.setItemCaption(patient, name);
	    }

	    patientSelect.setIcon(FontAwesome.USER);
	    patientSelect.setRequired(true);
	    patientSelect.addValidator(new NullValidator("Must be given", false));
	    patientSelect.setWidth("100%");
	    form.addComponent(patientSelect);

	    // date time from
	    DateField dateFrom = new DateField("From");
	    dateFrom.setIcon(FontAwesome.CALENDAR);
	    dateFrom.setValue(DateUtils.now());
	    dateFrom.setResolution(Resolution.MINUTE);
	    dateFrom.addValidator(
		    new DateRangeValidator("Incorrect date!", dateFrom.getValue(), DateUtils.nowPlusFiveYears(), null));
	    dateFrom.setWidth("100%");
	    form.addComponent(dateFrom);

	    // date time to
	    DateField dateTo = new DateField("To");
	    dateTo.setIcon(FontAwesome.CALENDAR);
	    dateTo.setValue(DateUtils.nowPlusOneHour());
	    dateTo.setResolution(Resolution.MINUTE);
	    dateTo.addValidator(
		    new DateRangeValidator("Incorrect date!", dateTo.getValue(), DateUtils.nowPlusFiveYears(), null));
	    dateTo.setWidth("100%");
	    form.addComponent(dateTo);

	    // HealthVisitor selection
	    ComboBox helthVisitorSelect = new ComboBox("Health-Visitor");
	    helthVisitorSelect.setFilteringMode(FilteringMode.CONTAINS);
	    helthVisitorSelect.setInvalidAllowed(false);
	    helthVisitorSelect.setNullSelectionAllowed(true);
	    for (HealthVisitor healthVisitor : healthVisitorsModel.findAll()) {
		String name = healthVisitor.getFirstname() + " " + healthVisitor.getLastname();
		helthVisitorSelect.addItem(healthVisitor);
		helthVisitorSelect.setItemCaption(healthVisitor, name);
	    }

	    helthVisitorSelect.setIcon(FontAwesome.GROUP);
	    helthVisitorSelect.setRequired(true);
	    helthVisitorSelect.addValidator(new NullValidator("Must be given", false));
	    helthVisitorSelect.setWidth("100%");
	    form.addComponent(helthVisitorSelect);

	    Button saveBtn = new Button("Save");
	    saveBtn.setIcon(FontAwesome.SAVE);
	    saveBtn.addClickListener(listener -> {
		boolean successful = visitsModel.saveNewVisit((Patient) patientSelect.getValue(), dateFrom.getValue(),
			dateTo.getValue(), (HealthVisitor) helthVisitorSelect.getValue());
		if (successful) {
		    window.close();
		}
	    });

	    layout.addComponent(form);
	    layout.addComponent(saveBtn);
	    layout.addStyleName("components-inside");
	    layout.setMargin(true);

	    window.setStyleName("components-inside");
	    window.setContent(layout);
	}
	// show details
	else {
	    window.setCaption(visit.getPatient().getFirstname() + " " + visit.getPatient().getLastname());
	    Set<VisitEvent> events = visit.getVisitEvents();

	    Table table = new Table();
	    table.addStyleName("components-inside");

	    // define the columns
	    table.addContainerProperty("Patient firstname", String.class, null);
	    table.addContainerProperty("Patient lastname", String.class, null);
	    table.addContainerProperty("Date", String.class, null);
	    table.addContainerProperty("From", String.class, null);
	    table.addContainerProperty("To", String.class, null);

	    events.forEach(event -> {
		String date = DateUtils.formatDate(event.getDateFrom());
		String from = DateUtils.formatTime(event.getDateFrom());
		String to = DateUtils.formatTime(event.getDateTo());

		// Create the table row.
		table.addItem(new Object[] { visit.getPatient().getFirstname(), visit.getPatient().getLastname(), date,
			from, to }, visit.getId());
	    });
	    window.setContent(table);
	}

	UI.getCurrent().addWindow(window);
    }

}
