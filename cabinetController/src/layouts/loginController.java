package layouts;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class loginController implements Initializable {

	@FXML
	private PasswordField passwordText;

	@FXML
	private TextField nameText;

	@FXML
	private Button loginButton;

	@FXML
	private Button logoutButton;

	public void login_b(ActionEvent e) throws IOException {
		
		
		Stage stageold=(Stage)loginButton.getScene().getWindow();
        stageold.close();
		

		try {
			Stage stage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("initialization.fxml"));
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

		/*
		 * stage = new Stage(); Parent root =
		 * FXMLLoader.load(getClass().getResource("Initialisierung.fxml")); Scene scene
		 * = new Scene(root, 400,600); stage.setTitle("Klimaschranksteuerer");
		 * stage.setWidth(600); stage.setHeight(400); stage.setResizable(false);
		 * stage.setScene(scene); stage.show();
		 */

	}

	public void logout_b(ActionEvent e) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
