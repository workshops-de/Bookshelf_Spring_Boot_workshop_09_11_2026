package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
@Sql(executionPhase = BEFORE_TEST_CLASS, statements = "INSERT INTO bookshelf_user (username, password, role) VALUES ('user', '$2a$10$1uEdToOBpQU0EbLbERKSk.jBYWAu1w8onrzvkilFh73gZbsP1a2Xq', 'ROLE_USER'), ('admin', '$2a$10$DM3jskYOr3g.wNXNxtWJHOYjM60s90QmZF.qc6ithjfO8/EM0Nm.O', 'ROLE_ADMIN')")
@Sql(executionPhase = AFTER_TEST_CLASS, statements = "DELETE FROM bookshelf_user WHERE username IN ('user', 'admin')")
class BookRestControllerRestClientTest {

    @Autowired
    RestTestClient restClient;

    @Test
    void GET_book_returns_all_books() {
        Book[] books = restClient
                .get().uri("/book")
                .headers(headers -> headers.setBasicAuth("user", "password"))
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
                .headers(headers -> headers.setBasicAuth("admin", "admin"))
                .body(newBook)
                .exchange()
                .expectStatus().isCreated();

        Book savedBook = restClient
                .get().uri("/book/{isbn}", newBook.getIsbn())
                .headers(headers -> headers.setBasicAuth("user", "password"))
                .exchangeSuccessfully()
                .returnResult(Book.class).getResponseBody();

        assertThat(savedBook).usingRecursiveComparison().isEqualTo(newBook);
    }

    @Test
    void non_admin_cannot_save_new_book() {
        Book newBook = BookTestData.book();

        restClient
                .post().uri("/book")
                .headers(headers -> headers.setBasicAuth("user", "password"))
                .body(newBook)
                .exchange()
                .expectStatus().isForbidden();
    }
}
