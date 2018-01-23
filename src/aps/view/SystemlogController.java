package aps.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import aps.Main;
import aps.view.innerview.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SystemlogController implements Initializable {
	private WorkIndicatorDialog wd=null;
	ConnectDB database = new ConnectDB();
    private Connection con;
    private ResultSet rs,rs4,rs2,rs3;
    private Statement st,st4,st2,st3;

	   @FXML
	    private JFXButton btnBack;

	    @FXML
	    private TableView<Systemlog> tableview;

	    @FXML
	    private TableColumn<Systemlog, String> usercol;

	    @FXML
	    private TableColumn<Systemlog, String> actioncol;

	    @FXML
	    private TableColumn<Systemlog, String> timecol;

	    @FXML
	    void goBack(ActionEvent event) {
	    	if(LoginController.getStats().equals("Admin")){
	    		
	    		Main.showMainDash();
	    	}
	    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image bac = new Image(getClass().getResourceAsStream("images/back.png"));
		btnBack.setGraphic(new ImageView(bac));
		
		usercol.setCellValueFactory(new PropertyValueFactory<Systemlog, String>("user"));
		actioncol.setCellValueFactory(new PropertyValueFactory<Systemlog, String>("action"));
		timecol.setCellValueFactory(new PropertyValueFactory<Systemlog, String>("time"));
		logs();
	}
	@SuppressWarnings("unchecked")
	public void logs(){
		tableview.getItems().clear();
		con=database.dbconnect();
		wd = new WorkIndicatorDialog(null, "Loading data...");
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
	 				st=con.createStatement();
	 				String log="select *from log";
	 				rs=st.executeQuery(log);
	 				while(rs.next()){
	 					String uza=rs.getString("user");
	 					String act=rs.getString("action");
	 					String tme=rs.getString("time");
	 					tableview.getItems().add(new Systemlog(uza,act,tme));
	 					s=1;
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
