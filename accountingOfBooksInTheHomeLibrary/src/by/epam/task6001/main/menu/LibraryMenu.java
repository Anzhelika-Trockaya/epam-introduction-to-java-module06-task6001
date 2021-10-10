package by.epam.task6001.main.menu;

import by.epam.task6001.controller.Controller;
import by.epam.task6001.controller.ControllerException;

public abstract class LibraryMenu {
    private final UserInput input;
    private final Controller controller;

    public UserInput getInput() {
        return input;
    }

    public Controller getController() {
        return controller;
    }

    public LibraryMenu(UserInput input, Controller controller){
        this.input = input;
        this.controller=controller;
    }

    abstract public void startMainMenu();

    public void searchBooks() {
        int quantityOnPage;
        String requestsStart;
        PageView pageView;

        requestsStart = "searchBooks " + getParameterOfSearch();
        quantityOnPage = 5;
        pageView = new PageView(getController(), MenuText.FoundBooksTitle.getText(), requestsStart, quantityOnPage);

        System.out.println();
        processPageView(pageView);
    }

    private String getParameterOfSearch() {
        String parameter;
        String action;
        String input;

        System.out.println(MenuText.SearchBookMenu.getText());
        System.out.println();

        while (true) {
            action = getInput().readNextLine("Enter number of action: ");

            switch (action) {
                case "1" -> {
                    input = getInput().readNextLine("Enter title: ");
                    parameter = "title=\"" + input + "\"";
                    return parameter;
                }
                case "2" -> {
                    input = getInput().readNextLine("Enter author: ");
                    parameter = "author=\"" + input + "\"";
                    return parameter;
                }
                case "3" -> {
                    input = getInput().readNextLine("Enter id: ");
                    parameter = "id=" + input;
                    return parameter;
                }
                default -> System.out.println("Incorrect number of action!");
            }
        }
    }

    public void viewBooks() {
        int quantityOnPage;
        String requestsStart;
        PageView pageView;

        requestsStart = "viewBooks";
        quantityOnPage = 5;
        pageView = new PageView(getController(), MenuText.ViewCommandTitle.getText(), requestsStart, quantityOnPage);

        processPageView(pageView);
    }

    private void processPageView(PageView pageView) {
        String action;

        try {
            System.out.println(pageView.getFirstPage());

            do {
                printPageViewMenu(pageView);
                action = getInput().readNextLine("Enter here: ");

                switch (action) {
                    case "-" -> {
                        if (pageView.isPrevPage()) {
                            System.out.println(pageView.getPrevPage());
                        } else{
                            System.out.println("Incorrect action!");
                        }
                    }
                    case "+" -> {
                        if (pageView.isNextPage()) {
                            System.out.println(pageView.getNextPage());
                        } else{
                            System.out.println("Incorrect action!");
                        }
                    }
                    case "0" -> {
                    }
                    default ->  System.out.println("Incorrect action!");
                }
            } while (!action.equals("0"));


        } catch (ControllerException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void printPageViewMenu(PageView pageView) {
        if (pageView.isNextPage() && pageView.isPrevPage()) {
            System.out.println("PREV PAGE \"-\"                               NEXT PAGE \"+\"\n");
        } else if (pageView.isNextPage() && !pageView.isPrevPage()) {
            System.out.println("                                              NEXT PAGE \"+\"\n");
        } else if (!pageView.isNextPage() && pageView.isPrevPage()) {
            System.out.println("PREV PAGE \"-\"\n");
        }
        System.out.println("\nMENU \"0\"");
    }

    public void exit() {
        System.exit(0);
    }

    public String enterBookString(){
        StringBuilder bookBuilder;
        String userInput;

        bookBuilder=new StringBuilder();

        bookBuilder.append("title=\"");
        userInput=getInput().readNextLine("\nEnter title: ");
        bookBuilder.append(userInput);
        bookBuilder.append("\"");
        bookBuilder.append(" author=\"");
        userInput=getInput().readNextLine("Enter author: ");
        bookBuilder.append(userInput);
        bookBuilder.append("\"");
        bookBuilder.append(" bookGenre=");
        userInput=getInput().readNextLine("Enter genre(TO LOWER CASE): ");
        userInput=userInput.replaceAll(" ","");
        bookBuilder.append(userInput);
        bookBuilder.append(" pages=");
        userInput=getInput().readNextLine("Enter number of pages: ");
        bookBuilder.append(userInput);
        bookBuilder.append(" isElectronic=");
        userInput=getInput().readNextLine("Is book electronic (true|false): ");
        bookBuilder.append(userInput);

        return bookBuilder.toString();
    }
}
