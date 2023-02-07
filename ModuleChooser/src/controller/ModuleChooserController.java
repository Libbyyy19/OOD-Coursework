
package controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import model.Course;
import model.Module;
import model.StudentProfile;
import model.Schedule;
import static model.Schedule.TERM_1;
import static model.Schedule.TERM_2;
import static model.Schedule.YEAR_LONG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Collection;

import view.ModuleChooserRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.CreateStudentProfilePane;
import view.ModuleChooserMenuBar;
import view.Term1ModulesReservePane;
import view.Term2ModulesReservePane;

public class ModuleChooserController {

	//fields to be used throughout class
	private ModuleChooserRootPane view;
	private StudentProfile model;
	private CreateStudentProfilePane cspp;
	private ModuleChooserMenuBar mstmb;
	private SelectModulesPane smp;
	private ReserveModulesPane tmrp;
	private Term1ModulesReservePane tmr;
	private Term2ModulesReservePane tmr2;
	private OverviewSelectionPane osp;
	private int maxLimit;
	private int maxLimit1;
	private int maxLimit3;
	private int minLimit;
	private int minLimit1;
	private int minLimit2;
	private Course[] courses;

	
	public ModuleChooserController(ModuleChooserRootPane view, StudentProfile model) {
		//initialise view and model fields
		this.view = view;
		this.model = model;
		
		maxLimit = 60;
		maxLimit1 = 60;
		maxLimit3 = 30;
		minLimit = 15;
		minLimit1 = 30;
		minLimit2 = 0;
		
		this.attachBindings();
		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		mstmb = view.getModuleSelectionToolMenuBar();
		smp = view.getSelectModulesPane();
		tmrp = view.getReserveModulesPane();
		tmr = tmrp.getTerm1ModulesReservePane();
		tmr2 = tmrp.getTerm2ModulesReservePane();
		osp = view.getOverviewSelection();
		cspp.addbtnDisableBind(cspp.isFieldEmpty());
		
		
		smp.setMaxLimit(maxLimit);
		smp.setMaxLimit1(maxLimit1);
		tmr.setMaxLimit3(maxLimit3);
		tmr2.setMaxLimit3(maxLimit3);
		
		//add courses to combobox in create student profile pane using the generateAndGetCourses helper method below
		cspp.addCoursesToComboBox(generateAndGetCourses());
		
		//attach event handlers to view using private helper method
		this.attachEventHandlers();	
	}
	
	private void attachBindings() {
		
	}
	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {
		
		//attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		smp.addAddHandler(new AddHandler());
		smp.addAddHandler1(new AddHandler1());
		smp.addRemoveHandler(new RemoveHandler());
		smp.addRemoveHandler1(new RemoveHandler1());
		smp.addResetHandler(new ResetHandler());
		smp.addSubmitHandler(new SubmitHandler());
		
		tmr.addAddHandler2(new AddHandler2());
		tmr.addRemoveHandler2(new RemoveHandler2());
		tmr.addConfirmHandler(new ConfirmHandler());
		
		tmr2.addAddHandler3(new AddHandler3());
		tmr2.addRemoveHandler3(new RemoveHandler3());
		tmr2.addConfirmHandler1(new ConfirmHandler1());
		
		osp.addSaveOverviewHander(new SaveOverviewHandler());
		
		//attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
		mstmb.addAboutHandler(e -> this.alertDialogBuilder(AlertType.INFORMATION, "Information dialog", null, "Version 1.0"));
		mstmb.addLoadHandler(new LoadHandler());
		mstmb.addSaveHandler(new SaveHandler());
	}

	//event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
		
			model.setStudentCourse(cspp.getSelectedCourse());
			model.setStudentName(cspp.getStudentName());
			model.setStudentEmail(cspp.getStudentEmail());
			model.setStudentPnumber(cspp.getStudentPnumber());
			model.setSubmissionDate(cspp.getStudentDate());
			System.out.println(model.toString());
			
			for(Module m : model.getStudentCourse().getAllModulesOnCourse()) {
				
				//if the delivery is equal to term 1, adds the module to unselectedTerm1 listview 
				if (m.getDelivery().equals(TERM_1)) 
				{
					smp.addModuleTerm1Unselected(m);
				}
				
				//if the delivery is equal to term 2, adds the module to unselectedTerm2 listview
				if(m.getDelivery().equals(TERM_2)) 
				{
					smp.addModuleTerm2Unselected(m);
				}
				
				//if the module is mandatory and equal to term 1, adds module to Yearlong list view 
				if(m.isMandatory() && m.getDelivery().equals(TERM_1)) 
				{
					smp.addModuleYearLong(m);
					smp.addCredits(15);
					smp.removeModule(m);
				}
				
				//if module is mandatory and equal to term2, adds the module to the year long list view 
				if(m.isMandatory() && m.getDelivery().equals(TERM_2)) 
				{
					smp.addModuleYearLong(m);
					smp.addCredits2(15);
				}
				
				if(m.isMandatory() && m.getDelivery().equals(YEAR_LONG))
				{
					smp.addModuleYearLong(m);
					smp.addCredits(15);
					smp.addCredits2(15);
				}
				view.changeTab(1);
			}
			
		}
	}
	
	private class AddHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
		
				if(smp.getCredits() >= 0 && smp.getCredits() < maxLimit){
					smp.addUnselectedModuleToSelected();
					smp.addCredits(smp.getSelectedModuleCredits());
				} else {
					alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Too many modules selected, max credits reached", "Error");
			}
		}
	}
	
	private class AddHandler1 implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			
			if(smp.getCredits2() >= 0 && smp.getCredits2() < maxLimit1) {
				smp.addUnselected2ModuleToSelected();
				smp.addCredits2(smp.getSelectedModuleCredits2());
			} else {
				alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Too many modules selected, max credits reached", "ERROR");
			}
		}
	}
	
	private class AddHandler2 implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
				
			if(tmr.getCredits() >= 0 && tmr.getCredits() < maxLimit3) {
				tmr.addSelectedModule();
				tmr.addCredits(tmr.getReservedModuleCredits());
				} else {
					alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Too many modules selected, maximum credits reached", "Error");
				}
			}
		}
	
	private class AddHandler3 implements EventHandler<ActionEvent>{
		public void handle (ActionEvent e) {
			
			if(tmr2.getCredits() >= 0 && tmr2.getCredits() < maxLimit3) {
				tmr2.addSelectedModuletoReserve();
				tmr2.addCredits(tmr2.getReservedModuleCredits());
			} else {
				alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Too many modules selected, max credits reached", "Error");
			}
		}
	}
		public class RemoveHandler implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
			
				if(smp.getCredits() >= 0 && smp.getCredits() > minLimit1) {
					smp.removeSelectedModule(model, courses);
					smp.removeCredits(smp.getSelectedModuleCredits());
				}
			}
		}
		public class RemoveHandler1 implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				
				if(smp.getCredits2() >= 0 && smp.getCredits2() > minLimit) {
					smp.removeSelectedModule2(model, courses);
					smp.removeCredits2(smp.getSelectedModuleCredits2());
					} 
			}
		}
		public class RemoveHandler2 implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				
				if(tmr.getCredits() >= 0 && tmr.getCredits() > minLimit2) {
				tmr.removeSelectedModule();
				tmr.removeCredits(tmr.getReservedModuleCredits());
			}
		}
	}
		private class RemoveHandler3 implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				
				if(tmr2.getCredits() >= 0 && tmr.getCredits() > minLimit2) {
				tmr2.removeSelectedModule();
				tmr2.removeCredits(tmr2.getReservedModuleCredits());
			}
		}
	}	
		private class ConfirmHandler implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				  if (tmr.getCredits() != 30) {
					  alertDialogBuilder(AlertType.ERROR, "Not Enough reserved Modules Selected", null, "Please selected 30 credits worth of Modules");
					  return;
				  }
				  alertDialogBuilder(AlertType.CONFIRMATION, "Modules Selected", null, "Reserved modules worth 30 credits have been Successfully selected");
				  tmrp.changePane();
			}
		}
		
		private class ConfirmHandler1 implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				if(tmr2.getCredits() != 30) {
					alertDialogBuilder(AlertType.ERROR, "Not enough reserved modules for term 2 selected", null, "Please select 30 credits worth of term 2 modules");
					return;
				}
				ObservableList<Module> ModulesYearLong = smp.getContents2();
				ObservableList<Module> ModulesTerm1 = smp.getContents3();
				ObservableList<Module> ModulesTerm2 = smp.getContents4();
				ObservableList<Module> ReservedT1 = tmr.getContents1();
				ObservableList<Module> ReservedT2 = tmr2.getContents1();
				ModulesTerm1.forEach(m -> model.addSelectedModule(m));
				ModulesTerm2.forEach(m -> model.addSelectedModule(m));
				ModulesYearLong.forEach(m -> model.addSelectedModule(m));
				ReservedT1.forEach(m -> model.addReservedModule(m));
				ReservedT2.forEach(m -> model.addReservedModule(m));
				setProfileOverview();
				alertDialogBuilder(AlertType.CONFIRMATION, "Modules selected", null, "Reserved Modules have been selected Successfully");
				
				if(tmr2.getContents1().isEmpty()) {
					alertDialogBuilder(AlertType.INFORMATION, "Please select Correct amount of modules", null, "Please select 30 credis worth of modules");
				} else {
				view.changeTab(3);
				 }
			
			}
		}
		public void setProfileOverview() {
			TextArea profileArea = osp.getProfileText();
			TextArea SelectedModule = osp.getSelectedModule();
			TextArea ReservedModules = osp.getReservedModules();
			Collection<Module> modules1 = model.getAllReservedModules();
			Collection<Module> modules = model.getAllSelectedModules();
			profileArea.setText(
					"First Name: " + model.getStudentName().getFirstName() + "\n" + "==========" + "\n" +
					"Last Name: " + model.getStudentName().getFamilyName() + "\n" + "==========" + "\n" +
					"P-Number: " + model.getStudentPnumber().toUpperCase() + "\n" +"==========" + "\n" +
					"Email: " + model.getStudentEmail() + "\n" + "==========" + "\n" +
					"Date: " + model.getSubmissionDate() + "\n"
					);
			modules.forEach(m -> {
				SelectedModule.appendText(m.toString() + "\n");
			});
			modules1.forEach(m -> {
				ReservedModules.appendText(m.toString() + "\n");
			});
			
			
		}
		public class ResetHandler implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {

						smp.clearModules();
						smp.clearCredits();
						
						for(Module m : model.getStudentCourse().getAllModulesOnCourse()) {
							
							if (m.getDelivery().equals(TERM_1)) 
							{
								smp.addModuleTerm1Unselected(m);
							}
							
							if(m.getDelivery().equals(TERM_2)) 
							{
								smp.addModuleTerm2Unselected(m);
							}
							
							if(m.isMandatory() && m.getDelivery().equals(TERM_1)) 
							{
								smp.addModuleYearLong(m);
								smp.addCredits(15);
							}
							
							if(m.isMandatory() && m.getDelivery().equals(TERM_2)) 
							{
								smp.addModuleYearLong(m);
								smp.addCredits2(15);
							}
							
							if(m.isMandatory() && m.getDelivery().equals(YEAR_LONG))
							{
								smp.addModuleYearLong(m);
								smp.addCredits(15);
								smp.addCredits2(15);
							}
						}
					}		
				}
		
		public class SubmitHandler implements EventHandler<ActionEvent> {
			public void handle (ActionEvent e) {
			
				for(Module m : smp.getContents()) {
					tmrp.getTerm1ModulesReservePane().getContents().add(m);
				}
				
				for(Module m : smp.getContents1()) {
					tmrp.getTerm2ModulesReservePane().getContents().add(m);
				}
				if (smp.getCredits() != 60) {
					alertDialogBuilder(AlertType.INFORMATION, "Credits do not meet requirements", null, "Please Select more Modules to Continue");
				} 
				if(smp.getCredits2() != 60) {
					alertDialogBuilder(AlertType.INFORMATION, "Credits do not meet requirements", null, "Please select correct amount of Credits (60) to continue");
				} else {
					view.changeTab(2);
			} 
		}
	}
		public class SaveHandler implements EventHandler<ActionEvent>{
			public void  handle (ActionEvent e) {
				
				
				
				try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("profileObj.dat"));){
					
					oos.writeObject(model);
					oos.flush();
					
					alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Saved Successfully", "Profile saved to profileObj.dat");
				}
				catch(IOException ioExcep) {
					System.out.println("Error saving");
				}
			}
		}
		
		public class LoadHandler implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				
				try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("profileObj.dat"));){
					
					model = (StudentProfile) ois.readObject();
					
				}
				catch (IOException ioExcep) {
					System.out.println("Error loading");
				}
				catch (ClassNotFoundException c) {
					System.out.println("Class not found");
				}
	
				smp.clearModules();
		}
	}	
		
		public class SaveOverviewHandler implements EventHandler<ActionEvent>{
			public void handle(ActionEvent e) {
				
				try {
					
					PrintWriter out = new PrintWriter(new File("Overview.txt"));
					out.println(osp.getOutput());
					out.close();
					
					alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Save Success", "Overview saved to Overview.txt File");
					System.exit(0);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					
				}
			}
		}
		private void alertDialogBuilder(AlertType type, String title, String header, String content) {
			Alert alert = new Alert(type);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(content);
			alert.showAndWait();
		}
			
	//helper method - generates course and module data and returns courses within an array
	private Course[] generateAndGetCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Schedule.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Schedule.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Schedule.TERM_2);	
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Schedule.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Schedule.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Schedule.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Schedule.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Schedule.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Schedule.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Schedule.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Schedule.TERM_1);
		Module ctec3911 = new Module("CTEC3911", "Mobile Application Development", 15, false, Schedule.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Schedule.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Schedule.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, Schedule.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Schedule.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Schedule.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Schedule.TERM_2);


		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(ctec3451);
		compSci.addModuleToCourse(ctec3902_CompSci);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(ctec3911);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);
		
		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(ctec3451);
		softEng.addModuleToCourse(ctec3902_SoftEng);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(ctec3911);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		
		return courses;
	}

	
}
	