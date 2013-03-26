/**
 * MainController.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.control;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.dhbw_db.model.authentication.Authenticator;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.model.execution.Executor;
import com.dhbw_db.model.io.database.AsynchronousWrapper;
import com.dhbw_db.model.io.database.AsynchronousWrapper.SupportedDatabases;
import com.dhbw_db.model.io.database.DataAccess;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;
import com.dhbw_db.view.ApplicationWindow;
import com.dhbw_db.view.StartPage;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

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

	private User user;

	private DataAccess dataAccess;

	LoggingService log;

	public MainController(ApplicationWindow view) {
		this.view = view;
		dataAccess = AsynchronousWrapper.getDataAccess(SupportedDatabases.MYSQL);

		log = LoggingService.getInstance();
	}

	/**
	 * Takes care of the standard ouptut for the program
	 * 
	 * @param message The message to show
	 */
	public void print(String message) {
		Notification.show(message, Type.HUMANIZED_MESSAGE);
	}

	/**
	 * Is responsible for standard error message handling.
	 * 
	 * @param message The error message
	 */
	public void printError(String message) {
		// TODO change this to info messages in the UI
		Notification.show(message, Type.ERROR_MESSAGE);
	}

	/**
	 * Forwards a call to the executor service to run.
	 * 
	 * @param call The call to execute
	 * @return Returns the <tt>FutureTask</tt> object for reference and result
	 *         checking
	 */
	public FutureTask<String> execute(Callable<String> call) {
		return Executor.getInstance()
						.addTask(call);
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
	 * Tries to authenticate a user with a given password
	 * 
	 * @param username The user's username
	 * @param password The user's entered password
	 */
	public void authenticate(String username, String password) {
		this.user = Authenticator.authenticate(username, password);
		((MainUI) (MainUI.getCurrent())).repaint(new StartPage());
	}

	/**
	 * Sets the window that belongs to this controller
	 * 
	 * @param view The ApplicationWindow to which this controller belongs
	 */
	public void setWindow(ApplicationWindow view) {
		this.view = view;
	}

	/**
	 * Returns the application instance's DAO.
	 * 
	 * @return The instance's data access object
	 */
	public DataAccess getDataAccess() {
		return this.dataAccess;
	}

	/**
	 * The main controller belonging to this application instance.
	 * 
	 * @return Returns the current main controller for this instance
	 */
	public static MainController get() {
		return ((MainUI) (MainUI.getCurrent())).getController();
	}

	/**
	 * Sets a user for this controller
	 * 
	 * @param u The user object to use.
	 */
	public void setUser(User u) {
		this.user = u;
	}

	/**
	 * Logs out the current user.
	 */
	public void logout() {
		log.log("Logging out user " + user.getLastName() + ", "
						+ user.getFirstName(),
				LogLevel.INFO);
		user = null;
		((MainUI) (MainUI.getCurrent())).repaint(new StartPage());
	}

}
