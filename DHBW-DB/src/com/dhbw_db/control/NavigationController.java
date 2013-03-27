/**
 * NavigationController.java Created by jhoppmann on Mar 22, 2013
 */
package com.dhbw_db.control;

import java.util.Map;

import com.dhbw_db.model.beans.Notebook.NotebookCategory;
import com.dhbw_db.view.PostButtonPage;
import com.dhbw_db.view.StartPage;
import com.dhbw_db.view.admin.AddNotebook;
import com.dhbw_db.view.admin.AdminAllRequestsPage;
import com.dhbw_db.view.admin.AdminApprovedRequestsPage;
import com.dhbw_db.view.admin.ChangeLaptops;
import com.dhbw_db.view.lecturer.LecturerAllRequestsPage;
import com.dhbw_db.view.lecturer.LecturerOpenRequestsPage;
import com.dhbw_db.view.student.NotebookRequest;
import com.dhbw_db.view.student.StudentRequestsPage;
import com.vaadin.ui.Component;

/**
 * The NavigationController controls the view changes initiated by a click on
 * the navbar.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class NavigationController {

	/**
	 * Every view that is reachable by the nav bar has an entry here
	 * 
	 * @author jhoppmann
	 * @version 0.1
	 * @since 0.1
	 */
	public enum View {
		START,
		NEW_REQUEST,
		STUDENT_REQUEST,
		LECTURER_OPEN,
		LECTURER_ALL,
		ADMIN_CHECKOUT,
		ADMIN_REQUESTS,
		ADMIN_NEWNOTEBOOK,
		ADMIN_NOTEBOOKSTATUS
	};

	/**
	 * Is responsible for causing the main controller to change to a view
	 * 
	 * @param v The view to change to
	 */
	public void changeView(View v) {
		MainController mc = MainController.get();

		Component c = null;

		switch (v) {
			case START:
				c = new StartPage();
				break;
			case NEW_REQUEST:
				if (checkForNotebooksLeft())
					c = new NotebookRequest();
				else
					c = new PostButtonPage(	"Keine Notebooks mehr verfügbar",
											"Leider sind im Moment alle Notebooks verliehen. Versuchen Sie es später noch einmal.");
				break;
			case STUDENT_REQUEST:
				c = new StudentRequestsPage();
				break;
			case LECTURER_OPEN:
				c = new LecturerOpenRequestsPage();
				break;
			case LECTURER_ALL:
				c = new LecturerAllRequestsPage();
				break;
			case ADMIN_CHECKOUT:
				c = new AdminApprovedRequestsPage();
				break;
			case ADMIN_REQUESTS:
				c = new AdminAllRequestsPage();
				break;
			case ADMIN_NOTEBOOKSTATUS:
				MainUI.getCurrent()
						.addWindow(new ChangeLaptops());
				break;
			case ADMIN_NEWNOTEBOOK:
				MainUI.getCurrent()
						.addWindow(new AddNotebook());
				break;
			default:
				break;
		}

		if (v != View.ADMIN_NEWNOTEBOOK && v != View.ADMIN_NOTEBOOKSTATUS) {
			mc.changeView(c);
		}
	}

	/**
	 * Sends a log out call to the main controller
	 */
	public void logout() {
		MainController.get()
						.logout();
	}

	/**
	 * Checks if there are notebooks left
	 * 
	 * @return <tt>true</tt> if there is even one notebook left, <tt>false</tt>
	 *         otherwise
	 */
	private boolean checkForNotebooksLeft() {
		boolean someLeft = false;
		Map<NotebookCategory, Integer> notebooks = MainController.get()
																	.getDataAccess()
																	.getNotebookCount();

		for (NotebookCategory nbc : notebooks.keySet()) {
			if (notebooks.get(nbc)
							.intValue() > 0)
				someLeft = true;
		}

		return someLeft;

	}
}
