package com.hexagonal.sample.core.application.command;

import java.time.LocalDate;

public record UpdateUserCommand(String id, String name, LocalDate birthDate) {}
