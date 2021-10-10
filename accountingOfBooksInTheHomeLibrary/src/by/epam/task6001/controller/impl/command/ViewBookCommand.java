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

public class ViewBookCommand implements Command{
    @Override
    public String execute(String[] params) {
        String response;
        List<Book> books;

        ViewProvider viewProvider;
        UserActionViewer viewer;


        viewProvider = ViewProvider.getInstance();
        viewer = viewProvider.getUserActionViewer();

        if (params.length == 2) {

            try {
                books = getBooks(params);

                response = viewer.getViewBooksResponse(books);

            } catch (ServiceException | ControllerException exception) {
                response = viewer.getExceptionResponse(exception);
            }

        } else {
            response = viewer.getExceptionResponse("Incorrect request params " + Arrays.toString(params));
        }

        return response;
    }

    private List<Book> getBooks(String[] params) throws ControllerException, ServiceException {
        List<Book> books;
        int quantityToSkip;
        int quantityToReturn;

        ServiceProvider serviceProvider;
        LibraryService libraryService;

        serviceProvider = ServiceProvider.getInstance();
        libraryService = serviceProvider.getLibraryService();

        quantityToReturn = takeQuantity(params[0], "quantityToReturn=");
        quantityToSkip = takeQuantity(params[1], "quantityToSkip=");
        books = libraryService.viewBooks(quantityToReturn, quantityToSkip);

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
