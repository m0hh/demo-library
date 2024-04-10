package com.library.demo.patron.repo;

import com.library.demo.book.entity.Book;
import com.library.demo.patron.entity.Patron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepo extends JpaRepository<Patron, Integer> {
    Page<Patron> findByNameContaining(String name, Pageable pageable);

}
