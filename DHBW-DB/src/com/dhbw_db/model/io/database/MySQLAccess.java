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
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;

import com.dhbw_db.model.beans.EMail;
import com.dhbw_db.model.beans.Notebook;
import com.dhbw_db.model.beans.Notebook.NotebookCategory;
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
			this.insertOSData();
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

		if (!tableExists(Table.NOTEBOOKCOUNT.toString())) {
			this.setupNotebookCountTable();
			this.insertNotebookNumbers();
		}

		log.log("createTables method executed", LogLevel.INFO);

		// testSomeMethods();
	}

	@Override
	public void initializeDatabase() throws SQLException {
		connect();
		createTables();
		// destroy reference after we're done
		disconnect();
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
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		}

		conn = DriverManager.getConnection(	connectionString.toString(),
											connectionProps);

		this.connection = conn;
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
				+ "Body VARCHAR(45) NULL, " + "Date TIMESTAMP, "
				+ "PRIMARY KEY (ID))";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(creationString);
			log.log("Created table " + Table.EMAIL, LogLevel.INFO);
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * Sets up the Notebook Table
	 */
	private void setupNotebookTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.NOTEBOOK.toString()
				+ "(ID INT NOT NULL AUTO_INCREMENT, "
				+ "Name VARCHAR(45) NOT NULL, " + "IsDefective BIT NOT NULL, "
				+ "IsAvailable BIT NOT NULL, " + "PRIMARY KEY (ID))";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(creationString);
			log.log("Created table " + Table.NOTEBOOK, LogLevel.INFO);
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
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

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(creationString);
			log.log("Created table " + Table.OS, LogLevel.INFO);
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * Sets up the availableNotebooks Table
	 */
	private void setupNotebookCountTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.NOTEBOOKCOUNT.toString()
				+ "(ID INT NOT NULL AUTO_INCREMENT, "
				+ "Name VARCHAR(45) NOT NULL, Count INT, "
				+ "PRIMARY KEY (ID))";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(creationString);
			log.log("Created table " + Table.NOTEBOOKCOUNT, LogLevel.INFO);
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * Sets up the Process Table
	 */
	private void setupProcessTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.PROCESS.toString()
				+ " (ID INT NOT NULL AUTO_INCREMENT, " + "RequesterID INT, "
				+ "ApproverID INT, " + "NotebookID INT, "
				+ "Hash VARCHAR(32), " + "CreationDate TIMESTAMP NULL, "
				+ "StartDate TIMESTAMP NULL, " + "EndDate TIMESTAMP NULL, "
				+ "UntilDate TIMESTAMP NULL, " + "StatusID INT, "
				+ "OSID INT, "
				+ "Description VARCHAR(240), Category VARCHAR(32), "
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

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(creationString);
			log.log("Created table " + Table.PROCESS, LogLevel.INFO);
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * Sets up the STATUS Table
	 */
	private void setupStatusTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.STATUS.toString() + "(ID INT NOT NULL AUTO_INCREMENT, "
				+ "Name VARCHAR(45) NOT NULL, " + "PRIMARY KEY (ID))";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(creationString);
			log.log("Created table " + Table.STATUS, LogLevel.INFO);
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * Sets up the USER Table
	 */
	private void setupUserTable() {
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.USER.toString() + "(ID INT NOT NULL AUTO_INCREMENT, "
				+ "MatrNo INT NOT NULL, " + "Firstname VARCHAR(45) NOT NULL, "
				+ "Name VARCHAR(45) NOT NULL, "
				+ "EMail VARCHAR(45) NOT NULL, "
				+ "IsStudent TINYINT NOT NULL, " + "IsAdmin TINYINT NOT NULL, "
				+ "IsLecturer TINYINT NOT NULL, "
				+ "Password VARCHAR(32) NOT NULL, " + "PRIMARY KEY (ID))";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(creationString);
			log.log("Created table " + Table.USER, LogLevel.INFO);
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * Inserts Status data
	 */
	private void insertStatusData() {

		String[] status = { "'OPEN'", "'APPROVED'", "'RETRACTED'",
				"'REJECTED'", "'OVERDUE'", "'COMPLETED'", "'ERROR'",
				"'CANCELED'" };

		for (String s : status) {

			String insertionString = "insert into "
					+ connectionInfo.get("database.database") + "."
					+ Table.STATUS.toString() + " (Name) VALUES (" + s + ")";

			try {
				Statement statement = connection.createStatement();
				statement.executeUpdate(insertionString);

			} catch (SQLException e) {
				e.printStackTrace();
				log.log(e.getMessage(), LogLevel.ERROR);
			}
			log.log("Inserted " + s + " into " + Table.STATUS.toString(),
					LogLevel.INFO);
		}
	}

	/**
	 * Inserts OS values
	 */
	private void insertOSData() {
		// define the available operating systems
		String[] operatingSystems = { "'Windows 7'", "'Windows 8'",
				"'MacOS Mountain Lion'", "'Windows 3.11'", "'SUSE Linux 12.3'" };

		for (String s : operatingSystems) {

			String insertionString = "insert into "
					+ connectionInfo.get("database.database") + "."
					+ Table.OS.toString() + " (Name) VALUES (" + s + ")";

			try {
				Statement statement = connection.createStatement();
				statement.executeUpdate(insertionString);

			} catch (SQLException e) {
				e.printStackTrace();
				log.log(e.getMessage(), LogLevel.ERROR);
			}
			log.log("Inserted " + s + " into " + Table.STATUS.toString(),
					LogLevel.INFO);
		}

	}

	/**
	 * Fills the notebook count table with initial data
	 */
	private void insertNotebookNumbers() {
		try {
			Statement statement = connection.createStatement();

			statement.addBatch("insert into "
					+ connectionInfo.get("database.database") + "."
					+ Table.NOTEBOOKCOUNT.toString()
					+ " (Name, Count) VALUES ('"
					+ NotebookCategory.LONG.toString() + "', 5)");
			statement.addBatch("insert into "
					+ connectionInfo.get("database.database") + "."
					+ Table.NOTEBOOKCOUNT.toString()
					+ " (Name, Count) VALUES ('"
					+ NotebookCategory.MEDIUM.toString() + "', 10)");
			statement.addBatch("insert into "
					+ connectionInfo.get("database.database") + "."
					+ Table.NOTEBOOKCOUNT.toString()
					+ " (Name, Count) VALUES ('"
					+ NotebookCategory.SHORT.toString() + "', 20)");

			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
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
	public List<User> getLecturers() {

		String sql = "SELECT ID, MatrNo, Firstname, Name, EMail, isStudent, isAdmin, isLecturer FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.USER.toString() + " WHERE IsLecturer = (1)";

		List<User> lecturers = new ArrayList<User>();

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				User user = new User();
				user.setID(result.getInt("ID"));
				user.setMatrNr(result.getInt("MatrNo"));
				user.setFirstName(result.getString("Firstname"));
				user.setLastName(result.getString("Name"));
				user.seteMail(result.getString("EMail"));
				user.setStudent(result.getBoolean("isStudent"));
				user.setAdmin(result.getBoolean("isAdmin"));
				user.setLecturer(result.getBoolean("isLecturer"));

				lecturers.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}
		return lecturers;
	}

	@Override
	public List<User> getAdmins() {

		String sql = "SELECT ID, MatrNo, Firstname, Name, EMail, isStudent, isAdmin, isLecturer FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.USER.toString() + " WHERE IsAdmin = (1)";

		List<User> userList = new ArrayList<User>();

		try {
			connect();

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				User user = new User();
				user.setID(result.getInt("ID"));
				user.setMatrNr(result.getInt("MatrNo"));
				user.setFirstName(result.getString("Firstname"));
				user.setLastName(result.getString("Name"));
				user.seteMail(result.getString("EMail"));
				user.setStudent(result.getBoolean("isStudent"));
				user.setAdmin(result.getBoolean("isAdmin"));
				user.setLecturer(result.getBoolean("isLecturer"));

				userList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return userList;
	}

	@Override
	public User getUserForID(int id) {

		String sql = "SELECT ID, MatrNo, Firstname, Name, EMail, isStudent, isAdmin, isLecturer FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.USER.toString() + " WHERE ID = ?";

		User user = null;

		try {
			connect();

			user = new User();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();

			if (result.next()) {

				user.setID(result.getInt("ID"));
				user.setMatrNr(result.getInt("MatrNo"));
				user.setFirstName(result.getString("Firstname"));
				user.setLastName(result.getString("Name"));
				user.seteMail(result.getString("EMail"));
				user.setStudent(result.getBoolean("isStudent"));
				user.setAdmin(result.getBoolean("isAdmin"));
				user.setLecturer(result.getBoolean("isLecturer"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return user;
	}

	@Override
	public String getOSForID(int id) {

		String sql = "SELECT Name FROM "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.OS.toString() + " WHERE ID = ?";

		String oSName = null;

		try {
			connect();

			oSName = new String();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();

			if (result.next()) {

				oSName = result.getString("Name");

			}

		} catch (SQLException e) {
			oSName = "Fehler";
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return oSName;
	}

	@Override
	public void insertRequest(Request request) {
		String sql = "INSERT INTO "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString()
				+ " (RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, UntilDate, StatusID, OSID, Description, Category) VALUES (? , ? , ? , ? , ? , ? , ? , ? , ? , ?, ?, ?)";

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, request.getRequesterId());
			statement.setInt(2, request.getApproverId());
			statement.setInt(3, request.getNotebookId());
			statement.setString(4, request.getHash());
			statement.setTimestamp(	5,
									(request.getCreated() != null) ? new Timestamp(request.getCreated()
																							.getTime())
											: null);
			statement.setTimestamp(	6,
									(request.getStart() != null) ? new Timestamp(request.getStart()
																						.getTime())
											: null);
			statement.setTimestamp(	7,
									(request.getEnd() != null) ? new Timestamp(request.getEnd()
																						.getTime())
											: null);
			statement.setTimestamp(	8,
									(request.getUntil() != null) ? new Timestamp(request.getUntil()
																						.getTime())
											: null);
			statement.setInt(9, request.getStatus()
										.getId());
			statement.setInt(10, request.getOs());

			statement.setString(11, request.getDescription());

			statement.setString(12, request.getCategory()
											.toString());

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}
	}

	@Override
	public void updateRequest(Request request) {
		String sql = "UPDATE "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString()
				+ " SET RequesterID = ? , ApproverID = ? , NotebookID = ?, Hash = ?, StartDate = ? , EndDate = ? , UntilDate = ? , StatusID = ?, OSID = ?, Description = ?, Category = ? WHERE ID = ?";

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, request.getRequesterId());
			statement.setInt(2, request.getApproverId());
			statement.setInt(3, request.getNotebookId());
			statement.setString(4, request.getHash());
			statement.setTimestamp(	5,
									(request.getStart() != null) ? new Timestamp(request.getStart()
																						.getTime())
											: null);
			statement.setTimestamp(	6,
									(request.getEnd() != null) ? new Timestamp(request.getEnd()
																						.getTime())
											: null);
			statement.setTimestamp(	7,
									(request.getUntil() != null) ? new Timestamp(request.getUntil()
																						.getTime())
											: null);
			statement.setInt(8, request.getStatus()
										.getId());
			statement.setInt(9, request.getOs());

			statement.setString(10, request.getDescription());

			statement.setString(11, request.getCategory()
											.toString());

			statement.setInt(12, request.getId());

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}
	}

	@Override
	public List<Request> getRequests() {
		String sql = "SELECT ID, RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, UntilDate, StatusID, OSID, Description, Category FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString();

		List<Request> requestList = new ArrayList<Request>();

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				Date creationDate = null;
				Date startDate = null;
				Date endDate = null;
				Date untilDate = null;

				if (result.getTimestamp("CreationDate") != null) {
					creationDate = new Date(result.getTimestamp("CreationDate")
													.getTime());
				}

				if (result.getTimestamp("StartDate") != null) {
					startDate = new Date(result.getTimestamp("StartDate")
												.getTime());
				}

				if (result.getTimestamp("EndDate") != null) {
					endDate = new Date(result.getTimestamp("EndDate")
												.getTime());
				}

				if (result.getTimestamp("UntilDate") != null) {
					untilDate = new Date(result.getTimestamp("UntilDate")
												.getTime());
				}

				Request request = new Request(	result.getInt("ID"),
												result.getInt("RequesterID"),
												result.getInt("ApproverID"),
												result.getInt("NotebookID"),
												creationDate,
												startDate,
												endDate,
												untilDate,
												result.getString("Hash"),
												result.getInt("StatusID"),
												result.getInt("OSID"),
												result.getString("Description"),
												NotebookCategory.valueOf(result.getString("Category")));

				requestList.add(request);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return requestList;
	}

	@Override
	public List<Request> getRequestsForApproverForID(int approverID) {
		String sql = "SELECT ID, RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, UntilDate, StatusID, OSID, Description, Category FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString() + " WHERE ApproverID = ?";

		List<Request> requestList = new ArrayList<Request>();

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, approverID);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				Date creationDate = null;
				Date startDate = null;
				Date endDate = null;
				Date untilDate = null;

				if (result.getTimestamp("CreationDate") != null) {
					creationDate = new Date(result.getTimestamp("CreationDate")
													.getTime());
				}

				if (result.getTimestamp("StartDate") != null) {
					startDate = new Date(result.getTimestamp("StartDate")
												.getTime());
				}

				if (result.getTimestamp("EndDate") != null) {
					endDate = new Date(result.getTimestamp("EndDate")
												.getTime());
				}

				if (result.getTimestamp("UntilDate") != null) {
					untilDate = new Date(result.getTimestamp("UntilDate")
												.getTime());
				}

				Request request = new Request(	result.getInt("ID"),
												result.getInt("RequesterID"),
												result.getInt("ApproverID"),
												result.getInt("NotebookID"),
												creationDate,
												startDate,
												endDate,
												untilDate,
												result.getString("Hash"),
												result.getInt("StatusID"),
												result.getInt("OSID"),
												result.getString("Description"),
												NotebookCategory.valueOf(result.getString("Category")));

				requestList.add(request);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return requestList;
	}

	@Override
	public List<Request> getRequestsForRequesterForID(int requesterID) {
		String sql = "SELECT ID, RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, UntilDate, StatusID, OSID, Description, Category FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString() + " WHERE RequesterID = ?";

		List<Request> requestList = new ArrayList<Request>();

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, requesterID);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				Date creationDate = null;
				Date startDate = null;
				Date endDate = null;
				Date untilDate = null;

				if (result.getTimestamp("CreationDate") != null) {
					creationDate = new Date(result.getTimestamp("CreationDate")
													.getTime());
				}

				if (result.getTimestamp("StartDate") != null) {
					startDate = new Date(result.getTimestamp("StartDate")
												.getTime());
				}

				if (result.getTimestamp("EndDate") != null) {
					endDate = new Date(result.getTimestamp("EndDate")
												.getTime());
				}

				if (result.getTimestamp("UntilDate") != null) {
					untilDate = new Date(result.getTimestamp("UntilDate")
												.getTime());
				}

				Request request = new Request(	result.getInt("ID"),
												result.getInt("RequesterID"),
												result.getInt("ApproverID"),
												result.getInt("NotebookID"),
												creationDate,
												startDate,
												endDate,
												untilDate,
												result.getString("Hash"),
												result.getInt("StatusID"),
												result.getInt("OSID"),
												result.getString("Description"),
												NotebookCategory.valueOf(result.getString("Category")));

				requestList.add(request);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return requestList;
	}

	@Override
	public Request getRequestForID(int id) {
		String sql = "SELECT ID, RequesterID, ApproverID, NotebookID, Hash, CreationDate, StartDate, EndDate, UntilDate, StatusID, OSID, Description, Category FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString() + " WHERE ID = ?";

		Request request = null;

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();

			if (result.next()) {

				Date creationDate = null;
				Date startDate = null;
				Date endDate = null;
				Date untilDate = null;

				if (result.getTimestamp("CreationDate") != null) {
					creationDate = new Date(result.getTimestamp("CreationDate")
													.getTime());
				}

				if (result.getTimestamp("StartDate") != null) {
					startDate = new Date(result.getTimestamp("StartDate")
												.getTime());
				}

				if (result.getTimestamp("EndDate") != null) {
					endDate = new Date(result.getTimestamp("EndDate")
												.getTime());
				}

				if (result.getTimestamp("UntilDate") != null) {
					untilDate = new Date(result.getTimestamp("UntilDate")
												.getTime());
				}

				request = new Request(	result.getInt("ID"),
										result.getInt("RequesterID"),
										result.getInt("ApproverID"),
										result.getInt("NotebookID"),
										creationDate,
										startDate,
										endDate,
										untilDate,
										result.getString("Hash"),
										result.getInt("StatusID"),
										result.getInt("OSID"),
										result.getString("Description"),
										NotebookCategory.valueOf(result.getString("Category")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return request;
	}

	@Override
	public Request getRequestForHash(String hash) {
		String sql = "SELECT ID, RequesterID, ApproverID, NotebookID, CreationDate, Hash, StartDate, EndDate, UntilDate, StatusID, OSID, Description, Category FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.PROCESS.toString() + " WHERE Hash = ?";

		Request request = null;

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, hash);

			ResultSet result = statement.executeQuery();

			if (result.next()) {

				Date creationDate = null;
				Date startDate = null;
				Date endDate = null;
				Date untilDate = null;

				if (result.getTimestamp("CreationDate") != null) {
					creationDate = new Date(result.getTimestamp("CreationDate")
													.getTime());
				}

				if (result.getTimestamp("StartDate") != null) {
					startDate = new Date(result.getTimestamp("StartDate")
												.getTime());
				}

				if (result.getTimestamp("EndDate") != null) {
					endDate = new Date(result.getTimestamp("EndDate")
												.getTime());
				}

				if (result.getTimestamp("UntilDate") != null) {
					untilDate = new Date(result.getTimestamp("UntilDate")
												.getTime());
				}

				request = new Request(	result.getInt("ID"),
										result.getInt("RequesterID"),
										result.getInt("ApproverID"),
										result.getInt("NotebookID"),
										creationDate,
										startDate,
										endDate,
										untilDate,
										result.getString("Hash"),
										result.getInt("StatusID"),
										result.getInt("OSID"),
										result.getString("Description"),
										NotebookCategory.valueOf(result.getString("Category")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
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
			connect();
			notebookList = new ArrayList<Notebook>();

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				Notebook notebook = new Notebook();

				notebook.setiD(result.getInt("ID"));
				notebook.setName(result.getString("Name"));
				notebook.setDefective(result.getBoolean("IsDefective"));
				notebook.setAvailable(result.getBoolean("IsAvailable"));

				notebookList.add(notebook);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return notebookList;
	}

	@Override
	public void insertNotebook(Notebook notebook) {
		String sql = "INSERT INTO "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.NOTEBOOK.toString()
				+ " (Name, IsDefective, IsAvailable) VALUES (?,?,?)";

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, notebook.getName());
			statement.setBoolean(2, notebook.isDefective());
			statement.setBoolean(3, notebook.isAvailable());

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
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
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, notebook.getName());
			statement.setBoolean(2, notebook.isDefective());
			statement.setBoolean(3, notebook.isAvailable());
			statement.setInt(4, notebook.getiD());

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}
	}

	@Override
	public void insertEMail(EMail eMail) {
		String sql = "INSERT INTO "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.EMAIL.toString()
				+ " (ReceiverMail, SenderMail, Header, Body, Date) VALUES (?,?,?,?,?)";

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, eMail.getReceiverMail());
			statement.setString(2, eMail.getSenderMail());
			statement.setString(3, eMail.getHeader());
			statement.setString(4, eMail.getBody());
			statement.setTimestamp(	5,
									(eMail.getDate() != null) ? new Timestamp(eMail.getDate()
																					.getTime())
											: null);

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}
	}

	@Override
	public Map<Integer, String> getOSs() {
		String sql = "SELECT ID, Name FROM "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.OS.toString();

		// initialize HashMap here, so that an empty map is returned in case of
		// error
		HashMap<Integer, String> osMap = new HashMap<Integer, String>();

		try {
			connection = connect();
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {

				osMap.put(result.getInt("ID"), result.getString("Name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return osMap;
	}

	@Override
	public Map<Integer, String> getStatusses() {
		String sql = "SELECT ID, Name FROM "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.STATUS.toString();

		// initialize HashMap here, so that an empty map is returned in case of
		// error
		HashMap<Integer, String> statusMap = new HashMap<Integer, String>();

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				statusMap.put(result.getInt("ID"), result.getString("Name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return statusMap;
	}

	@Override
	public Map<NotebookCategory, Integer> getNotebookCount() {
		String sql = "SELECT Name, Count FROM "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.NOTEBOOKCOUNT.toString();

		HashMap<NotebookCategory, Integer> notebooksMap = new HashMap<NotebookCategory, Integer>();

		try {
			connect();

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				notebooksMap.put(	NotebookCategory.valueOf(result.getString("Name")),
									result.getInt("Count"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return notebooksMap;
	}

	@Override
	public void updateNotebookCount(String name, int value) {
		String sql = "UPDATE "
				+ connectionInfo.getProperty("database.database") + "."
				+ Table.NOTEBOOKCOUNT.toString()
				+ " SET COUNT = COUNT + ? WHERE NAME = ?";

		try {
			connect();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, value);
			statement.setString(2, name);

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}
	}

	@Override
	public User authenticate(String username, String password) {
		String sql = "SELECT ID, MatrNo, Firstname, Name, EMail, isStudent, isAdmin, isLecturer, Password FROM "
				+ connectionInfo.getProperty("database.database")
				+ "."
				+ Table.USER.toString() + " WHERE Name = ?";

		User user = null;

		try {
			connect();
			user = null;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				// perform password check before any other work is done
				if (result.getString("Password")
							.equals(DigestUtils.md5Hex(password))) {

					user = new User();
					user.setID(result.getInt("ID"));
					user.setMatrNr(result.getInt("MatrNo"));
					user.setFirstName(result.getString("Firstname"));
					user.setLastName(result.getString("Name"));
					user.seteMail(result.getString("EMail"));
					user.setStudent(result.getBoolean("isStudent"));
					user.setAdmin(result.getBoolean("isAdmin"));
					user.setLecturer(result.getBoolean("isLecturer"));
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.log(e.getMessage(), LogLevel.ERROR);
		} finally {
			disconnect();
		}

		return user;
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
		 * 
		 * 
		 * Notebook notebook = new Notebook(); notebook.setAvailable(true);
		 * notebook.setDefective(false); notebook.setName("great notebook");
		 * notebook.setiD(1);
		 * 
		 * this.insertNotebook(notebook);
		 * 
		 * Request a1 = new Request( 1, 2, 12, 1, null, null, null, null,
		 * "sdf32", 1, 1,
		 * "This is a Request with id 1 from approver 12 and requester 2",
		 * "SHORT");
		 * 
		 * Request a2 = new Request( 2, 3, 14, 1, null, null, null, null,
		 * "sdf32", 1, 1, "This is a Request from approver 14 and requester 3",
		 * "MEDIUM");
		 * 
		 * Request a3 = new Request( 3, 4, 15, 1, null, null, null, null,
		 * "hiiamahash", 1, 1,
		 * "This is a Request with  approver id 15 requester id 4 hash: hiiamahash"
		 * , "LONG");
		 * 
		 * this.insertRequest(a1); this.insertRequest(a2);
		 * this.insertRequest(a3);
		 * 
		 * a1.setDescription(
		 * "new description for: This is a Request with id 1 from approver 12 and requester 2"
		 * ); this.updateRequest(a1);
		 * 
		 * Request b = this.getRequestForHash("hiiamahash"); Request c =
		 * this.getRequestForID(1);
		 * 
		 * Request d = this.getRequestsForApproverForID(14) .get(0); Request e =
		 * this.getRequestsForRequesterForID(2) .get(0);
		 * 
		 * System.out.println("hash hiiamahash: " + b.getDescription() +
		 * "Category(LONG): " + b.getCategory());
		 * 
		 * if (c != null) { System.out.println("id 1 und new description: " +
		 * c.getDescription()); } else { System.out.println("c is null"); }
		 * 
		 * System.out.println("approver 14: " + d.getDescription() +
		 * "Category(MEDIUM): " + d.getCategory());
		 * System.out.println("requester 2: " + e.getDescription() +
		 * "Category(SHORT): " + e.getCategory());
		 * System.out.println("new description: " + this.getRequests() .get(0)
		 * .getId() + "Category(SHORT): " + this.getRequests() .get(0)
		 * .getCategory());
		 * 
		 * EMail mail = new EMail(); mail.setBody("Hi this is the body");
		 * mail.setHeader("This is the header");
		 * mail.setReceiverMail("asd@dsf.w");
		 * mail.setSenderMail("sender@asd.wd"); this.insertEMail(mail);
		 * 
		 * System.out.println(this.getNotebooks() .get(0) .getName());
		 * 
		 * notebook.setName("small notebook");
		 * 
		 * this.updateNotebook(notebook);
		 * 
		 * System.out.println(this.getNotebooks() .get(0) .getName());
		 * System.out.println(this.getNotebooks() .get(0) .isAvailable());
		 * System.out.println(this.getNotebooks() .get(0) .isDefective());
		 * 
		 * System.out.println(this.getLecturers() .get(0) .getLastName());
		 * System.out.println(this.getAdmins() .get(0) .getLastName());
		 * System.out.println(this.getOSs() .get(1));
		 * System.out.println(this.getStatusses() .get(1));
		 * System.out.println(this.getUserForID(1) .getFirstName());
		 * 
		 * /* Map<NotebookCategory, Integer> map = this.getNotebookCount();
		 * System.out.println(map.get("short") + " " + map.get("medium") + " " +
		 * map.get("long")); this.updateNotebookCount("short", 1);
		 * this.updateNotebookCount("medium", 2);
		 * this.updateNotebookCount("long", 3); map = this.getNotebookCount();
		 * System.out.println(map.get("short") + " " + map.get("medium") + " " +
		 * map.get("long"));
		 */

	}

	/**
	 * Tries to close the object's connection, and sets it to null afterwards.
	 */
	private void disconnect() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.log(e.getMessage(), LogLevel.ERROR);
			} finally {
				connection = null;
			}
		}
	}

}
