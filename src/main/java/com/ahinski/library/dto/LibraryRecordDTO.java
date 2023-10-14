package com.ahinski.library.dto;

import java.time.LocalDateTime;

public class LibraryRecordDTO {

    private Long bookId;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;

    public LibraryRecordDTO() {
    }

    public LibraryRecordDTO(Long bookId, LocalDateTime borrowTime, LocalDateTime returnTime) {
        this.bookId = bookId;
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

