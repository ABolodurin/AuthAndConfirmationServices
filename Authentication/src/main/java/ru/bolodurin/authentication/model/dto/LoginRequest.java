package ru.bolodurin.authentication.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "Field username must be not empty")
        String username,
        @NotEmpty(message = "Field password must be not empty")
        String password) {
}
