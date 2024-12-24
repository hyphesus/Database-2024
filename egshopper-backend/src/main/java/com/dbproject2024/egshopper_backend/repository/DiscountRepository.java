package com.dbproject2024.egshopper_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbproject2024.egshopper_backend.model.Discount;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    // Find a discount by its unique code
    Optional<Discount> findByCode(String code);
}