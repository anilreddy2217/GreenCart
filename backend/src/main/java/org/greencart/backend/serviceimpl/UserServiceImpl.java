package org.greencart.backend.serviceimpl;

import org.greencart.backend.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.greencart.backend.exception.InvalidCredentialsException;
import org.greencart.backend.exception.UserNotFoundException;
import org.greencart.backend.dto.RegisterRequest;
import org.greencart.backend.dto.UpdateProfileRequest;
import org.greencart.backend.dto.UserResponse;
import org.greencart.backend.entity.Role;
import org.greencart.backend.entity.User;
import org.greencart.backend.exception.IncorrectCurrentPasswordException;
import org.greencart.backend.repository.UserRepository;
import org.greencart.backend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.greencart.backend.dto.ChangePasswordRequest;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phone())
                .role(Role.CUSTOMER)
                .build();

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getRole()
        );
    }

    @Override
    public UserResponse updateProfile(
            String email,
            UpdateProfileRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        user.setName(request.name());
        user.setPhone(request.phone());

        User updatedUser = userRepository.save(user);

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getPhone(),
                updatedUser.getRole()
        );
    }

    @Override
    public UserResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    @Override
    public void changePassword(
            String email,
            ChangePasswordRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(
                request.currentPassword(), user.getPassword())) {
            throw new IncorrectCurrentPasswordException(
                    "Current password is incorrect");
        }

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new IllegalArgumentException(
                    "New password and confirmation do not match");
        }

        if (passwordEncoder.matches(
                request.newPassword(), user.getPassword())) {
            throw new IllegalArgumentException(
                    "New password must be different from current password");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);
    }


}
