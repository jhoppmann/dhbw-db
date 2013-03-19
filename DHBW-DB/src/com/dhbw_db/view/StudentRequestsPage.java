/**
 * StudentRequestsPage.java Created by Yannic Frank on 15.03.2013
 */
package com.dhbw_db.view;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Yannic Frank
 * @version 0.1
 * @since
 */
public class StudentRequestsPage extends CustomComponent {

	private AbsoluteLayout mainLayout;

	private Panel panel;

	private VerticalLayout panelLayout;

	private Table requestsTable;

	private Label headlineLabel;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public StudentRequestsPage() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		defineTableColumns();
		addTableItems();
	}

	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// panel_2
		panel = buildPanel();
		mainLayout.addComponent(panel, "top:0.0px;left:0.0px;");

		return mainLayout;
	}

	private Panel buildPanel() {
		// common part: create layout
		panel = new Panel();
		panel.setImmediate(false);
		panel.setWidth("100.0%");
		panel.setHeight("100.0%");

		// absoluteLayout_3
		panelLayout = buildpanelLayout();
		panel.setContent(panelLayout);

		return panel;
	}

	private VerticalLayout buildpanelLayout() {
		// common part: create layout
		panelLayout = new VerticalLayout();
		panelLayout.setImmediate(false);
		panelLayout.setSpacing(true);
		panelLayout.setMargin(true);
		// panelLayout.setWidth("100.0%");
		// panelLayout.setHeight("100.0%");

		// headlineLabel
		headlineLabel = new Label();
		headlineLabel.setStyleName("headline");
		headlineLabel.setImmediate(false);
		headlineLabel.setWidth("-1px");
		headlineLabel.setHeight("-1px");
		headlineLabel.setValue("Eigene Anträge");
		panelLayout.addComponent(headlineLabel);

		// detailTable
		requestsTable = new Table();
		requestsTable.setCaption("Alle bisherigen Leihvorgänge");

		requestsTable.setImmediate(false);
		// requestsTable.setWidth("700px");
		// requestsTable.setHeight("45px");
		panelLayout.addComponent(requestsTable);

		return panelLayout;
	}

	/**
	 * Defines the headlines of the table columns.
	 */
	private void defineTableColumns() {
		requestsTable.addContainerProperty("ID", Integer.class, null);
		requestsTable.addContainerProperty("Notebook", String.class, null);
		requestsTable.addContainerProperty("Betriebssystem", String.class, null);
		requestsTable.addContainerProperty("Betreuer", String.class, null);
		requestsTable.addContainerProperty(	"Erstellung des Antrages",
											String.class,
											null);
		requestsTable.addContainerProperty("Ausleihdatum", String.class, null);
		requestsTable.addContainerProperty("Rückgabedatum", String.class, null);
		requestsTable.addContainerProperty(	"Ende des Antrages",
											String.class,
											null);
		requestsTable.addContainerProperty("Status", String.class, null);
		requestsTable.addContainerProperty(	"Bemerkung für Betreuer",
											String.class,
											null);
	}

	/**
	 * Adds items to the table.
	 */
	private void addTableItems() {
		// TODO Decide in which form this method receives the table items and
		// add
		// these items to the table. Maybe a SQL container might be a good
		// solution.
		// currentRequestTable.setContainerDataSource(container);
	}

}
