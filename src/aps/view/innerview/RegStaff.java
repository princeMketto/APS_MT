package aps.view.innerview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class RegStaff implements Initializable {
	LoggerM log=new LoggerM();
	   @FXML
	    private BorderPane borderJuju;

	    @FXML
	    private JFXButton btnsubmit;

	    @FXML
	    private JFXTextField staphone;

	    @FXML
	    private JFXTextField staaddress;

	    @FXML
	    private JFXTextField stamail;

	    @FXML
	    private JFXTextField username;

	    @FXML
	    private JFXTextField recover;

	    @FXML
	    private JFXPasswordField password;

	    @FXML
	    private JFXTextField stafirstname;

	    @FXML
	    private JFXTextField stamiddlename;

	    @FXML
	    private JFXTextField stalastname;

	    @FXML
	    private RadioButton stamale;

	    @FXML
	    private ToggleGroup stugender;

	    @FXML
	    private RadioButton stafemale;

	    @FXML
	    private JFXDatePicker stadob;

	    @FXML
	    private JFXButton btnphoto;

	    @FXML
	    private ImageView staphoto;

	    @FXML
	    private JFXTextField StaID;

	    @FXML
	    private ChoiceBox Stafstatus;

	    @FXML
	    private ChoiceBox StafsRole;
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
			    String gend,gendpar;
			    ObservableList<String>list = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stamale.setUserData("MALE");
		stafemale.setUserData("FEMALE");
		
		stamale.setToggleGroup(stugender);
		stafemale.setToggleGroup(stugender);
		
		stugender.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				  if (stugender.getSelectedToggle() != null && !stugender.getSelectedToggle().equals("Gender") ) {
					  	gend = stugender.getSelectedToggle().getUserData().toString().toUpperCase();
			          
			         }
				
			}
			
		});
		fillStatus();
		
	}

	private void fillStatus() {
		list.clear();
		list.add("Choose Status Here");
		Stafstatus.setValue("Choose Status Here");
		
		/*
		 * If we takes status from database too
		 */
	 	list.add("Admin");
	 	list.add("Teacher");
	 	list.add("Head of school");
	 	list.add("other");
	 	
	 	Stafstatus.setItems(list);
	}
	@FXML
    void attachPoto(ActionEvent event) {
    	flc  = new FileChooser();
    	selectedFile = flc.showOpenDialog(null);
    	if(selectedFile != null){
    	
    		try {
    			input = new FileInputStream(selectedFile);
				image= new Image(input);
				staphoto.setImage(image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
    	}
    }
    @FXML
    void submitData(ActionEvent event) {
    	String stuIDs = null,fname= null,mname= null,lname= null,gender= null,dateOb= null,phone= null,mail= null,status= null;
    //	Image img;
    	  LocalDate dob = null;
		String pass = null,recov = null,addres = null;
    	  Date dateOfB = null;
    	try{
    		stuIDs = StaID.getText().toUpperCase();
    		fname = stafirstname.getText().toUpperCase();
    		mname = stamiddlename.getText().toUpperCase();
    		lname = stalastname.getText().toUpperCase();
    		gender =gend;
    		dob = stadob.getValue();
    		 System.out.println(dob);
    		status = Stafstatus.getSelectionModel().getSelectedItem().toString();
    		phone = staphone.getText();
    		mail = stamail.getText();
    		addres = staaddress.getText();
    		pass = password.getText();
    		recov =  recover.getText();
    		dateOfB = Date.valueOf(dob);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Fill all important details");
		       tray.setMessage("Please Fill the form completely/correctly \n"
		       		+ "");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
        try{
	    	if(selectedFile != null){
	    input = new FileInputStream(selectedFile); 
	    	}else{
	    		selectedFile =new File("user0.png");
	    		input = new FileInputStream(selectedFile);
	    	}
	    } catch (NullPointerException  ex1) {
			// TODO Auto-generated catch block
			ex1.printStackTrace();
			//JOptionPane.showMessageDialog(null, "No attachment selected");
			 

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null, "File not found. retry");
		}
    		  if(dateOfB.equals(null) && status.equals("CHOOSE STATUS HERE")){
				  TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("Please provide Dates/Status");
				       tray.setMessage("Dear User. Date of birth and status are important.");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
			  }else{
				  
				 try{ 
					 con= database.dbconnect();
					 prep = (PreparedStatement) con.prepareStatement("INSERT INTO staffs(staff_id,firstName,middleName,lastName,Gender,dob,status,phone,address,mail,password,recover,photo) VALUES("
				    	  		+ "?,?,?,?,?,?,?,?,?,?,?,?,?)");
				    	  prep.setString(1, stuIDs);
				    	  prep.setString(2, fname);
				    	  prep.setString(3, mname);
				    	  prep.setString(4, lname);
				    	  prep.setString(5, gender);
				    	  prep.setDate(6, dateOfB);
				    	  prep.setString(7, status);
				    	  prep.setString(8, phone);
				    	  prep.setString(9, addres);
				    	  prep.setString(10, mail);
				    	  prep.setString(11, pass);
				    	  prep.setString(12, recov);
				    	 
						prep.setBinaryStream(13, (InputStream)input,(int)selectedFile.length());
						int out=prep.executeUpdate();
						if(out >0){
							 TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.SUCCESS);
							       tray.setTitle("Staff Registered successfully");
							       tray.setMessage("You can now find this staff in the list..");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
							     log.writter("Register staff with ID: "+stuIDs);
							  //empting fields
							       input = null;
							       StaID.setText(null); stafirstname.setText(null);stalastname.setText(null);
							       stamiddlename.setText(null); stadob.setValue(null);
							       staphone.setText(null); staaddress.setText(null);
							       stamail.setText(null); password.setText(null); recover.setText(null);
							       
							       
						}else{
							TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.ERROR);
						       tray.setTitle("Failure Registering staff");
						       tray.setMessage("Make sure to complete the form .");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
						}
				 }catch(SQLException err){
					 	err.printStackTrace();
					  TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.ERROR);
					       tray.setTitle("Failure Registering staff");
					       tray.setMessage("Make sure to provide a unique StaffID");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
				  	
				 }
				  
				  
			  }	
    		 
    }
}
