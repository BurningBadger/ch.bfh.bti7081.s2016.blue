package ch.bfh.bti7081.s2016.blue.hv.view.state;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class PreparedForMeeting implements BaseState {
	
	@Override
	public void doJob(StateContext context) {
		showPreparedWindow(context);
	}
	
	private void showPreparedWindow(StateContext context) {
		context.getSubwindow().setCaption("Meeting Overview");
		context.getSubwindow().setModal(true);
		context.getSubwindow().setWidth("500px");
		context.getSubwindow().setHeight("320px");
		//Main Layout
		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSizeFull();
		
		//User PIC + Info
		HorizontalLayout customerInfo = new HorizontalLayout();
		Image image = new Image(null, new ThemeResource("images/user-signin.jpg"));
		
		VerticalLayout customerInfoText = new VerticalLayout();
		Label lName = new Label("Name: " + context.getVe().getVisit().getPatient().getFirstname());
		Label lSurname = new Label("Surname: " + context.getVe().getVisit().getPatient().getLastname());
		
		customerInfoText.addComponent(lName);
		customerInfoText.addComponent(lSurname);
		
		customerInfo.addComponent(image);
		customerInfo.addComponent(customerInfoText);
		
		content.addComponent(customerInfo);
		
		HorizontalLayout buttonsPane = new HorizontalLayout();
		buttonsPane.setWidth("100%");
		buttonsPane.setHeight("120px");
		
		Button btnStart = new Button("Start Meeting", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				context.setStateSwitch(StateSwitch.E_START);
				context.setState(new Started());
				context.next();
				//context.getSubwindow().close();
			}
		});
		
		
		Button btnCancel = new Button("Cancel Meeting", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				context.setStateSwitch(StateSwitch.E_CANCEL);
				context.setState(new Cancelled());
				context.getSubwindow().close();
			}
		});
		
		buttonsPane.addComponent(btnStart);
		buttonsPane.setComponentAlignment(btnStart, Alignment.BOTTOM_LEFT);
		buttonsPane.addComponent(btnCancel);
		buttonsPane.setComponentAlignment(btnCancel, Alignment.BOTTOM_RIGHT);
		
		content.addComponent(buttonsPane);
		
		context.getSubwindow().setContent(content);
	}
	
}
