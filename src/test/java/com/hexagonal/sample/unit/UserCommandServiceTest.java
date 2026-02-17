package com.hexagonal.sample.unit;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.core.service.UserCommandService;
import com.hexagonal.sample.fake.FakeIdGenerator;
import com.hexagonal.sample.fake.FakeWriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserCommandServiceTest {

    private UserCommandService service;
    private FakeWriteRepository repo;

    @BeforeEach
    void setUp() {
        repo = new FakeWriteRepository();
        service = new UserCommandService(new FakeIdGenerator(), repo);
    }

    @Test
    void createUser_assignsIdAndSavesUser() {
        String id = service.createUser(new User(null, "Charlie", LocalDate.parse("2010-01-01")));

        assertEquals("42", id);
        assertEquals("Charlie", repo.lastSaved.name());
    }

    @Test
    void updateUser_savesUpdatedUser() {
        service.updateUser("5", new User("5", "Neo", LocalDate.parse("1990-01-01")));

        assertEquals("Neo", repo.lastUpdated.name());
    }

    @Test
    void deleteUser_deletesById() {
        service.deleteUser("5");

        assertEquals("5", repo.lastDeletedId);
    }
}
