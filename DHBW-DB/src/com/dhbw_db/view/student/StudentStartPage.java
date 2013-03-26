/**
 * StudentStartPage.java Created by Florian Hauck on 01.03.2013
 */
package com.dhbw_db.view.student;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;

/**
 * The layout for the student start page. Defines all components and their
 * positions.
 * 
 * @author Florian Hauck
 * @version 0.1
 * @since 0.1
 */
public class StudentStartPage extends CustomComponent {

	private static final long serialVersionUID = -5781154539150826766L;

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private Panel panel;

	@AutoGenerated
	private AbsoluteLayout panelLayout;

	@AutoGenerated
	private Table currentRequestTable;

	@AutoGenerated
	private Label headlineLabel;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then define the table headlines.
	 */
	public StudentStartPage() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		defineTableColumns();
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

		// PanelLayout
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
		headlineLabel.setStyleName("headline");
		headlineLabel.setImmediate(false);
		headlineLabel.setWidth("-1px");
		headlineLabel.setHeight("-1px");
		headlineLabel.setValue("Startseite Student");
		panelLayout.addComponent(headlineLabel, "top:20.0px;left:20.0px;");

		// currentRequestsTable
		currentRequestTable = new Table();
		currentRequestTable.setCaption("Aktuellster Leihantrag");
		defineTableColumns();
		currentRequestTable.setImmediate(false);
		currentRequestTable.setWidth("700px");
		currentRequestTable.setHeight("45px");
		panelLayout.addComponent(currentRequestTable, "top:80.0px;left:20.0px;");

		return panelLayout;
	}

	/**
	 * Defines the headlines of the table columns.
	 */
	private void defineTableColumns() {
		currentRequestTable.addContainerProperty("ID", Integer.class, null);
		currentRequestTable.addContainerProperty("Notebook", String.class, null);
		currentRequestTable.addContainerProperty(	"Betriebssystem",
													String.class,
													null);
		currentRequestTable.addContainerProperty("Betreuer", String.class, null);
		currentRequestTable.addContainerProperty(	"Ausleihdatum",
													String.class,
													null);
		currentRequestTable.addContainerProperty(	"Rückgabedatum",
													String.class,
													null);
		currentRequestTable.addContainerProperty("Status", String.class, null);
	}

}
