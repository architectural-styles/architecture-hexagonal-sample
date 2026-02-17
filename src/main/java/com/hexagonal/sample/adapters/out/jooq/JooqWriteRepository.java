package com.hexagonal.sample.adapters.out.jooq;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.core.exception.UserNotFoundException;
import com.hexagonal.sample.ports.out.WriteRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Objects;
import static org.jooq.impl.DSL.*;

@Profile("jooq")
public class JooqWriteRepository implements WriteRepository {

    private final DSLContext dsl;

    public JooqWriteRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    private interface Sql {
        String TABLE = "users";
        String ID = "id";
        String NAME = "name";
        String BIRTH_DATE = "birth_date";
    }

    @Override
    public void save(User user) {
        Objects.requireNonNull(user, "User must not be null");

        dsl.insertInto(table(Sql.TABLE))
                .set(field(Sql.ID, String.class), user.id())
                .set(field(Sql.NAME, String.class), user.name())
                .set(field(Sql.BIRTH_DATE, LocalDate.class), user.birthDate())
                .execute();
    }

    @Override
    public void update(String id, User user) {
        Objects.requireNonNull(id, "Id must not be null");
        Objects.requireNonNull(user, "User must not be null");

        int updated = dsl.update(table(Sql.TABLE))
                .set(field(Sql.NAME, String.class), user.name())
                .set(field(Sql.BIRTH_DATE, LocalDate.class), user.birthDate())
                .where(field(Sql.ID, String.class).eq(id))
                .execute();

        ensureFound(updated, id);
    }

    @Override
    public void deleteById(String id) {
        Objects.requireNonNull(id, "Id must not be null");

        int deleted = dsl.deleteFrom(table(Sql.TABLE))
                .where(field(Sql.ID, String.class).eq(id))
                .execute();

        ensureFound(deleted, id);
    }

    private void ensureFound(int count, String id) {
        if (count != 1) {
            throw new UserNotFoundException(id);}
    }

}
