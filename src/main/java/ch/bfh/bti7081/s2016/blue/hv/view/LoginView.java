package ch.bfh.bti7081.s2016.blue.hv.view;

import java.io.Serializable;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class LoginView extends CustomComponent implements View {

    private static final long serialVersionUID = -5989626786068850093L;

    private LoginListener loginListener;
    private TextField user;
    private PasswordField password;
    private Button loginButton;

    public LoginView(LoginListener loginListener) {
		this.loginListener = loginListener;
	
		buildUi();
    }

    private void buildUi() {
		setSizeFull();
	
		// Create the user input field
		user = new TextField("User:");
		user.setWidth("300px");
		user.setRequired(true);
		user.setInputPrompt("Your username (eg. joe@email.com)");
		user.addValidator(new EmailValidator("Username must be a valid email address"));
		user.setInvalidAllowed(false);
	
		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");
	
		// Create login button
		loginButton = new Button("Login", new Button.ClickListener() {
	
		    @Override
		    public void buttonClick(ClickEvent event) {
			if (!user.isValid() || !password.isValid()) {
			    return;
			}
	
			String username = user.getValue();
			String password = LoginView.this.password.getValue();
	
			//
			// Validate username and password with database here. For
			// examples sake
			// I use a dummy username and password.
			//
			
			boolean isValid = username.equals("test@test.com") && password.equals("passw0rd");
			 
	
			/*boolean isValid = false;
			try {
			    // isValid = um.isLoginCorrect(username, password);
			    isValid = true;
			}
			catch (Exception e) {
	
			}*/
	
			if (isValid) {
			    getSession().setAttribute("user", username);
			    LoginView.this.loginListener.loginSuccessful();
			}
			else {
			    // Wrong password clear the password field and refocuses it
			    showNotification(
				    new Notification("Login failed", "Please check your username and password and try again.",
					    Notification.Type.HUMANIZED_MESSAGE));
	
			    LoginView.this.password.setValue(null);
			    LoginView.this.password.focus();
			}
		    }
		});
	
		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(user, password, loginButton);
		// fields.setCaption("Please login to access the application.
		// (test@test.com/passw0rd)");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();
	
		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		setCompositionRoot(viewLayout);
    }

    // Validator for validating the passwords
    private static final class PasswordValidator extends AbstractValidator<String> {
	private static final long serialVersionUID = 8684687388246483015L;

	public PasswordValidator() {
	    super("The password provided is not valid");
	}

	@Override
	protected boolean isValidValue(String value) {
	    //
	    // Password must be at least 6 characters long and contain at least
	    // one number
	    //
	    if (value != null && (value.length() < 6 || !value.matches(".*\\d.*"))) {
	    	return false;
	    }
	    return true;
	}

	@Override
	public Class<String> getType() {
	    return String.class;
	}
    }

    private void showNotification(Notification notification) {
	// keep the notification visible a little while after moving the
	// mouse, or until clicked
	notification.setDelayMsec(2000);
	notification.show(Page.getCurrent());
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

    public interface LoginListener extends Serializable {
	void loginSuccessful();
    }

}
