# Hexagonal Architecture — Sample / Boilerplate

A reference boilerplate for a hexagonal (Ports & Adapters) architecture built with Spring Boot.  

The goal is to demonstrate strict boundary enforcement between the application core and the outside world, effortless infrastructure replacement, and a deliberate testing pyramid.  

The domain is intentionally minimal — a single `User` entity with full CRUD — so the architectural patterns remain in focus rather than getting buried in business details.

> **See also:** [application-layered-sample](../application-layered-sample) — the same feature set implemented as a classic layered monolith. The two projects are designed to be read side by side: the layered version makes the migration path toward hexagonal architecture self-evident, requiring nothing more than relocating the port interfaces and renaming packages.

---

## Architecture

```
      ┌─────────────────────────────────────────┐
      │                  core                   │
      │                                         │
      │   domain  ◄───  application             │
      │                     ▲                   │
      └─────────────────────│───────────────────┘
               ports.in ────┘──── ports.out
                  ▲                   ▲
           adapters.in          adapters.out
        (REST, MVC forms)   (JDBC, jOOQ, JPA, ID)
```

Layer | Package | Responsibility
------|---------|---------------
**Domain** | `core.domain` | Core entities and domain exceptions. No outward dependencies whatsoever.
**Application** | `core.application` | Use-case implementations. Depends only on `core.domain` and the ports in `ports.out`. Package-private — never accessed directly from outside.
**Ports In** | `ports.in` | Driving ports: `CommandUseCase`, `QueryUseCase`. The API the application exposes to the outside world.
**Ports Out** | `ports.out` | Driven ports: `ReadRepository`, `WriteRepository`, `IdGenerator`. The API the application requires from the outside world.
**Adapters In** | `adapters.in.*` | Driving adapters: REST controllers and MVC controllers (JTE templates). Call the application through `ports.in`.
**Adapters Out** | `adapters.out.*` | Driven adapters: JDBC, jOOQ, JPA, UUID v7 generator. Implement `ports.out`. Switched via Spring profile.

### Design Decisions

**Explicit port separation.** Ports are divided into `ports.in` (what drives the application) and `ports.out` (what the application drives). This is the defining characteristic of hexagonal architecture — the application core has no knowledge of who calls it or what it calls.

**Package-private implementations.** `UserCommandService`, `UserQueryService`, and all adapter classes are declared without `public`. Only port interfaces are visible outside their package. Spring wires them as beans through `@Configuration` classes that live in the same package. Package visibility is used as an architectural boundary — not an accidental omission, but a deliberate encapsulation tool.

**CQRS hint.** `CommandUseCase` and `QueryUseCase` are separate interfaces. Adapters inject only what they actually need. `UserQueryService` is annotated with `@Transactional(readOnly = true)` — a deliberate optimization that most developers overlook: read-only transactions reduce overhead at both the JDBC driver and ORM levels.

**Swappable infrastructure.** Three complete driven adapter implementations (JDBC via the modern `JdbcClient`, jOOQ, JPA) are activated by profile with zero changes to the application core. This is a direct, working demonstration of the Liskov Substitution Principle and Dependency Inversion — in practice, not just in theory.

**`JdbcClient` over `JdbcTemplate`.** The project uses the API introduced in Spring 6.1 — concise, fluent, and free of boilerplate. A deliberate choice of the modern tool over the one kept out of habit.

**JPA entities never leak out.** `UserJpaEntity` is a package-private class. A dedicated `UserJpaMapper` converts between the entity and the domain object in both directions. The boundary between the persistence model and the domain is drawn explicitly — not blurred by placing `@Entity` on a domain class, which is a common mistake notably absent here.

**`Mapper` as a static utility class.** A deliberate simplification — no mapping framework is involved. In a production project this would typically be replaced by MapStruct or a Spring-managed component, depending on mapping complexity and testability requirements.

**Two UIs, one core.** REST controllers and MVC controllers (with HTML forms) are both driving adapters on top of the same `ports.in`. A concrete proof that the delivery mechanism has no bearing on business logic.

**Separate error handling.** Two independent `@ControllerAdvice` classes — `GlobalRestExceptionHandler` and `GlobalMvcExceptionHandler` — each scoped to its own package. REST returns [RFC 9457 Problem Details](https://www.rfc-editor.org/rfc/rfc9457); MVC renders a template with an error message. Not a single catch-all solution, but context-appropriate handling for each protocol.

---

## Project Structure

```
src/main/java/.../
├── core/
│   ├── domain/                # User record, UserNotFoundException
│   └── application/
│       ├── command/           # CreateUserCommand, UpdateUserCommand
│       ├── query/             # UserView
│       └── impl/              # UserCommandService, UserQueryService, ApplicationConfig
├── ports/
│   ├── in/                    # CommandUseCase, QueryUseCase
│   └── out/                   # ReadRepository, WriteRepository, IdGenerator
└── adapters/
    ├── in/
    │   ├── common/dto/        # DTOs, Mapper
    │   ├── rest/              # RestQueryController, RestCommandController,
    │   │                      # GlobalRestExceptionHandler
    │   └── mvc/               # MvcQueryController, MvcFormController,
    │                          # MvcCommandController, GlobalMvcExceptionHandler
    └── out/
        ├── jdbc/              # JdbcReadRepository, JdbcWriteRepository
        ├── jooq/              # JooqReadRepository, JooqWriteRepository
        ├── jpa/               # JpaReadRepository, JpaWriteRepository,
        │                      # UserJpaEntity, UserJpaMapper
        └── id/                # UuidV7IdGenerator
```

---

## Running the Application

```bash
# Default — jdbc profile, H2 in-memory, port 8083
./mvnw spring-boot:run

# Switch to jOOQ
./mvnw spring-boot:run -Dspring-boot.run.profiles=jooq

# Switch to JPA
./mvnw spring-boot:run -Dspring-boot.run.profiles=jpa
```

The H2 console is available at `http://localhost:8083/h2`.

---

## API

### REST

Method | URL | Description
-------|-----|------------
`GET` | `/api/users` | List all users
`GET` | `/api/users?namePrefix=Al` | Filter by name prefix (case-insensitive)
`GET` | `/api/users/{id}` | Get user by ID
`POST` | `/api/users` | Create a user (`Location` header in response)
`PUT` | `/api/users/{id}` | Update a user
`DELETE` | `/api/users/{id}` | Delete a user

Errors are returned as [RFC 9457 Problem Details](https://www.rfc-editor.org/rfc/rfc9457).

### MVC (HTML)

Entry point: `http://localhost:8083/mvc/users`

> **Note.** The MVC interface exists to demonstrate dual-presentation — two UIs on top of a single application layer. In a real application, name search would be the only entry point: a UUID is an internal system identifier and is never exposed to the user.

---

## Tests

The testing pyramid is structured deliberately — each level has a distinct purpose:

```
            [E2E]           RestTestClient, random real HTTP port
          [Integration]     @SpringBootTest + MockMvc + H2 + @Sql
        [Slice]             @WebMvcTest — controllers in full isolation
      [Unit]                Services via Fake repositories, no Mockito
    [Arch]                  ArchUnit — dependency rules enforced on bytecode
```

**Fake repositories over Mockito** in service unit tests — not laziness in writing a mock, but a deliberate choice: a Fake provides real behavior, so the test verifies logic rather than asserting that a particular method was called.

**`@Sql` with separate files** `test-schema.sql` / `test-data.sql` — schema and fixtures are decoupled. Command integration tests use only the schema (`@Sql("/test-schema.sql")`); query integration tests also load data (`@Sql({"/test-schema.sql", "/test-data.sql"})`). Each test gets exactly the state it needs — nothing more.

**`RestTestClient` in E2E** — a dedicated client over a real HTTP port, not MockMvc. The distinction matters: MockMvc exercises the Spring MVC dispatch pipeline; `RestTestClient` goes through the full network stack.

**ArchUnit rules** — dependency constraints are verified automatically on every build:

- Adapters do not depend on each other
- Adapters do not access `core.application.impl` directly
- Core does not depend on adapters
- Domain does not depend on anything — not even other `core` packages

```bash
./mvnw test
```

---

## Dependencies

- Java 21, Spring Boot 3.x
- H2 (in-memory, development and tests only)
- Spring Web MVC — REST and HTML controllers
- Spring JdbcClient (6.1+), jOOQ, Spring Data JPA — three interchangeable adapters
- JTE — template engine for MVC
- `com.fasterxml.uuid` — UUID v7 generation
- ArchUnit — architectural tests
