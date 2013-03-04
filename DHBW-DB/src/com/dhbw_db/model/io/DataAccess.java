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
		USERS("Users"),
		PROCESSES("Processes"),
		NOTEBOOKS("Notebooks"),
		OS("OS"),
		STATUSSES("Statusses");

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

		if (!tableExists(Table.USERS.toString())) {
			setupUserTable();
		}
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
	private void setupUserTable() {
		// this is a sample method that demonstrates how table creation methods
		// should look like

		// TODO Complete method
		String creationString = "create table "
				+ connectionInfo.get("database.database") + "."
				+ Table.USERS.toString()
				+ " (uid INT (11) NOT NULL AUTO_INCREMENT, "
				+ "username VARCHAR(25) DEFAULT NULL, " + "PRIMARY KEY (uid))";
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(creationString);

		} catch (SQLException e) {
			e.printStackTrace();
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
