package com.hexagonal.sample.adapters.out.jdbc;

import com.hexagonal.sample.ports.out.ReadRepository;
import com.hexagonal.sample.ports.out.WriteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;


/**
 * Package-private.
 */
@Profile("jdbc")
@Configuration
class JdbcConfig {

    @Bean
    WriteRepository jdbcWriteRepository(JdbcClient jdbc) {
        return new JdbcWriteRepository(jdbc);
    }

    @Bean
    ReadRepository jdbcReadRepository(JdbcClient jdbc) {
        return new JdbcReadRepository(jdbc);
    }
}
