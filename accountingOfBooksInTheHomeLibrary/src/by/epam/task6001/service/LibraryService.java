package by.epam.task6001.service;

import by.epam.task6001.bean.Book;

import java.util.List;

public interface LibraryService {
    void addBook(Book book) throws ServiceException;
    void removeBook(long id) throws ServiceException;
    int numberOfBooks() throws ServiceException;
    List<Book> getBooks(int quantityToReturn, int quantityToSkip) throws ServiceException;
    int numberOfBooksWithId(long id) throws ServiceException;
    List<Book> searchBooksById(long id, int quantityToReturn, int quantityToSkip) throws ServiceException;
    int numberOfBooksWithTitle(String title) throws ServiceException;
    List<Book> searchBooksByTitle(String title, int quantityToReturn, int quantityToSkip) throws ServiceException;
    int numberOfBooksWithAuthor(String author) throws ServiceException;
    List<Book> searchBooksByAuthor(String author, int quantityToReturn, int quantityToSkip) throws ServiceException;
    void suggestBook(String userString, Book book) throws ServiceException;
}
