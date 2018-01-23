package aps.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import aps.view.ConnectDB;
import aps.view.WorkIndicatorDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class LoadStudent implements Initializable {

    @FXML
    private StackPane pop;
    @FXML
    private JFXTextField id;

    @FXML
    private JFXButton btnsearch;

    @FXML
    private JFXButton btntop1;

    @FXML
    private JFXButton btntop2;

    @FXML
    private JFXButton btntop3;

    @FXML
    private ImageView photo;

    @FXML
    private Text showID;

    @FXML
    private Text showName;

    @FXML
    private Text showGend;

    @FXML
    private Text showDob;

    @FXML
    private Text showClass;

    @FXML
    private Text parName;

    @FXML
    private Text parGend,status;

    @FXML
    private Text parPhone;

    @FXML
    private Text parMail;
    private WorkIndicatorDialog wd=null;
    ObservableList<String>list = FXCollections.observableArrayList();
    ObservableList<String>filteredlist = FXCollections.observableArrayList();
    ConnectDB database = new ConnectDB();
    private Connection con;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement prep;
  static  String stID = null;
  FileInputStream input;
	InputStream is = null;
	Image image=null;
	FileChooser flc;
  File selectedFile ;

  boolean em,su = false;
 static String datas;
  String idTo,sID=null,fname,mname,lname,statu,sclac,gend,admit,dob,parnam,parsex,paphone;
static String pamail;

  int s = 0;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image sear = new Image(getClass().getResourceAsStream("images/search.png"));
		btnsearch.setGraphic(new ImageView(sear));
		
		
	}
    @FXML
    void sendMail(ActionEvent event) {

		  try{
			  if(datas.length()==0){
				  TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("enter student regno first and click search");
			       tray.setMessage("you cant use this action with empty data");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(3000));
			  }else{
		    	AnchorPane infopane = null;
		    	try{
		    		FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("ParentEmail.fxml"));
					 infopane = loader.load();
		    	}catch(Exception e){ 
		    		e.printStackTrace();
		    	}

				/*
				 * 
				 */	JFXDialogLayout content = new JFXDialogLayout();
			    	content.setHeading(new Text("Email box"));
			    	content.setBody(infopane);
			    	content.setStyle("-fx-background-color: #84C7D2");
			    	
			    	JFXDialog dialog = new JFXDialog(pop,content,JFXDialog.DialogTransition.TOP);
			    	JFXButton bt = new JFXButton("Done");
			    	JFXButton bt1 = new JFXButton("Cancel");
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
		  }catch(Exception e){
			  e.printStackTrace();
			  TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.WARNING);
		       tray.setTitle("enter student regno first and click search");
		       tray.setMessage("you cant use this action with empty data");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(3000));
		  }
		
	    	
	    
	    
    }
	  @FXML
	    void sendMess(ActionEvent event) {
		  try{
			  if(datas.length()==0){
				  TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("enter student regno first and click search");
			       tray.setMessage("you cant use this action with empty data");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(3000));
			  }else{
		    	AnchorPane infopane = null;
		    	try{
		    		FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("ParentSms.fxml"));
					 infopane = loader.load();
		    	}catch(Exception e){ 
		    		e.printStackTrace();
		    	}

				/*
				 * 
				 */	JFXDialogLayout content = new JFXDialogLayout();
			    	content.setHeading(new Text("Message box"));
			    	content.setBody(infopane);
			    	content.setStyle("-fx-background-color: #84C7D2");
			    	
			    	JFXDialog dialog = new JFXDialog(pop,content,JFXDialog.DialogTransition.TOP);
			    	JFXButton bt = new JFXButton("Done");
			    	JFXButton bt1 = new JFXButton("Cancel");
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
		  }catch(Exception e){
			  e.printStackTrace();
			  TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.WARNING);
		       tray.setTitle("enter student regno first and click search");
		       tray.setMessage("you cant use this action with empty data");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(3000));
		  }
		
	    	
	    
	    }
	  public static String getParData(){
		  return datas;
	  }
	  public static String getParMail(){
		  return pamail;
	  }
    @SuppressWarnings("unchecked")
	@FXML
    void goSeacrh(ActionEvent event) {
    	
    	try{
    		idTo = id.getText().toUpperCase();
    	}catch(Exception e){
    		 e.printStackTrace();
	    		TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.ERROR);
			       tray.setTitle("Fill Student ID in the field");
			       tray.setMessage("there is nothing to search\n"
			       		+ "");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
    	}
    	      	wd = new WorkIndicatorDialog(null, "Loading...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   showID.setText(sID); showName.setText(fname+" "+mname+" "+lname);
	        	   showGend.setText(gend); showDob.setText(dob);
	        	   showClass.setText(sclac); 
	        	   parName.setText(parnam);  parGend.setText(parsex);
	        	   parPhone.setText(paphone);
	        	   parMail.setText(pamail);
	        	   status.setText(statu);
	        	   datas = parnam+"-"+paphone;
	        	   
	           }else{
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.ERROR);
			       tray.setTitle("No student with such ID");
			       tray.setMessage("Either u typed wrong ID or it doesn't exist."
			       		+ "");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
	           }
	           wd=null;
	 	   });
			 wd.exec("fetch", inputParam -> {
		           // Thinks to do...
		           // NO ACCESS TO UI ELEMENTS!
	 			try{
	 				con= database.dbconnect();
	 				   st= con.createStatement();
	 				   String query = "SELECT * FROM students WHERE student_id='"+idTo+"' ";
	 				   rs=st.executeQuery(query);
	 				   if(rs.next()){
	 					 s = 1;
	 					 Date adm;
	 					 String []parts,parts2;
	 					 LocalDate ld=null;
	 					   sID = rs.getString("student_id");
	 					   fname = rs.getString("firstName");
	 					   mname = rs.getString("middleName");
	 					   lname =  rs.getString("lastName");
	 					   sclac = rs.getString("studentClass");
	 					  Blob aBlob = rs.getBlob("photo");
	 					   is =  aBlob.getBinaryStream(1, aBlob.length());
	 					  image= new Image(is);
	 					  photo.setImage(image);
	 					 statu = rs.getString("status");
	 					  gend = rs.getString("Gender");
	 					 dob = rs.getString("dob");
	 					 admit = rs.getString("admissionYear");
	 					
	 				//	 System.out.println("EMBU:"+parts[0]);
	 					 adm =  rs.getDate("admissionYear");
	 					  parnam = rs.getString("sponsorName");
	 					  parsex = rs.getString("sponsorSex");
	 					  paphone = rs.getString("sponsorPhone");
	 					  pamail = rs.getString("sponsorMail");
	 				
	 					
	 				   }else{
	 					  s = 0; 
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
}
