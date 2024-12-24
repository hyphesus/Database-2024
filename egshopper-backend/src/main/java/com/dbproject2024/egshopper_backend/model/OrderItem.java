package com.dbproject2024.egshopper_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many order items belong to one order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // References the product being ordered
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Quantity of the product ordered
    private int quantity;

    // Price of the product at the time of order
    private Double priceAtOrderTime;

    // Constructors
    public OrderItem() {
    }

    public OrderItem(Order order, Product product, int quantity, Double priceAtOrderTime) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrderTime = priceAtOrderTime;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public Double getPriceAtOrderTime() {
        return priceAtOrderTime;
    }

    public void setPriceAtOrderTime(Double priceAtOrderTime) {
        this.priceAtOrderTime = priceAtOrderTime;
    }
}