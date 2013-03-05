/**
 * EMail.java Created by Florian Hauck on 01.03.2013
 */
package com.dhbw_db.model.beans;

import java.util.Date;

/**
 * @author Florian Hauck
 * @version 0.1
 * @since 0.1
 */
public class EMail {

	private int iD;

	private String receiverMail;

	private String senderMail;

	private String header;

	private String body;

	private Date date;

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
	 * @return the receiverMail
	 */
	public String getReceiverMail() {
		return receiverMail;
	}

	/**
	 * @param receiverMail the receiverMail to set
	 */
	public void setReceiverMail(String receiverMail) {
		this.receiverMail = receiverMail;
	}

	/**
	 * @return the senderMail
	 */
	public String getSenderMail() {
		return senderMail;
	}

	/**
	 * @param senderMail the senderMail to set
	 */
	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
