package com.hexagonal.sample.adapters.out.id;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.hexagonal.sample.ports.out.IdGenerator;

@Configuration
class IdConfig {

    @Bean
    IdGenerator idGenerator() {
        return new UuidV7IdGenerator();
    }
}
