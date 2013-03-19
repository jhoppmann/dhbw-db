/**
 * LecturerDetailsPage.java Created by Yannic Frank on 15.03.2013
 */
package com.dhbw_db.view;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Yannic Frank
 * @version 0.1
 * @since
 */
public class LecturerDetailsPage extends CustomComponent {

	private AbsoluteLayout mainLayout;

	private Panel panel;

	private VerticalLayout panelLayout;

	private Table requestsTable;

	private Label headlineLabel;

	private Button approveButton;

	private Button rejectButton;

	private TextArea rejectionReasonTextField;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public LecturerDetailsPage() {
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
		headlineLabel.setValue("Detailansicht");
		panelLayout.addComponent(headlineLabel);

		// detailTable
		requestsTable = new Table();
		requestsTable.setCaption("Ausgwählter Antrag");

		requestsTable.setImmediate(false);
		// requestsTable.setWidth("700px");
		requestsTable.setHeight("45px");
		panelLayout.addComponent(requestsTable);

		// approveButton
		approveButton = new Button();
		approveButton.setCaption("Genehmigen");
		approveButton.setImmediate(false);
		approveButton.setWidth("-1px");
		approveButton.setHeight("-1px");

		// rejectButton
		rejectButton = new Button();
		rejectButton.setCaption("Verweigern");
		rejectButton.setImmediate(false);
		rejectButton.setWidth("-1px");
		rejectButton.setHeight("-1px");

		// rejectionReasonTextfield
		rejectionReasonTextField = new TextArea();
		rejectionReasonTextField.setCaption("Verweigerungsgrund:");
		rejectionReasonTextField.setImmediate(false);
		// rejectionReasonTextField.setWidth("-1px");
		// rejectionReasonTextField.setHeight("-1px");

		HorizontalLayout horizontalLayout1 = new HorizontalLayout();

		horizontalLayout1.addComponent(approveButton);
		horizontalLayout1.addComponent(rejectButton);
		horizontalLayout1.addComponent(rejectionReasonTextField);

		horizontalLayout1.setSpacing(true);

		panelLayout.addComponent(horizontalLayout1);

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
