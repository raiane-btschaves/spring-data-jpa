package io.github.raianebtschaves.libraryapi.service;

import io.github.raianebtschaves.libraryapi.exceptions.OperationNotPermittedException;
import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.repository.AuthorRepository;
import io.github.raianebtschaves.libraryapi.repository.BookRepository;
import io.github.raianebtschaves.libraryapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final  AuthorRepository repository;
    private final AuthorValidator validator;
    private final BookRepository bookRepository;


    public Author save(Author author) {
        validator.validate(author);
        return repository.save(author);
    }

    public void update(Author author) {
        if (author.getId() == null) {
            throw new IllegalArgumentException("To update, the author must already be saved in the database.");
        }
        validator.validate(author);
        repository.save(author);
    }

    public Optional<Author> getById(UUID id) {
        return repository.findById(id);
    }

    public void delete(Author author) {
        if (haveBook(author)) {
            throw new OperationNotPermittedException(
                    "It is not permitted to delete an author who has books registered!");
        }
        repository.delete(author);
    }

    public List<Author> search(String name, String nacionality) {
        if (name != null && nacionality != null) {
            return repository.findByNameAndNationality(name, nacionality);
        }

        if (name != null) {
            return repository.findByName(name);
        }

        if (nacionality != null) {
            return repository.findByNationality(nacionality);
        }
        return repository.findAll();
    }

    public boolean haveBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }


}
