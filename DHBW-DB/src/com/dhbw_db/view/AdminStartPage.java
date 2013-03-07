/**
 * AdminStartPage.java Created by Florian Hauck on 01.03.2013
 */
package com.dhbw_db.view;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * The layout for the admin start page.
 * 
 * @author Florian Hauck
 * @version 0.1
 * @since
 */
@SuppressWarnings("serial")
public class AdminStartPage extends CustomComponent {

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private Panel panel;

	@AutoGenerated
	private AbsoluteLayout panelLayout;

	@AutoGenerated
	private Label headlineLabel;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public AdminStartPage() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO Decide what this view should show.
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
		headlineLabel.setValue("Startseite Admin");
		panelLayout.addComponent(headlineLabel, "top:20.0px;left:20.0px;");

		// TODO Add some components here.

		return panelLayout;
	}

}