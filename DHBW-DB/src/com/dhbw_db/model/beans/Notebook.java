/**
 * Notebook.java Created by Florian Hauck on 27.02.2013
 */
package com.dhbw_db.model.beans;

/**
 * @author Florian Hauck
 * @version 0.1
 * @since 0.1
 */
public class Notebook {

	public enum NotebookCategory {
		SHORT("Kurz (eine Woche)"),
		MEDIUM("Mittelfristig (ein Monat)"),
		LONG("Langfristig (drei Monate)");

		private String text;

		public String getText() {
			return text;
		}

		private NotebookCategory(String text) {
			this.text = text;
		}
	}

	private int iD;

	private String name;

	private boolean isDefective;

	private boolean isAvailable;

	/**
	 * @return the iD
	 */
	public int getiD() {
		return iD;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setiD(int iD) {
		this.iD = iD;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the isDefective
	 */
	public boolean isDefective() {
		return isDefective;
	}

	/**
	 * @param isDefective the isDefective to set
	 */
	public void setDefective(boolean isDefective) {
		this.isDefective = isDefective;
	}

	/**
	 * @return the isAvailable
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * @param isAvailable the isAvailable to set
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String toString() {
		return this.name + (isAvailable ? "" : " (ausgeliehen)")
				+ (isDefective ? " (defective)" : "");
	}

}
