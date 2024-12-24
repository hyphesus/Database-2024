package com.dbproject2024.egshopper_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbproject2024.egshopper_backend.model.Order;
import com.dbproject2024.egshopper_backend.model.OrderItem;
import com.dbproject2024.egshopper_backend.model.Cart;
import com.dbproject2024.egshopper_backend.model.CartItem;
import com.dbproject2024.egshopper_backend.model.Discount;
import com.dbproject2024.egshopper_backend.repository.OrderRepository;
import com.dbproject2024.egshopper_backend.repository.OrderItemRepository;
import com.dbproject2024.egshopper_backend.repository.CartRepository;
import com.dbproject2024.egshopper_backend.repository.CartItemRepository;
import com.dbproject2024.egshopper_backend.service.DiscountService;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private DiscountService discountService;

    /*
     * 1) "Satın Alma İşlemleri" (#4)
     * - Convert a user's cart into an order, optionally applying a discount code.
     */
    public Order placeOrder(Long cartId, String shippingAddress, String paymentMethod, String discountCode, Long userId,
            Integer userUsageCount)
            throws IllegalArgumentException {

        // 1. Fetch the cart
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));

        // 2. Check if the cart is active
        if (!cart.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new IllegalArgumentException("Cart is not active.");
        }

        // 3. Initialize discount variables
        Discount discount = null;
        double discountAmount = 0.0;

        // 4. Validate and apply discount if a discount code is provided
        if (discountCode != null && !discountCode.trim().isEmpty()) {
            discount = discountService.validateAndApplyDiscount(discountCode, cart.getCartItems().stream()
                    .mapToDouble(item -> item.getPriceAtAddTime() * item.getQuantity())
                    .sum(), userId, userUsageCount);

            // Calculate discount amount based on type
            if (discount.getType().equalsIgnoreCase("PERCENTAGE")) {
                discountAmount = (discount.getValue() / 100) * cart.getCartItems().stream()
                        .mapToDouble(item -> item.getPriceAtAddTime() * item.getQuantity())
                        .sum();
                if (discount.getMaxDiscountAmount() != null && discountAmount > discount.getMaxDiscountAmount()) {
                    discountAmount = discount.getMaxDiscountAmount();
                }
            } else if (discount.getType().equalsIgnoreCase("FIXED_AMOUNT")) {
                discountAmount = discount.getValue();
            }

            // Ensure discount does not exceed order amount
            double orderAmountBeforeDiscount = cart.getCartItems().stream()
                    .mapToDouble(item -> item.getPriceAtAddTime() * item.getQuantity())
                    .sum();
            if (discountAmount > orderAmountBeforeDiscount) {
                discountAmount = orderAmountBeforeDiscount;
            }
        }

        // 5. Create a new Order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("PENDING");
        order.setOrderedAt(java.time.LocalDateTime.now());

        // 6. Transfer CartItems to OrderItems
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrderTime(cartItem.getPriceAtAddTime());

            // Add to order
            order.addOrderItem(orderItem);
        }

        // 7. Apply discount to totalAmount
        order.setTotalAmount(order.getTotalAmount() - discountAmount);

        // 8. Save the order (cascades to order items)
        Order savedOrder = orderRepository.save(order);

        // 9. Mark the cart as ORDERED
        cart.setStatus("ORDERED");
        cartRepository.save(cart);

        return savedOrder;
    }

    /*
     * 2) "Sipariş Takibi" (#6)
     * - Retrieve all orders for a user
     */
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    /*
     * 3) "Sipariş Durumunu Güncelleme"
     * - Update the status of an order (e.g., PENDING -> COMPLETED)
     */
    public Order updateOrderStatus(Long orderId, String newStatus)
            throws IllegalArgumentException {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    /*
     * 4) Retrieve Order Details
     */
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    /*
     * 5) List all orders (for admin purposes)
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /*
     * 6) Find orders by status
     */
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}