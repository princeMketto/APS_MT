package aps.view.innerview;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import aps.view.ConnectDB;
import aps.view.LoggerM;
import aps.view.WorkIndicatorDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class AnnounceController implements Initializable {
	LoggerM log=new LoggerM();
	String Gavarage;
	
	private WorkIndicatorDialog wd=null;
	ConnectDB database = new ConnectDB();
    private Connection con;
    private ResultSet rs,rs4,rs2,rs3;
    private Statement st,st4,st2,st3;
	  @FXML
	    private RadioButton terminal;
	  @FXML
	    private StackPane stackannou;

	   @FXML
	    private JFXTextField search;
        
	    @FXML
	    private ToggleGroup examtype;

	    @FXML
	    private RadioButton annual;

	    @FXML
	    private ChoiceBox yearchoice;

	    @FXML
	    private ChoiceBox classchoice;

	    @FXML
	    private JFXTextArea headannounce;

	    @FXML
	    private JFXTextArea teacherannounce;
	    @FXML
	    private RadioButton GA;

	    @FXML
	    private ToggleGroup grade;

	    @FXML
	    private RadioButton GB;

	    @FXML
	    private RadioButton GC;

	    @FXML
	    private RadioButton GD;

	    @FXML
	    private RadioButton GE;

	    @FXML
	    private RadioButton GF;

	    @FXML
	    private RadioButton GS;

	    @FXML
	    private JFXButton btnsubmit;

	    @FXML
	    private TableView<Announce> tableview;

	    @FXML
	    private TableColumn<Announce, String> classcol;

	    @FXML
	    private TableColumn<Announce, String> headacol;

	    @FXML
	    private TableColumn<Announce, String> teacheracol;

	    @FXML
	    private TableColumn<Announce, String> typecol;

	    @FXML
	    private TableColumn<Announce, String> gradecol;
	    
	    @FXML
	    private TableColumn<Announce, String> yearcol;
	    
	    ObservableList<String> year1 = FXCollections.observableArrayList();
	    ObservableList<String> classes = FXCollections.observableArrayList();
	    ObservableList<Announce> searchdata;
	    
	    
	    
	    @FXML
	    void goannounces(ActionEvent event) {
	    	
	    	announce();
	    }

	@SuppressWarnings("unchecked")
	@Override
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		GA.setUserData("A");
		GA.setToggleGroup(grade);
		GB.setUserData("B");
		GB.setToggleGroup(grade);
		GC.setUserData("C");
		GC.setToggleGroup(grade);
		GD.setUserData("D");
		GD.setToggleGroup(grade);
		GE.setUserData("E");
		GE.setToggleGroup(grade);
		GS.setUserData("S");
		GS.setToggleGroup(grade);
		GF.setUserData("F");
		GF.setToggleGroup(grade);
		
		classchoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
             
				@Override
				public void changed(ObservableValue<? extends String> arg0, String old, String clas) {
					String classes=(String) classchoice.getSelectionModel().getSelectedItem();
					
					if(classes.equals("FORM I")||classes.equals("FORM II")||classes.equals("FORM III")||classes.equals("FORM IV")){
						grade.selectToggle(null);
						GS.setOpacity(0);
						GE.setOpacity(0);
					}else{
						grade.selectToggle(null);
						GS.setOpacity(1);
						GE.setOpacity(1);
					}
					
				}
		    	
		    });
	    
		
		classcol.setCellValueFactory(new PropertyValueFactory<Announce, String>("Aclass"));
		gradecol.setCellValueFactory(new PropertyValueFactory<Announce, String>("gradeA"));
		
		headacol.setCellValueFactory(new PropertyValueFactory<Announce, String>("head"));
		teacheracol.setCellValueFactory(new PropertyValueFactory<Announce, String>("teacher"));
		typecol.setCellValueFactory(new PropertyValueFactory<Announce, String>("atype"));
		yearcol.setCellValueFactory(new PropertyValueFactory<Announce, String>("ayear"));
				
		announcequery();
		search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			
			if (oldValue != null && (newValue.length() < oldValue.length())) {
		            	tableview.setItems(searchdata);
		            }
		            String value = newValue.toLowerCase();
		            ObservableList<Announce> subentries = FXCollections.observableArrayList();

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
				String classs = tableview.getSelectionModel().getSelectedItem().getAclass();
				String grd = tableview.getSelectionModel().getSelectedItem().getGradeA();
				String heada = tableview.getSelectionModel().getSelectedItem().getHead();
				String tannounc = tableview.getSelectionModel().getSelectedItem().getTeacher();
				String atype = tableview.getSelectionModel().getSelectedItem().getAtype();
				String yr = tableview.getSelectionModel().getSelectedItem().getAyear();
				
				yearchoice.setValue(yr);
				classchoice.setValue(classs);
				if(atype.equals("Terminal")){
						terminal.setSelected(true);
					}else if(atype.equals("Annual")){
						annual.setSelected(true);
					}
				headannounce.setText(heada);
				teacherannounce.setText(tannounc);
				if(grd.equals("A")){
					GA.setSelected(true);
				}else if(grd.equals("B")){
					GB.setSelected(true);
				}else if(grd.equals("C")){
					GC.setSelected(true);
				}else if(grd.equals("D")){
					GD.setSelected(true);
				}else if(grd.equals("E")){
					GE.setSelected(true);
				}else if(grd.equals("S")){
					GS.setSelected(true);
				}else if(grd.equals("F")){
					GF.setSelected(true);
				}
				
				
				
			}
		});
		con= database.dbconnect();
		 try {
				st= con.createStatement();
				 String yrs="SELECT distinct year FROM  result_from_teacher";
				    
				    rs=st.executeQuery(yrs);
				    year1.add("Choose year");
				    while(rs.next()){
				   String  	year=rs.getString("year");
				         year1.add(year);
				    
				       }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 classes.clear();
		 
		 classes.add("Choose class");
		    classes.add("FORM I");
		    classes.add("FORM II");
		    classes.add("FORM III");
		    classes.add("FORM IV");
		    String yrs2="SELECT *from classes where className LIKE 'FORM V%'";
		    try {
				rs=st.executeQuery(yrs2);
				 while(rs.next()){
			classes.add(rs.getString("className"));
				    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		    
		    
	       	yearchoice.setValue("Choose year");
	       	classchoice.setValue("Choose class");
	       	classchoice.setItems(classes);
	    	yearchoice.setItems(year1);
	    	
	    	
		
	}
	public void announcequery(){
		con= database.dbconnect();
		try{
			
		st= con.createStatement();
		String anc="SELECT * FROM announces";
		rs=st.executeQuery(anc);
		
		while(rs.next()){
			String cls=rs.getString("class");
			String headt=rs.getString("hmaster");
			String cteach=rs.getString("class_teacher");
			String atyp=rs.getString("type");
			String yrr=rs.getString("year");
			String gr=rs.getString("grade");
			
			tableview.getItems().add(new Announce(cls,gr,headt,cteach,atyp,yrr));
			searchdata =  tableview.getItems();
			
		}
		}catch(Exception ea){
			
		}
		
	}
	@SuppressWarnings("unchecked")
	public void announce(){
		

    	if((terminal.isSelected()||annual.isSelected())&&(GA.isSelected()||GB.isSelected()||GC.isSelected()||GD.isSelected()||GE.isSelected()||GS.isSelected()||GF.isSelected())){
    		Gavarage=grade.getSelectedToggle().getUserData().toString();
    	Boolean farm=false;
    	terminal.setUserData("Terminal");
    	annual.setUserData("Annual");
    	terminal.setToggleGroup(examtype);
    	annual.setToggleGroup(examtype);
    	
    	String typeexm=examtype.getSelectedToggle().getUserData().toString();
    	String year=(String) yearchoice.getSelectionModel().getSelectedItem();
    	String clacc=(String) classchoice.getSelectionModel().getSelectedItem();
    	String headword=headannounce.getText();
    	String teachword=teacherannounce.getText();
   if(headword.length()!=0&&teachword.length()!=0&&year.equals("Choose year")==false&&clacc.equals("Choose class")==false){
    try{
    	con=database.dbconnect();
    	st=con.createStatement();
    	st2=con.createStatement();
    	st4=con.createStatement();
    	
	 		  
	 
    	String an=" select * from announces where class='"+clacc+"' and type='"+typeexm+"' and year='"+year+"' and grade='"+Gavarage+"' ";
    	rs=st.executeQuery(an);
    	if(rs.next()){
    		farm=true;	
    	}else{
    		wd = new WorkIndicatorDialog(null, "Saving...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
	 	        	  tableview.getItems().clear();
	 	        	   TrayNotification tray = new TrayNotification();
	 			       tray.setNotificationType(NotificationType.SUCCESS);
	 			       tray.setTitle("Success");
	 			       tray.setMessage("Announcements successfully saved...");
	 			       tray.setAnimationType(AnimationType.SLIDE);
	 			       tray.showAndDismiss(Duration.millis(4000));
	 			       log.writter("Save announcements for "+clacc+" "+typeexm+" "+year);
	 			      announcequery();
	 			       
	 	           }else{
	 	        	  	 
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	  wd.exec("fetch", inputParam -> {
	 	 	   int k=0;
		    	
		    		String anno="INSERT INTO announces(class,type,hmaster,class_teacher,year,grade) VALUES('"+clacc+"','"+typeexm+"','"+headword+"','"+teachword+"','"+year+"','"+Gavarage+"')";
					try {
						st2.executeUpdate(anno);
						 k=1;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
	 	 		 try {
	                  Thread.sleep(1000);
	               }	
	               catch (InterruptedException er) {
	                  er.printStackTrace();
	               }
	             
	           return new Integer(k);
	           
	           
	        });
	 	 	  
    			
    	}
    	if(farm){
    		JFXDialogLayout content = new JFXDialogLayout();
	    	content.setHeading(new Text("Announces already exist"));
	    	content.setBody(new Text("You can update existing announcement"));
	    	JFXDialog dialog = new JFXDialog(stackannou,content,JFXDialog.DialogTransition.CENTER);
	    	JFXButton btnupdate = new JFXButton("UPDATE");
	    	btnupdate.getBorder();
	    	JFXButton btncancel = new JFXButton("CANCEL");
	    	
	    	content.setActions(btnupdate,btncancel);
	    	dialog.show();
	    	btncancel.setOnAction(new EventHandler<ActionEvent>(){
	    		@Override
				public void handle(ActionEvent arg0) {
					dialog.close();
				}
	    		
	    	});
	    	
	    	btnupdate.setOnAction(new EventHandler<ActionEvent>(){
	    	@SuppressWarnings("unchecked")
			public void handle(ActionEvent arg0) {
	    		
	    		wd = new WorkIndicatorDialog(null, "updating...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
	 	        	   tableview.getItems().clear();
	 	        	   TrayNotification tray = new TrayNotification();
	 			       tray.setNotificationType(NotificationType.SUCCESS);
	 			       tray.setTitle("Success");
	 			       tray.setMessage("Announcements successfully updated...");
	 			       tray.setAnimationType(AnimationType.SLIDE);
	 			       tray.showAndDismiss(Duration.millis(4000));
	 			      log.writter("Update announcements for "+clacc+" "+typeexm+" "+year);
	 			      announcequery();
	 	           }else{
	 	        	  	 
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	  wd.exec("fetch", inputParam -> {
	 	 		int s=0;
    		try{
    			
		    	
		       String ann="UPDATE announces set hmaster='"+headword+"', class_teacher='"+teachword+"' where class='"+clacc+"' and type='"+typeexm+"' and year='"+year+"' and grade='"+Gavarage+"'";
		       st4.executeUpdate(ann);
		       dialog.close();
		         s=1;
		     
    		}catch(Exception d){
    			 
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
	    	
		     
    	}
    
    }catch(Exception r){
    	
    }
    
    	}else if(headword.length()==0||teachword.length()==0){
    		   TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("FILL ");
		       tray.setMessage("Head and teacher's word are required");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    	}else{
    		 TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("FILL ");
		       tray.setMessage("Select terminal , Annual and grade average please!");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    
	}

}
