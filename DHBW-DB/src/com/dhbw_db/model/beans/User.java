/**
 * User.java Created by Yannic Frank on 27.02.2013
 */
package com.dhbw_db.model.beans;

/**
 * @author Yannic Frank
 * @version 0.2
 * @since 0.2
 */
public class User {

	int ID;

	int matrNr;

	String firstName;

	String lastName;

	String eMail;

	boolean isStudent;

	boolean isAdmin;

	boolean isLecturer;

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the matrNr
	 */
	public int getMatrNr() {
		return matrNr;
	}

	/**
	 * @param matrNr the matrNr to set
	 */
	public void setMatrNr(int matrNr) {
		this.matrNr = matrNr;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the eMail
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @return the isStudent
	 */
	public boolean isStudent() {
		return isStudent;
	}

	/**
	 * @param isStudent the isStudent to set
	 */
	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the isLecturer
	 */
	public boolean isLecturer() {
		return isLecturer;
	}

	/**
	 * @param isLecturer the isLecturer to set
	 */
	public void setLecturer(boolean isLecturer) {
		this.isLecturer = isLecturer;
	}

	public String toString() {
		return ID + " " + matrNr + " " + firstName + " " + lastName + " "
				+ eMail + " " + isStudent + " " + isAdmin + " " + isLecturer;
	}

}
