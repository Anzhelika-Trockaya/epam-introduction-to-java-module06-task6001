package by.epam.task6001.main.menu;

import by.epam.task6001.controller.Controller;

public class UserLibraryMenu extends LibraryMenu {

    public UserLibraryMenu(UserInput input, Controller controller) {
        super(input, controller);
    }

    @Override
    public void startMainMenu() {
        String action;

        System.out.println(MenuText.UserMainMenu.getText());
        action = getInput().readNextLine("Enter number of action: ");

        switch (action) {
            case "1" -> viewBooks();
            case "2" -> searchBooks();
            case "3" -> suggestBook();
            case "0" -> exit();
            default -> System.out.println("Incorrect number of action");
        }

    }

    private void suggestBook() {
        String request;
        String response;

        System.out.println(MenuText.SuggestBookTitle);
        request="suggestBook "+enterBookString();
        response=getController().doAction(request);

        System.out.println(response);
    }
}
