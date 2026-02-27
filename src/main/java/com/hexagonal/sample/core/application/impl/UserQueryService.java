package com.hexagonal.sample.core.application.impl;

import com.hexagonal.sample.ports.in.QueryUseCase;
import com.hexagonal.sample.core.application.query.UserView;
import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.core.domain.UserNotFoundException;
import com.hexagonal.sample.ports.out.ReadRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Package-private.
 */
@Transactional(readOnly = true)
class UserQueryService implements QueryUseCase {

    private final ReadRepository users;

    public UserQueryService(ReadRepository users) {
        this.users = users;
    }

    public UserView findById(String id) {
        return toView(
                users.findById(id).orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    public List<UserView> findAll() {
        return users.findAll().stream().map(this::toView).toList();
    }

    public List<UserView> findByNameStartingWith(String prefix) {
        return users.findByNameStartingWith(prefix).stream().map(this::toView).toList();
    }

    private UserView toView(User u) {
        return new UserView(u.id(), u.name(), u.birthDate());
    }
}
