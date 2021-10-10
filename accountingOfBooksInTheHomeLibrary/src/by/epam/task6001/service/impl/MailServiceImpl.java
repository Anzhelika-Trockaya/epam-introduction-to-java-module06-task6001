package by.epam.task6001.service.impl;

import by.epam.task6001.service.MailService;
import by.epam.task6001.service.ServiceException;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    private Session session;

    public void sendMail(String senderEmail, String senderPassword, List<String> recipientsEmails, String subject,
                         String text) throws ServiceException {
        Properties properties;
        MimeMessage message;
        Transport transport;

        properties = System.getProperties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");

        session = Session.getDefaultInstance(properties, null);

        try {
            message = prepareMessage(senderEmail, recipientsEmails, subject, text);

            transport = session.getTransport();
            transport.connect("smtp.gmail.com", senderEmail, senderPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException messagingException) {
            throw new ServiceException(messagingException);
        }
    }

    private MimeMessage prepareMessage(String senderEmail, List<String> recipientsEmails, String subject,
                                       String text) throws MessagingException {
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
