package de.workshops.bookshelf.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookshelfServiceTest {

    @Mock
    BookshelfRepository repository;

    @InjectMocks
    BookshelfService service;

    @Test
    @DisplayName("Wirft RuntimeException, wenn Isbn nicht in Liste der Bücher enthalten ist.")
    void shouldThrowRuntimeExceptionWhenIsbnNotContainedInBookList() {
        final var book1 = new Book();
        book1.setTitle("Book1");
        book1.setIsbn("11111111");

        final var book2 = new Book();
        book2.setTitle("Book2");
        book2.setIsbn("222222222");

        when(repository.findAllBooks()).thenReturn(List.of(book1, book2));

        assertThatThrownBy(() -> service.getByIsbn("33333333")).isInstanceOf(RuntimeException.class);
    }
}