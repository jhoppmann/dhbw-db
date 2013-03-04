/**
 * ApprovedState.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.model.request.states;

import com.dhbw_db.model.exceptions.NotAllowedException;
import com.dhbw_db.model.request.Request;
import com.dhbw_db.model.request.Request.Status;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class ApprovedState implements RequestState {

	private Request rq;

	public ApprovedState(Request rq) {
		this.rq = rq;
	}

	@Override
	public void retract() throws NotAllowedException {
		throw new NotAllowedException("The request has already been approved, it cannot be retracted.");
	}

	@Override
	public void cancel() throws NotAllowedException {
		rq.setStatus(Status.CANCELED);
	}

	@Override
	public void approve() throws NotAllowedException {
		throw new NotAllowedException("This request has already been approved.");
	}

	@Override
	public void reject() throws NotAllowedException {
		throw new NotAllowedException("This request has already been approved, it cannot be rejected");
	}

	@Override
	public void complete() throws NotAllowedException {
		rq.setStatus(Status.COMPLETED);
	}

}
