/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsignindesktop.ui.controllers;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;

import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import org.testfx.robot.Motion;

import signupsignindesktop.applications.Application;

/**
 * UserView controller Test Class
 *
 * @author Leticia
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserViewDesktopFxmlControllerIT extends ApplicationTest {

    public void start(Stage stage) throws Exception {
        new Application().start(stage);
        {
        }
    }

    @Test
    public void test1_initialStage() {

        /* doubleClickOn("#txtLogin");
        verifyThat("#txtLogin", hasText(" "));
        doubleClickOn("#pwdPassword");
        verifyThat("#pwdPassword", hasText(" ")); */
        clickOn("#txtLogin");
        write("login1");
        clickOn("#pwdPassword");
        write("password1");
        clickOn("#btnSignIn");
        verifyThat("#gpVistaUsuario", isVisible());

        //Prueba Log Out
        clickOn("#btnSettings");
        clickOn("#btnLogOut");
        //Dialog alert aceptar va ha volver a la vista de Sign In          
        sleep(1000);
        push(KeyCode.ENTER); 
        verifyThat("#gpIdentificar", isVisible());

    }

}
