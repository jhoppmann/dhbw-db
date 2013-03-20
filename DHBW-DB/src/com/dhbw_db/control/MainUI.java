package com.dhbw_db.control;

import com.dhbw_db.model.io.logging.FileLogger;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.view.ApplicationWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Main UI class. This is the entry point of the Vaadin Framework.
 * 
 * @author jhoppmann
 */
@SuppressWarnings("serial")
@Theme("ourTheme")
public class MainUI extends UI {

	private MainController mc;

	@Override
	protected void init(VaadinRequest request) {

		getPage().setTitle("DHBW - Notebookverleih");

		mc = new MainController(null);
		repaint();
		LoggingService.getInstance()
						.registerLogger(new FileLogger());
	}

	public MainController getController() {
		return mc;
	}

	public void repaint() {
		ApplicationWindow view = null;
		view = new ApplicationWindow();
		setContent(view);
		mc.setWindow(view);
	}

}