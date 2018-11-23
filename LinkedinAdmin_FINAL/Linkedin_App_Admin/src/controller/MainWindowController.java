package controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import linkedin_adminRMIServer.RMIClientInitializer;
import linkedin_adminRMIServer.adminServerinterface;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
@SuppressWarnings("unused")
public class MainWindowController extends RMIClientInitializer implements Initializable {
	//Picklist Values
	ObservableList<String> degree = FXCollections.observableArrayList("Associates", "Bachelors", "Masters", "PhD");


	//Views
	@FXML private Text errorText;
	@FXML private TextField userNameField;
	@FXML private TextField passwordField;
	@FXML private Button signInButton;
	@FXML private AnchorPane rootPane;
	@FXML private Button signUpButton;
	@FXML private TextField gradYearField;
	@FXML private TextField stateField;
	@FXML public ComboBox<String> degreeTypeCombo;
	private Main main;
	private Stage primaryStage;


	public void setMain(Main main) {
		this.main = main;
	}


	public void signIn() throws MalformedURLException, RemoteException, NotBoundException { //login rmi method 
				/////////////////////////********* RMI CODE START *********////////////////////////
		
		RMILoader();
		try {
		adminServerinterface client = (adminServerinterface) Naming.lookup("rmi://"+ getIp() +"/binded");  //calling loginuser method from admin server 
		System.out.println("Successfully Connected to Admin module's RMI Server");
		if (client.loginuser(userNameField.getText(),passwordField.getText())) {
			System.out.println("User Login Succesfully");
			
			System.out.println(client.getKey()); //returns primary key of logged in user  
		}
		else {
			showAlert2();  //username not available 
		}
		} catch (Exception e) {
			 System.out.println ("Could not Connect to RMI Server. Make sure rmi registry is running on server machine and Server IP is correct. ");
			 System.out.println(e);
		}
		
		/*
			System.out.println("Username: " + userNameField.getText());
			System.out.println("Password: " + passwordField.getText());

		 */



		/*boolean response = false;
			try {
				response = sendLoginInfo(userNameField.getText()+", " + passwordField.getText());
			} catch (RemoteException e) {
				System.out.println("Error reaching Admin module!");
				e.printStackTrace();
			}
			if(response) { searchScreenLoader(); }
			else { showAlert2(); }


		 */
		
	}
					/////////////////////////********* RMI CODE END *********////////////////////////

	public void signUp() {
		System.out.println("Username: " + userNameField.getText());
		System.out.println("Password: " + passwordField.getText());
	}

	public void showAlert2() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Incorrect Login or Password!");
		alert.setContentText("Please try logging in again");
		alert.showAndWait();		
	}


	/*public void searchViewSwitch() {
			Stage stage = (Stage) signUpButton.getScene().getWindow();
		    stage.close();
		}*/

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//degreeTypeCombo.setItems(degree);

	}




	//This method is only an event listener for testing and will be changed to a remove the action event.
	//Method will be called upon complete sign-in
	public void signUpButtonAction(ActionEvent event) {
		Stage stage2 = (Stage) signUpButton.getScene().getWindow();
		stage2.close();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SignUpView.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));  
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void searchScreenLoader() {
		Stage stage2 = (Stage) signInButton.getScene().getWindow();
		stage2.close();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SearchView.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));  
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/*@Override
		public boolean sendLoginInfo(String Message) throws RemoteException {
			// TODO Auto-generated method stub
			return true;
		}*/
}


