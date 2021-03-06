package ch.bfh.bti7081.s2016.blue.hv.view;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.themes.ValoTheme;

@Deprecated
public class Menu extends CssLayout {
    private static final long serialVersionUID = 9148437608385665635L;

    // Variables
    private static final String VALO_MENUITEMS = "valo-menuitems";
    private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
    private static final String VALO_MENU_VISIBLE = "valo-menu-visible";
    private Navigator navigator;
    private Map<String, Button> viewButtons = new HashMap<String, Button>();

    private CssLayout menuItemsLayout;
    private CssLayout menuPart;

    /**
     * Creates menu layout and populates it with menu items
     * 
     * @param navigator
     */
    public Menu(Navigator navigator) {
		this.navigator = navigator;
		setPrimaryStyleName(ValoTheme.MENU_ROOT);
		menuPart = new CssLayout();
		menuPart.addStyleName(ValoTheme.MENU_PART);
		//menuPart.addStyleName(ValoTheme.MENU_PART);
	
		// logout menu item
		/*MenuBar logoutMenu = new MenuBar();
		logoutMenu.addItem("Logout", FontAwesome.SIGN_OUT, new Command() {
		    private static final long serialVersionUID = -4029332663002821796L;
	
		    @Override
		    public void menuSelected(MenuItem selectedItem) {
		    	getSession().setAttribute("user", null);
		    	VaadinSession.getCurrent().getSession().invalidate();
		    	Page.getCurrent().reload();
		    }
		});
	
		logoutMenu.addStyleName("user-menu");
		menuPart.addComponent(logoutMenu);*/
	
		// container for the navigation buttons, which are added by addView()
		menuItemsLayout = new CssLayout();
		menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
		menuPart.addComponent(menuItemsLayout);
	
		addComponent(menuPart);
    }

    /**
     * Register a pre-created view instance in the navigation menu and in the
     * {@link Navigator}.
     *
     * @see Navigator#addView(String, View)
     *
     * @param view
     *            view instance to register
     * @param name
     *            view name
     * @param caption
     *            view caption in the menu
     * @param icon
     *            view icon in the menu
     */
    public void addView(View view, final String name, String caption, Resource icon) {
    	navigator.addView(name, view);
    	createViewButton(name, caption, icon);
    }

    /**
     * Register a view in the navigation menu and in the {@link Navigator} based
     * on a view class.
     *
     * @see Navigator#addView(String, Class)
     *
     * @param viewClass
     *            class of the views to create
     * @param name
     *            view name
     * @param caption
     *            view caption in the menu
     * @param icon
     *            view icon in the menu
     */
    public void addView(Class<? extends View> viewClass, final String name, String caption, Resource icon) {
    	navigator.addView(name, viewClass);
    	createViewButton(name, caption, icon);
    }

    private void createViewButton(final String name, String caption, Resource icon) {
		Button button = new Button(caption, new Button.ClickListener() {
		    private static final long serialVersionUID = 3967216491537592707L;
	
		    @Override
		    public void buttonClick(ClickEvent event) {
				if (!caption.equals("")) {
				    Page.getCurrent().setTitle(caption + " | Health Visitor");
				}
				else {
				    Page.getCurrent().setTitle("Health Visitor");
				}
				navigator.navigateTo(name);
		    }
		});
		button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		button.setIcon(icon);
		menuItemsLayout.addComponent(button);
		viewButtons.put(name, button);
    }

    /**
     * Highlights a view navigation button as the currently active view in the
     * menu. This method does not perform the actual navigation.
     *
     * @param viewName
     *            the name of the view to show as active
     */
    public void setActiveView(String viewName) {
		for (Button button : viewButtons.values()) {
		    button.removeStyleName("selected");
		}
		Button selected = viewButtons.get(viewName);
		if (selected != null) {
		    selected.addStyleName("selected");
		}
		menuPart.removeStyleName(VALO_MENU_VISIBLE);
    }

}
