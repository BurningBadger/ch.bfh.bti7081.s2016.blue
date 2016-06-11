package ch.bfh.bti7081.s2016.blue.hv.model;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Service to send emails.
 */
public class MailModel {

    private final static Logger LOGGER = Logger.getLogger(MailModel.class.getName());

    private static String hostName = null;
    private static String smtpPort = null;
    private static String smtpUsername = null;
    private static String smtpPassword = null;

    // read values at startup.
    static {
	try {
	    Properties properties = new Properties();
	    properties.load(MailModel.class.getResourceAsStream("/mail.properties"));
	    hostName = properties.getProperty("mail.hostname");
	    smtpPort = properties.getProperty("mail.smtp.port");
	    smtpUsername = properties.getProperty("mail.smtp.username");
	    smtpPassword = properties.getProperty("mail.smtp.password");
	}
	catch (IOException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	}
    }

    /**
     * Send an email.
     * 
     * @param from
     *            the sender email.
     * @param subject
     *            the subject.
     * @param text
     *            the email text.
     * @param recipients
     *            the recipients.
     * @throws IOException
     *             if en error occurred.
     */
    public static void send(String from, String subject, String text, String... recipients) throws IOException {
	try {
	    Email email = new SimpleEmail();
	    email.setHostName(hostName);
	    email.setSmtpPort(Integer.parseInt(smtpPort));
	    email.setAuthenticator(new DefaultAuthenticator(smtpUsername, smtpPassword));
	    email.setFrom(from);
	    email.setSubject(subject);
	    email.setMsg(text);
	    email.addTo(recipients);
	    email.send();
	}
	catch (EmailException e) {
	    LOGGER.log(Level.SEVERE, e.getMessage(), e);
	}
    }
}
