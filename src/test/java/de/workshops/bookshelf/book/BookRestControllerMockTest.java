package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerMockTest {

    @MockBean
    BookshelfService service;

    @Autowired
    BookRestController controller;

    @Test
    void testGetBookByIsbn() {
        final var expectedBook = new Book();
        expectedBook.setTitle("Some Title");

        when(service.getByIsbn(anyString())).thenReturn(expectedBook);
//        doThrow(Exception.class).when(service).getByIsbn(anyString());

        final var bookByIsbn = controller.getByIsbn("123456780");
        assertNotNull(bookByIsbn);
        assertEquals(expectedBook, bookByIsbn);
    }
}