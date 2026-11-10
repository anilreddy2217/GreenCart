package org.greencart.backend.service;

import org.greencart.backend.dto.RegisterRequest;
import org.greencart.backend.dto.UpdateProfileRequest;
import org.greencart.backend.dto.UserResponse;
import org.greencart.backend.dto.ChangePasswordRequest;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
    UserResponse updateProfile(
            String email,
            UpdateProfileRequest request
    );
    UserResponse getProfile(String email);
    void changePassword(
            String email,
            ChangePasswordRequest request
    );
}