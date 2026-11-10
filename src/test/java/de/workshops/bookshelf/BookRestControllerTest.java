package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookRestControllerTest {

    @MockitoBean
    BookService bookService;

    @Autowired
    BookRestController bookRestController;

    @Test
    void getAllBooks_returns_all_books() {
        List<Book> allBooks = BookTestData.books();
        when(bookService.getAllBooks()).thenReturn(allBooks);

        List<Book> books = bookRestController.getAllBooks();
        assertThat(books).hasSameSizeAs(allBooks);
    }

    @Test
    void getSingleBook_returns_book_by_isbn() {
        Book expectedBook = BookTestData.book();
        when(bookService.getSingleBook(expectedBook.getIsbn())).thenReturn(expectedBook);

        Book actualBook = bookRestController.getSingleBook(expectedBook.getIsbn());
        assertThat(actualBook).isEqualTo(expectedBook);
        assertThat(actualBook.getTitle()).isEqualTo(expectedBook.getTitle());
    }
}
