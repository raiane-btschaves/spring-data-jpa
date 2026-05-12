package io.github.raianebtschaves.libraryapi.repository;


import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.model.Book;
import io.github.raianebtschaves.libraryapi.model.GenderBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
        book.setGender(GenderBook.MYSTERY);
        book.setTitle("O Pavão Misterioso");
        book.setPublicationDate(LocalDate.of(1975, 3, 2));

        Author author = authorRepository
                .findById(UUID.fromString("a09660a6-2e15-4907-a01c-e42d5a8939e2"))
                .orElse(null);

        book.setAuthor(author);

        repository.save(book);
    }

    @Test
    void saveTes2() {
        // 1. Preencher os dados obrigatórios do Autor
        Author author = new Author();
        author.setName("Author Test");
        author.setDateBirth(LocalDate.of(1980, 5, 20)); // Adicionado campo obrigatório
        author.setNationality("Brasileira"); // Adicione caso seja NOT NULL na sua classe Author

        authorRepository.save(author);

        // 2. Criar e configurar o Livro
        Book book = new Book();
        book.setIsbn("80907-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.MYSTERY);
        book.setTitle("O Pavão Misterioso");
        book.setPublicationDate(LocalDate.of(1975, 3, 2));

        book.setAuthor(author);

        // 3. Salvar o Livro
        Book saveBook = repository.save(book);
    }


    @Test
    void saveCascadeTest() {
        Book book = new Book();
        book.setIsbn("80907-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.FICTION);
        book.setTitle("Outro livro");
        book.setPublicationDate(LocalDate.of(1980, 1, 2));

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
        book.setPublicationDate(LocalDate.of(1980, 1, 2));

        Author author = new Author();
        author.setName("Alex");
        author.setNationality("Paraguay");
        author.setDateBirth(LocalDate.of(1951, 1, 30));

        authorRepository.save(author);

        book.setAuthor(author);

        repository.save(book);
    }

    @Test
    void updateAuthorByBook() {
        UUID id = UUID.fromString("11086f26-098f-4102-a2a9-19e17ab0a824");
        var bookToUpdate = repository.findById(id).orElse(null);

        UUID idAuthor = UUID.fromString("ef201e2e-9994-4ffd-aaf5-433a72687427");
        Author maria = authorRepository.findById(idAuthor).orElse(null);

        bookToUpdate.setAuthor(maria);

        repository.save(bookToUpdate);
    }

    @Test
    void delete() {
        UUID id = UUID.fromString("11086f26-098f-4102-a2a9-19e17ab0a824");
        repository.deleteById(id);
    }

    @Test
    void deleteCascade() {
        UUID id = UUID.fromString("ff163213-de04-4d20-9609-90ecd2b575f8");
        repository.deleteById(id);
    }


    @Test
    @Transactional
    void searchBookTest() {
        UUID id = UUID.fromString("38a8b63c-b496-43ec-b055-96dff8c4d83c");
        Book book = repository.findById(id).orElse(null);
        System.out.println("Book: ");
        System.out.println(book.getTitle());

        // System.out.println("Author: ");
        // System.out.println(book.getAuthor().getName());

    }

    @Test
    void searchByTitleTest() {
        List<Book> list = repository.findByTitle("A Fronteira do Invisível");
        list.forEach(System.out::println);
    }

    @Test
    void searchByIsbnTest() {
        List<Book> list = repository.findByIsbn("1145627-48521999");
        list.forEach(System.out::println);
    }

    @Test
    void searchByTitleAndPriceTest() {
        var price = BigDecimal.valueOf(204.00);
        String titleSearch = "A Fronteira do Invisível";
        List<Book> list = repository.findByTitleAndPrice(titleSearch, price);
        list.forEach(System.out::println);
    }

    @Test
    void listBooksWithQueryJPQL() {
        var result = repository.listAllOrderByTitleAndPrice();
        result.forEach(System.out::println);
    }

    @Test
    void listBokAuthors() {
        var result = repository.ListBookAuthors();
        result.forEach(System.out::println);
    }

    @Test
    void listNonRepeatedBookTitles() {
        var result = repository.listNameDifferentBooks();
        result.forEach(System.out::println);
    }

    @Test
    void listGenderBooksAuthorsItalian() {
        var result = repository.listGenderAuthorsItalian();
        result.forEach(System.out::println);
    }

    @Test
    void listByGenderQueryParamTest() {
        var result = repository.findByGender(GenderBook.SCIENCE, "datePublication");
        result.forEach(System.out::println);
    }

    @Test
    void listByGenderPositionalQueryParamTest() {
        var result = repository.findByGenderPositionalParameters("datePublication", GenderBook.SCIENCE);
        result.forEach(System.out::println);
    }

    @Test
    void deleteByGenderTest() {
        repository.deleteByGender(GenderBook.MYSTERY);
    }

    @Test
    void updateDatePublicationTest() {
        repository.updateDatePublication(LocalDate.of(2000, 1, 1));
    }


    @Test
    void updatedDatePublicationTest() {
        LocalDate newDate = LocalDate.of(1993, 6,6);

        UUID idByBook = UUID.fromString("38a8b63c-b496-43ec-b055-96dff8c4d83c");

        repository.updatedDatePublication(newDate, idByBook);
    }

}