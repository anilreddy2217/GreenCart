
package org.greencart.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FarmerRegistrationRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @NotBlank
        @Size(min = 8, max = 72)
        String password,

        @NotBlank
        @Size(max = 15)
        String phone,

        @NotBlank
        @Size(max = 150)
        String farmName,

        @NotBlank
        @Size(max = 255)
        String farmAddress,

        @Size(max = 500)
        String description

) {}
