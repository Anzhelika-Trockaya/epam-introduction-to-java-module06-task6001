package com.epam.task6001.logic;

import com.epam.task6001.model.Book;
import com.epam.task6001.model.BookGenre;

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
        this.scanner = scanner;
        while (true) {
            printMainMenuText();
            processInputInMainMenu();
        }
    }

    protected void printMainMenuText() {
        System.out.println(MenuText.UserMainMenu.getText());
    }

    protected void processInputInMainMenu() throws IOException {
        String input;
        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        switch (input) {
            case "1" -> processOfViewingBooks("-------------    All books    ---------------", library.getBooks());
            case "2" -> processOfSearchingABook();
            case "3" -> processOfSuggestABook();
            case "0" -> library.finish();
            default -> System.out.println("No such action " + input + "!");
        }
    }

    protected void processOfSearchingABook() {
        String input;
        ArrayList<Book> foundBooks;

        System.out.println(MenuText.SearchBookMenu.getText());
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
            processOfViewingBooks("\n\n--------     Found books:     --------", foundBooks);
        } else {
            System.out.println("No books found!");
        }

        System.out.println("\n\n - To search another book enter 1");
        System.out.println(" - To return enter any other symbol");
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

    protected void processOfViewingBooks(String categoryName, ArrayList<Book> books) {
        String input;

        int page = 1;
        int numberOfPages = (int) Math.ceil((double) books.size() / 15);
        int startIndex;
        int endIndex;

        boolean exitView = false;

        while (!exitView) {
            startIndex = (page - 1) * 15;
            endIndex = startIndex + 14;

            System.out.println(indent + categoryName);
            System.out.println("Page " + page + " of " + numberOfPages + "\n");

            if (!(endIndex >= books.size())) {
                printBooks(books, startIndex, endIndex);
            } else {
                printBooks(books, startIndex, books.size() - 1);
                for (int i = library.getNumberOfBooks(); i <= endIndex; i++) {
                    System.out.println();
                }
            }
            System.out.println();


            if (numberOfPages <= 1) {
                System.out.println(" - To return enter any symbol");
                System.out.print("\nEnter here: ");
                scanner.nextLine();
                exitView = true;
            } else if (page == 1) {
                System.out.println("                                                 NEXT \"+\"");
                System.out.println(" - To return enter any other symbol");
                System.out.print("\nEnter here: ");
                input = scanner.nextLine();
                if ("+".equals(input)) {
                    page++;
                } else {
                    exitView = true;
                }
            } else if (page < numberOfPages) {
                System.out.println("    PREVIOUS \"-\"                               NEXT \"+\"");
                System.out.println(" - To return enter any other symbol");
                System.out.print("\nEnter here: ");
                input = scanner.nextLine();
                switch (input) {
                    case "-" -> page--;
                    case "+" -> page++;
                    default -> exitView = true;
                }
            } else {
                System.out.println("    PREVIOUS \"-\"");
                System.out.println(" - To return enter any other symbol");
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


    public void printBooks(ArrayList<Book> books, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            System.out.println(books.get(i));
        }
    }

    private void processOfSuggestABook() {
        String input;
        Book book;

        System.out.println(indent + "---------------    Suggest a book    ---------------");
        try {
            book = enterBook();
            if (library.isNotExistBook(book)) {
                sendSuggestion(book);
            } else {
                System.out.println(" - The suggestion not sent! Book with this com.epam.task6001.data is exist! -");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(" - The suggestion not sent! Incorrect com.epam.task6001.data of book! - ");
        }

        System.out.println("\n\n - To suggest another book enter 1");
        System.out.println(" - To return enter any other symbol");
        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        if ("1".equals(input)) {
            processOfSuggestABook();
        }
    }

    private void sendSuggestion(Book book) {
        String subject = "The home library";
        String text = library.getCurrentUser().getName() + " suggest the book:\n" + book;
        try {
            System.out.println(" - Sending messages. Loading ...");
            MailUtil.sendMail(Library.getLibraryMailAddress(), Library.getLibraryMailPassword(), library.getAdminsEmails(), subject, text);
            System.out.println(" - The suggestion sent to the administrator. -");
        } catch (Exception ex) {
            String input;
            System.out.println(" - The suggestion not sent! -\n");
            System.out.println(" - To try again enter 1");
            System.out.println(" - To return enter any other symbol");
            System.out.print("\nEnter here:");

            input = scanner.nextLine();
            if (input.equals("1")) {
                sendSuggestion(book);
            }
        }
    }

    protected Book enterBook() {
        String title;
        String author;
        String genre;
        String pages;
        String isElectronic;
        Book book;

        System.out.println("Enter book com.epam.task6001.data: ");
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

        book = new Book(title, author, BookGenre.valueOf(genre), Integer.parseInt(pages), Boolean.parseBoolean(isElectronic));

        return book;
    }
}
