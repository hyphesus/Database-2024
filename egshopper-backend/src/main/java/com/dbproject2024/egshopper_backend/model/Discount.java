package com.dbproject2024.egshopper_backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique discount code
    @Column(nullable = false, unique = true)
    private String code;

    // Description of the discount
    private String description;

    // Discount type: e.g., PERCENTAGE, FIXED_AMOUNT
    @Column(nullable = false)
    private String type;

    // Discount value: percentage (e.g., 10 for 10%) or fixed amount (e.g., 20 for
    // $20 off)
    @Column(nullable = false)
    private Double value;

    // Minimum order amount required to apply the discount
    private Double minOrderAmount = 0.0;

    // Maximum discount amount (useful for percentage discounts)
    private Double maxDiscountAmount = null;

    // Start and end dates for the discount validity
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Usage limit per user
    private Integer usageLimitPerUser = 1;

    // Total usage limit across all users
    private Integer totalUsageLimit = null;

    // Current usage count
    private Integer currentUsageCount = 0;

    // Constructors
    public Discount() {
    }

    public Discount(String code, String description, String type, Double value, Double minOrderAmount,
            Double maxDiscountAmount, LocalDateTime startDate, LocalDateTime endDate,
            Integer usageLimitPerUser, Integer totalUsageLimit) {
        this.code = code;
        this.description = description;
        this.type = type;
        this.value = value;
        this.minOrderAmount = minOrderAmount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usageLimitPerUser = usageLimitPerUser;
        this.totalUsageLimit = totalUsageLimit;
        this.currentUsageCount = 0;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(Double minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(Double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getUsageLimitPerUser() {
        return usageLimitPerUser;
    }

    public void setUsageLimitPerUser(Integer usageLimitPerUser) {
        this.usageLimitPerUser = usageLimitPerUser;
    }

    public Integer getTotalUsageLimit() {
        return totalUsageLimit;
    }

    public void setTotalUsageLimit(Integer totalUsageLimit) {
        this.totalUsageLimit = totalUsageLimit;
    }

    public Integer getCurrentUsageCount() {
        return currentUsageCount;
    }

    public void setCurrentUsageCount(Integer currentUsageCount) {
        this.currentUsageCount = currentUsageCount;
    }

    // Utility method to increment usage count
    public void incrementUsageCount() {
        if (this.currentUsageCount != null) {
            this.currentUsageCount += 1;
        }
    }
}