package layouts;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class initializationController {

	@FXML
	private Button beginButton;

	public void begin_b(ActionEvent e){
		
		Stage stageold=(Stage)beginButton.getScene().getWindow();
        stageold.close();
		

		try {
			Stage stage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("startPage.fxml"));
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
