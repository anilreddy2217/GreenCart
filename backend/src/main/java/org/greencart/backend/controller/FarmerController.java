
package org.greencart.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.greencart.backend.dto.FarmerRegistrationRequest;
import org.greencart.backend.dto.FarmerResponse;
import org.greencart.backend.service.FarmerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FarmerController {

    private final FarmerService farmerService;

    @PostMapping("/auth/register/farmer")
    public ResponseEntity<FarmerResponse> registerFarmer(
            @Valid @RequestBody FarmerRegistrationRequest request) {

        FarmerResponse response = farmerService.registerFarmer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/farmer/me")
    public ResponseEntity<FarmerResponse> getMyProfile(
            Authentication authentication) {

        FarmerResponse response =
                farmerService.getFarmerProfile(authentication.getName());

        return ResponseEntity.ok(response);
    }
}
