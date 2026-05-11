package io.github.raianebtschaves.libraryapi.service;

import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

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

    public Optional<Author> getById(UUID id) {
        return repository.findById(id);
    }
}
