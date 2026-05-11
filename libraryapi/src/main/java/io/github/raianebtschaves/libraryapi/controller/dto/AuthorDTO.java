package io.github.raianebtschaves.libraryapi.controller.dto;

import io.github.raianebtschaves.libraryapi.model.Author;

import java.time.LocalDate;
import java.util.UUID;

//Data Transfer Object
public record AuthorDTO(
        UUID id,
        String name,
        LocalDate dateBirth,
        String nationality) {


    public Author mapToAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setDateBirth(this.dateBirth);
        author.setNationality(this.nationality);
        return author;
    }
}
