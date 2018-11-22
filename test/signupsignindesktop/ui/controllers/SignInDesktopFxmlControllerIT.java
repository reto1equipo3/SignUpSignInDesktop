/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsignindesktop.ui.controllers;


import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;

import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;


import signupsignindesktop.applications.Application;

/**
 *
 * @author Igor
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInDesktopFxmlControllerIT extends ApplicationTest{
   
    @Override
    public void start(Stage stage) throws Exception{
        new Application().start(stage);
    }
    
    @Ignore
    @Test 
    public void test01_btnSignInIsEnabled(){
        clickOn("#txtLogin");
        write("Igor");
        clickOn("#pwdPassword");
        write("Abcd*123");
        verifyThat("#btnSignIn", isEnabled());
    }
    
    
    @Ignore
    @Test
    public void test02_btnSignInIsDisabled(){
       verifyThat("#txtLogin", hasText(""));
       verifyThat("#pwdPassword",hasText(""));
       verifyThat("#btnSignIn", isDisabled()); 
    }
    
    
   
    @Ignore
    @Test
    public void test03_btnSignInIsDisabled(){
        clickOn("#txtLogin");
        write("login1");
        verifyThat("#btnSignIn", isDisabled());
        eraseText(10);
        clickOn("#pwdPassword");
        write("password1");
        verifyThat("#btnSignIn", isDisabled());
        eraseText(10);
        verifyThat("#btnSignIn", isDisabled());
    }
    
   
    @Ignore
    @Test
    public void test04_openSignUpWindow(){
        clickOn("#hpSignUp");
        verifyThat("#gpSignUp", isVisible());
        clickOn("#hpSignIn");
        verifyThat("#gpSignIn", isVisible());
        
    }
  
    @Test
    public void test05_openUserViewWindow(){
        clickOn("#txtLogin");
        write("Igor");
        clickOn("#pwdPassword");
        write("Abcd*123");
        clickOn("#btnSignIn");
        verifyThat("#gpVistaUsuario", isVisible());
    }
    
    
    @Test
    public void test06_LoginError(){
        clickOn("#txtLogin");
        write("Igo");
        clickOn("#pwdPassword");
        write("Abcd*123");
        clickOn("#btnSignIn");
        verifyThat("#lblErrorLogin", isVisible());
    }
    
    
    
    @Test
    public void test07_PasswordError(){
        clickOn("#txtLogin");
        write("Igor");
        clickOn("#pwdPassword");
        write("Abcd*12");
        clickOn("#btnSignIn");
        verifyThat("#lblErrorPass", isVisible());
    }
    
    
    
    @Ignore
    @Test
    public void test08_LoginPatternError(){
        clickOn("#txtLogin");
        write("Igo?");
        clickOn("#pwdPassword");
        write("Abcd*123");
        clickOn("#btnSignIn");
        verifyThat("#lblErrorLogin", isVisible()); 
    }
    
    
   
    
    
    @Ignore
    @Test
    public void test09_PasswordToShort(){
        clickOn("#txtLogin");
        write("Igor");
        clickOn("#pwdPassword");
        write("Abc*1");
        clickOn("#btnSignIn");
        verifyThat("#lblErrorPass", isVisible());
        
    }
 
    @Ignore
    @Test
    public void test10_PasswordPatternError(){
        clickOn("#txtLogin");
        write("Igor");
        clickOn("#pwdPassword");
        write("abcd123");
        clickOn("#btnSignIn");
        verifyThat("#lblErrorPass", isVisible());  
    }
    
    @Ignore
    @Test
    public void test11_TextFieldLength(){
        clickOn("#txtLogin");
        write("Probando que no escriba mas de 20 caracteres");
        clickOn("#pwdPassword");
        write("Probando que no escriba mas de 15 caracteres");
       
    }
    
    @Ignore
    @Test
    public void test12_RememberLogin(){
        clickOn("#txtLogin");
        write("Igor");
        clickOn("#pwdPassword");
        write("Abcd*123");
        clickOn("#chkRememberLogin");
        clickOn("#btnSignIn");
        verifyThat("#gpVistaUsuario", isVisible());  
    }
  
    
   
   
    
    
   
}
