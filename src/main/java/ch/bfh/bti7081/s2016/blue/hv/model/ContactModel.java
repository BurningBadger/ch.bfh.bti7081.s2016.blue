package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.Contact;

import java.util.logging.Logger;

public class ContactModel extends BaseModel<Contact, Long> {

    private static final long serialVersionUID = -3477963697182614276L;

    private final static Logger LOGGER = Logger.getLogger(DrugsModel.class.getName());

    public ContactModel() {
	super(Contact.class);
    }
}
