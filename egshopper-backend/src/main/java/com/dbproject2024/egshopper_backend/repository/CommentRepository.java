package com.dbproject2024.egshopper_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dbproject2024.egshopper_backend.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // We have CRUD methods out-of-the-box, like:
    // - save(Comment comment)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    //
    // If you need custom query methods later (e.g., findByProductId, filter by
    // rating, etc.),
    // you can add them here. For example:
    //
    // List<Comment> findByProductId(Long productId);
    // List<Comment> findByRatingOrderByCreatedAtDesc(int rating);
    //
    // We'll create or refine methods like these if necessary in the next steps.

}