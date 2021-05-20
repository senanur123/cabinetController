package layouts;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class testResultsController2 {
	
	@FXML
    private Label slotnummerT1, slotnummerT2, slotnummerT3, slotnummerT4;
	
	@FXML
    private Label geratidT1, geratidT2, geratidT3, geratidT4;
	
	@FXML
    private Label statusText_1, statusText_2,statusText_3, statusText_4;
	
	@FXML
	private Button zuruckButton;
	
	public void zuruck_b(ActionEvent e) {
		
		Stage stageold = (Stage) zuruckButton.getScene().getWindow();
		stageold.close();

		try {
			Stage stage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("loginPage.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("Klimaschranksteuerer");
			stage.setFullScreen(true);
			stage.setResizable(true);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node) e.getSource()).getScene().getWindow());

			stage.show();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
