package com.dbproject2024.egshopper_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dbproject2024.egshopper_backend.model.Comment;
import com.dbproject2024.egshopper_backend.model.Product;
import com.dbproject2024.egshopper_backend.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // For rating validation, e.g., 1-5
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;

    // =========== 1) CREATE / SAVE A COMMENT ===========
    // This could be used for top-level comments *or* replies, depending on whether
    // parentComment is set.
    public Comment saveComment(Comment comment) throws IllegalArgumentException {
        validateComment(comment);
        // If everything is valid, save it to DB
        return commentRepository.save(comment);
    }

    // =========== 2) GET A COMMENT BY ID ===========
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    // =========== 3) LIST ALL COMMENTS ===========
    // we might use filters (by product, by rating, etc.).
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // =========== 4) DELETE A COMMENT ===========
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    // =========== 5) VALIDATE COMMENT DATA ===========
    // We can expand this method with custom logic.
    private void validateComment(Comment comment) throws IllegalArgumentException {
        // Content must not be empty or null
        if (!StringUtils.hasText(comment.getContent())) {
            throw new IllegalArgumentException("Comment content cannot be empty.");
        }

        // Rating must be within 1..5 (if you want to enforce that)
        if (comment.getRating() < MIN_RATING || comment.getRating() > MAX_RATING) {
            throw new IllegalArgumentException("Comment rating must be between 1 and 5.");
        }

        // Product reference must not be null
        if (comment.getProduct() == null) {
            throw new IllegalArgumentException("Comment must be associated with a Product.");
        }

        // We could also check if user is null (if we require a user to comment)
        // If user is mandatory:
        // if (comment.getUser() == null) {
        // throw new IllegalArgumentException("You must be logged in to comment.");
        // }

        // If it's a reply, ensure the parent comment actually exists, etc.
        // ...
    }

    // =========== 6) OPTIONAL METHODS ===========

    // For example, fetch all comments for a given product, sorted by creation date
    // descending:
    public List<Comment> getCommentsByProductId(Long productId) {
        // We'll add a custom method to the repository if needed:
        // e.g., commentRepository.findByProductIdOrderByCreatedAtDesc(productId)
        // For now, we haven't added that method to CommentRepository, but you could do
        // so if you like.
        return commentRepository.findAll(); // Placeholder
    }

    // For "Yorumları Filtreleme" by rating, date, etc. you'd add additional
    // methods:
    // public List<Comment> getCommentsByRatingDesc(Long productId) { ... }

    // For "Yorumlara Yanıt Verme," you can do a special method to link a parent
    // comment:
    public Comment replyToComment(Comment reply, Comment parent) {
        reply.setParentComment(parent);
        validateComment(reply);
        return commentRepository.save(reply);
    }

    // If you want to compute average rating for a product here (versus in
    // ProductService):
    public double computeAverageRatingForProduct(Product product) {
        // In reality, we'd fetch comments for that product, sum up ratings, and divide.
        // For example:
        // List<Comment> comments = commentRepository.findByProductId(product.getId());
        // if (comments.isEmpty()) return 0.0;
        // double sum = 0;
        // for (Comment c : comments) sum += c.getRating();
        // return sum / comments.size();
        return 0.0; // Placeholder
    }
}