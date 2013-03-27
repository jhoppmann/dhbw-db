/**
 * Test.java Created by jhoppmann on Mar 27, 2013
 */
package com.dhbw_db.view.admin;

import com.dhbw_db.control.MainController;
import com.dhbw_db.control.NotebookController;
import com.dhbw_db.model.beans.Notebook;
import com.dhbw_db.model.beans.Notebook.NotebookCategory;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The ChangeLaptops view allows an administrator to mark a notebook as
 * defective or mark it as working again.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class ChangeLaptops extends Window {

	private static final long serialVersionUID = 2367809203984420592L;

	private OptionGroup duration;

	private ComboBox notebooks;

	public ChangeLaptops() {
		super("Laptopstatus ändern");
		super.setModal(true);
		super.setClosable(true);
		super.setSizeUndefined();
		super.setWidth("500px");

		VerticalLayout vlo = new VerticalLayout();

		duration = new OptionGroup();
		duration.setCaption("Ausleihdauer");
		duration.setImmediate(false);
		duration.setNullSelectionAllowed(false);
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

		notebooks = new ComboBox();
		notebooks.setCaption("Notebooks");
		notebooks.setImmediate(false);
		notebooks.setNullSelectionAllowed(false);
		notebooks.setWidth("400px");
		notebooks.setHeight("-1px");
		for (Notebook nb : MainController.get()
											.getDataAccess()
											.getNotebooks()) {
			notebooks.addItem(nb);
			notebooks.setItemCaption(nb, nb.toString());
		}
		vlo.addComponent(notebooks);
		vlo.setComponentAlignment(notebooks, Alignment.MIDDLE_CENTER);

		Button changeStatus = new Button("Status ändern");
		changeStatus.addClickListener(new NotebookController(this));
		vlo.addComponent(changeStatus);
		vlo.setComponentAlignment(changeStatus, Alignment.MIDDLE_CENTER);

		this.setContent(vlo);

	}

	/**
	 * This returns the selected notebook
	 * 
	 * @return Noteboook that's been selected
	 */
	public Notebook getSelectedNotebook() {
		return (Notebook) notebooks.getValue();
	}

	/**
	 * This returns the selected duration
	 * 
	 * @return The selected duration
	 */
	public NotebookCategory getSelectedCategory() {
		return (NotebookCategory) duration.getValue();
	}
}
