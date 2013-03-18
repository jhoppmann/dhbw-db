package com.dhbw_db.control.listeners;

import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dhbw_db.model.io.database.AsynchronousWrapper;
import com.dhbw_db.model.io.database.AsynchronousWrapper.SupportedDatabases;
import com.dhbw_db.model.io.database.DataAccess;
import com.dhbw_db.model.io.logging.ConsoleLogger;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;
import com.dhbw_db.model.settings.Settings;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// create a new, empty properties object, so that we don't pass null
		Properties config = new Properties();
		try {
			// try to load the settings file
			config.load(getClass().getResourceAsStream("/settings.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// initialize the settings singleton
		Settings.initializeInstance(config);

		// set up the loggers
		LoggingService log = LoggingService.getInstance();
		log.registerLogger(new ConsoleLogger());

		// initialize the database
		DataAccess db = AsynchronousWrapper.getDataAccess(SupportedDatabases.MYSQL);
		try {
			db.initializeDatabase();
		} catch (SQLException e) {
			// TODO think of a way to log a stacktrace
			log.log(e.getMessage(), LogLevel.ERROR);
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// There is nothing to do here at the moment.
	}

}
