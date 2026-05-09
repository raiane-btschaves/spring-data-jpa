package io.github.raianebtschaves.libraryapi.repository;

import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    // Query Method
    // select * from book where id_author = id
    List<Book> findByAuthor(Author author); // interface declarativa- qery method

    //select * from book where title = title
    List<Book> findByTitle(String title);

    // select * from book where  isbn = ?
   List<Book> findByIsbn(String isbn);


   // Optional<Book> findByIsbn(String isbn);
   // Book<Book> findByIsbn(String isbn);

    // select * from book where title = ? and price = ?
    List<Book> findByTitleAndPrice(String title, BigDecimal price);

    // select * from book where title = ? or isbn = ?
    List<Book> findByTitleOrIsbn(String title, String isbn);

}
