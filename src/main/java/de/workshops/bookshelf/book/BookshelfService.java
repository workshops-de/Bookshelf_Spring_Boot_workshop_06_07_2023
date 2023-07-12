package de.workshops.bookshelf.book;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookshelfService {

    private final BookshelfRepository repository;

    BookshelfService(BookshelfRepository repository) {
        this.repository = repository;
    }

    List<Book> getAllBooks() {
        return repository.findAll();
    }

    Book getByIsbn(String isbn) {
        return repository.getByIsbn(isbn);
    }

    Book getByAuthor(String author) {
        return repository.findAll()
                .stream()
                .filter(book -> hasAuthor(book, author))
                .findFirst()
                .orElseThrow();
    }

    List<Book> search(BookSearchRequest searchRequest) {
        return repository.findAll()
                .stream()
                .filter(book -> hasIsbn(book, searchRequest.isbn()) || hasAuthor(book, searchRequest.author()))
                .toList();
    }

    @Transactional
    public Book addBook(CreateBookRequest createBookRequest) {
        final var author = new Author();
        author.setName(createBookRequest.getAuthor());

        final var book = new Book();
        book.setIsbn(createBookRequest.getIsbn());
        book.setTitle(createBookRequest.getTitle());
        book.setDescription(createBookRequest.getDescription());
        book.setAuthor(List.of(author));

        author.setBook(book);

        return repository.save(book);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthors()
                .stream()
                .anyMatch(a -> a.getName().contains(author));
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

}
