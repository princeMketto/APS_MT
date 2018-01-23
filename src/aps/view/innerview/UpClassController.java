package aps.view.innerview;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class UpClassController implements Initializable {
	ObservableList<SClass> searchdata;
	LoggerM log=new LoggerM();
	    @FXML
	    private JFXTextField combname;
	    @FXML
	    private JFXTextField search;

	   
	    @FXML
	    private StackPane stackupdate;

	    @FXML
	    private JFXButton btnupdate;

	    @FXML
	    private StackPane stackdelete;

	    @FXML
	    private JFXButton btndelete;

	    @FXML
	    private TableView<SClass> tableview;

	    @FXML
	    private TableColumn<SClass, String> combcol;
	    @FXML
	    private TableColumn<SClass, String> idcol;

	    private WorkIndicatorDialog wd=null;
	    ObservableList<String>list = FXCollections.observableArrayList();
	    ObservableList<String>filteredlist = FXCollections.observableArrayList();
	    ConnectDB database = new ConnectDB();
	    private Connection con;
	    private ResultSet rs;
	    private Statement st;
	    private PreparedStatement prep;
	  static  String suID = null;
		  boolean em,su = false;
	  int s = 0;
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			fillComb();
			idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
			combcol.setCellValueFactory(new PropertyValueFactory<>("stream"));
			search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
				
				if (oldValue != null && (newValue.length() < oldValue.length())) {
			            	tableview.setItems(searchdata);
			            }
			            String value = newValue.toLowerCase();
			            ObservableList<SClass> subentries = FXCollections.observableArrayList();

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
					suID = tableview.getSelectionModel().getSelectedItem().getId();
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
			 				   String query = "SELECT * FROM combination WHERE id='"+suID+"' ";
			 				   rs=st.executeQuery(query);
			 				   	if(rs.next()){
			 				   	 String name;
			 					   name = rs.getString("comb");
			 					  combname.setText(name);
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
		private void fillComb() {
	    	for(int i=0; i<tableview.getItems().size(); i++){
	    		
	    		tableview.getItems().clear();
	    	}
	    	wd = new WorkIndicatorDialog(null, "Loading...");
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
		 				   String query = "SELECT * FROM combination ";
		 				   rs=st.executeQuery(query);
		 				   	while(rs.next()){
		 				   	 String name,id;
		 				   	 	id = rs.getString("id");
		 					   name = rs.getString("comb");
		 					  tableview.getItems().add(new SClass(id,name));
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
		@FXML
	    void goDelete(ActionEvent event) {
	    	String ids;
	    	try{
	    		ids = suID;
	    		if(ids.isEmpty()){
	    			TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("Select combinaion on the table to delete");
				       tray.setMessage("you cant do this action");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
	    		}else{
	    			/*
					 * 
					 */	JFXDialogLayout content = new JFXDialogLayout();
				    	content.setHeading(new Text("Are you sure to delete this combination?"));
				    	content.setBody(new Text("this combination will be removed permanently"));
				    	
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
						prep = (PreparedStatement) con.prepareStatement("DELETE FROM combination WHERE id = ?");
					//	System.out.println("ID  "+supId);
						prep.setString(1,ids);
						
					int out =	prep.executeUpdate();
							prep.close();
					
						if(out > 0){	
							TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.SUCCESS);
						       tray.setTitle("COMBINATION PARMANENTLY REMOVED");
						       tray.setMessage("Removed combination wont be available in the list");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
						       log.writter("Deleted combination with name: "+combname);
						       combname.setText(null); 
						}else{
							TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.WARNING);
						       tray.setTitle("COMBINATION COULD HAVE NOT BEEN REMOVED");
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
			    	fillComb();
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
		    	fillComb();
			/*
			 * 
			 */
	    		}
	    	}catch(Exception e){
	    		TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.ERROR);
			       tray.setTitle("Select combination on the table to delete");
			       tray.setMessage("you cant do this action");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
	    	}
	    
	    }

	    @FXML
	    void goUpdate(ActionEvent event) {
	      	 String id=null;
	   			
	   			 try{
	   				 id = combname.getText().toUpperCase();
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
	   						 prep = (PreparedStatement) con.prepareStatement("UPDATE combination SET comb=?"
	   					    	  		+ " WHERE comb=?");
	   					    	
	   					    	  prep.setString(1, id);
	   					    	prep.setString(2, id);
	   					    	
	   							int out=prep.executeUpdate();
	   							if(out >0){
	   								 TrayNotification tray = new TrayNotification();
	   								       tray.setNotificationType(NotificationType.SUCCESS);
	   								       tray.setTitle("Combination Updated successfully");
	   								       tray.setMessage("You can now find this comb in the list..");
	   								       tray.setAnimationType(AnimationType.SLIDE);
	   								    log.writter("Updated combination with name: "+combname);
	   								       tray.showAndDismiss(Duration.millis(4000));
	   								
	   								    combname.setText(null);
	   							}else{
	   								TrayNotification tray = new TrayNotification();
	   							       tray.setNotificationType(NotificationType.ERROR);
	   							       tray.setTitle("Failure Updating combination");
	   							       tray.setMessage("Make sure to complete the form .");
	   							       tray.setAnimationType(AnimationType.SLIDE);
	   							       tray.showAndDismiss(Duration.millis(4000));
	   							}
	   					 }catch(SQLException err){
	   						 	err.printStackTrace();
	   						  TrayNotification tray = new TrayNotification();
	   						       tray.setNotificationType(NotificationType.ERROR);
	   						       tray.setTitle("Failure Updating combination");
	   						       tray.setMessage("Make sure to select combination first");
	   						       tray.setAnimationType(AnimationType.SLIDE);
	   						       tray.showAndDismiss(Duration.millis(4000));
	   					  	
	   					 }
	   					  
	   					
	   					fillComb();
	   				  	
	    }
	

}
