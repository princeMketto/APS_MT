package aps.view.innerview;

import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.util.Callback;  


import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import aps.Main;
import aps.view.ConnectDB;
import aps.view.LoggerM;
import aps.view.WorkIndicatorDialog;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ParentRepController implements Initializable {
	LoggerM log=new LoggerM();
	private WorkIndicatorDialog wd=null;
	int s = 0;
	//tabia
	String bdiii,ubraaa,arriii,ushirikiii,heshmaaa,uongziii,utiiii,usfiii,uaminiii,utamaduniii,kjiaminii,utunzuuu;
	
	String examinationtype,mikondo,divsion,Apositionfood,Adivsion,Aaveragejumla,Aposition,Adarajawastani,Ajumlastudent,madarasa,academicy,mihula;
	String mwaka,darasa,coz,maoezi,exam,wastan,midterm,subjectjumla,alamacoz,unit,position,averagejumla,darajawastani,jumlastudent;
	String civicsmazoezi="-",civicsmidterm="-",civicsexam="-",civicswastan="-",positioncivis="-",katiyacivics="-",gradecivics="-",uzitocivics="-";
	String geogmazoezi="-",geogmidterm="-",geogexam="-",geogwastan="-",positiongeog="-",katiyageog="-",gradegeog="-",uzitogeog="-";
	String histmazoezi="-",histmidterm="-", histexam="-",histwastan="-",positionhist="-",katiyahist="-",gradehist="-",uzitohist="-";
    String phymazoezi="-",phymidterm="-",phyexam="-", phytwastan="-",positionphy="-",katiyaphy="-",gradephy="-",uzitophy="-";
    String kiswmazoezi="-",kiswmidterm="-",kiswexam="-",kiswwastan="-",positionkisw="-",katiyakisw="-",gradekisw="-",uzitokiswa="-";
    String engmazoezi="-",engmidterm="-",engexam="-",engwastan="-",positioneng="-",katiyaeng="-",gradeeng="-",uzitoeng="-";
    String compmazoezi="-",compmidterm="-",compexam="-",compwastan="-",positioncomp="-",katiyacomp="-",gradecomp="-",uzitocomp="-";
    String chemmazoezi="-",chemmidterm="-",chemexam="-",chemwastan="-",positionchem="-",katiyachem="-",gradechem="-",uzitochem="-";
    String mathmazoezi="-",mathmidterm="-",mathexam="-",mathwastan="-",positionmath="-",katiyamath="-",grademath="-",uzitomath="-";
    String biosmazoezi="-",biosmidterm="-",biosexam="-",bioswastan="-",positionbios="-",katiyabios="-",gradebios="-",uzitobiosi="-";
    String uhasibumazoezi="-",uhasibumidterm="-",uhasibuexam="-",uhasibuwastan="-",positionuhasibu="-",katiyauhasibu="-",gradeuhasibu="-",uzitouhasibuu="-";
    String biasharamazoezi="-",biasharamidterm="-",biasharaexam="-",biasharawastan="-",positionbiashara="-",katiyabiashara="-",gradebiashara="-",uzitobiasharaa="-";
    String lengmazoezi="-",lengmidterm="-",lengexam="-",lengwastan="-",positionleng="-",katiyaleng="-",gradeleng="-",uzitoleng="-";
    String biblemazoezi="-",biblemidterm="-",bibleexam="-",biblewastan="-",positionbible="-",katiyabible="-",gradebible="-",uzitobiblee="-";
    String islamicmazoezi="-",islamicmidterm="-",islamicexam="-",islamicwastan="-",positionislamic="-",katiyaislamic="-",gradeislamic="-",uzitoislamicc="-";
    String foodmazoezi="-",foodmidterm="-",foodexam="-",foodwastan="-",positionfood="-",katiyafood="-",gradefood="-",uzitofood;
    
    //ADVANCE
    
    String uraiamazoezi="-",uraiamidterm="-",uraiaexam="-",uraiawastan="-",positionuraia="-",katiyauraia="-",gradeuraia="-",uzitouraia="-";
	String Ageogmazoezi="-",Ageogmidterm="-",Ageogexam="-",Ageogwastan="-",positionAgeog="-",katiyaAgeog="-",gradeAgeog="-",uzitoAgeog="-";
	String Ahistmazoezi="-",Ahistmidterm="-", Ahistexam="-",Ahistwastan="-",positionAhist="-",katiyaAhist="-",gradeAhist="-",uzitoAhist="-";
    String Aphymazoezi="-",Aphymidterm="-",Aphyexam="-", Aphytwastan="-",positionAphy="-",katiyaAphy="-",gradeAphy="-",uzitoAphy="-";
    String Akiswmazoezi="-",Akiswmidterm="-",Akiswexam="-",Akiswwastan="-",positionAkisw="-",katiyaAkisw="-",gradeAkisw="-",uzitoAkiswa="-";
    String Aengmazoezi="-",Aengmidterm="-",Aengexam="-",Aengwastan="-",positionAeng="-",katiyaAeng="-",gradeAeng="-",uzitoAeng="-";
    String Achemmazoezi="-",Achemmidterm="-",Achemexam="-",Achemwastan="-",positionAchem="-",katiyaAchem="-",gradeAchem="-",uzitoAchem="-";
    String Amathmazoezi="-",Amathmidterm="-",Amathexam="-",Amathwastan="-",positionAmath="-",katiyaAmath="-",gradeAmath="-",uzitoAmath="-";
    String Abiosmazoezi="-",Abiosmidterm="-",Abiosexam="-",Abioswastan="-",positionAbios="-",katiyaAbios="-",gradeAbios="-",uzitoAbiosi="-";
    String Auhasibumazoezi="-",Auhasibumidterm="-",Auhasibuexam="-",Auhasibuwastan="-",positionAuhasibu="-",katiyaAuhasibu="-",gradeAuhasibu="-",uzitoAuhasibuu="-";
    String Abiasharamazoezi="-",Abiasharamidterm="-",Abiasharaexam="-",Abiasharawastan="-",positionAbiashara="-",katiyaAbiashara="-",gradeAbiashara="-",uzitoAbiasharaa="-";
    String UCHUMImazoezi="-",UCHUMImidterm="-",UCHUMIexam="-",UCHUMIwastan="-",positionUCHUMI="-",katiyaUCHUMI="-",gradeUCHUMI="-",uzitoUCHUMI="-";
    String Afoodmazoezi="-",Afoodmidterm="-",Afoodexam="-",Afoodwastan="-",positionAfood="-",katiyaAfood="-",gradeAfood="-",uzitoAfood;
    String bammazoezi="-",bammidterm="-",bamexam="-",bamwastan="-",positionbam="-",katiyabam="-",gradebam="-",uzitobamu="-";
    
    
    
	Document document=null;
    OutputStream file=null;
   PdfWriter writer;
   Font boldTitle = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
   Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
   Font boldFontle = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
   Font boldFontmkuu = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
   Font phonenumba = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
   Font maagizo = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
   Font boldFonti = new Font(Font.FontFamily.TIMES_ROMAN,(float) 5.8, Font.BOLD);
   Font boldMzazi = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
   Font SCNAME = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
   
   String examtype,fullname,muhula,studentclass,classteacher,photoo,mkuuwashule,ID,clssname,mkuu,mwalimu,subjectposition;
   Image imagephoto;
   PdfPCell bidiinamaarifa = null,ariyakazi=null,uborawakazi=null,utanzajiwavifaa=null,ushirikiano=null,heshima=null,uongozi=null,utiinakujituma=null,usafi=null,uaminifu=null,utamaduni=null,utamaduniCD=null;
   PdfPTable tablepdf,fmuDarajanii,fmuUjumlanii,fmuWanafunzinii,masomo,fmuWanafunzii,fmu1,fmuChem,fmuMath,fmuHis,fmuJiog,fmuKisw,fmuEng,fmuPhy,fmuBios,fmuComp,Vielelezo,fmuUjumla,Elezo,Maelezo,fmuCom,fmuComW,fmuDaraja,fmuWanafunzi,fmuuhasibu,fmuCommerce,literatureinenglish,fmuDarajaIIIANDIV,divisheni,Point, islamicknow,bibleknow,food,UCHUMI,BAM,tabia,tabiaalama,fmuComuaminifu;
	

	ConnectDB database = new ConnectDB();
	    private Connection con;
	    private ResultSet rs,rs4,rs2,rs3;
	    private Statement st,st4,st2,st3;
	
	    String year;
	    ObservableList<String> year1 = FXCollections.observableArrayList();
	    ObservableList<String> classes = FXCollections.observableArrayList();
	    ObservableList<Report> searchdata;


	   
    @FXML
    private JFXTextField search;

    @FXML
    private RadioButton terminal;

    @FXML
    private ToggleGroup examtype2;

    @FXML
    private RadioButton annual;

    @FXML
    private ChoiceBox yearchoice;

    @FXML
    private ChoiceBox classchoice;

    @FXML
    private JFXButton btnprint,btnsubmit;

    @FXML
    private StackPane stackolevel;

    @FXML
    private StackPane stackalevel;

    @FXML
    private JFXButton btnview;

    @FXML
    private TableView<Report> tableview;

    @FXML
    private TableColumn<Report, String> idcol;

    @FXML
    private TableColumn<Report, String> fnamecol;

    @FXML
    private TableColumn<Report, String> mnamecol;

    @FXML
    private TableColumn<Report, String> lnamecol;

    @FXML
    private TableColumn<Report, String> classcol;

    @FXML
    void reportbutton(ActionEvent event) throws SQLException {
    	
    	selectma();
    	searchdata =  tableview.getItems();
    	 
    }
    //print reports
    @FXML
    void onprintbtn(ActionEvent event) {
    	
				closePDF();
				reports();
			
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

		idcol.setCellValueFactory(new PropertyValueFactory<Report, String>("id"));
		fnamecol.setCellValueFactory(new PropertyValueFactory<Report, String>("fname"));
		mnamecol.setCellValueFactory(new PropertyValueFactory<Report, String>("mname"));
		lnamecol.setCellValueFactory(new PropertyValueFactory<Report, String>("lname"));
		classcol.setCellValueFactory(new PropertyValueFactory<Report, String>("clas"));
		 year();
		 // seach starting
	search.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
		
		if (oldValue != null && (newValue.length() < oldValue.length())) {
	            	tableview.setItems(searchdata);
	            }
	            String value = newValue.toLowerCase();
	            ObservableList<Report> subentries = FXCollections.observableArrayList();

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
	

	
	public void selectma(){
		tableview.getItems().clear();
		academicy=(String) yearchoice.getSelectionModel().getSelectedItem();
		String studentclass=(String) classchoice.getSelectionModel().getSelectedItem();
		
	
		if(academicy.equals("Choose year")==false&&annual.isSelected() &&studentclass.equals("FORM I")){
		    mihula="Annual";
		    madarasa="(result_from_teacher.class='FORM IA' OR result_from_teacher.class='FORM IB' OR result_from_teacher.class='FORM IC' OR result_from_teacher.class='FORM ID' OR result_from_teacher.class='FORM IE' OR result_from_teacher.class='FORM IF' OR result_from_teacher.class='FORM IG' OR result_from_teacher.class='FORM IH')";
		 querystudent();
		
		}else if(academicy.equals("Choose year")==false&&terminal.isSelected() &&studentclass.equals("FORM I")){
			 mihula="Terminal";
			 madarasa="(result_from_teacher.class='FORM IA' OR result_from_teacher.class='FORM IB' OR result_from_teacher.class='FORM IC' OR result_from_teacher.class='FORM ID' OR result_from_teacher.class='FORM IE' OR result_from_teacher.class='FORM IF' OR result_from_teacher.class='FORM IG' OR result_from_teacher.class='FORM IH')";
			 querystudent();
			
		}else if(academicy.equals("Choose year")==false&&annual.isSelected() &&studentclass.equals("FORM II")){
			mihula="Annual";
			madarasa="(result_from_teacher.class='FORM IIA' OR result_from_teacher.class='FORM IIB' OR result_from_teacher.class='FORM IIC' OR result_from_teacher.class='FORM IID' OR result_from_teacher.class='FORM IIE' OR result_from_teacher.class='FORM IIF' OR result_from_teacher.class='FORM IIG' OR result_from_teacher.class='FORM IIH')";
			querystudent();
		}else if(academicy.equals("Choose year")==false&&terminal.isSelected() &&studentclass.equals("FORM II")){
			mihula="Terminal";
			madarasa="(result_from_teacher.class='FORM IIA' OR result_from_teacher.class='FORM IIB' OR result_from_teacher.class='FORM IIC' OR result_from_teacher.class='FORM IID' OR result_from_teacher.class='FORM IIE' OR result_from_teacher.class='FORM IIF' OR result_from_teacher.class='FORM IIG' OR result_from_teacher.class='FORM IIH')";
			querystudent();
		}else if(academicy.equals("Choose year")==false&&terminal.isSelected() &&studentclass.equals("FORM III")){
			mihula="Terminal";
			madarasa="(result_from_teacher.class LIKE 'FORM III%')";
			querystudent();
		}else if(academicy.equals("Choose year")==false&&annual.isSelected() &&studentclass.equals("FORM III")){
			mihula="Annual";
			madarasa="(result_from_teacher.class LIKE 'FORM III%')";
			querystudent();
		}else if(academicy.equals("Choose year")==false&&terminal.isSelected() &&studentclass.equals("FORM IV")){
			mihula="Terminal";
			madarasa="(result_from_teacher.class LIKE 'FORM IV%')";
			querystudent();
		}else if(academicy.equals("Choose year")==false&&terminal.isSelected() &&studentclass.equals("FORM V")){
			mihula="Terminal";
			madarasa="(result_from_teacher.class LIKE 'FORM V-%')";
			querystudent();						
		}else if(academicy.equals("Choose year")==false&&annual.isSelected() &&studentclass.equals("FORM V")){
			mihula="Annual";
			madarasa="(result_from_teacher.class LIKE 'FORM V-%')";
			querystudent();							
		}else if(academicy.equals("Choose year")==false&&annual.isSelected() &&studentclass.equals("FORM VI")){
			mihula="Annual";
			madarasa="(result_from_teacher.class LIKE 'FORM VI-%')";
			querystudent();							
		}else if(academicy.equals("Choose year")==false&&terminal.isSelected() &&studentclass.equals("FORM VI")){
			mihula="Annual";
			madarasa="(result_from_teacher.class LIKE 'FORM VI-%')";
			querystudent();							
		}else if(annual.isSelected()==false&&terminal.isSelected()==false){
			TrayNotification tray = new TrayNotification();
		     tray.setNotificationType(NotificationType.ERROR);
		     tray.setTitle("Report");
		     tray.setMessage("Select Annual or Terminal please!");
		     tray.setAnimationType(AnimationType.SLIDE);
		     tray.showAndDismiss(Duration.millis(4000));                                       
		}else if((annual.isSelected()==true||terminal.isSelected()==true)&&(academicy.equals("Choose year")||studentclass.equals("Choose class"))){
			TrayNotification tray = new TrayNotification();
		     tray.setNotificationType(NotificationType.ERROR);
		     tray.setTitle("Report");
		     tray.setMessage("Choose year and class please!");
		     tray.setAnimationType(AnimationType.SLIDE);
		     tray.showAndDismiss(Duration.millis(4000));                                       
		}
		
			
		
		
				}
	
	@SuppressWarnings("unchecked")
	public void year(){
		 year1.clear();
			wd = new WorkIndicatorDialog(null, "Loading...");
		 	   wd.addTaskEndNotification(result -> {
		 		  String outres = result.toString();
		          // System.out.println("nomaa "+outres);
		           if(outres.equals("1")){
	 				    year1.add("Choose year");
	 				    classes.add("FORM I");
	 				    classes.add("FORM II");
	 				    classes.add("FORM III");
	 				    classes.add("FORM IV");
	 				    classes.add("FORM V");
	 				    classes.add("FORM VI");
	 				    classes.add("Choose class");
	 			       	yearchoice.setValue("Choose year");
	 			       	classchoice.setValue("Choose class");
	 			       	classchoice.setItems(classes);
	 			    	yearchoice.setItems(year1);
		           }
		           wd=null;
		 	   });
		 		 wd.exec("fetch", inputParam -> {
			           // Thinks to do...
			           // NO ACCESS TO UI ELEMENTS!
		 			 con= database.dbconnect();
		 		   boolean done = false;
		 			 try {
		 				st= con.createStatement();
		 				 String yrs="SELECT distinct year FROM  result_from_teacher";
		 				    
		 				    rs=st.executeQuery(yrs);
		 				    while(rs.next()){
		 				    	year=rs.getString("year");
		 				         year1.add(year);
		 				         done = true;
		 				       }
		 				    if(done){
		 				    	s =1;
		 				    }
		 				    
		 				
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
			             
			           return new Integer(s);
			           
			           
			        });
	  		
		    }
	 public void noresultmassage(){
		TrayNotification tray = new TrayNotification();
	       tray.setNotificationType(NotificationType.ERROR);
	       tray.setTitle("Results");
	       tray.setMessage("NO results for selected class at that academic year");
	       tray.setAnimationType(AnimationType.SLIDE);
	       tray.showAndDismiss(Duration.millis(4000));
	  }
	 
	 @SuppressWarnings("unchecked")
	public void reports() {
		         //PDF
		
			wd = new WorkIndicatorDialog(null, " Generating report(s)...");
		 	   wd.addTaskEndNotification(result -> {
		 		  String outres = result.toString();
		          // System.out.println("nomaa "+outres);
		           if(outres.equals("1")){
		        	   document.close();
		 				try {
							file.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		 				openPDF();
		 				TrayNotification tray = new TrayNotification();
					       tray.setNotificationType(NotificationType.INFORMATION);
					       tray.setTitle("Student report generated");
					       tray.setMessage("you can move,print or share the file as needed.");
					       tray.setAnimationType(AnimationType.SLIDE);
					       tray.showAndDismiss(Duration.millis(4000));
					       
					       log.writter("Generate parent report(s)");
		           }
		           wd=null;
		 	   });
		 		 wd.exec("fetch", inputParam -> {
			           // Thinks to do...
			           // NO ACCESS TO UI ELEMENTS!
		 			 boolean done = false;
		 			 try{
		 		     file = new FileOutputStream(new File("result.pdf"));
		 			 document = new Document(PageSize.A4, 50, 50, 50, 50);
		 			 writer= PdfWriter.getInstance(document, file);
		 			 document.open();
		 			
		 			 for(int i=0; i<tableview.getItems().size(); i++){
		 				     
		 				     mkuu=" "; mwalimu=" ";
		 				     bdiii="-";ubraaa="-";arriii="-";ushirikiii="-";heshmaaa="-";uongziii="-";utiiii="-";usfiii="-";uaminiii="-";utamaduniii="-";kjiaminii="-"; utunzuuu="-";
		 					 civicsmazoezi="-"; civicsmidterm="-"; civicsexam="-";civicswastan="-";positioncivis="-";katiyacivics="-";gradecivics="-";uzitocivics="-";
		 					 geogmazoezi="-";geogmidterm="-";geogexam="-";geogwastan="-";positiongeog="-";katiyageog="-";gradegeog="-";uzitogeog="-";
		 					 histmazoezi="-";histmidterm="-"; histexam="-";histwastan="-";positionhist="-";katiyahist="-";gradehist="-";uzitohist="-";
		 				     phymazoezi="-";phymidterm="-";phyexam="-"; phytwastan="-";positionphy="-";katiyaphy="-";gradephy="-";uzitophy="-";
		 				     kiswmazoezi="-";kiswmidterm="-";kiswexam="-";kiswwastan="-";positionkisw="-";katiyakisw="-";gradekisw="-";uzitokiswa="-";
		 				     engmazoezi="-";engmidterm="-";engexam="-";engwastan="-";positioneng="-";katiyaeng="-";gradeeng="-";uzitoeng="-";
		 				     compmazoezi="-";compmidterm="-";compexam="-";compwastan="-";positioncomp="-";katiyacomp="-";gradecomp="-";uzitocomp="-";
		 				     chemmazoezi="-";chemmidterm="-";chemexam="-";chemwastan="-";positionchem="-";katiyachem="-";gradechem="-";uzitochem="-";
		 				     mathmazoezi="-";mathmidterm="-";mathexam="-";mathwastan="-";positionmath="-";katiyamath="-";grademath="-";uzitomath="-";
		 				     biosmazoezi="-";biosmidterm="-";biosexam="-";bioswastan="-";positionbios="-";katiyabios="-";gradebios="-";uzitobiosi="-";
		 				     uhasibumazoezi="-";uhasibumidterm="-";uhasibuexam="-";uhasibuwastan="-";positionuhasibu="-";katiyauhasibu="-";gradeuhasibu="-";uzitouhasibuu="-";
		 				     biasharamazoezi="-";biasharamidterm="-";biasharaexam="-";biasharawastan="-";positionbiashara="-";katiyabiashara="-";gradebiashara="-";uzitobiasharaa="-";
		 				     lengmazoezi="-";lengmidterm="-";lengexam="-";lengwastan="-";positionleng="-";katiyaleng="-";gradeleng="-";uzitoleng="-";
		 				     biblemazoezi="-";biblemidterm="-";bibleexam="-";biblewastan="-";positionbible="-";katiyabible="-";gradebible="-";uzitobiblee="-";
		 				     islamicmazoezi="-";islamicmidterm="-";islamicexam="-";islamicwastan="-";positionislamic="-";katiyaislamic="-";gradeislamic="-";uzitoislamicc="-";
		 				     foodmazoezi="-";foodmidterm="-";foodexam="-";foodwastan="-";positionfood="-";katiyafood="-";gradefood="-"; uzitofood="-"; 
		 				     
		 				     
		 				     //ADVANCE
		 				      uraiamazoezi="-";uraiamidterm="-";uraiaexam="-";uraiawastan="-";positionuraia="-";katiyauraia="-";gradeuraia="-";uzitouraia="-";
		 				 	  Ageogmazoezi="-";Ageogmidterm="-";Ageogexam="-";Ageogwastan="-";positionAgeog="-";katiyaAgeog="-";gradeAgeog="-";uzitoAgeog="-";
		 				 	  Ahistmazoezi="-";Ahistmidterm="-"; Ahistexam="-";Ahistwastan="-";positionAhist="-";katiyaAhist="-";gradeAhist="-";uzitoAhist="-";
		 				      Aphymazoezi="-";Aphymidterm="-";Aphyexam="-"; Aphytwastan="-";positionAphy="-";katiyaAphy="-";gradeAphy="-";uzitoAphy="-";
		 				      Akiswmazoezi="-";Akiswmidterm="-";Akiswexam="-";Akiswwastan="-";positionAkisw="-";katiyaAkisw="-";gradeAkisw="-";uzitoAkiswa="-";
		 				      Aengmazoezi="-";Aengmidterm="-";Aengexam="-";Aengwastan="-";positionAeng="-";katiyaAeng="-";gradeAeng="-";uzitoAeng="-";
		 				      Achemmazoezi="-";Achemmidterm="-";Achemexam="-";Achemwastan="-";positionAchem="-";katiyaAchem="-";gradeAchem="-";uzitoAchem="-";
		 				      Amathmazoezi="-";Amathmidterm="-";Amathexam="-";Amathwastan="-";positionAmath="-";katiyaAmath="-";gradeAmath="-";uzitoAmath="-";
		 				      Abiosmazoezi="-";Abiosmidterm="-";Abiosexam="-";Abioswastan="-";positionAbios="-";katiyaAbios="-";gradeAbios="-";uzitoAbiosi="-";
		 				      Auhasibumazoezi="-";Auhasibumidterm="-";Auhasibuexam="-";Auhasibuwastan="-";positionAuhasibu="-";katiyaAuhasibu="-";gradeAuhasibu="-";uzitoAuhasibuu="-";
		 				      Abiasharamazoezi="-";Abiasharamidterm="-";Abiasharaexam="-";Abiasharawastan="-";positionAbiashara="-";katiyaAbiashara="-";gradeAbiashara="-";uzitoAbiasharaa="-";
		 				      UCHUMImazoezi="-";UCHUMImidterm="-";UCHUMIexam="-";UCHUMIwastan="-";positionUCHUMI="-";katiyaUCHUMI="-";gradeUCHUMI="-";uzitoUCHUMI="-";
		 				      Afoodmazoezi="-";Afoodmidterm="-";Afoodexam="-";Afoodwastan="-";positionAfood="-";katiyaAfood="-";gradeAfood="-";uzitoAfood="-";
		 				      bammazoezi="-";bammidterm="-";bamexam="-";bamwastan="-";positionbam="-";katiyabam="-";gradebam="-";uzitobamu="-";
		 				 
		 	   		  String fname  = tableview.getItems().get(i).getFname();
		 	   		  String mname  = tableview.getItems().get(i).getMname();
		 	   		  String lname  = tableview.getItems().get(i).getLname();
		 	   		  String clas  = tableview.getItems().get(i).getClas();
		 	   		  String clasname=tableview.getItems().get(i).getClas().toString()+".";
		 	   		
		 	   		  studentclass= clasname.substring(clasname.indexOf(" ")+1,clasname.indexOf("."));
		 	   		  ID=tableview.getItems().get(i).getId();
		 	   		  clssname=clas;
		 	   		  fullname=fname+"  "+mname.charAt(0)+".  "+lname;
		 	   		  mwaka=(String) yearchoice.getSelectionModel().getSelectedItem();
		 	   		  darasa=(String) classchoice.getSelectionModel().getSelectedItem();
		 	   		  if(annual.isSelected()&&darasa.equals("FORM I")){
		 	   			 examinationtype="Annual";
		 	   			 examtype="ANNUAL";
		 				 muhula="II";
		 				 mikondo="(class='FORM IA' OR class='FORM IB' OR class='FORM IC' OR class='FORM ID' OR class='FORM IE' OR class='FORM IF' OR class='FORM IG' OR class='FORM IH')";
		 				 Olevalquery(); 
		 				 pdfreportOlevo();
		 				 
		 	   		  }else if(terminal.isSelected()&&darasa.equals("FORM I")){
		 	   			examinationtype="Terminal";
		 	  			 examtype="TERMINAL";
		 				 muhula="I";
		 				 mikondo="(class='FORM IA' OR class='FORM IB' OR class='FORM IC' OR class='FORM ID' OR class='FORM IE' OR class='FORM IF' OR class='FORM IG' OR class='FORM IH')";
		 				 Olevalquery(); 
		 				 pdfreportOlevo();
		 	   		  }else if(terminal.isSelected()&&darasa.equals("FORM II")){
		 	     			examinationtype="Terminal";
		 	     			 examtype="TERMINAL";
		 	   			 muhula="I";
		 	   			 mikondo="(class='FORM IIA' OR class='FORM IIB' OR class='FORM IIC' OR class='FORM IID' OR class='FORM IIE' OR class='FORM IIF' OR class='FORM IIG' OR class='FORM IIH')";
		 	   			 Olevalquery(); 
		 	   			 pdfreportOlevo();
		 	      		  }else if(annual.isSelected()&&darasa.equals("FORM II")){
		 	       			examinationtype="Annual";
		 	    			 examtype="ANNUAL";
		 	  			     muhula="II";
		 	  			 mikondo="(class='FORM IIA' OR class='FORM IIB' OR class='FORM IIC' OR class='FORM IID' OR class='FORM IIE' OR class='FORM IIF' OR class='FORM IIG' OR class='FORM IIH')";
		 	  			 Olevalquery(); 
		 	  			 pdfreportOlevo();
		 	     		  }else if(terminal.isSelected()&&darasa.equals("FORM III")){
		 	       			examinationtype="Terminal";
		 	    			 examtype="TERMINAL";
		 	  			 muhula="I";
		 	  			 mikondo="class LIKE 'FORM III%'";
		 	  			 Olevalquery(); 
		 	  			 pdfreportOlevo();
		 	     		  }else if(annual.isSelected()&&darasa.equals("FORM III")){
		 	         		 examinationtype="Annual";
		 	       			 examtype="ANNUAL";
		 	     			 muhula="II";
		 	     			 mikondo="class LIKE 'FORM III%'";
		 	     			 Olevalquery(); 
		 	     			 pdfreportOlevo();
		 	        		  }
		 	     		 else if(terminal.isSelected()&&darasa.equals("FORM IV")){
		 	         		 examinationtype="Terminal";
		 	       			 examtype="TERMINAL";
		 	     			 muhula="I";
		 	     			 mikondo="class LIKE 'FORM IV%'";
		 	     			 Olevalquery(); 
		 	     			 pdfreportOlevo();
		 	        		  }
		 	     		 else if(terminal.isSelected()&&darasa.equals("FORM V")){
		 	         		  examinationtype="Terminal";
		 	       			  examtype="TERMINAL";
		 	     			  muhula="I";
		 	     			  mikondo="class LIKE 'FORM V-%'";
		 	     			 advancequery();
		 	     			 PDFreportadvance();
		 	        		  }else if(annual.isSelected()&&darasa.equals("FORM V")){
		 	        			   examinationtype="Annual";
			 	       			   examtype="ANNUAL";
			 	     			   muhula="II";
			 	     			   mikondo="class LIKE 'FORM V-%'";
			 	     			   advancequery();
			 	     			   PDFreportadvance();
		 	        		  }
		 	   		
		 	   		document.newPage();
		 	     	done = true;
		 			 }
		 			 if(done){
		 				 s = 1;
		 			 }
		 			
		 			 }catch(Exception e){
		 				 s = 0;
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
		    
		public void pdfreportOlevo() throws SQLException, DocumentException, MalformedURLException, IOException{
			GregorianCalendar date = new GregorianCalendar();
			PdfPCell division;
			 
		    int     day = date.get(Calendar.DAY_OF_MONTH);
		    int  month = date.get(Calendar.MONTH);
		    int  year = date.get(Calendar.YEAR);
			
		    String dte=""+day+"/"+(month+1)+"/"+year;
		    String sculname = null,adress = null,location = null,contact = null,contact2=null;
		    
		   String schol="SELECT*FROM school_details";
		   rs=st.executeQuery(schol);
		    while(rs.next()){
			   sculname=rs.getString("title");
			    adress=rs.getString("address");
			   location=rs.getString("location");
			   contact=rs.getString("contact");
			   contact2=rs.getString("contact2");
			  
			   
		    }
		    String staff="SELECT  firstName,lastName FROM staffs where status LIKE '%"+clssname+"%' ";
 			 rs=st.executeQuery(staff);
 			if(rs.next()){
 				 String finame=rs.getString("firstName");
 				String laname=rs.getString("lastName");
 				classteacher=finame+ " "+ laname;
 			}else{
 				classteacher="Teacher";
 			}

 			 String mkuusign="SELECT sign from head_sign";
             
             
		       rs=st.executeQuery(mkuusign);
		       while(rs.next()){
		    	   java.sql.Blob imageBlob = rs.getBlob("sign");
		    	   byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
		    	  Image image = Image.getInstance(imageBytes);
		    	   image.scaleAbsolute(50f, 15f);
		           image.setAbsolutePosition(272, 203);
		           
		           document.add(image);
		       }
		       
		     
			
		    Phrase janalashule = new Phrase(sculname, SCNAME);
		    PdfContentByte scullname = writer.getDirectContent();
			ColumnText.showTextAligned(scullname, Element.ALIGN_CENTER, janalashule, 300, 810, 0);
			
			 Phrase pobox = new Phrase(adress+"-"+location+", TANZANIA", boldFontmkuu);
			 PdfContentByte box = writer.getDirectContent();
			 ColumnText.showTextAligned(box, Element.ALIGN_CENTER, pobox, 300, 800, 0);
			 
			 Phrase simu = new Phrase("Mobile: " +contact+" / "+contact2, phonenumba);
			 PdfContentByte mobile = writer.getDirectContent();
			 ColumnText.showTextAligned(mobile, Element.ALIGN_CENTER, simu, 300, 790, 0);
		    
			 Phrase tarrifa = new Phrase("TAARIFA YA MAENDELEO YA MWANAFUNZI KWA MZAZI", boldMzazi);
			 PdfContentByte taarifa = writer.getDirectContent();
			 ColumnText.showTextAligned(taarifa, Element.ALIGN_CENTER, tarrifa, 300, 775, 0);
			 document.add(new Paragraph(" "+"\n"));
			 
		     document.add(new Paragraph("Jina la Mwanafunzi "+fullname+ " Kidato "+studentclass+           " Muhula  "+muhula+"   "));
		     document.add(new Paragraph("Jina la Mwalimu wa Darasa "+classteacher+" Tarehe "+dte+"           "));
		     document.add(new Paragraph("                                                                             MASOMO NA TABIA", boldMzazi));
		     
		     try{
		    	st4=con.createStatement();
		    	String mkuuu="Select *FROM staffs WHERE status='HEAD OF SCHOOL'" ;
		    	rs4=st4.executeQuery(mkuuu);
		    	while(rs4.next()){
		    		String fname=rs4.getString("firstName");
		    		String mname=rs4.getString("middleName");
		    		String lname=rs4.getString("lastName");
		    		mkuuwashule=lname+", "+fname.charAt(0)+"."+mname.charAt(0);
		    		
		    	}
		    	
		    	  photoo="SELECT photo from students where student_id='"+ID+"' ";
		          
		          
			       rs=st.executeQuery(photoo);
			       while(rs.next()){
			    	   java.sql.Blob imageBlob = rs.getBlob("photo");
			    	   byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
			    	   imagephoto = Image.getInstance(imageBytes);
			    	   imagephoto.scaleAbsolute(75f, 75f);
			    	   imagephoto.setAbsolutePosition(470, 765);
			           document.add(imagephoto);
			           
			       }
			       
		    	
		     }catch(Exception a){
		    	 
		     }
		     String log="SELECT logo from school_details ";
             
             
		       rs=st.executeQuery(log);
		        while(rs.next()){
		    	   java.sql.Blob imageBlob = rs.getBlob("logo");
		    	   byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
		    	    Image imagel = Image.getInstance(imageBytes);
		    	   imagel.scaleAbsolute(75f, 75f);
		           imagel.setAbsolutePosition(45, 765);
		           
		           document.add(imagel);
		       }
		     
		     Phrase mkuuline = new Phrase("________________", boldFontmkuu);
		     PdfContentByte canvas76 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas76, Element.ALIGN_CENTER, mkuuline, 300, 205, 0);
		     
		     Phrase mkuushule = new Phrase(mkuuwashule, boldFontmkuu);
		     PdfContentByte canvas4 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas4, Element.ALIGN_CENTER, mkuushule, 300, 190, 0);
		     
			 Phrase mkuushule6 = new Phrase("MKUU WA SHULE", boldMzazi);
		     PdfContentByte canvas5 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas5, Element.ALIGN_CENTER, mkuushule6, 300, 175, 0);
		     
		     Phrase phrase = new Phrase(dte, boldMzazi);
		     PdfContentByte canvas = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase, 300, 160, 0);
			 
			 Phrase mzazimlezi = new Phrase("MAONI YA MZAZI/MLEZI KUHUSU TAARIFA YA MAENDELEO YA MWANAFUNZI", boldFontmkuu);
		     PdfContentByte canvas6 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas6, Element.ALIGN_CENTER, mzazimlezi, 200, 120, 0);
			 
			 Phrase line8 = new Phrase("-----------------------------------------------------------------------------------------kata hapa-------------------------------------------------------------------------------", boldFontmkuu);
		     PdfContentByte canvas13 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas13, Element.ALIGN_CENTER, line8, 285, 140, 0);
			 
			 Phrase line1 = new Phrase("..............................................................................................................................................................................................................................................", boldFontmkuu);
		     PdfContentByte canvas7 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas7, Element.ALIGN_CENTER, line1, 285, 100, 0);
			 Phrase line2 = new Phrase("..............................................................................................................................................................................................................................................", boldFontmkuu);
		     PdfContentByte canvas8 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas8, Element.ALIGN_CENTER, line2, 285, 90, 0);
			 
			 Phrase line3 = new Phrase("Sahihi ya Mzazi/mlezi:  .................................................", boldFontmkuu);
		     PdfContentByte canvas9 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas9, Element.ALIGN_CENTER, line3, 135, 65, 0);
			 
			 Phrase line4 = new Phrase("Jina la Mzazi/mlezi:     ....................................................", boldFontmkuu);
		     PdfContentByte canvas10 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas10, Element.ALIGN_CENTER, line4, 135, 50, 0);
			 
			 Phrase line5 = new Phrase("Namba ya Simu:          ....................................................", boldFontmkuu);
		     PdfContentByte canvas11 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas11, Element.ALIGN_CENTER, line5, 135, 35, 0);
			 
			 Phrase line6 = new Phrase("Tarehe:                         ....................................................", boldFontmkuu);
		     PdfContentByte canvas12 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas12, Element.ALIGN_CENTER, line6, 135, 20, 0);
			 
		     
			  tablepdf = new PdfPTable(new float[] {  6, (float)1.77});
			  tablepdf.setWidthPercentage(100);
			  tablepdf.addCell(new Phrase("SOMO", boldTitle));
			  tablepdf.addCell(new Phrase("TABIA NA MWENENDO", boldTitle));
		    // document.add(new Paragraph("                                                    "));
		      masomo = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		      masomo.setWidthPercentage(100);
		     PdfPCell somo = new PdfPCell(new Phrase("MASOMO", boldFonti));
		     somo.setColspan(1);
		     PdfPCell zoezi = new PdfPCell(new Phrase("MAZOEZI", boldFonti));
		     zoezi.setColspan(1);
		     PdfPCell midtem = new PdfPCell(new Phrase("MIDTERM", boldFonti));
		     midtem.setColspan(1);
		     PdfPCell annual = new PdfPCell(new Phrase(examtype, boldFonti));
		     annual.setColspan(1);
		     PdfPCell average = new PdfPCell(new Phrase("WASTANI", boldFonti));
		     average.setColspan(1);
		     PdfPCell nafasi = new PdfPCell(new Phrase("NAFASI", boldFonti));
		     nafasi.setColspan(1);
		     PdfPCell kati = new PdfPCell(new Phrase("KATI YA", boldFonti));
		     kati.setColspan(1);
		     PdfPCell alama = new PdfPCell(new Phrase("ALAMA", boldFonti));
		     alama.setColspan(1);
		     PdfPCell uzito = new PdfPCell(new Phrase("UZITO WA ALAMA", boldFonti));
		     uzito.setColspan(1);
		     PdfPCell maelekezo = new PdfPCell(new Phrase("MAELEKEZO", boldFonti));
		     maelekezo.setColspan(1);
		     PdfPCell ala = new PdfPCell(new Phrase("ALAMA", boldFonti));
		     ala.setColspan(1);
		    
		     fmu1 = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1}); 
		     fmu1.setWidthPercentage(100);
		     PdfPCell uraia = new PdfPCell(new Phrase("Uraia", boldFont));
		     uraia.setColspan(1);
		     PdfPCell zoeziMax = new PdfPCell(new Phrase(civicsmazoezi, boldFont));
		     zoeziMax.setColspan(1);
		     PdfPCell midtemMax = new PdfPCell(new Phrase(civicsmidterm, boldFont));
		     midtemMax.setColspan(1);
		     PdfPCell annualMax = new PdfPCell(new Phrase(civicsexam, boldFont));
		     annualMax.setColspan(1);
		     PdfPCell averageUraia = new PdfPCell(new Phrase(civicswastan, boldFont));
		     averageUraia.setColspan(1);
		     PdfPCell nafasiUraia = new PdfPCell(new Phrase(positioncivis, boldFont));
		     nafasiUraia.setColspan(1);
		     PdfPCell katiUraia = new PdfPCell(new Phrase(katiyacivics, boldFont));
		     katiUraia.setColspan(1);
		     PdfPCell alamaUraia = new PdfPCell(new Phrase(gradecivics, boldFont));
		     alamaUraia.setColspan(1);
		     PdfPCell uzitoUraia = new PdfPCell(new Phrase(uzitocivics, boldFont));
		     uzitoUraia.setColspan(1);
		     PdfPCell bidii = new PdfPCell(new Phrase("Bidii na maarifa", boldFont));
		     bidii.setColspan(1);
		     bidiinamaarifa = new PdfPCell(new Phrase(bdiii, boldFont));
		     bidiinamaarifa.setColspan(1);
		    
		     fmu1.addCell(uraia);
		     fmu1.addCell(zoeziMax);
		     fmu1.addCell(midtemMax);
		     fmu1.addCell(annualMax);
		     fmu1.addCell(averageUraia);
		     fmu1.addCell(nafasiUraia);
		     fmu1.addCell(katiUraia);
		     fmu1.addCell(alamaUraia);
		     fmu1.addCell(uzitoUraia);
		     fmu1.addCell(bidii);
		     fmu1.addCell(bidiinamaarifa);
		     
		     
			 	
		     fmuJiog = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		     fmuJiog .setWidthPercentage(100);
		     PdfPCell jiografia = new PdfPCell(new Phrase("Jiografia", boldFont));
		     jiografia.setColspan(1);
		     PdfPCell zoeziJiog = new PdfPCell(new Phrase(geogmazoezi, boldFont));
		     zoeziJiog.setColspan(1);
		     PdfPCell midtemJiog = new PdfPCell(new Phrase(geogmidterm, boldFont));
		     midtemJiog.setColspan(1);
		     PdfPCell annualJiog = new PdfPCell(new Phrase(geogexam, boldFont));
		     annualJiog.setColspan(1);
		     PdfPCell averageJiog = new PdfPCell(new Phrase(geogwastan, boldFont));
		     averageJiog.setColspan(1);
		     PdfPCell nafasiJiog = new PdfPCell(new Phrase(positiongeog, boldFont));
		     nafasiJiog.setColspan(1);
		     PdfPCell katiJiog = new PdfPCell(new Phrase(katiyageog, boldFont));
		     katiJiog.setColspan(1);
		     PdfPCell alamaJiog = new PdfPCell(new Phrase(gradegeog, boldFont));
		     alamaJiog.setColspan(1);
		     PdfPCell uzitoJiog = new PdfPCell(new Phrase(uzitogeog, boldFont));
		     uzitoJiog.setColspan(1);
		     PdfPCell bidiiJiog = new PdfPCell(new Phrase("Ari ya kazi", boldFont));
		     bidiiJiog.setColspan(1);
		     ariyakazi = new PdfPCell(new Phrase(arriii, boldFont));
		     
		     
		     fmuJiog.addCell(jiografia);
		     fmuJiog.addCell(zoeziJiog);
		     fmuJiog.addCell(midtemJiog);
		     fmuJiog.addCell(annualJiog);
		     fmuJiog.addCell(averageJiog);
		     fmuJiog.addCell(nafasiJiog);
		     fmuJiog.addCell(katiJiog);
		     fmuJiog.addCell(alamaJiog);
		     fmuJiog.addCell(uzitoJiog);
		     fmuJiog.addCell(bidiiJiog);
		     fmuJiog.addCell(ariyakazi);
		     
		     
		     
		      fmuHis = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		      fmuHis.setWidthPercentage(100); 
		    
		     PdfPCell historia = new PdfPCell(new Phrase("Historia", boldFont));
		     historia.setColspan(1);
		     PdfPCell zoeziHis = new PdfPCell(new Phrase(histmazoezi, boldFont));
		     zoeziHis.setColspan(1);
		     PdfPCell midtemHis = new PdfPCell(new Phrase(histmidterm, boldFont));
		     midtemHis.setColspan(1);
		     PdfPCell annualHis = new PdfPCell(new Phrase(histexam, boldFont));
		     annualHis.setColspan(1);
		     PdfPCell averageHis = new PdfPCell(new Phrase(histwastan, boldFont));
		     averageHis.setColspan(1);
		     PdfPCell nafasiHis = new PdfPCell(new Phrase(positionhist, boldFont));
		     nafasiHis.setColspan(1);
		     PdfPCell katiHis = new PdfPCell(new Phrase(katiyahist, boldFont));
		     katiHis.setColspan(1);
		     PdfPCell alamaHis = new PdfPCell(new Phrase(gradehist, boldFont));
		     alamaHis.setColspan(1);
		     PdfPCell uzitoHis = new PdfPCell(new Phrase(uzitohist, boldFont));
		     uzitoHis.setColspan(1);
		     PdfPCell bidiiHis = new PdfPCell(new Phrase("Ubora wa kazi", boldFont));
		     bidiiHis.setColspan(1);
		     uborawakazi = new PdfPCell(new Phrase(ubraaa, boldFont));
		     uborawakazi.setColspan(1);
		     
		     fmuHis.addCell(historia);
		     fmuHis.addCell(zoeziHis);
		     fmuHis.addCell(midtemHis);
		     fmuHis.addCell(annualHis);
		     fmuHis.addCell(averageHis);
		     fmuHis.addCell(nafasiHis);
		     fmuHis.addCell(katiHis);
		     fmuHis.addCell(alamaHis);
		     fmuHis.addCell(uzitoHis);
		     fmuHis.addCell(bidiiHis);
		     fmuHis.addCell(uborawakazi);
		     
		    
		    
		     
		      fmuKisw = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		      fmuKisw.setWidthPercentage(100);
		     PdfPCell kiswahili = new PdfPCell(new Phrase("Kiswahili", boldFont));
		     kiswahili.setColspan(1);
		    PdfPCell zoezikisw = new PdfPCell(new Phrase(kiswmazoezi, boldFont));
		     zoezikisw.setColspan(1);
		     PdfPCell midtemkisw = new PdfPCell(new Phrase(kiswmidterm, boldFont));
		     midtemkisw.setColspan(1);
		     PdfPCell annualkisw = new PdfPCell(new Phrase(kiswexam, boldFont));
		     annualkisw.setColspan(1);
		     PdfPCell averagekisw = new PdfPCell(new Phrase(kiswwastan, boldFont));
		     averagekisw.setColspan(1);
		     PdfPCell nafasikisw = new PdfPCell(new Phrase(positionkisw, boldFont));
		     nafasikisw.setColspan(1);
		     PdfPCell katikisw = new PdfPCell(new Phrase(katiyakisw, boldFont));
		     katikisw.setColspan(1);
		     PdfPCell alamakisw = new PdfPCell(new Phrase(gradekisw, boldFont));
		     alamakisw.setColspan(1);
		     PdfPCell uzitokisw = new PdfPCell(new Phrase(uzitokiswa, boldFont));
		     uzitokisw.setColspan(1);
		     PdfPCell bidiikisw = new PdfPCell(new Phrase("Utunzaji wa vifaa", boldFont));
		     bidiikisw.setColspan(1);
		     utanzajiwavifaa = new PdfPCell(new Phrase(utunzuuu, boldFont));
		     utanzajiwavifaa.setColspan(1);
		     
		     fmuKisw.addCell(kiswahili);
		     fmuKisw.addCell(zoezikisw);
		     fmuKisw.addCell(midtemkisw);
		     fmuKisw.addCell(annualkisw);
		     fmuKisw.addCell(averagekisw);
		     fmuKisw.addCell(nafasikisw);
		     fmuKisw.addCell(katikisw);
		     fmuKisw.addCell(alamakisw);
		     fmuKisw.addCell(uzitokisw);
		     fmuKisw.addCell(bidiikisw);
		     fmuKisw.addCell(utanzajiwavifaa);
		   
		     
		      fmuEng = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		      fmuEng.setWidthPercentage(100);
		     PdfPCell english = new PdfPCell(new Phrase("Kiingereza", boldFont));
		     english.setColspan(1);
		    PdfPCell zoeziEng = new PdfPCell(new Phrase(engmazoezi, boldFont));
		     zoeziEng.setColspan(1);
		     PdfPCell midtemEng = new PdfPCell(new Phrase(engmidterm, boldFont));
		     midtemEng.setColspan(1);
		     PdfPCell annualEng = new PdfPCell(new Phrase(engexam, boldFont));
		     annualEng.setColspan(1);
		     PdfPCell averageEng = new PdfPCell(new Phrase(engwastan, boldFont));
		     averageEng.setColspan(1);
		     PdfPCell nafasiEng = new PdfPCell(new Phrase(positioneng, boldFont));
		     nafasiEng.setColspan(1);
		     PdfPCell katiEng = new PdfPCell(new Phrase(katiyaeng, boldFont));
		     katiEng.setColspan(1);
		     PdfPCell alamaEng = new PdfPCell(new Phrase(gradeeng, boldFont));
		     alamaEng.setColspan(1);
		     PdfPCell uzitoEng = new PdfPCell(new Phrase(uzitoeng, boldFont));
		     uzitoEng.setColspan(1);
		     PdfPCell   bidiiEng = new PdfPCell(new Phrase("Ushirikiano", boldFont));
		     bidiiEng.setColspan(1);
		     ushirikiano = new PdfPCell(new Phrase(ushirikiii, boldFont));
		     ushirikiano.setColspan(1);
		     
		     fmuEng.addCell(english);
		     fmuEng.addCell(zoeziEng);
		     fmuEng.addCell(midtemEng);
		     fmuEng.addCell(annualEng);
		     fmuEng.addCell(averageEng);
		     fmuEng.addCell(nafasiEng);
		     fmuEng.addCell(katiEng);
		     fmuEng.addCell(alamaEng);
		     fmuEng.addCell(uzitoEng);
		     fmuEng.addCell(bidiiEng);
		     fmuEng.addCell(ushirikiano);
		
		      fmuPhy = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		      fmuPhy.setWidthPercentage(100);
		     PdfPCell Fizikia = new PdfPCell(new Phrase("Fizikia", boldFont));
		     Fizikia.setColspan(1);
		    PdfPCell zoeziFiz = new PdfPCell(new Phrase(phymazoezi, boldFont));
		     zoeziFiz.setColspan(1);
		     PdfPCell midtemFiz = new PdfPCell(new Phrase(phymidterm, boldFont));
		     midtemFiz.setColspan(1);
		     PdfPCell annualFiz = new PdfPCell(new Phrase(phyexam, boldFont));
		     annualFiz.setColspan(1);
		     PdfPCell averageFiz = new PdfPCell(new Phrase(phytwastan, boldFont));
		     averageFiz.setColspan(1);
		     PdfPCell nafasiFiz = new PdfPCell(new Phrase(positionphy, boldFont));
		     nafasiFiz.setColspan(1);
		     PdfPCell katiFiz = new PdfPCell(new Phrase(katiyaphy, boldFont));
		     katiFiz.setColspan(1);
		     PdfPCell alamaFiz = new PdfPCell(new Phrase(gradephy, boldFont));
		     alamaFiz.setColspan(1);
		     PdfPCell uzitoFiz = new PdfPCell(new Phrase(uzitophy, boldFont));
		     uzitoFiz.setColspan(1);
		     PdfPCell bidiiFiz = new PdfPCell(new Phrase("Heshima", boldFont));
		     bidiiFiz.setColspan(1);
		     heshima = new PdfPCell(new Phrase(heshmaaa, boldFont));
		     heshima.setColspan(1);
		    
		     fmuPhy.addCell(Fizikia);
		     fmuPhy.addCell(zoeziFiz);
		     fmuPhy.addCell(midtemFiz);
		     fmuPhy.addCell(annualFiz);
		     fmuPhy.addCell(averageFiz);
		     fmuPhy.addCell(nafasiFiz);
		     fmuPhy.addCell(katiFiz);
		     fmuPhy.addCell(alamaFiz);
		     fmuPhy.addCell(uzitoFiz);
		     fmuPhy.addCell(bidiiFiz);
		     fmuPhy.addCell(heshima);
		     
		     
		      fmuChem = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		      fmuChem.setWidthPercentage(100);
		     PdfPCell chemia = new PdfPCell(new Phrase("Kemia", boldFont));
		     chemia.setColspan(1);
		    PdfPCell zoezichemia = new PdfPCell(new Phrase(chemmazoezi, boldFont));
		     zoezichemia.setColspan(1);
		     PdfPCell midtemchemia = new PdfPCell(new Phrase(chemmidterm, boldFont));
		     midtemchemia.setColspan(1);
		     PdfPCell annualchemia = new PdfPCell(new Phrase(chemexam, boldFont));
		     annualchemia.setColspan(1);
		     PdfPCell averagechemia = new PdfPCell(new Phrase(chemwastan, boldFont));
		     averagechemia.setColspan(1);
		     PdfPCell nafasichemia = new PdfPCell(new Phrase(positionchem, boldFont));
		     nafasichemia.setColspan(1);
		     PdfPCell katichemia = new PdfPCell(new Phrase(katiyachem, boldFont));
		     katichemia.setColspan(1);
		     PdfPCell alamachemia = new PdfPCell(new Phrase(gradechem, boldFont));
		     alamachemia.setColspan(1);
		     PdfPCell uzitochemia = new PdfPCell(new Phrase(uzitochem, boldFont));
		     uzitochemia.setColspan(1);
		     PdfPCell bidiichemia = new PdfPCell(new Phrase("Uongozi", boldFont));
		     bidiichemia.setColspan(1);
		     uongozi = new PdfPCell(new Phrase(uongziii, boldFont));
		     uongozi.setColspan(1);
		     
		     fmuChem.addCell(chemia);
		     fmuChem.addCell(zoezichemia);
		     fmuChem.addCell(midtemchemia);
		     fmuChem.addCell(annualchemia);
		     fmuChem.addCell(averagechemia);
		     fmuChem.addCell(nafasichemia);
		     fmuChem.addCell(katichemia);
		     fmuChem.addCell(alamachemia);
		     fmuChem.addCell(uzitochemia);
		     fmuChem.addCell(bidiichemia);
		     fmuChem.addCell(uongozi);
  
		     
		     
		   
		     fmuBios = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		     fmuBios.setWidthPercentage(100);
		     PdfPCell bios = new PdfPCell(new Phrase("Baiolojia", boldFont));
		     bios.setColspan(1);
		    PdfPCell zoezibios = new PdfPCell(new Phrase(biosmazoezi, boldFont));
		     zoezibios.setColspan(1);
		     PdfPCell midtembios = new PdfPCell(new Phrase(biosmidterm, boldFont));
		     midtembios.setColspan(1);
		     PdfPCell annualbios = new PdfPCell(new Phrase(biosexam, boldFont));
		     annualbios.setColspan(1);
		     PdfPCell averagebios = new PdfPCell(new Phrase(bioswastan, boldFont));
		     averagebios.setColspan(1);
		     PdfPCell nafasibios = new PdfPCell(new Phrase(positionbios, boldFont));
		     nafasibios.setColspan(1);
		     PdfPCell katibios = new PdfPCell(new Phrase(katiyabios, boldFont));
		     katibios.setColspan(1);
		     PdfPCell alamabios = new PdfPCell(new Phrase(gradebios, boldFont));
		     alamabios.setColspan(1);
		     PdfPCell uzitobios = new PdfPCell(new Phrase(uzitobiosi, boldFont));
		     uzitobios.setColspan(1);
		     PdfPCell bidiibios = new PdfPCell(new Phrase("Utii na kujituma", boldFont));
		     bidiibios.setColspan(1);
		     utiinakujituma = new PdfPCell(new Phrase(utiiii, boldFont));
		     utiinakujituma.setColspan(1);
		     
		     fmuBios.addCell(bios);
		     fmuBios.addCell(zoezibios);
		     fmuBios.addCell(midtembios);
		     fmuBios.addCell(annualbios);
		     fmuBios.addCell(averagebios);
		     fmuBios.addCell(nafasibios);
		     fmuBios.addCell(katibios);
		     fmuBios.addCell(alamabios);
		     fmuBios.addCell(uzitobios);
		     fmuBios.addCell(bidiibios);
		     fmuBios.addCell(utiinakujituma);
		     
		    
		    
		     
		     fmuMath = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		     fmuMath.setWidthPercentage(100);
		     PdfPCell hisabati = new PdfPCell(new Phrase("Hisabati", boldFont));
		     hisabati.setColspan(1);
		    PdfPCell zoeziMath = new PdfPCell(new Phrase(mathmazoezi, boldFont));
		     zoeziMath.setColspan(1);
		     PdfPCell midtemMath = new PdfPCell(new Phrase(mathmidterm, boldFont));
		     midtemMath.setColspan(1);
		     PdfPCell annualMath = new PdfPCell(new Phrase(mathexam, boldFont));
		     annualMath.setColspan(1);
		     PdfPCell averageMath = new PdfPCell(new Phrase(mathwastan, boldFont));
		     averageMath.setColspan(1);
		     PdfPCell nafasiMath = new PdfPCell(new Phrase(positionmath, boldFont));
		     nafasiMath.setColspan(1);
		     PdfPCell katiMath = new PdfPCell(new Phrase(katiyamath, boldFont));
		     katiMath.setColspan(1);
		     PdfPCell alamaMath = new PdfPCell(new Phrase(grademath, boldFont));
		     alamaMath.setColspan(1);
		     PdfPCell uzitoMath = new PdfPCell(new Phrase(uzitomath, boldFont));
		     uzitoMath.setColspan(1);
		     PdfPCell bidiiMath = new PdfPCell(new Phrase("Usafi", boldFont));
		     bidiiMath.setColspan(1);
		     usafi = new PdfPCell(new Phrase(usfiii, boldFont));
		     usafi.setColspan(1);
		     
		     fmuMath.addCell(hisabati);
		     fmuMath.addCell(zoeziMath);
		     fmuMath.addCell(midtemMath);
		     fmuMath.addCell(annualMath);
		     fmuMath.addCell(averageMath);
		     fmuMath.addCell(nafasiMath);
		     fmuMath.addCell(katiMath);
		     fmuMath.addCell(alamaMath);
		     fmuMath.addCell(uzitoMath);
		     fmuMath.addCell(bidiiMath);
		     fmuMath.addCell(usafi);
		   
		     
		      fmuComp = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		      fmuComp.setWidthPercentage(100);
		     PdfPCell Comp = new PdfPCell(new Phrase("Computer", boldFont));
		     Comp.setColspan(1);
		     PdfPCell zoeziComp = new PdfPCell(new Phrase(compmazoezi, boldFont));
		     zoeziComp.setColspan(1);
		     PdfPCell midtemComp = new PdfPCell(new Phrase(compmidterm, boldFont));
		     midtemComp.setColspan(1);
		     PdfPCell annualComp = new PdfPCell(new Phrase(compexam, boldFont));
		     annualComp.setColspan(1);
		     PdfPCell averageComp = new PdfPCell(new Phrase(compwastan, boldFont));
		     averageComp.setColspan(1);
		     PdfPCell nafasiComp = new PdfPCell(new Phrase(positioncomp, boldFont));
		     nafasiComp.setColspan(1);
		     PdfPCell katiComp = new PdfPCell(new Phrase(katiyacomp, boldFont));
		     katiComp.setColspan(1);
		     PdfPCell alamaComp = new PdfPCell(new Phrase(gradecomp, boldFont));
		     alamaComp.setColspan(1);
		     PdfPCell uzitoComp = new PdfPCell(new Phrase(uzitocomp, boldFont));
		     uzitoComp.setColspan(1);
		     PdfPCell bidiiComp = new PdfPCell(new Phrase("Uaminifu", boldFont));
		     bidiiComp.setColspan(1);
		     uaminifu = new PdfPCell(new Phrase(uaminiii, boldFont));
		     uaminifu.setColspan(1);
		     
		    
		     fmuComp.addCell(Comp);
		     fmuComp.addCell(zoeziComp);
		     fmuComp.addCell(midtemComp);
		     fmuComp.addCell(annualComp);
		     fmuComp.addCell(averageComp);
		     fmuComp.addCell(nafasiComp);
		     fmuComp.addCell(katiComp);
		     fmuComp.addCell(alamaComp);
		     fmuComp.addCell(uzitoComp);
		     fmuComp.addCell(bidiiComp);
		     fmuComp.addCell(uaminifu);
		     
		     fmuCom = new PdfPTable(new float[] {10,2,1});
		     fmuCom.setWidthPercentage(100);
		     PdfPCell Com = new PdfPCell(new Phrase("", boldFont));
		    
		     PdfPCell bidiiCom = new PdfPCell(new Phrase("Utamaduni", boldFont));
		     bidiiCom.setColspan(1);
		     utamaduni= new PdfPCell(new Phrase(utamaduniii, boldFont));
		     utamaduni.setColspan(1);
		     fmuCom.addCell(Com);
		     fmuCom.addCell(bidiiCom);
		     fmuCom.addCell(utamaduni);
		     
		     fmuComuaminifu = new PdfPTable(new float[] {10,2,1});
		     fmuComuaminifu.setWidthPercentage(100);
		     PdfPCell emp = new PdfPCell(new Phrase("", boldFont));
		     
		     PdfPCell uaminfu = new PdfPCell(new Phrase("Uaminifu", boldFont));
		     bidiiCom.setColspan(1);
		     PdfPCell   uamin= new PdfPCell(new Phrase(uaminiii, boldFont));
		     uamin.setColspan(1);
		     fmuComuaminifu.addCell(emp);
		     fmuComuaminifu.addCell(uaminfu);
		     fmuComuaminifu.addCell(uamin);
		     
		     
		     fmuuhasibu = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		     fmuuhasibu.setWidthPercentage(100);
		     PdfPCell uhasibu = new PdfPCell(new Phrase("Uhasibu", boldFont));
		     uhasibu.setColspan(1);
		     PdfPCell zoeziuhasibu = new PdfPCell(new Phrase(uhasibumazoezi, boldFont));
		     zoeziuhasibu.setColspan(1);
		     PdfPCell midtemuhasibu = new PdfPCell(new Phrase(uhasibumidterm, boldFont));
		     midtemuhasibu.setColspan(1);
		     PdfPCell annualuhasibu = new PdfPCell(new Phrase(uhasibuexam, boldFont));
		     annualuhasibu.setColspan(1);
		     PdfPCell averageuhasibu = new PdfPCell(new Phrase(uhasibuwastan, boldFont));
		     averageuhasibu.setColspan(1);
		     PdfPCell nafasiuhasibu = new PdfPCell(new Phrase(positionuhasibu, boldFont));
		     nafasiuhasibu.setColspan(1);
		     PdfPCell katiuhasibu = new PdfPCell(new Phrase(katiyauhasibu, boldFont));
		     katiuhasibu.setColspan(1);
		     PdfPCell alamauhasibu = new PdfPCell(new Phrase(gradeuhasibu, boldFont));
		     alamauhasibu.setColspan(1);
		     PdfPCell uzitouhasibu = new PdfPCell(new Phrase(uzitouhasibuu, boldFont));
		     uzitouhasibu.setColspan(1);
		     PdfPCell bidiiuhasibu = new PdfPCell(new Phrase("-", boldFont));
		     bidiiuhasibu.setColspan(2);
		     

		   
		     fmuCommerce = new PdfPTable(new float[] { 2,1,1,1,1,1,1,1,1,2,1});
		     fmuCommerce.setWidthPercentage(100);
		     PdfPCell biashara = new PdfPCell(new Phrase("Biashara", boldFont));
		     biashara.setColspan(1);
		     PdfPCell zoezibiashara = new PdfPCell(new Phrase(biasharamazoezi, boldFont));
		     zoezibiashara.setColspan(1);
		     PdfPCell midtembiashara = new PdfPCell(new Phrase(biasharamidterm, boldFont));
		     midtembiashara.setColspan(1);
		     PdfPCell annualbiashara = new PdfPCell(new Phrase(biasharaexam, boldFont));
		     annualbiashara.setColspan(1);
		     PdfPCell averagebiashara = new PdfPCell(new Phrase(biasharawastan, boldFont));
		     averagebiashara.setColspan(1);
		     PdfPCell nafasibiashara = new PdfPCell(new Phrase(positionbiashara, boldFont));
		     nafasibiashara.setColspan(1);
		     PdfPCell katibiashara = new PdfPCell(new Phrase(katiyabiashara, boldFont));
		     katibiashara.setColspan(1);
		     PdfPCell alamabiashara = new PdfPCell(new Phrase(gradebiashara, boldFont));
		     alamabiashara.setColspan(1);
		     PdfPCell uzitobiashara = new PdfPCell(new Phrase(uzitobiasharaa, boldFont));
		     uzitobiashara.setColspan(1);
		     PdfPCell  bidiibiashara = new PdfPCell(new Phrase("Utamaduni", boldFont));
		     bidiibiashara.setColspan(1);
		   
		     utamaduniCD = new PdfPCell(new Phrase(utamaduniii, boldFont));
		     utamaduniCD.setColspan(1);
		     
		    
		     fmuCommerce.addCell(biashara);
		     fmuCommerce.addCell(zoezibiashara);
		     fmuCommerce.addCell(midtembiashara);
		     fmuCommerce.addCell(annualbiashara);
		     fmuCommerce.addCell(averagebiashara);
		     fmuCommerce.addCell(nafasibiashara);
		     fmuCommerce.addCell(katibiashara);
		     fmuCommerce.addCell(alamabiashara);
		     fmuCommerce.addCell(uzitobiashara);
		     fmuCommerce.addCell(bidiibiashara);
		     fmuCommerce.addCell(utamaduniCD);
		     
		 	   
		 	 fmuuhasibu.addCell(uhasibu);
		     fmuuhasibu.addCell(zoeziuhasibu);
		     fmuuhasibu.addCell(midtemuhasibu);
		     fmuuhasibu.addCell(annualuhasibu);
		     fmuuhasibu.addCell(averageuhasibu);
		     fmuuhasibu.addCell(nafasiuhasibu);
		     fmuuhasibu.addCell(katiuhasibu);
		     fmuuhasibu.addCell(alamauhasibu);
		     fmuuhasibu.addCell(uzitouhasibu);
		     fmuuhasibu.addCell(bidiiuhasibu);
		     
		    
		     literatureinenglish = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		     literatureinenglish .setWidthPercentage(100);
		     PdfPCell lite = new PdfPCell(new Phrase("L/English", boldFont));
		     lite.setColspan(1);
		     PdfPCell zoezilite = new PdfPCell(new Phrase(lengmazoezi, boldFont));
		     zoezilite.setColspan(1);
		     PdfPCell midtemlite = new PdfPCell(new Phrase(lengmidterm, boldFont));
		     midtemlite.setColspan(1);
		     PdfPCell annuallite = new PdfPCell(new Phrase(lengexam, boldFont));
		     annuallite.setColspan(1);
		     PdfPCell averagelite = new PdfPCell(new Phrase(lengwastan, boldFont));
		     averagelite.setColspan(1);
		     PdfPCell nafasilite = new PdfPCell(new Phrase(positionleng, boldFont));
		     nafasilite.setColspan(1);
		     PdfPCell katilite = new PdfPCell(new Phrase(katiyaleng, boldFont));
		     katilite.setColspan(1);
		     PdfPCell alamalite = new PdfPCell(new Phrase(gradeleng, boldFont));
		     alamalite.setColspan(1);
		     PdfPCell uzitolite = new PdfPCell(new Phrase(uzitoleng, boldFont));
		     uzitolite.setColspan(1);
		     PdfPCell bidiilite = new PdfPCell(new Phrase("", boldFont));
		     bidiilite.setColspan(2);
		     literatureinenglish.addCell(lite);
		     literatureinenglish.addCell(zoezilite);
		     literatureinenglish.addCell(midtemlite);
		     literatureinenglish.addCell(annuallite);
		     literatureinenglish.addCell(averagelite);
		     literatureinenglish.addCell(nafasilite);
		     literatureinenglish.addCell(katilite);
		     literatureinenglish.addCell(alamalite);
		     literatureinenglish.addCell(uzitolite);
		     literatureinenglish.addCell(bidiilite);
		     
		     
		    
		     islamicknow = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		     islamicknow.setWidthPercentage(100);
		     PdfPCell islamic = new PdfPCell(new Phrase("Islamic knowledge", boldFont));
		     islamic.setColspan(1);
		     PdfPCell zoeziislamic = new PdfPCell(new Phrase(islamicmazoezi, boldFont));
		     zoeziislamic.setColspan(1);
		     PdfPCell midtemislamic = new PdfPCell(new Phrase(islamicmidterm, boldFont));
		     midtemislamic.setColspan(1);
		     PdfPCell annualislamic = new PdfPCell(new Phrase(islamicexam, boldFont));
		     annualislamic.setColspan(1);
		     PdfPCell averageislamic = new PdfPCell(new Phrase(islamicwastan, boldFont));
		     averageislamic.setColspan(1);
		     PdfPCell nafasiislamic = new PdfPCell(new Phrase(positionislamic, boldFont));
		     nafasiislamic.setColspan(1);
		     PdfPCell katiislamic = new PdfPCell(new Phrase(katiyaislamic, boldFont));
		     katiislamic.setColspan(1);
		     PdfPCell alamaislamic = new PdfPCell(new Phrase(gradeislamic, boldFont));
		     alamaislamic.setColspan(1);
		     PdfPCell uzitoislamic = new PdfPCell(new Phrase(uzitoislamicc, boldFont));
		     uzitoislamic.setColspan(1);
		     PdfPCell bidiiislamic = new PdfPCell(new Phrase("", boldFont));
		     bidiiislamic.setColspan(2);
		     islamicknow.addCell(islamic);
		     islamicknow.addCell(zoeziislamic);
		     islamicknow.addCell(midtemislamic);
		     islamicknow.addCell(annualislamic);
		     islamicknow.addCell(averageislamic);
		     islamicknow.addCell(nafasiislamic);
		     islamicknow.addCell(katiislamic);
		     islamicknow.addCell(alamaislamic);
		     islamicknow.addCell(uzitoislamic);
		     islamicknow.addCell(bidiiislamic);
		    
		    
		     
		     bibleknow = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		     bibleknow.setWidthPercentage(100);
		     PdfPCell bible = new PdfPCell(new Phrase("Bible knowledge", boldFont));
		     bible.setColspan(1);
		     PdfPCell zoezibible = new PdfPCell(new Phrase(biblemazoezi, boldFont));
		     zoezibible.setColspan(1);
		     PdfPCell midtembible = new PdfPCell(new Phrase(biblemidterm, boldFont));
		     midtembible.setColspan(1);
		     PdfPCell annualbible = new PdfPCell(new Phrase(bibleexam, boldFont));
		     annualbible.setColspan(1);
		     PdfPCell averagebible = new PdfPCell(new Phrase(biblewastan, boldFont));
		     averagebible.setColspan(1);
		     PdfPCell nafasibible = new PdfPCell(new Phrase(positionbible, boldFont));
		     nafasibible.setColspan(1);
		     PdfPCell katibible = new PdfPCell(new Phrase(katiyabible, boldFont));
		     katibible.setColspan(1);
		     PdfPCell alamabible= new PdfPCell(new Phrase(gradebible, boldFont));
		     alamabible.setColspan(1);
		     PdfPCell uzitobible = new PdfPCell(new Phrase(uzitobiblee, boldFont));
		     uzitobible.setColspan(1);
		     PdfPCell bidiibible = new PdfPCell(new Phrase("", boldFont));
		     bidiibible.setColspan(2);
		     bibleknow.addCell(bible);
		     bibleknow.addCell(zoezibible);
		     bibleknow.addCell(midtembible);
		     bibleknow.addCell(annualbible);
		     bibleknow.addCell(averagebible);
		     bibleknow.addCell(nafasibible);
		     bibleknow.addCell(katibible);
		     bibleknow.addCell(alamabible);
		     bibleknow.addCell(uzitobible);
		     bibleknow.addCell(bidiibible);
		     
		     
		     food = new PdfPTable(new float[] { 2, 1,1,1,1,1,1,1,1,2,1});
		     food.setWidthPercentage(100);
		     PdfPCell foodn = new PdfPCell(new Phrase("Food and Nutrition", boldFont));
		     foodn.setColspan(1);
		     PdfPCell zoezifoodn= new PdfPCell(new Phrase(foodmazoezi, boldFont));
		     zoezifoodn.setColspan(1);
		     PdfPCell midtemfoodn = new PdfPCell(new Phrase(foodmidterm, boldFont));
		     midtemfoodn.setColspan(1);
		     PdfPCell annualfoodn = new PdfPCell(new Phrase(foodexam, boldFont));
		     annualfoodn.setColspan(1);
		     PdfPCell averagefoodn = new PdfPCell(new Phrase(foodwastan, boldFont));
		     averagefoodn.setColspan(1);
		     PdfPCell nafasifoodn = new PdfPCell(new Phrase(positionfood, boldFont));
		     nafasifoodn.setColspan(1);
		     PdfPCell katifoodn = new PdfPCell(new Phrase(katiyafood, boldFont));
		     katifoodn.setColspan(1);
		     PdfPCell alamafoodn= new PdfPCell(new Phrase(gradefood, boldFont));
		     alamafoodn.setColspan(1);
		     PdfPCell uzitofoodn = new PdfPCell(new Phrase(uzitofood, boldFont));
		     uzitofoodn.setColspan(1);
		     PdfPCell bidiifoodn = new PdfPCell(new Phrase("", boldFont));
		     bidiifoodn.setColspan(2);
		     food.addCell(foodn);
		     food.addCell(zoezifoodn);
		     food.addCell(midtemfoodn);
		     food.addCell(annualfoodn);
		     food.addCell(averagefoodn);
		     food.addCell(nafasifoodn);
		     food.addCell(katifoodn);
		     food.addCell(alamafoodn);
		     food.addCell(uzitofoodn);
		     food.addCell(bidiifoodn);
		     
		   
		     
		      fmuComW = new PdfPTable(new float[] {(float) 0.4, 1});
		      fmuComW.setWidthPercentage(100);
		     PdfPCell ComW = new PdfPCell(new Phrase("Wastani", boldFont));
		    
		     PdfPCell bidiiComW = new PdfPCell(new Phrase(averagejumla, boldFont));
		     bidiiComW.setColspan(1);
		      fmuDaraja = new PdfPTable(new float[] {(float) 0.8, 1,1});
		      fmuDaraja.setWidthPercentage(100);
		     PdfPCell ComDaraja = new PdfPCell(new Phrase("Daraja", boldFont));
		     PdfPCell alDaraja = new PdfPCell(new Phrase(darajawastani, boldFont));
		     alDaraja.setColspan(1);
		   
		     division = new PdfPCell(new Phrase(divsion, boldFont));
		     division.setColspan(1);
		   
		   
		      fmuUjumla = new PdfPTable(new float[] {(float) 0.4, 1});
		      fmuUjumla.setWidthPercentage(100);
		     PdfPCell ComUjumla = new PdfPCell(new Phrase("Nafasi yake ujumla", boldFont));
		     PdfPCell alUjumla = new PdfPCell(new Phrase(position, boldFont));
		     alUjumla.setColspan(1);
		      fmuWanafunzi = new PdfPTable(new float[] {(float) 0.4, 1});
		      fmuWanafunzi.setWidthPercentage(100);
		     PdfPCell ComWanafunzi = new PdfPCell(new Phrase("Kati ya wanafunzi", boldFont));
		     
		     PdfPCell alWanafunzi = new PdfPCell(new Phrase(jumlastudent, boldFont));
		     alWanafunzi.setColspan(1);     
		    //Vielelezo hapa
		      Vielelezo = new PdfPTable(new float[] { 1, 1,1,1,1,1});
		      Vielelezo.setWidthPercentage(100);
		     PdfPCell gredi = new PdfPCell(new Phrase(" Gredi", boldFont));
		     gredi.setColspan(1);
		     PdfPCell a = new PdfPCell(new Phrase("   A", boldFont));
		     a.setColspan(1);
		     PdfPCell b = new PdfPCell(new Phrase("   B", boldFont));
		     b.setColspan(1);
		     PdfPCell c = new PdfPCell(new Phrase("   C", boldFont));
		     c.setColspan(1);
		     PdfPCell d = new PdfPCell(new Phrase("   D", boldFont));
		     d.setColspan(1);
		     PdfPCell de = new PdfPCell(new Phrase("  F", boldFont));
		     de.setColspan(1);
		     
		      Elezo = new PdfPTable(new float[] { 1, 1,1,1,1,1});
		      Elezo.setWidthPercentage(100);
		     PdfPCell gred = new PdfPCell(new Phrase(" Alama", boldFont));
		     gredi.setColspan(1);
		     PdfPCell ag = new PdfPCell(new Phrase("   100 - 75", boldFont));
		     ag.setColspan(1);
		     PdfPCell bg = new PdfPCell(new Phrase("   74 - 65", boldFont));
		     bg.setColspan(1);
		     PdfPCell cg = new PdfPCell(new Phrase("   64 - 45", boldFont));
		     cg.setColspan(1);
		     PdfPCell dg = new PdfPCell(new Phrase("   44 - 30", boldFont));
		     dg.setColspan(1);
		     PdfPCell dge = new PdfPCell(new Phrase("   29 - 0", boldFont));
		     dge.setColspan(1);
		    
		      Maelezo = new PdfPTable(new float[] { 1, 1,1,1,1,1});
		      Maelezo.setWidthPercentage(100);
		     PdfPCell gredm = new PdfPCell(new Phrase(" Maelezo", boldFont));
		     gredi.setColspan(1);
		     PdfPCell agm = new PdfPCell(new Phrase("   Bora Sana", boldFont));
		     agm.setColspan(1);
		     PdfPCell bgm = new PdfPCell(new Phrase("   Vizuri", boldFont));
		     bgm.setColspan(1);
		     PdfPCell cgm = new PdfPCell(new Phrase("   Wastani", boldFont));
		     cgm.setColspan(1);
		     PdfPCell dgm = new PdfPCell(new Phrase("   Inaridhisha", boldFont));
		     dgm.setColspan(1);
		     PdfPCell egm = new PdfPCell(new Phrase("   Feli", boldFont));
		     egm.setColspan(1);
		     
		     divisheni = new PdfPTable(new float[] { 1, 1,1,1,1,1});
		     divisheni.setWidthPercentage(100);
		     PdfPCell gred1 = new PdfPCell(new Phrase(" Daraja", boldFont));
		     gredi.setColspan(1);
		     PdfPCell ag1 = new PdfPCell(new Phrase("   DIVISION I", boldFont));
		     ag1.setColspan(1);
		     PdfPCell bg1 = new PdfPCell(new Phrase("   DIVISION II", boldFont));
		     bg1.setColspan(1);
		     PdfPCell cg1 = new PdfPCell(new Phrase("   DIVISION III", boldFont));
		     cg1.setColspan(1);
		     PdfPCell dg1 = new PdfPCell(new Phrase("   DIVISION IV", boldFont));
		     dg1.setColspan(1);
		     PdfPCell eg1 = new PdfPCell(new Phrase("   DIVISION 0", boldFont));
		     eg1.setColspan(1);
		      Point = new PdfPTable(new float[] { 1, 1,1,1,1,1});
		      Point.setWidthPercentage(100);
		     PdfPCell gredm1 = new PdfPCell(new Phrase(" Point", boldFont));
		     gredi.setColspan(1);
		     PdfPCell agm1 = new PdfPCell(new Phrase("   7-17", boldFont));
		     agm1.setColspan(1);
		     PdfPCell bgm1 = new PdfPCell(new Phrase("   18-21", boldFont));
		     bgm1.setColspan(1);
		     PdfPCell cgm1 = new PdfPCell(new Phrase("   22-25", boldFont));
		     cgm1.setColspan(1);
		     PdfPCell dgm1 = new PdfPCell(new Phrase("   26-33", boldFont));
		     dgm1.setColspan(1);
		     PdfPCell egm1 = new PdfPCell(new Phrase("   34-35", boldFont));
		     egm1.setColspan(1);
		     
		     
		     
		     masomo.addCell(somo);
		     masomo.addCell(zoezi);
		     masomo.addCell(midtem);
		     masomo.addCell(annual);
		     masomo.addCell(average);
		     masomo.addCell(nafasi);
		     masomo.addCell(kati);
		     masomo.addCell(alama);
		     masomo.addCell(uzito);
		     masomo.addCell(maelekezo);
		     masomo.addCell(ala); 
		  
		   
		     fmuComW.addCell(ComW);
		     fmuComW.addCell(bidiiComW);
		     
		     fmuDaraja.addCell(ComDaraja);
		     fmuDaraja.addCell(alDaraja);
		     fmuDaraja.addCell(division);
		     fmuUjumla.addCell(ComUjumla);
		     fmuUjumla.addCell(alUjumla);
		     fmuWanafunzi.addCell(ComWanafunzi);
		     fmuWanafunzi.addCell(alWanafunzi);

		     Vielelezo.addCell(gredi);
		     Vielelezo.addCell(a);
		     Vielelezo.addCell(b);
		     Vielelezo.addCell(c);
		     Vielelezo.addCell(d);
		     Vielelezo.addCell(de);
		    
		     
		     Elezo.addCell(gred);
		     Elezo.addCell(ag);
		     Elezo.addCell(bg);
		     Elezo.addCell(cg);
		     Elezo.addCell(dg);
		     Elezo.addCell(dge);
		    
		     
		     Maelezo.addCell(gredm);
		     Maelezo.addCell(agm);
		     Maelezo.addCell(bgm);
		     Maelezo.addCell(cgm);
		     Maelezo.addCell(dgm);
		     Maelezo.addCell(egm);
		     
		     
		     divisheni.addCell(gred1);
		     divisheni.addCell(ag1);
		     divisheni.addCell(bg1);
		     divisheni.addCell(cg1);
		     divisheni.addCell(dg1);
		     divisheni.addCell(eg1);
		     
		     Point.addCell(gredm1);
		     Point.addCell(agm1);
		     Point.addCell(bgm1);
		     Point.addCell(cgm1);
		     Point.addCell(dgm1);
		     Point.addCell(egm1);
		     document.add(tablepdf);
		     
		       
		       //fmuCommerce.addCell(utamaduniCD);
		       document.add(masomo);
		       document.add(fmu1);
		       document.add(fmuHis);
		       document.add(fmuJiog);
		       document.add(fmuKisw);
		       document.add(fmuEng);
		       document.add(fmuPhy);
		       document.add(fmuChem);
		       document.add(fmuBios);
		       document.add(fmuMath);
		       try{
			    	  st=con.createStatement();
			    	  String fom3="SELECT * FROM subjects WHERE subjectCode='036'"; 
			    	  rs=st.executeQuery(fom3);
			    	  if(rs.next()){
			   document.add(fmuComp);
			    	  }else{
			   document.add(fmuComuaminifu);
			    	}
			       }catch(Exception s){
			    	   
			       }
		       
		      
		       try{
			    	  st=con.createStatement();
			    	  String fom3="SELECT * FROM subjects WHERE subjectCode='052'"; 
			    	  rs=st.executeQuery(fom3);
			    	  if(rs.next()){
			     document.add(fmuCommerce);
			    	  }else{
			     document.add(fmuCom);
			    	  }
			       }catch(Exception s){
			    	   
			       }
		       try{
			    	  st=con.createStatement();
			    	  String fom3="SELECT * FROM subjects WHERE subjectCode='053'"; 
			    	  rs=st.executeQuery(fom3);
			    	  if(rs.next()){
			   document.add(fmuuhasibu);
			    	  }
			       }catch(Exception s){
			    	   
			       }
			      
		       if(darasa.equals("FORM I")==false&&darasa.equals("FORM II")==false){
		       document.add(literatureinenglish);
		       }
		       try{
			    	  st=con.createStatement();
			    	  String fom3="SELECT * FROM subjects WHERE subjectCode='051'"; 
			    	  rs=st.executeQuery(fom3);
			    	  if(rs.next()){
			      document.add(food); 
			    	  }
			       }catch(Exception s){
			    	   
			       }
		       try{
			    	  st=con.createStatement();
			    	  String fom1="SELECT * FROM subjects WHERE subjectCode='014'"; 
			    	  rs=st.executeQuery(fom1);
			    	  if(rs.next()){
			   document.add(bibleknow); 
			    	  }
			       }catch(Exception s){
			    	   
			       }
		       try{
			    	  st=con.createStatement();
			    	  String fom="SELECT * FROM subjects WHERE subjectCode='015'"; 
			    	  rs=st.executeQuery(fom);
			    	  if(rs.next()){
			   document.add(islamicknow);  
			    	  }
			       }catch(Exception s){
			    	   
			       }
		       document.add(fmuComW);
		       document.add(fmuDaraja);
		       document.add(fmuUjumla);
		       document.add(fmuWanafunzi);
		       document.add(new Paragraph("                                                                                 VIELELEZO    ",boldMzazi));
		       document.add(Vielelezo);
		       document.add(Elezo);
		       document.add(Maelezo);
		      
		       document.add(new Paragraph(""+"\n"));
		       document.add(divisheni);
		       document.add(Point);
		     
		       document.add(new Paragraph("MAAGIZO YA MKUU WA SHULE   ",boldMzazi));
		       String maagizmkuu="SELECT hmaster,class_teacher from announces where class='"+darasa+"' AND year='"+mwaka+"' AND type='"+examinationtype+"' and grade='"+darajawastani+"' ";
		       
		       rs=st.executeQuery(maagizmkuu);
		       while(rs.next()){
		    	    mkuu=rs.getString("hmaster");
		    	    mwalimu=rs.getString("class_teacher");
		    	   
		       }
		       document.add(new Phrase(""+ mkuu+"  ",maagizo));
		       document.add(new Paragraph(""+"\n"));
		       document.add(new Paragraph("MAONI YA MWALIMU WA DARASA   ",boldMzazi));
		       
		       document.add(new Phrase(""+ mwalimu +"              ",maagizo));
		    

		}
		public void Olevalquery() throws SQLException{
			con=database.dbconnect();
			List<Integer> results = new ArrayList<Integer>();
			
			st=con.createStatement();
			 String str="SELECT  mazoezi,mid_term,exam,wastani ,coz_id FROM result_from_teacher where year='"+mwaka+"' AND type='"+examinationtype+"' AND stu_id='"+ID+"'  ";
			 rs=st.executeQuery(str);
			 while(rs.next()){
				  coz=rs.getString("coz_id");
				  maoezi=rs.getString("mazoezi");
				  exam=rs.getString("exam");
				  wastan=rs.getString("wastani");
				  midterm=rs.getString("mid_term");
				  
				  double wast=Double.parseDouble(wastan);
				  if(wast>=74.5){
					  alamacoz="A";
					  unit="1";
				  }else if(wast<29.5){
					  alamacoz="F";
					  unit="5";
				  }
				  else if(wast<74.5&&wast>=64.5){
					  alamacoz="B";
					  unit="2";
				  } else if(wast<64.5&&wast>=44.5){
					  alamacoz="C";
					  unit="3";
				  }
				  else if(wast<44.5&&wast>=29.5){
					  alamacoz="D";
					  unit="4";
				  }
				  int unt=Integer.parseInt(unit);
				    results.add(unt);
                  Collections.sort(results);
				  
				  
				  st2=con.createStatement();
				  String couseposition="SET @row_number=0";
				    String possubject= " SELECT ROW,wastani,coz_id FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id,coz_id FROM (SELECT wastani,stu_id,coz_id FROM result_from_teacher WHERE year='"+mwaka+"' and "+mikondo+" and type='"+examinationtype+"' and coz_id='"+coz+"' )as tablewastan order by wastani DESC) AS MM WHERE stu_id='"+ID+"'";
				    st2.execute(couseposition);
				       rs2=st2.executeQuery(possubject);
				       while(rs2.next()){
				    	   subjectposition=rs2.getString("ROW"); 
				       }  
				       
				       String jumlastudent="SET @row_number=0";
					    String maxstudent= " SELECT MAX(ROW),coz_id FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id,coz_id FROM (SELECT wastani,stu_id,coz_id FROM result_from_teacher WHERE year='"+mwaka+"' and "+mikondo+" and type='"+examinationtype+"' and coz_id='"+coz+"' )as tablewastan order by wastani DESC) AS MM";
					    st2.execute(jumlastudent);
					       rs2=st2.executeQuery(maxstudent);
					       while(rs2.next()){
					    	 subjectjumla=rs2.getString("MAX(ROW)"); 
					    	 }
				       
				  if(coz.equals("011")){
					  civicsmazoezi=maoezi;
					  civicsmidterm=midterm;
					  civicsexam=exam;
					  civicswastan=wastan;
					  positioncivis=subjectposition;
					  katiyacivics=subjectjumla;
					  gradecivics=alamacoz;
					  uzitocivics=unit;
				  }else if(coz.equals("013")){
					  geogmazoezi=maoezi;
					  geogmidterm=midterm;
					  geogexam=exam;
					  geogwastan=wastan;
					  positiongeog=subjectposition;
					  katiyageog=subjectjumla;
					  gradegeog=alamacoz;
					  uzitogeog=unit;
				  }else if(coz.equals("012")){
					  histmazoezi=maoezi;
					  histmidterm=midterm;
					  histexam=exam;
					  histwastan=wastan;
					  positionhist=subjectposition;
					  katiyahist=subjectjumla;
					  gradehist=alamacoz;
					  uzitohist=unit;
				  }else if(coz.equals("031")){
					  phymazoezi=maoezi;
					  phymidterm=midterm;
					  phyexam=exam;
					  phytwastan=wastan;
					  positionphy=subjectposition;
					  katiyaphy=subjectjumla;
					  gradephy=alamacoz;
					  uzitophy=unit;
				  }else if(coz.equals("021")){
					  kiswmazoezi=maoezi;
					  kiswmidterm=midterm;
					  kiswexam=exam;
					  kiswwastan=wastan;
					  positionkisw=subjectposition;
					  katiyakisw=subjectjumla;
					  gradekisw=alamacoz;
					  uzitokiswa=unit;
				  }else if(coz.equals("022")){
					  engmazoezi=maoezi;
					  engmidterm=midterm;
					  engexam=exam;
					  engwastan=wastan;
					  positioneng=subjectposition;
					  katiyaeng=subjectjumla;
					  gradeeng=alamacoz;
					  uzitoeng=unit;
				  }else if(coz.equals("036")){
					  compmazoezi=maoezi;
					  compmidterm=midterm;
					  compexam=exam;
					  compwastan=wastan;
					  positioncomp=subjectposition;
					  katiyacomp=subjectjumla;
					  gradecomp=alamacoz;
					  uzitocomp=unit;
				  }else if(coz.equals("032")){
					  chemmazoezi=maoezi;
					  chemmidterm=midterm;
					  chemexam=exam;
					  chemwastan=wastan;
					  positionchem=subjectposition;
					  katiyachem=subjectjumla;
					  gradechem=alamacoz;
					  uzitochem=unit;
				  }else if(coz.equals("041")){
					  mathmazoezi=maoezi;
					  mathmidterm=midterm;
					  mathexam=exam;
					  mathwastan=wastan;
					  positionmath=subjectposition;
					  katiyamath=subjectjumla;
					  grademath=alamacoz;
					  uzitomath=unit;
				  }else if(coz.equals("033")){
					  biosmazoezi=maoezi;
					  biosmidterm=midterm;
					  biosexam=exam;
					  bioswastan=wastan;
					  positionbios=subjectposition;
					  katiyabios=subjectjumla;
					  gradebios=alamacoz;
					  uzitobiosi=unit;
				  }else if(coz.equals("053")){
					  uhasibumazoezi=maoezi;
					  uhasibumidterm=midterm;
					  uhasibuexam=exam;
					  uhasibuwastan=wastan;
					  positionuhasibu=subjectposition;
					  katiyauhasibu=subjectjumla;
					  gradeuhasibu=alamacoz;
					  uzitouhasibuu=unit;
				  }
				  else if(coz.equals("052")){
					  biasharamazoezi=maoezi;
					  biasharamidterm=midterm;
					  biasharaexam=exam;
					  biasharawastan=wastan;
					  positionbiashara=subjectposition;
					  katiyabiashara=subjectjumla;
					  gradebiashara=alamacoz;
					  uzitobiasharaa=unit;
				  }else if(coz.equals("222")){
					  lengmazoezi=maoezi;
					  lengmidterm=midterm;
					  lengexam=exam;
					  lengwastan=wastan;
					  positionleng=subjectposition;
					  katiyaleng=subjectjumla;
					  gradeleng=alamacoz;
					  uzitoleng=unit;
				  }else if(coz.equals("014")){
					  biblemazoezi=maoezi;
					  biblemidterm=midterm;
					  bibleexam=exam;
					  biblewastan=wastan;
					  positionbible=subjectposition;
					  katiyabible=subjectjumla;
					  gradebible=alamacoz;
					  uzitobiblee=unit;
				  }else if(coz.equals("015")){
					  islamicmazoezi=maoezi;
					  islamicmidterm=midterm;
					  islamicexam=exam;
					  islamicwastan=wastan;
					  positionislamic=subjectposition;
					  katiyaislamic=subjectjumla;
					  gradeislamic=alamacoz;
					  uzitoislamicc=unit;
				  }else if(coz.equals("051")){
					  foodmazoezi=maoezi;
					  foodmidterm=midterm;
					  foodexam=exam;
					  foodwastan=wastan;
					  positionfood=subjectposition;
					  katiyafood=subjectjumla;
					  gradefood=alamacoz;
					  uzitofood=unit;
				  }
				  
				  
					  
				  
		       }
			 if(results.size()>=7){
		    	   
		    	  List<Integer> head = results.subList(0, 7);
		    	  
		    	  int sum = 0;
		    	  for(int h=0; h<head.size(); h++){
		    		    sum += head.get(h);
		    		}
                   
		    	    
		    	  if(sum>=7&&sum<=17){
               	   divsion="DIVISION I   Point "+sum;
                  }else if(sum>=18&&sum<=21){
               	   divsion="DIVISION II  Point "+sum;
                  }else if(sum>=22&&sum<=25){
               	   divsion="DIVISION III  Point "+sum;
                  }else if(sum>=26&&sum<=33){
               	   divsion="DIVISION IV  Point "+sum;
                  }else if(sum>=34&&sum<=35){
               	   divsion="DIVISION 0  Point "+sum;
                  }
                
		      }else if(results.size()<7){
		    	  divsion="DIVISION Incomplete results";
		      }
	       
			 
			 String std0="SET @row_number=0";
			    String std= " SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher WHERE year='"+mwaka+"' and "+mikondo+" and type='"+examinationtype+"' group by stu_id )as tablewastan order by wastani DESC) AS MM WHERE stu_id='"+ID+"'";
			         st.execute(std0);
			       rs=st.executeQuery(std);
			       
			       while(rs.next()){
			    	   String wab=rs.getString("wastani");
			    	    
			    	    position=rs.getString("ROW");
			    	    double wast2=Double.parseDouble(wab);
			    	    double majid=Math.round(wast2*100.0)/100.0;
			    	    averagejumla=""+majid;
						  if(wast2>=74.5){
							  darajawastani="A";
							  
						  }else if(wast2<=29.5){
							  darajawastani="F";
							  
						  }
						  else if(wast2<74.5&&wast2>=64.5){
							  darajawastani="B";
							  
						  } else if(wast2<64.5&&wast2>=44.5){
							  darajawastani="C";
							  
						  }
						  else if(wast2<44.5&&wast2>=29.5){
							  darajawastani="D";
							  
						  }
						 
			       }
			       String std2="SET @row_number=0";
				    String std3= " SELECT MAX(ROW) FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher WHERE year='"+mwaka+"' and "+mikondo+" and type='"+examinationtype+"' group by stu_id)as tablewastan  order by wastani DESC) AS MM ";
				         st.execute(std2);
				       rs=st.executeQuery(std3);
				       
				       while(rs.next()){
				    	    jumlastudent=rs.getString("MAX(ROW)");
				    	    
				    	   }
				String tbia="Select avg(bidii),avg(ubora),avg(ari),avg(utunzaji),avg(ushirikiano),avg(heshima),avg(uongozi),avg(utii),avg(usafi),avg(uaminifu),avg(tamaduni),avg(kujiamini) FROM tabia WHERE stu_id='"+ID+"' AND class='"+clssname+"' AND year='"+mwaka+"' AND type='"+examinationtype+"'";
					      rs=st.executeQuery(tbia);
					      try{
						      rs=st.executeQuery(tbia);
						      while(rs.next()){
						    	  tabia();
						      }
						      }catch(Exception a){
						    	  
						      }
		}
		public void PDFreportadvance() throws DocumentException, SQLException, MalformedURLException, IOException{
			

			GregorianCalendar date = new GregorianCalendar();
			PdfPCell division;
			 
		    int     day = date.get(Calendar.DAY_OF_MONTH);
		    int  month = date.get(Calendar.MONTH);
		    int  year = date.get(Calendar.YEAR);
			
		    String dte=""+day+"/"+(month+1)+"/"+year;
		    String sculname = null,adress = null,location = null,contact = null,contact2=null;
		    
		   String schol="SELECT*FROM school_details";
		   rs=st.executeQuery(schol);
		    while(rs.next()){
			   sculname=rs.getString("title");
			    adress=rs.getString("address");
			   location=rs.getString("location");
			   contact=rs.getString("contact");
			   contact2=rs.getString("contact2");
			  
			   
		    }
		    String staff="SELECT  firstName,lastName FROM staffs where status LIKE '%"+clssname+"%' ";
 			 rs=st.executeQuery(staff);
 			if(rs.next()){
 				 String finame=rs.getString("firstName");
 				String laname=rs.getString("lastName");
 				classteacher=finame+ " "+ laname;
 			}else{
 				classteacher="Teacher";
 			}

 			 String mkuusign="SELECT sign from head_sign";
             
             
		       rs=st.executeQuery(mkuusign);
		       while(rs.next()){
		    	   java.sql.Blob imageBlob = rs.getBlob("sign");
		    	   byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
		    	  Image image = Image.getInstance(imageBytes);
		    	   image.scaleAbsolute(50f, 15f);
		           image.setAbsolutePosition(272, 203);
		           
		           document.add(image);
		       }
			
		    Phrase janalashule = new Phrase(sculname, SCNAME);
		    PdfContentByte scullname = writer.getDirectContent();
			ColumnText.showTextAligned(scullname, Element.ALIGN_CENTER, janalashule, 300, 810, 0);
			
			 Phrase pobox = new Phrase(adress+"-"+location+", TANZANIA", boldFontmkuu);
			 PdfContentByte box = writer.getDirectContent();
			 ColumnText.showTextAligned(box, Element.ALIGN_CENTER, pobox, 300, 800, 0);
			 
			 Phrase simu = new Phrase("Mobile: " +contact+" / "+contact2, phonenumba);
			 PdfContentByte mobile = writer.getDirectContent();
			 ColumnText.showTextAligned(mobile, Element.ALIGN_CENTER, simu, 300, 790, 0);
		    
			 Phrase tarrifa = new Phrase("TAARIFA YA MAENDELEO YA MWANAFUNZI KWA MZAZI", boldMzazi);
			 PdfContentByte taarifa = writer.getDirectContent();
			 ColumnText.showTextAligned(taarifa, Element.ALIGN_CENTER, tarrifa, 300, 775, 0);
			 document.add(new Paragraph(" "+"\n"));
			 
		     document.add(new Paragraph("Jina la Mwanafunzi "+fullname+                  " Kidato  "+studentclass+           " Muhula "+muhula+"   "));
		     document.add(new Paragraph("Jina la Mwalimu wa Darasa "+classteacher+                   " Tarehe "+dte+"           "));
		     document.add(new Paragraph("                                                                             MASOMO", boldMzazi));
		     
		     try{
		    	st4=con.createStatement();
		    	String mkuuu="Select *FROM staffs WHERE status='HEAD OF SCHOOL'" ;
		    	rs4=st4.executeQuery(mkuuu);
		    	while(rs4.next()){
		    		String fname=rs4.getString("firstName");
		    		String mname=rs4.getString("middleName");
		    		String lname=rs4.getString("lastName");
		    		mkuuwashule=lname+", "+fname.charAt(0)+"."+mname.charAt(0);
		    		
		    	}
		    	
		    	  photoo="SELECT photo from students where student_id='"+ID+"' ";
		          
		          
			       rs=st.executeQuery(photoo);
			       while(rs.next()){
			    	   java.sql.Blob imageBlob = rs.getBlob("photo");
			    	   byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
			    	   imagephoto = Image.getInstance(imageBytes);
			    	   imagephoto.scaleAbsolute(75f, 75f);
			    	   imagephoto.setAbsolutePosition(470, 765);
			           document.add(imagephoto);
			           
			       }
			       
		    	
		     }catch(Exception a){
		    	 
		     }
		  String log="SELECT logo from school_details";
	          
	          
		       rs=st.executeQuery(log);
		       while(rs.next()){
		    	   java.sql.Blob imageBlob = rs.getBlob("logo");
		    	   byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
		    	Image   imagel = Image.getInstance(imageBytes);
		    	imagel.scaleAbsolute(75f, 75f);
		    	imagel.setAbsolutePosition(50, 765);
		           document.add(imagel);
		           
		       }
		       
	    	
	    
		     
		     Phrase mkuuline = new Phrase("________________", boldFontmkuu);
		     PdfContentByte canvas76 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas76, Element.ALIGN_CENTER, mkuuline, 300, 205, 0);
		     
		     Phrase mkuushule = new Phrase(mkuuwashule, boldFontmkuu);
		     PdfContentByte canvas4 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas4, Element.ALIGN_CENTER, mkuushule, 300, 190, 0);
		     
			 Phrase mkuushule6 = new Phrase("MKUU WA SHULE", boldMzazi);
		     PdfContentByte canvas5 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas5, Element.ALIGN_CENTER, mkuushule6, 300, 175, 0);
		     
		     Phrase phrase = new Phrase(dte, boldMzazi);
		     PdfContentByte canvas = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase, 300, 160, 0);
			 
			 Phrase mzazimlezi = new Phrase("MAONI YA MZAZI/MLEZI KUHUSU TAARIFA YA MAENDELEO YA MWANAFUNZI", boldFontmkuu);
		     PdfContentByte canvas6 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas6, Element.ALIGN_CENTER, mzazimlezi, 200, 120, 0);
			 
			 Phrase line8 = new Phrase("-----------------------------------------------------------------------------------------kata hapa-------------------------------------------------------------------------------", boldFontmkuu);
		     PdfContentByte canvas13 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas13, Element.ALIGN_CENTER, line8, 285, 140, 0);
			 
			 Phrase line1 = new Phrase("..............................................................................................................................................................................................................................................", boldFontmkuu);
		     PdfContentByte canvas7 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas7, Element.ALIGN_CENTER, line1, 285, 100, 0);
			 Phrase line2 = new Phrase("..............................................................................................................................................................................................................................................", boldFontmkuu);
		     PdfContentByte canvas8 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas8, Element.ALIGN_CENTER, line2, 285, 90, 0);
			 
			 Phrase line3 = new Phrase("Sahihi ya Mzazi/mlezi:  .................................................", boldFontmkuu);
		     PdfContentByte canvas9 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas9, Element.ALIGN_CENTER, line3, 135, 65, 0);
			 
			 Phrase line4 = new Phrase("Jina la Mzazi/mlezi:     ....................................................", boldFontmkuu);
		     PdfContentByte canvas10 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas10, Element.ALIGN_CENTER, line4, 135, 50, 0);
			 
			 Phrase line5 = new Phrase("Namba ya Simu:          ....................................................", boldFontmkuu);
		     PdfContentByte canvas11 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas11, Element.ALIGN_CENTER, line5, 135, 35, 0);
			 
			 Phrase line6 = new Phrase("Tarehe:                         ....................................................", boldFontmkuu);
		     PdfContentByte canvas12 = writer.getDirectContent();
			 ColumnText.showTextAligned(canvas12, Element.ALIGN_CENTER, line6, 135, 20, 0);
			 
		     
		  
		      masomo = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		      masomo.setWidthPercentage(100);
		     PdfPCell somo = new PdfPCell(new Phrase("MASOMO", boldFonti));
		     somo.setColspan(1);
		     PdfPCell zoezi = new PdfPCell(new Phrase("MAZOEZI", boldFonti));
		     zoezi.setColspan(1);
		     PdfPCell midtem = new PdfPCell(new Phrase("MIDTERM", boldFonti));
		     midtem.setColspan(1);
		     PdfPCell annual = new PdfPCell(new Phrase(examtype, boldFonti));
		     annual.setColspan(1);
		     PdfPCell average = new PdfPCell(new Phrase("WASTANI", boldFonti));
		     average.setColspan(1);
		     PdfPCell nafasi = new PdfPCell(new Phrase("NAFASI", boldFonti));
		     nafasi.setColspan(1);
		     PdfPCell kati = new PdfPCell(new Phrase("KATI YA", boldFonti));
		     kati.setColspan(1);
		     PdfPCell alama = new PdfPCell(new Phrase("ALAMA", boldFonti));
		     alama.setColspan(1);
		     PdfPCell uzito = new PdfPCell(new Phrase("UZITO WA ALAMA", boldFonti));
		     uzito.setColspan(1);
		    
		     fmu1 = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1}); 
		     fmu1.setWidthPercentage(100);
		     PdfPCell uraia = new PdfPCell(new Phrase("Uraia", boldFont));
		     uraia.setColspan(1);
		     PdfPCell zoeziMax = new PdfPCell(new Phrase(uraiamazoezi, boldFont));
		     zoeziMax.setColspan(1);
		     PdfPCell midtemMax = new PdfPCell(new Phrase(uraiamidterm, boldFont));
		     midtemMax.setColspan(1);
		     PdfPCell annualMax = new PdfPCell(new Phrase(uraiaexam, boldFont));
		     annualMax.setColspan(1);
		     PdfPCell averageUraia = new PdfPCell(new Phrase(uraiawastan, boldFont));
		     averageUraia.setColspan(1);
		     PdfPCell nafasiUraia = new PdfPCell(new Phrase(positionuraia, boldFont));
		     nafasiUraia.setColspan(1);
		     PdfPCell katiUraia = new PdfPCell(new Phrase(katiyauraia, boldFont));
		     katiUraia.setColspan(1);
		     PdfPCell alamaUraia = new PdfPCell(new Phrase(gradeuraia, boldFont));
		     alamaUraia.setColspan(1);
		     PdfPCell uzitoUraia = new PdfPCell(new Phrase(uzitouraia, boldFont));
		     uzitoUraia.setColspan(1);
		     
		    
		     fmu1.addCell(uraia);
		     fmu1.addCell(zoeziMax);
		     fmu1.addCell(midtemMax);
		     fmu1.addCell(annualMax);
		     fmu1.addCell(averageUraia);
		     fmu1.addCell(nafasiUraia);
		     fmu1.addCell(katiUraia);
		     fmu1.addCell(alamaUraia);
		     fmu1.addCell(uzitoUraia);
		    
		     
			 	
		     fmuJiog = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		     fmuJiog .setWidthPercentage(100);
		     PdfPCell jiografia = new PdfPCell(new Phrase("Jiografia", boldFont));
		     jiografia.setColspan(1);
		     PdfPCell zoeziJiog = new PdfPCell(new Phrase(Ageogmazoezi, boldFont));
		     zoeziJiog.setColspan(1);
		     PdfPCell midtemJiog = new PdfPCell(new Phrase(Ageogmidterm, boldFont));
		     midtemJiog.setColspan(1);
		     PdfPCell annualJiog = new PdfPCell(new Phrase(Ageogexam, boldFont));
		     annualJiog.setColspan(1);
		     PdfPCell averageJiog = new PdfPCell(new Phrase(Ageogwastan, boldFont));
		     averageJiog.setColspan(1);
		     PdfPCell nafasiJiog = new PdfPCell(new Phrase(positionAgeog, boldFont));
		     nafasiJiog.setColspan(1);
		     PdfPCell katiJiog = new PdfPCell(new Phrase(katiyaAgeog, boldFont));
		     katiJiog.setColspan(1);
		     PdfPCell alamaJiog = new PdfPCell(new Phrase(gradeAgeog, boldFont));
		     alamaJiog.setColspan(1);
		     PdfPCell uzitoJiog = new PdfPCell(new Phrase(uzitoAgeog, boldFont));
		     uzitoJiog.setColspan(1);
		     
		     
		     
		     fmuJiog.addCell(jiografia);
		     fmuJiog.addCell(zoeziJiog);
		     fmuJiog.addCell(midtemJiog);
		     fmuJiog.addCell(annualJiog);
		     fmuJiog.addCell(averageJiog);
		     fmuJiog.addCell(nafasiJiog);
		     fmuJiog.addCell(katiJiog);
		     fmuJiog.addCell(alamaJiog);
		     fmuJiog.addCell(uzitoJiog);
		     
		      fmuHis = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		      fmuHis.setWidthPercentage(100); 
		    
		     PdfPCell historia = new PdfPCell(new Phrase("Historia", boldFont));
		     historia.setColspan(1);
		     PdfPCell zoeziHis = new PdfPCell(new Phrase(Ahistmazoezi, boldFont));
		     zoeziHis.setColspan(1);
		     PdfPCell midtemHis = new PdfPCell(new Phrase(Ahistmidterm, boldFont));
		     midtemHis.setColspan(1);
		     PdfPCell annualHis = new PdfPCell(new Phrase(Ahistexam, boldFont));
		     annualHis.setColspan(1);
		     PdfPCell averageHis = new PdfPCell(new Phrase(Ahistwastan, boldFont));
		     averageHis.setColspan(1);
		     PdfPCell nafasiHis = new PdfPCell(new Phrase(positionAhist, boldFont));
		     nafasiHis.setColspan(1);
		     PdfPCell katiHis = new PdfPCell(new Phrase(katiyaAhist, boldFont));
		     katiHis.setColspan(1);
		     PdfPCell alamaHis = new PdfPCell(new Phrase(gradeAhist, boldFont));
		     alamaHis.setColspan(1);
		     PdfPCell uzitoHis = new PdfPCell(new Phrase(uzitoAhist, boldFont));
		     uzitoHis.setColspan(1);
		     
		     fmuHis.addCell(historia);
		     fmuHis.addCell(zoeziHis);
		     fmuHis.addCell(midtemHis);
		     fmuHis.addCell(annualHis);
		     fmuHis.addCell(averageHis);
		     fmuHis.addCell(nafasiHis);
		     fmuHis.addCell(katiHis);
		     fmuHis.addCell(alamaHis);
		     fmuHis.addCell(uzitoHis);
		     
		      fmuKisw = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		      fmuKisw.setWidthPercentage(100);
		     PdfPCell kiswahili = new PdfPCell(new Phrase("Kiswahili", boldFont));
		     kiswahili.setColspan(1);
		    PdfPCell zoezikisw = new PdfPCell(new Phrase(Akiswmazoezi, boldFont));
		     zoezikisw.setColspan(1);
		     PdfPCell midtemkisw = new PdfPCell(new Phrase(Akiswmidterm, boldFont));
		     midtemkisw.setColspan(1);
		     PdfPCell annualkisw = new PdfPCell(new Phrase(Akiswexam, boldFont));
		     annualkisw.setColspan(1);
		     PdfPCell averagekisw = new PdfPCell(new Phrase(Akiswwastan, boldFont));
		     averagekisw.setColspan(1);
		     PdfPCell nafasikisw = new PdfPCell(new Phrase(positionAkisw, boldFont));
		     nafasikisw.setColspan(1);
		     PdfPCell katikisw = new PdfPCell(new Phrase(katiyaAkisw, boldFont));
		     katikisw.setColspan(1);
		     PdfPCell alamakisw = new PdfPCell(new Phrase(gradeAkisw, boldFont));
		     alamakisw.setColspan(1);
		     PdfPCell uzitokisw = new PdfPCell(new Phrase(uzitoAkiswa, boldFont));
		     uzitokisw.setColspan(1);
		     
		     
		     fmuKisw.addCell(kiswahili);
		     fmuKisw.addCell(zoezikisw);
		     fmuKisw.addCell(midtemkisw);
		     fmuKisw.addCell(annualkisw);
		     fmuKisw.addCell(averagekisw);
		     fmuKisw.addCell(nafasikisw);
		     fmuKisw.addCell(katikisw);
		     fmuKisw.addCell(alamakisw);
		     fmuKisw.addCell(uzitokisw);
		     
		   
		     
		      fmuEng = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		      fmuEng.setWidthPercentage(100);
		     PdfPCell english = new PdfPCell(new Phrase("kiingereza", boldFont));
		     english.setColspan(1);
		    PdfPCell zoeziEng = new PdfPCell(new Phrase(Aengmazoezi, boldFont));
		     zoeziEng.setColspan(1);
		     PdfPCell midtemEng = new PdfPCell(new Phrase(Aengmidterm, boldFont));
		     midtemEng.setColspan(1);
		     PdfPCell annualEng = new PdfPCell(new Phrase(Aengexam, boldFont));
		     annualEng.setColspan(1);
		     PdfPCell averageEng = new PdfPCell(new Phrase(Aengwastan, boldFont));
		     averageEng.setColspan(1);
		     PdfPCell nafasiEng = new PdfPCell(new Phrase(positionAeng, boldFont));
		     nafasiEng.setColspan(1);
		     PdfPCell katiEng = new PdfPCell(new Phrase(katiyaAeng, boldFont));
		     katiEng.setColspan(1);
		     PdfPCell alamaEng = new PdfPCell(new Phrase(gradeAeng, boldFont));
		     alamaEng.setColspan(1);
		     PdfPCell uzitoEng = new PdfPCell(new Phrase(uzitoAeng, boldFont));
		     uzitoEng.setColspan(1);
		     
		     
		     fmuEng.addCell(english);
		     fmuEng.addCell(zoeziEng);
		     fmuEng.addCell(midtemEng);
		     fmuEng.addCell(annualEng);
		     fmuEng.addCell(averageEng);
		     fmuEng.addCell(nafasiEng);
		     fmuEng.addCell(katiEng);
		     fmuEng.addCell(alamaEng);
		     fmuEng.addCell(uzitoEng);
		    
		
		      fmuPhy = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		      fmuPhy.setWidthPercentage(100);
		     PdfPCell Fizikia = new PdfPCell(new Phrase("Fizikia", boldFont));
		     Fizikia.setColspan(1);
		    PdfPCell zoeziFiz = new PdfPCell(new Phrase(Aphymazoezi, boldFont));
		     zoeziFiz.setColspan(1);
		     PdfPCell midtemFiz = new PdfPCell(new Phrase(Aphymidterm, boldFont));
		     midtemFiz.setColspan(1);
		     PdfPCell annualFiz = new PdfPCell(new Phrase(Aphyexam, boldFont));
		     annualFiz.setColspan(1);
		     PdfPCell averageFiz = new PdfPCell(new Phrase(Aphytwastan, boldFont));
		     averageFiz.setColspan(1);
		     PdfPCell nafasiFiz = new PdfPCell(new Phrase(positionAphy, boldFont));
		     nafasiFiz.setColspan(1);
		     PdfPCell katiFiz = new PdfPCell(new Phrase(katiyaAphy, boldFont));
		     katiFiz.setColspan(1);
		     PdfPCell alamaFiz = new PdfPCell(new Phrase(gradeAphy, boldFont));
		     alamaFiz.setColspan(1);
		     PdfPCell uzitoFiz = new PdfPCell(new Phrase(uzitoAphy, boldFont));
		     uzitoFiz.setColspan(1);
		    
		     
		     fmuPhy.addCell(Fizikia);
		     fmuPhy.addCell(zoeziFiz);
		     fmuPhy.addCell(midtemFiz);
		     fmuPhy.addCell(annualFiz);
		     fmuPhy.addCell(averageFiz);
		     fmuPhy.addCell(nafasiFiz);
		     fmuPhy.addCell(katiFiz);
		     fmuPhy.addCell(alamaFiz);
		     fmuPhy.addCell(uzitoFiz);
		   
		     
		      fmuChem = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		      fmuChem.setWidthPercentage(100);
		     PdfPCell chemia = new PdfPCell(new Phrase("Kemia", boldFont));
		     chemia.setColspan(1);
		    PdfPCell zoezichemia = new PdfPCell(new Phrase(Achemmazoezi, boldFont));
		     zoezichemia.setColspan(1);
		     PdfPCell midtemchemia = new PdfPCell(new Phrase(Achemmidterm, boldFont));
		     midtemchemia.setColspan(1);
		     PdfPCell annualchemia = new PdfPCell(new Phrase(Achemexam, boldFont));
		     annualchemia.setColspan(1);
		     PdfPCell averagechemia = new PdfPCell(new Phrase(Achemwastan, boldFont));
		     averagechemia.setColspan(1);
		     PdfPCell nafasichemia = new PdfPCell(new Phrase(positionAchem, boldFont));
		     nafasichemia.setColspan(1);
		     PdfPCell katichemia = new PdfPCell(new Phrase(katiyaAchem, boldFont));
		     katichemia.setColspan(1);
		     PdfPCell alamachemia = new PdfPCell(new Phrase(gradeAchem, boldFont));
		     alamachemia.setColspan(1);
		     PdfPCell uzitochemia = new PdfPCell(new Phrase(uzitoAchem, boldFont));
		     uzitochemia.setColspan(1);
		    
		     
		     fmuChem.addCell(chemia);
		     fmuChem.addCell(zoezichemia);
		     fmuChem.addCell(midtemchemia);
		     fmuChem.addCell(annualchemia);
		     fmuChem.addCell(averagechemia);
		     fmuChem.addCell(nafasichemia);
		     fmuChem.addCell(katichemia);
		     fmuChem.addCell(alamachemia);
		     fmuChem.addCell(uzitochemia);
		    
		   
		     fmuBios = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		     fmuBios.setWidthPercentage(100);
		     PdfPCell bios = new PdfPCell(new Phrase("Baiolojia", boldFont));
		     bios.setColspan(1);
		    PdfPCell zoezibios = new PdfPCell(new Phrase(Abiosmazoezi, boldFont));
		     zoezibios.setColspan(1);
		     PdfPCell midtembios = new PdfPCell(new Phrase(Abiosmidterm, boldFont));
		     midtembios.setColspan(1);
		     PdfPCell annualbios = new PdfPCell(new Phrase(Abiosexam, boldFont));
		     annualbios.setColspan(1);
		     PdfPCell averagebios = new PdfPCell(new Phrase(Abioswastan, boldFont));
		     averagebios.setColspan(1);
		     PdfPCell nafasibios = new PdfPCell(new Phrase(positionAbios, boldFont));
		     nafasibios.setColspan(1);
		     PdfPCell katibios = new PdfPCell(new Phrase(katiyaAbios, boldFont));
		     katibios.setColspan(1);
		     PdfPCell alamabios = new PdfPCell(new Phrase(gradeAbios, boldFont));
		     alamabios.setColspan(1);
		     PdfPCell uzitobios = new PdfPCell(new Phrase(uzitoAbiosi, boldFont));
		     uzitobios.setColspan(1);
		     
		     
		     fmuBios.addCell(bios);
		     fmuBios.addCell(zoezibios);
		     fmuBios.addCell(midtembios);
		     fmuBios.addCell(annualbios);
		     fmuBios.addCell(averagebios);
		     fmuBios.addCell(nafasibios);
		     fmuBios.addCell(katibios);
		     fmuBios.addCell(alamabios);
		     fmuBios.addCell(uzitobios);
		     
		     
		     fmuMath = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		     fmuMath.setWidthPercentage(100);
		     PdfPCell hisabati = new PdfPCell(new Phrase("Hesabu", boldFont));
		     hisabati.setColspan(1);
		    PdfPCell zoeziMath = new PdfPCell(new Phrase(Amathmazoezi, boldFont));
		     zoeziMath.setColspan(1);
		     PdfPCell midtemMath = new PdfPCell(new Phrase(Amathmidterm, boldFont));
		     midtemMath.setColspan(1);
		     PdfPCell annualMath = new PdfPCell(new Phrase(Amathexam, boldFont));
		     annualMath.setColspan(1);
		     PdfPCell averageMath = new PdfPCell(new Phrase(Amathwastan, boldFont));
		     averageMath.setColspan(1);
		     PdfPCell nafasiMath = new PdfPCell(new Phrase(positionAmath, boldFont));
		     nafasiMath.setColspan(1);
		     PdfPCell katiMath = new PdfPCell(new Phrase(katiyaAmath, boldFont));
		     katiMath.setColspan(1);
		     PdfPCell alamaMath = new PdfPCell(new Phrase(gradeAmath, boldFont));
		     alamaMath.setColspan(1);
		     PdfPCell uzitoMath = new PdfPCell(new Phrase(uzitoAmath, boldFont));
		     uzitoMath.setColspan(1);
		    
		     
		     fmuMath.addCell(hisabati);
		     fmuMath.addCell(zoeziMath);
		     fmuMath.addCell(midtemMath);
		     fmuMath.addCell(annualMath);
		     fmuMath.addCell(averageMath);
		     fmuMath.addCell(nafasiMath);
		     fmuMath.addCell(katiMath);
		     fmuMath.addCell(alamaMath);
		     fmuMath.addCell(uzitoMath);
		     
		     BAM = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		     BAM.setWidthPercentage(100);
		     PdfPCell bam = new PdfPCell(new Phrase("Hesabu", boldFont));
		     bam.setColspan(1);
		     PdfPCell zoezibam = new PdfPCell(new Phrase(bammazoezi, boldFont));
		     zoezibam.setColspan(1);
		     PdfPCell midtembam = new PdfPCell(new Phrase(bammidterm, boldFont));
		     midtembam.setColspan(1);
		     PdfPCell annualbam = new PdfPCell(new Phrase(bamexam, boldFont));
		     annualbam.setColspan(1);
		     PdfPCell averagebam = new PdfPCell(new Phrase(bamwastan, boldFont));
		     averagebam.setColspan(1);
		     PdfPCell nafasibam = new PdfPCell(new Phrase(positionbam, boldFont));
		     nafasibam.setColspan(1);
		     PdfPCell katibam = new PdfPCell(new Phrase(katiyabam, boldFont));
		     katibam.setColspan(1);
		     PdfPCell alamabam = new PdfPCell(new Phrase(gradebam, boldFont));
		     alamabam.setColspan(1);
		     PdfPCell uzitobam = new PdfPCell(new Phrase(uzitobamu, boldFont));
		     uzitobam.setColspan(1);
		    
		     
		     BAM.addCell(bam);
		     BAM.addCell(zoezibam);
		     BAM.addCell(midtembam);
		     BAM.addCell(annualbam);
		     BAM.addCell(averagebam);
		     BAM.addCell(nafasibam);
		     BAM.addCell(katibam);
		     BAM.addCell(alamabam);
		     BAM.addCell(uzitobam);
		    
		     
		     
		    
		     
		     fmuuhasibu = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		     fmuuhasibu.setWidthPercentage(100);
		     PdfPCell uhasibu = new PdfPCell(new Phrase("Uhasibu", boldFont));
		     uhasibu.setColspan(1);
		     PdfPCell zoeziuhasibu = new PdfPCell(new Phrase(Auhasibumazoezi, boldFont));
		     zoeziuhasibu.setColspan(1);
		     PdfPCell midtemuhasibu = new PdfPCell(new Phrase(Auhasibumidterm, boldFont));
		     midtemuhasibu.setColspan(1);
		     PdfPCell annualuhasibu = new PdfPCell(new Phrase(Auhasibuexam, boldFont));
		     annualuhasibu.setColspan(1);
		     PdfPCell averageuhasibu = new PdfPCell(new Phrase(Auhasibuwastan, boldFont));
		     averageuhasibu.setColspan(1);
		     PdfPCell nafasiuhasibu = new PdfPCell(new Phrase(positionAuhasibu, boldFont));
		     nafasiuhasibu.setColspan(1);
		     PdfPCell katiuhasibu = new PdfPCell(new Phrase(katiyaAuhasibu, boldFont));
		     katiuhasibu.setColspan(1);
		     PdfPCell alamauhasibu = new PdfPCell(new Phrase(gradeAuhasibu, boldFont));
		     alamauhasibu.setColspan(1);
		     PdfPCell uzitouhasibu = new PdfPCell(new Phrase(uzitoAuhasibuu, boldFont));
		     uzitouhasibu.setColspan(1);
		   
		     

		   
		     fmuCommerce = new PdfPTable(new float[] { 1,1,1,1,1,1,1,1,1});
		     fmuCommerce.setWidthPercentage(100);
		     PdfPCell biashara = new PdfPCell(new Phrase("Biashara", boldFont));
		     biashara.setColspan(1);
		     PdfPCell zoezibiashara = new PdfPCell(new Phrase(Abiasharamazoezi, boldFont));
		     zoezibiashara.setColspan(1);
		     PdfPCell midtembiashara = new PdfPCell(new Phrase(Abiasharamidterm, boldFont));
		     midtembiashara.setColspan(1);
		     PdfPCell annualbiashara = new PdfPCell(new Phrase(Abiasharaexam, boldFont));
		     annualbiashara.setColspan(1);
		     PdfPCell averagebiashara = new PdfPCell(new Phrase(Abiasharawastan, boldFont));
		     averagebiashara.setColspan(1);
		     PdfPCell nafasibiashara = new PdfPCell(new Phrase(positionAbiashara, boldFont));
		     nafasibiashara.setColspan(1);
		     PdfPCell katibiashara = new PdfPCell(new Phrase(katiyaAbiashara, boldFont));
		     katibiashara.setColspan(1);
		     PdfPCell alamabiashara = new PdfPCell(new Phrase(gradeAbiashara, boldFont));
		     alamabiashara.setColspan(1);
		     PdfPCell uzitobiashara = new PdfPCell(new Phrase(uzitoAbiasharaa, boldFont));
		     uzitobiashara.setColspan(1);
		    
		    
		     fmuCommerce.addCell(biashara);
		     fmuCommerce.addCell(zoezibiashara);
		     fmuCommerce.addCell(midtembiashara);
		     fmuCommerce.addCell(annualbiashara);
		     fmuCommerce.addCell(averagebiashara);
		     fmuCommerce.addCell(nafasibiashara);
		     fmuCommerce.addCell(katibiashara);
		     fmuCommerce.addCell(alamabiashara);
		     fmuCommerce.addCell(uzitobiashara);
		 	   
		 	 fmuuhasibu.addCell(uhasibu);
		     fmuuhasibu.addCell(zoeziuhasibu);
		     fmuuhasibu.addCell(midtemuhasibu);
		     fmuuhasibu.addCell(annualuhasibu);
		     fmuuhasibu.addCell(averageuhasibu);
		     fmuuhasibu.addCell(nafasiuhasibu);
		     fmuuhasibu.addCell(katiuhasibu);
		     fmuuhasibu.addCell(alamauhasibu);
		     fmuuhasibu.addCell(uzitouhasibu);
		   
		     
		    
		    
		     
		    
		     UCHUMI = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		     UCHUMI.setWidthPercentage(100);
		     PdfPCell islamic = new PdfPCell(new Phrase("Uchumi", boldFont));
		     islamic.setColspan(1);
		     PdfPCell zoeziislamic = new PdfPCell(new Phrase(UCHUMImazoezi, boldFont));
		     zoeziislamic.setColspan(1);
		     PdfPCell midtemislamic = new PdfPCell(new Phrase(UCHUMImidterm, boldFont));
		     midtemislamic.setColspan(1);
		     PdfPCell annualislamic = new PdfPCell(new Phrase(UCHUMIexam, boldFont));
		     annualislamic.setColspan(1);
		     PdfPCell averageislamic = new PdfPCell(new Phrase(UCHUMIwastan, boldFont));
		     averageislamic.setColspan(1);
		     PdfPCell nafasiislamic = new PdfPCell(new Phrase(positionUCHUMI, boldFont));
		     nafasiislamic.setColspan(1);
		     PdfPCell katiislamic = new PdfPCell(new Phrase(katiyaUCHUMI, boldFont));
		     katiislamic.setColspan(1);
		     PdfPCell alamaislamic = new PdfPCell(new Phrase(gradeUCHUMI, boldFont));
		     alamaislamic.setColspan(1);
		     PdfPCell uzitoislamic = new PdfPCell(new Phrase(uzitoUCHUMI, boldFont));
		     uzitoislamic.setColspan(1);
		     
		     UCHUMI.addCell(islamic);
		     UCHUMI.addCell(zoeziislamic);
		     UCHUMI.addCell(midtemislamic);
		     UCHUMI.addCell(annualislamic);
		     UCHUMI.addCell(averageislamic);
		     UCHUMI.addCell(nafasiislamic);
		     UCHUMI.addCell(katiislamic);
		     UCHUMI.addCell(alamaislamic);
		     UCHUMI.addCell(uzitoislamic);
		    
		    
		    
		     
		   
		     food = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1});
		     food.setWidthPercentage(100);
		     PdfPCell foodn = new PdfPCell(new Phrase("Food and Nutrition", boldFont));
		     foodn.setColspan(1);
		     PdfPCell zoezifoodn= new PdfPCell(new Phrase(Afoodmazoezi, boldFont));
		     zoezifoodn.setColspan(1);
		     PdfPCell midtemfoodn = new PdfPCell(new Phrase(Afoodmidterm, boldFont));
		     midtemfoodn.setColspan(1);
		     PdfPCell annualfoodn = new PdfPCell(new Phrase(Afoodexam, boldFont));
		     annualfoodn.setColspan(1);
		     PdfPCell averagefoodn = new PdfPCell(new Phrase(Afoodwastan, boldFont));
		     averagefoodn.setColspan(1);
		     PdfPCell nafasifoodn = new PdfPCell(new Phrase(positionAfood, boldFont));
		     nafasifoodn.setColspan(1);
		     PdfPCell katifoodn = new PdfPCell(new Phrase(katiyaAfood, boldFont));
		     katifoodn.setColspan(1);
		     PdfPCell alamafoodn= new PdfPCell(new Phrase(gradeAfood, boldFont));
		     alamafoodn.setColspan(1);
		     PdfPCell uzitofoodn = new PdfPCell(new Phrase(uzitoAfood, boldFont));
		     uzitofoodn.setColspan(1);
		     food.addCell(foodn);
		     food.addCell(zoezifoodn);
		     food.addCell(midtemfoodn);
		     food.addCell(annualfoodn);
		     food.addCell(averagefoodn);
		     food.addCell(nafasifoodn);
		     food.addCell(katifoodn);
		     food.addCell(alamafoodn);
		     food.addCell(uzitofoodn);
		     
		     
		      fmuComW = new PdfPTable(new float[] {(float) 0.4, 1});
		      fmuComW.setWidthPercentage(100);
		     PdfPCell ComW = new PdfPCell(new Phrase("Wastani", boldFont));
		    
		     PdfPCell bidiiComW = new PdfPCell(new Phrase(Aaveragejumla, boldFont));
		     bidiiComW.setColspan(1);
		      fmuDaraja = new PdfPTable(new float[] {(float) 0.8, 1,1});
		      fmuDaraja.setWidthPercentage(100);
		     PdfPCell ComDaraja = new PdfPCell(new Phrase("Daraja", boldFont));
		     PdfPCell alDaraja = new PdfPCell(new Phrase(Adarajawastani, boldFont));
		     alDaraja.setColspan(1);
		    
		     division = new PdfPCell(new Phrase(Adivsion, boldFont));
		     division.setColspan(1);
		    
		   
		      fmuUjumla = new PdfPTable(new float[] {(float) 0.4, 1});
		      fmuUjumla.setWidthPercentage(100);
		     PdfPCell ComUjumla = new PdfPCell(new Phrase("Nafasi yake kwa ujumla", boldFont));
		     PdfPCell alUjumla = new PdfPCell(new Phrase(Aposition, boldFont));
		     alUjumla.setColspan(1);
		      fmuWanafunzi = new PdfPTable(new float[] {(float) 0.4, 1});
		      fmuWanafunzi.setWidthPercentage(100);
		     PdfPCell ComWanafunzi = new PdfPCell(new Phrase("Kati ya wanafunzi", boldFont));
		     
		     PdfPCell alWanafunzi = new PdfPCell(new Phrase(Ajumlastudent, boldFont));
		     alWanafunzi.setColspan(1);  
		     
		     //tabia
		     tabia = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1,1,1});
		     tabia.setWidthPercentage(100);
		     PdfPCell bidii = new PdfPCell(new Phrase("Bidii na maarifa", boldFont));
		     bidii.setColspan(1);
		     PdfPCell ubora = new PdfPCell(new Phrase("Ubora wa kazi", boldFont));
		     ubora.setColspan(1);
		     PdfPCell Ari = new PdfPCell(new Phrase("Ari ya kazi", boldFont));
		     Ari.setColspan(1);
		     PdfPCell ushiriki = new PdfPCell(new Phrase("Ushirikiano", boldFont));
		     ushiriki.setColspan(1);
		     PdfPCell heshima = new PdfPCell(new Phrase("Heshima", boldFont));
		     heshima.setColspan(1);
		     PdfPCell uaminifu = new PdfPCell(new Phrase("Uaminifu", boldFont));
		     uaminifu.setColspan(1);
		     PdfPCell uongozi = new PdfPCell(new Phrase("Uongozi", boldFont));
		     uongozi.setColspan(1);
		     PdfPCell utii = new PdfPCell(new Phrase("Utii na Heshima", boldFont));
		     utii.setColspan(1);
		     PdfPCell usafi = new PdfPCell(new Phrase("Usafi", boldFont));
		     usafi.setColspan(1);
		     PdfPCell utamaduni = new PdfPCell(new Phrase("Utamaduni", boldFont));
		     utamaduni.setColspan(1);
		     PdfPCell kujiamini = new PdfPCell(new Phrase("Kujiamini", boldFont));
		     kujiamini.setColspan(1);
		     tabia.addCell(bidii);
		     tabia.addCell(ubora);
		     tabia.addCell(Ari);
		     tabia.addCell(ushiriki);
		     tabia.addCell(heshima);
		     tabia.addCell(uaminifu);
		     tabia.addCell(uongozi);
		     tabia.addCell(utii);
		     tabia.addCell(usafi);
		     tabia.addCell(utamaduni);
		     tabia.addCell(kujiamini);
		     
		     tabiaalama = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1,1,1,1});
		     tabiaalama.setWidthPercentage(100);
		     PdfPCell bidiialama = new PdfPCell(new Phrase(bdiii, boldFont));
		     bidiialama.setColspan(1);
		     PdfPCell uboraalama = new PdfPCell(new Phrase(ubraaa, boldFont));
		     uboraalama.setColspan(1);
		     PdfPCell Arialama = new PdfPCell(new Phrase(arriii, boldFont));
		     Arialama.setColspan(1);
		     PdfPCell ushirikialama = new PdfPCell(new Phrase(ushirikiii, boldFont));
		     ushirikialama.setColspan(1);
		     PdfPCell heshimaalama = new PdfPCell(new Phrase(heshmaaa, boldFont));
		     heshimaalama.setColspan(1);
		     PdfPCell uaminifualama = new PdfPCell(new Phrase(uaminiii, boldFont));
		     uaminifualama.setColspan(1);
		     PdfPCell uongozialama = new PdfPCell(new Phrase(uongziii, boldFont));
		     uongozialama.setColspan(1);
		     PdfPCell utiialama = new PdfPCell(new Phrase(utiiii, boldFont));
		     utiialama.setColspan(1);
		     PdfPCell usafialama = new PdfPCell(new Phrase(usfiii, boldFont));
		     usafialama.setColspan(1);
		     PdfPCell utamadunialama = new PdfPCell(new Phrase(utamaduniii, boldFont));
		     utamadunialama.setColspan(1);
		     PdfPCell kujiaminialama = new PdfPCell(new Phrase(kjiaminii, boldFont));
		     kujiaminialama.setColspan(1);
		     tabiaalama.addCell(bidiialama);
		     tabiaalama.addCell(uboraalama);
		     tabiaalama.addCell(Arialama);
		     tabiaalama.addCell(ushirikialama);
		     tabiaalama.addCell(heshimaalama);
		     tabiaalama.addCell(uaminifualama);
		     tabiaalama.addCell(uongozialama);
		     tabiaalama.addCell(utiialama);
		     tabiaalama.addCell(usafialama);
		     tabiaalama.addCell(utamadunialama);
		     tabiaalama.addCell(kujiaminialama);
		     
		     
		    //Vielelezo hapa
		     Vielelezo = new PdfPTable(new float[] { 1,1,1,1,1,1,1,1});
		     Vielelezo.setWidthPercentage(100);
		     PdfPCell gredi = new PdfPCell(new Phrase(" Gredi", boldFont));
		     gredi.setColspan(1);
		     PdfPCell a = new PdfPCell(new Phrase("   A", boldFont));
		     a.setColspan(1);
		     PdfPCell b = new PdfPCell(new Phrase("   B", boldFont));
		     b.setColspan(1);
		     PdfPCell c = new PdfPCell(new Phrase("   C", boldFont));
		     c.setColspan(1);
		     PdfPCell d = new PdfPCell(new Phrase("   D", boldFont));
		     d.setColspan(1);
		     PdfPCell dkilo = new PdfPCell(new Phrase(" E", boldFont));
		     dkilo.setColspan(1);
		     PdfPCell e1kilo = new PdfPCell(new Phrase(" S", boldFont));
		     e1kilo.setColspan(1);
		     PdfPCell e1 = new PdfPCell(new Phrase("   F", boldFont));
		     e1.setColspan(1);
		     
		     
		     
		     
		      Elezo = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1});
		      Elezo.setWidthPercentage(100);
		     PdfPCell gred = new PdfPCell(new Phrase(" Alama", boldFont));
		     gredi.setColspan(1);
		     PdfPCell ag = new PdfPCell(new Phrase("   100 - 80", boldFont));
		     ag.setColspan(1);
		     PdfPCell bg = new PdfPCell(new Phrase("   79 - 70", boldFont));
		     bg.setColspan(1);
		     PdfPCell cg = new PdfPCell(new Phrase("   69 - 60", boldFont));
		     cg.setColspan(1);
		     PdfPCell dg = new PdfPCell(new Phrase("   59 - 50", boldFont));
		     dg.setColspan(1);
		     PdfPCell eg = new PdfPCell(new Phrase("   49 - 40", boldFont));
		     eg.setColspan(1);
		     PdfPCell dgkilo = new PdfPCell(new Phrase("   39 - 35", boldFont));
		     dgkilo.setColspan(1);
		     PdfPCell egkilo = new PdfPCell(new Phrase("   34 - 0", boldFont));
		     egkilo.setColspan(1);
		     
		     
		      Maelezo = new PdfPTable(new float[] { 1, 1,1,1,1,1,1,1});
		      Maelezo.setWidthPercentage(100);
		     PdfPCell gredm = new PdfPCell(new Phrase(" Maelezo", boldFont));
		     gredi.setColspan(1);
		     PdfPCell agm = new PdfPCell(new Phrase("   Bora Sana", boldFont));
		     agm.setColspan(1);
		     PdfPCell bgm = new PdfPCell(new Phrase("   Vizuri Sana", boldFont));
		     bgm.setColspan(1);
		     PdfPCell cgm = new PdfPCell(new Phrase("   Vizuri", boldFont));
		     cgm.setColspan(1);
		     PdfPCell dgme = new PdfPCell(new Phrase("   Wastani   ", boldFont));
		     dgme.setColspan(1);
		     PdfPCell egme = new PdfPCell(new Phrase("   Inaridhisha   ", boldFont));
		     egme.setColspan(1);
		     PdfPCell dgm = new PdfPCell(new Phrase("   Subsidiary", boldFont));
		     dgm.setColspan(1);
		     PdfPCell egm = new PdfPCell(new Phrase("   Feli", boldFont));
		     egm.setColspan(1);
		     
		     
		     divisheni = new PdfPTable(new float[] { 1, 1,1,1,1,1});
		     divisheni.setWidthPercentage(100);
		     PdfPCell gred1 = new PdfPCell(new Phrase(" Daraja", boldFont));
		     gredi.setColspan(1);
		     PdfPCell ag1 = new PdfPCell(new Phrase("   DIVISION I", boldFont));
		     ag1.setColspan(1);
		     PdfPCell bg1 = new PdfPCell(new Phrase("   DIVISION II", boldFont));
		     bg1.setColspan(1);
		     PdfPCell cg1 = new PdfPCell(new Phrase("   DIVISION III", boldFont));
		     cg1.setColspan(1);
		     PdfPCell dg1 = new PdfPCell(new Phrase("   DIVISION IV", boldFont));
		     dg1.setColspan(1);
		     PdfPCell eg1 = new PdfPCell(new Phrase("   DIVISION 0", boldFont));
		     eg1.setColspan(1);
		     
		     
		      Point = new PdfPTable(new float[] { 1, 1,1,1,1,1});
		      Point.setWidthPercentage(100);
		     PdfPCell gredm1 = new PdfPCell(new Phrase(" Point", boldFont));
		     gredi.setColspan(1);
		     PdfPCell agmdiv = new PdfPCell(new Phrase("   3 - 9 ", boldFont));
		     agmdiv.setColspan(1);
		     PdfPCell bgmdiv = new PdfPCell(new Phrase("   10 - 12 ", boldFont));
		     bgmdiv.setColspan(1);
		     PdfPCell cgmdiv = new PdfPCell(new Phrase("   13 - 17 ", boldFont));
		     cgmdiv.setColspan(1);
		     PdfPCell dgmdiv = new PdfPCell(new Phrase("   18 - 19 ", boldFont));
		     dgmdiv.setColspan(1);
		     PdfPCell egmdiv = new PdfPCell(new Phrase("   20 - 21 ", boldFont));
		     egmdiv.setColspan(1);
		     Point.addCell(gredm1);
		     Point.addCell(agmdiv);
		     Point.addCell(bgmdiv);
		     Point.addCell(cgmdiv);
		     Point.addCell(dgmdiv);
		     Point.addCell(egmdiv);

		     
		     
		     masomo.addCell(somo);
		     masomo.addCell(zoezi);
		     masomo.addCell(midtem);
		     masomo.addCell(annual);
		     masomo.addCell(average);
		     masomo.addCell(nafasi);
		     masomo.addCell(kati);
		     masomo.addCell(alama);
		     masomo.addCell(uzito);
		    
		   
		     fmuComW.addCell(ComW);
		     fmuComW.addCell(bidiiComW);
		     
		     fmuDaraja.addCell(ComDaraja);
		     fmuDaraja.addCell(alDaraja);
		     fmuDaraja.addCell(division);
		     fmuUjumla.addCell(ComUjumla);
		     fmuUjumla.addCell(alUjumla);
		     fmuWanafunzi.addCell(ComWanafunzi);
		     fmuWanafunzi.addCell(alWanafunzi);

		     Vielelezo.addCell(gredi);
		     Vielelezo.addCell(a);
		     Vielelezo.addCell(b);
		     Vielelezo.addCell(c);
		     Vielelezo.addCell(d);
		     Vielelezo.addCell(dkilo);
		     Vielelezo.addCell(e1kilo);
		     Vielelezo.addCell(e1);
		    
		     
		     Elezo.addCell(gred);
		     Elezo.addCell(ag);
		     Elezo.addCell(bg);
		     Elezo.addCell(cg);
		     Elezo.addCell(dg);
		     Elezo.addCell(eg);
		     Elezo.addCell(dgkilo);
		     Elezo.addCell(egkilo);
		    
		     
		     Maelezo.addCell(gredm);
		     Maelezo.addCell(agm);
		     Maelezo.addCell(bgm);
		     Maelezo.addCell(cgm);
		     Maelezo.addCell(dgme);
		     Maelezo.addCell(egme);
		     Maelezo.addCell(dgm);
		     Maelezo.addCell(egm);
		     
		     
		     divisheni.addCell(gred1);
		     divisheni.addCell(ag1);
		     divisheni.addCell(bg1);
		     divisheni.addCell(cg1);
		     divisheni.addCell(dg1);
		     divisheni.addCell(eg1);
		     
		     
		     
		     
		       
		      document.add(masomo);
		     
		     if(clssname.equals("FORM V-HGE")){
		    	  document.add(fmu1);
			      document.add(BAM);
			      document.add(fmuHis);
			      document.add(fmuJiog);
			      document.add(UCHUMI);
		       }else if(clssname.equals("FORM V-HGK")){
		    	  document.add(fmu1);
		    	  document.add(fmuHis);
				  document.add(fmuJiog);
				  document.add(fmuKisw);
		       }else if(clssname.equals("FORM V-HGL")){
		    	  document.add(fmu1);
			      document.add(fmuHis);
				  document.add(fmuJiog);
				  document.add(fmuEng);
		       }else if(clssname.equals("FORM V-HKL")){
			      document.add(fmu1);
				  document.add(fmuHis);
				  document.add(fmuKisw);
				  document.add(fmuEng);
			    }else if(clssname.equals("FORM V-PCB")){
			      document.add(fmu1);
				  document.add(BAM);
				  document.add(fmuPhy);
				  document.add(fmuChem);
				  document.add(fmuBios);
			   }else if(clssname.equals("FORM V-PCM")){
				  document.add(fmu1);
				  document.add(fmuPhy);
				  document.add(fmuChem);
				  document.add(fmuMath);
				}else if(clssname.equals("FORM V-PGM")){
				  document.add(fmu1);
				  document.add(fmuPhy);
				  document.add(fmuJiog);
				  document.add(fmuMath);
				}else if(clssname.equals("FORM V-EGM")){
				  document.add(fmu1);
				  document.add(UCHUMI);
				  document.add(fmuJiog);
				  document.add(fmuMath);
				}else if(clssname.equals("FORM V-CBG")){
				  document.add(fmu1);
				  document.add(BAM);
				  document.add(fmuChem);
				  document.add(fmuBios);
				  document.add(fmuJiog);
			    }else if(clssname.equals("FORM V-CBN")){
				  document.add(fmu1);
				  document.add(BAM);
				  document.add(fmuChem);
				  document.add(fmuBios);
				  document.add(food);
			    }else if(clssname.equals("FORM V-ECA")){
				  document.add(fmu1);
				  document.add(BAM);
				  document.add(UCHUMI);
				  document.add(fmuCommerce);
				  document.add(fmuuhasibu);
				}
		         
		       
		       document.add(fmuComW);
		       document.add(fmuDaraja);
		       document.add(fmuUjumla);
		       document.add(fmuWanafunzi);
		       document.add(new Paragraph("                                                                                 TABIA    ",boldMzazi));
		       document.add(tabia);
		       document.add(tabiaalama);
		       document.add(new Paragraph("                                                                                 VIELELEZO    ",boldMzazi));
		       document.add(Vielelezo);
		       document.add(Elezo);
		       document.add(Maelezo);
		       
		       document.add(new Paragraph(""+"\n"));
		       document.add(divisheni);
		       document.add(Point);
		      
		       document.add(new Paragraph("MAAGIZO YA MKUU WA SHULE   ",boldMzazi));
		       String maagizmkuu="SELECT hmaster,class_teacher from announces where class='"+clssname+"' AND year='"+mwaka+"' AND type='"+examinationtype+"' and grade='"+Adarajawastani+"' ";
		       
		       rs=st.executeQuery(maagizmkuu);
		       while(rs.next()){
		    	    mkuu=rs.getString("hmaster");
		    	    mwalimu=rs.getString("class_teacher");
		    	   
		       }
		       document.add(new Phrase(""+ mkuu+"  ",maagizo));
		       document.add(new Paragraph(""+"\n"));
		       document.add(new Paragraph("MAONI YA MWALIMU WA DARASA   ",boldMzazi));
		       
		       document.add(new Phrase(""+ mwalimu +"              ",maagizo));
		    

		
		}
		public void advancequery() throws SQLException{
			
			
			List<Integer> results = new ArrayList<Integer>();
			 con=database.dbconnect();
			 st=con.createStatement();
			 String str="SELECT  mazoezi,mid_term,exam,wastani ,coz_id FROM result_from_teacher where year='"+mwaka+"' AND type='"+examinationtype+"' AND stu_id='"+ID+"' ";
			 rs=st.executeQuery(str);
			 while(rs.next()){
				  coz=rs.getString("coz_id");
				  maoezi=rs.getString("mazoezi");
				  exam=rs.getString("exam");
				  wastan=rs.getString("wastani");
				  midterm=rs.getString("mid_term");
				  
				  double wast=Double.parseDouble(wastan);
				  if(wast>=79.5){
					  alamacoz="A";
					  unit="1";
				  }else if(wast<33.5){
					  alamacoz="F";
					  unit="7";
				  }
				  else if(wast<79.5&&wast>=69.5){
					  alamacoz="B";
					  unit="2";
				  } else if(wast<69.5&&wast>=59.5){
					  alamacoz="C";
					  unit="3";
				  }
				  else if(wast<59.5&&wast>=49.5){
					  alamacoz="D";
					  unit="4";
				  }
				  else if(wast<49.5&&wast>=39.5){
					  alamacoz="E";
					  unit="5";
				  }
				  else if(wast<39.5&&wast>=33.5){
					  alamacoz="S";
					  unit="6";
				  }
				 
				  if(coz.equals("141")==false&&coz.equals("111")==false){
				  int unt=Integer.parseInt(unit);
				   results.add(unt);
				  }
                  Collections.sort(results);
				  
				  
				  st2=con.createStatement();
				  String couseposition="SET @row_number=0";
				    String possubject= " SELECT ROW,wastani,coz_id FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id,coz_id FROM (SELECT wastani,stu_id,coz_id FROM result_from_teacher WHERE year='"+mwaka+"' and "+mikondo+" and type='"+examinationtype+"' and coz_id='"+coz+"' )as tablewastan order by wastani DESC) AS MM WHERE stu_id='"+ID+"'";
				    st2.execute(couseposition);
				       rs2=st2.executeQuery(possubject);
				       while(rs2.next()){
				    	   subjectposition=rs2.getString("ROW"); 
				       }  
				       
				       String jumlastudent="SET @row_number=0";
					    String maxstudent= " SELECT MAX(ROW),coz_id FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id,coz_id FROM (SELECT wastani,stu_id,coz_id FROM result_from_teacher WHERE year='"+mwaka+"' and "+mikondo+" and type='"+examinationtype+"' and coz_id='"+coz+"')as tablewastan  order by wastani DESC) AS MM";
					    st2.execute(jumlastudent);
					       rs2=st2.executeQuery(maxstudent);
					       while(rs2.next()){
					    	 subjectjumla=rs2.getString("MAX(ROW)"); 
					    	 }
				       
				  if(coz.equals("111")){
					  uraiamazoezi=maoezi;
					  uraiamidterm=midterm;
					  uraiaexam=exam;
					  uraiawastan=wastan;
					  positionuraia=subjectposition;
					  katiyauraia=subjectjumla;
					  gradeuraia=alamacoz;
					  uzitouraia=unit;
				  }else if(coz.equals("113")){
					  Ageogmazoezi=maoezi;
					  Ageogmidterm=midterm;
					  Ageogexam=exam;
					  Ageogwastan=wastan;
					  positionAgeog=subjectposition;
					  katiyaAgeog=subjectjumla;
					  gradeAgeog=alamacoz;
					  uzitoAgeog=unit;
				  }else if(coz.equals("112")){
					  Ahistmazoezi=maoezi;
					  Ahistmidterm=midterm;
					  Ahistexam=exam;
					  Ahistwastan=wastan;
					  positionAhist=subjectposition;
					  katiyaAhist=subjectjumla;
					  gradeAhist=alamacoz;
					  uzitoAhist=unit;
				  }else if(coz.equals("131")){
					  Aphymazoezi=maoezi;
					  Aphymidterm=midterm;
					  Aphyexam=exam;
					  Aphytwastan=wastan;
					  positionAphy=subjectposition;
					  katiyaAphy=subjectjumla;
					  gradeAphy=alamacoz;
					  uzitoAphy=unit;
				  }else if(coz.equals("121")){
					  Akiswmazoezi=maoezi;
					  Akiswmidterm=midterm;
					  Akiswexam=exam;
					  Akiswwastan=wastan;
					  positionAkisw=subjectposition;
					  katiyaAkisw=subjectjumla;
					  gradeAkisw=alamacoz;
					  uzitoAkiswa=unit;
				  }else if(coz.equals("122")){
					  Aengmazoezi=maoezi;
					  Aengmidterm=midterm;
					  Aengexam=exam;
					  Aengwastan=wastan;
					  positionAeng=subjectposition;
					  katiyaAeng=subjectjumla;
					  gradeAeng=alamacoz;
					  uzitoAeng=unit;
				  }else if(coz.equals("132")){
					  Achemmazoezi=maoezi;
					  Achemmidterm=midterm;
					  Achemexam=exam;
					  Achemwastan=wastan;
					  positionAchem=subjectposition;
					  katiyaAchem=subjectjumla;
					  gradeAchem=alamacoz;
					  uzitoAchem=unit;
				  }else if(coz.equals("141")){
					  bammazoezi=maoezi;
					  bammidterm=midterm;
					  bamexam=exam;
					  bamwastan=wastan;
					  positionbam=subjectposition;
					  katiyabam=subjectjumla;
					  gradebam=alamacoz;
					  uzitobamu=unit;
				  }else if(coz.equals("142")){
					  Amathmazoezi=maoezi;
					  Amathmidterm=midterm;
					  Amathexam=exam;
					  Amathwastan=wastan;
					  positionAmath=subjectposition;
					  katiyaAmath=subjectjumla;
					  gradeAmath=alamacoz;
					  uzitoAmath=unit;
				  }
				  else if(coz.equals("133")){
					  Abiosmazoezi=maoezi;
					  Abiosmidterm=midterm;
					  Abiosexam=exam;
					  Abioswastan=wastan;
					  positionAbios=subjectposition;
					  katiyaAbios=subjectjumla;
					  gradeAbios=alamacoz;
					  uzitoAbiosi=unit;
				  }else if(coz.equals("153")){
					  Auhasibumazoezi=maoezi;
					  Auhasibumidterm=midterm;
					  Auhasibuexam=exam;
					  Auhasibuwastan=wastan;
					  positionAuhasibu=subjectposition;
					  katiyaAuhasibu=subjectjumla;
					  gradeAuhasibu=alamacoz;
					  uzitoAuhasibuu=unit;
				  }
				  else if(coz.equals("152")){
					  Abiasharamazoezi=maoezi;
					  Abiasharamidterm=midterm;
					  Abiasharaexam=exam;
					  Abiasharawastan=wastan;
					  positionAbiashara=subjectposition;
					  katiyaAbiashara=subjectjumla;
					  gradeAbiashara=alamacoz;
					  uzitoAbiasharaa=unit;
				  }else if(coz.equals("151")){
					  UCHUMImazoezi=maoezi;
					  UCHUMImidterm=midterm;
					  UCHUMIexam=exam;
					  UCHUMIwastan=wastan;
					  positionUCHUMI=subjectposition;
					  katiyaUCHUMI=subjectjumla;
					  gradeUCHUMI=alamacoz;
					  uzitoUCHUMI=unit;
				  }else if(coz.equals("155")){
					  Afoodmazoezi=maoezi;
					  Afoodmidterm=midterm;
					  Afoodexam=exam;
					  Afoodwastan=wastan;
					  positionAfood=subjectposition;
					  katiyaAfood=subjectjumla;
					  gradeAfood=alamacoz;
					  uzitoAfood=unit;
				  }
				  
				  
					  
				  
		       }
			 
			 if(results.size()==3){
		    	   
		    	  
		    	  
		    	  int sum = 0;
		    	  for(int h=0; h<results.size(); h++){
		    		    sum += results.get(h);
		    		}
                   
		    	    
		    	  if(sum>=3&&sum<=9){
               	   Adivsion="DIVISION I   Point "+sum;
                  }else if(sum>=10&&sum<=12){
               	   Adivsion="DIVISION II  Point "+sum;
                  }else if(sum>=13&&sum<=17){
               	   Adivsion="DIVISION III  Point "+sum;
                  }else if(sum>=18&&sum<=19){
               	   Adivsion="DIVISION IV  Point "+sum;
                  }else if(sum>=20&&sum<=21){
               	   Adivsion="DIVISION 0  Point "+sum;
                  }
                
		      }else if(results.size()<3){
		    	  Adivsion="DIVISION Incomplete results";
		      }
	       
			 
			 String std0="SET @row_number=0";
			    String std= " SELECT ROW,wastani FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher WHERE year='"+mwaka+"' and "+mikondo+" and type='"+examinationtype+"' and NOT coz_id='141' and NOT coz_id='111' group by stu_id )as tablewastan order by wastani DESC) AS MM WHERE stu_id='"+ID+"'";
			         st.execute(std0);
			       rs=st.executeQuery(std);
			       
			       while(rs.next()){
			    	   String wab=rs.getString("wastani");
			    	    
			    	    Aposition=rs.getString("ROW");
			    	    double wast2=Double.parseDouble(wab);
			    	    double majid=Math.round(wast2*100.0)/100.0;
			    	    Aaveragejumla=""+majid;
						  if(wast2>=79.5){
							  Adarajawastani="A";
							  
						  }else if(wast2<33.5){
							  Adarajawastani="F";
							  
						  }
						  else if(wast2<79.5&&wast2>=69.5){
							  Adarajawastani="B";
							  
						  } else if(wast2<69.5&&wast2>=59.5){
							  Adarajawastani="C";
							  
						  }
						  else if(wast2<59.5&&wast2>=49.5){
							  Adarajawastani="D";
							  
						  } else if(wast2<49.5&&wast2>=39.5){
							  Adarajawastani="E";
							  
						  } else if(wast2<39.5&&wast2>=33.5){
							  Adarajawastani="S";
							  
						  }
						 
			       }
			        String std2="SET @row_number=0";
				    String std3= " SELECT MAX(ROW) FROM ( SELECT @row_number:=@row_number+1 AS ROW,wastani,stu_id FROM (SELECT AVG(wastani) AS wastani,stu_id FROM result_from_teacher WHERE year='"+mwaka+"' and "+mikondo+" and type='"+examinationtype+"' and NOT coz_id='141' and NOT coz_id='111' group by stu_id )as tablewastan order by wastani DESC) AS MM ";
				         st.execute(std2);
				       rs=st.executeQuery(std3);
				       
				       while(rs.next()){
				       Ajumlastudent=rs.getString("MAX(ROW)");
				    	    
				    	   
				    	  
				       }
				       
	      String tbia="Select avg(bidii),avg(ubora),avg(ari),avg(utunzaji),avg(ushirikiano),avg(heshima),avg(uongozi),avg(utii),avg(usafi),avg(uaminifu),avg(tamaduni),avg(kujiamini) FROM tabia WHERE stu_id='"+ID+"' AND class='"+clssname+"' AND year='"+mwaka+"' AND type='"+examinationtype+"'";
	      try{
	      rs=st.executeQuery(tbia);
	      while(rs.next()){
	    	  tabia();
	      }
	      }catch(Exception a){
	    	  System.out.println(a);
	      }
		
		}
		@SuppressWarnings("unchecked")
		public void openPDF(){
			wd = new WorkIndicatorDialog(null, "Opening Report...");
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

						if ((new File("result.pdf")).exists()) {

							Process p = Runtime
							   .getRuntime()
							   .exec("rundll32 url.dll,FileProtocolHandler result.pdf");
							p.waitFor();
							s=1;
								
						} else {

							JOptionPane.showMessageDialog(null, "File does not exist");

						}

						

				  	  } catch (Exception ex) {
				  		
						
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
		public void closePDF(){

			  try {
					
					

						Process pr = Runtime
						   .getRuntime()
						   .exec("taskkill /F /IM acroRd32.exe");
						pr.waitFor();
							
						 Thread.sleep(1200);   
					

			  	  } catch (Exception ex) {
			  		
					
				  }
		}
		@SuppressWarnings("unchecked")
		public void querystudent(){
			
			wd = new WorkIndicatorDialog(null, "Loading students data...");
		 	   wd.addTaskEndNotification(result -> {
		 		  String outres = result.toString();
		          // System.out.println("nomaa "+outres);
		           if(outres.equals("1")){
		        	   
		           }else{
		        	   noresultmassage();
		           }
		           wd=null;
		 	   });
		 		 wd.exec("fetch", inputParam -> {
			           // Thinks to do...
			           // NO ACCESS TO UI ELEMENTS!
		 			try{
						con=database.dbconnect();
						boolean success=false;
					st=con.createStatement();
					String std="select DISTINCT students.student_id,students.firstName,students.middleName,students.lastName,students.studentClass,result_from_teacher.class FROM students INNER JOIN result_from_teacher ON students.student_id=result_from_teacher.stu_id WHERE result_from_teacher.year='"+academicy+"' AND "+madarasa+" AND result_from_teacher.type='"+mihula+"' AND students.status!='DISABLED'";
					rs=st.executeQuery(std);
					while(rs.next()){
					    success=true;
						String id=rs.getString("students.student_id");
					    String fname=rs.getString("students.firstName");
					    String mname=rs.getString("students.middleName");
					    String lname=rs.getString("students.lastName");
					    String clas=rs.getString("result_from_teacher.class");
						tableview.getItems().add(new Report(id,fname,mname,lname,clas));
						
					}
					if(success){
						s = 1;
					}else{
						
						s = 0;
					}
					}catch(SQLException sq){
						
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
		public void tabia() throws SQLException{

	    	  String bidii=rs.getString("avg(bidii)");
	    	  double bdii=Double.parseDouble(bidii);
		   	   if(bdii>=4.5){
		   		    bdiii="A" ; 
		   	   }else if(3.5<=bdii&&bdii<4.5){
		   		    bdiii="B";
		   	   }else if(2.5<=bdii&&bdii<3.5){
		   		    bdiii="C";
		   	   }
		   	   else if(1.5<=bdii&&bdii<2.5){
		   		    bdiii="D";
		   	   }
		   	   else if(0<=bdii&&bdii<1.5){
		   		    bdiii="F";
		   	   }
	    	  String ubara=rs.getString("avg(ubora)");
	    	  double ubraa=Double.parseDouble(ubara);
	   	   if(ubraa>=4.5){
	   		   ubraaa="A" ; 
	   	   }else if(3.5<=ubraa&&ubraa<4.5){
	   		   ubraaa="B";
	   	   }else if(2.5<=ubraa&&ubraa<3.5){
	   		   ubraaa="C";
	   	   }
	   	   else if(1.5<=ubraa&&ubraa<2.5){
	   		   ubraaa="D";
	   	   }
	   	   else if(0<=ubraa&&ubraa<1.5){
	   		   ubraaa="F";
	   	   }
	    	  String ariya=rs.getString("avg(ari)");
	    	  double arrii=Double.parseDouble(ariya);
	   	   if(arrii>=4.5){
	   		   arriii="A" ; 
	   	   }else if(3.5<=arrii&&arrii<4.5){
	   		   arriii="B";
	   	   }else if(2.5<=arrii&&arrii<3.5){
	   		   arriii="C";
	   	   }
	   	   else if(1.5<=arrii&&arrii<2.5){
	   		   arriii="D";
	   	   }
	   	   else if(0<=arrii&&arrii<1.5){
	   		   arriii="F";
	   	   }
	    	String ushirikian=rs.getString("avg(ushirikiano)");
	    	double ushirikii=Double.parseDouble(ushirikian);
	   	   if(ushirikii>=4.5){
	   		ushirikiii="A" ; 
	   	   }else if(3.5<=ushirikii&&ushirikii<4.5){
	   		ushirikiii="B";
	   	   }else if(2.5<=ushirikii&&ushirikii<3.5){
	   		ushirikiii="C";
	   	   }
	   	   else if(1.5<=ushirikii&&ushirikii<2.5){
	   		ushirikiii="D";
	   	   }
	   	   else if(0<=ushirikii&&ushirikii<1.5){
	   		ushirikiii="F";
	   	   }
	    String hshima=rs.getString("avg(heshima)");
	    double heshmaa=Double.parseDouble(hshima);
	   	   if( heshmaa>=4.5){
	   		   heshmaaa="A" ; 
	   	   }else if(3.5<= heshmaa&& heshmaa<4.5){
	   		   heshmaaa="B";
	   	   }else if(2.5<= heshmaa&&heshmaa<3.5){
	   		    heshmaaa="C";
	   	   }
	   	   else if(1.5<= heshmaa&& heshmaa<2.5){
	   		   heshmaaa="D";
	   	   }
	   	   else if(0<= heshmaa&& heshmaa<1.5){
	   		   heshmaaa="F";
	   	   }
	    	  String uongzi=rs.getString("avg(uongozi)");
	    	  double uongzii=Double.parseDouble(uongzi);
	   	   if( uongzii>=4.5){
	   		   uongziii="A" ; 
	   	   }else if(3.5<= uongzii&& uongzii<4.5){
	   		   uongziii="B";
	   	   }else if(2.5<= uongzii&&uongzii<3.5){
	   		   uongziii="C";
	   	   }
	   	   else if(1.5<= uongzii&& uongzii<2.5){
	   		   uongziii="D";
	   	   }
	   	   else if(0<= uongzii&& uongzii<1.5){
	   		   uongziii="F";
	   	   }
	    	  String utiii=rs.getString("avg(utii)");
	    	  double utii=Double.parseDouble(utiii);
	   	   if( utii>=4.5){
	   		   utiiii="A" ; 
	   	   }else if(3.5<= utii&& utii<4.5){
	   		   utiiii="B";
	   	   }else if(2.5<=utii&&utii<3.5){
	   		   utiiii="C";
	   	   }
	   	   else if(1.5<=utii&&utii<2.5){
	   		   utiiii="D";
	   	   }
	   	   else if(0<=utii&&utii<1.5){
	   		   utiiii="F";
	   	   }
	    	  String usfi=rs.getString("avg(usafi)");
	    	  double usfii=Double.parseDouble(usfi);
	   	   if( usfii>=4.5){
	   		   usfiii="A" ; 
	   	   }else if(3.5<= usfii&& usfii<4.5){
	   		   usfiii="B";
	   	   }else if(2.5<=usfii&&usfii<3.5){
	   		   usfiii="C";
	   	   }
	   	   else if(1.5<=usfii&&usfii<2.5){
	   		   usfiii="D";
	   	   }
	   	   else if(0<=usfii&&usfii<1.5){
	   		   usfiii="F";
	   	   }
	    	  String uaminif=rs.getString("avg(uaminifu)");
	    	  double uamini=Double.parseDouble(uaminif);
	   	   if( uamini>=4.5){
	   		   uaminiii="A" ; 
	   	   }else if(3.5<= uamini&& uamini<4.5){
	   		   uaminiii="B";
	   	   }else if(2.5<=uamini&&uamini<3.5){
	   		   uaminiii="C";
	   	   }
	   	   else if(1.5<=uamini&&uamini<2.5){
	   		   uaminiii="D";
	   	   }
	   	   else if(0<=uamini&&uamini<1.5){
	   		   uaminiii="F";
	   	   }
	    	  String utamadu=rs.getString("avg(tamaduni)");
	    	  double utamadunii=Double.parseDouble(utamadu);
	   	   if( utamadunii>=4.5){
	   		   utamaduniii="A" ; 
	   	   }else if(3.5<= utamadunii&& utamadunii<4.5){
	   		   utamaduniii="B";
	   	   }else if(2.5<=utamadunii&&utamadunii<3.5){
	   		   utamaduniii="C";
	   	   }
	   	   else if(1.5<=utamadunii&&utamadunii<2.5){
	   		   utamaduniii="D";
	   	   }
	   	   else if(0<=utamadunii&&utamadunii<1.5){
	   		   utamaduniii="F";
	   	   }
	   	   
	   	   
	    	  String kjiamin=rs.getString("avg(kujiamini)");
	    	  double kjiamini=Double.parseDouble(kjiamin);
	   	   if( kjiamini>=4.5){
	   		kjiaminii="A" ; 
	   	   }else if(3.5<= kjiamini&& kjiamini<4.5){
	   		kjiaminii="B";
	   	   }else if(2.5<=kjiamini&&kjiamini<3.5){
	   		kjiaminii="C";
	   	   }
	   	   else if(1.5<=kjiamini&&kjiamini<2.5){
	   		kjiaminii="D";
	   	   }
	   	   else if(0<=kjiamini&&kjiamini<1.5){
	   		kjiaminii="F";
	   	   }
	   	   
	   	  String utunzu=rs.getString("avg(utunzaji)");
  	  double utunzuu =Double.parseDouble(utunzu);
 	   if( utunzuu>=4.5){
 		utunzuuu="A" ; 
 	   }else if(3.5<= utunzuu&& utunzuu<4.5){
 		utunzuuu="B";
 	   }else if(2.5<=utunzuu&&utunzuu<3.5){
 		utunzuuu="C";
 	   }
 	   else if(1.5<=utunzuu&&utunzuu<2.5){
 		utunzuuu="D";
 	   }
 	   else if(0<=utunzuu&&utunzuu<1.5){
 		utunzuuu="F";
 	   }
	   
	    	  
	    	  
	    	  
	    	  
	      
		}

	}


