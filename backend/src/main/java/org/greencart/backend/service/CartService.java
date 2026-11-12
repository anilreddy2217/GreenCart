
package org.greencart.backend.service;

import org.greencart.backend.dto.AddToCartRequest;
import org.greencart.backend.dto.CartResponse;
import org.greencart.backend.dto.UpdateCartItemRequest;

public interface CartService {

    CartResponse addToCart(String email, AddToCartRequest request);

    CartResponse getCart(String email);

    CartResponse updateCartItem(
            String email,
            Long productId,
            UpdateCartItemRequest request
    );

    void removeCartItem(String email, Long productId);

    void clearCart(String email);
}
