package com.hexagonal.sample.unit;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.core.exception.UserNotFoundException;
import com.hexagonal.sample.core.service.UserQueryService;
import com.hexagonal.sample.fake.FakeReadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserQueryServiceTest {

    private UserQueryService service;

    @BeforeEach
    void setUp() {
        service = new UserQueryService(new FakeReadRepository());
    }

    @Test
    void findById_returnsUser() {
        User user = service.findById("1");

        assertEquals("Alice", user.name());
    }

    @Test
    void findById_throwsIfNotFound() {
        assertThrows(UserNotFoundException.class,
                () -> service.findById("999")
        );
    }

    @Test
    void findAll_returnsTwoUsers() {
        List<User> users = service.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void findByNameStartsWith_filtersCorrectly() {
        List<User> users = service.findByNameStartingWith("A");

        assertEquals(1, users.size());
        assertEquals("Alice", users.get(0).name());
    }
}
