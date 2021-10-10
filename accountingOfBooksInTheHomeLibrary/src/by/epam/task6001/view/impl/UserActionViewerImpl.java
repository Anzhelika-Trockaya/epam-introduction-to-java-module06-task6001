package by.epam.task6001.view.impl;

import by.epam.task6001.bean.Book;
import by.epam.task6001.bean.User;
import by.epam.task6001.view.BookViewer;
import by.epam.task6001.view.UserActionViewer;
import by.epam.task6001.view.ViewProvider;

import java.util.List;

public class UserActionViewerImpl implements UserActionViewer {
    @Override
    public String getExceptionResponse(String s) {
        String response;
        response="-1 It is not possible to perform an action!\n"+s;
        return response;
    }

    @Override
    public String getAuthorizationResponse(User currentUser) {
        if(currentUser!=null){
            return "0 "+currentUser.getRole().name()+" Authorization was successful!";
        } else{
            return "1 Incorrect login or password!";
        }
    }

    @Override
    public String getExceptionResponse(Exception exception) {
        return "-1 An error has occurred!\n"+exception.getMessage();
    }

    @Override
    public String getAddBookResponse(boolean isAdded) {
        if(isAdded) {
            return "0 Book is added.";
        } else{
            return "1 Book is not added! Incorrect data of book!";
        }
    }

    @Override
    public String getRemoveBookResponse(boolean isRemoved) {
        if(isRemoved) {
            return "0 Book is removed.";
        } else{
            return "1 Book is not removed! Incorrect parameter value!";
        }
    }

    @Override
    public String getSearchBookResponse(String param, List<Book> books) {
        String response;
        String booksListString;
        ViewProvider viewProvider;
        BookViewer bookViewer;

        viewProvider=ViewProvider.getInstance();
        bookViewer=viewProvider.getBookViewer();

        booksListString=bookViewer.listOfBooksString(books);

        if(!booksListString.isEmpty()) {
            response = "0\nBooks with " + param + "\n" + booksListString;
        } else{
            response = "1 No books!";
        }

        return response;
    }

    @Override
    public String getViewBooksResponse(List<Book> books) {
        String response;
        String booksListString;
        ViewProvider viewProvider;
        BookViewer bookViewer;

        viewProvider=ViewProvider.getInstance();
        bookViewer=viewProvider.getBookViewer();

        booksListString=bookViewer.listOfBooksString(books);

        if(!booksListString.isEmpty()) {
        response="0\nAll books\n"+booksListString;
        } else{
            response = "1 No books!";
        }

        return response;
    }

    @Override
    public String getCountQuantityOfBooksResponse(int quantity) {
        String response;

        response="0 quantity="+quantity;

        return response;
    }

    @Override
    public String getSuggestBookResponse(boolean isSuggestSent) {
        if(isSuggestSent) {
            return "0 Suggestion sent.";
        } else{
            return "1 Suggestion not sent! Incorrect data of book!";
        }
    }
}
