package logic;

import model.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserMenu {
    protected Library library;
    protected final String indent = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    protected Scanner scanner;

    protected UserMenu(Library library) {
        this.library = library;
    }

    public void start(Scanner scanner) throws IOException {
        this.scanner=scanner;
        while (true) {
            printMainMenu();
            processInputInMainMenu();
        }
    }

    protected void printMainMenu() {
        System.out.println(indent + "----------------   MENU   ----------------");
        System.out.println(" - To view books enter 1");
        System.out.println(" - To search book enter 2");
        System.out.println(" - To suggest a book enter 3");
        System.out.println();
        System.out.println(" - To exit enter 0");
    }

    protected void processInputInMainMenu() throws IOException {
        String input;
        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        switch (input) {
            case "1" -> processOfViewingBooks();
            case "2" -> processOfSearchingABook();
            case "3" -> processOfSuggestABook();
            case "0" -> library.finish();
            default -> System.out.println("No such action " + input + "!");
        }
    }

    protected void processOfSearchingABook() {
        String input;
        ArrayList<Book> foundBooks;

        System.out.println(indent + "---------------  Search book ---------------");
        System.out.println("Select an parameter to search:");
        System.out.println(" - title - enter 1");
        System.out.println(" - author - enter 2");
        System.out.println(" - id - enter 3");
        System.out.println("\n - To return to the main menu enter any other symbol");

        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        switch (input) {
            case "1" -> foundBooks = searchBookByTitle(scanner);
            case "2" -> foundBooks = searchBookByAuthor(scanner);
            case "3" -> foundBooks = searchBookById(scanner);
            default -> {
                return;
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
        System.out.println(" - To return to the main menu enter any other symbol");
        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        if ("1".equals(input)) {
            processOfSearchingABook();
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
        long id;
        System.out.print(" - Enter id: ");
        try {
            id = Long.parseLong(scanner.nextLine());
            return library.searchBooksById(id);
        } catch (IllegalArgumentException exception) {
            System.out.println("Incorrect id!");
            return new ArrayList<>();
        }
    }

    protected void processOfViewingBooks() {
        String input;

        int page = 1;
        int numberOfPages = (int) Math.ceil((double) library.getNumberOfBooks() / 15);
        int startIndex;
        int endIndex;

        boolean exitView = false;

        while (!exitView) {
            startIndex = (page - 1) * 15;
            endIndex = startIndex + 14;

            System.out.println(indent + "-------------    All books    ---------------");
            System.out.println("Page " + page + " of " + numberOfPages + "\n");

            if (!(endIndex >= library.getNumberOfBooks())) {
                library.printBooks(startIndex, endIndex);
            } else {
                library.printBooks(startIndex, library.getNumberOfBooks() - 1);
                for (int i = library.getNumberOfBooks(); i <= endIndex; i++) {
                    System.out.println();
                }
            }
            System.out.println();


            if (numberOfPages <= 1) {
                System.out.println(" - To return to the main menu enter any symbol");
                System.out.print("\nEnter here: ");
                scanner.nextLine();
                exitView = true;
            } else if (page == 1) {
                System.out.println("                                                      - To view next page enter \"+\"");
                System.out.println(" - To return to the main menu enter any other symbol");
                System.out.print("\nEnter here: ");
                input = scanner.nextLine();
                if ("+".equals(input)) {
                    page++;
                } else {
                    exitView = true;
                }
            } else if (page < numberOfPages) {
                System.out.println("- To view previous page enter \"-\"                   - To view next page enter \"+\"");
                System.out.println(" - To return to the main menu enter any other symbol");
                System.out.print("\nEnter here: ");
                input = scanner.nextLine();
                switch (input) {
                    case "-" -> page--;
                    case "+" -> page++;
                    default -> exitView = true;
                }
            } else {
                System.out.println("- To view previous page enter \"-\"");
                System.out.println(" - To return to the main menu enter any other symbol");
                System.out.print("\nEnter here: ");
                input = scanner.nextLine();
                if ("-".equals(input)) {
                    page--;
                } else {
                    exitView = true;
                }
            }
        }
    }

    private void processOfSuggestABook(){
        String adminEmail = library.getAdminEmail();
        ////////////////////////send email
    }
}
