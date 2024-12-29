package com.dbproject2024.egshopper_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbproject2024.egshopper_backend.model.Cart;
import com.dbproject2024.egshopper_backend.model.CartItem;
import com.dbproject2024.egshopper_backend.model.Product;
import com.dbproject2024.egshopper_backend.repository.CartItemRepository;
import com.dbproject2024.egshopper_backend.repository.CartRepository;
import com.dbproject2024.egshopper_backend.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * 1) Add a product to the cart.
     * - The user selects a product, quantity, etc.
     * - We find (or create) the Cart by cartId.
     * - We add or update a CartItem linked to that Cart and the chosen Product.
     */
    public CartItem addProductToCart(Long cartId, Long productId, int quantity)
            throws IllegalArgumentException {

        // 1. Fetch the cart
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));

        // 2. Fetch the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        // 3. Check if this cart already has a CartItem for the same product
        CartItem existingItem = findCartItemByProduct(cart, productId);

        if (existingItem != null) {
            // Update (increase) the quantity
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return cartItemRepository.save(existingItem);
        } else {
            // Create a new cart item
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            // Optionally store the current product price
            newItem.setPriceAtAddTime(product.getPrice());
            return cartItemRepository.save(newItem);
        }
    }

    /**
     * 2) Helper method to find a CartItem in a given Cart by product ID.
     */
    private CartItem findCartItemByProduct(Cart cart, Long productId) {
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 3) Return all items in a cart (view cart contents).
     */
    public List<CartItem> getCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));
        return cart.getCartItems();
    }

    /**
     * 4) Update quantity of a particular cart item.
     * If the new quantity <= 0, remove the item.
     */
    public CartItem updateCartItemQuantity(Long cartItemId, int newQuantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found with ID: " + cartItemId));

        if (newQuantity <= 0) {
            cartItemRepository.deleteById(cartItemId);
            return null;
        } else {
            item.setQuantity(newQuantity);
            return cartItemRepository.save(item);
        }
    }

    /**
     * 5) Remove a specific cart item by its ID.
     */
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    /**
     * 6) Empty an entire cart (remove all items).
     */
    public void emptyCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));

        List<CartItem> items = cart.getCartItems();
        for (CartItem item : items) {
            cartItemRepository.delete(item);
        }
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    /**
     * 7) Get the Cart object itself by ID.
     */
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    /**
     * 8) "Checkout" the cart by marking it as ORDERED, etc.
     */
    public void checkoutCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));
        cart.setStatus("ORDERED");
        cartRepository.save(cart);
        // Next: create an Order from this cart, if desired.
    }

    /**
     * 9) (Optional) Get all carts in the DB (useful for admin or debugging).
     */
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}