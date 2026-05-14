package io.github.raianebtschaves.libraryapi.controller.dto;

import io.github.raianebtschaves.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

//Data Transfer Object
public record AuthorDTO(
        UUID id,
        @NotBlank(message = "Required field")
        @Size(min = 2, max = 100, message = "The poor guy's field is out of reach.")
        String name,
        @NotNull(message = "Required field")
        @Past(message ="It cannot be a future date.")
        LocalDate dateBirth,
        @NotBlank(message = "Required field")
        @Size(max = 50, min = 2, message = "The poor guy's field is out of reach.")
        String nationality) {


    public Author mapToAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setDateBirth(this.dateBirth);
        author.setNationality(this.nationality);
        return author;
    }
}
