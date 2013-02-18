package com.dhbw_db.control.listeners;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dhbw_db.model.settings.Settings;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class StartupListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Add initializing of database and loading settings
		System.out.println("Seems to work");

		Properties config = new Properties();
		try {
			config.load(getClass().getResourceAsStream("/settings.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Settings.initializeInstance(config);

		System.out.println(Settings.getInstance()
									.get("database.user"));

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// There is nothing to do here at the moment.
	}

}
