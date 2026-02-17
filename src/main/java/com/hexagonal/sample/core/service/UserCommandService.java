package com.hexagonal.sample.core.service;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.ports.out.IdGenerator;
import com.hexagonal.sample.ports.in.CommandUseCase;
import com.hexagonal.sample.ports.out.WriteRepository;

import java.util.Objects;

public final class UserCommandService implements CommandUseCase {

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
    public String createUser(User user) {
        Objects.requireNonNull(user, "User must not be null");
        String newId = idGenerator.nextId();
        User newUser = User.create(newId, user.name(), user.birthDate());
        repository.save(newUser);
        return newId;
    }

    public void updateUser(String id, User user) {
        repository.update(id, user);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }

}
