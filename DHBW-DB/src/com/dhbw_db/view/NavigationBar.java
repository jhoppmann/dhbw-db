/**
 * NavigationBar.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.view;

import java.util.List;

import com.dhbw_db.control.MainController;
import com.dhbw_db.control.NavigationController;
import com.dhbw_db.control.NavigationController.View;
import com.dhbw_db.model.beans.User;
import com.vaadin.ui.MenuBar;

/**
 * @author jhoppmann
 * @version 0.1
 * @since
 */
public class NavigationBar extends MenuBar {

	private static final long serialVersionUID = 4549157689158759031L;

	private MainController mc;

	private NavigationController control;

	private MenuItem checkedItem;

	public NavigationBar() {
		this.setWidth("100%");

		mc = MainController.get();

		User u = mc.getUser();

		control = new NavigationController();

		// build the navigation items
		if (u.isAdmin() || u.isLecturer() || u.isStudent())
			addItem("Start", new ViewChangeCommand(View.START));

		if (u.isStudent())
			addItem("Neuer Leihantrag", new ViewChangeCommand(View.NEW_REQUEST));

		if (u.isStudent())
			addItem("Eigene Anträge",
					new ViewChangeCommand(View.STUDENT_REQUEST));

		if (u.isLecturer())
			addItem("Offene Anträge", new ViewChangeCommand(View.LECTURER_OPEN));

		if (u.isLecturer())
			addItem("Antragsinbox", new ViewChangeCommand(View.LECTURER_ALL));

		if (u.isAdmin())
			addItem("Ausgabe / Rücknahme",
					new ViewChangeCommand(View.ADMIN_CHECKOUT));

		if (u.isAdmin())
			addItem("Alle Anträge", new ViewChangeCommand(View.ADMIN_REQUESTS));

		if (u.isAdmin())
			addItem("Notebookstatus ändern",
					new ViewChangeCommand(View.ADMIN_NOTEBOOKSTATUS));

		if (u.isAdmin())
			addItem("Notebook hinzufügen",
					new ViewChangeCommand(View.ADMIN_NEWNOTEBOOK));

		if (u != null && (u.isAdmin() || u.isLecturer() || u.isStudent())) {
			addItem("Logout", new MenuBar.Command() {

				private static final long serialVersionUID = 1L;

				@Override
				public void menuSelected(MenuItem selectedItem) {
					control.logout();
				}
			});
		}

		// sets the status of the "Start" item in the menu bar to "checked" by
		// default
		List<MenuItem> itemList = this.getItems();
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i)
						.getText()
						.equals("Start")) {
				checkedItem = itemList.get(i);
				checkedItem.setCheckable(true);
				checkedItem.setChecked(true);
			}
		}

	}

	/**
	 * Holds a view to change to on a click
	 * 
	 * @author jhoppmann
	 * @version 0.1
	 * @since 0,1
	 */
	private class ViewChangeCommand implements MenuBar.Command {

		private static final long serialVersionUID = -4984530031416204071L;

		private View v;

		public ViewChangeCommand(View v) {
			this.v = v;
		}

		@Override
		public void menuSelected(MenuItem selectedItem) {
			checkedItem.setChecked(false);
			checkedItem = selectedItem;
			selectedItem.setCheckable(true);
			selectedItem.setChecked(true);

			NavigationBar.this.control.changeView(v);
		}

	}

}
