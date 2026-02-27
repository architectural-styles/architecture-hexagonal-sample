package com.hexagonal.sample.adapters.in.rest.query;

import com.hexagonal.sample.adapters.in.common.dto.Mapper;
import com.hexagonal.sample.adapters.in.common.dto.UserResponse;
import com.hexagonal.sample.ports.in.QueryUseCase;
import com.hexagonal.sample.core.application.query.UserView;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public final class RestQueryController {

    private final QueryUseCase service;

    public RestQueryController(QueryUseCase service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getDetails(@PathVariable String id) {
        return Mapper.toResponse(service.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> list(@RequestParam(required = false) String namePrefix) {

        List<UserView> views = (namePrefix == null)
                ? service.findAll()
                : service.findByNameStartingWith(namePrefix);

        return views.stream().map(Mapper::toResponse).toList();
    }

}
