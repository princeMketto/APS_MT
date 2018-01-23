package aps.view.innerview;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ParentContr implements Initializable {
	LoggerM log=new LoggerM();
    @FXML
    private Label fullnamelb;

    @FXML
    private JFXButton btnsave;

    @FXML
    private Label labelIp;

    @FXML
    private JFXTextField parname;

    @FXML
    private JFXTextField parphone;

    @FXML
    private JFXTextField parmail;

    @FXML
    private JFXTextField addres;

    @FXML
    private JFXTextField location;
    @FXML
    private JFXTextField kazi;

    @FXML
    private JFXTextField taifa;
    String[]parts;
    ObservableList<String>filteredlist = FXCollections.observableArrayList();
    ConnectDB database = new ConnectDB();
    private Connection con;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement prep;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fillData();
	}
	  private void fillData() {
		  
		 // System.out.println("FROM:"+UpStudentController.getPar());
	//parts = UpStudentController.getPar().split("_");
	parname.setText(UpStudentController.getName());
	parphone.setText(UpStudentController.getPhone());
	parmail.setText(UpStudentController.getMail());
	addres.setText(UpStudentController.getAdr());
	location.setText(UpStudentController.getLoc());
	kazi.setText(UpStudentController.getOc());
	taifa.setText(UpStudentController.getNat());
	}
	@FXML
	    void goSave(ActionEvent event) {
		 String phone=null,name=null,mail=null,addr=null,loc=null,occ=null,nation=null;
		 	try{
		 		name = parname.getText().toUpperCase();
			 	phone = parphone.getText().toUpperCase();
			 	mail = parmail.getText().toUpperCase();
			 	addr  = addres.getText().toUpperCase();
			 	loc = location.getText().toUpperCase();
			 	occ = kazi.getText().toUpperCase();
			 	nation = taifa.getText().toUpperCase();
				
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
				 con= database.dbconnect();
				 prep = (PreparedStatement) con.prepareStatement("UPDATE students SET sponsorName=?,sponsorPhone=?,sponsorMail=?,"
				 		+ "sponsorAddress=?,sponsorLocation=?,occupation=?,nationality=? WHERE student_id=?");
			    	  	
			    	  prep.setString(1, name);
			    	  prep.setString(2, phone);
			    	  prep.setString(3, mail);
			    	  prep.setString(4, addr);
			    	  prep.setString(5, loc);
			    	  prep.setString(6, occ);
			    	  prep.setString(7, nation);
			    	  prep.setString(8, UpStudentController.getID());
			    //	 System.out.println(parts[6] +"\t"+parts[7]);
					int out=prep.executeUpdate();
					if(out >0){
						 TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.SUCCESS);
						       tray.setTitle("Student Updated successfully");
						       tray.setMessage("this student has a changed parent detail(s)");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
						       log.writter("Update student with ID: "+parts[5]);
						       parname.setText(null); parphone.setText(null);parmail.setText(null);
						       addres.setText(null);
						       location.setText(null);
						       kazi.setText(null); taifa.setText(null);
					}else{
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.ERROR);
					       tray.setTitle("Failure Updating student");
					       tray.setMessage("Make sure to complete the form .");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					}
			 }catch(SQLException err){
				 	err.printStackTrace();
				  TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.ERROR);
				       tray.setTitle("Failure Updating student");
				       tray.setMessage("Make sure to select student first");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
			  	
			 }
	    }

}
