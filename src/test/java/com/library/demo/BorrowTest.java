package com.library.demo;
import com.library.demo.book.entity.Book;
import com.library.demo.book.repo.BookRepo;
import com.library.demo.borrow.entity.Borrow;
import com.library.demo.borrow.repo.BorrowRepo;
import com.library.demo.borrow.service.BorrowService;
import com.library.demo.exeption.ApiRequestException;
import com.library.demo.exeption.NotFoundExecption;
import com.library.demo.patron.entity.Patron;
import com.library.demo.patron.repo.PatronRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowTest {
    @Mock
    private BookRepo bookRepo;

    @Mock
    private PatronRepo patronRepo;

    @Mock
    private BorrowRepo borrowRepo;

    @InjectMocks
    private BorrowService borrowService;


    private Patron testPatron;
    private Borrow testBorrow;
    private Book testBook;

    @BeforeEach
    void setUp() {
        testPatron = new Patron();
        testPatron.setId(1);
        testPatron.setName("Test Patron");
        testPatron.setEmail("example@example.com");
        testPatron.setPhone("0100000000");
        testBook = new Book(1,"The Bell Jar" ,"Sylvia Plath", LocalDate.ofYearDay(1980,300), "32545346");
        testBorrow = new Borrow();
        testBorrow.setId(1);
        testBorrow.setBook(testBook);
        testBorrow.setPatron(testPatron);
        testBorrow.setBorrowedAt(LocalDateTime.now());


    }

    @Test
    void testBorrowBook() {
        when(bookRepo.findById(1)).thenReturn(Optional.of(testBook));
        when(patronRepo.findById(1)).thenReturn(Optional.of(testPatron));
        when(borrowRepo.findByBookAndPatronAndReturnedAtIsNull(testBook, testPatron)).thenReturn(new ArrayList<>());
        when(borrowRepo.save(any(Borrow.class))).thenReturn(testBorrow);

        Borrow borrow = borrowService.borrowBook(1, 1);

        assertNotNull(borrow);
        assertEquals(testBook, borrow.getBook());
        assertEquals(testPatron, borrow.getPatron());
        assertEquals(testBorrow, borrow);
        verify(borrowRepo, times(1)).save(any(Borrow.class));
    }

    @Test
    void testBorrowBookPatronAlreadyBorrowed() {
        List<Borrow> borrows = new ArrayList<>();
        borrows.add(testBorrow);
        when(bookRepo.findById(1)).thenReturn(Optional.of(testBook));
        when(patronRepo.findById(1)).thenReturn(Optional.of(testPatron));
        when(borrowRepo.findByBookAndPatronAndReturnedAtIsNull(testBook, testPatron)).thenReturn(borrows);

        assertThrows(ApiRequestException.class, () -> borrowService.borrowBook(1, 1));
    }

    @Test
    void testReturnBook() {
        when(bookRepo.findById(1)).thenReturn(Optional.of(testBook));
        when(patronRepo.findById(1)).thenReturn(Optional.of(testPatron));
        when(borrowRepo.findByBookAndPatronAndReturnedAtIsNull(testBook, testPatron)).thenReturn(List.of(testBorrow));
        when(borrowRepo.save(testBorrow)).thenReturn(testBorrow);
        testBorrow.setReturnedAt(LocalDateTime.now());

        Borrow returnedBorrow = borrowService.returnBook(1, 1);

        assertEquals(testBorrow, returnedBorrow);
        assertNotNull(returnedBorrow.getReturnedAt());
        verify(borrowRepo, times(1)).save(testBorrow);
    }

    @Test
    void testReturnBook_NotFound() {
        when(bookRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundExecption.class, () -> borrowService.returnBook(1, 1));
    }
}
