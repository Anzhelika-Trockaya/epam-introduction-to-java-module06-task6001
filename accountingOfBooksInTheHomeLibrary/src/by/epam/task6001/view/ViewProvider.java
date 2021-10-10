package by.epam.task6001.view;

import by.epam.task6001.view.impl.BookViewerImpl;
import by.epam.task6001.view.impl.UserActionViewerImpl;

public class ViewProvider {
    private static final ViewProvider instance = new ViewProvider();
    private final UserActionViewer userActionViewer;
    private final BookViewer bookViewer;

    private ViewProvider(){
        userActionViewer=new UserActionViewerImpl();
        bookViewer=new BookViewerImpl();
    }

    public static ViewProvider getInstance(){
        return instance;
    }

    public UserActionViewer getUserActionViewer(){
        return  userActionViewer;
    }

    public BookViewer getBookViewer() {
        return bookViewer;
    }
}
