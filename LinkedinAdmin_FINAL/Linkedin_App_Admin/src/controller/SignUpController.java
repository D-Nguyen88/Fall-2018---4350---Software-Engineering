package controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import linkedin_adminRMIServer.adminServerinterface;

public class SignUpController extends client_initializer.RMIClientInitializer implements Initializable{
	
	//Views
	@FXML private Text errorText;
	@FXML private TextField firstNameSUField;
	@FXML private TextField lastNameSUField;
	@FXML private TextField userNameField;
	@FXML private TextField passwordSUField;
	@FXML private Button signUpButton;
	public int userSessionId = 0;
	
	@SuppressWarnings("unused")
	private Main main;
	@SuppressWarnings("unused")
	private Stage primaryStage;

	
	public void setMain(Main main) {
		this.main = main;
	}
	
	
	
	public void signUp() throws MalformedURLException, RemoteException, NotBoundException { //rmi method
		/////////////////////////********* RMI CODE START *********////////////////////////
		
	
		System.out.println("FirstName: " + firstNameSUField.getText());
		System.out.println("LastName: " + lastNameSUField.getText());
		System.out.println("Email: " + userNameField.getText());
		System.out.println("Password: " + passwordSUField.getText());
		
		RMILoader();
		try {
		adminServerinterface client = (adminServerinterface) Naming.lookup("rmi://"+ getAdminIp() +"/binded"); //connecting to RMI Server
		System.out.println("Successfully Connected to Admin module's RMI Server");	//if connection successful 
		if (client.registerUser(firstNameSUField.getText(), lastNameSUField.getText(),userNameField.getText(), "rmitest5", passwordSUField.getText())) {
			System.out.println("User Registered Succesfully");
			//load sign in page 
			
			TimeUnit.SECONDS.sleep(2);
			searchScreenLoader();
		}
		else {
			showAlert2();  //username not available 
		}
		} catch (Exception e) {
		 System.out.println ("Could not Connect to RMI Server. Make sure rmi registry is running on server machine and Server IP is correct. ");
		 System.out.println(e);
			
		}
			////////////////////////********* RMI CODE END *********////////////////////////
	}
	
	public void showAlert2() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Username not available!");
		alert.setContentText("Please use a different username for signup");
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
	//public void handleButtonAction(ActionEvent event) 
	public void searchScreenLoader() {
		Stage stage2 = (Stage) signUpButton.getScene().getWindow();
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
}
