/**
 * NavigationBar.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.view;

import com.dhbw_db.control.MainController;
import com.dhbw_db.control.MainUI;
import com.dhbw_db.model.beans.User;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;

/**
 * @author jhoppmann
 * @version 0.1
 * @since
 */
public class NavigationBar extends MenuBar {

	private static final long serialVersionUID = 4549157689158759031L;

	private MainController mc;

	public NavigationBar() {
		this.setWidth("100%");

		// build the request items
		// TODO Add authentication check

		mc = ((MainUI) (MainUI.getCurrent())).getController();

		User u = mc.getUser();

		// perhaps remodel to use enums?
		// always keeping an object of each view is not that good

		if (u.isAdmin())
			addItem("Start", new ViewChangeCommand(new AdminStartPage()));
		else if (u.isLecturer())
			addItem("Start", new ViewChangeCommand(new LecturerStartPage()));
		else
			addItem("Start", new ViewChangeCommand(new StudentStartPage()));

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

	private class ViewChangeCommand implements MenuBar.Command {

		private static final long serialVersionUID = -4984530031416204071L;

		Component c;

		public ViewChangeCommand(Component c) {
			this.c = c;
		}

		@Override
		public void menuSelected(MenuItem selectedItem) {
			NavigationBar.this.mc.changeView(c);

		}

	}

}
