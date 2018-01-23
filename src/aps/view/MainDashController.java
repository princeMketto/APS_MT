package aps.view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.swing.JPopupMenu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXRippler.RipplerMask;
import com.jfoenix.controls.JFXRippler.RipplerPos;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import aps.Main;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;



public class MainDashController implements Initializable {
	private Main main;
    @FXML
    private StackPane stackPop,stackMain,stackHead,stackbase;
	@FXML
	JFXListView<String> listview;


    @FXML
    private BorderPane borderpane;

    @FXML
    private BorderPane borderpaneInner;

    @FXML
    private JFXHamburger ham;

    @FXML
    private Label unam;
    @FXML
    private Label gdata;

    @FXML
    private ImageView logOffPi;

    @FXML
    private Label logOffLab;

    @FXML
    private JFXButton setting,btnHead;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXButton btngeneratereport;

    @FXML
    private JFXButton btnregi;

    @FXML
    private JFXButton btnuploadresult;

    @FXML
    private JFXButton btnstudentinfo;

    @FXML
    private JFXButton btntimetable;

    @FXML
    private JFXButton btnprintdata;

    @FXML
    private JFXButton btnsettings;

    @FXML
    private JFXButton btnlogs;

    @FXML
    private JFXButton btnmyprofile;

    @FXML
    private JFXButton btnhelp;

    @FXML
    private JFXButton btnfaq;

    @FXML
    private JFXButton btnabout;

    @FXML
    private JFXButton btnupdates;
    @FXML
    private CategoryAxis x;
    @FXML
    private CategoryAxis y;
    

    @FXML
    private BarChart<?, ?> barchart;

    static String user = null;
    @FXML
    private JFXButton btnupdate;
    @FXML
    private JFXButton btnProf;
    @FXML
    private JFXButton btnPurchase;
    LoginController lg = new LoginController();
    private Timeline timeline;
    private AnimationTimer timer;
    private Integer i=0;
    int count1,count2,count3,count4,count5,count6;
    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private Text words;

    @FXML
    private JFXPopup popup;
    @FXML
    private StackPane poproot;
    @FXML
    private JFXButton btntool1,btnrelod;

    @FXML
    private WorkIndicatorDialog wd=null;
    
    /*
     * 
     */
    JPopupMenu menu;
    ContextMenu context;
    ConnectDB database = new ConnectDB();
    private Connection con;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement prep,prep1;
    
	@Override
	public void initialize(URL url, ResourceBundle rb){
		fillGraph();
		initPop();
		user = LoginController.getuser();
		unam.setText(LoginController.getStats()+":"+user);
		//ustat.setText(lg.getStats());
		drawer.isHidden();
		
		logOffLab.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
			try {
				logOut();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	
		
		/*
		 * Drawer graphic
		 */
	/*	Image saledr = new Image(getClass().getResourceAsStream("images/cart.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		drawSale.setGraphic(new ImageView(saledr));
		Image custdr = new Image(getClass().getResourceAsStream("images/customer.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		drawCust.setGraphic(new ImageView(custdr));
		Image proddr = new Image(getClass().getResourceAsStream("images/product.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		drawProd.setGraphic(new ImageView(proddr));
		Image helpsdr = new Image(getClass().getResourceAsStream("images/help.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		drawHelp.setGraphic(new ImageView(helpsdr));
		*/
	/*Image supplierdr = new Image(getClass().getResourceAsStream("images/supplierX.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		drawSupp.setGraphic(new ImageView(supplierdr));*/
	/*
		 * drawer graphic end
		 */
		Image genrepo = new Image(getClass().getResourceAsStream("images/report.png"));
		btngeneratereport.setGraphic(new ImageView(genrepo));
		
		Image reg = new Image(getClass().getResourceAsStream("images/regista.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		btnregi.setGraphic(new ImageView(reg));
		Image upl = new Image(getClass().getResourceAsStream("images/upload.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		btnuploadresult.setGraphic(new ImageView(upl));
		Image upd = new Image(getClass().getResourceAsStream("images/update.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		btnupdate.setGraphic(new ImageView(upd));
		Image inf = new Image(getClass().getResourceAsStream("images/about.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		btnstudentinfo.setGraphic(new ImageView(inf));
		Image tim = new Image(getClass().getResourceAsStream("images/timetable.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		btntimetable.setGraphic(new ImageView(tim));
		Image setts = new Image(getClass().getResourceAsStream("images/setting.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		btnsettings.setGraphic(new ImageView(setts));
		
		
		Image logu = new Image(getClass().getResourceAsStream("images/logs.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		btnlogs.setGraphic(new ImageView(logu));
		Image prin = new Image(getClass().getResourceAsStream("images/printere.png"));
		btnprintdata.setGraphic(new ImageView(prin));
		/*Image purch = new Image(getClass().getResourceAsStream("images/purchasein.png"));
		btnPurchase.setGraphic(new ImageView(purch));
		Image abt = new Image(getClass().getResourceAsStream("images/about.png"));
		btnabout.setGraphic(new ImageView(abt));
		Image cha = new Image(getClass().getResourceAsStream("images/about.png"));
		btnChat.setGraphic(new ImageView(cha));

		Image pham = new Image(getClass().getResourceAsStream("images/pharma.png"));
		btnpharma.setGraphic(new ImageView(pham));*/

		Image sett = new Image(getClass().getResourceAsStream("images/sett.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		setting.setGraphic(new ImageView(sett));
		Image rel = new Image(getClass().getResourceAsStream("images/reload.png"));
		//btnsale = new JFXButton("Sales",new ImageView(salebtn));
		btnrelod.setGraphic(new ImageView(rel));
		 try {
	            VBox box = FXMLLoader.load(getClass().getResource("DrawerContent.fxml"));
	            drawer.setSidePane(box);
	            
	            for(Node node : box.getChildren()){
	            	if(node.getAccessibleText() != null){
	            		node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
	            			switch(node.getAccessibleText()){
	            			case "Material_one":
	            		//	System.out.println("You CLICKED1");
	            				goReport();
	            			break;
	            			case "Material_two":
		            			try {
		            				goRegister();
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		            			break;
	            			case "Material_three":
		            			try {
									goUplod();
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		            			break;
	            			case "Material_four":
		            			try {
		            				goInformation();
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		            		
		            			break;
	            			case "Material_five":
	            				try {
								//	goSupp();
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		            			
		            			break;
	            			case "Material_six":
		            			try {
									logOut();
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		            			
		            			break;
	            			}
	            		});
	            	}
	            }
	            
	      
		 
		HamburgerSlideCloseTransition trans = new HamburgerSlideCloseTransition(ham);
		trans.setRate(-1);
		ham.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
			trans.setRate(trans.getRate() * -1);
			trans.play();
			
			if(drawer.isShown())
            {
                drawer.close();
            }else
                drawer.open();
		
		});
		//initPopup();
		//Navigate through Dashboard drawer item
		  } catch (IOException ex) {
	          ex.printStackTrace();
			  // Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
	        }
	}
	@SuppressWarnings("static-access")
	private void goUplod() {

    	try {
			main.showUploadScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}
    @FXML
    void ReloadGraph(ActionEvent event) {
    	 fillGraph();
    }
	private void initPop() {
	/*	JFXButton b1 = new JFXButton("task1");
		JFXButton b2 = new JFXButton("task1");
		JFXButton b3 = new JFXButton("task1");
		b1.setPadding(new Insets(5));
		b2.setPadding(new Insets(5));
		b3.setPadding(new Insets(5));
		VBox box = new VBox(b1,b2,b3);
		JFXRippler popupSource = new JFXRippler(setting,RipplerMask.CIRCLE,RipplerPos.FRONT);
		popup.setContent(box);
		popup.setPopupContainer(poproot);
		popup.setSource(popupSource);
		popupSource.setOnMouseClicked((e)-> popup.show(PopupVPosition.TOP, PopupHPosition.LEFT));*/
		
	}
	@SuppressWarnings("unchecked")
	private void fillGraph() {
		 barchart.getData().clear();
		  wd = new WorkIndicatorDialog(null, "Refreshing data(S)...");
		   wd.addTaskEndNotification(result -> {
	          
	          String outres = result.toString();
	         // System.out.println("nomaa "+outres);
	          if(outres.equals("1")){
	        	  gdata.setText(null);
	        	  XYChart.Series series1 = new XYChart.Series();
				  series1.setName("FORM I"+"("+count1+")");
			   series1.getData().add(new XYChart.Data("I",count1));
			   barchart.getData().addAll(series1);
			   //
			   XYChart.Series series2 = new XYChart.Series();
			   series2.setName("FORM II"+"("+count2+")");
			   series2.getData().add(new XYChart.Data("II",count2));
			   barchart.getData().addAll(series2);
	          //
			   XYChart.Series series3 = new XYChart.Series();
			   series3.setName("FORM III"+"("+count3+")");
			   series3.getData().add(new XYChart.Data("III",count3));
			   barchart.getData().addAll(series3);
			   //
			   XYChart.Series series4 = new XYChart.Series();
			   series4.setName("FORM IV"+"("+count4+")");
			   series4.getData().add(new XYChart.Data("IV",count4));
			   barchart.getData().addAll(series4);
			   //
			   XYChart.Series series5 = new XYChart.Series();
			   series5.setName("FORM V"+"("+count5+")");
			   series5.getData().add(new XYChart.Data("V",count5));
			   barchart.getData().addAll(series5);
			   //
			   XYChart.Series series = new XYChart.Series();
			   series.setName("FORM VI"+"("+count6+")");
			   series.getData().add(new XYChart.Data("VI",count6));
			   barchart.getData().addAll(series);
	          }
	           wd=null; // don't keep the object, cleanup
	        });
		  	 wd.exec("fetch", inputParam -> {
		  		 count1=0;count2=0;count3=0;count4=0;count5=0;count6=0;
		  		 		gdata.setText("loading graph data ...");
		 		String timing,clac,dateTemp = null,name1,nameTemp = null,nextname=null;
				int amount1,amountTemp=0,amount2;
				 GregorianCalendar date = new GregorianCalendar();
				  int     day = date.get(Calendar.DAY_OF_MONTH);
				    int  month = date.get(Calendar.MONTH)+1;
				    int  year = date.get(Calendar.YEAR);
				  String  today = year+"/"+month+"/"+day;
				  int nextmonth=0;
				  nextmonth+=month;
				//*****
					try{
						con= database.dbconnect();
						   st= con.createStatement();
						  
						   String query="SELECT * FROM students WHERE NOT (status='TRANSFERRED' OR status='DISABLED' OR status='COMPLETE')";
						   rs=st.executeQuery(query);
						  
						 //  XYChart.Series series1 = new XYChart.Series();
						  String A="FORM IA",B="FORM IB",C="FORM IC",D="FORM ID",E="FORM IE",F="FORM IF";
						  String A2="FORM IIA",B2="FORM IIB",C2="FORM IIC",D2="FORM IID",E2="FORM IIE",F2="FORM IIF";
						  String A3="FORM IIIA",B3="FORM IIIB",C3="FORM IIIC",D3="FORM IIID",E3="FORM IIIE",F3="FORM IIIF";
						  String A4="FORM IVA",B4="FORM IVB",C4="FORM IVC",D4="FORM IVD",E4="FORM IVE",F4="FORM IVF";
						  String A5="FORM V-PCM",B5="FORM V-PGM",C5="FORM V-PCB",D5="FORM V-CBG",E5="FORM V-CBN",F5="FORM V-HGL"
								  ,G5="FORM V-EGM",H5="FORM V-HGE",I5="FORM V-ECA",J5="FORM V-HKL",K5="FORM V-HGK";
						  
						  String A6="FORM VI-PCM",B6="FORM VI-PGM",C6="FORM VI-PCB",D6="FORM VI-CBG",E6="FORM VI-CBN",F6="FORM VI-HGL"
								  ,G6="FORM VI-EGM",H6="FORM VI-HGE",I6="FORM VI-ECA",J6="FORM VI-HKL",K6="FORM VI-HGK";
						   while(rs.next()){
							   if(rs.getString("studentClass").equals(A) || rs.getString("studentClass").equals(B)
								|| rs.getString("studentClass").equals(C) ||rs.getString("studentClass").equals(D)
								|| rs.getString("studentClass").equals(E) || rs.getString("studentClass").equals(F)){
								   count1++;   
							   }
							   if(rs.getString("studentClass").equals(A2) || rs.getString("studentClass").equals(B2)
										|| rs.getString("studentClass").equals(C2) ||rs.getString("studentClass").equals(D2)
										|| rs.getString("studentClass").equals(E2) || rs.getString("studentClass").equals(F2)){
										   count2++;   
									   }
							   if(rs.getString("studentClass").equals(A3) || rs.getString("studentClass").equals(B3)
										|| rs.getString("studentClass").equals(C3) ||rs.getString("studentClass").equals(D3)
										|| rs.getString("studentClass").equals(E3) || rs.getString("studentClass").equals(F3)){
										   count3++;   
									   }
							   if(rs.getString("studentClass").equals(A4) || rs.getString("studentClass").equals(B4)
										|| rs.getString("studentClass").equals(C4) ||rs.getString("studentClass").equals(D4)
										|| rs.getString("studentClass").equals(E4) || rs.getString("studentClass").equals(F4)){
										   count4++;   
									   }
							   
							   if(rs.getString("studentClass").equals(A5) || rs.getString("studentClass").equals(B5)
										|| rs.getString("studentClass").equals(C5) ||rs.getString("studentClass").equals(D5)
										|| rs.getString("studentClass").equals(E5) || rs.getString("studentClass").equals(F5)
										|| rs.getString("studentClass").equals(G5)
										|| rs.getString("studentClass").equals(H5) ||rs.getString("studentClass").equals(I5)
										|| rs.getString("studentClass").equals(J5) || rs.getString("studentClass").equals(K5)){
										   count5++;   
									   }
							   if(rs.getString("studentClass").equals(A6) || rs.getString("studentClass").equals(B6)
										|| rs.getString("studentClass").equals(C6) ||rs.getString("studentClass").equals(D6)
										|| rs.getString("studentClass").equals(E6) || rs.getString("studentClass").equals(F6)
										|| rs.getString("studentClass").equals(G6) ||rs.getString("studentClass").equals(H6)
										|| rs.getString("studentClass").equals(I6) || rs.getString("studentClass").equals(J6)
										|| rs.getString("studentClass").equals(K6)){
										   count6++;   
									   }
							 
						   }
						  
						   //graph getdata here
						   rs.close();
						st.close();
						con.close();
						
					}catch(Exception ex){
						
					}
				  //******
		/*		try{
					con= database.dbconnect();
					   st= con.createStatement();
					  
					   String query="SELECT * FROM students WHERE (studentClass='FORM IA' OR studentClass='FORM IB' "
					    		+ " OR studentClass='FORM IC' OR studentClass='FORM ID' OR studentClass='FORM IE' OR"
					    		+ " studentClass='FORM IF' OR studentClass='FORM IG' OR studentClass='FORM IH'"
					    		+ ") AND NOT (status='TRANSFERRED' OR status='DISABLED' OR status='COMPLETE')";
					   rs=st.executeQuery(query);
					  
					 //  XYChart.Series series1 = new XYChart.Series();
					  
					   while(rs.next()){
						   count1++;
						 
					   }
					  
					   //graph getdata here
					   rs.close();
					st.close();
					con.close();
					
				}catch(Exception ex){
					
				}
				try{
					
					 con= database.dbconnect();
					    st= con.createStatement();
					    String select="SELECT * FROM students WHERE (studentClass='FORM IIA' OR studentClass='FORM IIB' "
					    		+ " OR studentClass='FORM IIC' OR studentClass='FORM IID' OR studentClass='FORM IIE' OR"
					    		+ " studentClass='FORM IIF' OR studentClass='FORM IIG' OR studentClass='FORM IIH'"
					    		+ ") AND NOT (status='TRANSFERRED' OR status='DISABLED' OR status='COMPLETE')";
					    rs=st.executeQuery(select);
					    
					    while(rs.next()){
							   count2++;
							 
						   }
						  
						   //graph getdata here
					  			    
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{
						 try{
					            st.close();
					            rs.close();
					            con.close();
					        }catch(Exception e){
					           // JOptionPane.showMessageDialog(null, "ERROR CLOSE");
					        }
					    
					}
				//3
				try{
					
					 con= database.dbconnect();
					    st= con.createStatement();
					    String select="SELECT * FROM students WHERE (studentClass LIKE 'FORM III%'"
					    		+ ") AND NOT (status='TRANSFERRED' OR status='DISABLED' OR status='COMPLETE')";
					    rs=st.executeQuery(select);
					    while(rs.next()){
							   count3++;
							 
						   }
						  
						   //graph getdata here
					    
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{
						 try{
					            st.close();
					            rs.close();
					            con.close();
					        }catch(Exception e){
					           // JOptionPane.showMessageDialog(null, "ERROR CLOSE");
					        }
					    
					}
				//4
				try{
					
					 con= database.dbconnect();
					    st= con.createStatement();
					    String select="SELECT * FROM students WHERE (studentClass LIKE 'FORM IV%') "
					    		+ " AND NOT (status='TRANSFERRED' OR status='DISABLED' OR status='COMPLETE')";
					    rs=st.executeQuery(select);
					   // count4=0;
					    while(rs.next()){
							   count4++;
							 
						   }
						  
						   //graph getdata here
					    
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{
						 try{
					            st.close();
					            rs.close();
					            con.close();
					        }catch(Exception e){
					           // JOptionPane.showMessageDialog(null, "ERROR CLOSE");
					        }
					    
					}
				//5
				try{
					
					 con= database.dbconnect();
					    st= con.createStatement();
					    String select="SELECT * FROM students WHERE (studentClass LIKE 'FORM V-%') "
					    		+ " AND NOT (status='TRANSFERRED' OR status='DISABLED' OR status='COMPLETE')";
					    rs=st.executeQuery(select);
					  //  count5=0;
					    while(rs.next()){
							   count5++;
							 
						   }
						  
						   //graph getdata here
					    
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{
						 try{
					            st.close();
					            rs.close();
					            con.close();
					        }catch(Exception e){
					           // JOptionPane.showMessageDialog(null, "ERROR CLOSE");
					        }
					    
					}
				//6
				try{
					
					 con= database.dbconnect();
					    st= con.createStatement();
					    String select="SELECT * FROM students WHERE (studentClass LIKE 'FORM VI-%') "
					    		+ " AND NOT (status='TRANSFERRED' OR status='DISABLED' OR status='COMPLETE')";
					    rs=st.executeQuery(select);
					  //  count6=0;
					    while(rs.next()){
							   count6++;
							 
						   }
						 
						   //graph getdata here
					    
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{
						 try{
					            st.close();
					            rs.close();
					            con.close();
					        }catch(Exception e){
					           // JOptionPane.showMessageDialog(null, "ERROR CLOSE");
					        }
					    
					}*/
		  		 //*************888
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
    void goInformation(ActionEvent event) {
    	BorderPane infopane = null;
    	try{
    		FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("LoadStudents.fxml"));
			 infopane = loader.load();
    	}catch(Exception e){ 
    		e.printStackTrace();
    	}

		/*
		 * 
		 */	JFXDialogLayout content = new JFXDialogLayout();
	    	content.setHeading(new Text("Student Information"));
	    	content.setBody(infopane);
	    	content.setStyle("-fx-background-color: #84C7D2");
	    	
	    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
	    	JFXButton bt = new JFXButton("Done");
	    	JFXButton bt1 = new JFXButton("Cancel");
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
	
    	
    }
    @FXML
    void goSetting(ActionEvent event) {
    	AnchorPane infopane = null;
    	try{
    		FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Setting.fxml"));
			 infopane = loader.load();
    	}catch(Exception e){ 
    		e.printStackTrace();
    	}

		/*
		 * 
		 */
    	Text tex = new Text("APS configuration");
    	tex.setStyle("-fx-text-fill: white");
    	JFXDialogLayout content = new JFXDialogLayout();
	    	content.setHeading(tex);
	    	content.setBody(infopane);
	    	content.setStyle("-fx-background-color: #84C7D2");
	    	
	    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
	    	JFXButton bt = new JFXButton("Done");
	    	JFXButton bt1 = new JFXButton("Cancel");
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
	
    	
    }
    @FXML
    void goPrint(ActionEvent event) {

    	BorderPane infopane = null;
    	try{
    		FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("innerview/LoadPrint.fxml"));
			 infopane = loader.load();
    	}catch(Exception e){ 
    		e.printStackTrace();
    	}

		/*
		 * 
		 */	JFXDialogLayout content = new JFXDialogLayout();
	    	content.setHeading(new Text("Choose data to print"));
	    	content.setBody(infopane);
	    	content.setStyle("-fx-background-color: #84C7D2");
	    	
	    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
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
	    
		/*
		 * 
		 */
	
    }
    void goInformation() {
    	BorderPane infopane = null;
    	try{
    		FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("LoadStudents.fxml"));
			 infopane = loader.load();
    	}catch(Exception e){ 
    		e.printStackTrace();
    	}

		/*
		 * 
		 */	JFXDialogLayout content = new JFXDialogLayout();
	    	content.setHeading(new Text("Student Information"));
	    	content.setBody(infopane);
	    	content.setStyle("-fx-background-color: #84C7D2");
	    	
	    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
	    	JFXButton bt = new JFXButton("Done");
	    	JFXButton bt1 = new JFXButton("Cancel");
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
	
    	
    }
    @FXML
    void goSyslogScene(ActionEvent event) {
    	try {
			main.showSyslogScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void goUpload(ActionEvent event) {
    	try {
			main.showUploadScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void goRegister(ActionEvent event) {
    	try {
			Main.showRegisterScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void goReport(ActionEvent event) {
    	try {
			Main.showReportScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   @FXML
   void goComp(ActionEvent event){
	   TrayNotification tray = new TrayNotification();
	       tray.setNotificationType(NotificationType.NOTICE);
	       tray.setTitle("upgrade  APS to access this feature");
	       tray.setMessage("this feature is available in the Premium package ");
	       tray.setAnimationType(AnimationType.SLIDE);
	       tray.showAndDismiss(Duration.millis(3000));
   }

    @FXML
    void goPosi(ActionEvent event) {
    	   TrayNotification tray = new TrayNotification();
	       tray.setNotificationType(NotificationType.NOTICE);
	       tray.setTitle("upgrade  APS to access this feature");
	       tray.setMessage("this feature is available in the Premium package ");
	       tray.setAnimationType(AnimationType.SLIDE);
	       tray.showAndDismiss(Duration.millis(3000));
    }
    @FXML
    void goSumm(ActionEvent event) {
    	AnchorPane infopane = null;
    	try{
    		FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Summary.fxml"));
			 infopane = loader.load();
    	}catch(Exception e){ 
    		e.printStackTrace();
    	}

		/*
		 * 
		 */	JFXDialogLayout content = new JFXDialogLayout();
	    	content.setHeading(new Text(""));
	    	content.setBody(infopane);
	    	content.setStyle("-fx-background-color: #84C7D2");
	    	
	    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
	    	JFXButton bt = new JFXButton("Done");
	    	JFXButton bt1 = new JFXButton("Cancel");
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
	
    	
    
    
    
    }
    @FXML
    void goTimeTable(ActionEvent event) {
    	 TrayNotification tray = new TrayNotification();
	       tray.setNotificationType(NotificationType.NOTICE);
	       tray.setTitle("timetable feature is not activated");
	       tray.setMessage("this feature will be available soon");
	       tray.setAnimationType(AnimationType.FADE);
	       tray.showAndDismiss(Duration.millis(3000));
    }
   public void goReport() {
    	try {
			Main.showReportScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	   @FXML
	    void goUpdate(ActionEvent event) {
	    	try {
				Main.showUpdateScene();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	   void goUpdate() {
	    	try {
				Main.showUpdateScene();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	static String getMe(){
		return user;
	}
	private void logOut() throws IOException {
		// TODO Auto-generated method stub
		Main.showLogOut();
	}
	private void goRegister() throws IOException {
		// TODO Auto-generated method stub
		Main.showRegisterScene();
	}
	   @FXML
	    void goAbout(ActionEvent event) {
	    	AnchorPane infopane = null;
	    	try{
	    		FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("About.fxml"));
				 infopane = loader.load();
	    	}catch(Exception e){ 
	    		e.printStackTrace();
	    	}

			/*
			 * 
			 */	JFXDialogLayout content = new JFXDialogLayout();
		    	content.setHeading(new Text("About APS"));
		    	content.setBody(infopane);
		    	content.setStyle("-fx-background-color: #84C7D2");
		    	
		    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
		    	JFXButton bt = new JFXButton("Done");
		    	JFXButton bt1 = new JFXButton("Cancel");
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
		
	    	
	    
	    
	    }

	    @FXML
	    void goFaq(ActionEvent event) {

	    	AnchorPane infopane = null;
	    	try{
	    		FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Faq.fxml"));
				 infopane = loader.load();
	    	}catch(Exception e){ 
	    		e.printStackTrace();
	    	}

			/*
			 * 
			 */	JFXDialogLayout content = new JFXDialogLayout();
		    	content.setHeading(new Text("Frequently Asked Questions -Online"));
		    	content.setBody(infopane);
		    	content.setStyle("-fx-background-color: #84C7D2");
		    	
		    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
		    	JFXButton bt = new JFXButton("Done");
		    	JFXButton bt1 = new JFXButton("Cancel");
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
		
	    	
	    
	    
	    
	    }

	    @FXML
	    void goHead(ActionEvent event) {

	    	AnchorPane infopane = null;
	    	try{
	    		FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Head.fxml"));
				 infopane = loader.load();
	    	}catch(Exception e){ 
	    		e.printStackTrace();
	    	}

			/*
			 * 
			 */	JFXDialogLayout content = new JFXDialogLayout();
		    	content.setHeading(new Text("Head of school"));
		    	content.setBody(infopane);
		    	content.setStyle("-fx-background-color: #84C7D2");
		    	
		    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
		    	JFXButton bt = new JFXButton("Done");
		    	JFXButton bt1 = new JFXButton("Cancel");
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
		
	    	
	    
	    }
	    @FXML
	    void goProfile(ActionEvent event) {


	    	AnchorPane infopane = null;
	    	try{
	    		FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Profile.fxml"));
				 infopane = loader.load();
	    	}catch(Exception e){ 
	    		e.printStackTrace();
	    	}

			/*
			 * 
			 */	JFXDialogLayout content = new JFXDialogLayout();
		    	content.setHeading(new Text("my profile"));
		    	content.setBody(infopane);
		    	content.setStyle("-fx-background-color: #84C7D2");
		    	
		    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
		    	JFXButton bt = new JFXButton("Done");
		    	JFXButton bt1 = new JFXButton("Cancel");
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
		
	    	
	    
	    
	    }
	    @FXML
	    void goViews(ActionEvent event) {

	    	AnchorPane infopane = null;
	    	try{
	    		FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Viewers.fxml"));
				 infopane = loader.load();
	    	}catch(Exception e){ 
	    		e.printStackTrace();
	    	}

			/*
			 * 
			 */	JFXDialogLayout content = new JFXDialogLayout();
		    	content.setHeading(new Text("Updates and Tips - online"));
		    	content.setBody(infopane);
		    	content.setStyle("-fx-background-color: #84C7D2");
		    	
		    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
		    	JFXButton bt = new JFXButton("Done");
		    	JFXButton bt1 = new JFXButton("Cancel");
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
		
	    	
	    
	    
	    
	    }
	    @FXML
	    void goHelp(ActionEvent event) {

	    	AnchorPane infopane = null;
	    	try{
	    		FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Help.fxml"));
				 infopane = loader.load();
	    	}catch(Exception e){ 
	    		e.printStackTrace();
	    	}

			/*
			 * 
			 */	JFXDialogLayout content = new JFXDialogLayout();
		    	content.setHeading(new Text("online Help"));
		    	content.setBody(infopane);
		    	content.setStyle("-fx-background-color: #84C7D2");
		    	
		    	JFXDialog dialog = new JFXDialog(stackbase,content,JFXDialog.DialogTransition.TOP);
		    	JFXButton bt = new JFXButton("Done");
		    	JFXButton bt1 = new JFXButton("Cancel");
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
		
	    	
	    
	    
	    
	    }
	    @FXML
	    void logout(ActionEvent event) throws IOException {
	    	Main.showLogOut();
	    }
	    @FXML
	    void showPop(MouseEvent event) {
	    	//popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, event.getX(), event.getY());
	    }

/*	private void initPopup() {
		JFXButton b1 = new JFXButton("task1");
		JFXButton b2 = new JFXButton("task2");
		JFXButton b3 = new JFXButton("task3");
		b1.setPadding(new Insets(10));
		b2.setPadding(new Insets(10));
		b3.setPadding(new Insets(10));
		VBox box = new VBox(b1,b2,b3);
		popup.setContent(box);
		popup.setSource(setting);
	}*/
	  

}
