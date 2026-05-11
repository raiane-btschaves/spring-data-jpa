package io.github.raianebtschaves.libraryapi.controller;

import io.github.raianebtschaves.libraryapi.controller.dto.AuthorDTO;
import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("authors")
//http://localhost:8080/authors
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;

    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AuthorDTO authorDTO) {
        Author authorEntity = authorDTO.mapToAuthor();
        service.save(authorEntity);

        //http://localhost:8080/authors/5334a47b-873c-4680-b045-fe27a0f037a7
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(authorEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> getDetails(@PathVariable("id") String id) {
        var idAuthor = UUID.fromString(id);
        Optional<Author> authorOptional = service.getById(idAuthor);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            AuthorDTO dto = new AuthorDTO(
                    author.getId(),
                    author.getName(),
                    author.getDateBirth(),
                    author.getNationality());
            return ResponseEntity.ok(dto);
        }
        return  ResponseEntity.notFound().build();
    }
}