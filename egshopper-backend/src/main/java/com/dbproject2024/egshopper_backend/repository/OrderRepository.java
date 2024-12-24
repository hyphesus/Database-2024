package com.dbproject2024.egshopper_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbproject2024.egshopper_backend.model.Order;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find all orders for a specific user
    List<Order> findByUserId(Long userId);

    // Find orders by status
    List<Order> findByStatus(String status);
}