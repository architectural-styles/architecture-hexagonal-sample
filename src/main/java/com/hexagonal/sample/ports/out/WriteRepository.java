package com.hexagonal.sample.ports.out;

import com.hexagonal.sample.core.domain.User;

public interface WriteRepository {

    void save(User user);
    void update(String id, User user);
    void deleteById(String id);

}
