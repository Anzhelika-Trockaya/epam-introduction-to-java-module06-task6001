package by.epam.task6001.controller.impl.command;

import by.epam.task6001.bean.Book;
import by.epam.task6001.controller.ControllerException;
import by.epam.task6001.service.LibraryService;
import by.epam.task6001.service.ServiceException;
import by.epam.task6001.service.ServiceProvider;
import by.epam.task6001.view.UserActionViewer;
import by.epam.task6001.view.ViewProvider;

import java.util.Arrays;
import java.util.List;

public class SearchBookCommand implements Command {
    @Override
    public String execute(String[] params) {
        String response;
        List<Book> books;
        int quantityToSkip;
        int quantityToReturn;

        ViewProvider viewProvider;
        UserActionViewer viewer;

        viewProvider = ViewProvider.getInstance();
        viewer = viewProvider.getUserActionViewer();

        if (params.length == 3) {
            try {
                quantityToReturn = takeQuantity(params[1], "quantityToReturn=");
                quantityToSkip = takeQuantity(params[2], "quantityToSkip=");
                books = searchBooks(params[0], quantityToReturn, quantityToSkip);

                response = viewer.getSearchBookResponse(params[0], books);

            } catch (ServiceException | ControllerException exception) {
                response = viewer.getExceptionResponse(exception);
            }

        } else {
            response = viewer.getExceptionResponse("Incorrect request params " + Arrays.toString(params));
        }

        return response;
    }

    private List<Book> searchBooks(String parameter, int quantityToReturn, int quantityToSkip)
            throws ServiceException, ControllerException {

        List<Book> books;

        String searchParameterRegex;
        String parameterValue;

        ServiceProvider serviceProvider;
        LibraryService libraryService;

        searchParameterRegex = "((id=[1-9][0-9]*)|((title|author)=\".+\"))";
        serviceProvider = ServiceProvider.getInstance();
        libraryService = serviceProvider.getLibraryService();

        if (parameter.matches(searchParameterRegex)) {
            if (parameter.startsWith("id=")) {
                parameterValue = parameter.substring(3);
                books = libraryService.searchBooksById(Long.parseLong(parameterValue), quantityToReturn, quantityToSkip);

            } else if (parameter.startsWith("title=\"")) {
                parameterValue = parameter.substring(7, parameter.length() - 1);
                books = libraryService.searchBooksByTitle(parameterValue, quantityToReturn, quantityToSkip);

            } else {
                parameterValue = parameter.substring(8, parameter.length() - 1);
                books = libraryService.searchBooksByAuthor(parameterValue, quantityToReturn, quantityToSkip);
            }

        } else {
            throw new ControllerException("Incorrect parameter " + parameter);
        }

        return books;
    }

    private int takeQuantity(String parameter, String parameterBeginning) throws ControllerException {
        String quantityRegex;
        int quantity;

        quantityRegex = parameterBeginning+"[0-9]+";

        if (parameter.matches(quantityRegex)) {
            quantity = Integer.parseInt(parameter.substring(parameterBeginning.length()));
            return quantity;

        } else {
            throw new ControllerException("Incorrect parameter " + parameter);
        }
    }

}
