package by.epam.task6001.dao.impl;

import by.epam.task6001.bean.Book;
import by.epam.task6001.dao.BookDAO;
import by.epam.task6001.dao.DAOException;
import by.epam.task6001.dao.parse.BookParser;
import by.epam.task6001.dao.parse.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBookDAO implements BookDAO {
    private final String booksFileName;
    private BufferedReader reader;

    {
        booksFileName = FileBookDAO.class
                .getResource("/by/epam/task6001/resource/books.txt")
                .toString()
                .substring(6);
    }

    public FileBookDAO() {
    }

    @Override
    public Book getNext() throws DAOException {
        String bookString;
        Book book;
        BookParser parser;

        try {
            bookString = readNextString();
            parser = BookParser.getInstance();

            if (bookString != null) {
                book = parser.parse(bookString);
            } else {
                book = null;
            }

            return book;

        } catch (IOException | ParseException exception) {
            throw new DAOException(exception);
        }
    }

    private String readNextString() throws IOException {
        String result;

        if (reader == null) {
            reader = new BufferedReader(new FileReader(booksFileName));
        }

        if (reader.ready()) {
            result = reader.readLine();
        } else {
            result = null;
            closeReader();
        }

        return result;
    }

    private void closeReader() throws IOException {
        if (reader != null) {
            reader.close();
            reader = null;
        }
    }

    @Override
    public void addBook(Book book) throws DAOException {
        String bookString;

        try {
            closeReader();
            bookString = getBookString(book);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(booksFileName, true))) {
                writer.newLine();
                writer.write(bookString);
                writer.flush();
            }

        } catch (IOException ioException) {
            throw new DAOException(ioException);
        }
    }

    private String getBookString(Book book) {
        StringBuilder bookStringBuilder;

        bookStringBuilder = new StringBuilder();
        bookStringBuilder.append("id=").append(book.getId()).append(' ');
        bookStringBuilder.append("title=\"").append(book.getTitle()).append("\" ");
        bookStringBuilder.append("author=\"").append(book.getAuthor()).append("\" ");
        bookStringBuilder.append("bookGenre=").append(book.getGenre()).append(' ');
        bookStringBuilder.append("pages=").append(book.getPages()).append(' ');
        bookStringBuilder.append("isElectronic=").append(book.isElectronic());

        return bookStringBuilder.toString();
    }

    @Override
    public void removeBook(long id) throws DAOException {
        String directoryName;
        Path tempFilePath;

        try {
            directoryName = getClass()
                    .getResource("/by/epam/task6001/resource")
                    .toString()
                    .substring(6);
            tempFilePath = Files.createTempFile(Path.of(directoryName), "books", ".txt").toAbsolutePath();

            closeReader();

            writeBooksExceptBookWithId(tempFilePath, id);
            closeReader();
            Files.deleteIfExists(Path.of(booksFileName));
            Files.move(tempFilePath, Path.of(booksFileName));

        } catch (IOException exception) {
            throw new DAOException(exception);
        }
    }

    private void writeBooksExceptBookWithId(Path tempFilePath, long id) throws IOException {
        String nextLine;
        String idString;

        idString = "id=" + id + " ";

        try (BufferedReader reader = new BufferedReader(new FileReader(booksFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath.toFile()))) {

            if (reader.ready()) {
                nextLine = reader.readLine();

                if (!nextLine.startsWith(idString)) {
                    writer.write(nextLine);
                } else if (reader.ready()) {
                    nextLine = reader.readLine();
                    writer.write(nextLine);
                }
            }

            while (reader.ready()) {
                nextLine = reader.readLine();

                if (!nextLine.startsWith(idString)) {
                    writer.newLine();
                    writer.write(nextLine);
                }
            }

            writer.flush();
        }
    }

}
