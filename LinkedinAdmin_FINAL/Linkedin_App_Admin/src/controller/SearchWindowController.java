package controller;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import client_initializer.RMIClientInitializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import linkedin_adminRMIServer.adminServerinterface;
import linkedin_backendRMIServer.BackendServerInterface;
import linkedin_backendRMIServer.FrontendQuery;
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


	
		
		/*searchButton.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		        Parent root;
		        try {
		            root = GifLoader.load(getClass().getClassLoader().getResource("controller/GifLoader.java"), resources);
		            Stage stage = new Stage();
		            stage.setTitle("My New Stage Title");
		            stage.setScene(new Scene(root, 450, 450));
		            stage.show();
		            // Hide this current window (if this is what you want)
		            ((Node)(event.getSource())).getScene().getWindow().hide();
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}*/
		
		public void getResults() {
		searchButton.setOnMouseClicked((event) -> {
			
			System.out.println("RMI Sucks!!");
			try {
				BackendServerInterface bsi = (BackendServerInterface) Naming.lookup("rmi://"+ RMIClientInitializer.getBackendIp() +"/BackendService");
				FrontendQuery frontendQuery = new FrontendQuery();
				frontendQuery.setFirstName(firstNameField.getText());
				frontendQuery.setLastName(lastNameField.getText());
				frontendQuery.setIndustry(industryField.getText());
				frontendQuery.setPosition(positionField.getText());
				frontendQuery.setState(stateField.getText());
				
				bsi.setQueryId();
				String queryId = bsi.getQueryId();
				System.out.println("RMI Sucks!!");
				String result = bsi.sendQuery(frontendQuery, queryId);
				System.out.println(result);
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        
			
			
		    //GifLoader polling = new GifLoader();
			//polling.initGUI(getClass().getResource("GifLoader.java"));
			//FXMLLoader fxmlLoader = new FXMLLoader();
			//fxmlLoader.setLocation(getClass().getResource("NewWindow.fxml"));
			/* 
			 * if "fx:controller" is not set in fxml
			 * fxmlLoader.setController(NewWindowController);
			 
			Scene scene = new Scene(polling.load(), 600, 400);
			Stage stage = new Stage();
			stage.setTitle("Polling");
			stage.setScene(scene);
			stage.show();*/
		});
		}
		
		/*public void getResults() {
			Stage stage2 = (Stage) searchButton.getScene().getWindow();
			stage2.close();
			try {
				GifLoader polling = new GifLoader();
				
				//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SearchView.fxml"));
				//Parent root1 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root1));  
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}*/
		
		
		public void getQuery() throws MalformedURLException, RemoteException {
			System.out.println("First Name: " + firstNameField.getText());
			System.out.println("Last Name: " + lastNameField.getText());
			System.out.println("State: " + stateField.getText());
			System.out.println("Industry: " + industryField.getText());
			System.out.println("Position: " + positionField.getText());
			
			try {
				BackendServerInterface bsi = (BackendServerInterface) Naming.lookup("rmi://"+ RMIClientInitializer.getBackendIp() +"/BackendService");
				FrontendQuery frontendQuery = new FrontendQuery();
				frontendQuery.setFirstName(firstNameField.getText());
				frontendQuery.setLastName(lastNameField.getText());
				frontendQuery.setIndustry(industryField.getText());
				frontendQuery.setPosition(positionField.getText());
				frontendQuery.setState(stateField.getText());
				
				bsi.setQueryId();
				String queryId = bsi.getQueryId();
				System.out.println("RMI Sucks!!");
				String result = bsi.sendQuery(frontendQuery, queryId);
				System.out.println(result);
				
				adminServerinterface client = (adminServerinterface) Naming.lookup("rmi://"+ RMIClientInitializer.getAdminIp() +"/binded");  //calling loginuser method from admin server 
				while (!client.pollComplete(bsi.getQueryId())) {
					System.out.println("Polling");
				}
				try {
					File stylesheet = new File("src/res/xml2csv.xsl"); //transformation sheet
				    File xmlSource = new File("/Users/Rambo/Downloads/Fall-2018---4350---Software-Engineering-master 4/LinkedInBackend/xml-files\\\\FL.xml"); //input file
				    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				    DocumentBuilder builder = factory.newDocumentBuilder();
				    Document document = builder.parse(xmlSource);
				    StreamSource stylesource = new StreamSource(stylesheet);
				    Transformer transformer = TransformerFactory.newInstance()
				            .newTransformer(stylesource);
				    Source source = new DOMSource(document);
				    Result outputTarget = new StreamResult(new File(queryId+".csv")); //output file
				    transformer.transform(source, outputTarget);
					}catch (Exception e) {
						System.out.println("XML to CSV failed" + e);
					}
				
				
				
			} catch (NotBoundException | TransformerException | ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		public void closeWindow() {
			Stage stage2 = (Stage) cancelButton.getScene().getWindow();
		    stage2.close();
		}
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			/*degreeTypeCombo.setItems(degree);
			degreeTypeCombo.setValue("Bachelors");
			stateComboBox.setItems(stateList);
			stateComboBox.setValue("GA");*/
			
		}
			
			
}