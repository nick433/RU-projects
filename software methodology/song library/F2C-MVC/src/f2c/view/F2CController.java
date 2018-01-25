package f2c.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.Optional;

import SongLib.Music;
import SongLib.SongLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;

public class F2CController implements Initializable {
	
	@FXML 
	Button add;
	@FXML 
	Button delete;
    @FXML
    private TextField name;
    @FXML
    private TextField artist;
    @FXML
    private TextField album;
    @FXML
    private TextField year;
    @FXML
    private TextField newName;
    @FXML
    private TextField newArtist;
    @FXML
    private TextField newAlbum;
    @FXML
    private TextField newYear;
       
    @FXML
    public ListView<String> listView;
    
    @FXML
    private VBox vboxlist;
    
    
    final ObservableList<String> currentList=FXCollections.observableArrayList();    
    public static ObservableList<Music> currentSongList=FXCollections.observableArrayList();
    public SongLoader s = new SongLoader(); 
    
    boolean dontdoit = false;
    
    @FXML
    public void handleMouseClick(MouseEvent e) {
    	
    	int selectedItemIndex = listView.getSelectionModel().getSelectedIndex();
		
		artist.setText(currentSongList.get(selectedItemIndex).getArtist().toString());
		album.setText(currentSongList.get(selectedItemIndex).getAlbum().toString());
		year.setText(currentSongList.get(selectedItemIndex).getYear().toString());
    	
        System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
    }
	@FXML 
	public void handleKeyPressed(KeyEvent k) {
	    	
	    	int selectedItemIndex = listView.getSelectionModel().getSelectedIndex();
	    	if(selectedItemIndex < currentSongList.size()-1){
	    		if(k.getCode().toString().equals("DOWN")){
		    		artist.setText(currentSongList.get(selectedItemIndex+1).getArtist().toString());
					album.setText(currentSongList.get(selectedItemIndex+1).getAlbum().toString());
					year.setText(currentSongList.get(selectedItemIndex+1).getYear().toString());
					return;
		    	}
	    	}
	    	if(selectedItemIndex > 0){
	    		if(k.getCode().toString().equals("UP")){
		    		artist.setText(currentSongList.get(selectedItemIndex-1).getArtist().toString());
					album.setText(currentSongList.get(selectedItemIndex-1).getAlbum().toString());
					year.setText(currentSongList.get(selectedItemIndex-1).getYear().toString());
					return;
		    	}
	    	}
	    	
	    	
			artist.setText(currentSongList.get(selectedItemIndex).getArtist().toString());
			album.setText(currentSongList.get(selectedItemIndex).getAlbum().toString());
			year.setText(currentSongList.get(selectedItemIndex).getYear().toString());
	    	
	        System.out.println("pressed " + k.getCode().toString());
	}
    
    
    @FXML
    private void changeName(ActionEvent event) {
    	
		int indexofchanged = listView.getSelectionModel().getSelectedIndex();
		String newname = name.getText();
		
		if (currentSongList.get(indexofchanged).getName().compareTo(newname) != 0)
		{
			String myartist = currentSongList.get(indexofchanged).getArtist();
	    	if(!AreYouSure("change this song name", 3)) return;
			
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Error Dialog");
	    	
	    	if (isBlank(newname))
	    	{
				alert.setHeaderText("Song name for change is empty");
				alert.setContentText("You need to specify a song name to change it to.");
				alert.showAndWait();
				return;
	    	}
	    	
			currentSongList.forEach((song) -> {
				if (song != null)
				{
					String isong = song.getName(), iartist = song.getArtist();
					
					if (isong.compareToIgnoreCase(newname) == 0 && iartist.compareToIgnoreCase(myartist) == 0)
					{
						alert.setHeaderText("You cannot add the same song twice");
						alert.setContentText("Two songs may not have the same name and artist.");
						alert.showAndWait();
						dontdoit = true;
						return;
					}
				}
			});
			
			if (!dontdoit)
			{
				currentSongList.get(indexofchanged).setName(newname);
				currentList.set(indexofchanged, newname);
			}
			
			dontdoit = false;
			
    		changeArtist(event);
    		changeAlbum(event);
    		changeYear(event);
		}
    }
    
    //almost carbon copy of changeName function
    @FXML
    private void changeArtist(ActionEvent event) { 	
		int indexofchanged = listView.getSelectionModel().getSelectedIndex();
		String newartistname = artist.getText();
		
		if (currentSongList.size() > 0 && (
				(currentSongList.get(indexofchanged).getArtist().compareTo(newartistname) != 0)))
		{
			String mysongname = currentSongList.get(indexofchanged).getName();
			
	    	if(!AreYouSure("change this artist name", 3)) return;
			
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Error Dialog");
	    	
	    	if (isBlank(newartistname))
	    	{
				alert.setHeaderText("Artist name for change is empty");
				alert.setContentText("You need to specify a artist name to change it to.");
				alert.showAndWait();
				return;
	    	}
	    	
			currentSongList.forEach((song) -> {
				if (song != null)
				{
					String isong = song.getName(), iartist = song.getArtist();
					
					if (iartist.compareToIgnoreCase(newartistname) == 0 && isong.compareToIgnoreCase(mysongname) == 0)
					{
						alert.setHeaderText("You cannot add the same song twice");
						alert.setContentText("Two songs may not have the same name and artist.");
						alert.showAndWait();
						dontdoit = true;
						return;
					}
				}
			});
			
			if (!dontdoit)
			{
				currentSongList.get(indexofchanged).setArtist(newartistname);
			}
			
			dontdoit = false;
			
    		changeName(event);
    		changeAlbum(event);
    		changeYear(event);
		}
    }
    
    @FXML
    private void changeAlbum(ActionEvent event) {
    	
    	int indexofchanged = listView.getSelectionModel().getSelectedIndex();
    	String newalbumname = album.getText();
    	
    	if (currentSongList.size() > 0 && (
    			(currentSongList.get(indexofchanged).getAlbum().compareTo(newalbumname) != 0)))
    	{
    		if(!AreYouSure("change this album name", 3)) return;
    	
    		currentSongList.get(indexofchanged).setAlbum(newalbumname);
    		
    		changeName(event);
    		changeArtist(event);
    		changeYear(event);
    	}
    }
    
    @FXML
    private void changeYear(ActionEvent event) {
    	
    	int indexofchanged = listView.getSelectionModel().getSelectedIndex();
    	String newyear = year.getText();
    	
    	if (currentSongList.size() > 0 && (
    			(currentSongList.get(indexofchanged).getYear().compareTo(newyear) != 0)))
    	{
    		if(!AreYouSure("change this song year", 3)) return;
    	
    		currentSongList.get(indexofchanged).setyear(newyear);
    	
    		changeName(event);
    		changeArtist(event);
    		changeAlbum(event);
    	}
    }
    
    @FXML
    private void addName(ActionEvent event) {
    	if(!AreYouSure("add this song", 3)) return;
    	
    	String NewSongName = newName.getText(), NewSongArtist = newArtist.getText();
    	Music newsong = new Music(NewSongName, NewSongArtist, newAlbum.getText(), newYear.getText());
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	
    	if (isBlank(newsong.getName()) || isBlank(newsong.getArtist()))
		{
			alert.setHeaderText("Song name or artist is empty");
			alert.setContentText("You need to specify a song name AND artist to add it.");
			alert.showAndWait();
			return;
		}
		currentSongList.forEach((song) -> {
			if (song != null)
			{
				if (song.getName().compareToIgnoreCase(NewSongName) == 0 && 
						song.getAlbum().compareToIgnoreCase(NewSongArtist) == 0)
				{
					alert.setHeaderText("You cannot add the same song twice");
					alert.setContentText("Two songs may not have the same name and artist.");
					alert.showAndWait();
					return;
				}
			}
		});
		
		currentSongList.add(newsong);
		Collections.sort(currentSongList, (Music m1, Music m2) -> { return m1.getName().compareToIgnoreCase(m2.getName()); });
		
		int newsonglocation = currentSongList.indexOf(newsong);
		currentList.add(newsonglocation, NewSongName);

		listView.setItems(currentList);
		listView.getSelectionModel().select(newsonglocation);
		listView.getFocusModel().focus(newsonglocation);
		
		name.setText(currentSongList.get(newsonglocation).getArtist().toString());
		artist.setText(currentSongList.get(newsonglocation).getArtist().toString());
		album.setText(currentSongList.get(newsonglocation).getAlbum().toString());
		year.setText(currentSongList.get(newsonglocation).getYear().toString());
		
        newName.clear();
        newArtist.clear();
        newAlbum.clear();
        newYear.clear();
    }
       
    @FXML
    private void delName(ActionEvent event) {
    	int selectedItemIndex = listView.getSelectionModel().getSelectedIndex();
    	int nextitemindex = (selectedItemIndex + 1 == currentList.size()) ? selectedItemIndex - 1 : selectedItemIndex;
    	
    	currentList.remove(selectedItemIndex);
    	currentSongList.remove(selectedItemIndex);
    	System.out.println(nextitemindex);
    	if (currentList.size() != 0)
    	{
    		listView.getSelectionModel().select(nextitemindex);
    		listView.getFocusModel().focus(nextitemindex);
    		name.setText(currentSongList.get(nextitemindex).getArtist().toString());
    		artist.setText(currentSongList.get(nextitemindex).getArtist().toString());
    		album.setText(currentSongList.get(nextitemindex).getAlbum().toString());
    		year.setText(currentSongList.get(nextitemindex).getYear().toString());
    	}
    	else{
    		name.setText("");
    		artist.setText("");
    		album.setText("");
    		year.setText("");
    	}
    	
    	
    }
          

     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	currentSongList = FXCollections.observableArrayList(s.load("songs.txt"));
       add.setDisable(true);
        delete.setDisable(true);
    
        newName.focusedProperty().addListener(new ChangeListener<Boolean>() {
         public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        	 
            if(newName.isFocused()){
              add.setDisable(false);
            }
         }
        });    
    
        listView.focusedProperty().addListener(new ChangeListener<Boolean>() {
          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        	
            if(listView.isFocused()){
              delete.setDisable(false);
            }
            
         }
        });    
        
        DecimalFormat format = new DecimalFormat( "#.0" );
        year.setTextFormatter( new TextFormatter<>(c ->
        {
            if ( c.getControlNewText().isEmpty() ) { return c; }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ) {
                return null;
            } else {
                return c;
            }
        }));
        newYear.setTextFormatter( new TextFormatter<>(c ->
        {
            if ( c.getControlNewText().isEmpty() ) { return c; }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() ) {
                return null;
            } else {
                return c;
            }
        }));
        
        
        listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
    
                	
                	public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                		listView.getSelectionModel().getSelectedIndex();
                		
                		name.setText(t1);
                		
                	}
                	
                });
        
        for (Music song : currentSongList)
        {
        	currentList.add(song.getName());
        }
        if(!currentSongList.isEmpty()){
        	artist.setText(currentSongList.get(0).getArtist().toString());
        	album.setText(currentSongList.get(0).getAlbum().toString());
        	year.setText(currentSongList.get(0).getYear().toString());
        }	
        listView.setItems(currentList);
        listView.getSelectionModel().select(0);
        listView.getFocusModel().focus(0);
    }   
	public boolean AreYouSure(String want, int seconds)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("Do you really want to " + want + "?");
		alert.setContentText("You have " + seconds + " seconds to cancel.");
		
		ButtonType buttonTypeOne = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
		
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(seconds*1000);
                if (alert.isShowing()) {
                    Platform.runLater(() -> alert.close());
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
        	return true; 
        } else if (result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
        	return false;
        } else {
        	System.out.println(result.get());
        }
		
		return true;
	}
    private static boolean isBlank(String string) {
        if (string == null || string.length() == 0)
            return true;

        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!Character.isWhitespace(string.codePointAt(i)))
                return false;
        }
        return true;
    }
	
}