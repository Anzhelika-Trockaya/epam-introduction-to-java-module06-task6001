package by.epam.task6001.view.impl;

import by.epam.task6001.bean.Book;
import by.epam.task6001.view.BookViewer;

import java.util.List;

public class BookViewerImpl implements BookViewer {
    @Override
    public String bookString(Book book) {
        StringBuilder bookStringBuilder;

        bookStringBuilder = new StringBuilder();

        bookStringBuilder.append("id=").append(book.getId()).append(" ");
        bookStringBuilder.append("title=\"").append(book.getTitle()).append("\" ");
        bookStringBuilder.append("author=\"").append(book.getAuthor()).append("\" ");
        bookStringBuilder.append("bookGenre=").append(book.getGenre().name()).append(" ");
        bookStringBuilder.append("pages=").append(book.getPages()).append(" ");
        bookStringBuilder.append("isElectronic=").append(book.isElectronic());

        return bookStringBuilder.toString();
    }

    @Override
    public String listOfBooksString(List<Book> books) {
        StringBuilder listStringBuilder;

        listStringBuilder = new StringBuilder();

        if (books.size() > 0) {
            listStringBuilder.append(bookString(books.get(0)));
        }

        for (int i = 1; i < books.size(); i++) {
            listStringBuilder.append("\n").append(bookString(books.get(i)));
        }

        return listStringBuilder.toString();
    }
}
