package io.github.raianebtschaves.libraryapi.service;

import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author save(Author author) {
        return repository.save(author);
    }

    public void update(Author author) {
        if (author.getId() == null) {
            throw new IllegalArgumentException("To update, the author must already be saved in the database.");
        }
        repository.save(author);
    }

    public Optional<Author> getById(UUID id) {
        return repository.findById(id);
    }

    public void delete(Author author) {
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


}
