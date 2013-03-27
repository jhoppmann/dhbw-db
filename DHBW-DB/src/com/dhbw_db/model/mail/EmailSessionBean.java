/**
 * EmailSessionBean.java Created by mwalliser on Mar 11, 2013, IN ARBEIT
 */
package com.dhbw_db.model.mail;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.dhbw_db.control.MainController;
import com.dhbw_db.model.beans.EMail;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.model.io.FileAccess;
import com.dhbw_db.model.io.database.DataAccess;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;
import com.dhbw_db.model.request.Request;
import com.vaadin.ui.UI;

/**
 * EmailSessionBean is responsible for e-mail distribution
 * 
 * @author mwalliser
 * @author Yannic Frank
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */

public class EmailSessionBean {

	/**
	 * e-mails are distributed via dhbw.notebook-verleih@gmx.de pw:
	 * o2,f=+34bK.Ea
	 */

	private static int port = 25;

	private static String host = "mail.gmx.net";

	private static String from = "dhbw.notebook-verleih@gmx.de";

	private static String username = "dhbw.notebook-verleih@gmx.de";

	private static String password = "o2,f=+34bK.Ea";

	/**
	 * Sends a confirmation email to the requesting student
	 * 
	 * @param r The corresponding request object, to which the messages refer to
	 */
	public void sendMailRequestStudent(Request r) {
		try {
			String body = processMailText("RequestStudent", r);
			DataAccess dao = MainController.get()
											.getDataAccess();
			List<String> to = new ArrayList<String>();
			to.add(dao.getUserForID(r.getRequesterId())
						.geteMail());

			MainController.get()
							.execute(new MailCommand(	to,
														"Antragsbestätigung",
														body));
		} catch (IOException e) {
			LoggingService.getInstance()
							.log(e.getMessage(), LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Sends an email with a students request to his/her appropriate lecturer
	 * 
	 * @param r The corresponding request object, to which the messages refer to
	 */
	public void sendMailRequestLecturer(Request r) {
		try {
			String body = processMailText("RequestLecturer", r);
			DataAccess dao = MainController.get()
											.getDataAccess();
			List<String> to = new ArrayList<String>();
			to.add(dao.getUserForID(r.getApproverId())
						.geteMail());

			MainController.get()
							.execute(new MailCommand(	to,
														"Neuer Antrag angelegt",
														body));
		} catch (IOException e) {
			LoggingService.getInstance()
							.log(e.getMessage(), LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Sends an email to the requesting student with notice of success
	 * 
	 * @param r The corresponding request object, to which the messages refer to
	 */
	public void sendMailRequestSuccess(Request r) {
		try {
			String body = processMailText("RequestSuccess", r);
			DataAccess dao = MainController.get()
											.getDataAccess();
			List<String> to = new ArrayList<String>();
			to.add(dao.getUserForID(r.getRequesterId())
						.geteMail());

			MainController.get()
							.execute(new MailCommand(	to,
														"Antrag genehmigt",
														body));
		} catch (IOException e) {
			LoggingService.getInstance()
							.log(e.getMessage(), LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Sends an email to the requesting student with notice of rejection
	 * 
	 * @param r The corresponding request object, to which the messages refer to
	 */
	public void sendMailRequestRejected(Request r) {
		try {
			String body = processMailText("RequestRejected", r);
			DataAccess dao = MainController.get()
											.getDataAccess();
			List<String> to = new ArrayList<String>();
			to.add(dao.getUserForID(r.getRequesterId())
						.geteMail());

			MainController.get()
							.execute(new MailCommand(	to,
														"Antrag abgelehnt",
														body));
		} catch (IOException e) {
			LoggingService.getInstance()
							.log(e.getMessage(), LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Sends an email to the administrators with information of the approved
	 * request
	 * 
	 * @param r The corresponding request object, to which the messages refer to
	 */
	public void sendMailAdminInfo(Request r) {
		try {
			String body = processMailText("AdminInfo", r);
			DataAccess dao = MainController.get()
											.getDataAccess();
			List<String> to = new ArrayList<String>();
			for (User u : dao.getAdmins()) {
				to.add(u.geteMail());
			}

			MainController.get()
							.execute(new MailCommand(	to,
														"Notebookausleihe genehmigt",
														body));
		} catch (IOException e) {
			LoggingService.getInstance()
							.log(e.getMessage(), LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Sends an email notification to a student who rented a notebook which is
	 * overdue
	 * 
	 * @param r The corresponding request object, to which the messages refer to
	 */
	public void sendMailOverdueWarning(Request r) {
		try {
			String body = processMailText("OverdueWarning", r);
			DataAccess dao = MainController.get()
											.getDataAccess();
			List<String> to = new ArrayList<String>();
			to.add(dao.getUserForID(r.getRequesterId())
						.geteMail());

			MainController.get()
							.execute(new MailCommand(	to,
														"Rückgabe überfällig!",
														body));
		} catch (IOException e) {
			LoggingService.getInstance()
							.log(e.getMessage(), LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Sends a confirmation email to the requesting student who cancelled
	 * his/her request
	 * 
	 * @param r The corresponding request object, to which the messages refer to
	 */
	public void sendMailRequestCancelledStudent(Request r) {
		try {
			String body = processMailText("RequestCancelledStudent", r);
			DataAccess dao = MainController.get()
											.getDataAccess();
			List<String> to = new ArrayList<String>();
			to.add(dao.getUserForID(r.getRequesterId())
						.geteMail());

			MainController.get()
							.execute(new MailCommand(	to,
														"Antrag zurückgezogen",
														body));
		} catch (IOException e) {
			LoggingService.getInstance()
							.log(e.getMessage(), LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Sends an email to the appropriate lecturer of the requesting student who
	 * cancelled his/her request
	 * 
	 * @param r The corresponding request object, to which the messages refer to
	 */
	public void sendMailRequestCancelledLecturer(Request r) {
		try {
			String body = processMailText("RequestCancelledLecturer", r);
			DataAccess dao = MainController.get()
											.getDataAccess();
			List<String> to = new ArrayList<String>();
			to.add(dao.getUserForID(r.getApproverId())
						.geteMail());

			MainController.get()
							.execute(new MailCommand(	to,
														"Antrag zurückgezogen",
														body));
		} catch (IOException e) {
			LoggingService.getInstance()
							.log(e.getMessage(), LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Sends an email to the requester if an admin cancelled his request.
	 * 
	 * @param r The corresponding request object, to which the messages refer to
	 */
	public void sendMailRequestCancelled(Request r) {
		try {
			String body = processMailText("RequestCancelledByAdmin", r);
			DataAccess dao = MainController.get()
											.getDataAccess();
			List<String> to = new ArrayList<String>();
			to.add(dao.getUserForID(r.getRequesterId())
						.geteMail());

			MainController.get()
							.execute(new MailCommand(	to,
														"Antrag abgebrochen",
														body));
		} catch (IOException e) {
			LoggingService.getInstance()
							.log(e.getMessage(), LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * This method handles the actual sending of a message.
	 * 
	 * @param to The mail address the mail should be sent
	 * @param subject The mail subject
	 * @param body The mail body in HTML
	 * 
	 * @throws MessagingException
	 */
	private static void sendHTMLMail(List<String> to, String subject,
			String body) throws MessagingException {

		Authenticator auth = new Authenticator() {
			private PasswordAuthentication pa = new PasswordAuthentication(	username,
																			password);

			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return pa;
			}
		};

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, auth);

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(from));

		for (String address : to)
			msg.addRecipient(	Message.RecipientType.TO,
								new InternetAddress(address));

		msg.setSubject(subject);
		msg.setContent(body, "text/html");

		Transport.send(msg);

		LoggingService.getInstance()
						.log("Message to " + to + " sent.", LogLevel.INFO);

		EMail sent = new EMail();
		sent.setBody(body);
		sent.setDate(new Date());
		sent.setHeader("");
		sent.setSenderMail(from);
		String receiver = new String();
		for (String s : to) {
			receiver += s + ", ";
		}
		receiver = receiver.substring(0, receiver.length() - 2);
		sent.setReceiverMail(receiver);

		MainController.get()
						.getDataAccess()
						.insertEMail(sent);

	}

	private static String processMailText(String filename, Request rq)
			throws IOException {
		String mailtext = (new FileAccess()).loadMailText(filename);

		DataAccess dao = MainController.get()
										.getDataAccess();

		String urlBase = UI.getCurrent()
							.getPage()
							.getLocation()
							.toString();

		// start replacements
		User requester = dao.getUserForID(rq.getRequesterId());
		User approver = dao.getUserForID(rq.getApproverId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mailtext = mailtext.replaceAll("\\$requester", requester.fullName());

		mailtext = mailtext.replaceAll(	"\\$matrNr",
										Integer.toString(requester.getMatrNr()));

		mailtext = mailtext.replaceAll("\\$lecturer", approver.fullName());

		mailtext = mailtext.replaceAll(	"\\$request.description",
										rq.getDescription());

		mailtext = mailtext.replaceAll(	"\\$request.start",
										sdf.format(rq.getStart()));

		mailtext = mailtext.replaceAll(	"\\$request.end",
										sdf.format(rq.getUntil()));

		mailtext = mailtext.replaceAll(	"\\$request.created",
										sdf.format(rq.getCreated()));

		mailtext = mailtext.replaceAll(	"\\$request.os",
										dao.getOSForID(rq.getOs()));

		mailtext = mailtext.replaceAll(	"\\$url",
										urlBase + "?hash=" + rq.getHash());

		return mailtext;
	}

	private class MailCommand implements Callable<String> {

		private List<String> to;

		private String subject;

		private String body;

		public MailCommand(List<String> to, String subject, String body) {
			this.to = to;
			this.subject = subject;
			this.body = body;
		}

		@Override
		public String call() throws Exception {
			try {
				sendHTMLMail(to, subject, body);
			} catch (MessagingException e) {
				e.printStackTrace();
				LoggingService.getInstance()
								.log(e.getMessage(), LogLevel.ERROR);
			}
			return "Message sent";
		}

	}
}
