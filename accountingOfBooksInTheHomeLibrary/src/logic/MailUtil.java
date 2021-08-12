package logic;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailUtil {
    private static Session session;

    public static void sendMail(String senderEmail, String senderPassword, String[] recipientsEmails, String subject, String text) throws MessagingException {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");

        session = Session.getDefaultInstance(properties, null);

        MimeMessage message = prepareMessage(senderEmail, recipientsEmails, subject, text);

        Transport transport = session.getTransport();
        transport.connect("smtp.gmail.com", senderEmail, senderPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private static MimeMessage prepareMessage(String senderEmail, String[] recipientsEmails, String subject, String text) throws MessagingException {
        MimeMessage message = new MimeMessage(session);

        for (String recipientEmail : recipientsEmails) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        }

        message.setFrom(new InternetAddress(senderEmail));
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
