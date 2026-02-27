package com.hexagonal.sample.core.application.query;

import java.time.LocalDate;

public record UserView(String id, String name, LocalDate birthDate) {}
