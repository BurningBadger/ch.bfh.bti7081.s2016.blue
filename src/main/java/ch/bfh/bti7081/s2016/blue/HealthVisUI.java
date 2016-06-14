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

	/*private void createMainView() {
		// Create Main Container for Views
    	CssLayout mainVl = new CssLayout();
		mainVl.setSizeFull();
	
		// implement Vaadin Navigator
		viewContainer = new CssLayout();
		viewContainer.addStyleName("valo-content");
		viewContainer.setSizeFull();
	
		final Navigator navigator = new Navigator(this, viewContainer);
	
		// Menu
		menu = new Menu(navigator);
		menu.addView(new LandingView(), "Home", LandingView.getName(), FontAwesome.DASHBOARD);
		menu.addView(new PatientListView(), "Patients", PatientListView.getName(), FontAwesome.CIRCLE);
		menu.addView(new TodayMeetingsView(), "TodayMeetings", TodayMeetingsView.getName(), FontAwesome.CALENDAR);
		menu.addView(new VisitsView(), "Visits", VisitsView.getName(), FontAwesome.TRIPADVISOR);
		menu.addView(new DrugsOrderView(), "Drug Orders", DrugsOrderView.getName(), FontAwesome.MEDKIT);
		menu.addView(new DrugsView(), "Drugs", DrugsView.getName(), FontAwesome.MEDKIT);
		menu.addView(new SettingsView(), "Settings", SettingsView.getName(), FontAwesome.ASTERISK);
		
		if (navigator.getState().isEmpty()) {
		    navigator.navigateTo(LandingView.getName());
		}
		navigator.addViewChangeListener(viewChangeListener);
	
		//
		// header of the menu
		final HorizontalLayout top = new HorizontalLayout();
		top.setWidth("100%");
		top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		top.addStyleName(ValoTheme.MENU_TITLE);
		top.setSpacing(true);
		//Label title = new Label("Health Visitor");
		//title.addStyleName(ValoTheme.LABEL_H2);
		//title.setSizeUndefined();
		//Image image = new Image(null, new ThemeResource("images/logo_75_60.png"));
		//image.setStyleName("logo");
		//top.addComponent(image);
		//top.addComponent(title);
		mainVl.addComponent(top);
		
		// button for toggling the visibility of the menu when on a small screen
		final Button showMenu = new Button("Menu", new Button.ClickListener() {
		    private static final long serialVersionUID = 2078454610142404940L;
	
		    @Override
		    public void buttonClick(ClickEvent event) {
				if (menu.getStyleName().contains("valo-menu-visible")) {
					menu.removeStyleName("valo-menu-visible");
				} else {
					menu.addStyleName("valo-menu-visible");
				}
		    }
		});
	
		showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
		showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
		showMenu.addStyleName("valo-menu-toggle");
		showMenu.setIcon(FontAwesome.LIST);
		top.addComponent(showMenu);
		
		//App Name
		final Label title = new Label(
                "<h3>SoED <strong>Health Visitors</strong></h3>", ContentMode.HTML);
		title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);
        
        //Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.addStyleName("user-menu");
        MenuItem mbItem = menuBar.addItem("User", new ThemeResource("icons/profile-pic-300px.jpg"), null);
        mbItem.addItem("Edit Profile", null);
        mbItem.addSeparator();
        mbItem.addItem("Sign Out", null);
		top.addComponent(menuBar);
		
		//Content + Menu
		HorizontalLayout mainHl = new HorizontalLayout(); 
		mainHl.setSizeFull();
		// Adding to the main Pane
		mainHl.addComponent(menu);
		mainHl.addComponent(viewContainer);
		mainHl.setExpandRatio(viewContainer, 1);
		
		//mainVl.addComponent(mainHl);
		//mainVl.setExpandRatio(mainHl, 1);
		
		setContent(mainVl);
    }*/

    /*ViewChangeListener viewChangeListener = new ViewChangeListener() {
	private static final long serialVersionUID = -1303877173524139079L;

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
	    return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
	    menu.setActiveView(event.getViewName());
	}

    };*/

    /**
     * Servlet definitions.
     */
    @WebServlet(urlPatterns = "/*", name = "HealthVisUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = HealthVisUI.class, productionMode = false)
    public static class HealthVisUIServlet extends VaadinServlet {
		private static final long serialVersionUID = -4366829570078802018L;
    }

}
