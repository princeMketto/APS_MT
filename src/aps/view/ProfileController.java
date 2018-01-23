package aps.view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import aps.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ProfileController implements Initializable {
	Main main;

    @FXML
    private JFXButton save;

    @FXML
    private Label fullnamelb;

    @FXML
    private JFXTextField lbfname;

    @FXML
    private JFXTextField lblname;

    @FXML
    private JFXTextField lbmail;

    @FXML
    private JFXTextField lbphone;

    @FXML
    private JFXButton whatbtn;

    @FXML
    private JFXTextField lbusername,recover;

    @FXML
    private JFXButton btnphoto;

    @FXML
    private JFXPasswordField lboldpass;

    @FXML
    private JFXPasswordField lbnewpass;

    @FXML
    private JFXPasswordField lbconfirm;
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXButton btnPass;
    ConnectDB database = new ConnectDB();
    private Connection con;
       private ResultSet rs;
       private Statement st;
       private PreparedStatement prep,prep1;
       String fnam = null,lnam = null,unam = null,phon = null,mail = null;
       private WorkIndicatorDialog wd=null;
       @FXML
       private ImageView myphoto;
    LoginController lg = new LoginController();
    String id;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image toimg = new Image(getClass().getResourceAsStream("images/edite.png"));
		save.setGraphic(new ImageView(toimg));
		//reFill();
		id = lg.getUserID();
	}
    @SuppressWarnings("unchecked")
	private void reFill() {
    	wd = new WorkIndicatorDialog(null, "Loading profile data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
		           // Thinks to do...
		           // NO ACCESS TO UI ELEMENTS!
	 			
	 		  	try{
	 				
	 				con= database.dbconnect();
	 				   st= con.createStatement();
	 				   String query = "SELECT * FROM staffs WHERE staff_id ='"+id+"'";
	 				   rs=st.executeQuery(query);
	 				  
	 				   if(rs.next()){
	 					  
	 					  
	 					   fnam = rs.getString("firstName");
	 					   lnam = rs.getString("middleName");
	 					   unam = rs.getString("lastName");
	 					   phon = rs.getString("phone");
	 					   mail = rs.getString("mail");
	 					   unam = rs.getString("staff_id");
	 					  fullnamelb.setText(fnam+" "+lnam);
	 				   }
	 				  fullnamelb.setText(fnam+" "+lnam);
	 				   lbfname.setText(fnam);
	 				   lblname.setText(lnam);
	 				   lbmail.setText(mail);
	 				   lbphone.setText(phon);
	 				   lbusername.setText(unam);
	 				   rs.close();
	 				   st.close();
	 				   con.close();
	 				   
	 			}catch(SQLException err){
	 				err.printStackTrace();
	 			}
		       
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		             
		           return new Integer(1);
		           
		           
		        });
  		
		
	}

    @FXML
    void fill(ActionEvent event) {
    	reFill();
    }
	@FXML
    void detailsInfo(ActionEvent event) {
    	try{
			 String fnam = null,lnam = null,unam = null,phon = null,mail = null;
			 try{
			 fnam = lbfname.getText();
			 lnam =   lblname.getText();
			 mail =   lbmail.getText();
			phon =   lbphone.getText();
			 unam =   lbusername.getText();
			 }catch(Exception er){
				 TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.SUCCESS);
			       tray.setTitle("USE VALID WORDS ONLY");
			       tray.setMessage("you cant use numbers or sysmbols");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			 }
			 con= database.dbconnect();
			  prep = (PreparedStatement) con.prepareStatement("UPDATE staffs SET firstName = ?,lastName = ?,mail = ?,phone = ?"
			  		+ " WHERE staff_id =?");

				  
		    	  prep.setString(1, fnam);
		    	  prep.setString(2, lnam);
		    	  prep.setString(3, mail);
		    	  prep.setString(4, phon);
		    	  prep.setString(5, lg.getUserID());
		    	  int out=prep.executeUpdate();
					
					if(out >0){
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.SUCCESS);
					       tray.setTitle("YOUR DETAILS UPDATED");
					       tray.setMessage("you can now be identified with new information");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					}else{
						System.out.println("error");
					}
			
			   prep.close();
			 //  st.close();
			   con.close();
			   
		}catch(SQLException err){
			err.printStackTrace();
		}	
    	reFill();
    }

    @SuppressWarnings("static-access")
	@FXML
    void goBack(ActionEvent event) throws IOException {
    	if(lg.getStats().equals("Admin")){
    		main.showMainDash();
    	}
    	/*if(lg.getStats().equals("cashier")){
    		main.showCashierDash();
    	}
    	if(lg.getStats().equals("storekeeper")){
    		main.showStoreDash();
    	}*/
    }

    @FXML
    void updatePassword(ActionEvent event) {
    	try{
   		 String oldpass = null,newpass = null,confirmpass = null,rec = null;
   		 try{
   			 oldpass =   lboldpass.getText();
   			 newpass = lbnewpass.getText();
   			 confirmpass = lbconfirm.getText();
   			 rec = recover.getText();
   		 }catch(Exception eer){
   			 TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.SUCCESS);
			       tray.setTitle("USE VALID PASSWORD");
			       tray.setMessage("you cant use numbers or sysmbols");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
   		 }
   		con= database.dbconnect();
			if(newpass.equals(confirmpass)){ 
   			prep = (PreparedStatement) con.prepareStatement("UPDATE staffs SET password = ?,recover = ?"
			  		+ " WHERE staff_id =? AND password = ?");
			  prep.setString(1, newpass);
			  prep.setString(2, rec);
	    	  prep.setString(3, lg.getUserID());
	    	  prep.setString(4, oldpass);
	    	  int out=prep.executeUpdate();
				
	    	  if(out >0){
					TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.INFORMATION);
				       tray.setTitle("PLEASE, SIGN IN USING NEW PASSWORD");
				       tray.setMessage(" This is for security reason");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
				       try {
						main.showLogOut();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("WRONG PASSWORD ENTERED");
				       tray.setMessage("you provide uknown old password");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
				}
			}else{
				TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("NEW PASSWORD DIDNT MATCH");
			       tray.setMessage("match password btn new password and confrim password");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			}
	    	  prep.close();
			   st.close();
			   con.close();
	    	  
   	}catch(SQLException err){
			err.printStackTrace();
		}	
    }

    @FXML
    void usernameInfo(ActionEvent event) {
    	try{
    		 String unam = null;
    		 try{
    			 unam =   lbusername.getText();
    		 }catch(Exception eer){
    			 TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.SUCCESS);
			       tray.setTitle("USE VALID WORDS ONLY");
			       tray.setMessage("you cant use numbers or sysmbols");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
    		 }
    		con= database.dbconnect();
			  prep = (PreparedStatement) con.prepareStatement("UPDATE user SET username = ?"
			  		+ " WHERE Id =?");
			  prep.setString(1, unam);
	    	  prep.setString(2, lg.getUserID());
	    	  int out=prep.executeUpdate();
				
	    	  if(out >0){
					TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.SUCCESS);
				       tray.setTitle("YOUR USERNAME UPDATED");
				       tray.setMessage("you can now be identified with new information");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
				}else{
					System.out.println("error");
				}
	    	  prep.close();
			   st.close();
			   con.close();
	    	  
    	}catch(SQLException err){
			err.printStackTrace();
		}
    	reFill();
    }


    @FXML
    void goHint(ActionEvent event) {
    	JFXSnackbar bar = new JFXSnackbar(anchor);
     	bar.show("For updating your profille data, click EDIT button, \n"
     			+ " fill or edit given data and then \n press its respective button to save changes",8000);
    }
    @FXML
    void attachPhoto(ActionEvent event) {

    }
}
