package com.epam.task6001.logic;

import com.epam.task6001.data.DataUtil;
import com.epam.task6001.model.Book;
import com.epam.task6001.model.User;
import com.epam.task6001.model.UserRole;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private static final String booksFileName = "accountingOfBooksInTheHomeLibrary/src/com/epam/task6001/data/books.txt";
    private static final String usersFileName = "accountingOfBooksInTheHomeLibrary/src/com/epam/task6001/data/personData.txt";
    private static final String indent = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    private User currentUser;
    private final Scanner scanner;

    private static final String libraryMailAddress = "trockaya.test@gmail.com";
    private static final String libraryMailPassword = "test147T";


    private final ArrayList<Book> books;
    private final ArrayList<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    ArrayList<Book> getBooks() {
        return books;
    }

    User getCurrentUser() {
        return currentUser;
    }

    static String getLibraryMailAddress() {
        return libraryMailAddress;
    }

    static String getLibraryMailPassword() {
        return libraryMailPassword;
    }

    public void start() throws IOException {
        UserMenu menu;

        DataUtil.loadBooks(booksFileName, books);
        DataUtil.loadUsers(usersFileName, users);

        System.out.println(indent + "--------------------          The home library         --------------------\n\n\n\n");
        initializeUser();

        while (currentUser == null) {
            System.out.println(indent + "--------------------          The home library         --------------------\n\n");
            System.out.println("Incorrect login or password!");
            System.out.println("Try again.\n\n");
            initializeUser();
        }

        if (currentUser.getRole() == UserRole.Admin) {
            menu = new AdminMenu(this);
        } else if (currentUser.getRole() == UserRole.User) {
            menu = new UserMenu(this);
        } else {
            throw new RuntimeException("Incorrect user role!");
        }
        menu.start(scanner);
    }

    private void initializeUser() {
        String login;
        String password;

        System.out.print("Enter login: ");
        login = scanner.nextLine();
        System.out.print("Enter password: ");
        password = scanner.nextLine();

        for (User user : users) {
            if (user.getLogin().equals(login)) {
                if (user.getPassword().equals(password)) {
                    currentUser = user;
                    return;
                }
            }
        }
    }

    int getNumberOfBooks() {
        return books.size();
    }

    ArrayList<Book> searchBooksByTitle(String title) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    ArrayList<Book> searchBooksByAuthor(String author) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().contains(author)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    ArrayList<Book> searchBooksById(long id) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getId() == id) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }


    boolean addBook(Book book) throws IOException {
        if (isNotExistBook(book) && book != null) {
            books.add(book);
            DataUtil.unloadBooks(booksFileName, books);
            return true;
        }
        return false;
    }

    boolean isNotExistBook(Book anyBook) {
        for (Book book : books) {
            if (anyBook.equals(book)) {
                return false;
            }
        }
        return true;
    }

    boolean removeBook(long id) throws IOException {
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                DataUtil.unloadBooks(booksFileName, books);
                return true;
            }
        }
        return false;
    }

    public void finish() {
        scanner.close();
        System.exit(0);
    }

    String[] getAdminsEmails() {
        ArrayList<String> emails = new ArrayList<>();
        for (User user : users) {
            if (user.getRole() == UserRole.Admin) {
                emails.add(user.getEmail());
            }
        }
        if (!emails.isEmpty()) {
            return emails.toArray(String[]::new);
        } else {
            throw new RuntimeException("Admin is not exist!");
        }
    }

    String[] getOtherUsersEmails() {
        String[] emails = new String[users.size() - 1];
        int i = 0;
        for (User user : users) {
            if (!user.equals(currentUser)) {
                emails[i] = user.getEmail();
                i++;
            }
        }
        return emails;
    }
}