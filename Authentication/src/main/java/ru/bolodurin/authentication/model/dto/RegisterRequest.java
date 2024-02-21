package ru.bolodurin.authentication.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotEmpty(message = "Field username must be not empty")
        String username,

        @NotEmpty(message = "Field email must be not empty")
        @Email(message = "Please enter a valid email address")
        String email,

        @NotEmpty(message = "Field password must be not empty")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password

) {
}
