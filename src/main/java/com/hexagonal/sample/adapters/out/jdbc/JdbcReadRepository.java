package com.hexagonal.sample.adapters.out.jdbc;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.ports.out.ReadRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Package-private.
 */
@Profile("jdbc")
final class JdbcReadRepository implements ReadRepository {

    private final JdbcClient jdbc;

    public JdbcReadRepository(JdbcClient jdbc) { this.jdbc = jdbc;}

    private interface Sql {
        String SELECT_BY_ID = "SELECT id, name, birth_date FROM users WHERE id = :id";

        String SELECT_ALL = "SELECT id, name, birth_date FROM users ORDER BY id";

        String SELECT_BY_NAME_PREFIX = """
            SELECT id, name, birth_date
            FROM users
            WHERE LOWER(name) LIKE LOWER(CONCAT(:prefix, '%'))
            ORDER BY name
        """;
    }

    @Override
    public Optional<User> findById(String id) {
        return jdbc.sql(Sql.SELECT_BY_ID)
                .param("id", id)
                .query(User.class)
                .optional();
    }

    @Override
    public List<User> findAll() {
        return jdbc.sql(Sql.SELECT_ALL).query(User.class).list();
    }

    @Override
    public List<User> findByNameStartingWith(String prefix) {
        Objects.requireNonNull(prefix, "Prefix must not be null");

        return jdbc.sql(Sql.SELECT_BY_NAME_PREFIX)
                .param("prefix", prefix)
                .query(User.class)
                .list();
    }

}
