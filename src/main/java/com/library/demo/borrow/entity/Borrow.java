package com.library.demo.borrow.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.library.demo.book.entity.Book;
import com.library.demo.patron.entity.Patron;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "borrow")
public class Borrow {
    @Id
    @SequenceGenerator(name = "borrow_id_seq",sequenceName = "borrow_id_seq",allocationSize = 1)
    @GeneratedValue(
            generator = "borrow_id_seq"
    )
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "book_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "book_id_borrows_fk"),
            nullable = false
    )
    @NotNull(message = "You must enter a book Id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "patron_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "patron_id_borrows_fk"),
            nullable = false
    )
    @NotNull(message = "You must enter a patron Id")
    @JsonBackReference
    private Patron patron;

    @Column(name = "borrowed_at")
    private LocalDateTime borrowedAt = LocalDateTime.now();

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;


}
