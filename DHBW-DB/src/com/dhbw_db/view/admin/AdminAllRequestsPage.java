/**
 * AdminAllRequestsPage.java Created by Yannic Frank on 15.03.2013
 */
package com.dhbw_db.view.admin;

import com.dhbw_db.control.MainController;
import com.dhbw_db.view.request.RequestTable;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The AdminAllRequestsPage shows all requests stored in the database
 * 
 * @author Yannic Frank
 * @version 0.1
 * @since
 */
public class AdminAllRequestsPage extends CustomComponent {

	private static final long serialVersionUID = 5716916047660854518L;

	private AbsoluteLayout mainLayout;

	private Panel panel;

	private VerticalLayout panelLayout;

	private RequestTable requestsTable;

	private Label headlineLabel;

	/**
	 * The constructor sets the main layout
	 */
	public AdminAllRequestsPage() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	/**
	 * Builds the main Layout
	 * 
	 * @return the mainLayout
	 */
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

	/**
	 * Builds the Panel
	 * 
	 * @return the Panel
	 */
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

	/**
	 * Builds the PanelLayout
	 * 
	 * @return the PanelLayout
	 */
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
		requestsTable = new RequestTable(MainController.get()
														.getDataAccess()
														.getRequests());
		panelLayout.addComponent(requestsTable);

		return panelLayout;
	}
}
