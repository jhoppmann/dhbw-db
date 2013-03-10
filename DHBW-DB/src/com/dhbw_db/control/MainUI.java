package com.dhbw_db.control;

import com.dhbw_db.view.ApplicationWindow;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Main UI class. This is the entry point of the Vaadin Framework.
 * 
 * @author jhoppmann
 */
@SuppressWarnings("serial")
public class MainUI extends UI {

	private MainController mc;

	@Override
	protected void init(VaadinRequest request) {
		ApplicationWindow view = new ApplicationWindow();
		view.setSizeFull();
		setContent(view);

		mc = new MainController(view);
	}

	public MainController getController() {
		return mc;
	}

}