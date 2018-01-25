package SongLib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import f2c.view.F2CController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SongLib extends Application {
	
	FileOutputStream fooStream = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/f2c/view/F2C.fxml"));
		
		GridPane root = (GridPane)loader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	    primaryStage.setOnCloseRequest(event -> {
			File myFoo = new File("songs.txt"); //hardcoded also in F2CController.java, be careful!
			try {
				fooStream = new FileOutputStream(myFoo, false);
				
				F2CController.currentSongList.forEach((song) -> {
					try {
						fooStream.write(song.toString().getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				
				fooStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	        
	    });
	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}

}
