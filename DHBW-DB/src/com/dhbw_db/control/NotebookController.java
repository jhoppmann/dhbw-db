/**
 * NotebookController.java Created by jhoppmann on Mar 27, 2013
 */
package com.dhbw_db.control;

import com.dhbw_db.model.beans.Notebook;
import com.dhbw_db.model.beans.Notebook.NotebookCategory;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;
import com.dhbw_db.view.admin.AddNotebook;
import com.dhbw_db.view.admin.ChangeLaptops;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

/**
 * This controller reacts to the admin decisions concerning notebooks
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class NotebookController implements ClickListener {

	private static final long serialVersionUID = 2271194832192508132L;

	private Window controlledView;

	public NotebookController(Window view) {
		this.controlledView = view;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton()
					.getCaption()
					.equals("Status ändern")) {
			Notebook nb = ((ChangeLaptops) (controlledView)).getSelectedNotebook();
			NotebookCategory nbc = ((ChangeLaptops) (controlledView)).getSelectedCategory();

			// handle empty form fields
			if (nbc == null || nb == null) {
				MainController.get()
								.print("Nicht alle Pflichtfelder gefüllt");
				return;
			}

			// change the category's count on the database
			if (nb.isDefective()) {
				MainController.get()
								.getDataAccess()
								.updateNotebookCount(nbc.toString(), 1);
			} else
				MainController.get()
								.getDataAccess()
								.updateNotebookCount(nbc.toString(), -1);

			// negate the notebook's current status
			nb.setDefective(!nb.isDefective());
			// update the notebook data on the database
			MainController.get()
							.getDataAccess()
							.updateNotebook(nb);
			// log the changes made
			LoggingService log = LoggingService.getInstance();
			log.log("Notebook " + nb.getName() + " marked as "
							+ (nb.isDefective() ? "defective" : "functional"),
					LogLevel.INFO);
			log.log("NotebookCategory " + nbc.toString() + " changed by "
							+ (nb.isDefective() ? "-1" : "+1"),
					LogLevel.INFO);
			controlledView.close();

		} else if (event.getButton()
						.getCaption()
						.equals("Hinzufügen")) {
			String name = ((AddNotebook) (controlledView)).getNotebookName();
			NotebookCategory nbc = ((AddNotebook) (controlledView)).getSelectedCategory();

			// handle empty fields
			if (nbc == null || name == null || name.equals("")) {
				MainController.get()
								.print("Nicht alle Pflichtfelder gefüllt");
				return;
			}

			// create a new notebook object that is functional and available
			Notebook nb = new Notebook();
			nb.setName(name);
			nb.setDefective(false);
			nb.setAvailable(true);

			// change the category's count on the database
			MainController.get()
							.getDataAccess()
							.updateNotebookCount(nbc.toString(), 1);

			// insert the new notebook
			MainController.get()
							.getDataAccess()
							.insertNotebook(nb);

			LoggingService log = LoggingService.getInstance();
			log.log("Notebook " + nb.getName() + " added", LogLevel.INFO);
			log.log("NotebookCategory " + nbc.toString() + " changed by +1",
					LogLevel.INFO);
			controlledView.close();

		}

	}
}
