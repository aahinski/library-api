package com.ahinski.library.dto;

import java.time.LocalDateTime;

public class LibraryRecordDTO {

    private Long bookId;
    private String bookTitle;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;

    // Constructors

    public LibraryRecordDTO() {
        // Default constructor
    }

    public LibraryRecordDTO(Long bookId, String bookTitle, LocalDateTime borrowTime, LocalDateTime returnTime) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowTime = borrowTime;
        this.returnTime = returnTime;
    }

    // Getters and Setters

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDateTime getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(LocalDateTime borrowTime) {
        this.borrowTime = borrowTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }
}

