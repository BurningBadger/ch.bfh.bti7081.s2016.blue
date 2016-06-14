package ch.bfh.bti7081.s2016.blue;

import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

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
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.model.HealthVisitorsModel;
import ch.bfh.bti7081.s2016.blue.hv.view.DrugsOrderView;
import ch.bfh.bti7081.s2016.blue.hv.view.DrugsView;
import ch.bfh.bti7081.s2016.blue.hv.view.EmergencyContactView;
import ch.bfh.bti7081.s2016.blue.hv.view.LandingView;
import ch.bfh.bti7081.s2016.blue.hv.view.LoginView;
import ch.bfh.bti7081.s2016.blue.hv.view.MenuLayout;
import ch.bfh.bti7081.s2016.blue.hv.view.PatientListView;
import ch.bfh.bti7081.s2016.blue.hv.view.PatientVisitHistoryListView;
import ch.bfh.bti7081.s2016.blue.hv.view.SettingsView;
import ch.bfh.bti7081.s2016.blue.hv.view.TodayMeetingsView;
import ch.bfh.bti7081.s2016.blue.hv.view.VisitsView;

/**
 * {@link HealthVisUI}. This represents the applications main entry point.
 */
@Theme("mytheme")
@Widgetset("ch.bfh.bti7081.s2016.blue.MyAppWidgetset")
public class HealthVisUI extends UI {

    private static final long serialVersionUID = -5147606114440444224L;
    private final static Logger LOGGER = Logger.getLogger(HealthVisUI.class.getName());
    private static final boolean isDebug = true;
    private HealthVisitorsModel healthVisitorsModel = new HealthVisitorsModel();
    private HealthVisitor currentUser;
    private static CssLayout viewContainer;
    Navigator navigator;

    public HealthVisUI() {
	LOGGER.info("HealthVisitor webapp started");

    }

    public HealthVisitor getCurrentUser(){
	if (currentUser != null) {
	    return currentUser;
	}

    	VaadinSession session = getSession();
	if (session == null) {
	    return null;
	}

	final Long userId = Long.valueOf(session.getAttribute("userId").toString());
	currentUser = healthVisitorsModel.findById(userId);

	if(currentUser != null)
	    return currentUser;

	return null;
    }
    
    public static void setMainView(Component c){
	viewContainer.removeAllComponents();
	viewContainer.addComponent(c);
    }

    @Override
    protected void init(VaadinRequest request) {
		VaadinSession.getCurrent().getSession().setMaxInactiveInterval(3000);
		this.getPage().setTitle("Health Visitor");
	
		checkLogin();
    }

    private void checkLogin() {
		String currentUser = (String) getSession().getAttribute("user");
		if (currentUser != null || !isDebug) {
		    //createMainView();
			createModernView();
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

    MenuLayout root = new MenuLayout();
    ComponentContainer viewDisplay = root.getContentContainer();
    
    private void createModernView() {
    	setContent(root);
    	root.setWidth("100%");
    	
    	//Main Navigator
    	navigator = new Navigator(this, viewDisplay);
    	
    	//Populate menu
    	HVMenu hvMenu = new HVMenu(navigator);
    	hvMenu.addMenuItem("Home", "Home", FontAwesome.DASHBOARD);
    	hvMenu.addMenuItem("Patients", "Patients", FontAwesome.CIRCLE);
    	hvMenu.addMenuItem("TodayMeetings", "Today Meetings", FontAwesome.CALENDAR);
    	hvMenu.addMenuItem("Visits", "Visits", FontAwesome.TRIPADVISOR);
    	hvMenu.addMenuItem("DrugOrders", "Drug Orders", FontAwesome.MEDKIT);
    	hvMenu.addMenuItem("Drugs", "Drugs", FontAwesome.MEDKIT);
    	root.addMenu(hvMenu);
    	
    	//Navigator Items
    	navigator.addView("Home", LandingView.class);
    	navigator.addView("Patients", PatientListView.class);
    	navigator.addView("TodayMeetings", TodayMeetingsView.class);
    	navigator.addView("Visits", VisitsView.class);
    	navigator.addView("DrugOrders", DrugsOrderView.class);
    	navigator.addView("Drugs", DrugsView.class);
    	navigator.addView("Settings", SettingsView.class);
    	navigator.addView("PatientVisitHistoryList", PatientVisitHistoryListView.class);
    	navigator.addView("EmergencyContact", EmergencyContactView.class);
    	
    	String f = Page.getCurrent().getUriFragment();
        if (f == null || f.equals("")) {
            navigator.navigateTo("Home");
        }
        
        navigator.addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = 6150190313711680972L;

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return true;
			}
			
			@Override
			public void afterViewChange(ViewChangeEvent event) {
				//Highlight Selected Menu Item
				hvMenu.setActiveView(event.getViewName());
			}
		});
	}

    /**
     * Servlet definitions.
     */
    @WebServlet(urlPatterns = "/*", name = "HealthVisUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = HealthVisUI.class, productionMode = false)
    public static class HealthVisUIServlet extends VaadinServlet {
		private static final long serialVersionUID = -4366829570078802018L;
    }

}
