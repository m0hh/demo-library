package com.library.demo.borrow.controller;

import com.library.demo.borrow.entity.Borrow;
import com.library.demo.borrow.service.BorrowService;
import com.library.demo.patron.entity.Patron;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public Borrow borrowBook(@PathVariable Integer bookId, @PathVariable Integer patronId){
        return borrowService.borrowBook(bookId, patronId);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public Borrow returnBook(@PathVariable Integer bookId, @PathVariable Integer patronId){
        return borrowService.returnBook( bookId, patronId);
    }
}
