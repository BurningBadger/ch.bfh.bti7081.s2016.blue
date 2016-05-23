package ch.bfh.bti7081.s2016.blue.hv.model;

import java.util.logging.Logger;

import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.view.VisitsView;

public class VisitsModel extends BaseModel<Visit, Long> {

    private static final long serialVersionUID = -5573378810271266249L;

    private final static Logger LOGGER = Logger.getLogger(VisitsModel.class.getName());

    public VisitsModel(VisitsView view) {
	super(Visit.class);
    }
    
}
