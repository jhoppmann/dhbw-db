/**
 * Request.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.model.request;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.dhbw_db.control.MainUI;
import com.dhbw_db.model.exceptions.NotAllowedException;
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

	/**
	 * The possible statuses the request can have
	 * 
	 * @author jhoppmann
	 * @version 0.1
	 * @since 0.1
	 */
	public enum Status {
		OPEN(1, "Open"),
		APPROVED(2, "Approved"),
		RETRACTED(2, "Retracted"),
		REJECTED(3, "Rejected"),
		OVERDUE(5, "Overdue"),
		COMPLETED(6, "Completed"),
		ERROR(7, "Error"),
		CANCELED(8, "Canceled");

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
		public Status getForId(int id) {
			for (Status st : Status.values()) {
				if (id == st.id)
					return st;
			}
			return ERROR;
		}
	};

	public Request(int requesterId, int approverId, int notebookId, Date until,
			Date start) {
		this.created = new Date();
		this.requesterId = requesterId;
		this.approverId = approverId;
		this.until = until;
		this.start = start;

		generateHash();
		setStatus(Status.OPEN);
	}

	/**
	 * Loads a request for a given ID.
	 * 
	 * @param id The requests unique ID.
	 * @return A request object
	 */
	public static Request load(int id) {
		// TODO Let some database magic happen here!
		return null;
	}

	/**
	 * Loads a request identified by its hash.
	 * 
	 * @param hash The requests unique hash.
	 * @return A request object
	 */
	public static Request loadByHash(String hash) {
		// TODO black, dark magic
		return null;
	}

	/**
	 * Persists this request to the database.
	 */
	public void save() {
		// TODO more database magic!
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
				setStatus(Status.OVERDUE);
			}
		}
		if (status == Status.OPEN) {
			if (now.after(until)) {
				setStatus(Status.CANCELED);
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
			((MainUI) (MainUI.getCurrent())).getController()
											.printError(nae.getMessage());
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
			((MainUI) (MainUI.getCurrent())).getController()
											.printError(nae.getMessage());
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
			((MainUI) (MainUI.getCurrent())).getController()
											.printError(nae.getMessage());
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
			((MainUI) (MainUI.getCurrent())).getController()
											.printError(nae.getMessage());
		}
	}

	/**
	 * Tries to complete the request
	 */
	public void complete() {
		// hand call down to the current state
		try {
			currentState.complete();
		} catch (NotAllowedException nae) {
			((MainUI) (MainUI.getCurrent())).getController()
											.printError(nae.getMessage());
		}
	}
}