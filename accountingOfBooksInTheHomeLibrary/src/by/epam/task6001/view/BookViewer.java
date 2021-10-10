package by.epam.task6001.view;

import by.epam.task6001.bean.Book;

import java.util.List;

public interface BookViewer {
    String bookString(Book book);
    String listOfBooksString(List<Book> books);
}
