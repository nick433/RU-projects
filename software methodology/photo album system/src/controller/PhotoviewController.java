package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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

public class PhotoviewController implements Initializable {
	private UserList listOfAllUsers;
	private Stage stage;
	private User currentUser;
	private Album currentAlbum;
	private ObservableList<Photo> obsList = null;
	private ObservableList<Photo> obsList2 = null;
	private Photo currentPhoto;
	private Album newAlb;
	@FXML
	Button makeAlbBtn;
	@FXML
	Button searchBtn;
	@FXML
	Button addBtn;
	@FXML
	Button moveBtn;
	@FXML
	Button delBtn;
	@FXML
	Button renameBtn;
	@FXML
	Button albumviewBtn;
	@FXML
	Button viewBtn;
	@FXML
	TextField nameAlbField;
	@FXML
	Button copyBtn;
	@FXML
	Button slideshowBtn;
	@FXML
	TextField addtagsField;
	@FXML
	Button addtagsBtn;
	@FXML
	TextField addcapText;
	@FXML
	Button addcapBtn;
	@FXML
	Label albumnameLabel;
	@FXML
	ListView<Photo> listView;
	@FXML
	ListView<Photo> listView1;
	@FXML
	ImageView imgView;    
	@FXML
	TextField tagName;
	@FXML
	TextField tagValue;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	
	@FXML
	public void handleMouseClick(MouseEvent e){
    	
		try{
			int selectedItemIndex = listView.getSelectionModel().getSelectedIndex();
			listView.getSelectionModel().select(selectedItemIndex);
			////System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem().getPhotoName());
			//causes a NullPointerException
			
			currentPhoto = listView.getSelectionModel().getSelectedItem();
			displayImage();
		}
		catch(IOException ee){
		
		}
		
	}
	
	public void initData() throws ClassNotFoundException, IOException {
		
			listOfAllUsers = UserList.read();
			currentUser = listOfAllUsers.getUserByUsername(getUser().getUsername()); //adding this fixed a bug that took 5ever to find
	//		currentAlbum = listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()); 
			//^^this causes serialization errors
			albumnameLabel.setText(getAlbum().getAlbumName());
			if(listOfAllUsers.getUserByUsername(
					getUser().getUsername()).getAlbumByName(
							currentAlbum.getAlbumName()).getSize() != 0 ){
				obsList = FXCollections.observableArrayList(currentUser.getAlbumByName(getAlbum().getAlbumName()).getPhotos());
				listView.setItems(obsList);
				
				listView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {

					@Override
					public ListCell<Photo> call(ListView<Photo> p) {
						ListCell<Photo> cell = new ListCell<Photo>() {
							@Override
							protected void updateItem(Photo photo, boolean empty) {
								super.updateItem(photo, empty);
								if (photo != null) {
									setText(photo.getPhotoName());
								} else {
									setText(null);
								}
							}
						};

						return cell;
					}
				});
				
				listView.getSelectionModel().select(0);
			
				//System.out.println(currentUser.getAlbumByName(getAlbum().getAlbumName()).getSize());
				displayImage();
			}
			else{
				//System.out.print("else " + currentUser.getAlbumByName(getAlbum().getAlbumName()).getPhotos());
				obsList = FXCollections.observableArrayList(currentUser.getAlbumByName(getAlbum().getAlbumName()).getPhotos());
				listView.setItems(obsList);
				listView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {

					@Override
					public ListCell<Photo> call(ListView<Photo> p) {
						ListCell<Photo> cell = new ListCell<Photo>() {
							@Override
							protected void updateItem(Photo photo, boolean empty) {
								super.updateItem(photo, empty);
								if (photo != null) {
									setText(photo.getPhotoName());
								} else {
									setText(null);
								}
							}
						};

						return cell;
					}
				});
			}

	} 
	public void displayImage() throws IOException {
		currentPhoto = listView.getSelectionModel().getSelectedItem();
		if(currentPhoto == null){
			imgView.setImage(null);
			//System.out.println("no photo to display");
			return;
		}
		imgView.setFitWidth(243);   //fitHeight="204.0" fitWidth="243.0"
		imgView.setFitHeight(204);
		imgView.setPreserveRatio(false);
		BufferedImage bufferedImage = null;
		//System.out.println(currentPhoto.getImage());
		bufferedImage = ImageIO.read(currentPhoto.getImage());
		Image image = SwingFXUtils.toFXImage(bufferedImage, null);
		imgView.setImage(image);
		//System.out.println("displayed image");
//		obsList.setAll(currentPhoto.getTags());
//		tagsLV.setItems(obsList);
//		dateLB.setText(currentPhoto.getDate());
//		captionLB.setText(currentPhoto.getCaption());
	}
	
	/**
	 * @param username
	 */
	public void setCurrentUser(String username) {
		currentUser = listOfAllUsers.getUserByUsername(username);
	}

	/**
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */

	@FXML
	protected void handleSearchBtnAction(ActionEvent event) throws ClassNotFoundException, IOException {
		
	}

	/**
	 * @param event
	 * @throws ClassNotFoundException
	 */

	/**
	 * @return
	 */

	/**
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void renamePhoto(ActionEvent event) throws ClassNotFoundException, IOException{

		currentPhoto = listView.getSelectionModel().getSelectedItem(); //this is just the photo object, unaware of it's parent classes

		if (currentPhoto == null) {
			return;
		}
		
		Dialog<String> dialog = new Dialog<>();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("Rename Photo");
		dialog.setResizable(false);
		dialog.setHeaderText("Enter New Photo File Name below:");


		Label photoName = new Label("Photo Name: ");
		TextField photoNameTF = new TextField(currentPhoto.getPhotoName());
		Platform.runLater(() -> photoNameTF.requestFocus());

		GridPane gridPane = new GridPane();
		gridPane.add(photoName, 1, 1);
		gridPane.add(photoNameTF, 2, 1);
		dialog.getDialogPane().setContent(gridPane);

		ButtonType renameBtn = new ButtonType("Rename", ButtonData.OK_DONE);
		ButtonType cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(renameBtn, cancelBtn);

		dialog.setResultConverter(new Callback<ButtonType, String>() {
			String errorMessage = null;

			@Override
			public String call(ButtonType btn) {
				if (btn == renameBtn) {

					if (photoNameTF.getText().trim().isEmpty()) {
						errorMessage = "Photo's name field cannot be empty.";
					}

					if (listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotoByName(photoNameTF.getText().trim()) != null) { 
						//will probably need to get the current abum through object chain from list of users
						errorMessage = "Photo name already exists";
					}

					if (errorMessage != null) {
						
					   	Alert alert = new Alert(AlertType.ERROR);
				    	alert.setTitle("Error Dialog");
				    	alert.setHeaderText("Error");
				    	alert.setContentText(errorMessage);
				    	alert.showAndWait();
						
						return null;
					}

					return photoNameTF.getText();
				}

				return null;
			}
		});

		Optional<String> dResult = dialog.showAndWait();

		if (dResult.isPresent()) {
			String newName = dResult.get();
			currentUser.getAlbumByName(currentAlbum.getAlbumName()).getPhotoByName(currentPhoto.getPhotoName()).changeName(newName);
/*right?*/	obsList = FXCollections.observableArrayList(listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotos());
			listView.setItems(null);
			listView.setItems(obsList);
			listView.getSelectionModel().select(0);

			try {
				UserList.write(listOfAllUsers);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}

	/**
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void removeAlbum(ActionEvent event) throws ClassNotFoundException {
//		Album a = listView.getSelectionModel().getSelectedItem();
//		obsList.remove(a);
//	currentUser.removeAlbum(a);
		listView.getSelectionModel().select(0);

		try {
			UserList.write(listOfAllUsers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */


	/**
	 * @param stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * @param u
	 */
	public void setListOfUsers(UserList u) {
		listOfAllUsers = u;
	}

	/**
	 * @param u
	 */
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
	public void setStartPhoto(Photo p){
		
	}

	@FXML
	private void backToAlbumView() throws ClassNotFoundException, IOException { 
		//this is broken, messes up the serializable in a way that you cant click anything after using this
		//alt_bTAV();
		Parent root;
		Scene ABVscene;
		Stage ABVStage;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumManagen.fxml"));
		root = loader.load();

		ABVscene = new Scene(root);
		ABVStage = new Stage();
		ABVStage.setResizable(false);
		ABVStage.setTitle("Manage Albums");
		ABVStage.setScene(ABVscene);
		
		AlbumManagenController controller = loader.getController();
		controller.setListOfUsers(listOfAllUsers);
		controller.setCurrentUser(getUser().getUsername().trim());
		controller.setStage(ABVStage);
		controller.initData();
		
		saveData();
		stage.close();
		
		ABVStage.show();
	} //make sure that scrolling through the pics will keep updating the image preview. this will work how the songlib changes the words you see
	
	@FXML
	private void addPhoto() throws ClassNotFoundException, IOException {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose a Photo");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("jpg or png files ONLY (*.jpg, *.png)",
				"*.jpg");

		fc.getExtensionFilters().add(extFilter);
		File file = fc.showOpenDialog(stage);

		if (file == null) {
			return;
		} else {
			// check to see if photo exists in the album.
			for (Photo p : listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotos()) {
				if (p.getFileLocation().equals(file)) {
					//System.out.println("cannot add same image");
					return;
				}
			}
		}
//add to photo list
		//System.out.println(	file.toString() + getUser().getUsername() + listOfAllUsers.getUserByUsername( getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getAlbumName() );
		Photo newPhoto = new Photo(file);
		//System.out.println("name = " + newPhoto.setName());
		listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).addPhoto(newPhoto);
		int i = 0;
		UserList.write(listOfAllUsers); //delthis
		for (Photo p : listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotos()) {
			if (p.getFileLocation().equals(file)) {
				//System.out.println("activated");
				obsList.add(listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotos().get(i));
			}
			i++;
		} //adds to observable list from users's albums's picture which was the one just added
		
		

		
//		obsList = FXCollections.observableArrayList(currentUser.getAlbumByName(getAlbum().getAlbumName()).getPhotos()); 
		//sometimes the code crashes here when adding a first photo
		//in this scenerio the picture is not added even after restarting. 
		
		//if it doesnt fail here an exception will happen and you wont see the photo appear, but upon restarting the app and entering 
		//the album you will see the picture in the list and the image displayed. no noticable patterns on what causes which exception
		
		//	obsList.setAll(listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotos());
//		listView.setItems(this.obsList);
		UserList.write(listOfAllUsers);
		//System.out.println("added photo");
		listView.getSelectionModel().select(0);
		currentPhoto = listView.getSelectionModel().getSelectedItem();
		displayImage();
		saveData();
	}
	@FXML
	private void movePhoto() throws ClassNotFoundException, IOException {
		if(copyPhoto())
			deletePhoto();
	}
	@FXML
	private void deletePhoto() throws ClassNotFoundException, IOException {
		Photo p = listView.getSelectionModel().getSelectedItem();
		obsList.remove(p);
		listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).removePhoto(
				listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotoByName(p.getPhotoName()) );
		try {
			UserList.write(listOfAllUsers);
		} catch (IOException e) {
			e.printStackTrace();
		}
		listView.getSelectionModel().select(0);
		displayImage();
		
	}
	@FXML
	private void duplicatePhoto() throws ClassNotFoundException, IOException {

	}
	@FXML
	private void viewPhoto() throws ClassNotFoundException, IOException {

	}
	@FXML
	private boolean copyPhoto() throws ClassNotFoundException, IOException {
		
		GridPane gridPane;
		ButtonType createBtn, cancelBtn;

		Dialog<User> dialog = new Dialog<>();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("What user?");
		dialog.setResizable(false);
		dialog.setHeaderText("Enter user's information below:");

		Label usernameLB = new Label("Username: ");
		TextField usernameTF = new TextField();
		Platform.runLater(() -> usernameTF.requestFocus());

		gridPane = new GridPane();
		gridPane.add(usernameLB, 1, 1);
		gridPane.add(usernameTF, 2, 1);

		dialog.getDialogPane().setContent(gridPane);
		createBtn = new ButtonType("Create", ButtonData.OK_DONE);
		cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(createBtn, cancelBtn);

		dialog.setResultConverter(new Callback<ButtonType, User>() {
			String errorMessage = null;

			@Override
			public User call(ButtonType btn) {
				if (btn == createBtn) {
					if (usernameTF.getText().trim().isEmpty()) {
						errorMessage = "Username field cannot be empty.";
					} else if (!listOfAllUsers.isUsernameTaken(usernameTF.getText())) {
						errorMessage = "Username doesn't exist.";
					} else {
						errorMessage = null;
					}

					if (errorMessage != null) {
					   	Alert alert = new Alert(AlertType.ERROR);
				    	alert.setTitle("Error Dialog");
				    	alert.setHeaderText("Error: unable to create user");
				    	alert.setContentText(errorMessage);
				    	alert.showAndWait();
						return null;
					}

					return new User(usernameTF.getText());
				}

				return null;
			}
		});

		Optional<User> dResult = dialog.showAndWait();
		User newUser;
		if(!dResult.isPresent()){
			return false;
		}
		newUser = dResult.get();
		//System.out.println("newuser: " + newUser.getUsername());
		
		
		
		
		Dialog<Album> dialog2 = new Dialog<>();
		dialog2.initModality(Modality.APPLICATION_MODAL);
		dialog2.setTitle("Create a new album");
		dialog2.setResizable(false);
		dialog2.setHeaderText("Enter Album's information below:");

		Label albumName = new Label("Album Name: ");
		TextField albumNameTF = new TextField();
		Platform.runLater(() -> albumNameTF.requestFocus());

		GridPane gridPane2 = new GridPane();
		gridPane2.add(albumName, 1, 1);
		gridPane2.add(albumNameTF, 2, 1);
		dialog2.getDialogPane().setContent(gridPane2);

		ButtonType createBtn2 = new ButtonType("Create", ButtonData.OK_DONE);
		ButtonType cancelBtn2 = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog2.getDialogPane().getButtonTypes().addAll(createBtn2, cancelBtn2);

		dialog2.setResultConverter(new Callback<ButtonType, Album>() {
			String errorMessage = null;

			@Override
			public Album call(ButtonType btn) {
				if (btn == createBtn2) {
					if (albumNameTF.getText().trim().isEmpty()) {
						errorMessage = "Album name field cannot be empty.";
					}

					if (currentUser.getAlbumByName(albumNameTF.getText().trim()) == null) {
						errorMessage = "Album does not exists";
					}

					if (errorMessage != null) {
						
					   	Alert alert = new Alert(AlertType.ERROR);
				    	alert.setTitle("Error Dialog");
				    	alert.setHeaderText("Error: unable to create album");
				    	alert.setContentText(errorMessage);
				    	alert.showAndWait();
				    	
						return null;
					}

					return new Album(albumNameTF.getText());
				}

				return null;
			}
		});

		Optional<Album> dResult2 = dialog2.showAndWait();
		Album newAlbum;
		
		
		if(!dResult2.isPresent()){
			return false;
		}
		newAlbum = dResult2.get();
		//System.out.println("albname: " + newAlbum.getAlbumName());
		
		if(dResult2.isPresent() && dResult.isPresent()){
			//System.out.println("adding to the thing");
			listOfAllUsers.getUserByUsername(newUser.getUsername()).getAlbumByName(newAlbum.getAlbumName()).addPhoto(currentPhoto);
			saveData();
			return true;
		}
		return false;
	}
	
	@FXML
	private void makeAlb() throws ClassNotFoundException, IOException {
		newAlb.setAlbumName(nameAlbField.getText());
		listOfAllUsers.getUserByUsername(getUser().getUsername()).addAlbum(newAlb);
	//	listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(newAlb.getAlbumName())
		saveData();
	}
	@FXML
	private void playSlideshow() throws ClassNotFoundException, IOException {
		saveData();
		int selectedItemIndex = listView.getSelectionModel().getSelectedIndex();
		Parent root;
		Scene scene;
		currentPhoto = listView.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Slideshow.fxml"));
		saveData();
		root = (Parent)loader.load();
		scene = new Scene(root);
		SlideshowController controller = loader.getController();
		controller.setPhotoIndex(selectedItemIndex);
		controller.setListOfUsers(listOfAllUsers);
		controller.setAlbum(currentAlbum);
		controller.setUser(currentUser);
		controller.setStartPhoto(currentPhoto);
		controller.setStage(stage);

		//Scene slideshowScene = new Scene(root);
		
		controller.initData();
		
		stage.setScene(scene);

		stage.show();
	}
	@FXML
	private void addTags() throws ClassNotFoundException, IOException {
		String tagToParse = addtagsField.getText();
		currentPhoto = listView.getSelectionModel().getSelectedItem();
		Tag newTag = new Tag(tagToParse);
		//System.out.println(newTag.getTags());
		listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotoByName(currentPhoto.getPhotoName()).updateTags(newTag);
		saveData();
		//System.out.println(listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotoByName(currentPhoto.getPhotoName()).tags.tagnames);
		//System.out.println(listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotoByName(currentPhoto.getPhotoName()).tags.tagvalues);
	}
	 
	@FXML
	private void addCap() throws ClassNotFoundException, IOException {
		String cap = addcapText.getText();
		currentPhoto = listView.getSelectionModel().getSelectedItem();
		listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()).getPhotoByName(currentPhoto.getPhotoName()).setCaption(cap);
		saveData();
	}
	@FXML void search() throws ClassNotFoundException, IOException {
		String tagN = tagName.getText();
		String tagV = tagValue.getText();
		currentAlbum = listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(getAlbum().getAlbumName()); 
		Album a = new Album();
		for(Photo p: currentAlbum.getPhotos()){
			for (int i = 0; i < p.tags.tagnames.size(); i++) {
				if(p.tags.tagnames.get(i).equals(tagN)){
					if(p.tags.tagvalues.get(i).equals(tagV)){
						a.addPhoto(p);
					}
				}
			
			}
		}
		//System.out.print(a.getPhotos());
		if(a.getPhotos().size() != 0 ){
			//System.out.println("auyyyyyyoooooooo");
			obsList2 = FXCollections.observableArrayList(a.getPhotos());
			listView1.setItems(obsList2);
			
			listView1.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {

				@Override
				public ListCell<Photo> call(ListView<Photo> p) {
					ListCell<Photo> cell = new ListCell<Photo>() {
						@Override
						protected void updateItem(Photo photo, boolean empty) {
							super.updateItem(photo, empty);
							if (photo != null) {
								setText(photo.getPhotoName());
							} else {
								setText(null);
							}
						}
					};

					return cell;
				}
			});
			
			listView1.getSelectionModel().select(0);
		
		//	//System.out.println(currentUser.getAlbumByName(getAlbum().getAlbumName()).getSize());
		//	displayImage();
		}
		else{
			////System.out.print("else " + currentUser.getAlbumByName(getAlbum().getAlbumName()).getPhotos());
			obsList2 = FXCollections.observableArrayList(a.getPhotos());
			listView1.setItems(obsList2);
			listView1.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {

				@Override
				public ListCell<Photo> call(ListView<Photo> p) {
					ListCell<Photo> cell = new ListCell<Photo>() {
						@Override
						protected void updateItem(Photo photo, boolean empty) {
							super.updateItem(photo, empty);
							if (photo != null) {
								setText(photo.getPhotoName());
							} else {
								setText(null);
							}
						}
					};

					return cell;
				}
			});
		}
		newAlb = a;
	}
	
	/**
	 * @throws ClassNotFoundException
	 */
	private void saveData() throws ClassNotFoundException {
		try {
			UserList.write(listOfAllUsers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}