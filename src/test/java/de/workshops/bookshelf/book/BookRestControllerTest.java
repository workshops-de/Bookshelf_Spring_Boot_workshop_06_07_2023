package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookRestControllerTest {

    @Autowired
    BookRestController controller;

    @Test
    void shouldGetAllBooks() {
        final var allBooks = controller.getAllBooks();

        assertEquals(3, allBooks.size());
        assertThat(allBooks).hasSize(3);
    }
}