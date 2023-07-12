package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookshelfService service;

    @Captor
    ArgumentCaptor<String> captor;

    @Autowired
    ObjectMapper mapper;
    @Test
    void shouldGetAllBooks() throws Exception {
        when(service.getAllBooks()).thenReturn(List.of(new Book(), new Book(), new Book()));

        final var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final var contentAsJsonString = mvcResult.getResponse().getContentAsString();
        List<Book> books = mapper.readValue(contentAsJsonString, new TypeReference<>() {});
        assertEquals(3, books.size());
    }

    @Test
    void shouldGetByIsbn() throws Exception {
        when(service.getByIsbn(captor.capture())).thenReturn(new Book());

        final var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/book/978-3836211161"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final var contentAsJsonString = mvcResult.getResponse().getContentAsString();
        Book book = mapper.readValue(contentAsJsonString, new TypeReference<>() {});
        assertNotNull(book);

        assertEquals("978-3836211161", captor.getValue());
    }

    @Test
    void shouldCreateNewBook() throws Exception {
        final var expectedBook = new Book();
        when(service.addBook(any(CreateBookRequest.class))).thenReturn(expectedBook);
        final var mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .content("""
                                {
                                    "isbn": "123-4567890",
                                    "title": "My first Book",
                                    "author": "Birgit Kratz",
                                    "description": "My very first book"
                                }""")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final var contentAsJsonString = mvcResult.getResponse().getContentAsString();
        Book savedBook = mapper.readValue(contentAsJsonString, new TypeReference<>() {});
        assertNotNull(savedBook);
    }
}