/**
 * Authenticator.java Created by jhoppmann on Mar 12, 2013
 */
package com.dhbw_db.model.authentication;

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
	 * @param user The user's username
	 * @param password The user's password
	 * @return A User object, or <tt>null</tt> if no user could be found
	 */
	public static User authenticate(String user, String password) {
		LoggingService log = LoggingService.getInstance();
		User u = null;
		log.log("Trying to authenticate user " + user, LogLevel.INFO);

		// TODO use database here

		// construct a test user here
		if (user.equals("standard") && password.equals("test")) {
			u = new User();
			u.setAdmin(false);
			u.setStudent(true);
			u.setLecturer(false);
			u.setID(-1);
			u.setMatrNr(1234567);
			u.setFirstName("Normaler");
			u.setLastName("Student");
			u.seteMail("ich@du.com");
		}

		if (user.equals("student") && password.equals("a")) {
			u = new User();
			u.setAdmin(false);
			u.setStudent(true);
			u.setLecturer(false);
			u.setID(-1);
			u.setMatrNr(1234567);
			u.setFirstName("Normaler");
			u.setLastName("Student");
			u.seteMail("ich@du.com");
		}

		if (user.equals("admin") && password.equals("a")) {
			u = new User();
			u.setAdmin(true);
			u.setStudent(false);
			u.setLecturer(false);
			u.setID(-1);
			u.setMatrNr(1234567);
			u.setFirstName("Normaler");
			u.setLastName("Student");
			u.seteMail("ich@du.com");
		}

		if (user.equals("lecturer") && password.equals("a")) {
			u = new User();
			u.setAdmin(false);
			u.setStudent(false);
			u.setLecturer(true);
			u.setID(-1);
			u.setMatrNr(1234567);
			u.setFirstName("Normaler");
			u.setLastName("Student");
			u.seteMail("ich@du.com");
		}

		if (u != null) {
			log.log("Authenticated user " + user, LogLevel.INFO);
		}
		return u;
	}
}
