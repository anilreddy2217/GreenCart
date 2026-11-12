
package org.greencart.backend.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.greencart.backend.dto.*;
import org.greencart.backend.entity.*;
import org.greencart.backend.exception.UserNotFoundException;
import org.greencart.backend.repository.*;
import org.greencart.backend.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CartResponse addToCart(String email, AddToCartRequest request) {

        Product product = productRepository
                .findByIdAndAvailableTrue(request.productId())
                .orElseThrow(() ->
                        new UserNotFoundException("Product not found or unavailable"));

        Cart cart = getOrCreateCart(email);

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        int newQuantity = request.quantity();

        if (cartItem != null) {
            newQuantity += cartItem.getQuantity();
        }

        if (newQuantity > product.getQuantity()) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock");
        }

        if (cartItem == null) {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(newQuantity)
                    .build();

            cart.getItems().add(cartItem);
        } else {
            cartItem.setQuantity(newQuantity);
        }

        cartItemRepository.save(cartItem);

        return toResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(String email) {

        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Cart not found"));

        return toResponse(cart);
    }

    @Override
    public CartResponse updateCartItem(
            String email,
            Long productId,
            UpdateCartItemRequest request) {

        Cart cart = getExistingCart(email);

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() ->
                        new UserNotFoundException("Cart item not found"));

        Product product = cartItem.getProduct();

        if (!product.isAvailable() ||
                request.quantity() > product.getQuantity()) {
            throw new IllegalArgumentException(
                    "Requested quantity exceeds available stock or product is unavailable");
        }

        cartItem.setQuantity(request.quantity());

        return toResponse(cart);
    }

    @Override
    public void removeCartItem(String email, Long productId) {

        Cart cart = getExistingCart(email);

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() ->
                        new UserNotFoundException("Cart item not found"));

        cart.getItems().remove(cartItem);
    }

    @Override
    public void clearCart(String email) {

        Cart cart = getExistingCart(email);
        cart.getItems().clear();
    }

    private Cart getOrCreateCart(String email) {

        return cartRepository.findByUserEmail(email)
                .orElseGet(() -> {
                    User user = userRepository.findByEmail(email)
                            .orElseThrow(() ->
                                    new UserNotFoundException("User not found"));

                    return cartRepository.save(
                            Cart.builder()
                                    .user(user)
                                    .build()
                    );
                });
    }

    private Cart getExistingCart(String email) {

        return cartRepository.findByUserEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Cart not found"));
    }

    private CartResponse toResponse(Cart cart) {

        var items = cart.getItems().stream()
                .map(item -> {
                    Product product = item.getProduct();

                    BigDecimal subtotal = product.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));

                    return new CartItemResponse(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            item.getQuantity(),
                            subtotal,
                            product.getImageUrl()
                    );
                })
                .toList();

        BigDecimal totalPrice = items.stream()
                .map(CartItemResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = items.stream()
                .mapToInt(CartItemResponse::quantity)
                .sum();

        return new CartResponse(
                cart.getId(),
                items,
                totalItems,
                totalPrice
        );
    }
}
