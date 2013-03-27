/**
 * RequestTable.java Created by jhoppmann on Mar 23, 2013
 */
package com.dhbw_db.view.request;

import java.text.SimpleDateFormat;
import java.util.List;

import com.dhbw_db.control.MainController;
import com.dhbw_db.model.io.database.DataAccess;
import com.dhbw_db.model.request.Request;
import com.dhbw_db.view.DetailsPage;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

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
	public RequestTable(List<Request> requests) {

		VerticalLayout lo = new VerticalLayout();
		if (requests != null && !requests.isEmpty()) {
			dao = MainController.get()
								.getDataAccess();
			requestsTable = new Table();
			requestsTable.setCaption("Alle bisherigen Leihvorgänge");
			requestsTable.setMultiSelect(false);
			requestsTable.setSelectable(true);
			defineTableColumns();
			addItems(requests);
			lo.addComponent(requestsTable);

			Button show = new Button("Anzeigen");
			show.addClickListener(new ClickListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					if (requestsTable.getValue() != null) {
						Request request = dao.getRequestForID((Integer) requestsTable.getValue());
						MainController.get()
										.changeView(new DetailsPage(request));
					}

				}
			});
			lo.addComponent(show);
		} else {
			Label noRequests = new Label("Keine Anträge für diese Ansicht vorhanden!");
			noRequests.setStyleName("headline");
			lo.addComponent(noRequests);
		}

		setCompositionRoot(lo);
	}

	private void defineTableColumns() {
		requestsTable.addContainerProperty("Notebook", String.class, null);
		requestsTable.addContainerProperty("Antragsteller", String.class, null);
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
	}

	private void addItems(List<Request> requests) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (Request r : requests) {
			requestsTable.addItem(	new Object[] {
											dao.getNotebookForID(r.getNotebookId())
												.getName(),
											dao.getUserForID(r.getRequesterId())
												.fullName(),
											dao.getUserForID(r.getApproverId())
												.getLastName(),
											sdf.format(r.getCreated()),
											sdf.format(r.getStart()),
											sdf.format(r.getUntil()),
											(r.getEnd() == null ? "Noch nicht zurückgegeben"
													: sdf.format(r.getEnd())),
											r.getStatus()
												.toString() },
									new Integer(r.getId()));
		}
	}
}
