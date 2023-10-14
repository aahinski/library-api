package com.ahinski.library.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LibraryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;

    public LibraryRecord() {
    }

    public LibraryRecord(Book book, LocalDateTime borrowTime, LocalDateTime returnTime) {
        this.book = book;
        this.borrowTime = borrowTime;
        this.returnTime = returnTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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

