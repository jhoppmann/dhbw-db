/**
 * Settings.java Created by Hoppi on Feb 18, 2013
 */
package com.dhbw_db.model.settings;

import java.util.Properties;

/**
 * The Settings class is a singleton, designed to hold previously configured
 * settings from a properties file, loaded at startup. There is no way to change
 * the content after the first initialization, since this could lead to
 * inconsistencies across multiple clients.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class Settings {
	private static Properties prop;

	private static Settings singleton;

	private Settings(Properties prop) {
		Settings.prop = prop;
	}

	public static void initializeInstance(Properties prop) {
		if (singleton == null)
			singleton = new Settings(prop);
	}

	public static Settings getInstance() {
		return singleton;
	}

	public String get(String property) {
		return prop.getProperty(property);
	}

}
