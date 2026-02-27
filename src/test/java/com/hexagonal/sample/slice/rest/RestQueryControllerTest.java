package com.hexagonal.sample.slice.rest;

import com.hexagonal.sample.adapters.in.rest.query.RestQueryController;
import com.hexagonal.sample.core.application.query.UserView;
import com.hexagonal.sample.core.domain.UserNotFoundException;
import com.hexagonal.sample.ports.in.QueryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestQueryController.class)
class RestQueryControllerTest {

    @Autowired MockMvc mvc;
    @MockitoBean QueryUseCase queryUseCase;

    private final UserView alice = new UserView("1", "Alice", LocalDate.of(1990, 1, 1));
    private final UserView bob   = new UserView("2", "Bob",   LocalDate.of(1985, 5, 20));

    @Test
    void shouldReturnUserById() throws Exception {
        given(queryUseCase.findById("1")).willReturn(alice);

        mvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        given(queryUseCase.findById("99")).willThrow(new UserNotFoundException("99"));

        mvc.perform(get("/api/users/99")).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        given(queryUseCase.findAll()).willReturn(List.of(alice, bob));

        mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnUsersByPrefix() throws Exception {
        given(queryUseCase.findByNameStartingWith("Al")).willReturn(List.of(alice));

        mvc.perform(get("/api/users").param("namePrefix", "Al"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

}
