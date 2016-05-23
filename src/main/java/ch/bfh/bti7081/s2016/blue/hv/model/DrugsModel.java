package ch.bfh.bti7081.s2016.blue.hv.model;

import java.util.logging.Logger;

import ch.bfh.bti7081.s2016.blue.hv.entities.Drug;

public class DrugsModel extends BaseModel<Drug, Long> {

    private static final long serialVersionUID = -3477963697182614276L;

    private final static Logger LOGGER = Logger.getLogger(DrugsModel.class.getName());

    public DrugsModel() {
	super(Drug.class);
    }
}
