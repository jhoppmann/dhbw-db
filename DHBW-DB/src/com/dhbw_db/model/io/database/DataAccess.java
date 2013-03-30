/**
 * DataAccess.java Created by jhoppmann on 14.02.2013
 */
package com.dhbw_db.model.io.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.dhbw_db.model.beans.EMail;
import com.dhbw_db.model.beans.Notebook;
import com.dhbw_db.model.beans.Notebook.NotebookCategory;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.model.request.Request;

/**
 * Handles all database communication.
 * 
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
		USER("User"),
		PROCESS("Process"),
		NOTEBOOK("Notebook"),
		OS("OS"),
		STATUS("Status"),
		EMAIL("EMail"),
		NOTEBOOKCOUNT("NotebookCount");

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
	 * @throws SQLException
	 * 
	 */
	public void insertRequest(Request request) throws SQLException;

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
	 * @param notebook The notebook to be inserted
	 */
	public void insertNotebook(Notebook notebook);

	/**
	 * Updates a notebook object on the database
	 * 
	 * @param notebook The notebook to be updated
	 */
	public void updateNotebook(Notebook notebook);

	/**
	 * Inserts an email entry into the database
	 * 
	 * @param eMail The email to be inserted
	 */
	public void insertEMail(EMail eMail);

	/**
	 * Gets a map of all operating systems available for the notebooks from the
	 * database
	 * 
	 * @return <tt>Map</tt> of all operating systems
	 */
	public Map<Integer, String> getOSs();

	/**
	 * Gets a map of all status values from the database
	 * 
	 * @return A <tt>Map</tt> of all status values
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

	/**
	 * Gets all requests for the passed approver ID
	 * 
	 * @param approverID The approverID for which the request are fetched
	 * @return The list of the requests a approver is assigned to
	 */
	public List<Request> getRequestsForApproverForID(int approverID);

	/**
	 * Gets all requests for the passed requester ID
	 * 
	 * @param requesterID The requesterID for which the requests are fetched
	 * @return The list of the requests a requester is assigned to
	 */
	public List<Request> getRequestsForRequesterForID(int requesterID);

	/**
	 * Gets the notebook count stored in a map
	 * 
	 * @return The map with the notebook count. Key value is short, medium or
	 *         long
	 */
	public Map<NotebookCategory, Integer> getNotebookCount();

	/**
	 * 
	 * Updates the notebook count
	 * 
	 * @param name The key value short, medium or long
	 * @param value The value by which the notebook count will be modified
	 */
	void updateNotebookCount(String name, int value);

	/**
	 * Gets the OS Name as a String for an OSID
	 * 
	 * @param OSID
	 * @return OS Name as a String
	 */
	public String getOSForID(int id);

	/**
	 * Returns a available and not defective notebook
	 * 
	 * @return Returns the first notebook which is available and not defective.
	 *         If no notebook is suitable returns null;
	 */
	public Notebook getANotebook();

	/**
	 * Returns the requested notebook
	 * 
	 * @param id
	 * @return A notebook
	 */
	public Notebook getNotebookForID(int id);

}
