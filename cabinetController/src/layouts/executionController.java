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
    private Label labelTest, labelCurrent, phaseLabel, expLabel;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
    private Button resultButton;
	
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
	
	boolean nextTarget = false;
	
	int targetNo = -1;
	

	Timer timer = new Timer();
	Timer timer2 = new Timer();
	Timer timer3 = new Timer();
	
	private final static executionController instance = new executionController();
	
	public executionController() {
		
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
	
	class askTemp extends TimerTask {
		
	    public void run() {
	    	Platform.runLater(()->{
		    	try {
		    		
		    		System.out.println("TIMER START!");
		    		toServer.println("OPERTEMP");
					tempMsg = fromServer.readLine();
					
					Pattern p = Pattern.compile("OPERTEMP-RESP:(-?\\d+\\.\\d+)");
				    Matcher m = p.matcher(tempMsg);
				    m.find();
				    
			    	System.out.println("Matcher result: " + m.group(1));
		    	 	instance.setCurrentTemp(m.group(1));
		    	 	
		    	 	ct = Double.parseDouble(instance.getCurrentTemp());
		    	 	gt = Double.parseDouble(instance.getGoalTemp()); 
		    	 	
				    System.out.print("currentTemp1: "+ct);
				    System.out.println("\tgoalTemp1: "+gt);
				    
				    changeLabel(String.valueOf(ct));
				    
				    if(tempCnt==0) {
				    	progress = 0;
				    	System.out.println("start temp to first temp of the cabinet: " + ct);
		    			tempCnt++;
		    			st = ct;
		    		}
				    
				    if(Double.parseDouble(instance.getGoalTemp())<0) {
				    	
				    	System.out.print("Goal temp is below zero!");
				    	
				    	double rate = (Double.parseDouble(instance.getRate1()))/100;
						
				    	if(ct<=(gt-(gt*rate)) && ct>=(gt+(gt*rate))) {
				    		targetNo++;
				    		nextTarget = true;
				    		System.out.println("Progress more than 1");
				    		progressBar.setProgress(1);
				    		changeLabel("Cabinet is set to " + instance.getGoalTemp() + " degrees successfully!");
			    			timer.cancel();
			    			pingDevices();
			    			
			    			progressBar.setProgress(0);
			    			tempCnt=0;
			    			
			    			System.out.println("targetno is now : " + targetNo);
			    			
				    	}else {
				    		nextTarget = false;
					    	System.out.println("Goal bigger than 0!");
					    	progress = (ct-st)/(gt-st);
					    	progressBar.setProgress(progress);
				    		System.out.println("the progress " + progress);
				    	}
				    }else {
				    	
						double rate = (Double.parseDouble(instance.getRate1()))/100;
						
				    	if(ct>=(gt-(gt*rate)) && ct<=(gt+(gt*rate))) {
				    		targetNo++;
				    		nextTarget = true;
				    		System.out.println("Progress more than 1");
				    		progressBar.setProgress(1);
				    		changeLabel("Cabinet is set to " + instance.getGoalTemp() + " degrees successfully!");
			    			timer.cancel();
			    			pingDevices();
			    			
			    			progressBar.setProgress(0);
			    			tempCnt=0;
			    			
			    			System.out.println("targetno is now : " + targetNo);
			    			
				    	}else {
				    		nextTarget = false;
					    	System.out.println("Goal bigger than 0!");
					    	progress = (ct-st)/(gt-st);
					    	progressBar.setProgress(progress);
				    		System.out.println("the progress " + progress);
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
		phaseLabel.setText(String.valueOf(pNo) + "/11");
	}
	public void setExp(String txt) {
		expLabel.setText(txt);
	}
	//ObservableList<Integer> olSuccess;
	
	public void pingDevices() {
		System.out.println("heyyy PING");
		toServer.println("STRTPING|25");
		try {
			//System.out.println("after strtping: " + fromServer.readLine());
			if(fromServer.readLine().contains("Starting STRTPING")) {
				setExp("The devices are being pinged...");
				for(int i=1;i<=20;i++) {
					toServer.println("PING|"+i);
					if(fromServer.readLine().contains("NOK")) {
					}else {
						// olSuccess.add(i);
					}
				}
				//setTarget(configs.get(targetNo));
				
				
			}else {
				System.out.println("STRTPING failed!");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(resultReg) {
			
			//TODO: results records to DATABASE!
			resultButton.setVisible(true);
		}
		
		
	}
	
	private void changeLabel(String currentTemp) {
		labelCurrent.setText(currentTemp);
	}

	boolean resultReg = false;
	
	class startNext extends TimerTask {
		public void run() {
			Platform.runLater(()->{
				if(nextTarget) {
					System.out.println("Next target to run!");
					if(targetNo>=10) {
						timer.cancel();
						timer3.cancel();
						resultReg = true;
					}else {
						setPhase(targetNo+2);
						setTarget(configs.get(targetNo));
					}
					
					
				}
				
			});
		}
		
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
	
	int pingSize = 0;
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			//pingSize = validationScreenController.getInstance().sizeOfTable();

			labelTest.setText(String.valueOf(validationScreenController.getInstance().getTestNo()));
			configs = DBConnection.getInstance().getConfig();
			resultButton.setVisible(false);
			progressBar.setProgress(0);
			sock = Client.getInstance().getSocket();
			toServer = Client.getInstance().getToServer();
			fromServer = Client.getInstance().getFromServer();
			startTest();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void setTarget(String target) {
		try {
			
			
			timer.cancel();
			System.out.println("inside settarget!");
			StringTokenizer tokenizer;
			tokenizer = new StringTokenizer(target, "|");
			
			String name = (String) tokenizer.nextElement();
			String goalTemp = (String) tokenizer.nextElement();
			String goalTime = (String) tokenizer.nextElement();
			String rate1 = (String) tokenizer.nextElement();
			String rate2 = (String) tokenizer.nextElement();
			
			
			
			if(Integer.parseInt(goalTime)<0) {
				nextTarget = false;
				setExp("The cabinet will wait for " + Math.abs(Integer.parseInt(goalTime)) + "seconds...");
				
				System.out.println("is it sleeping?");
				timer.cancel();
				
				long start = System.currentTimeMillis(); // 
				long end = 0;
				long elapsedTime = 0;
				int a = 0;
				boolean cont = true;
				while (cont) {
					
					end = System.currentTimeMillis();
					elapsedTime = end - start;
					// System.out.println("elapsed: " + elapsedTime);
				    if (elapsedTime >= Math.abs(Integer.parseInt(goalTime)) ) {
				    	System.out.println("elapsed: " + elapsedTime);
				    	cont = false;
				    	targetNo++;
				    	nextTarget = true;
				        break;
				    }
				    
				    if(a<5) {
				    	System.out.println("a : " + a);
				    	a++;
				    }
				}
				
				//Thread.sleep(Math.abs(Integer.parseInt(goalTime)));
				System.out.println("done sleeping!");
			}else {
				
				timer = new Timer();
				timer.schedule(new askTemp(), 0, 200);
				nextTarget = false;
				instance.setStartTemp(goalTemp);
				instance.setGoalTemp(goalTemp);
				instance.setGoalTime(goalTime);
				instance.setRate1(rate1);
				instance.setRate2(rate2);
				
				toServer.println(target);
				String msf = fromServer.readLine();
				System.out.println(msf);
				setExp("Cabinet is being set to " + instance.getGoalTemp() + "degrees...");
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void setTimers() {
		
		System.out.println("INSIDE SETTIMERS. RIGHT NOW: ");
		System.out.println("st: " + instance.getStartTemp());
		System.out.println("ct: " + instance.getCurrentTemp());
		System.out.println("gt: " + instance.getGoalTemp());
		System.out.println("gt2: " + instance.getGoalTime());
		System.out.println("r1: " + instance.getRate1());
		System.out.println("r2: " + instance.getRate2());		
	}

	public void setStartCondition() {
		
		instance.setStartTemp("0");
		instance.setCurrentTemp("0");
		instance.setGoalTemp("0");
		instance.setGoalTime("0");
		instance.setRate1("0");
		instance.setRate2("0");
	}

	
	private void startTest() {
		
		try {

			setStartCondition();
			
			
			timer.schedule(new askTemp(), 0, 200);
			timer3.schedule(new startNext(), 0, 3000);
			String zeroTarget = "SETTARGET|0.5|50|30|5";
			setTarget(zeroTarget);
			setPhase(1);
			nextTarget = false;
			//System.out.println("zero target is accomplished!");
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		
	}
}