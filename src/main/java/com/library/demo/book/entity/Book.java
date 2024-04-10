package com.library.demo.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name="books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @SequenceGenerator(name = "books_id_seq",sequenceName = "books_id_seq",allocationSize = 1)
    @GeneratedValue(
            generator = "books_id_seq"
    )
    @Column(name="id")
    private Integer id;

    @NotBlank(message = "Must enter a title")
    @Column(name="title")
    private String title;

    @NotBlank(message = "Must enter an author")
    @Column(name="author")
    private String author;

    @NotNull(message = "must enter a publication year")
    @Column(name="publication_year")
    private LocalDate publicationYear;

    @NotBlank(message = "must enter an ISBN")
    @Column(name="isbn")
    private String isbn;

}

