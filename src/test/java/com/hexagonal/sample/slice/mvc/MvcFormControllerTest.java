package com.hexagonal.sample.slice.mvc;

import com.hexagonal.sample.adapters.in.common.dto.Mapper;
import com.hexagonal.sample.adapters.in.mvc.query.MvcFormController;
import com.hexagonal.sample.core.application.query.UserView;
import com.hexagonal.sample.ports.in.QueryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;

@WebMvcTest(MvcFormController.class)
class MvcFormControllerTest {

    @Autowired MockMvc mvc;
    @MockitoBean
    QueryUseCase queryUseCase;

    private final UserView alice = new UserView("1", "Alice", LocalDate.of(1990, 1, 1));

    @Test
    void shouldShowSearchForm() throws Exception {
        mvc.perform(get("/mvc/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("form/main-page-search"));
    }

    @Test
    void shouldShowCreateForm() throws Exception {
        mvc.perform(get("/mvc/users/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("form/create-form"))
                .andExpect(model().attributeExists("request"));
    }

    @Test
    void shouldShowEditForm() throws Exception {
        given(queryUseCase.findById("1")).willReturn(alice);

        mvc.perform(get("/mvc/users/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("form/edit-form"))
                .andExpect(model().attribute("response", Mapper.toResponse(alice)));
    }

    @Test
    void shouldShowDeleteConfirmForm() throws Exception {
        given(queryUseCase.findById("1")).willReturn(alice);

        mvc.perform(get("/mvc/users/1/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("form/delete-confirm-form"))
                .andExpect(model().attribute("response", Mapper.toResponse(alice)));
    }
}
