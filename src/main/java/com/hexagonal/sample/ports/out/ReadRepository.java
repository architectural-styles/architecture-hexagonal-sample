package com.hexagonal.sample.ports.out;

import com.hexagonal.sample.core.domain.User;

import java.util.List;
import java.util.Optional;

public interface ReadRepository {

    Optional<User> findById(String id);
    List<User> findAll();
    List<User> findByNameStartingWith(String prefix);

}
