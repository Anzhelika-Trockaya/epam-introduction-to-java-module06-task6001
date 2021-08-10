package logic;

import model.Book;

import java.io.IOException;

public class AdminMenu extends UserMenu {

    public AdminMenu(Library library) {
       super(library);
    }

    protected void printMainMenu() {
        System.out.println(indent + "----------------   MENU   ----------------");
        System.out.println(" - To view books enter 1");
        System.out.println(" - To search book enter 2");
        System.out.println(" - To add book enter 3");
        System.out.println(" - To remove book enter 4");
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
            case "3" -> processOfAddingABook();
            case "4" -> processOfRemovingABook();
            case "0" -> library.finish();
            default -> System.out.println("No such action " + input + "!");
        }
    }

    private void processOfAddingABook() {
        String title;
        String author;
        String genre;
        String pages;
        Book book;
        String isElectronic;
        String input;
        boolean isAdded;

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
            if(library.addBook(book)) {
                System.out.println(" - Book is added. - ");
                //////////////////////////////////////send email
            } else{
                System.out.println(" - Book is not added! Maybe such a book already exists. - ");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(" - Book not added! Incorrect data of book! - ");
        }

        System.out.println("\n\n - To add another book enter 1");
        System.out.println(" - To return to the main menu enter any other symbol");
        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        if ("1".equals(input)) {
            processOfAddingABook();
        }
    }

    private void processOfRemovingABook() {
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
        System.out.println(" - To return to the main menu enter any other symbol");
        input = scanner.nextLine();
        if ("1".equals(input)) {
            processOfRemovingABook();
        }
    }


}
