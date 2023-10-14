package com.ahinski.library.controller;

import com.ahinski.library.dto.BookDTO;
import com.ahinski.library.exception.BookNotFoundException;
import com.ahinski.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.fetchBookList();
    }

    @GetMapping("/{bookId}")
    public BookDTO getBookById(@PathVariable Long bookId) throws BookNotFoundException {
        return bookService.fetchBookById(bookId);
    }

    @GetMapping("/isbn/{isbn}")
    public BookDTO getBookByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        return bookService.fetchBookByIsbn(isbn);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDTO addBook(@RequestBody BookDTO bookDTO) {
        return bookService.saveBook(bookDTO);
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDTO updateBook(@PathVariable Long bookId, @RequestBody BookDTO updatedBookDTO)
            throws BookNotFoundException {
        return bookService.updateBookById(bookId, updatedBookDTO);
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBook(@PathVariable Long bookId) throws BookNotFoundException {
        bookService.deleteBookById(bookId);
    }
}
