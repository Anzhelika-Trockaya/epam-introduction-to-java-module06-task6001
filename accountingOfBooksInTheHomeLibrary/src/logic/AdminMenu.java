package logic;

import model.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminMenu extends Menu {
    protected Library library;
    protected final String indent = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    public AdminMenu(Library library) {
        this.library = library;
    }

    public void start() throws IOException {
        printMainMenu();
        processInputInMainMenu();
    }

    protected void printMainMenu() {
        System.out.println(indent + "----------------   MENU   ----------------");
        System.out.println(" - To see books enter 1");
        System.out.println(" - To search book enter 2");
        System.out.println(" - To add book enter 3");
        System.out.println(" - To remove book enter 4");
        System.out.println();
        System.out.println(" - To exit enter 0");
    }

    protected void processInputInMainMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter here: ");
        String input = scanner.nextLine();
        switch (input) {
            case "1" -> processOfViewingBooks();
            case "2" -> processOfSearchingABook();
            case "3" -> processOfAddingABook();
            case "4" -> processOfRemovingABook();
            case "0" -> {
            }
            default -> {
                System.out.println("No such action " + input + "!");
                start();
            }
        }
    }

    private void processOfAddingABook() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String title;
        String author;
        String genre;
        String pages;
        Book book;
        String isElectronic;
        String input;

        System.out.println(indent + "---------------  Add book ---------------");
        System.out.println("Enter book data: ");
        System.out.print("Title: ");
        title = scanner.nextLine();
        System.out.print("Author: ");
        author = scanner.nextLine();
        System.out.print("Genre: ");
        genre = scanner.nextLine();
        System.out.print("Pages: ");
        pages = scanner.nextLine();
        System.out.print("Is book electronic? Enter \"true\" if it is otherwise enter \"false\": ");
        isElectronic = scanner.nextLine();

        try {
            book = new Book(title, author, genre, Integer.parseInt(pages), Boolean.parseBoolean(isElectronic));
            library.addBook(book);
            System.out.println(" - Book is added. - ");
        } catch (IllegalArgumentException exception) {
            System.out.println(" - Book not added! Incorrect data of book! - ");
        }

        System.out.println("\n\n - To add another book enter 1");
        System.out.println(" - To exit enter 0");
        System.out.println(" - To return to the main menu enter any other symbol");
        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        switch (input) {
            case "1" -> processOfAddingABook();
            case "0" -> {
            }
            default -> start();
        }
    }

    private void processOfRemovingABook() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String id;
        boolean isRemoved;
        String input;

        System.out.println(indent + "--------------  Remove book --------------");
        System.out.print("Enter book id: ");
        id = scanner.nextLine();

        try {
            isRemoved = library.removeBook(Long.parseLong(id));
            if (isRemoved) {
                System.out.println("Book is removed.");
            } else {
                System.out.println("Book with id " + id + " does not exist!");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Incorrect id " + id + "!");
        }


        System.out.println("\n\n - To remove another book enter 1");
        System.out.println(" - To exit enter 0");
        System.out.println(" - To return to the main menu enter any other symbol");
        scanner = new Scanner(System.in);
        input = scanner.nextLine();
        switch (input) {
            case "1" -> processOfRemovingABook();
            case "0" -> {
            }
            default -> start();
        }
    }

    private void processOfSearchingABook() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String input;
        ArrayList<Book> foundBooks = new ArrayList<>();

        System.out.println(indent + "---------------  Search book ---------------");
        System.out.println("Select an parameter to search:");
        System.out.println(" - title - enter 1");
        System.out.println(" - author - enter 2");
        System.out.println(" - id - enter 3");
        System.out.println("\n - To return to the main menu enter 0");

        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        switch (input) {
            case "1" -> foundBooks = searchBookByTitle(scanner);
            case "2" -> foundBooks = searchBookByAuthor(scanner);
            case "3" -> foundBooks = searchBookById(scanner);
            case "0" -> library.finish();
            default -> {
                System.out.println("System.out.println(\"No such action \" + input + \"!\")");
                processOfSearchingABook();
            }
        }

        if (!foundBooks.isEmpty()) {
            System.out.println("\n\n--------     Found books:     --------");
            for (Book book : foundBooks) {
                System.out.println(book);
            }
        } else {
            System.out.println("No books found!");
        }

        System.out.println("\n\n - To search another book enter 1");
        System.out.println(" - To exit enter 0");
        System.out.println(" - To return to the main menu enter any other symbol");
        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        switch (input) {
            case "1" -> processOfSearchingABook();
            case "0" -> {
            }
            default -> start();
        }
    }

    private ArrayList<Book> searchBookByTitle(Scanner scanner) {
        String input;
        System.out.print(" - Enter title: ");
        input = scanner.nextLine();
        return library.searchBooksByTitle(input);
    }

    private ArrayList<Book> searchBookByAuthor(Scanner scanner) {
        String input;
        System.out.print(" - Enter author: ");
        input = scanner.nextLine();
        return library.searchBooksByAuthor(input);
    }

    private ArrayList<Book> searchBookById(Scanner scanner) {
        String input;
        Long id;
        System.out.print(" - Enter id: ");
        input = scanner.nextLine();
        try {
            id = Long.parseLong(input);
            return library.searchBooksById(id);
        } catch (IllegalArgumentException exception) {
            System.out.println("Incorrect id!");
            return new ArrayList<>();
        }
    }

    private void processOfViewingBooks() throws IOException {

    }
}
