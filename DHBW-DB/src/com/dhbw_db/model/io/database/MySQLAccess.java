/**
 * MySQLAccess.java Created by jhoppmann on 18.03.2013
 */
package com.dhbw_db.model.io.database;

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
 * @author Yannic Frank
 * @version 0.1
 * @since 0.1
 */
public class MySQLAccess implements DataAccess {

	private Properties connectionInfo;

	private Connection connection;

	private LoggingService log;

	public MySQLAccess() {
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

	@Override
	public void initializeDatabase() throws SQLException {
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
				+ "EndDate TIMESTAMP NOT NULL, "
				+ "UntilDate TIMESTAMP NOT NULL, " + "StatusID INT NOT NULL, "
				+ "OSID INT NOT NULL, " + "Description VARCHAR(240), "
				+ "PRIMARY KEY (ID), "

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

	@Override
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

	@Override
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

	@Override
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

	@Override
	public void insertRequest(Request request) {
		String sql = "INSERT INTO "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString()
				+ " (ID, RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, UntilDate, StatusID, OSID, Description) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?, ?)";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, request.getId());
			statement.setInt(2, request.getRequesterId());
			statement.setInt(3, request.getApproverId());
			statement.setInt(4, request.getNotebookId());
			statement.setString(5, request.getHash());
			statement.setTimestamp(6, new Timestamp(request.getCreated()
															.getTime()));
			statement.setTimestamp(7, new Timestamp(request.getStart()
															.getTime()));
			statement.setTimestamp(8, new Timestamp(request.getEnd()
															.getTime()));
			statement.setTimestamp(9, new Timestamp(request.getUntil()
															.getTime()));
			statement.setInt(10, request.getStatus()
										.getId());
			statement.setInt(11, request.getOs());

			statement.setString(12, request.getDescription());

			statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateRequest(Request request) {
		String sql = "UPDATE "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString()
				+ " SET RequesterID = ? , ApproverID = ? , NotebookID = ?, Hash = ?, StartDate = ? , EndDate = ? , UntilDate = ? , StatusID = ?, OSID = ?, Description = ? WHERE ID = ?";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, request.getRequesterId());
			statement.setInt(2, request.getApproverId());
			statement.setInt(3, request.getNotebookId());
			statement.setString(4, request.getHash());
			statement.setTimestamp(5, new Timestamp(request.getStart()
															.getTime()));
			statement.setTimestamp(6, new Timestamp(request.getEnd()
															.getTime()));
			statement.setTimestamp(7, new Timestamp(request.getUntil()
															.getTime()));
			statement.setInt(8, request.getStatus()
										.getId());
			statement.setInt(9, request.getOs());

			statement.setString(10, request.getDescription());

			statement.setInt(11, request.getId());

			statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Request> getRequests() {
		String sql = "SELECT ID, RequestID, RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, UntilDate, StatusID, OSID, Description FROM "
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

				Date untilDate = new Date(result.getTimestamp("UntilDate")
												.getTime());

				int statusID = result.getInt("StatusID");
				int osID = result.getInt("OSID");
				String description = result.getString("Description");

				Request request = new Request(	id,
												requesterID,
												approverID,
												notebookID,
												creationDate,
												startDate,
												endDate,
												untilDate,
												hash,
												statusID,
												osID,
												description);

				requestList.add(request);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return requestList;

	}

	@Override
	public Request getRequestForID(int id) {
		String sql = "SELECT RequestID, RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, UntilDate, StatusID, OSID, Description FROM "
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
				Date untilDate = new Date(result.getTimestamp("UntilDate")
												.getTime());
				int statusID = result.getInt("StatusID");
				int osID = result.getInt("OSID");
				String description = result.getString("Description");

				request = new Request(	id,
										requesterID,
										approverID,
										notebookID,
										creationDate,
										startDate,
										endDate,
										untilDate,
										hash,
										statusID,
										osID,
										description);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return request;

	}

	@Override
	public Request getRequestForHash(String hash) {
		String sql = "SELECT ID, RequestID, RequesterID, ApproverID, NotebookID, CreationDate, StartDate, EndDate, UntilDate, StatusID, OSID, Description FROM "
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
				Date untilDate = new Date(result.getTimestamp("UntilDate")
												.getTime());
				int statusID = result.getInt("StatusID");
				int osID = result.getInt("OSID");
				String description = result.getString("Description");

				request = new Request(	id,
										requesterID,
										approverID,
										notebookID,
										creationDate,
										startDate,
										endDate,
										untilDate,
										hash,
										statusID,
										osID,
										description);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return request;

	}

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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
