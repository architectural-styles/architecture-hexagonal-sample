package com.hexagonal.sample.ports.out;

import com.hexagonal.sample.core.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * Port for reading users from the data store.
 * Implementations are provided per infrastructure profile: jdbc, jooq, jpa.
 */
public interface ReadRepository {

    /**
     * Returns a user by ID, or empty if not found.
     */
    Optional<User> findById(String id);

    /**
     * Returns all users ordered by ID.
     */
    List<User> findAll();

    /**
     * Returns users whose name starts with the given prefix, case-insensitive, ordered by name.
     */
    List<User> findByNameStartingWith(String prefix);
}
