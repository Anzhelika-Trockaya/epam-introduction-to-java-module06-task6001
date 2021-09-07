package com.epam.task6001.data;

import com.epam.task6001.model.Book;
import com.epam.task6001.model.BookGenre;
import com.epam.task6001.model.User;
import com.epam.task6001.model.UserRole;

import java.io.*;
import java.util.ArrayList;

public class DataUtil {

    public static ArrayList<Book> loadBooks(String booksFileName) throws IOException {
        ArrayList<Book> books;
        String[] bookStringArray;
        long id;
        String title;
        String author;
        BookGenre genre;
        boolean isElectronic;
        int pages;

        books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(booksFileName))) {
            while (reader.ready()) {
                bookStringArray = reader.readLine().split("\\|");
                id = Long.parseLong(bookStringArray[0]);
                title = bookStringArray[1];
                author = bookStringArray[2];
                genre = BookGenre.valueOf(bookStringArray[3]);
                pages = Integer.parseInt(bookStringArray[4]);
                isElectronic = Boolean.parseBoolean(bookStringArray[5]);
                books.add(new Book(id, title, author, genre, pages, isElectronic));
            }
        }

        return books;
    }

    public static void unloadBooks(String booksFileName, ArrayList<Book> books) throws IOException {
        String bookString;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(booksFileName))) {
            for (Book book : books) {
                bookString = book.getId() + "|" +
                        book.getTitle() + "|" +
                        book.getAuthor() + "|" +
                        book.getGenre() + "|" +
                        book.getPages() + "|" +
                        book.isElectronic()
                        + "\n";

                writer.write(bookString);
            }
        }
    }

    public static ArrayList<User> loadUsers(String usersFileName) throws IOException {
        ArrayList<User> users;
        String[] userString;
        String login;
        String name;
        String email;
        UserRole role;
        String password;

        users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(usersFileName))) {
            while (reader.ready()) {
                userString = reader.readLine().split("\\|");
                login = userString[0];
                name = userString[1];
                email = userString[2];
                role = UserRole.valueOf(userString[3]);
                password = PasswordsDecryptorUtil.decryptPassword(userString[4]);
                users.add(new User(login, name, email, role, password));
            }
        }
        return users;
    }

    public static void unloadUsers(String usersFileName, ArrayList<User> users) throws IOException {
        String userString;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFileName))) {
            for (User user : users) {
                userString = user.getLogin() +
                        "|" + user.getName() +
                        "|" + user.getEmail() +
                        "|" + user.getRole() +
                        "|" + PasswordsDecryptorUtil.encryptPassword(user.getPassword()) + "\n";

                writer.write(userString);
            }
        }
    }


}
