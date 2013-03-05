/**
 * CompletedState.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.model.request.states;

import com.dhbw_db.model.exceptions.NotAllowedException;
import com.dhbw_db.model.request.Request;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class CompletedState implements RequestState {

	@SuppressWarnings("unused")
	private Request rq;

	public CompletedState(Request rq) {
		this.rq = rq;
	}

	@Override
	public void retract() throws NotAllowedException {
		throw new NotAllowedException("This request has been completed. You can't retract it.");
	}

	@Override
	public void cancel() throws NotAllowedException {
		throw new NotAllowedException("This request has been completed. You can't cancel it.");
	}

	@Override
	public void approve() throws NotAllowedException {
		throw new NotAllowedException("This request has been completed. You can't approve it.");
	}

	@Override
	public void reject() throws NotAllowedException {
		throw new NotAllowedException("This request has been completed. You can't reject it.");
	}

	@Override
	public void complete() throws NotAllowedException {
		throw new NotAllowedException("This request has been completed. You can't complete it twice.");
	}

}
