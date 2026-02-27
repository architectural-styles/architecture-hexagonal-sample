package com.hexagonal.sample.integration.mvc;

import com.hexagonal.sample.adapters.in.common.dto.Mapper;
import com.hexagonal.sample.core.application.query.UserView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.util.List;

import com.hexagonal.sample.TestData;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("jdbc")
@AutoConfigureMockMvc
@Sql({"/test-schema.sql", "/test-data.sql"})
class MvcQueryIntegrationTest {

    @Autowired MockMvc mvc;

    @Test
    void shouldFindById() throws Exception {
        mvc.perform(get("/mvc/users/search/id").param("id", TestData.ALICE_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("result/user-details"))
                .andExpect(model().attribute("response", Mapper.toResponse(
                        new UserView(TestData.ALICE_ID, "Alice", LocalDate.of(1990, 1, 1))
                )))
                .andExpect(content().string(containsString("Alice")))
                .andExpect(content().string(containsString("1990-01-01")))
                .andExpect(content().string(containsString("/mvc/users/" + TestData.ALICE_ID + "/edit")))
                .andExpect(content().string(containsString("/mvc/users/" + TestData.ALICE_ID + "/delete")));
    }

    @Test
    void shouldShowErrorWhenUserNotFound() throws Exception {
        mvc.perform(get("/mvc/users/search/id").param("id", "non-existent-id"))
                .andExpect(view().name("form/main-page-search"))
                .andExpect(model().attribute("error", "User not found: non-existent-id"))
                .andExpect(content().string(containsString("User not found: non-existent-id")));
    }

    @Test
    void shouldFindByName() throws Exception {
        mvc.perform(get("/mvc/users/search/name").param("name", "A"))
                .andExpect(status().isOk())
                .andExpect(view().name("result/list"))
                .andExpect(model().attribute("searchTerm", "A"))
                .andExpect(model().attribute("userViews", List.of(
                        Mapper.toResponse(new UserView(TestData.ALICE_ID, "Alice", LocalDate.of(1990, 1, 1))),
                        Mapper.toResponse(new UserView(TestData.ANNA_ID,  "Anna",  LocalDate.of(2000, 3, 15)))
                )))
                .andExpect(content().string(containsString("Alice")))
                .andExpect(content().string(containsString("Anna")))
                .andExpect(content().string(containsString("/mvc/users/search/id?id=" + TestData.ALICE_ID)))
                .andExpect(content().string(containsString("/mvc/users/search/id?id=" + TestData.ANNA_ID)))
                .andExpect(content().string(containsString("/mvc/users/" + TestData.ALICE_ID + "/edit")))
                .andExpect(content().string(containsString("/mvc/users/" + TestData.ANNA_ID + "/edit")))
                .andExpect(content().string(containsString("/mvc/users/" + TestData.ALICE_ID + "/delete")))
                .andExpect(content().string(containsString("/mvc/users/" + TestData.ANNA_ID + "/delete")));
    }

    @Test
    void shouldReturnEmptyListWhenNoPrefixMatch() throws Exception {
        mvc.perform(get("/mvc/users/search/name").param("name", "ZZZ"))
                .andExpect(status().isOk())
                .andExpect(view().name("result/list"))
                .andExpect(model().attribute("searchTerm", "ZZZ"))
                .andExpect(model().attribute("userViews", List.of()))
                .andExpect(content().string(containsString("No results found.")));
    }
}
