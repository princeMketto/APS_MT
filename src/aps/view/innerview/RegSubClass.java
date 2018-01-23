package aps.view.innerview;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import aps.view.ConnectDB;
import aps.view.LoggerM;
import aps.view.WorkIndicatorDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class RegSubClass implements Initializable {
	LoggerM log=new LoggerM();
	   @FXML
	    private JFXTextField subcode;

	    @FXML
	    private JFXTextField subname,combname;

	    @FXML
	    private ChoiceBox subcategory;

	    @FXML
	    private JFXButton btnsubject,btncomb;

	    @FXML
	    private ChoiceBox<String> classname;

	    @FXML
	    private ChoiceBox classtream;

	    @FXML
	    private JFXButton btnclass;
	    private WorkIndicatorDialog wd=null;
		ConnectDB database = new ConnectDB();
		 private Connection con;
		    private ResultSet rs;
		    private Statement st;
		    private PreparedStatement prep;
			  FileInputStream input;
				InputStream is = null;
				Image image=null;
				FileChooser flc;
			    File selectedFile ;
			    ObservableList<String>list = FXCollections.observableArrayList();
			    ObservableList<String>list1 = FXCollections.observableArrayList();
			    ObservableList<String>list2 = FXCollections.observableArrayList();
	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	fillcats();
	fillClass();
	
	
	fillStream();
	}

	private void fillStream() {
		classname.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String old, String arg2) {
			if(arg2.equals("FORM I") || arg2.equals("FORM II") || arg2.equals("FORM III") || arg2.equals("FORM IV")){
				list2.clear();
				list2.add("Choose Stream/comb here");
				classtream.setValue("Choose Stream/comb here");

				/*
				 * If we takes status from database too
				 */
			 	list2.add("A");
			 	list2.add("B");
			 	list2.add("C");
			 	list2.add("D");
			 	list2.add("E");
			 	list2.add("F");
				list2.add("G");
			 	list2.add("H");
			 	classtream.setItems(list2);
			}else if(arg2.equals("FORM V") || arg2.equals("FORM VI")){
				list2.clear();
				list2.add("Choose Stream/comb here");
				classtream.setValue("Choose Stream/comb here");
			  	try{
					con= database.dbconnect();
					   st= con.createStatement();
					   String query = "SELECT comb FROM combination ";
					   rs=st.executeQuery(query);
					   	while(rs.next()){
					   		String nam = rs.getString("comb");
					   	list2.add("-"+nam);
					   	}
					   	classtream.setItems(list2);
					   	con.close();
				}catch(Exception err){
					err.printStackTrace();
				}
				
			}
				
			}
			
		});
		
		
	
		
	}

	private void fillClass() {
		list1.clear();
		list1.add("Choose classname here");
		classname.setValue("Choose classname here");

		/*
		 * If we takes status from database too
		 */
	 	list1.add("FORM I");
	 	list1.add("FORM II");
	 	list1.add("FORM III");
	 	list1.add("FORM IV");
	 	list1.add("FORM V");
	 	list1.add("FORM VI");
	 	classname.setItems(list1);
	 	
	}

	private void fillcats() {
		list.clear();
		list.add("Choose category Here");
		subcategory.setValue("Choose category Here");
		
		/*
		 * If we takes status from database too
		 */
	 	list.add("General");
	 	list.add("Computer");
	 	list.add("Business");
	 	list.add("Science");
		list.add("Religion");
	 	list.add("Literature");
	 	list.add("Advance");
	 	
	 	subcategory.setItems(list);
	}

    @FXML
    void goSubject(ActionEvent event) {
    	String subcodes = null, subnam = null,subcat=null;
    	try{
    		subcodes = subcode.getText().toUpperCase();
    		subnam = subname.getText().toUpperCase();
    		subcat = subcategory.getSelectionModel().getSelectedItem().toString();
    	}catch(Exception e){
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Fill all important details");
		       tray.setMessage("Please Fill the form completely/correctly \n"
		       		+ "");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    	if(subcat.equals("CHOOSE STATUS HERE")){
			  TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Please provide Dates/Status");
			       tray.setMessage("Dear User. Date of birth and status are important.");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
		  }else{
    	try{
    		con= database.dbconnect();
    		 prep = (PreparedStatement) con.prepareStatement("INSERT INTO subjects(subjectCode,subjectName,subjectUnit,category) VALUES("
		    	  		+ "?,?,?,?)");
		    	  prep.setString(1, subcodes);
		    	  prep.setString(2, subnam);
		    	  prep.setString(3, "2");
		    	  prep.setString(4, subcat);
		    		int out=prep.executeUpdate();
		    		if(out >0){
						 TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.SUCCESS);
						       tray.setTitle("Subject Registered successfully");
						       tray.setMessage("You can now find this subject in the list..");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
						       log.writter("Register subject with code: "+subcodes);
						  //empting fields
						       input = null;
						       subcode.setText(null); subname.setText(null);
						       
						       
					}else{
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.ERROR);
					       tray.setTitle("Failure Registering subject");
					       tray.setMessage("Make sure to complete the form .");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					}
    	}catch(SQLException sq){
    		 TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Failure Registering Subject");
		       tray.setMessage("Make sure to provide a unique Subject code");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
		  }
    }
    @FXML
    void goComb(ActionEvent event) {
    	String comname = null;
    	try{
    		comname = combname.getText().toUpperCase();
    	}catch(Exception e){
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Fill all important details");
		       tray.setMessage("Please Fill the form completely/correctly \n"
		       		+ "");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    	try{
    		con= database.dbconnect();
   		 prep = (PreparedStatement) con.prepareStatement("INSERT INTO combination(comb) VALUES("
		    	  		+ "?)");
		    	  prep.setString(1, comname);
		    	
		    		int out=prep.executeUpdate();
		    		if(out >0){
						 TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.SUCCESS);
						       tray.setTitle("Combination Registered successfully");
						       tray.setMessage("You can now find this Combination in the list..");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
						       log.writter("Register combination name: "+comname);
						  //empting fields
						       
						       combname.setText(null); 
						       
						       
					}else{
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.ERROR);
					       tray.setTitle("Failure Registering Combination");
					       tray.setMessage("Make sure to complete the form .");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					}

    	}catch(SQLException sq){
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Combination exists");
		       tray.setMessage("Make sure to write unique combination.");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    	fillStream();
    }

    @FXML
    void goClass(ActionEvent event) {
    	String clasnam = null, clascomb = null,classinfo=null;
    	try{
    		clasnam = classname.getSelectionModel().getSelectedItem().toString();
    		clascomb = classtream.getSelectionModel().getSelectedItem().toString();
    		classinfo = clasnam+""+clascomb;
    	}catch(Exception e){
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Fill all important details");
		       tray.setMessage("Please Fill the form completely/correctly \n"
		       		+ "");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    	if(clasnam.equals("Choose classname here") && clascomb.equals("Choose Stream/comb here")){
			  TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Please provide Class name/Stream");
			       tray.setMessage("Dear User.Class names are important.");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
		  }else{
    	try{
    		con= database.dbconnect();
    		 prep = (PreparedStatement) con.prepareStatement("INSERT INTO classes(className) VALUES("
		    	  		+ "?)");
		    	  prep.setString(1, classinfo);
		    	
		    		int out=prep.executeUpdate();
		    		if(out >0){
						 TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.SUCCESS);
						       tray.setTitle("Class Registered successfully");
						       tray.setMessage("You can now find this Class in the list..");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
						       log.writter("Register class name: "+classinfo);
						  //empting fields
						       input = null;
						       classname.getSelectionModel(); 
						       
						       
					}else{
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.ERROR);
					       tray.setTitle("Failure Registering class");
					       tray.setMessage("Make sure to complete the form .");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					}
    	}catch(SQLException sq){
    		 TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Failure Registering class");
		       tray.setMessage("Make sure to provide a unique class/comb/stream");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
		  }
    }
	}


