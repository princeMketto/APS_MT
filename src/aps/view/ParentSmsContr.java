package aps.view;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ParentSmsContr implements Initializable{
	  @FXML
	    private JFXTextField num;

	    @FXML
	    private JFXTextArea mess;

	    @FXML
	    private Label name;

	    @FXML
	    private JFXButton btnSend;
	    String []par;
	   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		par = LoadStudent.getParData().toString().split("-");
		
		name.setText(par[0]);
		num.setText(par[1]);
	}
	 @FXML
	    void send(ActionEvent event) {

	    }
}
