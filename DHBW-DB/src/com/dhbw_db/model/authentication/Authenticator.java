/**
 * Authenticator.java Created by jhoppmann on Mar 12, 2013
 */
package com.dhbw_db.model.authentication;

import com.dhbw_db.control.MainController;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;

/**
 * The <tt>Authenticator</tt> is used to authenticate a user with a given
 * password.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since
 */
public class Authenticator {

	/**
	 * This method searches for a user, checks his password and returns a User
	 * object of it.
	 * 
	 * @param user The user's username, in this case his matriculation number
	 * @param password The user's password
	 * @return A User object, or <tt>null</tt> if no user could be found
	 */
	public static User authenticate(String user, String password) {
		LoggingService log = LoggingService.getInstance();
		User u = null;
		log.log("Trying to authenticate user " + user, LogLevel.INFO);

		if (user.matches("\\d+")) {
			u = MainController.get()
								.getDataAccess()
								.authenticate(user, password);
		}
		if (u != null) {
			log.log("Authenticated user " + user, LogLevel.INFO);
		} else {
			MainController.get()
							.print("Benutzer konnte nicht authentifiziert werden");
			log.log("User " + user + " could not be authenticated.",
					LogLevel.ERROR);
		}
		return u;
	}
}
