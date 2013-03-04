/**
 * RequestState.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.model.request.states;

import com.dhbw_db.model.exceptions.NotAllowedException;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public interface RequestState {

	/**
	 * Tries to retract the request
	 * 
	 * @throws NotAllowedException If the action is not allowed for this state
	 */
	public void retract() throws NotAllowedException;

	/**
	 * Cancels the request immediately.
	 * 
	 * @throws NotAllowedException If the action is not allowed for this state
	 */
	public void cancel() throws NotAllowedException;

	/**
	 * Approves the request
	 * 
	 * @throws NotAllowedException If the action is not allowed for this state
	 */
	public void approve() throws NotAllowedException;

	/**
	 * Rejects the request
	 * 
	 * @throws NotAllowedException If the action is not allowed for this state
	 */
	public void reject() throws NotAllowedException;

	/**
	 * Marks the request as completed
	 * 
	 * @throws NotAllowedException If the action is not allowed for this state
	 */
	public void complete() throws NotAllowedException;
}
