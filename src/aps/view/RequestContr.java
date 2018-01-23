package aps.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.AuthenticationFailedException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.mail.util.MailConnectException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class RequestContr implements Initializable {

  

    @FXML
    private JFXButton btnsave;

    @FXML
    private Label lbwelc,fullnamelb;

   
    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField ip;

    @FXML
    private Label error;

    @FXML
    private JFXTextField email;

    @FXML
    private Label lbNote;

    @FXML
    private Label fullnamelb112;

    @FXML
    private Label fullnamelb1111;
    @FXML
    private Label fullnamelb12;
    @FXML
    private Label fullnamelb1;

    @FXML
    private Label fullnamelb11;

    @FXML
    private Label fullnamelb111;
    private WorkIndicatorDialog wd=null;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
    @SuppressWarnings("unchecked")
	@FXML
    void goSave(ActionEvent event) {
    	if(name.getText().length() !=0 && ip.getText().length() !=0 && email.getText().length() !=0){
    		String myip;
    		String nam = name.getText();
    		String mail;
    		   Pattern pattern0 = Pattern.compile("[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9]+"); // [A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}
	   			Matcher m0 = pattern0.matcher(ip.getText() );
	   			if(m0.find() && m0.group().equals(ip.getText())){
	   				//return true;
	   				myip = ip.getText();
	   				
	   			 Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+"); // [A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}
		   			Matcher m = pattern.matcher(email.getText());
		   			if(m.find() && m.group().equals(email.getText())){
		   				//return true;
		   				mail = email.getText();

		   				wd = new WorkIndicatorDialog(null, "Registering...");
		 		 	   wd.addTaskEndNotification(result -> {
		 		 		  String outres = result.toString();
		 		          // System.out.println("nomaa "+outres);
		 		           if(outres.equals("1")){
		 		        	   lbwelc.setText("Thank you for Joining us.");
		 		        	   lbNote.setText("In a short time, we will send you \n"
		 		        	   				+ "an Email with your unique Details \n"
		 		        	   				+ "Use those details to configure your\n"
		 		        	   				+ "Application as per Instructions given.");
		 		        	   name.setText(null);
		 		        	   ip.setText(null);
		 		        	   email.setText(null);
		 		           }else if(outres.equals("-1")){
		 		        	  System.out.println("ERROR: -1");
		 		           }else if(outres.equals("-2")){
			 		        	  System.out.println("ERROR: -2");
			 		           }else if(outres.equals("-3")){
				 		        	  System.out.println("ERROR: -3");
			 		           }
		 		           wd=null;
		 		 	   });
		 				 wd.exec("fetch", inputParam -> {
		 					 String Jmail = "jmketto3@gmail.com";
		 					 int s = 0;
		 						String head,message;
		 						head = "NEW USER";
		 						MailingService mai = new MailingService();
		 						
		 				
		 						mai.Init();
		 						mai.InSession(Jmail, "Juma kassim");
		 							//System.out.println("IP:"+myip+" MAIL:"+mail);
		 							message = "My public IP is:"+myip+", My EMAIL:"+mail+"\n My SCHOOL:"+nam;
		 							
		 							try {
		 								mai.sendMail(Jmail, "jmketto@gmail.com", head, message);
		 							
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
		   				mail = "";
		   			 TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle(" EMAIL is INVALID");
				       tray.setMessage("please make sure to use valid email address"
				       		+ "");
				       tray.setAnimationType(AnimationType.POPUP);
				       tray.showAndDismiss(Duration.millis(4000));
		   			}
	   				
	   			}else{
	   				//
	   				myip = "";
	   			 TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("public IP ADDRESS is INVALID");
			       tray.setMessage("please make sure to use valid IP address, search for 'MY IP ADDRESS'"
			       		+ "");
			       tray.setAnimationType(AnimationType.POPUP);
			       tray.showAndDismiss(Duration.millis(4000));
	   			}
	   		
	   			
    	}else{
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.WARNING);
		       tray.setTitle("all fields are important in this form");
		       tray.setMessage("fill all fields before registering "
		       		+ "");
		       tray.setAnimationType(AnimationType.POPUP);
		       tray.showAndDismiss(Duration.millis(5000));
    	}
    }
}
