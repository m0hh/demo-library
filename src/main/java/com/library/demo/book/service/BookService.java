package com.library.demo.book.service;

import com.library.demo.book.dtos.BookDTO;
import com.library.demo.book.entity.Book;
import com.library.demo.book.mapper.BookMapper;
import com.library.demo.book.repo.BookRepo;
import com.library.demo.exeption.NotFoundExecption;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    @Autowired
    private final BookRepo bookRepo;

    @Autowired
    private final BookMapper mapper;

    public Book addNewBook(Book book){
        return bookRepo.save(book);
    }

    public Page<Book> listAllBooksTitle(String title, Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        System.out.println(title);
        return  bookRepo.findByTitleContaining(title, pageable);
    }

    public Page<Book> listAllBooks(Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return  bookRepo.findAll(pageable);
    }

    public  Book getBookId(Integer id){
        return bookRepo.findById(id).orElseThrow(NotFoundExecption::new);
    }

    public Book updateBook(Integer id, BookDTO dto){
        Book book =  bookRepo.findById(id).orElseThrow(NotFoundExecption::new);
        mapper.mapBookDto(dto, book);

         return bookRepo.save(book);

    }

    public void deleteBook(Integer id){
        bookRepo.findById(id).orElseThrow(NotFoundExecption::new);
        bookRepo.deleteById(id);
    }

}
