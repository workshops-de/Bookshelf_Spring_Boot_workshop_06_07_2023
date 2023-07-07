package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Size;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {
    private final ObjectMapper mapper;

    private final ResourceLoader resourceLoader;

    private List<Book> books;

    public BookRestController(ObjectMapper mapper, ResourceLoader resourceLoader) {
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        final var resource = resourceLoader.getResource("classpath:books.json");
        this.books = mapper.readValue(resource.getInputStream(), new TypeReference<>() {});
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    @GetMapping("/{isbn}")
    public Book getByIsbn(@PathVariable @Size(min=3) String isbn) {
        return books.stream()
                .filter(book -> hasIsbn(book, isbn))
                .findFirst()
                .orElseThrow();
    }

    @GetMapping(params = "author")
    public Book getByAuthor(@RequestParam String author) {
        return books.stream()
                .filter(book -> hasAuthor(book, author))
                .findFirst()
                .orElseThrow();
    }

    @PostMapping("/search")
    List<Book> search(@RequestBody BookSearchRequest searchRequest) {
        return books.stream()
                .filter(book -> hasIsbn(book, searchRequest.isbn()) || hasAuthor(book, searchRequest.author()))
                .toList();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> error(Exception e) {
        return new ResponseEntity<>("Buch nicht gefunden. %s".formatted(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }
}
