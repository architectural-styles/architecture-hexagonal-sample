package com.hexagonal.sample.adapters.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package-private.
 */
interface UserJpaRepository extends JpaRepository<UserJpaEntity, String> {
    List<UserJpaEntity> findAllByOrderById();
    List<UserJpaEntity> findByNameStartingWithIgnoreCaseOrderByName(String prefix);
}
