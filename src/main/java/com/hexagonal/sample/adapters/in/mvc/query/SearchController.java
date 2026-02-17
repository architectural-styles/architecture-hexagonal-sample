package com.hexagonal.sample.adapters.in.mvc.query;

import com.hexagonal.sample.adapters.in.common.dto.Mapper;
import com.hexagonal.sample.core.domain.User;
import com.hexagonal.sample.ports.in.QueryUseCase;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mvc/users/search")
public class SearchController {

    private final QueryUseCase service;

    public SearchController(QueryUseCase service) {
        this.service = service;
    }

    @GetMapping("/id")
    public String searchById(@RequestParam String id, Model model) {
        model.addAttribute("response", Mapper.toResponse(service.findById(id)));
        return "result/user-details";
    }

    @GetMapping("/name")
    public String searchByName(@RequestParam String name, Model model) {
        List<User> users = service.findByNameStartingWith(name);
        model.addAttribute("searchTerm", name);
        model.addAttribute(
                "userViews", users.stream()
                        .map(Mapper::toResponse)
                        .toList()
        );
        return "result/list";
    }

}
