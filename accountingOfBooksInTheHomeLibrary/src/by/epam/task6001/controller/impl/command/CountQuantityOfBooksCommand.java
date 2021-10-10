package by.epam.task6001.controller.impl.command;

import by.epam.task6001.controller.ControllerException;
import by.epam.task6001.service.LibraryService;
import by.epam.task6001.service.ServiceException;
import by.epam.task6001.service.ServiceProvider;
import by.epam.task6001.view.UserActionViewer;
import by.epam.task6001.view.ViewProvider;

import java.util.Arrays;

public class CountQuantityOfBooksCommand implements Command {
    @Override
    public String execute(String[] params) {
        String response;
        int quantity;

        ViewProvider viewProvider;
        UserActionViewer viewer;

        viewProvider = ViewProvider.getInstance();
        viewer = viewProvider.getUserActionViewer();

        try {
            if (params == null) {
                quantity = countQuantityOfBooks("");
                response = viewer.getCountQuantityOfBooksResponse(quantity);
            } else if (params.length == 1) {
                quantity = countQuantityOfBooks(params[0]);
                response = viewer.getCountQuantityOfBooksResponse(quantity);
            } else {
                response = viewer.getExceptionResponse("Incorrect request params " + Arrays.toString(params));
            }
        } catch (ServiceException | ControllerException exception) {
            response = viewer.getExceptionResponse(exception);
        }

        return response;
    }

    private int countQuantityOfBooks(String parameter) throws ServiceException, ControllerException {
        int quantity;

        String searchParameterRegex;
        String parameterValue;

        ServiceProvider serviceProvider;
        LibraryService libraryService;

        searchParameterRegex = "((id=[1-9][0-9]*)|((title|author)=\".+\"))";
        serviceProvider = ServiceProvider.getInstance();
        libraryService = serviceProvider.getLibraryService();

        if(parameter.isEmpty()){
            quantity = libraryService.numberOfBooks();

        }else if (parameter.matches(searchParameterRegex)) {

            if (parameter.startsWith("id=")) {
                parameterValue = parameter.substring(3);
                quantity = libraryService.numberOfBooksWithId(Long.parseLong(parameterValue));

            } else if (parameter.startsWith("title=\"")) {
                parameterValue = parameter.substring(7, parameter.length() - 1);
                quantity = libraryService.numberOfBooksWithTitle(parameterValue);

            } else {
                parameterValue = parameter.substring(8, parameter.length() - 1);
                quantity = libraryService.numberOfBooksWithAuthor(parameterValue);
            }

        } else {
            throw new ControllerException("Incorrect parameter " + parameter);
        }

        return quantity;
    }

    private int takeQuantity(String parameter, String parameterBeginning) throws ControllerException {
        String quantityRegex;
        int quantity;

        quantityRegex = parameterBeginning + "[1-9][0-9]*";

        if (parameter.matches(quantityRegex)) {
            quantity = Integer.parseInt(parameter.substring(parameterBeginning.length()));
            return quantity;

        } else {
            throw new ControllerException("Incorrect parameter " + parameter);
        }
    }

}
