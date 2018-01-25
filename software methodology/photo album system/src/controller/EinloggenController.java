package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.*;

public class EinloggenController implements Initializable {
	@FXML
	TextField usernameTF;
	@FXML
	Label invalidLogin;
	@FXML
	FlowPane flowPane;
	@FXML
	Button loginBtn;

	private Stage stage;
	private UserList userList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			userList = UserList.read();
		} catch (FileNotFoundException f) {
			loadpresets();
		} catch (ClassNotFoundException | IOException o) {
			o.printStackTrace();
		}
		
		BooleanBinding bb = new BooleanBinding() {
			{
				super.bind(usernameTF.textProperty());
			}

			@Override
			protected boolean computeValue() {
				return (usernameTF.getText().trim().isEmpty());
			}
		};

		loginBtn.disableProperty().bind(bb);

		flowPane.setOnKeyReleased(e -> {
			try {
				if (e.getCode() == KeyCode.ENTER) {
					login(null);
				}
			} catch (ClassNotFoundException | IOException o) {
				o.printStackTrace();
			}
		});
	}
	
	void loadpresets()
	{
		UserList preset = new UserList(); 
		preset.addUser(new User("admin"));
		preset.addUser(new User("stock"));
		try {
			UserList.write(preset);
			userList = UserList.read();
		} catch (ClassNotFoundException | IOException o) {
			o.printStackTrace();
		}
	}

	/**
	 * @param event
	 *            ActionEvent
	 * @throws ClassNotFoundException,
	 *             IOException This method catches the user's click event to
	 *             login into the system.
	 */

	/**
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	private void login(ActionEvent event) throws ClassNotFoundException, IOException {
		Parent root;
		Stage adminStage, userStage;
		Scene adminScene, userScene;
		AdministrierenController adminCtrl;

		try {
			if (userList.userExists(usernameTF.getText().trim())) {
				if (usernameTF.getText().trim().equals("admin")) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Administrieren.fxml"));
					root = loader.load();
					adminScene = new Scene(root);
					adminStage = new Stage();
					adminStage.setResizable(false);
					adminStage.setTitle("Add/Delete Users");
					adminStage.setScene(adminScene);
					adminCtrl = loader.getController();
					adminCtrl.setStage(adminStage);
					closeLoginWindow();
					adminStage.show();
				} else {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumManagen.fxml")); 
					root = loader.load();
					userScene = new Scene(root);
					userStage = new Stage();
					userStage.setResizable(false);
					userStage.setTitle("Manage Albums");
					userStage.setScene(userScene);
					AlbumManagenController controller = loader.getController();
					controller.setListOfUsers(userList);
					controller.setCurrentUser(usernameTF.getText().trim());
					controller.setStage(userStage);
					controller.initData();
					closeLoginWindow();
					userStage.show();
				}
			}

			invalidLogin.setText("Username does not exist.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException n) {
			invalidLogin.setText("Username does not exist. (NPE)");
			loadpresets();
		}
	}

	/**
	 * closed the the current nonadmin user system window.
	 */

	/**
	 * 
	 */
	public void closeLoginWindow() {
		stage = (Stage) usernameTF.getScene().getWindow();
		stage.close();
	}

	/**
	 * @param stage
	 *            Stage sets the Admin's controller stage to the stage param.
	 */

	/**
	 * @param stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}
}