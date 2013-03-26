/**
 * NewRequestController.java Created by jhoppmann on Mar 14, 2013
 */
package com.dhbw_db.control;

import java.util.List;
import java.util.Map;

import com.dhbw_db.model.beans.Notebook.NotebookCategory;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.model.io.database.DataAccess;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;
import com.dhbw_db.model.mail.EmailSessionBean;
import com.dhbw_db.model.request.Request;
import com.dhbw_db.view.PostButtonPage;
import com.dhbw_db.view.student.NotebookRequest;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class NotebookRequestController implements ClickListener {

	private static final long serialVersionUID = 1L;

	private DataAccess dao;

	private NotebookRequest controlledView;

	public NotebookRequestController(NotebookRequest controlledView) {
		this.dao = MainController.get()
									.getDataAccess();
		this.controlledView = controlledView;
	}

	/**
	 * This will return the notebook categories.
	 * 
	 * @return A List with NotebookCategories.
	 */
	public Map<NotebookCategory, Integer> getNotebooks() {
		return dao.getNotebookCount();
	}

	/**
	 * This will get a Map of the available operating systems.
	 * 
	 * @return A Map with a string representations of operating systems as keys
	 *         and their unique IDs as values
	 */
	public Map<Integer, String> getOperatingSystems() {
		return dao.getOSs();
	}

	/**
	 * This will get a Map of the available approvers.
	 * 
	 * @return A Map with a string representations of approvers as keys and
	 *         their unique IDs as values
	 */
	public List<User> getApprovers() {
		return dao.getLecturers();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton()
					.getCaption()
					.equals("Zurücksetzen")) {
			controlledView.reset();
		} else if (event.getButton()
						.getCaption()
						.equals("Beantragen")) {
			Request r = controlledView.getRequest();
			String headline = "Antrag erfolgreich angelegt!";
			String subline = "Sie können nun über die Navigationsleiste zur "
					+ "Startseite zurückkehren.";
			try {
				dao.insertRequest(r);
				LoggingService.getInstance()
								.log(	"Request successfully created and "
												+ "persisted",
										LogLevel.INFO);
				(new EmailSessionBean()).sendMailRequestStudent(r);
				(new EmailSessionBean()).sendMailRequestLecturer(r);
			} catch (Exception e) {
				LoggingService.getInstance()
								.log(e.getMessage(), LogLevel.ERROR);
				e.printStackTrace();
				headline = "Antrag nicht angelegt!";
				subline = "Der Antrag konnte leider nicht angelegt werden. "
						+ "Versuchen Sie es erneut. Sollte das Problem "
						+ "weiterhin bestehen, kontaktieren Sie einen "
						+ "Administrator.";
			}
			MainController.get()
							.changeView(new PostButtonPage(	headline,
															subline));

		}

	}
}
