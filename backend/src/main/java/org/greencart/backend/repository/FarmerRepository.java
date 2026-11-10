
package org.greencart.backend.repository;

import org.greencart.backend.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    Optional<Farmer> findByUserEmail(String email);

    boolean existsByUserEmail(String email);
}
