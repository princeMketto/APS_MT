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

public class UpdateAdminController implements Initializable{
	private Main main;
	LoginController lg = new LoginController();
	@FXML
	private JFXButton btnBack;

	@FXML
	private JFXTabPane updateTab;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image bac = new Image(getClass().getResourceAsStream("images/back.png"));
		btnBack.setGraphic(new ImageView(bac));
		
		fillTabs();
		
	}
	private void fillTabs() {

		updateTab.setPrefSize(400, 300);
		Tab tabstud = new Tab();
		tabstud.setText("update student data");
		
		Tab tabstaf = new Tab();
		tabstaf.setText("update staff data");
		
		Tab tabsubject = new Tab();
		tabsubject.setText("update Subject data");
		
		Tab tabClass = new Tab();
		tabClass.setText("update Class data");
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("innerview/UpStudent.fxml"));
			BorderPane regstudpane = loader.load();
			tabstud.setContent(regstudpane);
			updateTab.getTabs().add(tabstud);
			
			FXMLLoader loader1 = new FXMLLoader();
			loader1.setLocation(getClass().getResource("innerview/UpStaff.fxml"));
			BorderPane regstaff = loader1.load();
			tabstaf.setContent(regstaff);
			updateTab.getTabs().add(tabstaf);
			
			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(getClass().getResource("innerview/UpSubject.fxml"));
			BorderPane regsubs = loader2.load();
			tabsubject.setContent(regsubs);
			updateTab.getTabs().add(tabsubject);
			
			FXMLLoader loader3 = new FXMLLoader();
			loader3.setLocation(getClass().getResource("innerview/UpClass.fxml"));
			BorderPane regclass = loader3.load();
			tabClass.setContent(regclass);
			updateTab.getTabs().add(tabClass);
			
			
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
