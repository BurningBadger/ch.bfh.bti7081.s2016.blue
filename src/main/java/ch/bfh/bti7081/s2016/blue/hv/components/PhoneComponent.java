package ch.bfh.bti7081.s2016.blue.hv.components;

import ch.bfh.bti7081.s2016.blue.hv.entities.Person;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Component class to show the phone number of a person. This can be used in every entity
 * inheriting from the person class.
 */
public class PhoneComponent {

    private Person person;

    public PhoneComponent(Person person) {

	this.person = person;
	showPhoneNumber(person);
    }

    // help method: show the number in a pop-up window
    private void showPhoneNumber(Person person) {

	// a simple pop-up window
	final FormLayout formLayout = new FormLayout();
	formLayout.setMargin(true);

	TextField firstName = new TextField("first name");
	firstName.setConvertedValue(person.getFirstname());
	formLayout.addComponent(firstName);
	TextField phoneNumber = new TextField("phone number");
	phoneNumber.setConvertedValue(person.getContact().getPhoneNumber());
	formLayout.addComponent(phoneNumber);

	final Window window = new Window();
	window.setWidth(1000.0f, Sizeable.Unit.PIXELS);
	window.center();
	window.setContent(formLayout);
	UI.getCurrent().addWindow(window);
    }
}
