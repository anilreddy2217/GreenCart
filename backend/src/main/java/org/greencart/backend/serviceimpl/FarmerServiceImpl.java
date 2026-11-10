package org.greencart.backend.serviceimpl;

import org.greencart.backend.dto.FarmerRegistrationRequest;
import org.greencart.backend.exception.EmailAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.greencart.backend.dto.FarmerRequest;
import org.greencart.backend.dto.FarmerResponse;
import org.greencart.backend.entity.Farmer;
import org.greencart.backend.entity.Role;
import org.greencart.backend.entity.User;
import org.greencart.backend.exception.UserNotFoundException;
import org.greencart.backend.repository.FarmerRepository;
import org.greencart.backend.repository.UserRepository;
import org.greencart.backend.service.FarmerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {

    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public FarmerResponse createFarmerProfile(
            String email,
            FarmerRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        if (user.getRole() != Role.FARMER) {
            throw new IllegalArgumentException(
                    "Only farmers can create farmer profiles");
        }

        if (farmerRepository.existsByUserEmail(email)) {
            throw new IllegalArgumentException(
                    "Farmer profile already exists");
        }

        Farmer farmer = Farmer.builder()
                .user(user)
                .farmName(request.farmName())
                .farmAddress(request.farmAddress())
                .description(request.description())
                .build();

        return toResponse(farmerRepository.save(farmer));
    }

    @Override
    public FarmerResponse getFarmerProfile(String email) {

        Farmer farmer = farmerRepository.findByUserEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Farmer profile not found"));

        return toResponse(farmer);
    }

    private FarmerResponse toResponse(Farmer farmer) {
        return new FarmerResponse(
                farmer.getId(),
                farmer.getUser().getId(),
                farmer.getFarmName(),
                farmer.getFarmAddress(),
                farmer.getDescription(),
                farmer.getCreatedAt()
        );
    }
    @Override
    @Transactional
    public FarmerResponse registerFarmer(FarmerRegistrationRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phone())
                .role(Role.FARMER)
                .build();

        User savedUser = userRepository.save(user);

        Farmer farmer = Farmer.builder()
                .user(savedUser)
                .farmName(request.farmName())
                .farmAddress(request.farmAddress())
                .description(request.description())
                .build();

        Farmer savedFarmer = farmerRepository.save(farmer);

        return toResponse(savedFarmer);
    }
}
