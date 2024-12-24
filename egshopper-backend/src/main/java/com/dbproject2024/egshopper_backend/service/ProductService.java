package com.dbproject2024.egshopper_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbproject2024.egshopper_backend.model.Comment;
import com.dbproject2024.egshopper_backend.model.Product;
import com.dbproject2024.egshopper_backend.repository.ProductRepository;
import com.dbproject2024.egshopper_backend.repository.CommentRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // We'll inject CommentRepository so we can (optionally) calculate average
    // rating or do other comment-related logic.
    @Autowired
    private CommentRepository commentRepository;

    // =========== 1) CREATE or UPDATE a PRODUCT ===========
    public Product saveProduct(Product product) {
        // Basic validation could go here if needed
        // e.g. if (product.getPrice() < 0) throw new IllegalArgumentException("Price
        // can't be negative");
        return productRepository.save(product);
    }

    // =========== 2) GET ALL PRODUCTS ===========
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // =========== 3) GET A PRODUCT BY ID ===========
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // =========== 4) DELETE A PRODUCT ===========
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // =========== 5) UPDATE AVERAGE RATING (Optional) ===========
    // If we store average rating in the product table, we can recalc it whenever a
    // new comment is added or updated.
    public void updateAverageRating(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null)
            return;

        // Get all comments for this product
        List<Comment> comments = commentRepository.findAll();
        // In a real scenario, you'd have a custom finder like:
        // commentRepository.findByProductId(productId)

        // Filter to only the ones for this product
        double sum = 0;
        int count = 0;
        for (Comment c : comments) {
            if (c.getProduct().getId().equals(productId)) {
                sum += c.getRating();
                count++;
            }
        }

        double avg = (count > 0) ? (sum / count) : 0.0;
        product.setAverageRating(avg);
        productRepository.save(product);
    }

    // =========== 6) ÜRÜN KARŞILAŞTIRMA (Requirement #9) ===========
    // We'll do a simple example: compare multiple product IDs, returning some
    // summary of differences.
    public String compareProducts(List<Long> productIds) {
        // 1. Fetch products by IDs
        List<Product> products = productRepository.findAllById(productIds);

        // 2. Build a comparison string or JSON.
        // Real scenario might have a better data structure for the frontend,
        // but let's keep it simple.

        if (products.size() < 2) {
            return "Please select at least two products to compare.";
        }

        StringBuilder comparisonResult = new StringBuilder("Comparison Results:\n");
        for (Product p : products) {
            comparisonResult.append(" - ID: ").append(p.getId())
                    .append(", Name: ").append(p.getName())
                    .append(", Price: ").append(p.getPrice())
                    .append(", Average Rating: ").append(p.getAverageRating())
                    .append("\n");
        }
        return comparisonResult.toString();
    }
}