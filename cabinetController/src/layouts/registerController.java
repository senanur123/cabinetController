package layouts;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class registerController {

	@FXML
	private Button plusButton, minusButton,  uberprufenButton; 
	
	@FXML
    private TextField slotNummerText, GerätText;
	
	@FXML
    private Label slotnummer1, slotnummer2;
	
	@FXML
    private Label geratid1, geratid2;
	
	
	
	
	public void plus_b(ActionEvent e) {

	}
	
	public void minus_b(ActionEvent e) {

	}
	
	public void uberprufen_b(ActionEvent e) {

		Stage stageold = (Stage) uberprufenButton.getScene().getWindow();
		stageold.close();

		try {
			Stage stage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("validationPage.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("Klimaschranksteuerer");
			stage.setWidth(600);
			stage.setHeight(400);
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node) e.getSource()).getScene().getWindow());

			stage.show();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	
	
	
	
}
