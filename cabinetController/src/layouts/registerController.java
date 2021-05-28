package layouts;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import DatabaseCM.DBConnection;
import DatabaseCM.Geraet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class registerController implements Initializable {

	@FXML
	private Button registerButton; 
	
	@FXML
	private Button startButton; 
	
	@FXML
	private Button editButton; 
	
	@FXML
	private Button setButton;
	
	@FXML
	private Label labelSlot;
	
	@FXML
	private Label labelMsg;
	
	@FXML
    private TextField geraetText;
	
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
	
	@FXML
	private TextField geraetEdit;
	
	List<Integer> list = new ArrayList<Integer>();
	static ObservableList<Geraet> ol;
	int selectedSlot;
	
	String geraeteid;
	
	String initMsg = "";
	
	Socket sock;
	PrintWriter toServer;
	BufferedReader fromServer;
	
	int counter = 1;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		labelSlot.setText(String.valueOf(counter));
		editVbox.setVisible(false);
		
	}
	
	
	public void onEdit(ActionEvent e) {
		editVbox.setVisible(true);
		
	}
	
	public void setChoice(int slottoadd) {
		slotEdit.getItems().add(slottoadd);
	}

	
	public void onSetEdit(ActionEvent e) {
		int slot = (int) slotEdit.getSelectionModel().getSelectedItem();
		System.out.println("slot" + slot);
		String gerid = geraetEdit.getText();
		
		//slotTable.getColumns().add(slot-1, geraeteColumn);
		System.out.println("deneme: " + slotTable.getColumns().get(1).getCellData(slot-1));
		
		slotTable.getItems().remove(slot-1);
		slotTable.getItems().add(new Geraet(slot, gerid));
		
		//
		//slotTable.getItems().add(new Geraet(slot-1, gerid));
	
		
		//replace(old, gerid);
		//(slotTable.getColumns().get(1), gerid);
		
	}
	
	
	public void onRegister(ActionEvent e) {
		
		if(counter<21) {
			String gid = geraetText.getText();

			if(gid==null || gid.isBlank()) {
				System.out.println("Empty record!");
				labelMsg.setText("Bitte geben Sie die Gerät-ID ein.");
				geraetText.setText(null);
			}else {
				System.out.println("slot to register: " + counter);
				int slot = counter;
				
				geraetText.setText(null);
				
				
				slotColumn.setCellValueFactory(new PropertyValueFactory<>("slotno"));
				geraeteColumn.setCellValueFactory(new PropertyValueFactory<>("geraetid"));
				slotTable.getItems().add(new Geraet(counter, gid));
				setChoice(counter);
				counter++;
				/*
				if(startDevice(slot, gid)) {
					counter++;
					labelMsg.setText("Gerät erfolgreich registriert!");
					
				}
				*/
				
				//if(DBConnection.registerDevice(gid, slot)) 
				
				
				
			}
			
		}else {
			labelSlot.setText("");
			labelSlot.setText("Die Kapazität ist voll, starten Sie das Test.");
		}
		labelSlot.setText(String.valueOf(counter));
			
		
		
	}
	
	public boolean startDevice(int slotno, String deviceid) {
		try {
			initMsg = "INIT|"+slotno + "|"+deviceid;
			sock = new Socket(InetAddress.getLocalHost(), 17);
			toServer = new PrintWriter(sock.getOutputStream(), true);
			fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			if(sock==null) {
				labelMsg.setText("Server Verbindung fehlerhaft!");
			}else {
				labelMsg.setText("Server Verbindung erfolgreich!");

			}
			
			toServer.println(initMsg); // starting the server!
  	        String serverMsg = fromServer.readLine();
  	        
  	        if(serverMsg.contains("registered")) {
  	        	return true;
  	        }
  	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return false;
		
	}
	
	public void onStart(ActionEvent e) {
		
		
		
		
		
			System.out.println("check: " + checkTable());
			if(checkTable()) {
				labelMsg.setText("Alles gut!");
			}
		/*
		try {
			Stage stage = (Stage) registerButton.getScene().getWindow();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("validationPage.fxml"));
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
		*/
		
	}


	private boolean checkTable() {
		ol = slotTable.getItems();
		
		if(ol.isEmpty()) {
			labelMsg.setText("Bitte geben sie mindestens ein Gerät ein!");
			
			
			return false;
			
		}
		
		
		for(int i=0;i<ol.size();i++) {
			
			for(int j=i+1;j<ol.size();j++) {
				if(ol.get(i).getGeraetid().equals(ol.get(j).getGeraetid())) {
					labelMsg.setText("Ein Gerät kann nicht mehrmals registriert werden!");
					return false;
				}
			}
		}
		
		
		return true;
	}



	
	
	
}
