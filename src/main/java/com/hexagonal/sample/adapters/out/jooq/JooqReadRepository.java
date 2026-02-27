package com.hexagonal.sample.adapters.out.jooq;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.ports.out.ReadRepository;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.jooq.impl.DSL.*;


/**
 * Package-private.
 */
@Profile("jooq")
final class JooqReadRepository implements ReadRepository {

    private final DSLContext dsl;

    public JooqReadRepository(DSLContext dsl) { this.dsl = dsl;}

    private interface Sql {
        String TABLE = "users";
        String ID = "id";
        String NAME = "name";
        String BIRTH_DATE = "birth_date";
    }

    @Override
    public Optional<User> findById(String id) {
        Objects.requireNonNull(id, "Id must not be null");

        return dsl
                .select(
                        field(Sql.ID, String.class),
                        field(Sql.NAME, String.class),
                        field(Sql.BIRTH_DATE, LocalDate.class)
                )
                .from(table(Sql.TABLE))
                .where(field(Sql.ID).eq(id))
                .fetchOptionalInto(User.class);
    }

    @Override
    public List<User> findAll() {
        return dsl
                .select(
                        field(Sql.ID, String.class),
                        field(Sql.NAME, String.class),
                        field(Sql.BIRTH_DATE, LocalDate.class)
                )
                .from(table(Sql.TABLE))
                .orderBy(field(Sql.ID))
                .fetchInto(User.class);
    }

    @Override
    public List<User> findByNameStartingWith(String prefix) {
        Objects.requireNonNull(prefix, "Prefix must not be null");

        return dsl
                .select(
                        field(Sql.ID, String.class),
                        field(Sql.NAME, String.class),
                        field(Sql.BIRTH_DATE, LocalDate.class)
                )
                .from(table(Sql.TABLE))
                .where(DSL.lower(field(Sql.NAME, String.class))
                        .like(prefix.toLowerCase() + "%"))
                .orderBy(field(Sql.NAME))
                .fetchInto(User.class);
    }

}
