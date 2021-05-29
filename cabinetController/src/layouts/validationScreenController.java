package layouts;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import DatabaseCM.Geraet;
import cabinetController.Client;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class validationScreenController implements Initializable{


	@FXML
	private Label labelTest, labelDate;

	@FXML
	private Button testButton;
	
	@FXML
	private TableView<Geraet> slotTable;
	
	@FXML
	private TableColumn<Geraet, Integer> slotColumn;

	@FXML
	private TableColumn<Geraet, String> geraeteColumn;
	
	static ObservableList<Geraet> ol;
	
	Socket sock;
	PrintWriter toServer;
	BufferedReader fromServer;
	
	int failure = 0;
	
	static ObservableList<Geraet> olState;
	

	public void onTest(ActionEvent e) throws IOException {
		
		toServer.println("STRTPRE|" + failure);
		String msg1 = fromServer.readLine();
		pingDevices(ol);

	}
	
	String preMsg = "";
	int ping;
	String endPre = "";

	private void pingDevices(ObservableList<Geraet> ol2) throws IOException {
		ping = initializationController.getInstance().getPing();
		
		for(int i=1; i<ol.size()+1;i++) {
			
			String gid=ol.get(i-1).getGeraetid();
			preMsg = "PRETST|" + i;
			toServer.println(preMsg);
			long start = System.nanoTime();
			String msg = fromServer.readLine();
			long finish = System.nanoTime();
			long timeElapsed = (finish - start)/1000000;
			System.out.println("msg from server : " + msg);
			
			if(msg.contains("N")) {
				// NOK
				System.out.println("device failed");
				ol.remove(i);
			}else{
				// OK
			
				if(timeElapsed>ping) {
					ol.remove(i);
				}
			}
			
		}
		
		
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
		
		
		slotColumn.setCellValueFactory(new PropertyValueFactory<>("slotno"));
		geraeteColumn.setCellValueFactory(new PropertyValueFactory<>("geraetid"));
		
		ol = registerController.getInstance().getOl();
		slotTable.getItems().addAll(ol);
		
		sock = Client.getInstance().getSocket();
		toServer = Client.getInstance().getToServer();
		fromServer = Client.getInstance().getFromServer();
	}

}
