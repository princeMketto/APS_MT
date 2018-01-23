package aps.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import com.jfoenix.controls.JFXSnackbar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class HeadController implements Initializable {
    @FXML
    private AnchorPane anchor;
	  @FXML
	    private ImageView headphoto;

	    @FXML
	    private Label fullnamelb;

	    @FXML
	    private JFXButton btnsave;

	    @FXML
	    private JFXButton btnsign;

	    @FXML
	    private ImageView sign;

	    @FXML
	    private JFXButton btnHint;

	    @FXML
	    private Label stafid;

	    @FXML
	    private Label name;

	    @FXML
	    private Label gender;

	    @FXML
	    private Label birth;

	    @FXML
	    private Label phone;

	    @FXML
	    private Label address;

	    @FXML
	    private Label mail;
	    private Connection con;
	    private ResultSet rs;
	    private Statement st;
	    private PreparedStatement prep;
	  static  String stID = null;
	  FileInputStream input;
		InputStream is,is1 = null;
		Image image,imgsign=null;
		FileChooser flc;
	  File selectedFile ;
	  private WorkIndicatorDialog wd=null;
	  ConnectDB database = new ConnectDB();
	   String id,fname,mname,lname,statu,gend,addr,dob,phon,mails;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		loaddata();
	}
    @SuppressWarnings("unchecked")
	private void loaddata() {
    	wd = new WorkIndicatorDialog(null, "Loading HOS info...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   headphoto.setImage(image);
	        	   sign.setImage(imgsign);
	        	   stafid.setText(id);
	        	   name.setText(fname+" "+mname+" "+lname);
	        	   gender.setText(gend);
	        	   birth.setText(dob);
	        	   phone.setText(phon);
	        	   address.setText(addr);
	        	   mail.setText(mails);
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
	 			 int s = 0;
		           // Thinks to do...
		           // NO ACCESS TO UI ELEMENTS!
	 			try{
	 				con= database.dbconnect();
	 				   st= con.createStatement();
	 				   String query = "SELECT sta.*,hed.sign "
	 						   		+"FROM staffs sta,head_sign hed "
	 						   		+"WHERE sta.status = hed.name";
	 				   rs=st.executeQuery(query);
	 				   if(rs.next()){
	 					
	 					 Date adm;
	 					 String []parts,parts2;
	 					 LocalDate ld=null;
	 					   id = rs.getString("staff_id");
	 					   fname = rs.getString("firstName");
	 					   mname = rs.getString("middleName");
	 					   lname =  rs.getString("lastName");
	 					  statu = rs.getString("status");
	 					  Blob aBlob = rs.getBlob("photo");
	 					   is =  aBlob.getBinaryStream(1, aBlob.length());
	 					  image= new Image(is);
	 					
	 					  Blob aBlob1 = rs.getBlob("sign");
	 					   is1 =  aBlob1.getBinaryStream(1, aBlob1.length());
	 					  imgsign= new Image(is1);
	 						
	 						/*
	 						 * Save this image temp
	 						 */
	 						
	 					  gend = rs.getString("Gender");
	 					 dob = rs.getString("dob");
	 					 addr = rs.getString("address");
	 					 parts = dob.split("-");
	 				
	 					  phon = rs.getString("phone");
	 					  mails = rs.getString("mail");
	 					s = 1;
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
    void attachPhoto(ActionEvent event) {
    	flc  = new FileChooser();
    	selectedFile = flc.showOpenDialog(null);
    	if(selectedFile != null){
    	
    		try {
    			input = new FileInputStream(selectedFile);
				image= new Image(input);
				sign.setImage(image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
    	}
    
    }

    @SuppressWarnings("unchecked")
	@FXML
    void goSave(ActionEvent event) {
    	   try{
   	    	if(selectedFile != null){
   	    input = new FileInputStream(selectedFile); 
   	    	}else{
   	    		JFXSnackbar bar = new JFXSnackbar(anchor);
            	bar.show("click this button only if you changed sign \n "
            			+ "if no changes just press cancel, or attach sign ",6000);
   	    	}
   	    } catch (NullPointerException  ex1) {
   			ex1.printStackTrace();
   			 
   		} catch (FileNotFoundException e) {
   			e.printStackTrace();
   		}
    	   wd = new WorkIndicatorDialog(null, "saving signature...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   JFXSnackbar bar = new JFXSnackbar(anchor);
	            	bar.show("HOS signature updated \n the ols sign is being replaced with new one",3000);
	            	loaddata();
	           }else  if(outres.equals("2")){
	        	  		 JFXSnackbar bar = new JFXSnackbar(anchor);
		            	bar.show("new HOS signature inserted \n ",3000);
		            	loaddata();
	           }else  if(outres.equals("0")){
	        	   JFXSnackbar bar = new JFXSnackbar(anchor);
	            	bar.show("Ops! signature could not be updated  \n please retry ",3000);
	           }else  if(outres.equals("-1")){
	        	   JFXSnackbar bar = new JFXSnackbar(anchor);
	            	bar.show("Ops! signature could not be updated  \n please retry ",3000);
	           }else  if(outres.equals("-2")){
	        	   JFXSnackbar bar = new JFXSnackbar(anchor);
	            	bar.show("Ops! looks like the databse didn't respond  \n please retry and if error persist "
	            			+ "\n contact database administrator ",3000);	
	           }
	           wd=null;
	 	   });
	 	  wd.exec("fetch", inputParam -> {
	 		  int p=0;
	 		   try{
	    		   con= database.dbconnect();
	    		   st = con.createStatement();
	    		   String query = "SELECT * FROM head_sign ";
					   rs=st.executeQuery(query);
					   if(rs.next()){
						   prep = (PreparedStatement) con.prepareStatement("UPDATE head_sign SET sign=?"
								   + " WHERE id=?");
						    	
						    	  prep.setBinaryStream(1, (InputStream)input,(int)selectedFile.length());
						    	  prep.setInt(2, 1);
						    	  int out = prep.executeUpdate();
						    	  if(out>0){
						    		 p=1; 
						    	  }else{
						    		p=0;  
						    	  }
					   }else{
						   prep = (PreparedStatement) con.prepareStatement("INSERT INTO head_sign (id,name,sign) VALUES(?,?,?)");
						   prep.setInt(1, 1);
						   prep.setString(2, "HEAD OF SCHOOL");
					    	 
						   prep.setBinaryStream(3, (InputStream)input,(int)selectedFile.length());
						    	 
						    	  int out = prep.executeUpdate();
						    	  if(out>0){
						    		p=2;  
						    	  }else{
						    		p=-1;  
						    	  } 
					   }
	    	   }catch(SQLException e){
	    		   e.printStackTrace();
	    		   p=-2;
	    	   }
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		           
		           return new Integer(p);
		           
		           
		        });
    
    }

    @FXML
    void showHint(ActionEvent event) {
    	JFXSnackbar bar = new JFXSnackbar(anchor);
    	bar.show("This window is used to view and change \n"
    			+ " Head of school signature by admin or HOS. \n"
    			+ "to change sign, click change sign and attach sign  ",3000);
    }
}
