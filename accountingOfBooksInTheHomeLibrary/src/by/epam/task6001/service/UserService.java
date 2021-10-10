package by.epam.task6001.service;

import by.epam.task6001.bean.User;

import java.util.List;

public interface UserService {
    User authorization(String login, String password) throws ServiceException;
    List<String> adminsEmails() throws ServiceException;
    List<String> usersEmails() throws ServiceException;
}
