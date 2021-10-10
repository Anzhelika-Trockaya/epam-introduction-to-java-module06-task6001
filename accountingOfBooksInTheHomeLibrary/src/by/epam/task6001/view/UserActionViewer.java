package by.epam.task6001.view;

import by.epam.task6001.bean.Book;
import by.epam.task6001.bean.User;

import java.util.List;

public interface UserActionViewer {
    String getExceptionResponse(String s);

    String getAuthorizationResponse(User currentUser);

    String getExceptionResponse(Exception exception);

    String getAddBookResponse(boolean isAdded);

    String getRemoveBookResponse(boolean isRemoved);

    String getSearchBookResponse(String param, List<Book> books);

    String getViewBooksResponse(List<Book> books);

    String getCountQuantityOfBooksResponse(int quantity);

    String getSuggestBookResponse(boolean isSuggestSent);
}
