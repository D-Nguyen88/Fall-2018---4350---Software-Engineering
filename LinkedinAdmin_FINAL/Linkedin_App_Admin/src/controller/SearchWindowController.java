package controller;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
@SuppressWarnings("unused")
public class SearchWindowController implements Initializable {
	//@FXML public ComboBox<String> degreeTypeCombo;
		//@FXML public ComboBox<String> stateComboBox;
		@FXML private Button cancelButton;
		@FXML private Button searchButton;
		@FXML private TextField firstNameField;
		@FXML private TextField lastNameField;
		@FXML private TextField stateField;
		@FXML private TextField industryField;
		@FXML private TextField positionField;

		
		
		
		//Picklist Values
			/*ObservableList<String> degree = FXCollections.observableArrayList("Associates", "Bachelors", "Masters", "PhD");
			ObservableList<String> stateList = FXCollections.observableArrayList("AK", "AL", "AR", "AZ", "CA", "CO", "CT", 
	                "DC", "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", 
	                "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", 
	                "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", 
	                "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY");*/

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			/*degreeTypeCombo.setItems(degree);
			degreeTypeCombo.setValue("Bachelors");
			stateComboBox.setItems(stateList);
			stateComboBox.setValue("GA");*/
			
		}
		
		public void closeWindow() {
			Stage stage2 = (Stage) cancelButton.getScene().getWindow();
		    stage2.close();
		}
		
		public void getQuery() {
			System.out.println("First Name: " + firstNameField.getText());
			System.out.println("Last Name: " + lastNameField.getText());
			System.out.println("State: " + stateField.getText());
			System.out.println("Industry: " + industryField.getText());
			System.out.println("Position: " + positionField.getText());
		}
			
			
}
