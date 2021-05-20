package layouts;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DatabaseCM.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	@FXML
	private Label labelLogin;

	public void login_b(ActionEvent e) throws IOException {
		
		Connection con = null;
	
		Stage stage=(Stage)loginButton.getScene().getWindow();
        String username = nameText.getText();	
        String password = passwordText.getText();

		try {
			
			
			con = DBConnection.getConnection();
			if(con==null) {
				System.out.println("Connection hatalý!");
			}else {
				
				// TODO: login username ve pass kontrol edilcek doðruysa 
				labelLogin.setText("Connection kuruldu!");
				//System.out.println("Connection kuruldu!");
			
				
				
				ArrayList<String> array = DBConnection.showTableperson();
				System.out.println("username: " + array.get(3));
				if(username.equals(array.get(3)) && password.equals(array.get(4))) {
					labelLogin.setText("Doðru");
					
					AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("initialization.fxml")); // eðer login baþarýlýysa çalýþacak
					stage.setScene(new Scene(root));
					stage.setTitle("Klimaschranksteuerer");
					stage.sizeToScene();
					stage.setResizable(true);
					//stage.initModality(Modality.WINDOW_MODAL);
					//stage.initOwner(((Node) e.getSource()).getScene().getWindow());

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

	public void logout_b(ActionEvent e) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
