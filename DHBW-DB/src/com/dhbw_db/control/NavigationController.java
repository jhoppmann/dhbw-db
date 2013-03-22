/**
 * NavigationController.java Created by jhoppmann on Mar 22, 2013
 */
package com.dhbw_db.control;

import com.dhbw_db.view.AdminAllRequestsPage;
import com.dhbw_db.view.AdminApprovedRequestsPage;
import com.dhbw_db.view.AdminStartPage;
import com.dhbw_db.view.LecturerAllRequestsPage;
import com.dhbw_db.view.LecturerOpenRequestsPage;
import com.dhbw_db.view.LecturerStartPage;
import com.dhbw_db.view.NotebookRequest;
import com.dhbw_db.view.StudentRequestsPage;
import com.dhbw_db.view.StudentStartPage;
import com.vaadin.ui.Component;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class NavigationController {

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
}
