package io.github.raianebtschaves.libraryapi.repository;

import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.model.Book;
import io.github.raianebtschaves.libraryapi.model.GenderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see BookRepositoryTest
 */

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

    // select * from book where date_publication between ? and ?
    List<Book> findByPublicationDateBetween(LocalDate start, LocalDate finish);

    //select * from book where title like '%A Fronteira%'
    List<Book> findByTitleLike(String keyword);

    // JPQL -> referencia as entidades e suas propriedades
    // select b.* from book as b order by b.title
    @Query("select b from Book as b order by b.title, b.price")
    List<Book> listAllOrderByTitleAndPrice();

    /**
     * select a.*
     * from book b
     * join author a on a.id = b.id_author
     */
    @Query("select a from Book b join b.author a")
    List<Author> ListBookAuthors();


    //select distinc b.* from book b
    @Query("select distinct b.title from Book b")
    List<String> listNameDifferentBooks();

    @Query(""" 
            select b.gender
            from Book b
            join b.author a
            where a.nationality = 'Italiana'
            order by b.gender
            """)
        //JPQL
    List<String> listGenderAuthorsItalian();

    //named paramters -> parâmetros nomeados
    @Query("select b from Book b where b.gender = :gender order by :paramOrnation")
    List<Book> findByGender(
            @Param("gender") GenderBook genderBook,
            @Param("paramOrnation") String nameProperty);

    // positonal parameters - em querys + simples
    // @Query("select b from Book b where b.gender = ?1 order by ?2")
    @Query("select b from Book b where b.gender = ?2 order by ?1")
    List<Book> findByGenderPositionalParameters(String nameProperty, GenderBook genderBook);

    @Modifying
    @Transactional
    @Query("delete from Book where gender = ?1")
    void deleteByGender(GenderBook gender);

    @Modifying
    @Transactional
    @Query("update Book set publicationDate = ?1")
    void updateDatePublication(LocalDate newDate);

    @Modifying
    @Transactional
    @Query("update Book b set b.publicationDate = :newDate where b.id = :bookId")
    void updatedDatePublication(@Param("newDate") LocalDate newDate, @Param("bookId") UUID bookId);

}
