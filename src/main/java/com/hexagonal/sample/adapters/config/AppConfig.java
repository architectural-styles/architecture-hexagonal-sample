package com.hexagonal.sample.adapters.config;

import com.hexagonal.sample.adapters.out.id.SequentialIdGenerator;
import com.hexagonal.sample.adapters.out.jdbc.JdbcReadRepository;
import com.hexagonal.sample.adapters.out.jdbc.JdbcWriteRepository;
import com.hexagonal.sample.adapters.out.jooq.JooqReadRepository;
import com.hexagonal.sample.adapters.out.jooq.JooqWriteRepository;
import com.hexagonal.sample.core.service.UserCommandService;
import com.hexagonal.sample.core.service.UserQueryService;
import com.hexagonal.sample.ports.in.CommandUseCase;
import com.hexagonal.sample.ports.in.QueryUseCase;
import com.hexagonal.sample.ports.out.IdGenerator;
import com.hexagonal.sample.ports.out.ReadRepository;
import com.hexagonal.sample.ports.out.WriteRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
public class AppConfig {

    /* --------------------------------------------------
     *  OUT ADAPTERS: Write Repositories
     * -------------------------------------------------- */

    @Bean
    @Profile("jdbc")
    WriteRepository jdbcWriteRepository(JdbcClient jdbc) {
        return new JdbcWriteRepository(jdbc);
    }

    @Bean
    @Profile("jooq")
    WriteRepository jooqWriteRepository(DSLContext dsl) {
        return new JooqWriteRepository(dsl);
    }

    /* --------------------------------------------------
     *  OUT ADAPTERS: Read Repositories
     * -------------------------------------------------- */

    @Bean
    @Profile("jdbc")
    ReadRepository jdbcReadRepository(JdbcClient jdbc) {
        return new JdbcReadRepository(jdbc);
    }

    @Bean
    @Profile("jooq")
    ReadRepository jooqReadRepository(DSLContext dsl) {
        return new JooqReadRepository(dsl);
    }

    /* --------------------------------------------------
     *  OUT ADAPTERS: Id Generator
     * -------------------------------------------------- */

    @Bean
    IdGenerator idGenerator() {
        return new SequentialIdGenerator();
    }

    /* --------------------------------------------------
     *  CORE: Services (Use Cases)
     * -------------------------------------------------- */

    @Bean
    QueryUseCase queryService(ReadRepository readRepository) {
        return new UserQueryService(readRepository);
    }

    @Bean
    CommandUseCase commandService(
            IdGenerator idGenerator,
            WriteRepository writeRepository
    ) {
        return new UserCommandService(idGenerator, writeRepository);
    }

}

