package com.hexagonal.sample.ports.in;

import com.hexagonal.sample.core.application.command.CreateUserCommand;
import com.hexagonal.sample.core.application.command.UpdateUserCommand;

/**
 * Defines write operations on users.
 * All methods are transactional.
 */
public interface CommandUseCase {

    /**
     * Creates a new user and returns the generated ID.
     */
    String createUser(CreateUserCommand command);

    /**
     * Updates an existing user.
     */
    void updateUser(UpdateUserCommand command);

    /**
     * Deletes a user by ID.
     */
    void deleteUser(String id);
}
