package com.dbproject2024.egshopper_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many cart items can belong to one cart
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // This references the product the user is buying
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // The chosen quantity
    private int quantity;

    // Price might be stored to lock in the price at the time item was added
    private Double priceAtAddTime;

    public CartItem() {
    }

    public CartItem(Cart cart, Product product, int quantity, Double priceAtAddTime) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.priceAtAddTime = priceAtAddTime;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPriceAtAddTime() {
        return priceAtAddTime;
    }

    public void setPriceAtAddTime(Double priceAtAddTime) {
        this.priceAtAddTime = priceAtAddTime;
    }
}