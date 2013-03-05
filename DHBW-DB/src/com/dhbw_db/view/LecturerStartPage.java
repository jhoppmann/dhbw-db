/**
 * LecturerStartPage.java Created by Florian Hauck on 01.03.2013
 */
package com.dhbw_db.view;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

/**
 * The layout for the lecturer start page. Defines all components and their
 * positions. Expects the table items and adds them to the table.
 * 
 * @author Florian Hauck
 * @version 0.1
 * @since 0.1
 */
@SuppressWarnings("serial")
public class LecturerStartPage extends CustomComponent {

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private Panel panel;

	@AutoGenerated
	private AbsoluteLayout panelLayout;

	@AutoGenerated
	private Table newRequestsTable;

	@AutoGenerated
	private Label headlineLabel;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then define the table headlines and add items to the table.
	 */
	public LecturerStartPage() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO Constructor expects the table items and should pass them to the
		// addTableItems method.
		defineTableColumns();
		addTableItems();
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
		mainLayout.addComponent(panel, "left:0.0px;");

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
		panelLayout.setWidth("800px");
		panelLayout.setHeight("500px");

		// headlineLabel
		headlineLabel = new Label();
		headlineLabel.setImmediate(false);
		headlineLabel.setWidth("-1px");
		headlineLabel.setHeight("-1px");
		headlineLabel.setValue("Startseite Dozent");
		panelLayout.addComponent(headlineLabel, "top:20.0px;left:20.0px;");

		// newRequestsTable
		newRequestsTable = new Table();
		newRequestsTable.setCaption("Offene Leihanträge");
		defineTableColumns();
		addTableItems();
		newRequestsTable.setImmediate(false);
		newRequestsTable.setWidth("700px");
		newRequestsTable.setHeight("200px");
		panelLayout.addComponent(newRequestsTable, "top:80.0px;left:20.0px;");

		return panelLayout;
	}

	/**
	 * Defines the headlines of the table columns.
	 */
	private void defineTableColumns() {
		newRequestsTable.addContainerProperty("ID", Integer.class, null);
		newRequestsTable.addContainerProperty("Notebook", String.class, null);
		newRequestsTable.addContainerProperty(	"Betriebssystem",
												String.class,
												null);
		newRequestsTable.addContainerProperty("Student", String.class, null);
		newRequestsTable.addContainerProperty(	"Ausleihdatum",
												String.class,
												null);
		newRequestsTable.addContainerProperty(	"Rückgabedatum",
												String.class,
												null);
		newRequestsTable.addContainerProperty("Status", String.class, null);
	}

	/**
	 * Adds items to the table.
	 */
	private void addTableItems() {
		// TODO Decide in which form this method receives the table items and
		// add
		// these items to the table. Maybe a SQL container might be a good
		// solution.
		// newRequestsTable.setContainerDataSource(container);
	}

}
