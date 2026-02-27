package com.hexagonal.sample.adapters.out.jooq;

import com.hexagonal.sample.ports.out.ReadRepository;
import com.hexagonal.sample.ports.out.WriteRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Package-private.
 */
@Profile("jooq")
@Configuration
class JooqConfig {

    @Bean
    WriteRepository jooqWriteRepository(DSLContext dsl) {
        return new JooqWriteRepository(dsl);
    }

    @Bean
    ReadRepository jooqReadRepository(DSLContext dsl) {
        return new JooqReadRepository(dsl);
    }
}
