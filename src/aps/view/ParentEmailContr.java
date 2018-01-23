package aps.view;

import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.AuthenticationFailedException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.mail.util.MailConnectException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ParentEmailContr implements Initializable {

    @FXML
    private JFXTextField num;

    @FXML
    private JFXTextArea mess;

    @FXML
    private Label name;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXButton btnReport;

    @FXML
    private JFXButton btnAttach;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXTextField subj;
    @FXML
    private Label attachlabel;
    ConnectDB database = new ConnectDB();
    private Connection con;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement prep;
    private WorkIndicatorDialog wd=null;
    String xculMail,xculName,parMail,parMail2,xculpass;
    int s=0;
    String []par;
	MailingService mai;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getSchoolMail();
		 mai = new MailingService();
	}
    @SuppressWarnings("unchecked")
	private void getSchoolMail() {
      	wd = new WorkIndicatorDialog(null, "Init. service...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   par = LoadStudent.getParData().toString().split("-");
	        	   
	        	   Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+"); // [A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}
	   			Matcher m = pattern.matcher( LoadStudent.getParMail().toString());
	   			if(m.find() && m.group().equals(LoadStudent.getParMail().toString())){
	   				//return true;
	   				parMail = LoadStudent.getParMail().toString().toLowerCase();
	   			}else{
	   				//
	   				parMail = "";
	   			 TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("parent EMAIL is INVALID");
			       tray.setMessage("please make sure to use valid email address"
			       		+ "");
			       tray.setAnimationType(AnimationType.POPUP);
			       tray.showAndDismiss(Duration.millis(5000));
	   			}
	       		
	       		
	       		name.setText(par[0]);
	        	   num.setText(parMail);
	        	   
	        	   
	           }else if(outres.equals("0")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.ERROR);
			       tray.setTitle("Please configure school EMAIL & PASSWORD first");
			       tray.setMessage("navigate to settings to set school EMAIL"
			       		+ "");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
	           } else if(outres.equals("2")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("looks like you didnt set school details");
			       tray.setMessage("navigate to settings to configure details."
			       		+ "");
			       tray.setAnimationType(AnimationType.POPUP);
			       tray.showAndDismiss(Duration.millis(5000));
	           }
	           wd=null;
	 	   });
			 wd.exec("fetch", inputParam -> {
		           // Thinks to do...
		           // NO ACCESS TO UI ELEMENTS!
	 			try{
	 				con= database.dbconnect();
	 				   st= con.createStatement();
	 				   String query = "SELECT * FROM school_details ";
	 				   rs=st.executeQuery(query);
	 				   xculMail=null; xculName=null;xculpass=null;
	 				   if(rs.next()){
	 					   xculName = rs.getString("title");
	 					   xculMail = rs.getString("mail");
	 					  xculpass = rs.getString("password");
	 					   if(!(xculMail.length() == 0) || xculpass.length() ==0){
	 					   s = 1;
	 					   }else{
	 						   s=0;
	 					   }
	 				   }else{
	 					  s = 2; 
	 				   }
	 			}catch(SQLException err){
	 				err.printStackTrace();
	 			}
		       
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		           
		           return new Integer(s);
		           
		           
		        });
	}
	@FXML
    void attachFile(ActionEvent event) {

    }

    @SuppressWarnings("unchecked")
	@FXML
    void send(ActionEvent event) {
    
    	Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+"); // [A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}
		Matcher m = pattern.matcher( num.getText());
		if(m.find() && m.group().equals(num.getText())){
			//return true;
			wd = new WorkIndicatorDialog(null, "Sending Mail...");
		 	   wd.addTaskEndNotification(result -> {
		 		  String outres = result.toString();
		          // System.out.println("nomaa "+outres);
		           if(outres.equals("1")){
		        	   TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.SUCCESS);
				       tray.setTitle("Your Email has been sent");
				       tray.setMessage("your email has been sent to the recipient"
				       		+ "");
				       tray.setAnimationType(AnimationType.POPUP);
				       tray.showAndDismiss(Duration.millis(5000));
				       mess.setText(null);
				       subj.setText(null);
		           }else if(outres.equals("2")){
		        	   TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("you cant send EMPTY message");
				       tray.setMessage("empty message is not allowed in this action"
				       		+ "");
				       tray.setAnimationType(AnimationType.POPUP);
				       tray.showAndDismiss(Duration.millis(5000));
		           }else if(outres.equals("-1")){
		        	   TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("failed to establish connection");
				       tray.setMessage("make sure you are connected to the internet"
				       		+ "");
				       tray.setAnimationType(AnimationType.POPUP);
				       tray.showAndDismiss(Duration.millis(5000));
		           }
		           else if(outres.equals("-2")){
		        	   TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("wrong usernam/password for sender Email");
				       tray.setMessage("make sure to set correct EMAIL and PASSWORD"
				       		+ "");
				       tray.setAnimationType(AnimationType.POPUP);
				       tray.showAndDismiss(Duration.millis(5000));
		           }
		           else if(outres.equals("-3")){
		        	   TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("failed to establish connection");
				       tray.setMessage("make sure you are connected to the internet and using \n correct password/username"
				       		+ "");
				       tray.setAnimationType(AnimationType.POPUP);
				       tray.showAndDismiss(Duration.millis(5000));
		           }else if(outres.equals("-4")){
		        	   TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("failed to refresh connection");
				       tray.setMessage("make sure you are connected to the internet and using \n correct password/username"
				       		+ "");
				       tray.setAnimationType(AnimationType.POPUP);
				       tray.showAndDismiss(Duration.millis(5000));
		           }
		           wd=null;
		 	   });
				 wd.exec("fetch", inputParam -> {
						String head,message;
						parMail2 = num.getText().toLowerCase();
						
						if(subj.getText().length() !=0){
						head = subj.getText();
						}else{
							head = "";
						}
						
				
						mai.Init();
						mai.InSession(xculMail, xculpass);
						if(mess.getText().length() !=0){
							message = mess.getText();
							
							try {
								mai.sendMail(xculMail, parMail2, head, message);
							
							if(mai.done()){
								s = 1;
							}
							} catch (MailConnectException e) {
								e.printStackTrace();
								s=-1;
							} catch (AuthenticationFailedException e) {
								e.printStackTrace();
								s=-2;
							} catch (Exception e) {
								e.printStackTrace();
								s=-3;
							}
						}else{
							s = 2;
						}
						
			               try {
			                  Thread.sleep(1000);
			               }	
			               catch (InterruptedException er) {
			                  er.printStackTrace();
			               }
			           
			           return new Integer(s);
			           
			           
			        });
			
		}else{
			//
			 TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.WARNING);
		       tray.setTitle("parent EMAIL is INVALID");
		       tray.setMessage("please make sure to use valid email address"
		       		+ "");
		       tray.setAnimationType(AnimationType.POPUP);
		       tray.showAndDismiss(Duration.millis(5000));
		}
    }

    @FXML
    void sendReport(ActionEvent event) {

    }

    @FXML
    void submit(ActionEvent event) {

    }


}
