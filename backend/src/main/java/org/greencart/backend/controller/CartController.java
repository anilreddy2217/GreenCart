
package org.greencart.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.greencart.backend.dto.AddToCartRequest;
import org.greencart.backend.dto.CartResponse;
import org.greencart.backend.dto.UpdateCartItemRequest;
import org.greencart.backend.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(
            Authentication authentication,
            @Valid @RequestBody AddToCartRequest request) {

        return ResponseEntity.ok(
                cartService.addToCart(authentication.getName(), request)
        );
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            Authentication authentication) {

        return ResponseEntity.ok(
                cartService.getCart(authentication.getName())
        );
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartResponse> updateCartItem(
            Authentication authentication,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request) {

        return ResponseEntity.ok(
                cartService.updateCartItem(
                        authentication.getName(),
                        productId,
                        request
                )
        );
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeCartItem(
            Authentication authentication,
            @PathVariable Long productId) {

        cartService.removeCartItem(authentication.getName(), productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            Authentication authentication) {

        cartService.clearCart(authentication.getName());

        return ResponseEntity.noContent().build();
    }
}
