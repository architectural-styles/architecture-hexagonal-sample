package com.hexagonal.sample.fake;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.ports.out.WriteRepository;

public class FakeWriteRepository implements WriteRepository {

    public User lastSaved;
    public User lastUpdated;
    public String lastDeletedId;

    @Override
    public void save(User user) {
        this.lastSaved = user;
    }

    @Override
    public void update(User user) {
        this.lastUpdated = user;
    }

    @Override
    public void deleteById(String id) {
        this.lastDeletedId = id;
    }
}