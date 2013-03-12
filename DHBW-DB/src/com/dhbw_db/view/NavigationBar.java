/**
 * NavigationBar.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.view;

import com.dhbw_db.control.MainUI;
import com.dhbw_db.model.beans.User;
import com.vaadin.ui.MenuBar;

/**
 * @author jhoppmann
 * @version 0.1
 * @since
 */
public class NavigationBar extends MenuBar {

	private static final long serialVersionUID = 4549157689158759031L;

	public NavigationBar() {
		this.setWidth("100%");

		// build the request items
		// TODO Add authentication check

		User u = ((MainUI) (MainUI.getCurrent())).getController()
													.getUser();

		addItem("Start", null);

		if (u.isStudent())
			addItem("New Request", null);

		if (u.isStudent())
			addItem("My Requests", null);

		if (u.isLecturer())
			addItem("Approve Requests", null);

		if (u.isAdmin())
			addItem("Running Requests", null);

		if (u.isAdmin())
			addItem("Notebook Statuses", null);

		if (u.isAdmin())
			addItem("Admin Area", null);

	}

}
