package by.epam.task6001.service.impl;

import by.epam.task6001.bean.Book;
import by.epam.task6001.dao.*;
import by.epam.task6001.service.*;

import java.util.ArrayList;
import java.util.List;

public class LibraryServiceImpl implements LibraryService {
    private final DAOProvider daoProvider = DAOProvider.getInstance();

    @Override
    public void addBook(Book book) throws ServiceException {
        BookDAO bookDAO;
        BookIdDAO bookIdDAO;

        try {
            bookDAO = daoProvider.getNewBookDAO();
            bookIdDAO = daoProvider.getBookIdDAO();

            book.setId(bookIdDAO.getNextId());
            bookDAO.addBook(book);
            sendBookInfoToUsers(book);

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    private void sendBookInfoToUsers(Book book) throws ServiceException {
        LibraryEmailDAO libraryEmailDAO;
        ServiceProvider serviceProvider;
        UserService userService;
        MailService mailService;
        EncryptorService encryptorService;

        String subject;
        String message;

        libraryEmailDAO = daoProvider.getLibraryEmailDAO();
        serviceProvider = ServiceProvider.getInstance();
        userService = serviceProvider.getUserService();
        mailService = serviceProvider.getMailService();
        encryptorService = serviceProvider.getEncryptorService();

        subject = "The home library. Added a new book.";
        message = subject + "\n" + bookToString(book);

        try {
            mailService.sendMail(libraryEmailDAO.getLogin()
                    , encryptorService.decrypt(libraryEmailDAO.getPassword())
                    , userService.usersEmails()
                    , subject
                    , message);

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void removeBook(long id) throws ServiceException {
        BookDAO dao;

        try {
            dao = daoProvider.getNewBookDAO();
            dao.removeBook(id);

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Book> viewBooks(int quantityToReturn, int quantityToSkip) throws ServiceException {
        BookDAO dao;
        List<Book> books;
        Book currentBook;

        try {
            if (quantityToReturn >= 0 && quantityToSkip >= 0) {
                dao = daoProvider.getNewBookDAO();
                books = new ArrayList<>();
                currentBook = dao.getNext();

                while (currentBook != null) {

                    if (quantityToSkip == 0) {
                        books.add(currentBook);

                        if (books.size() == quantityToReturn) {
                            return books;
                        }

                    } else {
                        quantityToSkip--;
                    }

                    currentBook = dao.getNext();

                }

                return books;

            } else {
                throw new ServiceException("Incorrect parameters to search!");
            }

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public int numberOfBooks() throws ServiceException {
        BookDAO dao;
        int number;

        try {
            dao = daoProvider.getNewBookDAO();
            number = 0;

            while (dao.getNext() != null) {
                number++;
            }

            return number;

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Book> searchBooksById(long id, int quantityToReturn, int quantityToSkip) throws ServiceException {
        BookDAO dao;
        List<Book> books;
        Book currentBook;
        int numberOfBooksWithId = 1;

        try {
            if (id > 0 && quantityToReturn >= 0 && quantityToSkip >= 0) {
                dao = daoProvider.getNewBookDAO();
                books = new ArrayList<>(numberOfBooksWithId);
                currentBook = dao.getNext();

                while (currentBook != null) {

                    if (id == currentBook.getId()) {

                        if (quantityToSkip == 0) {
                            books.add(currentBook);

                            if (books.size() == quantityToReturn) {
                                return books;
                            }

                        } else {
                            quantityToSkip--;
                        }

                    }

                    currentBook = dao.getNext();

                }

                return books;
            } else {
                throw new ServiceException("Incorrect parameters to search!");
            }

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public int numberOfBooksWithId(long id) throws ServiceException {
        BookDAO dao;
        Book currentBook;
        int numberOfBooksWithId;

        try {
            dao = daoProvider.getNewBookDAO();
            numberOfBooksWithId = 0;
            currentBook = dao.getNext();

            while (currentBook != null) {

                if (id == currentBook.getId()) {
                    numberOfBooksWithId++;
                }

                currentBook = dao.getNext();
            }

            return numberOfBooksWithId;

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Book> searchBooksByTitle(String title, int quantityToReturn, int quantityToSkip) throws ServiceException {
        BookDAO dao;
        List<Book> books;
        Book book;

        try {
            if (title != null && quantityToReturn >= 0 && quantityToSkip >= 0) {

                dao = daoProvider.getNewBookDAO();
                books = new ArrayList<>();
                book = dao.getNext();

                while (book != null) {

                    if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                        if (quantityToSkip == 0) {
                            books.add(book);

                            if (books.size() == quantityToReturn) {
                                return books;
                            }

                        } else {
                            quantityToSkip--;
                        }
                    }

                    book = dao.getNext();

                }

                return books;

            } else {
                throw new ServiceException("Incorrect parameters to search!");
            }

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public int numberOfBooksWithTitle(String title) throws ServiceException {
        BookDAO dao;
        Book currentBook;
        int number;

        try {
            dao = daoProvider.getNewBookDAO();
            number = 0;
            currentBook = dao.getNext();

            while (currentBook != null) {

                if (currentBook.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    number++;
                }

                currentBook = dao.getNext();
            }

            return number;

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Book> searchBooksByAuthor(String author, int quantityToReturn, int quantityToSkip) throws ServiceException {
        BookDAO dao;
        List<Book> books;
        Book book;

        try {
            if (author != null && quantityToReturn >= 0 && quantityToSkip >= 0) {

                dao = daoProvider.getNewBookDAO();
                books = new ArrayList<>();
                book = dao.getNext();

                while (book != null) {
                    if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {

                        if (quantityToSkip == 0) {
                            books.add(book);

                            if (books.size() == quantityToReturn) {
                                return books;
                            }

                        } else {
                            quantityToSkip--;
                        }

                    }

                    book = dao.getNext();
                }

                return books;

            } else {
                throw new ServiceException("Incorrect parameters to search!");
            }

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public int numberOfBooksWithAuthor(String author) throws ServiceException {
        BookDAO dao;
        Book currentBook;
        int number;

        try {
            dao = daoProvider.getNewBookDAO();
            number = 0;
            currentBook = dao.getNext();

            while (currentBook != null) {

                if (currentBook.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                    number++;
                }

                currentBook = dao.getNext();
            }

            return number;

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void suggestBook(String userName, Book book) throws ServiceException {
        LibraryEmailDAO libraryEmailDAO;
        ServiceProvider serviceProvider;
        UserService userService;
        MailService mailService;
        EncryptorService encryptorService;

        List<String> adminsEmails;
        String subject;
        String message;

        libraryEmailDAO = daoProvider.getLibraryEmailDAO();
        serviceProvider = ServiceProvider.getInstance();
        userService = serviceProvider.getUserService();
        mailService = serviceProvider.getMailService();
        encryptorService = serviceProvider.getEncryptorService();


        adminsEmails = userService.adminsEmails();
        subject = generateSuggestSubject(userName);
        message = generateSuggestMessage(userName, book);

        try {
            mailService.sendMail(libraryEmailDAO.getLogin()
                    , encryptorService.decrypt(libraryEmailDAO.getPassword())
                    , adminsEmails
                    , subject
                    , message);

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    private String bookToString(Book book) {
        StringBuilder bookStringBuilder;

        bookStringBuilder = new StringBuilder();
        bookStringBuilder.append("title=\"").append(book.getTitle()).append('"').append(' ');
        bookStringBuilder.append("author=\"").append(book.getAuthor()).append('"').append(' ');
        bookStringBuilder.append("bookGenre=").append(book.getGenre()).append(' ');
        bookStringBuilder.append("pages=").append(book.getPages()).append(' ');
        bookStringBuilder.append("isElectronic=").append(book.isElectronic());

        return bookStringBuilder.toString();
    }

    private String generateSuggestMessage(String userName, Book book) {
        StringBuilder messageBuilder;

        messageBuilder = new StringBuilder();

        messageBuilder.append("User ").append(userName).append(" suggested a book: ");
        messageBuilder.append(bookToString(book));

        return messageBuilder.toString();
    }

    private String generateSuggestSubject(String userName) {
        StringBuilder messageBuilder;

        messageBuilder = new StringBuilder();

        messageBuilder.append("The home library ")
                .append("User ")
                .append(userName)
                .append(" suggested a book.");

        return messageBuilder.toString();
    }
}
