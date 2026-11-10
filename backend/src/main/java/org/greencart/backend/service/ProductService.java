
package org.greencart.backend.service;

import org.greencart.backend.dto.ProductRequest;
import org.greencart.backend.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(String email, ProductRequest request);

    List<ProductResponse> getMyProducts(String email);

    ProductResponse updateProduct(
            Long productId,
            String email,
            ProductRequest request
    );

    void deleteProduct(Long productId, String email);

    List<ProductResponse> getAllAvailableProducts();

    ProductResponse getProductById(Long productId);

    List<ProductResponse> searchProducts(String name, String category);

}
