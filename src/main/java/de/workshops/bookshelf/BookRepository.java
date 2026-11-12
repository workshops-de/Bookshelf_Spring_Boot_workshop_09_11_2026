package de.workshops.bookshelf;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {

    default List<Book> getAllBooks() {
        return findAll();
    }

    default void saveBook(Book book) {
        save(book);
    }

    Optional<Book> findByIsbn(String isbn);
}
