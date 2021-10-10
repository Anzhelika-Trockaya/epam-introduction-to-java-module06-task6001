package by.epam.task6001.controller.impl.command;

import by.epam.task6001.bean.Book;
import by.epam.task6001.service.LibraryService;
import by.epam.task6001.service.ServiceException;
import by.epam.task6001.service.ServiceProvider;
import by.epam.task6001.view.UserActionViewer;
import by.epam.task6001.view.ViewProvider;

public class SuggestBookCommand implements Command {
    @Override
    public String execute(String[] params) {
        String response;
        String userName;
        String[] bookParams;
        Book book;
        BookCreator bookCreator;

        ServiceProvider serviceProvider;
        LibraryService libraryService;
        ViewProvider viewProvider;
        UserActionViewer viewer;

        viewProvider = ViewProvider.getInstance();
        viewer = viewProvider.getUserActionViewer();

        if (params.length == 6) {

            userName = takeUserName(params[0]);

            bookParams = takeBookParams(params);
            bookCreator = new BookCreator();
            book = bookCreator.createBook(bookParams);

            if (userName != null && book != null) {

                serviceProvider = ServiceProvider.getInstance();
                libraryService = serviceProvider.getLibraryService();

                try {
                    libraryService.suggestBook(userName, book);
                    response = viewer.getSuggestBookResponse(true);

                } catch (ServiceException serviceException) {
                    response = viewer.getExceptionResponse(serviceException);
                }

            } else {
                response = viewer.getSuggestBookResponse(false);
            }

        } else {
            response = viewer.getSuggestBookResponse(false);
        }


        return response;
    }

    private String[] takeBookParams(String[] params) {
        String[] bookParams;

        bookParams = new String[params.length - 1];
        System.arraycopy(params, 1, bookParams, 0, bookParams.length);

        return bookParams;
    }

    private String takeUserName(String parameter) {
        String userName;
        String userNameRegex;

        userName=null;
        userNameRegex="userName=\".+\"";

        if(parameter.matches(userNameRegex)){
            userName=parameter.substring(10, parameter.length()-1);
        }

        return userName;
    }
}
