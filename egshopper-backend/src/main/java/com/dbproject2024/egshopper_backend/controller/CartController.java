package com.dbproject2024.egshopper_backend.controller;

import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.dbproject2024.egshopper_backend.model.Cart;
import com.dbproject2024.egshopper_backend.model.CartItem;
import com.dbproject2024.egshopper_backend.service.CartService;

/*
 * This controller handles:
 *  - Adding products to the cart (Ürünleri Sepete Ekleme)
 *  - Viewing cart contents (Sepet içeriğini görüntüleme)
 *  - Updating cart items (Sepeti Güncelleme)
 *  - Removing items from the cart
 *  - Emptying the cart
 */

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /*
     * 1) "Add to Cart" button:
     * POST /api/cart/add
     * This endpoint is called when a user adds a product to their cart.
     *
     * Example JSON body:
     * {
     * "cartId": 1,
     * "productId": 101,
     * "quantity": 2
     * }
     */
    @GetMapping("/api/cart")
    public List<Object> getCartItems() {
        // Return an empty list if the cart is empty
        return Collections.emptyList(); // Replace with actual cart data
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request) {
        try {
            CartItem addedItem = cartService.addProductToCart(request.getCartId(), request.getProductId(),
                    request.getQuantity());
            return ResponseEntity.ok(addedItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 2) "View Cart" button:
     * GET /api/cart/{cartId}
     * This endpoint retrieves all items in a user's cart.
     */
    // @GetMapping("/{cartId}")
    // public ResponseEntity<List<CartItem>> viewCart(@PathVariable Long cartId) {
    // try {
    // List<CartItem> items = cartService.getCartItems(cartId);
    // return ResponseEntity.ok(items);
    // } catch (IllegalArgumentException e) {
    // return ResponseEntity.badRequest().build();
    // }
    // }

    /*
     * 3) "Update Cart Item" button:
     * PUT /api/cart/item/{cartItemId}
     * This endpoint updates the quantity of a specific cart item.
     *
     * Example JSON body:
     * {
     * "quantity": 3
     * }
     */
    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long cartItemId, @RequestBody UpdateCartItemRequest request) {
        try {
            CartItem updatedItem = cartService.updateCartItemQuantity(cartItemId, request.getQuantity());
            if (updatedItem != null) {
                return ResponseEntity.ok(updatedItem);
            } else {
                return ResponseEntity.ok("Cart item removed as quantity is zero or less.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 4) "Remove from Cart" button:
     * DELETE /api/cart/item/{cartItemId}
     * This endpoint removes a specific item from the cart.
     */
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId) {
        try {
            cartService.removeCartItem(cartItemId);
            return ResponseEntity.ok("Cart item removed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 5) "Empty Cart" button:
     * DELETE /api/cart/empty/{cartId}
     * This endpoint removes all items from the user's cart.
     */
    @DeleteMapping("/empty/{cartId}")
    public ResponseEntity<?> emptyCart(@PathVariable Long cartId) {
        try {
            cartService.emptyCart(cartId);
            return ResponseEntity.ok("Cart emptied successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 6) "Checkout" button:
     * POST /api/cart/checkout/{cartId}
     * This endpoint initiates the checkout process for the user's cart.
     * It marks the cart as ordered and prepares it for order creation.
     */
    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<?> checkoutCart(@PathVariable Long cartId) {
        try {
            cartService.checkoutCart(cartId);
            return ResponseEntity.ok("Checkout initiated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * DTO Classes for Request Bodies
     */

    // AddToCartRequest DTO
    public static class AddToCartRequest {
        private Long cartId;
        private Long productId;
        private int quantity;

        // Getters and Setters
        public Long getCartId() {
            return cartId;
        }

        public void setCartId(Long cartId) {
            this.cartId = cartId;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // UpdateCartItemRequest DTO
    public static class UpdateCartItemRequest {
        private int quantity;

        // Getters and Setters
        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}