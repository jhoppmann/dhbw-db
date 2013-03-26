/**
 * NavigationController.java Created by jhoppmann on Mar 22, 2013
 */
package com.dhbw_db.control;

import com.dhbw_db.view.admin.AdminAllRequestsPage;
import com.dhbw_db.view.admin.AdminApprovedRequestsPage;
import com.dhbw_db.view.admin.AdminStartPage;
import com.dhbw_db.view.lecturer.LecturerAllRequestsPage;
import com.dhbw_db.view.lecturer.LecturerOpenRequestsPage;
import com.dhbw_db.view.lecturer.LecturerStartPage;
import com.dhbw_db.view.student.NotebookRequest;
import com.dhbw_db.view.student.StudentRequestsPage;
import com.dhbw_db.view.student.StudentStartPage;
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
		START_ADMIN,
		START_STUDENT,
		START_LECTURER,
		NEW_REQUEST,
		STUDENT_REQUEST,
		LECTURER_OPEN,
		LECTURER_ALL,
		ADMIN_CHECKOUT,
		ADMIN_REQUESTS,
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
			case START_ADMIN:
				c = new AdminStartPage();
				break;
			case START_STUDENT:
				c = new StudentStartPage();
				break;
			case START_LECTURER:
				c = new LecturerStartPage();
				break;
			case NEW_REQUEST:
				c = new NotebookRequest();
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
		}

		mc.changeView(c);
	}

	/**
	 * Sends a log out call to the main controller
	 */
	public void logout() {
		MainController.get()
						.logout();
	}
}
