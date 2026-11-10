
package org.greencart.backend.repository;

import org.greencart.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByFarmerUserEmail(String email);

    Optional<Product> findByIdAndFarmerUserEmail(Long id, String email);

    List<Product> findByAvailableTrue();

    Optional<Product> findByIdAndAvailableTrue(Long id);

    List<Product> findByAvailableTrueAndNameContainingIgnoreCase(String name);

    List<Product> findByAvailableTrueAndCategoryIgnoreCase(String category);

}
