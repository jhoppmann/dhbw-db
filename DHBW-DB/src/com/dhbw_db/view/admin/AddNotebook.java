/**
 * AddNotebook.java Created by jhoppmann on Mar 27, 2013
 */
package com.dhbw_db.view.admin;

import com.dhbw_db.control.NotebookController;
import com.dhbw_db.model.beans.Notebook.NotebookCategory;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * This subwindow is used to add a new notebook to the application
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class AddNotebook extends Window {

	private static final long serialVersionUID = 2367809203984420592L;

	private OptionGroup duration;

	private TextField notebookName;

	public AddNotebook() {
		super("Notebook hinzufügen");
		super.setModal(true);
		super.setClosable(true);
		super.setSizeUndefined();
		super.setWidth("500px");

		VerticalLayout vlo = new VerticalLayout();

		duration = new OptionGroup();
		duration.setCaption("Ausleihdauer");
		duration.setImmediate(false);
		duration.setNullSelectionAllowed(false);
		duration.setRequired(true);
		for (NotebookCategory nbc : NotebookCategory.values()) {
			duration.addItem(nbc);
			duration.setItemCaption(nbc, nbc.getText());
			// a hack, but it works
			duration.setValue(nbc);

		}
		duration.setWidth("125px");
		duration.setHeight("60px");
		vlo.addComponent(duration);
		vlo.setComponentAlignment(duration, Alignment.MIDDLE_CENTER);

		notebookName = new TextField();
		notebookName.setRequired(true);
		vlo.addComponent(notebookName);
		vlo.setComponentAlignment(notebookName, Alignment.MIDDLE_CENTER);

		Button changeStatus = new Button("Hinzufügen");
		changeStatus.addClickListener(new NotebookController(this));
		vlo.addComponent(changeStatus);
		vlo.setComponentAlignment(changeStatus, Alignment.MIDDLE_CENTER);

		this.setContent(vlo);

	}

	/**
	 * This returns the selected duration
	 * 
	 * @return The selected duration
	 */
	public NotebookCategory getSelectedCategory() {
		try {
			duration.validate();
			return (NotebookCategory) duration.getValue();
		} catch (InvalidValueException e) {
		}
		return null;
	}

	/**
	 * Gets the name of the new notebook
	 * 
	 * @return the notebook's name
	 */
	public String getNotebookName() {
		return notebookName.getValue();
	}
}
