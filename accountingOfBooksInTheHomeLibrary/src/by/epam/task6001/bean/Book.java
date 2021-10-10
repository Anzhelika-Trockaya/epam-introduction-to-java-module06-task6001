package by.epam.task6001.bean;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private long id;
    private String title;
    private String author;
    private BookGenre genre;
    private int pages;
    private boolean isElectronic;

    public Book() {
        this.id = 0;
        this.title = "";
        this.author = "";
        this.genre = BookGenre.OTHER;
        this.pages = 0;
        this.isElectronic = false;
    }

    public Book(long id, String title, String author, BookGenre genre, int pages, boolean isElectronic) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
        this.isElectronic = isElectronic;
    }

    public Book(long id, String title, String author, int pages, boolean isElectronic) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = BookGenre.OTHER;
        this.pages = pages;
        this.isElectronic = isElectronic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isElectronic() {
        return isElectronic;
    }

    public void setElectronic(boolean isElectronic) {
        this.isElectronic = isElectronic;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre=" + genre +
                ", pages=" + pages +
                ", isElectronic=" + isElectronic +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                pages == book.pages &&
                isElectronic == book.isElectronic &&
                title.equals(book.title) &&
                author.equals(book.author) &&
                genre == book.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre, pages, isElectronic);
    }
}
