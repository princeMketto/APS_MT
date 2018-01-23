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

public class RegisterAdminController implements Initializable{
private Main main;
LoginController lg = new LoginController();
@FXML
private JFXButton btnBack;

@FXML
private JFXTabPane registerTab;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image bac = new Image(getClass().getResourceAsStream("images/back.png"));
		btnBack.setGraphic(new ImageView(bac));
		
		//tabs
		fillTabs();
	}
	
	private void fillTabs() {
		registerTab.setPrefSize(400, 300);
		Tab tabstud = new Tab();
		tabstud.setText("Register student");
		
		Tab tabstaf = new Tab();
		tabstaf.setText("Register staff");
		
		Tab tabsubClas = new Tab();
		tabsubClas.setText("Register Subject/Class");
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("innerview/RegStudent.fxml"));
			BorderPane regstudpane = loader.load();
			tabstud.setContent(regstudpane);
			registerTab.getTabs().add(tabstud);
			
			FXMLLoader loader1 = new FXMLLoader();
			loader1.setLocation(getClass().getResource("innerview/RegStaff.fxml"));
			BorderPane regstaff = loader1.load();
			tabstaf.setContent(regstaff);
			registerTab.getTabs().add(tabstaf);
			
			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(getClass().getResource("innerview/RegSubClass.fxml"));
			BorderPane regsubs = loader2.load();
			tabsubClas.setContent(regsubs);
			registerTab.getTabs().add(tabsubClas);
			
			
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
