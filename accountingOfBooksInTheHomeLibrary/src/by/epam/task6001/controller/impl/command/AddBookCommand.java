package by.epam.task6001.controller.impl.command;

import by.epam.task6001.bean.Book;
import by.epam.task6001.bean.BookGenre;
import by.epam.task6001.service.LibraryService;
import by.epam.task6001.service.ServiceException;
import by.epam.task6001.service.ServiceProvider;
import by.epam.task6001.view.UserActionViewer;
import by.epam.task6001.view.ViewProvider;

public class AddBookCommand implements Command {
    @Override
    public String execute(String[] params) {
        String response;
        Book book;
        BookCreator bookCreator;
        ServiceProvider serviceProvider;
        LibraryService libraryService;
        ViewProvider viewProvider;
        UserActionViewer viewer;

        bookCreator=new BookCreator();
        book = bookCreator.createBook(params);
        viewProvider = ViewProvider.getInstance();
        viewer = viewProvider.getUserActionViewer();


        if (book != null) {
            serviceProvider = ServiceProvider.getInstance();
            libraryService = serviceProvider.getLibraryService();

            try {
                libraryService.addBook(book);
                response = viewer.getAddBookResponse(true);

            } catch (ServiceException serviceException) {
                response = viewer.getExceptionResponse(serviceException);
            }

        } else {
            response = viewer.getAddBookResponse(false);
        }


        return response;
    }


}
