package io.github.raianebtschaves.libraryapi.validator;


import io.github.raianebtschaves.libraryapi.exceptions.DuplicateRecordException;
import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private AuthorRepository repository;

    public AuthorValidator(AuthorRepository repository) {
        this.repository = repository;
    }


    public void validate(Author author) {
        if (thereIsRegistredAuthor(author)) {
            throw new DuplicateRecordException("Author already registered!");
        }
    }

    private boolean thereIsRegistredAuthor(Author author) {
        Optional<Author> authorFound = repository.findByNameAndDateBirthAndNationality(
                author.getName(), author.getDateBirth(), author.getNationality()
        );

        if (author.getId() == null) {
            return authorFound.isPresent();
        }

        return !author.getId().equals(authorFound.get().getId()) && authorFound.isPresent();
    }
}
