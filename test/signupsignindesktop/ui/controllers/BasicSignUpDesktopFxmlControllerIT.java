package signupsignindesktop.ui.controllers;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.UserBean;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import signupsignindesktop.applications.SignUpTestApplication;

/**
 * Basic TestFx class to test connection errors (repeated login and email) and a
 * valid Sign Up action.
 *
 * @author Imanol
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BasicSignUpDesktopFxmlControllerIT extends ApplicationTest {

	private final UserBean user;

	public BasicSignUpDesktopFxmlControllerIT() {
		// Set up a valid user, but repeated login
		user = new UserBean();
		user.setFullName("Test SignUp");
		user.setEmail("testSignUp@email.com");
		user.setLogin("testSignUp");
		user.setPassword("Abcd*1234");
	}

	@Override
	public void start(Stage stage) throws Exception {
		new SignUpTestApplication().start(stage);
	}

	/**
	 * Tests EmailNotUniqueException behaviour.
	 */
	@Ignore
	@Test
	public void test1_repeatedEmail() {
		// Fill in the fields
		clickOn("#txtFullName");
		write(user.getFullName());

		clickOn("#txtEmail");
		write("test01@email.com"); // Already existing email

		clickOn("#txtLogin");
		write(user.getLogin());

		clickOn("#pwdPassword");
		write(user.getPassword());

		clickOn("#pwdConfirmPassword");
		write(user.getPassword());

		clickOn("#chkTermsOfUse");

		// Sign Up user
		clickOn("#btnSignUp");

		// Check that email error label is shown
		verifyThat("* Email already used.", isVisible());
	}

	/**
	 * Tests LoginExistingException behaviour.
	 */
	@Ignore
	@Test
	public void test2_repeatedLogin() {
		// Fill in the fields
		clickOn("#txtFullName");
		write(user.getFullName());

		clickOn("#txtEmail");
		write(user.getEmail());

		clickOn("#txtLogin");
		write("test01"); // Already existing login

		clickOn("#pwdPassword");
		write(user.getPassword());

		clickOn("#pwdConfirmPassword");
		write(user.getPassword());

		clickOn("#chkTermsOfUse");

		// Sign Up user
		clickOn("#btnSignUp");

		// Check that login error label is shown
		verifyThat("* Login already exists.", isVisible());
	}

	/**
	 * Tests valid sign up action.
	 */
	@Ignore
	@Test
	public void test3_validUser() {
		// Fill in the fields
		clickOn("#txtFullName");
		write(user.getFullName());

		clickOn("#txtEmail");
		write(user.getEmail());

		clickOn("#txtLogin");
		write(user.getLogin());

		clickOn("#pwdPassword");
		write(user.getPassword());

		clickOn("#pwdConfirmPassword");
		write(user.getPassword());

		clickOn("#chkTermsOfUse");

		// Sign Up user
		clickOn("#btnSignUp");

		// Check new user signed up correctly and SignInView is visible
		sleep(1000);
		push(KeyCode.ENTER);
		verifyThat("#gpSignIn", isVisible());
	}

	/**
	 * Tests only valid fullname is allowed.
	 */
	@Test
	public void test4_invalidFullname() {
		// Not null
		clickOn("#btnSignUp");
		verifyThat("* Field can not be empty.", isVisible());

		// Not empty
		write(" ");
		clickOn("#btnSignUp");
		verifyThat("* Field can not be empty.", isVisible());

		// Shorter than 50
		write("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijk");
		clickOn("#btnSignUp");
		verifyThat("* Fullname is too long.", isVisible());

		// Matches pattern
		write("123f%122rfgdfGg");
		clickOn("#btnSignUp");
		verifyThat("* Only uppercase or lowercase letters.", isVisible());
	}

	/**
	 * Tests only valid email is allowed.
	 */
	@Test
	public void test5_invalidEmail() {
		// Not null
		clickOn("#btnSignUp");
		verifyThat("* Field can not be empty.", isVisible());

		// Not empty
		clickOn("#txtEmail");
		write(" ");
		clickOn("#btnSignUp");
		verifyThat("* Field can not be empty.", isVisible());

		// Shorter than 50
		doubleClickOn("#txtEmail");
		write("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijk");
		clickOn("#btnSignUp");
		verifyThat("* Email is too long.", isVisible());

		// Matches pattern
		doubleClickOn("#txtEmail");
		write("123*Sdas@gasdasd.asdasdaa");
		clickOn("#btnSignUp");
		verifyThat("* Enter a valid email.", isVisible());
	}

	/**
	 * Tests only valid login is allowed.
	 */
	@Test
	public void test6_invalidLogin(){
		// Not null
		clickOn("#btnSignUp");
		verifyThat("* How are you supposed to sign in?", isVisible());

		// Not empty
		clickOn("#txtLogin");
		write(" ");
		clickOn("#btnSignUp");
		verifyThat("* How are you supposed to sign in?", isVisible());

		// Shorter than 20
		doubleClickOn("#txtLogin");
		write("abcdefghijabcdefghijk");
		clickOn("#btnSignUp");
		verifyThat("* Login is too long.", isVisible());

		// Matches pattern
		doubleClickOn("#txtLogin");
		write("123sadgsdSDFDSR#$");
		clickOn("#btnSignUp");
		verifyThat("* Login can only be composed by letters and numbers.", isVisible());
	}
	
	@Test
	public void test7_invalidPassword(){
		// Not null
		clickOn("#btnSignUp");
		verifyThat("* Security first, enter a password.", isVisible());

		// Not empty
		clickOn("#pwdPassword");
		write(" ");
		clickOn("#btnSignUp");
		verifyThat("* Security first, enter a password.", isVisible());

		// Shorter than 15
		doubleClickOn("#pwdPassword");
		write("1234567890123456");
		clickOn("#btnSignUp");
		verifyThat("* Password is too long.", isVisible());

		// Longer than 6
		doubleClickOn("#pwdPassword");
		write("12345");
		clickOn("#btnSignUp");
		verifyThat("* Password is too short.", isVisible());

		// Matches the pattern
		doubleClickOn("#pwdPassword");
		write("abcdfgast");
		clickOn("#btnSignUp");
		verifyThat("* Not secure enough! Try combining lowercase, uppercase and numbers.", isVisible());

		// Both password are equal
		doubleClickOn("#pwdPassword");
		write("Abcd*1234");
		clickOn("#pwdConfirmPassword");
		write("asdasdfasdf");
		clickOn("#btnSignUp");
		verifyThat("* Passwords must coincide.", isVisible());
	}
}
