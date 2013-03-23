/**
 * AsynchronousWrapper.java Created by jhoppmann on Mar 18, 2013
 */
package com.dhbw_db.model.io.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.dhbw_db.control.MainController;
import com.dhbw_db.control.MainUI;
import com.dhbw_db.model.beans.EMail;
import com.dhbw_db.model.beans.Notebook;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.model.request.Request;

/**
 * The AsynchronousWrapper class is a decorator for the data access that calls
 * the write methods asynchronously.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class AsynchronousWrapper implements DataAccess {

	/**
	 * This holds the types of databases that are supported by the application.
	 * 
	 * @author jhoppmann
	 * @version 0.1
	 * @since 0.1
	 */
	public enum SupportedDatabases {
		// for easy extensibility, this and the getDataAccess method must be
		// extended
		MYSQL;
	};

	private DataAccess wrappedAccess;

	private AsynchronousWrapper(DataAccess da) {
		this.wrappedAccess = da;
	}

	/**
	 * This factory method will return a wrapped data access for the supported
	 * database type that is given as parameter.
	 * 
	 * @param type The database type
	 * @return A DataAccess
	 */
	public static DataAccess getDataAccess(SupportedDatabases type) {
		if (type.equals(SupportedDatabases.MYSQL)) {
			return new AsynchronousWrapper(new MySQLAccess());
		}

		return null;
	}

	@Override
	public void initializeDatabase() throws SQLException {
		// Since nothing must happen before the database is initialized, this is
		// a synchronous call
		wrappedAccess.initializeDatabase();

	}

	@Override
	public List<User> getLecturers() {
		// Just hand the call down to the wrapped object
		return wrappedAccess.getLecturers();
	}

	@Override
	public List<User> getAdmins() {
		// Just hand the call down to the wrapped object
		return wrappedAccess.getAdmins();
	}

	@Override
	public User getUserForID(int id) {
		// Just hand the call down to the wrapped object
		return wrappedAccess.getUserForID(id);
	}

	@Override
	public void insertRequest(final Request request) {
		MainController mc = ((MainUI) (MainUI.getCurrent())).getController();

		mc.execute(new Callable<String>() {

			@Override
			public String call() throws Exception {
				AsynchronousWrapper.this.wrappedAccess.insertRequest(request);
				return "Inserted request " + request.getId();
			}
		});

	}

	@Override
	public void updateRequest(final Request request) {
		MainController mc = ((MainUI) (MainUI.getCurrent())).getController();

		mc.execute(new Callable<String>() {

			@Override
			public String call() throws Exception {
				AsynchronousWrapper.this.wrappedAccess.updateRequest(request);
				return "Updated request " + request.getId();
			}
		});
	}

	@Override
	public List<Request> getRequests() {
		// Just hand the call down to the wrapped object
		return wrappedAccess.getRequests();
	}

	@Override
	public Request getRequestForID(int id) {
		// Just hand the call down to the wrapped object
		return wrappedAccess.getRequestForID(id);
	}

	@Override
	public Request getRequestForHash(String hash) {
		// Just hand the call down to the wrapped object
		return wrappedAccess.getRequestForHash(hash);
	}

	@Override
	public List<Notebook> getNotebooks() {
		// Just hand the call down to the wrapped object
		return wrappedAccess.getNotebooks();
	}

	@Override
	public void insertNotebook(final Notebook notebook) {
		MainController mc = ((MainUI) (MainUI.getCurrent())).getController();

		mc.execute(new Callable<String>() {

			@Override
			public String call() throws Exception {
				AsynchronousWrapper.this.wrappedAccess.insertNotebook(notebook);
				return "Inserted notebook " + notebook.toString();
			}
		});
	}

	@Override
	public void updateNotebook(final Notebook notebook) {
		MainController mc = ((MainUI) (MainUI.getCurrent())).getController();

		mc.execute(new Callable<String>() {

			@Override
			public String call() throws Exception {
				AsynchronousWrapper.this.wrappedAccess.updateNotebook(notebook);
				return "Updated notebook " + notebook.toString();
			}
		});
	}

	@Override
	public void insertEMail(final EMail eMail) {
		MainController mc = ((MainUI) (MainUI.getCurrent())).getController();

		mc.execute(new Callable<String>() {

			@Override
			public String call() throws Exception {
				AsynchronousWrapper.this.wrappedAccess.insertEMail(eMail);
				return "Updated eMail " + eMail.toString();
			}
		});
	}

	@Override
	public Map<Integer, String> getOSs() {
		// Just hand the call down to the wrapped object
		return wrappedAccess.getOSs();
	}

	@Override
	public Map<Integer, String> getStatusses() {
		// Just hand the call down to the wrapped object
		return wrappedAccess.getStatusses();
	}

	@Override
	public User authenticate(String username, String password) {
		// Just hand the call down to the wrapped object
		return wrappedAccess.authenticate(username, password);
	}

	@Override
	public List<Request> getRequestsForApproverForID(int approverID) {
		return wrappedAccess.getRequestsForApproverForID(approverID);
	}

	@Override
	public List<Request> getRequestsForRequesterForID(int requesterID) {
		return wrappedAccess.getRequestsForRequesterForID(requesterID);
	}

}
