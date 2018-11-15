/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsignindesktop.ui.controllers;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.UserBean;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import signupsignindesktop.applications.Application;

/**
 *
 * @author Imanol
 */
public class NewUserCompleteTest extends ApplicationTest {
	
	private UserBean newUser = new UserBean(null, "test08", "test08@email.com", "Test Eight", null, null, "Abcd*1234", null, null);

	@Override
	public void start(Stage stage) throws Exception {
		new Application().start(stage);
	}

	@Test
	public void test1_SignUpNewUser(){
		// Go to Sign Up View
		clickOn("#hpSignUp");

		// Fill the fields
		clickOn("#txtFullName");
		write(newUser.getFullName());

		clickOn("#txtEmail");
		write(newUser.getEmail());

		clickOn("#txtLogin");
		write(newUser.getLogin());

		clickOn("#pwdPassword");
		write(newUser.getPassword());

		clickOn("#pwdConfirmPassword");
		write(newUser.getPassword());

		clickOn("#chkTermsOfUse");
		
		// Try to SignUp user
		clickOn("#btnSignUp");

		// Verify that we are back at the SignIn View
		sleep(1000);
		push(KeyCode.ENTER);
		verifyThat("#gpSignIn", isVisible());		

		// Fill in fields to sign in
		clickOn("#txtLogin");
		write(newUser.getLogin());

		clickOn("#pwdPassword");
		write(newUser.getPassword());

		// Try to SignIn user
		clickOn("#btnSignIn");

		// Verify that we are at the UserView
		sleep(1000);
		verifyThat("#gpVistaUsuario", isVisible());

		// Try to log out
		clickOn("#btnSettings");
		clickOn("#btnLogOut");
		sleep(1000);
		push(KeyCode.ENTER);
		sleep(1000);
		verifyThat("#gpSignIn", isVisible());
	}

	
}
