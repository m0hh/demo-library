package com.library.demo.borrow.service;

import com.library.demo.book.entity.Book;
import com.library.demo.book.repo.BookRepo;
import com.library.demo.borrow.entity.Borrow;
import com.library.demo.borrow.repo.BorrowRepo;
import com.library.demo.exeption.ApiRequestException;
import com.library.demo.exeption.NotFoundExecption;
import com.library.demo.patron.entity.Patron;
import com.library.demo.patron.repo.PatronRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BorrowService {

    @Autowired
    private final BookRepo bookRepo;

    @Autowired
    private final PatronRepo patronRepo;

    @Autowired
    private final BorrowRepo borrowRepo;

    public Borrow borrowBook(Integer book_id, Integer patron_id){
        Book book = bookRepo.findById(book_id).orElseThrow(NotFoundExecption::new);
        Patron patron =  patronRepo.findById(patron_id).orElseThrow(NotFoundExecption::new);

        List<Borrow> borrows = borrowRepo.findByBookAndPatronAndReturnedAtIsNull(book, patron);
        if (!borrows.isEmpty()){
            throw new ApiRequestException("The patron has already borrowed this book and needs to return it first");
        }

        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setPatron(patron);

        return borrowRepo.save(borrow);

    }

    public Borrow returnBook(Integer book_id, Integer patron_id){
        Book book = bookRepo.findById(book_id).orElseThrow(NotFoundExecption::new);
        Patron patron =  patronRepo.findById(patron_id).orElseThrow(NotFoundExecption::new);
        Borrow borrow = borrowRepo.findByBookAndPatronAndReturnedAtIsNull(book, patron).get(0);
        borrow.setReturnedAt(LocalDateTime.now());

        return borrowRepo.save(borrow);

    }
}
