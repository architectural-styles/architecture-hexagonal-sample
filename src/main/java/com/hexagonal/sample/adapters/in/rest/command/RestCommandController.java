package com.hexagonal.sample.adapters.in.rest.command;

import com.hexagonal.sample.adapters.in.common.dto.CreateUserRequest;
import com.hexagonal.sample.adapters.in.common.dto.Mapper;
import com.hexagonal.sample.adapters.in.common.dto.UpdateUserRequest;
import com.hexagonal.sample.ports.in.CommandUseCase;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public final class RestCommandController {

    private final CommandUseCase service;

    public RestCommandController(CommandUseCase service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @RequestBody CreateUserRequest request,
            HttpServletResponse response
    ) {
        String id = service.createUser(Mapper.toCreateCommand(request));
        response.setHeader(HttpHeaders.LOCATION, "/api/users/" + id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeProfile(
            @PathVariable String id,
            @RequestBody UpdateUserRequest body
    ) {
        service.updateUser(Mapper.toUpdateCommand(id, body));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(id);
    }
}
