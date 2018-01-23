package aps.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import aps.Main;
import aps.view.innerview.SClass;
import aps.view.innerview.Student;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class UploadAdminController implements Initializable {
	ObservableList<UploadDetail> searchdata;
	Main main;
	LoggerM log=new LoggerM();
	  @FXML
	    private static BorderPane borderpane;

	    @FXML
	    private AnchorPane anchor;
	    @FXML
	    private JFXTextField stafirstname;
	    
	    @FXML
	    private JFXTextField search;
	    
	    @FXML
	    private RadioButton terminal;

	    @FXML
	    private ToggleGroup examtype;

	    @FXML
	    private RadioButton annual;

	    @FXML
	    private ChoiceBox classchoice;

	    @FXML
	    private ChoiceBox yearchoice;

	    @FXML
	    private JFXButton btnBack;
	    @FXML
	    private ChoiceBox subjectchoice;
	    @FXML
	    private RadioButton marks;

	    @FXML
	    private ToggleGroup sheettype;

	    @FXML
	    private RadioButton behaviour;

	    @FXML
	    private StackPane stackactivate;

	    @FXML
	    private JFXButton btnattach;

	    @FXML
	    private StackPane stacktransfer;

	    @FXML
	    private JFXButton btnUpload;

	    @FXML
	    private StackPane stacksuspend;

	    @FXML
	    private JFXButton btnUpdate;

	    @FXML
	    private StackPane stackdelete;

	    @FXML
	    private JFXButton btndelete;

	    @FXML
	    private Text filenam;

	    @FXML
	    private TableView<UploadDetail> tableview;

	    @FXML
	    private TableColumn<UploadDetail, String> name;

	    @FXML
	    private TableColumn<UploadDetail, String> sclass;

	    @FXML
	    private TableColumn<UploadDetail, String> type;

	    @FXML
	    private TableColumn<UploadDetail, String> year;
	    private WorkIndicatorDialog wd=null;
		ConnectDB database = new ConnectDB();
		 private Connection con;
		    private ResultSet rs;
		    private Statement st;
		    private PreparedStatement prep;
			  FileInputStream input;
				InputStream is = null;
				Image image=null;
				FileChooser flc;
			    File selectedFile ;
			    String examTy,gendpar;
			    ObservableList<String>list = FXCollections.observableArrayList();
			    ObservableList<String>list1 = FXCollections.observableArrayList();
			    ObservableList<String>list2 = FXCollections.observableArrayList();
				String darasa = null,code = null,subj = null,years = null ,dar=null;
				String darasA = null,codE = null,subJ = null,yeaR = null,egtypE=null ;
				String egtype=null;
				String sheetTy = null,sHEETty=null;
				static String tofetch = null;
				int z = 0;
				int s = 0,b=0,j=0;
				boolean mDone;
				boolean mDone0;
				boolean sheetImo;
				boolean containb;
				String nam;
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
	    	
	    	Image bac = new Image(getClass().getResourceAsStream("images/back.png"));
			btnBack.setGraphic(new ImageView(bac));
			fillYear();
			fillClass();
			statusMe();
			
			terminal.setUserData("Terminal");
			annual.setUserData("Annual");
			
			terminal.setToggleGroup(examtype);
			annual.setToggleGroup(examtype);
			
			marks.setUserData("Marks");
			behaviour.setUserData("Behaviour");
			marks.setToggleGroup(sheettype);
			behaviour.setToggleGroup(sheettype);
			examtype.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

				@Override
				public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
					  if (examtype.getSelectedToggle() != null && !examtype.getSelectedToggle().equals("examType") ) {
						  examTy = examtype.getSelectedToggle().getUserData().toString();
				          
				         }
					
				}
				
			});
			sheettype.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

				@Override
				public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
					  if (sheettype.getSelectedToggle() != null && !sheettype.getSelectedToggle().equals("sheettype") ) {
						  sheetTy = sheettype.getSelectedToggle().getUserData().toString();
				          
				         }
					
				}
				
			});
			name.setCellValueFactory(new PropertyValueFactory<>("subject"));
			sclass.setCellValueFactory(new PropertyValueFactory<>("Sclass"));
			type.setCellValueFactory(new PropertyValueFactory<>("type"));
			year.setCellValueFactory(new PropertyValueFactory<>("year"));
search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
				
				if (oldValue != null && (newValue.length() < oldValue.length())) {
			            	tableview.setItems(searchdata);
			            }
			            String value = newValue.toLowerCase();
			            ObservableList<UploadDetail> subentries = FXCollections.observableArrayList();

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
			
		}
	    @SuppressWarnings("unchecked")
	    private void fillSubject() {
	       	String []parts;
	    	
	    	classchoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					if(arg2.equals("FORM I") || arg2.equals("FORM II") || arg2.equals("FORM III") || arg2.equals("FORM IV")){
						list2.clear();
						list2.add("choose subject here");
						subjectchoice.setValue("choose subject here");
						wd = new WorkIndicatorDialog(null, "Loading...");
					 	   wd.addTaskEndNotification(result -> {
					 		  String outres = result.toString();
					          // System.out.println("nomaa "+outres);
					           if(outres.equals("1")){
					        	   subjectchoice.setItems(list2);
					        	 
					           }
					           wd=null;
					 	  });
						wd.exec("fetch", inputParam -> {
				        		try{
				    				con= database.dbconnect();
				    				   st= con.createStatement();
				    				   String query = "SELECT  subjectCode,subjectName FROM subjects WHERE "
				    				   		+ "category='General' OR category='Computer' OR category='Religion' OR category='Science'"
				    				   		+ " OR category='Business'  OR category='Literature'"
				    				   		+ " ORDER BY subjectName ";
				    				   rs=st.executeQuery(query);
				    				   	while(rs.next()){
				    				   		String nam,cod,sub;
				    				   		cod = rs.getString("subjectCode");
				    				   		nam = rs.getString("subjectName");
				    				   		sub = cod+"-"+nam;
				    				   		list2.add(sub);
				    				   		
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
					           
					           return new Integer(1); 
				           });
				}else if(arg2.equals("FORM V") || arg2.equals("FORM VI")){
					list2.clear();
					list2.add("choose subject here");
					subjectchoice.setValue("choose subject here");
					wd = new WorkIndicatorDialog(null, "Loading...");
				 	   wd.addTaskEndNotification(result -> {
				 		  String outres = result.toString();
				          // System.out.println("nomaa "+outres);
				           if(outres.equals("1")){
				        	
				        	   subjectchoice.setItems(list2);
				        	 
				           }
				           wd=null;
				 	  });
					   wd.exec("fetch", inputParam -> {
			        		try{
			    				con= database.dbconnect();
			    				   st= con.createStatement();
			    				   String query = "SELECT  subjectCode,subjectName FROM subjects WHERE "
			    				   		+ "category='Advance'";
			    				   rs=st.executeQuery(query);
			    				   	while(rs.next()){
			    				   		String nam,cod,sub;
			    				   		cod = rs.getString("subjectCode");
			    				   		nam = rs.getString("subjectName");
			    				   		sub = cod+"-"+nam;
			    				   		list2.add(sub);
			    				   		
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
				           
				           return new Integer(1); 
			           });
				}
				
				}
	    	});
	       
		
		}
		@SuppressWarnings("unchecked")
		private void fillYear() {
	    
	    	list1.clear();
			list1.add("choose year here");
			yearchoice.setValue("choose year here");
			wd = new WorkIndicatorDialog(null, "Loading...");
		 	   wd.addTaskEndNotification(result -> {
		 		  String outres = result.toString();
		          // System.out.println("nomaa "+outres);
		           if(outres.equals("1")){
		        	   yearchoice.setItems(list1);
		        	   fillSubject();
		        	  
		           }
		           wd=null;
		 	  });
	           wd.exec("fetch", inputParam -> {
	        		String []parts;
	        		try{
	    				con= database.dbconnect();
	    				   st= con.createStatement();
	    				   String query = "SELECT DISTINCT  YEAR(students.admissionYear) as year FROM students ";
	    				   rs=st.executeQuery(query);
	    				   	while(rs.next()){
	    				   		String nam;
	    				   		nam = rs.getString("year");
	    				   		if(nam.equals("0000-00-00")){
	    				   			
	    				   		}else{
	    				   			parts = nam.split("-");
	    						   	list1.add(parts[0]);
	    				   		}
	    				   		
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
			           
			           return new Integer(1); 
		           });
	    
		}
		private void fillClass() {
	    	list.clear();
			list.add("choose class here");
			classchoice.setValue("choose class here");
	    	
		   	list.add("FORM I");
		   	list.add("FORM II");
		   	list.add("FORM III");
		   	list.add("FORM IV");
		   	list.add("FORM V");
		   	list.add("FORM VI");
		   	classchoice.setItems(list);
	  /*  	try{
				con= database.dbconnect();
				   st= con.createStatement();
				   String query = "SELECT className FROM classes ";
				   rs=st.executeQuery(query);
				   	while(rs.next()){
				   		String nam = rs.getString("className");
				   	list.add(nam);
				   	}
				   	classchoice.setItems(list);
				   	con.close();
			}catch(Exception err){
				err.printStackTrace();
			}*/
		}
		@SuppressWarnings("unchecked")
		public void statusMe(){
	    		
	    		tableview.getItems().clear();
	    	
			wd = new WorkIndicatorDialog(null, "Loading Data...");
		 	   wd.addTaskEndNotification(result -> {
		 		  String outres = result.toString();
		          // System.out.println("nomaa "+outres);
		           if(outres.equals("1")){
		        	   
		           }
		           wd=null;
		 	   });
		 	  wd.exec("fetch", inputParam -> {
			try{
				 con= database.dbconnect();
				    st= con.createStatement();
				    String select="SELECT DISTINCT a.subjectCode,a.subjectName,b.class,b.year,b.type "
				    		+ "FROM subjects a "
				    		+ "INNER JOIN result_from_teacher b "
				    		+ "ON a.subjectCode=b.coz_id "
				    		+ "ORDER BY b.class DESC";
				    rs=st.executeQuery(select);
				    while( rs.next()){
				    	tableview.getItems().add(new UploadDetail(rs.getString("subjectName"),rs.getString("class")
				    			,rs.getString("type"), rs.getString("year")));
				    	searchdata =  tableview.getItems();
						}
			}catch(Exception e){
				
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
	    void goDelete(ActionEvent event) {
	  	
	    	String [] part;
	    	JFXDialogLayout content = new JFXDialogLayout();
	    	JFXDialog dialog = new JFXDialog(stackdelete,content,JFXDialog.DialogTransition.TOP);
			try{
				egtype = examTy;
				sHEETty = sheetTy;
				darasa = classchoice.getSelectionModel().getSelectedItem().toString();
				years  = yearchoice.getSelectionModel().getSelectedItem().toString();
				subj  = subjectchoice.getSelectionModel().getSelectedItem().toString();
				part = subj.split("-");
				code = part[0];
				
				//code
			}catch(Exception e){
				TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Fill all field before submitting form");
			       tray.setMessage("The action can't be done with incomplete form");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       dialog.close();
			}
			if(sHEETty.equals("Marks")){
				if(!(darasa.equals("choose class here") && year.equals("choose year here") && 
						subj.equals("choose subject here") && year.equals(null))){
					//int affected;
					
		    	content.setHeading(new Text("Are you sure you want to delete selected sheet?"));
		    	content.setBody(new Text("if you commit this action it can't be reversed. \n"
		    			+ " for safety reason be sure with tis action"));
		    	
		    
		    	JFXButton bt = new JFXButton("Commit");
		    	JFXButton bt1 = new JFXButton("Cancel");
		    	bt.setOnAction(new EventHandler<ActionEvent>(){
		    
					@SuppressWarnings("unchecked")
					@Override
					public void handle(ActionEvent arg0) {
						wd = new WorkIndicatorDialog(null, "Deleting sheet)...");
						
				 	 	   wd.addTaskEndNotification(result -> {
				 	 		  String outres = result.toString();
				 	           if(outres.equals("1")){
				 	        	  TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.INFORMATION);
							       tray.setTitle("Selected sheet has been deleted");
							       tray.setMessage("The action can't be reversed");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
							       log.writter("Deleted sheet with code "+code+" "+darasa+" "+egtype+" "+years);
							       dialog.close();
							       statusMe();
				 	           }else if(outres.equals("2")){
				 	        	  TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.WARNING);
							       tray.setTitle("Selected sheet doesn't exit.");
							       tray.setMessage("The action can't be done with non-exist sheet");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
							       dialog.close();
				 	           }else if(outres.equals("3")){
					 	        	  TrayNotification tray = new TrayNotification();
								       tray.setNotificationType(NotificationType.WARNING);
								       tray.setTitle("Database Error.");
								       tray.setMessage("please consult database administrator");
								       tray.setAnimationType(AnimationType.SLIDE);
								       tray.showAndDismiss(Duration.millis(4000));
								       dialog.close();
					 	           }
				 	           wd=null;
				 	 	   });
				 	 	 wd.exec("fetch", inputParam -> {
				 	 		  
								int affected = 0;
									try{
									con=database.dbconnect();
						            
						          //  st= con.createStatement();
						            if(darasa.equals("FORM I")){
						            	 prep = (PreparedStatement) con.prepareStatement("DELETE FROM result_from_teacher WHERE coz_id= ? AND type = ? AND "
						            	 		+ "(class='FORM IA' OR class='FORM IB' OR class='FORM IC' OR class='FORM ID' OR class='FORM IE' OR class='FORM IF' OR class='FORM IG' OR class='FORM IH')"
						            	 		+ " AND year=?");
										 
						            	 prep.setString(1, code);
						            	 prep.setString(2, egtype);
						            	 prep.setString(3, years);
						            	 affected	 =  prep.executeUpdate();
									//affected = st.getUpdateCount();
									}else if(darasa.equals("FORM II")){
										 prep = (PreparedStatement) con.prepareStatement("DELETE FROM result_from_teacher WHERE coz_id= ? AND type = ? AND "
							            	 		+ "(class='FORM IIA' OR class='FORM IIB' OR class='FORM IIC' OR class='FORM IID' OR class='FORM IIE' OR class='FORM IIF' OR class='FORM IIG' OR class='FORM IIH')"
							            	 		+ " AND year=?");
											 
										 	prep.setString(1, code);
							            	 prep.setString(2, egtype);
							            	 prep.setString(3, years);
							            	 affected	 =  prep.executeUpdate();
									}else if(darasa.equals("FORM III")){
										 prep = (PreparedStatement) con.prepareStatement("DELETE FROM result_from_teacher WHERE coz_id= ? AND type = ? AND "
							            	 		+ "class LIKE 'FORM III%'"
							            	 		+ " AND year=?");
											 
							            	 prep.setString(1, code);
							            	 prep.setString(2, egtype);
							            	 prep.setString(3, years);
							            	 affected	 =  prep.executeUpdate();
									}else if(darasa.equals("FORM IV")){
										 prep = (PreparedStatement) con.prepareStatement("DELETE FROM result_from_teacher WHERE coz_id= ? AND type = ? AND "
							            	 		+ "class LIKE 'FORM IV%'"
							            	 		+ " AND year=?");
											 
							            	 prep.setString(1, code);
							            	 prep.setString(2, egtype);
							            	 prep.setString(3, years);
							            	 affected	 =  prep.executeUpdate();
									}
									else if(darasa.equals("FORM V")){
										 prep = (PreparedStatement) con.prepareStatement("DELETE FROM result_from_teacher WHERE coz_id= ? AND type = ? AND "
							            	 		+ "class LIKE 'FORM V%'"
							            	 		+ " AND year=?");
											 
							            	 prep.setString(1, code);
							            	 prep.setString(2, egtype);
							            	 prep.setString(3, years);
							            	 affected	 =  prep.executeUpdate();
									}else if(darasa.equals("FORM VI")){
										 prep = (PreparedStatement) con.prepareStatement("DELETE FROM result_from_teacher WHERE coz_id= ? AND type = ? AND "
							            	 		+ "class LIKE 'FORM VI%'"
							            	 		+ " AND year=?");
											 
							            	 prep.setString(1, code);
							            	 prep.setString(2, egtype);
							            	 prep.setString(3, years);
							            	 affected	 =  prep.executeUpdate();
									}else{
									System.out.println("Something wrong");
									}
									
									if(affected>0){
										s = 1;
									}else{
										s = 2;
									}
									
						            prep.close();
									}catch(SQLException er){
										System.out.println(er);
										
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
		    		
		    	});
		    	bt1.setOnAction(new EventHandler<ActionEvent>(){

					@Override
					public void handle(ActionEvent arg0) {
						dialog.close();
					}
		    		
		    	});
		    	content.setActions(bt,bt1);
		    	dialog.show();
				}
			}else if(sHEETty.equals("Behaviour")){
				if(!(darasa.equals("choose class here") && year.equals("choose year here") && 
						subj.equals("choose subject here") && year.equals(null))){
					//int affected;
					
		    	content.setHeading(new Text("Are you sure you want to delete selected sheet?"));
		    	content.setBody(new Text("if you commit this action it can't be reversed. \n"
		    			+ " for safety reason be sure with tis action"));
		    	
		    
		    	JFXButton bt = new JFXButton("Commit");
		    	JFXButton bt1 = new JFXButton("Cancel");
		    	bt.setOnAction(new EventHandler<ActionEvent>(){
		    
					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public void handle(ActionEvent arg0) {
					
						wd = new WorkIndicatorDialog(null, "Deleting sheet)...");
						
				 	 	   wd.addTaskEndNotification(result -> {
				 	 		  String outres = result.toString();
				 	          // System.out.println("nomaa "+outres);
				 	           if(outres.equals("1")){
				 	        	  TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.INFORMATION);
							       tray.setTitle("Selected sheet has been deleted");
							       tray.setMessage("The action can't be reversed");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
							       log.writter("Deleted behaviour sheet for "+darasa+" "+egtype+" "+years);
							       dialog.close();
							       statusMe();
				 	           }else if(outres.equals("2")){
				 	        	  TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.WARNING);
							       tray.setTitle("Selected sheet doesn't exit.");
							       tray.setMessage("The action can't be done with non-exist sheet");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
							       dialog.close();
				 	           }else if(outres.equals("3")){
					 	        	  TrayNotification tray = new TrayNotification();
								       tray.setNotificationType(NotificationType.WARNING);
								       tray.setTitle("Database Error.");
								       tray.setMessage("please consult database administrator");
								       tray.setAnimationType(AnimationType.SLIDE);
								       tray.showAndDismiss(Duration.millis(4000));
								       dialog.close();
					 	           }
				 	           wd=null;
				 	 	   });
				 	 	 wd.exec("fetch", inputParam -> {
				 	 		int affected = 0;
				 	 		try{
								con=database.dbconnect();
					          //  con.setAutoCommit(false);
					            //st= con.createStatement();
					            if(darasa.equals("FORM I")){
					            	 prep = (PreparedStatement) con.prepareStatement("DELETE FROM tabia WHERE type = ? AND "
					            	 		+ "(class='FORM IA' OR class='FORM IB' OR class='FORM IC' OR class='FORM ID' OR class='FORM IE' OR class='FORM IF' OR class='FORM IG' OR class='FORM IH')"
					            	 		+ " AND year=?");
									 
					            
					            	 prep.setString(1, egtype);
					            	 prep.setString(2, years);
					            	 affected	 =  prep.executeUpdate();
								//affected = st.getUpdateCount();
								}else if(darasa.equals("FORM II")){
									 prep = (PreparedStatement) con.prepareStatement("DELETE FROM tabia WHERE type = ? AND "
						            	 		+ "(class='FORM IIA' OR class='FORM IIB' OR class='FORM IIC' OR class='FORM IID' OR class='FORM IIE' OR class='FORM IIF' OR class='FORM IIG' OR class='FORM IIH')"
						            	 		+ " AND year=?");
										 
									
						            	 prep.setString(1, egtype);
						            	 prep.setString(2, years);
						            	 affected	 =  prep.executeUpdate();
								}else if(darasa.equals("FORM III")){
									 prep = (PreparedStatement) con.prepareStatement("DELETE FROM tabia WHERE  type = ? AND "
						            	 		+ "class LIKE 'FORM III%'"
						            	 		+ " AND year=?");
										 
						            	
						            	 prep.setString(1, egtype);
						            	 prep.setString(2, years);
						            	 affected	 =  prep.executeUpdate();
								}else if(darasa.equals("FORM IV")){
									 prep = (PreparedStatement) con.prepareStatement("DELETE FROM tabia WHERE type = ? AND "
						            	 		+ "class LIKE 'FORM IV%'"
						            	 		+ " AND year=?");
										 
						            	 
						            	 prep.setString(1, egtype);
						            	 prep.setString(2, years);
						            	 affected	 =  prep.executeUpdate();
								}
								else if(darasa.equals("FORM V")){
									 prep = (PreparedStatement) con.prepareStatement("DELETE FROM tabia WHERE type = ? AND "
						            	 		+ "class LIKE 'FORM V%'"
						            	 		+ " AND year=?");
										 
						            	
						            	 prep.setString(1, egtype);
						            	 prep.setString(2, years);
						            	 affected	 =  prep.executeUpdate();
								}else if(darasa.equals("FORM VI")){
									 prep = (PreparedStatement) con.prepareStatement("DELETE FROM tabia WHERE type = ? AND "
						            	 		+ "class LIKE 'FORM VI%'"
						            	 		+ " AND year=?");
										 
						            
						            	 prep.setString(1, egtype);
						            	 prep.setString(2, years);
						            	 affected	 =  prep.executeUpdate();
								}else{
								System.out.println("Something wrong");
								}
								
								
								if(affected>0){
									b = 1;
									
								}else{
									 b = 2;
									
								}
								
					            prep.close();
								}catch(SQLException er){
									er.printStackTrace();
								}
			 		               try {
			 		                  Thread.sleep(1000);
			 		               }	
			 		               catch (InterruptedException er) {
			 		                  er.printStackTrace();
			 		               }
			 		             
			 		           return new Integer(b);
			 		           
			 		           
			 		        });
					
					
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
				}
			}else{
				
			}
	
	    }

	    @SuppressWarnings("unchecked")
		@FXML
	    void goUpdate(ActionEvent event) {
	  //  String egtype=null;
	    	String [] part;
		boolean toError = false;
	    
			try{
				egtype = examTy;
				sHEETty = sheetTy;
				
				subj  = subjectchoice.getSelectionModel().getSelectedItem().toString();
				if(classchoice.getSelectionModel().getSelectedItem().toString().equals("choose class here") ||
						yearchoice.getSelectionModel().getSelectedItem().toString().equals("choose year here")){
					
				}else{
					String toclas = classchoice.getSelectionModel().getSelectedItem().toString();
					 darasa = classchoice.getSelectionModel().getSelectedItem().toString();
					if(toclas.equals("FORM I")){
						dar = "(b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH')";
						years  = yearchoice.getSelectionModel().getSelectedItem().toString();
					}if(toclas.equals("FORM II")){
						dar = "(b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH')";
						years  = yearchoice.getSelectionModel().getSelectedItem().toString();
					}if(toclas.equals("FORM III")){
						dar = "(b.class LIKE 'FORM III%')";
						years  = yearchoice.getSelectionModel().getSelectedItem().toString();
					}if(toclas.equals("FORM IV")){
						dar = "(b.class LIKE 'FORM IV%')";
						years  = yearchoice.getSelectionModel().getSelectedItem().toString();
					}if(toclas.equals("FORM V")){
						dar = "(b.class LIKE 'FORM V-%')";
						years  = yearchoice.getSelectionModel().getSelectedItem().toString();
					}if(toclas.equals("FORM VI")){
						dar = "(b.class LIKE 'FORM VI-%')";
						years  = yearchoice.getSelectionModel().getSelectedItem().toString();
					}
					
				}
			if(subj.contains("-")){
				part = subj.split("-");
				code = part[0];
			}else{
			
			}
			if(!(darasa.equals("choose class here") && year.equals("choose year here") && 
					subj.equals("choose subject here") && darasa.equals(null) && year.equals(null) &&
					subj.equals(null)) && toError==false && (egtype.equals("Terminal") || egtype.equals("Annual"))){
				tofetch  = egtype+"-"+darasa+"-"+years+"-"+code;
				wd = new WorkIndicatorDialog(null, "checking list...");
			 	   wd.addTaskEndNotification(result -> {
			 		  String outres = result.toString();
			          // System.out.println("nomaa "+outres);
			           if(outres.equals("1")){
			        	   try {
	 							main.showUploadUpdateScene();
	 						} catch (IOException e) {
	 							// TODO Auto-generated catch block
	 							e.printStackTrace();
	 						}
			           }else if(outres.equals("0")){
			        	   TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.WARNING);
					       tray.setTitle("No result on year selected");
					       tray.setMessage("we cant find any data on the detail you provide");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
			           }
			           wd=null;
			 	   });
			 		 wd.exec("fetch", inputParam -> {
				           // Thinks to do...
				           // NO ACCESS TO UI ELEMENTS!
			 			 String ty,cla,yea,cod;
			 			
			 			try{
			 				con= database.dbconnect();
			 				   st= con.createStatement();
			 				   String query = "SELECT b.stu_id,a.firstName,a.middleName,a.lastName,b.mazoezi,b.mid_term,b.exam,"
			 				   		+ "b.class,b.year,b.type,c.subjectCode,c.subjectName "
			 				   		+ "FROM subjects c "
			 				   		+ "INNER JOIN result_from_teacher b "
			 				   		+ "ON c.subjectCode=b.coz_id "
			 				   		+ "INNER JOIN students a "
			 				   		+ "ON a.student_id=b.stu_id "
			 				   		+ "WHERE b.type = '"+egtype+"' AND ("+dar+") AND b.year='"+years+"' AND c.subJectCode='"+code+"' "
			 				   		+ "ORDER BY b.class";
			 				   rs=st.executeQuery(query);
			 				  String id,fname,mname,lname,sclac,nam;
			 				  boolean exist = false;
			 				   if(rs.next()){
			 					 exist = true;
			 					   int mid,ex;
			 					   String mazoez;
			 				
			 					   id = rs.getString("stu_id");
			 					   fname = rs.getString("firstName");
			 					   mname = rs.getString("middleName");
			 					   lname =  rs.getString("lastName");
			 					   sclac = rs.getString("class");
			 					   mazoez = rs.getString("mazoezi");
			 					   mid = rs.getInt("mid_term");
			 					   ex = rs.getInt("exam");
			 					 z = 1;
			 					
			 				   }/*else{
			 					   z = 0;
			 				   }*/
			 				   if(exist){
			 					   z = 1;
			 				   }else{
			 					   z = 0;
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
				           
				           return new Integer(z);
				           
				           
				        });
			 
			}else{
				TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Fill all field before submitting form");
			       tray.setMessage("The action can't be done with incomplete form");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			}
			
			}catch(Exception e){
				toError = true;
				TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Fill all field before submitting form");
			       tray.setMessage("The action can't be done with incomplete form");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			}
	
	
	    }
	    static String getToFetch(){
	    	
	    	return tofetch;
	    }
	    public static void showUpload(){
	    	
	    }
	    public static void setNode(Node node) {
	    
	    	borderpane.setCenter(node);;

	        FadeTransition ft = new FadeTransition(Duration.millis(2000));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();

	    }
	    @SuppressWarnings("unchecked")
		@FXML
	    void goUpload(ActionEvent event) {
	    	
	    
	    	String [] part;
			
			try{
				egtype = examTy;
				//sHEETty = sheetTy;
				
				subj  = subjectchoice.getSelectionModel().getSelectedItem().toString();
				
				if(classchoice.getSelectionModel().getSelectedItem().toString().equals("choose class here") ||
						yearchoice.getSelectionModel().getSelectedItem().toString().equals("choose year here")){
					
				}else{
					darasa = classchoice.getSelectionModel().getSelectedItem().toString();
					years  = yearchoice.getSelectionModel().getSelectedItem().toString();
				}
			if(subj.contains("-")){
				part = subj.split("-");
				code = part[0];
			}else{
				
			}
				//code
			}catch(Exception e){
				TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Fill all field before submitting form");
			       tray.setMessage("The action can't be done with incomplete form");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			      
			}
			if(!(darasa.equals("choose class here") && years.equals("choose year here") && 
					subj.equals("choose subject here") && darasa.equals(null) && years.equals(null) &&
					subj.equals(null)) && (egtype.equals("Terminal") || egtype.equals("Annual")) && selectedFile!=null ){
				
				if(marks.isSelected()){
					wd = new WorkIndicatorDialog(null, "Uploading mark(S)...");
			 	 	   wd.addTaskEndNotification(result -> {
			 	 		  String outres = result.toString();
			 	          // System.out.println("nomaa "+outres);
			 	           if(outres.equals("1")){
			 	   
						       JFXSnackbar bar = new JFXSnackbar(anchor);
						     	bar.show("sheet successfully uploaded",4000);
						     	log.writter("Uploaded sheet with code "+code+" "+darasa+" "+egtype+" "+years);
						     	 statusMe();
			 	           }if(outres.equals("2")){
			 	        	  TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.WARNING);
						       tray.setTitle("Cell(S) contain Marks in a file exceeds 100%");
						       tray.setMessage("student  is skipped,correct and re-upload");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(8000));
						       statusMe();
						     
			 	           }else if(outres.equals("3")){
			 	        	  TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.WARNING);
						       tray.setTitle("Sheet EXIST");
						       tray.setMessage("the selected sheet already uploaded.");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
						       statusMe();
			 	           }else if(outres.equals("4")){
				 	        	  TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.WARNING);
							       tray.setTitle("this sheet contains NON-NUMERIC value");
							       tray.setMessage("marks cells should not include characters or letter");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
							       statusMe();
				 	           }else if(outres.equals("7")){
				 	        	  TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.WARNING);
							       tray.setTitle("Incomplete result for some student(s)");
							       tray.setMessage("student with fault is skipped, correct and re-upload");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
				 	        	  JFXSnackbar bar = new JFXSnackbar(anchor);
							     	bar.show("Incomplete data for some student, \n"
							     			+ " student with fault is skipped,correct and re-upload \n"
							     			+ " ",6000);
							     	 statusMe();
				 	           }else if(outres.equals("8")){
				 	        	  TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.WARNING);
							       tray.setTitle("one of the marks for some student(s) exceed 100%");
							       tray.setMessage("this student is skipped for now, reupload after correction");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
				 	        	  JFXSnackbar bar = new JFXSnackbar(anchor);
							     	bar.show("one of the marks for "+nam+" , \n"
							     			+ " exceeds 100%, you will need to correct this later \n "
							     			+ "as for now the system will skip this student",8000);
							     	 statusMe();
				 	           }
			 	           wd=null;
			 	 	   });
			 	 	 wd.exec("fetch", inputParam -> {
			 	 		try{
							con=database.dbconnect();
				         //   con.setAutoCommit(false);
				            st= con.createStatement();
				            PreparedStatement pstm = null ;
				            PreparedStatement pst = null ;
				            FileInputStream input = new FileInputStream(selectedFile);
				            POIFSFileSystem fs = new POIFSFileSystem(input);
				            HSSFWorkbook wb = new HSSFWorkbook(fs);
				            HSSFSheet sheet = wb.getSheetAt(0);
				            HSSFRow row;
				            
				           boolean sheetImo = false;
				           boolean behav = false;
				           boolean uploaded = false;
				           boolean exceed = false;
				           String studentclass = null;
				           for(int i=1; i<=sheet.getLastRowNum(); i++){
				        	   mDone=false;
				        	     mDone0 = false;
				                row = sheet.getRow(i);
				                String sID = row.getCell(0).getStringCellValue(); // name
				                
				                studentclass=row.getCell(2).getStringCellValue();
				            
				                String maz =null,test1="-",test2="-",test3="-",test4="-",test5="-";
							    double mid = 0;
						        double exam =0;
						        double wast = 0;
						    	//else{
						    
										  if((row.getCell(3)==null&&row.getCell(4)==null&&row.getCell(5)==null&&row.getCell(6)==null&&row.getCell(7)==null )&&( row.getCell(8) != null && row.getCell(9) != null)){   
												if( row.getCell(8).getNumericCellValue() > 100 || row.getCell(9).getNumericCellValue() > 100 ){
									    		
													exceed = true;
												   
													continue;
									    		}else{
									    	   maz="-";
											   mid =  (double) row.getCell(8).getNumericCellValue();
											   exam =  (double) row.getCell(9).getNumericCellValue();
											wast  = (mid+exam)/2;
										//	JOptionPane.showMessageDialog(null, "cond1  "+wast);
									    		}
										  }
										  if((row.getCell(3)!=null||row.getCell(4)!=null||row.getCell(5)!=null||row.getCell(6)!=null||row.getCell(7)!=null )&&( row.getCell(8)!= null && row.getCell(9)!= null)){  
											  
											  List<Double> results = new ArrayList<Double>();
											 
											  if(row.getCell(3)!=null){
											  results.add(row.getCell(3).getNumericCellValue());
											  test1=Double.toString(row.getCell(3).getNumericCellValue());
											  }
											  if(row.getCell(4)!=null){
											  results.add(row.getCell(4).getNumericCellValue());
											  test2=Double.toString(row.getCell(4).getNumericCellValue());
											  }
											  if(row.getCell(5)!=null){
											  results.add(row.getCell(5).getNumericCellValue());
											  test3=Double.toString(row.getCell(5).getNumericCellValue());
											  }
											  if(row.getCell(6)!=null){
											  results.add(row.getCell(6).getNumericCellValue());
											  test4=Double.toString(row.getCell(6).getNumericCellValue());
											  }
											  if(row.getCell(7)!=null){
											  results.add(row.getCell(7).getNumericCellValue());
											  test5=Double.toString(row.getCell(7).getNumericCellValue());
											  }
											  Double sum=0.0;
											  for(int h=0; h<results.size(); h++){
									    		    sum += results.get(h);
									    		}
                                              Double av=sum/results.size();
                                              double was = Math.round(av*100.0)/100.0;
                                              maz=""+was;
												if(was> 100 || row.getCell(8).getNumericCellValue() > 100 || row.getCell(9).getNumericCellValue() > 100 ){
									    			exceed = true;
									    			continue;
									    			}else{
									    	
											   mid =  (double) row.getCell(8).getNumericCellValue();
											   exam =  (double) row.getCell(9).getNumericCellValue();
											   wast  = (av+mid+exam)/3;
										//	JOptionPane.showMessageDialog(null, "cond2  "+wast);
									    		}
										  }   
										  if( (row.getCell(3)!=null||row.getCell(4)!=null||row.getCell(5)!=null||row.getCell(6)!=null||row.getCell(7)!=null )&&( row.getCell(8)== null || row.getCell(9)== null)){ 
											
												s = 7;
											 
											continue;
											  } 
										  if((row.getCell(3)==null&&row.getCell(4)==null&&row.getCell(5)==null&&row.getCell(6)==null&&row.getCell(7)==null )&&( row.getCell(8)== null && row.getCell(9)== null)){ 
												
												continue;
												  } 
										
				                double wastani = Math.round(wast*100.0)/100.0;
				           
				            con= database.dbconnect();
					
				            List <String>array_id = new ArrayList<String>();
						    List<String>array_coz= new ArrayList<String>();
						    List<String>array_type=new ArrayList<String>();
						    List<String>array_class =new ArrayList<String>();
						    List<String>array_year = new ArrayList<String>();
						  
						   boolean contain = false;
						  //  if(!studentclass.equals("choose") && !year.equals("choose") && !subj.equals("choose") && !btngroup.isSelected(null)  ){
						    	String sql = "SELECT stu_id,coz_id,type,class,year FROM result_from_teacher WHERE year='"+years+"' AND type='"+egtype+"' AND coz_id='"+code+"'";
						    	 rs=st.executeQuery(sql);
								    int count=0;
								    
								    while(rs.next()){
								    	
								    	array_id.add(rs.getString("stu_id"));
								    	array_coz.add(rs.getString("coz_id"));
								    	array_type.add(rs.getString("type"));
								    	array_class.add(rs.getString("class"));
								    	array_year.add(rs.getString("year"));					
								    	count++;
								  		
								    }
								    count=count+0;
								  //  JOptionPane.showMessageDialog(null, count);
								    for(int z=0; z<array_id.size(); z++){
									    //	JOptionPane.showMessageDialog(null,"Arr "+ array_class[i]);
									    	if(array_id.get(z).equals(sID) && array_coz.get(z).equals(code) && array_type.get(z).equals(egtype) && array_class.get(z).equals(studentclass)  && array_year.get(z).equals(years) ){
									    		contain = true;
									    		break;
									    	}
									    
									    }
								
									    if(contain){
									    	sheetImo = true;
									    	break;
									    }else{
									    	uploaded=true;
									    	//typ = btngroup.getSelection().getActionCommand().toString();
									    
									    	String mysql = "insert into result_from_teacher (stu_id,test1,test2,test3,test4,test5,mazoezi,mid_term,exam,wastani,coz_id,type,class,year) values ('"+sID+"','"+test1+"','"+test2+"','"+test3+"','"+test4+"','"+test5+"','"+maz+"','"+mid+"','"+exam+"','"+wastani+"','"+code+"','"+examTy+"','"+studentclass+"','"+years+"')";
								              
								                pst = (PreparedStatement) con.prepareStatement(mysql);
								              pst.executeUpdate(); 
									            pst.close();
									            con.close();
									            input.close();
									          
									            mDone0 = true;
									    }
						 
						
				            }
				           if(sheetImo){
				        	 s = 3;
				           }
				           if(mDone0){
				        	   s = 1;
				           }
				           if(exceed){
				        	   s = 2;
				           }
						}catch(IllegalStateException il){
							s = 4;
							il.printStackTrace();
						}catch(Exception e){
							e.printStackTrace();
						}
		 		       
		 		               try {
		 		                  Thread.sleep(1000);
		 		               }	
		 		               catch (InterruptedException er) {
		 		                  er.printStackTrace();
		 		               }
		 		             
		 		          return new Integer(s);
		 		         
		 		           
		 		           
		 		        });
				
				}else if(behaviour.isSelected()){
					
					/*
					 * BEHAVIOUR HERE
					 */
					wd = new WorkIndicatorDialog(null, "Uploading Behaviour...");
			 	 	   wd.addTaskEndNotification(result -> {
			 	 		  String outres = result.toString();
			 	          // System.out.println("nomaa "+outres);
			 	           if(outres.equals("1")){
			 	        	  TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.INFORMATION);
						       tray.setTitle("sheet successfully uploaded");
						       tray.setMessage("the selected sheet successfully uploaded.");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
						       log.writter("Uploaded behaviour sheet  for "+darasa+" "+egtype+" "+years);
			 	           }else if(outres.equals("2")){
			 	        	 
			 	           }else if(outres.equals("3")){
			 	        	  TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.WARNING);
						       tray.setTitle("Sheet EXIST");
						       tray.setMessage("the selected sheet already uploaded.");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
			 	           }else if(outres.equals("5")){
				 	        	  TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.WARNING);
							       tray.setTitle("this sheet contains NUMERIC value");
							       tray.setMessage("Letter cells should not include numbers as behaviour grade");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
				 	           }else if(outres.equals("6")){
				 	        	  TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.WARNING);
							       tray.setTitle("some student(s) miss behaviour,");
							       tray.setMessage("correct it and re-upload to correct the fault");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
				 	        	  JFXSnackbar bar = new JFXSnackbar(anchor);
							     	bar.show("Some student(s), miss some behaviour \n"
							     			+ " please fill all data correctly, and reupload the sheet\n"
							     			+ " ",6000);
				 	           }
			 	           wd=null;
			 	 	   });
			 	 	 wd.exec("fetch", inputParam -> {
			 	 		 int bd = 0,ubr=0,ar=0,utu=0,shrk=0,hshm=0,uongz=0,uti=0,usf=0,umnf=0,tmdn=0,kuj=0;
			 	 		try{
							con=database.dbconnect();
				            con.setAutoCommit(false);
				            st= con.createStatement();
				            PreparedStatement pstm = null ;
				            FileInputStream input = new FileInputStream(selectedFile);
				            POIFSFileSystem fs = new POIFSFileSystem( input );
				            HSSFWorkbook wb = new HSSFWorkbook(fs);
				            HSSFSheet sheet = wb.getSheetAt(0);
				            HSSFRow row,row0;
				            row0 = sheet.getRow(1);
				            
				            boolean uploaded=false;
				         
				            boolean behaviour;
				           String studentclass = null;
				           for(int i=1; i<=sheet.getLastRowNum(); i++){
				        	   containb=false;
				        	   sheetImo=false;
				        	   mDone=false;
						          
				                row = sheet.getRow(i);
				                String sID = row.getCell(0).getStringCellValue(); // name
				            
				               studentclass=row.getCell(2).getStringCellValue();
				            
										 if(row.getCell(3) != null && row.getCell(4) != null && row.getCell(5) != null && row.getCell(6) != null && row.getCell(7) != null && row.getCell(8) != null &&
												 row.getCell(9) != null && row.getCell(10) != null && row.getCell(11) != null && row.getCell(12) != null && row.getCell(13) != null){
										  
						    		try{
				                 String bidi = row.getCell(3).getStringCellValue().toUpperCase();
				                String ubora = row.getCell(4).getStringCellValue().toUpperCase();
				                 String ari = row.getCell(5).getStringCellValue().toUpperCase();
				                 String utunzaji = null;
				                 String kujiamin = null;
				                
				                 if(darasa.equals("FORM V") || darasa.equals("FORM VI")){
				                	  kujiamin =  row.getCell(6).getStringCellValue().toUpperCase();
				                 }else{
				                 utunzaji = row.getCell(6).getStringCellValue().toUpperCase();
				                 }
				             
			          
				                String shiriko = row.getCell(7).getStringCellValue().toUpperCase();
				                String heshima = row.getCell(8).getStringCellValue().toUpperCase();
				                String uongozi = row.getCell(9).getStringCellValue().toUpperCase();
				                String utii = row.getCell(10).getStringCellValue().toUpperCase();
				                String usafi = row.getCell(11).getStringCellValue().toUpperCase();
				                String uaminifu = row.getCell(12).getStringCellValue().toUpperCase();
				                String tamaduni = row.getCell(13).getStringCellValue().toUpperCase();
				              
				                if(bidi != null){
					                if(bidi.equals("A")){
					                	 bd = 5;
					                }else if(bidi.equals("B")){
					                	bd = 4;
					                }else if(bidi.equals("C")){
					                	bd = 3;
					                }else if(bidi.equals("D")){
					                	bd = 2;
					                }else if(bidi.equals("F")){
					                	bd = 1;
					                }
					               }if(ubora != null){
					                if(ubora.equals("A")){
					                	 ubr = 5;
							                
					                }else if(ubora.equals("B")){
					                	ubr = 4;
					                }else if(ubora.equals("C")){
					                	ubr = 3;
					                }else if(ubora.equals("D")){
					                	ubr = 2;
					                }else if(ubora.equals("F")){
					                	ubr = 1;
					                }
					               }if(ari != null){
					                if(ari.equals("A")){
					                	 ar = 5;
							                
					                }else if(ari.equals("B")){
					                	ar = 4;
					                }else if(ari.equals("C")){
					                	ar = 3;
					                }else if(ari.equals("D")){
					                	ar = 2;
					                }else if(ari.equals("F")){
					                	ar = 1;
					                }
					               }if(utunzaji != null){
						                if(utunzaji.equals("A")){
						                	 utu = 5;
								                
						                }else if(utunzaji.equals("B")){
						                	utu = 4;
						                }else if(utunzaji.equals("C")){
						                	utu = 3;
						                }else if(utunzaji.equals("D")){
						                	utu = 2;
						                }else if(utunzaji.equals("F")){
						                	utu = 1;
						                }
						               }if(kujiamin != null){
							                if(kujiamin.equals("A")){
							                	 kuj = 5;
									                
							                }else if(kujiamin.equals("B")){
							                	kuj = 4;
							                }else if(kujiamin.equals("C")){
							                	kuj = 3;
							                }else if(kujiamin.equals("D")){
							                	kuj = 2;
							                }else if(kujiamin.equals("F")){
							                	kuj = 1;
							                }
							               }if(shiriko != null){
							                if(shiriko.equals("A")){
							                	shrk = 5;
									                
							                }else if(shiriko.equals("B")){
							                	shrk = 4;
							                }else if(shiriko.equals("C")){
							                	shrk = 3;
							                }else if(shiriko.equals("D")){
							                	shrk = 2;
							                }else if(shiriko.equals("F")){
							                	shrk = 1;
							                }
							               }if(heshima != null){
								                if(heshima.equals("A")){
								                	hshm = 5;
										                
								                }else if(heshima.equals("B")){
								                	hshm = 4;
								                }else if(heshima.equals("C")){
								                	hshm = 3;
								                }else if(heshima.equals("D")){
								                	hshm = 2;
								                }else if(heshima.equals("F")){
								                	hshm = 1;
								                }
								               }if(uongozi != null){
									                if(uongozi.equals("A")){
									                	uongz = 5;
											                
									                }else if(uongozi.equals("B")){
									                	uongz = 4;
									                }else if(uongozi.equals("C")){
									                	uongz = 3;
									                }else if(uongozi.equals("D")){
									                	uongz = 2;
									                }else if(uongozi.equals("F")){
									                	uongz = 1;
									                }
									               }if(utii != null){
										                if(utii.equals("A")){
										                	uti = 5;
												                
										                }else if(utii.equals("B")){
										                	uti = 4;
										                }else if(utii.equals("C")){
										                	uti = 3;
										                }else if(utii.equals("D")){
										                	uti = 2;
										                }else if(utii.equals("F")){
										                	uti = 1;
										                }
										               }if(usafi != null){
											                if(usafi.equals("A")){
											                	usf = 5;
													                
											                }else if(usafi.equals("B")){
											                	usf = 4;
											                }else if(usafi.equals("C")){
											                	usf = 3;
											                }else if(usafi.equals("D")){
											                	usf = 2;
											                }else if(usafi.equals("F")){
											                	usf = 1;
											                }
											               }if(uaminifu != null){
												                if(uaminifu.equals("A")){
												                	umnf = 5;
														                
												                }else if(uaminifu.equals("B")){
												                	umnf = 4;
												                }else if(uaminifu.equals("C")){
												                	umnf = 3;
												                }else if(uaminifu.equals("D")){
												                	umnf = 2;
												                }else if(uaminifu.equals("F")){
												                	umnf = 1;
												                }
												               }if(tamaduni != null){
													                if(tamaduni.equals("A")){
													                	tmdn = 5;
															                
													                }else if(tamaduni.equals("B")){
													                	tmdn = 4;
													                }else if(tamaduni.equals("C")){
													                	tmdn = 3;
													                }else if(tamaduni.equals("D")){
													                	tmdn = 2;
													                }else if(tamaduni.equals("F")){
													                	tmdn = 1;
													                }
												               			}
													               
									  
						    		}catch(Exception ee){
						    			behaviour = true;
						    			 
						    		}
										 }else{
											s = 6;
								
											 behaviour = true;
											 continue;
										 }
				              
				            con= database.dbconnect();
						  
						  
						   
						
						   st=con.createStatement();
						   String sql = "SELECT stu_id,type,class,year FROM tabia WHERE year='"+years+"' AND type='"+examTy+"' AND stu_id='"+sID+"'";
						    	 rs=st.executeQuery(sql);
								   
								    
								    if(rs.next()){
								       containb=true;
								  		}
								  
								    if(containb){
								    	s=3;
									    }else{
									    	uploaded=true;
									    	
									    								    	
								                String mysql2 = "insert into tabia (stu_id,bidii,ubora,ari,utunzaji,ushirikiano,heshima,uongozi,utii,usafi,uaminifu,tamaduni,kujiamini,class,type,year) values ('"+sID+"','"+bd+"','"+ubr+"','"+ar+"','"+utu+"','"+shrk+"','"+hshm+"','"+uongz+"','"+uti+"','"+usf+"','"+umnf+"','"+tmdn+"','"+kuj+"','"+studentclass+"','"+examTy+"','"+years+"')";
								                pstm = (PreparedStatement) con.prepareStatement(mysql2);
								                
								                pstm.executeUpdate();
								                pstm.close();
									            con.close();
									            input.close();
									            mDone = true;
									    }
						 
						 
				            }
				           
				           if(mDone){
				        	   s = 1;
				           }
						}catch(NullPointerException nl){
							
						}catch(IllegalStateException il){
							  s = 5;
						}catch(Exception e){
							e.printStackTrace();
						}
		 		       
		 		               try {
		 		                  Thread.sleep(1000);
		 		               }	
		 		               catch (InterruptedException er) {
		 		                  er.printStackTrace();
		 		               }
		 		             
		 		           return new Integer(s);
		 		           
		 		           
		 		        });
					/*
					 * 
					 */
					
				
				}else{
					TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("Toggle between Marks or Behaviour mode ");
				       tray.setMessage("The action can't be done with incomplete form");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
				}
			
				
			}else{
				 TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Please provide all information");
			       tray.setMessage("Dear User. fill all importants detail.");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			}
	    }

	    @FXML
	    void openFile(ActionEvent event) {
	    	flc  = new FileChooser();
	    	selectedFile = flc.showOpenDialog(null);
	    	if(selectedFile != null){
	    	
	    		try {
	    			filenam.setText(selectedFile.getName());
	    			input = new FileInputStream(selectedFile);
	    			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	    @FXML
	    void goBack(ActionEvent event) {
	    	if(LoginController.getStats().equals("Admin")){
	    		
	    		Main.showMainDash();
	    	}
	    }
	

}
