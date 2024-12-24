package com.dbproject2024.egshopper_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbproject2024.egshopper_backend.model.OrderItem;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Find all order items for a specific order
    List<OrderItem> findByOrderId(Long orderId);

    // Find all order items for a specific product
    List<OrderItem> findByProductId(Long productId);
}