package com.hexagonal.sample.slice.mvc;

import com.hexagonal.sample.adapters.in.mvc.command.MvcCommandController;
import com.hexagonal.sample.core.application.command.UpdateUserCommand;
import com.hexagonal.sample.ports.in.CommandUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MvcCommandController.class)
class MvcCommandControllerTest {

    @Autowired MockMvc mvc;
    @MockitoBean
    CommandUseCase commandUseCase;

    @Test
    void shouldCreateUser() throws Exception {
        given(commandUseCase.createUser(any())).willReturn("4");

        mvc.perform(post("/mvc/users")
                        .param("name", "Alice")
                        .param("birthDate", "1990-01-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mvc/users/search/id?id=4"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        mvc.perform(post("/mvc/users/1")
                        .param("name", "Alice Updated")
                        .param("birthDate", "1990-01-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mvc/users/search/id?id=1"));

        then(commandUseCase).should().updateUser(
                new UpdateUserCommand("1", "Alice Updated", LocalDate.of(1990, 1, 1))
        );
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mvc.perform(post("/mvc/users/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mvc/users"));

        then(commandUseCase).should().deleteUser("1");
    }

}
