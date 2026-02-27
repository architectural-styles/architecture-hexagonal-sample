package com.hexagonal.sample.ports.out;

import com.hexagonal.sample.core.domain.User;

/**
 * Port for writing users to the data store.
 * Implementations are provided per infrastructure profile: jdbc, jooq, jpa.
 */
public interface WriteRepository {

    /**
     * Persists a new user.
     */
    void save(User user);

    /**
     * Updates an existing user.
     * Throws {@link com.hexagonal.sample.core.domain.UserNotFoundException}
     * if no user exists with the given ID.
     */
    void update(User user);

    /**
     * Deletes a user by ID.
     * Throws {@link com.hexagonal.sample.core.domain.UserNotFoundException}
     * if no user exists with the given ID.
     */
    void deleteById(String id);
}
