package com.hexagonal.sample.adapters.out.jooq;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.core.domain.UserNotFoundException;
import com.hexagonal.sample.ports.out.WriteRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Objects;
import static org.jooq.impl.DSL.*;


/**
 * Package-private.
 */
@Profile("jooq")
final class JooqWriteRepository implements WriteRepository {

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
    public void update(User user) {
        Objects.requireNonNull(user, "User must not be null");
        Objects.requireNonNull(user.id(), "Id must not be null");

        int updated = dsl.update(table(Sql.TABLE))
                .set(field(Sql.NAME, String.class), user.name())
                .set(field(Sql.BIRTH_DATE, LocalDate.class), user.birthDate())
                .where(field(Sql.ID, String.class).eq(user.id()))
                .execute();

        ensureFound(updated, user.id());
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
