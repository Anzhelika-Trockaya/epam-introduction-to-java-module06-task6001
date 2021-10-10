package by.epam.task6001.main.menu;

import by.epam.task6001.controller.Controller;
import by.epam.task6001.controller.ControllerException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageView {

    private final Controller controller;
    private final String titleOfBooks;
    private final int quantityOnPage;
    private final String requestsStart;
    private int currentPageNumber;
    private int quantityOfPages;

    public PageView(Controller controller, String titleOfBooks, String requestsStart, int quantityOnPage) {
        this.controller = controller;
        this.titleOfBooks = titleOfBooks;
        this.quantityOnPage = quantityOnPage;
        this.requestsStart = requestsStart;
        currentPageNumber = 0;
        quantityOfPages = 0;
    }

    public String getFirstPage() throws ControllerException {
        currentPageNumber = 1;
        countQuantityOfPages();

        return getCurrentPageString();
    }

    private String getCurrentPageString() {
        StringBuilder currentPageBuilder;
        String response;

        response = getCurrentPageResponse();

        if (response.startsWith("0\n")) {
            currentPageBuilder = new StringBuilder();

            currentPageBuilder.append(titleOfBooks).append('\n')
                    .append(responseToTable(response))
                    .append("\n                                 ")
                    .append("page ").append(currentPageNumber).append(" of ").append(quantityOfPages)
                    .append("\n");

            return currentPageBuilder.toString();
        } else {
            return response;
        }
    }

    private String responseToTable(String response) {
        String format;
        StringBuilder tableBuilder;
        String line;
        String booksStrings;

        tableBuilder = new StringBuilder();

        line = "---------------------------------------------------------------------------------------------" +
                "--------------------------------";
        format = "| %6s | %40s | %25s | %15s | %5s | %15s |";

        response = response.substring(2);
        booksStrings = getBooksFormattedStrings(response, format, line);

        tableBuilder.append('\n');
        tableBuilder.append(line)
                .append('\n')
                .append(String.format(format, "id", "title", "author", "genre", "pages", "is electronic"))
                .append('\n')
                .append(line)
                .append('\n');
        tableBuilder.append(booksStrings);

        return tableBuilder.toString();
    }

    private String getBooksFormattedStrings(String response, String format, String line) {
        StringBuilder tableBuilder;
        String currentBookLine;
        String bookRegex;
        Pattern bookPattern;
        Matcher bookMatcher;

        tableBuilder = new StringBuilder();

        bookRegex = "id=([0-9]+)\\stitle=\"(.+)\"\\sauthor=\"(.+)\"\\sbookGenre=([a-zA-Z_]+)\\spages=([0-9]+)" +
                "\\sisElectronic=(true|false)";
        bookPattern = Pattern.compile(bookRegex);
        bookMatcher = bookPattern.matcher(response);

        while (bookMatcher.find()) {
            currentBookLine = String.format(format
                    , bookMatcher.group(1)
                    , bookMatcher.group(2)
                    , bookMatcher.group(3)
                    , bookMatcher.group(4)
                    , bookMatcher.group(5)
                    , bookMatcher.group(6));

            tableBuilder.append(currentBookLine).append('\n').append(line).append('\n');
        }

        return tableBuilder.toString();
    }

    private String getCurrentPageResponse() {
        String request;
        String response;
        int quantityToSkip;

        quantityToSkip = (currentPageNumber - 1) * quantityOnPage;
        request = requestsStart + " quantityToReturn=" + quantityOnPage + " quantityToSkip=" + quantityToSkip;
        response = controller.doAction(request);

        return response;
    }

    private int countQuantityOfBooks() throws ControllerException {
        int quantityOfBooks;

        String request;
        String response;
        String parameterString;

        request = "countQuantity";

        if (requestsStart.startsWith("searchBooks ")) {
            parameterString = requestsStart.substring(11);
            request = request + parameterString;
        }

        response = controller.doAction(request);

        if (response.startsWith("0 quantity=")) {
            quantityOfBooks = Integer.parseInt(response.substring(11));
            return quantityOfBooks;
        } else {
            throw new ControllerException(response);
        }

    }

    private void countQuantityOfPages() throws ControllerException {
        int quantityOfBooks;

        quantityOfBooks = countQuantityOfBooks();

        if (quantityOfBooks > 0) {
            quantityOfPages = (int) Math.ceil((double) quantityOfBooks / quantityOnPage);
        } else {
            quantityOfPages = 1;
        }
    }

    public boolean isNextPage() {
        return currentPageNumber < quantityOfPages;
    }

    public boolean isPrevPage() {
        return currentPageNumber > 1;
    }

    public String getNextPage() {
        currentPageNumber++;
        return getCurrentPageString();
    }

    public String getPrevPage() {
        currentPageNumber--;
        return getCurrentPageString();
    }
}
