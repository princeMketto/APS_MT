package aps.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import aps.view.innerview.GenReport;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class SummaryContr implements Initializable {
	  @FXML
	    private AnchorPane anchor;

	    @FXML
	    private ChoiceBox<String> classchoice;

	    @FXML
	    private ChoiceBox<String> yearchoice;

	    @FXML
	    private RadioButton terminal;

	    @FXML
	    private ToggleGroup examtype;

	    @FXML
	    private RadioButton annual;

	    @FXML
	    private AreaChart<String,Integer> graph1;

	    @FXML
	    private Text summarylab;

	    @FXML
	    private PieChart piegraph;

	    @FXML
	    private JFXButton goBtn;

	    @FXML
	    private JFXButton hintbtn;
		LoggerM log=new LoggerM();
	static String examTy;
	String darasa=null,year=null;
	private WorkIndicatorDialog wd=null;
	ConnectDB database = new ConnectDB();
	 private Connection con;
	    private ResultSet rs;
	    private Statement st;
	    private PreparedStatement prep;
	    ObservableList<String>list = FXCollections.observableArrayList();
	    ObservableList<String>list1 = FXCollections.observableArrayList();
	    ObservableList<PieChart.Data> pieChartData;
	    List listofI = new ArrayList();
	    double []points = new double[17];
		    double []grade = new double[7];
		    double []grade2 = new double[3];
		    double []uzito2 = new double[3];
		    double []uzito = new double[7];
		    int scienc=0,art=0;
		    int space=0,grad_i=0,grad_ii=0,grad_iii=0,grad_iv=0,grad_0=0;
			  int grad_a = 0,grad_b=0,grad_c=0,grad_d=0,grad_f=0;
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
    void fill(ActionEvent event) {
    	  graph1.getData().clear();
    	  piegraph.getData().clear();
		   try{
			
	    			
	    			 darasa = classchoice.getSelectionModel().getSelectedItem();
	    			 year = yearchoice.getSelectionModel().getSelectedItem();
	    			//System.out.println("START:"+start+"\t END:"+end+"\t TIME:"+timing);
	    			 if(!(darasa.equals("choose class here") && year.equals("choose year here")) && 
	    						(terminal.isSelected() || annual.isSelected())){

	    					try{
	    						con= database.dbconnect();
	    						st= con.createStatement();
	    					
	    						if(darasa.equals("FORM I")){
	    							String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,"
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
	    						//	hardprint = darasa+"-"+year+"-"+examTy;
	    							fillTable(darasa,year);
	    						}else if(darasa.equals("FORM II")){
	    							String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,"
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
	    							fillTable(darasa,year);
	    							//hardprint = darasa+"-"+year+"-"+examTy;
	    						}else if(darasa.equals("FORM III")){
	    							String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,"
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
	    				//	hardprint = darasa+"-"+year+"-"+examTy;
							fillTable(darasa,year);
	    						}else if(darasa.equals("FORM IV")){
	    							String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,"
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
	    							//hardprint = darasa+"-"+year+"-"+examTy;
	    								    							fillTable(darasa,year);;
	    						}else if(darasa.equals("FORM V")){
	    							String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,"
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
	    						
	    						//	hardprint = darasa+"-"+year+"-"+examTy;
	    								    							fillTable(darasa,year);;
	    						}else if(darasa.equals("FORM VI")){
	    							String query = "SELECT a.student_id,a.Gender,a.firstName,a.middleName,a.lastName,a.status,a.studentClass,"
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
	    									+ "MAX(CASE WHEN b.coz_id = '155' AND b.class LIKE '%RM VI-%' AND b.type='"+examTy+"' AND b.year='"+year+"' THEN b.wastani ELSE NULL END ) sub11 "
	    									+ "FROM students a "
	    									+ "INNER JOIN result_from_teacher b "
	    									+ "ON a.student_id = b.stu_id WHERE NOT (a.status='DISABLED') AND b.class LIKE '%RM VI%' AND b.year='"+year+"' "
	    									+ "GROUP BY a.student_id "
	    									+ "ORDER BY a.Gender ASC,a.firstName ASC ";	
	    							rs=st.executeQuery(query);
	    						//	hardprint = darasa+"-"+year+"-"+examTy;
	    							fillTable(darasa,year);;
	    						}
	    					}catch(SQLException sq){
	    						
	    					}
	    				
	    			 }
	    		
		   }catch(Exception e){
			   e.printStackTrace();
			   TrayNotification tray = new TrayNotification();
		       tray.setNotificationType(NotificationType.ERROR);
		       tray.setTitle("PLEASE CHOOSE START, END DATE");
		       tray.setMessage("System cant calculate profit \n with empty time");
		       tray.setAnimationType(AnimationType.SLIDE);
		       tray.showAndDismiss(Duration.millis(4000));    
		   }
    }

    @SuppressWarnings("unchecked")
	private void fillTable(String darasa2,String year) {

		if(darasa2.equals("FORM I")){
			wd = new WorkIndicatorDialog(null, "Sketching Graph...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
	 	        	 
						 XYChart.Series<String,Integer> series1 = new XYChart.Series<>();
						graph1.setTitle("SUMMARY GRADE CHART FOR "+darasa+" "+year+" ");
						 series1.setName("GRADE A:"+grad_a);
						 series1.getData().add(new XYChart.Data<>("A",grad_a));
						 series1.setName("GRADE B:"+grad_b);
						 series1.getData().add(new XYChart.Data<>("B",grad_b));
						series1.setName("GRADE C:"+grad_c);
						 series1.getData().add(new XYChart.Data<>("C",grad_c));
						series1.setName("GRADE D:"+grad_d);
						 series1.getData().add(new XYChart.Data<>("D",grad_d));
						 
						series1.setName("GRADE F:"+grad_f);
						series1.getData().add(new XYChart.Data<>("F",grad_f));
						 graph1.getData().addAll(series1); 
						 summarylab.setText("GRADE A:"+grad_a+"\t"+"GRADE B:"+grad_b+"\t GRADE C:"+grad_c+"\t GRADE D:"+grad_d+"\t GRADE F"+grad_f);
	 	       
	 	           /*
	 	            * PIE CHART
	 	            */
						 piegraph.setTitle("PIE CHART SHOWING GRADES SUMMARY FOR "+darasa+" "+year+". ");
						 pieChartData = FXCollections.observableArrayList(
						         new PieChart.Data("GRADE A", grad_a), 
						         new PieChart.Data("GRADE B", grad_b), 
						         new PieChart.Data("GRADE C", grad_c),
						         new PieChart.Data("GRADE D", grad_d), 
						         new PieChart.Data("GRADE F", grad_f));
						 
						 piegraph.getData().addAll(pieChartData);
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		try {
					
					  points = new double[points.length];
					 
					//  listofgen.clear();
					 grad_a = 0;grad_b=0;grad_c=0;grad_d=0;grad_f=0;
						//System.out.println(darasa2+" "+year+" EG:"+examTy);
					
					  while(rs.next()){
						String gend;
						String stuID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("studentClass");
					   	 String sex= rs.getString("Gender");
					 //  	System.out.println("NAME:"+name);
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					    	double civ,hist,geo,kisw,comp,eng,phy,chem,commerc,bkee,bio,math,islam,food,divnty;
					     	 if(rs.getString("sub1")== null){
					     		 geo =00.0;
					     		points[0]=geo;
					     		
					     	 }else{
					     		geo = Double.parseDouble(rs.getString("sub1"));
					     		points[0]=geo;
					     		
					     	 }
					     	 if(rs.getString("sub2")== null){
					     		 civ =00.0;
					     		points[1]=civ;
					     	 }else{
					     		civ = Double.parseDouble(rs.getString("sub2"));
					     		points[1]=civ;
					     		
					     	 }
					     	 if(rs.getString("sub3")== null){
					     		 hist =00.0;
					     		points[2]=hist;
					     	 }else{
					     		hist = Double.parseDouble(rs.getString("sub3"));
					     		points[2]=hist;
					     	 }
					     	 if(rs.getString("sub4")== null){
					     		 kisw =00.0;
					     		points[3]=kisw;
					     	 }else{
					     		kisw = Double.parseDouble(rs.getString("sub4"));
					     		points[3]=kisw;
					     	 }
					     	 if(rs.getString("sub5")== null){
					     		 comp =00.0;
					     		points[4]=comp;
					     	 }else{
					     		comp = Double.parseDouble(rs.getString("sub5"));
					     		points[4]=comp;
					     	 }
					     	 if(rs.getString("sub6")== null){
					     		 eng =00.0;
					     		points[5]=eng;
					     	 }else{
					     		eng = Double.parseDouble(rs.getString("sub6"));
					     		points[5]=eng;
					     	 }
					     	 if(rs.getString("sub7")== null){
					     		 phy =00.0;
					     		points[6]=phy;
					     		
					     	 }else{
					     		phy = Double.parseDouble(rs.getString("sub7"));
					     		points[6]=phy;
					     		
					     	 }
					     	 if(rs.getString("sub8")== null){
					     		 chem =00.0;
					     		points[7]=chem;
					     		
					     	 }else{
					     		chem = Double.parseDouble(rs.getString("sub8"));
					     		points[7]=chem;
					     		
					     	 }
					     	 if(rs.getString("sub9")== null){
					     		 commerc =00.0;
					     		points[8]=commerc;
					     	 }else{
					     		commerc = Double.parseDouble(rs.getString("sub9"));
					     		points[8]=commerc;
					     	 }
					     	 if(rs.getString("sub10")== null){
					     		 bkee =00.0;
					     		points[9]=bkee;
					     	 }else{
					     		bkee = Double.parseDouble(rs.getString("sub10"));
					     		points[9]=bkee;
					     	 }
					     	 if(rs.getString("sub11")== null){
					     		 bio =00.0;
					     		points[10]=bio;
					     	 }else{
					     		bio = Double.parseDouble(rs.getString("sub11"));
					     		points[10]=bio;
					     	 }
					     	 if(rs.getString("sub12")== null){
					     		 math =00.0;
					     		points[11]=math;
					     	 }else{
					     		math = Double.parseDouble(rs.getString("sub12"));
					     		points[11]=math;
					     	 } 	 if(rs.getString("sub13")== null){
					     		 islam =00.0;
					     		points[12]=islam;
					     	 }else{
					     		islam = Double.parseDouble(rs.getString("sub13"));
					     		points[12]=islam;
					     	 }
					     	 if(rs.getString("sub14")== null){
					     		 divnty =00.0;
					     		points[13]=divnty;
					     	 }else{
					     		divnty = Double.parseDouble(rs.getString("sub14"));
					     		points[13]=divnty;
					     	 }
					     	 if(rs.getString("sub15")== null){
					     		 food =00.0;
					     		points[14]=food;
					     	 }else{
					     		food = Double.parseDouble(rs.getString("sub15"));
					     		points[14]=food;
					     	 }
					     	sorter(points);
					    	double	sum = 0;
					     	 int counter = 0;
					      	for(int k=0; k<points.length; k++){
					      		 if(points[k] != 00.0){
					      			sum+=points[k];
					      			 counter++;
					      			
					      		 }
					      		
					      	}
					      	/*
					      	 * 
					      	 */
					      //	System.out.println("ARRAY: "+Arrays.toString(points)+" SUM"+sum+" COUNT"+counter);
					      	 scienc=0; art=0;
					      	if(chem != 00.0 && phy != 00.0){
					      	
					      		scienc++;
					      	}else{
					      		art++;
					      	}
					      String showAver = null;
					    double avg = Math.round((sum/counter)*100.0)/100.0;
					 //   System.out.println(" AVG "+avg);
					    if(avg >=74.5 && avg<=100){
					   	showAver = "A";
							grad_a++;
						//	System.out.println(" A"+grad_a);
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
					    
					   // listofgen.add(new GenReport(stuID,name,studclas,secti,ave,showAver, "N/A")); weka students
					}
				//	System.out.println("GRADES:A "+grad_a+" B"+grad_b+" C"+grad_c+" D"+grad_d+" F"+grad_f);
					st.close();
					rs.close();
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
			wd = new WorkIndicatorDialog(null, "Sketching graph ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
						 XYChart.Series<String,Integer> series1 = new XYChart.Series<>();
						graph1.setTitle("SUMMARY GRADE CHART FOR "+darasa+" "+year+"\n ");
						 series1.setName("DIV I: "+grad_i);
						 series1.setName("DIV II:"+grad_ii);
						 series1.setName("DIV III:"+grad_iii);
						 series1.setName("DIV IV:"+grad_iv);
						 series1.setName("DIV 0:"+grad_0);
						 series1.getData().add(new XYChart.Data<>("I",grad_i));
						 series1.getData().add(new XYChart.Data<>("II",grad_ii));
						 series1.getData().add(new XYChart.Data<>("III",grad_iii));
						 series1.getData().add(new XYChart.Data<>("IV",grad_iv));
						 series1.getData().add(new XYChart.Data<>("0",grad_0));
						 graph1.getData().addAll(series1); 
						 summarylab.setText("DIV I:"+grad_i+"\t"+"DIV II:"+grad_ii+"\t DIV III:"+grad_iii+"\t DIV IV:"+grad_iv+"\t DIV 0:"+grad_0);
						 /*
			 	            * PIE CHART
			 	            */
								 piegraph.setTitle("PIE CHART SHOWING DIVISIONS SUMMARY FOR "+darasa+" "+year+". ");
								 pieChartData = FXCollections.observableArrayList(
								         new PieChart.Data("I", grad_i), 
								         new PieChart.Data("II", grad_ii), 
								         new PieChart.Data("III", grad_iii),
								         new PieChart.Data("IV", grad_iv), 
								         new PieChart.Data("ZERO", grad_0));
								 
								 piegraph.getData().addAll(pieChartData);
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		try {
					
					// int space=0,grad_i=0,grad_ii=0,grad_iii=0,grad_iv=0,grad_0=0;
					 points = new double[points.length];
					// listofgen.clear();
					 grad_i = 0;grad_ii=0;grad_iii=0;grad_iv=0;grad_0=0;
					 while(rs.next()){
						String stID;
						String gend;
								stID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("studentClass");
					   	 String sex= rs.getString("Gender");
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					        double civ,hist,geo,kisw,comp,eng,phy,chem,commerc,bkee,bio,math,islam,food,divnty,lite;
					      	 if(rs.getString("sub1")== null){
					      		 geo =00.0;
					      		points[0]=geo;
					      		
					      	 }else{
					      		geo = Double.parseDouble(rs.getString("sub1"));
					      		points[0]=geo;
					      		
					      	 }
					      	 if(rs.getString("sub2")== null){
					      		 civ =00.0;
					      		points[1]=civ;
					      	 }else{
					      		civ = Double.parseDouble(rs.getString("sub2"));
					      		points[1]=civ;
					      		
					      	 }
					      	 if(rs.getString("sub3")== null){
					      		 hist =00.0;
					      		points[2]=hist;
					      	 }else{
					      		hist = Double.parseDouble(rs.getString("sub3"));
					      		points[2]=hist;
					      	 }
					      	 if(rs.getString("sub4")== null){
					      		 kisw =00.0;
					      		points[3]=kisw;
					      	 }else{
					      		kisw = Double.parseDouble(rs.getString("sub4"));
					      		points[3]=kisw;
					      	 }
					      	 if(rs.getString("sub5")== null){
					      		 comp =00.0;
					      		points[4]=comp;
					      	 }else{
					      		comp = Double.parseDouble(rs.getString("sub5"));
					      		points[4]=comp;
					      	 }
					      	 if(rs.getString("sub6")== null){
					      		 eng =00.0;
					      		points[5]=eng;
					      	 }else{
					      		eng = Double.parseDouble(rs.getString("sub6"));
					      		points[5]=eng;
					      	 }
					      	 if(rs.getString("sub7")== null){
					      		 phy =00.0;
					      		points[6]=phy;
					      		
					      	 }else{
					      		phy = Double.parseDouble(rs.getString("sub7"));
					      		points[6]=phy;
					      		
					      	 }
					      	 if(rs.getString("sub8")== null){
					      		 chem =00.0;
					      		points[7]=chem;
					      		
					      	 }else{
					      		chem = Double.parseDouble(rs.getString("sub8"));
					      		points[7]=chem;
					      		
					      	 }
					      	 if(rs.getString("sub9")== null){
					      		 commerc =00.0;
					      		points[8]=commerc;
					      	 }else{
					      		commerc = Double.parseDouble(rs.getString("sub9"));
					      		points[8]=commerc;
					      	 }
					      	 if(rs.getString("sub10")== null){
					      		 bkee =00.0;
					      		points[9]=bkee;
					      	 }else{
					      		bkee = Double.parseDouble(rs.getString("sub10"));
					      		points[9]=bkee;
					      	 }
					      	 if(rs.getString("sub11")== null){
					      		 bio =00.0;
					      		points[10]=bio;
					      	 }else{
					      		bio = Double.parseDouble(rs.getString("sub11"));
					      		points[10]=bio;
					      	 }
					      	 if(rs.getString("sub12")== null){
					      		 math =00.0;
					      		points[11]=math;
					      	 }else{
					      		math = Double.parseDouble(rs.getString("sub12"));
					      		points[11]=math;
					      	 } 	 if(rs.getString("sub16")== null){
					      		 food =00.0;
					      		points[12]=food;
					      	 }else{
					      		food = Double.parseDouble(rs.getString("sub16"));
					      		points[12]=food;
					      	 }
					      	 if(rs.getString("sub14")== null){
					      		 islam =00.0;
					      		points[13]=islam;
					      	 }else{
					      		islam = Double.parseDouble(rs.getString("sub14"));
					      		points[13]=islam;
					      	 }
					      	 if(rs.getString("sub15")== null){
					      		 divnty =00.0;
					      		points[14]=divnty;
					      	 }else{
					      		divnty = Double.parseDouble(rs.getString("sub15"));
					      		points[14]=divnty;
					      	 }	 if(rs.getString("sub13")== null){
					      		 lite =00.0;
					      		points[15]=lite;
					      	 }else{
					      		lite = Double.parseDouble(rs.getString("sub13"));
					      		points[15]=lite;
					      	 }
					      	 scienc=0; art=0;
					      	if(chem != 00.0 && phy != 00.0){
					      		scienc++;
					      		}else{
					      			art++;
					      			}
					      	 /*
					      	*/
					      
					     	sorter(points);
					   double sumAv = 0;
					   int counterAv = 0;
					   for(int k=0; k<points.length; k++){
					   		 if(points[k] != 00.0){
					   			sumAv+=points[k];
					   			 counterAv++;
					   			
					   		 }
					   		
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
					     	grade = Arrays.copyOfRange(points, 0, 7);
					     	if(grade[0] == 0.0 || grade[1] == 0.0 || grade[2] == 0.0 || grade[3] == 0.0
					    			|| grade[4] == 0.0 || grade[5] == 0.0 || grade[6] == 0.0){
					    	//	listofgen.add(new GenReport(stID,name,studclas,secti,avere,showAver,"Incomplete")); 
					     	}else{
					     for(int j=0; j<grade.length; j++){
					   	//	JOptionPane.showMessageDialog(null, grade[i]);

					   		if(grade[j] >=74.5 && grade[j]<=100){
					   			uzito[j]=1;
					   		}else if(grade[j] >=64.5 && grade[j]<74.5){
					   			uzito[j]=2;
					   		}else if(grade[j] >=44.5 && grade[j]<64.5){
					   			uzito[j]=3;
					   		}else if(grade[j] >=29.5 && grade[j]<44.5){
					   			uzito[j]=4;
					   		}else if(grade[j] >=0 && grade[j]<29.5){
					   			uzito[j]=5;
					   		}else{
					   			//JOptionPane.showMessageDialog(null, "One of the marks in the sheet to be generated \n Exceeds 100%. please correct it during uploads.","ERROR",JOptionPane.WARNING_MESSAGE);
					   		}
					   	   
					     }
					     int	sum = 0;
					    	for(int k=0; k<uzito.length; k++){
					    		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
					    		sum+=uzito[k];
					    	}
					    	 divPoint = Double.toString(sum);
					    
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
							
					    // listofgen.add(new GenReport(stID,name,studclas,secti,avere,showAver, divi+" POINT "+divPoint));
					     	}
					     	}
					
					st.close();
					rs.close();
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
			wd = new WorkIndicatorDialog(null, "Sketching graph ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
						 XYChart.Series<String,Integer> series1 = new XYChart.Series<>();
						graph1.setTitle("SUMMARY DIVISION CHART FOR "+darasa+" "+year+"\n ");
						 series1.setName("DIV I: "+grad_i);
						 series1.setName("DIV II:"+grad_ii);
						 series1.setName("DIV III:"+grad_iii);
						 series1.setName("DIV IV:"+grad_iv);
						 series1.setName("DIV 0:"+grad_0);
						 series1.getData().add(new XYChart.Data<>("I",grad_i));
						 series1.getData().add(new XYChart.Data<>("II",grad_ii));
						 series1.getData().add(new XYChart.Data<>("III",grad_iii));
						 series1.getData().add(new XYChart.Data<>("IV",grad_iv));
						 series1.getData().add(new XYChart.Data<>("0",grad_0));
						 graph1.getData().addAll(series1); 
						 summarylab.setText("DIV I:"+grad_i+"\t"+"DIV II:"+grad_ii+"\t DIV III:"+grad_iii+"\t DIV IV:"+grad_iv+"\t DIV 0:"+grad_0);
						 /*
			 	            * PIE CHART
			 	            */
								 piegraph.setTitle("PIE CHART SHOWING DIVISIONS SUMMARY FOR "+darasa+" "+year+". ");
								 pieChartData = FXCollections.observableArrayList(
								         new PieChart.Data("I", grad_i), 
								         new PieChart.Data("II", grad_ii), 
								         new PieChart.Data("III", grad_iii),
								         new PieChart.Data("IV", grad_iv), 
								         new PieChart.Data("ZERO", grad_0));
								 
								 piegraph.getData().addAll(pieChartData);
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		try {
					
					//listofgen.clear();
					 points = new double[points.length];
					 grad_i = 0;grad_ii=0;grad_iii=0;grad_iv=0;grad_0=0;
					 while(rs.next()){
						String stID;
						String gend;
								stID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("studentClass");
					   	 String sex= rs.getString("Gender");
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					        double civ,hist,geo,kisw,comp,eng,phy,chem,commerc,bkee,bio,math,islam,food,divnty,lite;
					      	 if(rs.getString("sub1")== null){
					      		 geo =00.0;
					      		points[0]=geo;
					      		
					      	 }else{
					      		geo = Double.parseDouble(rs.getString("sub1"));
					      		points[0]=geo;
					      		
					      	 }
					      	 if(rs.getString("sub2")== null){
					      		 civ =00.0;
					      		points[1]=civ;
					      	 }else{
					      		civ = Double.parseDouble(rs.getString("sub2"));
					      		points[1]=civ;
					      		
					      	 }
					      	 if(rs.getString("sub3")== null){
					      		 hist =00.0;
					      		points[2]=hist;
					      	 }else{
					      		hist = Double.parseDouble(rs.getString("sub3"));
					      		points[2]=hist;
					      	 }
					      	 if(rs.getString("sub4")== null){
					      		 kisw =00.0;
					      		points[3]=kisw;
					      	 }else{
					      		kisw = Double.parseDouble(rs.getString("sub4"));
					      		points[3]=kisw;
					      	 }
					      	 if(rs.getString("sub5")== null){
					      		 comp =00.0;
					      		points[4]=comp;
					      	 }else{
					      		comp = Double.parseDouble(rs.getString("sub5"));
					      		points[4]=comp;
					      	 }
					      	 if(rs.getString("sub6")== null){
					      		 eng =00.0;
					      		points[5]=eng;
					      	 }else{
					      		eng = Double.parseDouble(rs.getString("sub6"));
					      		points[5]=eng;
					      	 }
					      	 if(rs.getString("sub7")== null){
					      		 phy =00.0;
					      		points[6]=phy;
					      		
					      	 }else{
					      		phy = Double.parseDouble(rs.getString("sub7"));
					      		points[6]=phy;
					      		
					      	 }
					      	 if(rs.getString("sub8")== null){
					      		 chem =00.0;
					      		points[7]=chem;
					      		
					      	 }else{
					      		chem = Double.parseDouble(rs.getString("sub8"));
					      		points[7]=chem;
					      		
					      	 }
					      	 if(rs.getString("sub9")== null){
					      		 commerc =00.0;
					      		points[8]=commerc;
					      	 }else{
					      		commerc = Double.parseDouble(rs.getString("sub9"));
					      		points[8]=commerc;
					      	 }
					      	 if(rs.getString("sub10")== null){
					      		 bkee =00.0;
					      		points[9]=bkee;
					      	 }else{
					      		bkee = Double.parseDouble(rs.getString("sub10"));
					      		points[9]=bkee;
					      	 }
					      	 if(rs.getString("sub11")== null){
					      		 bio =00.0;
					      		points[10]=bio;
					      	 }else{
					      		bio = Double.parseDouble(rs.getString("sub11"));
					      		points[10]=bio;
					      	 }
					      	 if(rs.getString("sub12")== null){
					      		 math =00.0;
					      		points[11]=math;
					      	 }else{
					      		math = Double.parseDouble(rs.getString("sub12"));
					      		points[11]=math;
					      	 } 	 if(rs.getString("sub16")== null){
					      		 food =00.0;
					      		points[12]=food;
					      	 }else{
					      		food = Double.parseDouble(rs.getString("sub16"));
					      		points[12]=food;
					      	 }
					      	 if(rs.getString("sub14")== null){
					      		 islam =00.0;
					      		points[13]=islam;
					      	 }else{
					      		islam = Double.parseDouble(rs.getString("sub14"));
					      		points[13]=islam;
					      	 }
					      	 if(rs.getString("sub15")== null){
					      		 divnty =00.0;
					      		points[14]=divnty;
					      	 }else{
					      		divnty = Double.parseDouble(rs.getString("sub15"));
					      		points[14]=divnty;
					      	 }	 if(rs.getString("sub13")== null){
					      		 lite =00.0;
					      		points[15]=lite;
					      	 }else{
					      		lite = Double.parseDouble(rs.getString("sub13"));
					      		points[15]=lite;
					      	 }
					      	 scienc=0; art=0;
					      	if(chem != 00.0 && phy != 00.0){
					      		scienc++;
					      	}else{
					      		art++;
					      	}
					      	 /*
					      	*/
					      
					     	sorter(points);
					   double sumAv = 0;
					   int counterAv = 0;
					   for(int k=0; k<points.length; k++){
					   		 if(points[k] != 00.0){
					   			sumAv+=points[k];
					   			 counterAv++;
					   			
					   		 }
					   		
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
					     	grade = Arrays.copyOfRange(points, 0, 7);
					    	if(grade[0] == 0.0 || grade[1] == 0.0 || grade[2] == 0.0 || grade[3] == 0.0
					    			|| grade[4] == 0.0 || grade[5] == 0.0 || grade[6] == 0.0){
					    	//	listofgen.add(new GenReport(stID,name,studclas,secti,avere,showAver,"Incomplete")); 
					     	}else{
					     for(int j=0; j<grade.length; j++){
					   	//	JOptionPane.showMessageDialog(null, grade[i]);

					   		if(grade[j] >=74.5 && grade[j]<=100){
					   			uzito[j]=1;
					   		}else if(grade[j] >=64.5 && grade[j]<74.5){
					   			uzito[j]=2;
					   		}else if(grade[j] >=44.5 && grade[j]<64.5){
					   			uzito[j]=3;
					   		}else if(grade[j] >=29.5 && grade[j]<44.5){
					   			uzito[j]=4;
					   		}else if(grade[j] >=0 && grade[j]<29.5){
					   			uzito[j]=5;
					   		}else{
					   			//JOptionPane.showMessageDialog(null, "One of the marks in the sheet to be generated \n Exceeds 100%. please correct it during uploads.","ERROR",JOptionPane.WARNING_MESSAGE);
					   		}
					    
					     }
						 int	sum = 0;
					    	for(int k=0; k<uzito.length; k++){
					    		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
					    		sum+=uzito[k];
					    	}
					    	 divPoint = Double.toString(sum);
					    	
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
						
						//	listofgen.add(new GenReport(stID,name,studclas,secti,avere,showAver, divi+" POINT "+divPoint));
					     	}
					 }
				
					st.close();
					rs.close();
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
			wd = new WorkIndicatorDialog(null, "Sketching graph ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
						 XYChart.Series<String,Integer> series1 = new XYChart.Series<>();
						graph1.setTitle("SUMMARY DIVISION CHART FOR "+darasa+" "+year+"\n ");
						 series1.setName("DIV I: "+grad_i);
						 series1.setName("DIV II:"+grad_ii);
						 series1.setName("DIV III:"+grad_iii);
						 series1.setName("DIV IV:"+grad_iv);
						 series1.setName("DIV 0:"+grad_0);
						 series1.getData().add(new XYChart.Data<>("I",grad_i));
						 series1.getData().add(new XYChart.Data<>("II",grad_ii));
						 series1.getData().add(new XYChart.Data<>("III",grad_iii));
						 series1.getData().add(new XYChart.Data<>("IV",grad_iv));
						 series1.getData().add(new XYChart.Data<>("0",grad_0));
						 graph1.getData().addAll(series1); 
						 summarylab.setText("DIV I:"+grad_i+"\t"+"DIV II:"+grad_ii+"\t DIV III:"+grad_iii+"\t DIV IV:"+grad_iv+"\t DIV 0:"+grad_0);
						 /*
			 	            * PIE CHART
			 	            */
								 piegraph.setTitle("PIE CHART SHOWING DIVISIONS SUMMARY FOR "+darasa+" "+year+". ");
								 pieChartData = FXCollections.observableArrayList(
								         new PieChart.Data("I", grad_i), 
								         new PieChart.Data("II", grad_ii), 
								         new PieChart.Data("III", grad_iii),
								         new PieChart.Data("IV", grad_iv), 
								         new PieChart.Data("ZERO", grad_0));
								 
								 piegraph.getData().addAll(pieChartData);
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 			try {
				
					//int space=0,grad_i=0,grad_ii=0,grad_iii=0,grad_iv=0,grad_0=0;
					points = new double[points.length];
				//listofgen.clear();
					 grad_i = 0;grad_ii=0;grad_iii=0;grad_iv=0;grad_0=0;
					while(rs.next()){
						
						String stID;
						String gend;
								stID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("studentClass");
					   	 String sex= rs.getString("Gender");
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					        double civ,hist,geo,kisw,comp,eng,phy,chem,commerc,bkee,bio,math,islam,food,divnty,lite;
					      	 if(rs.getString("sub1")== null){
					      		 geo =00.0;
					      		points[0]=geo;
					      		
					      	 }else{
					      		geo = Double.parseDouble(rs.getString("sub1"));
					      		points[0]=geo;
					      		
					      	 }
					      	 if(rs.getString("sub2")== null){
					      		 civ =00.0;
					      		points[1]=civ;
					      	 }else{
					      		civ = Double.parseDouble(rs.getString("sub2"));
					      		points[1]=civ;
					      		
					      	 }
					      	 if(rs.getString("sub3")== null){
					      		 hist =00.0;
					      		points[2]=hist;
					      	 }else{
					      		hist = Double.parseDouble(rs.getString("sub3"));
					      		points[2]=hist;
					      	 }
					      	 if(rs.getString("sub4")== null){
					      		 kisw =00.0;
					      		points[3]=kisw;
					      	 }else{
					      		kisw = Double.parseDouble(rs.getString("sub4"));
					      		points[3]=kisw;
					      	 }
					      	 if(rs.getString("sub5")== null){
					      		 comp =00.0;
					      		points[4]=comp;
					      	 }else{
					      		comp = Double.parseDouble(rs.getString("sub5"));
					      		points[4]=comp;
					      	 }
					      	 if(rs.getString("sub6")== null){
					      		 eng =00.0;
					      		points[5]=eng;
					      	 }else{
					      		eng = Double.parseDouble(rs.getString("sub6"));
					      		points[5]=eng;
					      	 }
					      	 if(rs.getString("sub7")== null){
					      		 phy =00.0;
					      		points[6]=phy;
					      		
					      	 }else{
					      		phy = Double.parseDouble(rs.getString("sub7"));
					      		points[6]=phy;
					      		
					      	 }
					      	 if(rs.getString("sub8")== null){
					      		 chem =00.0;
					      		points[7]=chem;
					      		
					      	 }else{
					      		chem = Double.parseDouble(rs.getString("sub8"));
					      		points[7]=chem;
					      		
					      	 }
					      	 if(rs.getString("sub9")== null){
					      		 commerc =00.0;
					      		points[8]=commerc;
					      	 }else{
					      		commerc = Double.parseDouble(rs.getString("sub9"));
					      		points[8]=commerc;
					      	 }
					      	 if(rs.getString("sub10")== null){
					      		 bkee =00.0;
					      		points[9]=bkee;
					      	 }else{
					      		bkee = Double.parseDouble(rs.getString("sub10"));
					      		points[9]=bkee;
					      	 }
					      	 if(rs.getString("sub11")== null){
					      		 bio =00.0;
					      		points[10]=bio;
					      	 }else{
					      		bio = Double.parseDouble(rs.getString("sub11"));
					      		points[10]=bio;
					      	 }
					      	 if(rs.getString("sub12")== null){
					      		 math =00.0;
					      		points[11]=math;
					      	 }else{
					      		math = Double.parseDouble(rs.getString("sub12"));
					      		points[11]=math;
					      	 } 	 if(rs.getString("sub16")== null){
					      		 food =00.0;
					      		points[12]=food;
					      	 }else{
					      		food = Double.parseDouble(rs.getString("sub16"));
					      		points[12]=food;
					      	 }
					      	 if(rs.getString("sub14")== null){
					      		 islam =00.0;
					      		points[13]=islam;
					      	 }else{
					      		islam = Double.parseDouble(rs.getString("sub14"));
					      		points[13]=islam;
					      	 }
					      	 if(rs.getString("sub15")== null){
					      		 divnty =00.0;
					      		points[14]=divnty;
					      	 }else{
					      		divnty = Double.parseDouble(rs.getString("sub15"));
					      		points[14]=divnty;
					      	 }	 if(rs.getString("sub13")== null){
					      		 lite =00.0;
					      		points[15]=lite;
					      	 }else{
					      		lite = Double.parseDouble(rs.getString("sub13"));
					      		points[15]=lite;
					      	 }
					      	 scienc=0; art=0;
					      	if(chem != 00.0 && phy != 00.0){
					      		scienc++;
					      	}else{
					      		art++;
					      	}
					      	 /*
					      	*/
					      
					     	sorter(points);
					   double sumAv = 0;
					   int counterAv = 0;
					   for(int k=0; k<points.length; k++){
					   		 if(points[k] != 00.0){
					   			sumAv+=points[k];
					   			 counterAv++;
					   			
					   		 }
					   		
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
					     	grade = Arrays.copyOfRange(points, 0, 7);
					    		if(grade[0] == 0.0 || grade[1] == 0.0 || grade[2] == 0.0 || grade[3] == 0.0
				    			|| grade[4] == 0.0 || grade[5] == 0.0 || grade[6] == 0.0){
					     		
					     		//listofgen.add(new GenReport(stID,name,studclas,secti,avere,showAver,"Incomplete")); 
					     	}else{
					     for(int j=0; j<grade.length; j++){
					   	//	JOptionPane.showMessageDialog(null, grade[i]);

					   		if(grade[j] >=74.5 && grade[j]<=100){
					   			uzito[j]=1;
					   		}else if(grade[j] >=64.5 && grade[j]<74.5){
					   			uzito[j]=2;
					   		}else if(grade[j] >=44.5 && grade[j]<64.5){
					   			uzito[j]=3;
					   		}else if(grade[j] >=29.5 && grade[j]<44.5){
					   			uzito[j]=4;
					   		}else if(grade[j] >=0 && grade[j]<29.5){
					   			uzito[j]=5;
					   		}else{
					   			
					   		}
					    
					     }
						 int	sum = 0;
					    	for(int k=0; k<uzito.length; k++){
					    		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
					    		sum+=uzito[k];
					    	}
					    	 divPoint = Double.toString(sum);
					    	
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
						
					    // listofgen.add(new GenReport(stID,name,studclas,secti,avere,showAver, divi+" POINT "+divi));
					     	}
					}
					
					st.close();
					rs.close();
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
			wd = new WorkIndicatorDialog(null, "Sketching Graph ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
						 XYChart.Series<String,Integer> series1 = new XYChart.Series<>();
						graph1.setTitle("SUMMARY DIVISION CHART FOR "+darasa+" "+year+"\n ");
						 series1.setName("DIV I: "+grad_i);
						 series1.setName("DIV II:"+grad_ii);
						 series1.setName("DIV III:"+grad_iii);
						 series1.setName("DIV IV:"+grad_iv);
						 series1.setName("DIV 0:"+grad_0);
						 series1.getData().add(new XYChart.Data<>("I",grad_i));
						 series1.getData().add(new XYChart.Data<>("II",grad_ii));
						 series1.getData().add(new XYChart.Data<>("III",grad_iii));
						 series1.getData().add(new XYChart.Data<>("IV",grad_iv));
						 series1.getData().add(new XYChart.Data<>("0",grad_0));
						 graph1.getData().addAll(series1); 
						 summarylab.setText("DIV I:"+grad_i+"\t"+"DIV II:"+grad_ii+"\t DIV III:"+grad_iii+"\t DIV IV:"+grad_iv+"\t DIV 0:"+grad_0);
						 /*
			 	            * PIE CHART
			 	            */
								 piegraph.setTitle("PIE CHART SHOWING DIVISIONS SUMMARY FOR "+darasa+" "+year+". ");
								 pieChartData = FXCollections.observableArrayList(
								         new PieChart.Data("I", grad_i), 
								         new PieChart.Data("II", grad_ii), 
								         new PieChart.Data("III", grad_iii),
								         new PieChart.Data("IV", grad_iv), 
								         new PieChart.Data("ZERO", grad_0));
								 
								 piegraph.getData().addAll(pieChartData);
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 	 		try {
					
				//	int space=0,grad_i=0,grad_ii=0,grad_iii=0,grad_iv=0,grad_0=0;
					points = new double[points.length];
				//	listofgen.clear();
					 grad_i = 0;grad_ii=0;grad_iii=0;grad_iv=0;grad_0=0;
					while(rs.next()){
                      
						String gend;
					String	stID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("studentClass");
					   	 String sex= rs.getString("Gender");
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					        
					        
					     	double hist,geo,kisw,eng,phy,chem,econo,bio,advmath,fudNut;
					      	
					      	 if(rs.getString("sub2")== null){
					      		hist =00.0;
					      		points[0]=hist;
					      	 }else{
					      		hist = Double.parseDouble(rs.getString("sub2"));
					      		points[0]=hist;
					      		
					      	 }
					      	 if(rs.getString("sub3")== null){
					      		geo =00.0;
					      		points[1]=geo;
					      	 }else{
					      		geo = Double.parseDouble(rs.getString("sub3"));
					      		points[1]=geo;
					      	 }
					      	 if(rs.getString("sub4")== null){
					      		 eng =00.0;
					      		points[2]=eng;
					      	 }else{
					      		eng = Double.parseDouble(rs.getString("sub4"));
					      		points[2]=eng;
					      	 }
					      	 if(rs.getString("sub5")== null){
					      		 phy =00.0;
					      		points[3]=phy;
					      	 }else{
					      		phy = Double.parseDouble(rs.getString("sub5"));
					      		points[3]=phy;
					      	 }
					      	 if(rs.getString("sub6")== null){
					      		 chem =00.0;
					      		points[4]=chem;
					      		
					      	 }else{
					      		chem = Double.parseDouble(rs.getString("sub6"));
					      		points[4]=chem;
					      		
					      	 }
					      	 if(rs.getString("sub7")== null){
					      		 bio =00.0;
					      		points[5]=bio;
					      		
					      	 }else{
					      		bio = Double.parseDouble(rs.getString("sub7"));
					      		points[5]=bio;
					      		
					      	 }
					      	 if(rs.getString("sub8")== null){
					      		 econo =00.0;
					      		points[6]=econo;
					      	 }else{
					      		econo = Double.parseDouble(rs.getString("sub8"));
					      		points[6]=econo;
					      	 }
					      	 if(rs.getString("sub11")== null){
					      		 advmath =00.0;
					      		points[7]=advmath;
					      	 }else{
					      		advmath = Double.parseDouble(rs.getString("sub11"));
					      		points[7]=advmath;
					      	 }
					      	 if(rs.getString("sub10")== null){
					      		 kisw =00.0;
					      		points[8]=kisw;
					      	 }else{
					      		kisw = Double.parseDouble(rs.getString("sub10"));
					      		points[8]=kisw;
					      	 }if(rs.getString("sub12")== null){
					      		 fudNut =00.0;
					      		points[9]=fudNut;
					      	 }else{
					      		fudNut = Double.parseDouble(rs.getString("sub12"));
					      		points[9]=fudNut;
					      	 }
					      	 scienc=0; art=0;
					      	if(chem != 00.0 || phy != 00.0 || bio !=00.0 || fudNut !=00.0 || advmath !=00.0 ){
					      		scienc++;
					      	}else{
					      		art++;
					      	}
					      	 /*
					      	*/
					      
					     	sorter(points);
					     	double sumAv = 0;
					     
					     	
					   	int count = 0;
					     	for(int n=0; n<points.length; n++){
					     		
					     		if(points[n] !=00.0){
					     			count++;
					     			sumAv+=points[n];
					     		}
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
					     	grade2 = Arrays.copyOfRange(points, 0, 3);
					     	if(grade2[1] == 0.0 || grade2[2] == 0.0){
					     		//System.out.println("GRADE2:"+Arrays.toString(grade2));
					     		//listofgen.add(new GenReport(stID,name,studclas,secti,avere,showAver,"Incomplete")); 
					     	}else{
					     //	System.out.println("GRADE2:"+Arrays.toString(grade2));
					     	  for(int j=0; j<grade2.length; j++){
					     		   	//	JOptionPane.showMessageDialog(null, "grade2 "+grade2[j]);

					     		   		if(grade2[j] >=79.5 && grade2[j]<=100){
					     		   			uzito2[j]=1;
					     		   		}else if(grade2[j] >=69.5 && grade2[j]<79.5){
					     		   			uzito2[j]=2;
					     		   		}else if(grade2[j] >=59.5 && grade2[j]<69.5){
					     		   			uzito2[j]=3;
					     		   		}else if(grade2[j] >=49.5 && grade2[j]<59.5){
					     		   			uzito2[j]=4;
					     		   		}else if(grade2[j] >=39.5 && grade2[j]<49.5){
					     		   			uzito2[j]=5;
					     		   		}else if(grade2[j] >=34.5 && grade2[j]<39.5){
					     		   			uzito2[j]=6;
					     		   		}
					     		   		else if(grade2[j] >=0 && grade2[j]<34.5){
					     		   			uzito2[j]=7;
					     		   		}else{
					     		   			
					     		   		}
					     		   		
					     		     }
					     //	 System.out.println("UZITO2:"+Arrays.toString(uzito2));
					     	 int	sum = 0;
					     	 String divi = null;
					     	 String divPoint;
					       	for(int k=0; k<uzito2.length; k++){
					       		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
					       		sum+=uzito2[k];
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
					
					  		
					     //   listofgen.add(new GenReport(stID,name,studclas,secti,avere,showAver, divi+" POINT "+divPoint));     
					}
					}
					
					st.close();
					rs.close();
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
			wd = new WorkIndicatorDialog(null, "Sketching Graph ...");
	 	 	   wd.addTaskEndNotification(result -> {
	 	 		  String outres = result.toString();
	 	          // System.out.println("nomaa "+outres);
	 	           if(outres.equals("1")){
						 XYChart.Series<String,Integer> series1 = new XYChart.Series<>();
						graph1.setTitle("SUMMARY DIVISION CHART FOR "+darasa+" "+year+"\n ");
						 series1.setName("DIV I: "+grad_i);
						 series1.setName("DIV II:"+grad_ii);
						 series1.setName("DIV III:"+grad_iii);
						 series1.setName("DIV IV:"+grad_iv);
						 series1.setName("DIV 0:"+grad_0);
						 series1.getData().add(new XYChart.Data<>("I",grad_i));
						 series1.getData().add(new XYChart.Data<>("II",grad_ii));
						 series1.getData().add(new XYChart.Data<>("III",grad_iii));
						 series1.getData().add(new XYChart.Data<>("IV",grad_iv));
						 series1.getData().add(new XYChart.Data<>("0",grad_0));
						 graph1.getData().addAll(series1); 
						 summarylab.setText("DIV I:"+grad_i+"\t"+"DIV II:"+grad_ii+"\t DIV III:"+grad_iii+"\t DIV IV:"+grad_iv+"\t DIV 0:"+grad_0);
						 /*
			 	            * PIE CHART
			 	            */
								 piegraph.setTitle("PIE CHART SHOWING DIVISIONS SUMMARY FOR "+darasa+" "+year+". ");
								 pieChartData = FXCollections.observableArrayList(
								         new PieChart.Data("I", grad_i), 
								         new PieChart.Data("II", grad_ii), 
								         new PieChart.Data("III", grad_iii),
								         new PieChart.Data("IV", grad_iv), 
								         new PieChart.Data("ZERO", grad_0));
								 
								 piegraph.getData().addAll(pieChartData);
	 	           }
	 	           wd=null;
	 	 	   });
	 	 	 wd.exec("fetch", inputParam -> {
	 			try {
				
					
					points = new double[points.length];
				//	listofgen.clear();
					 grad_i = 0;grad_ii=0;grad_iii=0;grad_iv=0;grad_0=0;
					while(rs.next()){
                        
						String gend;
					String	stID = rs.getString("student_id");
						 String name = rs.getString("firstName")+"  "+rs.getString("middleName")+"  "+rs.getString("lastName");
					   	 String studclas = rs.getString("studentClass");
					   	 String sex= rs.getString("Gender");
					        if(sex.equals("FEMALE")){
					        	gend = "F";
					        }else{
					        	gend = "M";
					        }
					        
					        
					     	double hist,geo,kisw,eng,phy,chem,econo,bio,advmath,fudNut;
					      	/* if(rs.getString("sub1")== null){
					      		 gs =00.0;
					      		//points[0]=gs;
					      		
					      	 }else{
					      		gs = Double.parseDouble(rs.getString("sub1"));
					      	//	points[0]=gs;
					      		
					      	 }*/
					      	 if(rs.getString("sub2")== null){
					      		hist =00.0;
					      		points[0]=hist;
					      	 }else{
					      		hist = Double.parseDouble(rs.getString("sub2"));
					      		points[0]=hist;
					      		
					      	 }
					      	 if(rs.getString("sub3")== null){
					      		geo =00.0;
					      		points[1]=geo;
					      	 }else{
					      		geo = Double.parseDouble(rs.getString("sub3"));
					      		points[1]=geo;
					      	 }
					      	 if(rs.getString("sub4")== null){
					      		 eng =00.0;
					      		points[2]=eng;
					      	 }else{
					      		eng = Double.parseDouble(rs.getString("sub4"));
					      		points[2]=eng;
					      	 }
					      	 if(rs.getString("sub5")== null){
					      		 phy =00.0;
					      		points[3]=phy;
					      	 }else{
					      		phy = Double.parseDouble(rs.getString("sub5"));
					      		points[3]=phy;
					      	 }
					      	 if(rs.getString("sub6")== null){
					      		 chem =00.0;
					      		points[4]=chem;
					      		
					      	 }else{
					      		chem = Double.parseDouble(rs.getString("sub6"));
					      		points[4]=chem;
					      		
					      	 }
					      	 if(rs.getString("sub7")== null){
					      		 bio =00.0;
					      		points[5]=bio;
					      		
					      	 }else{
					      		bio = Double.parseDouble(rs.getString("sub7"));
					      		points[5]=bio;
					      		
					      	 }
					      	 if(rs.getString("sub8")== null){
					      		 econo =00.0;
					      		points[6]=econo;
					      	 }else{
					      		econo = Double.parseDouble(rs.getString("sub8"));
					      		points[6]=econo;
					      	 }
					      	 if(rs.getString("sub11")== null){
					      		 advmath =00.0;
					      		points[7]=advmath;
					      	 }else{
					      		advmath = Double.parseDouble(rs.getString("sub11"));
					      		points[7]=advmath;
					      	 }
					      	 if(rs.getString("sub10")== null){
					      		 kisw =00.0;
					      		points[8]=kisw;
					      	 }else{
					      		kisw = Double.parseDouble(rs.getString("sub10"));
					      		points[8]=kisw;
					      	 }if(rs.getString("sub12")== null){
					      		 fudNut =00.0;
					      		points[9]=fudNut;
					      	 }else{
					      		fudNut = Double.parseDouble(rs.getString("sub12"));
					      		points[9]=fudNut;
					      	 }
					      	 scienc=0; art=0;
					      	if(chem != 00.0 || phy != 00.0 || bio !=00.0 || fudNut !=00.0 || advmath !=00.0 ){
					      		scienc++;
					      	}else{
					      		art++;
					      	}
					      	 /*
					      	*/
					      
					     	sorter(points);
					     	double sumAv = 0;
					     
					     	
					   	int count = 0;
					     	for(int n=0; n<points.length; n++){
					     		
					     		if(points[n] !=00.0){
					     			count++;
					     			sumAv+=points[n];
					     		}
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
					     	grade2 = Arrays.copyOfRange(points, 0, 3);
					     	if(grade2[1] == 0.0 || grade2[2] == 0.0){
					     		//System.out.println("GRADE2:"+Arrays.toString(grade2));
					     		//listofgen.add(new GenReport(stID,name,studclas,secti,avere,showAver,"Incomplete")); 
					     	}else{
					     //	System.out.println("GRADE2:"+Arrays.toString(grade2));
					     	  for(int j=0; j<grade2.length; j++){
					     		   	//	JOptionPane.showMessageDialog(null, "grade2 "+grade2[j]);

					     		   		if(grade2[j] >=79.5 && grade2[j]<=100){
					     		   			uzito2[j]=1;
					     		   		}else if(grade2[j] >=69.5 && grade2[j]<79.5){
					     		   			uzito2[j]=2;
					     		   		}else if(grade2[j] >=59.5 && grade2[j]<69.5){
					     		   			uzito2[j]=3;
					     		   		}else if(grade2[j] >=49.5 && grade2[j]<59.5){
					     		   			uzito2[j]=4;
					     		   		}else if(grade2[j] >=39.5 && grade2[j]<49.5){
					     		   			uzito2[j]=5;
					     		   		}else if(grade2[j] >=34.5 && grade2[j]<39.5){
					     		   			uzito2[j]=6;
					     		   		}
					     		   		else if(grade2[j] >=0 && grade2[j]<34.5){
					     		   			uzito2[j]=7;
					     		   		}else{
					     		   			
					     		   		}
					     		   		
					     		     }
					     //	 System.out.println("UZITO2:"+Arrays.toString(uzito2));
					     	 int	sum = 0;
					     	 String divi = null;
					     	 String divPoint;
					       	for(int k=0; k<uzito2.length; k++){
					       		//JOptionPane.showMessageDialog(null, "GRADE "+grade[k]);
					       		sum+=uzito2[k];
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
					
					  		
					     //   listofgen.add(new GenReport(stID,name,studclas,secti,avere,showAver, divi+" POINT "+divPoint));     
					}
					}
					
					st.close();
					rs.close();
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
	
		}else{
			
		}
		
	
	}
    
	@FXML
    void showHint(ActionEvent event) {

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
