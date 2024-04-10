package com.library.demo;


import com.library.demo.book.dtos.BookDTO;
import com.library.demo.book.entity.Book;
import com.library.demo.book.mapper.BookMapper;
import com.library.demo.book.repo.BookRepo;
import com.library.demo.book.service.BookService;
import com.library.demo.exeption.NotFoundExecption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookService bookService;

    private Book testBook;


    @BeforeEach
    void setUp() {
        testBook = new Book(1,"The Bell Jar" ,"Sylvia Plath", LocalDate.ofYearDay(1980,300), "32545346");
    }

    @Test
    void testAddNewBook() {
        when(bookRepo.save(testBook)).thenReturn(testBook);

        Book result = bookService.addNewBook(testBook);

        assertEquals(testBook, result);
        verify(bookRepo, times(1)).save(testBook);
    }

    @Test
    void testListAllBooksTitle() {
        String title = "Bell";
        int page = 0;
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(testBook));
        when(bookRepo.findByTitleContaining(title, PageRequest.of(page, 10))).thenReturn(bookPage);

        Page<Book> result = bookService.listAllBooksTitle(title, page);

        assertEquals(bookPage, result);
        verify(bookRepo, times(1)).findByTitleContaining(title, PageRequest.of(page, 10));
    }

    @Test
    void testListAllBooks() {
        int page = 0;
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(testBook));
        when(bookRepo.findAll(PageRequest.of(page, 10))).thenReturn(bookPage);

        Page<Book> result = bookService.listAllBooks(page);

        assertEquals(bookPage, result);
        verify(bookRepo, times(1)).findAll(PageRequest.of(page, 10));
    }

    @Test
    void testGetBookId() {
        Integer id = 1;
        when(bookRepo.findById(id)).thenReturn(Optional.of(testBook));

        Book result = bookService.getBookId(id);

        assertEquals(testBook, result);
        verify(bookRepo, times(1)).findById(id);
    }

    @Test
    void testUpdateBook() {
        Integer id = 1;
        BookDTO dto = new BookDTO();
        dto.setTitle("Kafka on the Shore");
        when(bookRepo.findById(id)).thenReturn(Optional.of(testBook));
        when(bookRepo.save(testBook)).thenReturn(testBook);

        Book updatedBook = testBook;
        updatedBook.setTitle("Kafka on the Shore");
        Book result = bookService.updateBook(id, dto);

        assertEquals(updatedBook, result);
        verify(bookRepo, times(1)).findById(id);
        verify(mapper, times(1)).mapBookDto(dto, testBook);
        verify(bookRepo, times(1)).save(testBook);
    }

    @Test
    void testDeleteBook() {
        Integer id = 1;
        when(bookRepo.findById(id)).thenReturn(Optional.of(testBook));

        bookService.deleteBook(id);

        verify(bookRepo, times(1)).findById(id);
        verify(bookRepo, times(1)).deleteById(id);
    }

    @Test
    void testDeleteBookNotFound() {
        Integer id = 1;
        when(bookRepo.findById(id)).thenReturn(Optional.empty());


        assertThrows(NotFoundExecption.class, () -> {
            bookService.deleteBook(id);
        });


        verify(bookRepo, times(1)).findById(id);
        verify(bookRepo, never()).deleteById(id);
    }
}