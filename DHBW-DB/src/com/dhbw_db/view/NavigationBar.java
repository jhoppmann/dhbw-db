/**
 * NavigationBar.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.view;

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

		addItem("Start", null);

		addItem("New Request", null);

		addItem("My Requests", null);

		addItem("Approve Requests", null);

		addItem("Running Requests", null);

		addItem("Notebook Statuses", null);

		addItem("Admin Area", null);

	}

}
