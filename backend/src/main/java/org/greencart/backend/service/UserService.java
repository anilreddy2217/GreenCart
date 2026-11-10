package org.greencart.backend.service;

import org.greencart.backend.dto.RegisterRequest;
import org.greencart.backend.dto.UpdateProfileRequest;
import org.greencart.backend.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
    UserResponse updateProfile(
            String email,
            UpdateProfileRequest request
    );
    UserResponse getProfile(String email);
}