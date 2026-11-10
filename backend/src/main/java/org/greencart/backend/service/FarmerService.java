
package org.greencart.backend.service;

import org.greencart.backend.dto.FarmerRequest;
import org.greencart.backend.dto.FarmerResponse;
import org.greencart.backend.dto.FarmerRegistrationRequest;

public interface FarmerService {

    FarmerResponse createFarmerProfile(
            String email,
            FarmerRequest request
    );

    FarmerResponse getFarmerProfile(String email);
    FarmerResponse registerFarmer(FarmerRegistrationRequest request);
}
