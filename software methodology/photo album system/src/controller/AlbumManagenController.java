package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.UserList;
import model.User;

public class AlbumManagenController implements Initializable {
	private UserList listOfAllUsers;
	private Stage stage;
	private User currentUser;
	private Album currentAlbum;

	@FXML
	Button createBtn;
	@FXML
	Button removeBtn;
	@FXML
	Button renameBtn;
	@FXML
	Button logoutBtn;
	@FXML
	Button searchBtn;
	@FXML
	Label UserNameLabel;
	private ObservableList<Album> obsList = null;
	@FXML
	ListView<Album> listView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
    @FXML
    public void handleMouseClick(MouseEvent e)throws ClassNotFoundException, IOException  {
    	if (e.getClickCount() == 2) {
    		openAlbum();
    	}
    }
    
    

	/**
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void initData() throws ClassNotFoundException, IOException {
		listOfAllUsers = UserList.read();
		obsList = FXCollections.observableArrayList(currentUser.getAlbums());
		listView.setItems(obsList);
		UserNameLabel.setText(currentUser.getUsername());

		listView.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>() {

			@Override
			public ListCell<Album> call(ListView<Album> p) {
				ListCell<Album> cell = new ListCell<Album>() {
					@Override
					protected void updateItem(Album album, boolean empty) {
						super.updateItem(album, empty);
						if (album != null) {
							setText(album.getAlbumName());
						} else {
							setText(null);
						}
					}
				};

				return cell;
			}
		});
		listView.getSelectionModel().select(0);
		renameBtn.disableProperty().bind(Bindings.size(obsList).isEqualTo(0));
		removeBtn.disableProperty().bind(Bindings.size(obsList).isEqualTo(0));
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
	@FXML
	private void createNewAlbum(ActionEvent event) throws ClassNotFoundException {
		Dialog<Album> dialog = new Dialog<>();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("Create a new album");
		dialog.setResizable(false);
		dialog.setHeaderText("Enter Album's information below:");

		Label albumName = new Label("Album Name: ");
		TextField albumNameTF = new TextField();
		Platform.runLater(() -> albumNameTF.requestFocus());

		GridPane gridPane = new GridPane();
		gridPane.add(albumName, 1, 1);
		gridPane.add(albumNameTF, 2, 1);
		dialog.getDialogPane().setContent(gridPane);

		ButtonType createBtn = new ButtonType("Create", ButtonData.OK_DONE);
		ButtonType cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(createBtn, cancelBtn);

		dialog.setResultConverter(new Callback<ButtonType, Album>() {
			String errorMessage = null;

			@Override
			public Album call(ButtonType btn) {
				if (btn == createBtn) {
					if (albumNameTF.getText().trim().isEmpty()) {
						errorMessage = "Album name field cannot be empty.";
					}

					if (currentUser.getAlbumByName(albumNameTF.getText().trim()) != null) {
						errorMessage = "Album name already exists";
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

		Optional<Album> dResult = dialog.showAndWait();

		if (dResult.isPresent()) {
			//System.out.println("234543");
			Album newAlbum = dResult.get();
			//System.out.println("albname: " + newAlbum.getAlbumName());
			listOfAllUsers.getUserByUsername(getUser().getUsername()).addAlbum(newAlbum); //this works
	//		currentUser.addAlbum(newAlbum);
	//		^^^this does not change set the serializable object with this user to have the new album name
			obsList.add(newAlbum);
			listView.getSelectionModel().select(0);

			try {
				//System.out.println("Writing.");
				UserList.write(listOfAllUsers);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return
	 */
	public User getUser() {
		return currentUser;
	}

	/**
	 * @param event
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void renameAlbum(ActionEvent event) throws ClassNotFoundException {
		Dialog<String> dialog = new Dialog<>();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("Rename an Album");
		dialog.setResizable(false);
		dialog.setHeaderText("Enter New Album's Name below:");

		Album currentAlbum = listView.getSelectionModel().getSelectedItem();

		if (currentAlbum == null) {
			return;
		}

		Label albumName = new Label("Album Name: ");
		TextField albumNameTF = new TextField(currentAlbum.getAlbumName());
		Platform.runLater(() -> albumNameTF.requestFocus());

		GridPane gridPane = new GridPane();
		gridPane.add(albumName, 1, 1);
		gridPane.add(albumNameTF, 2, 1);
		dialog.getDialogPane().setContent(gridPane);

		ButtonType renameBtn = new ButtonType("Rename", ButtonData.OK_DONE);
		ButtonType cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(renameBtn, cancelBtn);

		dialog.setResultConverter(new Callback<ButtonType, String>() {
			String errorMessage = null;

			@Override
			public String call(ButtonType btn) {
				if (btn == renameBtn) {

					if (albumNameTF.getText().trim().isEmpty()) {
						errorMessage = "Album's name field cannot be empty.";
					}

					if (currentUser.getAlbumByName(albumNameTF.getText().trim()) != null) {
						errorMessage = "Album name already exists";
					}

					if (errorMessage != null) {
						
					   	Alert alert = new Alert(AlertType.ERROR);
				    	alert.setTitle("Error Dialog");
				    	alert.setHeaderText("Error");
				    	alert.setContentText(errorMessage);
				    	alert.showAndWait();
						
						return null;
					}

					return albumNameTF.getText();
				}

				return null;
			}
		});

		Optional<String> dResult = dialog.showAndWait();

		if (dResult.isPresent()) {
			String newName = dResult.get();
			listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(currentAlbum.getAlbumName()).setAlbumName(newName); //this works
			//currentUser.getAlbumByName(currentAlbum.getAlbumName()).setAlbumName(newName);
			obsList = FXCollections.observableArrayList(listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbums());
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
		Album a = listView.getSelectionModel().getSelectedItem();
		obsList.remove(a);
		listOfAllUsers.getUserByUsername(getUser().getUsername()).removeAlbum(
				listOfAllUsers.getUserByUsername(getUser().getUsername()).getAlbumByName(a.getAlbumName()));
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
	private void openAlbum() throws ClassNotFoundException, IOException {
		
		Parent root;
		Scene scene;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Photoview.fxml"));
		saveData();
		root = (Parent) loader.load();
		scene = new Scene(root);
		PhotoviewController controller = loader.getController();
		currentAlbum = listView.getSelectionModel().getSelectedItem();
		controller.setListOfUsers(listOfAllUsers);
		//System.out.println("curralbum" + currentAlbum.getAlbumName());
		controller.setAlbum(currentAlbum);
		controller.setUser(currentUser);
		controller.setStage(stage);
		controller.initData();
		stage.setScene(scene);
		stage.show();
		
	}

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
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void loggingOut(ActionEvent event) throws IOException, ClassNotFoundException {
		Parent root;
		Scene scene;
		Stage logoutStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Einloggen.fxml"));
		root = loader.load();

		scene = new Scene(root);
		logoutStage = new Stage();
		logoutStage.setResizable(false);
		logoutStage.setTitle("Foto-Viewer Login Page");
		logoutStage.setScene(scene);
		saveData();
		stage.close();
		logoutStage.show();
	}

	/**
	 * @param u
	 */
	public void setUser(User u) {
		this.currentUser = u;
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