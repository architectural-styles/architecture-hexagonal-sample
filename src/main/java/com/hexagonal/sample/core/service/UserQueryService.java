package com.hexagonal.sample.core.service;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.core.exception.UserNotFoundException;
import com.hexagonal.sample.ports.in.QueryUseCase;
import com.hexagonal.sample.ports.out.ReadRepository;

import java.util.List;

public final class UserQueryService implements QueryUseCase {

    private final ReadRepository users;

    public UserQueryService(ReadRepository users) { this.users = users;}

    public User findById(String id) {
        return users.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> findAll() {
        return users.findAll();
    }

    public List<User> findByNameStartingWith(String prefix) {
        return users.findByNameStartingWith(prefix);
    }

}
