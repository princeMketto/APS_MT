package aps.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import aps.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


public class SettingController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private Label nameo;

    @FXML
    private JFXButton btnsave;
    @FXML
    private ImageView logo;

    @FXML
    private JFXButton attach;

    @FXML
    private JFXButton savebtn;

    @FXML
    private JFXTextField xculnum;

    @FXML
    private JFXTextField xculnam;

    @FXML
    private JFXTextField sculaddres;
    @FXML
    private JFXTextField sculmail;


    @FXML
    private JFXTextField xculcont1;

    @FXML
    private JFXTextField xculcont2;

    @FXML
    private JFXTextField xcullocation;
    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton helpbtn;
    private WorkIndicatorDialog wd=null;
    LoggerM log=new LoggerM();
	 ConnectDB database = new ConnectDB();
	    private Connection con;
	    private ResultSet rs;
	    private Statement st,st1,st2;
	    private PreparedStatement prep;
	    FileInputStream input;
		InputStream is = null;
		Image image=null;
		FileChooser flc;
	  File selectedFile ;
	    String scnum = null,scnam = null,scadr = null,sccon1 = null,sccon2 = null,scloc = null;
	    String snum = null,snam = null,smail=null,spass=null,smailV=null,sadr = null,scon1 = null,scon2 = null,sloc = null;
	    int s = 0;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image hel = new Image(getClass().getResourceAsStream("images/helpS.png"));
		helpbtn.setGraphic(new ImageView(hel));
		fetchData();
		
	}

    @SuppressWarnings("unchecked")
	private void fetchData() {
    	
    	wd = new WorkIndicatorDialog(null, "Loading school data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   xculnum.setText(scnum);
	 				 xculnam.setText(scnam);
	 				sculaddres.setText(scadr);
	 				sculmail.setText(smail);
	 				if(smail.length() !=0){
	 					password.setOpacity(1);
	 					password.setText(spass);
	 					
	 				}
	 				xculcont1.setText(sccon1);
	 				xculcont2.setText(sccon2.toLowerCase());
	 				xcullocation.setText(scloc);
	 				nameo.setText(scnam);
	 				
	 				 
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
	 				  
	 				   if(rs.next()){
	 					  
	 					  scnum = rs.getString("schoolNum");
	 					 scnam = rs.getString("title");
	 					scadr = rs.getString("address");
	 					smail = rs.getString("mail");
	 					spass = rs.getString("password");
	 					sccon1 = rs.getString("contact");
	 					sccon2 = rs.getString("contact2");
	 					scloc = rs.getString("location");
	 					 Blob aBlob = rs.getBlob("logo");
	 					   is =  aBlob.getBinaryStream(1, aBlob.length());
	 					  image= new Image(is);
							logo.setImage(image);
	 				   }
	 				 
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
    void showPass(KeyEvent event) {
    	if(sculmail.getText().length() !=0){
    		password.setOpacity(1);
    	}else{
    		password.setOpacity(0);
    	}
    		
    }
   

	@SuppressWarnings("unchecked")
	@FXML
    void fill(ActionEvent event) {
		
		try{
			snum = xculnum.getText().toUpperCase();
			snam = xculnam.getText().toUpperCase();
			sadr = sculaddres.getText().toUpperCase();
			
			Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+"); // [A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}
			Matcher m = pattern.matcher( sculmail.getText());
			if(m.find() && m.group().equals( sculmail.getText())){
				//return true;
				smail = sculmail.getText();
				spass = password.getText();
			}else{
				//
				smail = "";
				 JFXSnackbar bar = new JFXSnackbar(anchor);
		        	bar.show("Please use VALID EMAIL \n if you leave it like this you wont be able \n"
		        			+ "to use EMAIL services, unless EDITED. ",7000);
			}
			
			scon1 = xculcont1.getText().toUpperCase();
			scon2 = xculcont2.getText().toUpperCase();
			scloc = xcullocation.getText().toUpperCase();
		
			if(!(snum.length() == 0 && snam.length() == 0 && sadr.length()==0 &&
					scon1.length()==0 && scon2.length()==0 && scloc.length()==0)){
				
					
			wd = new WorkIndicatorDialog(null, "Inserting ...");
		 	   wd.addTaskEndNotification(result -> {
		 		  String outres = result.toString();
		          // System.out.println("nomaa "+outres);
		           if(outres.equals("1")){
		        	 /*  xculnum.setText(scnum);
		 				 xculnam.setText(scnam);
		 				sculaddres.setText(scadr);
		 				xculcont1.setText(sccon1);
		 				xculcont2.setText(sccon2);
		 				xcullocation.setText(scloc);*/
		        	   fetchData();
		 				JFXSnackbar bar = new JFXSnackbar(anchor);
		 	        	bar.show("School details set successfully",3000);
		 	        	reboot();
		           }else if(outres.equals("0")){
		        	  
		 				JFXSnackbar bar = new JFXSnackbar(anchor);
		 	        	bar.show("Details is not saved. retry",3000);
		           }
		           wd=null;
		 	   });
		 		 wd.exec("fetch", inputParam -> {
		 			 boolean contain = false;
			           // Thinks to do...
			           // NO ACCESS TO UI ELEMENTS!
		 			 try{
		 				con= database.dbconnect();
		 				   st= con.createStatement();
		 			 String select = "SELECT * FROM school_details";
					    rs=st.executeQuery(select);
					    
					    if(rs.next()){
					    String xcname = rs.getString("title");
					    String xcnum = rs.getString("schoolNum");
					    if(xcname.equals(snam) && xcnum.equals(snam)){
					    	contain = true;
					    }
					    }
					    st.close();
					    con.close();
		 			 }catch(SQLException e){
		 				 e.printStackTrace();
		 				 s = 2;
		 			 }
		 			 if(contain){
		 				 try{
		 					con= database.dbconnect();
			 				  
		 					 prep = (PreparedStatement) con.prepareStatement("UPDATE school_details SET "
		 						 		+ "title = ?,address = ?,mail = ?,password = ?,contact=?,contact2=?,location=? WHERE schoolNum = ? ");
				 				// prep.setString(1,snum);
								 prep.setString(1,snam);
								 prep.setString(2,sadr);
								 prep.setString(3,smail);
								 prep.setString(4,spass);
								 prep.setString(5,scon1);
								 prep.setString(6,scon2);
								 prep.setString(7,scloc);
								 prep.setString(8,snum);
								   int out=prep.executeUpdate();
								 if(out >0){
									 s = 1;
								 }else{
									 s = 0;
								 }
								 prep.close();
								 con.close();
		 				 }catch(SQLException sq){
		 					 sq.printStackTrace();
		 					 s=2;
		 				 }
		 			 }else{
		 				 try{
		 					con= database.dbconnect();
			 				   st1= con.createStatement();
			 				  st2= con.createStatement();
			 				// st= con.createStatement();
		 				String sql2 = "DELETE FROM school_details WHERE 1";
				    	st1.executeUpdate(sql2);
				    	String sql = "INSERT INTO school_details (schoolNum,title,address,mail,password,contact,contact2,location) "
				    			+ "VALUES('"+snum+"','"+snam+"','"+sadr+"','"+smail+"','"+spass+"','"+scon1+"','"+scon2+"','"+scloc+"')";
				    	int ou=st2.executeUpdate(sql);
				    	 if(ou>0){
			 				 s=1;
			 				 }else{
			 					 s=2;
			 				 }
				    	st2.close();
				    	st1.close();
				    	con.close();
		 				 }catch(SQLException x){
		 					 s=2;
		 					 x.printStackTrace();
		 				 }
		 				
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
				JFXSnackbar bar = new JFXSnackbar(anchor);
		    	bar.show("Please fill all details. They are important",4000);
			}
		
		
		}catch(Exception e){
			 e.printStackTrace();
        	 JFXSnackbar bar = new JFXSnackbar(anchor);
        	bar.show("Please fill all important field to perform this action",3000);
		}
	
    }

    @SuppressWarnings("unchecked")
	private void reboot() {
		// TODO Auto-generated method stub
      	wd = new WorkIndicatorDialog(null, "Restarting ...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.INFORMATION);
			       tray.setTitle("System restarts to  save your changes");
			       tray.setMessage("RUN the app again if it's not restarting by itself"
			       		+ "");
			       tray.setAnimationType(AnimationType.POPUP);
			       tray.showAndDismiss(Duration.millis(6000));

			     Stage stag =  (Stage) Main.mainItems.getScene().getWindow();
			     stag.close();
			 	wd = new WorkIndicatorDialog(null, "Initializing Service ...");
			 	   wd.addTaskEndNotification(resul -> {
			 		  String outre = result.toString();
			           if(outres.equals("1")){
			        	   Platform.runLater(new Runnable() {
						         public void run() {             
						             try {
										new Main().start(new Stage());
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
						         }
						      });   
			           }
			           wd=null;
			 	   });
			     wd.exec("fetch", inputParam -> {
			    	 
		               try {
		                  Thread.sleep(4000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		           
		           return new Integer(1);
		           
		           
		        });
	           }
	           wd=null;
	 	   });
			 wd.exec("fetch", inputParam -> {
		     
		               try {
		                  Thread.sleep(6000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		           
		           return new Integer(1);
		           
		           
		        });
	}
    @FXML
    void openFile(ActionEvent event) {

    	flc  = new FileChooser();
    	selectedFile = flc.showOpenDialog(null);
    	if(selectedFile != null){
    	
    		try {
    			input = new FileInputStream(selectedFile);
				image= new Image(input);

				logo.setImage(image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
    	}
    
    }

    @SuppressWarnings("unchecked")
	@FXML
    void save(ActionEvent event) {
    	wd = new WorkIndicatorDialog(null, "saving logo...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.SUCCESS);
			       tray.setTitle("School's logo successfully set");
			       tray.setMessage("this logo will appear in different reports");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       log.writter("update school logo");
			       input = null;
		
	           }else if(outres.equals("2")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.ERROR);
			       tray.setTitle("Failure Updating logo");
			       tray.setMessage("Make sure to attach photo file .");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
	           }else if(outres.equals("-1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.ERROR);
			       tray.setTitle("Failure Updating logo");
			       tray.setMessage("Make sure to select valid image");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
	           }
	           wd=null;
	 	   });
	     wd.exec("fetch", inputParam -> {
	    	 int z=0;
	    	 try{
			    	if(selectedFile != null){
			    input = new FileInputStream(selectedFile); 
			    	}else{
			    		//Save to file first then load it when needed
			    			
			    		//load here
			    		selectedFile =new File("logo.png");
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
	    	 try{ 
				 con= database.dbconnect();
				 prep = (PreparedStatement) con.prepareStatement("UPDATE school_details SET logo= ?"
			    	  		+ "");
					prep.setBinaryStream(1, (InputStream)input,(int)selectedFile.length());
					 // prep.setInt(2, 1);
					int out=prep.executeUpdate();
					if(out >0){
						z = 1;
						 	       
						     //  parname.setText(null); parname.setText(null);parmail.setText(null);
						       
					}else{
						z=2;
					}
					prep.close();
					con.close();
			 }catch(SQLException err){
				 	err.printStackTrace();
				 z=-1;
			  	
			 }
         try {
            Thread.sleep(4000);
         }	
         catch (InterruptedException er) {
            er.printStackTrace();
         }
     
     return new Integer(z);
     
     
  });
   	 
    }

	@FXML
    void hint(ActionEvent event) {
    	JFXSnackbar bar = new JFXSnackbar(anchor);
    	bar.show("use the field to enter only school details as promted \n"
    			+ " this detail identifies your school, and some of its \n"
    			+ "information are used for school informations.",5000);
    }
}
