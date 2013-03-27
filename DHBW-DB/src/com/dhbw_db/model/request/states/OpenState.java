/**
 * OpenState.java Created by jhoppmann on Mar 4, 2013
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
public class OpenState implements RequestState {

	private Request rq;

	public OpenState(Request rq) {
		this.rq = rq;
	}

	@Override
	public void retract() throws NotAllowedException {
		rq.moveToStatus(Status.RETRACTED);
		(new EmailSessionBean()).sendMailRequestCancelledLecturer(rq);
		rq.freeResources();
	}

	@Override
	public void cancel() throws NotAllowedException {
		rq.moveToStatus(Status.CANCELED);
		(new EmailSessionBean()).sendMailRequestCancelled(rq);
		rq.freeResources();
	}

	@Override
	public void approve() throws NotAllowedException {
		rq.moveToStatus(Status.APPROVED);
		(new EmailSessionBean()).sendMailRequestSuccess(rq);
	}

	@Override
	public void reject() throws NotAllowedException {
		rq.moveToStatus(Status.REJECTED);
		(new EmailSessionBean()).sendMailRequestRejected(rq);
		rq.freeResources();
	}

	@Override
	public void complete() throws NotAllowedException {
		throw new NotAllowedException("You can't mark a non-approved request as completed. Retract, cancel or reject it instead.");
	}

}
