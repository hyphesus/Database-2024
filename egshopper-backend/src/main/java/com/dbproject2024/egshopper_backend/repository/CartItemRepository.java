package com.dbproject2024.egshopper_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbproject2024.egshopper_backend.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // we inherit basic CRUD: save(), findById(), findAll(), deleteById(),
    // etc.
    //
    // If you need specialized lookups, e.g. find all cart items for a certain cart:
    // List<CartItem> findByCartId(Long cartId);
}