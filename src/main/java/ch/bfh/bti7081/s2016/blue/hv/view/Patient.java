package ch.bfh.bti7081.s2016.blue.hv.view;

import ch.bfh.bti7081.s2016.blue.HealthVisUI;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ch.bfh.bti7081.s2016.blue.hv.model.Contact;

/**
 * Created by uck1 on 15.05.2016.
 */
public class Patient extends FormLayout {

    private Button save = new Button("Save", this::save);
    private Button cancel = new Button("Cancel", this::cancel);
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField phone = new TextField("Phone: ");
    private TextField email = new TextField("Email");
//    TextField address = new TextField("Address: ");
//    TextField city = new TextField("City: ");
    Component birthday = new PopupDateField("Birthday");

    Contact contact;
//    PatientList patList;

    // Easily bind forms to beans and manage validation and buffering
    BeanFieldGroup<Contact> formFieldBindings;

    public Patient() {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);
        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        actions.setSpacing(true);
        addComponents(actions, firstName, lastName, phone, email, birthday);
    }

    public void save(Button.ClickEvent event) {
        try {
            formFieldBindings.commit();
            getUI().patientList.service.save(contact);
            String msg = String.format("Saved '%s %s'.",
                    contact.getFirstName(),
                    contact.getLastName());
            Notification.show(msg, Notification.Type.TRAY_NOTIFICATION);
            getUI().patientList.refreshContacts();
        } catch (FieldGroup.CommitException e) {

        }
    }
    public void cancel(Button.ClickEvent event) {
        // Place to call business logic.
        Notification.show("Cancelled", Notification.Type.TRAY_NOTIFICATION);
        getUI().patientList.contactList.select(null);
    }

    void edit(Contact contact) {
        this.contact = contact;
        if(contact != null) {
            // Bind the properties of the contact POJO to fiels in this form
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(contact, this);
            firstName.focus();
        }
        setVisible(contact != null);
    }

    @Override
    public HealthVisUI getUI() {
        return (HealthVisUI) super.getUI();
    }
}
