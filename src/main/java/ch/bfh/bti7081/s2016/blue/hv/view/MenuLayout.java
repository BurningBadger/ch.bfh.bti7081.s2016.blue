package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

public class MenuLayout extends HorizontalLayout {
	private static final long serialVersionUID = 3050045283946340134L;

	CssLayout contentArea = new CssLayout();

    CssLayout menuArea = new CssLayout();

    public MenuLayout() {
        setSizeFull();

        menuArea.setPrimaryStyleName("valo-menu");

        contentArea.setPrimaryStyleName("valo-content");
        contentArea.addStyleName("v-scrollable");
        contentArea.setSizeFull();

        addComponents(menuArea, contentArea);
        setExpandRatio(contentArea, 1);
    }

    public ComponentContainer getContentContainer() {
        return contentArea;
    }

    public void addMenu(Component menu) {
        menu.addStyleName("valo-menu-part");
        menuArea.addComponent(menu);
    }

	
}
