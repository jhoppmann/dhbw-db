/**
 * UnitTestMW.java Created by Moritz Walliser on 24.03.2013
 */

package com.dhbw_db.test;

import java.util.Date;

import javax.mail.MessagingException;

import com.dhbw_db.model.mail.EmailSessionBean;
import com.dhbw_db.model.request.Request;

/**
 * @author Moritz Walliser
 * @version 0.1
 * @since
 */
public class UnitTestMW {

	public static void main(String args[]) throws MessagingException {

		int requesterId = 0;
		int approverId = 0;
		int notebookId = 0;
		Date until = new Date();
		Date start = new Date();

		EmailSessionBean a = new EmailSessionBean();
		Request r = new Request(requesterId,
								approverId);
		a.sendMailRequestStudent(r);

	}
}
