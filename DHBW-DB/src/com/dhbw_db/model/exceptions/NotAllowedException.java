/**
 * NotAllowedException.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.model.exceptions;

/**
 * This exception will be thrown if an temporarily not allowed action is tried
 * to be taken (in contrast to an UnsupportedOperationException, where the
 * action is never possible)
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class NotAllowedException extends Exception {

	private static final long serialVersionUID = -8971703444542839988L;

	public NotAllowedException(String message) {
		super(message);
	}

}
