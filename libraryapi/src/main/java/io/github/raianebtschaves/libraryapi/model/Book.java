package io.github.raianebtschaves.libraryapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "book")
@Data
@ToString(exclude = "author")

public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name = "date_Publication", nullable = false)
    private LocalDate datePublication;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 30, nullable = false)
    private GenderBook gender;

    @Column(name = "price", precision = 18, scale = 2)
    private BigDecimal price;

 //  @ManyToOne(cascade = CascadeType.ALL)
   // @ManyToOne
    @ManyToOne(
            fetch = FetchType.LAZY // traz os dados do livro sem autor-carregamento lento
    )
  //  @JoinColumn(name = "id_author", nullable = false)
    @JoinColumn(name = "id_author")
    private Author author;


}
