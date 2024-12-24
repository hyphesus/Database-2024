package com.dbproject2024.egshopper_backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbproject2024.egshopper_backend.model.Discount;
import com.dbproject2024.egshopper_backend.repository.DiscountRepository;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    /*
     * 1) Create a new discount
     */
    public Discount createDiscount(Discount discount) throws IllegalArgumentException {
        // Validate discount data
        validateDiscount(discount);
        return discountRepository.save(discount);
    }

    /*
     * 2) Retrieve a discount by code
     */
    public Optional<Discount> getDiscountByCode(String code) {
        return discountRepository.findByCode(code);
    }

    /*
     * 3) Validate a discount code during checkout
     * Returns the discount if valid, else throws an exception
     */
    public Discount validateAndApplyDiscount(String code, Double orderAmount, Long userId, Integer userUsageCount)
            throws IllegalArgumentException {

        Discount discount = discountRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Invalid discount code."));

        // Check if current date is within the discount validity period
        LocalDateTime now = LocalDateTime.now();
        if (discount.getStartDate() != null && now.isBefore(discount.getStartDate())) {
            throw new IllegalArgumentException("Discount code is not yet valid.");
        }
        if (discount.getEndDate() != null && now.isAfter(discount.getEndDate())) {
            throw new IllegalArgumentException("Discount code has expired.");
        }

        // Check minimum order amount
        if (orderAmount < discount.getMinOrderAmount()) {
            throw new IllegalArgumentException("Order amount does not meet the minimum required for this discount.");
        }

        // Check usage limits
        if (discount.getTotalUsageLimit() != null && discount.getCurrentUsageCount() >= discount.getTotalUsageLimit()) {
            throw new IllegalArgumentException("Discount code has reached its total usage limit.");
        }

        if (discount.getUsageLimitPerUser() != null && userUsageCount >= discount.getUsageLimitPerUser()) {
            throw new IllegalArgumentException("You have reached the usage limit for this discount code.");
        }

        // All validations passed, increment usage counts
        discount.incrementUsageCount();
        discountRepository.save(discount);

        return discount;
    }

    /*
     * 4) Validate discount data before creation
     */
    private void validateDiscount(Discount discount) throws IllegalArgumentException {
        if (discount.getCode() == null || discount.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Discount code cannot be empty.");
        }

        if (!discount.getType().equalsIgnoreCase("PERCENTAGE") &&
                !discount.getType().equalsIgnoreCase("FIXED_AMOUNT")) {
            throw new IllegalArgumentException("Invalid discount type. Must be PERCENTAGE or FIXED_AMOUNT.");
        }

        if (discount.getValue() == null || discount.getValue() <= 0) {
            throw new IllegalArgumentException("Discount value must be positive.");
        }

        if (discount.getType().equalsIgnoreCase("PERCENTAGE") && discount.getValue() > 100) {
            throw new IllegalArgumentException("Percentage discount cannot exceed 100.");
        }

        if (discount.getStartDate() != null && discount.getEndDate() != null &&
                discount.getStartDate().isAfter(discount.getEndDate())) {
            throw new IllegalArgumentException("Discount start date cannot be after end date.");
        }

        // Additional validations can be added as needed
    }
}