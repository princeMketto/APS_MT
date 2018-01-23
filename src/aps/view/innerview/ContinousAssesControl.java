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
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ContinousAssesControl implements Initializable {
	ObservableList<Continous> studentlist;
	boolean b;
	int d;
	LoggerM log=new LoggerM();
    private WorkIndicatorDialog wd=null;
	ConnectDB database = new ConnectDB();
	private Connection con;
    private ResultSet rs,rs4,rs2,rs3;
    private Statement st,st4,st2,st3;
    
    int j=0;
    int k;
    String madarasa;
	  @FXML
	    private BorderPane borderpan;

	    @FXML
	    private JFXTextField search;

	    @FXML
	    private RadioButton terminal;

	    @FXML
	    private ToggleGroup examtype;

	    @FXML
	    private RadioButton annual;

	    @FXML
	    private ChoiceBox<String> classchoice;
	    @FXML
	    private ChoiceBox<String> choicemwaka;

	    @FXML
	    private ChoiceBox<String> yearchoice;

	   

	    @FXML
	    private AnchorPane anchor;

	    @FXML
	    private StackPane stackolevel;

	    @FXML
	    private JFXButton btnprint;

	    @FXML
	    private TableView<Continous> tableview;

	    @FXML
	    private TableColumn<Continous, String> idcol;
	    
	    

	    @FXML
	    private TableColumn<Continous, String> namecol;
	    
	    @FXML
	    private TableColumn<Continous, String> classcol;
	    

	    @FXML
	    private TableColumn<Continous, String> gendercol;

	    @FXML
	    private TableColumn<Continous, String> tes1col;

	    @FXML
	    private TableColumn<Continous, String> tes2col;

	    @FXML
	    private TableColumn<Continous, String> tes3col;

	    @FXML
	    private TableColumn<Continous, String> tes4col;

	    @FXML
	    private TableColumn<Continous, String> tes5col;

	    @FXML
	    private TableColumn<Continous, String> avcol;

	    @FXML
	    private TableColumn<Continous, String> midcol;

	    @FXML
	    private TableColumn<Continous, String> examcol;
	    static String examTy; 
	    
	    ObservableList<String> year1 = FXCollections.observableArrayList();
	    ObservableList<String> miaka = FXCollections.observableArrayList();
	    ObservableList<String> classes = FXCollections.observableArrayList();
	    ObservableList<Continous> searchdata;
	    String name,code,year,gnder;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		terminal.setUserData("Terminal");
		annual.setUserData("Annual");
		
		terminal.setToggleGroup(examtype);
		annual.setToggleGroup(examtype);
		examtype.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				  if (examtype.getSelectedToggle() != null && !examtype.getSelectedToggle().equals("examType") ) {
					  examTy = examtype.getSelectedToggle().getUserData().toString();
			          
			         }
				
			}
			
		});
		idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
		namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
		classcol.setCellValueFactory(new PropertyValueFactory<>("sclass"));
		gendercol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		tes1col.setCellValueFactory(new PropertyValueFactory<>("tes1"));
		tes2col.setCellValueFactory(new PropertyValueFactory<>("tes2"));
		tes3col.setCellValueFactory(new PropertyValueFactory<>("tes3"));
		tes4col.setCellValueFactory(new PropertyValueFactory<>("tes4"));
		tes5col.setCellValueFactory(new PropertyValueFactory<>("tes5"));
		avcol.setCellValueFactory(new PropertyValueFactory<>("average"));
		midcol.setCellValueFactory(new PropertyValueFactory<>("mid"));
		examcol.setCellValueFactory(new PropertyValueFactory<>("exam"));
		
		search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			
			if (oldValue != null && (newValue.length() < oldValue.length())) {
		            	tableview.setItems(searchdata);
		            }
		            String value = newValue.toLowerCase();
		            ObservableList<Continous> subentries = FXCollections.observableArrayList();

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
		
		 year1.add("Choose Subjects");
		    classes.add("FORM I");
		    classes.add("FORM II");
		    classes.add("FORM III");
		    classes.add("FORM IV");
		    classes.add("FORM V");
		    classes.add("FORM VI");
		    classes.add("Choose class");
	       	yearchoice.setValue("Choose Subjects");
	       	classchoice.setValue("Choose class");
	       	classchoice.setItems(classes);
	       	year();
	    	
	    	 classchoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
	    		 int s;
					@SuppressWarnings("unchecked")
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
						year1.clear();
						year1.add("Choose Subjects");
						wd = new WorkIndicatorDialog(null, "Loading data...");
					 	   wd.addTaskEndNotification(result -> {
					 		  String outres = result.toString();
					          // System.out.println("nomaa "+outres);
					           if(outres.equals("1")){
					        	   yearchoice.setValue("Choose Subjects");
					        	   yearchoice.setItems(year1); 
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
										String sbjname=code+"-"+sbj;
										
										year1.add(sbjname);
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
						year1.clear();
						year1.add("Choose Subjects");
						  wd = new WorkIndicatorDialog(null, "Loading data...");
					 	   wd.addTaskEndNotification(result -> {
					 		  String outres = result.toString();
					          // System.out.println("nomaa "+outres);
					           if(outres.equals("1")){
					        	   yearchoice.setValue("Choose Subjects");
					        	   yearchoice.setItems(year1);
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
										String sbjname=code+"-"+sbj;
										
										year1.add(sbjname);
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
	    	 
	    	 yearchoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
	               
					@Override
					public void changed(ObservableValue<? extends String> arg0, String old, String clas) {
						tableview.getItems().clear();
					
						String classes=(String) classchoice.getSelectionModel().getSelectedItem();
						String masomo=(String)  yearchoice.getSelectionModel().getSelectedItem();
						year=(String)    choicemwaka.getSelectionModel().getSelectedItem();
						String [] c=masomo.split("-");
						 code=c[0];
						
						con=database.dbconnect();
						if(classes.equals("FORM I")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
							
							
							madarasa="(class='FORM IA' OR class='FORM IB' OR class='FORM IC' OR class='FORM ID' OR class='FORM IE' OR class='FORM IF' OR class='FORM IG' OR class='FORM IH')";
							query();
						}else if(classes.equals("FORM II")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
							madarasa="(class='FORM IIA' OR class='FORM IIB' OR class='FORM IIC' OR class='FORM IID' OR class='FORM IIE' OR class='FORM IIF' OR class='FORM IIG' OR class='FORM IIH')";
							query();
						}else if(classes.equals("FORM III")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
							madarasa="(class LIKE 'FORM III%')";
							query();
						}else if(classes.equals("FORM IV")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
							madarasa="(class LIKE 'FORM IV%')";
							query();
						}else if(classes.equals("FORM V")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
							madarasa="(class LIKE 'FORM V-%')";
							query();
						}else if(classes.equals("FORM VI")&&masomo.equals("Choose Subjects")==false&&(terminal.isSelected()||annual.isSelected())){
							madarasa="(class LIKE 'FORM VI-%')";
							query();
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
    @FXML
    void goHard(ActionEvent event) {
    	close();
    	k=0;
    	String theclass =(String) classchoice.getSelectionModel().getSelectedItem();
    	String head3 =(String) classchoice.getSelectionModel().getSelectedItem();
    	String sub =(String) yearchoice.getSelectionModel().getSelectedItem();
    	String head1 ="SECONDARY SCHOOL";
    	String head2 ="Results CA "+sub;
    	String type;
    	if(annual.isSelected()){
    		type="Annual";
    	 }else{
    		type="Terminal";
    	}
    	FillData(head1, head2, head3, type , theclass);
    }
    @SuppressWarnings("unchecked")
	public void year(){
    	wd = new WorkIndicatorDialog(null, "..");
      	 wd.addTaskEndNotification(result -> {
      		  String outres = result.toString();
      	   
   	  
      	
      	    wd=null;
      	 });
      		 wd.exec("fetch", inputParam -> {
   			int z = 0;
   		 miaka.clear();
		 miaka.add("Choose Year");
		 
			
		 			 con= database.dbconnect();
		 		  
		 			 try {
		 				st= con.createStatement();
		 				 String yrs="SELECT distinct year FROM  result_from_teacher";
		 				    
		 				    rs=st.executeQuery(yrs);
		 				    while(rs.next()){
		 				      String	year=rs.getString("year");
		 				         miaka.add(year);
		 				         
		 				       }
		 				    choicemwaka.setItems(miaka);
		 				    choicemwaka.setValue("Choose Year");
		 				    
		 				
		 			} catch (SQLException e) {
		 				// TODO Auto-generated catch block
		 				e.printStackTrace();
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
    @SuppressWarnings("unchecked")
public void query(){
    	 List listofstud = new ArrayList();
        d=0;
    	b=false;
    	 wd = new WorkIndicatorDialog(null, "Loading CA...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   studentlist = FXCollections.observableList(listofstud);
	        	   tableview.setItems(studentlist);
	      	   searchdata =  tableview.getItems();
         	   tableview.getSortOrder().add(gendercol);
       	       tableview.getSortOrder().add(namecol);
	           }else{
	        	   TrayNotification tray = new TrayNotification();
		 		     tray.setNotificationType(NotificationType.ERROR);
		 		     tray.setTitle("Report");
		 		     tray.setMessage("No results for selected subject!");
		 		     tray.setAnimationType(AnimationType.SLIDE);
		 		     tray.showAndDismiss(Duration.millis(8000)); 
	           }
	           wd=null;
	 	   });
	 	  wd.exec("fetch", inputParam -> {

	 		 try{
	 			st=con.createStatement();
	          String s="SELECT *FROM result_from_teacher where "+madarasa+" AND coz_id='"+code+"' AND year='"+year+"' AND type='"+examTy+"'";
	          rs=st.executeQuery(s);
	          while(rs.next()){
	         	 b=true;
	         	 st2=con.createStatement();
	         	 String id=rs.getString("stu_id");
	         	 String sele="SELECT*FROM students where student_id='"+id+"'";
	         	 rs2= st2.executeQuery(sele);
	         	 while(rs2.next()){
	         		String  fname=rs2.getString("firstName");
	         		String  mname=rs2.getString("middleName");
	         		String  lname=rs2.getString("lastName");
	         		String gend=rs2.getString("gender");
	         		gnder=""+gend.charAt(0);
	         		name=fname+" "+mname+" "+lname;
	         		
	         		 
	         	 }
	         	 String clc=rs.getString("class");
	         	 String t1=rs.getString("test1");
	         	 String t2=rs.getString("test2");
	         	 String t3=rs.getString("test3");
	         	 String t4=rs.getString("test4");
	         	 String t5=rs.getString("test5");
	         	 String mazo=rs.getString("mazoezi");
	         	 String mid=rs.getString("mid_term");
	         	 String ex=rs.getString("exam");
	         	 
	         	 
	         	listofstud.add(new Continous(id,name,clc,gnder,t1,t2,t3,t4,t5,mazo,mid,ex));	
	          }
	          if(b){
	        	  d=1;
	          }else{
	         	d=2;
	          }
	 			}catch(Exception e){
	 				e.printStackTrace();
	          }
              try {
                 Thread.sleep(1000);
              }	
              catch (InterruptedException er) {
                 er.printStackTrace();
              }
            
          return new Integer(d);
          
          
       });
    	
    }
    @SuppressWarnings("unchecked")
	public void FillData(String head1,String head2,String head3,String type,String theclass){
    	
   	 wd = new WorkIndicatorDialog(null, "Generating CA...");
   	   wd.addTaskEndNotification(result -> {
   		  String outres = result.toString();
           // System.out.println("nomaa "+outres);
            if(outres.equals("1")){
         	   open();
         	 TrayNotification tray = new TrayNotification();
   	     tray.setNotificationType(NotificationType.SUCCESS);
   	     tray.setTitle("ResultCA");
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
   			      .createSheet("ResultsCA");
   			      HSSFRow row=spreadsheet.createRow(0);
   			      HSSFCellStyle RotateStyle = workbook.createCellStyle();
   			      HSSFCellStyle BoldStyle = workbook.createCellStyle();
   			      RotateStyle.setRotation((short)90);
   			      HSSFFont my_font = workbook.createFont();
   			     
   			      Header header = spreadsheet.getHeader();
   			      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)9)+head1+"\n"
   			      		+head2.toUpperCase()+ " \n CLASS:"+head3+".");
   			      Footer footer = spreadsheet.getFooter();
   			      footer.setCenter(HSSFFooter.font("Times New Roman", "Bold")+HSSFFooter.fontSize((short)9)+"");
   			      spreadsheet.setColumnWidth(0, 4000);
   			      spreadsheet.setColumnWidth(1, 9000);
   			      spreadsheet.setColumnWidth(2, 2500);
   			      spreadsheet.setColumnWidth(3, 1000);
   			      spreadsheet.setColumnWidth(4, 1000);
   			      spreadsheet.setColumnWidth(5, 1000);
   			      spreadsheet.setColumnWidth(6, 1000);
   			      spreadsheet.setColumnWidth(7, 1000);
   			      spreadsheet.setColumnWidth(8, 1000);
   			      spreadsheet.setColumnWidth(9, 1000);
   			       spreadsheet.setColumnWidth(10, 1000);
   			      
   			      
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
   			     cell.setCellValue("CLASS");
			      cell.setCellStyle(BoldStyle);
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
   			      
   			      cell.setCellValue("TESTS AVARAGE");
   			      cell.setCellStyle(RotateStyle);
   			      cell=row.createCell(9);
   			   cell.setCellValue("MIDTERM");
			      cell.setCellStyle(RotateStyle);
			      cell=row.createCell(10);
   			      
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
   		             	String cls=tableview.getItems().get(j).getSclass().toString();
   		               String te1=tableview.getItems().get(j).getTes1().toString();
   		               String te2=tableview.getItems().get(j).getTes2().toString();
   		               String te3=tableview.getItems().get(j).getTes3().toString();
   		               String te4=tableview.getItems().get(j).getTes4().toString();
   		               String te5=tableview.getItems().get(j).getTes5().toString();
   		               String mazo=tableview.getItems().get(j).getAverage().toString();
   		               String mid=tableview.getItems().get(j).getMid().toString();
   		               String exa=tableview.getItems().get(j).getExam().toString();
   		              
   		               
   		         	  
   		         	  
   		           
   		           	
   		           		 row=spreadsheet.createRow(i);
   		    	         cell=row.createCell(0);
   		    	         cell.setCellValue(ID);
   		    	         cell=row.createCell(1); 
   		    	         cell.setCellStyle(BoldStyle);
   		    	         cell.setCellValue(name);
   		    	         cell=row.createCell(2);
   		    	          cell.setCellValue(cls);
   		    	       cell=row.createCell(3);
   		    	         cell.setCellValue(te1);
   		    	          cell=row.createCell(4);
		    	         cell.setCellValue(te2);
		    	         cell=row.createCell(5);
   		    	         cell.setCellValue(te3);
   		    	      cell=row.createCell(6);
		    	         cell.setCellValue(te4);
		    	         cell=row.createCell(7);
   		    	         cell.setCellValue(te5);
   		    	      cell=row.createCell(8);
		    	         cell.setCellValue(mazo);
		    	         cell=row.createCell(9);
		    	         cell.setCellValue(mid);
		    	         cell=row.createCell(10);
		    	         cell.setCellValue(exa);
   		    	         
   		           	
   		           		
   		           	 i++;	
   							
   							}
   		
   				
   				 FileOutputStream out = new FileOutputStream(
   					      new File("CAresults.xls"));
   					      workbook.write(out);
   					      out.close();
   					      k=1;
   					      
   			}catch(Exception e){
   				
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
    @SuppressWarnings("unchecked")
	public void open(){
		
		 wd = new WorkIndicatorDialog(null, "Opening CA");
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

					if ((new File("CAresults.xls")).exists()) {

						Process p = Runtime
						   .getRuntime()
						   .exec("rundll32 url.dll,FileProtocolHandler CAresults.xls");
						p.waitFor();
							j=1;
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
	             
	           return new Integer(j);
	           
	           
	        });
		 
	}
    public void close(){

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
