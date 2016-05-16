package ch.bfh.bti7081.s2016.blue.hv.controller;

import java.util.logging.Logger;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

import ch.bfh.bti7081.s2016.blue.hv.model.Drug;
import ch.bfh.bti7081.s2016.blue.hv.util.Constants;

public class DrugsController extends BaseController {

	private static final long serialVersionUID = -3477963697182614276L;

	private final static Logger LOGGER = Logger.getLogger(DrugsController.class.getName());

	private final JPAContainer<Drug> drugs;

	public DrugsController() {
		drugs = JPAContainerFactory.make(Drug.class, Constants.PERSISTENCE_UNIT);
	}
}
