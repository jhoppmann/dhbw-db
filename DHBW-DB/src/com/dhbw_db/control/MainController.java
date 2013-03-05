/**
 * MainController.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.control;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.dhbw_db.model.execution.Executor;
import com.vaadin.ui.UI;

/**
 * The MainController is responsible for the most common tasks, and those that
 * need to always be available, like switching views and printing messages.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class MainController {
	private UI application;

	private Executor executor;

	public MainController(UI application) {
		this.application = application;
		executor = new Executor(2);
	}

	/**
	 * Takes care of the standard ouptut for the program
	 * 
	 * @param message The message to show
	 */
	public void print(String message) {
		// TODO change this to info messages in the UI
		System.out.println(message);
	}

	/**
	 * Is responsible for standard error message handling.
	 * 
	 * @param message The error message
	 */
	public void printError(String message) {
		// TODO change this to info messages in the UI
		System.err.println(message);
	}

	/**
	 * Forwards a call to the executor service to run.
	 * 
	 * @param call The call to execute
	 * @return Returns the <tt>FutureTask</tt> object for reference and result
	 *         checking
	 */
	public FutureTask<String> execute(Callable<String> call) {
		return executor.addTask(call);
	}

}
