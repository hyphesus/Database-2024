package com.dbproject2024.egshopper_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dbproject2024.egshopper_backend.model.Discount;
import com.dbproject2024.egshopper_backend.service.DiscountService;

import java.util.Optional;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    /*
     * 1) "Create Discount" (Admin)
     * POST /api/discounts/create
     * Only accessible by admins.
     *
     * Example JSON body:
     * {
     * "code": "SUMMER10",
     * "description": "10% off summer collection",
     * "type": "PERCENTAGE",
     * "value": 10.0,
     * "minOrderAmount": 50.0,
     * "maxDiscountAmount": 30.0,
     * "startDate": "2024-06-01T00:00:00",
     * "endDate": "2024-08-31T23:59:59",
     * "usageLimitPerUser": 2,
     * "totalUsageLimit": 100
     * }
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createDiscount(@RequestBody Discount discount) {
        try {
            Discount createdDiscount = discountService.createDiscount(discount);
            return ResponseEntity.ok(createdDiscount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 2) "Validate Discount" during checkout
     * POST /api/discounts/validate
     * Accessible by authenticated users.
     *
     * Example JSON body:
     * {
     * "code": "SUMMER10",
     * "orderAmount": 100.0,
     * "userId": 1,
     * "userUsageCount": 1
     * }
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validateDiscount(@RequestBody ValidateDiscountRequest request) {
        try {
            Discount discount = discountService.validateAndApplyDiscount(
                    request.getCode(),
                    request.getOrderAmount(),
                    request.getUserId(),
                    request.getUserUsageCount());

            // Calculate discount amount based on type
            double discountAmount = 0.0;
            if (discount.getType().equalsIgnoreCase("PERCENTAGE")) {
                discountAmount = (discount.getValue() / 100) * request.getOrderAmount();
                if (discount.getMaxDiscountAmount() != null && discountAmount > discount.getMaxDiscountAmount()) {
                    discountAmount = discount.getMaxDiscountAmount();
                }
            } else if (discount.getType().equalsIgnoreCase("FIXED_AMOUNT")) {
                discountAmount = discount.getValue();
            }

            // Ensure discount does not exceed order amount
            if (discountAmount > request.getOrderAmount()) {
                discountAmount = request.getOrderAmount();
            }

            return ResponseEntity.ok(new DiscountResponse(discount.getCode(), discountAmount));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 3) "Get Discount Details" (Optional)
     * GET /api/discounts/{code}
     * Accessible by authenticated users.
     */
    @GetMapping("/{code}")
    public ResponseEntity<?> getDiscountDetails(@PathVariable String code) {
        Optional<Discount> discountOpt = discountService.getDiscountByCode(code);
        if (discountOpt.isPresent()) {
            return ResponseEntity.ok(discountOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    /*
     * DTO Classes for Request and Response Bodies
     */

    // ValidateDiscountRequest DTO
    public static class ValidateDiscountRequest {
        private String code;
        private Double orderAmount;
        private Long userId;
        private Integer userUsageCount;

        // Getters and Setters
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(Double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Integer getUserUsageCount() {
            return userUsageCount;
        }

        public void setUserUsageCount(Integer userUsageCount) {
            this.userUsageCount = userUsageCount;
        }
    }

    // DiscountResponse DTO
    public static class DiscountResponse {
        private String code;
        private Double discountAmount;

        public DiscountResponse(String code, Double discountAmount) {
            this.code = code;
            this.discountAmount = discountAmount;
        }

        // Getters and Setters
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Double getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Double discountAmount) {
            this.discountAmount = discountAmount;
        }
    }
}