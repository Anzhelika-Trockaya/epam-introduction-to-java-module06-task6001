package by.epam.task6001.controller.impl.command;

import by.epam.task6001.bean.Book;
import by.epam.task6001.bean.BookGenre;

public class BookCreator {
    public BookCreator() {
    }

    public Book createBook(String[] params) {
        Book book;
        String title;
        String author;
        BookGenre bookGenre;
        int pages;
        boolean isElectronic;

        if (areParamsCorrect(params)) {
            bookGenre = BookGenre.valueOf(params[2].substring(10));
            title = params[0].substring(7, params[0].length() - 1);
            author = params[1].substring(8, params[1].length() - 1);
            pages = Integer.parseInt(params[3].substring(6));
            isElectronic = Boolean.parseBoolean(params[4].substring(13));

            book = new Book(0L, title, author, bookGenre, pages, isElectronic);
            return book;
        }


        return null;
    }

    private boolean areParamsCorrect(String[] params) {
        String titleRegex;
        String authorRegex;
        String bookGenreRegex;
        String pagesRegex;
        String isElectronicRegex;

        String bookGenreString;

        titleRegex = "title=\".+\"";
        authorRegex = "author=\".+\"";
        bookGenreRegex = "bookGenre=[A-Z_]+";
        pagesRegex = "pages=[1-9][0-9]*";
        isElectronicRegex = "isElectronic=(true|false)";

        if (params.length == 5) {
            bookGenreString = params[2].substring(10);

            return params[0].matches(titleRegex)
                    && params[1].matches(authorRegex)
                    && params[2].matches(bookGenreRegex)
                    && params[3].matches(pagesRegex)
                    && params[4].matches(isElectronicRegex)
                    && existBookGenre(bookGenreString);
        }

        return false;
    }

    private boolean existBookGenre(String genre) {
        for (BookGenre bookGenre : BookGenre.values()) {
            if (genre.equals(bookGenre.name())) {
                return true;
            }
        }

        return false;
    }
}
