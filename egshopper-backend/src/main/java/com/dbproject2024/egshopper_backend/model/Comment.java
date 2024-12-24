package com.dbproject2024.egshopper_backend.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

// further user entity will be created if needed
// import com.dbproject2024.egshopper_backend.model.User;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The text/content of the comment
    @Column(nullable = false, length = 1000)
    private String content;

    // The star rating (1-5) or it could be 0 if no rating.
    // We'll refine validation logic in the Service layer.
    private int rating;

    // Date and time the comment was posted
    private LocalDateTime createdAt = LocalDateTime.now();

    // Many comments can belong to one product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // The user who posted this comment (we'll ensure "Doğrulama" later)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // For replying to other comments:
    // A comment may have a "parent" if it's a reply to another comment.
    // If it's top-level, parentComment is null.
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    // Constructors
    public Comment() {
    }

    public Comment(String content, int rating, Product product, User user, Comment parentComment) {
        this.content = content;
        this.rating = rating;
        this.product = product;
        this.user = user;
        this.parentComment = parentComment;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters (you can use Lombok if you like)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }
}