package com.dhbw_db.control.listeners;

import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dhbw_db.model.io.DataAccess;
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

		// initialize the database
		DataAccess db = new DataAccess();
		try {
			db.intializeDatabase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// There is nothing to do here at the moment.
	}

}
