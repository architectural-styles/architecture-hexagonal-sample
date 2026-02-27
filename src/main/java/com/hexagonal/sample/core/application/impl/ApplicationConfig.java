package com.hexagonal.sample.core.application.impl;

import com.hexagonal.sample.ports.in.CommandUseCase;
import com.hexagonal.sample.ports.in.QueryUseCase;
import com.hexagonal.sample.ports.out.ReadRepository;
import com.hexagonal.sample.ports.out.WriteRepository;
import com.hexagonal.sample.ports.out.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Package-private.
 */
@Configuration
class ApplicationConfig {

    @Bean
    CommandUseCase commandService(IdGenerator idGenerator, WriteRepository repo) {
        return new UserCommandService(idGenerator, repo);
    }

    @Bean
    QueryUseCase queryService(ReadRepository repo) {
        return new UserQueryService(repo);
    }
}
