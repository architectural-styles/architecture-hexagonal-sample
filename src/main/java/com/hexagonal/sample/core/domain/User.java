package com.hexagonal.sample.core.domain;

import java.time.LocalDate;

public record User(String id, String name, LocalDate birthDate) {

    public static User create(String id, String name, LocalDate birthDate) {
        return new User(id, name, birthDate);
    }

    public static User withoutId(String name, LocalDate birthDate) {
        return new User(null, name, birthDate);
    }

}