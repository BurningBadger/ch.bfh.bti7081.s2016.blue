package ch.bfh.bti7081.s2016.blue.hv.view.state;

import com.vaadin.ui.Window;

import ch.bfh.bti7081.s2016.blue.hv.entities.VisitEvent;

public class StateContext {
	
	private BaseState stateModel;
	private StateSwitch stateSwitch;
	private Window subwindow;
	private VisitEvent ve;
	
	public StateContext() {
		this.setStateSwitch(StateSwitch.E_PREPARED);
		this.setState(new PreparedForMeeting());
	}
	
	public void next(Window subwindow, VisitEvent ve) {
		this.setVe(ve);
		this.setSubwindow(subwindow);
		this.getState().doJob(this);
	}
	
	public void next() {
		this.getState().doJob(this);
	}

	public BaseState getState() {
		return stateModel;
	}

	public void setState(BaseState stateModel) {
		this.stateModel = stateModel;
	}

	public Window getSubwindow() {
		return subwindow;
	}

	public void setSubwindow(Window subwindow) {
		this.subwindow = subwindow;
	}

	public VisitEvent getVe() {
		return ve;
	}

	public void setVe(VisitEvent ve) {
		this.ve = ve;
	}

	public StateSwitch getStateSwitch() {
		return stateSwitch;
	}

	public void setStateSwitch(StateSwitch ss) {
		this.stateSwitch = ss;
	}

}
