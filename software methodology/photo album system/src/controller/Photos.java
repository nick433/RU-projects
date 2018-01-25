package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import model.UserList;

import java.io.IOException;

public class Photos extends Application {
	Stage window;
	
	public static void main(String[] args) {
		launch(args);
	}

	
	@Override  
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		window = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Einloggen.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Foto-Viewer Login Page");
		primaryStage.setResizable(false);
		primaryStage.show();
	}	
}
