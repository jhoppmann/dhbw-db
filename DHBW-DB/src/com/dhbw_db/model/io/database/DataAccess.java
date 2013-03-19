/**
 * DataAccess.java Created by jhoppmann on 14.02.2013
 */
package com.dhbw_db.model.io.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.dhbw_db.model.beans.EMail;
import com.dhbw_db.model.beans.Notebook;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.model.request.Request;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public interface DataAccess {

	/**
	 * The Table enumeration holds an entry for every table used in the project
	 * and its name as a string.
	 * 
	 * @author jhoppmann
	 * @version 0.1
	 * @since 0.1
	 */
	public enum Table {
		// TODO check case and singular / plural
		USER("User"),
		PROCESS("Process"),
		NOTEBOOK("Notebook"),
		OS("OS"),
		STATUS("Status"),
		EMAIL("EMail");

		private String text;

		public String toString() {
			return text;
		}

		private Table(String fullText) {
			this.text = fullText;
		}
	};

	/**
	 * Makes sure the database is reachable, the user has the necessary rights
	 * and all needed tables are present
	 * 
	 * @throws SQLException
	 * 
	 */
	public void initializeDatabase() throws SQLException;

	/**
	 * 
	 * Gets all lectures
	 * 
	 * @return A List of lectures
	 */
	public List<User> getLecturers();

	/**
	 * 
	 * Gets all Admins
	 * 
	 * @return A List of Admins
	 */
	public List<User> getAdmins();

	/**
	 * 
	 * Gets a user object for ID
	 * 
	 * @return the requested user object
	 */
	public User getUserForID(int id);

	/**
	 * 
	 * Inserts a request in the database
	 * 
	 * @param Request the request to be updated
	 * 
	 */
	public void insertRequest(Request request);

	/**
	 * 
	 * Updates a request in the database.
	 * 
	 * @param Request the request to be updated
	 * 
	 */
	public void updateRequest(Request request);

	/**
	 * Gets all requests from the database
	 * 
	 * 
	 * @return List of all Request
	 */
	public List<Request> getRequests();

	/**
	 * Gets a requests for id from the database
	 * 
	 * @return A Request object for the request corresponding to the ID
	 */
	public Request getRequestForID(int id);

	/**
	 * Gets a requests for hash from the database
	 * 
	 * @return A Request object for the request corresponding to the hash
	 */
	public Request getRequestForHash(String hash);

	/**
	 * Gets all notebooks from the database
	 * 
	 * 
	 * @return List of all notebooks
	 */
	public List<Notebook> getNotebooks();

	/**
	 * Inserts a notebook entry into the database
	 * 
	 * @param the notebook to be inserted
	 */
	public void insertNotebook(Notebook notebook);

	/**
	 * Updates a notebook object on the database
	 * 
	 * @param the notebook to be updated
	 */
	public void updateNotebook(Notebook notebook);

	/**
	 * Inserts an email entry into the database
	 * 
	 * @param the email to be inserted
	 */
	public void insertEMail(EMail eMail);

	/**
	 * Gets a map of all operating systems available for the notebooks from the
	 * database
	 * 
	 * @return hashmap of all operating systems
	 */
	public Map<Integer, String> getOSs();

	/**
	 * Gets a map of all status values from the database
	 * 
	 * 
	 * @return hashmap of all status values
	 */
	public Map<Integer, String> getStatusses();

	/**
	 * Tries to authenticate a user from the database. If the user exists and
	 * the given password is right, a user object will be returned
	 * 
	 * @param username The user's username
	 * @param password The user's password
	 * @return A user object if the user is found in conjunction with this
	 *         password, or <tt>null</tt> if not
	 */
	public User authenticate(String username, String password);

}
