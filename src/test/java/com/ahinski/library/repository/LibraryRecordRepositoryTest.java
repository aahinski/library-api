package com.ahinski.library.repository;

import com.ahinski.library.entity.Book;
import com.ahinski.library.entity.LibraryRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LibraryRecordRepositoryTest {

    @Autowired
    private LibraryRecordRepository libraryRecordRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Long bookId1;
    private Long bookId2;

    @BeforeEach
    void setUp() {
        Book book1 = new Book();
        book1.setTitle("Book 1");

        Book book2 = new Book();
        book1.setTitle("Book 2");

        entityManager.persistAndFlush(book1);
        entityManager.persistAndFlush(book2);

        bookId1 = book1.getId();
        bookId2 = book2.getId();

        LibraryRecord record1 = new LibraryRecord(book1, null, null);
        LibraryRecord record2 = new LibraryRecord(book2, LocalDateTime.now(), LocalDateTime.now().plusDays(7));

        entityManager.persistAndFlush(record1);
        entityManager.persistAndFlush(record2);
    }

    @Test
    void testExistsByBookIdAndBorrowTimeIsNotNull() {
        Long bookIdWithBorrowTime = bookId1;
        assertFalse(libraryRecordRepository.existsByBookIdAndBorrowTimeIsNotNull(bookIdWithBorrowTime));
        Long bookIdWithNullBorrowTime = bookId2;
        assertTrue(libraryRecordRepository.existsByBookIdAndBorrowTimeIsNotNull(bookIdWithNullBorrowTime));
    }

    @Test
    void testFindWhereBorrowTimeIsNull() {
        List<LibraryRecord> records = libraryRecordRepository.findWhereBorrowTimeIsNull();
        assertTrue(records.contains(libraryRecordRepository.findByBookId(bookId1).get()));
        assertFalse(records.contains(libraryRecordRepository.findByBookId(bookId2).get()));
    }

    @Test
    void testDeleteByBookId() {
        List<LibraryRecord> records = libraryRecordRepository.findAll();
        LibraryRecord libraryRecord = libraryRecordRepository.findByBookId(bookId1).get();
        assertTrue(records.contains(libraryRecord));
        libraryRecordRepository.deleteByBookId(bookId1);
        records = libraryRecordRepository.findAll();
        assertFalse(records.contains(libraryRecord));
    }

    @Test
    void testFindByBookId() {
        Long bookIdToFind = bookId1;
        Optional<LibraryRecord> foundRecord = libraryRecordRepository.findByBookId(bookIdToFind);
        assertTrue(foundRecord.isPresent());
    }
}
