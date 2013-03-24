/**
 * RequestTable.java Created by jhoppmann on Mar 23, 2013
 */
package com.dhbw_db.view.request;

import java.text.SimpleDateFormat;
import java.util.List;

import com.dhbw_db.control.MainController;
import com.dhbw_db.model.io.database.DataAccess;
import com.dhbw_db.model.request.Request;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;

/**
 * The RequestTable class is a component that takes a <tt>List</tt> of Requests
 * and displays them.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class RequestTable extends CustomComponent {

	private static final long serialVersionUID = 3077903416900652649L;

	private Table requestsTable;

	private DataAccess dao;

	/**
	 * This constructor constructs a table of given width for the List of
	 * requests
	 * 
	 * @param requests The List of requests to be displayed.
	 * @param width The width of the table, in pixels
	 */
	public RequestTable(List<Request> requests, int width) {
		dao = MainController.get()
							.getDataAccess();
		HorizontalLayout lo = new HorizontalLayout();
		requestsTable = new Table();
		requestsTable.setCaption("Alle bisherigen Leihvorgänge");
		requestsTable.setWidth(width + "px");
		defineTableColumns();
		addItems(requests);
		lo.addComponent(requestsTable);

		setCompositionRoot(lo);
	}

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

		// TODO Change visible Columns according to user authorizations
	}

	private void addItems(List<Request> requests) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (Request r : requests) {
			requestsTable.addItem(	new Object[] {
											new Integer(r.getId()),
											Integer.toString(r.getNotebookId()),
											Integer.toString(r.getOs()),
											dao.getUserForID(r.getApproverId())
												.getLastName(),
											sdf.format(r.getCreated()),
											sdf.format(r.getStart()),
											sdf.format(r.getEnd()),
											sdf.format(r.getEnd()),
											r.getStatus()
												.toString(), r.getDescription() },
									new Integer(r.getId()));
		}
	}

}
