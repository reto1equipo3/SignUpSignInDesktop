/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsignindesktop.ui.controllers;

import exceptions.BadPasswordException;
import exceptions.DatabaseException;
import exceptions.LoginNotExistingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.UserBean;

/**
 * Controller UI class for SignIn view in SignUpSignIn application. It contains
 * event handlers and initialization code for the view defined in
 * SignInDesktopFxmlController.fxml file.
 *
 * @author Igor
 */
public class SignInDesktopFxmlController extends GenericController {

	private static final Logger logger = Logger.getLogger("SignInDesktop.controller");

	private UserBean user;

	/**
	 * User's login name UI text field.
	 */
	@FXML
	private TextField txtLogin;
	/**
	 * User's password value UI password field.
	 */
	@FXML
	private PasswordField pwdPassword;
	/**
	 * Button to fire action to log in at the UI.
	 */
	@FXML
	private Button btnSignIn;
	/**
	 * Hiperlink to fire action to Sign up at the UI.
	 */
	@FXML
	private Hyperlink hpSignUp;
	/**
	 * Remember login checkbox.
	 */
	@FXML
	private CheckBox chkRememberLogin;
	/**
	 * User's image UI image view.
	 */
	@FXML
	private ImageView imgUser;
	/**
	 * Login error label.
	 */
	@FXML
	private Label lblErrorLogin;
	/**
	 * Password error label.
	 */
	@FXML
	private Label lblErrorPass;

	public void setUser(UserBean user) {
		this.user = user;
	}

	/**
	 * Create new Text file to save the Login
	 */
	File file = new File("UserData.txt");

	/**
	 * Method for initializing SignIn Stage.
	 *
	 * @param root The Parent object representing root node of view graph.
	 */
	public void initStage(Parent root) {
		LOGGER.info("Initializing Login stage");
		//Create a scene 
		Scene scene = new Scene(root);
		stage = new Stage();
		//Associate scene to stage(window)  
		stage.setScene(scene);

		//Set window title
		stage.setTitle("Sign In");

		//Set window size
		stage.setResizable(false);

		//Set window's envent handlers
		stage.setOnShowing(this::handleWindowShowing);

		//Set control events handle
		btnSignIn.setOnAction(this::handleSignInAction);
		hpSignUp.setOnAction(this::handleSignUpAction);
		pwdPassword.textProperty().addListener(this::textChanged);
		txtLogin.textProperty().addListener(this::textChanged);

		//show window
		stage.show();

	}

	/**
	 * Window event method handler. It implements behavior for
	 * WINDOW_SHOWING type event.
	 *
	 * @param event The window event
	 */
	private void handleWindowShowing(WindowEvent event) {
		LOGGER.info("Starting SignInDesktopController::handleWindowShowing");
		readData();
		//Sign In button is disable
		btnSignIn.setDisable(true);
		imgUser.setImage(new Image("/signupsignindesktop/ui/img/defaultUserPhoto.png"));

		btnSignIn.setTooltip(new Tooltip("Rellena los campos login y password para habilitar el boton"));

		btnSignIn.setMnemonicParsing(true);
		btnSignIn.setText("_Sign In");

		hpSignUp.setMnemonicParsing(true);
		hpSignUp.setText("Sign _Up");

	}

	/**
	 * Action event handler for SignIn button. It validates that user and
	 * password fields are filled. If they are not, an error message dialog
	 * is shown. Otherwise the UserView window is opened.
	 *
	 * @param event The Action event
	 */
	@FXML
	private void handleSignInAction(ActionEvent event) {
		LOGGER.info("Beginning SignInDesktopController::handleSignInAction.");

		//Validates login and password fields
		user = new UserBean();
		user.setLogin(txtLogin.getText());
		user.setPassword(pwdPassword.getText());

		try {
			user = logic.signIn(user);
			if (chkRememberLogin.isSelected()) {
				RememberUserLogin();
			}
			GoToUserView();

		} catch (LoginNotExistingException e) {
			LOGGER.log(Level.SEVERE, "SignInDesktopController::handleSignInAction: {0}", e.getMessage());

			lblErrorLogin.setText("*Wrong login.");
			lblErrorLogin.setVisible(true);

			txtLogin.setStyle("-fx-border-color:red;");
			txtLogin.requestFocus();

		} catch (BadPasswordException e) {
			LOGGER.log(Level.SEVERE, "SignInDesktopController::handleSignInAction: {0}", e.getMessage());

			lblErrorPass.setText("*Wrong Password.");
			lblErrorPass.setVisible(true);

			pwdPassword.setStyle("-fx-border-color:red;");
			pwdPassword.requestFocus();

		} catch (DatabaseException ex) {
			LOGGER.log(Level.SEVERE, "SignInDesktopController::handleSignInAction: {0}", ex.getMessage());
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Sorry, something went wrong. Try again later.");
			alert.showAndWait();
		}

	}

	/**
	 * Action event handler for SignUp hyperlink. It opens the SignUp
	 * window.
	 *
	 * @param event The Action event
	 */
	@FXML
	private void handleSignUpAction(ActionEvent event) {
		LOGGER.info("Beginning SignInDesktopController::handleSignUpAction.");
		try {
			//Load node graph from fxml file 
			FXMLLoader loader
				= new FXMLLoader(getClass().getResource("/signupsignindesktop/ui/fxml/SignUpDesktopFXMLDocument.fxml"));

			Parent root = loader.load();

			//Get controller for graph 
			SignUpDesktopFxmlController controller
				= ((SignUpDesktopFxmlController) loader.getController());
			controller.setLogic(logic);
			//Initializes stage
			controller.initStage(root);
			//hides login stage
			//stage.hide();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,
				"UI SignInDesktopFxmlController: Error opening SignUp window: {0}",
				e.getMessage());
		}
	}

	/**
	 * Text changed event handler. It validates that login and password
	 * fields has any content to enable/disable SignIn button. Also limit
	 * the number of characters on the Login textField and Password
	 * textField and changes the border color of txtFields
	 *
	 * @param observable The value being observed.
	 * @param oldValue The old value of the observable.
	 * @param newValue The new value of the observable.
	 */
	private void textChanged(ObservableValue observable, String oldValue,
		String newValue) {
		if (txtLogin.getText().trim().length() > this.MAX_LENGTH
			|| pwdPassword.getText().trim().length() > this.MAX_LENGTH) {

			LOGGER.info("UI SignInDesktopFxmlController: The maximum length of the field is 255 characters: {0}");
			btnSignIn.setDisable(true);
		} //If text fields are empty disable Sign In button
		else if (txtLogin.getText().trim().isEmpty() || pwdPassword.getText().trim().isEmpty()) {
			btnSignIn.setDisable(true);
		} //Enable Sign In button
		else {
			btnSignIn.setDisable(false);
		}

		//Put Login textField border color to default color
		if (!txtLogin.getText().trim().isEmpty()) {
			txtLogin.setStyle("-fx-border-color:AXIS_COLOR;");
			lblErrorLogin.setVisible(false);
		}
		//Put Password textField border color to default color
		if (!pwdPassword.getText().trim().isEmpty()) {
			pwdPassword.setStyle("-fx-border-color:AXIS_COLOR;");
			lblErrorPass.setVisible(false);
		}

		//Limit the number of characters on the Login textField
		if (txtLogin.getLength() == 21) {
			txtLogin.setText(txtLogin.getText().substring(0, 20));
		}
		//Limit the number of characters on the Password passwordField
		if (pwdPassword.getLength() == 16) {
			pwdPassword.setText(pwdPassword.getText().substring(0, 15));
		}

	}

	/**
	 * Method to open UserView window
	 *
	 */
	private void GoToUserView() {
		try {
			//Load node graph from fxml file 
			FXMLLoader loader
				= new FXMLLoader(getClass().getResource("/signupsignindesktop/ui/fxml/UserViewDesktopFXMLDocument.fxml"));

			Parent root = loader.load();

			//Get controller for graph 
			UserViewDesktopFxmlController controller
				= ((UserViewDesktopFxmlController) loader.getController());
			controller.setLogic(logic);
			controller.setUser(user);

			//Initializes stage
			controller.initStage(root);

			//hides login stage
			stage.hide();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,
				"UI SignUpDesktopFxmlController: Error opening UserView window.",
				e.getMessage());

		}
	}

	/**
	 * Method to save the users Login in TXT file.
	 *
	 */
	public void RememberUserLogin() {
		try {
			//If file does not exist we create a new one
			if (!file.exists()) {
				file.createNewFile();
			}
			//Instance of BufferWriter
			BufferedWriter bw;

			//Initialization of BufferedWriter with FileWriter as parameter with "file" as parameter
			bw = new BufferedWriter(new FileWriter(file));

			//we write the data that we want to save in the TXT file
			bw.write(txtLogin.getText());

			//Close to save the user data
			bw.close();

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,
				"UI SignUpDesktopFxmlController: Error saving user data.",
				e.getMessage());

		}

	}

	/**
	 * Method to put in the txtLogin the users Login saved in a TXT file.
	 *
	 */
	private void readData() {
		String[] datos;
		//To save the data temporarily
		String read = null;
		//If file exist we read the user data
		if (file.exists()) {
			try {
				Scanner reader = new Scanner(file);
				while (reader.hasNextLine()) {
					read = reader.next();
				}
				if (read != null) {
					datos = read.split(",");
					txtLogin.setText(datos[0]);

				}
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.SEVERE,
					"UI SignUpDesktopFxmlController: Error opening file.",
					e.getMessage());
			}
		}
	}

}
