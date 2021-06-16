package cabinetController;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
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
		/*
		CabinetMock cm = new CabinetMock(17);
		
		cm.startServer();
		
		sock = Client.getInstance().getSocket();
		toServer = Client.getInstance().getToServer();
		fromServer = Client.getInstance().getFromServer();
		
		ArrayList<Integer> al = new ArrayList<>();
		String strtMsg = "STRT|Klimaschrank 1|senanur|admin|10|3";
		try {
			toServer.println(strtMsg);
			fromServer.readLine();
			toServer.println("INIT|1| PRFLG-154879855678");
			fromServer.readLine();
			toServer.println("INIT|2| PRFLG-154879855679");
			fromServer.readLine();
			toServer.println("INIT|3| PRFLG-154879855680");
			fromServer.readLine();
			toServer.println("INIT|4| PRFLG-154879855681");
			fromServer.readLine();
			toServer.println("INIT|5| PRFLG-154879855682");
			fromServer.readLine();
			toServer.println("ENDINIT");
			fromServer.readLine();
			
			toServer.println("STRTPRE|25");
			fromServer.readLine();
			
			for(int i=1; i<=5; i++) {
				toServer.println("PRETST|" + String.valueOf(i));
				String msg = fromServer.readLine();
				if(msg.contains("NOK")) {
					System.out.println("faulty examinee!");
				}else {
					al.add(i);
				}
			}
			
			System.out.println("the al size: " + al.size());
			
			for(int j=0; j<al.size(); j++) {
				
				System.out.println("als " + j + "th. element: " + al.get(j) );
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		toServer.println("PRETST|2");
		toServer.println("PRETST|3");
		toServer.println("PRETST|4");
		toServer.println("PRETST|5");
		
		toServer.println("ENDPRE");
		
		toServer.println("STRTBURNIN");
		
		*/
		
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
