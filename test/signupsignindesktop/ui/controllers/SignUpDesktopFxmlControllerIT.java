/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsignindesktop.ui.controllers;

import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import signupsignindesktop.applications.SignUpTestApplication;

/**
 *
 * @author Imanol
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpDesktopFxmlControllerIT extends ApplicationTest {

	@Override
	public void start(Stage stage) throws Exception {
		new SignUpTestApplication().start(stage);
	}

	@Override
	public void stop() {
	}

	/**
	 * Tests the initial state of the sign up window
	 */
	@Test
	public void test0_InitialState() {
		// Fullname
		verifyThat("#txtFullName", isEnabled());
		verifyThat("#txtFullName", hasText(""));
		verifyThat("#lblErrorFullName", isInvisible());

		// Email
		verifyThat("#txtEmail", isEnabled());
		verifyThat("#txtEmail", hasText(""));
		verifyThat("#lblErrorEmail", isInvisible());

		// Login
		verifyThat("#txtLogin", isEnabled());
		verifyThat("#txtLogin", hasText(""));
		verifyThat("#lblErrorLogin", isInvisible());

		// Password
		verifyThat("#pwdPassword", isEnabled());
		verifyThat("#pwdPassword", hasText(""));
		verifyThat("#lblErrorPassword", isInvisible());

		// Choose file
		verifyThat("#imgUserPhoto", isVisible());
		verifyThat("#btnChooseFile", isEnabled());

		// Terms of use
		//verifyThat("#chkTermsOfUse", isSelected());
		verifyThat("#hpTermsOfUse", isEnabled());

		// Sign up button
		verifyThat("#btnSignUp", isEnabled());

		// Sign in hyperlink
		verifyThat("#hpSignIn", isEnabled());
	}

	/**
	 * Tests errors are shown when clicked on Sign up button with empty fields
	 * and that errors disappear after writing on them.
	 */
	@Test
	public void test1_ErrorsAppearAndDisappear() {
		// Click on sign up button with all fields empty
		clickOn("#btnSignUp");

		// Fullname
		verifyThat("#txtFullName", isFocused());
		verifyThat("#lblErrorFullName", isVisible());
		write("Paco Pico");
		verifyThat("#lblErrorFullName", isInvisible());

		// Email
		clickOn("#txtEmail");
		verifyThat("#lblErrorEmail", isVisible());
		write("pPico@gmail.com");
		verifyThat("#lblErrorEmail", isInvisible());

		// Login
		clickOn("#txtLogin");
		verifyThat("#lblErrorLogin", isVisible());
		write("pPico");
		verifyThat("#lblErrorLogin", isInvisible());

		// Password
		clickOn("#pwdPassword");
		verifyThat("#lblErrorPassword", isVisible());
		write("Abcd*1234");
		verifyThat("#lblErrorPassword", isInvisible());

		// Terms of use
		verifyThat("#lblErrorTermsOfUse", isVisible());
		clickOn("#chkTermsOfUse");
		verifyThat("#lblErrorTermsOfUse", isInvisible());
	}

	/**
	 * Tests that only a valid fullname is accepted.
	 */
	@Test
	public void test2_OnlyValidFullnameAccepted() {
		// Not null
		clickOn("#btnSignUp");
		verifyThat("#lblErrorFullName", isVisible());

		// Not empty
		write(" ");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorFullName", isVisible());

		// Shorter than 50
		write("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijk");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorFullName", isVisible());

		// Matches pattern
		write("123f%122rfgdfGg");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorFullName", isVisible());

		// Is valid
		write("Paco Pico");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorFullName", isInvisible());
	}

	/**
	 * Tests that only a valid email is accepted.
	 */
	@Test
	public void test3_OnlyValidEmailAccepted() {
		// Not null
		clickOn("#btnSignUp");
		verifyThat("#lblErrorEmail", isVisible());

		// Not empty
		clickOn("#txtEmail");
		write(" ");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorEmail", isVisible());

		// Shorter than 50
		doubleClickOn("#txtEmail");
		write("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijk");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorEmail", isVisible());

		// Matches pattern
		doubleClickOn("#txtEmail");
		write("123*Sdas@gasdasd.asdasdaa");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorEmail", isVisible());

		// Is valid
		doubleClickOn("#txtEmail");
		clickOn("#txtEmail");
		write("Paco-Pico_01@gmail.com");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorEmail", isInvisible());
	}

	/**
	 * Tests that only a valid login is accepted.
	 */
	@Test
	public void test4_OnlyValidLoginAccepted() {
		// Not null
		clickOn("#btnSignUp");
		verifyThat("#lblErrorLogin", isVisible());

		// Not empty
		clickOn("#txtLogin");
		write(" ");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorLogin", isVisible());

		// Shorter than 20
		doubleClickOn("#txtLogin");
		write("abcdefghijabcdefghijk");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorLogin", isVisible());

		// Matches pattern
		doubleClickOn("#txtLogin");
		write("123sadgsdSDFDSR#$");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorLogin", isVisible());

		// Is valid
		doubleClickOn("#txtLogin");
		clickOn("#txtLogin");
		write("pPaco1");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorLogin", isInvisible());
	}

	/**
	 * Tests that only a valid password is accepted and the confirmation
	 * password is correct.
	 */
	@Test
	public void test5_OnlyValidPasswordAccepted() {
		// Not null
		clickOn("#btnSignUp");
		verifyThat("#lblErrorPassword", isVisible());

		// Not empty
		clickOn("#pwdPassword");
		write(" ");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorPassword", isVisible());

		// Shorter than 15
		doubleClickOn("#pwdPassword");
		write("1234567890123456");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorPassword", isVisible());

		// Longer than 6
		doubleClickOn("#pwdPassword");
		write("123456");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorPassword", isVisible());

		// Matches the pattern
		doubleClickOn("#pwdPassword");
		write("abcdfgast");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorPassword", isVisible());

		// Both password are equal
		doubleClickOn("#pwdPassword");
		write("Abcd*1234");
		clickOn("#pwdConfirmPassword");
		write("asdasdfasdf");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorPassword", isVisible());

		// Is valid
		doubleClickOn("#pwdPassword");
		clickOn("#pwdPassword");
		write("Abcd*1234");
		doubleClickOn("#pwdConfirmPassword");
		write("Abcd*1234");
		clickOn("#btnSignUp");
		verifyThat("#lblErrorPassword", isInvisible());
	}

	/**
	 * Test that sign in window is opened when click on the hyperlink.
	 */
	@Test
	public void test7_GoToSignIn(){
		clickOn("#hpSignIn");
		
		verifyThat("#gpIdentificar", isVisible());
	}
}
