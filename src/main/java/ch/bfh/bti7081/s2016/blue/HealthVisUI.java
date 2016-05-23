package ch.bfh.bti7081.s2016.blue;

import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import ch.bfh.bti7081.s2016.blue.hv.model.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.util.Constants;
import ch.bfh.bti7081.s2016.blue.hv.view.DrugsView;
import ch.bfh.bti7081.s2016.blue.hv.view.LandingView;
import ch.bfh.bti7081.s2016.blue.hv.view.LoginView;
import ch.bfh.bti7081.s2016.blue.hv.view.Menu;
import ch.bfh.bti7081.s2016.blue.hv.view.PatientListView;
import ch.bfh.bti7081.s2016.blue.hv.view.PatientView;
import ch.bfh.bti7081.s2016.blue.hv.view.VisitsView;

/**
 * {@link HealthVisUI}. This represents the applications main entry point.
 */
@Theme("mytheme")
@Widgetset("ch.bfh.bti7081.s2016.blue.MyAppWidgetset")
public class HealthVisUI extends UI {

    private static final long serialVersionUID = -5147606114440444224L;

    private final static Logger LOGGER = Logger.getLogger(HealthVisUI.class.getName());

    private JPAContainer<HealthVisitor> healthVisitors;

    // public PatientListView patientList = new PatientListView();
    // public ContactService contactService =
    // ContactService.createDemoService();

    private static Menu menu = null;
    private static final boolean isDebug = false;

    public HealthVisUI() {
	LOGGER.info("HealthVisitor webapp started");
	// initializations
	healthVisitors = JPAContainerFactory.make(HealthVisitor.class, Constants.PERSISTENCE_UNIT);
    }

    @Override
    protected void init(VaadinRequest request) {

	// //Set Session timeout to 3000s
	VaadinSession.getCurrent().getSession().setMaxInactiveInterval(3000);
	// //Change Page title
	this.getPage().setTitle("Health Visitor");
	// //Prepare the template page
	//
	checkLogin();
	createMainView();
    }

    private void checkLogin() {
	String currentUser = (String) getSession().getAttribute("user");
	if (currentUser != null || isDebug) {
	    createMainView();
	}
	else {
	    this.setContent(new LoginView(new LoginView.LoginListener() {
		private static final long serialVersionUID = -6472665895715933073L;

		@Override
		public void loginSuccessful() {
		    Page.getCurrent().reload();
		}
	    }));
	}
    }

    private void createMainView() {
	// Create Main Container for Views
	HorizontalLayout mainVl = new HorizontalLayout();
	mainVl.setSizeFull();
	this.setContent(mainVl);

	// implement Vaadin Navigator
	CssLayout viewContainer = new CssLayout();
	viewContainer.addStyleName("valo-content");
	viewContainer.setSizeFull();

	final Navigator navigator = new Navigator(this, viewContainer);

	// Views
	LandingView lv = new LandingView();

	// Menu
	menu = new Menu(navigator);
	menu.addView(lv, "", LandingView.getName(), FontAwesome.DASHBOARD);
	// menu.addView(new DrugsView(), "Drugs", DrugsView.getName(),
	// FontAwesome.AMBULANCE);
	menu.addView(new PatientListView(), "Patients", PatientListView.getName(), FontAwesome.AMBULANCE);
	menu.addView(new PatientView(), "Patient", PatientView.getName(), FontAwesome.AMBULANCE);
	menu.addView(new VisitsView(), "Visits", VisitsView.getName(), FontAwesome.STICKY_NOTE);
	navigator.addViewChangeListener(viewChangeListener);
	// navigator.addView("", lv);

	// Adding to the main Pane
	mainVl.addComponent(menu);
	mainVl.addComponent(viewContainer);
	mainVl.setExpandRatio(viewContainer, 1);

	// Implement Webapp Login/Logout

	/*
	 * final VerticalLayout layout = new VerticalLayout();
	 * 
	 * final TextField name = new TextField(); name.setCaption("Login:");
	 * 
	 * Button button = new Button("ok"); button.addClickListener(e -> {
	 * layout.addComponent(new Label("Thanks " + name.getValue() +
	 * ", it works!")); Notification.show("Hi " + name.getValue()); });
	 * 
	 * layout.addComponents(name, button); layout.setMargin(true);
	 * layout.setSpacing(true);
	 * 
	 * setContent(layout);
	 */
    }

    ViewChangeListener viewChangeListener = new ViewChangeListener() {
	private static final long serialVersionUID = -1303877173524139079L;

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
	    return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
	    menu.setActiveView(event.getViewName());
	}

    };

    /**
     * Servlet definitions.
     */
    @WebServlet(urlPatterns = "/*", name = "HealthVisUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = HealthVisUI.class, productionMode = false)
    public static class HealthVisUIServlet extends VaadinServlet {
    }

}
