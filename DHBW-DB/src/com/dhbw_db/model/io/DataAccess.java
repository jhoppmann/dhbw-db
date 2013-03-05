/**
 * DataAccess.java Created by jhoppmann on 14.02.2013
 */
package com.dhbw_db.model.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.dhbw_db.model.settings.Settings;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class DataAccess {

	private Properties connectionInfo;

	private Connection connection;

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

		System.out.println("createTables method executed");

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
}
