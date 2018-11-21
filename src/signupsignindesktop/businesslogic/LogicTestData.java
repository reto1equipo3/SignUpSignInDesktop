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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UserBean;


/**
 * This class implements {@link Logic} bussines logic interface generating fake
 * data for testing purposes.
 *
 * @author Leticia
 */
public class LogicTestData implements Logic {

    private static final Logger LOGGER = Logger.getLogger("signupsignindesktop.businesslogic");
    private ArrayList<UserBean> users; //List for storing users data

    /**
     * Create a 10 userBean fake data object, and builds 10 instances of {@link UserBean}
     * object into ArrayList.
     */
    public LogicTestData() {

        LOGGER.info("Building fake users data for testing UI.");
        users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            UserBean user = new UserBean();
            user.setLogin("login" + i);
            user.setPassword("password" + i);
            user.setEmail("email" + i);
            users.add(user);

        }
    }

    /**
     * Check if a user's login with password already exists, throwing an
     * exception if that's the case.
     *
     * @param user The userBean object to be Sign In.
     * @return user to be Sign in.
     * @throws LoginNotExistingException Login does not exist
     * @throws BadPasswordException Password is wrong
     * @throws DatabaseException Something is wrong
     */
    @Override
    public UserBean signIn(UserBean user) throws LoginNotExistingException, BadPasswordException, DatabaseException {

        LOGGER.info("Validating Login existence.");

        if (users.stream().filter(u -> ((UserBean) u).getLogin().equals(user.getLogin())).count() == 0) {

            LOGGER.info("The aren't users with that login");
            throw new LoginNotExistingException("Not exist this login.");
        } else {
            LOGGER.info("Login already exist");

            for (UserBean u : users) {
                if (u.getLogin().equals(user.getLogin())) {
                    if (u.getPassword().equals(user.getPassword())) {
                        LOGGER.info("Passwords are equal :)");
                        user.setEmail(u.getEmail());
                        user.setFullName(u.getFullName());
//                        user.setphoto(u.getPhoto());
                    } else {
                        LOGGER.info("Bad password :(");
                        throw new BadPasswordException("Wrong password");
                    }
                }
            }

        }

        return user;
    }

    /**
     * Check if a user's login and email already exists, throwing an Exception
     * if that's the case.
     *
     * @param user The userBean to be Sign Up.
     * @throws LoginExistingException Login already exists
     * @throws EmailNotUniqueException Email already exists
     */
    @Override
    public void signUp(UserBean user) throws LoginExistingException, EmailNotUniqueException {

        if (users.stream().filter(u -> ((UserBean) u).getLogin().equals(user.getLogin())).count() == 0) {

            LOGGER.info("The aren't users with that login");
        } else {

            LOGGER.severe("Login already exists");
            throw new LoginExistingException();
        }

        if (users.stream().filter(u -> u.getEmail().equals(user.getEmail())).count() == 0) {
            LOGGER.info("The aren't users with that email");
        } else {
            LOGGER.severe("Email already exists");
            throw new EmailNotUniqueException();
        }
        users.add(user);
    }

    /**
     * Log out
     *
     * @param user The userBean object to be log Out.
     */
    @Override
    public void logOut(UserBean user) {

    }

}
