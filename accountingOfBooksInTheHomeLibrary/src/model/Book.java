package model;

import java.util.Objects;

public class Book {
    private static long currentId = 1;
    private long id;
    private String title;
    private String author;
    private BookGenre genre;
    private int pages;
    private final boolean isElectronic;

    public Book(long id, String title, String author, BookGenre genre, int pages, boolean isElectronic) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
        if (currentId <= id) {
            currentId = id + 1;
        }
        this.isElectronic=isElectronic;
    }

    public Book(String title, String author, BookGenre genre, int pages, boolean isElectronic) {
        if (!title.isEmpty() && !author.isEmpty() && pages > 0) {
            this.id = currentId;
            currentId++;
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.pages = pages;
            this.isElectronic=isElectronic;
        } else {
            throw new IllegalArgumentException("Incorrect data of book!");
        }
    }

    /*public static Book parseBook(){
        Book book;
        ///////
        return book;
    }*/

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

    public boolean isElectronic(){
        return isElectronic;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre=" + genre +
                ", pages=" + pages +
                ", isElectronic="+ isElectronic +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pages == book.pages &&
                isElectronic == book.isElectronic &&
                title.equals(book.title) &&
                author.equals(book.author) &&
                genre == book.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, genre, pages, isElectronic);
    }
}
