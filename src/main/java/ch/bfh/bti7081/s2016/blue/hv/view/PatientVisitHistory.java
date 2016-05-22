package ch.bfh.bti7081.s2016.blue.hv.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * Created by suttc1 on 22.05.2016.
 */

public class PatientVisitHistory extends CustomComponent implements View{
	private static final String NAME = "Visits-History";
	private Button menuBut = new Button("Menu");
	private Button backBut = new Button("Back");
	private Label historyTitle = new Label();
	private Label historyLabel = new Label();
	
	
	
	
	
	public PatientVisitHistory(int patientID, int date) {
//		try {
//			//GET DB INFO FOR USER ID WITH DATE & TITLE
//		} catch (Exception dbconnectionerror) {
//			Notification notif = new Notification(
//				    "WARNING",
//				    "<br/>unable to get requested data",
//				    Notification.TYPE_ERROR_MESSAGE);
//		}
		
        configureComponents();
        buildLayout();
    }
	
	private void configureComponents() {
    }
	
	private void buildLayout() {
        Panel panel = new Panel();
        panel.setSizeFull();
        VerticalLayout vertLay = new VerticalLayout();

        // 1st row
        HorizontalLayout firstLay = new HorizontalLayout(menuBut, backBut);
        
//        menuBut.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//            	setCompositionRoot(LandingView);
//            }
//        });
//        historyTitle.setValue("History of "+db.getDate(patientID, date));
        vertLay.addComponent(historyTitle);
        vertLay.addComponent(firstLay);
        
        HorizontalLayout horLay = new HorizontalLayout(historyLabel);
        vertLay.addComponent(horLay);
        panel.setContent(vertLay);
        setCompositionRoot(panel);
    }

    public static String getName() {
        return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
