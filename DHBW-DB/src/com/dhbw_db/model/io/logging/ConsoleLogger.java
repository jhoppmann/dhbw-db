/**
 * ConsoleLogger.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.model.io.logging;

import com.dhbw_db.model.io.logging.LoggingService.LogLevel;
import com.dhbw_db.model.settings.Settings;

/**
 * The ConsoleLogger logs message to the console, omitting debug messages when
 * the application is not in debug mode.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class ConsoleLogger extends Logger {

	@Override
	public void log(String message, LogLevel l) {
		// omit debug messages if we're not in debug mode
		if (l != LogLevel.DEBUG || Settings.getInstance()
											.isDebugMode()) {
			System.out.println(l.toString() + ": " + message);
		}

	}

}
