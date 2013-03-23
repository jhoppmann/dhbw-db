/**
 * NewRequestController.java Created by jhoppmann on Mar 14, 2013
 */
package com.dhbw_db.control;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.dhbw_db.model.beans.Notebook.NotebookCategory;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.model.io.database.DataAccess;
import com.dhbw_db.model.request.Request;
import com.dhbw_db.view.NotebookRequest;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class NotebookRequestController implements ClickListener {

	private static final long serialVersionUID = 1L;

	private DataAccess dao;

	private NotebookRequest controlledView;

	public NotebookRequestController(NotebookRequest controlledView) {
		this.dao = MainController.get()
									.getDataAccess();
		this.controlledView = controlledView;
	}

	/**
	 * This will return the notebook categories.
	 * 
	 * @return A List with NotebookCategories.
	 */
	public List<NotebookCategory> getNotebooks() {
		return Arrays.asList(NotebookCategory.values());
	}

	/**
	 * This will get a Map of the available operating systems.
	 * 
	 * @return A Map with a string representations of operating systems as keys
	 *         and their unique IDs as values
	 */
	public Map<Integer, String> getOperatingSystems() {
		return dao.getOSs();
	}

	/**
	 * This will get a Map of the available approvers.
	 * 
	 * @return A Map with a string representations of approvers as keys and
	 *         their unique IDs as values
	 */
	public List<User> getApprovers() {
		return dao.getLecturers();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton()
					.getCaption()
					.equals("Zurücksetzen")) {
			controlledView.reset();
		} else if (event.getButton()
						.getCaption()
						.equals("Beantragen")) {
			Request r = controlledView.getRequest();
			dao.insertRequest(r);
		}

	}
}
