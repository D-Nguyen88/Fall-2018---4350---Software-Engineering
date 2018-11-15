package controller;
import java.net.URL;
import java.rmi.Naming;
import java.util.ResourceBundle;
import client_initializer.RMIClientInitializer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import linkedin_backendRMIServer.BackendServerInterface;
import linkedin_backendRMIServer.FrontendQuery;

@SuppressWarnings("unused")
public class SearchWindowController extends RMIClientInitializer implements Initializable {
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
			/////////////////////////********* RMI CODE START *********////////////////////////

			RMILoader();
			try {
				BackendServerInterface backend = (BackendServerInterface) Naming.lookup("rmi://"+ getBackendIp() +"/binded"); //connecting to RMI Server
				System.out.println("Successfully Connected to Admin module's RMI Server");	//if connection successful

				FrontendQuery frontendQuery = new FrontendQuery();
				frontendQuery.setFirstName(firstNameField.getText());
				frontendQuery.setLastName(lastNameField.getText());
				frontendQuery.setIndustry(industryField.getText());
				frontendQuery.setPosition(positionField.getText());
				frontendQuery.setState(stateField.getText());

				String result = backend.sendQuery(frontendQuery);

			} catch (Exception e) {
				System.out.println ("Could not Connect to RMI Server. Make sure rmi registry is running on server machine and Server IP is correct. ");
				System.out.println(e);
			}

			/////////////////////////********* RMI CODE END *********////////////////////////

			System.out.println("First Name: " + firstNameField.getText());
			System.out.println("Last Name: " + lastNameField.getText());
			System.out.println("State: " + stateField.getText());
			System.out.println("Industry: " + industryField.getText());
			System.out.println("Position: " + positionField.getText());
		}
}
