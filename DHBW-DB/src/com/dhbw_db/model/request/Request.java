/**
 * Request.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.model.request;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.dhbw_db.control.MainController;
import com.dhbw_db.model.beans.Notebook;
import com.dhbw_db.model.beans.Notebook.NotebookCategory;
import com.dhbw_db.model.exceptions.NotAllowedException;
import com.dhbw_db.model.io.database.DataAccess;
import com.dhbw_db.model.io.database.MySQLAccess;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;
import com.dhbw_db.model.mail.EmailSessionBean;
import com.dhbw_db.model.request.states.ApprovedState;
import com.dhbw_db.model.request.states.CanceledState;
import com.dhbw_db.model.request.states.CompletedState;
import com.dhbw_db.model.request.states.ErrorState;
import com.dhbw_db.model.request.states.OpenState;
import com.dhbw_db.model.request.states.OverdueState;
import com.dhbw_db.model.request.states.RejectedState;
import com.dhbw_db.model.request.states.RequestState;
import com.dhbw_db.model.request.states.RetractedState;
import com.google.gag.annotation.enforceable.CantTouchThis;
import com.google.gag.enumeration.Stop;

/**
 * The Request represents one request for a notebook rental. It handles all
 * transition between its states internally.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class Request {

	private RequestState currentState;

	private Status status;

	private int id;

	private int requesterId;

	private int approverId;

	private int notebookId;

	private Date created;

	private Date start;

	private Date end;

	private Date until;

	private String hash;

	private int os;

	private String description;

	private NotebookCategory category;

	/**
	 * The possible statuses the request can have
	 * 
	 * @author jhoppmann
	 * @version 0.1
	 * @since 0.1
	 */
	public enum Status {
		OPEN(1, "Offen"),
		APPROVED(2, "Genehmigt"),
		RETRACTED(3, "Zurückgezogen"),
		REJECTED(4, "Abgelehnt"),
		OVERDUE(5, "Überfällig"),
		COMPLETED(6, "Beendet"),
		ERROR(7, "Fehler"),
		CANCELED(8, "Abgebrochen");

		private int id;

		private String text;

		public String toString() {
			return text;
		}

		public int getId() {
			return id;
		}

		private Status(int id, String text) {
			this.id = id;
			this.text = text;
		}

		/**
		 * Returns the corresponding status to a given status id. If none fits,
		 * ERROR is returned by default.
		 * 
		 * @return The corresponding status, or ERROR, if none is found
		 */
		public static Status getForId(int id) {
			for (Status st : Status.values()) {
				if (id == st.id)
					return st;
			}
			return ERROR;
		}
	};

	public Request(int requesterId, int approverId) {
		this.created = new Date();
		this.end = null;
		this.requesterId = requesterId;
		this.approverId = approverId;

		generateHash();
		setStatus(Status.OPEN);
	}

	/**
	 * @param currentState
	 * @param status
	 * @param id
	 * @param requesterId
	 * @param approverId
	 * @param notebookId
	 * @param created
	 * @param start
	 * @param end
	 * @param until
	 * @param hash
	 * @param os
	 */
	public Request(int id, int requesterId, int approverId, int notebookId,
			Date created, Date start, Date end, Date until, String hash,
			int statusID, int os, String description, NotebookCategory category) {
		super();

		this.id = id;
		this.requesterId = requesterId;
		this.approverId = approverId;
		this.notebookId = notebookId;
		this.created = created;
		this.start = start;
		this.end = end;
		this.until = until;
		this.hash = hash;

		setStatus(Status.getForId(statusID));

		this.os = os;
		this.description = description;
		this.category = category;
	}

	/**
	 * This method creates a unique 32-digit hash
	 */
	@CantTouchThis(Stop.HAMMERTIME)
	private void generateHash() {
		// if this method is changed, it will break backward compatibility (see
		// annotation)
		hash = DigestUtils.md5Hex(created.toString() + requesterId + approverId);
	}

	/**
	 * Checks if the maximum rental duration is reached and marks the request as
	 * overdue, or if the request has become obsolete and auto-cancels it.
	 */
	public void checkTime() {
		Date now = new Date();
		if (status == Status.APPROVED) {
			if (now.after(until)) {
				moveToStatus(Status.OVERDUE);

				(new EmailSessionBean()).sendMailOverdueWarning(this);
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		cal.add(Calendar.WEEK_OF_YEAR, 1);

		if (status == Status.OPEN) {
			if (now.after(until) || now.after(cal.getTime())) {
				moveToStatus(Status.CANCELED);
			}
		}
	}

	/**
	 * This method handles status transitions.
	 * 
	 * @param s The status to which this request object should be transitioned
	 */
	public void setStatus(Status s) {
		this.status = s;
		switch (s) {
			case OPEN:
				currentState = new OpenState(this);
				break;
			case REJECTED:
				currentState = new RejectedState(this);
				break;
			case RETRACTED:
				currentState = new RetractedState(this);
				break;
			case APPROVED:
				currentState = new ApprovedState(this);
				break;
			case OVERDUE:
				currentState = new OverdueState(this);
				break;
			case COMPLETED:
				currentState = new CompletedState(this);
				break;
			case CANCELED:
				currentState = new CanceledState(this);
				break;
			case ERROR:
			default:
				currentState = new ErrorState(this);
				break;

		}
	}

	/**
	 * This moves the request from its current status to a new one and logs the
	 * transition.
	 * 
	 * @param s The new status
	 */
	public void moveToStatus(Status s) {
		setStatus(s);
		LoggingService.getInstance()
						.log(	"Request " + getId() + " moved to state "
										+ s.name(),
								LogLevel.INFO);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the requesterId
	 */
	public int getRequesterId() {
		return requesterId;
	}

	/**
	 * @param requesterId the requesterId to set
	 */
	public void setRequesterId(int requesterId) {
		this.requesterId = requesterId;
	}

	/**
	 * @return the notebookId
	 */
	public int getNotebookId() {
		return notebookId;
	}

	/**
	 * @param notebookId the notebookId to set
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId = notebookId;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * @return the approverId
	 */
	public int getApproverId() {
		return approverId;
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * 
	 * @return the current status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Tries to retract the request
	 */
	public void retract() {
		// hand call down to the current state
		try {
			currentState.retract();
		} catch (NotAllowedException nae) {
			MainController.get()
							.printError(nae.getMessage());
			LoggingService.getInstance()
							.log(nae.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * Tries to cancel the request
	 */
	public void cancel() {
		// hand call down to the current state
		try {
			currentState.cancel();
		} catch (NotAllowedException nae) {
			MainController.get()
							.printError(nae.getMessage());
			LoggingService.getInstance()
							.log(nae.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * Tries to approve the request
	 */
	public void approve() {
		// hand call down to the current state
		try {
			currentState.approve();
		} catch (NotAllowedException nae) {
			MainController.get()
							.printError(nae.getMessage());
			LoggingService.getInstance()
							.log(nae.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * Tries to reject the request
	 */
	public void reject() {
		// hand call down to the current state
		try {
			currentState.reject();
		} catch (NotAllowedException nae) {
			MainController.get()
							.printError(nae.getMessage());
			LoggingService.getInstance()
							.log(nae.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * Tries to complete the request
	 */
	public void complete() {
		// hand call down to the current state
		try {
			currentState.complete();
			this.end = new Date();
		} catch (NotAllowedException nae) {
			MainController.get()
							.printError(nae.getMessage());
			LoggingService.getInstance()
							.log(nae.getMessage(), LogLevel.ERROR);
		}
	}

	/**
	 * @return the operatingSystem
	 */
	public int getOs() {
		return os;
	}

	/**
	 * @param operatingSystem the operatingSystem to set
	 */
	public void setOs(int os) {
		this.os = os;
	}

	/**
	 * @return the until
	 */
	public Date getUntil() {
		return until;
	}

	/**
	 * @param until the until to set
	 */
	public void setUntil(Date until) {
		this.until = until;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the category
	 */
	public NotebookCategory getCategory() {
		return category;
	}

	/**
	 * @param c the category to set
	 */
	public void setCategory(NotebookCategory c) {
		this.category = c;
	}

	/**
	 * This method is called to start a request. It will then perform all
	 * necessary operations to set it into an initial state.
	 */
	public void start() {

		DataAccess dao = MainController.get()
										.getDataAccess();

		List<Notebook> notebooks = dao.getNotebooks();

		for (Notebook nb : notebooks) {
			if (nb.getiD() == this.getNotebookId()) {
				nb.setAvailable(false);
				dao.updateNotebook(nb);
			}
		}

		dao.updateNotebookCount(this.getCategory()
									.toString(), -1);

		this.status = Status.OPEN;
		(new EmailSessionBean()).sendMailRequestStudent(this);
		(new EmailSessionBean()).sendMailRequestLecturer(this);
	}

	/**
	 * Frees all resources after the request can no longer proceed
	 */
	public void freeResources() {
		DataAccess dao = new MySQLAccess();

		Notebook nb = dao.getNotebookForID(notebookId);
		nb.setAvailable(true);

		dao.updateNotebookCount(this.getCategory()
									.toString(), 1);

		LoggingService.getInstance()
						.log(	"Freed Notebook " + nb.getName()
										+ " in category "
										+ getCategory().getText() + ".",
								LogLevel.DEBUG);
	}
}
