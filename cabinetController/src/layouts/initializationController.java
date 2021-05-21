package layouts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import cabinetController.CabinetMock;
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
	
	Socket sock;
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
	
	
	public void begin_b(ActionEvent e){
		try {
			sock = new Socket(InetAddress.getLocalHost(), 17);
			toServer = new PrintWriter(sock.getOutputStream(), true);
			fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			if(sock==null) {
				labelInit.setText("Server Verbindung gescheitert!");
				 Thread.sleep(5000);
			}else {
				labelInit.setText("Server Verbindung erfolgreich!");
				 Thread.sleep(5000);
			}
			
			cabinetName = cabinetText.getText().toString();
			fullName = nameText.getText().toString();
			privilege = privText.getText().toString();
			failureRate = Integer.parseInt(failureText.getText()) ;
			ping = Integer.parseInt(pingText.getText());
			
			String strtMsg = "STRT|" + cabinetName +"|"+ fullName+ "|" + privilege + "|" + failureRate + "|" + ping;
        	//System.out.println("Start message: " + strtMsg);
			
        	userInput = strtMsg;
      	    
      	    if(userInput!=null) {
      	    	toServer.println(userInput); // starting the server!
      	        message = fromServer.readLine();
      	        //labelInit.setText("Starting the server...");
      	        if(message!=null) {
      	        	
      	        	labelInit.setText(message);
      	        	
 	        	        if(message.contains("examinees")) { // initializing an examinee
 	        	        	labelInit.setText("The Page should move on now in  10 secs!");
 	        	        	Thread.sleep(10000);
 	        	        	Stage stage=(Stage)beginButton.getScene().getWindow();
 	        	        	AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("startPage.fxml"));
    	        			stage.setScene(new Scene(root));
    	        			
 	        	        }else {
 	        	        	labelInit.setText("Cabinet won't start!");
 	        	        	Thread.sleep(5000);
 	        	        	
 	        	        }
      	        }
      	    }else {
      	    	labelInit.setText("Check that you gave appropriate values!");
      	    	Thread.sleep(5000);
      	    }
	 	    
	 	    
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		
		
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			labelInit.setText("Server wird starten...");
			Thread.sleep(5000);
			int port = 17;
			CabinetMock cm = new CabinetMock(port);
			cm.startServer();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	    
	}
}