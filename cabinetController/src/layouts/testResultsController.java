package layouts;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DatabaseCM.DBConnection;
import DatabaseCM.Device;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class testResultsController implements Initializable{
	
	@FXML
	private Button saveButton, endButton;
	
	@FXML
	private TableView<Device> resultTable;
	
	@FXML
	private TableColumn<Device, String> deviceIdColumn;

	@FXML
	private TableColumn<Device, Boolean> successColumn;
	
	ObservableList<Device> ol;
	
	
	int test;
	LocalDate date;
	String user;
	
	public void onSave(ActionEvent e) {
		
		for(int k=0;k<ol.size();k++) {
			System.out.println("inside ol " + k + ". element: " + ol.get(k).getDeviceid());
			DBConnection.getInstance().insertDevice(ol.get(k).getDeviceid(), ol.get(k).getSuccess());
			DBConnection.getInstance().insertTest(test, ol.get(k).getSlotno(), ol.get(k).getDeviceid(), ol.get(k).getSuccess(), user, date);
		}
		saveButton.setVisible(false);
		endButton.setVisible(true);
		
		
	}
	
	
	public void onEnd(ActionEvent e) {
		Stage stage = (Stage) endButton.getScene().getWindow();
	    stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		user = loginController.getInstance().getUsername();
		date = LocalDate.now();
		test = DBConnection.getInstance().getTestNo(); 
		
		deviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("deviceid"));
		successColumn.setCellValueFactory(new PropertyValueFactory<>("success"));
		
		ol = executionController.getInstance().getOl();
		
		
		
		
		for(int k=0;k<ol.size();k++) {
			System.out.println("the " + k + ". element of ol: " + ol.get(k).getDeviceid() + "success: " + ol.get(k).getSuccess());
			
		}
		
		
		
		resultTable.getItems().addAll(ol);
		
		
	}

	}

