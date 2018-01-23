package aps.view.innerview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import aps.view.LoggerM;
import aps.view.WorkIndicatorDialog;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class UpSubjectController implements Initializable {
	ObservableList<Subject> searchdata;
	LoggerM log=new LoggerM();
    @FXML
    private JFXTextField search;

    @FXML
    private ImageView stafphoto;

    @FXML
    private JFXButton btnphoto;

    @FXML
    private JFXTextField subcode;

    @FXML
    private JFXTextField subname;

    @FXML
    private ChoiceBox subcat;

    @FXML
    private StackPane stackupdate;

    @FXML
    private JFXButton btnupdate;

    @FXML
    private StackPane stackdelete;

    @FXML
    private JFXButton btndelete;

    @FXML
    private TableView<Subject> tableview;

    @FXML
    private TableColumn<Subject, String> subcodecol;

    @FXML
    private TableColumn<Subject, String> namecol;

    @FXML
    private TableColumn<Subject, String> unitcol;

    @FXML
    private TableColumn<Subject, String> catcol;
    private WorkIndicatorDialog wd=null;
    ObservableList<String>list = FXCollections.observableArrayList();
    ObservableList<String>filteredlist = FXCollections.observableArrayList();
    ConnectDB database = new ConnectDB();
    private Connection con;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement prep;
  static  String suID = null;
  FileInputStream input;
	InputStream is = null;
	Image image=null;
	FileChooser flc;
  File selectedFile ;
  String gend,gendpar;
  boolean em,su = false;
  int s = 0;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillSubject();
		fillCateg();
		subcodecol.setCellValueFactory(new PropertyValueFactory<>("code"));
		namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
		unitcol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		catcol.setCellValueFactory(new PropertyValueFactory<>("category"));
search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			
			if (oldValue != null && (newValue.length() < oldValue.length())) {
		            	tableview.setItems(searchdata);
		            }
		            String value = newValue.toLowerCase();
		            ObservableList<Subject> subentries = FXCollections.observableArrayList();

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
		tableview.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				suID = tableview.getSelectionModel().getSelectedItem().getCode();
				fetchData(suID);
				
			}

			@SuppressWarnings("unchecked")
			private void fetchData(String suID) {


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
		 				   String query = "SELECT * FROM subjects WHERE subjectCode='"+suID+"'";
		 				   rs=st.executeQuery(query);
		 				   if(rs.next()){
		 					   String id,name,unit,cate;
		 					 String []parts,parts2;
		 					 LocalDate ld=null;
		 					   id = rs.getString("subjectCode");
		 					   name = rs.getString("subjectName");
		 					   unit = rs.getString("subjectUnit");
		 					   cate =  rs.getString("category");
		 					
		 					 
		 					  subcode.setText(id);	
		 					 subname.setText(name);
		 					subcat.setValue(cate);
		 				
		 					
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
	private void fillSubject() {
for(int i=0; i<tableview.getItems().size(); i++){
    		
    		tableview.getItems().clear();
    	}

		wd = new WorkIndicatorDialog(null, "Loading Subjects...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("2")){
	        	   
	           }
	           wd=null;
	 	   });
	 	  wd.exec("fetch", inputParam -> {
	 			try{
	 				con= database.dbconnect();
	 				   st= con.createStatement();
	 				   String query = "SELECT * FROM subjects ";
	 				   rs=st.executeQuery(query);
	 				   	while(rs.next()){
	 				   	 String id,name,unit,cate;
	 					   id = rs.getString("subjectCode");
	 					   name = rs.getString("subjectName");
	 					   unit = rs.getString("subjectUnit");
	 					   cate =  rs.getString("category");
	 					  tableview.getItems().add(new Subject(id,name,"2", cate));
	 					  searchdata =  tableview.getItems();
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
	private void fillCateg() {
    	list.clear();
		list.add("Choose category Here");
		subcat.setValue("Choose category Here");
		
		/*
		 * If we takes status from database too
		 */
	 	list.add("General");
	 	list.add("Computer");
	 	list.add("Business");
	 	list.add("Science");
		list.add("Religion");
	 	list.add("Literature");
	 	list.add("Advance");
	 	
	 	subcat.setItems(list);
    	/*
    	list.clear();
		list.add("Choose category Here");
		subcat.setValue("Choose category Here");
    	
		wd = new WorkIndicatorDialog(null, "...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("2")){
	        	   
	           }
	           wd=null;
	 	   });
	 	  wd.exec("fetch", inputParam -> {
	 			try{
	 				con= database.dbconnect();
	 				   st= con.createStatement();
	 				   String query = "SELECT DISTINCT category FROM subjects ";
	 				   rs=st.executeQuery(query);
	 				   	while(rs.next()){
	 				   		String nam = rs.getString("category");
	 				   	list.add(nam);
	 				   	}
	 				   subcat.setItems(list);
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
	 	   });*/
  	
	}
	@FXML
    void goDelete(ActionEvent event) {

    	String ids;
    	try{
    		ids = suID;
    		if(ids.isEmpty()){
    			TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Select subject on the table to delete");
			       tray.setMessage("you cant do this action");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
    		}else{
    			/*
				 * 
				 */	JFXDialogLayout content = new JFXDialogLayout();
			    	content.setHeading(new Text("Are you sure to delete this subject?"));
			    	content.setBody(new Text("this subject will be removed permanently"));
			    	
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
					prep = (PreparedStatement) con.prepareStatement("DELETE FROM subjects WHERE subjectCode = ?");
				//	System.out.println("ID  "+supId);
					prep.setString(1,ids);
					
				int out =	prep.executeUpdate();
						prep.close();
				
					if(out > 0){	
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.SUCCESS);
					       tray.setTitle("SUBJECT PARMANENTLY REMOVED");
					       tray.setMessage("Removed subject wont be available in the list");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					       log.writter("Deleted subject with code: "+subcode);
					       subcode.setText(null); subname.setText(null);
					       
					}else{
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.WARNING);
					       tray.setTitle("SUBJECT COULD HAVE NOT BEEN REMOVED");
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
		    	fillSubject();
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
	    	fillSubject();
		/*
		 * 
		 */
    		}
    	}catch(Exception e){
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Select subject on the table to delete");
		       tray.setMessage("you cant do this action");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    	
    
    
    }

    @FXML
    void goUpdate(ActionEvent event) {
      	 String id=null,name=null,cat=null,unit=null;
   			
   			 try{
   				 id = subcode.getText().toUpperCase();
   				 name = subname.getText().toUpperCase();
   		    	 cat = subcat.getSelectionModel().getSelectedItem().toString();
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
   		     
   		    
   					  if(cat != "CHOOSE CATEGORY HERE"){
   					 try{ 
   						 con= database.dbconnect();
   						 prep = (PreparedStatement) con.prepareStatement("UPDATE subjects SET subjectName=?,subjectUnit=?,category=?"
   					    	  		+ " WHERE subjectCode=?");
   					    	
   					    	  prep.setString(1, name);
   					    	  prep.setString(2, "1");
   					    	  prep.setString(3, cat);
   					    	  prep.setString(4, id);
   					    	
   							int out=prep.executeUpdate();
   							if(out >0){
   								 TrayNotification tray = new TrayNotification();
   								       tray.setNotificationType(NotificationType.SUCCESS);
   								       tray.setTitle("Subject Updated successfully");
   								       tray.setMessage("You can now find this subject in the list..");
   								       tray.setAnimationType(AnimationType.SLIDE);
   								       tray.showAndDismiss(Duration.millis(4000));
   								      log.writter("Update subject with code "+subcode);
   								    subcode.setText(null); subname.setText(null);
   							}else{
   								TrayNotification tray = new TrayNotification();
   							       tray.setNotificationType(NotificationType.ERROR);
   							       tray.setTitle("Failure Updating subject");
   							       tray.setMessage("Make sure to complete the form .");
   							       tray.setAnimationType(AnimationType.SLIDE);
   							       tray.showAndDismiss(Duration.millis(4000));
   							}
   					 }catch(SQLException err){
   						 	err.printStackTrace();
   						  TrayNotification tray = new TrayNotification();
   						       tray.setNotificationType(NotificationType.ERROR);
   						       tray.setTitle("Failure Updating subject");
   						       tray.setMessage("Make sure to select subject first");
   						       tray.setAnimationType(AnimationType.SLIDE);
   						       tray.showAndDismiss(Duration.millis(4000));
   					  	
   					 }
   					  
   					  }else{ 
   						  TrayNotification tray = new TrayNotification();
   					       tray.setNotificationType(NotificationType.ERROR);
   					       tray.setTitle("Failure Updating subject");
   					       tray.setMessage("Choose category for the subject");
   					       tray.setAnimationType(AnimationType.SLIDE);
   					       tray.showAndDismiss(Duration.millis(4000));
   				  	
   					  }
   					fillSubject();
   				  }	
   	
       
    }


