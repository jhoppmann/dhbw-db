/**
 * Logger.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.model.io.logging;

import com.dhbw_db.model.io.logging.LoggingService.LogLevel;

/**
 * A class implementing this interface provides a concrete implementation of a
 * type of logging, handles the logging itself and decides if and how messages
 * are logged.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since
 */
public abstract class Logger {

	public abstract void log(String message, LogLevel l);

	@Override
	public boolean equals(Object obj) {
		return this.getClass()
					.equals(obj.getClass());
	}

}
