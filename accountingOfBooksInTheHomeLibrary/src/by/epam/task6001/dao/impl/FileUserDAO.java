package by.epam.task6001.dao.impl;

import by.epam.task6001.bean.User;
import by.epam.task6001.dao.DAOException;
import by.epam.task6001.dao.UserDAO;
import by.epam.task6001.dao.parse.ParseException;
import by.epam.task6001.dao.parse.UserParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUserDAO implements UserDAO {
    private final String usersFileName;
    private BufferedReader reader;

    {
        usersFileName = FileUserDAO.class
                .getResource("/by/epam/task6001/resource/personData.txt")
                .toString()
                .substring(6);
    }

    public FileUserDAO() {
    }

    @Override
    public User nextUser() throws DAOException {
        User user;
        String userString;
        UserParser parser;

        try {
            userString = readNextString();
            parser = UserParser.getInstance();

            if (userString != null) {
                user = parser.parse(userString);
            } else {
                user = null;
            }

            return user;

        } catch (IOException | ParseException exception) {
            throw new DAOException(exception);
        }

    }

    private String readNextString() throws IOException {
        String result;

        if (reader == null) {
            reader = new BufferedReader(new FileReader(usersFileName));
        }

        if (reader.ready()) {
            result = reader.readLine();
        } else {
            result = null;
            reader.close();
            reader = null;
        }

        return result;
    }
}
