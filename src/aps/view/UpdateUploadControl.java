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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import aps.Main;
import aps.view.innerview.Student;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class UpdateUploadControl implements Initializable {
	ObservableList<Marks> searchdata;
	LoggerM log=new LoggerM();
	Main main;
	  @FXML
	    private JFXButton btnBack;

	    @FXML
	    private JFXTextField search;

	    @FXML
	    private JFXTextField stuID;
	    @FXML
	    private JFXTextField tst1;

	    @FXML
	    private JFXTextField tst2;

	    @FXML
	    private JFXTextField tst3;

	    @FXML
	    private JFXTextField tst4;
	    
	    @FXML
	    private JFXTextField tst5;

	    @FXML
	    private JFXTextField mid;

	    @FXML
	    private JFXTextField ex;

	    @FXML
	    private StackPane stackactivate;

	    @FXML
	    private JFXButton btnupdate;

	    @FXML
	    private TableView<Marks> tableview;

	    @FXML
	    private TableColumn<Marks, Integer> idcol;

	    @FXML
	    private TableColumn<Marks, Integer> namecol;
	    @FXML
	    private TableColumn<Marks, String> test1;

	    @FXML
	    private TableColumn<Marks, String> test2;

	    @FXML
	    private TableColumn<Marks, String> test3;

	    @FXML
	    private TableColumn<Marks, String> test4;
	    @FXML
	    private TableColumn<Marks, String> test5;
	    

	    @FXML
	    private TableColumn<Marks, Integer> mazoezicol;

	    @FXML
	    private TableColumn<Marks, Integer> midcol;

	    @FXML
	    private TableColumn<Marks, Integer> examcol;
	    String []part;
	    String parsed;
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
	 String sID,Sname,Smaz,testa1,testa2,testa3,testa4,testa5;
	 int Smid,Sexam;
	 int t = 0;
	 String ty,cla,yea,cod,T1,T2,T3,T4,T5;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image bac = new Image(getClass().getResourceAsStream("images/back.png"));
		btnBack.setGraphic(new ImageView(bac));
		parsed = UploadAdminController.getToFetch();
		part = parsed.split("-");
		fillData();
		idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
		namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
		test1.setCellValueFactory(new PropertyValueFactory<>("test1"));
		test2.setCellValueFactory(new PropertyValueFactory<>("test2"));
		test3.setCellValueFactory(new PropertyValueFactory<>("test3"));
		test4.setCellValueFactory(new PropertyValueFactory<>("test4"));
		test5.setCellValueFactory(new PropertyValueFactory<>("test5"));
		mazoezicol.setCellValueFactory(new PropertyValueFactory<>("mazoezi"));
		midcol.setCellValueFactory(new PropertyValueFactory<>("midterm"));
		examcol.setCellValueFactory(new PropertyValueFactory<>("exams"));
		search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			
			if (oldValue != null && (newValue.length() < oldValue.length())) {
		            	tableview.setItems(searchdata);
		            }
		            String value = newValue.toLowerCase();
		            ObservableList<Marks> subentries = FXCollections.observableArrayList();

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
					//System.out.println("You click at cell "+ tableview.getSelectionModel().getSelectedCells().get(0).getColumn());
				//proCode = tableProd.getSelectionModel().getSelectedItem().
		
				sID = tableview.getSelectionModel().getSelectedItem().getId();
				Sname = tableview.getSelectionModel().getSelectedItem().getName();
				testa1 = tableview.getSelectionModel().getSelectedItem().getTest1();
				testa2 = tableview.getSelectionModel().getSelectedItem().getTest2();
				testa3 = tableview.getSelectionModel().getSelectedItem().getTest3();
				testa4 = tableview.getSelectionModel().getSelectedItem().getTest4();
				testa5 = tableview.getSelectionModel().getSelectedItem().getTest5();
				
				Smid = tableview.getSelectionModel().getSelectedItem().getMidterm();
				Sexam = tableview.getSelectionModel().getSelectedItem().getExams();
				fetchData(sID,Sname,testa1,testa2,testa3,testa4,testa5,Smid,Sexam);
			
			}

			private void fetchData(String sID, String sname, String testa1,String testa2,String testa3,String testa4,String testa5, int smid, int sexam) {
				stuID.setText(""+sID);
			if(testa1.equals("-")){
				tst1.setText("");
			}else{
				tst1.setText(""+testa1);
			}
			if(testa2.equals("-")){
				tst2.setText("");
			}else{
				tst2.setText(""+testa2);
			}
			if(testa3.equals("-")){
				tst3.setText("");
			}else{
				tst3.setText(""+testa3);
			}
			if(testa4.equals("-")){
				tst4.setText("");
			}else{
				tst4.setText(""+testa4);
			}
			if(testa5.equals("-")){
				tst5.setText("");
			}else{
				tst5.setText(""+testa5);
			}
				mid.setText(""+Smid);
				ex.setText(""+Sexam);
				
				
			}
		});
	}
	   @SuppressWarnings("unchecked")
	private void fillData() {
		   tableview.getItems().clear();
			
		 	wd = new WorkIndicatorDialog(null, "Loading data...");
		 	   wd.addTaskEndNotification(result -> {
		 		  String outres = result.toString();
		          // System.out.println("nomaa "+outres);
		           if(outres.equals("1")){
		        	   
		           }
		           wd=null;
		 	   });
		 		 wd.exec("fetch", inputParam -> {
			 
		 			 String ty,dar,yea,cod,clac = null;
		 			 ty = part[0].toString(); 
		 			 dar = part[1].toString(); 
						if(dar.equals("FORM I")){
							clac = "(b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH')";
						}if(dar.equals("FORM II")){
							clac = "(b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH')";
						}if(dar.equals("FORM III")){
							clac = "(b.class LIKE 'FORM III%')";
						}if(dar.equals("FORM IV")){
							clac = "(b.class LIKE 'FORM IV%')";
						}if(dar.equals("FORM V")){
							clac = "(b.class LIKE 'FORM V-%')";
						}if(dar.equals("FORM VI")){
							clac = "(b.class LIKE 'FORM VI-%')";
						}
		 			 yea = part[2].toString();
		 			 cod = part[3].toString();
		 			try{
		 				con= database.dbconnect();
		 				   st= con.createStatement();
		 				   String query = "SELECT b.stu_id,a.firstName,a.middleName,a.lastName,b.test1,b.test2,b.test3,b.test4,b.test5,b.mazoezi,b.mid_term,b.exam,"
		 				   		+ "b.class,b.year,b.type,c.subjectCode,c.subjectName "
		 				   		+ "FROM subjects c "
		 				   		+ "INNER JOIN result_from_teacher b "
		 				   		+ "ON c.subjectCode=b.coz_id "
		 				   		+ "INNER JOIN students a "
		 				   		+ "ON a.student_id=b.stu_id "
		 				   		+ "WHERE b.type = '"+ty+"' AND ("+clac+") AND b.year='"+yea+"' AND c.subjectCode='"+cod+"' "
		 				   		+ "ORDER BY b.class";
		 				
		 				   rs=st.executeQuery(query);
		 				   while(rs.next()){
		 					   String id,fname,mname,lname,sclac,nam,mazoez,t1,t2,t3,t4,t5;
		 					   int mid,ex;
		 				
		 					   id = rs.getString("stu_id");
		 					   fname = rs.getString("firstName");
		 					   mname = rs.getString("middleName");
		 					   lname =  rs.getString("lastName");
		 					   sclac = rs.getString("class");
		 					   t1 = rs.getString("test1");
		 					   t2 = rs.getString("test2");
		 					   t3 = rs.getString("test3");
		 					   t4 = rs.getString("test4");
		 					   t5 = rs.getString("test5");
		 					   mazoez = rs.getString("mazoezi");
		 					   mid = rs.getInt("mid_term");
		 					   ex = rs.getInt("exam");
		 					   nam = fname+" "+mname+" "+lname;
		 					  
		 				 tableview.getItems().add(new Marks(id,nam,t1,t2,t3,t4,t5,mazoez, mid, ex));
		 				 searchdata =  tableview.getItems();
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
	    void goBack(ActionEvent event) {

	    	try {
				main.showUploadScene();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	    }

	    @SuppressWarnings("unchecked")
		@FXML
	    void goToUpdate(ActionEvent event) {
	    	String myID,mazoe,mdte,axa,mazD;
	    	T1="-";T2="-";T3="-";T4="-";T5="-";
	    	myID = stuID.getText();
	    	
	    	
	    	mdte = mid.getText();
	    	axa = ex.getText();
	    	
	    	if(myID.length()==0 || mdte.length()==0 ||axa.length()==0){
	    		TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("ALL FIELD ARE IMPORTANT,");
			       tray.setMessage("choose student on the list, edit marks and upload \n mazoezi is optional");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(5000));
	    	}else{
	    		double mid,exm; double wastan,maz;
		    	List<Double> results = new ArrayList<Double>();
		    	if(tst1.getText().length()!=0){
		    		results.add(Double.parseDouble(tst1.getText()));
		    		T1=tst1.getText();
		    		}
		    	if(tst2.getText().length()!=0){
		    		results.add(Double.parseDouble(tst2.getText()));
		    		T2=tst2.getText();
		    		
		    		}
		    	if(tst3.getText().length()!=0){
		    		results.add(Double.parseDouble(tst3.getText()));
		    		T3=tst3.getText();
		    		}
		    	if(tst4.getText().length()!=0){
		    		results.add(Double.parseDouble(tst4.getText()));
		    		T4=tst4.getText();
		    		}
		    	if(tst5.getText().length()!=0){
		    		results.add(Double.parseDouble(tst5.getText()));
		    		T5=tst5.getText();
		    		}
		    	Double sum=0.0;
				  for(int h=0; h<results.size(); h++){
		    		    sum += results.get(h);
		    		}
                 Double av=sum/results.size();
                 double was = Math.round(av*100.0)/100.0;
                  
	    		
	    		if(tst5.getText().length()==0&&tst4.getText().length()==0&&tst3.getText().length()==0&&tst2.getText().length()==0&&tst1.getText().length()==0){
	    			mid = Double.parseDouble(mdte); exm = Double.parseDouble(axa);
	    			wastan = (mid+exm)/2;
	    			mazD="-";
	    		}else{
	    			mid = Double.parseDouble(mdte); exm =Double.parseDouble(axa);
	    			wastan = (was+mid+exm)/3;
	    			mazD=""+was;
	    	}
	    		double wastani = Math.round(wastan*100.0)/100.0;

	    		wd = new WorkIndicatorDialog(null, "Updating results...");
			 	   wd.addTaskEndNotification(result -> {
			 		  String outres = result.toString();
			          // System.out.println("nomaa "+outres);
			           if(outres.equals("1")){
			        	   TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.INFORMATION);
					       tray.setTitle("result updated with success");
					       tray.setMessage("information with such data is recalculated.");
					       tray.setAnimationType(AnimationType.SLIDE);
					      
					      log.writter("Updated results for student with ID: "+myID+" subject "+cod+" "+cla+" "+ty+" "+yea);
					       tray.showAndDismiss(Duration.millis(4000));
					       fillData();
			           }else  if(outres.equals("0")){
			        	   TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.WARNING);
					       tray.setTitle("no changes made");
					       tray.setMessage("please retry,.");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					       fillData();
			           }
			           wd=null;
			 	   });
			 		 wd.exec("fetch", inputParam -> {
			 		  	try {
				    		con=database.dbconnect();
				    		 
				 			 ty = part[0].toString(); cla = part[1].toString(); yea = part[2].toString();
				 			 cod = part[3].toString();

							st=con.createStatement();
							String updt="UPDATE result_from_teacher set test1='"+T1+"',test2='"+T2+"', test3='"+T3+"',test4='"+T4+"',test5='"+T5+"',mazoezi='"+mazD+"', mid_term='"+mid+"',exam='"+exm+"'"
									+ ",wastani='"+wastani+"' where stu_id='"+myID+"' AND coz_id='"+cod+"' AND year='"+yea+"' AND type='"+ty+"' ";
							int out= st.executeUpdate(updt);
							if(out >0){
								t = 1;
							}else{
								t = 0;
							}
						} catch (SQLException e) {
						
						}
				               try {
				                  Thread.sleep(1000);
				               }	
				               catch (InterruptedException er) {
				                  er.printStackTrace();
				               }
				           
				           return new Integer(t);
				           
				           
				        });
	  	
	    	}
	   }
}
