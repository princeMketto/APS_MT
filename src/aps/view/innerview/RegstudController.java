package aps.view.innerview;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class RegstudController implements Initializable {
	LoggerM log=new LoggerM();
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXTextField kazi;
    @FXML
    private BorderPane borderJuju;

    @FXML
    private JFXTextField parname;

    @FXML
    private RadioButton pamale;

    @FXML
    private ToggleGroup pargender;

    @FXML
    private RadioButton pafemale;

    @FXML
    private JFXTextField paphone;

    @FXML
    private JFXTextField pamail;
   

    @FXML
    private JFXTextField taifa;

    @FXML
    private JFXTextField stufirstname;

    @FXML
    private JFXTextField stumiddlename;
    @FXML
    private StackPane pop;

    @FXML
    private JFXTextField stulastname;

    @FXML
    private RadioButton stumale;

    @FXML
    private ToggleGroup stugender;

    @FXML
    private RadioButton stufemale;

    @FXML
    private JFXDatePicker studob;

    @FXML
    private JFXButton btnphoto,btnsubmit;

    @FXML
    private ImageView stuphoto;

    @FXML
    private JFXTextField StuID;

    @FXML
    private ChoiceBox Stuclass;
    @FXML
    private JFXTextField addres;

    @FXML
    private JFXTextField location;
    @FXML
    private JFXButton btncleanfm;

    @FXML
    private JFXButton btnAttach;

    @FXML
    private Label labfile;

    @FXML
    private JFXButton btnUpload;

    @FXML
    private JFXDatePicker admissiondate;
    private WorkIndicatorDialog wd=null;
	ConnectDB database = new ConnectDB();
	 private Connection con;
	    private ResultSet rs;
	    private Statement st;
	    private PreparedStatement prep;
		  FileInputStream input,input1;
			InputStream is = null;
			Image image=null;
			FileChooser flc,flc1;
			List<String> yumo,succes,empty ;
		    File selectedFile,selectedFile1 ;
		    String gend,gendpar;
		    ObservableList<String>list = FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stumale.setUserData("Male");
		stufemale.setUserData("Female");
		
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
		pamale.setUserData("MALE");
		pafemale.setUserData("FELAME");
		
		pamale.setToggleGroup(pargender);
		pafemale.setToggleGroup(pargender);
		
		pargender.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				  if (pargender.getSelectedToggle() != null && !pargender.getSelectedToggle().equals("Gender") ) {
					  gendpar = pargender.getSelectedToggle().getUserData().toString().toUpperCase();
			          
			         }
				
			}
			
		});
		
		fillClass();
	}

    @SuppressWarnings("unchecked")
	private void fillClass() {
    	list.clear();
		list.add("Choose Admitted class Here");
    	Stuclass.setValue("Choose Admitted class Here");
    	wd = new WorkIndicatorDialog(null, "Preparing Form...");
      	 wd.addTaskEndNotification(result -> {
      		  String outres = result.toString();
      		  if(outres.equals("1")){
      			  
      		  }
   	  
      	
      	    wd=null;
      	 });
      		 wd.exec("fetch", inputParam -> {
   			int z = 0;
   		   	try{
   				con= database.dbconnect();
   				   st= con.createStatement();
   				   String query = "SELECT className FROM classes ";
   				   rs=st.executeQuery(query);
   				   	while(rs.next()){
   				   		String nam = rs.getString("className");
   				   	list.add(nam);
   				   	}
   				   	Stuclass.setItems(list);
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
      	       
      	          
      	        return new Integer(z);
      	        
      	        
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
				stuphoto.setImage(image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
    	}
    }
	  @FXML
	    void attachFile(ActionEvent event) {
	    	flc1  = new FileChooser();
	    	selectedFile1 = flc1.showOpenDialog(null);
	    	if(selectedFile1 != null){
	    	
	    		try {
	    			labfile.setText(selectedFile1.getName());
	    			input1 = new FileInputStream(selectedFile1);
	    			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }

    @FXML
    void setName(MouseEvent event) {
    	parname.setText(stumiddlename.getText().toUpperCase()+" "+stulastname.getText().toUpperCase());
    }
    private void showarn(Text text){
    	AnchorPane infopane = null;
    
	JFXDialogLayout content = new JFXDialogLayout();
	    	content.setHeading(new Text("Registration Form upload with fault."));
	    	content.setBody(text);
	    	content.setStyle("-fx-background-color: #84C7D2");
	    	
	    	JFXDialog dialog = new JFXDialog(pop,content,JFXDialog.DialogTransition.TOP);
	    	JFXButton bt = new JFXButton("Done");
	    	JFXButton bt1 = new JFXButton("Cancel");
	    	bt.setOnAction(new EventHandler<ActionEvent>(){
	    		String prodName=null;
				@Override
				public void handle(ActionEvent arg0) {
		
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
	    
    }
    @SuppressWarnings("unchecked")
	@FXML
    void uploadData(ActionEvent event) {
    	if(selectedFile1!=null){
    	wd = new WorkIndicatorDialog(null, "Registering student(S)...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          System.out.println("nomaa "+empty.size());
	 		  
	           if(succes.size()!=0){
	   
			       JFXSnackbar bar = new JFXSnackbar(anchor);
			     	bar.show("sheet successfully uploaded",8000);
			     	log.writter("Uploaded registration form");
			     	
	           }
	           if(succes.size()!=0&&yumo.size()!=0){
	        	   Text text1 = new Text(""
	        				+"1.students with REGNO:"+yumo+" Do not have unique ID\n"
	        				+ ",Either this student(s) is already registered or use REG NO:\n"
	        				+ "That is assigned to another student. \n"
	        				+ "They are not registered, if they are new in a system, please \n"
	        				+ "provide them with new REG NO. and re upload the form.\n"
	        				+ " MADE CHANGES TO CORRECT INVALIDITY AND RE-UPLOAD THIS FORM.");
	        	   showarn(text1);
	        	   /* JFXSnackbar bar = new JFXSnackbar(anchor);
	        	  
			     	bar.show("students with id: "+yumo+" Does not have unique ID",16000);
			     	log.writter("Uploaded registration form");*/
			     
	           }
	           if(succes.size()==0&&yumo.size()!=0){
			     	  TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.ERROR);
				       tray.setTitle("Registration form contains already registered STUDENTS");
				       tray.setMessage("for new students, please use NEW REGNO,");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(8000));
	           }
	           if(empty.size()!=0){
	        	   Text text2 = new Text(""
	        				+"1.Students with REGNO:"+empty+" Have incomplete information\n"
	        				+ " MADE CHANGES TO CORRECT INVALIDITY AND RE-UPLOAD THIS FORM.");
	        	   showarn(text2);
	           }
	           wd=null;
	 	   });
	 	  wd.exec("fetch", inputParam -> {
	 		  int s=0;
	 	 		try{
	 	 			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	 	 			DataFormatter formatter = new DataFormatter();
					con=database.dbconnect();
		            st= con.createStatement();
		            FileInputStream input = new FileInputStream(selectedFile1);
		            POIFSFileSystem fs = new POIFSFileSystem( input );
		            HSSFWorkbook wb = new HSSFWorkbook(fs);
		            HSSFSheet sheet = wb.getSheetAt(0);
		            HSSFRow row=null;
		            Boolean k=false;
		            yumo = new ArrayList<String>();
		            succes=new ArrayList<String>();
		            empty=new ArrayList<String>();
		            
		          
		           
		        
		           for(int i=12; i<=sheet.getLastRowNum(); i++){
		        	   row = sheet.getRow(i);
		        	   String stuID = null,stuFn,stuMn,stuLn,stuSex,stuClas,parNa,parGen,parPho,parEm = null,
	        		   parAdr = null,parRes = null,parOc = null,parNat = null;
		        	   java.sql.Date stuDob0,stuAdm0;
	        		   int Admyear;
		        	  
		        	   if(row.getCell(0) !=null && row.getCell(1)!=null &&
		        		row.getCell(2)!=null && row.getCell(3)!=null  &&
		        		row.getCell(4)!=null && row.getCell(5)!=null  &&
		        		row.getCell(6)!=null && row.getCell(7)!=null &&
		        		row.getCell(8)!=null && row.getCell(9)!=null  &&
		        		row.getCell(10)!=null&& row.getCell(11)!=null  &&
		        		(row.getCell(12)!=null&& row.getCell(13)!=null  &&row.getCell(14)!=null  && row.getCell(15)!=null ) ){
		        		
		        		 
		        		   stuID = row.getCell(0).getStringCellValue().toUpperCase();
		        		   stuFn = row.getCell(1).getStringCellValue().toUpperCase();
		        		   stuMn = row.getCell(2).getStringCellValue().toUpperCase();
		        		   stuLn = row.getCell(3).getStringCellValue().toUpperCase();
		        		   stuSex = row.getCell(4).getStringCellValue().toUpperCase();
		        		   java.util.Date   stuDo =  row.getCell(5).getDateCellValue();
		        		   stuDob0 = new java.sql.Date(stuDo.getTime());
		        		 
		        		   
		        		   stuClas = row.getCell(6).getStringCellValue().toUpperCase();
		        		   java.util.Date   stuad =  row.getCell(7).getDateCellValue();
                           stuAdm0 =new java.sql.Date(stuad.getTime());
                           String stuA=df.format(stuad);
		        		   String []y2=stuA.split("/");
		        		   Admyear=Integer.parseInt(y2[0]);
		        		   System.out.println(Admyear);
		        		   parNa = row.getCell(8).getStringCellValue().toUpperCase();
		        		   parGen = row.getCell(9).getStringCellValue().toUpperCase();
		        		   parPho ="0"+formatter.formatCellValue(row.getCell(10));
		        		   parEm = row.getCell(11).getStringCellValue().toUpperCase();
			        	   parAdr = row.getCell(12).getStringCellValue().toUpperCase();
			        	   parRes = row.getCell(13).getStringCellValue().toUpperCase();
			        	   parOc = row.getCell(14).getStringCellValue().toUpperCase();
			        	   parNat = row.getCell(15).getStringCellValue().toUpperCase();
			        	   String str ="Select* from  students where student_id='"+stuID+"' ";
			        	 rs=st.executeQuery(str);
			        	  if(rs.next()){
			        		  yumo.add(stuID);
			        		  continue;
			        		  
			        	  }else{
		        
						  prep = (PreparedStatement) con.prepareStatement("INSERT INTO students(student_id,firstName,middleName,lastName,Gender,dob,studentClass,admissionYear,sponsorName,sponsorSex,sponsorPhone,sponsorMail,sponsorAddress,sponsorLocation,occupation,nationality,currentYr,status,photo) VALUES("
							    	  		+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							    	  
						    	 prep.setString(1, stuID);
							    	  prep.setString(2,  stuFn);
							    	  prep.setString(3, stuMn);
							    	  prep.setString(4, stuLn);
							    	  prep.setString(5, stuSex);
							    	  prep.setDate(6, stuDob0);
							    	  prep.setString(7, stuClas);
							    	  prep.setDate(8, stuAdm0);
							    	  prep.setString(9, parNa);
							    	  prep.setString(10, parGen);
							    	  prep.setString(11, parPho);
							    	  prep.setString(12,  parEm);
							    	  prep.setString(13, parAdr);
							    	  prep.setString(14, parRes);
							    	  prep.setString(15,parOc);
							    	  prep.setString(16, parNat);
							    	  prep.setInt(17, Admyear);
							    	  
							    	  prep.setString(18, "ACTIVE");
							    	  File pict =new File("user0.png");
							    	  FileInputStream	inputpic = new FileInputStream(pict);
									prep.setBinaryStream(19, (InputStream)inputpic,(int)pict.length());
									prep.executeUpdate();
									
									succes.add(stuID);
									prep.close();
						    
			        	  }
		        	   
		        	   }else if((row.getCell(0)!=null && row.getCell(1)!=null &&
				        		row.getCell(2)!=null&& row.getCell(3)!=null&&
				        		row.getCell(4)!=null && row.getCell(5)!=null &&
				        		row.getCell(6)!=null&& row.getCell(7)!=null&&
				        		row.getCell(8)!=null && row.getCell(9)!=null&&
				        		row.getCell(10)!=null) && (row.getCell(11)==null ||
				        		row.getCell(12)==null || row.getCell(13)==null ||
				        		row.getCell(14)==null || row.getCell(15)==null) ){
		        		   stuID = row.getCell(0).getStringCellValue();
		        		   stuFn = row.getCell(1).getStringCellValue().toUpperCase();
		        		   stuMn = row.getCell(2).getStringCellValue().toUpperCase();
		        		   stuLn = row.getCell(3).getStringCellValue().toUpperCase();
		        		   stuSex = row.getCell(4).getStringCellValue().toUpperCase();
                           java.util.Date   stuDo =  row.getCell(5).getDateCellValue();
                           stuDob0 = new java.sql.Date(stuDo.getTime());
		        		   stuClas = row.getCell(6).getStringCellValue().toUpperCase();
                           java.util.Date   stuad =  row.getCell(7).getDateCellValue();
                           stuAdm0 =new java.sql.Date(stuad.getTime());
                           String stuA=df.format(stuad);
		        		   String []y2=stuA.split("/");
		        		   Admyear=Integer.parseInt(y2[0]);
		        		   parNa = row.getCell(8).getStringCellValue().toUpperCase();
		        		   parGen = row.getCell(9).getStringCellValue().toUpperCase();
		        		   parPho ="0"+formatter.formatCellValue(row.getCell(10));
		        	       
		        		   if(row.getCell(11)!=null){
		        		   parEm = row.getCell(11).getStringCellValue().toUpperCase();
		        		   }
		        		   if(row.getCell(12)!=null){
		        		   parAdr = row.getCell(12).getStringCellValue().toUpperCase();
		        		   }
		        		   if(row.getCell(13)!=null){
		        		   parRes = row.getCell(13).getStringCellValue().toUpperCase();
		        		   }
		        		   if(row.getCell(14)!=null){
		        		   parOc = row.getCell(14).getStringCellValue().toUpperCase();
		        		   }
		        		   if(row.getCell(15)!=null){
		        		   parNat = row.getCell(15).getStringCellValue().toUpperCase();
		        		   }
		        		   
		        		   String str ="Select* from  students where student_id='"+stuID+"' ";
				        	 rs=st.executeQuery(str);
				        	  if(rs.next()){
				        		 yumo.add(stuID);
				        		 
				        		  continue;
				        		  
				        	  }else{
		        		   

					    	 prep = (PreparedStatement) con.prepareStatement("INSERT INTO students(student_id,firstName,middleName,lastName,Gender,dob,studentClass,admissionYear,sponsorName,sponsorSex,sponsorPhone,sponsorMail,sponsorAddress,sponsorLocation,occupation,nationality,currentYr,status,photo) VALUES("
						    	  		+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						    	  prep.setString(1, stuID);
						    	  prep.setString(2, stuFn);
						    	  prep.setString(3, stuMn);
						    	  prep.setString(4, stuLn);
						    	  prep.setString(5, stuSex);
						    	  prep.setDate(6, stuDob0);
						    	  prep.setString(7, stuClas);
						    	  prep.setDate(8, stuAdm0);
						    	  prep.setString(9, parNa);
						    	  prep.setString(10, parGen);
						    	  prep.setString(11, parPho);
						    	  prep.setString(12, parEm);
						    	  prep.setString(13, parAdr);
						    	  prep.setString(14, parRes);
						    	  prep.setString(15, parOc);
						    	  prep.setString(16, parNat);
						    	  prep.setInt(17, Admyear);
						    	  prep.setString(18, "ACTIVE");
						    	  File pict =new File("user0.png");
						    	  FileInputStream	inputpic = new FileInputStream(pict);
								prep.setBinaryStream(19, (InputStream)inputpic,(int)pict.length());
								prep.executeUpdate();
								
								
								succes.add(stuID);
								       
								
								prep.close();
				        	  }
		        		   
		        	   }else if(row.getCell(0)==null||row.getCell(1)==null||
				        		row.getCell(2)==null||row.getCell(3)==null||
				        		row.getCell(4)==null||row.getCell(5)==null ||
				        		row.getCell(6)==null||row.getCell(7)==null||
				        		row.getCell(8)==null||row.getCell(9)==null||
				        		row.getCell(10)==null){
		        		  
		        		        empty.add(stuID);
		        		        continue;
		        	   }
		        	   
		              
		           }
		          
	 	 		}catch(SQLException e){
	 	 			e.printStackTrace();
	 	 			s=0;
	 	 		}catch(Exception e){
	 	 			e.printStackTrace();
	 	 		}
	 	 		     s=1;
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		             
		          return new Integer(s);
		         
		           
		           
		        });
    	}else{
    		 TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("FILE");
		       tray.setMessage("Attach file please!");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));
    	}
    }
  @SuppressWarnings("unchecked")
	@FXML
  void browseForm(ActionEvent event) {
  		String head = "SCHOOL NAME"; String head1 = " APS EXTERNAL REGISTRATION FORM";
  		String head2 = "YEAR";
  		 wd = new WorkIndicatorDialog(null, "Generating form...");
  		 wd.addTaskEndNotification(result -> {
  			  String outres = result.toString();
  	        // System.out.println("nomaa "+outres);
  	         if(outres.equals("1")){
  	        	
  	      	   open();
  	      	 TrayNotification tray = new TrayNotification();
  		     tray.setNotificationType(NotificationType.SUCCESS);
  		     tray.setTitle("EXTERNAL REGISTRATION FORM CREATED");
  		     tray.setMessage("you can fill the form and upload it at any time");
  		     tray.setAnimationType(AnimationType.SLIDE);
  		     tray.showAndDismiss(Duration.millis(4000));
  	         }else{
  	      	   TrayNotification tray = new TrayNotification();
  				     tray.setNotificationType(NotificationType.ERROR);
  				     tray.setTitle("Could not generate form");
  				     tray.setMessage("please, retry");
  				     tray.setAnimationType(AnimationType.SLIDE);
  				     tray.showAndDismiss(Duration.millis(4000));	 
  	         }
  	         wd=null;
  		   });
  		  wd.exec("fetch", inputParam -> {
  		       
  				  boolean done =false;
  					try{
  						 HSSFWorkbook workbook = new HSSFWorkbook(); 
  					      HSSFSheet spreadsheet = workbook
  					      .createSheet("Student registration form");
  					      HSSFRow row0,row,row1,row2,row3,row4,row5,row6,row7,row8;
  					      HSSFCellStyle RotateStyle = workbook.createCellStyle();
  					      HSSFCellStyle BoldStyle = workbook.createCellStyle();
  					      HSSFCellStyle BoldStyle1 = workbook.createCellStyle();
  					      RotateStyle.setRotation((short)90);
  					      HSSFFont my_font = workbook.createFont();
  					      HSSFFont my_font1 = workbook.createFont();
  					     
  					      Header header = spreadsheet.getHeader();
  					      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)9)+head+"\n"
  					      		+head2.toUpperCase()+ " \n"+head1+".");
  					      Footer footer = spreadsheet.getFooter();
  					      footer.setCenter(HSSFFooter.font("Times New Roman", "Bold")+HSSFFooter.fontSize((short)9)+"*NB: use valid FORMATS.");
  					     
  					      spreadsheet.setColumnWidth(0, 4000);
  					      spreadsheet.setColumnWidth(1, 4000);
  					      spreadsheet.setColumnWidth(2, 4000);
  					      spreadsheet.setColumnWidth(3, 4000);
  					      spreadsheet.setColumnWidth(4, 4000);
  					      spreadsheet.setColumnWidth(5,4000);
  					      spreadsheet.setColumnWidth(6, 4000);
  					      spreadsheet.setColumnWidth(7, 4000);
  					      spreadsheet.setColumnWidth(8, 4000);
  					      spreadsheet.setColumnWidth(9, 4000);
  					      spreadsheet.setColumnWidth(10, 4000);
  					      spreadsheet.setColumnWidth(11, 4000);
  					      spreadsheet.setColumnWidth(12, 4000);
  					      spreadsheet.setColumnWidth(13, 4000);
  					      spreadsheet.setColumnWidth(14, 4000);
  					      spreadsheet.setColumnWidth(15, 4000);
  					      spreadsheet.setColumnWidth(16, 4000);
  					      spreadsheet.setColumnWidth(17, 4000);
  					     
  					      
  					      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
  					      my_font1.setItalic(true);
  					      String str = "SECONDARY SCHOOL";
  					      BoldStyle.setFont(my_font);
  					      BoldStyle1.setFont(my_font1);
  					      RotateStyle.setFont(my_font);
  					      HSSFCell cell,cell0,cell1,cell2,cell3;
  					      
  					      /*
  					       * KEY
  					       */
  					      row0=spreadsheet.createRow(0);
  					         cell0 = row0.createCell(0);
  					         cell0.setCellValue("TO ENTER VALID DATA PLEASE USE THE FOLLOWING FORMAT");
  					         cell0.setCellStyle(BoldStyle);
  					         
  					         row1=spreadsheet.createRow(1);
  					         cell0 = row1.createCell(1);
  					         cell0.setCellValue("REG. NO:- you can use number,character or both. Ex: IRGSS/12/2017");
  					         cell0.setCellStyle(BoldStyle1);
  					         
  					         row2=spreadsheet.createRow(2);
  					         cell0 = row2.createCell(1);
  					         cell0.setCellValue("SEX: must be MALE or FEMALE");
  					         cell0.setCellStyle(BoldStyle1);
  					         
  					         row3=spreadsheet.createRow(3);
  					         cell0 = row3.createCell(1);
  					         cell0.setCellValue("DATE OF BIRTH: YYY/MM/DD");
  					         cell0.setCellStyle(BoldStyle1);
  					         
  					         row4=spreadsheet.createRow(4);
  					         cell0 = row4.createCell(1);
  					         cell0.setCellValue("ADMITTED DATE: YYY/MM/DD");
  					         cell0.setCellStyle(BoldStyle1);
  					        
  					         row5=spreadsheet.createRow(5);
  					         cell0 = row5.createCell(1);
  					         cell0.setCellValue("ADMITTED CLASS: FORM IX,FORM IIX(for olevel), and FORM V-XXX(for a level)");
  					         cell0.setCellStyle(BoldStyle1);
  					         
  					         row6=spreadsheet.createRow(6);
  					         cell0 = row6.createCell(1);
  					         cell0.setCellValue("EMAIL: xxx@xx.xx");
  					         cell0.setCellStyle(BoldStyle1);
  					         
  					        
  					         row7=spreadsheet.createRow(7);
  					         cell2 = row7.createCell(1);
  					         cell2.setCellValue("ADDRESS, LOCATION, OCCUPATION, are not mandatory fields except for PHONE");
  					         cell2.setCellStyle(BoldStyle1);
  					         
  					         row8=spreadsheet.createRow(8);
  					         cell3 = row8.createCell(1);
  					         cell3.setCellValue("WE RECOMMEND YOU TO FILL VALID DATA AS THEY ARE IMPORTANT");
  					         cell3.setCellStyle(BoldStyle1);
  					        
  					 
  					       row=spreadsheet.createRow(11);
  					      cell = row.createCell(0);
  					      cell.setCellValue(str);
  					      //cell.setCellStyle(myStyle);
  					      cell=row.createCell(0);
  					      cell.setCellValue("REG NO.");
  					      cell.setCellStyle(BoldStyle);
  					      cell=row.createCell(1);
  					      cell.setCellValue("FIRST NAME");
  					      cell.setCellStyle(BoldStyle);
  					      cell=row.createCell(2);
  					      cell.setCellValue("MIDDLE NAME");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(3);
  					      cell.setCellValue("LAST NAME");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(4);
  					      cell.setCellValue("SEX");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(5);
  					      cell.setCellValue("DATE OF BIRTH");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(6);
  					      cell.setCellValue("ADMITTED CLASS");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(7);
  					      cell.setCellValue("ADMITTED DATE");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(8);
  					      cell.setCellValue("PARENT FULL NAME");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(9);
  					      cell.setCellValue("PARENT SEX");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(10);
  					      cell.setCellValue("PHONE");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(11);
  					      cell.setCellValue("EMAIL");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(12);
  					      cell.setCellValue("ADDRESS");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(13);
  					      cell.setCellValue("RESIDENCE");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(14);
  					      cell.setCellValue("OCCUPATION");
  					      cell.setCellStyle(RotateStyle);
  					      cell=row.createCell(15);
  					      cell.setCellValue("NATIONALITY");
  					      cell.setCellStyle(RotateStyle);
  					   
  					      closeform();
  						 FileOutputStream out = new FileOutputStream(
  							      new File("registration_form.xls"));
  							      workbook.write(out);
  							      out.close();
  							      
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
	public void closeform(){

		  try {

				

					Process pk = Runtime
					   .getRuntime()
					   .exec("cmd /c taskkill /f /im excel.exe");
					pk.waitFor();
						
					 Thread.sleep(100);
				

		  	  } catch (Exception ex) {
				ex.printStackTrace();
			  }
	}
	@SuppressWarnings("unchecked")
	public void open(){
		
		 wd = new WorkIndicatorDialog(null, "Opening...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	  
	           }else{
	        	  	 
	           }
	           wd=null;
	 	   });
	 	  wd.exec("fetch", inputParam -> {
	          int s=0;
				
	 		 try {

					if ((new File("registration_form.xls")).exists()) {

						Process p = Runtime
						   .getRuntime()
						   .exec("rundll32 url.dll,FileProtocolHandler registration_form.xls");
						p.waitFor();
							s=1;
					} else {

						JOptionPane.showMessageDialog(null, "File does not exist");

					}

					

			  	  } catch (Exception ex) {
					ex.printStackTrace();
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
  @SuppressWarnings("unchecked")
@FXML
  void submitData(ActionEvent event) {
	  wd = new WorkIndicatorDialog(null, "Registering...");
	   	 wd.addTaskEndNotification(result -> {
	   		  String outres = result.toString();
	   		  if(outres.equals("1")){
	   			TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.SUCCESS);
			       tray.setTitle("Student Registered successfully");
			       tray.setMessage("You can now find this student in the list..");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
		         
			       input = null;
			       addres.setText(""); location.setText(""); paphone.setText(null);
			       StuID.setText(null); stufirstname.setText(null);stulastname.setText(null);
			       stumiddlename.setText(null); //studob.setValue(null);
			       parname.setText(null); parname.setText(null);pamail.setText("");
			       kazi.setText(""); taifa.setText("");
	   		  }if(outres.equals("0")){
	   			TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.ERROR);
			       tray.setTitle("Fill all important details");
			       tray.setMessage("Please Fill the form completely/correctly \n"
			       		+ "");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
	   		  }if(outres.equals("-1")){
	   			TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("Please provide Dates");
			       tray.setMessage("Dear User. Date of births/Admission are important.");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
	   		  }if(outres.equals("-2")){
	   			TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.ERROR);
			       tray.setTitle("Failure Registering student");
			       tray.setMessage("Make sure to complete the form .");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
	   		  }if(outres.equals("-3")){
	   			 TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.ERROR);
			       tray.setTitle("Failure Registering student");
			       tray.setMessage("Make sure to provide a unique StudentID");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
	   		  }if(outres.equals("-4")){
	   			TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.ERROR);
			       tray.setTitle("Failure Registering student");
			       tray.setMessage("Choose Admitted class");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
	   		  }
		  
	   	
	   	    wd=null;
	   	 });
	   		 wd.exec("fetch", inputParam -> {
				int z = 0;
			  	String stuIDs = null,fname= null,mname= null,lname= null,gender= null,dateOb= null,
			  			parentname= null,parsex= null,parentphone= null,parentmail= null,adclass= null,
			  			paradres=null,parloc=null,parkazi=null,nation=null;
			  //	Image img;
			  	  LocalDate dob = null,admit = null;
			  	  Date dateOfB = null,dateOa = null;
			  	try{
			  		stuIDs = StuID.getText().toUpperCase();
			  		fname = stufirstname.getText().toUpperCase();
			  		mname = stumiddlename.getText().toUpperCase();
			  		lname = stulastname.getText().toUpperCase();
			  		gender =gend;
			  		dob = studob.getValue();
			  		
			  		adclass = Stuclass.getSelectionModel().getSelectedItem().toString().toUpperCase();
			  		parentname = parname.getText().toUpperCase();
			  		parsex = gendpar;
			  		parentphone = paphone.getText();
			  		
			  		
			  		admit = admissiondate.getValue();
			  		dateOfB = Date.valueOf(dob);
			  		dateOa = Date.valueOf(admit);
			  		
			  		parentmail = pamail.getText();	
			  		paradres = addres.getText().toUpperCase();	
			  		parkazi = kazi.getText().toUpperCase();
			  		nation = taifa.getText().toUpperCase();
			  		parloc = location.getText().toUpperCase();
			  	 
			  	}catch(Exception e){
			  		e.printStackTrace();
			  	
			  		z=0;
			  	
			  	}

			      try{
				    	if(selectedFile != null){
				    input = new FileInputStream(selectedFile); 
				    	}else{
				    		selectedFile =new File("user0.png");
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
			  		  if(dateOfB.equals(null) || dateOa.equals(null)){
							  z=-1;
						  }else{
							  if(adclass != "CHOOSE ADMITTED CLASS HERE"){
							 try{ 
								 con= database.dbconnect();
								 prep = (PreparedStatement) con.prepareStatement("INSERT INTO students(student_id,firstName,middleName,lastName,Gender,dob,studentClass,admissionYear,sponsorName,sponsorSex,sponsorPhone,sponsorMail,sponsorAddress,sponsorLocation,occupation,nationality,currentYr,status,photo) VALUES("
							    	  		+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							    	  prep.setString(1, stuIDs);
							    	  prep.setString(2, fname);
							    	  prep.setString(3, mname);
							    	  prep.setString(4, lname);
							    	  prep.setString(5, gender);
							    	  prep.setDate(6, dateOfB);
							    	  prep.setString(7, adclass);
							    	  prep.setDate(8, dateOa);
							    	  prep.setString(9, parentname);
							    	  prep.setString(10, parsex);
							    	  prep.setString(11, parentphone);
							    	  prep.setString(12, parentmail);
							    	  prep.setString(13, paradres);
							    	  prep.setString(14, parloc);
							    	  prep.setString(15, parkazi);
							    	  prep.setString(16, nation);
							    	  prep.setInt(17, admit.getYear());
							    	  prep.setString(18, "ACTIVE");
									prep.setBinaryStream(19, (InputStream)input,(int)selectedFile.length());
									int out=prep.executeUpdate();
									if(out >0){
										 z=1;
										 log.writter("Register student with ID: "+stuIDs);      
									}else{
										z=-2;
									}
									prep.close();
							 }catch(SQLException err){
								 	err.printStackTrace();
								 z=-3;
							  	
							 }
							  
							  }else{ 
								  z=-4;
						  	
							  }
						  }
			 //*****************
	   	            try {
	   	               Thread.sleep(1000);
	   	            }	
	   	            catch (InterruptedException er) {
	   	               er.printStackTrace();
	   	            }
	   	       
	   	          
	   	        return new Integer(z);
	   	        
	   	        
	   	     });
		
	  
	
  	
  }
}
   
		        		   
		        		   
		        		  
		        		   
		        		  
		        	