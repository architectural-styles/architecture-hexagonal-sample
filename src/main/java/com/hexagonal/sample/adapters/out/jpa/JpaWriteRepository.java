package com.hexagonal.sample.adapters.out.jpa;

import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.core.domain.UserNotFoundException;
import com.hexagonal.sample.ports.out.WriteRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Profile;


/**
 * Package-private.
 */
@Profile("jpa")
final class JpaWriteRepository implements WriteRepository {

    private final EntityManager em;

    public JpaWriteRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(User user) {
        em.persist(new UserJpaEntity(user.id(), user.name(), user.birthDate()));
    }

    @Override
    public void update(User user) {
        UserJpaEntity entity = em.find(UserJpaEntity.class, user.id());
        if (entity == null) throw new UserNotFoundException(user.id());
        entity.setName(user.name());
        entity.setBirthDate(user.birthDate());
    }

    @Override
    public void deleteById(String id) {
        UserJpaEntity entity = em.find(UserJpaEntity.class, id);
        if (entity == null) throw new UserNotFoundException(id);
        em.remove(entity);
    }
}
