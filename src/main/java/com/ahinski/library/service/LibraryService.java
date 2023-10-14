package com.ahinski.library.service;

import com.ahinski.library.dto.BookDTO;
import com.ahinski.library.dto.LibraryRecordDTO;
import com.ahinski.library.exception.BookNotFoundException;

import java.util.List;

public interface LibraryService {

    LibraryRecordDTO addRecord(BookDTO bookDTO);

    List<LibraryRecordDTO> fetchAllFreeLibraryRecords();

    LibraryRecordDTO borrowBook(Long bookId) throws BookNotFoundException;

    LibraryRecordDTO returnBook(Long bookId) throws BookNotFoundException;

    boolean isBookBorrowed(Long bookId) throws BookNotFoundException;

    void deleteByBookId(Long bookId) throws BookNotFoundException;

}
