/**
 * MainController.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.control;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.dhbw_db.model.authentication.Authenticator;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.model.execution.Executor;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.view.ApplicationWindow;
import com.vaadin.ui.Component;

/**
 * The MainController is responsible for the most common tasks, and those that
 * need to always be available, like switching views and printing messages.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class MainController {
	private ApplicationWindow view;

	private Executor executor;

	private User user;

	LoggingService log;

	public MainController(ApplicationWindow view) {
		this.view = view;
		executor = new Executor(2);

		log = LoggingService.getInstance();
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

	/**
	 * Replaces the current view in the main space with another one.
	 * 
	 * @param c The view to which to switch.
	 */
	public void changeView(Component c) {
		view.replaceView(c);
	}

	/**
	 * Returns the current user object.
	 * 
	 * @return The current user, or <tt>null</tt> if none is logged in.
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * @param value
	 * @param value2
	 */
	public void authenticate(String username, String password) {
		this.user = Authenticator.authenticate(username, password);
		((MainUI) (MainUI.getCurrent())).repaint();
	}

	/**
	 * @param view2
	 */
	public void setWindow(ApplicationWindow view) {
		this.view = view;
	}

}
