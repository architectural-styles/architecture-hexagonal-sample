package com.hexagonal.sample.adapters.out.jpa;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Package-private.
 */
@Entity
@Table(name = "users")
class UserJpaEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    protected UserJpaEntity() {}

    public UserJpaEntity(String id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public String id()           { return id; }
    public String name()         { return name; }
    public LocalDate birthDate() { return birthDate; }

    public void setId(String id) { this.id = id;}
    public void setName(String name) { this.name = name;}
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate;}
}