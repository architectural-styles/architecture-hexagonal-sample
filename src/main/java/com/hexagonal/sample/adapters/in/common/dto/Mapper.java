package com.hexagonal.sample.adapters.in.common.dto;

import com.hexagonal.sample.core.application.command.CreateUserCommand;
import com.hexagonal.sample.core.application.command.UpdateUserCommand;
import com.hexagonal.sample.core.application.query.UserView;

import java.time.LocalDate;

public final class Mapper {

    public static CreateUserCommand toCreateCommand(CreateUserRequest request) {
        return new CreateUserCommand(
                request.name(),
                LocalDate.parse(request.birthDate())
        );
    }

    public static UpdateUserCommand toUpdateCommand(String id, UpdateUserRequest body) {
        return new UpdateUserCommand(
                id,
                body.name(),
                LocalDate.parse(body.birthDate())
        );
    }

    public static UserResponse toResponse(UserView view) {
        return new UserResponse(view.id(), view.name(), view.birthDate());
    }

}
