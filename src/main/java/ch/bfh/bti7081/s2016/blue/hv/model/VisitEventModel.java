package ch.bfh.bti7081.s2016.blue.hv.model;

import ch.bfh.bti7081.s2016.blue.hv.entities.Drug;
import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;

public class VisitEventModel extends BaseModel<VisitEvent, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3706721372956511180L;

	public VisitEventModel() {
		super(VisitEvent.class);
	}
	
	boolean validate(Drug drug)
    {
	
	return false;
    }
}
