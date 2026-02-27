package com.hexagonal.sample.core.application.impl;

import com.hexagonal.sample.ports.in.CommandUseCase;
import com.hexagonal.sample.core.application.command.CreateUserCommand;
import com.hexagonal.sample.core.application.command.UpdateUserCommand;
import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.ports.out.WriteRepository;
import com.hexagonal.sample.ports.out.IdGenerator;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Package-private.
 */
class UserCommandService implements CommandUseCase {

    private final IdGenerator idGenerator;
    private final WriteRepository repository;

    public UserCommandService(
            IdGenerator idGenerator,
            WriteRepository repository
    ) {
        this.idGenerator = idGenerator;
        this.repository = repository;
    }

    @Override
    @Transactional
    public String createUser(CreateUserCommand command) {
        Objects.requireNonNull(command, "User must not be null");
        String newId = idGenerator.nextId();
        repository.save(new User(newId, command.name(), command.birthDate()));
        return newId;
    }

    @Override
    @Transactional
    public void updateUser(UpdateUserCommand command) {
        repository.update(new User(command.id(), command.name(), command.birthDate()));
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        repository.deleteById(id);
    }
}
