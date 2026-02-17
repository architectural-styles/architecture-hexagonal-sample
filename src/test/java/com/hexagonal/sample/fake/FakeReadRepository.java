package com.hexagonal.sample.fake;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.ports.out.ReadRepository;

import java.time.LocalDate;
import java.util.*;

public class FakeReadRepository implements ReadRepository {

    private final Map<String, User> data = new HashMap<>();

    public FakeReadRepository() {
        // можем наполнить тестовыми данными
        data.put("1", new User("1", "Alice", LocalDate.parse("2000-01-01")));
        data.put("2", new User("2", "Bob", LocalDate.parse("1999-01-01")));
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<User> findByNameStartingWith(String prefix) {
        return data.values().stream()
                .filter(u -> u.name().startsWith(prefix))
                .toList();
    }
}
