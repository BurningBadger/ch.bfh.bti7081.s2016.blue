package ch.bfh.bti7081.s2016.blue.hv.controller;

import java.util.logging.Logger;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

import ch.bfh.bti7081.s2016.blue.hv.model.Visit;
import ch.bfh.bti7081.s2016.blue.hv.util.Constants;
import ch.bfh.bti7081.s2016.blue.hv.view.VisitsView;

public class VisitsController extends BaseController {

    private static final long serialVersionUID = -5573378810271266249L;

    private final static Logger LOGGER = Logger.getLogger(VisitsController.class.getName());

    private JPAContainer<Visit> visits;

    public VisitsController(VisitsView view) {
	visits = JPAContainerFactory.make(Visit.class, Constants.PERSISTENCE_UNIT);
	view.setVisits(visits);
    }
}
