package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.Prescription;
import ch.bfh.bti7081.s2016.blue.hv.entities.Visit;
import ch.bfh.bti7081.s2016.blue.hv.view.VisitsView;

import java.util.logging.Logger;

public class PrescriptionsModel extends BaseModel<Prescription, Long> {

    private static final long serialVersionUID = -5573378810271266249L;

    private final static Logger LOGGER = Logger.getLogger(PrescriptionsModel.class.getName());

    public PrescriptionsModel() {
	super(Prescription.class);
    }
    
}
