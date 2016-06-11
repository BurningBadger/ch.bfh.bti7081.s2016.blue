package ch.bfh.bti7081.s2016.blue.hv.model;

import java.util.logging.Logger;

import ch.bfh.bti7081.s2016.blue.hv.entities.EmergencyContact;



public class EmergencyContactModel extends BaseModel <EmergencyContact, Long> {
    
    private static final long serialVersionUID = -1592139441973771073L;
    
    private final static Logger LOGGER = Logger.getLogger(EmergencyContactModel.class.getName());
    
    public EmergencyContactModel() {
	super(EmergencyContact.class);
    }
}

