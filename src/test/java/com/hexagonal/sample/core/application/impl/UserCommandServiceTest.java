package com.hexagonal.sample.core.application.impl;

import com.fasterxml.uuid.Generators;
import com.hexagonal.sample.core.application.command.CreateUserCommand;
import com.hexagonal.sample.core.application.command.UpdateUserCommand;
import com.hexagonal.sample.fake.FakeWriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class UserCommandServiceTest {

    private UserCommandService service;
    private FakeWriteRepository repo;

    @BeforeEach
    void setUp() {
        repo = new FakeWriteRepository();
        service = new UserCommandService(
                () -> Generators.timeBasedEpochGenerator().generate().toString(),
                repo
        );
    }

    @Test
    void createUser_assignsIdAndSavesUser() {
        String id = service.createUser(
                new CreateUserCommand("Jack", LocalDate.of(1990, 1, 1))
        );

        assertNotNull(id);
        assertFalse(id.isBlank());
        assertEquals("Jack", repo.lastSaved.name());
    }

    @Test
    void updateUser_savesUpdatedUser() {
        service.updateUser(
                new UpdateUserCommand("1", "Ann", LocalDate.of(1990, 1, 1))
        );

        assertEquals("Ann", repo.lastUpdated.name());
    }

    @Test
    void deleteUser_deletesById() {
        service.deleteUser("5");

        assertEquals("5", repo.lastDeletedId);
    }
}
