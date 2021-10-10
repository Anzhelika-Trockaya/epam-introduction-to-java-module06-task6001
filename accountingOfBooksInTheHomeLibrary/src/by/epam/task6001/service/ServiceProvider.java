package by.epam.task6001.service;

import by.epam.task6001.service.impl.LibraryServiceImpl;
import by.epam.task6001.service.impl.MailServiceImpl;
import by.epam.task6001.service.impl.EncryptorServiceImpl;
import by.epam.task6001.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static ServiceProvider instance = new ServiceProvider();

    private final LibraryService libraryService;
    private final UserService userService;
    private final MailService mailService;
    private final EncryptorService encryptorService;

    private ServiceProvider() {
        libraryService = new LibraryServiceImpl();
        userService = new UserServiceImpl();
        mailService = new MailServiceImpl();
        encryptorService = new EncryptorServiceImpl();
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public LibraryService getLibraryService() {
        return libraryService;
    }

    public UserService getUserService() {
        return userService;
    }

    public MailService getMailService() {
        return mailService;
    }

    public EncryptorService getEncryptorService() {
        return encryptorService;
    }
}
