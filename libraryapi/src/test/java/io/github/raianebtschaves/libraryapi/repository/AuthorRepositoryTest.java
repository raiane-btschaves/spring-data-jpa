package io.github.raianebtschaves.libraryapi.repository;

import io.github.raianebtschaves.libraryapi.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

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

}


