package com.hexagonal.sample.e2e.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.context.jdbc.Sql;

import com.hexagonal.sample.TestData;

@ActiveProfiles("jdbc")
@AutoConfigureRestTestClient
@Sql({"/test-schema.sql", "/test-data.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestQueryControllerE2ETest {

    @Autowired RestTestClient client;

    @Test
    void shouldFindById() {
        client.get().uri("/api/users/" + TestData.ALICE_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(TestData.ALICE_ID)
                .jsonPath("$.name").isEqualTo("Alice")
                .jsonPath("$.birthDate").isEqualTo("1990-01-01");
    }

    @Test
    void shouldReturn404ForUnknownUser() {
        client.get().uri("/api/users/non-existent-id")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("User not found: non-existent-id");
    }

    @Test
    void shouldReturnAllUsers() {
        client.get().uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[0].name").isEqualTo("Alice")
                .jsonPath("$[1].name").isEqualTo("Bob")
                .jsonPath("$[2].name").isEqualTo("Anna");
    }

    @Test
    void shouldFindByPrefix() {
        client.get().uri("/api/users?namePrefix=An")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].id").isEqualTo(TestData.ANNA_ID)
                .jsonPath("$[0].name").isEqualTo("Anna")
                .jsonPath("$[0].birthDate").isEqualTo("2000-03-15");
    }
}
