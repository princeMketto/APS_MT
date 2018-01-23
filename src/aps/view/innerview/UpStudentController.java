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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;



public class UpStudentController implements Initializable {
	ObservableList<Student> searchdata;
	 ObservableList<Student> studentlist;
	 List listofstud = new ArrayList();
	LoggerM log=new LoggerM();
	  @FXML
	    private JFXTextField search;

    @FXML
    private ImageView studphoto;

    @FXML
    private JFXButton btnphoto;

    @FXML
    private JFXButton btnparent;
    @FXML
    private StackPane parpop;

    @FXML
    private JFXTextField stuid;

    @FXML
    private JFXTextField stufname;

    @FXML
    private JFXTextField stumname;

    @FXML
    private JFXTextField stulname;

    @FXML
    private RadioButton stumale;

    @FXML
    private ToggleGroup stugender;

    @FXML
    private RadioButton stufemale;

    @FXML
    private JFXDatePicker studob;

    @FXML
    private ChoiceBox studclass;

    @FXML
    private JFXDatePicker studadmitted;

    @FXML
    private JFXTextField parname;

    @FXML
    private JFXTextField parphone;

    @FXML
    private JFXTextField parmail;

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
    private StackPane stackolevel;

    @FXML
    private JFXButton btnolevel;

    @FXML
    private StackPane stackalevel;

    @FXML
    private JFXButton btnalevel;

    @FXML
    private StackPane stackactivate;

    @FXML
    private JFXButton btnactivate;

    @FXML
    private TableView<Student> tableview;

    @FXML
    private TableColumn<Student, String> idcol;

    @FXML
    private TableColumn<Student, String> fnamecol;

    @FXML
    private TableColumn<Student, String> mnamecol;
    int revYear = 0;
    @FXML
    private TableColumn<Student, String> lnamecol;

    @FXML
    private TableColumn<Student, String> classcol;
    private WorkIndicatorDialog wd=null;
    ObservableList<String>list = FXCollections.observableArrayList();
    ObservableList<String>filteredlist = FXCollections.observableArrayList();
    ConnectDB database = new ConnectDB();
    private Connection con;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement prep;
  static  String stID = null,parID=null,Pname=null,Pphon,Pmail,Pnat,Ploc,Poc,Padre;
  FileInputStream input;
	InputStream is = null;
	Image image=null;
	FileChooser flc;
  File selectedFile ;
  String gend,gendpar;
  String mzaz,mzphon,mzmail,mzaddress,mzlocation,mzkaz,mztaif;
  boolean em,su = false;
  int s = 0,a=0;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	fetchStudent();
		
	stumale.setUserData("MALE");
	stufemale.setUserData("FEMALE");
	
	stumale.setToggleGroup(stugender);
	stufemale.setToggleGroup(stugender);
	
	stugender.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

		@Override
		public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
			  if (stugender.getSelectedToggle() != null && !stugender.getSelectedToggle().equals("Gender") ) {
				  	gend = stugender.getSelectedToggle().getUserData().toString().toUpperCase();
		          
		         }
			
		}
		
	});
	fillClass();
	idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
	fnamecol.setCellValueFactory(new PropertyValueFactory<>("fname"));
	mnamecol.setCellValueFactory(new PropertyValueFactory<>("mname"));
	lnamecol.setCellValueFactory(new PropertyValueFactory<>("lname"));
	classcol.setCellValueFactory(new PropertyValueFactory<>("clas"));
	
search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
		
		if (oldValue != null && (newValue.length() < oldValue.length())) {
	            	tableview.setItems(searchdata);
	            }
	            String value = newValue.toLowerCase();
	            ObservableList<Student> subentries = FXCollections.observableArrayList();

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
	 				   String query = "SELECT * FROM students WHERE student_id='"+stID+"' AND "
	 				   		+ "NOT (status='TRANSFERRED' OR status='DISABLED')";
	 				   rs=st.executeQuery(query);
	 					  mzaz=null;mzphon=null;mzmail=null;mzaddress=null;mzlocation=null;mzkaz=null;mztaif=null;

	 				   if(rs.next()){
	 					   String id,fname,mname,lname,sclac,gend,admit,dob,parnam,parsex,paphone,pamail;
	 					 Date adm;
	 					 String []parts,parts2;
	 					 LocalDate ld=null;
	 					   id = rs.getString("student_id");
	 					   fname = rs.getString("firstName");
	 					   mname = rs.getString("middleName");
	 					   lname =  rs.getString("lastName");
	 					   sclac = rs.getString("studentClass");
	 					  Blob aBlob = rs.getBlob("photo");
	 					   is =  aBlob.getBinaryStream(1, aBlob.length());
	 					  image= new Image(is);
	 						studphoto.setImage(image);
	 						/*
	 						 * Save this image temp
	 						 */
	 						saveImage(image);
	 					  gend = rs.getString("Gender");
	 					 dob = rs.getString("dob");
	 					 admit = rs.getString("admissionYear");
	 					 parts = dob.split("-");
	 					 parts2 = admit.split("-");
	 				//	 System.out.println("EMBU:"+parts[0]);
	 					 adm =  rs.getDate("admissionYear");
	 					  Pname = rs.getString("sponsorName");
	 					  parsex = rs.getString("sponsorSex");
	 					 Pphon = rs.getString("sponsorPhone");
	 					Pmail = rs.getString("sponsorMail");
	 					Padre = rs.getString("sponsorAddress");
	 					Ploc = rs.getString("sponsorLocation");
	 					Poc = rs.getString("occupation");
	 					Pnat = rs.getString("nationality");
	 					 stuid.setText(id);	
	 					stufname.setText(fname);
	 					stumname.setText(mname); stulname.setText(lname); 
	 					if(gend.equals("MALE")){
	 						stumale.setSelected(true);
	 						stufemale.setSelected(false);
	 					}else if(gend.equals("FEMALE")){
	 						stufemale.setSelected(true);
	 						stumale.setSelected(false);
	 					}
	 					
	 					studob.setValue(LocalDate.of(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
	 					studclass.setValue(sclac);
	 					studadmitted.setValue(LocalDate.of(Integer.parseInt(parts2[0]),Integer.parseInt(parts2[1]), Integer.parseInt(parts2[2])));;
	 					/*parname.setText(parnam);
	 					parphone.setText(paphone);
	 					parmail.setText(pamail);*/
	 					
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
		
	});
	}
	static String getID(){
		return stID;
	}
	static String getName(){
		return Pname;
	}
	static String getPhone(){
		return Pphon;
	}
	static String getMail(){
		return Pmail;
	}
	static String getLoc(){
		return Ploc;	
	}
	static String getAdr(){
		return Padre;
	}
	static String getOc(){
		return Poc;
	}
	static String getNat(){
		return Pnat;
	}
    @FXML
    void parentData(ActionEvent event) {
    String input=null;
    	try{
    		input = stID;
    		if(input.length() != 0){
    		
    	    //	parID = mzaz+"_"+mzphon+"_"+mzmail+"_"+mzaddress+"_"+mzlocation+"_"+input+"_"+mzkaz+"_"+mztaif;
    	    
    			System.out.println(parID);
    	    	AnchorPane infopane = null;
    	    	try{
    	    		FXMLLoader loader = new FXMLLoader();
    				loader.setLocation(getClass().getResource("Parent.fxml"));
    				 infopane = loader.load();
    	    	}catch(Exception e){ 
    	    		e.printStackTrace();
    	    	}

    			/*
    			 * 
    			 */	JFXDialogLayout content = new JFXDialogLayout();
    		    	content.setHeading(new Text("Parent/guardian details"));
    		    	content.setBody(infopane);
    		    	content.setStyle("-fx-background-color: #84C7D2");
    		    	
    		    	JFXDialog dialog = new JFXDialog(parpop,content,JFXDialog.DialogTransition.TOP);
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
    	    	}else{
    	    		
    	    	}
    	}catch(Exception e){
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.WARNING);
		       tray.setTitle("Select student in a table first");
		       tray.setMessage("this action cannot be parformed with NULL choice");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    
    }
	@SuppressWarnings("unchecked")
	private void fillClass() {

    	list.clear();
		list.add("Choose Admitted class Here");
		studclass.setValue("Choose Admitted class Here");
    	
		wd = new WorkIndicatorDialog(null, "");
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
	 				   String query = "SELECT className FROM classes ";
	 				   rs=st.executeQuery(query);
	 				   	while(rs.next()){
	 				   		String nam = rs.getString("className");
	 				   	list.add(nam);
	 				   	}
	 				   	studclass.setItems(list);
	 				   	rs.close();
	 				   	st.close();
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
	@FXML
    void fetchStudent() {
    	for(int i=0; i<tableview.getItems().size(); i++){
    		
    		tableview.getItems().clear();
    	}
    	wd = new WorkIndicatorDialog(null, "Loading students...");
 	   wd.addTaskEndNotification(result -> {
 		  String outres = result.toString();
          // System.out.println("nomaa "+outres);
           if(outres.equals("1")){
        	   studentlist = FXCollections.observableList(listofstud);
        	   tableview.setItems(studentlist);
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
 				   String query = "SELECT student_id,firstName,middleName,lastName,studentClass FROM students WHERE "
 				   		+ "NOT (status='TRANSFERRED' OR status='DISABLED') ";
 				   rs=st.executeQuery(query);
 				   while(rs.next()){
 					   String id,fname,mname,lname,sclac;
 					 
 				
 					   id = rs.getString("student_id");
 					   fname = rs.getString("firstName");
 					   mname = rs.getString("middleName");
 					   lname =  rs.getString("lastName");
 					   sclac = rs.getString("studentClass");
 					   
 				// tableview.getItems().add(new Student(id,fname,mname, lname, sclac));
 			listofstud.add(new Student(id,fname,mname, lname, sclac));	
 				
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
    void attachPoto(ActionEvent event) {
    	flc  = new FileChooser();
    	selectedFile = flc.showOpenDialog(null);
    	if(selectedFile != null){
    	
    		try {
    			input = new FileInputStream(selectedFile);
				image= new Image(input);

				studphoto.setImage(image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
    	}
    }
    @FXML
    void goActive(ActionEvent event) {
 /*   	BorderPane infopane = null;
    	try{
    		FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Activate.fxml"));
			 infopane = loader.load();
    	}catch(Exception e){ 
    		e.printStackTrace();
    	}
*/
		/*
		 * 
		 */	
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(20, 150, 10, 10));
    	
    	JFXTextField studID = new JFXTextField();
    	studID.setPromptText("Enter studentID here");
    	studID.setLabelFloat(true);
    	studID.setFocusColor(Color.GREEN);
    	studID.setFont(Font.font("Century Gothic",FontWeight.BOLD,14));
    	studID.setUnFocusColor(Color.BLUEVIOLET);
    	grid.add(studID, 1, 0);
    	
    	JFXDialogLayout content = new JFXDialogLayout();
	    	content.setHeading(new Text("Student Anti-Suspension & Re-turn panel"));
	    	content.setBody(grid);
	    	
	    	JFXDialog dialog = new JFXDialog(stackactivate,content,JFXDialog.DialogTransition.TOP);
	    	JFXButton bt = new JFXButton("Activate");
	    	JFXButton bt1 = new JFXButton("Cancel");
	    	
	    	bt.setOnAction(new EventHandler<ActionEvent>(){
	    		String prodName=null;
				@Override
				public void handle(ActionEvent arg0) {
					String sID;
					try{
						sID = studID.getText();
						
						try{
							con=database.dbconnect() ;
						    st= con.createStatement();
						    String select="SELECT status FROM students WHERE student_id='"+sID+"'" ;
						    rs=st.executeQuery(select);
						    
						    if( rs.next()){
						 
								String status = rs.getString("status");
								if(status.equals("DISABLED") || status.equals("TRANSFERRED")){
				 					prep = (PreparedStatement) con.prepareStatement("UPDATE students SET status = ? WHERE student_id = ? ");
					 				 prep.setString(1,"ACTIVE");
					 				 
									 prep.setString(2,sID);
									   int out=prep.executeUpdate();
									 if(out <= 0){
										 TrayNotification tray = new TrayNotification();
									       tray.setNotificationType(NotificationType.WARNING);
									       tray.setTitle("No disabled/transerred student with given ID");
									       tray.setMessage("This ID is not in the list.");
									       tray.setAnimationType(AnimationType.SLIDE);
									       tray.showAndDismiss(Duration.millis(4000));
									       dialog.close();
										 }else{
											 TrayNotification tray = new TrayNotification();
										       tray.setNotificationType(NotificationType.SUCCESS);
										       tray.setTitle("Student Renewed(activated)");
										       tray.setMessage("This ID is now in the list.");
										       tray.setAnimationType(AnimationType.SLIDE);
										       tray.showAndDismiss(Duration.millis(4000));
										       dialog.close();
										       log.writter("Activate student with ID: "+sID);
										       fetchStudent();
									 }
									 prep.close();
				 				
				 							    	
				 			}else{
				 				TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.WARNING);
							       tray.setTitle("Student with this ID is never suspended");
							       tray.setMessage("Given student is active");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
							       }
						    }else{
						    	TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.WARNING);
							       tray.setTitle("No student with given ID");
							       tray.setMessage("This ID is not in the list.");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
						    }
						    rs.close();
						    st.close();
						    con.close();
						}catch(SQLException e){
							e.printStackTrace();
						}
					}catch(Exception e){
						
					}
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
    void goUpdate(ActionEvent event) {
    	 String id=null,fname=null,mname=null,lname=null,sclac=null,gender=null,parnam=null,parsex=null,paphone=null,pamail=null;
			 Date dOa=null,dOB=null;
			 LocalDate dob=null,admit=null;
			 try{
				 id = stuid.getText().toUpperCase();
				 fname = stufname.getText().toUpperCase();
		    		mname = stumname.getText().toUpperCase();
		    		lname = stulname.getText().toUpperCase();
		    		gender =gend;
		    		dob = studob.getValue();
		    		 System.out.println(dob);
		    		 sclac = studclass.getSelectionModel().getSelectedItem().toString().toUpperCase();
		    		// parnam = parname.getText().toUpperCase();
		    	//	parsex = gendpar;
		    		
		    		admit = studadmitted.getValue();
		    		dOB = Date.valueOf(dob);
		    		dOa = Date.valueOf(admit);
		    		
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
		       if(dOB.equals(null) || dOa.equals(null)){
					  TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.WARNING);
					       tray.setTitle("Please provide Dates");
					       tray.setMessage("Dear User. Date of births/Admission are important.");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
				  }else{
					  if(sclac != "CHOOSE ADMITTED CLASS HERE"){
						  
					 try{ 
						 con= database.dbconnect();
						 prep = (PreparedStatement) con.prepareStatement("UPDATE students SET firstName=?,middleName=?,lastName=?,Gender=?,"
						 		+ "dob=?,studentClass=?,admissionYear=?,photo=?"
					    	  		+ " WHERE student_id=?");
					    		
					    	  prep.setString(1, fname);
					    	  prep.setString(2, mname);
					    	  prep.setString(3, lname);
					    	  prep.setString(4, gender);
					    	  prep.setDate(5, dOB);
					    	  prep.setString(6, sclac);
					    	  prep.setDate(7, dOa);
					    	 
							prep.setBinaryStream(8, (InputStream)input,(int)selectedFile.length());
							  prep.setString(9, id);
							int out=prep.executeUpdate();
							if(out >0){
								 TrayNotification tray = new TrayNotification();
								       tray.setNotificationType(NotificationType.SUCCESS);
								       tray.setTitle("Student Updated successfully");
								       tray.setMessage("You can now find this student in the list..");
								       tray.setAnimationType(AnimationType.SLIDE);
								       tray.showAndDismiss(Duration.millis(4000));
								       log.writter("Update student with ID: "+id);
								       input = null;
								       stuid.setText(null); stufname.setText(null);stulname.setText(null);
								       stumname.setText(null); studob.setValue(null);
								     //  parname.setText(null); parname.setText(null);parmail.setText(null);
								       
							}else{
								TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.ERROR);
							       tray.setTitle("Failure Updating student");
							       tray.setMessage("Make sure to complete the form .");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
							}
							prep.close();
							con.close();
					 }catch(SQLException err){
						 	err.printStackTrace();
						  TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.ERROR);
						       tray.setTitle("Failure Updating student");
						       tray.setMessage("Make sure to select student first");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
					  	
					 }
					  
					  }else{ 
						  TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.ERROR);
					       tray.setTitle("Failure Updating student");
					       tray.setMessage("Choose Admitted class");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
				  	
					  }
				  }	
		       
		       fetchStudent();	 
    }

    @FXML
    void goDelete(ActionEvent event) {
    	String ids;
    	try{
    		ids = stID;
    		if(ids.isEmpty()){
    			TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Select student on the table to delete");
			       tray.setMessage("you cant do this action");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
    		}else{
    			/*
				 * 
				 */	JFXDialogLayout content = new JFXDialogLayout();
			    	content.setHeading(new Text("Are you sure to delete this student?"));
			    	content.setBody(new Text("this student will be removed permanently"));
			    	
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
					prep = (PreparedStatement) con.prepareStatement("DELETE FROM students WHERE student_id = ?");
				//	System.out.println("ID  "+supId);
					prep.setString(1,ids);
					
				int out =	prep.executeUpdate();
						prep.close();
				
					if(out > 0){	
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.SUCCESS);
					       tray.setTitle("STUDENT PARMANENTLY REMOVED");
					       tray.setMessage("Removed student wont be available in the list");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					       log.writter("deleted student with ID: "+ids);
					}else{
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.WARNING);
					       tray.setTitle("STUDENT COULD HAVE NOT BEEN REMOVED");
					       tray.setMessage("something went wrong. please retry");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					}
					prep.close();
					con.close();
				}catch(SQLException err){
					err.printStackTrace();
				}
					/*
					 * 
					 */
		    	fetchStudent();
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
	    	fetchStudent();
		/*
		 * 
		 */
    		}
    	}catch(Exception e){
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Select student on the table to delete");
		       tray.setMessage("you cant do this action");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    	
    }
    @FXML
    void goTransfer(ActionEvent event) {

    	String ids;
    	try{
    		ids = stID;
    		if(ids.isEmpty()){
    			TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Select student on the table to transfer");
			       tray.setMessage("you cant do this action");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
    		}else{
    			/*
				 * 
				 */	JFXDialogLayout content = new JFXDialogLayout();
			    	content.setHeading(new Text("Are you sure to transfer this student?"));
			    	content.setBody(new Text("this student will be removed from list"));
			    	
			    	JFXDialog dialog = new JFXDialog(stacktransfer,content,JFXDialog.DialogTransition.CENTER);
			    	JFXButton bt = new JFXButton("COMMIT");
			    	JFXButton bt1 = new JFXButton("CANCEL");
			    	bt.setOnAction(new EventHandler<ActionEvent>(){
			    		
						@Override
						public void handle(ActionEvent arg0) {
				/*
				 * 
				 */

		  
		    	try{
		    	
					con= database.dbconnect();
					prep = (PreparedStatement) con.prepareStatement("UPDATE students SET status = ? WHERE student_id = ?");
				//	System.out.println("ID  "+supId);
					prep.setString(1,"TRANSFERRED");
					prep.setString(2,ids);
					
				int out =	prep.executeUpdate();
						prep.close();
				
					if(out > 0){	
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.SUCCESS);
					       tray.setTitle("STUDENT TRANSFER SET");
					       tray.setMessage("Transferred student wont be available in the list");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					       log.writter("Transfer student with ID: "+ids);
					}else{
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.WARNING);
					       tray.setTitle("STUDENT COULD HAVE NOT BEEN RETRANSFERRED");
					       tray.setMessage("something went wrong. please retry");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					}
					prep.close();
					con.close();
				}catch(SQLException err){
					err.printStackTrace();
				}
					/*
					 * 
					 */
		    	fetchStudent();
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
	    	fetchStudent();
		/*
		 * 
		 */
    		}
    	}catch(Exception e){
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Select student on the table to delete");
		       tray.setMessage("you cant do this action");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    	
    
    }
    @FXML
    void goSuspend(ActionEvent event) {


    	String ids;
    	try{
    		ids = stID;
    		if(ids.isEmpty()){
    			TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Select student on the table to suspend");
			       tray.setMessage("you cant do this action");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
    		}else{
    			/*
				 * 
				 */	JFXDialogLayout content = new JFXDialogLayout();
			    	content.setHeading(new Text("Are you sure to suspend this student?"));
			    	content.setBody(new Text("this student will be removed from list"));
			    	
			    	JFXDialog dialog = new JFXDialog(stacksuspend,content,JFXDialog.DialogTransition.CENTER);
			    	JFXButton bt = new JFXButton("COMMIT");
			    	JFXButton bt1 = new JFXButton("CANCEL");
			    	bt.setOnAction(new EventHandler<ActionEvent>(){
			    		
						@Override
						public void handle(ActionEvent arg0) {
				/*
				 * 
				 */

		  
		    	try{
		    	
					con= database.dbconnect();
					prep = (PreparedStatement) con.prepareStatement("UPDATE students SET status = ? WHERE student_id = ?");
				//	System.out.println("ID  "+supId);
					prep.setString(1,"DISABLED");
					prep.setString(2,ids);
					
				int out =	prep.executeUpdate();
						prep.close();
				
					if(out > 0){	
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.SUCCESS);
					       tray.setTitle("STUDENT SUSPENSION SET");
					       tray.setMessage("Suspended student wont be available in the list");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					       log.writter("Suspended student with ID: "+ids);
					}else{
						TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.WARNING);
					       tray.setTitle("STUDENT COULD HAVE NOT BEEN SUSPENDED");
					       tray.setMessage("something went wrong. please retry");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					}
					prep.close();
					con.close();
					
				}catch(SQLException err){
					err.printStackTrace();
				}
					/*
					 * 
					 */
		    	fetchStudent();
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
	    	fetchStudent();
		/*
		 * 
		 */
    		}
    	}catch(Exception e){
    		TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("Select student on the table to delete");
		       tray.setMessage("you cant do this action");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    }

    @FXML
    void goOlevel(ActionEvent event) {
    	String ids;
    	
    		ids = stID;
    
    			/*
				 * 
				 */	JFXDialogLayout content = new JFXDialogLayout();
			    	content.setHeading(new Text("Are you sure to Shift OLEVEL classes"));
			    	content.setBody(new Text("all olevel class will shift. eg FORM IA to FORM IIA"));
			    	
			    	JFXDialog dialog = new JFXDialog(stackolevel,content,JFXDialog.DialogTransition.CENTER);
			    	JFXButton bt = new JFXButton("SHIFT FOWARD");
			    	JFXButton bt1 = new JFXButton("SHIFT BACKWARD");
			    	bt.setOnAction(new EventHandler<ActionEvent>(){
			    		
						@SuppressWarnings("unchecked")
						@Override
						public void handle(ActionEvent arg0) {

							
							 wd = new WorkIndicatorDialog(null, "organizing data(S)...");
						 	   wd.addTaskEndNotification(result -> {
						 		  String outres = result.toString();
						          // System.out.println("nomaa "+outres);
						           if(outres.equals("1")){
						        	  
						           }
						 		   wd=null;
						 	   });
						 		 wd.exec("fetch", inputParam -> {
						 			
						 			 try{
											con = database.dbconnect();
											st = con.createStatement();
											
											 String rev = "SELECT DISTINCT currentYr FROM students WHERE studentClass LIKE 'FORM IV%' AND NOT (status='TRANSFERRED' OR status='DISABLED')";
											    rs = st.executeQuery(rev);
											    String reve = null;
											    int revi = 0;
											    revYear=0;
											    while(rs.next()){
											    	reve = rs.getString("currentYr");
											    }
											    revi = Integer.parseInt(reve);
											 //   JOptionPane.showMessageDialog(null, revi);
											   revYear =  revi - 1;
											rs.close();
											st.close();
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
						
							/*
							 * NYUMA
							 */
						 		 wd = new WorkIndicatorDialog(null, "changing classes...");
							 	   wd.addTaskEndNotification(result -> {
							 		  String outres = result.toString();
							          // System.out.println("nomaa "+outres);
							           if(outres.equals("1")){
							        	   log.writter("Shift forward oleval students");
							        	   TrayNotification tray = new TrayNotification();
									       tray.setNotificationType(NotificationType.NOTICE);
									       tray.setTitle("All olevel students are in new classes");
									       tray.setMessage("you can only reverse this action once, in BACKWARD CHANGE");
									       tray.setAnimationType(AnimationType.SLIDE);
									       tray.showAndDismiss(Duration.millis(4000));
									       dialog.close();
											fetchStudent();
							           }else if(outres.equals("2")){
							        	   TrayNotification tray = new TrayNotification();
									       tray.setNotificationType(NotificationType.ERROR);
									       tray.setTitle("something went wrong, retry");
									       tray.setMessage("you cant do this action");
									       tray.setAnimationType(AnimationType.SLIDE);
									       tray.showAndDismiss(Duration.millis(4000));
							           }
							 		   wd=null;
							 	   }); 
							 		 wd.exec("fetch", inputParam -> {
							 			try{
											con = database.dbconnect();
										 	st = con.createStatement();
													for(int i=0; i<tableview.getItems().size(); i++){
									    		String ids = tableview.getItems().get(i).getId();
									    		String sclas = tableview.getItems().get(i).getClas();
									    		//	System.out.println(ids);
												
									 		   // JOptionPane.showMessageDialog(null,myId+" "+sclass);
											
											    
									 		   if(sclas.equals("FORM IA")){
									 			   String sql = "UPDATE students SET studentClass='FORM IIA',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  // JOptionPane.showMessageDialog(null,ids+" "+sclass);
									 		   }else  if(sclas.equals("FORM IB")){
									 			   String sql = "UPDATE students SET studentClass='FORM IIB',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IC")){
									 			   String sql = "UPDATE students SET studentClass='FORM IIC',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM ID")){
									 			   String sql = "UPDATE students SET studentClass='FORM IID',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IIA")){
									 			   String sql = "UPDATE students SET studentClass='FORM IIIA',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IIB")){
									 			   String sql = "UPDATE students SET studentClass='FORM IIIB',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IIC")){
									 			   String sql = "UPDATE students SET studentClass='FORM IIIC',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IID")){
									 			   String sql = "UPDATE students SET studentClass='FORM IIID',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IIIA")){
									 			   String sql = "UPDATE students SET studentClass='FORM IVA',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IIIB")){
									 			   String sql = "UPDATE students SET studentClass='FORM IVB',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IIIC")){
									 			   String sql = "UPDATE students SET studentClass='FORM IVC',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IIID")){
									 			   String sql = "UPDATE students SET studentClass='FORM IVD',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IVA")){
									 			   String sql = "UPDATE students SET studentClass='GRAD-IVA',status='COMPLETE' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IVB")){
									 			   String sql = "UPDATE students SET studentClass='GRAD-IVB',status='COMPLETE' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IVC")){
									 			   String sql = "UPDATE students SET studentClass='GRAD-IVC',status='COMPLETE' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("FORM IVD")){
									 			   String sql = "UPDATE students SET studentClass='GRAD-IVD',status='COMPLETE' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("PRE-FORM IA")){
									 			   String sql = "UPDATE students SET studentClass='FORM IA' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("PRE-FORM IB")){
									 			   String sql = "UPDATE students SET studentClass='FORM IB' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("PRE-FORM IC")){
									 			   String sql = "UPDATE students SET studentClass='FORM IC' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("PRE-FORM ID")){
									 			   String sql = "UPDATE students SET studentClass='FORM ID' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("GRAD-IVA")){
									 			   String sql = "UPDATE students SET studentClass='COMPLETED IVA',status='COMPLETE' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("GRAD-IVB")){
									 			   String sql = "UPDATE students SET studentClass='COMPLETED IVB',status='COMPLETE' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("GRAD-IVC")){
									 			   String sql = "UPDATE students SET studentClass='COMPLETED IVC',status='COMPLETE' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }else  if(sclas.equals("GRAD-IVD")){
									 			   String sql = "UPDATE students SET studentClass='COMPLETED IVD',status='COMPLETE' WHERE student_id='"+ids+"'";
									 			   st.executeUpdate(sql);
									 			  
									 		   }
									 		//  log.writter("Shift forward oleval students");
									 		   a=1;
													}
													st.close();
													con.close();
											}catch(SQLException err){
												err.printStackTrace();
												a=2;
											}
							 			
								               try {
								                  Thread.sleep(1000);
								               }	
								               catch (InterruptedException er) {
								                  er.printStackTrace();
								               }
								           
								           return new Integer(a);
								           
								           
								        });
		
		 
		    	
		    	
				}
	    		
	    	});
	    	bt1.setOnAction(new EventHandler<ActionEvent>(){

				@SuppressWarnings("unchecked")
				@Override
				public void handle(ActionEvent arg0) {
					
					 wd = new WorkIndicatorDialog(null, "organizing data(S)...");
				 	   wd.addTaskEndNotification(result -> {
				 		  String outres = result.toString();
				          // System.out.println("nomaa "+outres);
				           if(outres.equals("1")){
				        	  
				           }
				 		   wd=null;
				 	   });
				 		 wd.exec("fetch", inputParam -> {
				 			
				 			 try{
									con = database.dbconnect();
									st = con.createStatement();
									
									 String rev = "SELECT DISTINCT currentYr FROM students WHERE studentClass LIKE 'FORM IV%' AND NOT (status='TRANSFERRED' OR status='DISABLED')";
									    rs = st.executeQuery(rev);
									    String reve = null;
									    int revi = 0;
									    revYear=0;
									    while(rs.next()){
									    	reve = rs.getString("currentYr");
									    }
									    revi = Integer.parseInt(reve);
									 //   JOptionPane.showMessageDialog(null, revi);
									   revYear =  revi - 1;
									rs.close();
									st.close();
									con.close();
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
				
					/*
					 * NYUMA
					 */
				 		 wd = new WorkIndicatorDialog(null, "changing classes...");
					 	   wd.addTaskEndNotification(result -> {
					 		  String outres = result.toString();
					          // System.out.println("nomaa "+outres);
					           if(outres.equals("1")){
					        	   log.writter("Shift backward oleval students");
					        	   TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.NOTICE);
							       tray.setTitle("All olevel students are in new classes");
							       tray.setMessage("you can only reverse this action once, in FORWARD CHANGE");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
							       dialog.close();
									fetchStudent();
					           }else if(outres.equals("2")){
					        	   TrayNotification tray = new TrayNotification();
							       tray.setNotificationType(NotificationType.ERROR);
							       tray.setTitle("something went wrong, retry");
							       tray.setMessage("you cant do this action");
							       tray.setAnimationType(AnimationType.SLIDE);
							       tray.showAndDismiss(Duration.millis(4000));
					           }
					 		   wd=null;
					 	   }); 
					 		 wd.exec("fetch", inputParam -> {
					 			try{
					 				con = database.dbconnect();
									 st = con.createStatement();
									for(int i=0; i<tableview.getItems().size(); i++){
							    		String ids = tableview.getItems().get(i).getId();
						    		String sclas = tableview.getItems().get(i).getClas();
						    			

										
							 		   // JOptionPane.showMessageDialog(null,myId+" "+sclass);
									
									    

												
												   // JOptionPane.showMessageDialog(null,myId+" "+sclass);
												   
												   if(sclas.equals("FORM IIA")){
													   String sql = "UPDATE students SET studentClass='FORM IA',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													 
												   }else  if(sclas.equals("FORM IIB")){
													   String sql = "UPDATE students SET studentClass='FORM IB',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IIC")){
													   String sql = "UPDATE students SET studentClass='FORM IC',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IID")){
													   String sql = "UPDATE students SET studentClass='FORM ID',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IIIA")){
													   String sql = "UPDATE students SET studentClass='FORM IIA',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IIIB")){
													   String sql = "UPDATE students SET studentClass='FORM IIB',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IIIC")){
													   String sql = "UPDATE students SET studentClass='FORM IIC',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IIID")){
													   String sql = "UPDATE students SET studentClass='FORM IID',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IVA")){
													   String sql = "UPDATE students SET studentClass='FORM IIIA',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IVB")){
													   String sql = "UPDATE students SET studentClass='FORM IIIB',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IVC")){
													   String sql = "UPDATE students SET studentClass='FORM IIIC',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IVD")){
													   String sql = "UPDATE students SET studentClass='FORM IIID',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-IVA")){
													   String sql = "UPDATE students SET studentClass='FORM IVA',status='ACTIVE',currentYr = '"+revYear+"' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-IVB")){
													   String sql = "UPDATE students SET studentClass='FORM IVB',status='ACTIVE',currentYr = '"+revYear+"' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-IVC")){
													   String sql = "UPDATE students SET studentClass='FORM IVC',status='ACTIVE',currentYr = '"+revYear+"' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-IVD")){
													   String sql = "UPDATE students SET studentClass='FORM IVD',status='ACTIVE',currentYr = '"+revYear+"' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else if(sclas.equals("FORM IA")){
													   String sql = "UPDATE students SET studentClass='PRE-FORM IA' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													 
												   }else  if(sclas.equals("FORM IB")){
													   String sql = "UPDATE students SET studentClass='PRE-FORM IB' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM IC")){
													   String sql = "UPDATE students SET studentClass='PRE-FORM IC' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM ID")){
													   String sql = "UPDATE students SET studentClass='PRE-FORM ID' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }
												   
												   a=1;
												}
									//s=1;
										st.close();
										con.close();
									    	}catch(SQLException err){
										err.printStackTrace();
										a=2;
									}
					 			
						               try {
						                  Thread.sleep(1000);
						               }	
						               catch (InterruptedException er) {
						                  er.printStackTrace();
						               }
						           
						           return new Integer(a);
						           
						           
						        });
				
				}
	    	});
	    	content.setActions(bt,bt1);
	    	dialog.show();
	    	fetchStudent();
		/*
		 * 
		 */
    		
    	
    
    }
    @FXML
    void goAlevel(ActionEvent event) {

    	String ids;
    	
    		ids = stID;
    
    			/*
				 * 
				 */	JFXDialogLayout content = new JFXDialogLayout();
			    	content.setHeading(new Text("Are you sure to Shift ALEVEL classes"));
			    	content.setBody(new Text("all olevel class will shift. eg FORM V-CBN to FORM VI-CBN"));
			    	
			    	JFXDialog dialog = new JFXDialog(stackalevel,content,JFXDialog.DialogTransition.CENTER);
			    	JFXButton bt = new JFXButton("SHIFT FOWARD");
			    	JFXButton bt1 = new JFXButton("SHIFT BACKWARD");
			    	bt.setOnAction(new EventHandler<ActionEvent>(){
			    		
						@SuppressWarnings("unchecked")
						@Override
						public void handle(ActionEvent arg0) {
				/*
				 *MBELE 
				 */
					 		 wd = new WorkIndicatorDialog(null, "changing classes...");
						 	   wd.addTaskEndNotification(result -> {
						 		  String outres = result.toString();
						          // System.out.println("nomaa "+outres);
						           if(outres.equals("1")){
						        	   log.writter("Shift forward advance students");
						        	   TrayNotification tray = new TrayNotification();
								       tray.setNotificationType(NotificationType.NOTICE);
								       tray.setTitle("All Advance students are in new classes");
								       tray.setMessage("you can only reverse this action once, in FORWARD CHANGE");
								       tray.setAnimationType(AnimationType.SLIDE);
								       tray.showAndDismiss(Duration.millis(4000));
								       dialog.close();
										fetchStudent();
						           }else if(outres.equals("2")){
						        	   TrayNotification tray = new TrayNotification();
								       tray.setNotificationType(NotificationType.ERROR);
								       tray.setTitle("something went wrong, retry");
								       tray.setMessage("you cant do this action");
								       tray.setAnimationType(AnimationType.SLIDE);
								       tray.showAndDismiss(Duration.millis(4000));
						           }
						 		   wd=null;
						 	   }); 
						 	  wd.exec("fetch", inputParam -> {
						 			try{
						 				con = database.dbconnect();
						 				st = con.createStatement();
										for(int i=0; i<tableview.getItems().size(); i++){
								    		String ids = tableview.getItems().get(i).getId();
								    		String sclas = tableview.getItems().get(i).getClas();
							    		
													   if(sclas.equals("FORM V-CBG")){
														   String sql = "UPDATE students SET studentClass='FORM VI-CBG',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  // JOptionPane.showMessageDialog(null,ids+" "+sclas);
													   }else  if(sclas.equals("FORM V-HGE")){
														   String sql = "UPDATE students SET studentClass='FORM VI-HGE',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM V-HGL")){
														   String sql = "UPDATE students SET studentClass='FORM VI-HGL',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM V-HKL")){
														   String sql = "UPDATE students SET studentClass='FORM VI-HKL',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM V-PCB")){
														   String sql = "UPDATE students SET studentClass='FORM VI-PCB',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM V-CBN")){
														   String sql = "UPDATE students SET studentClass='FORM VI-CBN',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }
													   else  if(sclas.equals("FORM VI-CBG")){
														   String sql = "UPDATE students SET studentClass='GRAD-VICBG',status='COMPLETE' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM VI-HGE")){
														   String sql = "UPDATE students SET studentClass='GRAD-VIHGE',status='COMPLETE' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM VI-HGL")){
														   String sql = "UPDATE students SET studentClass='GRAD-VIHGL',status='COMPLETE' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM VI-HKL")){
														   String sql = "UPDATE students SET studentClass='GRAD-VIHKL',status='COMPLETE' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM VI-PCB")){
														   String sql = "UPDATE students SET studentClass='GRAD-VIPCB',status='COMPLETE' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }// additional classes
													   else  if(sclas.equals("FORM V-EGM")){
														   String sql = "UPDATE students SET studentClass='FORM VI-EGM',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM V-PCM")){
														   String sql = "UPDATE students SET studentClass='FORM VI-PCM',currentYr = currentYr + 1 WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM VI-EGM")){
														   String sql = "UPDATE students SET studentClass='GRAD-VIEGM' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM VI-PCM")){
														   String sql = "UPDATE students SET studentClass='GRAD-VIPCM' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("FORM VI-CBN")){
														   String sql = "UPDATE students SET studentClass='GRAD-VICBN' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("PRE-VCBG")){
														   String sql = "UPDATE students SET studentClass='FORM V-CBG' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("PRE-VHGE")){
														   String sql = "UPDATE students SET studentClass='FORM V-HGE' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("PRE-VHGL")){
														   String sql = "UPDATE students SET studentClass='FORM V-HGE' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("PRE-VHKL")){
														   String sql = "UPDATE students SET studentClass='FORM V-HKL' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("PRE-VPCB")){
														   String sql = "UPDATE students SET studentClass='FORM V-PCB' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("PRE-VEGM")){
														   String sql = "UPDATE students SET studentClass='FORM V-EGM' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("PRE-VPCM")){
														   String sql = "UPDATE students SET studentClass='FORM V-PCM' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("PRE-VCBN")){
														   String sql = "UPDATE students SET studentClass='FORM V-CBN' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("GRAD-VICBG")){
														   String sql = "UPDATE students SET studentClass='COMPLETED-CBG' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("GRAD-VIHGE")){
														   String sql = "UPDATE students SET studentClass='COMPLETED-HGE' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("GRAD-VIHGL")){
														   String sql = "UPDATE students SET studentClass='COMPLETED-HGL' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("GRAD-VIHKL")){
														   String sql = "UPDATE students SET studentClass='COMPLETED-HKL' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("GRAD-VIPCB")){
														   String sql = "UPDATE students SET studentClass='COMPLETED-PCB' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("GRAD-VIPCM")){
														   String sql = "UPDATE students SET studentClass='COMPLETED-PCM' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("GRAD-VIEGM")){
														   String sql = "UPDATE students SET studentClass='COMPLETED-EGM' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }else  if(sclas.equals("GRAD-VICBN")){
														   String sql = "UPDATE students SET studentClass='COMPLETED-CBN' WHERE student_id='"+ids+"'";
														   st.executeUpdate(sql);
														  
													   }
												a=1;
										}
												st.close();
												con.close();
													}catch(SQLException err){
											err.printStackTrace();
											a=2;
										}
						 			
							               try {
							                  Thread.sleep(1000);
							               }	
							               catch (InterruptedException er) {
							                  er.printStackTrace();
							               }
							           
							           return new Integer(a);
							           
							           
							        });
					
		    /*	fetchStudent();
		    	dialog.close();*/
		    	
		    	
				}
	    		
	    	});
	    	bt1.setOnAction(new EventHandler<ActionEvent>(){

				@SuppressWarnings("unchecked")
				@Override
				public void handle(ActionEvent arg0) {
					// int revYear = 0;
					 wd = new WorkIndicatorDialog(null, "organizing data(S)...");
				 	   wd.addTaskEndNotification(result -> {
				 		  String outres = result.toString();
				          // System.out.println("nomaa "+outres);
				           if(outres.equals("1")){
				        	  
				           }
				 		   wd=null;
				 	   });
				 		 wd.exec("fetch", inputParam -> {
				 			revYear=0;
				 			 try{
				 				 con = database.dbconnect();
				 				 st = con.createStatement();
								 String rev = "SELECT DISTINCT currentYr FROM students WHERE studentClass LIKE 'FORM V%' AND NOT (status='TRANSFERRED' OR status='DISABLED')";
								    rs = st.executeQuery(rev);
								    String reve = null;
								    int revi = 0;
								   
								    while(rs.next()){
								    	reve = rs.getString("currentYr");
								    }
								    revi = Integer.parseInt(reve);
								 //   JOptionPane.showMessageDialog(null, revi);
								   revYear =  revi - 1;
								st.close();
								con.close();
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
					 
					/*
					 * NYUMA
					 */ wd = new WorkIndicatorDialog(null, "changing classes...");
				 	   wd.addTaskEndNotification(result -> {
				 		  String outres = result.toString();
				          // System.out.println("nomaa "+outres);
				           if(outres.equals("1")){
				        	   log.writter("Shift backward advance students");
				        	   TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.NOTICE);
						       tray.setTitle("All Advance students are in new classes");
						       tray.setMessage("you can only reverse this action once, in FORWARD CHANGE");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
						       dialog.close();
								fetchStudent();
				           }else if(outres.equals("2")){
				        	   TrayNotification tray = new TrayNotification();
						       tray.setNotificationType(NotificationType.ERROR);
						       tray.setTitle("something went wrong, retry");
						       tray.setMessage("you cant do this action");
						       tray.setAnimationType(AnimationType.SLIDE);
						       tray.showAndDismiss(Duration.millis(4000));
				           }
				 		   wd=null;
				 	   }); 
				 	  wd.exec("fetch", inputParam -> {
				 			try{
				 				con = database.dbconnect();
				 				st = con.createStatement();
						 		 for(int i=0; i<tableview.getItems().size(); i++){
							    		String ids = tableview.getItems().get(i).getId();
						    		String sclas = tableview.getItems().get(i).getClas();
						    		
												   if(sclas.equals("FORM VI-CBG")){
													   String sql = "UPDATE students SET studentClass='FORM V-CBG',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													 
												   }else  if(sclas.equals("FORM VI-HGE")){
													   String sql = "UPDATE students SET studentClass='FORM V-HGE',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM VI-HGL")){
													   String sql = "UPDATE students SET studentClass='FORM V-HGL',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM VI-HKL")){
													   String sql = "UPDATE students SET studentClass='FORM V-HKL',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM VI-PCB")){
													   String sql = "UPDATE students SET studentClass='FORM V-PCB',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM VI-PCM")){
													   String sql = "UPDATE students SET studentClass='FORM V-PCM',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM VI-EGM")){
													   String sql = "UPDATE students SET studentClass='FORM V-EGM',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM VI-CBN")){
													   String sql = "UPDATE students SET studentClass='FORM V-CBN',currentYr = currentYr - 1 WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-VICBG")){
													   String sql = "UPDATE students SET studentClass='FORM VI-CBG' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-VIHGE")){
													   String sql = "UPDATE students SET studentClass='FORM VI-HGE' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-VIHGL")){
													   String sql = "UPDATE students SET studentClass='FORM VI-HGL' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-VIHKL")){
													   String sql = "UPDATE students SET studentClass='FORM VI-HKL' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-VIPCB")){
													   String sql = "UPDATE students SET studentClass='FORM VI-PCB' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-VIPCM")){
													   String sql = "UPDATE students SET studentClass='FORM VI-PCM' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-VIEGM")){
													   String sql = "UPDATE students SET studentClass='FORM VI-EGM' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("GRAD-VICBN")){
													   String sql = "UPDATE students SET studentClass='FORM VI-CBN' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM V-CBG")){
													   String sql = "UPDATE students SET studentClass='PRE-VCBG' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM V-HGE")){
													   String sql = "UPDATE students SET studentClass='PRE-VHGE' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM V-HGL")){
													   String sql = "UPDATE students SET studentClass='PRE-VHGL' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM V-HKL")){
													   String sql = "UPDATE students SET studentClass='PRE-VHKL' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM V-PCB")){
													   String sql = "UPDATE students SET studentClass='PRE-VPCB' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM V-EGM")){
													   String sql = "UPDATE students SET studentClass='PRE-VEGM' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM V-PCM")){
													   String sql = "UPDATE students SET studentClass='PRE-VPCM' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }else  if(sclas.equals("FORM V-CBN")){
													   String sql = "UPDATE students SET studentClass='PRE-VCBN' WHERE student_id='"+ids+"'";
													   st.executeUpdate(sql);
													  
												   }
							 		a=1;
						 		 } 
						 		 st.close();
						 		 con.close();
									}catch(SQLException err){
									err.printStackTrace();
									a=2;
								}
				 			
					               try {
					                  Thread.sleep(1000);
					               }	
					               catch (InterruptedException er) {
					                  er.printStackTrace();
					               }
					           
					           return new Integer(a);
					           
					           
					        });
				 	
				}
	    		
	    	});
	    	content.setActions(bt,bt1);
	    	dialog.show();
	    	fetchStudent();
		/*
		 * 
		 */
    		
    	
    
    
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
}
