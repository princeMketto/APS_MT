package aps.view.innerview;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Header;

import com.jfoenix.controls.JFXButton;

import aps.view.ConnectDB;
import aps.view.Marks;
import aps.view.WorkIndicatorDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class LoadPrint implements Initializable {

    @FXML
    private Text clos;

    @FXML
    private JFXButton btntransfer;

    @FXML
    private JFXButton btnsuspend;

    @FXML
    private JFXButton btn1;

    @FXML
    private JFXButton btn2;

    @FXML
    private JFXButton btn3;

    @FXML
    private JFXButton btn4;

    @FXML
    private JFXButton btn5;

    @FXML
    private JFXButton btn6;

    @FXML
    private JFXButton btnstaff;

    private WorkIndicatorDialog wd=null;
	 ConnectDB database = new ConnectDB();
	    private Connection con;
	    private ResultSet rs;
	    private Statement st;
	    private PreparedStatement prep;
	  static  String stID = null;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
    @FXML
    void go1(ActionEvent event) {
    	print1();
    }

    @FXML
    void go2(ActionEvent event) {
    	print2();
    }

    @FXML
    void go3(ActionEvent event) {
    	print3();
    }

    @FXML
    void go4(ActionEvent event) {
    	print4();
    }

    @FXML
    void go5(ActionEvent event) {
    	print5();
    }

    @FXML
    void go6(ActionEvent event) {
    	print6();
    }

  
	@FXML
    void goClose(MouseEvent event) {
   // Platform.exit();
  
    }

    @FXML
    void goStaf(ActionEvent event) {
		printStaff();
    }

   
	@FXML
    void goSusp(ActionEvent event) {
    	printSuspend();
    }


	@FXML
    void goTrans(ActionEvent event) {
    	printShifted();
    }
	@SuppressWarnings("unchecked")
	public void openFile(String clasi){

    	
    	wd = new WorkIndicatorDialog(null, "opening file...");
 	 	   wd.addTaskEndNotification(result -> {
 	 		  String outres = result.toString();
 	          // System.out.println("nomaa "+outres);
 	           if(outres.equals("1")){
 	        	 
 	           }else if(outres.equals("2")){
 	        	  TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("failure in opening file");
			       tray.setMessage("close any open file and try again");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
 	           }else if(outres.equals("-1")){
 	        	  TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.WARNING);
			       tray.setTitle("file does not exist");
			       tray.setMessage("please generate the file again");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
 	           }
 	           wd=null;
 	 	   });
 	 		 wd.exec("fetch", inputParam -> {
 	 			int mes=0;
 	 			if(clasi.equals("fm1")){
 	 				try {

 						if ((new File("Form_I.xls")).exists()) {

 							Process p = Runtime
 							   .getRuntime()
 							   .exec("rundll32 url.dll,FileProtocolHandler Form_I.xls");
 							p.waitFor();
 							mes=1;	
 						} else {
 							mes=-1;
 						}

 				  	  } catch (Exception ex) {
 	 	 	  	  		  mes = 2;
 	 	 	  	  
 	 	 	  		  }
 	 			}else if(clasi.equals("fm2")){
 	 				try {

 						if ((new File("Form_II.xls")).exists()) {

 							Process p = Runtime
 							   .getRuntime()
 							   .exec("rundll32 url.dll,FileProtocolHandler Form_II.xls");
 							p.waitFor();
 							mes=1;	
 						} else {
 							mes=-1;
 						}

 				  	  } catch (Exception ex) {
 	 	 	  	  		  mes = 2;
 	 	 	  	  
 	 	 	  		  }
 	 			}else if(clasi.equals("fm3")){
 	 				try {

 						if ((new File("Form_III.xls")).exists()) {

 							Process p = Runtime
 							   .getRuntime()
 							   .exec("rundll32 url.dll,FileProtocolHandler Form_III.xls");
 							p.waitFor();
 							mes=1;	
 						} else {
 							mes=-1;
 						}

 				  	  } catch (Exception ex) {
 	 	 	  	  		  mes = 2;
 	 	 	  	  
 	 	 	  		  }
 	 			}else if(clasi.equals("fm4")){
 	 				try {

 						if ((new File("Form_IV.xls")).exists()) {

 							Process p = Runtime
 							   .getRuntime()
 							   .exec("rundll32 url.dll,FileProtocolHandler Form_IV.xls");
 							p.waitFor();
 							mes=1;	
 						} else {
 							mes=-1;
 						}

 				  	  } catch (Exception ex) {
 	 	 	  	  		  mes = 2;
 	 	 	  	  
 	 	 	  		  }
 	 			}else if(clasi.equals("fm5")){
 	 				try {

 						if ((new File("Form_V.xls")).exists()) {

 							Process p = Runtime
 							   .getRuntime()
 							   .exec("rundll32 url.dll,FileProtocolHandler Form_V.xls");
 							p.waitFor();
 							mes=1;	
 						} else {
 							mes=-1;
 						}

 				  	  } catch (Exception ex) {
 	 	 	  	  		  mes = 2;
 	 	 	  	  
 	 	 	  		  }
 	 			}else if(clasi.equals("fm6")){
 	 				try {

 						if ((new File("Form_VI.xls")).exists()) {

 							Process p = Runtime
 							   .getRuntime()
 							   .exec("rundll32 url.dll,FileProtocolHandler Form_VI.xls");
 							p.waitFor();
 							mes=1;	
 						} else {
 							mes=-1;
 						}

 				  	  } catch (Exception ex) {
 	 	 	  	  		  mes = 2;
 	 	 	  	  
 	 	 	  		  }
 	 			}else if(clasi.equals("trans")){
 	 				try {

 						if ((new File("transferred_names.xls")).exists()) {

 							Process p = Runtime
 							   .getRuntime()
 							   .exec("rundll32 url.dll,FileProtocolHandler transferred_names.xls");
 							p.waitFor();
 							mes=1;	
 						} else {
 							mes=-1;
 						}

 				  	  } catch (Exception ex) {
 	 	 	  	  		  mes = 2;
 	 	 	  	  
 	 	 	  		  }
 	 			}else if(clasi.equals("susp")){
 	 				try {

 						if ((new File("inactive_student.xls")).exists()) {

 							Process p = Runtime
 							   .getRuntime()
 							   .exec("rundll32 url.dll,FileProtocolHandler inactive_student.xls");
 							p.waitFor();
 							mes=1;	
 						} else {
 							mes=-1;
 						}

 				  	  } catch (Exception ex) {
 	 	 	  	  		  mes = 2;
 	 	 	  	  
 	 	 	  		  }
 	 			}else if(clasi.equals("staf")){
 	 				try {

 						if ((new File("staff_names.xls")).exists()) {

 							Process p = Runtime
 							   .getRuntime()
 							   .exec("rundll32 url.dll,FileProtocolHandler staff_names.xls");
 							p.waitFor();
 							mes=1;	
 						} else {
 							mes=-1;
 						}

 				  	  } catch (Exception ex) {
 	 	 	  	  		  mes = 2;
 	 	 	  	  
 	 	 	  		  }
 	 			}
 	 	
 		       
 		               try {
 		                  Thread.sleep(1000);
 		               }	
 		               catch (InterruptedException er) {
 		                  er.printStackTrace();
 		               }
 		             
 		           return new Integer(mes);
 		           
 		           
 		        });
  
  
	}
	  @SuppressWarnings("unchecked")
	private void print1() {
		  wd = new WorkIndicatorDialog(null, "Fetching data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.INFORMATION);
			       tray.setTitle("List successfully Generated");
			       tray.setMessage("All FORM I students are in the list");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       
			       openFile("fm1");
			   
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
	 			try{
					HSSFWorkbook workbook = new HSSFWorkbook(); 
				      HSSFSheet spreadsheet = workbook
				      .createSheet("FORM I STUDENTS");
				      HSSFRow row=spreadsheet.createRow(0);
				      HSSFCellStyle RotateStyle = workbook.createCellStyle();
				      HSSFCellStyle BoldStyle = workbook.createCellStyle();
				      RotateStyle.setRotation((short)90);
				      HSSFFont my_font = workbook.createFont();
				      Header header = spreadsheet.getHeader();
				      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)9)+"FORM I STUDENT THIS YEAR\n");

				      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				      spreadsheet.setColumnWidth(0, 3300);
				      spreadsheet.setColumnWidth(1, 1300);
				      spreadsheet.setColumnWidth(2, 7000);
				      spreadsheet.setColumnWidth(3, 5500);
				      spreadsheet.setColumnWidth(4, 2500);
				      BoldStyle.setFont(my_font);
				      RotateStyle.setFont(my_font);
				      HSSFCell cell;
				      cell=row.createCell(0);
				      cell.setCellValue("STUDENT REGNO.");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(1);
				      cell.setCellValue("SEX");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(2);
				      cell.setCellValue("STUDENT NAME");		
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(3);
				      cell.setCellValue("CLASS");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(4);
				      cell.setCellValue("ADMISSION YEAR");
				      cell.setCellStyle(BoldStyle);
				     
				      
				      int i=1;
				     con = database.dbconnect();
				     st = con.createStatement();
				     
				      String str = "select * from students WHERE (studentClass='FORM IA' OR studentClass='FORM IB' OR studentClass='FORM IC' OR studentClass='FORM ID' OR studentClass='FORM IE' OR studentClass='FORM IF' OR studentClass='FORM IG' OR studentClass='FORM IH') AND NOT ( status='DISABLED' OR status='TRANSFERRED' OR status='COMPLETE') ORDER BY Gender,firstName,studentClass ASC";
			 rs = st.executeQuery(str);
			     while(rs.next())
			     {
			    	 
			    	 String name = rs.getString("firstName");
			    	 String mname = rs.getString("middleName");
			    	 String lname = rs.getString("lastName");
			    	 // String sex = rs.getString("Gender");
			    	 String clac = rs.getString("studentClass");
			    	 String id = rs.getString("student_id");
			   	 String yea = rs.getString("admissionYear");
			    	 String fullname = name + "    "+mname+"\t    " + lname;
			    	 
			        row=spreadsheet.createRow(i);
			        cell=row.createCell(0);
			        cell.setCellValue(id);
			        String sex= rs.getString("Gender");
			        if(sex.equals("FEMALE")){
			        cell=row.createCell(1);
			        cell.setCellValue("F");
			        }else if(sex.equals("MALE")){
			            cell=row.createCell(1);
			            cell.setCellValue("M"); 
			        }
			        cell=row.createCell(2); 
			        cell.setCellValue(fullname);
			    
			        cell=row.createCell(3);
			        cell.setCellValue(clac);
			        
			        cell=row.createCell(4);
			        cell.setCellValue(yea);
			       
			 
			       
			       
			 	
			  
			        i++;
			     }
			     FileOutputStream out = new FileOutputStream(
			     new File("Form_I.xls"));
			     workbook.write(out);
			     out.close();
			    
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);
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
	  @SuppressWarnings("unchecked")
	private void print2() {
		  wd = new WorkIndicatorDialog(null, "Fetching data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.INFORMATION);
			       tray.setTitle("List successfully Generated");
			       tray.setMessage("All FORM II students are in the list");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       openFile("fm2");
			 	 
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
	 			try{
					HSSFWorkbook workbook = new HSSFWorkbook(); 
				      HSSFSheet spreadsheet = workbook
				      .createSheet("FORM II STUDENTS");
				      HSSFRow row=spreadsheet.createRow(0);
				      HSSFCellStyle RotateStyle = workbook.createCellStyle();
				      HSSFCellStyle BoldStyle = workbook.createCellStyle();
				      RotateStyle.setRotation((short)90);
				      HSSFFont my_font = workbook.createFont();
				      Header header = spreadsheet.getHeader();
				      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)9)+"FORM I STUDENT THIS YEAR\n");

				      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				      spreadsheet.setColumnWidth(0, 3300);
				      spreadsheet.setColumnWidth(1, 1300);
				      spreadsheet.setColumnWidth(2, 7000);
				      spreadsheet.setColumnWidth(3, 5500);
				      spreadsheet.setColumnWidth(4, 2500);
				      BoldStyle.setFont(my_font);
				      RotateStyle.setFont(my_font);
				      HSSFCell cell;
				      cell=row.createCell(0);
				      cell.setCellValue("STUDENT REGNO.");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(1);
				      cell.setCellValue("SEX");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(2);
				      cell.setCellValue("STUDENT NAME");		
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(3);
				      cell.setCellValue("CLASS");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(4);
				      cell.setCellValue("ADMISSION YEAR");
				      cell.setCellStyle(BoldStyle);
				     
				      int i=1;
				     con = database.dbconnect();
				     st = con.createStatement();
				     
				      String str = "select * from students WHERE (studentClass='FORM IIA' OR studentClass='FORM IIB' OR studentClass='FORM IIC' OR studentClass='FORM IID' OR studentClass='FORM IIE' OR studentClass='FORM IIF' OR studentClass='FORM IIG' OR studentClass='FORM IIH') AND NOT ( status='DISABLED' OR status='TRANSFERRED' OR status='COMPLETE') ORDER BY Gender,firstName,studentClass ASC";
			 rs = st.executeQuery(str);
			     while(rs.next())
			     {
			    	 
			    	 String name = rs.getString("firstName");
			    	 String mname = rs.getString("middleName");
			    	 String lname = rs.getString("lastName");
			    	 // String sex = rs.getString("Gender");
			    	 String clac = rs.getString("studentClass");
			    	 String id = rs.getString("student_id");
			   	 String yea = rs.getString("admissionYear");
			   	String fullname = name + "    "+mname+"\t    " + lname;
			    	 
			        row=spreadsheet.createRow(i);
			        cell=row.createCell(0);
			        cell.setCellValue(id);
			        String sex= rs.getString("Gender");
			        if(sex.equals("FEMALE")){
			        cell=row.createCell(1);
			        cell.setCellValue("F");
			        }else if(sex.equals("MALE")){
			            cell=row.createCell(1);
			            cell.setCellValue("M"); 
			        }
			        cell=row.createCell(2); 
			        cell.setCellValue(fullname);
			    
			        cell=row.createCell(3);
			        cell.setCellValue(clac);
			       
			        cell=row.createCell(4);
			        cell.setCellValue(yea);
			 
			       
			       
			 	
			  
			        i++;
			     }
			     FileOutputStream out = new FileOutputStream(
			     new File("Form_II.xls"));
			     workbook.write(out);
			     out.close();
			    
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);
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
	  @SuppressWarnings("unchecked")
	private void print3() {
		  wd = new WorkIndicatorDialog(null, "Fetching data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.INFORMATION);
			       tray.setTitle("List successfully Generated");
			       tray.setMessage("All FORM III students are in the list");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       openFile("fm3");
			       
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
	 			try{
					HSSFWorkbook workbook = new HSSFWorkbook(); 
				      HSSFSheet spreadsheet = workbook
				      .createSheet("FORM III STUDENTS");
				      HSSFRow row=spreadsheet.createRow(0);
				      HSSFCellStyle RotateStyle = workbook.createCellStyle();
				      HSSFCellStyle BoldStyle = workbook.createCellStyle();
				      RotateStyle.setRotation((short)90);
				      HSSFFont my_font = workbook.createFont();
				      Header header = spreadsheet.getHeader();
				      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)9)+"FORM I STUDENT THIS YEAR\n");

				      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				      spreadsheet.setColumnWidth(0, 3300);
				      spreadsheet.setColumnWidth(1, 1300);
				      spreadsheet.setColumnWidth(2, 7000);
				      spreadsheet.setColumnWidth(3, 5500);
				      spreadsheet.setColumnWidth(4, 2500);
				      BoldStyle.setFont(my_font);
				      RotateStyle.setFont(my_font);
				      HSSFCell cell;
				      cell=row.createCell(0);
				      cell.setCellValue("STUDENT REGNO.");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(1);
				      cell.setCellValue("SEX");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(2);
				      cell.setCellValue("STUDENT NAME");		
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(3);
				      cell.setCellValue("CLASS");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(4);
				      cell.setCellValue("ADMISSION YEAR");
				      cell.setCellStyle(BoldStyle);
				     
				      int i=1;
				     con = database.dbconnect();
				     st = con.createStatement();
				     
				      String str = "select * from students WHERE ( studentClass LIKE 'FORM III%') AND NOT ( status='DISABLED' OR status='TRANSFERRED' OR status='COMPLETE') ORDER BY Gender,firstName,studentClass ASC";
			 rs = st.executeQuery(str);
			     while(rs.next())
			     {
			    	 
			    	 String name = rs.getString("firstName");
			    	 String mname = rs.getString("middleName");
			    	 String lname = rs.getString("lastName");
			    	 // String sex = rs.getString("Gender");
			    	 String clac = rs.getString("studentClass");
			    	 String id = rs.getString("student_id");
			   	 String yea = rs.getString("admissionYear");
			   	String fullname = name + "    "+mname+"\t    " + lname;
			    	 
			        row=spreadsheet.createRow(i);
			        cell=row.createCell(0);
			        cell.setCellValue(id);
			        String sex= rs.getString("Gender");
			        if(sex.equals("FEMALE")){
			        cell=row.createCell(1);
			        cell.setCellValue("F");
			        }else if(sex.equals("MALE")){
			            cell=row.createCell(1);
			            cell.setCellValue("M"); 
			        }
			        cell=row.createCell(2); 
			        cell.setCellValue(fullname);
			    
			        cell=row.createCell(3);
			        cell.setCellValue(clac);
			        
			        cell=row.createCell(4);
			        cell.setCellValue(yea);
			       
			 
			       
			       
			 	
			  
			        i++;
			     }
			     FileOutputStream out = new FileOutputStream(
			     new File("Form_III.xls"));
			     workbook.write(out);
			     out.close();
			    
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);
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
	  @SuppressWarnings("unchecked")
	private void print4() {
		  wd = new WorkIndicatorDialog(null, "Fetching data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.INFORMATION);
			       tray.setTitle("List successfully Generated");
			       tray.setMessage("All FORM IV students are in the list");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       openFile("fm4");
			       
				
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
	 			try{
					HSSFWorkbook workbook = new HSSFWorkbook(); 
				      HSSFSheet spreadsheet = workbook
				      .createSheet("FORM IV STUDENTS");
				      HSSFRow row=spreadsheet.createRow(0);
				      HSSFCellStyle RotateStyle = workbook.createCellStyle();
				      HSSFCellStyle BoldStyle = workbook.createCellStyle();
				      RotateStyle.setRotation((short)90);
				      HSSFFont my_font = workbook.createFont();
				      Header header = spreadsheet.getHeader();
				      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)9)+"FORM I STUDENT THIS YEAR\n");

				      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				      spreadsheet.setColumnWidth(0, 3300);
				      spreadsheet.setColumnWidth(1, 1300);
				      spreadsheet.setColumnWidth(2, 7000);
				      spreadsheet.setColumnWidth(3, 5500);
				      spreadsheet.setColumnWidth(4, 2500);
				      BoldStyle.setFont(my_font);
				      RotateStyle.setFont(my_font);
				      HSSFCell cell;
				      cell=row.createCell(0);
				      cell.setCellValue("STUDENT REGNO.");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(1);
				      cell.setCellValue("SEX");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(2);
				      cell.setCellValue("STUDENT NAME");		
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(3);
				      cell.setCellValue("CLASS");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(4);
				      cell.setCellValue("ADMISSION YEAR");
				      cell.setCellStyle(BoldStyle);
				     
				      int i=1;
				     con = database.dbconnect();
				     st = con.createStatement();
				     
				      String str = "select * from students WHERE ( studentClass LIKE 'FORM IV%') AND NOT ( status='DISABLED' OR status='TRANSFERRED' OR status='COMPLETE') ORDER BY Gender,firstName,studentClass ASC";
			 rs = st.executeQuery(str);
			     while(rs.next())
			     {
			    	 
			    	 String name = rs.getString("firstName");
			    	 String mname = rs.getString("middleName");
			    	 String lname = rs.getString("lastName");
			    	 // String sex = rs.getString("Gender");
			    	 String clac = rs.getString("studentClass");
			    	 String id = rs.getString("student_id");
			   	 String yea = rs.getString("admissionYear");
			   	String fullname = name + "    "+mname+"\t    " + lname;
			    	 
			        row=spreadsheet.createRow(i);
			        cell=row.createCell(0);
			        cell.setCellValue(id);
			        String sex= rs.getString("Gender");
			        if(sex.equals("FEMALE")){
			        cell=row.createCell(1);
			        cell.setCellValue("F");
			        }else if(sex.equals("MALE")){
			            cell=row.createCell(1);
			            cell.setCellValue("M"); 
			        }
			        cell=row.createCell(2); 
			        cell.setCellValue(fullname);
			    
			        cell=row.createCell(3);
			        cell.setCellValue(clac);
			       
			        cell=row.createCell(4);
			        cell.setCellValue(yea);
			 
			       
			       
			 	
			  
			        i++;
			     }
			     FileOutputStream out = new FileOutputStream(
			     new File("Form_IV.xls"));
			     workbook.write(out);
			     out.close();
			     
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);
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
	  @SuppressWarnings("unchecked")
	private void print5() {
		  wd = new WorkIndicatorDialog(null, "Fetching data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.INFORMATION);
			       tray.setTitle("List successfully Generated");
			       tray.setMessage("All FORM V students are in the list");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       openFile("fm5");
			 	  
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
	 			try{
					HSSFWorkbook workbook = new HSSFWorkbook(); 
				      HSSFSheet spreadsheet = workbook
				      .createSheet("FORM V STUDENTS");
				      HSSFRow row=spreadsheet.createRow(0);
				      HSSFCellStyle RotateStyle = workbook.createCellStyle();
				      HSSFCellStyle BoldStyle = workbook.createCellStyle();
				      RotateStyle.setRotation((short)90);
				      HSSFFont my_font = workbook.createFont();
				      Header header = spreadsheet.getHeader();
				      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)9)+"FORM I STUDENT THIS YEAR\n");

				      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				      spreadsheet.setColumnWidth(0, 3300);
				      spreadsheet.setColumnWidth(1, 1300);
				      spreadsheet.setColumnWidth(2, 7000);
				      spreadsheet.setColumnWidth(3, 5500);
				      spreadsheet.setColumnWidth(4, 2500);
				      BoldStyle.setFont(my_font);
				      RotateStyle.setFont(my_font);
				      HSSFCell cell;
				      cell=row.createCell(0);
				      cell.setCellValue("STUDENT REGNO.");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(1);
				      cell.setCellValue("SEX");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(2);
				      cell.setCellValue("STUDENT NAME");		
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(3);
				      cell.setCellValue("CLASS");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(4);
				      cell.setCellValue("ADMISSION YEAR");
				      cell.setCellStyle(BoldStyle);
				     
				      int i=1;
				     con = database.dbconnect();
				     st = con.createStatement();
				     
				      String str = "select * from students WHERE ( studentClass LIKE 'FORM V-%') AND NOT ( status='DISABLED' OR status='TRANSFERRED' OR status='COMPLETE') ORDER BY Gender,firstName,studentClass ASC";
			 rs = st.executeQuery(str);
			     while(rs.next())
			     {
			    	 
			    	 String name = rs.getString("firstName");
			    	 String mname = rs.getString("middleName");
			    	 String lname = rs.getString("lastName");
			    	 // String sex = rs.getString("Gender");
			    	 String clac = rs.getString("studentClass");
			    	 String id = rs.getString("student_id");
			   	 String yea = rs.getString("admissionYear");
			   	String fullname = name + "    "+mname+"\t    " + lname;
			    	 
			        row=spreadsheet.createRow(i);
			        cell=row.createCell(0);
			        cell.setCellValue(id);
			        String sex= rs.getString("Gender");
			        if(sex.equals("FEMALE")){
			        cell=row.createCell(1);
			        cell.setCellValue("F");
			        }else if(sex.equals("MALE")){
			            cell=row.createCell(1);
			            cell.setCellValue("M"); 
			        }
			        cell=row.createCell(2); 
			        cell.setCellValue(fullname);
			    
			        cell=row.createCell(3);
			        cell.setCellValue(clac);
			       
			        cell=row.createCell(4);
			        cell.setCellValue(yea);
			 
			       
			       
			 	
			  
			        i++;
			     }
			     FileOutputStream out = new FileOutputStream(
			     new File("Form_V.xls"));
			     workbook.write(out);
			     out.close();
			     				}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);
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
	  @SuppressWarnings("unchecked")
	private void print6() {
		  wd = new WorkIndicatorDialog(null, "Fetching data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.INFORMATION);
			       tray.setTitle("List successfully Generated");
			       tray.setMessage("All FORM VI students are in the list");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       openFile("fm6");
			    
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
	 			try{
					HSSFWorkbook workbook = new HSSFWorkbook(); 
				      HSSFSheet spreadsheet = workbook
				      .createSheet("FORM VI STUDENTS");
				      HSSFRow row=spreadsheet.createRow(0);
				      HSSFCellStyle RotateStyle = workbook.createCellStyle();
				      HSSFCellStyle BoldStyle = workbook.createCellStyle();
				      RotateStyle.setRotation((short)90);
				      HSSFFont my_font = workbook.createFont();
				      Header header = spreadsheet.getHeader();
				      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)9)+"FORM I STUDENT THIS YEAR\n");

				      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				      spreadsheet.setColumnWidth(0, 3300);
				      spreadsheet.setColumnWidth(1, 1300);
				      spreadsheet.setColumnWidth(2, 7000);
				      spreadsheet.setColumnWidth(3, 5500);
				      spreadsheet.setColumnWidth(4, 2500);
				      BoldStyle.setFont(my_font);
				      RotateStyle.setFont(my_font);
				      HSSFCell cell;
				      cell=row.createCell(0);
				      cell.setCellValue("STUDENT REGNO.");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(1);
				      cell.setCellValue("SEX");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(2);
				      cell.setCellValue("STUDENT NAME");		
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(3);
				      cell.setCellValue("CLASS");
				      cell.setCellStyle(BoldStyle);
				      cell=row.createCell(4);
				      cell.setCellValue("ADMISSION YEAR");
				      cell.setCellStyle(BoldStyle);
				     
				      int i=1;
				     con = database.dbconnect();
				     st = con.createStatement();
				     
				      String str = "select * from students WHERE ( studentClass LIKE 'FORM VI-%') AND NOT ( status='DISABLED' OR status='TRANSFERRED' OR status='COMPLETE') ORDER BY Gender,firstName,studentClass ASC";
			 rs = st.executeQuery(str);
			     while(rs.next())
			     {
			    	 
			    	 String name = rs.getString("firstName");
			    	 String mname = rs.getString("middleName");
			    	 String lname = rs.getString("lastName");
			    	 // String sex = rs.getString("Gender");
			    	 String clac = rs.getString("studentClass");
			    	 String id = rs.getString("student_id");
			   	 String yea = rs.getString("admissionYear");
			   	String fullname = name + "    "+mname+"\t    " + lname;
			    	 
			        row=spreadsheet.createRow(i);
			        cell=row.createCell(0);
			        cell.setCellValue(id);
			        String sex= rs.getString("Gender");
			        if(sex.equals("FEMALE")){
			        cell=row.createCell(1);
			        cell.setCellValue("F");
			        }else if(sex.equals("MALE")){
			            cell=row.createCell(1);
			            cell.setCellValue("M"); 
			        }
			        cell=row.createCell(2); 
			        cell.setCellValue(fullname);
			    
			        cell=row.createCell(3);
			        cell.setCellValue(clac);
			       
			        cell=row.createCell(4);
			        cell.setCellValue(yea);
			 
			       
			       
			 	
			  
			        i++;
			     }
			     FileOutputStream out = new FileOutputStream(
			     new File("Form_VI.xls"));
			     workbook.write(out);
			     out.close();
			    
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, e);
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
    @SuppressWarnings("unchecked")
	private void printSuspend() {
    	 wd = new WorkIndicatorDialog(null, "Fetching data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.INFORMATION);
			       tray.setTitle("List successfully Generated");
			       tray.setMessage("All SUSPENDED students are in the list");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       openFile("susp");
			 	
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
	 			try{
	 				HSSFWorkbook workbook = new HSSFWorkbook(); 
	 			      HSSFSheet spreadsheet = workbook
	 			      .createSheet("SUSPENDED OR EXPELLED OR DEAD STUDENT");
	 			      HSSFRow row=spreadsheet.createRow(0);
	 			      HSSFCellStyle RotateStyle = workbook.createCellStyle();
	 			      HSSFCellStyle BoldStyle = workbook.createCellStyle();
	 			      RotateStyle.setRotation((short)90);
	 			      HSSFFont my_font = workbook.createFont();
	 			      
	 			      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	 			      spreadsheet.setColumnWidth(0, 3300);
	 			      spreadsheet.setColumnWidth(1, 1300);
	 			      spreadsheet.setColumnWidth(2, 7000);
	 			      spreadsheet.setColumnWidth(3, 5500);
	 			      spreadsheet.setColumnWidth(4, 2500);
	 			      BoldStyle.setFont(my_font);
	 			      RotateStyle.setFont(my_font);
	 			      HSSFCell cell;
	 			      cell=row.createCell(0);
	 			      cell.setCellValue("STUDENT REGNO.");
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(1);
	 			      cell.setCellValue("SEX");
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(2);
	 			      cell.setCellValue("STUDENT NAME");		
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(3);
	 			      cell.setCellValue("LAST CLASS");
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(4);
	 			      cell.setCellValue("YEAR");
	 			      cell.setCellStyle(BoldStyle);
	 			     
	 			      int i=1;
	 			     con = database.dbconnect();
	 			     st = con.createStatement();
	 			     
	 			      String str = "select * from students WHERE status='DISABLED' ORDER BY Gender,firstName ASC";
	 		 rs = st.executeQuery(str);
	 		     while(rs.next())
	 		     {
	 		    	 
	 		    	 String name = rs.getString("firstName");
	 		    	 String lname = rs.getString("lastName");
	 		    	 // String sex = rs.getString("Gender");
	 		    	 String last = rs.getString("studentClass");
	 		    	 String id = rs.getString("student_id");
	 		   	 String yea = rs.getString("currentYr");
	 		    	 String fullname = name + "    " + lname;
	 		    	 
	 		        row=spreadsheet.createRow(i);
	 		        cell=row.createCell(0);
	 		        cell.setCellValue(id);
	 		        String sex= rs.getString("Gender");
	 		        if(sex.equals("FEMALE")){
	 		        cell=row.createCell(1);
	 		        cell.setCellValue("F");
	 		        }else if(sex.equals("MALE")){
	 		            cell=row.createCell(1);
	 		            cell.setCellValue("M"); 
	 		        }
	 		        cell=row.createCell(2); 
	 		        cell.setCellValue(fullname);
	 		    
	 		        cell=row.createCell(3);
	 		        cell.setCellValue(last);
	 		       cell=row.createCell(4);
	 		        cell.setCellValue(yea);
	 		 
	 		       
	 		       
	 		 	
	 		  
	 		        i++;
	 		     }
	 		     FileOutputStream out = new FileOutputStream(
	 		     new File("inactive_student.xls"));
	 		     workbook.write(out);
	 		     out.close();
	 		     
	 				}catch(Exception e){
	 					JOptionPane.showMessageDialog(null, e);
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
    @SuppressWarnings("unchecked")
	private void printShifted() {
    	 wd = new WorkIndicatorDialog(null, "Fetching data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.INFORMATION);
			       tray.setTitle("List successfully Generated");
			       tray.setMessage("All TRANSFERED students are in the list");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       openFile("trans");
			 	
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
	 			try{
	 				HSSFWorkbook workbook = new HSSFWorkbook(); 
	 			      HSSFSheet spreadsheet = workbook
	 			      .createSheet("TRANSFERRED STUDENT");
	 			      HSSFRow row=spreadsheet.createRow(0);
	 			      HSSFCellStyle RotateStyle = workbook.createCellStyle();
	 			      HSSFCellStyle BoldStyle = workbook.createCellStyle();
	 			      RotateStyle.setRotation((short)90);
	 			      HSSFFont my_font = workbook.createFont();
	 			      
	 			      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	 			      spreadsheet.setColumnWidth(0, 3300);
	 			      spreadsheet.setColumnWidth(1, 1300);
	 			      spreadsheet.setColumnWidth(2, 7000);
	 			      spreadsheet.setColumnWidth(3, 6500);
	 			      spreadsheet.setColumnWidth(4, 2500);
	 			      BoldStyle.setFont(my_font);
	 			      RotateStyle.setFont(my_font);
	 			      HSSFCell cell;
	 			      cell=row.createCell(0);
	 			      cell.setCellValue("STUDENT REGNO.");
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(1);
	 			      cell.setCellValue("SEX");
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(2);
	 			      cell.setCellValue("STUDENT NAME");		
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(3);
	 			      cell.setCellValue("LAST CLASS");
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(4);
	 			      cell.setCellValue("YEAR");
	 			      cell.setCellStyle(BoldStyle);
	 			     
	 			      int i=1;
	 			     con = database.dbconnect();
	 			     st = con.createStatement();
	 			     
	 			      String str = "select * from students WHERE status='TRANSFERRED' ORDER BY Gender,firstName ASC";
	 		 rs = st.executeQuery(str);
	 		     while(rs.next())
	 		     {
	 		    	 
	 		    	 String name = rs.getString("firstName");
	 		    	 String lname = rs.getString("lastName");
	 		    	 // String sex = rs.getString("Gender");
	 		    	 String last = rs.getString("studentClass");
	 		    	 String id = rs.getString("student_id");
	 		   	 String yea = rs.getString("currentYr");
	 		    	 String fullname = name + "    " + lname;
	 		    	 
	 		        row=spreadsheet.createRow(i);
	 		        cell=row.createCell(0);
	 		        cell.setCellValue(id);
	 		        String sex= rs.getString("Gender");
	 		        if(sex.equals("FEMALE")){
	 		        cell=row.createCell(1);
	 		        cell.setCellValue("F");
	 		        }else if(sex.equals("MALE")){
	 		            cell=row.createCell(1);
	 		            cell.setCellValue("M"); 
	 		        }
	 		        cell=row.createCell(2); 
	 		        cell.setCellValue(fullname);
	 		    
	 		        cell=row.createCell(3);
	 		        cell.setCellValue(last);
	 		       cell=row.createCell(4);
	 		        cell.setCellValue(yea);
	 		 
	 		       
	 		       
	 		 	
	 		  
	 		        i++;
	 		     }
	 		     FileOutputStream out = new FileOutputStream(
	 		     new File("transferred_names.xls"));
	 		     workbook.write(out);
	 		     out.close();
	 		   
	 				}catch(Exception e){
	 					JOptionPane.showMessageDialog(null, e);
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
	@SuppressWarnings("unchecked")
	private void printStaff() {
		 wd = new WorkIndicatorDialog(null, "Fetching data...");
	 	   wd.addTaskEndNotification(result -> {
	 		  String outres = result.toString();
	          // System.out.println("nomaa "+outres);
	           if(outres.equals("1")){
	        	   TrayNotification tray = new TrayNotification();
			       tray.setNotificationType(NotificationType.INFORMATION);
			       tray.setTitle("List successfully Generated");
			       tray.setMessage("All staffs are in the list");
			       tray.setAnimationType(AnimationType.SLIDE);
			       tray.showAndDismiss(Duration.millis(4000));
			       openFile("staf");
			     
	           }
	           wd=null;
	 	   });
	 		 wd.exec("fetch", inputParam -> {
	 			try{
	 				HSSFWorkbook workbook = new HSSFWorkbook(); 
	 			      HSSFSheet spreadsheet = workbook
	 			      .createSheet("Teacher names");
	 			      HSSFRow row=spreadsheet.createRow(0);
	 			      HSSFCellStyle RotateStyle = workbook.createCellStyle();
	 			      HSSFCellStyle BoldStyle = workbook.createCellStyle();
	 			      RotateStyle.setRotation((short)90);
	 			      HSSFFont my_font = workbook.createFont();
	 			      
	 			      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	 			      spreadsheet.setColumnWidth(0, 3300);
	 			      spreadsheet.setColumnWidth(1, 1300);
	 			      spreadsheet.setColumnWidth(2, 7000);
	 			      spreadsheet.setColumnWidth(3, 6500);
	 			      spreadsheet.setColumnWidth(4, 3500);
	 			      BoldStyle.setFont(my_font);
	 			      RotateStyle.setFont(my_font);
	 			      HSSFCell cell;
	 			      cell=row.createCell(0);
	 			      cell.setCellValue("STAFF IDs.");
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(1);
	 			      cell.setCellValue("SEX");
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(2);
	 			      cell.setCellValue("TEACHERS' NAMES");		
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(3);
	 			      cell.setCellValue("STATUS");
	 			      cell.setCellStyle(BoldStyle);
	 			      cell=row.createCell(4);
	 			      cell.setCellValue("CONTACTS");
	 			      cell.setCellStyle(BoldStyle);
	 			     
	 			      int i=1;
	 			     con = database.dbconnect();
	 			     st = con.createStatement();
	 			     
	 			      String str = "select * from staffs ORDER BY Gender,firstName ASC";
	 		 rs = st.executeQuery(str);
	 		     while(rs.next())
	 		     {
	 		    	 String name = rs.getString("firstName");
	 		    	 String lname = rs.getString("lastName");
	 		    	 // String sex = rs.getString("Gender");
	 		    	 String contact = rs.getString("phone");
	 		    	 String staff = rs.getString("staff_id");
	 		   	 String statu = rs.getString("status");
	 		    	 String fullname = name + "    " + lname;
	 		    	 
	 		        row=spreadsheet.createRow(i);
	 		        cell=row.createCell(0);
	 		        cell.setCellValue(staff);
	 		        String sex= rs.getString("Gender");
	 		        if(sex.equals("FEMALE")){
	 		        cell=row.createCell(1);
	 		        cell.setCellValue("F");
	 		        }else if(sex.equals("MALE")){
	 		            cell=row.createCell(1);
	 		            cell.setCellValue("M"); 
	 		        }
	 		        cell=row.createCell(2); 
	 		        cell.setCellValue(fullname);
	 		    
	 		        cell=row.createCell(3);
	 		        cell.setCellValue(statu);
	 		       cell=row.createCell(4);
	 		        cell.setCellValue(contact);
	 		 
	 		       
	 		       
	 		 	
	 		  
	 		        i++;
	 		     }
	 		     FileOutputStream out = new FileOutputStream(
	 		     new File("staff_names.xls"));
	 		     workbook.write(out);
	 		     out.close();
	 		     
	 		    			}catch(Exception e){
	 					JOptionPane.showMessageDialog(null, e);
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
