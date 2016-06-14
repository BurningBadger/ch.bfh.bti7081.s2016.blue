package ch.bfh.bti7081.s2016.blue;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.MenuBar.Command;

public class HVMenu extends CssLayout implements Component {
	
	private static final long serialVersionUID = -1691748755333601690L;
	
	private Map<String, Button> viewButtons = new HashMap<String, Button>();
	CssLayout menuItemsLayout = new CssLayout();
	Navigator navigator = null;
	
	public HVMenu(Navigator navigator) {
		this.navigator = navigator;
		
		HorizontalLayout top = new HorizontalLayout();
		top.setWidth("100%");
		top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		top.addStyleName(ValoTheme.MENU_TITLE);
		this.addComponent(top);
		
		Button showMenu = new Button("Menu", new Button.ClickListener() {
			private static final long serialVersionUID = -7142946830590280550L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (HVMenu.this.getStyleName().contains("valo-menu-visible")) {
					HVMenu.this.removeStyleName("valo-menu-visible");
                } else {
                	HVMenu.this.addStyleName("valo-menu-visible");
                }
			}
		});
		
        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName("valo-menu-toggle");
        showMenu.setIcon(FontAwesome.LIST);
        this.addComponent(showMenu);
        
        Label title = new Label("<h3>SoED <strong>Health Visitors</strong></h3>",
                ContentMode.HTML);
        title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);
        
        MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        MenuItem settingsItem = settings.addItem("", new ThemeResource("icons/profile-pic-300px.jpg"), null);
        settingsItem.addItem("Edit Profile", null);
        //settingsItem.addItem("Preferences", null);
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", new Command() {
			private static final long serialVersionUID = 9217597349015872609L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				getSession().setAttribute("user", null);
		    	VaadinSession.getCurrent().getSession().invalidate();
		    	Page.getCurrent().reload();
			}
		});
        this.addComponent(settings);
        
        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        this.addComponent(menuItemsLayout);
	}
	
	public void addMenuItem(String uri, String caption, Resource icon) {
		//menuItems.put(uri, new Object[] {caption, icon});
		Button button = new Button(caption, new Button.ClickListener() {
		    private static final long serialVersionUID = 3967216491537592707L;
	
		    @Override
		    public void buttonClick(ClickEvent event) {
				navigator.navigateTo(uri);
		    }
		});
		button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		button.setIcon(icon);
		menuItemsLayout.addComponent(button);
		viewButtons.put(uri, button);
	}
	
	public void setActiveView(String viewName) {
		for (Button button : viewButtons.values()) {
		    button.removeStyleName("selected");
		}
		Button selected = viewButtons.get(viewName);
		if (selected != null) {
		    selected.addStyleName("selected");
		}
		this.removeStyleName("valo-menu-visible");
    }
	
}
