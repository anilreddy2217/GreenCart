
package org.greencart.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.greencart.backend.dto.ProductRequest;
import org.greencart.backend.dto.ProductResponse;
import org.greencart.backend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmer/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {

        ProductResponse response = productService.addProduct(
                authentication.getName(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getMyProducts(
            Authentication authentication) {

        return ResponseEntity.ok(
                productService.getMyProducts(authentication.getName()));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                productService.updateProduct(
                        productId, authentication.getName(), request));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId,
            Authentication authentication) {

        productService.deleteProduct(
                productId, authentication.getName());

        return ResponseEntity.noContent().build();
    }
}
