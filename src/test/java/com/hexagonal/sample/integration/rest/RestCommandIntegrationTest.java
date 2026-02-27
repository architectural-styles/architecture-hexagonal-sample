package com.hexagonal.sample.integration.rest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@ActiveProfiles("jdbc")
@AutoConfigureMockMvc
@Sql("/test-schema.sql")
class RestCommandIntegrationTest {

    @Autowired MockMvc mvc;

    @Test
    void shouldCreateUser() throws Exception {
        MvcResult result = mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"name": "Alice", "birthDate": "1990-01-01"}
                        """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        String id = extractId(result);

        mvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        MvcResult result = mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"name": "Bob", "birthDate": "1985-05-20"}
                        """))
                .andExpect(status().isCreated())
                .andReturn();

        String id = extractId(result);

        mvc.perform(put("/api/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
        {"name": "Bob Updated", "birthDate": "1985-05-20"}
        """))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bob Updated"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        MvcResult result = mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"name": "Charlie", "birthDate": "2000-03-15"}
                        """))
                .andExpect(status().isCreated())
                .andReturn();

        String id = extractId(result);

        mvc.perform(delete("/api/users/" + id))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/users/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentUser() throws Exception {
        mvc.perform(put("/api/users/non-existent-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"name": "Ghost", "birthDate": "1990-01-01"}
                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentUser() throws Exception {
        mvc.perform(delete("/api/users/non-existent-id"))
                .andExpect(status().isNotFound());
    }

    private String extractId(MvcResult result) {
        String location = result.getResponse().getHeader("Location");
        return location.substring(location.lastIndexOf("/") + 1);
    }
}
