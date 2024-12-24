package com.dbproject2024.egshopper_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dbproject2024.egshopper_backend.model.Product;
import com.dbproject2024.egshopper_backend.service.ProductService;

/*
 * This controller will handle:
 *  - Listing products
 *  - Creating/updating products
 *  - Deleting products
 *  - Viewing a single product's details
 *  - Comparing multiple products (#9)
 *  - Possibly updating average rating (#15) 
 */

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /*
     * 1) "Add / Update Product" button:
     * POST /api/products
     * This endpoint is called when an admin or authorized user wants to create or
     * update a product.
     * (Requirement: Ürün ekleme/güncelleme is not explicitly in your bullet list,
     * but typical in e-commerce.)
     */
    @PostMapping
    public ResponseEntity<?> saveOrUpdateProduct(@RequestBody Product product) {
        try {
            Product saved = productService.saveProduct(product);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 2) "View All Products" button:
     * GET /api/products
     * This shows a list of all products. Also part of "Ürünleri İnceleme" (#1).
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /*
     * 3) "View Single Product" button:
     * GET /api/products/{id}
     * Allows the user to see details of a single product (images, rating, etc.).
     * (Also relevant to #1: "Ürün detaylarına bakma.")
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    /*
     * 4) "Delete Product" button:
     * DELETE /api/products/{id}
     * For removing a product from the database. Possibly admin-only.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * 5) "Update Average Rating" (Optional):
     * PUT /api/products/{id}/update-rating
     * We might call this after a comment is added or changed.
     */
    @PutMapping("/{id}/update-rating")
    public ResponseEntity<?> updateProductRating(@PathVariable Long id) {
        productService.updateAverageRating(id);
        return ResponseEntity.ok("Average rating updated for product ID " + id);
    }

    /*
     * 6) "Ürünleri Karşılaştırma" (#9)
     * POST /api/products/compare
     * We can pass a list of product IDs in the request body, and get a comparison.
     *
     * Example JSON body:
     * {
     * "productIds": [101, 102, 103]
     * }
     */
    @PostMapping("/compare")
    public ResponseEntity<String> compareProducts(@RequestBody List<Long> productIds) {
        if (productIds == null || productIds.size() < 2) {
            return ResponseEntity.badRequest().body("Please provide at least two product IDs.");
        }
        String comparisonResult = productService.compareProducts(productIds);
        return ResponseEntity.ok(comparisonResult);
    }
}