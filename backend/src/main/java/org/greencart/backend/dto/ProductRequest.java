
package org.greencart.backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank
        @Size(max = 150)
        String name,

        @Size(max = 1000)
        String description,

        @NotNull
        @DecimalMin(value = "0.01")
        @Digits(integer = 8, fraction = 2)
        BigDecimal price,

        @NotNull
        @PositiveOrZero
        Integer quantity,

        @NotBlank
        @Size(max = 50)
        String category,

        @Size(max = 500)
        String imageUrl

) {}
