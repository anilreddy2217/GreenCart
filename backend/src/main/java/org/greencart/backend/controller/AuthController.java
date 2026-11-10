
package org.greencart.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.greencart.backend.dto.LoginRequest;
import org.greencart.backend.dto.LoginResponse;
import org.greencart.backend.dto.RegisterRequest;
import org.greencart.backend.dto.UserResponse;
import org.greencart.backend.service.AuthService;
import org.greencart.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }
}
