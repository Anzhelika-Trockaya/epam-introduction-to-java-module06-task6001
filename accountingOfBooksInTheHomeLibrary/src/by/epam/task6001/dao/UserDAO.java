package by.epam.task6001.dao;

import by.epam.task6001.bean.User;

public interface UserDAO {
    User nextUser() throws DAOException;
}
