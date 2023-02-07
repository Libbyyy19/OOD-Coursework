package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;



public class OverviewSelectionPane extends GridPane {

	
	private TextArea profile, SelectedModule, ReservedModules;
	private Button btnSave;
	private String print;
	
	public OverviewSelectionPane() {
		
		this.setPadding(new Insets(10));
		this.setVgap(10);
		this.setHgap(10);
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHgrow(Priority.ALWAYS);
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHgrow(Priority.ALWAYS);
		
		this.getColumnConstraints().addAll(column0, column1);
		
	
		//textareas
		profile = new TextArea("Profile" + "\n" + "==================" + "\n");
		profile.prefWidthProperty().bind(widthProperty());
		SelectedModule = new TextArea("Selected Modules" + "\n" + "=================" + "\n");
		SelectedModule.prefWidthProperty().bind(widthProperty());
		ReservedModules = new TextArea("Reserved Modules" + "\n" + "=============" + "\n"
		);
		ReservedModules.prefWidthProperty().bind(widthProperty());
		
		//button
		btnSave = new Button("Save Overview");
		btnSave.setPadding(new Insets(5, 20, 5, 20));
		
		
		//vboxes & hboxes
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(30));
		vbox.prefWidthProperty().bind(widthProperty());
		
		HBox hbox = new HBox(10);
		hbox.setPadding(new Insets(30));
		hbox.prefWidthProperty().bind(widthProperty());
		
		VBox vbox1 = new VBox();
		vbox1.setPadding(new Insets(30));
		vbox1.prefWidthProperty().bind(widthProperty());
		
		vbox.getChildren().addAll(profile);
		vbox1.getChildren().addAll(btnSave);
		hbox.getChildren().addAll(SelectedModule, ReservedModules);
		
		

		
		this.add(vbox, 0, 1);
		this.add(hbox, 0, 2);
		this.add(vbox1, 0, 3);
		
		
	
	}
	public void addSaveOverviewHander(EventHandler <ActionEvent> handler ) {
		btnSave.setOnAction(handler);
	}
	public TextArea getProfileText() {
		return profile;
	}
	
	public TextArea getSelectedModule() {
		return SelectedModule;
	}
	
	public TextArea getReservedModules() {
		return ReservedModules;
	}
	public String getOutput() {
		return print;
	}
	
}
