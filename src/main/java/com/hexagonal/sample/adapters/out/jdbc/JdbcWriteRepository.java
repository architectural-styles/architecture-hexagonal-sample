package com.hexagonal.sample.adapters.out.jdbc;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.core.exception.UserNotFoundException;
import com.hexagonal.sample.ports.out.WriteRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Objects;

@Profile("jdbc")
public class JdbcWriteRepository implements WriteRepository {

    private final JdbcClient jdbc;

    public JdbcWriteRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    private interface Sql {
        String INSERT = """
            INSERT INTO users (id, name, birth_date)
            VALUES (:id, :name, :birthDate)
        """;

        String UPDATE = """
            UPDATE users
            SET name = :name, birth_date = :birthDate
            WHERE id = :id
        """;

        String DELETE = """
            DELETE FROM users WHERE id = :id
        """;
    }

    @Override
    public void save(User user) {
        Objects.requireNonNull(user, "User must not be null");

        jdbc.sql(Sql.INSERT)
                .param("id", user.id())
                .param("name", user.name())
                .param("birthDate", user.birthDate())
                .update();
    }

    @Override
    public void update(String id, User user) {
        Objects.requireNonNull(id, "Id must not be null");
        Objects.requireNonNull(user, "User must not be null");

        ensureFound(jdbc.sql(Sql.UPDATE)
                .param("id", id)
                .param("name", user.name())
                .param("birthDate", user.birthDate())
                .update(), id);
    }

    @Override
    public void deleteById(String id) {
        Objects.requireNonNull(id, "Id must not be null");
        ensureFound(jdbc.sql(Sql.DELETE).param("id", id).update(), id);
    }

    private void ensureFound(int count, String id) {
        if (count != 1) throw new UserNotFoundException(id);
    }

}
