package com.ahinski.library.service;

import com.ahinski.library.dto.BookDTO;
import com.ahinski.library.exception.BookNotFoundException;

import java.util.List;

public interface BookService {

    BookDTO saveBook(BookDTO book);

    List<BookDTO> fetchBookList();

    BookDTO fetchBookById(Long bookId) throws BookNotFoundException;

    void deleteBookById(Long bookId) throws BookNotFoundException;

    BookDTO updateBookById(Long bookId, BookDTO updatedBookDTO) throws BookNotFoundException;

    BookDTO fetchBookByIsbn(String isbn) throws BookNotFoundException;

}
