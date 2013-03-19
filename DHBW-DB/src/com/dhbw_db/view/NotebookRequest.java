/**
 * NotebookRequest.java Created by Florian Hauck on 01.03.2013
 */
package com.dhbw_db.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dhbw_db.control.NotebookRequestController;
import com.dhbw_db.model.beans.Notebook.NotebookCategory;
import com.dhbw_db.model.beans.User;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;

/**
 * The layout for the notebook request page. Defines all components and their
 * positions. Expects three String arrays, to add the items of the notebook
 * option group, the lecturer combo box and the OS combo box. Includes get
 * methods, to get all user inputs. The two buttons do NOT have any
 * functionality or button listeners yet.
 * 
 * @author Florian Hauck
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
@SuppressWarnings("serial")
public class NotebookRequest extends CustomComponent {

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private Panel panel;

	@AutoGenerated
	private AbsoluteLayout panelLayout;

	@AutoGenerated
	private Button requestButton;

	@AutoGenerated
	private Button removeButton;

	@AutoGenerated
	private PopupDateField endDatePopupDateField;

	@AutoGenerated
	private PopupDateField startDatePopupDateField;

	@AutoGenerated
	private TextArea commentTextArea;

	@AutoGenerated
	private ComboBox lecturerComboBox;

	@AutoGenerated
	private ComboBox oSComboBox;

	@AutoGenerated
	private OptionGroup notebookOptionGroup;

	@AutoGenerated
	private Label headlineLabel;

	NotebookRequestController control;

	private List<NotebookCategory> notebooks;

	private List<User> lecturers;

	private Map<Integer, String> operatingSystems;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then add items to the notebook option group, the lecturers combo
	 * box and the OS combo box.
	 */
	public NotebookRequest() {
		control = new NotebookRequestController(this);
		buildMainLayout();
		setCompositionRoot(mainLayout);

		notebooks = control.getNotebooks();
		operatingSystems = control.getOperatingSystems();
		lecturers = control.getApprovers();

		addNotebookOptions();
		addOperatingSystems();
		addLecturers();
	}

	/**
	 * Builds the main layout and adds a new panel to it. The panel is needed to
	 * get a scroll bar.
	 * 
	 * @return The main layout which contains a panel which contains an absolute
	 *         layout with all components.
	 */
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// panel
		panel = buildPanel();
		mainLayout.addComponent(panel, "top:0.0px;");

		return mainLayout;
	}

	/**
	 * Builds a panel and adds a new absolute layout to the panel.
	 * 
	 * @return The panel which contains an absolute layout with all components.
	 */
	@AutoGenerated
	private Panel buildPanel() {
		// common part: create layout
		panel = new Panel();
		panel.setImmediate(false);
		panel.setWidth("100.0%");
		panel.setHeight("100.0%");

		// panelLayout
		panelLayout = buildPanelLayout();
		panel.setContent(panelLayout);

		return panel;
	}

	/**
	 * Builds an absolute layout and adds all components to that layout.
	 * 
	 * @return The absolute layout which contains all components.
	 */
	@AutoGenerated
	private AbsoluteLayout buildPanelLayout() {
		// common part: create layout
		panelLayout = new AbsoluteLayout();
		panelLayout.setImmediate(false);
		// to get the scroll bar the size should not be full size
		panelLayout.setWidth("300px");
		panelLayout.setHeight("500px");

		// headlineLabel
		headlineLabel = new Label();
		headlineLabel.setImmediate(false);
		headlineLabel.setWidth("-1px");
		headlineLabel.setHeight("-1px");
		headlineLabel.setValue("Neuer Leihantrag");
		panelLayout.addComponent(headlineLabel, "top:20.0px;left:20.0px;");

		// notebookOptionGroup
		notebookOptionGroup = new OptionGroup();
		notebookOptionGroup.setCaption("Ausleihgeräte");
		notebookOptionGroup.setImmediate(false);
		notebookOptionGroup.setNullSelectionAllowed(false);
		notebookOptionGroup.setWidth("96px");
		notebookOptionGroup.setHeight("60px");
		panelLayout.addComponent(notebookOptionGroup, "top:70.0px;left:20.0px;");

		// oSComboBox
		oSComboBox = new ComboBox();
		oSComboBox.setCaption("Betriebssystem");
		oSComboBox.setImmediate(false);
		oSComboBox.setNullSelectionAllowed(false);
		oSComboBox.setWidth("-1px");
		oSComboBox.setHeight("-1px");
		panelLayout.addComponent(oSComboBox, "top:150.0px;left:20.0px;");

		// lecturerComboBox
		lecturerComboBox = new ComboBox();
		lecturerComboBox.setCaption("Betreuer");
		lecturerComboBox.setImmediate(false);
		lecturerComboBox.setNullSelectionAllowed(false);
		lecturerComboBox.setWidth("-1px");
		lecturerComboBox.setHeight("-1px");
		panelLayout.addComponent(lecturerComboBox, "top:200.0px;left:20.0px;");

		// commentTextArea
		commentTextArea = new TextArea();
		commentTextArea.setCaption("Bemerkung für den Betreuer");
		commentTextArea.setImmediate(false);
		commentTextArea.setWidth("-1px");
		commentTextArea.setHeight("-1px");
		panelLayout.addComponent(commentTextArea, "top:250.0px;left:20.0px;");

		// startDatePopupDateField
		startDatePopupDateField = new PopupDateField();
		startDatePopupDateField.setCaption("Ausleihdatum");
		startDatePopupDateField.setImmediate(false);
		startDatePopupDateField.setWidth("-1px");
		startDatePopupDateField.setHeight("-1px");
		startDatePopupDateField.setValue(new Date());
		panelLayout.addComponent(	startDatePopupDateField,
									"top:370.0px;left:20.0px;");

		// endDatePopupDateField
		endDatePopupDateField = new PopupDateField();
		endDatePopupDateField.setCaption("Rückgabedatum");
		endDatePopupDateField.setImmediate(false);
		endDatePopupDateField.setWidth("-1px");
		endDatePopupDateField.setHeight("-1px");
		panelLayout.addComponent(	endDatePopupDateField,
									"top:420.0px;left:20.0px;");

		// removeButton
		removeButton = new Button();
		removeButton.setCaption("Zurücksetzen");
		removeButton.setImmediate(true);
		removeButton.setWidth("-1px");
		removeButton.setHeight("-1px");
		removeButton.addClickListener(control);
		panelLayout.addComponent(removeButton, "top:460.0px;left:20.0px;");

		// requestButton
		requestButton = new Button();
		requestButton.setCaption("Beantragen");
		requestButton.setImmediate(true);
		requestButton.setWidth("-1px");
		requestButton.setHeight("-1px");
		panelLayout.addComponent(requestButton, "top:460.0px;left:140.0px;");

		return panelLayout;
	}

	/**
	 * @return The value of the start date field.
	 */
	public Date getStartDate() {
		return startDatePopupDateField.getValue();
	}

	/**
	 * @return The value of the end date field.
	 */
	public Date getEndDate() {
		return endDatePopupDateField.getValue();
	}

	/**
	 * @return The value of the comment text area.
	 */
	public String getComment() {
		return commentTextArea.getValue();
	}

	/**
	 * @return The selected lecturer.
	 */
	public String getLecturer() {
		return (String) lecturerComboBox.getConvertedValue();
	}

	/**
	 * @return The selected OS.
	 */
	public String getOS() {
		return (String) oSComboBox.getConvertedValue();
	}

	/**
	 * @return The selected notebook category.
	 */
	public String getNotebook() {
		return (String) notebookOptionGroup.getConvertedValue();
	}

	/**
	 * Adds the notebooks as items to the notebook option group.
	 * 
	 * @param notebooks
	 */
	private void addNotebookOptions() {
		for (NotebookCategory nbc : notebooks) {
			notebookOptionGroup.addItem(nbc);
		}
	}

	/**
	 * Adds the operating systems as items to the OS combo box.
	 * 
	 * @param operatingSystems
	 */
	private void addOperatingSystems() {
		for (Integer osID : operatingSystems.keySet()) {
			oSComboBox.addItem(osID);
			oSComboBox.setItemCaption(osID, operatingSystems.get(osID));
		}
	}

	/**
	 * Adds the lecturers as items to the lecturer combo box.
	 * 
	 * @param lecturers
	 */
	private void addLecturers() {
		for (User u : lecturers) {
			lecturerComboBox.addItem(u.getID());
			lecturerComboBox.setItemCaption(u.getID(), u.getLastName() + ", "
					+ u.getFirstName());
		}
	}

	/**
	 * Clears all elements
	 */
	public void reset() {
		commentTextArea.setValue("");
		endDatePopupDateField.setValue(null);
		startDatePopupDateField.setValue(new Date());
		lecturerComboBox.select(null);
		notebookOptionGroup.select(null);
		oSComboBox.select(null);
	}

}
