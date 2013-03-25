/**
 * EmailSessionBean.java Created by mwalliser on Mar 11, 2013, IN ARBEIT
 */
package com.dhbw_db.model.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * import javax.mail.Message; import javax.mail.MessagingException; import
 * javax.mail.Session; import javax.mail.Transport; import
 * javax.mail.internet.AddressException; import
 * javax.mail.internet.InternetAddress; import javax.mail.internet.MimeMessage;
 */

/**
 * EmailSessionBean is responsible for e-mail distribution
 * 
 * @author mwalliser
 * @version 0.1
 * @since
 */

// @Stateless
// @LocalBean
public class EmailSessionBean {

	/**
	 * e-mails are distributed via dhbw.notebook-verleih@gmx.de pw:
	 * o2,f=+34bK.Ea
	 */

	// private static int port = 465;
	private static int port = 25;

	private static String host = "mail.gmx.net";

	// private static String host = "smtp.gmx.com";

	private static String from = "dhbw.notebook-verleih@gmx.de";

	// private boolean auth = true;

	private static String username = "dhbw.notebook-verleih@gmx.de";

	private static String password = "o2,f=+34bK.Ea";

	private static Protocol protocol = Protocol.SMTPS;

	// private boolean debug = true;

	// private MainController mc = ((MainUI)
	// (MainUI.getCurrent())).getController();

	// private MainController mc = MainController.get();

	// private SimpleDateFormat date = new SimpleDateFormat("dd'.'MM'.'yyyy");

	// /**
	// * Sends a confirmation email to the requesting student
	// *
	// * @param to Specifies the target e-mail adress of recipient
	// * @param cc Specifies the target e-mail adress of recipient in cc
	// * @param subject Specifies the subject of the e-mail message
	// * @param r The corresponding request object, to which the messages refer
	// to
	// * @throws MessagingException
	// */
	//
	// public void sendMailRequestStudent(String to, String cc, String subject,
	// Request r) throws MessagingException {
	// Multipart multipart = new MimeMultipart("message");
	// MimeBodyPart textPart = new MimeBodyPart();
	// String textContent = "Hallo "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + ",\n\n"
	// + "Sie haben einen Antrag zum Ausleihen eines Notebooks gestellt."
	// + "Ihrem zuständigen Dozenten "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName()
	// + " wurde der Antrag zur Bearbeitung zugeschickt."
	// + "Sie erhalten eine Nachricht, wenn der Antrag bearbeitet wurde.";
	//
	// textPart.setText(textContent);
	//
	// MimeBodyPart htmlPart = new MimeBodyPart();
	// String htmlContent =
	// "<html><h1>Ihre Antragsdaten:</h1><table border=\"0\">"
	// +
	// "<tr><th>Name</th><th>Matrikelnummer</th><th>Zuständiger Dozent</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
	// + "<tr><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getMatrNr()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName()
	// + "</td><td>"
	// + date.format(r.getStart())
	// .toString()
	// + "</td><td>"
	// + date.format(r.getEnd())
	// .toString()
	// + "</td><td>"
	// + String.valueOf(r.getOs())
	// + "</td></tr></table><br>"
	// + "<p>Optionale Bemerkung: "
	// + r.getDescription()
	// + "</p>"
	// + "<p>Zum Stornieren Ihres Antrags klicken Sie bitte hier: "
	// + "<a href=\"" + "cancelLink" + "\"LINK</a></html>";
	//
	// htmlPart.setContent(htmlContent, "text/html");
	//
	// multipart.addBodyPart(textPart);
	// multipart.addBodyPart(htmlPart);
	// sendEmail(to, cc, subject, multipart);
	// }
	//
	// /**
	// * Sends an email with a students request to his/her appropriate lecturer
	// *
	// * @param to Specifies the target e-mail adress of recipient
	// * @param cc Specifies the target e-mail adress of recipient in cc
	// * @param subject Specifies the subject of the e-mail message
	// * @param r The corresponding request object, to which the messages refer
	// to
	// */
	//
	// public void sendMailRequestLecturer(String to, String cc, String subject,
	// Request r) throws MessagingException {
	// Multipart multipart = new MimeMultipart("message");
	// MimeBodyPart textPart = new MimeBodyPart();
	// String textContent = "Hallo " + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName() + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName() + ",\n\n"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName() + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + " hat einen Antrag zum Ausleihen eines Notebooks gestellt.";
	//
	// textPart.setText(textContent);
	//
	// MimeBodyPart htmlPart = new MimeBodyPart();
	// String htmlContent = "<html><h1>Antragsdaten:</h1><table border=\"0\">"
	// +
	// "<tr><th>Name</th><th>Matrikelnummer</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
	// + "<tr><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getMatrNr()
	// + "</td><td>"
	// + date.format(r.getStart())
	// .toString()
	// + "</td><td>"
	// + date.format(r.getEnd())
	// .toString()
	// + "</td><td>"
	// + String.valueOf(r.getOs())
	// + "</td></tr></table><br>"
	// + "<p>Optionale Bemerkung: "
	// + r.getDescription()
	// + "</p>"
	// +
	// "<p>Zum Genehmigen oder Ablehnen dieses Antrags klicken Sie bitte hier: "
	// + "<a href=\"" + "processLink" + "\"LINK</a></html>";
	//
	// htmlPart.setContent(htmlContent, "text/html");
	//
	// multipart.addBodyPart(textPart);
	// multipart.addBodyPart(htmlPart);
	// sendEmail(to, cc, subject, multipart);
	// }
	//
	// /**
	// * Sends an email to the requesting student with notice of success
	// *
	// * @param to Specifies the target e-mail adress of recipient
	// * @param cc Specifies the target e-mail adress of recipient in cc
	// * @param subject Specifies the subject of the e-mail message
	// * @param r The corresponding request object, to which the messages refer
	// to
	// * @throws MessagingException
	// */
	//
	// public void sendMailRequestSuccess(String to, String cc, String subject,
	// Request r) throws MessagingException {
	// Multipart multipart = new MimeMultipart("message");
	// MimeBodyPart textPart = new MimeBodyPart();
	// String textContent = "Hallo "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + ",\n\n"
	// + "Sie haben einen Antrag zum Ausleihen eines Notebooks gestellt."
	// + "Ihr zuständiger Dozent "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName() + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName() + " hat den Antrag genehmigt."
	// + "Sie können das Notebook am " + date.format(r.getStart())
	// .toString()
	// + " zur Ausleihe abholen.";
	//
	// textPart.setText(textContent);
	//
	// MimeBodyPart htmlPart = new MimeBodyPart();
	// String htmlContent =
	// "<html><h1>Ihre Antragsdaten:</h1><table border=\"0\">"
	// +
	// "<tr><th>Name</th><th>Matrikelnummer</th><th>Zuständiger Dozent</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
	// + "<tr><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getMatrNr()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName()
	// + "</td><td>"
	// + date.format(r.getStart())
	// .toString()
	// + "</td><td>"
	// + date.format(r.getEnd())
	// .toString()
	// + "</td><td>"
	// + String.valueOf(r.getOs())
	// + "</td></tr></table><br>"
	// + "<p>Optionale Bemerkung: "
	// + r.getDescription()
	// + "</p>"
	// + "<p>Zum Stornieren Ihres Antrags klicken Sie bitte hier: "
	// + "<a href=\"" + "cancelLink" + "\"LINK</a></html>";
	//
	// htmlPart.setContent(htmlContent, "text/html");
	//
	// multipart.addBodyPart(textPart);
	// multipart.addBodyPart(htmlPart);
	// sendEmail(to, cc, subject, multipart);
	// }
	//
	// /**
	// * Sends an email to the requesting student with notice of rejection
	// *
	// * @param to Specifies the target e-mail adress of recipient
	// * @param cc Specifies the target e-mail adress of recipient in cc
	// * @param subject Specifies the subject of the e-mail message
	// * @param r The corresponding request object, to which the messages refer
	// to
	// * @throws MessagingException
	// */
	//
	// public void sendMailRequestRejected(String to, String cc, String subject,
	// Request r) throws MessagingException {
	// Multipart multipart = new MimeMultipart("message");
	// MimeBodyPart textPart = new MimeBodyPart();
	// String textContent = "Hallo "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + ",\n\n"
	// + "Sie haben einen Antrag zum Ausleihen eines Notebooks gestellt."
	// + "Ihr zuständiger Dozent "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName() + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName() + " hat den Antrag abgelehnt.";
	//
	// textPart.setText(textContent);
	//
	// MimeBodyPart htmlPart = new MimeBodyPart();
	// String htmlContent =
	// "<html><h1>Ihre Antragsdaten:</h1><table border=\"0\">"
	// +
	// "<tr><th>Name</th><th>Matrikelnummer</th><th>Zuständiger Dozent</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
	// + "<tr><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getMatrNr()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName()
	// + "</td><td>"
	// + date.format(r.getStart())
	// .toString()
	// + "</td><td>"
	// + date.format(r.getEnd())
	// .toString()
	// + "</td><td>"
	// + String.valueOf(r.getOs())
	// + "</td></tr></table><br>"
	// + "<p>Optionale Bemerkung: "
	// + r.getDescription()
	// + "</p></html>";
	//
	// htmlPart.setContent(htmlContent, "text/html");
	//
	// multipart.addBodyPart(textPart);
	// multipart.addBodyPart(htmlPart);
	// sendEmail(to, cc, subject, multipart);
	// }
	//
	// /**
	// * Sends an email to an administrator with information of the approved
	// * request
	// *
	// * @param to Specifies the target e-mail adress of recipient
	// * @param cc Specifies the target e-mail adress of recipient in cc
	// * @param subject Specifies the subject of the e-mail message
	// * @param r The corresponding request object, to which the messages refer
	// to
	// * @throws MessagingException
	// */
	//
	// public void sendMailAdminInfo(String to, String cc, String subject,
	// Request r) throws MessagingException {
	// Multipart multipart = new MimeMultipart("message");
	// MimeBodyPart textPart = new MimeBodyPart();
	// String textContent = "An die Administratoren des Notebookverleihs: \n\n"
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName()
	// + " hat einen Antrag zum Ausleihen eines Notebooks genehmigt.";
	//
	// textPart.setText(textContent);
	//
	// MimeBodyPart htmlPart = new MimeBodyPart();
	// String htmlContent = "<html><h1>Antragsdaten:</h1><table border=\"0\">"
	// +
	// "<tr><th>Name</th><th>Matrikelnummer</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
	// + "<tr><td>" + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName() + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName() + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getMatrNr() + "</td><td>" + date.format(r.getStart())
	// .toString()
	// + "</td><td>" + date.format(r.getEnd())
	// .toString() + "</td><td>"
	// + String.valueOf(r.getOs()) + "</td></tr></table><br>"
	// + "<p>Optionale Bemerkung: " + r.getDescription()
	// + "</p></html>";
	//
	// htmlPart.setContent(htmlContent, "text/html");
	//
	// multipart.addBodyPart(textPart);
	// multipart.addBodyPart(htmlPart);
	// sendEmail(to, cc, subject, multipart);
	// }
	//
	// /**
	// * Sends an email to a student who lent a notebook which is overdue
	// *
	// * @param to Specifies the target e-mail adress of recipient
	// * @param cc Specifies the target e-mail adress of recipient in cc
	// * @param subject Specifies the subject of the e-mail message
	// * @param r The corresponding request object, to which the messages refer
	// to
	// * @throws MessagingException
	// */
	//
	// public void sendMailOverdueWarning(String to, String cc, String subject,
	// Request r) throws MessagingException {
	// Multipart multipart = new MimeMultipart("message");
	// MimeBodyPart textPart = new MimeBodyPart();
	// String textContent = "Hallo "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + ",\n\n"
	// + "Sie haben am "
	// + date.format(r.getStart())
	// .toString()
	// + " ein Notebook vom Notebookverleih "
	// + "ausgeliehen. Die Leihfrist für das Notebook ist am "
	// + date.format(r.getEnd())
	// .toString()
	// + " abgelaufen.\n"
	// + "Bitte geben Sie das Notebook schnellstmöglich zurück."
	// +
	// "Beim Überschreiten der Leihfrist fallen Mahngebühren an. Die jeweilige Höhe ist der Gebührenordnung zu entnehmen.";
	//
	// textPart.setText(textContent);
	//
	// MimeBodyPart htmlPart = new MimeBodyPart();
	// String htmlContent =
	// "<html><h1>Ihre Antragsdaten:</h1><table border=\"0\">"
	// +
	// "<tr><th>Name</th><th>Matrikelnummer</th><th>Zuständiger Dozent</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
	// + "<tr><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getMatrNr()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName()
	// + "</td><td>"
	// + date.format(r.getStart())
	// .toString()
	// + "</td><td>"
	// + date.format(r.getEnd())
	// .toString()
	// + "</td><td>"
	// + String.valueOf(r.getOs())
	// + "</td></tr></table><br>"
	// + "<p>Optionale Bemerkung: "
	// + r.getDescription()
	// + "</p></html>";
	//
	// htmlPart.setContent(htmlContent, "text/html");
	//
	// multipart.addBodyPart(textPart);
	// multipart.addBodyPart(htmlPart);
	// sendEmail(to, cc, subject, multipart);
	// }
	//
	// /**
	// * Sends a confirmation email to the requesting student who cancelled
	// * his/her request
	// *
	// * @param to Specifies the target e-mail adress of recipient
	// * @param cc Specifies the target e-mail adress of recipient in cc
	// * @param subject Specifies the subject of the e-mail message
	// * @param r The corresponding request object, to which the messages refer
	// to
	// * @throws MessagingException
	// */
	//
	// public void sendMailRequestCancelledStudent(String to, String cc,
	// String subject, Request r) throws MessagingException {
	// Multipart multipart = new MimeMultipart("message");
	// MimeBodyPart textPart = new MimeBodyPart();
	// String textContent = "Hallo "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + ",\n\n"
	// + "Sie haben am "
	// + date.format(r.getCreated())
	// .toString()
	// + " einen Antrag zum Ausleihen eines Notebooks "
	// +
	// "gestellt. Ihnen wird hiermit bestätigt, dass Sie diesen Antrag storniert haben.";
	//
	// textPart.setText(textContent);
	//
	// MimeBodyPart htmlPart = new MimeBodyPart();
	// String htmlContent =
	// "<html><h1>Ihre Antragsdaten:</h1><table border=\"0\">"
	// +
	// "<tr><th>Name</th><th>Matrikelnummer</th><th>Zuständiger Dozent</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
	// + "<tr><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getMatrNr()
	// + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName()
	// + "</td><td>"
	// + date.format(r.getStart())
	// .toString()
	// + "</td><td>"
	// + date.format(r.getEnd())
	// .toString()
	// + "</td><td>"
	// + String.valueOf(r.getOs())
	// + "</td></tr></table><br>"
	// + "<p>Optionale Bemerkung: "
	// + r.getDescription()
	// + "</p></html>";
	//
	// htmlPart.setContent(htmlContent, "text/html");
	//
	// multipart.addBodyPart(textPart);
	// multipart.addBodyPart(htmlPart);
	// sendEmail(to, cc, subject, multipart);
	// }
	//
	// /**
	// * Sends an email to the appropriate lecturer of the requesting student
	// who
	// * cancelled his/her request
	// *
	// * @param to Specifies the target e-mail adress of recipient
	// * @param cc Specifies the target e-mail adress of recipient in cc
	// * @param subject Specifies the subject of the e-mail message
	// * @param r The corresponding request object, to which the messages refer
	// to
	// * @throws MessagingException
	// */
	//
	// public void sendMailRequestCancelledLecturer(String to, String cc,
	// String subject, Request r) throws MessagingException {
	// Multipart multipart = new MimeMultipart("message");
	// MimeBodyPart textPart = new MimeBodyPart();
	// String textContent = "Hallo "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getApproverId())
	// .getLastName()
	// + ",\n\n"
	// + "Am "
	// + date.format(r.getCreated())
	// .toString()
	// + " hat "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName()
	// + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName()
	// + " einen Antrag zum Ausleihen eines Notebooks "
	// + "gestellt. Dieser Antrag wurde vom Antragsteller zurückgezogen.";
	//
	// textPart.setText(textContent);
	//
	// MimeBodyPart htmlPart = new MimeBodyPart();
	// String htmlContent = "<html><h1>Antragsdaten:</h1><table border=\"0\">"
	// +
	// "<tr><th>Name</th><th>Matrikelnummer</th><th>Ausleihdatum</th><th>Rückgabedatum</th><th>Betriebssystem</th></tr>"
	// + "<tr><td>" + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getFirstName() + " "
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getLastName() + "</td><td>"
	// + mc.getDataAccess()
	// .getUserForID(r.getRequesterId())
	// .getMatrNr() + "</td><td>" + date.format(r.getStart())
	// .toString()
	// + "</td><td>" + date.format(r.getEnd())
	// .toString() + "</td><td>"
	// + String.valueOf(r.getOs()) + "</td></tr></table><br>"
	// + "<p>Optionale Bemerkung: " + r.getDescription()
	// + "</p></html>";
	//
	// htmlPart.setContent(htmlContent, "text/html");
	//
	// multipart.addBodyPart(textPart);
	// multipart.addBodyPart(htmlPart);
	// sendEmail(to, cc, subject, multipart);
	// }
	//
	// /**
	// * sendEmail is only called by sendMailRequestStudent,
	// * sendMailRequestLecturer, sendMailRequestSuccess,
	// sendMailRequestRejected,
	// * sendMailAdminInfo, sendMailOverdueWarning,
	// * sendMailRequestCancelledStudent, sendMailRequestCancelledLecturer
	// *
	// * @param to Specifies the target e-mail adress of recipient
	// * @param cc Specifies the target e-mail adress of recipient in cc
	// * @param subject Specifies the subject of the e-mail message
	// * @param multipart The contents of the message in text and/or html format
	// */
	//
	// @SuppressWarnings("incomplete-switch")
	// private void sendEmail(String to, String cc, String subject,
	// Multipart contents) {
	// Properties props = new Properties();
	// props.put("mail.smtp.host", host);
	// props.put("mail.smtp.port", port);
	// switch (protocol) {
	// case SMTPS:
	// props.put("mail.smtp.ssl.enable", true);
	// break;
	// case TLS:
	// props.put("mail.smtp.starttls.enable", true);
	// break;
	// }
	//
	// Authenticator authenticator = null;
	// if (auth) {
	// props.put("mail.smtp.auth", true);
	// authenticator = new Authenticator() {
	// private PasswordAuthentication pa = new PasswordAuthentication( username,
	// password);
	//
	// @Override
	// public PasswordAuthentication getPasswordAuthentication() {
	// return pa;
	// }
	// };
	// }
	//
	// Session session = Session.getInstance(props, authenticator);
	// session.setDebug(debug);
	//
	// MimeMessage message = new MimeMessage(session);
	// try {
	// message.setFrom(new InternetAddress(from));
	// InternetAddress[] address = { new InternetAddress(to) };
	// message.setRecipients(Message.RecipientType.TO, address);
	// message.setSubject(subject);
	// message.setSentDate(new Date());
	// // message.setText(body);
	// message.setContent(contents);
	// Transport.send(message);
	// } catch (MessagingException ex) {
	// ex.printStackTrace();
	// }
	//
	// }
	//

	/**
	 * 
	 * @param to The mail address the mail should be sent
	 * @param subject The mail subject
	 * @param body The mail body in HTML
	 */

	public static void sendHTMLMail(String to, String subject, String body)
			throws Exception {

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

		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

		msg.setSubject(subject);
		msg.setContent(body, "text/html");

		Transport.send(msg);

	}
}
