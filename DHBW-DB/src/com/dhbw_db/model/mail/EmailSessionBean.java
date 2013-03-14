/**
 * EmailSessionBean.java Created by mwalliser on Mar 11, 2013, IN ARBEIT
 */
package com.dhbw_db.model.mail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.dhbw_db.control.MainController;
import com.dhbw_db.control.MainUI;
import com.dhbw_db.model.request.Request;

/**
 * EmailSessionBean is responsible for e-mail distribution
 * 
 * @author mwalliser
 * @version 0.1
 * @since
 */

@Stateless
@LocalBean
public class EmailSessionBean {

	/**
	 * e-mails are distributed via dhbw.notebook-verleih@gmx.de pw:
	 * o2,f=+34bK.Ea
	 */

	private int port = 465;

	private String host = "mail.gmx.net";

	private String from = "dhbw.notebook-verleih@gmx.de";

	private boolean auth = true;

	private String username = "dhbw.notebook-verleih@gmx.de";

	private String password = "o2,f=+34bK.Ea";

	private Protocol protocol = Protocol.SMTPS;

	private boolean debug = true;

	private MainController mc = ((MainUI) (MainUI.getCurrent())).getController();

	private SimpleDateFormat date = new SimpleDateFormat("dd'.'MM'.'yyyy");

	/*
	 * public void sendMailRequestStudent(String to, String cc, String subject,
	 * String student, String lecturer, String mtkNumber, String startDate,
	 * String endDate, String os, String comment, String cancelLink) throws
	 * MessagingException {
	 * 
	 * Multipart multipart = new MimeMultipart("message"); MimeBodyPart textPart
	 * = new MimeBodyPart(); String textContent = "Hallo " + student + ",\n\n" +
	 * "Sie haben einen Antrag zum Ausleihen eines Notebooks gestellt." +
	 * "Ihrem zuständigen Dozenten " + lecturer +
	 * " wurde der Antrag zur Bearbeitung zugeschickt." +
	 * "Sie erhalten eine Nachricht, wenn der Antrag bearbeitet wurde.";
	 * 
	 * textPart.setText(textContent);
	 * 
	 * MimeBodyPart htmlPart = new MimeBodyPart(); String htmlContent =
	 * "<html><h1>Ihre Antragsdaten:</h1><table border=\"0\">" +
	 * "<tr><th>Name</th><th>Matrikelnummer</th><th>Zuständiger Dozent</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
	 * + "<tr><td>" + student + "</td><td>" + mtkNumber + "</td><td>" + lecturer
	 * + "</td><td>" + startDate + "</td><td>" + endDate + "</td><td>" + os +
	 * "</td></tr></table><br>" + "<p>Optionale Bemerkung: " + comment + "</p>"
	 * + "<p>Zum Stornieren Ihres Antrags klicken Sie bitte hier: " +
	 * "<a href=\"" + cancelLink + "\"LINK</a></html>";
	 * 
	 * htmlPart.setContent(htmlContent, "text/html");
	 * 
	 * multipart.addBodyPart(textPart); multipart.addBodyPart(htmlPart);
	 * sendEmail(to, cc, subject, multipart); }
	 */
	public void sendMailRequestStudent(String to, String cc, String subject,
			Request r) throws MessagingException {
		Multipart multipart = new MimeMultipart("message");
		MimeBodyPart textPart = new MimeBodyPart();
		String textContent = "Hallo "
				+ mc.getDataAccess()
					.getRequesterNameById(r.getRequesterId())
				+ ",\n\n"
				+ "Sie haben einen Antrag zum Ausleihen eines Notebooks gestellt."
				+ "Ihrem zuständigen Dozenten "
				+ mc.getDataAccess()
					.getApproverNameById(r.getApproverId())
				+ " wurde der Antrag zur Bearbeitung zugeschickt."
				+ "Sie erhalten eine Nachricht, wenn der Antrag bearbeitet wurde.";

		textPart.setText(textContent);

		MimeBodyPart htmlPart = new MimeBodyPart();
		String htmlContent = "<html><h1>Ihre Antragsdaten:</h1><table border=\"0\">"
				+ "<tr><th>Name</th><th>Matrikelnummer</th><th>Zuständiger Dozent</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
				+ "<tr><td>"
				+ mc.getDataAccess()
					.getRequesterNameById(r.getRequesterId())
				+ "</td><td>"
				+ mc.getDataAccess()
					.getMatrNrById(r.getRequesterId())
				+ "</td><td>"
				+ mc.getDataAccess()
					.getApproverNameById(r.getApproverId())
				+ "</td><td>"
				+ date.format(r.getStart())
						.toString()
				+ "</td><td>"
				+ date.format(r.getEnd())
						.toString()
				+ "</td><td>"
				+ String.valueOf(r.getOs())
				+ "</td></tr></table><br>"
				+ "<p>Optionale Bemerkung: "
				+ r.getComment()
				+ "</p>"
				+ "<p>Zum Stornieren Ihres Antrags klicken Sie bitte hier: "
				+ "<a href=\"" + "cancelLink" + "\"LINK</a></html>";

		htmlPart.setContent(htmlContent, "text/html");

		multipart.addBodyPart(textPart);
		multipart.addBodyPart(htmlPart);
		sendEmail(to, cc, subject, multipart);
	}

	public void sendMailRequestLecturer(String to, String cc, String subject,
			Request r) throws MessagingException {
		Multipart multipart = new MimeMultipart("message");
		MimeBodyPart textPart = new MimeBodyPart();
		String textContent = "Hallo "
				+ mc.getDataAccess()
					.getApproverNameById(r.getApproverId()) + ",\n\n"
				+ mc.getDataAccess()
					.getRequesterNameById(r.getRequesterId())
				+ " hat einen Antrag zum Ausleihen eines Notebooks gestellt.";

		textPart.setText(textContent);

		MimeBodyPart htmlPart = new MimeBodyPart();
		String htmlContent = "<html><h1>Antragsdaten:</h1><table border=\"0\">"
				+ "<tr><th>Name</th><th>Matrikelnummer</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
				+ "<tr><td>"
				+ mc.getDataAccess()
					.getRequesterNameById(r.getRequesterId())
				+ "</td><td>"
				+ mc.getDataAccess()
					.getMatrNrById(r.getRequesterId())
				+ "</td><td>"
				+ mc.getDataAccess()
					.getApproverNameById(r.getApproverId())
				+ "</td><td>"
				+ date.format(r.getStart())
						.toString()
				+ "</td><td>"
				+ date.format(r.getEnd())
						.toString()
				+ "</td><td>"
				+ String.valueOf(r.getOs())
				+ "</td></tr></table><br>"
				+ "<p>Optionale Bemerkung: "
				+ r.getComment()
				+ "</p>"
				+ "<p>Zum Genehmigen oder Ablehnen dieses Antrags klicken Sie bitte hier: "
				+ "<a href=\"" + "processLink" + "\"LINK</a></html>";

		htmlPart.setContent(htmlContent, "text/html");

		multipart.addBodyPart(textPart);
		multipart.addBodyPart(htmlPart);
		sendEmail(to, cc, subject, multipart);
	}

	public void sendMailRequestSuccess(String to, String cc, String subject,
			Request r) throws MessagingException {
		Multipart multipart = new MimeMultipart("message");
		MimeBodyPart textPart = new MimeBodyPart();
		String textContent = "Hallo "
				+ mc.getDataAccess()
					.getRequesterNameById(r.getRequesterId())
				+ ",\n\n"
				+ "Sie haben einen Antrag zum Ausleihen eines Notebooks gestellt."
				+ "Ihr zuständiger Dozent "
				+ mc.getDataAccess()
					.getApproverNameById(r.getApproverId())
				+ " hat den Antrag genehmigt." + "Sie können das Notebook am "
				+ r.getStart()
					.toString() + " zur Ausleihe abholen.";

		textPart.setText(textContent);

		MimeBodyPart htmlPart = new MimeBodyPart();
		String htmlContent = "<html><h1>Ihre Antragsdaten:</h1><table border=\"0\">"
				+ "<tr><th>Name</th><th>Matrikelnummer</th><th>Zuständiger Dozent</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
				+ "<tr><td>"
				+ mc.getDataAccess()
					.getRequesterNameById(r.getRequesterId())
				+ "</td><td>"
				+ mc.getDataAccess()
					.getMatrNrById(r.getRequesterId())
				+ "</td><td>"
				+ mc.getDataAccess()
					.getApproverNameById(r.getApproverId())
				+ "</td><td>"
				+ date.format(r.getStart())
						.toString()
				+ "</td><td>"
				+ date.format(r.getEnd())
						.toString()
				+ "</td><td>"
				+ String.valueOf(r.getOs())
				+ "</td></tr></table><br>"
				+ "<p>Optionale Bemerkung: "
				+ r.getComment()
				+ "</p>"
				+ "<p>Zum Stornieren Ihres Antrags klicken Sie bitte hier: "
				+ "<a href=\"" + "cancelLink" + "\"LINK</a></html>";

		htmlPart.setContent(htmlContent, "text/html");

		multipart.addBodyPart(textPart);
		multipart.addBodyPart(htmlPart);
		sendEmail(to, cc, subject, multipart);
	}

	public void sendMailRequestRejected(String to, String cc, String subject,
			Request r) throws MessagingException {
		Multipart multipart = new MimeMultipart("message");
		MimeBodyPart textPart = new MimeBodyPart();
		String textContent = "Hallo "
				+ mc.getDataAccess()
					.getRequesterNameById(r.getRequesterId())
				+ ",\n\n"
				+ "Sie haben einen Antrag zum Ausleihen eines Notebooks gestellt."
				+ "Ihr zuständiger Dozent "
				+ mc.getDataAccess()
					.getApproverNameById(r.getApproverId())
				+ " hat den Antrag abgelehnt.";

		textPart.setText(textContent);

		MimeBodyPart htmlPart = new MimeBodyPart();
		String htmlContent = "<html><h1>Ihre Antragsdaten:</h1><table border=\"0\">"
				+ "<tr><th>Name</th><th>Matrikelnummer</th><th>Zuständiger Dozent</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
				+ "<tr><td>"
				+ mc.getDataAccess()
					.getRequesterNameById(r.getRequesterId())
				+ "</td><td>"
				+ mc.getDataAccess()
					.getMatrNrById(r.getRequesterId())
				+ "</td><td>"
				+ mc.getDataAccess()
					.getApproverNameById(r.getApproverId())
				+ "</td><td>"
				+ date.format(r.getStart())
						.toString()
				+ "</td><td>"
				+ date.format(r.getEnd())
						.toString()
				+ "</td><td>"
				+ String.valueOf(r.getOs())
				+ "</td></tr></table><br>"
				+ "<p>Optionale Bemerkung: "
				+ r.getComment()
				+ "</p>"
				+ "<p>Zum Stornieren Ihres Antrags klicken Sie bitte hier: "
				+ "<a href=\"" + "cancelLink" + "\"LINK</a></html>";

		htmlPart.setContent(htmlContent, "text/html");

		multipart.addBodyPart(textPart);
		multipart.addBodyPart(htmlPart);
		sendEmail(to, cc, subject, multipart);
	}

	public void sendMailAdminInfo() {

	}

	public void sendMailOverdueWarning() {

	}

	public void sendMailRequestCancelledStudent() {

	}

	public void sendMailRequestCancelledLecturer() {

	}

	/**
	 * 
	 * @param to Specifies the target e-mail adress of recipient
	 * @param cc Specifies the target e-mail adress of recipient in cc
	 * @param subject Specifies the subject of the e-mail message
	 * @param body Specifies the body of the e-mail message
	 */

	@SuppressWarnings("incomplete-switch")
	private void sendEmail(String to, String cc, String subject,
			Multipart contents) {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		switch (protocol) {
			case SMTPS:
			props.put("mail.smtp.ssl.enable", true);
				break;
			case TLS:
			props.put("mail.smtp.starttls.enable", true);
				break;
		}

		Authenticator authenticator = null;
		if (auth) {
			props.put("mail.smtp.auth", true);
			authenticator = new Authenticator() {
				private PasswordAuthentication pa = new PasswordAuthentication(	username,
																				password);

				@Override
				public PasswordAuthentication getPasswordAuthentication() {
					return pa;
				}
			};
		}

		Session session = Session.getInstance(props, authenticator);
		session.setDebug(debug);

		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject(subject);
			message.setSentDate(new Date());
			// message.setText(body);
			message.setContent(contents);
			Transport.send(message);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}

	}

}
