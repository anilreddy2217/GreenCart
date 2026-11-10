
package org.greencart.backend.service;

import org.greencart.backend.dto.LoginRequest;
import org.greencart.backend.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
