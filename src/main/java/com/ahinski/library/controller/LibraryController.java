package com.ahinski.library.controller;

import com.ahinski.library.dto.LibraryRecordDTO;
import com.ahinski.library.exception.BookNotFoundException;
import com.ahinski.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/freeRecords")
    public List<LibraryRecordDTO> getAllFreeLibraryRecords() {
        return libraryService.fetchAllFreeLibraryRecords();
    }

    @PostMapping("/borrow/{bookId}")
    public LibraryRecordDTO borrowBook(@PathVariable Long bookId) throws BookNotFoundException {
        return libraryService.borrowBook(bookId);
    }

    @PostMapping("/return/{libraryRecordId}")
    public LibraryRecordDTO returnBook(@PathVariable Long libraryRecordId) throws BookNotFoundException {
        return libraryService.returnBook(libraryRecordId);
    }

    @GetMapping("/isBorrowed/{bookId}")
    public boolean isBookBorrowed(@PathVariable Long bookId) throws BookNotFoundException {
        return libraryService.isBookBorrowed(bookId);
    }
}
