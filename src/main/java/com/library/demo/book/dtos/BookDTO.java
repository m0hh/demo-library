package com.library.demo.book.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {


    private String title;


    private String author;


    private LocalDate publicationYear;


    private String isbn;
}
