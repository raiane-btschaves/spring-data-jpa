package io.github.raianebtschaves.libraryapi.service;

import io.github.raianebtschaves.libraryapi.model.Author;
import io.github.raianebtschaves.libraryapi.model.Book;
import io.github.raianebtschaves.libraryapi.model.GenderBook;
import io.github.raianebtschaves.libraryapi.repository.AuthorRepository;
import io.github.raianebtschaves.libraryapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ServiceTransaction {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    //book (title, ..., name_archive) -> id.png
    @Transactional
    public void saveBookWithPhoto() {
        // salva o livro:
        //repository.save(book); estado maneged

        //pega o ig do livro = book.getId();
        //var id = book.getId();

        //salvar foto do livro -> buket na nuvem
        //bucketService.salvar(book.getFoto(), id + ".png");

        //atualizar o nome arquivo que foi salvo
        //book.setNameArchivePhoto(id + ".png");
    }

    @Transactional
    public void updateWithoutUpdating() {
        var book = bookRepository
                .findById(UUID.fromString("8c312ef0-6c2b-4c51-90b8-a114002a71f6"))
                .orElse(null);

        book.setPublicationDate(LocalDate.of(2024,7,16));
    }

    @Transactional
    public void execute() {
        // salva o autor
        Author author = new Author();
        author.setName("Teste Consuelo");
        author.setNationality("Boliviano");
        author.setDateBirth(LocalDate.of(1996, 7, 15));


        authorRepository.save(author);
        // authorRepository.saveAndFlush(author);

        //salva o livro
        Book book = new Book();
        book.setIsbn("80907-84874");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.FICTION);
        book.setTitle("Livro da Consuelo");
        book.setPublicationDate(LocalDate.of(2024, 4, 2));

        book.setAuthor(author);

        bookRepository.save(book);
        //   bookRepository.saveAndFlush(book);

        if (author.getName().equals("Teste Consuelo")) {
            throw new RuntimeException("Rollback!");
        }

    }
}
