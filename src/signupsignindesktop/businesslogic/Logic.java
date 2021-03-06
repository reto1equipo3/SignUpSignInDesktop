
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsignindesktop.businesslogic;

import exceptions.BadPasswordException;
import exceptions.DatabaseException;
import exceptions.EmailNotUniqueException;
import exceptions.LoginExistingException;
import exceptions.LoginNotExistingException;
import model.UserBean;





/**
 * Logic Interface encapsulating methods for users management. 
 * @author Igor
 */
public interface Logic {
    /**
     * This method adds a new created UserBean.
     * @param user The UserBean object to be added.
     * @return Return users data
     * @throws LoginNotExistingException If login does not exist.
     * @throws BadPasswordException If password is wrong.
     * @throws DatabaseException Shows this exception if you can not connect to the database
     */
    public UserBean signIn(UserBean user) throws LoginNotExistingException, BadPasswordException, DatabaseException;
    
    /**
     * This method initializes the session of a user already registered 
     * @param user .The UserBean object to be initialized
     * @throws LoginExistingException If login already exists.
     * @throws EmailNotUniqueException If email is already used.
     * @throws DatabaseException Shows this exception if you can not connect to the database
     */
    public void signUp(UserBean user) throws LoginExistingException, EmailNotUniqueException, DatabaseException;
    
   
    /**
     * This method ends the user's session
     * @param user. The UserBean object to be ended
     * @throws DatabaseException Shows this exception if you can not connect to the database
    */
    public void logOut(UserBean user) throws DatabaseException;

}