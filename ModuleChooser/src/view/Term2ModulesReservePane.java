package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;

public class Term2ModulesReservePane extends GridPane {

	private ListView<Module> Unselected2;
	private ListView<Module> Reserved2;
	private ObservableList<Module> UnselectedT2;
	private ObservableList<Module> ReservedT2; 
	private Button Add, Remove, Confirm;
	private int credits;
	private int maxLimit3;
	
	public Term2ModulesReservePane() {
		
	
	this.setPadding(new Insets(80, 80, 80, 80));
	this.setVgap(15);
	this.setHgap(20);
	
			//Labels
			Label lblUnselectedTerm2 = new Label ("Unselected Term 1 Modules");
			Label lblReservedTerm2 = new Label ("Reserved Term 1 Modules");
			Label lblReserveCred = new Label("Reserve 30 credits worth of term 1 modules");
			lblReserveCred.setPadding(new Insets(5, 15, 5, 60));
			
			//buttons
			Add = new Button("Add");
			Add.setPadding(new Insets(5, 20, 5, 20));
			Remove = new Button("Remove");
			Remove.setPadding(new Insets(5, 20, 5, 20));
			Confirm = new Button("Confirm");
			Confirm.setPadding(new Insets(5, 20, 5, 20));
			
			//listviews 
			UnselectedT2 = FXCollections.observableArrayList();
			Unselected2 = new ListView<>(UnselectedT2);
			Unselected2.setPrefSize(500, 500);
			
			ReservedT2 = FXCollections.observableArrayList();
			Reserved2 = new ListView<>(ReservedT2);
			Reserved2.setPrefSize(500, 500);
			
			//hboxes
			HBox hbox = new HBox(5);
			hbox.getChildren().addAll(lblReserveCred);
			hbox.prefWidthProperty().bind(widthProperty());
			
			HBox hbox1 = new HBox(5);
			hbox1.getChildren().addAll(Add, Remove, Confirm);
			hbox1.prefWidthProperty().bind(widthProperty());
			
			//vboxes
			VBox vbox = new VBox(8);
			vbox.getChildren().addAll(lblUnselectedTerm2, Unselected2, hbox);
			vbox.prefWidthProperty().bind(widthProperty());
			
			VBox vbox1 = new VBox(8);
			vbox1.getChildren().addAll(lblReservedTerm2, Reserved2, hbox1);
			vbox1.prefWidthProperty().bind(widthProperty());
			
			
			this.add(vbox, 0, 0);
			this.add(vbox1, 1, 0);
			
	}
	
	public ObservableList<Module> getContents(){
		return UnselectedT2;
	}
	
	public ObservableList<Module> getContents1(){
		return ReservedT2;
	}

	public void addAddHandler3(EventHandler <ActionEvent> handler) {
		Add.setOnAction(handler);
	}
	
	public void addConfirmHandler1(EventHandler <ActionEvent> handler) {
		Confirm.setOnAction(handler);
	}
	public int getCredits() {
		return credits;
	}
	
	public int addCredits(int c) {
		return credits += c;
	}
	
	public int removeCredits(int c) {
		return credits -= c;
	}
	
	public int getMaxLimit3() {
		return maxLimit3;
	}
	
	public void setMaxLimit3(int maxLimit3) {
		if(!(maxLimit3 < 1)) {
			this.maxLimit3 = maxLimit3;
		}
	}
	public int getReservedModuleCredits() {
		int credits = 15;
		
		if(ReservedT2.isEmpty()) {
			for(Module m : ReservedT2){
				credits += m.getModuleCredits();
			}
		}
		return credits;
	}
	
	public void addSelectedModuletoReserve() {
		if(!Unselected2.getSelectionModel().isEmpty()) {
			int credits = 0;
			for (Module m : ReservedT2) {
				credits += m.getModuleCredits();
			}
			if(((credits + Unselected2.getSelectionModel().getSelectedItem().getModuleCredits()) <= maxLimit3)) {
				ReservedT2.add(Unselected2.getSelectionModel().getSelectedItem());
				UnselectedT2.remove(Unselected2.getSelectionModel().getSelectedItem());
			}
		}
	}
	
	public void removeSelectedModule() {
		if(!Reserved2.getSelectionModel().isEmpty()) {
			int credits = 0;
			for(Module m : ReservedT2) {
				credits -= m.getModuleCredits();
			}
			if(((credits - Reserved2.getSelectionModel().getSelectedItem().getModuleCredits()) <= maxLimit3)) {
				UnselectedT2.add(Reserved2.getSelectionModel().getSelectedItem());
				ReservedT2.remove(Reserved2.getSelectionModel().getSelectedItem());
			}
		}
	}

	public void addRemoveHandler3(EventHandler<ActionEvent> handler) {
		Remove.setOnAction(handler);
		
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
}