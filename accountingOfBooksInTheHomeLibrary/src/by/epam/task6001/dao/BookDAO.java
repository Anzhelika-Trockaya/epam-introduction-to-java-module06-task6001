package by.epam.task6001.dao;

import by.epam.task6001.bean.Book;

public interface BookDAO {

    Book getNext() throws DAOException;

    void addBook(Book book) throws DAOException;

    void removeBook(long id) throws DAOException;
}
