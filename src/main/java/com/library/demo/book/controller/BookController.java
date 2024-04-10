package com.library.demo.book.controller;


import com.library.demo.book.dtos.BookDTO;
import com.library.demo.book.entity.Book;
import com.library.demo.book.service.BookService;
import com.library.demo.exeption.ApiRequestException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/books")
@AllArgsConstructor
public class BookController {

    @Autowired
    private final BookService bookService;

    @PostMapping("")
    public Book addBook(@RequestBody @Valid Book book,  final BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            String errorMessage = String.join(", ", errors);
            throw new ApiRequestException(errorMessage);
        }
        return bookService.addNewBook(book);
    }

    @GetMapping("")
    public Page<Book> listBooks(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "page", required = false) Integer page
    ){
        if (page == null){
            throw new ApiRequestException("Specify the page");
        }

        if (title == null){
            return bookService.listAllBooks(page);
        }else {
            return bookService.listAllBooksTitle(title, page);
        }
    }

    @Cacheable("application")
    @GetMapping("/{id}")
    public Book getBook(@PathVariable Integer id){
        return bookService.getBookId(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Integer id, @RequestBody BookDTO dto) {
        return bookService.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


}
