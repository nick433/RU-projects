package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;

public class AdministrierenController implements Initializable {
	@FXML
	private Button createBtn;
	@FXML
	private Button removeBtn;
	@FXML
	private ListView<User> listView;
	UserList listOfAllUsers = null;
	Stage stage;
	private ObservableList<User> obsList = null;

	/*
	 * Called to initialize a controller after its root element has been
	 * completely processed. (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initData();
		} catch (FileNotFoundException f) {
			UserList preset = new UserList(); 
			preset.addUser(new User("admin"));
			preset.addUser(new User("stock"));
			try {
				UserList.write(preset);
				initData();
			} catch (ClassNotFoundException | IOException o) {
				o.printStackTrace();
			}
		} catch (ClassNotFoundException | IOException o) {
			o.printStackTrace();
		}
		listView.getSelectionModel().select(0);
	}

	/**
	 * This method initializes the controllers data variables after its root
	 * element is complete.
	 */

	public void initData() throws ClassNotFoundException, IOException {
		listOfAllUsers = UserList.read();
		obsList = FXCollections.observableArrayList(listOfAllUsers.getUsers());
		listView.setItems(obsList);

		listView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {

			@Override
			public ListCell<User> call(ListView<User> p) {
				ListCell<User> cell = new ListCell<User>() {

					@Override
					protected void updateItem(User user, boolean empty) {
						super.updateItem(user, empty);
						if (user != null) {
							setText(user.getUsername());
							setText("Username: " + user.getUsername());
						} else {
							setText(null);
						}
					}
				};

				return cell;
			}
		});

		listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (obs != null) {
				if (obs.getValue().getUsername().equals("admin")) {
					removeBtn.setDisable(true);
				} else {
					removeBtn.setDisable(false);
				}
			}
		});
	}

	/**
	 * @param event
	 *            ActionEvent.
	 * @throws ClassNotFoundException
	 *             This method catches the user's click event to create a new
	 *             user and creates a new dialog for the user.
	 */

	@FXML
	private void createNewUserAction(ActionEvent event) throws ClassNotFoundException {
		GridPane gridPane;
		ButtonType createBtn, cancelBtn;

		Dialog<User> dialog = new Dialog<>();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("Create a new user");
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
					} else if (listOfAllUsers.isUsernameTaken(usernameTF.getText())) {
						errorMessage = "Username already taken.";
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

		if (dResult.isPresent()) {
			User newUser = dResult.get();
			listOfAllUsers.addUser(newUser);
			obsList.add(newUser);
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
	 *            ActionEvent
	 * @throws ClassNotFoundException
	 *             This method catches the user's click event to remove a new
	 *             user and removes the selected user from the listOfAllUsers.
	 */

	@FXML
	private void removeUserAction(ActionEvent event) throws ClassNotFoundException, IOException {
		User userToBeRemoved = listView.getSelectionModel().getSelectedItem();
		obsList.remove(userToBeRemoved);
		listOfAllUsers.removeUser(userToBeRemoved);
		listView.getSelectionModel().select(0);
		UserList.write(listOfAllUsers);
	}

	/**
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	@FXML
	private void loggingOut(ActionEvent event) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Einloggen.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("Foto-Viewer Login Page");
		stage.setScene(scene);
		saveData();
		closeAdminAppWindow();
		stage.show();
	}

	/**
	 * closed the the current Admin system window.
	 */

	private void closeAdminAppWindow() {
		this.stage.close();
	}

	/**
	 * @throws ClassNotFoundException
	 *             This method saves the data of all users.
	 */

	private void saveData() throws ClassNotFoundException {
		try {
			UserList.write(listOfAllUsers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param stage
	 *            Stage sets the Admin's controller stage to the stage param.
	 */

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}