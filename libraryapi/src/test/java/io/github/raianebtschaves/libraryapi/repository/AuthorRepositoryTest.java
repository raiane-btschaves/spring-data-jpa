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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

    @Autowired
    BookRepository bookRepository;


    @Test
    public void saveTeste() {
        Author author = new Author();
        author.setName("Maria");
        author.setNationality("Brasileira");
        author.setDateBirth(LocalDate.of(1951, 1, 30));

        var authorSave = repository.save(author);
        System.out.println("Author save: " + authorSave);
    }


    @Test
    public void updateTeste() {
        var id = UUID.fromString("6dae6290-004d-4355-9143-e1c5d4153c0e");

        Optional<Author> possibleAuthor = repository.findById(id);

        if (possibleAuthor.isPresent()) {
            Author authorFound = possibleAuthor.get();
            System.out.println("Author details: ");
            System.out.println(authorFound);

            authorFound.setDateBirth(LocalDate.of(1960, 3, 30));

            repository.save(authorFound);

        }
    }

    @Test
    public void listTests() {
        List<Author> list = repository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Authors count: " + repository.count());
    }

    @Test
    public void deleteByIdTest() {
        var id = UUID.fromString("6dae6290-004d-4355-9143-e1c5d4153c0e");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("8146e7f7-98d9-40b4-a456-85cfdd4de32b");
        var maria = repository.findById(id).get();
        repository.deleteById(maria.getId());
    }

    @Test
    void saveAuthorWithBooksTest() {
        Author author = new Author();
        author.setName("Raiane");
        author.setNationality("Italiana");
        author.setDateBirth(LocalDate.of(1995, 9, 3));


        Book book = new Book();
        book.setIsbn("20547-4578922");
        book.setPrice(BigDecimal.valueOf(204));
        book.setGender(GenderBook.SCIENCE);
        book.setTitle("A Fronteira do Invisível");
        book.setDatePublication(LocalDate.of(2018, 5, 11));
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setIsbn("1145627-48521999");
        book2.setPrice(BigDecimal.valueOf(350));
        book2.setGender(GenderBook.SCIENCE);
        book2.setTitle("O Código do Imprevisto");
        book2.setDatePublication(LocalDate.of(2025, 3, 3));
        book2.setAuthor(author);


        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        repository.save(author);

   //     bookRepository.saveAll(author.getBooks());

    }

    @Test
   // @Transactional
    void listBookAuthor() {
        var id = UUID.fromString("850aec4c-5d31-4067-9adb-edac05d4471c");
        var author = repository.findById(id).get();

        // buscar os libros do autor

       List<Book> booksList = bookRepository.findByAuthor(author);
       author.setBooks(booksList);

        author.getBooks().forEach(System.out::println); // method reference
    }

}


