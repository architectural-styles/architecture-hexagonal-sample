package com.hexagonal.sample.core.application.command;

import java.time.LocalDate;

public record CreateUserCommand(String name, LocalDate birthDate) {}
