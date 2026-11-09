package org.greencart.backend.dto;

public record LoginResponse(
        String token,
        UserResponse user
) {}