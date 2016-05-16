package ch.bfh.bti7081.s2016.blue;

import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.blue.hv.model.HealthVisitor;
import ch.bfh.bti7081.s2016.blue.hv.util.Constants;

/**
 * {@link HealthVisUI}. This represents the applications main entry point.
 */
@Theme("mytheme")
@Widgetset("ch.bfh.bti7081.s2016.blue.MyAppWidgetset")
public class HealthVisUI extends UI {

	private static final long serialVersionUID = -5147606114440444224L;

	private final static Logger LOGGER = Logger.getLogger(HealthVisUI.class.getName());

	private JPAContainer<HealthVisitor> healthVisitors;

	public HealthVisUI() {
		LOGGER.info("HealthVisitor webapp started");
		// initializations
		healthVisitors = JPAContainerFactory.make(HealthVisitor.class, Constants.PERSISTENCE_UNIT);
	}

	@Override
	protected void init(VaadinRequest request) {

		// TODO implement Vaadin Navigator
		// TODO Implement Webapp Login/Logout

		final VerticalLayout layout = new VerticalLayout();

		final TextField name = new TextField();
		name.setCaption("Login:");

		Button button = new Button("ok");
		button.addClickListener(e -> {
			layout.addComponent(new Label("Thanks " + name.getValue() + ", it works!"));
			Notification.show("Hi " + name.getValue());
		});

		layout.addComponents(name, button);
		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
	}

	/**
	 * Servlet definitions.
	 */
	@WebServlet(urlPatterns = "/*", name = "HealthVisUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = HealthVisUI.class, productionMode = false)
	public static class HealthVisUIServlet extends VaadinServlet {
	}

}
