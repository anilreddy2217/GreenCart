
package org.greencart.backend.dto;

import java.time.LocalDateTime;

public record FarmerResponse(
        Long id,
        Long userId,
        String farmName,
        String farmAddress,
        String description,
        LocalDateTime createdAt
) {}
