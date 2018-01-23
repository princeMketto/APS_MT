package aps.view.innerview;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import aps.view.ConnectDB;
import aps.view.LoggerM;
import aps.view.WorkIndicatorDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ProgRepController implements Initializable {
	LoggerM log=new LoggerM();
    private WorkIndicatorDialog wd=null;
	String madarasa;
	ConnectDB database = new ConnectDB();
	private Connection con;
    private ResultSet rs,rs4,rs2,rs3;
    private Statement st,st4,st2,st3;
	
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
    private ChoiceBox subjectchoice;

   

    @FXML
    private JFXButton btnsubmit;

    @FXML
    private StackPane stackolevel;

    @FXML
    private JFXButton btnmarks;

    @FXML
    private StackPane stackalevel;

    @FXML
    private JFXButton btnbehavi;

    @FXML
    private TableView<Progress> tableview;

    @FXML
    private TableColumn<Progress, String> idcol;

    @FXML
    private TableColumn<Progress, String> namecol;
    
    @FXML
    private TableColumn<Progress, String> gendercol;
    @FXML
    private TableColumn<Progress, String> classcol;

    @FXML
    private TableColumn<Progress, String> mazocol;

    @FXML
    private TableColumn<Progress, String> midcol;

    @FXML
    private TableColumn<Progress, String> examcol;
    int s = 0;
    
    
    @FXML
    void generatebeha(ActionEvent event) {
    	closeprogressive();
    	String theclass =(String) classchoice.getSelectionModel().getSelectedItem();
    	String head3 =(String) classchoice.getSelectionModel().getSelectedItem();
    	
    	
    	String head1 ="SECONDARY SCHOOL";
    	String head2 ="BEHAVIOUR SHEET";
    	
    	
    	behavior(head1, head2, head3, theclass);
    	log.writter("Generate behaviour sheet for "+theclass);
    
    }

    
    @FXML
    void generatemarks(ActionEvent event) {
    	closeprogressive();
    	String theclass =(String) classchoice.getSelectionModel().getSelectedItem();
    	String head3 =(String) classchoice.getSelectionModel().getSelectedItem();
    	String sub =(String) subjectchoice.getSelectionModel().getSelectedItem();
    	String head1 ="SECONDARY SCHOOL";
    	String head2 ="PROGRESSIVE SHEET FOR "+sub;
    	String type;
    	if(annual.isSelected()){
    		type="Annual";
    	}else{
    		type="Terminal";
    	}
    	FillData(head1, head2, head3, type , theclass);
    	//open();
    	log.writter("Generate progressive sheet for "+theclass+" "+type);
    }
    
    ObservableList<String> classes1 = FXCollections.observableArrayList();
    ObservableList<String> subjects = FXCollections.observableArrayList();
    ObservableList<Progress> searchdata;
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idcol.setCellValueFactory(new PropertyValueFactory<Progress, String>("id"));
		namecol.setCellValueFactory(new PropertyValueFactory<Progress, String>("name"));
		gendercol.setCellValueFactory(new PropertyValueFactory<Progress, String>("gender"));
		classcol.setCellValueFactory(new PropertyValueFactory<Progress, String>("Sclass"));
		mazocol.setCellValueFactory(new PropertyValueFactory<Progress, String>("mazoezi"));
		midcol.setCellValueFactory(new PropertyValueFactory<Progress, String>("midterm"));
		examcol.setCellValueFactory(new PropertyValueFactory<Progress, String>("exam"));
		mazocol.setEditable(true);
		namecol.setSortType(TableColumn.SortType.ASCENDING);
		gendercol.setSortType(TableColumn.SortType.ASCENDING);
		
		   
		    classes1.add("FORM I");
		    classes1.add("FORM II");
		    classes1.add("FORM III");
		    classes1.add("FORM IV");
		    classes1.add("FORM V");
		    classes1.add("FORM VI");
		    classes1.add("Choose class");
		    classchoice.setValue("Choose class");
		    classchoice.setItems(classes1);
		    
		    search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
				
				if (oldValue != null && (newValue.length() < oldValue.length())) {
			            	tableview.setItems(searchdata);
			            }
			            String value = newValue.toLowerCase();
			            ObservableList<Progress> subentries = FXCollections.observableArrayList();

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
		    
		    classchoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

				@Override
				public void changed(ObservableValue<? extends String> arg0, String old, String clas) {
					con=database.dbconnect();
					try {
						st=con.createStatement();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if(clas.equals("FORM I")||clas.equals("FORM II")||clas.equals("FORM III")||clas.equals("FORM IV")){
					subjects.clear();
					subjects.add("Choose Subjects");
					wd = new WorkIndicatorDialog(null, "Loading data...");
				 	   wd.addTaskEndNotification(result -> {
				 		  String outres = result.toString();
				          // System.out.println("nomaa "+outres);
				           if(outres.equals("1")){
				        		subjectchoice.setValue("Choose Subjects");
								subjectchoice.setItems(subjects); 
				           }else{
				        	   
				           }
				           wd=null;
				 	   });
				 	  wd.exec("fetch", inputParam -> {
				           // Thinks to do...
				           // NO ACCESS TO UI ELEMENTS!
			 			
				 		  String select="SELECT * FROM subjects WHERE category='General' OR category='Computer' OR category='Science' OR category='Business'  OR category='Literature' OR category='Religion'";
							try {
								rs=st.executeQuery(select);
								while(rs.next()){
									String sbj=rs.getString("subjectName");
									String code=rs.getString("subjectCode");
									String sbjname=sbj+"-"+code;
									
									subjects.add(sbjname);
									s = 1;
								}
								
							
								
							} catch (SQLException e) {
							
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
				}else if(clas.equals("FORM V")||clas.equals("FORM VI")){
					subjects.clear();
					  subjects.add("Choose Subjects");
					  wd = new WorkIndicatorDialog(null, "Loading data...");
				 	   wd.addTaskEndNotification(result -> {
				 		  String outres = result.toString();
				          // System.out.println("nomaa "+outres);
				           if(outres.equals("1")){
				        	   subjectchoice.setValue("Choose Subjects");
								subjectchoice.setItems(subjects);
				           }else{
				        	   
				           }
				           wd=null;
				 	   });
				 	  wd.exec("fetch", inputParam -> {
				           // Thinks to do...
				           // NO ACCESS TO UI ELEMENTS!
				 		 String select="SELECT * FROM subjects WHERE category='Advance'";
							try {
								rs=st.executeQuery(select);
								while(rs.next()){
									String sbj=rs.getString("subjectName");
									String code=rs.getString("subjectCode");
									String sbjname=sbj+"-"+code;
									
									subjects.add(sbjname);
									s = 1;
								}
								
								
								
							} catch (SQLException e) {
							
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
					 
				}
		}
			});
		
		    
		    subjectchoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
               
				@Override
				public void changed(ObservableValue<? extends String> arg0, String old, String clas) {
					tableview.getItems().clear();
				
					String classes=(String) classchoice.getSelectionModel().getSelectedItem();
					String masomo=(String)  subjectchoice.getSelectionModel().getSelectedItem();
					if(classes.equals("FORM I")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
						madarasa="(studentClass='FORM IA' OR studentClass='FORM IB' OR studentClass='FORM IC' OR studentClass='FORM ID' OR studentClass='FORM IE' OR studentClass='FORM IF' OR studentClass='FORM IG' OR studentClass='FORM IH')";
						studentquery();
					}else if(classes.equals("FORM II")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
						madarasa="(studentClass='FORM IIA' OR studentClass='FORM IIB' OR studentClass='FORM IIC' OR studentClass='FORM IID' OR studentClass='FORM IIE' OR studentClass='FORM IIF' OR studentClass='FORM IIG' OR studentClass='FORM IIH')";
						studentquery();
					}else if(classes.equals("FORM III")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
						madarasa="(studentClass LIKE 'FORM III%')";
						studentquery();
					}else if(classes.equals("FORM IV")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
						madarasa="(studentClass LIKE 'FORM IV%')";
						studentquery();
					}else if(classes.equals("FORM V")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
						madarasa="(studentClass LIKE 'FORM V-%')";
						studentquery();
					}else if(classes.equals("FORM VI")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
						madarasa="(studentClass LIKE 'FORM VI-%')";
						studentquery();
					}else if(terminal.isSelected()==false&&annual.isSelected()==false){
						 TrayNotification tray = new TrayNotification();
					     tray.setNotificationType(NotificationType.ERROR);
					     tray.setTitle("Report");
					     tray.setMessage("Select Annual or Terminal please!");
					     tray.setAnimationType(AnimationType.SLIDE);
					     tray.showAndDismiss(Duration.millis(4000));
					}
					
				}
		    	
		    });    
		    
	}
	
		@SuppressWarnings("unchecked")
		public void studentquery(){
			
		  	String std="select* from students where "+madarasa+" and NOT status='DISABLED' and NOT status='TRANSFERRED'";
		  	 wd = new WorkIndicatorDialog(null, "Loading data...");
		 	   wd.addTaskEndNotification(result -> {
		 		  String outres = result.toString();
		          // System.out.println("nomaa "+outres);
		           if(outres.equals("1")){
		        	   tableview.getSortOrder().add(gendercol);
		        	   tableview.getSortOrder().add(namecol);
		        	   
		   			   
		           }else{
		        	   TrayNotification tray = new TrayNotification();
					     tray.setNotificationType(NotificationType.ERROR);
					     tray.setTitle("Report");
					     tray.setMessage("No registered students for selected class");
					     tray.setAnimationType(AnimationType.SLIDE);
					     tray.showAndDismiss(Duration.millis(4000));	 
		           }
		           wd=null;
		 	   });
		 	  wd.exec("fetch", inputParam -> {
		           // Thinks to do...
		           // NO ACCESS TO UI ELEMENTS!
		 		  	try {
				   		st=con.createStatement();
				   	Boolean succ=false;
						rs=st.executeQuery(std);
				
					while(rs.next()){
						String gender = null;
						 succ=true;
						 String stuid=rs.getString("student_id");
						 String fname=rs.getString("firstName");
						 String lname=rs.getString("lastName");
						 String mname=rs.getString("middleName");
						 String g=rs.getString("Gender");
						 if(g.equals("MALE")){
							 gender="M"; 
						 }else if(g.equals("FEMALE")){
							 gender="F";
						 }
						 String clase=rs.getString("studentClass");
						 String name=fname+" "+mname+" "+lname;
						 tableview.getItems().add(new Progress(stuid,name,gender,clase,"","",""));
					}
					
					searchdata =  tableview.getItems();
					
					if(succ){
						s = 1;
					}else{
						s = 0;
						
					}
					} catch (SQLException e) {
					
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

		
		   	
		  		
			
		}
		@SuppressWarnings("unchecked")
		public void behavior(String head1,String head2,String head3,String theclass){
			 wd = new WorkIndicatorDialog(null, "Generating sheet...");
		 	   wd.addTaskEndNotification(result -> {
		 		  String outres = result.toString();
		          // System.out.println("nomaa "+outres);
		           if(outres.equals("1")){
		        	   openBehav();
		          	 TrayNotification tray = new TrayNotification();
		      	     tray.setNotificationType(NotificationType.SUCCESS);
		      	     tray.setTitle("Behaviour");
		      	     tray.setMessage("BEHAVIOUR SHEET SUCCESSFULLY CREATED");
		      	     tray.setAnimationType(AnimationType.SLIDE);
		      	     tray.showAndDismiss(Duration.millis(4000));
		           }else{
		        	   TrayNotification tray = new TrayNotification();
					     tray.setNotificationType(NotificationType.ERROR);
					     tray.setTitle("Could not generate sheet");
					     tray.setMessage("please, retry");
					     tray.setAnimationType(AnimationType.SLIDE);
					     tray.showAndDismiss(Duration.millis(4000));	 
		           }
		           wd=null;
		 	   });
		 	  wd.exec("fetch", inputParam -> {
		           // Thinks to do...
		           // NO ACCESS TO UI ELEMENTS!
					boolean done = false;
		 		  try{
						
						 HSSFWorkbook workbook = new HSSFWorkbook(); 
					      HSSFSheet spreadsheet = workbook
					      .createSheet("Student names");
					      HSSFRow row=spreadsheet.createRow(0);
					      HSSFCellStyle RotateStyle = workbook.createCellStyle();
					      HSSFCellStyle BoldStyle = workbook.createCellStyle();
					      RotateStyle.setRotation((short)90);
					      HSSFFont my_font = workbook.createFont();
					     
					      Header header = spreadsheet.getHeader();
					      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)9)+head1+"\n"
					      		+head2.toUpperCase()+ " \n CLASS:"+head3+".");
					      Footer footer = spreadsheet.getFooter();
					      footer.setCenter(HSSFFooter.font("Times New Roman", "Bold")+HSSFFooter.fontSize((short)9)+"*NB: MIDTERM cell ANNUAL/TERMINAL cell must be filled");
					      spreadsheet.setColumnWidth(0, 4000);
					      spreadsheet.setColumnWidth(1, 7000);
					      spreadsheet.setColumnWidth(2, 2800);
					      spreadsheet.setColumnWidth(3, 1000);
					      spreadsheet.setColumnWidth(4, 1000);
					      spreadsheet.setColumnWidth(5, 1000);
					      spreadsheet.setColumnWidth(6, 1000);
					      spreadsheet.setColumnWidth(7, 1000);
					      spreadsheet.setColumnWidth(8, 1000);
					      spreadsheet.setColumnWidth(9, 1000);
					      spreadsheet.setColumnWidth(10, 1000);
					      spreadsheet.setColumnWidth(11, 1000);
					      spreadsheet.setColumnWidth(12, 1000);
					      spreadsheet.setColumnWidth(13, 1000);
					      
					      
					      
					      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					      String str = "SECONDARY SCHOOL";
					      BoldStyle.setFont(my_font);
					      RotateStyle.setFont(my_font);
					      HSSFCell cell;
					      cell = row.createCell(0);
					      cell.setCellValue(str);
					      //cell.setCellStyle(myStyle);
					      cell=row.createCell(0);
					      cell.setCellValue("REG NO.");
					      cell.setCellStyle(BoldStyle);
					      cell=row.createCell(1);
					      cell.setCellValue("STUDENT NAME");
					      cell.setCellStyle(BoldStyle);
					      cell=row.createCell(2);
					      cell.setCellValue("STUDENT CLASS");
					      cell.setCellStyle(RotateStyle);
					      cell=row.createCell(3);
					      cell.setCellValue("BIDII NA MAARIFA");
					      cell.setCellStyle(RotateStyle);
					      cell=row.createCell(4);
					      cell.setCellValue("UBORA WA KAZI");
					      cell.setCellStyle(RotateStyle);
					      cell=row.createCell(5);
					      cell.setCellValue("ARI YA KAZI");
					      cell.setCellStyle(RotateStyle);
					      cell=row.createCell(6);
					      if(theclass.equals("FORM V") || theclass.equals("FORM VI")){
					    	  cell.setCellValue("KUJIAMINI");
						      cell.setCellStyle(RotateStyle);
					      }else{
					      cell.setCellValue("UTUNZAJI WA VIFAA");
					      cell.setCellStyle(RotateStyle);
					      }
					      cell=row.createCell(7);
					      cell.setCellValue("USHIRIKIANO");
					      cell.setCellStyle(RotateStyle);
					      cell=row.createCell(8);
					      cell.setCellValue("HESHIMA");
					      cell.setCellStyle(RotateStyle);
					      cell=row.createCell(9);
					      cell.setCellValue("UONGOZI");
					      cell.setCellStyle(RotateStyle);
					      cell=row.createCell(10);
					      cell.setCellValue("UTII NA KUJITUMA");
					      cell.setCellStyle(RotateStyle);
					      cell=row.createCell(11);
					      cell.setCellValue("USAFI");
					      cell.setCellStyle(RotateStyle);
					      cell=row.createCell(12);
					      cell.setCellValue("UAMINIFU");
					      cell.setCellStyle(RotateStyle);
					      cell=row.createCell(13);
					      cell.setCellValue("UTAMADUNI");
					      cell.setCellStyle(RotateStyle);
					      
					      
					      int i=1;
					      for(int j=0;j<tableview.getItems().size(); j++){
				           	 
				           	  
				          String ID=tableview.getItems().get(j).getId().toString();
				           	  String name=tableview.getItems().get(j).getName().toString();
				               String classe=tableview.getItems().get(j).getSclass();
				              
				               
				         	  
				         	  
				           
				           	
				           		 row=spreadsheet.createRow(i);
				    	         cell=row.createCell(0);
				    	         cell.setCellValue(ID);
				    	         cell=row.createCell(1); 
				    	         cell.setCellStyle(BoldStyle);
				    	         cell.setCellValue(name);
				    	         cell=row.createCell(2);
				    	         cell.setCellValue(classe);
				    	      
				    	         
				           	
				           		
				           	 i++;	
									done = true;
									}
					     if(done){
					    	 s = 1;
					     }
						
					     
					        
						
						 FileOutputStream out = new FileOutputStream(
							      new File("behaviour.xls"));
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
		             
		           return new Integer(s);
		           
		           
		        });

			}
@SuppressWarnings("unchecked")
public void FillData(String head1,String head2,String head3,String type,String theclass){
	 wd = new WorkIndicatorDialog(null, "Generating sheet...");
	   wd.addTaskEndNotification(result -> {
		  String outres = result.toString();
        // System.out.println("nomaa "+outres);
         if(outres.equals("1")){
      	   open();
      	 TrayNotification tray = new TrayNotification();
	     tray.setNotificationType(NotificationType.SUCCESS);
	     tray.setTitle("Progressive");
	     tray.setMessage(head2+" "+head3+ " SUCCESSFULLY CREATED");
	     tray.setAnimationType(AnimationType.SLIDE);
	     tray.showAndDismiss(Duration.millis(4000));
         }else{
      	   TrayNotification tray = new TrayNotification();
			     tray.setNotificationType(NotificationType.ERROR);
			     tray.setTitle("Could not generate sheet");
			     tray.setMessage("please, retry");
			     tray.setAnimationType(AnimationType.SLIDE);
			     tray.showAndDismiss(Duration.millis(4000));	 
         }
         wd=null;
	   });
	  wd.exec("fetch", inputParam -> {
         // Thinks to do...
         // NO ACCESS TO UI ELEMENTS!
		  boolean done =false;
			try{
				 HSSFWorkbook workbook = new HSSFWorkbook(); 
			      HSSFSheet spreadsheet = workbook
			      .createSheet("Student names");
			      HSSFRow row=spreadsheet.createRow(0);
			      HSSFCellStyle RotateStyle = workbook.createCellStyle();
			      HSSFCellStyle BoldStyle = workbook.createCellStyle();
			      RotateStyle.setRotation((short)90);
			      HSSFFont my_font = workbook.createFont();
			     
			      Header header = spreadsheet.getHeader();
			      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)9)+head1+"\n"
			      		+head2.toUpperCase()+ " \n CLASS:"+head3+".");
			      Footer footer = spreadsheet.getFooter();
			      footer.setCenter(HSSFFooter.font("Times New Roman", "Bold")+HSSFFooter.fontSize((short)9)+"*NB: MIDTERM cell ANNUAL/TERMINAL cell must be filled");
			      spreadsheet.setColumnWidth(0, 4000);
			      spreadsheet.setColumnWidth(1, 7000);
			      spreadsheet.setColumnWidth(2, 2800);
			      spreadsheet.setColumnWidth(3, 1000);
			      spreadsheet.setColumnWidth(4, 1000);
			      spreadsheet.setColumnWidth(5, 1000);
			      spreadsheet.setColumnWidth(6, 1000);
			      spreadsheet.setColumnWidth(7, 1000);
			      spreadsheet.setColumnWidth(8, 1000);
			      spreadsheet.setColumnWidth(9, 1000);
			      
			      
			      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			      String str = "SECONDARY SCHOOL";
			      BoldStyle.setFont(my_font);
			      RotateStyle.setFont(my_font);
			      HSSFCell cell;
			      cell = row.createCell(0);
			      cell.setCellValue(str);
			      //cell.setCellStyle(myStyle);
			      cell=row.createCell(0);
			      cell.setCellValue("REG NO.");
			      cell.setCellStyle(BoldStyle);
			      cell=row.createCell(1);
			      cell.setCellValue("STUDENT NAME");
			      cell.setCellStyle(BoldStyle);
			      cell=row.createCell(2);
			      cell.setCellValue("STUDENT CLASS");
			      cell.setCellStyle(RotateStyle);
			      cell=row.createCell(3);
			      cell.setCellValue("TEST1");
			      cell.setCellStyle(RotateStyle);
			      cell=row.createCell(4);
			      cell.setCellValue("TEST2");
			      cell.setCellStyle(RotateStyle);
			      cell=row.createCell(5);
			      cell.setCellValue("TEST3");
			      cell.setCellStyle(RotateStyle);
			      cell=row.createCell(6);
			      cell.setCellValue("TEST4");
			      cell.setCellStyle(RotateStyle);
			      cell=row.createCell(7);
			      cell.setCellValue("TEST5");
			      cell.setCellStyle(RotateStyle);
			      cell=row.createCell(8);
			      
			      cell.setCellValue("MID TERM");
			      cell.setCellStyle(RotateStyle);
			      cell=row.createCell(9);
			      
			      if(type.equals("Terminal")){
			    	  cell.setCellValue("TERMINAL");
				      cell.setCellStyle(RotateStyle);
			      }else if(type.equals("Annual")){
			    	  cell.setCellValue("ANNUAL");
				      cell.setCellStyle(RotateStyle);
			      }
			      
			      
			      int i=1;
			      for(int j=0;j<tableview.getItems().size(); j++){
		           	 
		           	  
		          String ID=tableview.getItems().get(j).getId().toString();
		           	  String name=tableview.getItems().get(j).getName().toString();
		               String classe=tableview.getItems().get(j).getSclass();
		              
		               
		         	  
		         	  
		           
		           	
		           		 row=spreadsheet.createRow(i);
		    	         cell=row.createCell(0);
		    	         cell.setCellValue(ID);
		    	         cell=row.createCell(1); 
		    	         cell.setCellStyle(BoldStyle);
		    	         cell.setCellValue(name);
		    	         cell=row.createCell(2);
		    	         cell.setCellValue(classe);
		    	      
		    	         
		           	
		           		
		           	 i++;	
							done = true;
							}
			     
				if(done){
					s = 1;
				}
			     
			        
				
				 FileOutputStream out = new FileOutputStream(
					      new File("progressive.xls"));
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
           
         return new Integer(s);
         
         
      });
	
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
		          
					
		 		 try {

						if ((new File("progressive.xls")).exists()) {

							Process p = Runtime
							   .getRuntime()
							   .exec("rundll32 url.dll,FileProtocolHandler progressive.xls");
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
		public void openBehav(){
			
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
		          
					
		 		 try {

						if ((new File("behaviour.xls")).exists()) {

							Process p = Runtime
							   .getRuntime()
							   .exec("rundll32 url.dll,FileProtocolHandler behaviour.xls");
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
		public void closeprogressive(){

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

	
	

}
