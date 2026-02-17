package com.hexagonal.sample.ports.in;

import com.hexagonal.sample.core.domain.User;

import java.util.List;

public interface QueryUseCase {

    User findById(String id);

    List<User> findAll();

    List<User> findByNameStartingWith(String prefix);

}
