package com.hexagonal.sample.slice.rest;

import com.hexagonal.sample.adapters.in.rest.command.RestCommandController;
import com.hexagonal.sample.core.application.command.UpdateUserCommand;
import com.hexagonal.sample.core.domain.UserNotFoundException;
import com.hexagonal.sample.ports.in.CommandUseCase;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestCommandController.class)
class RestCommandControllerTest {

    @Autowired MockMvc mvc;
    @MockitoBean CommandUseCase commandUseCase;

    @Test
    void shouldCreateUser() throws Exception {
        given(commandUseCase.createUser(any())).willReturn("1");

        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"name": "Alice", "birthDate": "1990-01-01"}
                        """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users/1"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        mvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"name": "Alice Updated", "birthDate": "1990-01-01"}
                    """))
                .andExpect(status().isNoContent());

        then(commandUseCase).should().updateUser(
                new UpdateUserCommand("1", "Alice Updated", LocalDate.of(1990, 1, 1))
        );
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        then(commandUseCase).should().deleteUser("1");
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentUser() throws Exception {
        willThrow(new UserNotFoundException("99"))
                .given(commandUseCase).updateUser(
                        new UpdateUserCommand("99", "Ghost", LocalDate.of(1990, 1, 1))
                );

        mvc.perform(put("/api/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"name": "Ghost", "birthDate": "1990-01-01"}
                        """))
                .andExpect(status().isNotFound());
    }
}
