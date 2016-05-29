package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.HealthVisitor;

import java.util.logging.Logger;

public class HealthVisitorsModel extends BaseModel<HealthVisitor, Long>{

    private static final long serialVersionUID = -3477963697182614276L;

    private final static Logger LOGGER = Logger.getLogger(HealthVisitorsModel.class.getName());

    public HealthVisitorsModel() {
	super(HealthVisitor.class);
    }



    boolean validate(HealthVisitor healthVisitor)
    {

	return false;
    }

}
