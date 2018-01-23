package aps.view.innerview;

import java.awt.image.BufferedImage;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import aps.view.ConnectDB;
import aps.view.LoggerM;
import aps.view.WorkIndicatorDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class UpStaffController implements Initializable {
	
	LoggerM log=new LoggerM();
    @FXML
    private JFXTextField search;

    @FXML
    private ImageView stafphoto;

    @FXML
    private JFXButton btnphoto;

    @FXML
    private JFXTextField staid;

    @FXML
    private JFXTextField stafname;

    @FXML
    private JFXTextField stamname;

    @FXML
    private JFXTextField stalname;

    @FXML
    private RadioButton stamale;

    @FXML
    private ToggleGroup stagender;

    @FXML
    private RadioButton stafemale;

    @FXML
    private JFXDatePicker stadob;

    @FXML
    private ChoiceBox stastatus;

    @FXML
    private JFXTextField staaddr;

    @FXML
    private JFXTextField staphone;

    @FXML
    private JFXTextField stamail;

    @FXML
    private StackPane stackupdate;

    @FXML
    private JFXButton btnupdate;

    @FXML
    private StackPane stackdelete;

    @FXML
    private JFXButton btndelete;

    @FXML
    private StackPane stacktransfer;

    @FXML
    private JFXButton btntransfer;

    @FXML
    private StackPane stacksuspend;

    @FXML
    private JFXButton btnsuspend;

    @FXML
    private StackPane stackactivate;

    @FXML
    private JFXButton btnactivate;

    @FXML
    private TableView<Staff> tableview;

    @FXML
    private TableColumn<Staff, String> idcol;

    @FXML
    private TableColumn<Staff, String> fnamecol;

    @FXML
    private TableColumn<Staff, String> mnamecol;

    @FXML
    private TableColumn<Staff, String> lnamecol;

    @FXML
    private TableColumn<Staff, String> statuscol;
    private WorkIndicatorDialog wd=null;
    ObservableList<String>list = FXCollections.observableArrayList();
    ObservableList<String>filteredlist = FXCollections.observableArrayList();
    ObservableList<Staff> staflist;
	 List listofstaf = new ArrayList();
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
  String gend,gendpar;
  boolean em,su = false;
  int s = 0;
  ObservableList<Staff> searchdata;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			
			if (oldValue != null && (newValue.length() < oldValue.length())) {
		            	tableview.setItems(searchdata);
		            }
		            String value = newValue.toLowerCase();
		            ObservableList<Staff> subentries = FXCollections.observableArrayList();

		            long count = tableview.getColumns().stream().count();
		            for (int i = 0; i < tableview.getItems().size(); i++) {
		                for (int j = 0; j < count; j++) {
		                    String entry = "" + tableview.getColumns().get(j).getCellData(i);
		                    if (entry.toLowerCase().contains(value)) {
		                    	
		                        subentries.add(tableview.getItems().get(i));
		                        break;
		                    }
		                }
		            }
		            tableview.setItems(subentries);
		        });
	fetchStaff();
	stamale.setUserData("MALE");
	stafemale.setUserData("FEMALE");
	
	stamale.setToggleGroup(stagender);
	stafemale.setToggleGroup(stagender);
	
	stagender.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

		@Override
		public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
			  if (stagender.getSelectedToggle() != null && !stagender.getSelectedToggle().equals("Gender") ) {
				  	gend = stagender.getSelectedToggle().getUserData().toString().toUpperCase();
		          
		         }
			
		}
		
	});
	fillStatus();
	idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
	fnamecol.setCellValueFactory(new PropertyValueFactory<>("fname"));
	mnamecol.setCellValueFactory(new PropertyValueFactory<>("mname"));
	lnamecol.setCellValueFactory(new PropertyValueFactory<>("lname"));
	statuscol.setCellValueFactory(new PropertyValueFactory<>("status"));
	tableview.setOnMouseClicked(new EventHandler<MouseEvent>(){

		@Override
		public void handle(MouseEvent arg0) {
			stID = tableview.getSelectionModel().getSelectedItem().getId();
			fetchData(stID);
			
		}

		@SuppressWarnings("unchecked")
		private void fetchData(String stID) {


	    	wd = new WorkIndicatorDialog(null, "Loading...");
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
	 				   String query = "SELECT * FROM staffs WHERE staff_id='"+stID+"'";
	 				   rs=st.executeQuery(query);
	 				   if(rs.next()){
	 					   String id,fname,mname,lname,statu,gend,addr,dob,phon,mail;
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
	 						stafphoto.setImage(image);
	 						/*
	 						 * Save this image temp
	 						 */
	 						saveImage(image);
	 					  gend = rs.getString("Gender");
	 					 dob = rs.getString("dob");
	 					 addr = rs.getString("address");
	 					 parts = dob.split("-");
	 				
	 					  phon = rs.getString("phone");
	 					  mail = rs.getString("mail");
	 					 
	 					 staid.setText(id);	
	 					stafname.setText(fname);
	 					stamname.setText(mname); stalname.setText(lname); 
	 					if(gend.equals("MALE")){
	 						stamale.setSelected(true);
	 						stafemale.setSelected(false);
	 					}else if(gend.equals("FEMALE")){
	 						stafemale.setSelected(true);
	 						stamale.setSelected(false);
	 					}
	 					
	 					stadob.setValue(LocalDate.of(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
	 					stastatus.setValue(statu);
	 					//studadmitted.setValue(LocalDate.of(Integer.parseInt(parts2[0]),Integer.parseInt(parts2[1]), Integer.parseInt(parts2[2])));;
	 					staaddr.setText(addr);
	 					staphone.setText(phon);
	 					stamail.setText(mail);
	 					
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
		           
		           return new Integer(1);
		           
		           
		        });
		}
		
	});
	}


	@SuppressWarnings("unchecked")
	private void fillStatus() {


    	list.clear();
		list.add("Choose status Here");
		list.add("STAFF");
		list.add("Admin");
		list.add("HEAD OF SCHOOL");
		list.add("Non-STAFF");
		stastatus.setValue("Choose status Here");
    	
		wd = new WorkIndicatorDialog(null, "");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("2")){
	        	   stastatus.setItems(list); 
	           }
	           wd=null;
	 	   });
	 	  wd.exec("fetch", inputParam -> {
	 			try{
	 				con= database.dbconnect();
	 				   st= con.createStatement();
	 				   String query = "SELECT DISTINCT className FROM classes ";
	 				   rs=st.executeQuery(query);
	 				   	while(rs.next()){
	 				   		String nam = rs.getString("className");
	 				   	list.add("CLASS TEACHER: "+nam);
	 				   	}
	 				  		con.close();
	 			}catch(Exception err){
	 				err.printStackTrace();
	 			}
	 		 try {
                 Thread.sleep(1000);
              }	
              catch (InterruptedException er) {
                 er.printStackTrace();
              }
          
          return new Integer(2);
	 	   });
    
		
	
		
	
		
	}


	@SuppressWarnings("unchecked")
	private void fetchStaff() {
	for(int i=0; i<tableview.getItems().size(); i++){
    		
    		tableview.getItems().clear();
    	}
	wd = new WorkIndicatorDialog(null, "Loading staffs...");
	   wd.addTaskEndNotification(result -> {
		  String outres = result.toString();
       // System.out.println("nomaa "+outres);
        if(outres.equals("1")){
        	   staflist = FXCollections.observableList(listofstaf);
        	   tableview.setItems(staflist);
        	   searchdata =  tableview.getItems();
        }
        wd=null;
	   });
		 wd.exec("fetch", inputParam -> {
	           // Thinks to do...
	           // NO ACCESS TO UI ELEMENTS!
			try{
				con= database.dbconnect();
				   st= con.createStatement();
				   String query = "SELECT staff_id,firstName,middleName,lastName,status FROM staffs ";
				   rs=st.executeQuery(query);
				   while(rs.next()){
					   String id,fname,mname,lname,sclac;
					 
				
					   id = rs.getString("staff_id");
					   fname = rs.getString("firstName");
					   mname = rs.getString("middleName");
					   lname =  rs.getString("lastName");
					   sclac = rs.getString("status");
					   
				 listofstaf.add(new Staff(id,fname,mname, lname, sclac));
				 
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
	           
	           return new Integer(1);
	           
	           
	        });
	}
	@FXML
    void attachPoto(ActionEvent event) {
    	flc  = new FileChooser();
    	selectedFile = flc.showOpenDialog(null);
    	if(selectedFile != null){
    	
    		try {
    			input = new FileInputStream(selectedFile);
				image= new Image(input);

				stafphoto.setImage(image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
    	}
    }
	private void saveImage(Image image2) {
		File outputFile = new File("apsTemp.png");
	    BufferedImage bImage = SwingFXUtils.fromFXImage(image2, null);
	    try {
	      ImageIO.write(bImage, "png", outputFile);
	    } catch (IOException e) {
	      throw new RuntimeException(e);
	    }		
	}

    @FXML
    void goUpdate(ActionEvent event) {
   	 String id=null,fname=null,mname=null,lname=null,statu=null,gender=null,phon=null,addr=null,mail=null;
			 Date dOB=null;
			 LocalDate dob=null;
			 try{
				 id = staid.getText().toUpperCase();
				 fname = stafname.getText().toUpperCase();
		    		mname = stamname.getText().toUpperCase();
		    		lname = stalname.getText().toUpperCase();
		    		gender =gend;
		    		dob = stadob.getValue();
		    		
		    		 statu = stastatus.getSelectionModel().getSelectedItem().toString();
		    		 addr = staaddr.getText().toUpperCase();
		    	//	parsex = gendpar;
		    		 phon = staphone.getText();
		    		 mail = stamail.getText();
		    		dOB = Date.valueOf(dob);
		    		
		    		
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
			    		//Save to file first then load it when needed
			    			
			    		//load here
			    		selectedFile =new File("apsTemp.png");
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
		       if(dOB.equals(null)){
					  TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.WARNING);
					       tray.setTitle("Please provide Dates");
					       tray.setMessage("Dear User. Date of births is important.");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
				  }else{
					  if(statu != "CHOOSE STATUS HERE"){
					 try{ 
						 con= database.dbconnect();
						 prep = (PreparedStatement) con.prepareStatement("UPDATE staffs SET firstName=?,middleName=?,lastName=?,Gender=?,"
						 		+ "dob=?,status=?,phone=?,address=?,mail=?,photo=?"
					    	  		+ " WHERE staff_id=?");
					    	
					    	  prep.setString(1, fname);
					    	  prep.setString(2, mname);
					    	  prep.setString(3, lname);
					    	  prep.setString(4, gender);
					    	  prep.setDate(5, dOB);
					    	  prep.setString(6, statu);
					    	  prep.setString(7, phon);
					    	  prep.setString(8, addr);
					    //	  prep.setString(10, parsex);
					    	  prep.setString(9, mail);
							prep.setBinaryStream(10, (InputStream)input,(int)selectedFile.length());
							  prep.setString(11, id);
							int out=prep.executeUpdate();
							if(out >0){
								 TrayNotification tray = new TrayNotification();
								       tray.setNotificationType(NotificationType.SUCCESS);
								       tray.setTitle("Staff Updated successfully");
								       tray.setMessage("You can now find this staff in the list..");
								       tray.setAnimationType(AnimationType.SLIDE);
								       tray.showAndDismiss(Duration.millis(4000));
								       log.writter("Update staff with ID: "+staid);
								  //empting fields
								       input = null;
								       staid.setText(null); stafname.setText(null);stalname.setText(null);
								       stamname.setText(null); stadob.setValue(null);
								       stamail.setText(null); staphone.setText(null);staaddr.setText(null);
								       
							}else{
								TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.ERROR);
							       tray.setTitle("Failure Updating staff");
							       tray.setMessage("Make sure to complete the form .");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
							}
					 }catch(SQLException err){
						 	err.printStackTrace();
						  TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.ERROR);
						       tray.setTitle("Failure Updating staff");
						       tray.setMessage("Make sure to select staff first");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
					  	
					 }
					  
					  }else{ 
						  TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.ERROR);
					       tray.setTitle("Failure Updating staff");
					       tray.setMessage("Choose Admitted class");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
				  	
					  }
				  }	
		       
		       fetchStaff();	 
   
    }
    @FXML
    void goDelete(ActionEvent event) {
    	String ids;
    	try{
    		ids = stID;
    		if(ids.isEmpty()){
    			TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Select staff on the table to delete");
			       tray.setMessage("you cant do this action");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
    		}else{
    			/*
				 * 
				 */	JFXDialogLayout content = new JFXDialogLayout();
			    	content.setHeading(new Text("Are you sure to delete this staff?"));
			    	content.setBody(new Text("this staff will be removed permanently"));
			    	
			    	JFXDialog dialog = new JFXDialog(stackdelete,content,JFXDialog.DialogTransition.CENTER);
			    	JFXButton bt = new JFXButton("COMMIT");
			    	JFXButton bt1 = new JFXButton("CANCEL");
			    	bt.setOnAction(new EventHandler<ActionEvent>(){
			    		String prodName=null;
						@Override
						public void handle(ActionEvent arg0) {
				/*
				 * 
				 */

		  
		    	try{
		    	
					con= database.dbconnect();
					prep = (PreparedStatement) con.prepareStatement("DELETE FROM staffs WHERE staff_id = ?");
				//	System.out.println("ID  "+supId);
					prep.setString(1,ids);
					
				int out =	prep.executeUpdate();
						prep.close();
				
					if(out > 0){	
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.SUCCESS);
					       tray.setTitle("STAFF PARMANENTLY REMOVED");
					       tray.setMessage("Removed staff wont be available in the list");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					       log.writter("Deleted staff with ID: "+staid);
					}else{
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.WARNING);
					       tray.setTitle("STAFF COULD HAVE NOT BEEN REMOVED");
					       tray.setMessage("something went wrong. please retry");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					}
					
				}catch(SQLException err){
					err.printStackTrace();
				}
					/*
					 * 
					 */
		    	fetchStaff();
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
	    	fetchStaff();
		/*
		 * 
		 */
    		}
    	}catch(Exception e){
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Select staff on the table to delete");
		       tray.setMessage("you cant do this action");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    	
    
    }
}
