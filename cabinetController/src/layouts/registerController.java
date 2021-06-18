package layouts;


import java.io.BufferedReader;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class registerController implements Initializable {

	@FXML
	private Button registerButton, editButton, startButton, setButton; 
	
	@FXML
	private Label labelSlot, labelMsg;
	
	@FXML
    private TextField geraetText, geraetEdit;
	
	@FXML
	private TableView<Device> slotTable;
	
	@FXML
	private TableColumn<Device, Integer> slotColumn;

	@FXML
	private TableColumn<Device, String> geraeteColumn;
	
	@FXML
	private VBox editVbox;
	
	@FXML
	private ChoiceBox<Integer> slotEdit;
	
	static ObservableList<Device> ol;
	int selectedSlot;
	
	String geraeteid;
	
	String initMsg;
	
	Socket sock;
	PrintWriter toServer;
	BufferedReader fromServer;
	
	int counter = 1;
	
	private final static registerController instance = new registerController();
	
	public registerController () {
		
	}

	 public static registerController getInstance() {
	       return instance;
	 }
	 
	 public ObservableList<Device> getOl() {
		return ol;
	}
	 
	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		labelSlot.setText(String.valueOf(counter));
		editVbox.setVisible(false);
		sock = Client.getInstance().getSocket();
		toServer = Client.getInstance().getToServer();
		fromServer = Client.getInstance().getFromServer();
		
	}
	
	
	public void onEdit(ActionEvent e) {
		editVbox.setVisible(true);
		
	}
	
	public void setChoice(int slottoadd) {
		slotEdit.getItems().add(slottoadd);
	}

	public void onSetEdit(ActionEvent e) {
		slotColumn.setSortType(SortType.ASCENDING);
		slotTable.getSortOrder().add(slotColumn);
		
		int slot = (int) slotEdit.getSelectionModel().getSelectedItem();
		String gerid = geraetEdit.getText();
		
		slotTable.getItems().remove(slot-1);
		slotTable.getItems().add(new Device(slot, gerid));
		
		
		slotColumn.setSortType(SortType.ASCENDING);
		slotTable.getSortOrder().add(slotColumn);
		
	
		
	}

	
	
	public void onRegister(ActionEvent e) {
		slotColumn.setSortType(SortType.ASCENDING);
		slotTable.getSortOrder().add(slotColumn);
		
		if(counter<21) {
			String gid = geraetText.getText();

			if(gid==null || gid.isBlank()) {
				System.out.println("Empty record!");
				labelMsg.setTextFill(Color.color(1, 0, 0));
				labelMsg.setText("Bitte geben Sie die Gerät-ID ein.");
				geraetText.setText(null);
			}else {
				int slot = counter;
				geraetText.setText(null);
				
				if(initDevice(slot, gid)) {
					slotColumn.setCellValueFactory(new PropertyValueFactory<>("slotno"));
					geraeteColumn.setCellValueFactory(new PropertyValueFactory<>("deviceid"));
					slotTable.getItems().add(new Device(counter, gid));
					setChoice(counter);
					counter++;
					labelMsg.setTextFill(Color.color(0.42, 0.92, 0.46));
					labelMsg.setText("Gerät erfolgreich registriert!");
					
				}else {
					slotTable.getItems().remove(counter-1);
					labelMsg.setTextFill(Color.color(1, 0, 0));
					labelMsg.setText("Gerät kann nicht initialisiert werden. Bitte geben Sie eine andere Geräte-ID ein.");
				}
				if(counter==21) {
					labelSlot.setText("");
					labelMsg.setText("Die Kapazität ist voll, starten Sie das Test.");
					geraetText.setEditable(false);
					
				}
				
			}
			
		}
		
		labelSlot.setText(String.valueOf(counter));
		
		slotColumn.setSortType(SortType.ASCENDING);
		slotTable.getSortOrder().add(slotColumn);
		
		
	}
	
	public boolean initDevice(int slotno, String deviceid) {
		try {
			
			if(!DBConnection.getInstance().getInstance().isRegistered(deviceid)) {
				// can be initialised!
				initMsg = "INIT|"+slotno + "|"+deviceid;
				if(sock==null) {
					System.out.println("Socket null!");
					return false;
				}else {
					toServer.println(initMsg); // starting the server!
		  	        String serverMsg = fromServer.readLine();
		  	        
		  	        if(serverMsg.contains("Ignoring")) {
		  	        	labelMsg.setTextFill(Color.color(1, 0, 0));
		  	        	labelMsg.setText("Ein Gerät kann nicht mehrmals registriert werden!");
		  	        	return false;
		  	        }else{
		  	        	return true;
		  	        }
		  	        
				}
			}else {
				labelMsg.setTextFill(Color.color(1, 0, 0));
  	        	labelMsg.setText("Das Gerät war schon getestet!");
  	        	return false;
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	String endInitMsg = "ENDINIT";
	
	public void onStart(ActionEvent e) {
		
			System.out.println("check: " + checkTable());
			if(checkTable()) {
				
				try {
					labelMsg.setTextFill(Color.color(0.42, 0.92, 0.46));
					labelMsg.setText("Alles gut!");
					toServer.println(endInitMsg);
					String msgS = fromServer.readLine();
					
					if(msgS.contains("SLOT")) {
						Stage stage = (Stage) registerButton.getScene().getWindow();
						AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("validationPage.fxml"));
						stage.setScene(new Scene(root));
						stage.sizeToScene();
						stage.setResizable(true);
						stage.show();
						stage.setMinWidth(stage.getWidth());
				        stage.setMinHeight(stage.getHeight());
						stage.show();
					}
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
	}


	private boolean checkTable() {
		ol = slotTable.getItems();
		
		if(ol.isEmpty()) {
			labelMsg.setTextFill(Color.color(1, 0, 0));
			labelMsg.setText("Bitte geben sie mindestens ein Gerät ein!");
			return false;
		}
		
		
		return true;
	}



	
	
	
}
