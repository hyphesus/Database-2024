package com.dbproject2024.egshopper_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dbproject2024.egshopper_backend.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Basic CRUD methods are inherited from JpaRepository:
    // - save(Product product)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    //
    // If you need custom queries (e.g. findByNameContainingIgnoreCase, etc.),
    // you can add them here. Example:
    //
    // List<Product> findByNameContainingIgnoreCase(String keyword);
}