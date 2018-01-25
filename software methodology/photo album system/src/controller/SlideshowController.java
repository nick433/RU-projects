package controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.*;

public class SlideshowController implements Initializable {
	private UserList listOfAllUsers;
	private Stage stage;
	private User currentUser;
	private Album currentAlbum;
	private ObservableList<Tag> obsList = null;
	private Photo currentPhoto;
	private int photoIndex;
	
	@FXML
	Button closeBtn;
	@FXML
	Button nextBtn;
	@FXML
	Button prevBtn;
	@FXML
	Button deleteTagBtn;
	@FXML
	Button albumviewBtn;
	@FXML
	Label caption; 
	@FXML
	Label dateTaken;
//	@FXML
//	Label photonameLabel;
	@FXML
	ListView<Tag> listView;
	@FXML
	ImageView pic;    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void refreshlist()
	{
		List<Tag> l = new ArrayList<Tag>();
		l.add(listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotoByName(currentPhoto.getPhotoName()).tags);
		obsList = FXCollections.observableArrayList(l);
		listView.setItems(obsList);
		
		listView.setCellFactory(new Callback<ListView<Tag>, ListCell<Tag>>() {

			@Override
			public ListCell<Tag> call(ListView<Tag> p) {
				ListCell<Tag> cell = new ListCell<Tag>() {
					@Override
					protected void updateItem(Tag tag, boolean empty) {
						super.updateItem(tag, empty);
						if (tag != null) {
							String result = "";
							for(int i = 0; i < tag.tagnames.size(); i++)
							{
								result += tag.tagnames.get(i) + "="  + tag.tagvalues.get(i) + "\n";
							}
							setText(result);
						} else {
							setText(null);
						}
					}
				};

				return cell;
			}
		});  
	}
	
	public void initData() throws ClassNotFoundException, IOException {
		
		listOfAllUsers = UserList.read();
		currentUser = listOfAllUsers.getUserByUsername(getUser().getUsername()); //adding this fixed a bug that took 5ever to find
//		currentAlbum = listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()); 
		//^^this causes serialization errors
		if(listOfAllUsers.getUserByUsername(
				getUser().getUsername()).getAlbumByName(currentAlbum.getAlbumName()).getSize() != 0 ){
			
			//System.out.println(currentUser.getAlbumByName(getAlbum().getAlbumName()).getSize());
			displayImage();
			
		}

	} 
	public void displayImage() throws IOException {
//		currentPhoto = listView.getSelectionModel().getSelectedItem();
		if(currentPhoto == null){
			pic.setImage(null);
			//System.out.println("no photo to display");
			return;
		}
		pic.setFitWidth(510);   //fitHeight="204.0" fitWidth="243.0"
		pic.setFitHeight(372);
		pic.setPreserveRatio(false);
		BufferedImage bufferedImage = null;
		//System.out.println(currentPhoto.getImage());
		bufferedImage = ImageIO.read(currentPhoto.getImage());
		Image image = SwingFXUtils.toFXImage(bufferedImage, null);
		pic.setImage(image);
		//System.out.println("displayed image");
		caption.setText("Caption: " + listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotoByName(currentPhoto.getPhotoName()).getCaption());
		dateTaken.setText("Date taken: " + currentPhoto.getFileLastModified());
		refreshlist();
		
	//	obsList.setAll(currentPhoto.getTags());
	//	tagsLV.setItems(obsList);
	//	dateLB.setText(currentPhoto.getDate());
	//	captionLB.setText(currentPhoto.getCaption());
	}
	
	public void setStartPhoto(Photo p){
		currentPhoto = p;
	}
	@FXML
	private void closeWindow() throws ClassNotFoundException, IOException {

	}
	@FXML
	private void nextPic() throws ClassNotFoundException, IOException {
		if(listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getSize() <= photoIndex + 1){
			return;
		}
		currentPhoto = listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotoByIndex(photoIndex + 1);
		displayImage();
		photoIndex++;
	}
	@FXML
	private void prevPic() throws ClassNotFoundException, IOException {
		if(photoIndex - 1 < 0){
			return;
		}
		currentPhoto = listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotoByIndex(photoIndex - 1);
		displayImage();
		photoIndex--;
	}
	@FXML
	private void deleteTag() throws ClassNotFoundException, IOException {

	}
	
	@FXML
	private void backToPhotoView() throws ClassNotFoundException, IOException { 
		//this is broken, messes up the serializable in a way that you cant click anything after using this
		//alt_bTAV();
		Parent root;
		Scene PTVscene;
		Stage PTVStage;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Photoview.fxml"));
		root = loader.load();

		PTVscene = new Scene(root);
		PTVStage = new Stage();
		PTVStage.setResizable(false);
		PTVStage.setTitle("Manage Photos");
		PTVStage.setScene(PTVscene);
		
		PhotoviewController controller = loader.getController();
		controller.setListOfUsers(listOfAllUsers);
		controller.setAlbum(currentAlbum);
		controller.setCurrentUser(getUser().getUsername().trim());
		controller.setAlbum(currentAlbum);
		controller.setStage(PTVStage);
		controller.initData();
		
		saveData();
		stage.close();
		
		PTVStage.show();
	}
	
	public void setUser(User u) {
		this.currentUser = u;
	}
	
	public User getUser() {
		return this.currentUser;
	}
	public void setAlbum(Album a){
		this.currentAlbum = a;
	}
	private Album getAlbum(){
		return this.currentAlbum;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public void setListOfUsers(UserList u) {
		listOfAllUsers = u;
	}
	public void setPhotoIndex(int i){
		this.photoIndex = i;
	}
	private void saveData() throws ClassNotFoundException {
		try {
			UserList.write(listOfAllUsers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
