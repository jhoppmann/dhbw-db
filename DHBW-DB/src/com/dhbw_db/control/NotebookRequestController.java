/**
 * NewRequestController.java Created by jhoppmann on Mar 14, 2013
 */
package com.dhbw_db.control;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dhbw_db.model.beans.Notebook.NotebookCategory;

/**
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class NotebookRequestController {

	/**
	 * This will return the notebook categories.
	 * 
	 * @return A List with NotebookCategories.
	 */
	public List<NotebookCategory> getNotebooks() {
		return Arrays.asList(NotebookCategory.values());
	}

	/**
	 * This will get a Map of the available operating systems.
	 * 
	 * @return A Map with a string representations of operating systems as keys
	 *         and their unique IDs as values
	 */
	public Map<Integer, String> getOperatingSystems() {
		HashMap<Integer, String> operatingSystems = new HashMap<Integer, String>();

		// fill in some dummy data
		operatingSystems.put(1, "Windows 3.11");
		operatingSystems.put(2, "Windows 7");
		operatingSystems.put(3, "Windows 8");
		operatingSystems.put(4, "MacOS");
		operatingSystems.put(5, "Kubuntu");
		return operatingSystems;
	}

	/**
	 * This will get a Map of the available approvers.
	 * 
	 * @return A Map with a string representations of approvers as keys and
	 *         their unique IDs as values
	 */
	public Map<Integer, String> getApprovers() {
		HashMap<Integer, String> approvers = new HashMap<Integer, String>();

		// fill in some dummy data
		approvers.put(1, "Roland 'Zorro' Kuestermann");
		approvers.put(2, "Katja Wengler");
		approvers.put(3, "Stefan 'Fledermausmann' Klink");
		approvers.put(4, "Harald 'Dirty Harry' Haake");
		approvers.put(5, "Bernd August 'Pornomensch' Scheiderbauer");

		return approvers;
	}
}
