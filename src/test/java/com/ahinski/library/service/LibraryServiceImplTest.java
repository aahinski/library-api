package com.ahinski.library.service;

import com.ahinski.library.dto.BookDTO;
import com.ahinski.library.dto.LibraryRecordDTO;
import com.ahinski.library.entity.Book;
import com.ahinski.library.entity.LibraryRecord;
import com.ahinski.library.exception.BookNotFoundException;
import com.ahinski.library.mapper.BookMapper;
import com.ahinski.library.mapper.LibraryRecordMapper;
import com.ahinski.library.repository.BookRepository;
import com.ahinski.library.repository.LibraryRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class LibraryServiceImplTest {

    @Autowired
    private LibraryService libraryService;

    @MockBean
    private LibraryRecordRepository libraryRecordRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private LibraryRecordMapper libraryRecordMapper;

    private BookDTO bookDTO;
    private Book book;
    private LibraryRecord libraryRecord;
    private LibraryRecordDTO libraryRecordDTO;

    @BeforeEach
    void setUp() {
        bookDTO = createMockBookDTO("123456", "Harry Potter",
                "Fantasy", "A book about the boy who lived", "J.K. Rowling");

        book = createMockBook("123456", "Harry Potter",
                "Fantasy", "A book about the boy who lived", "J.K. Rowling");

        libraryRecord = new LibraryRecord();
        libraryRecord.setBook(book);

        libraryRecordDTO = new LibraryRecordDTO();
        libraryRecordDTO.setBookId(1L);
    }

    @Test
    void testAddRecord() {
        when(libraryRecordRepository.save(any())).thenReturn(libraryRecord);

        LibraryRecordDTO addedRecordDTO = libraryService.addRecord(bookDTO);

        assertNotNull(addedRecordDTO);
    }

    @Test
    void testBorrowBook_NormalScenario() throws BookNotFoundException {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.of(libraryRecord));
        when(libraryRecordRepository.save(any())).thenReturn(libraryRecord);

        LibraryRecordDTO borrowedRecordDTO = libraryService.borrowBook(bookId);

        assertNotNull(borrowedRecordDTO.getBorrowTime());
        assertNotNull(borrowedRecordDTO.getReturnTime());
    }

    @Test
    void testBorrowBook_BookAlreadyBorrowed() {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.of(libraryRecord));
        when(libraryRecordRepository.existsByBookIdAndBorrowTimeIsNotNull(bookId)).thenReturn(true);

        assertThrows(BookNotFoundException.class, () -> libraryService.borrowBook(bookId));
    }

    @Test
    void testBorrowBook_BookNotFound() {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> libraryService.borrowBook(bookId));
    }

    @Test
    void testReturnBook_NormalScenario() throws BookNotFoundException {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.of(libraryRecord));
        when(libraryRecordRepository.save(any())).thenReturn(libraryRecord);
        when(libraryRecordRepository.existsByBookIdAndBorrowTimeIsNotNull(bookId)).thenReturn(true);

        LibraryRecordDTO returnedRecordDTO = libraryService.returnBook(bookId);

        assertNull(returnedRecordDTO.getBorrowTime());
        assertNull(returnedRecordDTO.getReturnTime());
    }

    @Test
    void testReturnBook_BookNotBorrowed() {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.of(libraryRecord));
        when(libraryRecordRepository.existsByBookIdAndBorrowTimeIsNotNull(bookId)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> libraryService.returnBook(bookId));
    }

    @Test
    void testReturnBook_BookNotFound() {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> libraryService.returnBook(bookId));
    }

    @Test
    void testIsBookBorrowed_NormalScenario() throws BookNotFoundException {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.of(libraryRecord));

        boolean isBorrowed = libraryService.isBookBorrowed(bookId);

        assertFalse(isBorrowed);
    }

    @Test
    void testIsBookBorrowed_BookNotBorrowed() throws BookNotFoundException {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.of(libraryRecord));
        when(libraryRecordRepository.existsByBookIdAndBorrowTimeIsNotNull(bookId)).thenReturn(false);

        boolean isBorrowed = libraryService.isBookBorrowed(bookId);

        assertFalse(isBorrowed);
    }

    @Test
    void testIsBookBorrowed_BookNotFound() {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> libraryService.isBookBorrowed(bookId));
    }

    @Test
    void testDeleteByBookId_NormalScenario() throws BookNotFoundException {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.of(libraryRecord));

        libraryService.deleteByBookId(bookId);
    }

    @Test
    void testDeleteByBookId_BookNotFound() {
        Long bookId = 1L;
        when(libraryRecordRepository.findByBookId(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> libraryService.deleteByBookId(bookId));
    }

    private Book createMockBook(String isbn, String title, String genre, String description, String author) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setGenre(genre);
        book.setDescription(description);
        book.setAuthor(author);
        return book;
    }

    private BookDTO createMockBookDTO(String isbn, String title, String genre, String description, String author) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn(isbn);
        bookDTO.setTitle(title);
        bookDTO.setGenre(genre);
        bookDTO.setDescription(description);
        bookDTO.setAuthor(author);
        return bookDTO;
    }
}

