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
    
    /**
     * Initializing handleWindow of Sign in, with button Log out
     */

    @Test
    public void test1_initialStageLogOut() {

        /* doubleClickOn("#txtLogin");
        verifyThat("#txtLogin", hasText(" "));
        doubleClickOn("#pwdPassword");
        verifyThat("#pwdPassword", hasText(" ")); */
        doubleClickOn("#txtLogin");
        write("Leticia");
        clickOn("#pwdPassword");
        write("Abcd*123");
        clickOn("#btnSignIn");
        verifyThat("#gpVistaUsuario", isVisible());

        //Prueba Log Out
        clickOn("#btnExit");
        clickOn("#btnLogOut");
        //Dialog alert aceptar va ha volver a la vista de Sign In          
        sleep(2000);
        push(KeyCode.ENTER);
        verifyThat("#gpSignIn", isVisible());
        sleep(2000);

    }
    /**
     * Test to prove a bad login.
     */

    @Test
    public void test2_BadLogin() {
        doubleClickOn("#txtLogin");
        write("Leticita");
        clickOn("#pwdPassword");
        write("Abcd*123");
        clickOn("#btnSignIn");
        verifyThat("#gpVistaUsuario", isVisible());

    }

    /**
     * Test to prove a bad password
     */
    @Test
    public void test3_BadPssword() {
        doubleClickOn("#txtLogin");
        write("Leticia");
        clickOn("#pwdPassword");
        write("Abcd*123444");
        clickOn("#btnSignIn");
        verifyThat("#gpVistaUsuario", isVisible());

    }

    /**
     * Close application  with button close
     */
    @Test
    public void test4_initialStageClose() {

        /* doubleClickOn("#txtLogin");
        verifyThat("#txtLogin", hasText(" "));
        doubleClickOn("#pwdPassword");
        verifyThat("#pwdPassword", hasText(" ")); */
        doubleClickOn("#txtLogin");
        write("Leticia");
        clickOn("#pwdPassword");
        write("Abcd*123");
        clickOn("#btnSignIn");
        verifyThat("#gpVistaUsuario", isVisible());
        //Prueba Log Out
        clickOn("#btnExit");
        clickOn("#btnClose");
        //Dialog alert aceptar va ha volver a la vista de Sign In          
        sleep(2000);
        push(KeyCode.ENTER);
        sleep(1000);
    }

}
