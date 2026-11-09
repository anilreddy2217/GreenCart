package org.greencart.backend.dto;

import org.greencart.backend.entity.Role;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone,
        Role role
) {}