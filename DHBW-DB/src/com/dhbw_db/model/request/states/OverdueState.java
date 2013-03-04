/**
 * OverdueState.java Created by jhoppmann on Mar 4, 2013
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
public class OverdueState implements RequestState {

	private Request rq;

	public OverdueState(Request rq) {
		this.rq = rq;
	}

	@Override
	public void retract() throws NotAllowedException {
		throw new NotAllowedException("This status is overdue. You can't retract it.");
	}

	@Override
	public void cancel() throws NotAllowedException {
		rq.setStatus(Status.CANCELED);
	}

	@Override
	public void approve() throws NotAllowedException {
		throw new NotAllowedException("This status is overdue. You can't approve it.");
	}

	@Override
	public void reject() throws NotAllowedException {
		throw new NotAllowedException("This status is overdue. You can't approve it.");
	}

	@Override
	public void complete() throws NotAllowedException {
		rq.setStatus(Status.COMPLETED);
	}

}
