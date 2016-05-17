package ch.bfh.bti7081.s2016.blue.hv.view;


import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import ch.bfh.bti7081.s2016.blue.hv.model.Contact;
import ch.bfh.bti7081.s2016.blue.hv.model.ContactService;
import ch.bfh.bti7081.s2016.blue.hv.controller.PatientController;

/**
 * Created by uck1 on 15.05.2016.
 */
public class PatientListView extends CustomComponent{

    TextField filter = new TextField();
    PatientController patient = new PatientController();
    Grid contactList = new Grid();
    Button newContact = new Button("New Contact");

    ContactService service = ContactService.createDemoService();

    public PatientListView() {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        /* Synchronous event handling.
         *
         * Receive user interaction events on the server-side. This allows you
         * to synchronously handle those events. Vaadin automatically sends
         * only the needed changes to the web page without loading a new page.
         */
        newContact.addClickListener(e -> patient.edit(new Contact()));
        filter.setInputPrompt("Filter contacts...");
        filter.addTextChangeListener(e -> refreshContacts(e.getText()));

        contactList.setContainerDataSource(new BeanItemContainer<>(Contact.class));
        contactList.setColumnOrder("firstName", "lastName", "email");
        contactList.removeColumn("address");
        contactList.removeColumn("city");
        contactList.removeColumn("id");
        contactList.removeColumn("birthDate");
        contactList.removeColumn("phone");
        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
        contactList.addSelectionListener(e
                -> patient.edit((Contact) contactList.getSelectedRow()));
        refreshContacts();
    }

    private void buildLayout() {

        HorizontalLayout actions = new HorizontalLayout(filter, newContact);
        actions.setWidth("100%");
        filter.setWidth("100%");
        actions.setExpandRatio(filter, 1);

        VerticalLayout vertical = new VerticalLayout(actions, contactList);
        vertical.setSizeFull();
        contactList.setSizeFull();
        vertical.setExpandRatio(contactList, 1);

        HorizontalLayout horizontal = new HorizontalLayout(vertical, patient);
        horizontal.setSizeFull();
        horizontal.setExpandRatio(vertical, 1);

        setCompositionRoot(horizontal);
    }

    void refreshContacts() {
        refreshContacts(filter.getValue());
    }

    private void refreshContacts(String stringFilter) {
        contactList.setContainerDataSource(new BeanItemContainer<>(
                Contact.class, service.findAll(stringFilter)));
        patient.setVisible(false);
    }

}
