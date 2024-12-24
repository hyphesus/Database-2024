package com.dbproject2024.egshopper_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dbproject2024.egshopper_backend.model.Comment;
import com.dbproject2024.egshopper_backend.service.CommentService;

// This controller manages operations related to "Yorum Ekleme," "Yorumları Değerlendirme,"
// "Yorumlara Yanıt Verme," and more.

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /*
     * 1) "Yorum Ekleme"
     * - POST /api/comments
     * - This endpoint is called when a user wants to add a top-level comment to a
     * product.
     * The 'Comment' object in the request body should contain the product ID
     * (comment.product.id),
     * as well as the comment text and rating.
     *
     * Example JSON body:
     * {
     * "content": "Amazing product!",
     * "rating": 5,
     * "product": { "id": 123 },
     * "user": { "id": 45 } // if user is mandatory
     * }
     */
    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody Comment comment) {
        try {
            Comment saved = commentService.saveComment(comment);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 2) "Yorum Okuma" / "Yorumları İnceleme" (#1 part: "Ürün yorumlarını okuma")
     * - GET /api/comments
     * - This endpoint can be used to retrieve all comments (or you could add
     * filtering).
     * Optionally, we can extend it to filter by productId, rating, etc.
     *
     * Example:
     * GET /api/comments?productId=123 -> only return comments for product 123
     * GET /api/comments?sort=highest -> sort by rating descending
     */
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String sort) {

        // Basic approach: if productId is not null, filter by product
        // else return all comments. For now, we'll do something simple:
        List<Comment> comments = commentService.getAllComments();
        // TODO: Add actual filtering logic (if productId != null => filter by product,
        // etc.)
        return ResponseEntity.ok(comments);
    }

    /*
     * 3) "Yorum Detayı" (not explicitly in the bullet points, but useful)
     * - GET /api/comments/{id}
     * - Fetch a single comment by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Comment found = commentService.getCommentById(id);
        if (found != null) {
            return ResponseEntity.ok(found);
        }
        return ResponseEntity.notFound().build();
    }

    /*
     * 4) "Yorumlara Yanıt Verme" (#13)
     * - POST /api/comments/{parentId}/reply
     * - This endpoint is called when the user (or store owner) wants to reply to an
     * existing comment.
     *
     * Example JSON body:
     * {
     * "content": "Hello! Thanks for your feedback",
     * "rating": 0, // or we might omit rating if it's just a reply
     * "product": { "id": 123 },
     * "user": { "id": 45 }
     * }
     */
    @PostMapping("/{parentId}/reply")
    public ResponseEntity<?> replyToComment(@PathVariable Long parentId, @RequestBody Comment reply) {
        // Fetch the parent comment
        Comment parent = commentService.getCommentById(parentId);
        if (parent == null) {
            return ResponseEntity.badRequest().body("Parent comment not found");
        }
        try {
            Comment savedReply = commentService.replyToComment(reply, parent);
            return ResponseEntity.ok(savedReply);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * 5) "Yorumları Değerlendirme" (#11)
     * Since we store "rating" in the Comment entity, a user effectively
     * "değerlendirme" by posting/updating a comment with a star rating.
     * If you want a separate endpoint just for rating, you can do:
     *
     * - PUT /api/comments/{id}/rate?value=5
     *
     * But for now, let's keep it simple. The user can pass "rating" in the body
     * to the standard endpoints above. We'll consider a separate rating endpoint
     * if that suits your design.
     */

    /*
     * 6) "Yorumları Filtreleme" (#12)
     * Right now, the GET /api/comments can be extended with query params
     * (sort=highest, productId=..., rating=..., etc.). We'll place that logic
     * in the CommentService in the future if needed.
     *
     * Example approach:
     * if (productId != null) {
     * return commentRepository.findByProductId(productId);
     * }
     * if (sort.equals("highest")) {
     * return commentRepository.findAll(Sort.by("rating").descending());
     * }
     */

    /*
     * 7) "Yorum Silme" (not explicitly in the bullet, but typical)
     * - DELETE /api/comments/{id}
     * - Admin or comment owner might remove a comment.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * 8) "Doğrulama ve Güvenilirlik" (#14)
     * In practice, you'd ensure only logged-in users (with real accounts) can post
     * or reply.
     * That requires authentication (e.g., Spring Security).
     * For now, we assume the 'user' field in the Comment is properly set.
     * A real app might do:
     *
     * - You get the authenticated user from SecurityContext,
     * - You set comment.setUser(currentUser),
     * - Then call commentService.saveComment(comment).
     */

    /*
     * 9) "Ürün İnceleme İstatistikleri" (#15)
     * Typically you'd call a method from ProductService or CommentService
     * to compute average rating, total comments, etc.
     * We might do something like:
     * 
     * GET /api/comments/stats?productId=123
     *
     * Then commentService.computeAverageRatingForProduct(product)
     * and also get total comment count.
     *
     * For now, let's not fully implement it here, but we can add it if you want:
     */

}