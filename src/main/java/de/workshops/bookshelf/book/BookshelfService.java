package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookshelfService {

    private final BookshelfRepository repository;

    BookshelfService(BookshelfRepository repository) {
        this.repository = repository;
    }

    List<Book> getAllBooks() {
        return repository.findAllBooks();
    }

    Book getByIsbn(String isbn) {
        return repository.findAllBooks()
                .stream()
                .filter(book -> hasIsbn(book, isbn))
                .findFirst()
                .orElseThrow();
    }

    Book getByAuthor(String author) {
        return repository.findAllBooks()
                .stream()
                .filter(book -> hasAuthor(book, author))
                .findFirst()
                .orElseThrow();
    }

    List<Book> search(BookSearchRequest searchRequest) {
        return repository.findAllBooks()
                .stream()
                .filter(book -> hasIsbn(book, searchRequest.isbn()) || hasAuthor(book, searchRequest.author()))
                .toList();
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

}
