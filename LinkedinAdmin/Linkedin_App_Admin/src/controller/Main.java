package controller;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import linkedin_adminRMIServer.RMIClientInitializer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

@SuppressWarnings("unused")
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
		Scanner s1 = new Scanner (System.in);
		System.out.print("Enter the Admin APP's RMI Server IP address: ");
		RMIClientInitializer.setIp(s1.nextLine());
		s1.close();
		launch(args);
	}
}


