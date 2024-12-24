package com.dbproject2024.egshopper_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dbproject2024.egshopper_backend.model.Order;
import com.dbproject2024.egshopper_backend.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /*
     * 1) "Place Order" button:
     * POST /api/orders/place
     * This endpoint converts a user's cart into an order.
     *
     * Example JSON body:
     * {
     * "cartId": 1,
     * "shippingAddress": "123 Main St, Anytown, USA",
     * "paymentMethod": "Credit Card",
     * "discountCode": "SUMMER10",
     * "userId": 1,
     * "userUsageCount": 1
     * }
     */
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest request) {
        try {
            Order order = orderService.placeOrder(
                    request.getCartId(),
                    request.getShippingAddress(),
                    request.getPaymentMethod(),
                    request.getDiscountCode(),
                    request.getUserId(),
                    request.getUserUsageCount());
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 2) "View User Orders" button:
     * GET /api/orders/user/{userId}
     * This endpoint retrieves all orders placed by a specific user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    /*
     * 3) "Update Order Status" button (Admin):
     * PUT /api/orders/{orderId}/status
     * This endpoint allows an admin to update the status of an order.
     *
     * Example JSON body:
     * {
     * "status": "COMPLETED"
     * }
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,
            @RequestBody UpdateOrderStatusRequest request) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, request.getStatus());
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 4) "View Order Details" button:
     * GET /api/orders/{orderId}
     * This endpoint retrieves details of a specific order.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }

    /*
     * DTO Classes for Request Bodies
     */

    // PlaceOrderRequest DTO
    public static class PlaceOrderRequest {
        private Long cartId;
        private String shippingAddress;
        private String paymentMethod;
        private String discountCode; // New field for discount code
        private Long userId; // New field for user ID
        private Integer userUsageCount; // New field for user's discount usage count

        // Getters and Setters
        public Long getCartId() {
            return cartId;
        }

        public void setCartId(Long cartId) {
            this.cartId = cartId;
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

        public String getDiscountCode() {
            return discountCode;
        }

        public void setDiscountCode(String discountCode) {
            this.discountCode = discountCode;
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

    // UpdateOrderStatusRequest DTO
    public static class UpdateOrderStatusRequest {
        private String status;

        // Getters and Setters
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}