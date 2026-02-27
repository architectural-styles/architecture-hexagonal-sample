package com.hexagonal.sample.integration.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("jdbc")
@AutoConfigureMockMvc
@Sql("/test-schema.sql")
class MvcCommandIntegrationTest {

    @Autowired MockMvc mvc;

    @Test
    void shouldCreateAndRedirect() throws Exception {
        MvcResult result = mvc.perform(post("/mvc/users")
                        .param("name", "Alice")
                        .param("birthDate", "1990-01-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/mvc/users/search/id?id=*"))
                .andExpect(flash().attribute("message", "User information saved successfully!"))
                .andReturn();

        String location = result.getResponse().getRedirectedUrl();
        mvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Alice")));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        MvcResult created = mvc.perform(post("/mvc/users")
                        .param("name", "Bob")
                        .param("birthDate", "1985-05-20"))
                .andReturn();

        String id = extractId(created.getResponse().getRedirectedUrl());

        mvc.perform(post("/mvc/users/" + id)
                        .param("name", "Bob Updated")
                        .param("birthDate", "1985-05-20"))
                .andExpect(redirectedUrl("/mvc/users/search/id?id=" + id))
                .andExpect(flash().attribute("message", "User #" + id + " successfully updated"));

        mvc.perform(get("/mvc/users/search/id").param("id", id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bob Updated")));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        MvcResult created = mvc.perform(post("/mvc/users")
                        .param("name", "Charlie")
                        .param("birthDate", "2000-03-15"))
                .andReturn();

        String id = extractId(created.getResponse().getRedirectedUrl());

        mvc.perform(post("/mvc/users/" + id + "/delete"))
                .andExpect(redirectedUrl("/mvc/users"))
                .andExpect(flash().attribute("message", "User deleted!"));
    }
    @Test
    void shouldShowErrorWhenUpdatingNonExistentUser() throws Exception {
        mvc.perform(post("/mvc/users/99")
                        .param("name", "Ghost")
                        .param("birthDate", "1990-01-01"))
                .andExpect(view().name("form/main-page-search"))
                .andExpect(model().attribute("error", "User not found: 99"))
                .andExpect(content().string(containsString("User not found: 99")));
    }

    private String extractId(String location) {
        return location.substring(location.lastIndexOf("=") + 1);
    }

}
