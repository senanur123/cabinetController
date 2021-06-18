package layouts;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import DatabaseCM.DBConnection;
import DatabaseCM.Device;
import cabinetController.Client;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class validationScreenController implements Initializable{


	@FXML
	private Label labelTest, labelDate;

	@FXML
	private Button testButton;
	
	@FXML
	private TableView<Device> slotTable;
	
	@FXML
	private TableColumn<Device, Integer> slotColumn;

	@FXML
	private TableColumn<Device, String> geraeteColumn;
	
	ObservableList<Device> ol;
	
	Socket sock;
	PrintWriter toServer;
	BufferedReader fromServer;
	
	int failure = 25;

	String burninmsg;

	String sMsg;
	String currentTemp;
	
	int testNo;
	
	private final static validationScreenController instance = new validationScreenController();
	
	public validationScreenController() {
		
	}
	public static validationScreenController getInstance() {
		return instance;
	}
	
	public String getCurrentTemp() {
		return currentTemp;
	}
	
	public void setTestNo(int testNo) {
		this.testNo = testNo;
	}
	
	public int getTestNo() {
		return testNo;
	}
	
	public ObservableList<Device> getOl(){
		return ol;
	}
	
	public void setOl(ObservableList<Device> ol){
		this.ol = ol;
	}
	

	public void onTest(ActionEvent e) throws IOException {
		
		toServer.println("STRTPRE|" + failure);
		String msg1 = fromServer.readLine();
		System.out.println("strtpre server msg: " + msg1);
		pingDevices(ol);

	}
	
	String preMsg = "";
	int ping;
	String endPre = "";

	private void pingDevices(ObservableList<Device> ol2) throws IOException {
		
		
		for(int i=0; i<ol.size();i++) { // 0 to 5
			
			String gid=ol.get(i).getDeviceid();
			System.out.println("the gerat to pretest: " + gid);
			preMsg = "PRETST|" + (i+1);
			toServer.println(preMsg);
			String msg = fromServer.readLine();
			
			
			if(msg.contains("NOK")) {
				
				
			}else {
				ol.get(i).setSuccess(true);
			}
			
		}
		
		instance.setOl(ol);
		endPreTest();
		
	}
	
	
	
	private void endPreTest() throws IOException {
		endPre = "ENDPRE";
		toServer.println(endPre);
		String msg = fromServer.readLine();
		if(msg.contains("Ready")) {
			System.out.println("pretest successful!");
			
			
			try {
				Stage stage = (Stage) testButton.getScene().getWindow();
				AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("execution.fxml"));
				stage.setScene(new Scene(root));
				stage.sizeToScene();
				stage.setResizable(true);
				stage.show();
				stage.setMinWidth(stage.getWidth());
		        stage.setMinHeight(stage.getHeight());
				stage.show();

				stage.show();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}else {
			System.out.println("pretest failed!");
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		labelDate.setText(java.time.LocalDate.now().toString());
		int test = DBConnection.getInstance().getTestNo(); 
		
		validationScreenController.getInstance().setTestNo(test);
		labelTest.setText(String.valueOf(test));
		
		slotColumn.setCellValueFactory(new PropertyValueFactory<>("slotno"));
		geraeteColumn.setCellValueFactory(new PropertyValueFactory<>("deviceid"));
		
		ol = registerController.getInstance().getOl(); 
		slotTable.getItems().addAll(ol);
		
		sock = Client.getInstance().getSocket();
		toServer = Client.getInstance().getToServer();
		fromServer = Client.getInstance().getFromServer();
	}

}
