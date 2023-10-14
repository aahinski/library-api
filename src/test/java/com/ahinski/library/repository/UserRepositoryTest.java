package com.ahinski.library.repository;

import com.ahinski.library.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        User user = new User("testUser", "test@example.com", "password");
        userRepository.save(user);

        User foundUser = userRepository.findByUsername("testUser").orElse(null);

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
    }

    @Test
    void testExistsByUsername() {
        User user = new User("existingUser", "existing@example.com", "password");
        userRepository.save(user);

        boolean exists = userRepository.existsByUsername("existingUser");

        assertTrue(exists);
    }

    @Test
    void testNotExistsByUsername() {
        User user = new User("nonExistingEmail", "nonExisting@example.com", "nonPassword");

        boolean exists = userRepository.existsByUsername("nonExistingUser");

        assertFalse(exists);
    }

    @Test
    void testExistsByEmail() {
        User user = new User("nonExistingUser", "existing@example.com", "password");
        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("existing@example.com");

        assertTrue(exists);
    }

    @Test
    void testNotExistsByEmail() {
        User user = new User("nonExistingEmail", "nonExisting@example.com", "nonPassword");

        boolean exists = userRepository.existsByEmail("nonExisting@example.com");

        assertFalse(exists);
    }
}
