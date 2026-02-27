package com.hexagonal.sample.integration.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hexagonal.sample.TestData;

@Transactional
@SpringBootTest
@ActiveProfiles("jdbc")
@AutoConfigureMockMvc
@Sql({"/test-schema.sql", "/test-data.sql"})
class RestQueryIntegrationTest {

    @Autowired MockMvc mvc;

    @Test
    void shouldFindById() throws Exception {
        mvc.perform(get("/api/users/" + TestData.ALICE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.ALICE_ID))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.birthDate").value("1990-01-01"));
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        mvc.perform(get("/api/users/non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Bob"))
                .andExpect(jsonPath("$[2].name").value("Anna"));
    }

    @Test
    void shouldFindByPrefix() throws Exception {
        mvc.perform(get("/api/users").param("namePrefix", "A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Anna"));
    }

    @Test
    void shouldFindByPrefixCaseInsensitive() throws Exception {
        mvc.perform(get("/api/users").param("namePrefix", "a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnEmptyListWhenNoPrefixMatch() throws Exception {
        mvc.perform(get("/api/users").param("namePrefix", "ZZZ"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnAllUsersWhenPrefixIsEmpty() throws Exception {
        mvc.perform(get("/api/users").param("namePrefix", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }
}
