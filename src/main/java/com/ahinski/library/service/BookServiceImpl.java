package com.ahinski.library.service;

import com.ahinski.library.dto.BookDTO;
import com.ahinski.library.entity.Book;
import com.ahinski.library.entity.LibraryRecord;
import com.ahinski.library.exception.BookNotFoundException;
import com.ahinski.library.mapper.BookMapper;
import com.ahinski.library.repository.BookRepository;
import com.ahinski.library.repository.LibraryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    LibraryRecordRepository libraryRecordRepository;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public BookDTO saveBook(BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        bookRepository.save(book);
        LibraryRecord libraryRecord = new LibraryRecord();
        libraryRecord.setBook(book);
        libraryRecordRepository.save(libraryRecord);
        return bookMapper.bookToBookDTO(book);
    }

    @Override
    public List<BookDTO> fetchBookList() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.booksToBookDTOs(books);
    }

    @Override
    public BookDTO fetchBookById(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return bookMapper.bookToBookDTO(book);
        } else {
            throw new BookNotFoundException("No book found to fetch");
        }
    }

    @Override
    public void deleteBookById(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            libraryRecordRepository.deleteByBookId(bookId);
            bookRepository.deleteById(bookId);
        } else {
            throw new BookNotFoundException("No book found to delete");
        }
    }

    @Override
    public BookDTO updateBookById(Long bookId, BookDTO updatedBookDTO) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setIsbn(updatedBookDTO.getIsbn());
            book.setAuthor(updatedBookDTO.getAuthor());
            book.setDescription(updatedBookDTO.getDescription());
            book.setGenre(updatedBookDTO.getGenre());
            book.setTitle(updatedBookDTO.getTitle());
            bookRepository.save(book);
            return bookMapper.bookToBookDTO(book);
        } else {
            throw new BookNotFoundException("No book found to update");
        }
    }

    @Override
    public BookDTO fetchBookByIsbn(String isbn) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return bookMapper.bookToBookDTO(book);
        } else {
            throw new BookNotFoundException("No book found to fetch");
        }
    }
}