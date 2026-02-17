package com.hexagonal.sample.adapters.in.rest.command;

import com.hexagonal.sample.adapters.in.common.dto.Mapper;
import com.hexagonal.sample.adapters.in.common.dto.Request;
import com.hexagonal.sample.ports.in.CommandUseCase;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public final class CommandController {

    private final CommandUseCase service;

    public CommandController(CommandUseCase service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody Request request, HttpServletResponse response) {
        String id = service.createUser(Mapper.toDomain(request));
        response.setHeader(HttpHeaders.LOCATION, "/users/" + id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeProfile(@PathVariable String id, @RequestBody Request request) {
        service.updateUser(id, Mapper.toDomain(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        service.deleteUser(id);
    }

}
