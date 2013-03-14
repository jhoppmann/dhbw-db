/**
 * NewRequestController.java Created by jhoppmann on Mar 14, 2013
 */
package com.dhbw_db.control;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class NotebookRequestController {

	/**
	 * This will get a Map of the available notebooks.
	 * 
	 * @return A Map with a string representations of notebooks as keys and
	 *         their unique IDs as values
	 */
	public Map<Integer, String> getNotebooks() {
		HashMap<Integer, String> notebooks = new HashMap<Integer, String>();

		// build dummy data here
		notebooks.put(1, "Acer Aspire");
		notebooks.put(2, "MacBook");
		notebooks.put(3, "ThinkPad R500");
		notebooks.put(4, "MacBook");
		notebooks.put(5, "ThinkPad R500");

		return notebooks;
	}

	/**
	 * This will get a Map of the available operating systems.
	 * 
	 * @return A Map with a string representations of operating systems as keys
	 *         and their unique IDs as values
	 */
	public Map<String, Integer> getOperatingSystems() {
		return null;
	}

	/**
	 * This will get a Map of the available approvers.
	 * 
	 * @return A Map with a string representations of approvers as keys and
	 *         their unique IDs as values
	 */
	public Map<String, Integer> getApprovers() {
		return null;
	}
}
