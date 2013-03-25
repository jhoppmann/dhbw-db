/**
 * FileLogger.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.model.io.logging;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;

import com.dhbw_db.control.MainController;
import com.dhbw_db.model.io.FileAccess;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;

/**
 * @author jhoppmann
 * @version 0.1
 * @since
 */
public class FileLogger extends Logger {

	private File target;

	private boolean asynchronous;

	/**
	 * This constructor needs a target file and
	 * 
	 * @param target A target file using an absolute path
	 * @param asynchronous if this should be called asynchronously
	 */
	public FileLogger(File target, boolean asynchronous) {
		this.target = target;
		this.asynchronous = asynchronous;
	}

	/**
	 * The no-argument constructor constructs a FileLogger that uses a standard
	 * file and asynchronous execution of writ commands.
	 */
	public FileLogger() {
		this(	new File("./log/notebookrental.log"),
				true);
	}

	@Override
	public void log(String message, LogLevel l) {
		if (asynchronous) {
			MainController.get()
							.execute(new WriteCommand(	message,
														l,
														target));
		} else {
			String line = l.toString() + "\t" + (new Date()).toString() + "\t"
					+ message;
			try {
				FileAccess.appendLine(target, line, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof FileLogger)) {
			return false;
		}
		FileLogger other = (FileLogger) o;
		if (!other.target.equals(this.target))
			return false;
		// if this is reached, class and target are the same
		return true;
	}

	/**
	 * WriteCommand is an inner class to be able to asynchronously call a rather
	 * expensive file system write command.
	 * 
	 * @author jhoppmann
	 * @version 0.1
	 * @since 0.1
	 */
	private class WriteCommand implements Callable<String> {

		String message;

		LogLevel l;

		File file;

		public WriteCommand(String message, LogLevel l, File file) {
			this.message = message;
			this.l = l;
			this.file = file;
		}

		@Override
		public String call() throws Exception {
			String line = l.toString() + "\t" + (new Date()).toString() + "\t"
					+ message;
			FileAccess.appendLine(file, line, true);
			return null;
		}

	}

}
