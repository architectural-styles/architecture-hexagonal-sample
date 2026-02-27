package com.hexagonal.sample.e2e.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.client.RestTestClient;

@ActiveProfiles("jdbc")
@Sql("/test-schema.sql")
@AutoConfigureRestTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestCommandControllerE2ETest {

    @Autowired
    private RestTestClient client;

    @Test
    void shouldCreateUser() {
        String location = createUser("Alice", "1990-01-01");
        String id = extractId(location);

        client.get().uri("/api/users/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo("Alice")
                .jsonPath("$.birthDate").isEqualTo("1990-01-01");
    }

    @Test
    void shouldUpdateUser() {
        String location = createUser("Bob", "1985-05-20");
        String id = extractId(location);

        client.put().uri("/api/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                {"name": "Bob Updated", "birthDate": "1985-05-20"}
                """)
                .exchange()
                .expectStatus().isNoContent();

        client.get().uri("/api/users/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo("Bob Updated")
                .jsonPath("$.birthDate").isEqualTo("1985-05-20");
    }

    @Test
    void shouldDeleteUser() {
        String location = createUser("Charlie", "2000-03-15");
        String id = extractId(location);

        client.delete().uri("/api/users/" + id)
                .exchange()
                .expectStatus().isNoContent();

        client.get().uri("/api/users/" + id)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("User not found: " + id);
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentUser() {
        client.put().uri("/api/users/99")
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                {"name": "Ghost", "birthDate": "1990-01-01"}
                """)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("User not found: 99");
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentUser() {
        client.delete().uri("/api/users/99")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("User not found: 99");
    }

    private String createUser(String name, String birthDate) {
        return client.post().uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {"name": "%s", "birthDate": "%s"}
                        """.formatted(name, birthDate))
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Void.class)
                .getResponseHeaders()
                .getFirst("Location");
    }

    private String extractId(String location) {
        return location.substring(location.lastIndexOf("/") + 1);
    }

}
