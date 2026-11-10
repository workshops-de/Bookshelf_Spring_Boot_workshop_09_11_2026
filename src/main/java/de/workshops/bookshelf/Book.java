package de.workshops.bookshelf;

import lombok.Data;

@Data
public class Book {

    private String title;
    private String description;
    private String author;
    private String isbn;

    public Book(String title, String description, String author, String isbn) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.isbn = isbn;
    }
}
