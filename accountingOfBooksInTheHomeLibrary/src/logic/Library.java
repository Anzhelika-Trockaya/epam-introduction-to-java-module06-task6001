package logic;

import data.Data;
import model.Book;
import model.User;
import model.UserRole;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private static final String booksFileName = "accountingOfBooksInTheHomeLibrary/src/data/books.txt";
    private static final String usersFileName = "accountingOfBooksInTheHomeLibrary/src/data/personData.txt";
    private static final String indent = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    private User currentUser;
    private Scanner scanner;

    private final ArrayList<Book> books;
    private final ArrayList<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        UserMenu menu;

        Data.loadBooks(booksFileName, books);
        Data.loadUsers(usersFileName, users);

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

    public void printBooks(int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            System.out.println(books.get(i));
        }
    }

    public int getNumberOfBooks() {
        return books.size();
    }

    public ArrayList<Book> searchBooksByTitle(String title) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public ArrayList<Book> searchBooksByAuthor(String author) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().contains(author)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public ArrayList<Book> searchBooksById(long id) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getId() == id) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }


    public boolean addBook(Book book) throws IOException {
        if (isNotExist(book) && book != null) {
            books.add(book);
            Data.unloadBooks(booksFileName, books);
            return true;
        }
        return false;
    }

    boolean isNotExist(Book anyBook) {
        for (Book book : books) {
            if (anyBook.equals(book)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeBook(long id) throws IOException {
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                Data.unloadBooks(booksFileName, books);
                return true;
            }
        }
        return false;
    }

    public void finish() throws IOException {
        scanner.close();
        System.exit(0);
    }

    public String getAdminEmail() {
        for (User user : users) {
            if (user.getRole() == UserRole.Admin) {
                return user.getEmail();
            }
        }
        throw new RuntimeException("Admin is not exist!");
    }
}
