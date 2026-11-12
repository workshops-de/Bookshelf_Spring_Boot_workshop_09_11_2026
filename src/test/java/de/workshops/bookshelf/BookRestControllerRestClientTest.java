package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
class BookRestControllerRestClientTest {

    @Autowired
    RestTestClient restClient;

    @Test
    void GET_book_returns_all_books() {
        Book[] books = restClient
                .get().uri("/book")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Book[].class).getResponseBody();

        assertThat(books).hasSize(3);
    }

    @Test
    @DirtiesContext
    void POST_book_saves_new_book() {
        Book newBook = BookTestData.book();

        restClient
                .post().uri("/book")
                .body(newBook)
                .exchange()
                .expectStatus().isCreated();

        Book savedBook = restClient
                .get().uri("/book/{isbn}", newBook.getIsbn())
                .exchangeSuccessfully()
                .returnResult(Book.class).getResponseBody();

        assertThat(savedBook).usingRecursiveComparison().isEqualTo(newBook);
    }
}
