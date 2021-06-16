package layouts;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DatabaseCM.DBConnection;
import DatabaseCM.Geraet;
import cabinetController.Client;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
    private Label currentTempLabel, phaseLabel, expLabel;
	
	@FXML
    private Label testStartLabel, testLabel, testNoLabel, executingLabel, stufeLabel, aktTempLabel;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
    private Button resultButton, testStartButton;
	
	Socket sock;
	PrintWriter toServer;
	BufferedReader fromServer;
	
	String startTemp;
	String currentTemp;
	String goalTemp;
	String goalTime;
	String rate1;
	String rate2;
	
    double progress;
	
	String tempMsg;
	String sMsg;
	int pingSize = 0;
	
	boolean nextTarget = false;
	
	int targetNo = 0;
	
	ObservableList<Geraet> ol;
	

	Timer timer = new Timer();
	
	private final static executionController instance = new executionController();
	
	public executionController() {
		
	}
	
	public void setOl(ObservableList<Geraet> ol) {
		this.ol = ol;
	}
	
	public ObservableList<Geraet> getOl() {
		return ol;
	}
	
    public static executionController getInstance() {
        return instance;
    }
	
	public void setGoalTemp(String gt) {
		this.goalTemp = gt;
	}
	public String getGoalTemp() {
		return goalTemp;
	}
	
	public void setCurrentTemp(String ct) {
		this.currentTemp = ct;
	}
	
	public String getCurrentTemp() {
		return currentTemp;
	}
	
	public void setStartTemp(String st) {
		this.startTemp = st;
	}
	
	public String getStartTemp() {
		return startTemp;
	}
	public void setGoalTime(String gtt) {
		this.goalTime = gtt;
	}
	public String getGoalTime() {
		return goalTime;
	}
	public void setRate1(String r1) {
		this.rate1 = r1;
	}
	public String getRate1() {
		return rate1;
	}
	
	public void setRate2(String r2) {
		this.rate2 = r2;
	}
	public String getRate2() {
		return rate2;
	}
	
	
	int tempCnt = 0;
	
	ArrayList<String> configs;
	
	
	double ct;
	double st;
	double gt;
	
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			//pingSize = validationScreenController.getInstance().sizeOfTable();
			ol = validationScreenController.getInstance().getOl();
			instance.setOl(ol);
			System.out.println("inside exec!");
			for(int m = 0; m<ol.size();m++) {
				System.out.println(m + ". element of ol: " + ol.get(m).getGeraetid());
			}
			int tn = validationScreenController.getInstance().getTestNo();
			testNoLabel.setText(String.valueOf(tn));
			configs = DBConnection.getInstance().getConfig();
			progressBar.setProgress(0);
			sock = Client.getInstance().getSocket();
			toServer = Client.getInstance().getToServer();
			fromServer = Client.getInstance().getFromServer();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void startTest() {
		
		try {
			
			toServer.println("STRTBURNIN");
			fromServer.readLine();
			setStartCondition();
			
			timer.schedule(new askTemp(), 0, 10);
			
			String zeroTarget = "SETTARGET|0.1|20|30|5";
			
			setTarget(zeroTarget);
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		
	}
	
	
	private void setTarget(String target) {
		try {
			// target = SETTARGET
			System.out.println("inside settarget: " + target);
			StringTokenizer tokenizer;
			tokenizer = new StringTokenizer(target, "|");
			
			String targetName = (String) tokenizer.nextElement();
			String goalTemp = (String) tokenizer.nextElement();
			String goalTime = (String) tokenizer.nextElement();
			String rate1 = (String) tokenizer.nextElement();
			String rate2 = (String) tokenizer.nextElement();
			
			
			if(Integer.parseInt(goalTime)>0) {
				
				instance.setStartTemp(goalTemp);
				instance.setGoalTemp(goalTemp);
				instance.setGoalTime(goalTime);
				instance.setRate1(rate1);
				instance.setRate2(rate2);
				
				toServer.println(target);
				fromServer.readLine();
				setExp("Schrank wird auf " + instance.getGoalTemp() + " Grad eingestellt...");
				
				
				
			}else {
				System.out.println("hey");
				System.out.println("do nothing!!!");
				
				//long start = System.currentTimeMillis(); // 
				//long end = 0;
				//long elapsedTime = 0;
				//int a = 0;
			
				int time =  Math.abs(Integer.parseInt(goalTime));
				boolean cont = true;
				while (cont) {
					
					currentTempLabel.setText(String.valueOf(time));
					
					time-=100;
					
				    if (time<=0) {
				    	cont = false;
				        break;
				    }
				    
				}
				

				System.out.println("hey2");
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void pingDevices() {

		toServer.println("STRTPING|25");
		try {
		
			if(fromServer.readLine().contains("Starting STRTPING")) {
				setExp("The devices are being pinged...");
				for(int i=0;i<ol.size();i++) {
					toServer.println("PING|"+(i+1));
					if(fromServer.readLine().contains("NOK")) {
						ol.get(i).setFailed(true);
					}else {
						System.out.println("device okay!");
					}
				}

			}else {
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//toServer.println("STOPPING");
		instance.setOl(ol);
		
		
	}
	
	private void changeLabel(String currentTemp) {
		currentTempLabel.setText(currentTemp);
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
			
			e1.printStackTrace();
		}
		
		
		
	}
	
	
	public void onTestStart(ActionEvent e) {
		
		testStartLabel.setVisible(false);
		testStartButton.setVisible(false);
		testLabel.setVisible(true);
		testNoLabel.setVisible(true);
		executingLabel.setVisible(true);
		progressBar.setVisible(true);
		stufeLabel.setVisible(true);
		expLabel.setVisible(true);
		phaseLabel.setVisible(true);
		aktTempLabel.setVisible(true);
		currentTempLabel.setVisible(true);
		
		startTest();
	}
	
	
	

	public void setStartCondition() {
		
		instance.setStartTemp("0");
		instance.setCurrentTemp("0");
		instance.setGoalTemp("0");
		instance.setGoalTime("0");
		instance.setRate1("0");
		instance.setRate2("0");
	}

	
	
	

	
	class askTemp extends TimerTask {
		
	    public void run() {
	    	Platform.runLater(()->{
		    	try {
		    		
		    		if((Integer.parseInt(instance.getGoalTime()))<=0) {
		    			setExp("Der Schrank wartet " + Math.abs(Integer.parseInt(instance.getGoalTime())) + " Sekunden...");
		    		}else { // goaltime >0
		    			toServer.println("OPERTEMP");
						tempMsg = fromServer.readLine();
						
						Pattern p = Pattern.compile("OPERTEMP-RESP:(-?\\d+\\.\\d+)");
					    Matcher m = p.matcher(tempMsg);
					    m.find();
					    
					    //System.out.println("Matcher result: " + m.group(1));
			    	 	instance.setCurrentTemp(m.group(1));
			    	 	
			    	 	ct = Double.parseDouble(instance.getCurrentTemp());
			    	 	gt = Double.parseDouble(instance.getGoalTemp()); 
			    	 	
			    	 	
			    	 	changeLabel(String.valueOf(ct));
			    	 	
			    	 	
			    	 	 if(tempCnt==0) {
			    	 		 
					    	progress = 0;
					    	progressBar.setProgress(progress);
			    			tempCnt++;
			    			st = ct;
			    	 	 }
			    	 	 
			    	 	if(Double.parseDouble(instance.getGoalTemp())<0) {
					    	
					    	//System.out.print("Goal temp is below zero!");
					    	
					    	double rate = (Double.parseDouble(instance.getRate1()))/100;
							
					    	if(ct<=(gt-(gt*rate)) && ct>=(gt+(gt*rate))) {
					    		
					    		changeLabel("Schrank ist erfolgreich auf " + instance.getGoalTemp() +   " Grad eingestellt!");
					    		progressBar.setProgress(1);
					    		tempCnt=0;
					    		progressBar.setProgress(1);
					    		progress = 0;
					    		
					    		if(targetNo>=10) {
					    			timer.cancel();
					    			progressBar.setProgress(1);
					    			changeLabel("Test beendet!");
				    			}else{
				    				pingDevices();					  //  0,
				    				setTarget(configs.get(targetNo)); //  0, 
				    												  // 70, 
			    					setPhase(targetNo); 			  //  0, 
			    					
	
				    			}
					    		
					    		
				    			targetNo++; 					      //  1, 
				    		
				    			
					    	}else {
					    		
						    	progress = (ct-st)/(gt-st);
						    	progressBar.setProgress(progress);
						    	System.out.println("the progress " + progress);
					    	}
					    }
			    	 	
			    	 	
			    	 	else { // for 0.1 targetno=0;
					    	
							double rate = (Double.parseDouble(instance.getRate1()))/100;
							
					    	if(ct>=(gt-(gt*rate)) && ct<=(gt+(gt*rate))) {
					    		
					    		changeLabel("Schrank ist erfolgreich auf " + instance.getGoalTemp() +   " Grad eingestellt!");
					    		progressBar.setProgress(1);
					    		tempCnt=0;
					    		progressBar.setProgress(1);
					    		progress = 0;
					    		
					    		if(targetNo>=10) {
					    			pingDevices();
					    			timer.cancel();
					    			progressBar.setProgress(1);
					    			changeLabel("Test beendet!");
					    			resultButton.setVisible(true);
				    			}else{
				    				pingDevices();					  //  0,
				    				setTarget(configs.get(targetNo)); //  0, 
				    												  // 70, 
			    					setPhase(targetNo); 			  //  0, 
			    					
	
				    			}
					    		
					    		
				    			targetNo++; 					      //  1, 
				    							      //  1, 
				    			
					    	}else {
						    	progress = Math.abs((ct-st)/(gt-st));
						    	progressBar.setProgress(progress);
						    	System.out.println("the progress " + progress);
					    	}
		    		}
				    	
				    }
				    
		    	} catch (IOException e) {
					e.printStackTrace();
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				}
	    	});
	    }

		
	}



	public void setPhase(int pNo) {
		phaseLabel.setText(String.valueOf(pNo) + "/9");
	}
	public void setExp(String txt) {
		expLabel.setText(txt);
	}

	
}