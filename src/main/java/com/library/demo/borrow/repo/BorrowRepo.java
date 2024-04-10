package com.library.demo.borrow.repo;

import com.library.demo.book.entity.Book;
import com.library.demo.borrow.entity.Borrow;
import com.library.demo.patron.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepo extends JpaRepository<Borrow, Integer> {
    List<Borrow> findByBookAndPatronAndReturnedAtIsNull(Book book, Patron patron);
}
