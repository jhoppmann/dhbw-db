/**
 * Settings.java Created by jhoppmann on Feb 18, 2013
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

	/**
	 * The class's only constructor. Sets the settings it will have to use
	 * during its life and constructs the object
	 * 
	 * @param prop The <tt>Properties</tt> object to initialize this class with
	 */
	private Settings(Properties prop) {
		Settings.prop = prop;
	}

	/**
	 * Initializes an instance of the class, or does nothing if it already has
	 * been initialized
	 * 
	 * @param prop The <tt>Properties</tt> object to initialize this class with
	 */
	public static void initializeInstance(Properties prop) {
		if (singleton == null)
			singleton = new Settings(prop);
	}

	/**
	 * Returns the singleton's instance
	 * 
	 * @return The current instance, or <tt>null</tt> if it hasn't been
	 *         initialized yet
	 */
	public static Settings getInstance() {
		return singleton;
	}

	/**
	 * Gets the value to one key in the properties map
	 * 
	 * @param property The key of the value to get
	 * @return The corresponding value, or <tt>null</tt> if there is none
	 */
	public String get(String property) {
		return prop.getProperty(property);
	}

	/**
	 * getAll() returns a copy of the full map to keep the original immutable
	 * 
	 * @return The whole map of properties read in previously
	 */
	public Properties getAll() {
		return (Properties) prop.clone();
	}

}
