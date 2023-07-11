package de.workshops.bookshelf.book;

import jakarta.validation.constraints.Size;
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

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {

    private final BookshelfService service;

    public BookRestController(BookshelfService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getByIsbn(@PathVariable @Size(min=3) String isbn) {
        return service.getByIsbn(isbn);
    }

    @GetMapping(params = "author")
    public Book getByAuthor(@RequestParam String author) {
        return service.getByAuthor(author);
    }

    @PostMapping("/search")
    public List<Book> search(@RequestBody BookSearchRequest searchRequest) {
        return service.search(searchRequest);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return book;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> error(Exception e) {
        return new ResponseEntity<>("Buch nicht gefunden. %s".formatted(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
