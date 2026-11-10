
package org.greencart.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        Long farmerId,
        String farmerName,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        String category,
        String imageUrl,
        boolean available,
        LocalDateTime createdAt
) {}
