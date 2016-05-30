package ch.bfh.bti7081.s2016.blue.hv.view.state;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class WriteNotes implements BaseState {

	@Override
	public void doJob(StateContext context) {
		showNotesWindow(context);
	}
	
	private void showNotesWindow(StateContext context) {
		context.getSubwindow().setCaption("Leave notes");
		context.getSubwindow().setModal(true);
		context.getSubwindow().setWidth("500px");
		context.getSubwindow().setHeight("320px");
		
		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSizeFull();
		
		Button btnNext = new Button("Next", new Button.ClickListener() {
			private static final long serialVersionUID = 3069773826520337334L;

			@Override
			public void buttonClick(ClickEvent event) {
				context.setStateSwitch(StateSwitch.E_FINISH);
				context.setState(new Finished());
				context.next();
			}
		});
		
		TextArea taNotes = new TextArea("Notes for the meeting");
		taNotes.setSizeFull();
		
		content.addComponent(taNotes);
		content.addComponent(btnNext);
		content.setComponentAlignment(btnNext, Alignment.BOTTOM_RIGHT);
		
		context.getSubwindow().setContent(content);
	}

}
