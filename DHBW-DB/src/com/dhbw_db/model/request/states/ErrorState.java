/**
 * ErrorState.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.model.request.states;

import com.dhbw_db.model.exceptions.NotAllowedException;
import com.dhbw_db.model.mail.EmailSessionBean;
import com.dhbw_db.model.request.Request;
import com.dhbw_db.model.request.Request.Status;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class ErrorState implements RequestState {

	private Request rq;

	public ErrorState(Request rq) {
		this.rq = rq;
	}

	@Override
	public void retract() throws NotAllowedException {
		throw new NotAllowedException("There was an error. No actions are allowed for this request.");
	}

	@Override
	public void cancel() throws NotAllowedException {
		rq.moveToStatus(Status.CANCELED);
		rq.freeResources();
		(new EmailSessionBean()).sendMailRequestCancelled(rq);
	}

	@Override
	public void approve() throws NotAllowedException {
		throw new NotAllowedException("There was an error. No actions are allowed for this request.");
	}

	@Override
	public void reject() throws NotAllowedException {
		throw new NotAllowedException("There was an error. No actions are allowed for this request.");
	}

	@Override
	public void complete() throws NotAllowedException {
		throw new NotAllowedException("There was an error. No actions are allowed for this request.");
	}

}
