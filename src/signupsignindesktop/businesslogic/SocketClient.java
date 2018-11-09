/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signupsignindesktop.businesslogic;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class connects the logic tier with the server socket
 *
 * @author Gaizka, Imanol
 */
public class SocketClient {

	/**
	 * @param PORT int: The port the app will use to connect to the database
	 * @param IP String: The IP of the database
	 */
	private final int PORT = 6000;
	private final String IP = "127.0.0.1";
	private static final Logger LOGGER = Logger.getLogger("businessLogic");

	/**
	 * The method that initiates the client side of the socket
	 *
	 * @param msg
	 * @return answer usermessage.Message: returns the message to the class
	 * LogicImpementation
	 */
	public message.Message init(message.Message msg) {

		Socket client = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		message.Message answer = null;
		try {
			client = new Socket(IP, PORT);
			LOGGER.info("Conectado con servidor");
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
			oos.writeObject(msg);
			answer = (message.Message) ois.readObject();

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "SocketClient::init: IOException", e.getMessage());
		} catch (ClassNotFoundException ex) {
			LOGGER.log(Level.SEVERE, "SocketClient::init: ClassNotFoundException", ex.getMessage());
		} finally {
			try {
				if (client != null) {
					client.close();
				}
				if (ois != null) {
					ois.close();
				}
				if (oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "SocketClient::init: IOException", e.getMessage());
			}
		}
		return answer;
	}

	/*public static void main(String[] args){
        SocketCliente c = new SocketCliente();
        usermessage.UserBean user = new usermessage.UserBean();
        user.setLogin("Antonio69");
        user.setPassword("antonio");
        usermessage.Message msg = new usermessage.Message();
        msg.setMessage(usermessage.MessageType.SIGN_IN);
        msg.setData(user);
        c.init(msg);
    }*/
}
