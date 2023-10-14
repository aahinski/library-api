package com.ahinski.library.service;

import com.ahinski.library.dto.BookDTO;
import com.ahinski.library.entity.Book;
import com.ahinski.library.exception.BookNotFoundException;
import com.ahinski.library.mapper.BookMapper;
import com.ahinski.library.repository.BookRepository;
import com.ahinski.library.repository.LibraryRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private LibraryRecordRepository libraryRecordRepository;

    @MockBean
    private BookMapper bookMapper;

    private Book book;

    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setIsbn("123456");
        book.setTitle("Harry Potter");
        book.setGenre("Fantasy");
        book.setDescription("A book about the boy who lived");
        book.setAuthor("J.K. Rowling");

        bookDTO = new BookDTO();
        bookDTO.setIsbn("123456");
        bookDTO.setTitle("Harry Potter");
        bookDTO.setGenre("Fantasy");
        bookDTO.setDescription("A book about the boy who lived");
        bookDTO.setAuthor("J.K. Rowling");
    }

    @Test
    void testSaveBook() {
        when(bookMapper.bookToBookDTO(any())).thenReturn(bookDTO);
        when(bookRepository.save(any())).thenReturn(book);

        BookDTO savedBookDTO = bookService.saveBook(bookDTO);

        assertNotNull(savedBookDTO);
    }

    @Test
    void testFetchBookList() {
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(book);

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<BookDTO> expectedBookDTOs = new ArrayList<>();
        expectedBookDTOs.add(bookDTO);

        when(bookMapper.booksToBookDTOs(mockBooks)).thenReturn(expectedBookDTOs);

        List<BookDTO> actualBookDTOs = bookService.fetchBookList();

        assertEquals(expectedBookDTOs, actualBookDTOs);
    }

    @Test
    void testFetchBookById_NormalScenario() throws BookNotFoundException {
        Long bookId = 1L;
        Book mockBook = createMockBook(bookId, "123456", "Harry Potter", "Fantasy", "A book about the boy who lived", "J.K. Rowling");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        BookDTO expectedBookDTO = createMockBookDTO("123456", "Harry Potter", "Fantasy", "A book about the boy who lived", "J.K. Rowling");
        when(bookMapper.bookToBookDTO(mockBook)).thenReturn(expectedBookDTO);

        BookDTO actualBookDTO = bookService.fetchBookById(bookId);

        assertEquals(expectedBookDTO, actualBookDTO);
    }

    @Test
    void testFetchBookById_BookNotFound() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.fetchBookById(bookId));
    }

    @Test
    void testFetchBookByIsbn_NormalScenario() throws BookNotFoundException {
        String bookIsbn = "123456";
        Book mockBook = createMockBook(1L, bookIsbn, "Harry Potter", "Fantasy", "A book about the boy who lived", "J.K. Rowling");
        when(bookRepository.findByIsbn(bookIsbn)).thenReturn(Optional.of(mockBook));

        BookDTO expectedBookDTO = createMockBookDTO("123456", "Harry Potter", "Fantasy", "A book about the boy who lived", "J.K. Rowling");
        when(bookMapper.bookToBookDTO(mockBook)).thenReturn(expectedBookDTO);

        BookDTO actualBookDTO = bookService.fetchBookByIsbn(bookIsbn);

        assertEquals(expectedBookDTO, actualBookDTO);
    }

    @Test
    void testFetchBookByIsbn_BookNotFound() {
        String bookIsbn = "123456";
        when(bookRepository.findByIsbn(bookIsbn)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.fetchBookByIsbn(bookIsbn));
    }

    @Test
    void testDeleteBookById_NormalScenario() throws BookNotFoundException {
        Long bookId = 1L;
        Book mockBook = createMockBook(bookId, "123456", "Harry Potter", "Fantasy", "A book about the boy who lived", "J.K. Rowling");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        bookService.deleteBookById(bookId);
    }

    @Test
    void testDeleteBookById_BookNotFound() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBookById(bookId));
    }

    @Test
    void testUpdateBookById_NormalScenario() throws BookNotFoundException {
        Long bookId = 1L;
        Book mockBook = createMockBook(bookId, "123456", "Harry Potter", "Fantasy", "A book about the boy who lived", "J.K. Rowling");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        BookDTO updatedBookDTO = createMockBookDTO("123456", "New Title", "Updated Genre", "Updated Description", "New Author");

        BookDTO updatedBook = bookService.updateBookById(bookId, updatedBookDTO);
    }

    @Test
    void testUpdateBookById_BookNotFound() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        BookDTO updatedBookDTO = createMockBookDTO("123456", "New Title", "Updated Genre", "Updated Description", "New Author");

        assertThrows(BookNotFoundException.class, () -> bookService.updateBookById(bookId, updatedBookDTO));
    }

    private Book createMockBook(Long id, String isbn, String title, String genre, String description, String author) {
        Book book = new Book();
        book.setId(id);
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
