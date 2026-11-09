package org.greencart.backend.service;

import org.greencart.backend.dto.RegisterRequest;
import org.greencart.backend.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
}