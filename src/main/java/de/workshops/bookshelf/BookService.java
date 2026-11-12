package de.workshops.bookshelf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookService {

    private final BookRepository repository;

    private final BookshelfProperties properties;

    public BookService(BookRepository repository, BookshelfProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    public List<Book> getAllBooks() {
        return repository.getAllBooks();
    }

    public Book getSingleBook(String isbn) {
       return repository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Unknown ISBN: " + isbn));
    }

    public List<Book> searchBookByAuthor(String author) {
        return repository.getAllBooks().stream()
                .filter(book -> hasAuthor(book, author))
                .toList();
    }

    public List<Book> searchBooks(BookSearchRequest request) {
        return repository.getAllBooks().stream()
                .filter(book -> request.getAuthor() == null || hasAuthor(book, request.getAuthor()))
                .filter(book -> request.getIsbn() == null || hasIsbn(book, request.getIsbn()))
                .toList();
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }

    public void saveBook(Book book) {
        log.info("ISBN lookup as {} using {} ...", properties.getOwner(), properties.getIsbnLookup().getUrl());

        repository.saveBook(book);
    }
}
