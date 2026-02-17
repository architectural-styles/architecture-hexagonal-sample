package com.hexagonal.sample.ports.in;

import com.hexagonal.sample.core.domain.User;

public interface CommandUseCase {

    String createUser(User user);

    void updateUser(String id, User user);

    void deleteUser(String id);

}
