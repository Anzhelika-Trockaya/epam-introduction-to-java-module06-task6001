package logic;

import model.Book;

import javax.mail.MessagingException;
import java.io.IOException;

public class AdminMenu extends UserMenu {

    public AdminMenu(Library library) {
       super(library);
    }

    protected void printMainMenuText() {
        System.out.println(MenuText.AdminMainMenu.getText());
    }

    protected void processInputInMainMenu() throws IOException {
        String input;

        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        switch (input) {
            case "1" -> processOfViewingBooks("-------------    All books    ---------------",library.getBooks());
            case "2" -> processOfSearchingABook();
            case "3" -> processOfAddingABook();
            case "4" -> processOfRemovingABook();
            case "0" -> library.finish();
            default -> System.out.println("No such action " + input + "!");
        }
    }

    private void processOfAddingABook() throws IOException {
        Book book;
        String input;

        System.out.println(indent + "---------------  Add book ---------------");

        try {
            book = enterBook();
            if(library.addBook(book)) {
                System.out.println(" - Book is added. - ");
                sendEmail(library.getOtherUsersEmails(),
                        "The home library",
                        "Administrator "+library.getCurrentUser().getName()+" added new book:\n"+book);
            } else{
                System.out.println(" - Book is not added! Maybe such a book already exists. - ");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(" - Book not added! Incorrect data of book! - ");
        } catch (MessagingException e) {
            System.out.println(" - Message not sent!");
        }

        System.out.println("\n\n - To add another book enter 1");
        System.out.println(" - To return enter any other symbol");
        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        if ("1".equals(input)) {
            processOfAddingABook();
        }
    }

    private void processOfRemovingABook() throws IOException {
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
        System.out.println(" - To return enter any other symbol");
        System.out.print("\nEnter here: ");
        input = scanner.nextLine();
        if ("1".equals(input)) {
            processOfRemovingABook();
        }
    }


}
