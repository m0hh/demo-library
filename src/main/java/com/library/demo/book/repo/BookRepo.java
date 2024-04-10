package com.library.demo.book.repo;

import com.library.demo.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Integer> {
    Page<Book> findByTitleContaining(String title, Pageable pageable);
}
