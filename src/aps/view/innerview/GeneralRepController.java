package aps.view.innerview;


import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class GeneralRepController implements Initializable {
	LoggerM log=new LoggerM();
	   @FXML
	    private JFXTextField search;

	    @FXML
	    private RadioButton terminal;

	    @FXML
	    private ToggleGroup examtype;

	    @FXML
	    private RadioButton annual;

	    @FXML
	    private ChoiceBox<String> yearchoice;

	    @FXML
	    private ChoiceBox<String> classchoice;

	    @FXML
	    private JFXButton btnsubmit;

	    @FXML
	    private StackPane stackolevel;

	    @FXML
	    private JFXButton btnprint;

	    @FXML
	    private TableView<GenReport> tableview;

	    @FXML
	    private TableColumn<GenReport, String> idcol;

	    @FXML
	    private TableColumn<GenReport, String> namecol;

	    @FXML
	    private TableColumn<GenReport, String> classcol;
	    @FXML
	    private TableColumn<GenReport, String> section;

	    @FXML
	    private TableColumn<GenReport, String> avcol;
	    @FXML
	    private TableColumn<GenReport, String> pos;
	    @FXML
	    private AnchorPane anchor;
	    @FXML
	    private Label lb00;

	    @FXML
	    private Label lab01;

	    @FXML
	    private Label la02;

	    @FXML
	    private Label lab10;

	    @FXML
	    private Label lab11;

	    @FXML
	    private Label lab12;

	    @FXML
	    private Label lbl00;

	    @FXML
	    private Label lbl01;

	    @FXML
	    private Label lbl02;

	    @FXML
	    private Label lbl10;

	    @FXML
	    private Label lbl11;

	    @FXML
	    private Label lbl12;


	    @FXML
	    private JFXButton btnMore;
	    int space=0,grad_i=0,grad_ii=0,grad_iii=0,grad_iv=0,grad_0=0;
		  int grad_a = 0,grad_b=0,grad_c=0,grad_d=0,grad_f=0;

	    @FXML
	    private TableColumn<String, String> gradcol;
	    ObservableList<String>list = FXCollections.observableArrayList();
	    ObservableList<String>list1 = FXCollections.observableArrayList();
	    ObservableList<GenReport> searchdata;
	   
	    ObservableList<GenReport> genlist;
		 List listofgen = new ArrayList();
	    @FXML
	    private TableColumn<GenReport, String> divcol;
	    private WorkIndicatorDialog wd=null;
	 		ConnectDB database = new ConnectDB();
	 		 private Connection con;
	 		    private ResultSet rs,rs1,rsT,rsC,rsP1,rsP2;
	 		    private Statement st,st1,stC,stT,stP1,stP2;
	 		    private PreparedStatement prep;
	 		  static  String examTy;
	 		  static String hardprint;
	 		  int sheet = 0;
	 		  String darasa = null, year=null;
	 		//  double []points = new double[17];
	 		  List<Double> pointe = new ArrayList<Double>();
	 		 List<Double> gradeo = new ArrayList<Double>();
	 		 List<Integer> uzit = new ArrayList<Integer>();
	 		   /* double []grade = new double[7];
	 		    double []grade2 = new double[3];
	 		    double []uzito2 = new double[3];
	 		    double []uzito = new double[7];*/
	 		    String secti,posi;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fillClass();
		fillYear();
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
		classcol.setCellValueFactory(new PropertyValueFactory<>("clas"));
		section.setCellValueFactory(new PropertyValueFactory<>("sect"));
		pos.setCellValueFactory(new PropertyValueFactory<>("posit"));
		avcol.setCellValueFactory(new PropertyValueFactory<>("average"));
		gradcol.setCellValueFactory(new PropertyValueFactory<>("grade"));
		divcol.setCellValueFactory(new PropertyValueFactory<>("division"));
		
		search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			
			if (oldValue != null && (newValue.length() < oldValue.length())) {
		            	tableview.setItems(searchdata);
		            }
		            String value = newValue.toLowerCase();
		            ObservableList<GenReport> subentries = FXCollections.observableArrayList();

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
	        	
	        	  
	           }
	           wd=null;
	 	  });
           wd.exec("fetch", inputParam -> {
        		String []parts;
        		try{
    				con= database.dbconnect();
    				   st= con.createStatement();
    				   String query = "SELECT DISTINCT  year FROM result_from_teacher ";
    				   rs=st.executeQuery(query);
    				   	while(rs.next()){
    				   		String nam;
    				   		nam = rs.getString("year");
    				   	list1.add(nam);
    				   		
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
  
	
	}
	@FXML
    void goSubmit(ActionEvent event) {
		 grad_a=0; grad_b=0; grad_c=0; grad_d=0; grad_f=0;
		 grad_i=0; grad_ii=0; grad_iii=0; grad_iv=0; grad_0=0;
		try{
			darasa = classchoice.getSelectionModel().getSelectedItem().toString();
			year = yearchoice.getSelectionModel().getSelectedItem().toString();
		}catch(Exception e){
			JFXSnackbar bar = new JFXSnackbar(anchor);
	     	bar.show("fill all field above before\n"
	     			+ "submitting the form.\n The action cant be done with empty form",5000);
			
		      
		}
		if(!(darasa.equals("choose class here") && year.equals("choose year here")) && 
				(terminal.isSelected() || annual.isSelected())){
			try{
				con= database.dbconnect();
				st= con.createStatement();
				st1= con.createStatement();
				if(darasa.equals("FORM I")){

					String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,b.class,"
							+ "MAX(CASE WHEN b.coz_id = '013' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END )sub1, "
							+ "MAX(CASE WHEN b.coz_id = '011' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub2, "
							+ "MAX(CASE WHEN b.coz_id = '012' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub3, "
							+ "MAX(CASE WHEN b.coz_id = '021' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub4, "
							+ "MAX(CASE WHEN b.coz_id = '036' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub5, "
							+ "MAX(CASE WHEN b.coz_id = '022' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub6, "
							+ "MAX(CASE WHEN b.coz_id = '031' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub7, "
							+ "MAX(CASE WHEN b.coz_id = '032' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub8, "
							+ "MAX(CASE WHEN b.coz_id = '052' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub9, "
							+ "MAX(CASE WHEN b.coz_id = '053' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub10, "
							+ "MAX(CASE WHEN b.coz_id = '033' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub11, "
							+ "MAX(CASE WHEN b.coz_id = '041' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub12, "
							+ "MAX(CASE WHEN b.coz_id = '015' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub13, "
							+ "MAX(CASE WHEN b.coz_id = '014' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub14, "
							+ "MAX(CASE WHEN b.coz_id = '051' AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub15 "
							+ "FROM students a "
							+ "INNER JOIN result_from_teacher b "
							+ "ON a.student_id = b.stu_id WHERE NOT (a.status='DISABLED') AND (b.class='FORM IA' OR b.class='FORM IB' OR b.class='FORM IC' OR b.class='FORM ID' OR b.class='FORM IE' OR b.class='FORM IF' OR b.class='FORM IG' OR b.class='FORM IH') AND b.year='"+year+"' "
							+ "GROUP BY a.student_id "
							+ "ORDER BY a.Gender ASC,a.firstName ASC ";		
					rs=st.executeQuery(query);
					rs1=st1.executeQuery(query);
					
					hardprint = darasa+"-"+year+"-"+examTy;
					fillTable(darasa,year,examTy);
				}else if(darasa.equals("FORM II")){
					String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,b.class,"
							+ "MAX(CASE WHEN b.coz_id = '013' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END )sub1, "
							+ "MAX(CASE WHEN b.coz_id = '011' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub2, "
							+ "MAX(CASE WHEN b.coz_id = '012' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub3, "
							+ "MAX(CASE WHEN b.coz_id = '021' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub4, "
							+ "MAX(CASE WHEN b.coz_id = '036' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub5, "
							+ "MAX(CASE WHEN b.coz_id = '022' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub6, "
							+ "MAX(CASE WHEN b.coz_id = '031' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub7, "
							+ "MAX(CASE WHEN b.coz_id = '032' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub8, "
							+ "MAX(CASE WHEN b.coz_id = '052' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub9, "
							+ "MAX(CASE WHEN b.coz_id = '053' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub10, "
							+ "MAX(CASE WHEN b.coz_id = '033' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub11, "
							+ "MAX(CASE WHEN b.coz_id = '041' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub12, "
							+ "MAX(CASE WHEN b.coz_id = '015' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub13, "
							+ "MAX(CASE WHEN b.coz_id = '014' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub14, "
							+ "MAX(CASE WHEN b.coz_id = '051' AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub15 "
							+ "FROM students a "
							+ "INNER JOIN result_from_teacher b "
							+ "ON a.student_id = b.stu_id WHERE NOT (a.status='DISABLED') AND (b.class='FORM IIA' OR b.class='FORM IIB' OR b.class='FORM IIC' OR b.class='FORM IID' OR b.class='FORM IIE' OR b.class='FORM IIF' OR b.class='FORM IIG' OR b.class='FORM IIH') AND b.year='"+year+"' "
							+ "GROUP BY a.student_id "
							+ "ORDER BY a.Gender ASC,a.firstName ASC ";	
					rs=st.executeQuery(query);
					fillTable(darasa,year,examTy);
					rs1=st1.executeQuery(query);
					hardprint = darasa+"-"+year+"-"+examTy;
				}else if(darasa.equals("FORM III")){
					String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,b.class,"
							+ "MAX(CASE WHEN b.coz_id = '013' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END )sub1, "
							+ "MAX(CASE WHEN b.coz_id = '011' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub2, "
							+ "MAX(CASE WHEN b.coz_id = '012' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub3, "
							+ "MAX(CASE WHEN b.coz_id = '021' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub4, "
							+ "MAX(CASE WHEN b.coz_id = '036' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub5, "
							+ "MAX(CASE WHEN b.coz_id = '022' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub6, "
							+ "MAX(CASE WHEN b.coz_id = '031' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END )sub7, "
							+ "MAX(CASE WHEN b.coz_id = '032' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub8, "
							+ "MAX(CASE WHEN b.coz_id = '052' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub9, "
							+ "MAX(CASE WHEN b.coz_id = '053' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub10, "
							+ "MAX(CASE WHEN b.coz_id = '033' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub11, "
							+ "MAX(CASE WHEN b.coz_id = '041' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub12, "
							+ "MAX(CASE WHEN b.coz_id = '222' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub13, "
							+ "MAX(CASE WHEN b.coz_id = '015' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub14, "
							+ "MAX(CASE WHEN b.coz_id = '014' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub15, "
							+ "MAX(CASE WHEN b.coz_id = '051' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub16 "
							+ "FROM students a "
							+ "INNER JOIN result_from_teacher b "
							+ "ON a.student_id = b.stu_id WHERE NOT (a.status='DISABLED') AND b.class LIKE '"+darasa+"%' AND b.year='"+year+"' "
							+ "GROUP BY a.student_id "
							+ "ORDER BY a.Gender ASC,a.firstName ASC ";
			rs=st.executeQuery(query);
			rs1=st1.executeQuery(query);
			hardprint = darasa+"-"+year+"-"+examTy;
			fillTable(darasa,year,examTy);
				}else if(darasa.equals("FORM IV")){
					String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,b.class,"
							+ "MAX(CASE WHEN b.coz_id = '013' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END )sub1, "
							+ "MAX(CASE WHEN b.coz_id = '011' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub2, "
							+ "MAX(CASE WHEN b.coz_id = '012' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub3, "
							+ "MAX(CASE WHEN b.coz_id = '021' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub4, "
							+ "MAX(CASE WHEN b.coz_id = '036' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub5, "
							+ "MAX(CASE WHEN b.coz_id = '022' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub6, "
							+ "MAX(CASE WHEN b.coz_id = '031' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END )sub7, "
							+ "MAX(CASE WHEN b.coz_id = '032' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub8, "
							+ "MAX(CASE WHEN b.coz_id = '052' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub9, "
							+ "MAX(CASE WHEN b.coz_id = '053' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub10, "
							+ "MAX(CASE WHEN b.coz_id = '033' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub11, "
							+ "MAX(CASE WHEN b.coz_id = '041' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub12, "
							+ "MAX(CASE WHEN b.coz_id = '222' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub13, "
							+ "MAX(CASE WHEN b.coz_id = '015' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub14, "
							+ "MAX(CASE WHEN b.coz_id = '014' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub15, "
							+ "MAX(CASE WHEN b.coz_id = '051' AND b.class LIKE '"+darasa+"%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub16 "
							+ "FROM students a "
							+ "INNER JOIN result_from_teacher b "
							+ "ON a.student_id = b.stu_id WHERE NOT (a.status='DISABLED') AND b.class LIKE '"+darasa+"%' AND b.year='"+year+"' "
							+ "GROUP BY a.student_id "
							+ "ORDER BY a.Gender ASC,a.firstName ASC ";		
					rs=st.executeQuery(query);
					rs1=st1.executeQuery(query);
					hardprint = darasa+"-"+year+"-"+examTy;
					fillTable(darasa,year,examTy);
				}else if(darasa.equals("FORM V")){
					String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,b.class,"
							+ "MAX(CASE WHEN b.coz_id = '111' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END )sub1, "
							+ "MAX(CASE WHEN b.coz_id = '112' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub2, "
							+ "MAX(CASE WHEN b.coz_id = '113' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub3, "
							+ "MAX(CASE WHEN b.coz_id = '122' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub4, "
							+ "MAX(CASE WHEN b.coz_id = '131' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub5, "
							+ "MAX(CASE WHEN b.coz_id = '132' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub6, "
							+ "MAX(CASE WHEN b.coz_id = '133' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END )sub7, "
							+ "MAX(CASE WHEN b.coz_id = '151' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub8, "
							+ "MAX(CASE WHEN b.coz_id = '141' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub9, "
							+ "MAX(CASE WHEN b.coz_id = '121' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub10, "
							+ "MAX(CASE WHEN b.coz_id = '142' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub11, "
							+ "MAX(CASE WHEN b.coz_id = '155' AND b.class LIKE '%RM V-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub12 "
							+ "FROM students a "
							+ "INNER JOIN result_from_teacher b "
							+ "ON a.student_id = b.stu_id WHERE NOT (a.status='DISABLED') AND b.class LIKE '%RM V-%' AND b.year='"+year+"' "
							+ "GROUP BY a.student_id "
							+ "ORDER BY a.Gender ASC,a.firstName ASC ";		
					rs=st.executeQuery(query);
					rs1=st1.executeQuery(query);
					hardprint = darasa+"-"+year+"-"+examTy;
					fillTable(darasa,year,examTy);
				}else if(darasa.equals("FORM VI")){
					String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,b.class,"
							+ "MAX(CASE WHEN b.coz_id = '111' AND b.class LIKE '%RM VI%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END )sub1, "
							+ "MAX(CASE WHEN b.coz_id = '112' AND b.class LIKE '%RM VI%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub2, "
							+ "MAX(CASE WHEN b.coz_id = '113' AND b.class LIKE '%RM VI%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub3, "
							+ "MAX(CASE WHEN b.coz_id = '122' AND b.class LIKE '%RM VI%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub4, "
							+ "MAX(CASE WHEN b.coz_id = '131' AND b.class LIKE '%RM VI%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub5, "
							+ "MAX(CASE WHEN b.coz_id = '132' AND b.class LIKE '%RM VI%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub6, "
							+ "MAX(CASE WHEN b.coz_id = '133' AND b.class LIKE '%RM VI%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END )sub7, "
							+ "MAX(CASE WHEN b.coz_id = '151' AND b.class LIKE '%RM VI%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub8, "
							+ "MAX(CASE WHEN b.coz_id = '141' AND b.class LIKE '%RM VI%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub9, "
							+ "MAX(CASE WHEN b.coz_id = '121' AND b.class LIKE '%RM VI%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub10, "
							+ "MAX(CASE WHEN b.coz_id = '142' AND b.class LIKE '%RM VI-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub11, "
							+ "MAX(CASE WHEN b.coz_id = '155' AND b.class LIKE '%RM VI-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub12 "
							+ "FROM students a "
							+ "INNER JOIN result_from_teacher b "
							+ "ON a.student_id = b.stu_id WHERE NOT (a.status='DISABLED') AND b.class LIKE '%RM VI%' AND b.year='"+year+"' "
							+ "GROUP BY a.student_id "
							+ "ORDER BY a.Gender ASC,a.firstName ASC ";	
					rs=st.executeQuery(query);
					rs1=st1.executeQuery(query);
					hardprint = darasa+"-"+year+"-"+examTy;
					fillTable(darasa,year,examTy);
				}
			}catch(SQLException sq){
				
			}
		}else{
			JFXSnackbar bar = new JFXSnackbar(anchor);
	     	bar.show("fill all field above before\n"
	     			+ "submitting the form.\n The action cant be done with empty form",5000);
		}
		log.writter("Generate general results (mkeka) for "+darasa+" "+examTy+" "+year);
		
    }
	@SuppressWarnings("unchecked")
	private void fillTable(String darasa2,String year,String examTy) {
		System.out.println(darasa2+"\t"+year+"\t"+examTy);
		if(darasa2.equals("FORM I")){
			wd = new WorkIndicatorDialog(null, "Loading ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
	 	        	  lb00.setText("GRADE A-"); lab01.setText("GRADE B-"); la02.setText("GRADE C-"); 
						lab10.setText(""+grad_a); lab11.setText(""+grad_b); lab12.setText(""+grad_c);
						
						lbl00.setText("GRADE D-"); lbl01.setText("GRADE F-");
						lbl10.setText(""+grad_d); lbl11.setText(""+grad_f); lbl12.setText("");
				
						genlist = FXCollections.observableList(listofgen);
			        	   tableview.setItems(genlist);
			        	   searchdata=tableview.getItems();
			         
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		 String classes = "(class='FORM IA' OR class='FORM IB' OR class='FORM IC' OR class='FORM ID' OR class='FORM IE'"
		 	 		 		+ " OR class='FORM IF' OR class='FORM IG' OR class='FORM IH')";
	 	 		try {
					  for(int i=0; i<tableview.getItems().size(); i++){
				    		
				    		tableview.getItems().clear();
				    	}
					 
					  listofgen.clear();
					 
					  while(rs.next()){
						pointe.clear();
						uzit.clear();
						String gend;
						String stuID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("class");
					   	 String sex= rs.getString("Gender");
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					        stP1 = con.createStatement();
					        String mrow = "SET @row_number=0 ";
					      
					        String posQ =  "SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id "
									+ "FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher "
									+ "WHERE year='"+year+"' AND "+classes+" and type='"+examTy+"' "
											+ "group by stu_id order by wastani DESC) as tablewastan) AS MM WHERE stu_id='"+stuID+"'";
					        		stP1.execute(mrow);
					        rsP1 = stP1.executeQuery(posQ);
					        if(rsP1.next()){
					        	posi = rsP1.getString("ROW");
					        }
					        stP1.close(); rsP1.close();
					    	double civ,hist,geo,kisw,comp,eng,phy=-55.0,chem=-55.0,commerc,bkee,bio,math,islam,food,divnty;
					     	 if(rs.getString("sub1")== null){
					     	
					     	 }else{
					     		geo = Double.parseDouble(rs.getString("sub1"));
					     		pointe.add(geo);
					     	 }
					     	 if(rs.getString("sub2")== null){
					     
					     	 }else{
					     		civ = Double.parseDouble(rs.getString("sub2"));
					     		pointe.add(civ);
					     		
					     	 }
					     	 if(rs.getString("sub3")== null){
					     	
					     	 }else{
					     		hist = Double.parseDouble(rs.getString("sub3"));
					     		pointe.add(hist);
					     	 }
					     	 if(rs.getString("sub4")== null){
					     		
					     	 }else{
					     		kisw = Double.parseDouble(rs.getString("sub4"));
					     		pointe.add(kisw);
					     	 }
					     	 if(rs.getString("sub5")== null){
					     	
					     	 }else{
					     		comp = Double.parseDouble(rs.getString("sub5"));
					     		pointe.add(comp);
					     	 }
					     	 if(rs.getString("sub6")== null){
					     		
					     	 }else{
					     		eng = Double.parseDouble(rs.getString("sub6"));
					     		pointe.add(eng);
					     	 }
					     	 if(rs.getString("sub7")== null){
					     		
					     	 }else{
					     		phy = Double.parseDouble(rs.getString("sub7"));
					     		pointe.add(phy);
					     		
					     	 }
					     	 if(rs.getString("sub8")== null){
					     	
					     		
					     	 }else{
					     		chem = Double.parseDouble(rs.getString("sub8"));
					     		pointe.add(chem);
					     		
					     	 }
					     	 if(rs.getString("sub9")== null){
					     		
					     	 }else{
					     		commerc = Double.parseDouble(rs.getString("sub9"));
					     		pointe.add(commerc);
					     	 }
					     	 if(rs.getString("sub10")== null){
					     		
					     	 }else{
					     		bkee = Double.parseDouble(rs.getString("sub10"));
					     		pointe.add(bkee);
					     	 }
					     	 if(rs.getString("sub11")== null){
					     		
					     	 }else{
					     		bio = Double.parseDouble(rs.getString("sub11"));
					     		pointe.add(bio);
					     	 }
					     	 if(rs.getString("sub12")== null){
					     		
					     	 }else{
					     		math = Double.parseDouble(rs.getString("sub12"));
					     		pointe.add(math);
					     	 } 	 if(rs.getString("sub13")== null){
					     		
					     	 }else{
					     		islam = Double.parseDouble(rs.getString("sub13"));
					     		pointe.add(islam);
					     	 }
					     	 if(rs.getString("sub14")== null){
					     	
					     	 }else{
					     		divnty = Double.parseDouble(rs.getString("sub14"));
					     		pointe.add(divnty);
					     	 }
					     	 if(rs.getString("sub15")== null){
					     	
					     	 }else{
					     		food = Double.parseDouble(rs.getString("sub15"));
					     		pointe.add(food);
					     	 }
					     	
					     	Collections.sort(pointe);
					    	double	sum = 0;
					     	 int counter = 0;
					      	for(int k=0; k<pointe.size(); k++){
					      			sum+=pointe.get(k);
					      			 counter++;
					      	
					      	}
					      	
					      	if(chem != -55.0 && phy != -55.0){
					      	
					      		secti = "SCIENCE";
					      	}else{
					      		secti = "ARTS";
					      	}
					      String showAver = null;
					    double avg = Math.round((sum/counter)*100.0)/100.0;
					    if(avg >=74.5 && avg<=100){
					   	showAver = "A";
							grad_a++;
							}else if(avg >=64.5 && avg<74.5){
								showAver = "B";
								grad_b++;
							}else if(avg >=44.5 && avg<64.5){
								showAver = "C";
								grad_c++;
							}else if(avg >=29.5 && avg<44.5){
								showAver = "D";
								grad_d++;
							}else if(avg >=0 && avg<29.5){
								showAver = "F";
								grad_f++;
							}else{
								System.out.println("ERROR ON FORM I AVERAGE");
							}
					    String ave = Double.toString(avg);
					    //*****
					//	grade = Arrays.copyOfRange(points, 0, 7);
						//gradeo = pointe.subList(0, 7);
				     	if(pointe.size() < 7){
				    		listofgen.add(new GenReport(stuID,name,studclas,secti,posi,ave,showAver,"Incomplete")); 
				    		System.out.println("MARKS:"+pointe);
				     	}else{
				     		System.out.println("MARKS:"+pointe);
				     for(int j=0; j<pointe.size(); j++){
				   	

				   		if(pointe.get(j) >=74.5 && pointe.get(j)<=100){
				   			uzit.add(1);
				   		}else if(pointe.get(j) >=64.5 && pointe.get(j)<74.5){
				   			uzit.add(2);
				   		}else if(pointe.get(j) >=44.5 && pointe.get(j)<64.5){
				   			uzit.add(3);
				   		}else if(pointe.get(j) >=29.5 && pointe.get(j)<44.5){
				   			uzit.add(4);
				   		}else if(pointe.get(j) >=0 && pointe.get(j)<29.5){
				   			uzit.add(5);
				   		}else{
				   			//JOptionPane.showMessageDialog(null, "One of the marks in the sheet to be generated \n Exceeds 100%. please correct it during uploads.","ERROR",JOptionPane.WARNING_MESSAGE);
				   		}
				   	   
				     }
				     int 	sumP = 0;
				     String divi=null,divPoint=null;
				   Collections.sort(uzit);
				   uzit = uzit.subList(0, 7);
				   System.out.println(uzit);
				    	for(int k=0; k<uzit.size(); k++){
				    		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
				    		sumP+=uzit.get(k);
				    	}
				    	 divPoint = Integer.toString(sumP);
				    
						if(sumP >=7 && sumP<=17){
							divi = "I";
							 grad_i++;
						}else if(sumP >=18 && sumP<=21){
							divi = "II";
							 grad_ii++;
						}else if(sumP >=22 && sumP<=25){
							divi = "III";
							 grad_iii++;
						}else if(sumP >=26 && sumP<=33){
							divi = "IV";
							 grad_iv++;
						}else if(sumP >=34 && sumP<=35){
							divi = "0";
							 grad_0++;
						}
					    //****
					    listofgen.add(new GenReport(stuID,name,studclas,secti,posi,ave,showAver, divi+" POINT "+divPoint));
				     	}
				     	}
					
					st.close();
					rs.close();
					stP1.close();
					rsP1.close();
				//con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		             
		           return new Integer(1);
		           
		           
		        });
		
		}else if(darasa2.equals("FORM II")){
			wd = new WorkIndicatorDialog(null, "Loading ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
	 	        	  
						genlist = FXCollections.observableList(listofgen);
			        	   tableview.setItems(genlist);  
			        	   searchdata=tableview.getItems();
			        	   lb00.setText("DIVISION I-"); lab01.setText("DIVISION II-"); la02.setText("DIVISION III-"); 
							lab10.setText(""+grad_i); lab11.setText(""+grad_ii); lab12.setText(""+grad_iii);
							
							lbl00.setText("DIVISION IV-"); lbl01.setText("DIVISION 0-");
							lbl10.setText(""+grad_iv); lbl11.setText(""+grad_0); lbl12.setText("");
							searchdata=tableview.getItems();
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 	
	 	 		try {
					for(int i=0; i<tableview.getItems().size(); i++){
			    		
			    		tableview.getItems().clear();
			    	}
					// int space=0,grad_i=0,grad_ii=0,grad_iii=0,grad_iv=0,grad_0=0;
					
					 listofgen.clear();
					 
					 while(rs.next()){
						 pointe.clear();
						 uzit.clear();
						String stID;
						String gend;
								stID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("class");
					   	 String sex= rs.getString("Gender");
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					        stP1 = con.createStatement();
					        String mrow = "SET @row_number=0 ";
					      
					        String posQ =  "SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id "
									+ "FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher "
									+ "WHERE year='"+year+"' AND (class='"+darasa2+"A' OR class='"+darasa2+"B' OR class='"+darasa2+"C' OR "
									+ "class='"+darasa2+"D' OR class='"+darasa2+"E' OR class='"+darasa2+"F' OR class='"+darasa2+"G') and type='"+examTy+"' "
											+ "group by stu_id order by wastani DESC) as tablewastan) AS MM WHERE stu_id='"+stID+"'";
					        		stP1.execute(mrow);
					        rsP1 = stP1.executeQuery(posQ);
					        if(rsP1.next()){
					        	posi = rsP1.getString("ROW");
					        }
					        stP1.close(); rsP1.close();
					        double civ,hist,geo,kisw,comp,eng,phy=-55.0,chem=-55.0,commerc,bkee,bio,math,islam,food,divnty,lite;
					      	 if(rs.getString("sub1")== null){
					   
					      		
					      	 }else{
					      		geo = Double.parseDouble(rs.getString("sub1"));
					     		pointe.add(geo);
					      		
					      	 }
					      	 if(rs.getString("sub2")== null){
					      		
					      	 }else{
					      		civ = Double.parseDouble(rs.getString("sub2"));
					     		pointe.add(civ);
					      		
					      	 }
					      	 if(rs.getString("sub3")== null){
					      	
					      	 }else{
					      		hist = Double.parseDouble(rs.getString("sub3"));
					     		pointe.add(hist);
					      	 }
					      	 if(rs.getString("sub4")== null){
					      		
					      	 }else{
					      		kisw = Double.parseDouble(rs.getString("sub4"));
					     		pointe.add(kisw);
					      	 }
					      	 if(rs.getString("sub5")== null){
					      	
					      	 }else{
					      		comp = Double.parseDouble(rs.getString("sub5"));
					     		pointe.add(comp);
					      	 }
					      	 if(rs.getString("sub6")== null){
					      		
					      	 }else{
					      		eng = Double.parseDouble(rs.getString("sub6"));
					     		pointe.add(eng);
					      	 }
					      	 if(rs.getString("sub7")== null){
					      		
					      		
					      	 }else{
					      		phy = Double.parseDouble(rs.getString("sub7"));
					     		pointe.add(phy);
					      		
					      	 }
					      	 if(rs.getString("sub8")== null){
					      		
					      		
					      	 }else{
					      		chem = Double.parseDouble(rs.getString("sub8"));
					     		pointe.add(chem);
					      		
					      	 }
					      	 if(rs.getString("sub9")== null){
					      		
					      	 }else{
					      		commerc = Double.parseDouble(rs.getString("sub9"));
					     		pointe.add(commerc);
					      	 }
					      	 if(rs.getString("sub10")== null){
					      		
					      	 }else{
					      		bkee = Double.parseDouble(rs.getString("sub10"));
					     		pointe.add(bkee);
					      	 }
					      	 if(rs.getString("sub11")== null){
					      		
					      	 }else{
					      		bio = Double.parseDouble(rs.getString("sub11"));
					     		pointe.add(bio);
					      	 }
					      	 if(rs.getString("sub12")== null){
					      		
					      	 }else{
					      		math = Double.parseDouble(rs.getString("sub12"));
					     		pointe.add(math);
					      	 } 	 if(rs.getString("sub15")== null){
					      		 
					      	 }else{
					      		food = Double.parseDouble(rs.getString("sub15"));
					     		pointe.add(food);
					      	 }
					      	 if(rs.getString("sub14")== null){
					      		
					      	 }else{
					      		islam = Double.parseDouble(rs.getString("sub14"));
					     		pointe.add(islam);
					      	 }
					      	 if(rs.getString("sub15")== null){
					      		
					      	 }else{
					      		divnty= Double.parseDouble(rs.getString("sub15"));
					     		pointe.add(divnty);
					      	 }	/* if(rs.getString("sub13")== null){
					      		 lite =00.0;
					      		points[15]=lite;
					      	 }else{
					      		lite = Double.parseDouble(rs.getString("sub13"));
					      		points[15]=lite;
					      	 }*/
					      	if(chem != -55.0 && phy != -55.0){
					      		secti = "SCIENCE";
					      		}else{
					      			secti = "ARTS";
					      			}
					      	 /*
					      	*/
					      
					     	
					   double sumAv = 0;
					   int counterAv = 0;
					   for(int k=0; k<pointe.size(); k++){
					   		
					   			sumAv+=pointe.get(k);
					   			 counterAv++;
					   			
					   	
					   		
					   	}
					   	
					   double avgMe = Math.round((sumAv/counterAv)*100.0)/100.0;
					   String avere = Double.toString(avgMe);
					   /*
					    * GRADES FROM AVERAGE
					    */
						String divi = null;
					   String showAver = null;
					   String divPoint  = null;
					   if(avgMe >=74.5 && avgMe<=100){
						   	showAver = "A";
								//grad_a++;
								}else if(avgMe >=64.5 && avgMe<74.5){
									showAver = "B";
									//grad_b++;
								}else if(avgMe >=44.5 && avgMe<64.5){
									showAver = "C";
									//grad_c++;
								}else if(avgMe >=29.5 && avgMe<44.5){
									showAver = "D";
									//grad_d++;
								}else if(avgMe >=0 && avgMe<29.5){
									showAver = "F";
									//grad_f++;
								}else{
									
								}
					     //	grade = Arrays.copyOfRange(points, 0, 7);
					     	if(pointe.size()<7){
					    		listofgen.add(new GenReport(stID,name,studclas,secti,posi,avere,showAver,"Incomplete")); 
					     	}else{
					     for(int j=0; j<pointe.size(); j++){
					   	//	JOptionPane.showMessageDialog(null, grade[i]);

					   		if(pointe.get(j) >=74.5 && pointe.get(j)<=100){
					   			uzit.add(1);
					   		}else if(pointe.get(j) >=64.5 && pointe.get(j)<74.5){
					   			uzit.add(2);
					   		}else if(pointe.get(j) >=44.5 && pointe.get(j)<64.5){
					   			uzit.add(3);
					   		}else if(pointe.get(j) >=29.5 && pointe.get(j)<44.5){
					   			uzit.add(4);
					   		}else if(pointe.get(j) >=0 && pointe.get(j)<29.5){
					   			uzit.add(5);
					   		}else{
					   			//JOptionPane.showMessageDialog(null, "One of the marks in the sheet to be generated \n Exceeds 100%. please correct it during uploads.","ERROR",JOptionPane.WARNING_MESSAGE);
					   		}
					   	   
					     }
					     int	sum = 0;
					     Collections.sort(uzit);
					     uzit = uzit.subList(0, 7);
					    	for(int k=0; k<uzit.size(); k++){
					    		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
					    		sum+=uzit.get(k);
					    	}
					    	 divPoint = Integer.toString(sum);
					    
							if(sum >=7 && sum<=17){
								divi = "I";
								 grad_i++;
							}else if(sum >=18 && sum<=21){
								divi = "II";
								 grad_ii++;
							}else if(sum >=22 && sum<=25){
								divi = "III";
								 grad_iii++;
							}else if(sum >=26 && sum<=33){
								divi = "IV";
								 grad_iv++;
							}else if(sum >=34 && sum<=35){
								divi = "0";
								 grad_0++;
							}
							
					     listofgen.add(new GenReport(stID,name,studclas,secti,posi,avere,showAver, divi+" POINT "+divPoint));
					     	}
					     	}
					
					st.close();
					rs.close();
					stP1.close();
					rsP1.close();
					//con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		             
		           return new Integer(1);
		           
		           
		        });
		
		}else if(darasa2.equals("FORM III")){
			wd = new WorkIndicatorDialog(null, "Loading ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
	 	        	
						genlist = FXCollections.observableList(listofgen);
			        	   tableview.setItems(genlist);  
			        	   lb00.setText("DIVISION I-"); lab01.setText("DIVISION II-"); la02.setText("DIVISION III-"); 
							lab10.setText(""+grad_i); lab11.setText(""+grad_ii); lab12.setText(""+grad_iii);
							
							lbl00.setText("DIVISION IV-"); lbl01.setText("DIVISION 0-");
							lbl10.setText(""+grad_iv); lbl11.setText(""+grad_0); lbl12.setText("");
							searchdata=tableview.getItems();
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		 String classes = "(class LIKE 'FORM III%')";
	 	 		try {
					for(int i=0; i<tableview.getItems().size(); i++){
			    		
			    		tableview.getItems().clear();
			    	}
					listofgen.clear();
					
					 while(rs.next()){
						 uzit.clear();
						 pointe.clear();
						String stID;
						String gend;
								stID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("class");
					   	 String sex= rs.getString("Gender");
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					        stP1 = con.createStatement();
					        String mrow = "SET @row_number=0 ";
					      
					        String posQ =  "SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id "
									+ "FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher "
									+ "WHERE year='"+year+"' AND "+classes+" and type='"+examTy+"' "
											+ "group by stu_id order by wastani DESC) as tablewastan) AS MM WHERE stu_id='"+stID+"'";
					        		stP1.execute(mrow);
					        rsP1 = stP1.executeQuery(posQ);
					        if(rsP1.next()){
					        	posi = rsP1.getString("ROW");
					        }
					        stP1.close(); rsP1.close();
					        double civ,hist,geo,kisw,comp,eng,phy=-55.0,chem=-55.0,commerc,bkee,bio,math,islam,food,divnty,lite;
					      	 if(rs.getString("sub1")== null){
					      		
					      	 }else{
					      		geo = Double.parseDouble(rs.getString("sub1"));
					      		pointe.add(geo);
					      		
					      	 }
					      	 if(rs.getString("sub2")== null){
					    
					      	 }else{
					      		civ = Double.parseDouble(rs.getString("sub2"));
					      		pointe.add(civ);
					      		
					      	 }
					      	 if(rs.getString("sub3")== null){
					      	
					      	 }else{
					      		hist = Double.parseDouble(rs.getString("sub3"));
					      		pointe.add(hist);
					      	 }
					      	 if(rs.getString("sub4")== null){
					      	
					      	 }else{
					      		kisw = Double.parseDouble(rs.getString("sub4"));
					      		pointe.add(kisw);
					      	 }
					      	 if(rs.getString("sub5")== null){
					      	
					      	 }else{
					      		comp = Double.parseDouble(rs.getString("sub5"));
					      		pointe.add(comp);
					      	 }
					      	 if(rs.getString("sub6")== null){
					      		
					      	 }else{
					      		eng = Double.parseDouble(rs.getString("sub6"));
					      		pointe.add(eng);
					      	 }
					      	 if(rs.getString("sub7")== null){
					      	
					      		
					      	 }else{
					      		phy = Double.parseDouble(rs.getString("sub7"));
					      		pointe.add(phy);
					      		
					      	 }
					      	 if(rs.getString("sub8")== null){
					    
					      		
					      	 }else{
					      		chem = Double.parseDouble(rs.getString("sub8"));
					      		pointe.add(chem);
					      		
					      	 }
					      	 if(rs.getString("sub9")== null){
					      	
					      	 }else{
					      		commerc = Double.parseDouble(rs.getString("sub9"));
					      		pointe.add(commerc);
					      	 }
					      	 if(rs.getString("sub10")== null){
					      	
					      	 }else{
					      		bkee = Double.parseDouble(rs.getString("sub10"));
					      		pointe.add(bkee);
					      	 }
					      	 if(rs.getString("sub11")== null){
					      
					      	 }else{
					      		bio = Double.parseDouble(rs.getString("sub11"));
					      		pointe.add(bio);
					      	 }
					      	 if(rs.getString("sub12")== null){
					      	
					      	 }else{
					      		math = Double.parseDouble(rs.getString("sub12"));
					      		pointe.add(math);
					      	 } 	 if(rs.getString("sub16")== null){
					      	
					      	 }else{
					      		food = Double.parseDouble(rs.getString("sub16"));
					      		pointe.add(food);
					      	 }
					      	 if(rs.getString("sub14")== null){
					      	
					      	 }else{
					      		islam = Double.parseDouble(rs.getString("sub14"));
					      		pointe.add(islam);
					      	 }
					      	 if(rs.getString("sub15")== null){
					      	
					      	 }else{
					      		divnty = Double.parseDouble(rs.getString("sub15"));
					      		pointe.add(divnty);
					      	 }	 if(rs.getString("sub13")== null){
					      		
					      	 }else{
					      		lite = Double.parseDouble(rs.getString("sub13"));
					      		pointe.add(lite);
					      	 }
					      	if(chem != -55.0 && phy != -55.0){
					      		secti = "SCIENCE";
					      	}else{
					      		secti = "ARTS";
					      	}
					      	 /*
					      	*/
					      
					    Collections.sort(pointe);
					   double sumAv = 0;
					   int counterAv = 0;
					   for(int k=0; k<pointe.size(); k++){
					   		
					   			sumAv+=pointe.get(k);
					   			 counterAv++;
						
					   	}
					   	
					   double avgMe = Math.round((sumAv/counterAv)*100.0)/100.0;
					   String avere = Double.toString(avgMe);
					   String divPoint = null;
				    	String divi = null;
					   /*
					    * GRADES FROM AVERAGE
					    */
					   String showAver = null;
					   if(avgMe >=74.5 && avgMe<=100){
						   	showAver = "A";
								//grad_a++;
								}else if(avgMe >=64.5 && avgMe<74.5){
									showAver = "B";
									//grad_b++;
								}else if(avgMe >=44.5 && avgMe<64.5){
									showAver = "C";
									//grad_c++;
								}else if(avgMe >=29.5 && avgMe<44.5){
									showAver = "D";
									//grad_d++;
								}else if(avgMe >=0 && avgMe<29.5){
									showAver = "F";
									//grad_f++;
								}else{
									System.out.println("ERROR ON FORM I AVERAGE");
								}
					   
					    	if(pointe.size() < 7){
					    		listofgen.add(new GenReport(stID,name,studclas,secti,posi,avere,showAver,"Incomplete")); 
					     	}else{
					     for(int j=0; j<pointe.size(); j++){
					   	//	JOptionPane.showMessageDialog(null, grade[i]);

					   		if(pointe.get(j) >=74.5 && pointe.get(j)<=100){
					   			uzit.add(1);
					   		}else if(pointe.get(j) >=64.5 && pointe.get(j)<74.5){
					   			uzit.add(2);
					   		}else if(pointe.get(j) >=44.5 && pointe.get(j)<64.5){
					   			uzit.add(3);
					   		}else if(pointe.get(j) >=29.5 && pointe.get(j)<44.5){
					   			uzit.add(4);
					   		}else if(pointe.get(j) >=0 && pointe.get(j)<29.5){
					   			uzit.add(5);
					   		}else{
					   			//JOptionPane.showMessageDialog(null, "One of the marks in the sheet to be generated \n Exceeds 100%. please correct it during uploads.","ERROR",JOptionPane.WARNING_MESSAGE);
					   		}
					    
					     }
						 int	sum = 0;
						 Collections.sort(uzit);
						 uzit = uzit.subList(0,7);
					    	for(int k=0; k<uzit.size(); k++){
					    		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
					    		sum+=uzit.get(k);
					    	}
					    	 divPoint = Integer.toString(sum);
					    	
							if(sum >=7 && sum<=17){
								divi = "I";
								 grad_i++;
							}else if(sum >=18 && sum<=21){
								divi = "II";
								 grad_ii++;
							}else if(sum >=22 && sum<=25){
								divi = "III";
								 grad_iii++;
							}else if(sum >=26 && sum<=33){
								divi = "IV";
								 grad_iv++;
							}else if(sum >=34 && sum<=35){
								divi = "0";
								 grad_0++;
							}
						
							listofgen.add(new GenReport(stID,name,studclas,secti,posi,avere,showAver, divi+" POINT "+divPoint));
					     	}
					 }
				
					st.close();
					rs.close();
					stP1.close();
					rsP1.close();
					//con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		             
		           return new Integer(1);
		           
		           
		        });
		
		}else if(darasa2.equals("FORM IV")){
			wd = new WorkIndicatorDialog(null, "Loading ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
	 	        	  
						genlist = FXCollections.observableList(listofgen);
			        	   tableview.setItems(genlist);  
			        	   lb00.setText("DIVISION I-"); lab01.setText("DIVISION II-"); la02.setText("DIVISION III-"); 
							lab10.setText(""+grad_i); lab11.setText(""+grad_ii); lab12.setText(""+grad_iii);
							
							lbl00.setText("DIVISION IV-"); lbl01.setText("DIVISION 0-");
							lbl10.setText(""+grad_iv); lbl11.setText(""+grad_0); lbl12.setText("");
							searchdata=tableview.getItems();
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		 String classes = "(class LIKE 'FORM IV%')";
	 			try {
					for(int i=0; i<tableview.getItems().size(); i++){
			    		
			    		tableview.getItems().clear();
			    	}
					//int space=0,grad_i=0,grad_ii=0,grad_iii=0,grad_iv=0,grad_0=0;
					
					listofgen.clear();
					while(rs.next()){
						 uzit.clear();
						 pointe.clear();
						String stID;
						String gend;
								stID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("class");
					   	 String sex= rs.getString("Gender");
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					        stP1 = con.createStatement();
					        String mrow = "SET @row_number=0 ";
					      
					        String posQ =  "SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id "
									+ "FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher "
									+ "WHERE year='"+year+"' AND "+classes+" and type='"+examTy+"' "
											+ "group by stu_id order by wastani DESC) as tablewastan) AS MM WHERE stu_id='"+stID+"'";
					        		stP1.execute(mrow);
					        rsP1 = stP1.executeQuery(posQ);
					        if(rsP1.next()){
					        	posi = rsP1.getString("ROW");
					        }
					        stP1.close(); rsP1.close();
					        double civ,hist,geo,kisw,comp,eng,phy=-55.0,chem=-55.0,commerc,bkee,bio,math,islam,food,divnty,lite;
					      	 if(rs.getString("sub1")== null){
					     
					      		
					      	 }else{
					      		geo = Double.parseDouble(rs.getString("sub1"));
					      		pointe.add(geo);
					      		
					      	 }
					      	 if(rs.getString("sub2")== null){
					      		
					      	 }else{
					      		civ = Double.parseDouble(rs.getString("sub2"));
					      		pointe.add(civ);
					      		
					      	 }
					      	 if(rs.getString("sub3")== null){
					     
					      	 }else{
					      		hist = Double.parseDouble(rs.getString("sub3"));
					      		pointe.add(hist);
					      	 }
					      	 if(rs.getString("sub4")== null){
					      		
					      	 }else{
					      		kisw = Double.parseDouble(rs.getString("sub4"));
					      		pointe.add(kisw);
					      	 }
					      	 if(rs.getString("sub5")== null){
					      		
					      	 }else{
					      		comp = Double.parseDouble(rs.getString("sub5"));
					      		pointe.add(comp);
					      	 }
					      	 if(rs.getString("sub6")== null){
					      	
					      	 }else{
					      		eng = Double.parseDouble(rs.getString("sub6"));
					      		pointe.add(eng);
					      	 }
					      	 if(rs.getString("sub7")== null){
					      
					      		
					      	 }else{
					      		phy = Double.parseDouble(rs.getString("sub7"));
					      		pointe.add(phy);
					      		
					      	 }
					      	 if(rs.getString("sub8")== null){
					      	
					      		
					      	 }else{
					      		chem = Double.parseDouble(rs.getString("sub8"));
					      		pointe.add(chem);
					      		
					      	 }
					      	 if(rs.getString("sub9")== null){
					      	
					      	 }else{
					      		commerc = Double.parseDouble(rs.getString("sub9"));
					      		pointe.add(commerc);
					      	 }
					      	 if(rs.getString("sub10")== null){
					      	
					      	 }else{
					      		bkee = Double.parseDouble(rs.getString("sub10"));
					      		pointe.add(bkee);
					      	 }
					      	 if(rs.getString("sub11")== null){
					      		
					      	 }else{
					      		bio = Double.parseDouble(rs.getString("sub11"));
					      		pointe.add(bio);
					      	 }
					      	 if(rs.getString("sub12")== null){
					      	
					      	 }else{
					      		math = Double.parseDouble(rs.getString("sub12"));
					      		pointe.add(math);
					      	 } 	 if(rs.getString("sub16")== null){
					      		
					      	 }else{
					      		food = Double.parseDouble(rs.getString("sub16"));
					      		pointe.add(food);
					      	 }
					      	 if(rs.getString("sub14")== null){
					      		 
					      	 }else{
					      		islam = Double.parseDouble(rs.getString("sub14"));
					      		pointe.add(islam);
					      	 }
					      	 if(rs.getString("sub15")== null){
					     
					      	 }else{
					      		divnty = Double.parseDouble(rs.getString("sub15"));
					      		pointe.add(divnty);
					      	 }	 if(rs.getString("sub13")== null){
					     
					      	 }else{
					      		lite = Double.parseDouble(rs.getString("sub13"));
					      		pointe.add(lite);
					      	 }
					      	if(chem != -55.0 && phy != -55.0){
					      		secti = "SCIENCE";
					      	}else{
					      		secti = "ARTS";
					      	}
					      	 /*
					      	*/
					      
					     	Collections.sort(pointe);
					     	
					   double sumAv = 0;
					   int counterAv = 0;
					   for(int k=0; k<pointe.size(); k++){
					   		
					   			sumAv+=pointe.get(k);
					   			 counterAv++;
					   			
					   		 
					   		
					   	}
					   	
					   double avgMe = Math.round((sumAv/counterAv)*100.0)/100.0;
					   String avere = Double.toString(avgMe);
					   String divPoint = null;
				    	String divi = null;
					   /*
					    * GRADES FROM AVERAGE
					    */
					   String showAver = null;
					   if(avgMe >=74.5 && avgMe<=100){
						   	showAver = "A";
								//grad_a++;
								}else if(avgMe >=64.5 && avgMe<74.5){
									showAver = "B";
									//grad_b++;
								}else if(avgMe >=44.5 && avgMe<64.5){
									showAver = "C";
									//grad_c++;
								}else if(avgMe >=29.5 && avgMe<44.5){
									showAver = "D";
									//grad_d++;
								}else if(avgMe >=0 && avgMe<29.5){
									showAver = "F";
									//grad_f++;
								}else{
									System.out.println("ERROR ON FORM Iv AVERAGE");
								}
					    		if(pointe.size() < 7){
					     		
					     		listofgen.add(new GenReport(stID,name,studclas,secti,posi,avere,showAver,"Incomplete")); 
					     	}else{
					     for(int j=0; j<pointe.size(); j++){
					   	//	JOptionPane.showMessageDialog(null, grade[i]);

					   		if(pointe.get(j) >=74.5 && pointe.get(j)<=100){
					   			uzit.add(1);
					   		}else if(pointe.get(j) >=64.5 && pointe.get(j)<74.5){
					   			uzit.add(2);
					   		}else if(pointe.get(j) >=44.5 && pointe.get(j)<64.5){
					   			uzit.add(3);
					   		}else if(pointe.get(j) >=29.5 && pointe.get(j)<44.5){
					   			uzit.add(4);
					   		}else if(pointe.get(j) >=0 && pointe.get(j)<29.5){
					   			uzit.add(5);
					   		}else{
					   			
					   		}
					    
					     }
						 int	sum = 0;
						 Collections.sort(uzit);
						 uzit = uzit.subList(0,7);
					    	for(int k=0; k<uzit.size(); k++){
					    		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
					    		sum+=uzit.get(k);
					    	}
					    	 divPoint = Integer.toString(sum);
					    	
							if(sum >=7 && sum<=17){
								divi = "I";
								 grad_i++;
							}else if(sum >=18 && sum<=21){
								divi = "II";
								 grad_ii++;
							}else if(sum >=22 && sum<=25){
								divi = "III";
								 grad_iii++;
							}else if(sum >=26 && sum<=33){
								divi = "IV";
								 grad_iv++;
							}else if(sum >=34 && sum<=35){
								divi = "0";
								 grad_0++;
							}
						
					     listofgen.add(new GenReport(stID,name,studclas,secti,posi,avere,showAver, divi+" POINT "+divPoint ));
					     	}
					}
					
					st.close();
					rs.close();
					stP1.close();
					rsP1.close();
					//con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		             
		           return new Integer(1);
		           
		           
		        });
	
		}else if(darasa2.equals("FORM V")){
			wd = new WorkIndicatorDialog(null, "Loading ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
	 	        	  
						genlist = FXCollections.observableList(listofgen);
			        	   tableview.setItems(genlist);  
			        	   lb00.setText("DIVISION I-"); lab01.setText("DIVISION II-"); la02.setText("DIVISION III-"); 
							lab10.setText(""+grad_i); lab11.setText(""+grad_ii); lab12.setText(""+grad_iii);
							
							lbl00.setText("DIVISION IV-"); lbl01.setText("DIVISION 0-");
							lbl10.setText(""+grad_iv); lbl11.setText(""+grad_0); lbl12.setText("");
							searchdata=tableview.getItems();
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		 String classes = "(class LIKE 'FORM v-%')";
	 	 		try {
					for(int i=0; i<tableview.getItems().size(); i++){
			    		
			    		tableview.getItems().clear();
			    	}
			
					listofgen.clear();
					while(rs.next()){
                      pointe.clear();
                      uzit.clear();
						String gend;
					String stID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("class");
					   	 String sex= rs.getString("Gender");
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					        
					        stP1 = con.createStatement();
					        String mrow = "SET @row_number=0 ";
					      
					        String posQ =  "SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id "
									+ "FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher "
									+ "WHERE year='"+year+"' AND "+classes+" and type='"+examTy+"' AND NOT coz_id='141' AND NOT coz_id='111' "
											+ "group by stu_id order by wastani DESC) as tablewastan) AS MM WHERE stu_id='"+stID+"'";
					        		stP1.execute(mrow);
					        rsP1 = stP1.executeQuery(posQ);
					        if(rsP1.next()){
					        	posi = rsP1.getString("ROW");
					        }
					        stP1.close(); rsP1.close();
					     	double hist,geo,kisw,eng,phy=-55.0,chem=-55.0,econo,bio=-55.0,advmath=-55.0,fudNut=-55.0;
					      	
					      	 if(rs.getString("sub2")== null){
					      	
					      	 }else{
					      		hist = Double.parseDouble(rs.getString("sub2"));
					      		pointe.add(hist);
					      		
					      	 }
					      	 if(rs.getString("sub3")== null){
					      		
					      	 }else{
					      		geo = Double.parseDouble(rs.getString("sub3"));
					      		pointe.add(geo);
					      	 }
					      	 if(rs.getString("sub4")== null){
					      	
					      	 }else{
					      		eng = Double.parseDouble(rs.getString("sub4"));
					      		pointe.add(eng);
					      	 }
					      	 if(rs.getString("sub5")== null){
					      	
					      	 }else{
					      		phy = Double.parseDouble(rs.getString("sub5"));
					      		pointe.add(phy);
					      	 }
					      	 if(rs.getString("sub6")== null){
					     
					      		
					      	 }else{
					      		chem = Double.parseDouble(rs.getString("sub6"));
					      		pointe.add(chem);
					      		
					      	 }
					      	 if(rs.getString("sub7")== null){
					      	
					      		
					      	 }else{
					      		bio = Double.parseDouble(rs.getString("sub7"));
					      		pointe.add(bio);
					      		
					      	 }
					      	 if(rs.getString("sub8")== null){
					      		 
					      	 }else{
					      		econo = Double.parseDouble(rs.getString("sub8"));
					      		pointe.add(econo);
					      	 }
					      	 if(rs.getString("sub11")== null){
					      	
					      	 }else{
					      		advmath = Double.parseDouble(rs.getString("sub11"));
					      		pointe.add(advmath);
					      	 }
					      	 if(rs.getString("sub10")== null){
					      	
					      	 }else{
					      		kisw = Double.parseDouble(rs.getString("sub10"));
					      		pointe.add(kisw);
					      	 }if(rs.getString("sub12")== null){
					      		
					      	 }else{
					      		fudNut = Double.parseDouble(rs.getString("sub12"));
					      		pointe.add(fudNut);
					      	 }
					      	if(chem != -55.0 || phy != -55.0 || bio !=-55.0 || fudNut !=-55.0 || advmath !=-55.0 ){
					      		secti = "SCIENCE";
					      	}else{
					      		secti = "ARTS";
					      	}
					      	 /*
					      	*/
					      
					     	Collections.sort(pointe);
					     	double sumAv = 0;
					     
					     	
					   	int count = 0;
					     	for(int n=0; n<pointe.size(); n++){
					     		
					     			count++;
					     			sumAv+=pointe.get(n);
					     		
					     	}
					     	double avg = Math.round((sumAv/count)*100.0)/100.0;
					     	String avere = Double.toString(avg);
					     	 /*
							    * GRADES FROM AVERAGE
							    */
							   String showAver = null;
							   if(avg >=79.5 && avg<=100){
								   	showAver = "A";
										//grad_a++;
										}else if(avg >=69.5 && avg<79.5){
											showAver = "B";
											//grad_b++;
										}else if(avg >=59.5 && avg<69.5){
											showAver = "C";
											//grad_c++;
										}else if(avg >=49.5 && avg<59.5){
											showAver = "D";
											//grad_d++;
										}else if(avg >=39.5 && avg<49.5){
											showAver = "E";
											//grad_f++;
										}else if(avg >=33.5 && avg<39.5){
											showAver = "S";
											//grad_f++;
										}else if(avg >=0 && avg<33.5){
											showAver = "F";
											//grad_f++;
										}else{
											
										}
							  // System.out.println("ORG:"+Arrays.toString(points));
					     	//JOptionPane.showMessageDialog(null, "COUNT "+count);
					     	
					     	if(pointe.size() < 3){
					     		//System.out.println("GRADE2:"+Arrays.toString(grade2));
					     		listofgen.add(new GenReport(stID,name,studclas,secti,posi,avere,showAver,"Incomplete")); 
					     	}else{
					     //	System.out.println("GRADE2:"+Arrays.toString(grade2));
					     	  for(int j=0; j<pointe.size(); j++){
					     		   	//	JOptionPane.showMessageDialog(null, "grade2 "+grade2[j]);

					     		   		if(pointe.get(j) >=79.5 && pointe.get(j)<=100){
					     		   			uzit.add(1);
					     		   		}else if(pointe.get(j) >=69.5 && pointe.get(j)<79.5){
					     		   		uzit.add(2);
					     		   		}else if(pointe.get(j) >=59.5 && pointe.get(j)<69.5){
					     		   		uzit.add(3);
					     		   		}else if(pointe.get(j) >=49.5 && pointe.get(j)<59.5){
					     		   		uzit.add(4);
					     		   		}else if(pointe.get(j) >=39.5 && pointe.get(j)<49.5){
					     		   		uzit.add(5);
					     		   		}else if(pointe.get(j) >=34.5 && pointe.get(j)<39.5){
					     		   		uzit.add(6);
					     		   		}
					     		   		else if(pointe.get(j) >=0 && pointe.get(j)<34.5){
					     		   		uzit.add(7);
					     		   		}else{
					     		   			
					     		   		}
					     		   		
					     		     }
					     //	 System.out.println("UZITO2:"+Arrays.toString(uzito2));
					     	 int	sum = 0;
					     	 String divi = null;
					     	 String divPoint;
					     	 Collections.sort(uzit);
					       	for(int k=0; k<uzit.size(); k++){
					       		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
					       		sum+=uzit.get(k);
					       	}
					       //	System.out.println("SUM:"+sum);
					       	divPoint = Integer.toString(sum);
					  		if(sum >=3 && sum<=9){
					   			divi = "I";
					   			grad_i++;
					   		}else if(sum >=10 && sum<=12){
					   			
					   			divi = "II";
					   			grad_ii++;
					   		}else if(sum >=13 && sum<=17){
					   			
					   			divi = "III";
					   			grad_iii++;
					   		}else if(sum >=18 && sum<=19){
					   			
					   			divi = "IV";
					   			grad_iv++;
					   		}else if(sum >=20 && sum<=21){
					   			
					   			divi = "0";
					   			grad_0++;
					   		}
					
					  		
					        listofgen.add(new GenReport(stID,name,studclas,secti,posi,avere,showAver, divi+" POINT "+divPoint));     
					}
					}
					
					st.close();
					rs.close();
					stP1.close();
					rsP1.close();
				//	con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		             
		           return new Integer(1);
		           
		           
		        });
		
		}else if(darasa2.equals("FORM VI")){
			wd = new WorkIndicatorDialog(null, "Loading ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
	 	        	 
						genlist = FXCollections.observableList(listofgen);
			        	   tableview.setItems(genlist);  
			        	   lb00.setText("DIVISION I-"); lab01.setText("DIVISION II-"); la02.setText("DIVISION III-"); 
							lab10.setText(""+grad_i); lab11.setText(""+grad_ii); lab12.setText(""+grad_iii);
							
							lbl00.setText("DIVISION IV-"); lbl01.setText("DIVISION 0-");
							lbl10.setText(""+grad_iv); lbl11.setText(""+grad_0); lbl12.setText("");  
							searchdata=tableview.getItems();
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		 String classes = "(class LIKE 'FORM VI-%')";
		 	 		try {
						for(int i=0; i<tableview.getItems().size(); i++){
				    		
				    		tableview.getItems().clear();
				    	}
				
						listofgen.clear();
						while(rs.next()){
	                      pointe.clear();
	                      uzit.clear();
							String gend;
						String stID = rs.getString("student_id");
							 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
						   	 String studclas = rs.getString("class");
						   	 String sex= rs.getString("Gender");
						        if(sex.equals("FEMALE")){
						        	gend = "F";
						        }else{
						        	gend = "M";
						        }
						        
						        stP1 = con.createStatement();
						        String mrow = "SET @row_number=0 ";
						      
						        String posQ =  "SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id "
										+ "FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher "
										+ "WHERE year='"+year+"' AND "+classes+" and type='"+examTy+"' AND NOT coz_id='141' AND NOT coz_id='111'  "
												+ "group by stu_id order by wastani DESC) as tablewastan) AS MM WHERE stu_id='"+stID+"'";
						        		stP1.execute(mrow);
						        rsP1 = stP1.executeQuery(posQ);
						        if(rsP1.next()){
						        	posi = rsP1.getString("ROW");
						        }
						        stP1.close(); rsP1.close();
						     	double hist,geo,kisw,eng,phy=-55.0,chem=-55.0,econo,bio=-55.0,advmath=-55.0,fudNut=-55.0;
						      	
						      	 if(rs.getString("sub2")== null){
						      	
						      	 }else{
						      		hist = Double.parseDouble(rs.getString("sub2"));
						      		pointe.add(hist);
						      		
						      	 }
						      	 if(rs.getString("sub3")== null){
						      		
						      	 }else{
						      		geo = Double.parseDouble(rs.getString("sub3"));
						      		pointe.add(geo);
						      	 }
						      	 if(rs.getString("sub4")== null){
						      	
						      	 }else{
						      		eng = Double.parseDouble(rs.getString("sub4"));
						      		pointe.add(eng);
						      	 }
						      	 if(rs.getString("sub5")== null){
						      	
						      	 }else{
						      		phy = Double.parseDouble(rs.getString("sub5"));
						      		pointe.add(phy);
						      	 }
						      	 if(rs.getString("sub6")== null){
						     
						      		
						      	 }else{
						      		chem = Double.parseDouble(rs.getString("sub6"));
						      		pointe.add(chem);
						      		
						      	 }
						      	 if(rs.getString("sub7")== null){
						      	
						      		
						      	 }else{
						      		bio = Double.parseDouble(rs.getString("sub7"));
						      		pointe.add(bio);
						      		
						      	 }
						      	 if(rs.getString("sub8")== null){
						      		 
						      	 }else{
						      		econo = Double.parseDouble(rs.getString("sub8"));
						      		pointe.add(econo);
						      	 }
						      	 if(rs.getString("sub11")== null){
						      	
						      	 }else{
						      		advmath = Double.parseDouble(rs.getString("sub11"));
						      		pointe.add(advmath);
						      	 }
						      	 if(rs.getString("sub10")== null){
						      	
						      	 }else{
						      		kisw = Double.parseDouble(rs.getString("sub10"));
						      		pointe.add(kisw);
						      	 }if(rs.getString("sub12")== null){
						      		
						      	 }else{
						      		fudNut = Double.parseDouble(rs.getString("sub12"));
						      		pointe.add(fudNut);
						      	 }
						      	if(chem != -55.0 || phy != -55.0 || bio !=-55.0 || fudNut !=-55.0 || advmath !=-55.0 ){
						      		secti = "SCIENCE";
						      	}else{
						      		secti = "ARTS";
						      	}
						      	 /*
						      	*/
						      
						     	Collections.sort(pointe);
						     	double sumAv = 0;
						     
						     	
						   	int count = 0;
						     	for(int n=0; n<pointe.size(); n++){
						     		
						     			count++;
						     			sumAv+=pointe.get(n);
						     		
						     	}
						     	double avg = Math.round((sumAv/count)*100.0)/100.0;
						     	String avere = Double.toString(avg);
						     	 /*
								    * GRADES FROM AVERAGE
								    */
								   String showAver = null;
								   if(avg >=79.5 && avg<=100){
									   	showAver = "A";
											//grad_a++;
											}else if(avg >=69.5 && avg<79.5){
												showAver = "B";
												//grad_b++;
											}else if(avg >=59.5 && avg<69.5){
												showAver = "C";
												//grad_c++;
											}else if(avg >=49.5 && avg<59.5){
												showAver = "D";
												//grad_d++;
											}else if(avg >=39.5 && avg<49.5){
												showAver = "E";
												//grad_f++;
											}else if(avg >=33.5 && avg<39.5){
												showAver = "S";
												//grad_f++;
											}else if(avg >=0 && avg<33.5){
												showAver = "F";
												//grad_f++;
											}else{
												
											}
								  // System.out.println("ORG:"+Arrays.toString(points));
						     	//JOptionPane.showMessageDialog(null, "COUNT "+count);
						     	
						     	if(pointe.size() < 3){
						     		//System.out.println("GRADE2:"+Arrays.toString(grade2));
						     		listofgen.add(new GenReport(stID,name,studclas,secti,posi,avere,showAver,"Incomplete")); 
						     	}else{
						     //	System.out.println("GRADE2:"+Arrays.toString(grade2));
						     	  for(int j=0; j<pointe.size(); j++){
						     		   	//	JOptionPane.showMessageDialog(null, "grade2 "+grade2[j]);

						     		   		if(pointe.get(j) >=79.5 && pointe.get(j)<=100){
						     		   			uzit.add(1);
						     		   		}else if(pointe.get(j) >=69.5 && pointe.get(j)<79.5){
						     		   		uzit.add(2);
						     		   		}else if(pointe.get(j) >=59.5 && pointe.get(j)<69.5){
						     		   		uzit.add(3);
						     		   		}else if(pointe.get(j) >=49.5 && pointe.get(j)<59.5){
						     		   		uzit.add(4);
						     		   		}else if(pointe.get(j) >=39.5 && pointe.get(j)<49.5){
						     		   		uzit.add(5);
						     		   		}else if(pointe.get(j) >=34.5 && pointe.get(j)<39.5){
						     		   		uzit.add(6);
						     		   		}
						     		   		else if(pointe.get(j) >=0 && pointe.get(j)<34.5){
						     		   		uzit.add(7);
						     		   		}else{
						     		   			
						     		   		}
						     		   		
						     		     }
						     //	 System.out.println("UZITO2:"+Arrays.toString(uzito2));
						     	 int	sum = 0;
						     	 String divi = null;
						     	 String divPoint;
						     	 Collections.sort(uzit);
						       	for(int k=0; k<uzit.size(); k++){
						       		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
						       		sum+=uzit.get(k);
						       	}
						       //	System.out.println("SUM:"+sum);
						       	divPoint = Integer.toString(sum);
						  		if(sum >=3 && sum<=9){
						   			divi = "I";
						   			grad_i++;
						   		}else if(sum >=10 && sum<=12){
						   			
						   			divi = "II";
						   			grad_ii++;
						   		}else if(sum >=13 && sum<=17){
						   			
						   			divi = "III";
						   			grad_iii++;
						   		}else if(sum >=18 && sum<=19){
						   			
						   			divi = "IV";
						   			grad_iv++;
						   		}else if(sum >=20 && sum<=21){
						   			
						   			divi = "0";
						   			grad_0++;
						   		}
						
						  		
						        listofgen.add(new GenReport(stID,name,studclas,secti,posi,avere,showAver, divi+" POINT "+divPoint));     
						}
						}
						
						st.close();
						rs.close();
						stP1.close();
						rsP1.close();
					//	con.close();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
			               try {
			                  Thread.sleep(1000);
			               }	
			               catch (InterruptedException er) {
			                  er.printStackTrace();
			               }
			             
			           return new Integer(1);
			           
			           
			        });
	
		}else{
			
		}
		
	}	
	  @FXML
	    void showMore(ActionEvent event) {

	    }
	    @FXML
	    void goHard(ActionEvent event) {
	    	try{
	    	if(hardprint.length() == 0){
	    		JFXSnackbar bar = new JFXSnackbar(anchor);
	        	bar.show("Fill details in above fields. \n you cant generate any data with invalid data",3000);
	    	}else{
	    		String []subpart = hardprint.split("-");
	    		if(subpart[0].equals("FORM I")){
	    			FillForm1(subpart[0],subpart[1],subpart[2]);
	    		}else if(subpart[0].equals("FORM II")){
	    			FillForm1(subpart[0],subpart[1],subpart[2]);
	    		}else if(subpart[0].equals("FORM III")){
	    			FillForm2(subpart[0],subpart[1],subpart[2]);
	    		}else if(subpart[0].equals("FORM IV")){
	    			FillForm2(subpart[0],subpart[1],subpart[2]);
	    		}else if(subpart[0].equals("FORM V")){
	    			FillForm3(subpart[0],subpart[1],subpart[2]);
	    		}else if(subpart[0].equals("FORM VI")){
	    			FillForm3(subpart[0],subpart[1],subpart[2]);
	    		}else{
	    			System.out.println("Wrong MKEKA HARD.");
	    		}
	    	}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		JFXSnackbar bar = new JFXSnackbar(anchor);
	        	bar.show("Fill details in above fields. \n you cant generate any data with invalid data",3000);
	    	}
	    }
	    @SuppressWarnings({ "unchecked", "unused" })
		public void FillForm1(String clasi,String year,String type){
	    	String stuDara;
	    	if(clasi.equals("FORM I")){
	    		
	    	}
	    	wd = new WorkIndicatorDialog(null, "generating sheet ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
	 	        	  TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.INFORMATION);
				       tray.setTitle("general sheet generated with success");
				       tray.setMessage("select Product data on the table above");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
				       openMkeka(clasi);
	 	           }else if(outres.equals("0")){
	 	        	  TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("error generating sheet. retry");
				       tray.setMessage("try to close any open sheet and generate again");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		try{
		    		//JOptionPane.showMessageDialog(null, "form1");
		    	 HSSFWorkbook workbook = new HSSFWorkbook(); 
		         HSSFSheet spreadsheet = workbook
		         .createSheet("Student names");
		         HSSFRow row=spreadsheet.createRow(0);
		         HSSFCellStyle RotateStyle = workbook.createCellStyle();
		         HSSFCellStyle BoldStyle = workbook.createCellStyle();
		         RotateStyle.setRotation((short)90);
		         HSSFFont my_font = workbook.createFont();
		         stT = con.createStatement();
		         String sql = "SELECT * FROM school_details";
		         String title=null;
		         
		         rsT = stT.executeQuery(sql);
		         if(rsT.next()){
		        	  title = rsT.getString("title");
		         }
		         stT.close();
		         rsT.close();
		        
		         int stucount=0;
		         String studI = null;
		  
		         Header header = spreadsheet.getHeader();
		         header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)14)+title+"\n"
		         		+type.toUpperCase()+ " GROSS PERFORMANCE REPORT "+clasi+" "+year);
		         spreadsheet.setColumnWidth(0, 2500);
		         spreadsheet.setColumnWidth(1, 1500);
		         spreadsheet.setColumnWidth(2, 7000);
		         spreadsheet.setColumnWidth(3, 3000);
		         spreadsheet.setColumnWidth(4, 1350);
		         spreadsheet.setColumnWidth(5, 1350);
		         spreadsheet.setColumnWidth(6, 1350);
		         spreadsheet.setColumnWidth(7, 1350);
		         spreadsheet.setColumnWidth(8, 1350);
		         spreadsheet.setColumnWidth(9, 1350);
		         spreadsheet.setColumnWidth(10, 1350);
		         spreadsheet.setColumnWidth(11, 1350);
		         spreadsheet.setColumnWidth(12, 1350);
		         spreadsheet.setColumnWidth(13, 1350);
		         spreadsheet.setColumnWidth(14, 1350);
		         spreadsheet.setColumnWidth(15, 1350);
		         spreadsheet.setColumnWidth(16, 1350);
		         spreadsheet.setColumnWidth(17, 1350);
		         spreadsheet.setColumnWidth(18, 1350);
		         
		         my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		        
		         BoldStyle.setFont(my_font);
		         RotateStyle.setFont(my_font);
		         HSSFCell cell,cellavg,cellpnt,cellposi;
		         cell=row.createCell(0);
		         cell.setCellValue("REG NO.");
		         cell.setCellStyle(BoldStyle);
		         cell=row.createCell(1);
		         cell.setCellValue("SEX");
		         cell.setCellStyle(BoldStyle);
		         cell=row.createCell(2);
		         cell.setCellValue("STUDENT NAME");
		         cell.setCellStyle(BoldStyle);
		         cell=row.createCell(3);
		         cell.setCellValue("STUDENT CLASS");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(4);
		         cell.setCellValue("CIVICS");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(5);
		        
		         cell.setCellValue("HISTORY");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(6);
		         
		         cell.setCellValue("GEOGRAPHY");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(7);
		         cell.setCellValue("KISWAHILI");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(8);
		         cell.setCellValue("ENGLISH LANGUAGE");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(9);
		         cell.setCellValue("PHYSICS");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(10);
		         cell.setCellValue("CHEMISTRY");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(11);
		         cell.setCellValue("BIOLOGY");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(12);
		         cell.setCellValue("COMPUTER STUDIES");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(13);
		         cell.setCellValue("BASIC MATHEMATICS");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(14);
		         cell.setCellValue("COMMERCE");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(15);
		         cell.setCellValue("BOOK-KEEPING");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(16);
		         cell.setCellValue("FOOD AND NUTRITION");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(17);
		         cell.setCellValue("ISLAMIC KNOWLEDGE");
		         cell.setCellStyle(RotateStyle);
		         cell=row.createCell(18);
		         cell.setCellValue("DIVINITY");
		         cell.setCellStyle(RotateStyle);
		         cellavg=row.createCell(19);
		         cellavg.setCellValue("AVERAGE");
		         cellavg.setCellStyle(RotateStyle);
		         cellpnt=row.createCell(20);
		         cellpnt.setCellValue("GRADE");
		         cellpnt.setCellStyle(RotateStyle);
		         cellposi=row.createCell(21);
		         cellposi.setCellValue("DIVISION");
		         cellposi.setCellStyle(RotateStyle);
		         cellposi=row.createCell(22);
		         cellposi.setCellValue("POINT");
		         cellposi.setCellStyle(RotateStyle);
		         cellposi=row.createCell(23);
		         cellposi.setCellValue("POSITION");
		         cellposi.setCellStyle(RotateStyle);
		         int i=1;
		         int space=0;
		         int grad_a = 0,grad_b=0,grad_c=0,grad_d=0,grad_f=0;
		         int grad_i = 0,grad_ii=0,grad_iii=0,grad_iv=0,grad_0=0;
		         while(rs1.next())
		         {
		        	 pointe.clear();
		        	 uzit.clear();
		        	  stucount++;
		        	 String leoh = rs1.getString("student_id");
		        	// System.out.printdoubleleoh);
		        	 String name = rs1.getString("firstName")+"  "+rs1.getString("middleName")+"  "+rs1.getString("lastName");
		       	 String studclas = rs1.getString("class");
		            row=spreadsheet.createRow(i);
		            cell=row.createCell(0);
		            cell.setCellValue(rs1.getString("student_id"));
		            String sex= rs1.getString("Gender");
		            if(sex.equals("FEMALE")){
		            cell=row.createCell(1);
		            cell.setCellValue("F");
		            }else if(sex.equals("MALE")){
		                cell=row.createCell(1);
		                cell.setCellValue("M"); 
		            }
		            stP1 = con.createStatement();
		            String mrow = "SET @row_number=0 ";
				      String nafa = null;
			        String posQ =  "SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id "
							+ "FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher "
							+ "WHERE year='"+year+"' AND (class='"+clasi+"A' OR class='"+clasi+"B' OR class='"+clasi+"C' OR "
									+ "class='"+clasi+"D' OR class='"+clasi+"E' OR class='"+clasi+"F' OR class='"+clasi+"G') and type='"+type+"' "
									+ "group by stu_id order by wastani DESC) as tablewastan) AS MM WHERE stu_id='"+leoh+"'";
			        		stP1.execute(mrow);
			        rsP1 = stP1.executeQuery(posQ);
			        if(rsP1.next()){
			        	nafa = rsP1.getString("ROW");
			        }
			        cellposi=row.createCell(23);
			        cellposi.setCellValue(nafa);
			        stP1.close(); rsP1.close();
		            
		            cell=row.createCell(2); 
		            cell.setCellValue(name);
		            
		            cell=row.createCell(3);
		            cell.setCellValue(studclas);
		            
		           
		            cell=row.createCell(4);
		            cell.setCellValue(rs1.getString("sub2"));
		            
		            cell=row.createCell(5);
		          	 cell.setCellValue(rs1.getString("sub3"));
		          	 
		          	cell=row.createCell(6);
		         	 cell.setCellValue(rs1.getString("sub1"));
		         	 
		         	cell=row.createCell(7);
		         	 cell.setCellValue(rs1.getString("sub4"));
		         	 
		         	cell=row.createCell(8);
		        	 cell.setCellValue(rs1.getString("sub6"));
		        	 
		        		cell=row.createCell(9);
		           	 cell.setCellValue(rs1.getString("sub7"));
		        	 
		        		cell=row.createCell(10);
		           	 cell.setCellValue(rs1.getString("sub8"));
		           	 
		         	cell=row.createCell(11);
		       	 cell.setCellValue(rs1.getString("sub11"));
		       	 
		     	cell=row.createCell(12);
		    	 cell.setCellValue(rs1.getString("sub5"));
		         	 
		         	cell=row.createCell(13);
		         	 cell.setCellValue(rs1.getString("sub12"));
		         	 
		         	cell=row.createCell(14);
		       	 cell.setCellValue(rs1.getString("sub9"));
		       	 
		       	cell=row.createCell(15);
		      	 cell.setCellValue(rs1.getString("sub10"));
		      	 
		     	cell=row.createCell(16);
		     	 cell.setCellValue(rs1.getString("sub15"));
		     	cell=row.createCell(17);
		     	 cell.setCellValue(rs1.getString("sub13"));
		     	cell=row.createCell(18);
		     	 cell.setCellValue(rs1.getString("sub14"));
		     	
		     	 /*
		     	  * 
		     	  */
		     	double civ,hist,geo,kisw,comp,eng,phy=-55.0,chem=-55.0,commerc,bkee,bio,math,islam,food,divnty;
		     	 if(rs1.getString("sub1")== null){
		     	
		     	 }else{
		     		geo = Double.parseDouble(rs1.getString("sub1"));
		     		pointe.add(geo);
		     	 }
		     	 if(rs1.getString("sub2")== null){
		     
		     	 }else{
		     		civ = Double.parseDouble(rs1.getString("sub2"));
		     		pointe.add(civ);
		     		
		     	 }
		     	 if(rs1.getString("sub3")== null){
		     	
		     	 }else{
		     		hist = Double.parseDouble(rs1.getString("sub3"));
		     		pointe.add(hist);
		     	 }
		     	 if(rs1.getString("sub4")== null){
		     		
		     	 }else{
		     		kisw = Double.parseDouble(rs1.getString("sub4"));
		     		pointe.add(kisw);
		     	 }
		     	 if(rs1.getString("sub5")== null){
		     	
		     	 }else{
		     		comp = Double.parseDouble(rs1.getString("sub5"));
		     		pointe.add(comp);
		     	 }
		     	 if(rs1.getString("sub6")== null){
		     		
		     	 }else{
		     		eng = Double.parseDouble(rs1.getString("sub6"));
		     		pointe.add(eng);
		     	 }
		     	 if(rs1.getString("sub7")== null){
		     		
		     	 }else{
		     		phy = Double.parseDouble(rs1.getString("sub7"));
		     		pointe.add(phy);
		     		
		     	 }
		     	 if(rs1.getString("sub8")== null){
		     	
		     		
		     	 }else{
		     		chem = Double.parseDouble(rs1.getString("sub8"));
		     		pointe.add(chem);
		     		
		     	 }
		     	 if(rs1.getString("sub9")== null){
		     		
		     	 }else{
		     		commerc = Double.parseDouble(rs1.getString("sub9"));
		     		pointe.add(commerc);
		     	 }
		     	 if(rs1.getString("sub10")== null){
		     		
		     	 }else{
		     		bkee = Double.parseDouble(rs1.getString("sub10"));
		     		pointe.add(bkee);
		     	 }
		     	 if(rs1.getString("sub11")== null){
		     		
		     	 }else{
		     		bio = Double.parseDouble(rs1.getString("sub11"));
		     		pointe.add(bio);
		     	 }
		     	 if(rs1.getString("sub12")== null){
		     		
		     	 }else{
		     		math = Double.parseDouble(rs1.getString("sub12"));
		     		pointe.add(math);
		     	 } 	 if(rs1.getString("sub13")== null){
		     		
		     	 }else{
		     		islam = Double.parseDouble(rs1.getString("sub13"));
		     		pointe.add(islam);
		     	 }
		     	 if(rs1.getString("sub14")== null){
		     	
		     	 }else{
		     		divnty = Double.parseDouble(rs1.getString("sub14"));
		     		pointe.add(divnty);
		     	 }
		     	 if(rs1.getString("sub15")== null){
		     	
		     	 }else{
		     		food = Double.parseDouble(rs1.getString("sub15"));
		     		pointe.add(food);
		     	 }
		     	
		     	Collections.sort(pointe);
		    	double	sum = 0;
		     	 int counter = 0;
		      	for(int k=0; k<pointe.size(); k++){
		      			sum+=pointe.get(k);
		      			 counter++;
		      	
		      	}
		      	
		      	if(chem != -55.0 && phy != -55.0){
		      	
		      		secti = "SCIENCE";
		      	}else{
		      		secti = "ARTS";
		      	}
		      String showAver = null;
		    double avg = Math.round((sum/counter)*100.0)/100.0;
		    cellpnt=row.createCell(19);
    		cellpnt.setCellValue(avg);
    		cellavg=row.createCell(20);
    		if(avg >=74.5 && avg<=100){
	    		cellavg.setCellValue("A");
	    		grad_a++;
	    		grad_i++;
	    		}else if(avg >=64.5 && avg<74.5){
	    			cellavg.setCellValue("B");
	    			grad_b++;
	    			grad_ii++;
	    		}else if(avg >=44.5 && avg<64.5){
	    			cellavg.setCellValue("C");
	    			grad_c++;
	    			grad_iii++;
	    		}else if(avg >=29.5 && avg<44.5){
	    			cellavg.setCellValue("D");
	    			grad_d++;
	    			grad_iv++;
	    		}else if(avg >=0 && avg<29.5){
	    			cellavg.setCellValue("F");
	    			grad_f++;
	    			grad_0++;
	    		}else{
	    			JOptionPane.showMessageDialog(null, "ERROR");
	    		}
    		
		    String ave = Double.toString(avg);
		    //*****
		//	grade = Arrays.copyOfRange(points, 0, 7);
			//gradeo = pointe.subList(0, 7);
	     	if(pointe.size() < 7){
	     		cellposi=row.createCell(21);
	    		cellposi.setCellValue("Incomplete");
	     	}else{
	     		System.out.println("MARKS:"+pointe);
	     for(int j=0; j<pointe.size(); j++){
	   	

	   		if(pointe.get(j) >=74.5 && pointe.get(j)<=100){
	   			uzit.add(1);
	   		}else if(pointe.get(j) >=64.5 && pointe.get(j)<74.5){
	   			uzit.add(2);
	   		}else if(pointe.get(j) >=44.5 && pointe.get(j)<64.5){
	   			uzit.add(3);
	   		}else if(pointe.get(j) >=29.5 && pointe.get(j)<44.5){
	   			uzit.add(4);
	   		}else if(pointe.get(j) >=0 && pointe.get(j)<29.5){
	   			uzit.add(5);
	   		}else{
	   			//JOptionPane.showMessageDialog(null, "One of the marks in the sheet to be generated \n Exceeds 100%. please correct it during uploads.","ERROR",JOptionPane.WARNING_MESSAGE);
	   		}
	   	   
	     }
	     int 	sumP = 0;
	     String divi=null,divPoint=null;
	   Collections.sort(uzit);
	   uzit = uzit.subList(0, 7);
	   System.out.println(uzit);
	    	for(int k=0; k<uzit.size(); k++){
	    		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
	    		sumP+=uzit.get(k);
	    	}
	    	 divPoint = Integer.toString(sumP);
	    	
	    		cellposi=row.createCell(22);
 			cellposi.setCellValue(divPoint);
			if(sumP >=7 && sumP<=17){
				divi = "I";
				 cellposi=row.createCell(21);
		         cellposi.setCellValue(divi);
				 grad_i++;
			}else if(sumP >=18 && sumP<=21){
				divi = "II";
				cellposi=row.createCell(21);
		         cellposi.setCellValue(divi);
				 grad_ii++;
			}else if(sumP >=22 && sumP<=25){
				divi = "III";
				cellposi=row.createCell(21);
		         cellposi.setCellValue(divi);
				 grad_iii++;
			}else if(sumP >=26 && sumP<=33){
				divi = "IV";
				cellposi=row.createCell(21);
		         cellposi.setCellValue(divi);
				 grad_iv++;
			}else if(sumP >=34 && sumP<=35){
				divi = "0";
				cellposi=row.createCell(21);
		         cellposi.setCellValue(divi);
				 grad_0++;
			}
		
			}
		      	 
		            i++;
		             space = i+4;
		            
		         }
		         row=spreadsheet.createRow(space);
		         cell = row.createCell(2);
		         cell.setCellValue("RESULTS GRADE SUMMARY");
		         cell.setCellStyle(BoldStyle);
		         
		         row=spreadsheet.createRow(space+1);
		         cell = row.createCell(2);
		         cell.setCellValue("GRADE TITLE");
		         cell.setCellStyle(BoldStyle);
		         
		         cell = row.createCell(3);
		         cell.setCellValue("CANDIDATES");
		         cell.setCellStyle(BoldStyle);
		        /* cell = row.createCell(4);
		         cell.setCellValue("M");
		         cell.setCellStyle(BoldStyle);*/
		         
		         row=spreadsheet.createRow(space+2);
		         cell = row.createCell(2);
		         cell.setCellValue("GRADE A");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_a);
		        /* cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+3);
		         cell = row.createCell(2);
		         cell.setCellValue("GRADE B");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_b);
		        /* cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+4);
		         cell = row.createCell(2);
		         cell.setCellValue("GRADE C");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_c);
		         /*cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+5);
		         cell = row.createCell(2);
		         cell.setCellValue("GRADE D");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_d);
		         /*cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+6);
		         cell = row.createCell(2);
		         cell.setCellValue("GRADE F");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_f);
		        /* cell = row.createCell(4);
		         cell.setCellValue("0");*/

		         row=spreadsheet.createRow(space+7);
		         cell = row.createCell(2);
		         cell.setCellValue("ABSENTEES");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(stucount-(grad_a+grad_b+grad_c+grad_d+grad_f));
		         /*cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+8);
		         cell = row.createCell(2);
		         cell.setCellValue("TOTAL No. OF CANDIDATES");
		         cell.setCellStyle(BoldStyle);
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_a+grad_b+grad_c+grad_d+grad_f+(stucount-(grad_a+grad_b+grad_c+grad_d+grad_f)));
		         cell.setCellStyle(BoldStyle);
		        /* cell = row.createCell(4);
		         cell.setCellValue("10");
		         cell.setCellStyle(BoldStyle);*/
		         
		         
		         
		         
		         FileOutputStream out = new FileOutputStream(
		         new File(clasi+" mkeka.xls"));
		         workbook.write(out);
		         out.close();
		       sheet = 1;
		         // JOptionPane.showMessageDialog(null,clasi+" GENERAL RESULT CREATED"+" \n"+year);
		    	con.close();	
		    	}catch(Exception e){
		    			e.printStackTrace();
		    			sheet = 0;
		    		/*	JOptionPane.showMessageDialog(null, "File is open in another application(EXCEL) \n"
		    	  		  		+ "please close the application and restart \n"
		    	  		  		+ "this process to create the file.");
*/
		    	}
		       
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		             
		           return new Integer(sheet);
		           
		           
		        });
	    
	    	}
	    @SuppressWarnings({ "unchecked", "unused" })
		public void FillForm2(String clasi,String year,String type){
	    	wd = new WorkIndicatorDialog(null, "generating sheet ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	 		  if(outres.equals("1")){
	 	        	  TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.INFORMATION);
				       tray.setTitle("general sheet generated with success");
				       tray.setMessage("select Product data on the table above");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
				       openMkeka(clasi);
	 	           }else if(outres.equals("2")){
	 	        	  TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("error generating sheet. retry");
				       tray.setMessage("try to close any open sheet and generate again");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	    	try{
		    		//JOptionPane.showMessageDialog(null, "form2");

		    		 HSSFWorkbook workbook = new HSSFWorkbook(); 
		    	      HSSFSheet spreadsheet = workbook
		    	      .createSheet("Student names");
		    	      HSSFRow row=spreadsheet.createRow(0);
		    	      HSSFCellStyle RotateStyle = workbook.createCellStyle();
		    	      HSSFCellStyle BoldStyle = workbook.createCellStyle();
		    	      RotateStyle.setRotation((short)90);
		    	      HSSFFont my_font = workbook.createFont();
		    	      
		    	      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    	      stT = con.createStatement();
		    	      String sql = "SELECT * FROM school_details";
		    	      String title=null;
		    	      rsT = stT.executeQuery(sql);
		    	      if(rsT.next()){
		    	     	  title = rsT.getString("title");
		    	      }
		    	      stT.close();
		    	      rsT.close();
		    	      
		    	    
		    	      int stucount=0;
		    	 
		    	      Header header = spreadsheet.getHeader();
		    	      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)14)+title+"\n"
		    	      		+type.toUpperCase()+ " GROSS PERFORMANCE REPORT "+clasi+" "+year);
		    	      spreadsheet.setColumnWidth(0, 2300);
		    	      spreadsheet.setColumnWidth(1, 1300);
		    	      spreadsheet.setColumnWidth(2, 7000);
		    	      spreadsheet.setColumnWidth(3, 3000);
		    	      spreadsheet.setColumnWidth(4, 1350);
		    	      spreadsheet.setColumnWidth(5, 1350);
		    	      spreadsheet.setColumnWidth(6, 1350);
		    	      spreadsheet.setColumnWidth(7, 1350);
		    	      spreadsheet.setColumnWidth(8, 1350);
		    	      spreadsheet.setColumnWidth(9, 1350);
		    	      spreadsheet.setColumnWidth(10, 1350);
		    	      spreadsheet.setColumnWidth(11, 1350);
		    	      spreadsheet.setColumnWidth(12, 1350);
		    	      spreadsheet.setColumnWidth(13, 1350);
		    	      spreadsheet.setColumnWidth(14, 1350);
		    	      spreadsheet.setColumnWidth(15, 1350);
		    	      spreadsheet.setColumnWidth(16, 1350);
		    	      spreadsheet.setColumnWidth(17, 1350);
		    	      spreadsheet.setColumnWidth(18, 1350);
		    	      spreadsheet.setColumnWidth(19, 1350);
		    	      spreadsheet.setColumnWidth(20, 1350);
		    	      spreadsheet.setColumnWidth(21, 1350);
		    	      spreadsheet.setColumnWidth(22, 1350);
		    	      spreadsheet.setColumnWidth(23, 1350);
		    	      
		    	      BoldStyle.setFont(my_font);
		    	      RotateStyle.setFont(my_font);
		    	      HSSFCell cell,cell17,cell18,cell19;
		    	      cell=row.createCell(0);
		    	      cell.setCellValue("REG NO.");
		    	      cell.setCellStyle(BoldStyle);
		    	      cell=row.createCell(1);
		    	      cell.setCellValue("SEX");
		    	      cell.setCellStyle(BoldStyle);
		    	      cell=row.createCell(2);
		    	      cell.setCellValue("STUDENT NAME");		
		    	      cell.setCellStyle(BoldStyle);
		    	      cell=row.createCell(3);
		    	      cell.setCellValue("STUDENT CLASS");
		    	      cell.setCellStyle(RotateStyle);
		    	      
		    	      cell=row.createCell(4);
		    	      cell.setCellValue("CIVICS");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(5);
		    	     
		    	      cell.setCellValue("HISTORY");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(6);
		    	      
		    	      cell.setCellValue("GEOGRAPHY");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(7);
		    	      cell.setCellValue("KISWAHILI");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(8);
		    	      cell.setCellValue("ENGLISH LANGUAGE");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(9);
		    	      cell.setCellValue("LITERATURE IN ENGLISH");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(10);
		    	      cell.setCellValue("PHYSICS");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(11);
		    	      cell.setCellValue("CHEMISTRY");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(12);
		    	      cell.setCellValue("BIOLOGY");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(13);
		    	      cell.setCellValue("COMPUTER STUDIES");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(14);
		    	      cell.setCellValue("BASIC MATHEMATICS");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(15);
		    	      cell.setCellValue("HOME ECONOMICS");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(16);
		    	      cell.setCellValue("COMMERCE");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(17);
		    	      cell.setCellValue("BOOK-KEEPING");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(18);
		    	      cell.setCellValue("FOOD AND NUTRITION");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(19);
		    	      cell.setCellValue("ISLAMIC KNOWLEDGE");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(20);
		    	      cell.setCellValue("DIVINITY");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell17=row.createCell(21);
		    	      cell17.setCellValue("POINT");
		    	      cell17.setCellStyle(RotateStyle);
		    	      cell18=row.createCell(22);
		    	      cell18.setCellValue("DIVISION");
		    	      cell18.setCellStyle(RotateStyle);
		    	      cell19=row.createCell(23);
		    	      cell19.setCellValue("AVERAGE");
		    	      cell19.setCellStyle(RotateStyle);
		    	      cell19 = row.createCell(24);
		    	      cell19.setCellValue("POSITION");
		    	      cell19.setCellStyle(RotateStyle);
		    	      int i=1;
		    	      int space=0,grad_i=0,grad_ii=0,grad_iii=0,grad_iv=0,grad_0=0;
		         while(rs1.next())
		         {
		        	 pointe.clear();
		        	 uzit.clear();
		        	 stucount++;
		        	 String leoh = rs1.getString("student_id");
		        	 String name = rs1.getString("firstName")+"  "+rs1.getString("middleName")+"  "+rs1.getString("lastName");
		        	 String studclas = rs1.getString("class");
		            row=spreadsheet.createRow(i);
		            cell=row.createCell(0);
		            cell.setCellValue(rs1.getString("student_id"));
		            String sex= rs1.getString("Gender");
		            if(sex.equals("FEMALE")){
		            cell=row.createCell(1);
		            cell.setCellValue("F");
		            }else if(sex.equals("MALE")){
		                cell=row.createCell(1);
		                cell.setCellValue("M"); 
		            }
		            cell=row.createCell(2); 
		            cell.setCellValue(name);
		            stP1 = con.createStatement();
		            String mrow = "SET @row_number=0 ";
				      String nafa = null;
			        String posQ =  "SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id "
							+ "FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher "
							+ "WHERE year='"+year+"' AND (class='"+clasi+"A' OR class='"+clasi+"B' OR class='"+clasi+"C' OR "
									+ "class='"+clasi+"D' OR class='"+clasi+"E' OR class='"+clasi+"F' OR class='"+clasi+"G') and type='"+type+"' "
									+ "group by stu_id order by wastani DESC) as tablewastan) AS MM WHERE stu_id='"+leoh+"'";
			        		stP1.execute(mrow);
			        rsP1 = stP1.executeQuery(posQ);
			        if(rsP1.next()){
			        	nafa = rsP1.getString("ROW");
			        }
			        cell19=row.createCell(24);
			        cell19.setCellValue(nafa);
			        stP1.close(); rsP1.close();
		          
		            
		            cell=row.createCell(3);
		            cell.setCellValue(studclas);
	
		            cell=row.createCell(4);
		            cell.setCellValue(rs1.getString("sub2"));
		            
		            cell=row.createCell(5);
		          	 cell.setCellValue(rs1.getString("sub3"));
		          	 
		          	cell=row.createCell(6);
		         	 cell.setCellValue(rs1.getString("sub1"));
		         	 
		         	cell=row.createCell(7);
		         	 cell.setCellValue(rs1.getString("sub4"));
		         	 
		         	cell=row.createCell(8);
		        	 cell.setCellValue(rs1.getString("sub6"));
		        	 
		        		cell=row.createCell(9);
		           	 cell.setCellValue(rs1.getString("sub13"));
		        	 
		        		cell=row.createCell(10);
		           	 cell.setCellValue(rs1.getString("sub7"));
		           	 
		         	cell=row.createCell(11);
		       	 cell.setCellValue(rs1.getString("sub8"));
		       	 
		     	cell=row.createCell(12);
		    	 cell.setCellValue(rs1.getString("sub11"));
		         	 
		         	cell=row.createCell(13);
		         	 cell.setCellValue(rs1.getString("sub5"));
		         	 
		         	cell=row.createCell(14);
		       	 cell.setCellValue(rs1.getString("sub12"));
		       	 
		     	cell=row.createCell(16);
		    	 cell.setCellValue(rs1.getString("sub9"));
		    	 
		    		cell=row.createCell(17);
		       	 cell.setCellValue(rs1.getString("sub10"));
		       	 
		       	cell=row.createCell(18);
		      	 cell.setCellValue(rs1.getString("sub16"));
		      	 
		    	cell=row.createCell(19);
		    	 cell.setCellValue(rs1.getString("sub14"));
		    	 
		    		cell=row.createCell(20);
		      	 cell.setCellValue(rs1.getString("sub15"));
		      	 
		      	 double civ,hist,geo,kisw,comp,eng,phy=-55.0,chem=-55.0,commerc,bkee,bio,math,islam,food,divnty,lite;
		      	 if(rs1.getString("sub1")== null){
		      		
		      	 }else{
		      		geo = Double.parseDouble(rs1.getString("sub1"));
		      		pointe.add(geo);
		      		
		      	 }
		      	 if(rs1.getString("sub2")== null){
		    
		      	 }else{
		      		civ = Double.parseDouble(rs1.getString("sub2"));
		      		pointe.add(civ);
		      		
		      	 }
		      	 if(rs1.getString("sub3")== null){
		      	
		      	 }else{
		      		hist = Double.parseDouble(rs1.getString("sub3"));
		      		pointe.add(hist);
		      	 }
		      	 if(rs1.getString("sub4")== null){
		      	
		      	 }else{
		      		kisw = Double.parseDouble(rs1.getString("sub4"));
		      		pointe.add(kisw);
		      	 }
		      	 if(rs1.getString("sub5")== null){
		      	
		      	 }else{
		      		comp = Double.parseDouble(rs1.getString("sub5"));
		      		pointe.add(comp);
		      	 }
		      	 if(rs1.getString("sub6")== null){
		      		
		      	 }else{
		      		eng = Double.parseDouble(rs1.getString("sub6"));
		      		pointe.add(eng);
		      	 }
		      	 if(rs1.getString("sub7")== null){
		      	
		      		
		      	 }else{
		      		phy = Double.parseDouble(rs1.getString("sub7"));
		      		pointe.add(phy);
		      		
		      	 }
		      	 if(rs1.getString("sub8")== null){
		    
		      		
		      	 }else{
		      		chem = Double.parseDouble(rs1.getString("sub8"));
		      		pointe.add(chem);
		      		
		      	 }
		      	 if(rs1.getString("sub9")== null){
		      	
		      	 }else{
		      		commerc = Double.parseDouble(rs1.getString("sub9"));
		      		pointe.add(commerc);
		      	 }
		      	 if(rs1.getString("sub10")== null){
		      	
		      	 }else{
		      		bkee = Double.parseDouble(rs1.getString("sub10"));
		      		pointe.add(bkee);
		      	 }
		      	 if(rs1.getString("sub11")== null){
		      
		      	 }else{
		      		bio = Double.parseDouble(rs1.getString("sub11"));
		      		pointe.add(bio);
		      	 }
		      	 if(rs1.getString("sub12")== null){
		      	
		      	 }else{
		      		math = Double.parseDouble(rs1.getString("sub12"));
		      		pointe.add(math);
		      	 } 	 if(rs1.getString("sub16")== null){
		      	
		      	 }else{
		      		food = Double.parseDouble(rs1.getString("sub16"));
		      		pointe.add(food);
		      	 }
		      	 if(rs1.getString("sub14")== null){
		      	
		      	 }else{
		      		islam = Double.parseDouble(rs1.getString("sub14"));
		      		pointe.add(islam);
		      	 }
		      	 if(rs1.getString("sub15")== null){
		      	
		      	 }else{
		      		divnty = Double.parseDouble(rs1.getString("sub15"));
		      		pointe.add(divnty);
		      	 }	 if(rs1.getString("sub13")== null){
		      		
		      	 }else{
		      		lite = Double.parseDouble(rs1.getString("sub13"));
		      		pointe.add(lite);
		      	 }
		      	if(chem != -55.0 && phy != -55.0){
		      		secti = "SCIENCE";
		      	}else{
		      		secti = "ARTS";
		      	}
		      	 /*
		      	*/
		      
		    Collections.sort(pointe);
		   double sumAv = 0;
		   int counterAv = 0;
		   for(int k=0; k<pointe.size(); k++){
		   		
		   			sumAv+=pointe.get(k);
		   			 counterAv++;
			
		   	}
		   	
		   double avgMe = Math.round((sumAv/counterAv)*100.0)/100.0;
		   String avere = Double.toString(avgMe);
		   String divPoint = null;
	    	String divi = null;

	    	cell19=row.createCell(23);
	    	cell19.setCellValue(avgMe);
	    	
	    	/*
		    * GRADES FROM AVERAGE
		    */
	
		    	if(pointe.size() < 7){
		    		cell18=row.createCell(22);
	    			 cell18.setCellValue("Incomplete");
		     	}else{
		     for(int j=0; j<pointe.size(); j++){
		   	//	JOptionPane.showMessageDialog(null, grade[i]);

		   		if(pointe.get(j) >=74.5 && pointe.get(j)<=100){
		   			uzit.add(1);
		   		}else if(pointe.get(j) >=64.5 && pointe.get(j)<74.5){
		   			uzit.add(2);
		   		}else if(pointe.get(j) >=44.5 && pointe.get(j)<64.5){
		   			uzit.add(3);
		   		}else if(pointe.get(j) >=29.5 && pointe.get(j)<44.5){
		   			uzit.add(4);
		   		}else if(pointe.get(j) >=0 && pointe.get(j)<29.5){
		   			uzit.add(5);
		   		}else{
		   			//JOptionPane.showMessageDialog(null, "One of the marks in the sheet to be generated \n Exceeds 100%. please correct it during uploads.","ERROR",JOptionPane.WARNING_MESSAGE);
		   		}
		    
		     }
			 int	sum = 0;
			 Collections.sort(uzit);
			 uzit = uzit.subList(0,7);
		    	for(int k=0; k<uzit.size(); k++){
		    		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
		    		sum+=uzit.get(k);
		    	}
		    	
		    	 divPoint = Integer.toString(sum);
		    	 cell17=row.createCell(21);
			       	cell17.setCellValue(divPoint);
			    	
		    		if(sum >=7 && sum<=17){
		    			cell18=row.createCell(22);
		    			 cell18.setCellValue("I");
		    			 grad_i++;
		    		}else if(sum >=18 && sum<=21){
		    			cell18=row.createCell(22);
		    			 cell18.setCellValue("II");
		    			 grad_ii++;
		    		}else if(sum >=22 && sum<=25){
		    			cell18=row.createCell(22);
		    			 cell18.setCellValue("III");
		    			 grad_iii++;
		    		}else if(sum >=26 && sum<=33){
		    			cell18=row.createCell(22);
		    			 cell18.setCellValue("IV");
		    			 grad_iv++;
		    		}else if(sum >=34 && sum<=35){
		    			cell18=row.createCell(22);
		    			 cell18.setCellValue("0");
		    			 grad_0++;
		    		}
		      	 /*
		      	  * 
		      	  */
		   
		    		
		     			}
		  
		            i++;
		            space=i+4;
		         }
		         row=spreadsheet.createRow(space);
		         cell = row.createCell(2);
		         cell.setCellValue("RESULTS DIVISION SUMMARY");
		         cell.setCellStyle(BoldStyle);
		         
		         row=spreadsheet.createRow(space+1);
		         cell = row.createCell(2);
		         cell.setCellValue("DIVISION TITLE/DIVISION");
		         cell.setCellStyle(BoldStyle);
		         
		         cell = row.createCell(3);
		         cell.setCellValue("CANDIDATES");
		         cell.setCellStyle(BoldStyle);
		        /* cell = row.createCell(4);
		         cell.setCellValue("M");
		         cell.setCellStyle(BoldStyle);*/
		         
		         row=spreadsheet.createRow(space+2);
		         cell = row.createCell(2);
		         cell.setCellValue("ONE - I");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_i);
		        /* cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+3);
		         cell = row.createCell(2);
		         cell.setCellValue("TWO - II");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_ii);
		        /* cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+4);
		         cell = row.createCell(2);
		         cell.setCellValue("THREE - III");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_iii);
		         /*cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+5);
		         cell = row.createCell(2);
		         cell.setCellValue("FOUR - IV");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_iv);
		         /*cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+6);
		         cell = row.createCell(2);
		         cell.setCellValue("ZERO - 0");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_0);
		        /* cell = row.createCell(4);
		         cell.setCellValue("0");*/

		         row=spreadsheet.createRow(space+7);
		         cell = row.createCell(2);
		         cell.setCellValue("ABSENTEES");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue((stucount-(grad_i+grad_ii+grad_iii+grad_iv+grad_0)));
		         /*cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+8);
		         cell = row.createCell(2);
		         cell.setCellValue("TOTAL No. OF CANDIDATES");
		         cell.setCellStyle(BoldStyle);
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_i+grad_ii+grad_iii+grad_iv+grad_0+(stucount-(grad_i+grad_ii+grad_iii+grad_iv+grad_0)));
		         cell.setCellStyle(BoldStyle);
		        /* cell = row.createCell(4);
		         cell.setCellValue("10");
		         cell.setCellStyle(BoldStyle);*/
		         
		         FileOutputStream out = new FileOutputStream(
		         new File(clasi+" mkeka.xls"));
		         workbook.write(out);
		         out.close();
		       sheet = 1;
		         // JOptionPane.showMessageDialog(null,clasi+" GENERAL RESULT CREATED"+" \n"+year);
		    		}catch(Exception e){
		    				e.printStackTrace();
		    		sheet = 2;
		    		}
		       
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		             
		           return new Integer(sheet);
		           
		           
		        });

	    	}
	    @SuppressWarnings("unchecked")
		public void FillForm3(String clasi,String year,String type){
	    	wd = new WorkIndicatorDialog(null, "generating sheet ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	 		 if(outres.equals("1")){
	 	        	  TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.INFORMATION);
				       tray.setTitle("general sheet generated with success");
				       tray.setMessage("select Product data on the table above");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
				       openMkeka(clasi);
	 	           }else if(outres.equals("0")){
	 	        	  TrayNotification tray = new TrayNotification();
				       tray.setNotificationType(NotificationType.WARNING);
				       tray.setTitle("error generating sheet. retry");
				       tray.setMessage("try to close any open sheet and generate again");
				       tray.setAnimationType(AnimationType.SLIDE);
				       tray.showAndDismiss(Duration.millis(4000));
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		try{
		    		HSSFWorkbook workbook = new HSSFWorkbook(); 
		    	      HSSFSheet spreadsheet = workbook
		    	      .createSheet("Student names");
		    	      HSSFRow row=spreadsheet.createRow(0);
		    	      HSSFCellStyle RotateStyle = workbook.createCellStyle();
		    	      HSSFCellStyle BoldStyle = workbook.createCellStyle();
		    	      RotateStyle.setRotation((short)90);
		    	      HSSFFont my_font = workbook.createFont();
		    	      
		    	      stT = con.createStatement();
		    	      String sql = "SELECT * FROM school_details";
		    	      String title=null;
		    	      rsT = stT.executeQuery(sql);
		    	      if(rsT.next()){
		    	     	  title = rsT.getString("title");
		    	      }
		    	      
		    	      stT.close();
		    	      rsT.close();	 
		    	    
		    	      int stucount=0;
		    	
		    	      Header header = spreadsheet.getHeader();
		    	      header.setCenter(HSSFHeader.font("Times New Roman", "Bold")+HSSFHeader.fontSize((short)14)+title+"\n"
		    	      		+type.toUpperCase()+ " GROSS PERFORMANCE REPORT "+clasi+" "+year);
		    	      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    	      spreadsheet.setColumnWidth(0, 2500);
		    	      spreadsheet.setColumnWidth(1, 1500);
		    	      spreadsheet.setColumnWidth(2, 5000);
		    	      spreadsheet.setColumnWidth(3, 3000);
		    	      spreadsheet.setColumnWidth(4, 2000);
		    	      spreadsheet.setColumnWidth(5, 2000);
		    	      spreadsheet.setColumnWidth(6, 2000);
		    	      spreadsheet.setColumnWidth(7, 2000);
		    	      spreadsheet.setColumnWidth(8, 2000);
		    	      spreadsheet.setColumnWidth(9, 2000);
		    	      spreadsheet.setColumnWidth(10, 2000);
		    	      spreadsheet.setColumnWidth(11, 2000);
		    	      spreadsheet.setColumnWidth(12, 2000);
		    	      spreadsheet.setColumnWidth(13, 2000);
		    	      spreadsheet.setColumnWidth(14, 2000);
		    	      spreadsheet.setColumnWidth(15, 2500);
		    	      spreadsheet.setColumnWidth(16, 2500);
		    	      spreadsheet.setColumnWidth(18, 2500);
		    	
		    	      BoldStyle.setFont(my_font);
		    	      RotateStyle.setFont(my_font);
		    	      HSSFCell cell,cellpnt,celldiv,cellAvg;
		    	      cell=row.createCell(0);
		    	      cell.setCellValue("REG NO.");
		    	      cell.setCellStyle(BoldStyle);
		    	      cell=row.createCell(1);
		    	      cell.setCellValue("SEX");
		    	      cell.setCellStyle(BoldStyle);
		    	      cell=row.createCell(2);
		    	      cell.setCellValue("STUDENT NAME");		
		    	      cell.setCellStyle(BoldStyle);
		    	      cell=row.createCell(3);
		    	      cell.setCellValue("STUDENT CLASS");
		    	      cell.setCellStyle(RotateStyle);
		    	      
		    	      cell=row.createCell(4);
		    	      cell.setCellValue("GENERAL STUDIES");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(5);
		    	     
		    	      cell.setCellValue("BAM");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(6);
		    	      
		    	      cell.setCellValue("HISTORY");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(7);
		    	      cell.setCellValue("GEOGRAPHY");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(8);
		    	      cell.setCellValue("KISWAHILI");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(9);
		    	      cell.setCellValue("ENGLISH LANGUAGE");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(10);
		    	      cell.setCellValue("PHYSICS");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(11);
		    	      cell.setCellValue("CHEMISTRY");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(12);
		    	      cell.setCellValue("BIOLOGY");
		    	      cell.setCellStyle(RotateStyle);
		    	     
		    	      cell=row.createCell(13);
		    	      cell.setCellValue("ECONOMICS");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(14);
		    	      cell.setCellValue("ADVANCE MATH");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(15);
		    	      cell.setCellValue("FOOD AND NUTRITION");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(16);
		    	      cell.setCellValue("POINT");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(17);
		    	      cell.setCellValue("DIVISION");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(18);
		    	      cell.setCellValue("AVERAGE");
		    	      cell.setCellStyle(RotateStyle);
		    	      cell=row.createCell(19);
		    	      cell.setCellValue("POSITION");
		    	      cell.setCellStyle(RotateStyle);
		    	      int i=1;
		    	      int space=0,grad_i=0,grad_ii=0,grad_iii=0,grad_iv=0,grad_0=0;
		         while(rs1.next())
		         {
		        	 pointe.clear();
		        	 uzit.clear();
		        	 stucount++;
		        	 String leoh = rs1.getString("student_id");
		        	 String name = rs1.getString("firstName")+"  "+rs1.getString("middleName")+"  "+rs1.getString("lastName");
		        	 String studclasi = rs1.getString("class")+"^";
		        	 String studclas = studclasi.substring(studclasi.indexOf("-")+1,studclasi.indexOf("^"));
		        	// pinfos.substring(pinfos.indexOf("@")+1,pinfos.indexOf("#"));
		            row=spreadsheet.createRow(i);
		            cell=row.createCell(0);
		            cell.setCellValue(rs1.getString("student_id"));
		            String sex= rs1.getString("Gender");
		            if(sex.equals("FEMALE")){
		            cell=row.createCell(1);
		            cell.setCellValue("F");
		            }else if(sex.equals("MALE")){
		                cell=row.createCell(1);
		                cell.setCellValue("M"); 
		            }
		            
		            stP1 = con.createStatement();
		            
		            String mrow = "SET @row_number=0 ";
				      String nafa = null;
			        String posQ =  "SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id "
							+ "FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher "
							+ "WHERE year='"+year+"' AND class LIKE '"+clasi+"-%' AND type='"+type+"' AND NOT coz_id='141' AND NOT coz_id='111' "
									+ "group by stu_id order by wastani DESC) as tablewastan) AS MM WHERE stu_id='"+leoh+"'";
			        		stP1.execute(mrow);
			        rsP1 = stP1.executeQuery(posQ);
			        
			        if(rsP1.next()){
			        	nafa = rsP1.getString("ROW");
			        }
			        
			        cell=row.createCell(19);
			        cell.setCellValue(nafa);
			        stP1.close(); rsP1.close();
		            cell=row.createCell(2); 
		            cell.setCellValue(name);
		            
		            cell=row.createCell(3);
		            cell.setCellValue(studclas);
		            
		            cell=row.createCell(4);
		            cell.setCellValue(rs1.getString("sub1"));
		            
		            cell=row.createCell(5);
		          	 cell.setCellValue(rs1.getString("sub9"));
		          	 
		          	cell=row.createCell(6);
		         	 cell.setCellValue(rs1.getString("sub2"));
		         	 
		         	cell=row.createCell(7);
		         	 cell.setCellValue(rs1.getString("sub3"));
		         	 
		         	cell=row.createCell(8);
		        	 cell.setCellValue(rs1.getString("sub10"));
		        	 
		        		cell=row.createCell(9);
		           	 cell.setCellValue(rs1.getString("sub4"));
		        	 
		        		cell=row.createCell(10);
		           	 cell.setCellValue(rs1.getString("sub5"));
		           	 
		         	cell=row.createCell(11);
		       	 cell.setCellValue(rs1.getString("sub6"));
		       	 
		     	cell=row.createCell(12);
		    	 cell.setCellValue(rs1.getString("sub7"));
		         	 
		         	cell=row.createCell(13);
		         	 cell.setCellValue(rs1.getString("sub8"));
		         	 
		         	cell=row.createCell(14);
		       	 cell.setCellValue(rs1.getString("sub11"));
		       	 
		       	cell=row.createCell(15);
		      	 cell.setCellValue(rs1.getString("sub12"));
		      	 
		      	double hist,geo,kisw,eng,phy=-55.0,chem=-55.0,econo,bio=-55.0,advmath=-55.0,fudNut=-55.0;
		      	
		      	 if(rs1.getString("sub2")== null){
		      	
		      	 }else{
		      		hist = Double.parseDouble(rs1.getString("sub2"));
		      		pointe.add(hist);
		      		
		      	 }
		      	 if(rs1.getString("sub3")== null){
		      		
		      	 }else{
		      		geo = Double.parseDouble(rs1.getString("sub3"));
		      		pointe.add(geo);
		      	 }
		      	 if(rs1.getString("sub4")== null){
		      	
		      	 }else{
		      		eng = Double.parseDouble(rs1.getString("sub4"));
		      		pointe.add(eng);
		      	 }
		      	 if(rs1.getString("sub5")== null){
		      	
		      	 }else{
		      		phy = Double.parseDouble(rs1.getString("sub5"));
		      		pointe.add(phy);
		      	 }
		      	 if(rs1.getString("sub6")== null){
		     
		      		
		      	 }else{
		      		chem = Double.parseDouble(rs1.getString("sub6"));
		      		pointe.add(chem);
		      		
		      	 }
		      	 if(rs1.getString("sub7")== null){
		      	
		      		
		      	 }else{
		      		bio = Double.parseDouble(rs1.getString("sub7"));
		      		pointe.add(bio);
		      		
		      	 }
		      	 if(rs1.getString("sub8")== null){
		      		 
		      	 }else{
		      		econo = Double.parseDouble(rs1.getString("sub8"));
		      		pointe.add(econo);
		      	 }
		      	 if(rs1.getString("sub11")== null){
		      	
		      	 }else{
		      		advmath = Double.parseDouble(rs1.getString("sub11"));
		      		pointe.add(advmath);
		      	 }
		      	 if(rs1.getString("sub10")== null){
		      	
		      	 }else{
		      		kisw = Double.parseDouble(rs1.getString("sub10"));
		      		pointe.add(kisw);
		      	 }if(rs1.getString("sub12")== null){
		      		
		      	 }else{
		      		fudNut = Double.parseDouble(rs1.getString("sub12"));
		      		pointe.add(fudNut);
		      	 }
		      	if(chem != -55.0 || phy != -55.0 || bio !=-55.0 || fudNut !=-55.0 || advmath !=-55.0 ){
		      		secti = "SCIENCE";
		      	}else{
		      		secti = "ARTS";
		      	}
		      	 /*
		      	*/
		      
		     	Collections.sort(pointe);
		     	double sumAv = 0;
		     
		     	
		   	int count = 0;
		     	for(int n=0; n<pointe.size(); n++){
		     		
		     			count++;
		     			sumAv+=pointe.get(n);
		     		
		     	}
		     	double avg = Math.round((sumAv/count)*100.0)/100.0;
		     	String avere = Double.toString(avg);
		     	cellpnt=row.createCell(18);
	       		cellpnt.setCellValue(avere);
		     	 /*
				    * GRADES FROM AVERAGE
				    */
		     	
				   String showAver = null;
				   if(avg >=79.5 && avg<=100){
					   	showAver = "A";
							//grad_a++;
							}else if(avg >=69.5 && avg<79.5){
								showAver = "B";
								//grad_b++;
							}else if(avg >=59.5 && avg<69.5){
								showAver = "C";
								//grad_c++;
							}else if(avg >=49.5 && avg<59.5){
								showAver = "D";
								//grad_d++;
							}else if(avg >=39.5 && avg<49.5){
								showAver = "E";
								//grad_f++;
							}else if(avg >=33.5 && avg<39.5){
								showAver = "S";
								//grad_f++;
							}else if(avg >=0 && avg<33.5){
								showAver = "F";
								//grad_f++;
							}else{
								
							}
				  // System.out.println("ORG:"+Arrays.toString(points));
		     	//JOptionPane.showMessageDialog(null, "COUNT "+count);
		     	
		     	if(pointe.size() < 3){
		     		celldiv=row.createCell(17);
		    		celldiv.setCellValue("Incomplete");
		     	}else{
		     //	System.out.println("GRADE2:"+Arrays.toString(grade2));
		     	  for(int j=0; j<pointe.size(); j++){
		     		   	//	JOptionPane.showMessageDialog(null, "grade2 "+grade2[j]);

		     		   		if(pointe.get(j) >=79.5 && pointe.get(j)<=100){
		     		   			uzit.add(1);
		     		   		}else if(pointe.get(j) >=69.5 && pointe.get(j)<79.5){
		     		   		uzit.add(2);
		     		   		}else if(pointe.get(j) >=59.5 && pointe.get(j)<69.5){
		     		   		uzit.add(3);
		     		   		}else if(pointe.get(j) >=49.5 && pointe.get(j)<59.5){
		     		   		uzit.add(4);
		     		   		}else if(pointe.get(j) >=39.5 && pointe.get(j)<49.5){
		     		   		uzit.add(5);
		     		   		}else if(pointe.get(j) >=34.5 && pointe.get(j)<39.5){
		     		   		uzit.add(6);
		     		   		}
		     		   		else if(pointe.get(j) >=0 && pointe.get(j)<34.5){
		     		   		uzit.add(7);
		     		   		}else{
		     		   			
		     		   		}
		     		   		
		     		     }
		     //	 System.out.println("UZITO2:"+Arrays.toString(uzito2));
		     	 int	sum = 0;
		     	 String divi = null;
		     	 String divPoint;
		     	 Collections.sort(uzit);
		       	for(int k=0; k<uzit.size(); k++){
		       		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
		       		sum+=uzit.get(k);
		       	}
		       //	System.out.println("SUM:"+sum);
		       	divPoint = Integer.toString(sum);
		       	cellpnt=row.createCell(16);
	       		cellpnt.setCellValue(divPoint);
	       		celldiv=row.createCell(17);
	       		if(sum >=3 && sum<=9){
	       			celldiv.setCellValue("I");
	       			grad_i++;
	       		}else if(sum >=10 && sum<=12){
	       			
	       			celldiv.setCellValue("II");
	       			grad_ii++;
	       		}else if(sum >=13 && sum<=17){
	       			
	       			celldiv.setCellValue("III");
	       			grad_iii++;
	       		}else if(sum >=18 && sum<=19){
	       			
	       			celldiv.setCellValue("IV");
	       			grad_iv++;
	       		}else if(sum >=20 && sum<=21){
	       			
	       			celldiv.setCellValue("0");
	       			grad_0++;
	       		}
		
		      	 /*
		      	  * 
		      	  */
		  
		         }
		            i++;
		            space=i+4;
		         }
		         row=spreadsheet.createRow(space);
		         cell = row.createCell(2);
		         cell.setCellValue("RESULTS DIVISION SUMMARY");
		         cell.setCellStyle(BoldStyle);
		         
		         row=spreadsheet.createRow(space+1);
		         cell = row.createCell(2);
		         cell.setCellValue("DIVISION TITLE/DIVISION");
		         cell.setCellStyle(BoldStyle);
		         
		         cell = row.createCell(3);
		         cell.setCellValue("CANDIDATES");
		         cell.setCellStyle(BoldStyle);
		        /* cell = row.createCell(4);
		         cell.setCellValue("M");
		         cell.setCellStyle(BoldStyle);*/
		         
		         row=spreadsheet.createRow(space+2);
		         cell = row.createCell(2);
		         cell.setCellValue("ONE - I");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_i);
		        /* cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+3);
		         cell = row.createCell(2);
		         cell.setCellValue("TWO - II");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_ii);
		        /* cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+4);
		         cell = row.createCell(2);
		         cell.setCellValue("THREE - III");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_iii);
		         /*cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+5);
		         cell = row.createCell(2);
		         cell.setCellValue("FOUR - IV");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_iv);
		         /*cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+6);
		         cell = row.createCell(2);
		         cell.setCellValue("ZERO - 0");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_0);
		        /* cell = row.createCell(4);
		         cell.setCellValue("0");*/

		         row=spreadsheet.createRow(space+7);
		         cell = row.createCell(2);
		         cell.setCellValue("ABSENTEES & INCOMPLETE");
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue((stucount-(grad_i+grad_ii+grad_iii+grad_iv+grad_0)));
		         /*cell = row.createCell(4);
		         cell.setCellValue("0");*/
		         
		         row=spreadsheet.createRow(space+8);
		         cell = row.createCell(2);
		         cell.setCellValue("TOTAL No. OF CANDIDATES");
		         cell.setCellStyle(BoldStyle);
		         //GRADE A SCORE
		         cell = row.createCell(3);
		         cell.setCellValue(grad_i+grad_ii+grad_iii+grad_iv+grad_0+(stucount-(grad_i+grad_ii+grad_iii+grad_iv+grad_0)));
		         cell.setCellStyle(BoldStyle);
		        /* cell = row.createCell(4);
		         cell.setCellValue("10");
		         cell.setCellStyle(BoldStyle);*/
		         FileOutputStream out = new FileOutputStream(
		         new File(clasi+" mkeka.xls"));
		         workbook.write(out);
		         out.close();
		        sheet = 1;
		         // JOptionPane.showMessageDialog(null,clasi+"GENERAL RESULT CREATED"+" \n"+year);
		    		}catch(Exception e){
		    			e.printStackTrace();
		    			sheet = 0;
		    	}
		       
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		             
		           return new Integer(sheet);
		           
		           
		        });
	    
	    	
	    	}
	    @SuppressWarnings("unchecked")
		public void openMkeka(String clasi){
	    	
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
	 	 			int mkmes=0;
	 	 			  try {

	 	 	  			if ((new File(clasi+" mkeka.xls")).exists()) {

	 	 	  				Process p = Runtime
	 	 	  				   .getRuntime()
	 	 	  				   .exec("rundll32 url.dll,FileProtocolHandler "+clasi+" mkeka.xls");
	 	 	  				p.waitFor();
	 	 	  				mkmes = 1;	
	 	 	  			} else {
	 	 	  				mkmes = -1;
	 	 	  				//JOptionPane.showMessageDialog(null, "File does not exist");

	 	 	  			}

	 	 	  			

	 	 	  	  	  } catch (Exception ex) {
	 	 	  	  		  mkmes = 2;
	 	 	  	  		/*JOptionPane.showMessageDialog(null, "File is open in another application(EXCEL) \n"
	 	 	  	  		  		+ "please close the application and restart \n"
	 	 	  	  		  		+ "this process to create the file.");*/

	 	 	  		  }
	 		       
	 		               try {
	 		                  Thread.sleep(1000);
	 		               }	
	 		               catch (InterruptedException er) {
	 		                  er.printStackTrace();
	 		               }
	 		             
	 		           return new Integer(mkmes);
	 		           
	 		           
	 		        });
	  
	  }
	public static void sorter(double[] num){
		int j;
		boolean flag = true;
		double temp;
		
		while(flag){
			flag = false;
			for(j=0; j<num.length-1; j++){
				if(num[j] < num[j+1]){
					temp= num[j];
					num[j] = num[j+1];
					num[j+1] = temp;
					flag = true;
				}
			}
		}
	}
}
