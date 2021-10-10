package by.epam.task6001.dao;

import by.epam.task6001.dao.impl.FileBookDAO;
import by.epam.task6001.dao.impl.FileBookIdDAO;
import by.epam.task6001.dao.impl.FileLibraryEmailDAO;
import by.epam.task6001.dao.impl.FileUserDAO;

public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();

    private BookDAO bookDAO;
    private UserDAO userDAO;
    private final LibraryEmailDAO libraryEmailDAO;
    private final BookIdDAO bookIdDAO;

    private DAOProvider() {
        libraryEmailDAO=new FileLibraryEmailDAO();
        bookIdDAO =new FileBookIdDAO();
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public BookDAO getNewBookDAO() {
        bookDAO = new FileBookDAO();
        return bookDAO;
    }

    public UserDAO getNewUserDAO(){
        userDAO = new FileUserDAO();
        return userDAO;
    }

    public LibraryEmailDAO getLibraryEmailDAO() {
        return libraryEmailDAO;
    }

    public BookIdDAO getBookIdDAO() {
        return bookIdDAO;
    }
}
