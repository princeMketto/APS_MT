package aps.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ViewersContro implements Initializable {
	  @FXML
	    private WebView webview;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		final WebEngine webEngine = webview.getEngine();
		 webEngine.load("http://www.jcodemix.blogspot.com");
		 webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>(){
			 public void changed(ObservableValue<? extends State> ov, State oldState, State newState){
				 if (newState == State.SUCCEEDED) {
					 
				 }
			 }
		 });
	}
}

