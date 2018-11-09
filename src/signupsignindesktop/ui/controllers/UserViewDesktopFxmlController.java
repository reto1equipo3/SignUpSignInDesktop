/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsignindesktop.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.UserBean;
import signupsignindesktop.businesslogic.LogicTestData;


/**
 * FXML Controller UserView class for User view in SignUpSingIn applicattion. It
 * contains event handlers and initialization code for the view defined in
 * UserViewDesktopFxmlController.fxml file.
 *
 * @author Leticia
 */
public class UserViewDesktopFxmlController extends GenericController {

    private UserBean user;
    private LogicTestData logicTestData;

    /**
     * User's lays out its children within a flexible grid of rows and columns.
     */
    @FXML
    private GridPane gpVistaUsuario;
    /**
     * User's photo.
     */
    @FXML
    private ImageView imgUserPhoto;
    /**
     * User's gretting "welcome".
     */
    @FXML
    private Label lblWelcome;
    /**
     * User's full name UI Label field.
     */
    @FXML
    private Label lblFullName;
    /**
     * Menu button that contains an item to Log out.
     */
    @FXML
    private MenuButton btnSettings;
    /**
     * Menut item to fire action to log out at the aplication and returns to
     * Sign in view.
     */
    @FXML
    private MenuItem btnLogOut;

    /**
     * User's login tittle UI label field.
     */
    @FXML
    private Label lblLogin;
    /**
     * User's login name UI Label field.
     */
    @FXML
    private Label lblLoginUser;
    /**
     * User's email tittle UI label field.
     */
    @FXML
    private Label lblEmail;
    /**
     * User's email name UI label field.
     */
    @FXML
    private Label lblEmailUser;

    //Setters del user es el que manda la interfaz SignIn.  
    public void setUser(UserBean user) {

        this.user = user;
    }

    /**
     * Method for initializing UserView Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing user View stage.");

        Scene scene = new Scene(root);//Crear la escena a la raiz del nodo root.
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        //Asociamos la escena a la ventana
        stage.setScene(scene);

        //Le vamos a dar un titulo a la ventana.       
        stage.setTitle("User View");

        //Para que la ventana no sea redimensionable
        stage.setResizable(false);

        // Configurar los manejadores de eventos de la ventana
        stage.setOnShowing(this::handleWindowShowing);

        //Para ver la ventana
        stage.show();
    }

    /**
     * Window event method handler. It implements behaviour for WINDOW_SHOWING
     * type event.
     *
     * @param event The window event
     */
    private void handleWindowShowing(WindowEvent event) {

        LOGGER.info("Beginning UserView controller::handleWindowsShowing");

        //Mostrar el full Name del User
        if (user.getFullName() == null || user.getFullName().trim().equals("")) {
            lblFullName.setText("Fullname not found");
        } else {
            lblFullName.setText(user.getFullName());
        }

        //Mostrar el login del User
        if (user.getLogin() == null || user.getLogin().trim().equals("")) {
            lblLoginUser.setText("Login not found");

        } else {
            lblLoginUser.setText(user.getLogin());
        }

        //Mostrar el email del User
        if (user.getEmail() == null || user.getEmail().trim().equals("")) {
            lblEmailUser.setText("Email not found");
        } else {
            lblEmailUser.setText(user.getEmail());
        }

        //La foto del usuario
        if (user.getPhoto() == null) {
            imgUserPhoto.setImage(new Image("/signupsignindesktop/ui/img/defaultUserPhoto.png"));
        } else {
            imgUserPhoto.setImage((Image) user.getPhoto());
        }

        //El boton esta escuchando. 
        btnLogOut.setOnAction(this::handleLogOutAction);

        //Mnoemónico
        btnSettings.setMnemonicParsing(true);
        btnSettings.setText("_Settings");
        btnLogOut.setMnemonicParsing(true);
        btnLogOut.setText("_Log Out");

        btnSettings.setTooltip(new Tooltip("Item of Log Out"));

    }

    /**
     * Action event handler for log out button. Close session and return to the
     * sign in.
     *
     * @param event The ActionEvent object for the event.
     */
    private void handleLogOutAction(ActionEvent event) {

        //Tenemos que mostrar dialogo para ver que quiere hacer el usuario. 
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);  //No queremos que salga titulo eb la cabecera
        alert.setContentText("Are you sure you want to log out application?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                //Load node graph from fxml file
                FXMLLoader loader
                        = new FXMLLoader(getClass().getResource("/signupsignindesktop/ui/fxml/SignInDesktopFXMLDocument.fxml"));
                Parent root = (Parent) loader.load();
                //Get controller for graph 
                SignInDesktopFxmlController controller
                        = ((SignInDesktopFxmlController) loader.getController());
                controller.setLogic(logic);
                //Initializes stage
                controller.initStage(root);
                //hides UserView stage
                stage.hide();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE,
                        "UI UserView: Error opening SignIn window: {0}",
                        ex.getMessage());
            }
        }
    }

}
