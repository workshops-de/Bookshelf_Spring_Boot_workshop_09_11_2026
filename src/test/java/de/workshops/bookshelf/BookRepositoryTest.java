package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void fetches_all_books() {
        List<Book> allBooks = bookRepository.getAllBooks();
        assertThat(allBooks).isNotEmpty();
    }

    @Test
    @DirtiesContext
    void saves_new_book() {
        List<Book> allBooksBeforeSave = bookRepository.getAllBooks();

        Book newBook = BookTestData.book();
        bookRepository.saveBook(newBook);

        List<Book> allBooksAfterSave = bookRepository.getAllBooks();
        assertThat(allBooksAfterSave).hasSize(allBooksBeforeSave.size() + 1);
    }
}
