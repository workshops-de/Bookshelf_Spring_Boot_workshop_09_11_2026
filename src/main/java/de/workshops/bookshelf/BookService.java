package de.workshops.bookshelf;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.getAllBooks();
    }

    public Book getSingleBook(String isbn) {
        return repository.getAllBooks().stream()
                .filter(book -> hasIsbn(book, isbn))
                .findFirst()
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
        repository.saveBook(book);
    }
}
