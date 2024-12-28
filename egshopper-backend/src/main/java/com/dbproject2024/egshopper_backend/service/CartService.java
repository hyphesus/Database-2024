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

    /*
     * 1) "Ürünü sepete ekleme"
     * - The user selects a product, quantity, etc.
     * - We find or create a Cart (maybe the user's "ACTIVE" cart).
     * - We add a CartItem linked to that Cart and the chosen Product.
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
        // so we can update the quantity instead of creating a new one.
        CartItem existingItem = findCartItemByProduct(cart, productId);

        if (existingItem != null) {
            // Increase the quantity
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return cartItemRepository.save(existingItem);
        } else {
            // Create a new cart item
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            // Possibly store the price at add time
            newItem.setPriceAtAddTime(product.getPrice());
            return cartItemRepository.save(newItem);
        }
    }

    /*
     * 2) Helper method to find a CartItem in a given Cart by product ID.
     * We look in the cart's item list for a matching product.
     */
    private CartItem findCartItemByProduct(Cart cart, Long productId) {
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    /*
     * 3) "Sepeti görüntüleme" and "Sepet içeriğini görüntüleme"
     * - Return all items in a cart by cartId.
     */
    public List<CartItem> getCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));
        return cart.getCartItems();
    }

    /*
     * 4) "Sepeti Güncelleme"
     * - Update quantity of a particular cart item.
     * - If quantity is 0, we might remove the item from the cart.
     */
    public CartItem updateCartItemQuantity(Long cartItemId, int newQuantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found with ID: " + cartItemId));

        if (newQuantity <= 0) {
            // Remove item if the new quantity is 0 or negative
            cartItemRepository.deleteById(cartItemId);
            return null;
        } else {
            item.setQuantity(newQuantity);
            return cartItemRepository.save(item);
        }
    }

    /*
     * 5) "Ürünü sepetten silme"
     * - Directly remove a specific cart item.
     */
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    /*
     * 6) "Sepeti boşaltma"
     * - Remove all items from a given cart.
     */
    public void emptyCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));

        // Remove all items
        List<CartItem> items = cart.getCartItems();
        for (CartItem item : items) {
            cartItemRepository.delete(item);
        }
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    /*
     * 7) (Optional) "getCartById" if you want to retrieve the cart object itself.
     */
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    /*
     * 8) "Satın Alma İşlemleri"
     * - In many designs, you'd create an Order from the Cart items,
     * then mark the cart as ORDERED. We'll handle actual order creation in
     * OrderService.
     * - However, we can do a preliminary step here if you like:
     */
    public void checkoutCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));

        // Mark the cart as ORDERED
        cart.setStatus("ORDERED");
        cartRepository.save(cart);
        // Next step: create an "Order" record from these items (we'll do in
        // OrderService).
    }
}