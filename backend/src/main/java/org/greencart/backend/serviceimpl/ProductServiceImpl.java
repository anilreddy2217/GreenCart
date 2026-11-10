
package org.greencart.backend.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.greencart.backend.dto.ProductRequest;
import org.greencart.backend.dto.ProductResponse;
import org.greencart.backend.entity.Farmer;
import org.greencart.backend.entity.Product;
import org.greencart.backend.exception.UserNotFoundException;
import org.greencart.backend.repository.FarmerRepository;
import org.greencart.backend.repository.ProductRepository;
import org.greencart.backend.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FarmerRepository farmerRepository;

    @Override
    public ProductResponse addProduct(String email, ProductRequest request) {

        Farmer farmer = farmerRepository.findByUserEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Farmer profile not found"));

        Product product = Product.builder()
                .farmer(farmer)
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .category(request.category())
                .imageUrl(request.imageUrl())
                .build();

        return toResponse(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getMyProducts(String email) {

        return productRepository.findByFarmerUserEmail(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(
            Long productId,
            String email,
            ProductRequest request) {

        Product product = productRepository
                .findByIdAndFarmerUserEmail(productId, email)
                .orElseThrow(() ->
                        new UserNotFoundException("Product not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setCategory(request.category());
        product.setImageUrl(request.imageUrl());

        return toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long productId, String email) {

        Product product = productRepository
                .findByIdAndFarmerUserEmail(productId, email)
                .orElseThrow(() ->
                        new UserNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    private ProductResponse toResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getFarmer().getId(),
                product.getFarmer().getFarmName(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getImageUrl(),
                product.isAvailable(),
                product.getCreatedAt()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllAvailableProducts() {

        return productRepository.findByAvailableTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) {

        Product product = productRepository
                .findByIdAndAvailableTrue(productId)
                .orElseThrow(() ->
                        new UserNotFoundException("Product not found"));

        return toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> searchProducts(
            String name, String category) {

        if (name != null && !name.isBlank()) {
            return productRepository
                    .findByAvailableTrueAndNameContainingIgnoreCase(name)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        if (category != null && !category.isBlank()) {
            return productRepository
                    .findByAvailableTrueAndCategoryIgnoreCase(category)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        return getAllAvailableProducts();
    }

}
