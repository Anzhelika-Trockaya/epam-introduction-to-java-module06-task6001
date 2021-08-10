package data;

import model.Book;
import model.BookGenre;
import model.User;
import model.UserRole;

import java.io.*;
import java.util.ArrayList;

public class Data {


    public static void loadBooks(String booksFileName, ArrayList<Book> books) throws IOException {
        String[] bookStringArray;
        long id;
        String title;
        String author;
        BookGenre genre;
        boolean isElectronic;
        int pages;

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
                        book.isElectronic()+"\n";
                writer.write(bookString);
            }
        }
    }

    public static void loadUsers(String usersFileName, ArrayList<User> users) throws IOException {
        String[] userString;
        String login;
        String name;
        String email;
        UserRole role;
        String password;
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFileName))) {
            while (reader.ready()) {
                userString = reader.readLine().split("\\|");
                login = userString[0];
                name = userString[1];
                email = userString[2];
                role = UserRole.valueOf(userString[3]);
                password = decryptPassword(userString[4]);
                users.add(new User(login, name, email, role, password));
            }
        }

    }

    public static void unloadUsers(String usersFileName, ArrayList<User> users) throws IOException {
        String userString;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFileName))) {
            for (User user: users) {
                userString = user.getLogin() + "|" + user.getName() + "|" + user.getEmail() + "|" + user.getRole() + "|" + encryptPassword(user.getPassword()) + "\n";
                writer.write(userString);
            }
        }
    }

    private static String decryptPassword(String encryptedPassword) {
        char[] encryptedPasswordChars = encryptedPassword.toCharArray();
        char[] symbols = "abcdefghijklmnopqrstuvwxyzABCDEGGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        char[] decryptedPasswordChars = new char[encryptedPasswordChars.length];
        String decryptedPassword;

        for (int i = 0; i < encryptedPassword.length(); i++) {
            for (int j = 0; j < 62; j++) {
                if (symbols[j] == encryptedPasswordChars[i]) {
                    decryptedPasswordChars[i] = symbols[(j + 7) % 62];
                }
            }
        }

        decryptedPassword = String.valueOf(decryptedPasswordChars);
        return decryptedPassword;
    }

    private static String encryptPassword(String decryptedPassword) {
        char[] decryptedPasswordChars = decryptedPassword.toCharArray();
        char[] symbols = "abcdefghijklmnopqrstuvwxyzABCDEGGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        char[] encryptedPasswordChars = new char[decryptedPasswordChars.length];
        String encryptedPassword;

        for (int i = 0; i < decryptedPassword.length(); i++) {
            for (int j = 0; j < 62; j++) {
                if (symbols[j] == decryptedPasswordChars[i]) {
                    encryptedPasswordChars[i] = j >= 7 ? symbols[j - 7] : symbols[62 + j - 7];
                }
            }
        }

        encryptedPassword = String.valueOf(encryptedPasswordChars);
        return encryptedPassword;
    }
}
