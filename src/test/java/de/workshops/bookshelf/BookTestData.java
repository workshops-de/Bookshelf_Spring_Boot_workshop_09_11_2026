package de.workshops.bookshelf;

import java.util.List;

public class BookTestData {

    public static Book book() {
        return new Book("Spring Boot 2", "A book about Spring Boot", "Michael Simons", "978-3-86490-525-4");
    }

    public static List<Book> books() {
        return List.of(
                new Book("Spring Boot 2", "A book about Spring Boot", "Michael Simons", "978-3-86490-525-4"),
                new Book("Java ist auch eine Insel", "A book about Java", "Christian Ullenboom", "978-3-367-11134-3"),
                new Book("IntelliJ IDEA in Action", "A book about IntelliJ IDEA", "Duane K. Fields, Stephen Saunders, Eugene Belyaev", "978-1-932394-44-3")
        );
    }
}
