/**
 * RejectedState.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.model.request.states;

import com.dhbw_db.model.exceptions.NotAllowedException;
import com.dhbw_db.model.request.Request;

/**
 * This represents the state in which the request has been rejected
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class RejectedState implements RequestState {

	@SuppressWarnings("unused")
	private Request rq;

	public RejectedState(Request rq) {
		this.rq = rq;
	}

	@Override
	public void retract() throws NotAllowedException {
		throw new NotAllowedException("This request has been rejected, and therefore no action can be taken.");
	}

	@Override
	public void cancel() throws NotAllowedException {
		throw new NotAllowedException("This request has been rejected, no cancellation possible.");

	}

	@Override
	public void approve() throws NotAllowedException {
		throw new NotAllowedException("This request has already been rejected, it cannot be approved.");
	}

	@Override
	public void reject() throws NotAllowedException {
		throw new NotAllowedException("This request has been rejected, and therefore no action can be taken.");
	}

	@Override
	public void complete() throws NotAllowedException {
		throw new NotAllowedException("A rejected request can't be completed.");
	}

}
