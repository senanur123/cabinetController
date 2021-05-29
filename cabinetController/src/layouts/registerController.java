package layouts;


import java.io.BufferedReader;
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
import javafx.stage.Stage;

public class registerController implements Initializable {

	@FXML
	private Button registerButton, editButton, startButton, setButton; 
	
	@FXML
	private Label labelSlot, labelMsg;
	
	@FXML
    private TextField geraetText, geraetEdit;
	
	@FXML
	private TableView<Geraet> slotTable;
	
	@FXML
	private TableColumn<Geraet, Integer> slotColumn;

	@FXML
	private TableColumn<Geraet, String> geraeteColumn;
	
	@FXML
	private VBox editVbox;
	
	@FXML
	private ChoiceBox<Integer> slotEdit;
	
	//List<Integer> list = new ArrayList<Integer>();
	static ObservableList<Geraet> ol;
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
	 
	 public ObservableList<Geraet> getOl() {
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
		/*
		slotColumn.setSortType(SortType.ASCENDING);
		slotTable.getSortOrder().add(slotColumn);
		*/
		int slot = (int) slotEdit.getSelectionModel().getSelectedItem();
		String gerid = geraetEdit.getText();
		
		slotTable.getItems().remove(slot-1);
		slotTable.getItems().add(new Geraet(slot, gerid));
		
		
		slotColumn.setSortType(SortType.ASCENDING);
		slotTable.getSortOrder().add(slotColumn);
		
	
		
	}

	
	
	public void onRegister(ActionEvent e) {
		/*
		slotColumn.setSortType(SortType.ASCENDING);
		slotTable.getSortOrder().add(slotColumn);
	*/
		
		if(counter<21) {
			String gid = geraetText.getText();

			if(gid==null || gid.isBlank()) {
				System.out.println("Empty record!");
				labelMsg.setText("Bitte geben Sie die Ger�t-ID ein.");
				geraetText.setText(null);
			}else {
				int slot = counter;
				geraetText.setText(null);
			
				if(initDevice(slot, gid)) {
					slotColumn.setCellValueFactory(new PropertyValueFactory<>("slotno"));
					geraeteColumn.setCellValueFactory(new PropertyValueFactory<>("geraetid"));
					slotTable.getItems().add(new Geraet(counter, gid));
					setChoice(counter);
					counter++;
					labelMsg.setText("Ger�t erfolgreich registriert!");
					
				}else {
					slotTable.getItems().remove(counter-1);
					labelMsg.setText("Ger�t kann nicht initialisiert werden. Bitte geben Sie eine andere Ger�te-ID ein.");
				}
				
				
			}
			
		}else {
			labelSlot.setText("");
			labelSlot.setText("Die Kapazit�t ist voll, starten Sie das Test.");
		}
		labelSlot.setText(String.valueOf(counter));
		
		slotColumn.setSortType(SortType.ASCENDING);
		slotTable.getSortOrder().add(slotColumn);
		
		
	}
	
	public boolean initDevice(int slotno, String deviceid) {
		try {
			initMsg = "INIT|"+slotno + "|"+deviceid;
			if(sock==null) {
				System.out.println("Socket null!");
				return false;
			}else {
				toServer.println(initMsg); // starting the server!
	  	        String serverMsg = fromServer.readLine();
	  	        
	  	        if(serverMsg.contains("Ignoring")) {
	  	        	labelMsg.setText("Ein Ger�t kann nicht mehrmals registriert werden!");
	  	        	return false;
	  	        }else{
	  	        	return true;
	  	        }
	  	        
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
			labelMsg.setText("Bitte geben sie mindestens ein Ger�t ein!");
			return false;
		}
		
		
		return true;
	}



	
	
	
}
