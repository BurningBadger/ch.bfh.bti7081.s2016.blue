package ch.bfh.bti7081.s2016.blue.hv.controller;

import java.util.logging.Logger;

import ch.bfh.bti7081.s2016.blue.hv.model.Visit;
import ch.bfh.bti7081.s2016.blue.hv.view.VisitsView;

public class VisitsController extends BaseController<Visit, Long> {

    private static final long serialVersionUID = -5573378810271266249L;

    private final static Logger LOGGER = Logger.getLogger(VisitsController.class.getName());

    public VisitsController(VisitsView view) {
	super(Visit.class);
    }
}
