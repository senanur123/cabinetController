package layouts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import cabinetController.CabinetMock;
import cabinetController.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class initializationController implements Initializable {
	
	@FXML
	private Button beginButton;
	
	@FXML 
	private Label labelInit;
	
	@FXML
	private TextField cabinetText;
	
	@FXML
	private TextField nameText;
	
	@FXML
	private TextField privText;
	
	@FXML
	private TextField failureText;
	
	@FXML
	private TextField pingText;
	
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

	private final static initializationController instance = new initializationController();
	
	public initializationController() {
		
	}

    public static initializationController getInstance() {
        return instance;
    }
    
    public int getPing() {
		return ping;
	}

	
	public void begin_b(ActionEvent e){
		try {
			sock = Client.getInstance().getSocket();
			toServer = Client.getInstance().getToServer();
			fromServer = Client.getInstance().getFromServer();
			
			if(sock==null) {
				System.out.println("Socket didnt work!");
				labelInit.setText("Server Verbindung gescheitert!");
			}else {
				labelInit.setText("Server Verbindung erfolgreich!");
			}
			
			cabinetName = cabinetText.getText().toString();
			fullName = nameText.getText().toString();
			privilege = privText.getText().toString();
			failureRate = Integer.parseInt(failureText.getText()) ;
			ping = Integer.parseInt(pingText.getText());
			
			String strtMsg = "STRT|" + cabinetName +"|"+ fullName+ "|" + privilege + "|" + failureRate + "|" + ping;

			
        	userInput = strtMsg;
      	    
      	    if(userInput!=null) {
      	    	toServer.println(userInput); // starting the server!
      	        message = fromServer.readLine();
      	        //labelInit.setText("Starting the server...");
      	        if(message!=null) {
      	        	
      	        	labelInit.setText(message);
      	        	
 	        	        if(message.contains("examinees")) { // initializing an examinee
 	        	        	//labelInit.setText("The Page should move on now in  10 secs!");
 	        	        	//Thread.sleep(10000);
 	        	        	Stage stage=(Stage)beginButton.getScene().getWindow();
 	        	        	AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("register.fxml"));
    	        			stage.setScene(new Scene(root));
    	        			stage.sizeToScene();
    	        			stage.setResizable(true);
    	        			stage.show();
    	        			stage.setMinWidth(stage.getWidth());
    	        	        stage.setMinHeight(stage.getHeight());
    	        			
 	        	        }else {
 	        	        	labelInit.setText("Cabinet won't start!");

 	        	        	
 	        	        }
      	        }
      	    }else {
      	    	labelInit.setText("Check that you gave appropriate values!");
      	    }
	 	    
	 	    
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		
		
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			labelInit.setText("Server wird starten...");
			int port = 17;
			CabinetMock cm = new CabinetMock(port);
			cm.startServer();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	    
	}

}