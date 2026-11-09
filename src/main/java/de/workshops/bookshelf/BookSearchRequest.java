package de.workshops.bookshelf;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class BookSearchRequest {

    @Size(min = 3)
    private String author;

    @ISBN
    private String isbn;
}
