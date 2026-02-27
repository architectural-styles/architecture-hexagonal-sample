package com.hexagonal.sample.slice.mvc;

import com.hexagonal.sample.adapters.in.common.dto.Mapper;
import com.hexagonal.sample.adapters.in.mvc.query.MvcQueryController;
import com.hexagonal.sample.core.application.query.UserView;
import com.hexagonal.sample.core.domain.UserNotFoundException;
import com.hexagonal.sample.ports.in.QueryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;

@WebMvcTest(MvcQueryController.class)
class MvcQueryControllerTest {

    @Autowired MockMvc mvc;
    @MockitoBean QueryUseCase queryUseCase;

    private final UserView alice = new UserView("1", "Alice", LocalDate.of(1990, 1, 1));

    @Test
    void shouldFindById() throws Exception {
        given(queryUseCase.findById("1")).willReturn(alice);

        mvc.perform(get("/mvc/users/search/id").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("result/user-details"))
                .andExpect(model().attribute("response", Mapper.toResponse(alice)));
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        given(queryUseCase.findById("99")).willThrow(new UserNotFoundException("99"));

        mvc.perform(get("/mvc/users/search/id").param("id", "99"))
                .andExpect(view().name("form/main-page-search"))
                .andExpect(model().attribute("error", "User not found: 99"));
    }

    @Test
    void shouldFindByName() throws Exception {
        given(queryUseCase.findByNameStartingWith("Al")).willReturn(List.of(alice));

        mvc.perform(get("/mvc/users/search/name").param("name", "Al"))
                .andExpect(status().isOk())
                .andExpect(view().name("result/list"))
                .andExpect(model().attribute("searchTerm", "Al"))
                .andExpect(model().attribute("userViews", List.of(Mapper.toResponse(alice))));
    }

}
