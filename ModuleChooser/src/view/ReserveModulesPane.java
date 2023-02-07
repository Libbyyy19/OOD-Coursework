package view;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

public class ReserveModulesPane extends Accordion {

	private Term1ModulesReservePane tmr;
	private Term2ModulesReservePane tmr2;
	private TitledPane t1, t2;
	
	public ReserveModulesPane() {
	
		
		tmr = new Term1ModulesReservePane();
		tmr2 = new Term2ModulesReservePane();
		
		t1 = new TitledPane("Term 1 Modules", tmr);
		t1.setPrefSize(400, 500);
		t2 = new TitledPane("Term 2 Modules", tmr2);
		t2.setPrefSize(500, 500);
		
		this.setExpandedPane(t1);
		this.getPanes().add(t1);
		this.getPanes().add(t2);
	}
	
		public Term1ModulesReservePane getTerm1ModulesReservePane() {
			return tmr;
		}
		
		public Term2ModulesReservePane getTerm2ModulesReservePane() {
			return tmr2;
		}
		
		public void changePane() {
			this.setExpandedPane(t2);
		}
	}
	

