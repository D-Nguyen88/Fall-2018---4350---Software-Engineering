package controller;
	
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
//import linkedin_adminRMIServer.RMIClientInitializer;


public class Main extends Application {
	
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		mainWindow();		
	}
	
	public void mainWindow() {
		
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/LoginView.fxml"));
			AnchorPane pane = loader.load();
			
			MainWindowController mainWindowController = loader.getController();
			
			Scene scene = new Scene(pane);
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner (System.in);
		System.out.print("Enter the Admin APP's RMI Server IP address: ");
		client_initializer.RMIClientInitializer.setAdminIp(input.nextLine());
		System.out.print("Enter the backend's RMI Server IP address: ");
		client_initializer.RMIClientInitializer.setBackendIp(input.nextLine());
		input.close();
		launch(args);
	}
}
