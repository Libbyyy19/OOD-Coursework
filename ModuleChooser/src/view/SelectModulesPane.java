package view;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Course;
import model.Module;
import model.StudentProfile;


public class SelectModulesPane extends GridPane {
	
	private ListView<Module> UnselectedTerm1;
	private ObservableList<Module> Term1;
	private ListView<Module> UnselectedTerm2;  
	private ObservableList<Module> Term2;
	private ListView<Module> SelectedYearLongMod;
	private ObservableList<Module> SelectedYearLong;
	private ListView<Module> SelectedTerm1;
	private ObservableList<Module> Selected1;
	private ListView<Module> SelectedTerm2;
	private ObservableList<Module> Selected2;
	private Button btnAdd, btnRemove, btnAdd1, btnRemove1, btnReset, btnSubmit;
	private int maxLimit; 
	private int maxLimit1;
	private TextField CreditsTerm1;
	private TextField CreditsTerm2;
	
	public SelectModulesPane() {
		
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHgrow(Priority.ALWAYS);
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHgrow(Priority.ALWAYS);
		
		//create Labels
		Label lblUnselectedTerm1 = new Label("Unselected Term 1 Modules");
		lblUnselectedTerm1.setPadding(new Insets(10));
		Label lblUnselectedTerm2 = new Label("Unselected Term 2 Modules");
		lblUnselectedTerm2.setPadding(new Insets(10));
		Label lblTerm1 = new Label("Term 1 ");
		Label lblSelectedYearLong = new Label ("Selected Year Long Modules");
		lblSelectedYearLong.setPadding(new Insets(10));
		Label lblTerm2 = new Label("Term 2 ");
		Label lblSelectedTerm1 = new Label("Selected Term 1 Modules");
		lblSelectedTerm1.setPadding(new Insets(10));
		Label lblCreditTerm1 = new Label("Credits Term 1 : ");
		Label lblSelectedTerm2 = new Label("Selected Term 2 : ");
		lblSelectedTerm2.setPadding(new Insets(10));
		Label lblCreditTerm2 = new Label("Credits Term 2 : ");
		lblCreditTerm2.setPadding(new Insets(10));
		
		//buttons
		btnAdd = new Button("Add");
		btnAdd.setPadding(new Insets(5, 20, 5, 20));
		btnRemove = new Button("Remove");
		btnRemove.setPadding(new Insets(5, 20, 5, 20));
		btnAdd1 = new Button("Add");
		btnAdd1.setPadding(new Insets(5, 20, 5, 20));
		btnRemove1 = new Button("Remove");
		btnRemove1.setPadding(new Insets(5, 20, 5, 20));
		btnReset = new Button("Reset");
		btnReset.setPadding(new Insets(5, 20, 5, 20));
		btnSubmit = new Button("Submit");
		btnSubmit.setPadding(new Insets(5, 20, 5, 20));
		btnSubmit.setAlignment(Pos.TOP_LEFT);
		
		
		//Textfields 
		CreditsTerm1 = new TextField("0");
		CreditsTerm1.setEditable(false);
		CreditsTerm1.setPrefHeight(5);
		CreditsTerm1.setPrefWidth(30);
		
		CreditsTerm2 = new TextField("0");
		CreditsTerm2.setEditable(false);
		CreditsTerm2.setPrefHeight(5);
		CreditsTerm2.setPrefWidth(30);
		
		//Listviews
		Term1 = FXCollections.observableArrayList();
		UnselectedTerm1 = new ListView<>(Term1);
		
		Term2 = FXCollections.observableArrayList();
		UnselectedTerm2 = new ListView<>(Term2);
		
		SelectedYearLong = FXCollections.observableArrayList();
		SelectedYearLongMod = new ListView<>(SelectedYearLong);
		
		Selected1 = FXCollections.observableArrayList();
		SelectedTerm1 = new ListView<>(Selected1);
		
		Selected2 = FXCollections.observableArrayList();
		SelectedTerm2 = new ListView<>(Selected2);
		
		//vboxes & hboxes
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15));
		vbox.prefWidthProperty().bind(widthProperty());

		VBox vbox1 = new VBox();
		vbox1.setPadding(new Insets(15));
		vbox1.prefWidthProperty().bind(widthProperty());
		
		HBox hbox = new HBox(8);
		hbox.setPadding(new Insets(10));
		hbox.getChildren().addAll(lblTerm1, btnAdd, btnRemove);
		hbox.setAlignment(Pos.CENTER);
		hbox.prefWidthProperty().bind(widthProperty());
		
		HBox hbox1 = new HBox(8);
		hbox1.setPadding(new Insets(10));
		hbox1.getChildren().addAll(lblTerm2, btnAdd1, btnRemove1);
		hbox1.setAlignment(Pos.CENTER);
		hbox1.prefWidthProperty().bind(widthProperty());
		
		HBox hbox2 = new HBox(8);
		hbox2.setPadding(new Insets(10));
		hbox2.getChildren().addAll(lblCreditTerm1, CreditsTerm1);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.prefWidthProperty().bind(widthProperty());
		
		HBox hbox3 = new HBox(8);
		hbox3.setPadding(new Insets(10));
		hbox3.getChildren().addAll(lblCreditTerm2, CreditsTerm2);
		hbox3.setAlignment(Pos.CENTER);
		hbox2.prefWidthProperty().bind(widthProperty());
		
		vbox.getChildren().addAll(lblUnselectedTerm1, UnselectedTerm1, hbox, lblUnselectedTerm2, UnselectedTerm2, hbox1, hbox2, btnReset);
		vbox1.getChildren().addAll(lblSelectedYearLong, SelectedYearLongMod, lblSelectedTerm1, SelectedTerm1, lblSelectedTerm2, SelectedTerm2, hbox3, btnSubmit);
		btnReset.setAlignment(Pos.BASELINE_RIGHT);
		this.add(vbox, 0, 0);
		this.add(vbox1, 1, 0);
	}
	
	public ObservableList<Module> getContents() {
		return Term1;
	}
	
	public ObservableList<Module> getContents1(){
		return Term2;
	}
	
	public ObservableList<Module> getContents2(){
		return SelectedYearLong;
	}
	
	public ObservableList<Module> getContents3(){
		return Selected1;
	}
	
	public ObservableList<Module> getContents4(){
		return Selected2;
	}
	
	//in if statement 
	public void addModuleTerm1Unselected(Module m) {
		Term1.add(m);
	}
	
	//in if statement
	public void addModuleTerm2Unselected(Module m) {
		Term2.add(m);
	}
	
	//in if statement 
	public void addModuleYearLong(Module m) {
		SelectedYearLong.add(m);
	}
	
	public boolean addSelectedModule(Module m) {
		return Selected1.add(m);
	}
	
	public void removeModule(Module m) {
		Term1.remove(m);
	}
	
	//button handlers 
	public void addAddHandler(EventHandler<ActionEvent> handler) {
		btnAdd.setOnAction(handler);
	}

	public void addRemoveHandler(EventHandler<ActionEvent> handler) {
		btnRemove.setOnAction(handler);
	}
	
	public void addAddHandler1(EventHandler<ActionEvent> handler) {
		btnAdd1.setOnAction(handler);
	}
	
	public void addRemoveHandler1(EventHandler<ActionEvent> handler) {
		btnRemove1.setOnAction(handler);
	}
	
	public void addResetHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
	}
	
	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}
	
	//retrieves the amount of credits
		public int getCredits() {
		return Integer.parseInt(CreditsTerm1.getText());
	}
		public int getCredits2() {
			return Integer.parseInt(CreditsTerm2.getText());
	}
	
	public ObservableList<Module> getTerm1(){
		return Term1;
	}
	public TextField getCreditsField() {
		return CreditsTerm1;
	}
	
	public TextField getCreditsField2() {
		return CreditsTerm2;
		
	}
	//gets max amount of credits needed 
	public int getMaxLimit() {
		return maxLimit;
	}
	
	public int getMaxLimit1() {
		return maxLimit1;
	}
	//sets max amount of credits 
	public void setMaxLimit(int maxLimit) {
		if(!(maxLimit < 1)) {
			this.maxLimit = maxLimit; 
	}
}
	public void setMaxLimit1(int maxLimit1) {
		if(!(maxLimit1 < 1)) {
			this.maxLimit1 = maxLimit1;
		}
	}
	
	public void addCredits(int credits) {
		CreditsTerm1.setText(String.valueOf(Integer.parseInt(CreditsTerm1.getText()) + credits ));
	}
	
	public void addCredits2(int credits) {
		CreditsTerm2.setText(String.valueOf(Integer.parseInt(CreditsTerm2.getText()) + credits ));
	}
	
	public void removeCredits(int credits) {
		CreditsTerm1.setText(String.valueOf(Integer.parseInt(CreditsTerm1.getText()) - credits ));
	}
	
	public void removeCredits2(int credits) {
		CreditsTerm2.setText(String.valueOf(Integer.parseInt(CreditsTerm2.getText()) - credits));
	}
	
	public void clearCredits() {
		CreditsTerm1.setText("0");
		CreditsTerm2.setText("0");
	}

	public void setCredits(int credits) {
		CreditsTerm1.setText(String.valueOf(15));
		
	}
	
	public void setCredits2(int credits) {
		CreditsTerm2.setText(String.valueOf(15));
	}
	
	public int getSelectedModuleCredits() {
		
		int credits1 = 15;
		
		if(Selected1.isEmpty()) {
			for(Module m : Selected1) {
			 credits1 += m.getModuleCredits();
			}
		}
		
		return credits1;
	}
	
	public int getSelectedModuleCredits2() {
		
		int credits = 15;
		
		if(Selected2.isEmpty()) {
			for(Module m : Selected2) {
				credits += m.getModuleCredits();
			}
		}
		
		return credits;
	}
	
	//clears both observable lists
		public void clearModules() {
			Selected1.clear();
			Selected2.clear();
			Term1.clear();
			Term2.clear();
			SelectedYearLong.clear();
	}
		
	public void addMandatorySelectedMod(Module m) {
		SelectedYearLong.add(m);
	}
	
	
	//adds a module from unselected list to the selected list 
	public void addUnselectedModuleToSelected() {

		if(!UnselectedTerm1.getSelectionModel().isEmpty()) {
			int credits = 0;
			for(Module m : Selected1) {
				credits += m.getModuleCredits();
			}
			if(((credits + UnselectedTerm1.getSelectionModel().getSelectedItem().getModuleCredits()) <= maxLimit)) {
				Selected1.add(UnselectedTerm1.getSelectionModel().getSelectedItem());
				Term1.remove(UnselectedTerm1.getSelectionModel().getSelectedItem());
			} 
		}
	}
	
	public void addUnselected2ModuleToSelected() {
		
		if(!UnselectedTerm2.getSelectionModel().isEmpty()) {
			int credits1 = 0;
			for(Module m : Selected2) {
				credits1 += m.getModuleCredits();
			}
			if(((credits1 + UnselectedTerm2.getSelectionModel().getSelectedItem().getModuleCredits()) <= maxLimit1)) {
				Selected2.add(UnselectedTerm2.getSelectionModel().getSelectedItem());
				Term2.remove(UnselectedTerm2.getSelectionModel().getSelectedItem());
			} 
		}
	}
	
	public void removeSelectedModule(StudentProfile details, Course[] courses) {
		
		if(!SelectedTerm1.getSelectionModel().isEmpty()) {
			int credits = 0;
			for(Module m : Selected1) {
				credits -= m.getModuleCredits();
			}
			if(((credits - SelectedTerm1.getSelectionModel().getSelectedItem().getModuleCredits()) <= maxLimit)) {
				Term1.add(SelectedTerm1.getSelectionModel().getSelectedItem());
				Selected1.remove(SelectedTerm1.getSelectionModel().getSelectedItem());
			} 
		}	
	}
	
	public void removeSelectedModule2(StudentProfile details, Course[] courses) {
		
		if(!SelectedTerm2.getSelectionModel().isEmpty()) {
			int credits = 0;
			for(Module m : Selected2) {
				credits -= m.getModuleCredits();
			}
			if(((credits - SelectedTerm2.getSelectionModel().getSelectedItem().getModuleCredits()) <= maxLimit1)) {
				Term2.add(SelectedTerm2.getSelectionModel().getSelectedItem());
				Selected2.remove(SelectedTerm2.getSelectionModel().getSelectedItem());
			
			} 
		}
	}
}
	

