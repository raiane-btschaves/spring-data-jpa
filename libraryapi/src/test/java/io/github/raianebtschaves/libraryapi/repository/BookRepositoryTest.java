package io.github.raianebtschaves.libraryapi.repository;


import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.model.Book;
import io.github.raianebtschaves.libraryapi.model.GenderBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository repository;

    @Autowired
    AuthorRepository authorRepository;


    @Test
    void saveTest() {
        Book book = new Book();
        book.setIsbn("80907-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.FICTION);
        book.setTitle("Outro livro");
        book.setPublication_date(LocalDate.of(1980, 1, 2));

        Author author = authorRepository
                .findById(UUID.fromString("41c27808-730e-457e-b6f6-93271cb8202d"))
                .orElse(null);

        book.setAuthor(new Author());

        repository.save(book);
    }


    @Test
    void saveCascadeTest() {
        Book book = new Book();
        book.setIsbn("80907-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.FICTION);
        book.setTitle("Outro livro");
        book.setPublication_date(LocalDate.of(1980, 1, 2));

        Author author = new Author();
        author.setName("João");
        author.setNationality("Brasileira");
        author.setDateBirth(LocalDate.of(1951, 1, 30));

        book.setAuthor(author);

        repository.save(book);
    }


    @Test
    void saveAuthorAndBookTest() {
        Book book = new Book();
        book.setIsbn("80907-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.FICTION);
        book.setTitle("Terceiro livro");
        book.setPublication_date(LocalDate.of(1980, 1, 2));

        Author author = new Author();
        author.setName("Alex");
        author.setNationality("Paraguay");
        author.setDateBirth(LocalDate.of(1951, 1, 30));

        authorRepository.save(author);

        book.setAuthor(author);

        repository.save(book);
    }

}