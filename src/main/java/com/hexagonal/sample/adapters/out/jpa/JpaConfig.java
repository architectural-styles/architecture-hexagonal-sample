package com.hexagonal.sample.adapters.out.jpa;

import com.hexagonal.sample.ports.out.ReadRepository;
import com.hexagonal.sample.ports.out.WriteRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * Package-private.
 */
@Profile("jpa")
@Configuration
class JpaConfig {

    @Bean
    WriteRepository jpaWriteRepository(EntityManager em) {
        return new JpaWriteRepository(em);
    }

    @Bean
    ReadRepository jpaReadRepository(UserJpaRepository jpa) {
        return new JpaReadRepository(jpa);
    }
}
