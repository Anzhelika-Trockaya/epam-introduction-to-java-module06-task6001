package by.epam.task6001.controller.impl.command;

import by.epam.task6001.service.LibraryService;
import by.epam.task6001.service.ServiceException;
import by.epam.task6001.service.ServiceProvider;
import by.epam.task6001.view.UserActionViewer;
import by.epam.task6001.view.ViewProvider;

public class RemoveBookCommand implements Command {
    @Override
    public String execute(String[] params) {
        String response;
        long id;

        ServiceProvider serviceProvider;
        LibraryService libraryService;
        ViewProvider viewProvider;
        UserActionViewer viewer;

        id = takeId(params);
        viewProvider = ViewProvider.getInstance();
        viewer = viewProvider.getUserActionViewer();

        if (id > 0) {
            serviceProvider = ServiceProvider.getInstance();
            libraryService = serviceProvider.getLibraryService();

            try {
                libraryService.removeBook(id);
                response = viewer.getRemoveBookResponse(true);

            } catch (ServiceException serviceException) {
                response = viewer.getExceptionResponse(serviceException);
            }

        } else {
            response = viewer.getRemoveBookResponse(false);
        }

        return response;
    }

    private long takeId(String[] params) {
        long id = -1;
        String idRegex;

        idRegex = "id=[1-9][0-9]*";
        if (params.length == 1 && params[0].matches(idRegex)) {
            id = Long.parseLong(params[0].substring(3));
        }

        return id;
    }
}
