package de.workshops.bookshelf.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

//////// Repository using JPA and SpringBoot Query Methods
@Repository
public interface BookshelfRepository extends ListCrudRepository<Book, Integer> {
    Book findByIsbn(String isbn);

    @Query("select book from Book book where book.isbn = ?1")
    Book getByIsbn(String isbn);
}

//////// Repository using JdbcTemplate
//@Repository
//class BookshelfRepository {
//    private final JdbcTemplate template;
//
//    BookshelfRepository(JdbcTemplate template) {
//        this.template = template;
//    }
//
//    List<Book> findAllBooks() {
//        String sql = "SELECT * FROM book";
//        return template.query(sql, new BeanPropertyRowMapper<>(Book.class));
//    }
//
//    void saveBook(Book book) {
//        String sql = "INSERT INTO book (title, description, author, isbn) VALUES (?, ?, ?, ?)";
//        template.update(sql, book.getTitle(), book.getDescription(), book.getAuthor(), book.getIsbn());
//    }
//}
