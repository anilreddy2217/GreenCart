
package org.greencart.backend.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.greencart.backend.dto.LoginRequest;
import org.greencart.backend.dto.LoginResponse;
import org.greencart.backend.dto.UserResponse;
import org.greencart.backend.entity.User;
import org.greencart.backend.repository.UserRepository;
import org.greencart.backend.security.JwtService;
import org.greencart.backend.service.AuthService;
import org.greencart.backend.exception.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"));

        if (!passwordEncoder.matches(
                request.password(), user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getEmail(), user.getRole().name());

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );

        return new LoginResponse(token, userResponse);
    }

}