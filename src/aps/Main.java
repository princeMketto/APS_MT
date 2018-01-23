package aps;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import aps.Main;
import aps.view.ConnectDB;
import aps.view.LoginController;
import aps.view.WorkIndicatorDialog;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


public class Main extends Application {
//	LoginController lg = new LoginController();
	 private PreparedStatement prep;
		private static Stage primaryStage;
		private static BorderPane mainLayout;
		public static BorderPane mainItems;
		StackPane anchor = new StackPane();
		int s=0,f=0,e=0;
		boolean su,al=false;
		private WorkIndicatorDialog wd=null;
		 String head,content;
		 ConnectDB database = new ConnectDB();
		 private Connection con;
		    private ResultSet rs;
		    private Statement st;
		    static String infos;
		    
	@Override
	public void start(Stage primaryStage) throws IOException {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		Image icon = new Image(getClass().getResourceAsStream("view/images/logo.png"));
		primaryStage.getIcons().add(icon);
		Main.primaryStage=primaryStage;
		Main.primaryStage.setTitle("Aps");
		showMainView();
		//checkAlert();
	}
	 public static void setNode(Node node) {
	        /* AudioClip notifyMe = new AudioClip((getClass().getResource("Notify.wav")).toString());
	        notifyMe.play();
	         */

		// mainItems.getChildren().clear();
		 
		 // anchorPane.getChildren().add((Node) node);
		 mainItems.setCenter(node);;

	        FadeTransition ft = new FadeTransition(Duration.millis(2000));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();

	    }

		private void showMainView() throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Mainview.fxml"));
			mainLayout = loader.load();
			Scene scene = new Scene(mainLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		public static void showMainDash(){
			try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainDash.fxml"));
			 mainItems = loader.load();
			//mainLayout.setCenter(mainItems);
			 
			Scene scene = new Scene(mainItems,primaryStage.getWidth(),primaryStage.getHeight());
			primaryStage.setScene(scene);
			primaryStage.show();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		public static void showReportScene() throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/ReportAdmin.fxml"));
			BorderPane regpane = loader.load();
			 setNode(regpane);
		//	mainItems.setCenter(prodpane);
		}
		public static void showUploadScene() throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Upload.fxml"));
			BorderPane regpane = loader.load();
			 setNode(regpane);
		//	mainItems.setCenter(prodpane);
			 
		}
		public static void showUploadUpdateScene() throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/UpdateUpload.fxml"));
			BorderPane regpane = loader.load();
			 setNode(regpane);
		//	mainItems.setCenter(prodpane);
		}
		public static void showSyslogScene() throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Systemlog.fxml"));
			BorderPane regpane = loader.load();
			 setNode(regpane);
		//	mainItems.setCenter(prodpane);
		}
		public static void showRegisterScene() throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RegisterAdmin.fxml"));
			BorderPane regpane = loader.load();
			 setNode(regpane);
		//	mainItems.setCenter(prodpane);
		}
		public static void showUpdateScene() throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/UpdateAdmin.fxml"));
			BorderPane updpane = loader.load();
			 setNode(updpane);
		//	mainItems.setCenter(prodpane);
		}
		public static void showLogOut() throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Mainview.fxml"));
			mainLayout = loader.load();
			Scene scene = new Scene(mainLayout,primaryStage.getWidth(),primaryStage.getHeight());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		@SuppressWarnings("unchecked")
		private  void checkAlert() {
			   wd = new WorkIndicatorDialog(null, "Contacting Server...");
			   wd.addTaskEndNotification(result -> {
		          
		          String outres = result.toString();
		         // System.out.println("nomaa "+outres);
		          if(outres.equals("1")){
		        	  TrayNotification tray = new TrayNotification();
		      	       tray.setNotificationType(NotificationType.NOTICE);
		      	       tray.setTitle(head);
		      	       tray.setMessage(content);
		      	       tray.setAnimationType(AnimationType.POPUP);
		      	       tray.showAndDismiss(Duration.millis(15000));
		          }
		           wd=null; // don't keep the object, cleanup
		        });
		  	 wd.exec("alert", inputParam -> {
		           // Thinks to do...
		           // NO ACCESS TO UI ELEMENTS!
		  		 try{
		    			con= database.dbconnect();
		    		   st= con.createStatement();
		    		   String query = "SELECT * FROM alert ";
		    		   rs=st.executeQuery(query);
		    		   	if(rs.next()){
		    		   		head=  rs.getString("head");
		    		   		content = rs.getString("content");
		    		   		
		    		   		
		    		   		al=true;
		    		  
		    		   	}else{
		    		   		al=false;
		    	
		    		   	}
		    		   }catch(SQLException err){
		    			   err.printStackTrace();
		    			   
		    		   }
		       
		               try {
		                  Thread.sleep(1000);
		               }	
		               catch (InterruptedException er) {
		                  er.printStackTrace();
		               }
		              if(al){
		            	  e = 1;
		              }else{
		            	  e = 0;
		              }
		              
		           return new Integer(e);
		           
		           
		        });
		}
	public static void main(String[] args) {
		launch(args);
	}
}
