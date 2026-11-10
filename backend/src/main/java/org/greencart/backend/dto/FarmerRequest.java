
package org.greencart.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FarmerRequest(

        @NotBlank
        @Size(max = 150)
        String farmName,

        @NotBlank
        @Size(max = 255)
        String farmAddress,

        @Size(max = 500)
        String description

) {}
