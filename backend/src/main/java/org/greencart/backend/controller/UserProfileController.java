
package org.greencart.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.greencart.backend.dto.UpdateProfileRequest;
import org.greencart.backend.dto.UserResponse;
import org.greencart.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.greencart.backend.dto.ChangePasswordRequest;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            Authentication authentication) {

        UserResponse user = userService.getProfile(
                authentication.getName()
        );

        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            Authentication authentication) {

        UserResponse updatedUser = userService.updateProfile(
                authentication.getName(),
                request
        );

        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Authentication authentication) {

        userService.changePassword(
                authentication.getName(),
                request
        );

        return ResponseEntity.noContent().build();
    }

}
