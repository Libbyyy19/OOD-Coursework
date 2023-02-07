package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Module;

public class Term1ModulesReservePane extends GridPane {

	private ListView<Module> Unselected1;
	private ListView<Module> Reserved1;
	private ObservableList<Module> UnselectedTerm1;
	private ObservableList<Module> Reserved1Term1;
	private Button btnAdd, Remove, Confirm;
	private int credits;
	private int maxLimit3;
	
	public Term1ModulesReservePane() {
		
		this.setPadding(new Insets(80, 80, 80, 80));
		this.setVgap(15);
		this.setHgap(20);
		
		//Labels
		Label lblUnselectedTerm1 = new Label ("Unselected Term 1 Modules");
		Label lblReservedTerm1 = new Label ("Reserved Term 1 Modules");
		Label lblReserveCred = new Label("Reserve 30 credits worth of term 1 modules");
		lblReserveCred.setPadding(new Insets(5, 15, 5, 60));
		
		//listviews 
		UnselectedTerm1 = FXCollections.observableArrayList();
		Unselected1 = new ListView<>(UnselectedTerm1);
		Unselected1.setPrefSize(500, 500);
		
		Reserved1Term1 = FXCollections.observableArrayList();
		Reserved1 = new ListView<>(Reserved1Term1);
		Reserved1.setPadding(new Insets(5, 15, 5, 15));
		Reserved1.setPrefSize(500, 500);
		
		//buttons 
		btnAdd = new Button("Add");
		btnAdd.setPadding(new Insets(5, 20, 5, 20));
		Remove = new Button("Remove");
		Remove.setPadding(new Insets(5, 20, 5, 20));
		Confirm = new Button("Confirm");
		Confirm.setPadding(new Insets(5, 20, 5, 20));
		
		//hboxes 
		HBox hbox = new HBox(5);
		hbox.getChildren().addAll(lblReserveCred);
		hbox.setPadding(new Insets(10));
		hbox.prefWidthProperty().bind(widthProperty());
		
		HBox hbox1 = new HBox(5);
		hbox1.getChildren().addAll(btnAdd, Remove, Confirm);
		hbox1.prefWidthProperty().bind(widthProperty());
		hbox1.setPadding(new Insets(10));
		
		//vboxes 
		VBox vbox = new VBox(8);
		vbox.getChildren().addAll(lblUnselectedTerm1, Unselected1, hbox);
		vbox.prefWidthProperty().bind(widthProperty());
		
		VBox vbox1 = new VBox(8);
		vbox1.getChildren().addAll(lblReservedTerm1, Reserved1, hbox1);
		vbox1.prefWidthProperty().bind(widthProperty());
		
		this.add(vbox, 0, 0);
		this.add(vbox1, 1, 0);

	}
	
	public ObservableList<Module> getContents(){
		return UnselectedTerm1;
	}
	
	public ObservableList<Module> getContents1(){
		return Reserved1Term1;
	}

	public ObservableList<Module> getUnselectedTerm1() {
		return UnselectedTerm1;
	}

	public void addAddHandler2(EventHandler<ActionEvent> handler) {
		btnAdd.setOnAction(handler);
	}
	
	public void addRemoveHandler2(EventHandler<ActionEvent> handler) {
		Remove.setOnAction(handler);
	}
	
	public void addConfirmHandler(EventHandler<ActionEvent> handler) {
		Confirm.setOnAction(handler);
	}
	
	public int getReservedModuleCredits() {
		int credits = 15;
		
		if(Reserved1Term1.isEmpty()) {
			for(Module m : Reserved1Term1) {
				credits += m.getModuleCredits();
			}
		}
		return credits;
	}
	
	public void addSelectedModule () {
		if(!Unselected1.getSelectionModel().isEmpty()) {
			int credits = 0;
			for(Module m : Reserved1Term1) {
				credits += m.getModuleCredits();
			}
			if(((credits + Unselected1.getSelectionModel().getSelectedItem().getModuleCredits()) <= maxLimit3)){
				Reserved1Term1.add(Unselected1.getSelectionModel().getSelectedItem());
				UnselectedTerm1.remove(Unselected1.getSelectionModel().getSelectedItem());
			}
		}
	}
	
	public void removeSelectedModule() {
		if(!Reserved1.getSelectionModel().isEmpty()) {
			int credits = 0;
			for(Module m : Reserved1Term1) {
				credits -= m.getModuleCredits();
			}
			if(((credits - Reserved1.getSelectionModel().getSelectedItem().getModuleCredits()) <= maxLimit3)) {
				UnselectedTerm1.add(Reserved1.getSelectionModel().getSelectedItem());
				Reserved1Term1.remove(Reserved1.getSelectionModel().getSelectedItem());
			}
		}
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
}

		

