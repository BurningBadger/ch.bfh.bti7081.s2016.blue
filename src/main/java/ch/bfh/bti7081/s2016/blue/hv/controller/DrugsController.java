package ch.bfh.bti7081.s2016.blue.hv.controller;

import java.util.logging.Logger;

import ch.bfh.bti7081.s2016.blue.hv.model.Drug;

public class DrugsController extends BaseController<Drug, Long> {

    private static final long serialVersionUID = -3477963697182614276L;

    private final static Logger LOGGER = Logger.getLogger(DrugsController.class.getName());

    public DrugsController() {
	super(Drug.class);
    }
}
