package ch.bfh.bti7081.s2016.blue.hv.view.state;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class Started implements BaseState {
	
	private Table table = null;

	@Override
	public void doJob(StateContext context) {
		showStartedWindow(context);
	}
	
	private void showStartedWindow(StateContext context) {
		context.getSubwindow().setCaption("Tasks Overview");
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
				context.setStateSwitch(StateSwitch.E_DRUGS);
				context.setState(new GiveDrugs());
				context.next();
			}
		});
		btnNext.setVisible(false);
		
		//Tasks List
		table = new Table("List of Tasks For Today");
		table.setWidth("100%");
		table.addContainerProperty("Description", String.class, null);
		
		for(int i=0; i<3; i++) {
			/*Object newItemId = table.addItem();
			Item row1 = table.getItem(newItemId);
			
			row1.getItemProperty("Name").setValue("Do something");
			row1.getItemProperty("Mag").setValue(new Button("Finished"));*/
			
			table.addItem(new Object[]{"Do something"}, i);
			
		}
		
		table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			private static final long serialVersionUID = -6068202479431585809L;

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					table.removeItem(event.getItemId());
					if(table.getItemIds().isEmpty()) {
						btnNext.setVisible(true);
					}
				}
			}
		});
		
		table.setPageLength(table.size());
		content.addComponent(table);
		
		content.addComponent(btnNext);
		content.setComponentAlignment(btnNext, Alignment.BOTTOM_RIGHT);
		
		context.getSubwindow().setContent(content);
	}
	
}
