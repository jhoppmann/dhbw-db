package com.dhbw_db.model.mail;

import java.util.Date;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Session Bean implementation class EmailSessionBean
 */
@Stateless
@LocalBean
public class EmailSessionBean {

	private int port = 465;

	private String host = "mail.gmx.net";

	private String from = "moritz.walliser@gmx.de";

	private boolean auth = true;

	private String username = "moritz.walliser@gmx.de";

	private String password = "kfgnm2655";

	private Protocol protocol = Protocol.SMTPS;

	private boolean debug = true;

	@SuppressWarnings("incomplete-switch")
	public void sendEmail(String to, String subject, String body) {
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
			message.setText(body);
			Transport.send(message);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}

	}

}
