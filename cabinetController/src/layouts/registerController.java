package layouts;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class registerController implements Initializable {

	@FXML
	private Button registerButton; 
	
	@FXML
    private ChoiceBox slotChoice; 
	
	@FXML
    private TextField geraetText;
	
	@FXML
	private TableView slotTable;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<Integer> list = new ArrayList<Integer>();
		ObservableList<Integer> ol = FXCollections.observableList(list);
		for(int i=1;i<21;i++) {
			ol.add(i);
		}
		
		slotChoice.setItems(ol);
		
	}
	
	
	
	
	public void onRegister(ActionEvent e) {


		Stage stage = (Stage) registerButton.getScene().getWindow();
		

		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("validationPage.fxml"));
			stage.setScene(new Scene(root));
			stage.sizeToScene();
			stage.setResizable(true);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node) e.getSource()).getScene().getWindow());

			stage.show();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}



	
	
	
}
