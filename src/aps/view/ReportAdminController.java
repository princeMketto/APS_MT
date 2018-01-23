package aps.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;

import aps.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ReportAdminController implements Initializable {
	private Main main;
	LoginController lg = new LoginController();
    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXTabPane reportTab;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image bac = new Image(getClass().getResourceAsStream("images/back.png"));
		btnBack.setGraphic(new ImageView(bac));
		
		fillTabs();
		
	}
	  private void fillTabs() {


			reportTab.setPrefSize(400, 300);
			Tab tabparRep = new Tab();
			tabparRep.setText("Parent report");
			
			Tab tabGen = new Tab();
			tabGen.setText("General result sheet");
			
			Tab tabProg = new Tab();
			tabProg.setText("Progressive sheet(CA)");
			
			Tab tabAnn = new Tab();
			tabAnn.setText("Announcements");
			
			Tab tabContin = new Tab();
			tabContin.setText("CA results");
			try {

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("innerview/ParentRep.fxml"));
				BorderPane regstudpane = loader.load();
				tabparRep.setContent(regstudpane);
				reportTab.getTabs().add(tabparRep);
				
				FXMLLoader loader1 = new FXMLLoader();
				loader1.setLocation(getClass().getResource("innerview/GeneralRep.fxml"));
				BorderPane regstaff = loader1.load();
				tabGen.setContent(regstaff);
				reportTab.getTabs().add(tabGen);
				
				FXMLLoader loader2 = new FXMLLoader();
				loader2.setLocation(getClass().getResource("innerview/ProgRep.fxml"));
				BorderPane regsubs = loader2.load();
				tabProg.setContent(regsubs);
				reportTab.getTabs().add(tabProg);
				
				FXMLLoader loader3 = new FXMLLoader();
				loader3.setLocation(getClass().getResource("innerview/Announcement.fxml"));
				BorderPane regclass = loader3.load();
				tabAnn.setContent(regclass);
				reportTab.getTabs().add(tabAnn);
				
				FXMLLoader loader4 = new FXMLLoader();
				loader4.setLocation(getClass().getResource("innerview/Continous.fxml"));
				BorderPane cont = loader4.load();
				tabContin.setContent(cont);
				reportTab.getTabs().add(tabContin);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		
			
		
	}
	@FXML
	    void goBack(ActionEvent event) {
	if(LoginController.getStats().equals("Admin")){
    		
    		Main.showMainDash();
    	}
	    }
}
