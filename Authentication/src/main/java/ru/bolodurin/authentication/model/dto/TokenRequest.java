package ru.bolodurin.authentication.model.dto;

import org.hibernate.validator.constraints.UUID;

public record TokenRequest(@UUID String token) {
}
