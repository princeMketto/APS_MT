package aps.view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import aps.Main;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


public class LoginController implements Initializable {
	LoggerM log=new LoggerM();
	@FXML
    private StackPane stackPop;
	String uname;
	static String myName;
	private Main main;
	int s=0,f=0,e=0;
	boolean su,al,dab=false;
	private WorkIndicatorDialog wd=null;
	ConnectDB database = new ConnectDB();
	 private Connection con;
	    private ResultSet rs;
	    private Statement st;
	    private PreparedStatement prep;
	 @FXML
	    private JFXButton logbtn;
	  @FXML
	    private JFXTextField usname;

	    @FXML
	    private JFXPasswordField password;
	    String head,content;
	    boolean goconf = false;
	    @FXML
	    private Label errorlab;
	 static String user,userid,stats;
	   @FXML
	    private JFXButton serv;

	    @FXML
	    private JFXButton forgot,btnReq;
	    @FXML
	    void onForget(ActionEvent event) {



	    	AnchorPane infopane = null;
	    	try{
	    		FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Forgot.fxml"));
				 infopane = loader.load();
	    	}catch(Exception e){ 
	    		e.printStackTrace();
	    	}

			/*
			 * 
			 */	JFXDialogLayout content = new JFXDialogLayout();
		    	content.setHeading(new Text("Password recovery"));
		    	content.setBody(infopane);
		    	content.setStyle("-fx-background-color: #84C7D2");
		    	
		    	JFXDialog dialog = new JFXDialog(stackPop,content,JFXDialog.DialogTransition.TOP);
		    	JFXButton bt = new JFXButton("Done");
		    	JFXButton bt1 = new JFXButton("Cancel");
		    	dialog.setMaxHeight(220);
		    	dialog.setMaxWidth(260);
		    	dialog.setPrefWidth(220);
		    	dialog.setPrefHeight(260);
		    	
		    	bt.setOnAction(new EventHandler<ActionEvent>(){
		    		String prodName=null;
					@Override
					public void handle(ActionEvent arg0) {
					/*
					 * 
					 */

						/*
						 * 
						 */
			    
			    	dialog.close();
			    	
			    	
					}
		    		
		    	});
		    	bt1.setOnAction(new EventHandler<ActionEvent>(){

					@Override
					public void handle(ActionEvent arg0) {
						dialog.close();
					}
		    		
		    	});
		    	content.setActions(bt,bt1);
		    	dialog.show();
		    
			/*
			 * 
			 */
		
	    	
	    
	    
	    
	    
	    
	    }
	    @FXML
	    void goReq(ActionEvent event) {

	    	AnchorPane infopane = null;
	    	try{
	    		FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Request.fxml"));
				 infopane = loader.load();
	    	}catch(Exception e){ 
	    		e.printStackTrace();
	    	}

			/*
			 * 
			 */	JFXDialogLayout content = new JFXDialogLayout();
		    	content.setHeading(new Text("Package registration"));
		    	content.setBody(infopane);
		    	content.setStyle("-fx-background-color: #84C7D2");
		    	
		    	JFXDialog dialog = new JFXDialog(stackPop,content,JFXDialog.DialogTransition.TOP);
		    	JFXButton bt = new JFXButton("Done");
		    	JFXButton bt1 = new JFXButton("Cancel");
		    	dialog.setMaxHeight(220);
		    	dialog.setMaxWidth(260);
		    	dialog.setPrefWidth(220);
		    	dialog.setPrefHeight(260);
		    	
		    	bt.setOnAction(new EventHandler<ActionEvent>(){
		    		String prodName=null;
					@Override
					public void handle(ActionEvent arg0) {
					/*
					 * 
					 */

						/*
						 * 
						 */
			    
			    	dialog.close();
			    	
			    	
					}
		    		
		    	});
		    	bt1.setOnAction(new EventHandler<ActionEvent>(){

					@Override
					public void handle(ActionEvent arg0) {
						dialog.close();
					}
		    		
		    	});
		    	content.setActions(bt,bt1);
		    	dialog.show();
		    
			/*
			 * 
			 */
		
	    }

	    @FXML
	    void config(ActionEvent event) {


	    	AnchorPane infopane = null;
	    	try{
	    		FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Server.fxml"));
				 infopane = loader.load();
	    	}catch(Exception e){ 
	    		e.printStackTrace();
	    	}

			/*
			 * 
			 */	JFXDialogLayout content = new JFXDialogLayout();
		    	content.setHeading(new Text("server configuration"));
		    	content.setBody(infopane);
		    	content.setStyle("-fx-background-color: #84C7D2");
		    	
		    	JFXDialog dialog = new JFXDialog(stackPop,content,JFXDialog.DialogTransition.TOP);
		    	JFXButton bt = new JFXButton("Done");
		    	JFXButton bt1 = new JFXButton("Cancel");
		    	dialog.setMaxHeight(220);
		    	dialog.setMaxWidth(260);
		    	dialog.setPrefWidth(220);
		    	dialog.setPrefHeight(260);
		    	
		    	bt.setOnAction(new EventHandler<ActionEvent>(){
		    		String prodName=null;
					@Override
					public void handle(ActionEvent arg0) {
					/*
					 * 
					 */

						/*
						 * 
						 */
			    
			    	dialog.close();
			    	
			    	
					}
		    		
		    	});
		    	bt1.setOnAction(new EventHandler<ActionEvent>(){

					@Override
					public void handle(ActionEvent arg0) {
						dialog.close();
					}
		    		
		    	});
		    	content.setActions(bt,bt1);
		    	dialog.show();
		    
			/*
			 * 
			 */
		
	    	
	    
	    
	    
	    
	    }
	    
	    void Conf(){



	    	AnchorPane infopane = null;
	    	try{
	    		FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Server.fxml"));
				 infopane = loader.load();
	    	}catch(Exception e){ 
	    		e.printStackTrace();
	    	}

			/*
			 * 
			 */	JFXDialogLayout content = new JFXDialogLayout();
		    	content.setHeading(new Text("server configuration"));
		    	content.setBody(infopane);
		    	content.setStyle("-fx-background-color: #84C7D2");
		    	
		    	JFXDialog dialog = new JFXDialog(stackPop,content,JFXDialog.DialogTransition.TOP);
		    	JFXButton bt = new JFXButton("Done");
		    	JFXButton bt1 = new JFXButton("Cancel");
		    	dialog.setMaxHeight(220);
		    	dialog.setMaxWidth(260);
		    	dialog.setPrefWidth(220);
		    	dialog.setPrefHeight(260);
		    	
		    	bt.setOnAction(new EventHandler<ActionEvent>(){
		    		String prodName=null;
					@Override
					public void handle(ActionEvent arg0) {
					/*
					 * 
					 */

						/*
						 * 
						 */
			    
			    	dialog.close();
			    	
			    	
					}
		    		
		    	});
		    	bt1.setOnAction(new EventHandler<ActionEvent>(){

					@Override
					public void handle(ActionEvent arg0) {
						dialog.close();
					}
		    		
		    	});
		    	content.setActions(bt,bt1);
		    	dialog.show();
		    
			/*
			 * 
			 */
		
	    	
	    
	    
	    
	    
	    
	    }
    @SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
	@FXML
    void gologin(ActionEvent event) throws IOException {
    	

    		
    	 RequiredFieldValidator validator = new RequiredFieldValidator();
    	    validator.setMessage("Input Required");
    	    
    	    usname.getValidators().add(validator);
    	    usname.focusedProperty().addListener(( o,  oldVal,  newVal) -> {
    	        if (!newVal)
    	        	usname.validate();
    	    });
		 
		    
   if(!(usname.getText().equals("") || password.getText().equals("") || usname.getText().isEmpty()
		   || password.getText().isEmpty())){
	   wd = new WorkIndicatorDialog(logbtn.getScene().getWindow(), "Authenticating...");
	   wd.addTaskEndNotification(result -> {
          
          String outres = result.toString();
         // System.out.println("nomaa "+outres);
          if(outres.equals("1")){
        	  if(stats.equals("Admin")){
        		  try {
      				Main.showMainDash();
      			} catch (Exception e) {
      				// TODO Auto-generated catch block
      				e.printStackTrace();
      			}
        		  
        	  }
        	/*  if(stats.equals("cashier")){
        		  try {
      				main.showCashierDash();
      			} catch (Exception e) {
      				// TODO Auto-generated catch block
      				e.printStackTrace();
      			}
        	  }
        	  if(stats.equals("storekeeper")){
        		  try {
      				main.showStoreDash();
      			} catch (Exception e) {
      				// TODO Auto-generated catch block
      				e.printStackTrace();
      			}
        	  }*/
        	
	    		 
			   TrayNotification tray = new TrayNotification();
			//   Notification notification = NotificationType.SUCCESS;
		       tray.setNotificationType(NotificationType.SUCCESS);
		       tray.setTitle("Login success");
		       tray.setMessage("Thank you signing in.You may now proceed to your respective duty");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(1500));
		       log.writter("Login successfully");
		       
		       
		     
		       
        	  
          }else if(outres.equals("2")){
        	  TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.NOTICE);
			       tray.setTitle("server details are not set");
			       tray.setMessage("fill server url in the popup.");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       Conf();
          }else{
        	
        	  TrayNotification tray = new TrayNotification();
				//   Notification notification = NotificationType.SUCCESS;
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Wrong Username or Password");
			       tray.setMessage("Dear User. Make sure to provide correct username and password.");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(2500));
			      
          }
           wd=null; // don't keep the object, cleanup
        });
	  	 wd.exec(usname.getText(), inputParam -> {
	           // Thinks to do...
	           // NO ACCESS TO UI ELEMENTS!
	  		 try{
	    			con= database.dbconnect();
	    		   st= con.createStatement();
	    		   String query = "SELECT * FROM staffs WHERE staff_id='"+usname.getText()+"' AND password='"+password.getText()+"'";
	    		   rs=st.executeQuery(query);
	    		   	if(rs.next()){
	    		   		String lname = rs.getString("lastName");
	    		   		String fnme=rs.getString("firstName");
	    		        uname=fnme+" "+lname;
	    		   		user= " "+ rs.getString("firstName")+" "+lname.charAt(0);
	    		   		userid = rs.getString("staff_id");
	    		   		stats  = rs.getString("status");
	    		   		
	    		   		myName = uname;
                        
	    		   		su=true;
	    		  
	    		   	}else{
	    		   		su=false;
	    	
	    		   	}
	    		   	dab = false;
	    		   }catch(IllegalStateException il){
	    			   dab = true;
	    		   }
	  		 catch(SQLException err){
	    			   err.printStackTrace();
	    			   
	    		   }
	       
	               try {
	                  Thread.sleep(1000);
	               }	
	               catch (InterruptedException er) {
	                  er.printStackTrace();
	               }
	              if(su){
	            	  s = 1;
	              }else{
	            	  s = 0;
	              }
	              if(dab){
	            	  s = 2;
	              }
	           return new Integer(s);
	           
	           
	        });
	  
	   //*************
   
   }else{
	
	   TrayNotification tray = new TrayNotification();
		//   Notification notification = NotificationType.SUCCESS;
	       tray.setNotificationType(NotificationType.WARNING);
	       tray.setTitle("Empty field submitted");
	       tray.setMessage("Dear User, make sure to provide correct username and password");
	       tray.setAnimationType(AnimationType.SLIDE);
	       tray.showAndDismiss(Duration.millis(2500));
   }

    }
   
	static String getuser(){
    	return user;
    }
	static String getMyName(){
		return myName;
	}
static String getUserID(){
	return userid;
}
static String getStats(){
	return stats;
}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Image btnlog = new Image(getClass().getResourceAsStream("images/login.png"));
		logbtn.setGraphic(new ImageView(btnlog));
		
		Image sev = new Image(getClass().getResourceAsStream("images/tinyset.png"));
		serv.setGraphic(new ImageView(sev));
		
		Image forg = new Image(getClass().getResourceAsStream("images/forgot.png"));
		forgot.setGraphic(new ImageView(forg));
	}

	
}
