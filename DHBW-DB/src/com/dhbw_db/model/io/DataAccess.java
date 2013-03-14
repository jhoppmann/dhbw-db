/**
 * DataAccess.java Created by jhoppmann on 14.02.2013
 */
package com.dhbw_db.model.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;

import com.dhbw_db.model.beans.EMail;
import com.dhbw_db.model.beans.Notebook;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;
import com.dhbw_db.model.request.Request;
import com.dhbw_db.model.settings.Settings;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class DataAccess {

	private Properties connectionInfo;

	private Connection connection;

	private LoggingService log;

	/**
	 * The Table enumeration holds an entry for every table used in the project
	 * and its name as a string.
	 * 
	 * @author jhoppmann
	 * @version 0.1
	 * @since 0.1
	 */
	private enum Table {
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

	public DataAccess() {
		connectionInfo = Settings.getInstance()
									.getAll();
		log = LoggingService.getInstance();
	}

	/**
	 * Creates all tables used by the program.
	 * 
	 * @throws SQLException
	 */
	private void createTables() throws SQLException {
		// TODO Here goes the code to actually test for table existence and
		// table creation while using the connection field

		if (!tableExists(Table.USER.toString())) {
			this.setupUserTable();
		}

		if (!tableExists(Table.NOTEBOOK.toString())) {
			this.setupNotebookTable();
		}

		if (!tableExists(Table.OS.toString())) {
			this.setupOSTable();
		}

		if (!tableExists(Table.STATUS.toString())) {
			this.setupStatusTable();
			this.insertStatusData();
		}

		if (!tableExists(Table.EMAIL.toString())) {
			this.setupEMailTable();
		}

		if (!tableExists(Table.PROCESS.toString())) {
			this.setupProcessTable();
		}

		log.log("createTables method executed", LogLevel.INFO);

		// testSomeMethods();
	}

	/**
	 * Makes sure the database is reachable, the user has the necessary rights
	 * and all needed tables are present
	 * 
	 * @throws SQLException
	 * 
	 */
	public void intializeDatabase() throws SQLException {
		connection = connect();
		createTables();
		// destroy reference after we're done
		connection = null;
	}

	/**
	 * Method that connects to the database and returns the <tt>Connection</tt>
	 * object
	 * 
	 * @return A Connection to the database
	 * @throws SQLException Throws this exception if something went wrong
	 *             somewhere.
	 */
	private Connection connect() throws SQLException {
		Connection conn = null;

		Properties connectionProps = new Properties();
		connectionProps.put("user", connectionInfo.get("database.user"));
		connectionProps.put("password", connectionInfo.get("database.password"));

		// construct the string containing address and database
		StringBuilder connectionString = new StringBuilder();
		connectionString.append("jdbc:mysql://")
						.append(connectionInfo.get("database.host"))
						.append(":")
						.append(connectionInfo.get("database.port"))
						.append("/")
						.append(connectionInfo.get("database.database"));

		// TODO Check this
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		conn = DriverManager.getConnection(	connectionString.toString(),
											connectionProps);

		return conn;
	}

	/**
	 * Sets up the table for the users
	 */

	/*
	 * private void setupUserTable() { // this is a sample method that
	 * demonstrates how table creation methods // should look like
	 * 
	 * // TODO Complete method String creationString = "create table " +
	 * connectionInfo.get("database.database") + "." + Table.USER.toString() +
	 * " (uid INT (11) NOT NULL AUTO_INCREMENT, " +
	 * "username VARCHAR(25) DEFAULT NULL, " + "PRIMARY KEY (uid))"; Statement
	 * statement = null; try { statement = connection.createStatement();
	 * statement.executeUpdate(creationString);
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } }
	 */

	/**
	 * Sets up the EMail Table
	 */
	private void setupEMailTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.EMAIL.toString() + " (ID INT NOT NULL AUTO_INCREMENT, "
				+ "ReceiverMail VARCHAR(45) NULL, "
				+ "SenderMail VARCHAR(45) NULL, " + "Header VARCHAR(45) NULL, "
				+ "Body VARCHAR(45) NULL, " + "Date TIMESTAMP NULL, "
				+ "PRIMARY KEY (ID))";

		System.out.println("Table is being created: " + creationString);

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(creationString);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the Notebook Table
	 */
	private void setupNotebookTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.NOTEBOOK.toString() + "(ID INT NOT NULL, "
				+ "Name VARCHAR(45) NOT NULL, " + "IsDefective BIT NOT NULL, "
				+ "IsAvailable BIT NOT NULL, " + "PRIMARY KEY (ID))";

		System.out.println("Table is being created: " + creationString);

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(creationString);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the OS Table
	 */
	private void setupOSTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.OS.toString() + "(ID INT NOT NULL AUTO_INCREMENT, "
				+ "Name VARCHAR(45) NOT NULL, " + "PRIMARY KEY (ID))";

		System.out.println("Table is being created: " + creationString);

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(creationString);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the Process Table
	 */
	private void setupProcessTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.PROCESS.toString()
				+ " (ID INT NOT NULL AUTO_INCREMENT, "
				+ "RequestID VARCHAR(45) NOT NULL, "
				+ "RequesterID INT NOT NULL, " + "ApproverID INT NOT NULL, "
				+ "NotebookID INT NOT NULL, " + "Hash VARCHAR(32) NOT NULL, "
				+ "CreationDate TIMESTAMP NOT NULL, "
				+ "StartDate TIMESTAMP NOT NULL, "
				+ "EndDate TIMESTAMP NOT NULL, " + "StatusID INT NOT NULL, "
				+ "OSID INT NOT NULL, " + "PRIMARY KEY (ID), "

				+ "CONSTRAINT UserID1 " + "FOREIGN KEY (RequesterID) "
				+ "REFERENCES " + connectionInfo.get("database.database") + "."
				+ Table.USER.toString() + " (ID) " + "ON DELETE NO ACTION "
				+ "ON UPDATE NO ACTION, "

				+ "CONSTRAINT UserID2 " + "FOREIGN KEY (ApproverID) "
				+ "REFERENCES " + connectionInfo.get("database.database") + "."
				+ Table.USER.toString() + " (ID) " + "ON DELETE NO ACTION "
				+ "ON UPDATE NO ACTION, "

				+ "CONSTRAINT NotebookID " + "FOREIGN KEY (NotebookID) "
				+ "REFERENCES " + connectionInfo.get("database.database") + "."
				+ Table.NOTEBOOK.toString() + " (ID) " + "ON DELETE NO ACTION "
				+ "ON UPDATE NO ACTION, "

				+ "CONSTRAINT OSID " + "FOREIGN KEY (OSID) " + "REFERENCES "
				+ connectionInfo.get("database.database") + "."
				+ Table.OS.toString() + " (ID) " + "ON DELETE NO ACTION "
				+ "ON UPDATE NO ACTION, "

				+ "CONSTRAINT StatusID " + "FOREIGN KEY (StatusID) "
				+ "REFERENCES " + connectionInfo.get("database.database") + "."
				+ Table.STATUS.toString() + " (ID) " + "ON DELETE NO ACTION "
				+ "ON UPDATE NO ACTION) ";

		System.out.println("Table is being created: " + creationString);

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(creationString);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the STATUS Table
	 */
	private void setupStatusTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.STATUS.toString() + "(ID INT NOT NULL, "
				+ "Name VARCHAR(45) NOT NULL, " + "PRIMARY KEY (ID))";

		System.out.println("Table is being created: " + creationString);

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(creationString);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the USER Table
	 */
	private void setupUserTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.USER.toString() + "(ID INT NOT NULL AUTO_INCREMENT, "
				+ "MatrNr INT NOT NULL, " + "Vorname VARCHAR(45) NOT NULL, "
				+ "Name VARCHAR(45) NOT NULL, "
				+ "EMail VARCHAR(45) NOT NULL, " + "IsStudent BIT NOT NULL, "
				+ "IsAdmin BIT NOT NULL, " + "IsLecturer BIT NOT NULL, "
				+ "Password VARCHAR(45) NOT NULL, " + "PRIMARY KEY (ID))";

		System.out.println("Table is being created: " + creationString);

		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(creationString);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts Status data
	 */
	private void insertStatusData() {
		int i = 0;
		String[] status = { "'OPEN'", "'APPROVED'", "'RETRACTED'",
				"'REJECTED'", "'OVERDUE'", "'COMPLETED'", "'ERROR'",
				"'CANCELED'" };

		for (String s : status) {
			i++;

			String creationString = "insert into "
					+ connectionInfo.get("database.database") + "."
					+ Table.STATUS.toString() + " (ID, Name) VALUES (" + i
					+ "," + s + ")";

			System.out.println("Inserted: " + creationString);

			Statement statement = null;
			try {
				statement = connection.createStatement();
				statement.executeUpdate(creationString);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks whether a table with <tt>tableName</tt> exists on the database.
	 * 
	 * @param tableName The table's name
	 * @return <tt>true</tt> if the table exists, <tt>false</tt> otherwise.
	 * @throws SQLException if an error occurs
	 */
	private boolean tableExists(String tableName) throws SQLException {

		ResultSet tables = connection.getMetaData()
										.getTables(null, null, tableName, null);
		boolean exists = tables.next();
		return exists;
	}

	/*
	 * Methods which fetch data from the Database
	 */

	/**
	 * Gets a request by its id from the database
	 * 
	 * @param id The id of the request
	 * @return Request the corresponding request
	 * @deprecated This method uses the old constructor so the request may be
	 *             not complete
	 */

	public Request getRequestByIDold(int id) {
		String selectString = "SELECT RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, StatusID, OSID FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString() + " WHERE ID = " + id;

		Request request = null;
		Statement statement = null;

		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(selectString);

			if (rs.next()) {

				int rID = rs.getInt("RequesterID");
				int aID = rs.getInt("ApproverID");
				int nID = rs.getInt("NotebookID");
				Date startDate = new Date(rs.getTimestamp("StartDate")
											.getTime());
				Date endDate = new Date(rs.getTimestamp("EndDate")
											.getTime());

				request = new Request(	rID,
										aID,
										nID,
										endDate,
										startDate);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return request;

	}

	/**
	 * Gets a request by its hash from the database
	 * 
	 * @param id The id of the request
	 * @return Request the corresponding request
	 * @deprecated This method uses the old constructor so the request may be
	 *             not complete
	 */

	public Request getRequestByHashold(String hash) {
		String selectString = "SELECT ID, RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, StatusID, OSID FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString() + " WHERE Hash = " + hash;

		Request request = null;
		Statement statement = null;

		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(selectString);

			if (rs.next()) {

				int ID = rs.getInt("ID");
				int rID = rs.getInt("RequesterID");
				int aID = rs.getInt("ApproverID");
				int nID = rs.getInt("NotebookID");
				Date startDate = new Date(rs.getTimestamp("StartDate")
											.getTime());
				Date endDate = new Date(rs.getTimestamp("EndDate")
											.getTime());

				request = new Request(	rID,
										aID,
										nID,
										endDate,
										startDate);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return request;

	}

	/**
	 * 
	 * Updates a request in the database
	 * 
	 * Doesn't use prepared Statements
	 * 
	 * @param Request the request to be updated
	 */

	public void updateRequest(Request request) {
		String updateString = "UPDATE "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.PROCESS.toString() + " SET RequesterID = "
				+ request.getRequesterId() + " , ApproverID = "
				+ request.getApproverId() + " , NotebookID = "
				+ request.getNotebookId() + " , Hash = " + request.getHash()
				+ " , StartDate = " + new Timestamp(request.getStart()
															.getTime())
				+ " , EndDate = " + new Timestamp(request.getEnd()
															.getTime())
				+ " , StatusID = " + request.getStatus()
											.getId() + " WHERE ID = "
				+ request.getId();

		Statement statement = null;

		try {
			statement = connection.createStatement();
			statement.executeQuery(updateString);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Inserts a request in the database
	 * 
	 * Doesn't use prepared Statements
	 * 
	 * @param Request the request to be updated
	 */

	public void insertRequest(Request request) {
		String insertString = "INSERT INTO "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString()
				+ " (RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, StatusID) VALUES ("
				+ request.getRequesterId() + " , " + request.getApproverId()
				+ " , " + request.getNotebookId() + " , " + request.getHash()
				+ " , " + new Timestamp(request.getCreated()
												.getTime()) + " , "
				+ new Timestamp(request.getStart()
										.getTime()) + " , "
				+ new Timestamp(request.getEnd()
										.getTime()) + " , "
				+ request.getStatus()
							.getId() + ")";

		Statement statement = null;

		try {
			statement = connection.createStatement();
			statement.executeQuery(insertString);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Gets all lectures
	 * 
	 * @return A collection of lectures
	 */

	public List<User> getLectures() {

		String sql = "SELECT ID, MatrNr, Vorname, Name, EMail, isStudent, isAdmin, isLecturer FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.USER.toString() + " WHERE IsLecturer = (1)";

		List<User> userList = null;

		try {

			userList = new ArrayList<User>();

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int id = result.getInt("ID");
				int matrNr = result.getInt("MatrNr");
				String vorname = result.getString("Vorname");
				String name = result.getString("Name");
				String eMail = result.getString("EMail");
				Boolean isStudent = result.getBoolean("isStudent");
				Boolean isAdmin = result.getBoolean("isAdmin");
				Boolean isLecturer = result.getBoolean("isLecturer");

				User user = new User();
				user.setID(id);
				user.setMatrNr(matrNr);
				user.setFirstName(vorname);
				user.setLastName(name);
				user.seteMail(eMail);
				user.setStudent(isStudent);
				user.setAdmin(isAdmin);
				user.setLecturer(isLecturer);

				userList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userList;

	}

	/**
	 * 
	 * Gets all Admins
	 * 
	 * @return A collection of Admins
	 */

	public List<User> getAdmins() {

		String sql = "SELECT ID, MatrNr, Vorname, Name, EMail, isStudent, isAdmin, isLecturer FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.USER.toString() + " WHERE IsAdmin = (1)";

		List<User> userList = null;

		try {

			userList = new ArrayList<User>();

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int id = result.getInt("ID");
				int matrNr = result.getInt("MatrNr");
				String vorname = result.getString("Vorname");
				String name = result.getString("Name");
				String eMail = result.getString("EMail");
				Boolean isStudent = result.getBoolean("isStudent");
				Boolean isAdmin = result.getBoolean("isAdmin");
				Boolean isLecturer = result.getBoolean("isLecturer");

				User user = new User();
				user.setID(id);
				user.setMatrNr(matrNr);
				user.setFirstName(vorname);
				user.setLastName(name);
				user.seteMail(eMail);
				user.setStudent(isStudent);
				user.setAdmin(isAdmin);
				user.setLecturer(isLecturer);

				userList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userList;

	}

	/**
	 * 
	 * Gets a user object for ID
	 * 
	 * @return the requestet user object
	 */

	public User getUserForID(int id) {

		String sql = "SELECT ID, MatrNr, Vorname, Name, EMail, isStudent, isAdmin, isLecturer FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.USER.toString() + " WHERE ID = ?";

		User user = null;

		try {
			user = new User();

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				int tempid = result.getInt("ID");
				int matrNr = result.getInt("MatrNr");
				String vorname = result.getString("Vorname");
				String name = result.getString("Name");
				String eMail = result.getString("EMail");
				Boolean isStudent = result.getBoolean("isStudent");
				Boolean isAdmin = result.getBoolean("isAdmin");
				Boolean isLecturer = result.getBoolean("isLecturer");

				user.setID(tempid);
				user.setMatrNr(matrNr);
				user.setFirstName(vorname);
				user.setLastName(name);
				user.seteMail(eMail);
				user.setStudent(isStudent);
				user.setAdmin(isAdmin);
				user.setLecturer(isLecturer);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;

	}

	/**
	 * Gets all requests from the database
	 * 
	 * 
	 * @return List of all Request
	 */

	public List<Request> getRequests() {
		String sql = "SELECT ID, RequestID, RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, StatusID, OSID FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString();

		List<Request> requestList = null;

		try {

			requestList = new ArrayList<Request>();

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int id = result.getInt("ID");

				int requesterID = result.getInt("RequesterID");
				int approverID = result.getInt("ApproverID");
				int notebookID = result.getInt("NotebookID");
				String hash = result.getString("Hash");

				Date creationDate = new Date(result.getTimestamp("CreationDate")
													.getTime());
				Date startDate = new Date(result.getTimestamp("StartDate")
												.getTime());
				Date endDate = new Date(result.getTimestamp("EndDate")
												.getTime());
				int statusID = result.getInt("StatusID");
				int osID = result.getInt("OSID");

				Request request = new Request(	id,
												requesterID,
												approverID,
												notebookID,
												creationDate,
												startDate,
												endDate,
												null,
												hash,
												statusID,
												osID);

				requestList.add(request);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return requestList;

	}

	/**
	 * Gets a requests for id from the database
	 * 
	 * 
	 * @return really requested Request
	 */

	public Request getRequestForID(int id) {
		String sql = "SELECT RequestID, RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, StatusID, OSID FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString() + " WHERE ID = ?";

		Request request = null;

		try {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();

			if (result.next()) {

				int requesterID = result.getInt("RequesterID");
				int approverID = result.getInt("ApproverID");
				int notebookID = result.getInt("NotebookID");
				String hash = result.getString("Hash");

				Date creationDate = new Date(result.getTimestamp("CreationDate")
													.getTime());
				Date startDate = new Date(result.getTimestamp("StartDate")
												.getTime());
				Date endDate = new Date(result.getTimestamp("EndDate")
												.getTime());
				int statusID = result.getInt("StatusID");
				int osID = result.getInt("OSID");

				request = new Request(	id,
										requesterID,
										approverID,
										notebookID,
										creationDate,
										startDate,
										endDate,
										null,
										hash,
										statusID,
										osID);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return request;

	}

	/**
	 * Gets a requests for hash from the database
	 * 
	 * 
	 * @return really requested Request
	 */

	public Request getRequestForHash(String hash) {
		String sql = "SELECT ID, RequestID, RequesterID, ApproverID, NotebookID, CreationDate, StartDate, EndDate, StatusID, OSID FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString() + " WHERE Hash = ?";

		Request request = null;

		try {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, hash);

			ResultSet result = statement.executeQuery();

			if (result.next()) {

				int id = result.getInt("ID");
				int requesterID = result.getInt("RequesterID");
				int approverID = result.getInt("ApproverID");
				int notebookID = result.getInt("NotebookID");
				// String hash = result.getString("Hash");

				Date creationDate = new Date(result.getTimestamp("CreationDate")
													.getTime());
				Date startDate = new Date(result.getTimestamp("StartDate")
												.getTime());
				Date endDate = new Date(result.getTimestamp("EndDate")
												.getTime());
				int statusID = result.getInt("StatusID");
				int osID = result.getInt("OSID");

				request = new Request(	id,
										requesterID,
										approverID,
										notebookID,
										creationDate,
										startDate,
										endDate,
										null,
										hash,
										statusID,
										osID);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return request;

	}

	/**
	 * Gets all notebooks from the database
	 * 
	 * 
	 * @return List of all notebooks
	 */

	public List<Notebook> getNotebooks() {
		String sql = "SELECT ID, Name, IsDefective, IsAvailable FROM "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.NOTEBOOK.toString();

		List<Notebook> notebookList = null;

		try {

			notebookList = new ArrayList<Notebook>();

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int id = result.getInt("ID");

				String name = result.getString("Name");

				Boolean isDefective = result.getBoolean("IsDefective");
				Boolean isAvailable = result.getBoolean("IsAvailable");

				Notebook notebook = new Notebook();

				notebook.setiD(id);
				notebook.setName(name);
				notebook.setDefective(isDefective);
				notebook.setAvailable(isAvailable);

				notebookList.add(notebook);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notebookList;

	}

	/**
	 * Inserts a notebook object into the mighty database
	 * 
	 * @param the notebook to be inserted
	 */

	public void insertNotebook(Notebook notebook) {
		String sql = "INSERT INTO "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.NOTEBOOK.toString()
				+ " (ID, Name, IsDefective, IsAvailable) VALUES (?,?,?,?)";

		try {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, notebook.getiD());
			statement.setString(2, notebook.getName());
			statement.setBoolean(3, notebook.isDefective());
			statement.setBoolean(4, notebook.isAvailable());

			statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Updates a notebook object on the database
	 * 
	 * 
	 * @param the notebook to be updated
	 */

	public void updateNotebook(Notebook notebook) {
		String sql = "UPDATE "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.NOTEBOOK.toString()
				+ " SET Name = ?, IsDefective = ?, IsAvailable = ? WHERE ID = ?";

		try {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, notebook.getName());
			statement.setBoolean(2, notebook.isDefective());
			statement.setBoolean(3, notebook.isAvailable());
			statement.setInt(4, notebook.getiD());

			statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Inserts a email object into the database
	 * 
	 * @param the email to be inserted
	 */

	public void insertEMail(EMail eMail) {
		String sql = "INSERT INTO "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.EMAIL.toString()
				+ " (ID, ReceiverMail, SenderMail, Header, Body, Date) VALUES (?,?,?,?,?,?)";

		try {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, eMail.getiD());
			statement.setString(2, eMail.getReceiverMail());
			statement.setString(3, eMail.getSenderMail());
			statement.setString(4, eMail.getHeader());
			statement.setString(5, eMail.getBody());
			statement.setTimestamp(6, new Timestamp(eMail.getDate()
															.getTime()));

			statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets a map of all operating systems available for the notebooks from the
	 * database
	 * 
	 * 
	 * @return hashmap of all operating systems
	 */

	public HashMap<Integer, String> getOSs() {
		String sql = "SELECT ID, Name FROM "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.OS.toString();

		HashMap<Integer, String> osMap = null;

		try {

			osMap = new HashMap<Integer, String>();

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				int id = result.getInt("ID");
				String name = result.getString("Name");

				osMap.put(id, name);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return osMap;

	}

	/**
	 * Gets a map of all status values from the database
	 * 
	 * 
	 * @return hashmap of all status values
	 */

	public HashMap<Integer, String> getStatusses() {
		String sql = "SELECT ID, Name FROM "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.STATUS.toString();

		HashMap<Integer, String> statusMap = null;

		try {

			statusMap = new HashMap<Integer, String>();

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				int id = result.getInt("ID");
				String name = result.getString("Name");

				statusMap.put(id, name);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return statusMap;

	}

	public User authenticate(String username, String password) {
		String sql = "SELECT ID, MatrNr, Vorname, Name, EMail, isStudent, isAdmin, isLecturer, Password FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.USER.toString() + " WHERE Name = ?";

		User user = null;
		String storedPassword = null;

		try {

			user = new User();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				int id = result.getInt("ID");
				int matrNr = result.getInt("MatrNr");
				String vorname = result.getString("Vorname");
				String name = result.getString("Name");
				String eMail = result.getString("EMail");
				Boolean isStudent = result.getBoolean("isStudent");
				Boolean isAdmin = result.getBoolean("isAdmin");
				Boolean isLecturer = result.getBoolean("isLecturer");

				storedPassword = result.getString("Password");
				// Just for checking whether the as parameter passed password is
				// valid.

				user.setID(id);
				user.setMatrNr(matrNr);
				user.setFirstName(vorname);
				user.setLastName(name);
				user.seteMail(eMail);
				user.setStudent(isStudent);
				user.setAdmin(isAdmin);
				user.setLecturer(isLecturer);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String hashPassword = DigestUtils.md5Hex(password); // makes a hash out
															// of the passed
															// password

		if (hashPassword.equals(storedPassword)) {
			return user;
		}

		else {
			return null;
		}
	}

	public void testSomeMethods() {

		/*
		 * Request requestOne = getRequestByID(1);
		 * System.out.println("Request1 before update: " + requestOne.getId() +
		 * " " + requestOne.getOs());
		 * 
		 * requestOne.setOs(5); updateRequest(requestOne); requestOne =
		 * getRequestByID(1); System.out.println("Request1 after update: " +
		 * requestOne.getId() + " " + requestOne.getOs());
		 */
		System.out.println(this.getAdmins()
								.get(0));

		System.out.println(this.getLectures()
								.get(0));
		System.out.println(this.getLectures()
								.get(1));

	}

}
