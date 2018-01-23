package aps.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import aps.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class DrawerController implements Initializable {
	private Main main;
	  @FXML
	    private JFXButton drawrepo;

	    @FXML
	    private JFXButton drawreg;

	    @FXML
	    private JFXButton drawres;

	    @FXML
	    private JFXButton drawinfo;

	    @FXML
	    private JFXButton drawtim;

	    @FXML
	    private JFXButton drawlogout;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image regs = new Image(getClass().getResourceAsStream("images/drawregister.png"));
		drawreg.setGraphic(new ImageView(regs));
		Image rep = new Image(getClass().getResourceAsStream("images/drawerreport.png"));
		drawrepo.setGraphic(new ImageView(rep));
		Image up = new Image(getClass().getResourceAsStream("images/drawerupload.png"));
		drawres.setGraphic(new ImageView(up));
		Image inf = new Image(getClass().getResourceAsStream("images/drawerabout.png"));
		drawinfo.setGraphic(new ImageView(inf));
		
		Image tim = new Image(getClass().getResourceAsStream("images/drawertimetable.png"));
		drawtim.setGraphic(new ImageView(tim));
		Image log = new Image(getClass().getResourceAsStream("images/draweroff2.png"));
		drawlogout.setGraphic(new ImageView(log));
		
		/*drawSale.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
			openSale();
		});*/
	}

	/*private void openSale() {
		try {
			//main.showSaleScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}
