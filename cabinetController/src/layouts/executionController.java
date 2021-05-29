package layouts;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cabinetController.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class executionController implements Initializable {
	
	
	@FXML
    private Label labelTest, startTime, remainedTime, labelCurrent;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
    private Button resultButton;
	
	Socket sock;
	PrintWriter toServer;
	BufferedReader fromServer;
	
	String currentTemp = "0";
	String tempMsg;
	
	String sMsg;
	String goalTemp = "70";
	String targetMsg1 = "SETTARGET|70.5|180|3|5";
	Timer timer = new Timer();
	Timer timer2 = new Timer();
	
	double diff = 1000;
	
	class progress extends TimerTask{
		public void run() {
			
		}
	}
	
	class askTemp extends TimerTask {
	    public void run() {
	    	
	    	try {
	    		toServer.println("OPERTEMP");
				tempMsg = fromServer.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
		    Pattern p = Pattern.compile("OPERTEMP-RESP:(\\d+\\.\\d+)");
		    Matcher m = p.matcher(tempMsg);
		    m.find();
		    currentTemp = m.group(1);
		    System.out.println("currentTemp: "+currentTemp);
		    changeLabel(currentTemp);
		    diff = (Double.parseDouble(goalTemp)-Double.parseDouble(currentTemp))/100;
		    System.out.println("the diff: " + diff);
		    progressBar.setProgress(1 - ((Double.parseDouble(goalTemp)-Double.parseDouble(currentTemp))/100));
	    }
	}
	
	class stopTarget extends TimerTask {
		public void run() {
			if(diff<=0.03) {
				timer.cancel();
			}
		}
	}
	public void changeLabel(String ctemp) {
		labelCurrent.setText(ctemp);
	}
	
	public String askForTemp() {
		try {
			toServer.println("OPERTEMP");
			tempMsg = fromServer.readLine();
			
			Pattern p = Pattern.compile("OPERTEMP-RESP:(\\d+\\.\\d+)");
		    Matcher m = p.matcher(tempMsg);
		    m.find();
		    currentTemp = m.group(1);
		    System.out.println("currentTemp: "+currentTemp);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return currentTemp;
		
	}
	public void onResult(ActionEvent e) {
		

		try {
			Stage stage = (Stage) resultButton.getScene().getWindow();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("testResults.fxml"));
			stage.setScene(new Scene(root));
			stage.sizeToScene();
			stage.setResizable(true);
			stage.show();
			stage.setMinWidth(stage.getWidth());
	        stage.setMinHeight(stage.getHeight());
			stage.show();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		resultButton.setVisible(false);
		
		sock = Client.getInstance().getSocket();
		toServer = Client.getInstance().getToServer();
		fromServer = Client.getInstance().getFromServer();
		startTest();
		
		
	}
	
	private void firstT() {
		try {
			
			toServer.println(targetMsg1);
			String firstServerMsg = fromServer.readLine();
			System.out.println("first target: " + firstServerMsg);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

	private void startTest() {
		firstT();
		timer.schedule(new askTemp(), 0, 5000);
		timer2.schedule(new stopTarget(), 0, 5000);
		try {
			currentTemp = askForTemp();
			
			System.out.println("current: " + currentTemp);
			System.out.println("goal: " + goalTemp);
			
			
		/*
			
			if((Double.parseDouble(currentTemp) >= (Double.parseDouble(goalTemp)-3) )|| (Double.parseDouble(currentTemp) <= (Double.parseDouble(goalTemp)+3) )) {
				timer.cancel();
				System.out.println("reached the first goal!");
				resultButton.setVisible(true);
			}else {
				System.out.println("not yet!");
			}
			*/
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	

	
	
	

}
