package layouts;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	@FXML
	private Label labelLogin;

	
	Connection con = null;
	
	public void loginAction(ActionEvent e) throws IOException {
		
		
	
        String username = nameText.getText();	
        String password = passwordText.getText();

		try {
			
			if(con==null) {
				System.out.println("Verbindung wurde nicht hergestellt!");
			}else {
				
				//System.out.println("Connection kuruldu!");
				Stage stage = (Stage)loginButton.getScene().getWindow();
				
				ArrayList<String> array = DBConnection.showTableperson();
				System.out.println("username: " + array.get(3));
				if(username.equals(array.get(3)) && password.equals(array.get(4))) {
					
					System.out.println("The username pass is correct!");
					
					AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("initialization.fxml")); 
					stage.setScene(new Scene(root));
					stage.setTitle("Klimaschranksteuerer");
					stage.show();
					
					
				}else {
					labelLogin.setText("Check!");
				}
				
			
				
				
				
			}
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			
			labelLogin.setText("Give your username and password in order to continue!");
			con = DBConnection.getConnection();
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
