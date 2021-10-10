package by.epam.task6001.service.impl;

import by.epam.task6001.bean.User;
import by.epam.task6001.bean.UserRole;
import by.epam.task6001.dao.DAOException;
import by.epam.task6001.dao.DAOProvider;
import by.epam.task6001.dao.UserDAO;
import by.epam.task6001.service.EncryptorService;
import by.epam.task6001.service.ServiceException;
import by.epam.task6001.service.ServiceProvider;
import by.epam.task6001.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final DAOProvider provider = DAOProvider.getInstance();
    @Override
    public User authorization(String login, String password) throws ServiceException {
        User user;
        UserDAO dao;
        ServiceProvider serviceProvider;
        EncryptorService passwordService;
        String decryptedUserPassword;

        try {
            dao = provider.getNewUserDAO();
            user = dao.nextUser();
            serviceProvider=ServiceProvider.getInstance();
            passwordService = serviceProvider.getEncryptorService();

            while (user != null) {
                decryptedUserPassword = passwordService.decrypt(user.getPassword());

                if (login.equals(user.getLogin()) && password.equals(decryptedUserPassword)) {
                    return user;
                }

                user = dao.nextUser();
            }

            return null;

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<String> adminsEmails() throws ServiceException {
        User user;
        UserDAO dao;
        List<String> emails;

        dao = provider.getNewUserDAO();
        emails = new ArrayList<>();

        try {
            user = dao.nextUser();

            while (user != null && user.getRole() == UserRole.ADMIN) {
                emails.add(user.getEmail());
                user = dao.nextUser();
            }

            return emails;

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<String> usersEmails() throws ServiceException {
        User user;
        UserDAO dao;
        List<String> emails;

        dao = provider.getNewUserDAO();
        emails = new ArrayList<>();

        try {
            user = dao.nextUser();

            while (user != null) {
                emails.add(user.getEmail());
                user = dao.nextUser();
            }

            return emails;

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
