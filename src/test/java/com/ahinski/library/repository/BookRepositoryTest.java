package com.ahinski.library.repository;

import com.ahinski.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Book book = new Book();
        book.setIsbn("123456");
        book.setTitle("Harry Potter");
        book.setGenre("Fantasy");
        book.setDescription("A book he boy who lived");
        book.setAuthor("J.K. Rowling");

        entityManager.persist(book);
    }

    @Test
    void whenFindByIsbn_thenReturnBook() {
        Book book = bookRepository.findByIsbn("123456").get();
        assertEquals(book.getTitle(), "Harry Potter");
    }
}