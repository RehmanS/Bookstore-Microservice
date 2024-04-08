package com.bookstore.bookservice.repository;
import com.bookstore.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> getBookByIsbn(String isbn);
    Book findBookByIsbn(String isbn);
    void deleteBookByIsbn(String isbn);
    @Query(value = "SELECT * FROM book WHERE NAME LIKE %?1% ",nativeQuery = true)
    List<Book> findBookByName(String text);
    @Query(value = "SELECT * FROM book WHERE AUTHOR LIKE %?1% ",nativeQuery = true)
    List<Book> findBooksByAuthor(String text);

}

