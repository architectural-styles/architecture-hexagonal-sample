package com.hexagonal.sample.adapters.in.common.dto;

import java.time.LocalDate;

public record UserResponse(String id, String name, LocalDate birthDate) {}
