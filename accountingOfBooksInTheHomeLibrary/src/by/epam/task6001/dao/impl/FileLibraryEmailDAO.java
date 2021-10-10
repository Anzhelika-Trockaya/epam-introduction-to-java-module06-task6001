package by.epam.task6001.dao.impl;

import by.epam.task6001.dao.DAOException;
import by.epam.task6001.dao.LibraryEmailDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLibraryEmailDAO implements LibraryEmailDAO {
    private final String emailFileName;

    {
        emailFileName = FileLibraryEmailDAO.class
                .getResource("/by/epam/task6001/resource/libraryMail.txt")
                .toString()
                .substring(6);
    }

    public FileLibraryEmailDAO() {
    }

    @Override
    public String getLogin() throws DAOException {
        String content;
        String contentRegex;
        String email;

        contentRegex = "email=.+@.+\\.[a-z]+\\spassword=.+";
        content = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(emailFileName))) {

            if (reader.ready()) {
                content = reader.readLine();
            }

        } catch (IOException ioException) {
            throw new DAOException(ioException);
        }

        if (content != null && content.matches(contentRegex)) {

            email = content.substring(6).replaceAll("\\spassword=.+", "");
            return email;

        } else {
            throw new DAOException("File with library email is not correct!");
        }
    }

    @Override
    public String getPassword() throws DAOException {
        String content;
        String contentRegex;
        String password;

        contentRegex = "email=.+@.+\\.[a-z]+\\spassword=.+";
        content = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(emailFileName))) {

            if (reader.ready()) {
                content = reader.readLine();
            }

        } catch (IOException ioException) {
            throw new DAOException(ioException);
        }

        if (content != null && content.matches(contentRegex)) {

            password = content.replaceAll("email=.+\\spassword=", "");
            return password;

        } else {
            throw new DAOException("File with library email is not correct!");
        }
    }
}
