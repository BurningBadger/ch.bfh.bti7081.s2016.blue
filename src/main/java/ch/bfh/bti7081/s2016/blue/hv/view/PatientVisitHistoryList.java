package ch.bfh.bti7081.s2016.blue.hv.view;

import org.h2.command.ddl.SetComment;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

public class PatientVisitHistoryList extends CustomComponent implements View{
	private static final String NAME = "Visits-History-List";
	private Button menuBut = new Button("Menu");
	private Button backBut = new Button("Back");
	private int patientID = 0;
	ListSelect visits = new ListSelect("Last Visits");
	
	@SuppressWarnings("deprecation")
	public void PatientVisitHistoryList(int patientID){
		this.patientID = patientID;
		
		try {
			//GET DB INFO FOR USER ID WITH DATE & TITLE			
			visits.addItems("Date", "Title");
			visits.setNullSelectionAllowed(false);
		} catch (Exception dbconnectionerror) {
			new Notification(
				    "WARNING",
				    "<br/>unable to get requested data",
				    Notification.TYPE_ERROR_MESSAGE);
		}
		
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
        vertLay.addComponent(firstLay);

        HorizontalLayout horLay = new HorizontalLayout(visits);
        vertLay.addComponent(horLay);
        panel.setContent(vertLay);
        visits.setRows(6);
        setCompositionRoot(panel);
    }

    public static String getName() {
        return NAME;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

}
