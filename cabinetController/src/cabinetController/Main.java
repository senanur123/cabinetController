package cabinetController;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;

import DatabaseCM.DBConnection;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	
	static Socket sock;
	static PrintWriter toServer;
	static BufferedReader fromServer;
	
	public static void main(String[] args) {
		launch(args);
		
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/loginPage.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Klimaschranksteuerer");
		stage.sizeToScene();
		stage.setResizable(true);
		stage.show();
		stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        
        
         
	}

	
}
