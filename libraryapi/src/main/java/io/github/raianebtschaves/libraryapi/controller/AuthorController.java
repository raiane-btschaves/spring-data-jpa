package io.github.raianebtschaves.libraryapi.controller;

import io.github.raianebtschaves.libraryapi.controller.dto.AuthorDTO;
import io.github.raianebtschaves.libraryapi.controller.dto.ErrorResponse;
import io.github.raianebtschaves.libraryapi.exceptions.DuplicateRecordException;
import io.github.raianebtschaves.libraryapi.exceptions.OperationNotPermittedException;
import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("authors")
//http://localhost:8080/authors
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid AuthorDTO author) {
//        if(true) {
//            throw new RuntimeException("Simulando um erro interno inesperado para o Postamn");
//        }
        try {
            Author authorEntity = author.mapToAuthor();
            service.save(authorEntity);

            //http://localhost:8080/authors/5334a47b-873c-4680-b045-fe27a0f037a7
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}").buildAndExpand(authorEntity.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (DuplicateRecordException e) {
            var errorDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
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
        return ResponseEntity.notFound().build();
    }

    // indempodetente
    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        try {
            var idAuthor = UUID.fromString(id);
            Optional<Author> authorOptional = service.getById(idAuthor);

            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            service.delete(authorOptional.get());

            return ResponseEntity.noContent().build();
        } catch (OperationNotPermittedException e) {
            var errorResponse = ErrorResponse.responseDefault(e.getMessage());
            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {
        List<Author> result = service.search(name, nationality);
        List<AuthorDTO> list = result.stream().map(author -> new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getDateBirth(),
                author.getNationality())
        ).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") String id, @RequestBody @Valid AuthorDTO dto) {
        try {

            var idAuthor = UUID.fromString(id);
            Optional<Author> authorOptional = service.getById(idAuthor);

            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var author = authorOptional.get();
            author.setName(dto.name());
            author.setNationality(dto.nationality());
            author.setDateBirth(dto.dateBirth());

            service.update(author);

            return ResponseEntity.noContent().build();

        } catch (DuplicateRecordException e) {
            var errorDTO = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}