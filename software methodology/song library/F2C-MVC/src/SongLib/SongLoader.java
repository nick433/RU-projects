package SongLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

import f2c.view.F2CController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SongLoader {
	
	public class Song {
		String name;
		String artist;
		int year;
		String album;
	}
	
	ArrayList<Song> songs;
	public String filename;
	
	public ArrayList<Music> load(String filename)
	{
		ArrayList<Music> result= new ArrayList<Music>();
		this.filename = filename;
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line = null; Music newsong = null;

		    while ((line = br.readLine()) != null) {
		        String[] songelements = line.split(",");
		        switch (songelements.length)
		        {
		        case 0:
		        case 1:
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Song name or artist is empty");
					alert.setContentText("You need to specify a song name AND artist to add it.");
					alert.showAndWait();
		        	continue;
		        case 2:
		        	newsong = new Music(songelements[0], songelements[1], "", "");
		        	break;
		        case 3:
		        	newsong = new Music(songelements[0], songelements[1], songelements[2], "");
		        	break;
		        case 4:
		        	newsong = new Music(songelements[0], songelements[1], songelements[2], songelements[3]);
		        	break;
		        }
		        result.add(newsong);
		    }
		    
		    Collections.sort(result, (Music m1, Music m2) -> { return m1.getName().compareToIgnoreCase(m2.getName()); });
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	} 

}
