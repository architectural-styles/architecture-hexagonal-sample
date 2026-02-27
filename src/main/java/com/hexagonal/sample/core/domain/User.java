package com.hexagonal.sample.core.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Core domain entity representing a user.
 * Enforces invariants: non-null ID and name, non-blank name, non-future birthDate.
 */
public record User(String id, String name, LocalDate birthDate) {
    public User {
        Objects.requireNonNull(id, "Id required");
        Objects.requireNonNull(name, "Name required");
        if (name.isBlank()) throw new IllegalArgumentException("Name blank");
        if (birthDate.isAfter(LocalDate.now())) throw new IllegalArgumentException("Future date");
    }
}
