/**
 * MainController.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.control;

import com.vaadin.ui.UI;

/**
 * The MainController is responsible for the most common tasks, and those that
 * need to always be available, like switching views and printing messages.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class MainController {
	private UI application;

	public MainController(UI application) {
		this.application = application;
	}

	public void print(String message) {
		System.out.println(message);
	}

	public void printError(String message) {
		System.err.println(message);
	}

}
