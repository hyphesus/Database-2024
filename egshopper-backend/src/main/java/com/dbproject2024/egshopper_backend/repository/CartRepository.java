package com.dbproject2024.egshopper_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbproject2024.egshopper_backend.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Basic CRUD methods are inherited:
    // - save(Cart cart)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    //
    // You can add custom queries if needed, e.g.:
    // Optional<Cart> findByUserIdAndStatus(Long userId, String status);

}