/**
 * Logger.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.model.io.logging;

import java.util.ArrayList;
import java.util.List;

/**
 * The LoggingService class is a singleton responsible for handling the
 * different loggers the application utilizes and forward messages to them.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class LoggingService {
	/**
	 * The LogLevel enum contains the possible levels of a logging entry
	 * 
	 * @author jhoppmann
	 * @version 0.1
	 * @since 0.1
	 */
	public enum LogLevel {
		ERROR,
		INFO,
		DEBUG
	};

	private static LoggingService service;

	private List<Logger> loggers;

	/**
	 * log() delegates the call to all registered loggers to be handled there
	 * 
	 * @param message The message to be logged
	 * @param l The message's level
	 */
	public void log(String message, LogLevel level) {
		for (Logger logger : loggers) {
			logger.log(message, level);
		}
	}

	/**
	 * This private and only constructor initializes the list containing the
	 * registered loggers
	 */
	private LoggingService() {
		loggers = new ArrayList<Logger>();
	}

	/**
	 * Returns the single instance of the logging service class available.
	 * 
	 * @return The single instance of this class.
	 */
	public static LoggingService getInstance() {
		if (service == null) {
			service = new LoggingService();
		}
		return service;
	}

	/**
	 * Registers a new logger to the service.
	 * 
	 * @param logger A new logger to be registered, if and only if there is no
	 *            registered logger <tt>l</tt> where
	 *            <tt>logger.equals(l) == true</tt>.
	 */
	public void registerLogger(Logger logger) {
		if (!loggers.contains(logger)) {
			loggers.add(logger);
		}
	}

	/**
	 * Removes a specific type of <tt>Logger</tt> from the service.
	 * 
	 * @param logger The Logger to be removed, specifically a Logger of the same
	 *            concrete class as the registered Logger object.
	 */
	public void removeLogger(Logger logger) {
		loggers.remove(logger);
	}
}
