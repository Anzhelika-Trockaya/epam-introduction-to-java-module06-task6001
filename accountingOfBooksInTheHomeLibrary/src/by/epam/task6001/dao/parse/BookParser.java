package by.epam.task6001.dao.parse;

import by.epam.task6001.bean.Book;
import by.epam.task6001.bean.BookGenre;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookParser {
    private static final BookParser instance = new BookParser();

    private BookParser() {
    }

    public static BookParser getInstance() {
        return instance;
    }

    public Book parse(String bookString) throws ParseException {
        Book book;
        long id;
        String title;
        String author;
        BookGenre genre;
        int pages;
        boolean isElectronic;

        String bookRegex;
        Pattern pattern;
        Matcher matcher;

        bookRegex = "(id=([1-9][0-9]*)\\s)?title=\"(.+)\"\\sauthor=\"(.+)\"\\sbookGenre=([A-Z_]+)" +
                "\\spages=([1-9][0-9]*)\\sisElectronic=(true|false)";
        pattern = Pattern.compile(bookRegex);
        matcher = pattern.matcher(bookString);

        if (matcher.find() && isExistGenre(matcher.group(5))) {

            if (!matcher.group(2).isEmpty()) {
                id = Long.parseLong(matcher.group(2));
            } else {
                id = 0;
            }

            title = matcher.group(3);
            author = matcher.group(4);
            genre = BookGenre.valueOf(matcher.group(5));
            pages = Integer.parseInt(matcher.group(6));
            isElectronic = Boolean.parseBoolean(matcher.group(7));

            book = new Book(id, title, author, genre, pages, isElectronic);

            return book;

        } else {
            throw new ParseException("Incorrect books string!");
        }

    }

    private boolean isExistGenre(String genre) {
        for (BookGenre bookGenre : BookGenre.values()) {
            if (genre.equals(bookGenre.toString())) {
                return true;
            }
        }
        return false;
    }

}
