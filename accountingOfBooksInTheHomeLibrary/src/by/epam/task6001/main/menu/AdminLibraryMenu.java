package by.epam.task6001.main.menu;

import by.epam.task6001.controller.Controller;

public class AdminLibraryMenu extends LibraryMenu {
    public AdminLibraryMenu(UserInput input, Controller controller) {
        super(input, controller);
    }

    @Override
    public void startMainMenu() {
        String action;

        System.out.println(MenuText.AdminMainMenu.getText());
        action = getInput().readNextLine("Enter number of action: ");

        switch (action) {
            case "1" -> viewBooks();
            case "2" -> searchBooks();
            case "3" -> addBook();
            case "4" -> removeBook();
            case "0" -> exit();
            default -> System.out.println("Incorrect number of action");
        }

    }

    private void addBook() {
        String request;
        String response;

        System.out.println(MenuText.AddCommandTitle.getText());
        request="addBook "+enterBookString();
        response=getController().doAction(request);

        System.out.println(response);
    }

    private void removeBook() {
        String request;
        String response;
        String id;

        System.out.println(MenuText.RemoveCommandTitle.getText());
        id=getInput().readNextLine("Enter id: ");
        request="removeBook id="+id;
        response=getController().doAction(request);

        System.out.println(response);
    }
}
