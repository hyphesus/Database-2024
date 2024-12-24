package com.dbproject2024.egshopper_backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // For linking this cart to a user (we'll assume a User entity exists).
    // If you haven't created User.java yet, you can comment this out or create a
    // placeholder.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // One cart has many cart items
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    // When was this cart created? Helps with logs, etc.
    private LocalDateTime createdAt = LocalDateTime.now();

    // Optional: A cart might have a status (e.g., ACTIVE, ORDERED, EXPIRED)
    private String status = "ACTIVE";

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Helper methods to manage cartItems
    public void addItem(CartItem item) {
        cartItems.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item) {
        cartItems.remove(item);
        item.setCart(null);
    }
}