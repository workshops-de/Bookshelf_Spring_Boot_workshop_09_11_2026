package de.workshops.bookshelf;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URL;

@Validated
@ConfigurationProperties("bookshelf")
@Getter
@Setter
public class BookshelfProperties {

    /**
     * The owner of the bookshelf.
     */
    private String owner;

    @Valid
    private IsbnLookup isbnLookup;

    @Getter
    @Setter
    public static class IsbnLookup {

        private URL url;

        /**
         * The API key for the ISBN lookup service.
         */
        private String apiKey;
    }
}
