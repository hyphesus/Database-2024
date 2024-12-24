package com.dbproject2024.egshopper_backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "orders") // "order" is a reserved keyword in SQL, so using "orders"
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who placed the order
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // List of items in the order
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // Order status (e.g., PENDING, COMPLETED, CANCELLED)
    @Column(nullable = false)
    private String status = "PENDING";

    // Total amount for the order
    private Double totalAmount = 0.0;

    // Date and time when the order was placed
    private LocalDateTime orderedAt = LocalDateTime.now();

    // Shipping address details (simplified as a string; can be expanded)
    private String shippingAddress;

    // Payment method details (simplified as a string; can be expanded)
    private String paymentMethod;

    // Constructors
    public Order() {
    }

    public Order(User user, String shippingAddress, String paymentMethod) {
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.status = "PENDING";
        this.orderedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Utility methods to manage orderItems
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
        // Update totalAmount
        this.totalAmount += item.getPriceAtOrderTime() * item.getQuantity();
    }

    public void removeOrderItem(OrderItem item) {
        orderItems.remove(item);
        item.setOrder(null);
        // Update totalAmount
        this.totalAmount -= item.getPriceAtOrderTime() * item.getQuantity();
    }
}