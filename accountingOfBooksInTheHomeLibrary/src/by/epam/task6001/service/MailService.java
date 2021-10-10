package by.epam.task6001.service;

import java.util.List;

public interface MailService {
    void sendMail(String senderEmail, String senderPassword, List<String> recipientsEmails, String subject,
             String text) throws ServiceException;
}
