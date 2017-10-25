/**
 * 
 */
package com.sophos.semih.util;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sophos.semih.common.ApplicationSetup;

/**
 * @author FT
 *
 */
public class SendMailTLS implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -459966088750720860L;
	
	private static final Log log = LogFactory.getLog(SendMailTLS.class);

	public static void sendMail(String texto, String asunto, String toAddress) {
		 
		final String username = ApplicationSetup.getInstance().getParamValue("ALERT_EMAIL");
		String decrypedPassword = null;
		try {
			decrypedPassword = Utilidades.decrypt(ApplicationSetup.getInstance().getParamValue("ALERT_EMAIL_PASSWORD"));
		} catch (GeneralSecurityException e) {
			log.error("Error desencriptando password de cuenta de correo", e);
		}
 
		final String password = decrypedPassword;
				
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", ApplicationSetup.getInstance().getParamValue("SMTP_HOST"));
		props.put("mail.smtp.port", ApplicationSetup.getInstance().getParamValue("SMTP_PUERTO"));
		props.put("mail.smtp.ssl.trust", ApplicationSetup.getInstance().getParamValue("SMTP_HOST"));
 
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(toAddress));
			message.setSubject(asunto);
			message.setText(texto);
 
			Transport.send(message);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
