package layouts;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import cabinetController.CabinetMock;
import cabinetController.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class initializationController implements Initializable {
	
	@FXML
	private Button beginButton;
	
	@FXML 
	private Label labelInit;
	
	static Socket sock;
	PrintWriter toServer;
	BufferedReader fromServer;
	
	public String cabinetName;
	public String fullName;
	public String privilege;
	public int failureRate;
	public int ping;
	public int slotNo;
	public String examineeID;
	
	String userInput = "";
	String message = "";
	
	CabinetMock cm;

	private final static initializationController instance = new initializationController();
	
	public initializationController() {
		
	}

    public static initializationController getInstance() {
        return instance;
    }
    
	
	public void begin_b(ActionEvent e){
		try {
			sock = Client.getInstance().getSocket();
			toServer = Client.getInstance().getToServer();
			fromServer = Client.getInstance().getFromServer();
			
			if(sock==null) {
				System.out.println("Socket didnt work!");
				labelInit.setTextFill(Color.color(1, 0, 0));
				labelInit.setText("Server Verbindung gescheitert!");
			}else {
				labelInit.setTextFill(Color.color(0.42, 0.92, 0.46));
				labelInit.setText("Server Verbindung erfolgreich!");
			}
			
			String username = loginController.getInstance().getUsername();
			String privilege = loginController.getInstance().getPrivilege();
			
			if(username.isBlank() ||  username.isEmpty() || privilege.isBlank() ||  privilege.isEmpty()) {
				labelInit.setTextFill(Color.color(1, 0, 0));
				labelInit.setText("Bitte füllen Sie die leere Eingaben!");
			}else {
				
				String strtMsg = "STRT|Klimaschrank 1|"+ username+ "|" + privilege + "|10|3";
				
	        	userInput = strtMsg;
	      	    
	      	    if(userInput!=null) {
	      	    	toServer.println(userInput); // starting the server!
	      	        message = fromServer.readLine();
	      	        if(message!=null) {
	      	        	labelInit.setTextFill(Color.color(0.42, 0.92, 0.46));
	      	        	labelInit.setText(message);
	      	        	
	 	        	        if(message.contains("examinees")) { // initializing an examinee

	 	        	        	Stage stage=(Stage)beginButton.getScene().getWindow();
	 	        	        	AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("register.fxml"));
	    	        			stage.setScene(new Scene(root));
	    	        			stage.sizeToScene();
	    	        			stage.setResizable(true);
	    	        			stage.show();
	    	        			stage.setMinWidth(stage.getWidth());
	    	        	        stage.setMinHeight(stage.getHeight());
	    	        	        
	    	        	        stage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
	    	        	            public void handle(WindowEvent we) {
	    	        	                System.out.println("Stage is closingggggggggg");
	    	        	                try {
		    	        	               
	    	        	                	toServer.println("STOP");
		    	        	               
		    	        	               
	    	        	                }catch(Exception e) {
	    	        	        			e.printStackTrace();
	    	        	        		}
	    	        	            }
	    	        	        });
	    	        			
	 	        	        }else {
	 	        				labelInit.setTextFill(Color.color(1, 0, 0));
	 	        	        	labelInit.setText("Cabinet won't start!");

	 	        	        	
	 	        	        }
	      	        }
	      	    }else {
	    			labelInit.setTextFill(Color.color(1, 0, 0));
	      	    	labelInit.setText("Überprüfen Sie, ob Sie gültige Eingaben gemacht haben!");
	      	    }
			}
		 	    
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		
			
			
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			labelInit.setTextFill(Color.color(0.42, 0.92, 0.46));
			//labelInit.setText("Server wird starten...");
			int port = 17;
			
			cm = new CabinetMock(port);
			cm.startServer();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	    
	}

}