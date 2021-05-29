package layouts;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import DatabaseCM.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class loginController implements Initializable {
	
	@FXML
	private PasswordField passwordText;

	@FXML
	private TextField nameText;

	@FXML
	private Button loginButton, logoutButton;
	
	@FXML
	private Label labelLogin;

	
	Connection con;
	
	public void loginAction(ActionEvent e) throws IOException {
		
        String username = nameText.getText();	
        String password = passwordText.getText();

		try {
			
			if(con==null) {
				System.out.println("Verbindung zur Datenbank fehlgeschlagen!");
			}else {
				
				
				boolean loginSuccess = false;
				loginSuccess = DBConnection.getPerson(username, password);
				
				if(loginSuccess) {
					Stage stage = (Stage)loginButton.getScene().getWindow();
					AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("initialization.fxml")); 
					stage.setScene(new Scene(root));
					stage.sizeToScene();
					stage.setResizable(true);
					stage.show();
					stage.setMinWidth(stage.getWidth());
			        stage.setMinHeight(stage.getHeight());
					
				}else {
					labelLogin.setTextFill(Color.color(1, 0, 0));
					labelLogin.setText("Bitte überprüfen Sie, ob Ihre Anmeldeinformationen wahr sind.");
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			labelLogin.setTextFill(Color.color(0.42, 0.92, 0.46));
			labelLogin.setText("Geben Sie Ihren Benutzernamen und Ihr Passwort ein, um sich anzumelden.");
			con = DBConnection.getInstance().getConn();
			
			
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
