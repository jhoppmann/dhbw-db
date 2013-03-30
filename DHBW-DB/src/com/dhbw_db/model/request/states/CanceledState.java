/**
 * CanceledStatus.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.model.request.states;

import com.dhbw_db.model.exceptions.NotAllowedException;
import com.dhbw_db.model.request.Request;

/**
 * This represents the state in which the request is canceled.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class CanceledState implements RequestState {

	@SuppressWarnings("unused")
	private Request rq;

	public CanceledState(Request rq) {
		this.rq = rq;
	}

	@Override
	public void retract() throws NotAllowedException {
		throw new NotAllowedException("This request has been canceled, and therefore no action can be taken.");
	}

	@Override
	public void cancel() throws NotAllowedException {
		throw new NotAllowedException("A request cannot be canceled twice.");
	}

	@Override
	public void approve() throws NotAllowedException {
		throw new NotAllowedException("This request has been canceled, and therefore no action can be taken.");
	}

	@Override
	public void reject() throws NotAllowedException {
		throw new NotAllowedException("This request has been canceled, and therefore no action can be taken.");
	}

	@Override
	public void complete() throws NotAllowedException {
		throw new NotAllowedException("This request has been canceled, and therefore no action can be taken.");
	}

}
